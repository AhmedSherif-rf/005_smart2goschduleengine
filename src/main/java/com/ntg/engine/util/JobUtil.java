package com.ntg.engine.util;

import com.ntg.common.NTGMessageOperation;
import com.ntg.engine.config.PersistableCronTriggerFactoryBean;
import com.ntg.engine.entites.Schedule;
import com.ntg.engine.entites.ScheduleAction;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

public class JobUtil {

    /**
     * Create Quartz Job.
     *
     * @param jobClass  Class whose executeInternal() method needs to be called.
     * @param isDurable Job needs to be persisted even after completion. if true, job will be persisted, not otherwise.
     * @param context   Spring application context.
     * @param jobName   Job name.
     * @param jobGroup  Job group.
     * @return JobDetail object
     */
    public static JobDetail createJob(Class<? extends QuartzJobBean> jobClass, boolean isDurable, ApplicationContext context, String jobName,
                                      String jobGroup, Schedule schedule,  Map<String,Object> tenantInfo) {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(jobClass);
        factoryBean.setDurability(isDurable);
        factoryBean.setApplicationContext(context);
        factoryBean.setName(jobName);
        factoryBean.setGroup(jobGroup);
/**
 *@ModifaiedBy:Aya.ramadan =>Dev-00000521 : sla millstone bugs
 */

        // set job data map
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("scheduleObject", schedule);
        // add tenent schema for external schema
        jobDataMap.put("tenantInfo", tenantInfo);
        if (schedule.getScheduleAction().getRecId() == ScheduleAction.ScheduleActionSendEmail) {
            jobDataMap.put("EmailTemplete", String.valueOf(schedule.getEmailTemplate().getRecId()));
        } else if (schedule.getScheduleAction().getRecId() == ScheduleAction.ScheduleActionRULE_INVOKER) {
            jobDataMap.put("saveCheck", String.valueOf(schedule.getSaveCheck()));
        }

        jobDataMap.put("startedAt" , new Timestamp(new Date().getTime()));
        factoryBean.setJobDataMap(jobDataMap);

        factoryBean.afterPropertiesSet();

        return factoryBean.getObject();
    }

    /**
     * Create cron trigger.
     *
     * @param triggerName        Trigger name.
     * @param startTime          Trigger start time.
     * @param cronExpression     Cron expression.
     * @param misFireInstruction Misfire instruction (what to do in case of misfire happens).
     * @return Trigger
     */
    public static Trigger createCronTrigger(String triggerName, Date startTime, String cronExpression, int misFireInstruction) {
        PersistableCronTriggerFactoryBean factoryBean = new PersistableCronTriggerFactoryBean();
        factoryBean.setName(triggerName);
        factoryBean.setStartTime(startTime);
        factoryBean.setCronExpression(cronExpression);
        factoryBean.setMisfireInstruction(misFireInstruction);
        try {
            factoryBean.afterPropertiesSet();
        } catch (ParseException e) {
            NTGMessageOperation.PrintErrorTrace(e);
        }
        return factoryBean.getObject();
    }

    /**
     * Create a Single trigger.
     *
     * @param triggerName        Trigger name.
     * @param startTime          Trigger start time.
     * @param misFireInstruction Misfire instruction (what to do in case of misfire happens).
     * @return Trigger
     */
    public static Trigger createSingleTrigger(String triggerName, Date startTime, int misFireInstruction) {
        SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
        factoryBean.setName(triggerName);
        factoryBean.setStartTime(startTime);
        factoryBean.setMisfireInstruction(misFireInstruction);
        factoryBean.setRepeatCount(0);
        factoryBean.afterPropertiesSet();
        return factoryBean.getObject();
    }


    public static void Debug(String Info) {

        System.out.println("(Deb)-->:" + Info);

    }

    public static void Info(String Info) {

        System.out.println("(Inf)==>:" + Info);

    }
}
