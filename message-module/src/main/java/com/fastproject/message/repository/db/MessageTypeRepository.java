package com.fastproject.message.repository.db;

import com.fastproject.message.domain.MessageType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageTypeRepository extends JpaRepository<MessageType, Long>, JpaSpecificationExecutor<MessageType> {

    boolean existsByCode(String code);

    boolean existsByCodeAndIdNot(String code, Long id);
}