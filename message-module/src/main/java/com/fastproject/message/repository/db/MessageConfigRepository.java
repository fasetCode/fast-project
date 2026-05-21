package com.fastproject.message.repository.db;

import com.fastproject.message.domain.MessageConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageConfigRepository extends JpaRepository<MessageConfig, Long>, JpaSpecificationExecutor<MessageConfig> {

    boolean existsByType(String type);

    boolean existsByTypeAndIdNot(String type, Long id);
}