package com.fastproject.message.repository.db;

import com.fastproject.message.domain.MessageTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageTemplateRepository extends JpaRepository<MessageTemplate, Long>, JpaSpecificationExecutor<MessageTemplate> {

    boolean existsByCode(String code);

    boolean existsByCodeAndIdNot(String code, Long id);

    MessageTemplate findByCodeAndStatus(String code, Integer status);
}