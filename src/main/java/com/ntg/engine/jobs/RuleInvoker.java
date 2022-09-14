package com.ntg.engine.jobs;

import java.util.Date;
import java.util.Map;

import com.ntg.engine.util.Utils;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.ntg.engine.entites.Schedule;
import com.ntg.engine.service.serviceImpl.RuleInvokerServiceImpl;

public class RuleInvoker implements Job {
    @Autowired
    private RuleInvokerServiceImpl ruleInvokerServiceImpl;

    //@Aya.Ramadan Dev-00000521 : sla millstone bugs
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("=================================Send SMS Job Start " + new Date());
        JobDataMap dataMap = context.getMergedJobDataMap();
        Schedule scheduleObject = (Schedule) dataMap.get("scheduleObject");
        Map<String, Object> tenantInfo = (Utils.isNotEmpty(dataMap.get("tenantInfo"))) ? (Map<String, Object>) dataMap.get("tenantInfo") : null;
        EngineMainJob.RunninJobs.put(scheduleObject.getJobName(), dataMap);

        System.out.println("=============================== preparing to Execute Rule " + scheduleObject.getJobName()
                + " ==========================");

        ruleInvokerServiceImpl.getRestServiceResponse(scheduleObject.getRecId() + "",
                scheduleObject.getTypeId() + "", scheduleObject.getSaveCheck() + "", scheduleObject.getQuery(),
                scheduleObject.getRecurringType(), scheduleObject.getRecurringUnit(),
                scheduleObject.getRecurringValue(), scheduleObject.getTableName(), tenantInfo);
    }

}
