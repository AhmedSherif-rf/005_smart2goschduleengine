package com.ntg.engine.dto;

import java.io.Serializable;
import java.util.Date;

public class ScheduleJobHistoryDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5498655512804057608L;

	private Long recId;

	private Long objectId;

	private Long typeId;

	private Long scheduleId;

	private Date transactionData;

	private String historyObject;

	private String tableName;

	private Long milestoneId;

	public Long getObjectId() {
		return objectId;
	}

	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public Long getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(Long scheduleId) {
		this.scheduleId = scheduleId;
	}

	public Date getTransactionData() {
		return transactionData;
	}

	public void setTransactionData(Date transactionData) {
		this.transactionData = transactionData;
	}

	public String getHistoryObject() {
		return historyObject;
	}

	public void setHistoryObject(String historyObject) {
		this.historyObject = historyObject;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Long getRecId() {
		return recId;
	}

	public void setRecId(Long recId) {
		this.recId = recId;
	}

	public Long getMilestoneId() {
		return milestoneId;
	}

	public void setMilestoneId(Long milestoneId) {
		this.milestoneId = milestoneId;
	}

}
