package com.fastproject.usergrowth.repository.db;

import com.fastproject.usergrowth.domain.UserLevelRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLevelRecordRepository extends JpaRepository<UserLevelRecord, Long>, JpaSpecificationExecutor<UserLevelRecord> {
}
