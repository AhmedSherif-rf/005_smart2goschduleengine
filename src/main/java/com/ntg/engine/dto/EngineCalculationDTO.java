package com.ntg.engine.dto;

import java.io.Serializable;
import java.util.List;

public class EngineCalculationDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3867003293459406273L;
	
	private Long ticketId;
	private Double percentageCalculated;
	private Long profileId;
	private List<Long> ticketsRecId;
	
	public Long getTicketId() {
		return ticketId;
	}

	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}

	public Double getPercentageCalculated() {
		return percentageCalculated;
	}

	public void setPercentageCalculated(Double percentageCalculated) {
		this.percentageCalculated = percentageCalculated;
	}

	public Long getProfileId() {
		return profileId;
	}

	public void setProfileId(Long profileId) {
		this.profileId = profileId;
	}

	public List<Long> getTicketsRecId() {
		return ticketsRecId;
	}

	public void setTicketsRecId(List<Long> ticketsRecId) {
		this.ticketsRecId = ticketsRecId;
	}
}
