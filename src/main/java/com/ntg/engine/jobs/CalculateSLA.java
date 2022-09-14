package com.ntg.engine.jobs;

import com.ntg.common.NTGMessageOperation;
import com.ntg.engine.dto.GenericObjectSLA;
import com.ntg.engine.dto.LoginSettings;
import com.ntg.engine.entites.Schedule;
import com.ntg.engine.entites.ScheduleAction;
import com.ntg.engine.entites.SlaMilestones;
import com.ntg.engine.entites.Types;
import com.ntg.engine.jobs.service.CommonCachingFunction;
import com.ntg.engine.repository.GenericObjectSlaMilestonesRepository;
import com.ntg.engine.repository.SlaMilestoneRepository;
import com.ntg.engine.service.RecurringService;
import com.ntg.engine.service.TypesService;
import com.ntg.engine.service.serviceImpl.RuleInvokerServiceImpl;
import com.ntg.engine.service.serviceImpl.SendMailServiceImpl;
import com.ntg.engine.util.Utils;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;
import java.util.Map;


public class CalculateSLA implements Job {

    @Autowired
    private RecurringService recurringService;

    @Autowired
    private SendMailServiceImpl sendMailServiceImpl;

    @Autowired
    private RuleInvokerServiceImpl ruleInvokerServiceImpl;

    @Autowired
    private SlaMilestoneRepository slaMilestoneRepository;


    @Value("${SLA.calulatedSLAURL}")
    private String calculatedSLAServiceURL;

    @Value("${loginSettings.url}")
    private String baseURL;

    @Value("${SLA.calculateSLARemainingTime}")
    private String calculateSLARemainingTime;

    @Autowired
    private CommonCachingFunction CommonCachingFunction;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LoginSettings loginSettings;

    @Autowired
    private TypesService typesService;

    @Autowired
    private GenericObjectSlaMilestonesRepository genericObjectSlaMilestonesRepo;

    /**
     * @param jobExecutionContext
     * @return void
     * @author gabr
     * @description All Objects calculate SLA for each object and know the SLA
     * percentage consumed if it match the range execute the the action
     * Call Web service that gets The consumed SLA
     * @category SLA Calculation Job
     */

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("=================================Calculate SLA Job Start" + new Date());
        // sessionToken = loginService.logIn();
        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
        Schedule scheduleObject = null;
        Map<String, Object> tenantInfo = null;
        try {
            scheduleObject = (Schedule) jobDataMap.get("scheduleObject");
            tenantInfo = (Utils.isNotEmpty(jobDataMap.get("tenantInfo"))) ? (Map<String, Object>) jobDataMap.get("tenantInfo") : null;
            EngineMainJob.RunninJobs.put(scheduleObject.getJobName(), jobDataMap);
        } catch (Exception e) {
            e.getMessage();
        }

