package com.ntg.engine.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ntg.engine.entites.SlaMilestones;

@Repository
public interface SlaMilestoneRepository extends CrudRepository<SlaMilestones, Long> {
    /**
     * @ModifaiedBy:Aya.ramadan =>Dev-00000521 : sla millstone bugs
     */

    @Transactional
    @Query(value = "select m.* from adm_Sla_Milestone m where m.sla_Profile_Id = :slaProfileId and (is_deleted = false or is_deleted is null )", nativeQuery = true)
    public List<SlaMilestones> findBySlaProfileId(@Param("slaProfileId") Long slaProfileId);

    @Transactional
    public SlaMilestones findByRecId(@Param("recId") Long recId);
}
