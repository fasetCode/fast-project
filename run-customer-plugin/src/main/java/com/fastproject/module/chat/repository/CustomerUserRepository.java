package com.fastproject.module.chat.repository;

import com.fastproject.module.chat.domain.CustomerUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CustomerUserRepository extends JpaRepository<CustomerUser, Long>, JpaSpecificationExecutor<CustomerUser> {

}
