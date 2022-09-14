package com.ntg.engine.service;

import java.util.Map;

public interface RuleInvokerService {

    void getRestServiceResponse(String scheduleId, String typeId, String saveAfterDoRule, String query,
                                Long recurringType, Long recurringUnitNum, Long recurringValue, String tableName, Map<String, Object> tenantInfo);

}