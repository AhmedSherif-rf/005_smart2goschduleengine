package com.ntg.engine.service.serviceImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ntg.common.NTGMessageOperation;
import com.ntg.engine.dto.LoginSettings;
import com.ntg.engine.dto.MailSettings;
import com.ntg.engine.dto.StringResponse;
import com.ntg.engine.entites.EmailTemplate;
import com.ntg.engine.entites.Schedule;
import com.ntg.engine.entites.SlaMilestones;
import com.ntg.engine.entites.Types;
import com.ntg.engine.exceptions.NTGException;
import com.ntg.engine.jobs.service.CommonCachingFunction;
import com.ntg.engine.repository.EmailTemplateRepository;
import com.ntg.engine.service.LoginService;
import com.ntg.engine.service.RecurringService;
import com.ntg.engine.service.SendMailService;
import com.ntg.engine.service.TypesService;
import com.ntg.engine.util.Constants;
import com.ntg.engine.util.Utils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.activation.DataHandler;
import javax.mail.*;
import javax.mail.Message.RecipientType;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @changedBy:Aya.Ramadan to remove toEmail and CCEmail from email template and
 * add it to schedule Object
 */
@Service
public class SendMailServiceImpl implements SendMailService {

    @Autowired
    private MailSettings mailSettings;

    @Autowired
    private RecurringService recurringService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private LoginSettings loginSettings;

    @Autowired
    private EmailTemplateRepository emailTemplateRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TypesService typesService;

    @Autowired
    private CommonCachingFunction commonCachingFun;

    private String sessionToken = "";
    private Pattern pattern = Pattern.compile("\\{\\{(.*?)\\}\\}");
    private Matcher matcher;




    @Override
    public void replaseValuesEmailBody(String emailTempleteRecId, Schedule scheduleObject, SlaMilestones milestone, Map<String, Object> tenantInfo) throws Exception {
//@Aya.Ramadan Dev-00000521 : sla millstone bugs

        String tenantSchema = (Utils.isNotEmpty(tenantInfo) && Utils.isNotEmpty(tenantInfo.get("tenantSchema"))) ? tenantInfo.get("tenantSchema").toString() : "";

        Types curSchType = typesService.findByRecId(Long.valueOf(scheduleObject.getTypeId()));

        if (curSchType != null && curSchType.getObjects() != null) {
            String tableName = "sch_" + scheduleObject.getTableName();
            List<Map<String, Object>> res;

            res = recurringService.findObjectsDependOnRecurringType(scheduleObject.getRecurringType(),
                    scheduleObject.getRecurringValue(), scheduleObject.getRecurringUnit(),
                    scheduleObject.getRecId(), tableName, scheduleObject.getQuery(),
                    curSchType.getObjects().getSlaMilestoneTableName(), tenantSchema);
            if (Utils.isNotEmpty(res)) {

                for (Map<String, Object> cell : res) {

                    replaceValuesForSendingEmail(scheduleObject.getTypeId(), scheduleObject, emailTempleteRecId,
                            tableName, cell, milestone, tenantInfo);
                }
            }

        }


    }

