package com.ntg.engine.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ntg.engine.entites.Schedule;
import com.ntg.engine.repository.ScheduleRepository;

@Service
public class ScheduleService {

	@Autowired
	private ScheduleRepository scheduleRepository;
	
	
	@Transactional
	public List<Schedule> findByJobRunning(boolean jobRunning){
		return scheduleRepository.findByJobRunningAndIsDeleted(jobRunning , false);
	}
}
