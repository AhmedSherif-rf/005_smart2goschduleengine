/*Created By Mohamed Gabr*/
package com.ntg.engine.entites;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
@com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY)
@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "adm_types_groups")
public class TypesGroups {

	@Id
	@SequenceGenerator(allocationSize = 1, name = "adm_types_groups_s", sequenceName = "adm_types_groups_s", initialValue = 1000)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "adm_types_groups_s")
	@Column(name = "recid", nullable = false)
	
	private long recId;

	
	@ManyToOne()
	@JoinColumn(name = "types_id", referencedColumnName = "recid", foreignKey = @ForeignKey(name = "types_fk"))
	private Types types;

	
	@Column(name = "group_id")
	private Long groupId;

	
	@Column(name = "allow_monitor")
	private boolean monitor;

	
	@Column(name = "allow_create")
	private boolean create;

	
	@Column(name = "allow_Log_View", nullable = true)
	private Boolean allowLogView;

	
	@Column(name = "allow_modify")
	private boolean modify;

	@javax.persistence.Transient
	private boolean selectedFlag;

	public Boolean isAllowLogView() {
		return (allowLogView == null) ? false : allowLogView;
	}

	public void setAllowLogView(Boolean allowLogView) {
		this.allowLogView = allowLogView;
	}

	public long getRecId() {
		return recId;
	}

	public void setRecId(long recId) {
		this.recId = recId;
	}

	public Types getTypes() {
		return types;
	}

	public void setTypes(Types types) {
		this.types = types;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public boolean isMonitor() {
		return monitor;
	}

	public void setMonitor(boolean monitor) {
		this.monitor = monitor;
	}

	public boolean isCreate() {
		return create;
	}

	public void setCreate(boolean create) {
		this.create = create;
	}

	public boolean isModify() {
		return modify;
	}

	public void setModify(boolean modify) {
		this.modify = modify;
	}

	public boolean isSelectedFlag() {
		return selectedFlag;
	}

	public void setSelectedFlag(boolean selectedFlag) {
		this.selectedFlag = selectedFlag;
	}

}
