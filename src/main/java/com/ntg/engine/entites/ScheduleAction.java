package com.ntg.engine.entites;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY)
@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "adm_schedule_action")
public class ScheduleAction {

	public static final int ScheduleActionSendEmail = 1;
	public static final int ScheduleActionRULE_INVOKER = 2;
	public static final int ScheduleActionSLACalulate = 3;
	public static final int ScheduleActionCalculateSLARemainingTime =4;

	@Id
	@SequenceGenerator(allocationSize = 1, name = "adm_schedule_action_s", sequenceName = "adm_schedule_action_s", initialValue = 1000)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "adm_schedule_action_s")
	@Column(name = "recid", nullable = false)
	private long recId;

	@Column(name = "name")
	private String name;

	@OneToMany(mappedBy = "scheduleAction")
	@JsonBackReference
	private List<Schedule> schedule;

	public long getRecId() {
		return recId;
	}

	public void setRecId(long recId) {
		this.recId = recId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Schedule> getSchedule() {
		return schedule;
	}

	public void setSchedule(List<Schedule> schedule) {
		this.schedule = schedule;
	}

}
