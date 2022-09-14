/**
 * @Aya.Ramadan To calculateSLARemainingTime for engin =>Dev-00000521 : sla millstone bugs
 */
package com.ntg.engine.dto;

import java.util.ArrayList;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ntg.engine.entites.SLAProfile;

public class GenericObjectSLA {

    private Long recId;
    private Long status;
    private Long objectId;
    private Long typesId;
    private SLAProfile sla;
    private Long slaId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Africa/Cairo")
    private Date profileStartDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Africa/Cairo")
    private Date profileEndDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Africa/Cairo")
    private Date closedDate;
    private String closedBy;
    private String closedById;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Africa/Cairo")
    private Date pausedDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Africa/Cairo")
    private Date lastPausedDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Africa/Cairo")
    private Date resumedDate;
    private Long slaObj;
    private Double remainingSlaValue;
    private Long slaThresholdPeriod;
    private String slaThresholdType;
    private Double consumedSlaValue;
    private Boolean considerVacation;
    private ArrayList<Integer> daysList;
    private Boolean isFullDay;
    private Boolean isGroupTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Africa/Cairo")
    private Date timeFrom;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Africa/Cairo")
    private Date timeTo;
    private Long slaProgress;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Africa/Cairo")
    private Date slaDueDate;
    private Double pausedRemainingSlaValue;
    private Long GenericObjectSLAMilestoneId;
    private SlaProgressLookup slaProgressLookup;

    public Long getRecId() {
        return recId;
    }

    public void setRecId(Long recId) {
        this.recId = recId;
    }


    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public Long getTypesId() {
        return typesId;
    }

    public void setTypesId(Long typesId) {
        this.typesId = typesId;
    }

    public SLAProfile getSla() {
        return sla;
    }

    public void setSla(SLAProfile sla) {
        this.sla = sla;
    }

    public Long getSlaId() {
        return slaId;
    }

    public void setSlaId(Long slaId) {
        this.slaId = slaId;
    }

    public Date getProfileStartDate() {
        return profileStartDate;
    }

    public void setProfileStartDate(Date profileStartDate) {
        this.profileStartDate = profileStartDate;
    }

    public Date getProfileEndDate() {
        return profileEndDate;
    }

    public void setProfileEndDate(Date profileEndDate) {
        this.profileEndDate = profileEndDate;
    }

    public Date getClosedDate() {
        return closedDate;
    }

    public void setClosedDate(Date closedDate) {
        this.closedDate = closedDate;
    }

    public String getClosedBy() {
        return closedBy;
    }

    public void setClosedBy(String closedBy) {
        this.closedBy = closedBy;
    }

    public String getClosedById() {
        return closedById;
    }

    public void setClosedById(String closedById) {
        this.closedById = closedById;
    }

    public Date getPausedDate() {
        return pausedDate;
    }

    public void setPausedDate(Date pausedDate) {
        this.pausedDate = pausedDate;
    }

    public Date getLastPausedDate() {
        return lastPausedDate;
    }

    public void setLastPausedDate(Date lastPausedDate) {
        this.lastPausedDate = lastPausedDate;
    }

    public Date getResumedDate() {
        return resumedDate;
    }

    public void setResumedDate(Date resumedDate) {
        this.resumedDate = resumedDate;
    }

    public Long getSlaObj() {
        return slaObj;
    }

    public void setSlaObj(Long slaObj) {
        this.slaObj = slaObj;
    }

    public Double getRemainingSlaValue() {
        return remainingSlaValue;
    }

    public void setRemainingSlaValue(Double remainingSlaValue) {
        this.remainingSlaValue = remainingSlaValue;
    }

    public Double getConsumedSlaValue() {
        return consumedSlaValue;
    }

    public void setConsumedSlaValue(Double consumedSlaValue) {
        this.consumedSlaValue = consumedSlaValue;
    }

    public Boolean getConsiderVacation() {
        return considerVacation;
    }

    public void setConsiderVacation(Boolean considerVacation) {
        this.considerVacation = considerVacation;
    }

    public ArrayList<Integer> getDaysList() {
        return daysList;
    }

    public void setDaysList(ArrayList<Integer> daysList) {
        this.daysList = daysList;
    }

    public Boolean getIsFullDay() {
        return isFullDay;
    }

    public void setIsFullDay(Boolean isFullDay) {
        this.isFullDay = isFullDay;
    }

    public Boolean getIsGroupTime() {
        return isGroupTime;
    }

    public void setIsGroupTime(Boolean isGroupTime) {
        this.isGroupTime = isGroupTime;
    }

    public Date getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(Date timeFrom) {
        this.timeFrom = timeFrom;
    }

    public Date getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(Date timeTo) {
        this.timeTo = timeTo;
    }

    public Long getSlaProgress() {
        return slaProgress;
    }

    public void setSlaProgress(Long slaProgress) {
        this.slaProgress = slaProgress;
    }

    public Date getSlaDueDate() {
        return slaDueDate;
    }

    public void setSlaDueDate(Date slaDueDate) {
        this.slaDueDate = slaDueDate;
    }

    public Double getPausedRemainingSlaValue() {
        return pausedRemainingSlaValue;
    }

    public void setPausedRemainingSlaValue(Double pausedRemainingSlaValue) {
        this.pausedRemainingSlaValue = pausedRemainingSlaValue;
    }

    public Long getGenericObjectSLAMilestoneId() {
        return GenericObjectSLAMilestoneId;
    }

    public void setGenericObjectSLAMilestoneId(Long genericObjectSLAMilestoneId) {
        GenericObjectSLAMilestoneId = genericObjectSLAMilestoneId;
    }

    public SlaProgressLookup getSlaProgressLookup() {
        return slaProgressLookup;
    }

    public void setSlaProgressLookup(SlaProgressLookup slaProgressLookup) {
        this.slaProgressLookup = slaProgressLookup;
    }

    public Long getStatus() {
        return (status == null) ? -1 : status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getSlaThresholdPeriod() {
        return slaThresholdPeriod;
    }

    public void setSlaThresholdPeriod(Long slaThresholdPeriod) {
        this.slaThresholdPeriod = slaThresholdPeriod;
    }

    public String getSlaThresholdType() {
        return slaThresholdType;
    }

    public void setSlaThresholdType(String slaThresholdType) {
        this.slaThresholdType = slaThresholdType;
    }

}