    @Override
    public void replaceValuesForSendingEmail(Long typeId, Schedule schedule, String emailTempleteRecId,
                                             String tableName, Map<String, Object> cell, SlaMilestones milestone, Map<String, Object> tenantInfo)
            {
        String companyName = (Utils.isNotEmpty(tenantInfo) && Utils.isNotEmpty(tenantInfo.get("companyName"))) ? tenantInfo.get("companyName").toString() : "NTG";
        String tenantSchema = (Utils.isNotEmpty(tenantInfo) && Utils.isNotEmpty(tenantInfo.get("tenantSchema"))) ? tenantInfo.get("tenantSchema").toString() : "";

        try {

            EmailTemplate emailTemplate = emailTemplateRepository.findByRecId(Long.valueOf(emailTempleteRecId));
            String mailBody = emailTemplate.getTemplateBody();
            String mailSubject = emailTemplate.getEmailSubject();
            //@Aya.Ramadan => Dev-00003294 :For "issue in schedule job when add email template with insert fields. not appear data of UDA you selected it in mail"
            if (mailBody != null && mailBody.contains("{{") && mailBody.contains("}}")) {
                mailBody = commonCachingFun.resolveDescription(mailBody, commonCachingFun.loadCrmObject(Long.valueOf(
                                cell.get("typeid").toString()), Long.valueOf(cell.get("recid").toString()), companyName),
                        true,Long.valueOf(
                                cell.get("typeid").toString()), Long.valueOf(cell.get("recid").toString()) , companyName);
            }
            if (mailSubject != null && mailSubject.contains("{{") && mailSubject.contains("}}")) {
                mailSubject = commonCachingFun.resolveDescription(mailSubject, commonCachingFun.loadCrmObject(Long.valueOf(
                                cell.get("typeid").toString()), Long.valueOf(cell.get("recid").toString()), companyName),
                        true,Long.valueOf(
                                cell.get("typeid").toString()), Long.valueOf(cell.get("recid").toString()) , companyName);
            }

            Set<String> columnName = cell.keySet();
            if (Utils.isNotEmpty(emailTemplate.getTemplateBody())) {
                matcher = pattern.matcher(emailTemplate.getTemplateBody());
                while (matcher.find()) {
                    if (columnName.contains(matcher.group(1).trim().toLowerCase())) {
                        Object obj = cell.get(matcher.group(1).trim().toLowerCase());
                        mailBody = mailBody.replace("{{" + matcher.group(1) + "}}",
                                " " + (Utils.isNotEmpty(obj) ? obj.toString() : "  ") + " ");

                    } else {
                        System.err.println(
                                "(Info) ==> No Column found with Name :" + matcher.group(1).toLowerCase());
                    }
                }
            }

            List<String> toEmailList = new ArrayList<String>();
            List<String> ccEmailList = new ArrayList<String>();
            if (milestone != null) {
                if (milestone.getStaticEmail()) {
                    toEmailList = getEmailsList(milestone.getToEmail(), cell, companyName);
                    ccEmailList = (Utils.isNotEmpty(milestone.getCcEmail()) ? getEmailsList(milestone.getCcEmail(), cell, companyName)
                            : null);
                } else if (milestone.getDynamicEmail()) {
                    toEmailList = getUdaValue(milestone.getToDynamicMailUda(), cell, companyName);
                    ccEmailList = getUdaValue(milestone.getCcDynamicMailUda(), cell, companyName);
                }
            } else {
                if (schedule.isStaticEmail()) {
                    toEmailList = getEmailsList(schedule.getToEmail(), cell, companyName);
                    ccEmailList = (Utils.isNotEmpty(schedule.getCcEmail()) ? getEmailsList(schedule.getCcEmail(), cell, companyName)
                            : null);
                } else if (schedule.isDynamicEmail()) {
                    toEmailList = getUdaValue(schedule.getToDynamicMailUda(), cell, companyName);
                    ccEmailList = getUdaValue(schedule.getCcDynamicMailUda(), cell, companyName);
                }
            }
            if (Utils.isEmpty(toEmailList)) {
                throw new NTGException("",
                        "Email Template hasnot toMail  to send to it Template Recid:emailTempleteRecId");
            }
            sendMail(toEmailList, ccEmailList, null, mailSubject, mailBody, commonCachingFun.loadEmailAttachments(emailTemplate.getRecId()
                    ,Long.valueOf(cell.get("recid").toString()),companyName ));
            String history = " Email sent To " + toEmailList + ", CC " + ccEmailList + " For " + mailSubject;
            System.out.println("(Info) ==> " + history);
            recurringService.saveScheduleJobHistor(Long.valueOf(cell.get("recid").toString()), typeId,
                    schedule.getRecId(), history , tableName, (milestone != null) ? milestone.getRecId() : null, tenantSchema);
        } catch (Exception ex) {
            System.out.println("(Info) ==> Fail To Process ID:" + cell.get("recid") + ", Type: " + typeId + " SchID: " + schedule.getRecId() +
                    ",Table:" + tableName + ", ==> " + ex.getMessage());
            recurringService.saveScheduleJobHistor(Long.valueOf(cell.get("recid").toString()), typeId,
                    schedule.getRecId(), NTGMessageOperation.GetErrorTrace(ex), tableName, (milestone != null) ? milestone.getRecId() : null, tenantSchema);

        }


    }

    private List<String> getUdaValue(String to, Map<String, Object> cell, String companyName) {

        if (Utils.isNotEmpty(to)) {
            if (to.startsWith("uda.")) {
                to = to.replace("uda.", "");
            }
            return getEmailsList((String) cell.get(to), cell, companyName);
        }
        return null;
    }

    @Override
    public void sendMail(List<String> toMails, List<String> ccMails, List<String> bccMails, String subject,
                         String mailBody, byte[] attachments) throws MessagingException, JsonProcessingException, UnsupportedEncodingException {
        if (mailSettings.getSendMailByEngine()) {
            sendMailByEngin(toMails, ccMails, bccMails, subject, mailBody, attachments);
        }
    }

