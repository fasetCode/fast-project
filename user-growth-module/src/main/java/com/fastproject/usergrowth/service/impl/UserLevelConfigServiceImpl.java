package com.fastproject.usergrowth.service.impl;

import com.fastproject.db.QueryHelp;
import com.fastproject.exception.BusinessException;
import com.fastproject.usergrowth.domain.UserLevelConfig;
import com.fastproject.usergrowth.mapper.UserLevelConfigMapper;
import com.fastproject.usergrowth.repository.db.UserLevelConfigRepository;
import com.fastproject.usergrowth.service.UserLevelConfigService;
import com.fastproject.usergrowth.vo.levelconfig.*;
import com.fastproject.utils.enums.StatusEnum;
import com.fastproject.utils.XssUtil;
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

@Service
@Slf4j
@RequiredArgsConstructor
public class UserLevelConfigServiceImpl implements UserLevelConfigService {

    private final UserLevelConfigRepository repository;
    private final QueryHelp<UserLevelConfig> queryHelp;
    private final UserLevelConfigMapper mapper;

    @Override
    public Long save(UserLevelConfigCreate create) {
        log.info("保存信息：{}", create);
        create.setDescription(XssUtil.clean(create.getDescription()));
        UserLevelConfig entity = mapper.toEntity(create);
        repository.save(entity);
        return entity.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(UserLevelConfigUpdate update) {
        log.info("修改信息：{}", update);
        update.setDescription(XssUtil.clean(update.getDescription()));
        System.out.println(update.getDescription());
        UserLevelConfig entity = repository.findById(update.getId())
                .orElseThrow(() -> new BusinessException("数据不存在"));
        mapper.updateFromDto(update, entity);
        repository.save(entity);
    }

    @Override
    public void delete(Long id) {
        log.info("删除信息：{}", id);
        repository.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(List<Long> ids) {
        log.info("批量删除信息：{}", ids);
        repository.deleteAllById(ids);
    }

    @Override
    public UserLevelConfigVo findById(Long id) {
        log.info("根据ID查询信息：{}", id);
        return repository.findById(id).map(mapper::toVo).orElse(null);
    }

    @Override
    public PageVo<List<UserLevelConfigVo>> findPage(UserLevelConfigQuery query) {
        log.info("分页查询信息：{}", query);
        Pageable pageable = PageRequest.of(query.getPage(), query.getPageSize(), Sort.by("id").descending());

        Specification<UserLevelConfig> spec = queryHelp.getWhere(query, (root, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (org.springframework.util.StringUtils.hasText(query.getTitle())) {
                predicates.add(cb.like(root.get("title"), "%" + query.getTitle() + "%"));
            }
            if (org.springframework.util.StringUtils.hasText(query.getIcon())) {
                predicates.add(cb.like(root.get("icon"), "%" + query.getIcon() + "%"));
            }
            if (query.getGrowthValue() != null) {
                predicates.add(cb.equal(root.get("growthValue"), query.getGrowthValue()));
            }
            if (query.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), query.getStatus()));
            }
            if (org.springframework.util.StringUtils.hasText(query.getBackground())) {
                predicates.add(cb.like(root.get("background"), "%" + query.getBackground() + "%"));
            }
            if (org.springframework.util.StringUtils.hasText(query.getColor())) {
                predicates.add(cb.like(root.get("color"), "%" + query.getColor() + "%"));
            }
            if (org.springframework.util.StringUtils.hasText(query.getDescription())) {
                predicates.add(cb.like(root.get("description"), "%" + query.getDescription() + "%"));
            }
            return predicates;
        });

        Page<UserLevelConfig> page = repository.findAll(spec, pageable);
        List<UserLevelConfigVo> list = mapper.toVo(page.getContent());

        return PageVo.of(page.getTotalElements(), list);
    }

    @Override
    public QueryLevelConfigVo getAccountByGrowthValue(Long growthValue) {
        if (growthValue == null) {
            growthValue = 0L;
        }
        List<UserLevelConfig> list = repository.findByStatusOrderByGrowthValueAsc(StatusEnum.NORMAL.getCode());
        List<UserLevelConfigVo> configVos = mapper.toVo(list);

        if (configVos == null || configVos.isEmpty()) {
            return null;
        }
        QueryLevelConfigVo vo = new QueryLevelConfigVo();
        for (int i = 0; i < configVos.size(); i++) {
            UserLevelConfigVo configVo = configVos.get(i);
            if (growthValue >= configVo.getGrowthValue()) {
                vo.setLevelConfigVo(configVo);
            } else {
                // 找到第一个大于当前成长值的等级，即为下一等级
                vo.setNextLevelConfigVo(configVo);
                break;
            }
        }
        return vo;
    }
}
