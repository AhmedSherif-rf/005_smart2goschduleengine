package com.ntg.engine.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ntg.engine.entites.Schedule;

@Repository
public interface ScheduleRepository extends CrudRepository<Schedule, Long> {
	//@Aya.Ramadan Dev-00000521 : sla millstone bugs
	@Transactional
	public List<Schedule> findByJobRunningAndIsDeleted(boolean jobRunning ,Boolean isDeleted);

}
