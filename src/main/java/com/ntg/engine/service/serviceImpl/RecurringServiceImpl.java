package com.ntg.engine.service.serviceImpl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ntg.common.NTGMessageOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ntg.engine.dto.ScheduleJobHistoryDTO;
import com.ntg.engine.repository.custom.ScheduleJobHistoryDao;
import com.ntg.engine.repository.custom.SqlHelperDao;
import com.ntg.engine.service.RecurringService;
import com.ntg.engine.util.Constants;
import com.ntg.engine.util.Utils;

@Service
public class RecurringServiceImpl implements RecurringService {

    @Autowired
    private ScheduleJobHistoryDao scheduleHistoryRepository;

    @Autowired
    private SqlHelperDao sqlHelperDao;

    @Override
    public List<Map<String, Object>> findObjectsDependOnRecurringType(Long recurringType, Long recurringValue,
                                                                      Long recurringUnit, Long scheduleID, String tableName, String query, String slaMilestoneTableName, String tenantSchema) {
        List<Map<String, Object>> result = null;
        tenantSchema = (Utils.isNotEmpty(tenantSchema)) ? tenantSchema + "." : "";


            String recurringFilter = null;
            Date dateBack = null;

            if (Constants.RECURRING_ONCE == recurringType) {



                recurringFilter = " AND  NOT EXISTS ( select 1 from " + tenantSchema + tableName + " e  " +
                        " LEFT OUTER JOIN " + tenantSchema + slaMilestoneTableName + " ms ON ms.sla_milestones_id = e.MILESTONE_ID " +
                        " WHERE e.TYPE_ID= c.TYPEID and c.RECID=e.OBJECT_ID and  e.SCHEDULE_ID=?  ) ";

                query = checkLimit(query, recurringFilter);
                result = sqlHelperDao.queryForList(query, new Object[]{scheduleID});
            } else if (Constants.RECURRING_EVERY == recurringType) {


                Calendar cal = Calendar.getInstance();
                cal.setTime(new Date());

                if (Constants.SCHEDULE_HOUR == recurringUnit) {
                    cal.add(Calendar.HOUR, (int) -recurringValue);
                    dateBack = cal.getTime();
                } else if (Constants.SCHEDULE_DAY == recurringUnit) {
                    cal.add(Calendar.DAY_OF_MONTH, (int) -recurringValue);
                    dateBack = cal.getTime();
                } else if (Constants.SCHEDULE_WEEK == recurringUnit) {
                    cal.add(Calendar.DAY_OF_MONTH, (int) -(recurringValue * 7));
                    dateBack = cal.getTime();
                } else if (Constants.SCHEDULE_MONTH == recurringUnit) {
                    cal.add(Calendar.MONTH, (int) -recurringValue);
                    dateBack = cal.getTime();
                }
                recurringFilter = " AND not EXISTS ( select 1 from " + tenantSchema + tableName + " e  " +
                        " LEFT OUTER JOIN " + tenantSchema + slaMilestoneTableName + " ms ON ms.sla_milestones_id = e.MILESTONE_ID " +
                        " WHERE e.TYPE_ID= c.TYPEID and c.RECID=e.OBJECT_ID and  e.SCHEDULE_ID="
                        + scheduleID + " and (TRANSACTION_DATA >= ? ) )";
                query = query + " " + recurringFilter;
                result = sqlHelperDao.queryForList(query, new Object[]{scheduleID, dateBack});

            } else {

                result = sqlHelperDao.queryForList(query);

            }

        return result;
    }

    @Override
    public void saveScheduleJobHistor(Long objectId, Long typeId, Long scheduleId, String mail, String tableName,
                                      Long milestoneId, String tenantSchema) {
        tenantSchema = (Utils.isNotEmpty(tenantSchema)) ? tenantSchema + "." : "";

        ScheduleJobHistoryDTO scheduleHistory = new ScheduleJobHistoryDTO();
        scheduleHistory.setObjectId(objectId);
        scheduleHistory.setTypeId(typeId);
        scheduleHistory.setScheduleId(scheduleId);
        scheduleHistory.setHistoryObject(mail);
        scheduleHistory.setTableName(tableName);
        scheduleHistory.setTransactionData(new Date());
        scheduleHistory.setMilestoneId(milestoneId);
        List<ScheduleJobHistoryDTO> emailsHistories = scheduleHistoryRepository
                .findByObjectIdAndTypeIdAndScheduleId(scheduleHistory, tenantSchema);
        if (Utils.isNotEmpty(emailsHistories)) {
            if (emailsHistories.size() > 1) {
                scheduleHistory.setTransactionData(new Date());
                emailsHistories.get(0).setHistoryObject(mail);
                scheduleHistoryRepository.updateScheduleJobHistory(emailsHistories.get(0), tenantSchema);
             } else {
                System.out.println("(Info) ==>  Stop Type and object and schedule not unique ===>ObjID : " + objectId + ",TypeId:" + typeId
                        + ",ScheduleId:" + scheduleId  );

            }
        } else {
            scheduleHistoryRepository.saveScheduleJobHistory(scheduleHistory, tenantSchema);

            System.out.println("(Info) ==> Schedule Added To History successfully ===>ObjID : " + scheduleHistory.getObjectId() + ",TypeId:" + scheduleHistory.getTypeId()
                    + ",ScheduleId:" + scheduleHistory.getScheduleId()  );
        }

    }

    ///////////////ayaaaaaaaaaaaaa ////Stupid fix need to be fixed :D
    private String checkLimit(String query, String recurringFilter) {
        String limit = "";
        if (query.indexOf(" Limit (") > -1) {
            limit = query.substring(query.indexOf(" Limit ("));
            query = query.substring(0, query.indexOf(" Limit ("));
        }
        query = query + " " + recurringFilter + limit;
        return query;
    }

}
