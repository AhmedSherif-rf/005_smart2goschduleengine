package com.ntg.engine.entites;

import java.io.Serializable;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY)
@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "adm_types_uda")

public class TypesUDa implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static long ASSIGNMENT_INFO_UDA_TYPE = 17;
	public static long DATE_UDA_TYPE = 6;

	@Id
	@SequenceGenerator(allocationSize = 1, name = "adm_types_uda_s", sequenceName = "adm_types_uda_s", initialValue = 1000)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "adm_types_uda_s")
	@Column(name = "recid", nullable = false)
	private Long recId = 0L;

	@Column(name = "uda_Table_Name", nullable = true)
	private String udaTableName;

	@Column(name = "additional_uda_Table_Names", nullable = true)
	private String AdditionalUdaTableNames;

	@Column(name = "uda_Form_Option", nullable = true)
	@ColumnDefault("2.0")
	private Long udaFormOption;

	@Column(name = "uda_visible_Condition", nullable = true)
	private String visibleCondition;

	@Column(name = "uda_normal_condition", nullable = true)
	private String normalCondition;

	@Column(name = "uda_read_only_condition", nullable = true, length = 4000)
	private String readOnlyCondition;

	@Column(name = "uda_valied_condition", nullable = true, length = 4000)
	private String valiedCondition;

	@Column(name = "appearAsContent")
	private Boolean appearAsContent;

	@Column(name = "uda_added_Message", nullable = true, length = 4000)
	private String addedMessage;

	@Column(name = "default_value", nullable = true)
	private String defaultValue;

	@Column(name = "uda_is_mandatory_condition", nullable = true)
	private String isMandatoryCondition;

	@Column(name = "uda_Caption", nullable = true)
	private String udaCaption;

	@Column(name = "uda_name", nullable = true)
	private String udaName;

	@Column(name = "uda_type", nullable = false)
	private Long udaType = 0L;

	@Column(name = "uda_panel_caption")
	private String udaPanelCaption;

	@Column(name = "uda_panel_id")
	private Long udaPanelID;

	@Column(name = "level_repository_id", nullable = true)
	private Long multipleLevelRepository;

	@Column(name = "object_id")
	private Long object_id;
	
	@Column(name = "form_app_id")
	private Long formAppId;

	@Column(name = "form_type_id")
	private Long formTypeId;

	@Column(name = "form_uda_id")
	private Long formUdaId;

	@Column(name = "form_uda_selected_fields")
	private String formUdaSelectedFields;

	@Column(name = "form_uda_options")
	private String formUdaOptions;

	@Column(name = "uda_order", nullable = true)
	private Long udaOrder = 0L;
