package com.fastproject.message.repository.db;

import com.fastproject.message.domain.MessageRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRecordRepository extends JpaRepository<MessageRecord, Long>, JpaSpecificationExecutor<MessageRecord> {
}
