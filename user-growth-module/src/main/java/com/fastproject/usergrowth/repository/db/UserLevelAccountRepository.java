package com.fastproject.usergrowth.repository.db;

import com.fastproject.usergrowth.domain.UserLevelAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLevelAccountRepository extends JpaRepository<UserLevelAccount, Long>, JpaSpecificationExecutor<UserLevelAccount> {

    UserLevelAccount findByUserId(Long userId);
}
