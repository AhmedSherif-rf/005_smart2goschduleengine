package com.ntg.engine.entites;
/**
*@ModifaiedBy:Aya.ramadan =>Dev-00000521 : sla millstone bugs
*/

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY)
@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "adm_email_template")
public class EmailTemplate {

	@Id
	@SequenceGenerator(allocationSize = 1, name = "adm_email_template_s", sequenceName = "adm_email_template_s", initialValue = 1000)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "adm_email_template_s")
	@Column(name = "recid", nullable = false)
	 
	private long recId;

	 
	@Column(name = "name")
	private String name;

	 
	@Column(name = "description", nullable = false)
	private String description;

	 
	@Column(name = "template_Body", nullable = true)
	@Lob
	private String templateBody;

	 
	@ManyToOne
	@JoinColumn(name = "type_id", nullable = false)
	private Types type;

	 
	@Column(name = "Email_Subject")
	private String emailSubject;

//	@OneToMany(mappedBy = "emailTemplate")
//	@JsonBackReference
//	private List<Schedule> schedule;
//
//	 
	@Transient
	private Long typeId;

	// ====added by Abdulrahman Helal for attachement
	 
	@Column(name = "send_content_as_attachment")
	private Boolean sendContentAsAttachment;

	 
	@Column(name = "note_for_attachment_conrent")
	private String noteForAttachmentContent;
	// ============================

//	public List<Schedule> getSchedule() {
//		return schedule;
//	}
//
//	public void setSchedule(List<Schedule> schedule) {
//		this.schedule = schedule;
//	}

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTemplateBody() {
		return templateBody;
	}

	public void setTemplateBody(String templateBody) {
		this.templateBody = templateBody;
	}

	public Types getType() {
		return type;
	}

	public void setType(Types type) {
		this.type = type;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public String getEmailSubject() {
		return emailSubject;
	}

	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}

	public Boolean getSendContentAsAttachment() {
		return sendContentAsAttachment;
	}

	public void setSendContentAsAttachment(Boolean sendContentAsAttachment) {
		this.sendContentAsAttachment = sendContentAsAttachment;
	}

	public String getNoteForAttachmentContent() {
		return noteForAttachmentContent;
	}

	public void setNoteForAttachmentContent(String noteForAttachmentContent) {
		this.noteForAttachmentContent = noteForAttachmentContent;
	}

}
