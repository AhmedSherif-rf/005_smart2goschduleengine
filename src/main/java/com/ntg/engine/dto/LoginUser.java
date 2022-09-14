package com.ntg.engine.dto;

import javax.xml.bind.annotation.XmlElement;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class LoginUser {

	public LoginUser() {
	}

	@XmlElement
	public long ServerTimeZoneOffset;

	public String employeeId;

	@XmlElement
	public String DefaultCurrencySymbol;

	@JsonInclude(Include.NON_EMPTY)
	public Boolean isHaveAdminPrev;

	@JsonInclude(Include.NON_EMPTY)
	public Boolean hasAdminGroup;

	transient public String UserSessionToken;

	public String sessionTokenId;

	@JsonInclude(Include.NON_EMPTY)
	public Boolean embeded;

	public void setEmbeded(Boolean embeded) {
		this.embeded = embeded;
	}

	//
	// @XmlElement
	// public String ngCRMSessionToken;

}
