package com.ntg.engine.repository;

import com.ntg.engine.dto.GenericObjectSLAMilestone;
import com.ntg.engine.entites.SlaMilestones;


public interface GenericObjectSlaMilestonesRepository {

    public void saveMilestonesOnGenericObject(long genericObjectId, long profileId, SlaMilestones slaMilestone, String slaMilestoneTableName, String tenantSchema);

    public GenericObjectSLAMilestone findByGenericObjectAndImsla(long genericObjectId, long profileId, SlaMilestones slaMilestoned, String slaMilestoneTableName, String tenantSchema);

    public void save(GenericObjectSLAMilestone genericObjectSlaMilestones, String slaMilestoneTableName, String tenantSchema);


}