//@Aya.Ramadan To calculateSLARemainingTime for engin =>Dev-00000521 : sla millstone bugs
	/*
	 * @OrderBy(value = "recId")
	 * 
	 * @OneToMany(mappedBy = "typesuda")
	 * 
	 * @JsonManagedReference private Set<TypesUdasGroups> typesUdasGroups;
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "type_id", referencedColumnName = "recid")
	@JsonBackReference
	private Types types;

	@Column(name = "type_id", insertable = false, updatable = false)
	private Long typeId;

	@Column(name = "ispublic")
	private Boolean ispublic = true;

	@Transient
	private int hasRule;

	@Column(name = "appearinmonitor")
	private Boolean AppearInMonitor;

	@Column(name = "source_operation_id", nullable = true)
	private Long sourceOperationId;

	@Column(name = "source_operation_data_id", nullable = true)
	private Long sourceOperationDataId;

	@Column(name = "bind_data_id", nullable = true)
	private Long bindDataId;

	@Column(name = "related_value_list_id", nullable = true)
	private Long relatedValueListId;

	@Column(name = "value_list_condition_db", nullable = true)
	private String valueListConditionDB;

	@Column(name = "visable_type", nullable = true)
	private Long visableType = 0L;

	@Column(name = "size_ofdiv", nullable = false, columnDefinition = "int default 12")
	private Long sizeofdiv = 0L;

	@Column(name = "repositories_info", nullable = true)
	@Lob
	private String repositoriesInfo;

	@OneToMany(mappedBy = "typesUda", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JsonManagedReference
	private Set<UdaMultiValue> multiValues = new HashSet<UdaMultiValue>();

	@OneToMany(mappedBy = "typesUda", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonManagedReference
	private Set<UdaGrid> udaGrid = new HashSet<UdaGrid>();

	@Column(name = "enable_grid_click")
	private Boolean enableGridClick;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "grid_object_id", referencedColumnName = "recId")
	private Objects gridObject;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "grid_type_id", referencedColumnName = "recId")
	private Types gridType;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "grid_uda_id", referencedColumnName = "recId")
	private TypesUDa gridUda;

	@Column(name = "associated_types_id", nullable = true, length = 4000)
	private String associatedTypesId;

	@ColumnDefault("1")
	@Column(name = "date_types")
	private Long dateTypes = 0L;

	@Column(name = "REPOSITORY_ID", nullable = true)
	private Long repositoryId;

	
	@Column(name = "calulated_uda")
	private String calulatedUda;

	@Column(name = "IS_ASSIGNMENT_INFO")
	private Boolean isAssignmentInfo;

	@Column(name = "allow_Manual")
	private Boolean allowManual;

	@Column(name = "appear_in_dialog")
	private Boolean appearInDialog;

	@Column(name = "copy_template")
	private Boolean copyTemplate;

	@Column(name = "processtemplate_id")
	private Long processTemplate;

	@Column(name = "auto_id_Prefix")
	private String autoIDPreFix;

	@Column(name = "auto_id_num_digits")
	private Long autoIDNumDigits;

	@Column(name = "enable_uda_log")
	private Boolean enableUdaLog;

	@Column(name = "allow_re_assign")
	private Boolean allowReAssign;

	@Column(name = "privilege_is_public")
	private Boolean privilegeIsPublic = true;

	@Column(name = "show_Memper")
	@ColumnDefault("'0'")
	private Boolean showMemper;

	@Column(name = "uda_id")
	private Long udaId;

	@Column(name = "parent_uda_name")
	private String parentUdaName;

	@Transient()
	private List<List<TypesUDa>> SubFormUDAsList;

	@Column(name = "appear_in_search")
	@ColumnDefault("'0'")
	private Boolean appearInSearch;

	@Column(name = "editable_value_list")
	@ColumnDefault("'0'")
	private Boolean editableValueList;

	@Column(name = "accept_html")
	@ColumnDefault("'0'")
	private Boolean acceptHtml;

	@Column(name = "uda_Description", nullable = true, length = 3000)
	private String udaDescription;

	@Column(name = "qr_code")
	@ColumnDefault("'0'")
	private Boolean qrCode;

	@Column(name = "e_signature")
	@ColumnDefault("'0'")
	private Boolean eSignature;

	@Column(name = "location")
	@ColumnDefault("'0'")
	private Boolean location;

	@Column(name = "is_deleted")
	@ColumnDefault("'0'")
	private Boolean isDeleted = false;

	@Column(name = "partOne")
	
	@ColumnDefault("'0'")
	private Boolean partOne = false;

	@Column(name = "partTwo")
	@ColumnDefault("'0'")
	private Boolean partTwo = false;

	@Column(name = "free_space_span", nullable = true)
	private Long freeSpaceSpan;

	@Column(name = "dictionary_id")
	private Long dictionaryId;

	@Column(name = "LableColor", nullable = true, length = 4000)
	private String LableColor;

	@Column(name = "isBold")
	@ColumnDefault("'0'")
	private boolean isBold;

	public Boolean getPartOne() {
		return partOne;
	}

	public void setPartOne(Boolean partOne) {
		this.partOne = partOne;
	}

	public Boolean getPartTwo() {
		return partTwo;
	}

	public void setPartTwo(Boolean partTwo) {
		this.partTwo = partTwo;
	}

	public boolean isBold() {
		return isBold;
	}

	public void setBold(boolean isBold) {
		this.isBold = isBold;
	}

	@Column(name = "show_gant")
	@ColumnDefault("'0'")
	private Boolean showGant;

	@Column(name = "columnName")
	private String columnName;

	@Column(name = "TableGridName")
	private String tableGridName;

	@Column(name = "IsDb")
	@ColumnDefault("'0'")
	private Boolean isDb;

	@Column(name = "value_list_type_id")
	private Long valueListTypeId;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "created_date")
	private Date createdDate;

	@Column(name = "modified_by")
	private String modifiedBy;

	@Column(name = "modified_date")
	private Date modifiedDate;

	@Column(name = "num_of_levels")
	private Long numOfLevels;

	@Column(name = "parent_level_id")
	private Long parentLevelId;

	@Column(name = "level_order")
	private Long levelOrder;

//	@Transient
//	private List<TreeFormLevels> levels;

	public Long getValueListTypeId() {
		return valueListTypeId;
	}

	public void setValueListTypeId(Long valueListTypeId) {
		this.valueListTypeId = valueListTypeId;
	}

	public Boolean getShowGant() {
		return showGant;
	}

	public void setShowGant(Boolean showGant) {
		this.showGant = showGant;
	}

	public Boolean getIsDb() {
		return isDb;
	}

	public void setIsDb(Boolean isDb) {
		this.isDb = isDb;
	}

	public String getTableGridName() {
		return tableGridName;
	}

	public void setTableGridName(String tableGridName) {
		this.tableGridName = tableGridName;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getLableColor() {
		return LableColor;
	}

	public void setLableColor(String lableColor) {
		LableColor = lableColor;
	}

	public Boolean getAppearAsContent() {
		return appearAsContent;
	}

	public void setAppearAsContent(Boolean appearAsContent) {
		this.appearAsContent = appearAsContent;
	}

	public Long getFreeSpaceSpan() {
		return freeSpaceSpan;
	}

	public void setFreeSpaceSpan(Long freeSpaceSpan) {
		this.freeSpaceSpan = freeSpaceSpan;
	}

	public Boolean getAcceptHtml() {
		return acceptHtml;
	}

	public void setAcceptHtml(Boolean acceptHtml) {
		this.acceptHtml = acceptHtml;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Boolean getEditableValueList() {
		return editableValueList;
	}

	public void setEditableValueList(Boolean editableValueList) {
		this.editableValueList = editableValueList;
	}

	public Boolean getAppearInSearch() {
		return appearInSearch;
	}

	public void setAppearInSearch(Boolean appearInSearch) {
		this.appearInSearch = appearInSearch;
	}

	public String getParentUdaName() {
		return parentUdaName;
	}

	public void setParentUdaName(String parentUdaName) {
		this.parentUdaName = parentUdaName;
	}

	public List<List<TypesUDa>> getSubFormUDAsList() {
		return SubFormUDAsList;
	}

	public void setSubFormUDAsList(List<List<TypesUDa>> subFormUDAsList) {
		SubFormUDAsList = subFormUDAsList;
	}

	public TypesUDa() {

	}

	public TypesUDa(Long recId) {
		this.recId = recId;
	}

	public TypesUDa(long recId, String udaCaption) {
		this.recId = recId;
		this.udaCaption = udaCaption;
	}

	public TypesUDa(long recId, String udaCaption, Long sourceOperationId, Long sourceOperationDataId,
			Long bindDataId) {
		this.recId = recId;
		this.udaCaption = udaCaption;
		this.sourceOperationId = sourceOperationId;
		this.sourceOperationDataId = sourceOperationDataId;
		this.bindDataId = bindDataId;
	}

	public TypesUDa(long recId, String udaName, String udaCaption) {
		this.recId = recId;
		this.udaName = udaName;
		this.udaCaption = udaCaption;
	}

	public TypesUDa(long recId, String udaName, String udaCaption, Long udaType) {
		this.recId = recId;
		this.udaName = udaName;
		this.udaCaption = udaCaption;
		this.udaType = udaType;
	}

	public long getUdaPanelID() {
		return (udaPanelID == null) ? -1 : udaPanelID;
	}

	public void setUdaPanelID(Long udaPanelID) {
		this.udaPanelID = (udaPanelID == null) ? -1 : udaPanelID;
	}

	public Long[] getAssociatedTypesId() {
		if (associatedTypesId != null && !associatedTypesId.isEmpty()) {
			String[] idsArrays = associatedTypesId.split(",");
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

	public void setAssociatedTypesId(Long[] associatedTypesId) {
		if (associatedTypesId != null && associatedTypesId.length > 0) {
			Stream<Long> streamLong = Arrays.stream(associatedTypesId);
			String stringVals = streamLong.map(String::valueOf).collect(Collectors.joining(",", ",", ""));
			this.associatedTypesId = stringVals;
		} else {
			this.associatedTypesId = null;
		}
	}

	public Long getDateTypes() {
		if (dateTypes == null)
			dateTypes = 0L;
		return dateTypes;
	}

	public String getUdaCaption() {
		return udaCaption;
	}

	public void setUdaCaption(String udaCaption) {
		this.udaCaption = udaCaption;
	}

	public void setDateTypes(Long dateTypes) {
		this.dateTypes = dateTypes;
	}

	public Long getSizeofdiv() {
		if (sizeofdiv == null)
			sizeofdiv = 0L;
		return sizeofdiv;
	}

	public void setSizeofdiv(Long sizeofdiv) {
		this.sizeofdiv = sizeofdiv;
	}
//@Aya.Ramadan To calculateSLARemainingTime for engin =>Dev-00000521 : sla millstone bugs
	/*
	 * public Set<TypesUdasGroups> getTypesUdasGroups() { return typesUdasGroups; }
	 * 
	 * public void setTypesUdasGroups(Set<TypesUdasGroups> typesUdasGroups) {
	 * this.typesUdasGroups = typesUdasGroups; }
	 */

	public Boolean getIspublic() {
		return ispublic;
	}

	public void setIspublic(Boolean ispublic) {
		this.ispublic = ispublic;
	}

	public Long getUdaOrder() {
		if (udaOrder == null)
			udaOrder = 0L;
		return udaOrder;
	}

	public void setUdaOrder(Long udaOrder) {
		this.udaOrder = udaOrder;
	}

	public Set<UdaGrid> getUdaGrid() {
		return udaGrid;
	}

	public void setUdaGrid(Set<UdaGrid> udaGrid) {
		this.udaGrid = udaGrid;
	}

	public Boolean getShowMemper() {
		return showMemper;
	}

	public void setShowMemper(Boolean showMemper) {
		this.showMemper = showMemper;
	}

	public String getUdaName() {
		return udaName;
	}

	public void setUdaName(String udaName) {
		this.udaName = udaName;
	}

	public long getUdaType() {
		return udaType;
	}

	public void setUdaType(long udaType) {
		this.udaType = udaType;
	}

	public String getUdaPanelCaption() {
		return udaPanelCaption;
	}

	public void setUdaPanelCaption(String udaPanelCaption) {
		this.udaPanelCaption = udaPanelCaption;
	}

	public String getUdaTableName() {
		return udaTableName;
	}

	public void setUdaTableName(String udaTableName) {
		this.udaTableName = udaTableName;
	}

	public Types getTypes() {
		return types;
	}

	public void setTypes(Types types) {
		this.types = types;
	}

	public Long getRecId() {
		return recId;
	}

	public void setRecId(long recId) {
		this.recId = recId;
	}

	public Long getObject_id() {
		if (object_id == null)
			object_id = 0L;
		return object_id;
	}

	public void setObject_id(Long object_id) {
		this.object_id = object_id;
	}

	public Long getFormTypeId() {
		return formTypeId;
	}

	public void setFormTypeId(Long formTypeId) {
		this.formTypeId = formTypeId;
	}

	public Long getFormUdaId() {
		return formUdaId;
	}

	public void setFormUdaId(Long formUdaId) {
		this.formUdaId = formUdaId;
	}

	public byte[] getRepositoriesInfo() {
		return (repositoriesInfo == null) ? null : repositoriesInfo.getBytes();
	}

	public Set<UdaMultiValue> getMultiValues() {
		return multiValues;
	}

	public void setMultiValues(Set<UdaMultiValue> multiValues) {
		this.multiValues = multiValues;
	}

	public void setRepositoriesInfo(byte[] repositoriesInfo) throws UnsupportedEncodingException {
		this.repositoriesInfo = (repositoriesInfo == null) ? null : new String(repositoriesInfo, "UTF-8");
	}

	public Long getVisableType() {
		if (visableType == null)
			visableType = 0L;
		return visableType;
	}

	public void setVisableType(Long visableType) {
		this.visableType = visableType;
	}

	public String getNormalCondition() {
		return normalCondition;
	}

	public void setNormalCondition(String normalCondition) {
		this.normalCondition = normalCondition;
	}

	public String getReadOnlyCondition() {
		return readOnlyCondition;
	}

	public void setReadOnlyCondition(String readOnlyCondition) {
		this.readOnlyCondition = readOnlyCondition;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getIsMandatoryCondition() {
		return isMandatoryCondition;
	}

	public void setIsMandatoryCondition(String isMandatoryCondition) {
		this.isMandatoryCondition = isMandatoryCondition;
	}

	public Long getUdaFormOption() {
		return udaFormOption;
	}

	public void setUdaFormOption(Long udaFormOption) {
		this.udaFormOption = udaFormOption;
	}

	public String getVisibleCondition() {
		return visibleCondition;
	}

	public void setVisibleCondition(String visibleCondition) {
		this.visibleCondition = visibleCondition;
	}

	public Long getSourceOperationId() {
		return sourceOperationId;
	}

	public void setSourceOperationId(Long sourceOperationId) {
		this.sourceOperationId = sourceOperationId;
	}

	public Long getSourceOperationDataId() {
		return sourceOperationDataId;
	}

	public void setSourceOperationDataId(Long sourceOperationDataId) {
		this.sourceOperationDataId = sourceOperationDataId;
	}

	public Long getBindDataId() {
		return bindDataId;
	}

	public void setBindDataId(Long bindDataId) {
		this.bindDataId = bindDataId;
	}

	public String getValueListConditionDB() {
		return valueListConditionDB;
	}

	public void setValueListConditionDB(String valueListConditionDB) {
		this.valueListConditionDB = valueListConditionDB;
	}

	public Long getRelatedValueListId() {
		return relatedValueListId;
	}

	public void setRelatedValueListId(Long relatedValueListId) {
		this.relatedValueListId = relatedValueListId;
	}

	public String getCalulatedUda() {
		return calulatedUda;
	}

	public void setCalulatedUda(String calulatedUda) {
		this.calulatedUda = calulatedUda;
	}

	public Boolean getIsAssignmentInfo() {
		return isAssignmentInfo;
	}

	public void setIsAssignmentInfo(Boolean isAssignmentInfo) {
		this.isAssignmentInfo = isAssignmentInfo;
	}

	public Boolean getAllowManual() {
		return (allowManual == null) ? false : allowManual;
	}

	public void setAllowManual(Boolean allowManual) {
		if (allowManual == null)
			this.allowManual = false;
		else
			this.allowManual = allowManual;
	}

	public Boolean getCopyTemplate() {
		return (copyTemplate == null) ? false : copyTemplate;
	}

	public void setCopyTemplate(Boolean copyTemplate) {
		if (copyTemplate == null)
			this.copyTemplate = false;
		else
			this.copyTemplate = copyTemplate;
	}

	public Long getProcessTemplate() {
		return processTemplate;
	}

	public void setProcessTemplate(Long processTemplate) {
		this.processTemplate = processTemplate;
	}

	public String getAutoIDPreFix() {
		return autoIDPreFix;
	}

	public void setAutoIDPreFix(String autoIDPreFix) {
		this.autoIDPreFix = autoIDPreFix;
	}

	public Long getAutoIDNumDigits() {
		return autoIDNumDigits;
	}

	public void setAutoIDNumDigits(Long autoIDNumDigits) {
		this.autoIDNumDigits = autoIDNumDigits;
	}

	public Boolean getEnableUdaLog() {
		return (enableUdaLog == null) ? false : enableUdaLog;
	}

	public void setEnableUdaLog(Boolean enableUdaLog) {
		this.enableUdaLog = enableUdaLog;
	}

	public Boolean getAppearInMonitor() {
		return (AppearInMonitor == null) ? false : AppearInMonitor;
	}

	public void setAppearInMonitor(Boolean appearInMonitor) {
		AppearInMonitor = appearInMonitor;
	}

	/*
	 * public Boolean getAppearInForm() { return AppearInForm; }
	 */

	/*
	 * public void setAppearInForm(Boolean appearInForm) { AppearInForm =
	 * appearInForm; }
	 */

//	public MultipleLevelRepo getMultipleLevelRepository() {
//		return multipleLevelRepository;
//	}
//
//	public void setMultipleLevelRepository(MultipleLevelRepo multipleLevelRepository) {
//		this.multipleLevelRepository = multipleLevelRepository;
//	}

	public Boolean getAllowReAssign() {
		return (allowReAssign == null) ? false : allowReAssign;
	}

	public void setAllowReAssign(Boolean allowReAssign) {
		this.allowReAssign = allowReAssign;
	}

	public Boolean getPrivilegeIsPublic() {
		return (privilegeIsPublic == null) ? false : privilegeIsPublic;
	}

	public void setPrivilegeIsPublic(Boolean privilegeIsPublic) {
		this.privilegeIsPublic = privilegeIsPublic;
	}

	public Long getUdaId() {
		return (udaId == null) ? -1 : udaId;
	}

	public void setUdaId(Long udaId) {
		this.udaId = udaId;
	}

	public String getValiedCondition() {
		return valiedCondition;
	}

	public void setValiedCondition(String valiedCondition) {
		this.valiedCondition = valiedCondition;
	}

	public String getAddedMessage() {
		return addedMessage;
	}

	public void setAddedMessage(String addedMessage) {
		this.addedMessage = addedMessage;
	}

	public String getUdaDescription() {
		return udaDescription;
	}

	public void setUdaDescription(String udaDescription) {
		this.udaDescription = udaDescription;
	}

	public Boolean getQrCode() {
		return qrCode;
	}

	public void setQrCode(Boolean qrCode) {
		this.qrCode = qrCode;
	}

	public Boolean geteSignature() {
		return eSignature;
	}

	public void seteSignature(Boolean eSignature) {
		this.eSignature = eSignature;
	}

	public Boolean getLocation() {
		return location;
	}

	public void setLocation(Boolean location) {
		this.location = location;
	}

	public int getHasRule() {
		return hasRule;
	}

	public void setHasRule(int hasRule) {
		this.hasRule = hasRule;
	}

	public Boolean getEnableGridClick() {
		return (enableGridClick == null) ? false : enableGridClick;
	}

	public void setEnableGridClick(Boolean enableGridClick) {
		this.enableGridClick = enableGridClick;
	}

	public Objects getGridObject() {
		return gridObject;
	}

	public void setGridObject(Objects gridObject) {
		this.gridObject = gridObject;
	}

	public Types getGridType() {
		return gridType;
	}

	public void setGridType(Types gridType) {
		this.gridType = gridType;
	}

	public TypesUDa getGridUda() {
		return gridUda;
	}

	public void setGridUda(TypesUDa gridUda) {
		this.gridUda = gridUda;
	}

	public Long getDictionaryId() {
		return dictionaryId;
	}

	public void setDictionaryId(Long dictionaryId) {
		this.dictionaryId = dictionaryId;
	}

	public Long getFormAppId() {
		return formAppId;
	}

	public void setFormAppId(Long formAppId) {
		this.formAppId = formAppId;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}
//
//	public ValueListRepositoryDTO getRepositoryUDA() {
//		return repositoryUDA;
//	}
//
//	public void setRepositoryUDA(ValueListRepositoryDTO repositoryUDA) {
//		this.repositoryUDA = repositoryUDA;
//	}

	public Long getRepositoryId() {
		return repositoryId;
	}

	public void setRepositoryId(Long repositoryId) {
		this.repositoryId = repositoryId;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getFormUdaSelectedFields() {
		return formUdaSelectedFields;
	}

	public void setFormUdaSelectedFields(String formUdaSelectedFields) {
		this.formUdaSelectedFields = formUdaSelectedFields;
	}
//
//	public List<FormListMappingDTO> getFormListMappingDTOList() {
//		return formListMappingDTOList;
//	}

//	public void setFormListMappingDTOList(List<FormListMappingDTO> formListMappingDTOList) {
//		this.formListMappingDTOList = formListMappingDTOList;
//	}
//
//	public FormListFilterDTO getFormListFilterDTO() {
//		return formListFilterDTO;
//	}

//	public void setFormListFilterDTO(FormListFilterDTO formListFilterDTO) {
//		this.formListFilterDTO = formListFilterDTO;
//	}

	public String getFormUdaOptions() {
		return formUdaOptions;
	}

	public void setFormUdaOptions(String formUdaOptions) {
		this.formUdaOptions = formUdaOptions;
	}

	public Long getNumOfLevels() {
		return numOfLevels;
	}

	public void setNumOfLevels(Long numOfLevels) {
		this.numOfLevels = numOfLevels;
	}

	public Long getParentLevelId() {
		return parentLevelId;
	}

	public void setParentLevelId(Long parentLevelId) {
		this.parentLevelId = parentLevelId;
	}

	public Long getLevelOrder() {
		return levelOrder;
	}

	public void setLevelOrder(Long levelOrder) {
		this.levelOrder = levelOrder;
	}

	public String getAdditionalUdaTableNames() {
		return AdditionalUdaTableNames;
	}

	public void setAdditionalUdaTableNames(String additionalUdaTableNames) {
		AdditionalUdaTableNames = additionalUdaTableNames;
	}

	public Boolean getAppearInDialog() {
		return (appearInDialog == null) ? false : appearInDialog;
	}

	public void setAppearInDialog(Boolean appearInDialog) {
		this.appearInDialog = appearInDialog;
	}

}
