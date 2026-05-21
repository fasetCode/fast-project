package com.fastproject.mall.repository.db;

import com.fastproject.mall.domain.MallUserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户收货地址 Repository
 */
@Repository
public interface MallUserAddressRepository extends JpaRepository<MallUserAddress, Long>, JpaSpecificationExecutor<MallUserAddress> {

    List<MallUserAddress> findByUserId(Long userId);

    MallUserAddress findByUserIdAndIsDefault(Long userId, Integer isDefault);
}
