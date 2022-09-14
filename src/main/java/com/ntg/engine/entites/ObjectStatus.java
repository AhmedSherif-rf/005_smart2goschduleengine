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

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ntg.common.ConditionElement;

@Entity
@com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY)
@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "adm_status")
public class ObjectStatus {

	@Id
	@SequenceGenerator(allocationSize = 1, name = "adm_status_s", sequenceName = "adm_status_s", initialValue = 1000)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "adm_status_s")
	@Column(name = "recid", nullable = false)
	private long recId;

	@Column(name = "name", nullable = false)
	@ConditionElement(column = "name", tableName = "adm_status")
	private String name;

	@Column(name = "is_Common")
	private boolean isCommon;

	@Column(name = "is_Default")
	private boolean isDefault;

	@Column(name = "is_Type")
	private boolean isType;

	@ManyToOne()
	@JoinColumn(name = "objectid", referencedColumnName = "recid")
	private Objects objects;

	@JsonIgnore
	@ManyToOne()
	@JoinColumn(name = "typeid", referencedColumnName = "recid")
	private Types types;

	@Column(name = "weight")
	private long weight;

	@Column(name = "color")
	private String color;

	@Column(name = "sla_percentage")
	private Long sLA_Percentage;

	@Column(name = "is_Deleted")
	@ColumnDefault(value="'0'")
	private Boolean isDeleted = false;

	public ObjectStatus() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ObjectStatus(long recId, String name) {
		super();
		this.recId =recId;
		this.name = name;
	}

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

	public boolean isCommon() {
		return isCommon;
	}

	public void setCommon(boolean isCommon) {
		this.isCommon = isCommon;
	}

	public boolean isDefault() {
		return isDefault;
	}

	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	public boolean isType() {
		return isType;
	}

	public void setType(boolean isType) {
		this.isType = isType;
	}

	public Objects getObjects() {
		return objects;
	}

	public void setObjects(Objects objects) {
		this.objects = objects;
	}

	public Types getTypes() {
		return types;
	}

	public void setTypes(Types types) {
		this.types = types;
	}

	public long getWeight() {
		return weight;
	}

	public void setWeight(long weight) {
		this.weight = weight;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Long getsLA_Percentage() {
		return (sLA_Percentage == null) ? 0 : sLA_Percentage;
	}

	public void setsLA_Percentage(Long sLA_Percentage) {
		this.sLA_Percentage = sLA_Percentage;
	}

	public Boolean isDeleted() {
		
		return isDeleted;
	}

	public void setDeleted(Boolean isDeleted) {
		if(isDeleted==null) {
			isDeleted=false;
		}else
		this.isDeleted = isDeleted;
	}

}
