package com.ntg.engine.jobs;

import com.ntg.engine.dto.EngineCalculationDTO;
import com.ntg.engine.entites.Schedule;
import com.ntg.engine.repository.custom.SqlHelperDao;
import com.ntg.engine.service.LoginService;
import com.ntg.engine.util.Utils;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class CalculateSLARemainingTime implements Job {

    @Autowired
    private LoginService loginService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SqlHelperDao sqlHelperDao;

    @Value("${SLA.calculateSLARemainingTime}")
    private String calculateSLARemainingTime;

    @Value("${loginSettings.url}")
    private String baseURL;


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("=================================calculateSLARemainingTime" + new Date());

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

        List<Map<String, Object>> objectsList = sqlHelperDao.queryForList(scheduleObject.getQuery(), null);

        if (!objectsList.isEmpty()) {
            String companyName = (Utils.isNotEmpty(tenantInfo) && Utils.isNotEmpty(tenantInfo.get("companyName"))) ? tenantInfo.get("companyName").toString() : "NTG";

            List<Long> ticketsRecId = new ArrayList<Long>();
            for (Map<String, Object> object : objectsList) {
                Long objectRecId = Long.valueOf(object.get("recid").toString());
                ticketsRecId.add(objectRecId);
            }
            EngineCalculationDTO engineCalculationDTO = new EngineCalculationDTO();
            engineCalculationDTO.setTicketsRecId(ticketsRecId);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
            String SessionToken = loginService.logIn(companyName);
            if (Utils.isNotEmpty(SessionToken)) {
                headers.set("SessionToken", SessionToken);
                headers.set("User-Agent", "schedulerBackEnd");
                String calculatedSLA = baseURL + calculateSLARemainingTime;
                HttpEntity<EngineCalculationDTO> entity = new HttpEntity<EngineCalculationDTO>(engineCalculationDTO, headers);
                restTemplate.exchange(calculatedSLA, HttpMethod.POST, entity, String.class);
            } else {
                System.out.println("================= Failed to login to get user or group email==============");
            }
        }
    }

}
