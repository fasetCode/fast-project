package com.fastproject.mall.domain;

import com.fastproject.db.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;

/**
 * 商城 - 购物车明细
 */
@Table(name = "mall_cart")
@Getter
@Setter
@Entity
@SQLDelete(sql = "update mall_cart set deleted = 1 where id = ?")
@SQLRestriction("deleted = 0")
public class MallCart extends BaseEntity {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 店铺ID
     */
    private Long shopId;

    /**
     * 商品ID
     */
    private Long productId;

    /**
     * SKU ID（如启用 SKU；无 SKU 时可为空）
     */
    private Long skuId;

    /**
     * 商品快照（JSON 字符串）
     */
    @Column(columnDefinition = "TEXT")
    private String productSnapshot;

    /**
     * SKU 快照（JSON 字符串）
     */
    @Column(columnDefinition = "TEXT")
    private String skuSnapshot;

    /**
     * 加入购物车时的单价
     */
    @Column(precision = 12, scale = 2)
    private BigDecimal price;

    /**
     * 数量
     */
    private Integer quantity;

    /**
     * 是否选中（结算用）0 未选中 1 已选中
     */
    private Integer selected;

}
