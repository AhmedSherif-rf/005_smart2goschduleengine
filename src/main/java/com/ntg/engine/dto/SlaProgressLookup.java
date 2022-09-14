/**
*@Aya.Ramadan To calculateSLARemainingTime for engin =>Dev-00000521 : sla millstone bugs
*/
package com.ntg.engine.dto;

public class SlaProgressLookup {
		
	private long recId;
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
