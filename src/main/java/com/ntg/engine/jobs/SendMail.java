package com.ntg.engine.jobs;

import java.util.Date;
import java.util.Map;

import com.ntg.common.NTGMessageOperation;
import com.ntg.engine.service.JobService;
import com.ntg.engine.util.Utils;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.ntg.engine.entites.Schedule;
import com.ntg.engine.service.SendMailService;

public class SendMail implements Job {

    @Autowired
    private SendMailService sendMailService;

    @Autowired
    private JobService jobService;


    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        String jobName = jobExecutionContext.getJobDetail().getKey().toString().split("\\.")[1];

        try {
            JobDataMap dataMap = jobExecutionContext.getMergedJobDataMap();

            String emailTempleteRecId = dataMap.getString("EmailTemplete");
            Schedule scheduleObject = null;
            Map<String, Object> tenantInfo = null;
            scheduleObject = (Schedule) dataMap.get("scheduleObject");
            tenantInfo = (Utils.isNotEmpty(dataMap.get("tenantInfo"))) ? (Map<String, Object>) dataMap.get("tenantInfo") : null;
            EngineMainJob.RunninJobs.put(scheduleObject.getJobName(), dataMap);


            System.out.println("(Info)==> Send Mail Job Start ==>" + new Date() + " ==>>" + scheduleObject.getName() + "@"+jobName);


            /**
             * @changedBy:Aya.Ramadan
             * to remove toEmail and CCEmail from email template and add it to   schedule Object
             * */
            sendMailService.replaseValuesEmailBody(emailTempleteRecId, scheduleObject, null, tenantInfo);
        } catch (Exception ex) {
            System.out.println("(Info)==> Send Mail Job Issue @" + jobName);
            NTGMessageOperation.PrintErrorTrace(ex);
        }

        jobService.deleteJob(jobName);


    }

}
