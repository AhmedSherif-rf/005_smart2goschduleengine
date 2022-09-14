package com.ntg.engine.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.ntg.engine.entites.Objects;
import org.springframework.transaction.annotation.Transactional;


public interface ObjectsRepository extends CrudRepository<Objects, Long> {

    @Transactional
    public Objects findByRecId(@Param("id") Long id);

}
