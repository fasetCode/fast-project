package com.fastproject.mall.service;

import com.fastproject.mall.vo.useraddress.MallUserAddressCreate;
import com.fastproject.mall.vo.useraddress.MallUserAddressQuery;
import com.fastproject.mall.vo.useraddress.MallUserAddressUpdate;
import com.fastproject.mall.vo.useraddress.MallUserAddressVo;
import com.fastproject.utils.vo.PageVo;

import java.util.List;

/**
 * 用户收货地址 Service 接口
 */
public interface MallUserAddressService {

    Long save(MallUserAddressCreate create);

    void update(MallUserAddressUpdate update);

    void delete(Long id);

    void batchDelete(List<Long> ids);

    MallUserAddressVo findById(Long id);

    List<MallUserAddressVo> findByUserId(Long userId);

    MallUserAddressVo findDefaultByUserId(Long userId);

    PageVo<List<MallUserAddressVo>> findPage(MallUserAddressQuery query);

    List<MallUserAddressVo> findAll();
}
