package com.ntg.engine.entites;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY)
@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "adm_types_uda_multi_value")
public class UdaMultiValue {

	@Id
	@SequenceGenerator(allocationSize = 1, name = "adm_types_uda_multi_val_s", sequenceName = "adm_types_uda_multi_val_s", initialValue = 1000)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "adm_types_uda_multi_val_s")
	@Column(name = "recid", nullable = false)
	private Long recId = 0L;
	
	@Column(name = "name", nullable = false)
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "uda_id", nullable = false, referencedColumnName = "recid", insertable = false, updatable = false)
	@JsonBackReference
	private TypesUDa typesUda;

	@Column(name = "is_default")
	private Boolean isDefault = false;

	@Column(name = "value_list_condition", nullable = true)
	private String valueListCondition;

	@Column(name = "is_deleted")
	private Boolean isDeleted = false;

	@Column(name = "uda_id")
	private Long udaId;

	public Long getRecId() {
		if (recId == null)
			recId = 0L;
		return recId;
	}

	public void setRecId(Long recId) {
		this.recId = recId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TypesUDa getTypesUda() {
		return typesUda;
	}

	public void setTypesUda(TypesUDa typesUda) {
		this.typesUda = typesUda;
	}

	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	public String getValueListCondition() {
		return valueListCondition;
	}

	public void setValueListCondition(String valueListCondition) {
		this.valueListCondition = valueListCondition;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Long getUdaId() {
		return udaId;
	}

	public void setUdaId(Long udaId) {
		this.udaId = udaId;
	}

}