    private void sendMailByEngin(List<String> toMails, List<String> ccMails, List<String> bccMails, String subject,
                                 String mailBody, byte[] attachments) throws MessagingException, UnsupportedEncodingException {
        Properties props = System.getProperties();

        props.put("mail.smtp.auth", mailSettings.getMailAuth());
        props.put("mail.smtp.host", mailSettings.getMailServer());
        props.put("mail.smtp.port", mailSettings.getPortNumber());
        props.setProperty("mail.smtp.starttls.enable", mailSettings.getStarttls());
        props.setProperty("mail.smtp.ssl.enable", mailSettings.getSsl());

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(mailSettings.getMailUserName(),
                        mailSettings.getMailPassword());
            }
        });
        session.setDebug(true);
        Message message;
        message = getMessage(toMails, ccMails, bccMails, subject, mailBody, session, attachments);
        Transport.send(message);
    }

    private List<String> getEmailsList(String mails, Map<String, Object> cell, String companyName) {
        String assinedMember = null;
        String assinedGroup = null;
        Set<String> columnsName = cell.keySet();
        for (String column : columnsName) {
            if (column.toLowerCase().startsWith("ai_mem")) {
                assinedMember = Utils.isNotEmpty(cell.get(column)) ? cell.get(column).toString() : null;
            } else if (column.toLowerCase().startsWith("ai_group")) {
                assinedGroup = Utils.isNotEmpty(cell.get(column)) ? cell.get(column).toString() : null;
            }
        }

        String[] toEmails = mails.split(";");
        List<String> emailList = Arrays.asList(toEmails);

        if (hasDynamicValue(emailList, "{{")) {
            Collections.replaceAll(emailList, "{{assigned_group}}", getEmail(assinedGroup, true, companyName));
            emailList = replaceAssigneByEmail(assinedMember, emailList, Constants.ASSIGNED_MEMBER, companyName);
            emailList = replaceAssigneByEmail(assinedGroup, emailList, Constants.ASSIGNED_GROUP, companyName);
        }
        return emailList;
    }

    public List<String> replaceAssigneByEmail(String assine, List<String> emailList, String assineType, String companyName) {
        String assineMail = null;
        if (Utils.isNotEmpty(assine)) {
            assineMail = getEmail(assine, false, companyName);
            if (Utils.isNotEmpty(assineMail)) {
                Collections.replaceAll(emailList, assineType, assineMail);
            }
        }

        return emailList;

    }

    private String getEmail(String name, boolean isGroup, String companyName) {
        StringResponse stringResponse = null;
        HttpHeaders headersSms = new HttpHeaders();
        headersSms.setContentType(MediaType.APPLICATION_JSON);
        sessionToken = loginService.logIn(companyName);
        if (Utils.isNotEmpty(sessionToken)) {
            headersSms.set("SessionToken", sessionToken);
            headersSms.set("User-Agent", "schedulerBackEnd");
            HttpEntity<String> entity = new HttpEntity<String>(name, headersSms);
            String getUserAndGroupEmailUrl = loginSettings.getUrl() + mailSettings.getEmployeesGroupsEmailsURL() + isGroup;
            ResponseEntity<StringResponse> result = restTemplate.exchange(getUserAndGroupEmailUrl, HttpMethod.POST, entity, StringResponse.class);
            stringResponse = result.getBody();
        } else {
            System.out.println("================= Failed to login to get user or group email==============");
        }
        return (Utils.isNotEmpty(stringResponse)) ? stringResponse.returnValue : null;

    }

    private Message getMessage(List<String> toMails, List<String> ccMails, List<String> bccMails, String subject,
                               String mailBody, Session session, byte[] attachments) throws MessagingException, UnsupportedEncodingException {
        Message message = new MimeMessage(session);

        message.setFrom(new InternetAddress(mailSettings.getFromMailAddress()));

        InternetAddress[] tos = InternetAddress.parse(StringUtils.join(toMails, ","));
        message.setRecipients(RecipientType.TO, tos);
        if (ccMails != null) {
            InternetAddress[] ccs = InternetAddress.parse(StringUtils.join(ccMails, ","));
            message.setRecipients(RecipientType.CC, ccs);
        }
        if (bccMails != null) {
            InternetAddress[] bCCs = InternetAddress.parse(StringUtils.join(bccMails, ","));
            message.setRecipients(RecipientType.BCC, bCCs);
        }
        message.setSubject(subject);
        message.setSentDate(new Date());
        message.setHeader("Content-Type", "text/html; charset=UTF-8");
        Multipart multipart = new MimeMultipart("mixed");
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(mailBody, "text/html; charset=UTF-8");
        multipart.addBodyPart(messageBodyPart);
        if (Utils.isNotEmpty(attachments)) {
            ZipEntry localFileHeader;
            int readLen;
            byte[] readBuffer = new byte[4096];

            InputStream is = new ByteArrayInputStream(attachments);
            try (ZipInputStream zipInputStream = new ZipInputStream(is)) {
                while ((localFileHeader = zipInputStream.getNextEntry()) != null) {
                    try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                        while ((readLen = zipInputStream.read(readBuffer)) != -1) {
                            outputStream.write(readBuffer, 0, readLen);
                        }
                        ByteArrayDataSource bds = new ByteArrayDataSource(outputStream.toByteArray(),"application/octet-stream");
                        MimeBodyPart mimeBodyPart = new MimeBodyPart();
                        mimeBodyPart.setDisposition("attachment");
                        mimeBodyPart.setFileName(MimeUtility.encodeText(localFileHeader.getName()));
                        mimeBodyPart.setDataHandler(new DataHandler(bds));
                        multipart.addBodyPart(mimeBodyPart);
                    }
                }
            } catch (IOException ignored) {}
        }
        message.setContent(multipart);
        return message;
    }

    public static boolean hasDynamicValue(List<String> emails, String key) {
        return emails.stream().filter(k -> key.contains(k)).collect(Collectors.toList()).size() > 0;
    }
}