        List<Map<String, Object>> objectsList;
        try {

            Types curSchType = typesService.findByRecId(scheduleObject.getTypeId());

            if (curSchType != null && curSchType.getObjects() != null) {
                String tenantSchema = (Utils.isNotEmpty(tenantInfo) && Utils.isNotEmpty(tenantInfo.get("tenantSchema"))) ? tenantInfo.get("tenantSchema").toString() : "";
                objectsList = recurringService.findObjectsDependOnRecurringType(scheduleObject.getRecurringType(),
                        scheduleObject.getRecurringValue(), scheduleObject.getRecurringUnit(),
                        scheduleObject.getRecId(), "sch_" + scheduleObject.getTableName(), scheduleObject.getQuery(),
                        curSchType.getObjects().getSlaMilestoneTableName(), tenantSchema);

                if (Utils.isNotEmpty(objectsList)) {
                    calculateSLAMailStone(objectsList, scheduleObject, curSchType.getObjects().getSlaMilestoneTableName(), tenantInfo);
                }
            }


        } catch (Exception e) {
            // TODO Auto-generated catch block
            NTGMessageOperation.PrintErrorTrace(e);
        }
    }

    //@Aya.Ramadan To calculateSLARemainingTime for engin =>Dev-00000521 : sla millstone bugs
    private void calculateSLAMailStone(List<Map<String, Object>> objectsList, Schedule scheduleObject, String slaMilestoneTableName, Map<String, Object> tenantInfo) {
        String companyName = (Utils.isNotEmpty(tenantInfo) && Utils.isNotEmpty(tenantInfo.get("companyName"))) ? tenantInfo.get("companyName").toString() : "NTG";
        String tenantSchema = (Utils.isNotEmpty(tenantInfo) && Utils.isNotEmpty(tenantInfo.get("tenantSchema"))) ? tenantInfo.get("tenantSchema").toString() : "";

        for (Map<String, Object> object : objectsList) {

            Long sLAThresholdType = Long.valueOf(object.get("sla_threshold_type").toString());
            Long sLAThresholdPeriod = Long.valueOf(object.get("sla_threshold_period").toString());
            Long genericObjectRecId = Long.valueOf(object.get("recid").toString());
            Long genericObjectSlaRecId = Long.valueOf(object.get("imsla").toString());
            if (sLAThresholdType == 2) {
                sLAThresholdPeriod = sLAThresholdPeriod * 60;
            }
            List<SlaMilestones> slaMilestones = slaMilestoneRepository
                    .findBySlaProfileId(scheduleObject.getSlaProfileId());
            for (SlaMilestones SlaMilestone : slaMilestones) {
                Double remainingSLAValue = 0.0;
                try {
                    remainingSLAValue = getRemaining(object, companyName);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                Double rang = ((sLAThresholdPeriod - remainingSLAValue) / sLAThresholdPeriod) * 100;
                if (SlaMilestone.fromRange <= rang && rang <= SlaMilestone.toRange) {
                    genericObjectSlaMilestonesRepo.saveMilestonesOnGenericObject(genericObjectRecId, genericObjectSlaRecId, SlaMilestone, slaMilestoneTableName, tenantSchema);
                    switch (Long.valueOf(SlaMilestone.getApplyedActionId()).intValue()) {
                        case ScheduleAction.ScheduleActionSendEmail:
                            sendMailServiceImpl.replaceValuesForSendingEmail(scheduleObject.getTypeId(),
                                    scheduleObject, String.valueOf(SlaMilestone.getEmailTemplate().getRecId()),
                                    "sch_" + scheduleObject.getTableName(), object, SlaMilestone, tenantInfo);
                            break;
                        case ScheduleAction.ScheduleActionRULE_INVOKER:
                            ruleInvokerServiceImpl.getRestServiceResponse(scheduleObject.getRecId().toString(),
                                    String.valueOf(scheduleObject.getTypeId()),
                                    SlaMilestone.getSaveAfterDoRule().toString(), scheduleObject.getQuery(),
                                    scheduleObject.getRecurringType(), scheduleObject.getRecurringUnit(),
                                    scheduleObject.getRecurringValue(), "sch_" + scheduleObject.getTableName(), tenantInfo);

                            break;
                        default:
                            break;
                    }

                }
            }
        }
    }

    /**
     * @param object
     * @return SLARemaining Time from back end
     * @throws InterruptedException
     * @AddedBy:Aya.Ramadan to calculate remaining value from back end
     */
    private Double getRemaining(Map<String, Object> object, String companyName) throws InterruptedException {

        Double value = 0.0;
        Long objectRecId = Long.valueOf(object.get("recid").toString());
        String calculatedSLA = baseURL + "/rest/" + calculateSLARemainingTime + "/" + object.get("typeId") + "/" + objectRecId;
        HttpHeaders headers = new HttpHeaders();
        String key = "Login_" + companyName + "_" + loginSettings.getUserName();
        String SessionToken = CommonCachingFunction.CachingCommonData.get(key).toString();
        if (Utils.isNotEmpty(SessionToken)) {
            headers.set("SessionToken", SessionToken);
            headers.set("User-Agent", "schedulerBackEnd");
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<String>(null, headers);
            ResponseEntity<GenericObjectSLA[]> responseEntity = restTemplate.postForEntity(calculatedSLA, entity,
                    GenericObjectSLA[].class);
            if (Utils.isNotEmpty(responseEntity.getBody())) {
                for (GenericObjectSLA engineCalcObject : responseEntity.getBody()) {
                    value = (engineCalcObject.getRemainingSlaValue() != null) ? engineCalcObject.getRemainingSlaValue() : 0.0;
                }
            }
        } else {
            System.out.println("================= Failed to login to get user or group email==============");
        }
        return value;
    }
}
		