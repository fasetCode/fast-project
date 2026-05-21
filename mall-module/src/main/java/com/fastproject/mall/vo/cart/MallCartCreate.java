package com.fastproject.mall.vo.cart;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 购物车创建 VO
 */
@Data
public class MallCartCreate {

    /**
     * 用户ID
     */
    @NotNull(message = "用户ID不能为空")
    private Long userId;

    /**
     * 店铺ID
     */
    private Long shopId;

    /**
     * 商品ID
     */
    @NotNull(message = "商品ID不能为空")
    private Long productId;

    /**
     * SKU ID（如启用 SKU；无 SKU 时可为空）
     */
    private Long skuId;

    /**
     * 商品快照（JSON 字符串）
     */
    private String productSnapshot;

    /**
     * SKU 快照（JSON 字符串）
     */
    private String skuSnapshot;

    /**
     * 加入购物车时的单价
     */
    private BigDecimal price;

    /**
     * 数量
     */
    @NotNull(message = "数量不能为空")
    private Integer quantity;

    /**
     * 是否选中（结算用）0 未选中 1 已选中
     */
    private Integer selected;
}
