package com.ntg.engine.service.serviceImpl;

import com.ntg.common.NTGMessageOperation;
import com.ntg.engine.dto.LoginSettings;
import com.ntg.engine.dto.ScheduleResponse;
import com.ntg.engine.entites.Types;
import com.ntg.engine.jobs.service.CommonCachingFunction;
import com.ntg.engine.service.RecurringService;
import com.ntg.engine.service.RuleInvokerService;
import com.ntg.engine.service.TypesService;
import com.ntg.engine.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class RuleInvokerServiceImpl implements RuleInvokerService {

    @Autowired
    private LoginSettings sms;

    @Autowired
    private RecurringService recurringService;

    @Autowired
    private CommonCachingFunction CommonCachingFunction;

    @Value("${RuleInvoker.BatchSize}")
    public  Long batchSize;
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TypesService typesService;

    @Override
    public void getRestServiceResponse(String scheduleId, String typeId, String saveAfterDoRule, String query,
                                       Long recurringType, Long recurringUnitNum, Long recurringValue, String tableName, Map<String, Object> tenantInfo) {
        try {
            String companyName = (Utils.isNotEmpty(tenantInfo) && Utils.isNotEmpty(tenantInfo.get("companyName"))) ? tenantInfo.get("companyName").toString() : "NTG";
            String tenantSchema = (Utils.isNotEmpty(tenantInfo) && Utils.isNotEmpty(tenantInfo.get("tenantSchema"))) ? tenantInfo.get("tenantSchema").toString() : "";

            Types curSchType = typesService.findByRecId(Long.valueOf(typeId));

            if (curSchType != null && curSchType.getObjects() != null) {

                List<Map<String, Object>> res = recurringService.findObjectsDependOnRecurringType(recurringType,
                        recurringValue, recurringUnitNum, Long.valueOf(scheduleId), "sch_" + tableName, query,
                        curSchType.getObjects().getSlaMilestoneTableName(), tenantSchema);

                if (!res.isEmpty()) {
                    Iterator<Map<String, Object>> resultIterable = res.iterator();
                    List<Map<String, Object>> result = new ArrayList<>();
                    while (resultIterable.hasNext()) {
                        result.add(resultIterable.next());
                        if (result.size() == batchSize) {
                            sendBatch(scheduleId, typeId, saveAfterDoRule, tableName, companyName, tenantSchema, result);
                            result = new ArrayList<>();
                        }
                    }
                    if(!result.isEmpty()){
                        sendBatch(scheduleId, typeId, saveAfterDoRule, tableName, companyName, tenantSchema, result);
                    }
                }
            }
        } catch (Exception e) {
            NTGMessageOperation.PrintErrorTrace(e);
        }

    }

    private void sendBatch(String scheduleId, String typeId, String saveAfterDoRule, String tableName, String companyName,
                           String tenantSchema, List<Map<String, Object>> result) throws InterruptedException {
        List<Map<String, Object>> listToBeSent = new ArrayList<>();
        for (Map<String, Object> eachObject : result) {
            Map<String, Object> mapToBeSent = new Hashtable<>();
            mapToBeSent.put("recid", eachObject.get("recid"));
            listToBeSent.add(mapToBeSent);
        }
        String sessionToken = CommonCachingFunction.BackEndLogin(companyName);
        if (Utils.isNotEmpty(sessionToken)) {
            // @Aya.Ramadan Dev-00000521 : sla millstone bugs
            HttpHeaders headersSms = new HttpHeaders();
            headersSms.setContentType(MediaType.APPLICATION_JSON);
            headersSms.set("SessionToken", sessionToken);
            headersSms.set("User-Agent", "schedulerBackEnd");
            // call new WS for rule invoker by schedule engine
            String getSMSScheduleUrl = sms.getUrl() + "/rest/Schedule/RuleInvoker/" + scheduleId + "/" + typeId
                    + "/" + saveAfterDoRule;
            HttpEntity<List<Map<String, Object>>> entitySms = new HttpEntity<>(
                    listToBeSent, headersSms);
            ResponseEntity<ScheduleResponse> responseEntity = restTemplate.postForEntity(getSMSScheduleUrl,
                    entitySms, ScheduleResponse.class);
            ScheduleResponse scheduleRes = responseEntity.getBody();
            if (scheduleRes != null && (Utils.isNotEmpty(scheduleRes.getObjectIds()) || Utils.isNotEmpty(scheduleRes.getFailObjectIds()))) {
                if (Utils.isNotEmpty(scheduleRes.getObjectIds()) ) {
                    System.out.println("=========> Start Inserting Sucess History into Table (" + "sch_" + tableName
                            + ") #Rows(" + scheduleRes.getObjectIds().size() + ")<========");
                    for (Long recid : scheduleRes.getObjectIds()) {
                        recurringService.saveScheduleJobHistor(recid, Long.valueOf(typeId),
                                Long.valueOf(scheduleId), "Rule Execution Done", "sch_" + tableName, null, tenantSchema);
                    }
                }
                if (scheduleRes.getFailObjectIds() != null) {
                    System.out.println("=========> Start Inserting Fail History into Table (" + "sch_" + tableName
                            + ") #Rows(" + scheduleRes.getFailObjectIds().size() + ")<========");
                    for (Long recId : scheduleRes.getFailObjectIds().keySet()) {
                        recurringService.saveScheduleJobHistor(recId, Long.valueOf(typeId),
                                Long.valueOf(scheduleId), scheduleRes.getFailObjectIds().get(recId) == null?
                                        "Rule Execution Failed ":"Rule Execution Failed with message : \n"
                                        + scheduleRes.getFailObjectIds().get(recId), "sch_" + tableName, null, tenantSchema);
                    }
                }
            }
        } else {
            System.out.println("================= Failed to login to get user or group email==============");
        }
    }
}
