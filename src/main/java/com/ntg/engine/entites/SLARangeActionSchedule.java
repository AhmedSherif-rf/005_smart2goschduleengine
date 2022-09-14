package com.ntg.engine.entites;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;

@Embeddable
public class SLARangeActionSchedule implements Serializable {

	@Column(name = "from_range")
	public Long fromRange;

	@Column(name = "to_range")
	public Long toRange;

	@Column(name = "applyed_action_id")
	public Long applyedActionId;
	
	@Column(name = "action_query",nullable=true)
	@Lob
	public String applyedActionQuery;
	
	@Column(name = "email_template_id",nullable=true)
	public Long emailTemplateId;
	
	@Column(name="save_after_do_rule",nullable=true)
	private Boolean saveAfterDoRule;
	
	public SLARangeActionSchedule() {
		
	}
	
	public Long getFromRange() {
		return fromRange;
	}

	public void setFromRange(Long fromRange) {
		this.fromRange = fromRange;
	}

	public Long getToRange() {
		return toRange;
	}

	public void setToRange(Long toRange) {
		this.toRange = toRange;
	}

	public Long getApplyedActionId() {
		return applyedActionId;
	}

	public void setApplyedActionId(Long applyedActionId) {
		this.applyedActionId = applyedActionId;
	}

	public Long getEmailTemplateId() {
		return emailTemplateId;
	}

	public String getApplyedActionQuery() {
		return applyedActionQuery;
	}

	public void setApplyedActionQuery(String applyedActionQuery) {
		this.applyedActionQuery = applyedActionQuery;
	}

	public void setEmailTemplateId(Long emailTemplateId) {
		this.emailTemplateId = emailTemplateId;
	}

	public Boolean getSaveAfterDoRule() {
		return saveAfterDoRule;
	}

	public void setSaveAfterDoRule(Boolean saveAfterDoRule) {
		this.saveAfterDoRule = saveAfterDoRule;
	}
}