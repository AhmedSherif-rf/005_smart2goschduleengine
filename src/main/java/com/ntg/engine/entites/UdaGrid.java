package com.ntg.engine.entites;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY)
@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "adm_types_uda_grid")
public class UdaGrid {

	@Id
	@SequenceGenerator(allocationSize = 1, name = "adm_types_uda_grid_s", sequenceName = "adm_types_uda_grid_s", initialValue = 1000)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "adm_types_uda_grid_s")

	@Column(name = "recid", nullable = false)

	private Long recId = 0L;

	@Column(name = "type", nullable = false)
	private Long type = 0L;

	private Long object_id;

	@Column(name = "form_app_id")
	private Long formAppId;

	@Column(name = "form_type_id")
	private Long formTypeId;

	@Column(name = "form_uda_id")
	private Long formUdaId;

	@Column(name = "form_uda_selected_fields")
	private String formUdaSelectedFields;

	@Column(name = "uda_Form_Option", nullable = true)
	@ColumnDefault("2.0")
	private Long udaFormOption;

	@Column(name = "calculated_Field")
	private String calculatedField;

	@Column(name = "seq", nullable = false)
	private Long seq;

	@Column(name = "width")
	private Long width;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "caption", nullable = true)
	private String caption;

	@ManyToOne()
	@JoinColumn(name = "uda_id", nullable = false, referencedColumnName = "recid")
	@JsonBackReference
	private TypesUDa typesUda;

	@Transient
	private TypesUDa repositoryUDA = new TypesUDa();

	@Column(name = "column_visibility", nullable = true)
	private Long columnVisibility;

	@Column(name = "header_Name")
	private String headerName;

	@Column(name = "field")
	private String field;

	@Column(name = "hidden")
	private Boolean hidden;

	@Column(name = "system_field")
	private Boolean systemField;

	@Column(name = "process")
	private Boolean process;

	@Column(name = "REPOSITORY_ID", nullable = true)
	private Long repositoryId;

	@Column(name = "is_deleted")
	@ColumnDefault("'0'")
	private Boolean isDeleted = false;

	@Column(name = "auto_id_Prefix")
	private String autoIDPreFix;

	@Column(name = "auto_id_num_digits")
	private Long autoIDNumDigits;

	@Column(name = "enable_click")
	private Boolean enableClick;

	@Column(name = "readonly_condition", nullable = true)
	private String readonlyCondition;

	@Column(name = "is_mandatory_condition", nullable = true)
	private String isMandatoryCondition;

	@Column(name = "valuelist_condition", nullable = true)
	private String valueListCondition;

	@Column(name = "hide_condition", nullable = true)
	private String hideCondition;

	@Column(name = "qr_code")
	@ColumnDefault("'0'")
	private Boolean qrCode;

	@Column(name = "e_signature")
	@ColumnDefault("'0'")
	private Boolean eSignature;

	@Column(name = "columnName")
	private String columnName;

	@Column(name = "TableGridName")
	private String tableGridName;

	@Column(name = "value_list_type_id")
	private Long valueListTypeId;

	@Column(name = "dictionary_id")
	private Long dictionaryId;

	@Column(name = "source_operation_id", nullable = true)
	private Long sourceOperationId;

	@Column(name = "source_operation_data_id", nullable = true)
	private Long sourceOperationDataId;

	@Column(name = "bind_data_id", nullable = true)
	private Long bindDataId;

	@Column(name = "form_uda_options")
	private String formUdaOptions;

	public UdaGrid() {
		super();
	}

	public UdaGrid(Long recId, Long type, String name, String headerName) {
		super();
		this.recId = recId;
		this.type = type;
		this.name = name;
		this.headerName = headerName;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public TypesUDa getTypesUda() {
		return typesUda;
	}

	public void setTypesUda(TypesUDa typesUda) {
		this.typesUda = typesUda;
	}

	public Long getRecId() {
		if (recId == null)
			recId = 0L;
		return recId;
	}

	public void setRecId(Long recId) {
		this.recId = recId;
	}

	public long getType() {
		return type;
	}

	public void setType(long type) {
		this.type = type;
	}

	public Long getSeq() {
		if (seq == null)
			seq = 0L;
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}

	public Long getWidth() {
		if (width == null)
			width = 400L;
		return width;
	}

	public void setWidth(Long width) {
		this.width = width;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TypesUDa getRepositoryUDA() {
		return repositoryUDA;
	}

	public void setRepositoryUDA(TypesUDa repositoryUDA) {
		this.repositoryUDA = repositoryUDA;
	}

	public String getHeaderName() {
		return headerName;
	}

	public void setHeaderName(String headerName) {
		this.headerName = headerName;
	}

	public Long getColumnVisibility() {
		return (columnVisibility == null) ? 0 : columnVisibility;
	}

	public void setColumnVisibility(Long columnVisibility) {
		this.columnVisibility = columnVisibility;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public Boolean getHidden() {
		return hidden;
	}

	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}

	public Boolean getSystemField() {
		return systemField;
	}

	public void setSystemField(Boolean systemField) {
		this.systemField = systemField;
	}

	public Boolean getProcess() {
		return process;
	}

	public void setProcess(Boolean process) {
		this.process = process;
	}

	public Long getObject_id() {
		return object_id;
	}

	public void setObject_id(Long object_id) {
		this.object_id = object_id;
	}

	public Long getFormAppId() {
		return formAppId;
	}

	public void setFormAppId(Long formAppId) {
		this.formAppId = formAppId;
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

	public Long getUdaFormOption() {
		return udaFormOption;
	}

	public void setUdaFormOption(Long udaFormOption) {
		this.udaFormOption = udaFormOption;
	}

	public String getCalculatedField() {
		return calculatedField;
	}

	public void setCalculatedField(String calculatedField) {
		this.calculatedField = calculatedField;
	}

	public Long getRepositoryId() {
		return repositoryId;
	}

	public void setRepositoryId(Long repositoryId) {
		this.repositoryId = repositoryId;
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

	public Boolean getEnableClick() {
		return enableClick;
	}

	public void setEnableClick(Boolean enableClick) {
		this.enableClick = enableClick;
	}

	public String getReadonlyCondition() {
		return readonlyCondition;
	}

	public void setReadonlyCondition(String readonlyCondition) {
		this.readonlyCondition = readonlyCondition;
	}

	public String getIsMandatoryCondition() {
		return isMandatoryCondition;
	}

	public void setIsMandatoryCondition(String isMandatoryCondition) {
		this.isMandatoryCondition = isMandatoryCondition;
	}

	public String getValueListCondition() {
		return valueListCondition;
	}

	public void setValueListCondition(String valueListCondition) {
		this.valueListCondition = valueListCondition;
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

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getTableGridName() {
		return tableGridName;
	}

	public void setTableGridName(String tableGridName) {
		this.tableGridName = tableGridName;
	}

	public Long getValueListTypeId() {
		return valueListTypeId;
	}

	public void setValueListTypeId(Long valueListTypeId) {
		this.valueListTypeId = valueListTypeId;
	}

	public Long getDictionaryId() {
		return dictionaryId;
	}

	public void setDictionaryId(Long dictionaryId) {
		this.dictionaryId = dictionaryId;
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

	public String getFormUdaSelectedFields() {
		return formUdaSelectedFields;
	}

	public void setFormUdaSelectedFields(String formUdaSelectedFields) {
		this.formUdaSelectedFields = formUdaSelectedFields;
	}

	public String getFormUdaOptions() {
		return formUdaOptions;
	}

	public void setFormUdaOptions(String formUdaOptions) {
		this.formUdaOptions = formUdaOptions;
	}

	public String getHideCondition() {
		return hideCondition;
	}

	public void setHideCondition(String hideCondition) {
		this.hideCondition = hideCondition;
	}

}
