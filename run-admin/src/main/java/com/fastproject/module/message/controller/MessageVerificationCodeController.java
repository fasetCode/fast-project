package com.fastproject.module.message.controller;

import com.fastproject.message.service.MessageVerificationCodeService;
import com.fastproject.message.vo.verificationcode.MessageVerificationCodeQuery;
import com.fastproject.message.vo.verificationcode.MessageVerificationCodeVo;
import com.fastproject.utils.vo.PageVo;
import com.fastproject.utils.vo.ResultVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 验证码 Controller
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/message/verificationCode")
public class MessageVerificationCodeController {

    private final MessageVerificationCodeService messageVerificationCodeService;

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:message:verificationCode:delete')")
    public ResultVo<Object> delete(@PathVariable Long id) {
        messageVerificationCodeService.delete(id);
        return ResultVo.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/batch")
    @PreAuthorize("@ps.hasPermission('admin:message:verificationCode:delete')")
    public ResultVo<Object> batchDelete(@RequestBody List<Long> ids) {
        messageVerificationCodeService.batchDelete(ids);
        return ResultVo.success();
    }

    /**
     * 分页
     */
    @PostMapping("/page")
    @PreAuthorize("@ps.hasPermission('admin:message:verificationCode:page')")
    public ResultVo<PageVo<List<MessageVerificationCodeVo>>> page(@RequestBody MessageVerificationCodeQuery query) {
        return ResultVo.success(messageVerificationCodeService.findPage(query));
    }

    /**
     * 详情
     */
    @GetMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:message:verificationCode:page')")
    public ResultVo<MessageVerificationCodeVo> get(@PathVariable Long id) {
        return ResultVo.success(messageVerificationCodeService.findById(id));
    }
}