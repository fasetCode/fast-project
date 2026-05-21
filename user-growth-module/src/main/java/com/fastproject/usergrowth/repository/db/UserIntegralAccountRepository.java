package com.fastproject.usergrowth.repository.db;

import com.fastproject.usergrowth.domain.UserIntegralAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserIntegralAccountRepository extends JpaRepository<UserIntegralAccount, Long>, JpaSpecificationExecutor<UserIntegralAccount> {
    UserIntegralAccount findByUserId(Long userId);
}
