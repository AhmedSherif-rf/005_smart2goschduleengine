package com.ntg.engine.config;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;

import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import com.ntg.common.NTGMessageOperation;
import com.ntg.engine.jobs.EngineMainJob;
import com.ntg.engine.service.JobsListener;
import com.ntg.engine.service.TriggerListner;

@Configuration
public class QuartzSchedulerConfig {

	/*
	 * @Autowired DataSource dataSource;
	 */

	@Autowired
	private ApplicationContext context;

	@Autowired
	private TriggerListner triggerListner;

	@Autowired
	private JobsListener jobsListener;
	
	
	@Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }

	@Bean
	public SchedulerFactoryBean schedulerFactoryBean(PlatformTransactionManager transactionManager) throws IOException {

		SchedulerFactoryBean factory = new SchedulerFactoryBean();
		factory.setOverwriteExistingJobs(true);
		// factory.setDataSource(dataSource);
		factory.setQuartzProperties(quartzProperties());
		factory.setGlobalTriggerListeners(triggerListner);
		factory.setGlobalJobListeners(jobsListener);
		factory.setTriggers(scheduleCronJob());
		AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
		jobFactory.setApplicationContext(context);
		factory.setTransactionManager(transactionManager);
		factory.setJobFactory(jobFactory);

		return factory;
	}

	@Bean
	public Properties quartzProperties() throws IOException {
		PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
		propertiesFactoryBean.setLocation(new ClassPathResource("/quartzSE.properties"));
		propertiesFactoryBean.afterPropertiesSet();
		return propertiesFactoryBean.getObject();
	}

	public Trigger scheduleCronJob() {

		String jobKey = "JobEngineMain";
		String groupKey = "SampleGroup";
		String triggerKey = "triggeEngineMain";
		JobDetail jobDetail = createJob(EngineMainJob.class, true, context, jobKey, groupKey);
		System.out.println("creating trigger for key :" + jobKey + " at date :" + new Date());
		PersistableCronTriggerFactoryBean factoryBean = new PersistableCronTriggerFactoryBean();
		factoryBean.setName(triggerKey);
		factoryBean.setStartTime(new Date());
		factoryBean.setCronExpression("0/10 * * * * ?");
		factoryBean.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
		factoryBean.setJobDetail(jobDetail);
		try {
			factoryBean.afterPropertiesSet();
		} catch (ParseException e) {
			NTGMessageOperation.PrintErrorTrace(e);
		}
		return factoryBean.getObject();

	}

	public JobDetail createJob(Class jobClass, boolean isDurable, ApplicationContext context, String jobName, String jobGroup) {
		JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
		factoryBean.setJobClass(jobClass);
		factoryBean.setDurability(isDurable);
		factoryBean.setApplicationContext(context);
		factoryBean.setName(jobName);
		factoryBean.setGroup(jobGroup);
		// set job data map
		JobDataMap jobDataMap = new JobDataMap();
		jobDataMap.put("DS", "noNeed");
		factoryBean.setJobDataMap(jobDataMap);
		factoryBean.afterPropertiesSet();

		return factoryBean.getObject();
	}
}
