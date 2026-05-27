package com.fastproject.system.service.impl;

import com.fastproject.db.QueryHelp;
import com.fastproject.exception.BusinessException;
import com.fastproject.file.api.FileHandle;
import com.fastproject.file.vo.FileUrlVo;
import com.fastproject.system.domain.SysDepartment;
import com.fastproject.system.domain.SysPost;
import com.fastproject.system.domain.SysRole;
import com.fastproject.system.domain.SysUsers;
import com.fastproject.system.mapper.SysRoleMapper;
import com.fastproject.system.mapper.SysUsersMapper;
import com.fastproject.system.repository.db.SysDepartmentRepository;
import com.fastproject.system.repository.db.SysPostRepository;
import com.fastproject.system.repository.db.SysRoleRepository;
import com.fastproject.system.repository.db.SysUsersRepository;
import com.fastproject.system.service.SysUsersService;
import com.fastproject.system.tenant.TenantAccessSupport;
import com.fastproject.system.vo.users.*;
import com.fastproject.utils.vo.PageVo;
import com.fastproject.vo.SysUsersLoginVo;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class SysUsersServiceImpl implements SysUsersService {

    private final SysUsersRepository sysUsersRepository;
    private final SysRoleRepository sysRoleRepository;
    private final SysDepartmentRepository sysDepartmentRepository;
    private final SysPostRepository sysPostRepository;
    private final QueryHelp<SysUsers> queryHelp;
    private final SysUsersMapper sysUsersMapper;
    private final SysRoleMapper sysRoleMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final FileHandle fileHandle;
    private final TenantAccessSupport tenantAccessSupport;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long save(SysUsersCreate create) {
        log.info("保存用户信息：{}", create);
        if (sysUsersRepository.existsByUsername(create.getUsername())) {
            throw new BusinessException("账号已存在");
        }
        if (sysUsersRepository.existsByPhone(create.getPhone())) {
            throw new BusinessException("电话已存在");
        }
        if (sysUsersRepository.existsByEmail(create.getEmail())) {
            throw new BusinessException("邮箱已存在");
        }
        SysUsers users = sysUsersMapper.toUser(create);
        tenantAccessSupport.bindCurrentTenant(users);
        if (create.getRoleIds() != null && !create.getRoleIds().isEmpty()) {
            Set<SysRole> roles = new HashSet<>(sysRoleRepository.findAllById(create.getRoleIds()));
            roles.forEach(role -> tenantAccessSupport.checkEntityAccess(role, "角色不属于当前租户"));
            users.setRoles(roles);
        }
        // 设置部门
        if (create.getDepartmentId() != null) {
            SysDepartment department = sysDepartmentRepository.findById(create.getDepartmentId()).orElse(null);
            tenantAccessSupport.checkEntityAccess(department, "部门不属于当前租户");
            users.setDepartment(department);
        }
        // 设置岗位
        if (create.getPostId() != null) {
            SysPost post = sysPostRepository.findById(create.getPostId()).orElse(null);
            tenantAccessSupport.checkEntityAccess(post, "岗位不属于当前租户");
            users.setPost(post);
        }
        sysUsersRepository.save(users);
        return users.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysUserUpdate sysUserUpdate) {
        log.info("修改用户信息：{}", sysUserUpdate);
        SysUsers users = sysUsersRepository.findById(sysUserUpdate.getId())
                .orElseThrow(() -> new BusinessException("用户不存在"));
        tenantAccessSupport.checkEntityAccess(users, "无权修改当前租户用户");

        if (sysUsersRepository.existsByUsernameAndIdNot(sysUserUpdate.getUsername(), sysUserUpdate.getId())) {
            throw new BusinessException("账号已存在");
        }
        if (sysUsersRepository.existsByPhoneAndIdNot(sysUserUpdate.getPhone(), sysUserUpdate.getId())) {
            throw new BusinessException("电话已存在");
        }
        if (sysUsersRepository.existsByEmailAndIdNot(sysUserUpdate.getEmail(), sysUserUpdate.getId())) {
            throw new BusinessException("邮箱已存在");
        }

        users.getRoles().clear();
        if (sysUserUpdate.getRoleIds() != null && !sysUserUpdate.getRoleIds().isEmpty()) {
            Set<SysRole> roles = new HashSet<>(sysRoleRepository.findAllById(sysUserUpdate.getRoleIds()));
            roles.forEach(role -> tenantAccessSupport.checkEntityAccess(role, "角色不属于当前租户"));
            users.setRoles(roles);
        }
        // 设置部门
        if (sysUserUpdate.getDepartmentId() != null) {
            SysDepartment department = sysDepartmentRepository.findById(sysUserUpdate.getDepartmentId()).orElse(null);
            tenantAccessSupport.checkEntityAccess(department, "部门不属于当前租户");
            users.setDepartment(department);
        } else {
            users.setDepartment(null);
        }
        // 设置岗位
        if (sysUserUpdate.getPostId() != null) {
            SysPost post = sysPostRepository.findById(sysUserUpdate.getPostId()).orElse(null);
            tenantAccessSupport.checkEntityAccess(post, "岗位不属于当前租户");
            users.setPost(post);
        } else {
            users.setPost(null);
        }
        // 如果头像是以 http 开头的完整 URL，则不更新，保留原值
        if (StringUtils.hasText(sysUserUpdate.getAvatar()) && sysUserUpdate.getAvatar().startsWith("http")) {
            sysUserUpdate.setAvatar(users.getAvatar());
        }
        sysUsersMapper.updateUserFromDto(sysUserUpdate, users);
        sysUsersRepository.save(users);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        log.info("删除用户信息：{}", id);
        SysUsers users = sysUsersRepository.findById(id)
                .orElseThrow(() -> new BusinessException("用户不存在"));
        tenantAccessSupport.checkEntityAccess(users, "无权删除当前租户用户");
        sysUsersRepository.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(List<Long> ids) {
        log.info("批量删除用户信息：{}", ids);
        List<SysUsers> users = sysUsersRepository.findAllById(ids);
        for (SysUsers user : users) {
            tenantAccessSupport.checkEntityAccess(user, "无权删除当前租户用户");
        }
        sysUsersRepository.deleteAllById(ids);
    }

    @Override
    @Transactional(readOnly = true)
    public SysUsersDetailVo findById(Long id) {
        log.info("根据ID查询用户信息：{}", id);
        SysUsers users = sysUsersRepository.findByIdWithRoles(id).orElse(null);
        if (users != null) {
            tenantAccessSupport.checkEntityAccess(users, "无权查看当前租户用户");
            SysUsersDetailVo vo = new SysUsersDetailVo();
            vo.setId(users.getId());
            vo.setUsername(users.getUsername());
            vo.setNickname(users.getNickname());
            vo.setEmail(users.getEmail());
            vo.setPhone(users.getPhone());
            vo.setGender(users.getGender());
            vo.setStatus(users.getStatus());
            // 设置部门ID
            if (users.getDepartment() != null) {
                vo.setDepartmentId(users.getDepartment().getId());
            }
            // 设置岗位ID
            if (users.getPost() != null) {
                vo.setPostId(users.getPost().getId());
            }
            Set<SysRole> roles = users.getRoles();
            List<Long> roleIds = roles.stream().map(SysRole::getId).collect(Collectors.toList());
            vo.setRoleIds(roleIds);
            vo.setRoles(sysRoleMapper.toVo(new ArrayList<>(roles)));
            // 转换头像URL
            if (StringUtils.hasText(users.getAvatar())) {
                String avatarUrl = fileHandle.getUrl(users.getAvatar());
                if (StringUtils.hasText(avatarUrl)) {
                    vo.setAvatar(avatarUrl);
                } else {
                    vo.setAvatar(users.getAvatar());
                }
            }
            return vo;
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public PageVo<List<SysUsersVo>> findPage(SysUsersQuery sysUsersQuery) {
        log.info("分页查询用户信息：{}", sysUsersQuery);
        Pageable pageable = PageRequest.of(sysUsersQuery.getPage(), sysUsersQuery.getPageSize(), Sort.by("id").descending());

        Specification<SysUsers> spec = queryHelp.getWhere(sysUsersQuery, (root, cb) -> {

            List<Predicate> predicates = new ArrayList<>();
            tenantAccessSupport.applyTenantPredicate(predicates, root, cb);
            if (StringUtils.hasText(sysUsersQuery.getUsername())) {
                predicates.add(cb.like(root.get("username"), "%" + sysUsersQuery.getUsername() + "%"));
            }
            if (StringUtils.hasText(sysUsersQuery.getNickname())) {
                predicates.add(cb.like(root.get("nickname"), "%" + sysUsersQuery.getNickname() + "%"));
            }
            if (StringUtils.hasText(sysUsersQuery.getEmail())) {
                predicates.add(cb.equal(root.get("email"), sysUsersQuery.getEmail()));
            }
            if (StringUtils.hasText(sysUsersQuery.getPhone())) {
                predicates.add(cb.equal(root.get("phone"), sysUsersQuery.getPhone()));
            }
            if (sysUsersQuery.getGender() != null) {
                predicates.add(cb.equal(root.get("gender"), sysUsersQuery.getGender()));
            }
            return predicates;
        } );

        Page<SysUsers> page = sysUsersRepository.findAll(spec, pageable);
        List<SysUsersVo> list = sysUsersMapper.toVo(page.getContent());

        // 批量获取头像URL
        Set<String> avatarIds = list.stream()
                .map(SysUsersVo::getAvatar)
                .filter(StringUtils::hasText)
                .collect(Collectors.toSet());

        if (!avatarIds.isEmpty()) {
            Set<FileUrlVo> urlVos = fileHandle.getUrls(avatarIds);
            Map<String, String> urlMap = urlVos.stream()
                    .collect(Collectors.toMap(FileUrlVo::getId, FileUrlVo::getUrl));

            list.forEach(vo -> {
                if (StringUtils.hasText(vo.getAvatar())) {
                    vo.setAvatar(urlMap.getOrDefault(vo.getAvatar(), vo.getAvatar()));
                }
            });
        }

        return PageVo.of(page.getTotalElements(), list);
    }

    @Override
    @Transactional(readOnly = true)
    public SysUsersLoginVo getLoginByUsername(String username) {
        SysUsers byUsername = sysUsersRepository.findByUsername(username).orElse(null);
        return sysUsersMapper.toLoginUser(byUsername);
    }

    @Override
    @Transactional(readOnly = true)
    public UserInfoVo getUserInfoById(Long userId) {
        log.info("根据用户ID获取用户信息：{}", userId);
        SysUsers user = sysUsersRepository.findById(userId).orElse(null);
        if (user == null) {
            return null;
        }
        tenantAccessSupport.checkEntityAccess(user, "无权查看当前租户用户");

        UserInfoVo vo = new UserInfoVo();
        vo.setId(user.getId());
        vo.setUsername(user.getUsername());
        vo.setNickname(user.getNickname());

        // 转换头像URL
        if (StringUtils.hasText(user.getAvatar())) {
            String avatarUrl = fileHandle.getUrl(user.getAvatar());
            vo.setAvatar(StringUtils.hasText(avatarUrl) ? avatarUrl : user.getAvatar());
        }

        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePassword(SysUserPasswordUpdate passwordUpdate) {
        log.info("重置用户密码：用户ID={}", passwordUpdate.getId());
        SysUsers users = sysUsersRepository.findById(passwordUpdate.getId())
                .orElseThrow(() -> new BusinessException("用户不存在"));
        tenantAccessSupport.checkEntityAccess(users, "无权修改当前租户用户密码");

        // 加密并设置新密码
        users.setPassword(passwordEncoder.encode(passwordUpdate.getNewPassword()));
        sysUsersRepository.save(users);
    }

    @Override
    @Transactional(readOnly = true)
    public UserProfileVo getUserProfile(Long userId) {
        SysUsers user = sysUsersRepository.findByIdWithRoles(userId)
                .orElseThrow(() -> new BusinessException("用户不存在"));
        tenantAccessSupport.checkEntityAccess(user, "无权查看当前租户用户");
        
        UserProfileVo vo = new UserProfileVo();
        vo.setId(user.getId());
        vo.setUsername(user.getUsername());
        vo.setNickname(user.getNickname());
        vo.setPhone(user.getPhone());
        vo.setEmail(user.getEmail());
        vo.setGender(user.getGender());
        vo.setRemark(user.getRemark());
        
        if (user.getDepartment() != null) {
            vo.setDepartment(user.getDepartment().getName());
        }
        if (user.getPost() != null) {
            vo.setPost(user.getPost().getName());
        }
        if (user.getRoles() != null && !user.getRoles().isEmpty()) {
            vo.setRoles(user.getRoles().stream().map(SysRole::getTitle).collect(Collectors.toList()));
        }
        
        if (StringUtils.hasText(user.getAvatar())) {
            String avatarUrl = fileHandle.getUrl(user.getAvatar());
            vo.setAvatar(StringUtils.hasText(avatarUrl) ? avatarUrl : user.getAvatar());
        }
        
        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUserProfile(Long userId, UserProfileUpdate update) {
        SysUsers user = sysUsersRepository.findById(userId)
                .orElseThrow(() -> new BusinessException("用户不存在"));
        tenantAccessSupport.checkEntityAccess(user, "无权修改当前租户用户");

        if (StringUtils.hasText(update.getPhone()) && sysUsersRepository.existsByPhoneAndIdNot(update.getPhone(), userId)) {
            throw new BusinessException("电话已存在");
        }
        if (StringUtils.hasText(update.getEmail()) && sysUsersRepository.existsByEmailAndIdNot(update.getEmail(), userId)) {
            throw new BusinessException("邮箱已存在");
        }

        user.setNickname(update.getNickname());
        user.setPhone(update.getPhone());
        user.setEmail(update.getEmail());
        user.setGender(update.getGender());
        user.setRemark(update.getRemark());
        
        if (StringUtils.hasText(update.getAvatar())) {
            if (!update.getAvatar().startsWith("http")) {
                user.setAvatar(update.getAvatar());
            }
        }
        sysUsersRepository.save(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateProfilePassword(Long userId, UserProfilePwdUpdate update) {
        SysUsers user = sysUsersRepository.findById(userId)
                .orElseThrow(() -> new BusinessException("用户不存在"));
        tenantAccessSupport.checkEntityAccess(user, "无权修改当前租户用户密码");

        if (!passwordEncoder.matches(update.getOldPassword(), user.getPassword())) {
            throw new BusinessException("旧密码不正确");
        }

        user.setPassword(passwordEncoder.encode(update.getNewPassword()));
        sysUsersRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SysUsersVo> searchUsers(String keyword, Integer limit) {
        log.info("搜索用户信息：keyword={}, limit={}", keyword, limit);
        Pageable pageable = PageRequest.of(0, limit, Sort.by("id").descending());

        Specification<SysUsers> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            tenantAccessSupport.applyTenantPredicate(predicates, root, cb);
            if (StringUtils.hasText(keyword)) {
                predicates.add(cb.or(
                        cb.like(root.get("username"), "%" + keyword + "%"),
                        cb.like(root.get("nickname"), "%" + keyword + "%")
                ));
            }
            return predicates.isEmpty() ? null : cb.and(predicates.toArray(new Predicate[0]));
        };

        Page<SysUsers> page = sysUsersRepository.findAll(spec, pageable);
        return sysUsersMapper.toVo(page.getContent());
    }

    @Override
    @Transactional(readOnly = true)
    public PageVo<List<SysUsersPickerVo>> findPickerPage(SysUsersPickerQuery query) {
        Pageable pageable = PageRequest.of(query.getPage(), query.getPageSize(), Sort.by("id").descending());

        Specification<SysUsers> spec = (root, q, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            tenantAccessSupport.applyTenantPredicate(predicates, root, cb);
            if (StringUtils.hasText(query.getKeyword())) {
                predicates.add(cb.or(
                        cb.like(root.get("username"), "%" + query.getKeyword() + "%"),
                        cb.like(root.get("nickname"), "%" + query.getKeyword() + "%")
                ));
            }
            return predicates.isEmpty() ? null : cb.and(predicates.toArray(new Predicate[0]));
        };

        Page<SysUsers> page = sysUsersRepository.findAll(spec, pageable);
        List<SysUsersPickerVo> list = sysUsersMapper.toPickerVo(page.getContent());
        fillPickerAvatarUrl(list);
        return PageVo.of(page.getTotalElements(), list);
    }

    @Override
    @Transactional(readOnly = true)
    public SysUsersPickerVo getPickerById(Long id) {
        SysUsers user = sysUsersRepository.findById(id).orElse(null);
        if (user == null) {
            return null;
        }
        tenantAccessSupport.checkEntityAccess(user, "无权查看当前租户用户");
        SysUsersPickerVo vo = sysUsersMapper.toPickerVo(user);
        if (vo == null) {
            return null;
        }
        fillPickerAvatarUrl(List.of(vo));
        return vo;
    }

    private void fillPickerAvatarUrl(List<SysUsersPickerVo> list) {
        Set<String> avatarIds = list.stream()
                .map(SysUsersPickerVo::getAvatar)
                .filter(StringUtils::hasText)
                .collect(Collectors.toSet());
        if (avatarIds.isEmpty()) {
            return;
        }
        Set<FileUrlVo> urlVos = fileHandle.getUrls(avatarIds);
        Map<String, String> urlMap = urlVos.stream()
                .collect(Collectors.toMap(FileUrlVo::getId, FileUrlVo::getUrl));
        list.forEach(vo -> {
            if (StringUtils.hasText(vo.getAvatar())) {
                vo.setAvatar(urlMap.getOrDefault(vo.getAvatar(), vo.getAvatar()));
            }
        });
    }
}
