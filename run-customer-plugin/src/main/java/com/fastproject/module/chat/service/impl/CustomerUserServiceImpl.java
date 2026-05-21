package com.fastproject.module.chat.service.impl;

import com.fastproject.module.chat.domain.CustomerUser;
import com.fastproject.module.chat.repository.CustomerUserRepository;
import com.fastproject.module.chat.service.CustomerUserService;
import com.fastproject.utils.vo.PageVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 客服用户Service实现类
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerUserServiceImpl implements CustomerUserService {

    private final CustomerUserRepository customerUserRepository;

    /**
     * 保存客服用户
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long save(CustomerUser customerUser) {
        log.info("保存客服用户信息：{}", customerUser);
        return customerUserRepository.save(customerUser).getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(CustomerUser customerUser) {
        log.info("修改客服用户信息：{}", customerUser);
        if (!customerUserRepository.existsById(customerUser.getId())) {
            throw new RuntimeException("客服用户不存在");
        }
        customerUserRepository.save(customerUser);
    }

    /**
     * 更新客服用户
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        log.info("删除客服用户信息：{}", id);
        customerUserRepository.deleteById(id);
    }

    /**
     * 批量删除客服用户
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(List<Long> ids) {
        log.info("批量删除客服用户信息：{}", ids);
        customerUserRepository.deleteAllById(ids);
    }

    /**
     * 根据ID查询客服用户
     */
    @Override
    @Transactional(readOnly = true)
    public CustomerUser findById(Long id) {
        log.info("根据ID查询客服用户信息：{}", id);
        return customerUserRepository.findById(id).orElse(null);
    }

    /**
     * 分页查询客服用户
     */
    @Override
    @Transactional(readOnly = true)
    public PageVo<List<CustomerUser>> findPage(int page, int pageSize) {
        log.info("分页查询客服用户信息：page={}, pageSize={}", page, pageSize);
        PageRequest pageable = PageRequest.of(page, pageSize, Sort.by("id").descending());
        Page<CustomerUser> pageResult = customerUserRepository.findAll(pageable);
        return PageVo.of(pageResult.getTotalElements(), pageResult.getContent());
    }

    /**
     * 统计客服用户数量
     */
    @Override
    @Transactional(readOnly = true)
    public long count() {
        return customerUserRepository.count();
    }
}
