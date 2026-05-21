package com.fastproject.ratelimit.service;

import com.fastproject.ratelimit.vo.ipbw.IpBlackWhiteListCreate;
import com.fastproject.ratelimit.vo.ipbw.IpBlackWhiteListQuery;
import com.fastproject.ratelimit.vo.ipbw.IpBlackWhiteListUpdate;
import com.fastproject.ratelimit.vo.ipbw.IpBlackWhiteListVo;
import com.fastproject.utils.vo.PageVo;

import java.util.List;

/**
 * IP黑白名单配置 Service 接口
 */
public interface IpBlackWhiteListService {

    /**
     * 添加
     */
    Long save(IpBlackWhiteListCreate create);

    /**
     * 修改
     */
    void update(IpBlackWhiteListUpdate update);

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
    IpBlackWhiteListVo findById(Long id);

    /**
     * 分页查询
     */
    PageVo<List<IpBlackWhiteListVo>> findPage(IpBlackWhiteListQuery query);

    /**
     * 检查IP是否在名单中
     * @return 匹配的名单配置，如果没有匹配则返回null
     */
    IpBlackWhiteListVo checkIp(String appCode, String ip);
}
