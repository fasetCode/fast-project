package com.fastproject.module.chat.service;

import com.fastproject.module.chat.domain.CustomerUser;
import com.fastproject.utils.vo.PageVo;
import com.fastproject.utils.vo.ResultVo;

import java.util.List;

public interface CustomerUserService {

    /**
     * 保存客服用户
     */
    Long save(CustomerUser customerUser);

    /**
     * 更新客服用户
     */
    void update(CustomerUser customerUser);

    /**
     * 删除客服用户
     */
    void delete(Long id);

    /**
     * 批量删除客服用户
     */
    void batchDelete(List<Long> ids);

    /**
     * 根据ID查询客服用户
     */
    CustomerUser findById(Long id);

    /**
     * 分页查询客服用户
     */
    PageVo<List<CustomerUser>> findPage(int page, int pageSize);

    /**
     * 统计客服用户数量
     */
    long count();
}
