package com.fastproject.module.chat.service.impl;

import com.fastproject.module.chat.domain.User;
import com.fastproject.module.chat.repository.UserRepository;
import com.fastproject.module.chat.service.UserService;
import com.fastproject.utils.vo.PageVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long save(User user) {
        log.info("保存用户信息：{}", user);
        return userRepository.save(user).getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(User user) {
        log.info("修改用户信息：{}", user);
        if (!userRepository.existsById(user.getId())) {
            throw new RuntimeException("用户不存在");
        }
        userRepository.save(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        log.info("删除用户信息：{}", id);
        userRepository.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(List<Long> ids) {
        log.info("批量删除用户信息：{}", ids);
        userRepository.deleteAllById(ids);
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(Long id) {
        log.info("根据ID查询用户信息：{}", id);
        return userRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public PageVo<List<User>> findPage(int page, int pageSize) {
        log.info("分页查询用户信息：page={}, pageSize={}", page, pageSize);
        PageRequest pageable = PageRequest.of(page, pageSize, Sort.by("id").descending());
        Page<User> pageResult = userRepository.findAll(pageable);
        return PageVo.of(pageResult.getTotalElements(), pageResult.getContent());
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return userRepository.count();
    }
}
