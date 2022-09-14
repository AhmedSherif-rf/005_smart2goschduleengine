package com.ntg.engine.entites;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ntg.common.ConditionElement;


@Entity
@com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY)
@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "adm_types", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "type_name", "objectid" }, name = "uk_typeName_objectId") })
@XmlAccessorType(XmlAccessType.NONE)
public class Types implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(allocationSize = 1, name = "adm_types_s", sequenceName = "adm_types_s", initialValue = 1000)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "adm_types_s")
	@Column(name = "recid", nullable = false)
	
	@XmlElement(required = false)
	private Long recId;

	
	@Column(name = "type_name", nullable = false)
	@XmlElement(required = false)
	@ConditionElement(column = "name")
	private String typeName;

	
	@ManyToOne()
	@JoinColumn(name = "objectid", nullable = false, referencedColumnName = "recid")
	@XmlElement(required = false)
	private Objects objects;

	@OneToMany(mappedBy = "types", fetch = FetchType.LAZY)
	@OrderBy(value = "recId")
	@JsonManagedReference
	private Set<TypesUDa> typesUda;

	
	@OneToMany(mappedBy = "types")
	@OrderBy(value = "recId")
	@JsonBackReference
	private Set<TypesGroups> typesGroups;

	
	@Column(name = "sla_profile_id", nullable = true)
	private Long sLAProfileId;

	
	@Column(name ="im_show_customer_info", nullable = false, columnDefinition = "numeric(1,0) default 1")
	private int im_show_customer_info;

	
	@Column(name ="im_show_user_info", nullable = false, columnDefinition = "numeric(1,0) default 1")
	private int im_show_user_info;

	
	@Column(name ="im_show_loginuser_info", nullable = false, columnDefinition = "numeric(1,0) default 0")
	private int im_show_loginuser_info;

	
	@Column(name ="im_show_asisgn_info", nullable = false, columnDefinition = "numeric(1,0) default 1")
	private int im_show_asisgn_info;

	
	@Column(name ="im_show_fault_info", nullable = false, columnDefinition = "numeric(1,0) default 1")
	private int im_show_fault_info;

	
	@Column(name ="im_show_rootcuause_info", nullable = false, columnDefinition = "numeric(1,0) default 1")
	private int im_show_rootcuause_info;

	
	@Column(name ="im_show_more_info", nullable = false, columnDefinition = "numeric(1,0) default 1")
	private int im_show_more_info;

	
	@ManyToOne()
	@JoinColumn(name = "statusid", nullable = true, referencedColumnName = "recid")
	@XmlElement(required = false)
	private ObjectStatus status;

	
	@OneToOne
	@JoinColumn(name = "emailConfiguration", referencedColumnName = "recid")
	private EmailConfiguration emailConfiguration;
//	
	
	@Column(name = "isheaderhidden", nullable = true)
	@ColumnDefault("'0'")
	@XmlElement(required = false)
	private Boolean isHeaderHidden = false;	
	
	
	@Column(name = "group_Value", nullable = true,length = 4000)
 
	@XmlElement(required = false)
	private String groupValue;


	public Boolean getShowTaskStatusInMonitor() {
		return (ShowTaskStatusInMonitor==null)?false:ShowTaskStatusInMonitor;
	}

	public void setShowTaskStatusInMonitor(Boolean showTaskStatusInMonitor) {
		this.ShowTaskStatusInMonitor = (showTaskStatusInMonitor==null)?false:showTaskStatusInMonitor;
	}

	
	@Column(name = "Show_Task_Status_In_Monitor", nullable = true)
	@ColumnDefault("'0'")
	@XmlElement(required = false)
	private Boolean ShowTaskStatusInMonitor = false;


	// =================================
	
	@XmlElement(required = false)
	@Transient
	private Long[] groupValueHelper;
	
	public void setGroupValueHelper(Long[] groupValueHelper) {
		if (groupValueHelper != null && groupValueHelper.length > 0) {
			Stream<Long> streamLong = Arrays.stream(groupValueHelper);
			String stringVals = streamLong.map(String::valueOf).collect(Collectors.joining(",", ",", ""));
			this.groupValue = stringVals;
		} else {
			this.groupValue = null;
		}
	}

	// =========================================
	

	
	@Column(name = "Disbale_Status", nullable = false, columnDefinition = "numeric(1,0)")
	@ColumnDefault("'1'")
	private int DisbaleStatus;

	
	@ManyToOne()
	@JoinColumn(name = "parent_Type_Id", nullable = true, referencedColumnName = "recid", foreignKey = @ForeignKey(name = "PARENT_TYPES_FK"))
	@XmlElement(required = false)
	private Types parentType;
	
	
	@Column(name = "inherit_UDAs")
	private Boolean inheritUDAs;

	
	@Column(name = "enable_log")
	private Boolean enableLog;

	
	@Column(name = "is_deleted")
	@ColumnDefault("'0'")
	@XmlElement(required = false)
	private Boolean isDeleted = false;

	
	@Column(name = "link_to_task_view")
	private Boolean linkToTaskView = false;

	//added by Asmaa for data privacy
	
	@Column(name = "is_restricted_security")
	@ColumnDefault("'0'")
	@XmlElement(required = false)
	private boolean restrictedSecurity = false;
	
	
	@Column(name = "add_exception")
	@ColumnDefault("'0'")
	@XmlElement(required = false)
	private boolean addException = false;
	
	
	@Column(name = "selected_restricted_option")
	@XmlElement(required = false)
	private Long selectedRestrictedOption = 0L;
		
	@Column(name = "shortcut_On_Dashboard")
	private Boolean shortCutOnDashboard = false;
	
	@Column(name = "shortcut_Caption")
	private String shortCutCaption;
	
	
	@Column(name = "inherit_rule")
	private Boolean InheritRule = false;
	
	
	@Column(name = "inherit_plan_template")
	private Boolean InheritPlanTemplate = false;

	
	@Transient
	private Boolean CopyAllowLog ;
	
	
	@Transient
	private Boolean CopyMonitor ;
	
	@Transient
	private Boolean CopyCreate ;
	
	@Transient
	private Boolean CopyModify ;
	
