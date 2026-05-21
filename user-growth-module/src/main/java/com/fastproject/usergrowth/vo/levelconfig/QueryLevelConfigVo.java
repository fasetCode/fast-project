package com.fastproject.usergrowth.vo.levelconfig;

import com.fastproject.usergrowth.api.UserGrowthApi;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class QueryLevelConfigVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 当前等级
     */
    private UserLevelConfigVo levelConfigVo;

    /**
     * 下一等级
     */
    private UserLevelConfigVo nextLevelConfigVo;

}
