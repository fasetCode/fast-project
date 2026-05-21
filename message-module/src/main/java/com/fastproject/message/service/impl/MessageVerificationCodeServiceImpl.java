package com.fastproject.message.service.impl;

import com.fastproject.db.QueryHelp;
import com.fastproject.exception.BusinessException;
import com.fastproject.message.domain.MessageVerificationCode;
import com.fastproject.message.enums.MessageVerificationCodeStatusEnum;
import com.fastproject.message.mapper.MessageVerificationCodeMapper;
import com.fastproject.message.repository.db.MessageVerificationCodeRepository;
import com.fastproject.message.service.MessageVerificationCodeService;
import com.fastproject.message.vo.verificationcode.MessageVerificationCodeUpdate;
import com.fastproject.message.vo.verificationcode.MessageVerificationCodeCreate;
import com.fastproject.message.vo.verificationcode.MessageVerificationCodeQuery;
import com.fastproject.message.vo.verificationcode.MessageVerificationCodeVo;
import com.fastproject.utils.vo.PageVo;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageVerificationCodeServiceImpl implements MessageVerificationCodeService {

    private final MessageVerificationCodeRepository messageVerificationCodeRepository;
    private final QueryHelp<MessageVerificationCode> queryHelp;
    private final MessageVerificationCodeMapper messageVerificationCodeMapper;

    @Override
    public Long save(MessageVerificationCodeCreate create) {
        log.info("保存验证码：{}", create);

        MessageVerificationCode verificationCode = messageVerificationCodeMapper.toVerificationCode(create);
        messageVerificationCodeRepository.save(verificationCode);
        return verificationCode.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(MessageVerificationCodeUpdate update) {
        log.info("修改验证码：{}", update);
        MessageVerificationCode verificationCode = messageVerificationCodeRepository.findById(update.getId())
                .orElseThrow(() -> new BusinessException("验证码不存在"));

        messageVerificationCodeMapper.updateVerificationCodeFromDto(update, verificationCode);
        messageVerificationCodeRepository.save(verificationCode);
    }

    @Override
    public void delete(Long id) {
        log.info("删除验证码：{}", id);
        if (!messageVerificationCodeRepository.existsById(id)) {
            throw new BusinessException("验证码不存在");
        }
        messageVerificationCodeRepository.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(List<Long> ids) {
        log.info("批量删除验证码：{}", ids);
        messageVerificationCodeRepository.deleteAllById(ids);
    }

    @Override
    public MessageVerificationCodeVo findById(Long id) {
        log.info("根据ID查询验证码：{}", id);
        return messageVerificationCodeRepository.findById(id)
                .map(messageVerificationCodeMapper::toVo)
                .orElse(null);
    }

    @Override
    public PageVo<List<MessageVerificationCodeVo>> findPage(MessageVerificationCodeQuery query) {
        log.info("分页查询验证码：{}", query);
        Pageable pageable = PageRequest.of(query.getPage(), query.getPageSize(), Sort.by("createTime").descending());

        Specification<MessageVerificationCode> spec = queryHelp.getWhere(query, (root, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(query.getCode())) {
                predicates.add(cb.like(root.get("code"), "%" + query.getCode() + "%"));
            }
            if (StringUtils.hasText(query.getTarget())) {
                predicates.add(cb.like(root.get("target"), "%" + query.getTarget() + "%"));
            }
            if (query.getConfigId() != null) {
                predicates.add(cb.equal(root.get("configId"), query.getConfigId()));
            }
            if (query.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), query.getStatus()));
            }
            return predicates;
        });

        Page<MessageVerificationCode> page = messageVerificationCodeRepository.findAll(spec, pageable);
        List<MessageVerificationCodeVo> list = messageVerificationCodeMapper.toVo(page.getContent());

        return PageVo.of(page.getTotalElements(), list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean verify(String target, String code) {
        log.info("校验验证码：target={}, code={}", target, code);
        return messageVerificationCodeRepository.findFirstByTargetAndCodeAndStatusOrderByCreateTimeDesc(
                target, code, MessageVerificationCodeStatusEnum.VALID.getCode()
        ).map(verificationCode -> {
            // 校验过期时间
            if (verificationCode.getExpireTime() != null && verificationCode.getExpireTime() < System.currentTimeMillis()) {
                verificationCode.setStatus(MessageVerificationCodeStatusEnum.EXPIRED.getCode());
                messageVerificationCodeRepository.save(verificationCode);
                return false;
            }
            // 校验通过，标记为已使用
            verificationCode.setStatus(MessageVerificationCodeStatusEnum.USED.getCode());
            messageVerificationCodeRepository.save(verificationCode);
            return true;
        }).orElse(false);
    }
}