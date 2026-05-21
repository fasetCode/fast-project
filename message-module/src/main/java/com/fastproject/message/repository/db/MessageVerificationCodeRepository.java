package com.fastproject.message.repository.db;

import com.fastproject.message.domain.MessageVerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MessageVerificationCodeRepository extends JpaRepository<MessageVerificationCode, Long>, JpaSpecificationExecutor<MessageVerificationCode> {

    boolean existsByTargetAndConfigId(String target, Long configId);

    boolean existsByTargetAndConfigIdAndIdNot(String target, Long configId, Long id);

    Optional<MessageVerificationCode> findFirstByTargetAndCodeAndStatusOrderByCreateTimeDesc(String target, String code, Integer status);
}