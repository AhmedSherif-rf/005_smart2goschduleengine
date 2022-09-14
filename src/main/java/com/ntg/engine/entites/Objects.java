package com.ntg.engine.entites;

import java.io.Serializable;
import java.sql.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY)
@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "adm_objects")
@XmlAccessorType(XmlAccessType.NONE)
public class Objects implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(allocationSize = 1, name = "adm_objects_s", sequenceName = "adm_objects_s", initialValue = 1000)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "adm_objects_s")
	@Column(name = "recid", nullable = false)
	 
	@XmlElement(required = false)
	private long recId;

	 
	@Column(name = "object_name", nullable = false)
	@XmlElement(required = false)
	private String objectName;

	 
	@Column(name = "uda_table_name")
	@XmlElement(required = false)
	private String udaTableName;

	 
	@Column(name = "table_name")
	@XmlElement(required = false)
	private String tableName;

	 
	@Column(name = "sla_table_name")
	@XmlElement(required = false)
	private String slaTableName;

	 
	@Column(name = "sla_milestone_table_name")
	@XmlElement(required = false)
	private String slaMilestoneTableName;

	 
	@Column(name = "log_table_name")
	@XmlElement(required = false)
	private String logTableName;

	 
	@Column(name = "is_deleted")
	@XmlElement(required = false)
	private Boolean deleted;

	 
	@Column(name = "show_Dashboard")
	@XmlElement(required = false)
	private Boolean showDashboard;

	 
	@Column(name = "deleted_by")
	@XmlElement(required = false)
	private String deletedBy;

	 
	@Column(name = "deleted_at")
	@XmlElement(required = false)
	private Date deletedAt;
	
	 
	@Column(name = "is_deleted_permenantly")
	@XmlElement(required = false)
	private Boolean deletedPermenantly;

	 
	@Column(name = "deleted_permenantly_by")
	@XmlElement(required = false)
	private String deletedPermenantlyBy;

	 
	@Column(name = "deleted_permenantly_at")
	@XmlElement(required = false)
	private Date deletedPermenantlyAt;

	 
	@Column(name = "hide_Header")
	private Boolean hideHeader = false;
	
	 
	@Column(name = "rtl_flag")
	@XmlElement(required = false)
	private Boolean rtlFlag;
	
	
	 
	@Column(name = "show_types")
	@XmlElement(required = false)
	private Boolean showTypes;

	
	 
	@Column(name = "module_type", nullable = false)
	private Long moduleType = 1L;
	
	 
	@Column(name = "dictionary_id")
	private Long dictionaryId;
//	
	// @JsonIgnore
	// @XmlElement(required=false)
	 
	@OneToMany(mappedBy = "objects", targetEntity = Types.class, fetch = FetchType.LAZY)
	@JsonBackReference
	private Set<Types> type;
	
	 


	public Objects(long objID) {
		this.recId = objID;
	}

	public Objects() {
	}

	public Boolean isDeleted() {
		return (deleted == null) ? false : deleted;
	}

	public void setIsDeleted(boolean isDeleted) {
		this.deleted = isDeleted;
	}

	public String getDeletedBy() {
		return deletedBy;
	}

	public void setDeletedBy(String deletedBy) {
		this.deletedBy = deletedBy;
	}

	public Date getDeletedAt() {
		return deletedAt;
	}

	public void setDeletedAt(Date deletedAt) {
		this.deletedAt = deletedAt;
	}

	public String getUdaTableName() {
		return udaTableName;
	}

	public void setUdaTableName(String udaTableName) {
		this.udaTableName = udaTableName;
	}

	 
	public long getObjectRecId() {
		return recId;
	}

	 
	public void setObjectRecId(long recId) {
		this.recId = recId;
	}

	public Long getRecId() {
		return recId;
	}

	public void setRecId(long recId) {
		this.recId = recId;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public Set<Types> getType() {
		return type;
	}

	public void setType(Set<Types> type) {
		this.type = type;
	}

	public String getTableName() {
		return (tableName==null)?"":tableName.replaceAll("[^\\w]","_");
	}

	public void setTableName(String tableName) {

		this.tableName = (tableName==null)?"":tableName.replaceAll("[^\\w]","_");
	}

	public Boolean getShowDashboard() {
		return (showDashboard == null) ? false : showDashboard;
	}

	public void setShowDashboard(Boolean showDashboard) {
		this.showDashboard = showDashboard;
	}


	public Boolean getRtlFlag() {
		return rtlFlag;
	}

	public void setRtlFlag(Boolean rtlFlag) {
		this.rtlFlag = rtlFlag;
	}
	
	public Boolean getShowTypes() {
		return showTypes;
	}

	public void setShowTypes(Boolean showTypes) {
		this.showTypes = showTypes;
	}

	public Boolean getDeletedPermenantly() {
		return deletedPermenantly;
	}

	public void setDeletedPermenantly(Boolean deletedPermenantly) {
		this.deletedPermenantly = deletedPermenantly;
	}

	public String getDeletedPermenantlyBy() {
		return deletedPermenantlyBy;
	}

	public void setDeletedPermenantlyBy(String deletedPermenantlyBy) {
		this.deletedPermenantlyBy = deletedPermenantlyBy;
	}

	public Date getDeletedPermenantlyAt() {
		return deletedPermenantlyAt;
	}

	public void setDeletedPermenantlyAt(Date deletedPermenantlyAt) {
		this.deletedPermenantlyAt = deletedPermenantlyAt;
	}
	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	
	public Long getModuleType() {
		return moduleType;
	}

	public void setModuleType(Long moduleType) {
		this.moduleType = moduleType;
	}

	public Long getDictionaryId() {
		return dictionaryId;
	}

	public void setDictionaryId(Long dictionaryId) {
		this.dictionaryId = dictionaryId;
	}

	public Boolean getHideHeader() {
		return hideHeader;
	}

	public void setHideHeader(Boolean hideHeader) {
		this.hideHeader = hideHeader;
	}



	public String getSlaTableName() {
		return slaTableName;
	}

	public void setSlaTableName(String slaTableName) {
		this.slaTableName = slaTableName;
	}

	public String getSlaMilestoneTableName() {
		return slaMilestoneTableName;
	}

	public void setSlaMilestoneTableName(String slaMilestoneTableName) {
		this.slaMilestoneTableName = slaMilestoneTableName;
	}

	public String getLogTableName() {
		return logTableName;
	}

	public void setLogTableName(String logTableName) {
		this.logTableName = logTableName;
	}
	//
	
	
	
	

}
