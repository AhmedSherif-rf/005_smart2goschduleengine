package com.ntg.engine.entites;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

@Entity
@com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY)
@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "adm_types_udas_groups")
public class TypesUdasGroups {
	@Id
	@SequenceGenerator(allocationSize = 1, name = "adm_types_udas_groups_s", sequenceName = "adm_types_udas_groups_s", initialValue = 1000)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "adm_types_udas_groups_s")
	@Column(name = "recid", nullable = false)
	private long recId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "typesuda_id", referencedColumnName = "recid", foreignKey = @ForeignKey(name = "typesuda_types_fk"))
	private TypesUDa typesuda;

	@Column(name = "group_id")
	private Long groupId;

	@Column(name = "read_only")
	private boolean readOnly;

	
	@Column(name = "is_deleted")
	@ColumnDefault("'0'")
	private Boolean isDeleted = false;

	@Column(name = "uda_id")
	
	private Long udaId;

	public TypesUdasGroups() {

	}

	public TypesUdasGroups(Long groupId, Long udaId) {
		this.groupId = groupId;
		this.udaId = udaId;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	@javax.persistence.Transient
	private boolean selectedFlag;

	public long getRecId() {
		return recId;
	}

	public void setRecId(long recId) {
		this.recId = recId;
	}

	public TypesUDa getTypesuda() {
		return typesuda;
	}

	public void setTypesuda(TypesUDa typesuda) {
		this.typesuda = typesuda;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public boolean isSelectedFlag() {
		return selectedFlag;
	}

	public void setSelectedFlag(boolean selectedFlag) {
		this.selectedFlag = selectedFlag;
	}

	public Long getUdaId() {
		return udaId;
	}

	public void setUdaId(Long udaId) {
		this.udaId = udaId;
	}

}
