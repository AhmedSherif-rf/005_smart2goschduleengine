package com.ntg.engine.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ntg.engine.entites.TypesUDa;

@Repository
public interface TypesUDAsRepository extends CrudRepository<TypesUDa, Long> {

	public ArrayList<TypesUDa> findByTypesRecIdOrderByUdaOrderAsc(long typeId);

	public List<TypesUDa> findByTypesRecIdAndUdaType(long typesRecId, long udaType);

}
