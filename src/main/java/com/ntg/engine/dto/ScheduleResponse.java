package com.ntg.engine.dto;

import java.util.HashMap;
import java.util.List;

public class ScheduleResponse {
	private List<Long> objectIds;
	private HashMap<Long , String> failObjectIds;

	public List<Long> getObjectIds() {
		return objectIds;
	}

	public void setObjectIds(List<Long> objectIds) {
		this.objectIds = objectIds;
	}

	public HashMap<Long, String> getFailObjectIds() {
		return failObjectIds;
	}

	public void setFailObjectIds(HashMap<Long, String> failObjectIds) {
		this.failObjectIds = failObjectIds;
	}
}