//	@OneToMany(mappedBy = "type",
//	fetch = FetchType.LAZY, targetEntity = TypesDashBoard.class)
//	private Set<TypesDashBoard> typesDashboard;
	
	
	@Column(name = "agail_List_Value")
	@XmlElement(required = false)
	private Long agailListValue = 0L;
	
	
	@Column(name = "agail_Udas_Appear")
	@XmlElement(required = false)
	private String agailUdasAppear;
	
	
	@Column(name = "AllowAgileView")
	@ColumnDefault("'0'")
	@XmlElement(required = false)
	private boolean AllowAgileView = false;
	
	@Column(name = "AllawMapView")
	@ColumnDefault("'0'")
	@XmlElement(required = false)
	private boolean AllawMapView = false;
	
	
	@Column(name = "AllowCalenderView")
	@ColumnDefault("'0'")
	@XmlElement(required = false)
	private boolean AllowCalenderView = false;
	
	public boolean isAllowCalenderView() {
		return AllowCalenderView;
	}

	public void setAllowCalenderView(boolean allowCalenderView) {
		AllowCalenderView = allowCalenderView;
	}

	public Long getSelectedDateUDA() {
		return selectedDateUDA;
	}

	public void setSelectedDateUDA(Long selectedDateUDA) {
		this.selectedDateUDA = selectedDateUDA;
	}

	
	@Column(name = "selectedDateUDA")
	@XmlElement(required = false)
	private Long selectedDateUDA = 0L;
	
	public boolean isAllawMapView() {
		return AllawMapView;
	}

	public void setAllawMapView(boolean allawMapView) {
		AllawMapView = allawMapView;
	}

	public boolean isAllowAgileView() {
		return AllowAgileView;
	}

	public void setAllowAgileView(boolean allowAgileView) {
		AllowAgileView = allowAgileView;
	}

	public Long getAgailListValue() {
		return agailListValue;
	}

	public void setAgailListValue(Long agailListValue) {
		this.agailListValue = agailListValue;
	}

	public String getAgailUdasAppear() {
		return agailUdasAppear;
	}

	public void setAgailUdasAppear(String agailUdasAppear) {
		this.agailUdasAppear = agailUdasAppear;
	}

	
	 

	public boolean isAddException() {
		return addException;
	}

	public void setAddException(boolean addException) {
		this.addException = addException;
	}

	public boolean isRestrictedSecurity() {
		return restrictedSecurity;
	}

	public void setRestrictedSecurity(boolean restrictedSecurity) {
		this.restrictedSecurity = restrictedSecurity;
	}

	public long getSelectedRestrictedOption() {
		return (selectedRestrictedOption==null)?-1:selectedRestrictedOption;
	}

	public void setSelectedRestrictedOption(Long selectedRestrictedOption) {
		this.selectedRestrictedOption = selectedRestrictedOption;
	}

	public Boolean getCopyMonitor() {
		return CopyMonitor;
	}

	public void setCopyMonitor(Boolean copyMonitor) {
		CopyMonitor = copyMonitor;
	}

	public Boolean getCopyCreate() {
		return CopyCreate;
	}

	public void setCopyCreate(Boolean copyCreate) {
		CopyCreate = copyCreate;
	}

	public Boolean getCopyModify() {
		return CopyModify;
	}

	public void setCopyModify(Boolean copyModify) {
		CopyModify = copyModify;
	}

	public Boolean getCopyAllowLog() {
		return CopyAllowLog;
	}

	public void setCopyAllowLog(Boolean copyAllowLog) {
		CopyAllowLog = copyAllowLog;
	}

	public Types() {
	}

	public Types(Long id) {
		this.setRecId(id);
	}

	public Types(Long recId, Boolean nativ) {
		super();
		this.recId = recId;
	}

	public int getIm_show_customer_info() {
		return im_show_customer_info;
	}

	public void setIm_show_customer_info(int im_show_customer_info) {
		this.im_show_customer_info = im_show_customer_info;
	}

	public int getIm_show_asisgn_info() {
		return im_show_asisgn_info;
	}

	public void setIm_show_asisgn_info(int im_show_asisgn_info) {
		this.im_show_asisgn_info = im_show_asisgn_info;
	}

	public int getIm_show_fault_info() {
		return im_show_fault_info;
	}

	public void setIm_show_fault_info(int im_show_fault_info) {
		this.im_show_fault_info = im_show_fault_info;
	}

	public int getIm_show_rootcuause_info() {
		return im_show_rootcuause_info;
	}

	public void setIm_show_rootcuause_info(int im_show_rootcuause_info) {
		this.im_show_rootcuause_info = im_show_rootcuause_info;
	}

	public Long getRecId() {
		return recId;
	}

	public void setRecId(Long recId) {
		this.recId = recId;
	}

	public Objects getObjects() {
		return objects;
	}

	public void setObjects(Objects objects) {
		this.objects = objects;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Set<TypesUDa> getTypesUda() {
		return typesUda;
	}

	public void setTypesUda(Set<TypesUDa> typesUda) {
		this.typesUda = typesUda;
	}

	public Set<TypesGroups> getTypesGroups() {
		return typesGroups;
	}

	public void setTypesGroups(Set<TypesGroups> typesGroups) {
		this.typesGroups = typesGroups;
	}

	public Long getsLAProfileId() {
		return sLAProfileId;
	}

	public void setsLAProfileId(Long sLAProfileId) {
		this.sLAProfileId = sLAProfileId;
	}

	public boolean isHeaderHidden() {
		return isHeaderHidden;
	}

	public void setHeaderHidden(boolean isHeaderHidden) {
		this.isHeaderHidden = isHeaderHidden;
	}

	public ObjectStatus getStatus() {
		return status;
	}

	public void setStatus(ObjectStatus status) {
		this.status = status;
	}

	public Boolean getIsHeaderHidden() {
		return isHeaderHidden;
	}

	public void setIsHeaderHidden(Boolean isHeaderHidden) {
		this.isHeaderHidden = isHeaderHidden;
	}

	public int getDisbaleStatus() {
		return DisbaleStatus;
	}

	public void setDisbaleStatus(int disbaleStatus) {
		DisbaleStatus = disbaleStatus;
	}

	public int getIm_show_user_info() {
		return im_show_user_info;
	}

	public void setIm_show_user_info(int im_show_user_info) {
		this.im_show_user_info = im_show_user_info;
	}

	public int getIm_show_loginuser_info() {
		return im_show_loginuser_info;
	}

	public void setIm_show_loginuser_info(int im_show_loginuser_info) {
		this.im_show_loginuser_info = im_show_loginuser_info;
	}

	public Types getParentType() {
		return parentType;
	}

	public void setParentType(Types parentType) {
		this.parentType = parentType;
	}

	public boolean isEnableLog() {
		return (enableLog == null) ? false : enableLog;
	}

	public void setEnableLog(boolean enableLog) {
		this.enableLog = enableLog;
	}

	public Boolean getIsDeleted() {
		return (isDeleted == null) ? false : isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Boolean getLinkToTaskView() {
		return linkToTaskView;
	}

	public void setLinkToTaskView(Boolean linkToTaskView) {
		this.linkToTaskView = linkToTaskView;
	}

	

	public Long[] getGroupValue() {
		if (groupValue != null && !groupValue.isEmpty()) {
			String[] idsArrays = groupValue.split(",");
			List<Long> listOfID = new ArrayList<Long>();
			for (String id : idsArrays) {
				if (id.equals(""))
					continue;
				else
					listOfID.add(Long.parseLong(id));
			}
			Long[] array = listOfID.toArray(new Long[listOfID.size()]);

			return array;
		}
		return null;
	}

	public void setGroupValue(Long[] groupValueHelper) {
		if (groupValueHelper != null && groupValueHelper.length > 0) {
			Stream<Long> streamLong = Arrays.stream(groupValueHelper);
			String stringVals = streamLong.map(String::valueOf).collect(Collectors.joining(",", ",", ""));
			this.groupValue = stringVals;
		} else {
			this.groupValue = null;
		}
	}


	public Boolean getInheritUDAs() {
		return inheritUDAs==null?false:inheritUDAs;
	}

	public void setInheritUDAs(Boolean inheritUDAs) {
		this.inheritUDAs = inheritUDAs;
	}

	public EmailConfiguration getEmailConfiguration() {
		return emailConfiguration;
	}

	public void setEmailConfiguration(EmailConfiguration emailConfiguration) {
		this.emailConfiguration = emailConfiguration;
	}


}
