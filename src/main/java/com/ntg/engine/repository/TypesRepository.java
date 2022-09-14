package com.ntg.engine.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ntg.engine.entites.Types;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TypesRepository extends CrudRepository<Types, Long> {

	@Transactional
	public Types findByRecId(long recId);

}
