package com.fastproject.module.chat.repository;

import com.fastproject.module.chat.domain.Config;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ConfigRepository extends JpaRepository<Config, Long> , JpaSpecificationExecutor<Config> {

}
