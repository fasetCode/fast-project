package com.fastproject.mall.service.impl;

import com.fastproject.db.QueryHelp;
import com.fastproject.exception.BusinessException;
import com.fastproject.mall.domain.MallUserAddress;
import com.fastproject.mall.mapper.MallUserAddressMapper;
import com.fastproject.mall.repository.db.MallUserAddressRepository;
import com.fastproject.mall.service.MallUserAddressService;
import com.fastproject.mall.vo.useraddress.MallUserAddressCreate;
import com.fastproject.mall.vo.useraddress.MallUserAddressQuery;
import com.fastproject.mall.vo.useraddress.MallUserAddressUpdate;
import com.fastproject.mall.vo.useraddress.MallUserAddressVo;
import com.fastproject.utils.vo.PageVo;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户收货地址 Service 实现类
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class MallUserAddressServiceImpl implements MallUserAddressService {

    private final MallUserAddressRepository mallUserAddressRepository;
    private final QueryHelp<MallUserAddress> queryHelp;
    private final MallUserAddressMapper mallUserAddressMapper;

    @Override
    public Long save(MallUserAddressCreate create) {
        log.info("保存用户收货地址：{}", create);
        MallUserAddress entity = mallUserAddressMapper.toUserAddress(create);
        mallUserAddressRepository.save(entity);
        return entity.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(MallUserAddressUpdate update) {
        log.info("修改用户收货地址：{}", update);
        MallUserAddress entity = mallUserAddressRepository.findById(update.getId())
                .orElseThrow(() -> new BusinessException("收货地址不存在"));
        mallUserAddressMapper.updateUserAddressFromDto(update, entity);
        mallUserAddressRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        log.info("删除用户收货地址：{}", id);
        mallUserAddressRepository.findById(id).orElseThrow(() -> new BusinessException("收货地址不存在"));
        mallUserAddressRepository.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(List<Long> ids) {
        log.info("批量删除用户收货地址：{}", ids);
        mallUserAddressRepository.deleteAllById(ids);
    }

    @Override
    public MallUserAddressVo findById(Long id) {
        log.info("根据ID查询用户收货地址：{}", id);
        MallUserAddress entity = mallUserAddressRepository.findById(id).orElse(null);
        return entity != null ? mallUserAddressMapper.toVo(entity) : null;
    }

    @Override
    public List<MallUserAddressVo> findByUserId(Long userId) {
        log.info("根据用户ID查询收货地址列表：{}", userId);
        return mallUserAddressMapper.toVo(mallUserAddressRepository.findByUserId(userId));
    }

    @Override
    public MallUserAddressVo findDefaultByUserId(Long userId) {
        log.info("查询用户默认收货地址：{}", userId);
        MallUserAddress entity = mallUserAddressRepository.findByUserIdAndIsDefault(userId, 1);
        return entity != null ? mallUserAddressMapper.toVo(entity) : null;
    }

    @Override
    public PageVo<List<MallUserAddressVo>> findPage(MallUserAddressQuery query) {
        log.info("分页查询用户收货地址：{}", query);
        Pageable pageable = PageRequest.of(query.getPage(), query.getPageSize(), Sort.by("id").descending());
        Specification<MallUserAddress> spec = queryHelp.getWhere(query, (root, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (query.getUserId() != null) predicates.add(cb.equal(root.get("userId"), query.getUserId()));
            if (query.getIsDefault() != null) predicates.add(cb.equal(root.get("isDefault"), query.getIsDefault()));
            return predicates;
        });
        Page<MallUserAddress> page = mallUserAddressRepository.findAll(spec, pageable);
        return PageVo.of(page.getTotalElements(), mallUserAddressMapper.toVo(page.getContent()));
    }

    @Override
    public List<MallUserAddressVo> findAll() {
        return mallUserAddressMapper.toVo(mallUserAddressRepository.findAll(Sort.by("id").descending()));
    }
}
