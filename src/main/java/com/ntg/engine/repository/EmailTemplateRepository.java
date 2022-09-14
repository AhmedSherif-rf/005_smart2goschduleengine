package com.ntg.engine.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ntg.engine.entites.EmailTemplate;

@Transactional
@Repository
public interface EmailTemplateRepository extends PagingAndSortingRepository<EmailTemplate, Long> {

	public List<EmailTemplate> findByTypeRecId(Long recId);

	public EmailTemplate findByRecId(Long recId);
}
