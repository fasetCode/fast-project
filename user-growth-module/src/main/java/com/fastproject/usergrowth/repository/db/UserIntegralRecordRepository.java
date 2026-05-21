package com.fastproject.usergrowth.repository.db;

import com.fastproject.usergrowth.domain.UserIntegralRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserIntegralRecordRepository extends JpaRepository<UserIntegralRecord, Long>, JpaSpecificationExecutor<UserIntegralRecord> {
}
