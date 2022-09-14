package com.ntg.engine.repository.customImpl;

import com.ntg.engine.dto.ScheduleJobHistoryDTO;
import com.ntg.engine.repository.custom.ScheduleJobHistoryDao;
import com.ntg.engine.repository.custom.SqlHelperDao;
import com.ntg.engine.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class ScheduleJobHistoryImpl implements ScheduleJobHistoryDao {

    private JdbcTemplate template;

    @Autowired
    private SqlHelperDao SqlHelperDao;

    public static String connectionUrl;

    @Autowired
    public void setDataSource(DataSource ds) {
        template = new JdbcTemplate(ds);
    }

    @Override
    public List<ScheduleJobHistoryDTO> findByObjectIdAndTypeIdAndScheduleId(ScheduleJobHistoryDTO scheduleJobHistoryDTO, String tenantSchema) {
        tenantSchema = (Utils.isNotEmpty(tenantSchema)) ? tenantSchema + "." : "";
        String query = "select recId, OBJECT_ID as objectId, TYPE_ID as typeId,SCHEDULE_ID as scheduleId,TRANSACTION_DATA as transactionData ,Milestone_Id as milestoneId "
                + " from " + tenantSchema + scheduleJobHistoryDTO.getTableName() + " s where s.OBJECT_ID=? AND s.TYPE_ID=? AND SCHEDULE_ID=? AND Milestone_Id=?";

        List<ScheduleJobHistoryDTO> scheduleJobHistoryDTOs = new ArrayList<>();
        return template.query(query, new Object[]{scheduleJobHistoryDTO.getObjectId(), scheduleJobHistoryDTO.getTypeId(),
                        scheduleJobHistoryDTO.getScheduleId(), scheduleJobHistoryDTO.getMilestoneId()},
                new ResultSetExtractor<List<ScheduleJobHistoryDTO>>() {

                    @Override
                    public List<ScheduleJobHistoryDTO> extractData(ResultSet rs) throws SQLException, DataAccessException {
                        while (rs.next()) {
                            ScheduleJobHistoryDTO object = new ScheduleJobHistoryDTO();

                            object.setRecId(rs.getLong("recId"));
                            object.setObjectId(rs.getLong("objectId"));
                            object.setTypeId(rs.getLong("typeId"));
                            object.setScheduleId(rs.getLong("scheduleId"));
                            object.setMilestoneId(Utils.isNotEmpty(rs.getLong("milestoneId")) ? rs.getLong("milestoneId") : null);
                            object.setTransactionData(rs.getDate("transactionData"));
                            scheduleJobHistoryDTOs.add(object);
                        }
                        return scheduleJobHistoryDTOs;
                    }
                });
    }

    @Transactional
    @Override
    public void saveScheduleJobHistory(ScheduleJobHistoryDTO scheduleJobHistoryDTO, String tenantSchema) {
        tenantSchema = (Utils.isNotEmpty(tenantSchema)) ? tenantSchema + "." : "";
        Object[] params = new Object[]{scheduleJobHistoryDTO.getObjectId(), scheduleJobHistoryDTO.getTypeId(),
                scheduleJobHistoryDTO.getScheduleId(), new Date(),
                Utils.isNotEmpty(scheduleJobHistoryDTO.getHistoryObject()) ? scheduleJobHistoryDTO.getHistoryObject() : "",
                scheduleJobHistoryDTO.getMilestoneId()};

        // define SQL types of the arguments

        String postgresSeq = SqlHelperDao.SquanceFetchSql(scheduleJobHistoryDTO.getTableName() + "_S", 2L);
        int[] types = new int[]{Types.NUMERIC, Types.NUMERIC, Types.NUMERIC, Types.TIMESTAMP, Types.CLOB, Types.NUMERIC};
        String insertSql;
        if(getConnectionType() == 1)
            insertSql = "insert into " + tenantSchema + scheduleJobHistoryDTO.getTableName()
                + "(RECID, OBJECT_ID, TYPE_ID,SCHEDULE_ID,TRANSACTION_DATA,HISTORY_Object,Milestone_Id) values (("
                + postgresSeq + "),?,?,?,?,?,?)";
        else
            insertSql = "insert into " + tenantSchema + scheduleJobHistoryDTO.getTableName()
                    + "(RECID, OBJECT_ID, TYPE_ID,SCHEDULE_ID,TRANSACTION_DATA,HISTORY_Object,Milestone_Id) values (("
                    + postgresSeq + "),?,?,?,?,?::bytea,?)";
        template.update(insertSql, params, types);

    }

    @Transactional
    @Override
    public void updateScheduleJobHistory(ScheduleJobHistoryDTO scheduleJobHistoryDTO, String tenantSchema) {
        tenantSchema = (Utils.isNotEmpty(tenantSchema)) ? tenantSchema + "." : "";
        String query = "UPDATE " + tenantSchema + scheduleJobHistoryDTO.getTableName() + " SET TRANSACTION_DATA = ? WHERE recid = ?";
        template.update(query, new Date(), scheduleJobHistoryDTO.getRecId());
    }

    public int getConnectionType() { // return 1 for oracle AND 2 FOR POSTGRES
        return (this.connectionUrl.toLowerCase().indexOf("oracle") > -1) ? 1 : 2;
    }
}
