package com.ntg.engine.dto;

import java.util.Date;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ntg.engine.entites.SlaMilestones;


public class GenericObjectSLAMilestone {

	private Long recid;
	
	private Long genericObjectRecid;
	
	private Long genericObjectSlaRecid;
	
	private Long corLookupRecid;
	
	private Long jobRun = 0L;
	
	@JsonFormat(pattern = "yyyy-MM-dd@HH:mm", timezone = "Africa/Cairo")
	private Date transactionDate;
	
	private Long slaMilestonesId;
	
	@ManyToOne
	@JoinColumn(name = "sla_Milestone_ID")
	private SlaMilestones slaMilestones;

	public Long getRecid() {
		return recid;
	}

	public void setRecid(Long recid) {
		this.recid = recid;
	}

	public Long getGenericObjectRecid() {
		return genericObjectRecid;
	}

	public void setGenericObjectRecid(Long genericObjectRecid) {
		this.genericObjectRecid = genericObjectRecid;
	}

	public Long getGenericObjectSlaRecid() {
		return genericObjectSlaRecid;
	}

	public void setGenericObjectSlaRecid(Long genericObjectSlaRecid) {
		this.genericObjectSlaRecid = genericObjectSlaRecid;
	}

	public Long getCorLookupRecid() {
		return corLookupRecid;
	}

	public void setCorLookupRecid(Long corLookupRecid) {
		this.corLookupRecid = corLookupRecid;
	}

	public Long getJobRun() {
		return jobRun;
	}

	public void setJobRun(Long jobRun) {
		this.jobRun = jobRun;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public Long getSlaMilestonesId() {
		return slaMilestonesId;
	}

	public void setSlaMilestonesId(Long slaMilestonesId) {
		this.slaMilestonesId = slaMilestonesId;
	}

	public SlaMilestones getSlaMilestones() {
		return slaMilestones;
	}

	public void setSlaMilestones(SlaMilestones slaMilestones) {
		this.slaMilestones = slaMilestones;
	}
	
	
}
