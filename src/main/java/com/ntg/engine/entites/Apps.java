package com.ntg.engine.entites;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

@Entity
@com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY)
@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "adm_apps")
public class Apps {

	@Id
	@SequenceGenerator(allocationSize = 1, name = "adm_apps_s", sequenceName = "adm_apps_s", initialValue = 1000)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "adm_apps_s")
	@Column(name = "recid", nullable = false)

	private Long recId;

	
	@Column(name = "app_name")
	private String appName;

	
	@Column(name = "icon")
	private String icon;

	
	@Column(name = "is_deleted")
	@ColumnDefault("'0'")
	private Boolean isDeleted = false;

	
	@Column(name = "short_notes")
	private String shortNotes;

	
	@Column(name = "description")
	private String description;

	
	@Column(name = "bg_color")
	private String bgColor;

	
	@Lob
	@Column(name = "app_logo")
	private byte[] appLogo;

	
	@Column(name = "is_border_color")
	private Boolean borderColor;

	
	@Column(name = "is_private", nullable = true)
	@ColumnDefault("'0'")
	private Boolean isPrivate = false;

	
	@Column(name = "User_id", nullable = true)
	private String UserId;

	
	@Column(name = "APP_IDENTIFIER", length = 3 , unique = true , updatable = false)
	private String appIdentifier;

	public Boolean getIsPrivate() {
		return (isPrivate==null)?false:isPrivate;
	}

	public void setIsPrivate(Boolean isPrivate) {
		this.isPrivate = isPrivate;
	}

	public String getUserId() {
		return UserId;
	}

	public void setUserId(String userId) {
		UserId = userId;
	}

	public Long getRecId() {
		return recId;
	}

	public void setRecId(Long recId) {
		this.recId = recId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Boolean getIsDeleted() {
		return (isDeleted == null) ? false : isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}



	public String getShortNotes() {
		return shortNotes;
	}

	public void setShortNotes(String shortNotes) {
		this.shortNotes = shortNotes;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBgColor() {
		return bgColor;
	}

	public void setBgColor(String bgColor) {
		this.bgColor = bgColor;
	}

	public byte[] getAppLogo() {
		return appLogo;
	}

	public void setAppLogo(byte[] appLogo) {
		this.appLogo = appLogo;
	}

	public Boolean getBorderColor() {
		return borderColor;
	}

	public void setBorderColor(Boolean borderColor) {
		this.borderColor = borderColor;
	}

	public String getAppIdentifier() {
		return appIdentifier;
	}

	public void setAppIdentifier(String appIdentifier) {
		this.appIdentifier = appIdentifier;
	}
}
