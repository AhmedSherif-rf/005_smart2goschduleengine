package com.ntg.engine.entites;
/**
*@ModifaiedBy:Aya.ramadan =>Dev-00000521 : sla millstone bugs
*/

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY)
@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "adm_sla")
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "@id", scope = SLAProfile.class)
public class SLAProfile implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(allocationSize = 1, name = "adm_sla_s", sequenceName = "adm_sla_s", initialValue = 100)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "adm_sla_s")
	@Column(name = "recid", nullable = false)
	
	private long recId;

	
	@Column(name = "profilename", nullable = false)
	private String profileName;

	
	@JsonFormat(pattern = "HH:mm:ss", timezone = "Africa/Cairo")
	@Column(name = "timefrom", nullable = true)
	private Date timeFrom;

	
	@JsonFormat(pattern = "HH:mm:ss", timezone = "Africa/Cairo")
	@Column(name = "timeto", nullable = true)
	private Date timeTo;

	
	@Column(name = "sla_threshold_type", nullable = false)
	private String sLAThresholdType;

	
	@Column(name = "sla_threshold_period", nullable = false)
	private long sLAThresholdPeriod;

	
	@Column(name = "days_list", nullable = true)
	private ArrayList<Integer> daysList;

	
	@Column(name = "is_full_day", nullable = true)
	private Boolean isFullDay;

	
	@Column(name = "days", nullable = true)
	private String days;

	
	@ColumnDefault("'0'")
	@Column(name = "is_group_time", nullable = true)
	private Boolean isGroupTime;

	
	@ColumnDefault("'0'")
	@Column(name = "consider_vacation", nullable = true)
	private Boolean considerVacation;

	
	@ManyToOne()
	@JoinColumn(name = "sla_gool_type", referencedColumnName = "recid")
	private SLAGoolType slaGoolType;

	// By Amin
	@Column(name = "Status", nullable = true)
	
	private Long status;

	
	// @JsonBackReference
	// @ManyToOne
	@Column(name = "typeid", nullable = true)
	private Long type;

	
	@ManyToOne
	@JoinColumn(name = "object_Id", referencedColumnName = "recid", nullable = true)
	private Objects object;

	
	@OneToMany(cascade = CascadeType.ALL,  fetch = FetchType.EAGER)
	// FETCHTYPE = EAGER MADE BY AALMALKY AND NEED TO BE DISCUSSED .
	@JoinColumn(name = "sla_Profile_Id" ,foreignKey = @ForeignKey(name = "SLA_PROFILEID_RECID") )
	private List<SlaMilestones> slaMilestones = new ArrayList<SlaMilestones>();

	public long getRecId() {
		return recId;
	}

	public void setRecId(long recId) {
		this.recId = recId;
	}

	public String getProfileName() {
		return profileName;
	}

	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}

	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
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

	public String getsLAThresholdType() {
		return sLAThresholdType;
	}

	public void setsLAThresholdType(String sLAThresholdType) {
		this.sLAThresholdType = sLAThresholdType;
	}

	public long getsLAThresholdPeriod() {
		return sLAThresholdPeriod;
	}

	public void setsLAThresholdPeriod(long sLAThresholdPeriod) {
		this.sLAThresholdPeriod = sLAThresholdPeriod;
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

	public Boolean getIsConsiderVacation() {
		return considerVacation;
	}

	public void setConsiderVacation(Boolean considerVacation) {
		if (considerVacation == null || !considerVacation)
			this.considerVacation = false;
		else
			this.considerVacation = true;
	}

	public void setGroupTime(boolean isGroupTime) {
		this.isGroupTime = isGroupTime;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Objects getObject() {
		return object;
	}

	public void setObject(Objects object) {
		this.object = object;
	}

	public List<SlaMilestones> getSlaMilestones() {
		return slaMilestones;
	}

	public void setSlaMilestones(List<SlaMilestones> slaMilestones) {
		this.slaMilestones = slaMilestones;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public SLAGoolType getSlaGoolType() {
		return slaGoolType;
	}

	public void setSlaGoolType(SLAGoolType slaGoolType) {
		this.slaGoolType = slaGoolType;
	}

}
