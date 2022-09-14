package com.ntg.engine.repository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ntg.engine.entites.SlaMilestones;
import com.ntg.engine.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ntg.engine.dto.GenericObjectSLAMilestone;
import com.ntg.engine.entites.Objects;


@Component
public class GenericObjectSlaMilestonesRepositoryImpl implements GenericObjectSlaMilestonesRepository {

    @Autowired
    private ObjectsRepository objRepo;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private SlaMilestoneRepository slaMilestoneRepository;

    @Override
    public void saveMilestonesOnGenericObject(long genericObjectId, long profileId, SlaMilestones slaMilestone, String slaMilestoneTableName, String tenantSchema) {
        GenericObjectSLAMilestone slaMileStoneObj = findByGenericObjectAndImsla(genericObjectId, profileId, slaMilestone, slaMilestoneTableName, tenantSchema);
        if (Utils.isNotEmpty(slaMileStoneObj)) {
            save(slaMileStoneObj, slaMilestoneTableName, tenantSchema);
        }
    }

    @Override
    public GenericObjectSLAMilestone findByGenericObjectAndImsla(long genericObjectId, long profileId, SlaMilestones slaMilestone, String slaMilestoneTableName, String tenantSchema) {
        tenantSchema = (Utils.isNotEmpty(tenantSchema)) ? tenantSchema + "." : "";
        String sql = "SELECT * FROM " + tenantSchema + slaMilestoneTableName + " WHERE  generic_object_recid = ? " +
                " AND generic_object_sla_recid = ? AND sla_milestones_Id = ?";
        List<Object> params = new ArrayList<>();
        params.add(genericObjectId);
        params.add(profileId);
        params.add(slaMilestone.getRecId());

        List<Map<String, Object>> genericMileStones = jdbcTemplate.queryForList(sql, params.toArray());
        GenericObjectSLAMilestone genericMileStone = null;
        if (Utils.isNotEmpty(genericMileStones)) {
            Map<String, Object> oneMileStone = genericMileStones.get(0);
            if (Utils.isNotEmpty(Utils.isNotEmpty(oneMileStone))) {
                Long recId = (Utils.isNotEmpty(oneMileStone.get("recid")))
                        ? Long.parseLong(oneMileStone.get("recid").toString()) : 0;
                Long milestonesId = (Utils.isNotEmpty(oneMileStone.get("sla_milestones_id")))
                        ? Long.parseLong(oneMileStone.get("sla_milestones_id").toString()) : 0;
                Long jobRun = (Utils.isNotEmpty(oneMileStone.get("job_run")))
                        ? Long.parseLong(oneMileStone.get("job_run").toString()) : 0;
                Long genericObjectSlaRecid = (Utils.isNotEmpty(oneMileStone.get("generic_object_sla_recid")))
                        ? Long.parseLong(oneMileStone.get("generic_object_sla_recid").toString()) : 0;
                Long corLookupRecid = (Utils.isNotEmpty(oneMileStone.get("cor_lookup_recid")))
                        ? Long.parseLong(oneMileStone.get("cor_lookup_recid").toString()) : 0;
                Long genericObjectRecid = (Utils.isNotEmpty(oneMileStone.get("generic_object_recid")))
                        ? Long.parseLong(oneMileStone.get("generic_object_recid").toString()) : 0;

                genericMileStone = new GenericObjectSLAMilestone();
                genericMileStone.setRecid(recId);
                genericMileStone.setJobRun(jobRun);
                genericMileStone.setGenericObjectRecid(genericObjectRecid);
                genericMileStone.setGenericObjectSlaRecid(genericObjectSlaRecid);
                genericMileStone.setCorLookupRecid(corLookupRecid);
                genericMileStone.setSlaMilestones(slaMilestone);
                genericMileStone.setSlaMilestonesId(slaMilestone.getRecId());
            }
        }
        return genericMileStone;
    }


    @Override
    public void save(GenericObjectSLAMilestone genericObjectSlaMilestones, String slaMilestoneTableName, String tenantSchema) {
        tenantSchema = (Utils.isNotEmpty(tenantSchema)) ? tenantSchema + "." : "";
        String sql = "UPDATE " + tenantSchema + slaMilestoneTableName + " SET transaction_date = ?,"
                + " cor_lookup_recid = ? , job_run = ? where recid = ? ";
        List<Object> params = new ArrayList<>();
        params.add(new Date());
        params.add(genericObjectSlaMilestones.getCorLookupRecid());
        params.add(genericObjectSlaMilestones.getJobRun());
        params.add(genericObjectSlaMilestones.getRecid());

        jdbcTemplate.update(sql, params.toArray());
    }

}
