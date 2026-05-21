package com.fastproject.ratelimit.service;

import com.fastproject.ratelimit.vo.userbw.UserBlackWhiteListCreate;
import com.fastproject.ratelimit.vo.userbw.UserBlackWhiteListQuery;
import com.fastproject.ratelimit.vo.userbw.UserBlackWhiteListUpdate;
import com.fastproject.ratelimit.vo.userbw.UserBlackWhiteListVo;
import com.fastproject.utils.vo.PageVo;

import java.util.List;

/**
 * 用户黑白名单配置 Service 接口
 */
public interface UserBlackWhiteListService {

    /**
     * 添加
     */
    Long save(UserBlackWhiteListCreate create);

    /**
     * 修改
     */
    void update(UserBlackWhiteListUpdate update);

    /**
     * 删除
     */
    void delete(Long id);

    /**
     * 批量删除
     */
    void batchDelete(List<Long> ids);

    /**
     * 根据ID查询
     */
    UserBlackWhiteListVo findById(Long id);

    /**
     * 分页查询
     */
    PageVo<List<UserBlackWhiteListVo>> findPage(UserBlackWhiteListQuery query);

    /**
     * 检查用户是否在名单中
     * @return 匹配的名单配置，如果没有匹配则返回null
     */
    UserBlackWhiteListVo checkUser(String appCode, Long userId);
}
