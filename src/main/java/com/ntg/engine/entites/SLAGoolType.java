package com.ntg.engine.entites;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.ntg.common.ConditionElement;


@Entity
@com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY)
@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "adm_sla_gool_type")
public class SLAGoolType {
	@Id
	@SequenceGenerator(allocationSize = 1, name = "adm_sla_gool_type_s", sequenceName = "adm_sla_gool_type_s", initialValue = 1000)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "adm_sla_gool_type_s")
	@Column(name = "recid", nullable = false)
	
	private long recId;

	
	@Column(name = "name", nullable = false, unique = true)
	@ConditionElement(column = "name", tableName = "adm_sla_gool_type")
	private String name;

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

}
