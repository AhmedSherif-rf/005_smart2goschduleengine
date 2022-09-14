package com.ntg.engine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ntg.engine.entites.Types;
import com.ntg.engine.repository.TypesRepository;

@Service
public class TypesService {

	@Autowired
	private TypesRepository typesRepository;

	public Types findByRecId(Long recId) {
		return typesRepository.findByRecId(recId);
	}
}
