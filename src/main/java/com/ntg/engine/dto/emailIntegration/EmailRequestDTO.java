package com.ntg.engine.dto.emailIntegration;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class EmailRequestDTO  implements Serializable {




	/**
	 * 
	 */
	private static final long serialVersionUID = -1515039207172532972L;

	private List<String> to;

	private String from;

	private List<String> cc;

	private List<String> bcc;

	private String body;

	private String subject;

	private String templateName;

	private boolean tempFlag;

	private String assignee;

	private String group;

	private Map<String, String> udalst;

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public List<String> getTo() {
		return to;
	}

	public void setTo(List<String> to) {
		this.to = to;
	}

	public List<String> getCc() {
		return cc;
	}

	public void setCc(List<String> cc) {
		this.cc = cc;
	}

	public List<String> getBcc() {
		return bcc;
	}

	public void setBcc(List<String> bcc) {
		this.bcc = bcc;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public boolean getTempFlag() {
		return tempFlag;
	}

	public void setTempFlag(boolean tempFlag) {
		this.tempFlag = tempFlag;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public Map<String, String> getUdalst() {
		return udalst;
	}

	public void setUdalst(Map<String, String> udalst) {
		this.udalst = udalst;
	}
}