package com.ntg.engine.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ntg.engine.entites.Schedule;
import com.ntg.engine.entites.SlaMilestones;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

public interface SendMailService {

    /**
     * @changedBy:Aya.Ramadan to remove toEmail and CCEmail from email template and
     * add it to schedule Object
     */
    public void replaseValuesEmailBody(String emailTempleteRecId, Schedule scheduleObject, SlaMilestones milestone, Map<String, Object> tenantInfo)  throws Exception;

    public void replaceValuesForSendingEmail(Long typeId, Schedule schedule, String emailTempleteRecId,
                                             String tableName, Map<String, Object> cell, SlaMilestones milestone, Map<String, Object> tenantInfo) throws Exception ;

    public void sendMail(List<String> toMails, List<String> ccMails, List<String> bccMails, String subject,
                         String mailBody, byte[] attachments) throws MessagingException, JsonProcessingException, UnsupportedEncodingException;

}
