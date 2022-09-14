package com.ntg.engine.jobs;

import com.ntg.common.NTGMessageOperation;
import com.ntg.engine.dto.LoginSettings;
import com.ntg.engine.entites.Schedule;
import com.ntg.engine.entites.ScheduleAction;
import com.ntg.engine.jobs.service.CommonCachingFunction;
import com.ntg.engine.service.JobService;
import com.ntg.engine.service.ScheduleService;
import com.ntg.engine.util.LoginUser;
import com.ntg.engine.util.Utils;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@DisallowConcurrentExecution
public class EngineMainJob implements Job {
    static boolean IsVersionPosted = false;

    @Autowired
    private JobService jobService;

    @Autowired
    private ScheduleService scheduleService;

    @Value("${postModuleVersion}")
    String postModuleVersionURL;

    @Value("${scheduleSchema}")
    String scheduleSchemaURL;

    @Autowired
    private CommonCachingFunction CachingFunction;

    @Value("${pom.version}")
    public String backendVersion;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LoginSettings loginSettings;

    // Aya Copied from mail engine
    static HashMap<String, JobDataMap> RunninJobs = new HashMap<>();
    static int JobCounts = -1;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        Date d = new Date();
        if(JobCounts == -1) {
            System.out.println(
                    "(Info)===>Main Job First Init");
        }
        if (!IsVersionPosted) {
            try {

                String loginToken = this.CachingFunction.BackEndLogin(loginSettings.getCompanyName());
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
                headers.set("SessionToken", loginToken);
                headers.set("User-Agent", "schedulerBackEnd");

                HttpEntity entity = new HttpEntity<>(void.class, headers);
                String url = this.postModuleVersionURL + "/Smart2Go_Schedule_Engine" + "/" + this.backendVersion;

                ResponseEntity res = restTemplate.exchange(url, HttpMethod.POST, entity, LoginUser.class);
                System.out.println("Post Version --> " + res.getStatusCodeValue());

                IsVersionPosted = true;
            } catch (Exception e) {
                NTGMessageOperation.PrintErrorTrace(e);
            }

        }

        try {
            List<Schedule> schedulerConfigs = findAllSchedulerJobs();

            //reconcile and remove deleted jobs
            removeDeletedJobs(schedulerConfigs);


            Object[] keys = RunninJobs.keySet().toArray(); /// Aya
            for (Object key : keys) {
                RunninJobs.get(key).put("ToBeCheck", Boolean.TRUE);
            }
            if (Utils.isNotEmpty(schedulerConfigs)) {
                if (JobCounts != schedulerConfigs.size()) {
                    JobCounts = schedulerConfigs.size();
                    System.out.println("(Info)==>Jobs Count  : "
                            + schedulerConfigs.size());
                }

                for (Schedule schedulerConfig : schedulerConfigs) {
                    Map<String, Object> tenantInfo = fetchSchemaForEachScedule(schedulerConfig.getRecId());
                    CronExpression trigger = new CronExpression(schedulerConfig.getCroneExp());
                    boolean isTimeToStart = trigger.isSatisfiedBy(d);
                    if (isTimeToStart) {
                        if (jobService.isJobWithNamePresent(schedulerConfig.getJobName())) {
                            System.out.println("(Info)==> Time To Schedule ==>" + schedulerConfig.getJobName()  + " Already Exists");
                         } else {
                            jobService.scheduleOneTimeJob(schedulerConfig.getJobName(),
                                    getJobClassByName(schedulerConfig.getScheduleAction().getRecId()), new Date(),
                                    schedulerConfig, tenantInfo);
                            System.out.println("(Info)==> Time To Schedule ==>" + schedulerConfig.getJobName() );
                        }
                    }
                }
            } else {
                if (JobCounts != 0) {
                    JobCounts = 0;
                    System.out.println("(Info)==>Jobs Count  : "
                            + JobCounts);
                }
            }
        } catch (Exception e) {
            NTGMessageOperation.PrintErrorTrace(e);
        }
    }

    private void removeDeletedJobs(List<Schedule> schedulerConfigs) {

        if (RunninJobs != null && RunninJobs.size() > 0) {
            // check if running jobs exists in list of jobs from DB if not remove it
            if (Utils.isNotEmpty(schedulerConfigs)) {
                for (String jobName : RunninJobs.keySet()) {

                    Schedule matchingJob = schedulerConfigs.stream().filter(j -> j.getJobName().equals(jobName))
                            .findFirst().orElse(null);

                    if (matchingJob == null) {
                        RunninJobs.remove(jobName);
                    }
                }
            }

            // means all jobs remove so clear all running jobs
            else {
                RunninJobs.clear();
            }
        }

    }

    private Map<String, Object> fetchSchemaForEachScedule(Long scheduleId) throws InterruptedException {
        String loginToken = this.CachingFunction.BackEndLogin(loginSettings.getCompanyName());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.set("SessionToken", loginToken);
        headers.set("User-Agent", "schedulerBackEnd");

        HttpEntity entity = new HttpEntity<>(headers);
        ResponseEntity<Map<String, Object>> res = restTemplate.exchange(scheduleSchemaURL + "/" + scheduleId, HttpMethod.GET, entity, new ParameterizedTypeReference<Map<String, Object>>() {
        });
        return (Utils.isNotEmpty(res) && Utils.isNotEmpty(res.getBody())) ? res.getBody() : null;
    }

    //remove Transactionl as it's added in the service layer
    synchronized private List<Schedule> findAllSchedulerJobs() {
        List<Schedule> schedulerConfigs = scheduleService.findByJobRunning(false);
        return schedulerConfigs;
    }

    private Class getJobClassByName(Long actionRecid) {
        switch (actionRecid.intValue()) {
            case ScheduleAction.ScheduleActionSendEmail:
                return SendMail.class;
            case ScheduleAction.ScheduleActionRULE_INVOKER:
                return RuleInvoker.class;
            case ScheduleAction.ScheduleActionSLACalulate:
                return CalculateSLA.class;
            case ScheduleAction.ScheduleActionCalculateSLARemainingTime:
                return CalculateSLARemainingTime.class;
            default:
                System.out.println("Invalid Action");
                return null;
        }
    }

    private void compareData(Schedule schedule) {
        JobDataMap Olddata = RunninJobs.get(schedule.getJobName());
        if (Olddata != null) {
            boolean DataIsIdentical = true;
            Object OldValue = Olddata.get("scheduleObject");
            Object NewValue = schedule;

            if ((OldValue != null && NewValue == null) || (OldValue == null && NewValue != null)
                    || (!OldValue.toString().equals(NewValue.toString()))) {
                DataIsIdentical = false;
            }
            if (DataIsIdentical == false) {
                System.out.println("Request Breack Job --> " + schedule.getJobName());
                Olddata.put("Break", Boolean.TRUE);
            }
        }
        // Delete Jobs which not in DB any More
        Object[] keys = RunninJobs.keySet().toArray();
        for (Object key : keys) {
            Object v = RunninJobs.get(key).get("ToBeCheck");
            if (v != null && v == Boolean.TRUE) {
                RunninJobs.get(key).put("Break", Boolean.TRUE);
            }
        }
    }
}
