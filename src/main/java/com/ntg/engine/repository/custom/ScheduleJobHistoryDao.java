package com.ntg.engine.repository.custom;

import java.util.List;

import com.ntg.engine.dto.ScheduleJobHistoryDTO;

public interface ScheduleJobHistoryDao {

    public List<ScheduleJobHistoryDTO> findByObjectIdAndTypeIdAndScheduleId(ScheduleJobHistoryDTO scheduleJobHistoryDTO, String tenantSchema);

    public void saveScheduleJobHistory(ScheduleJobHistoryDTO scheduleJobHistoryDTO, String tenantSchema);

    public void updateScheduleJobHistory(ScheduleJobHistoryDTO scheduleJobHistoryDTO, String tenantSchema);
}
