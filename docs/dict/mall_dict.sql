-- ============================================================================
-- 商城模块 - 字典初始化 SQL（PostgreSQL）
--
-- 复用：sys_dict_type.type = 'status'（1 正常 / 2 冻结），用于 MallShop / MallProductCategory /
--       MallProductBrand / MallPickupShop / MallFreightTemplate 的 status 字段。
--
-- ID 段：sys_dict_type 9501 ~ 9515；sys_dict_data 95xxxx
-- 幂等：所有 INSERT 均带 ON CONFLICT(id) DO NOTHING
-- ============================================================================


-- ----------------------------------------------------------------------------
-- 0. 通用 yes_no 字典（0 否 / 1 是）—— 用于 is_new/is_hot/is_recommend/show_in_home/is_default/commented/selected
-- ----------------------------------------------------------------------------
INSERT INTO "public"."sys_dict_type" ("id", "create_by", "create_time", "deleted", "update_by", "update_time", "name", "remark", "status", "type")
VALUES (9500, NULL, NOW(), 0, NULL, NOW(), '通用-是否', '通用是/否字典', 1, 'yes_no')
ON CONFLICT (id) DO NOTHING;

INSERT INTO "public"."sys_dict_data" ("id", "create_by", "create_time", "deleted", "update_by", "update_time", "dict_type_id", "label", "remark", "sort", "status", "value") VALUES
(950001, NULL, NOW(), 0, NULL, NOW(), 9500, '否', '', 1, 1, '0'),
(950002, NULL, NOW(), 0, NULL, NOW(), 9500, '是', '', 2, 1, '1')
ON CONFLICT (id) DO NOTHING;


-- ----------------------------------------------------------------------------
-- 1. mall_pay_type —— 商品/订单行 支付方式（1 现金 / 2 积分 / 3 现金+积分）
--    使用：MallOrderItem.payType, MallProductSku.payType
-- ----------------------------------------------------------------------------
INSERT INTO "public"."sys_dict_type" ("id", "create_by", "create_time", "deleted", "update_by", "update_time", "name", "remark", "status", "type")
VALUES (9501, NULL, NOW(), 0, NULL, NOW(), '商城-支付方式', '现金/积分/混合', 1, 'mall_pay_type')
ON CONFLICT (id) DO NOTHING;

INSERT INTO "public"."sys_dict_data" ("id", "create_by", "create_time", "deleted", "update_by", "update_time", "dict_type_id", "label", "remark", "sort", "status", "value") VALUES
(950101, NULL, NOW(), 0, NULL, NOW(), 9501, '现金',     '', 1, 1, '1'),
(950102, NULL, NOW(), 0, NULL, NOW(), 9501, '积分',     '', 2, 1, '2'),
(950103, NULL, NOW(), 0, NULL, NOW(), 9501, '现金+积分', '', 3, 1, '3')
ON CONFLICT (id) DO NOTHING;


-- ----------------------------------------------------------------------------
-- 2. mall_pay_channel —— 订单支付渠道（1 微信 / 2 支付宝 / 3 余额 / 4 其它）
--    使用：MallOrder.payType
-- ----------------------------------------------------------------------------
INSERT INTO "public"."sys_dict_type" ("id", "create_by", "create_time", "deleted", "update_by", "update_time", "name", "remark", "status", "type")
VALUES (9502, NULL, NOW(), 0, NULL, NOW(), '商城-支付渠道', '微信/支付宝/余额/其它', 1, 'mall_pay_channel')
ON CONFLICT (id) DO NOTHING;

INSERT INTO "public"."sys_dict_data" ("id", "create_by", "create_time", "deleted", "update_by", "update_time", "dict_type_id", "label", "remark", "sort", "status", "value") VALUES
(950201, NULL, NOW(), 0, NULL, NOW(), 9502, '微信',   '', 1, 1, '1'),
(950202, NULL, NOW(), 0, NULL, NOW(), 9502, '支付宝', '', 2, 1, '2'),
(950203, NULL, NOW(), 0, NULL, NOW(), 9502, '余额',   '', 3, 1, '3'),
(950204, NULL, NOW(), 0, NULL, NOW(), 9502, '其它',   '', 4, 1, '4')
ON CONFLICT (id) DO NOTHING;


-- ----------------------------------------------------------------------------
-- 3. mall_delivery_type —— 配送方式（1 物流 / 2 无需物流 / 3 到店自提 / 4 同城配送）
--    使用：MallOrderItem.deliveryType
-- ----------------------------------------------------------------------------
INSERT INTO "public"."sys_dict_type" ("id", "create_by", "create_time", "deleted", "update_by", "update_time", "name", "remark", "status", "type")
VALUES (9503, NULL, NOW(), 0, NULL, NOW(), '商城-配送方式', '物流/无需物流/自提/同城', 1, 'mall_delivery_type')
ON CONFLICT (id) DO NOTHING;

INSERT INTO "public"."sys_dict_data" ("id", "create_by", "create_time", "deleted", "update_by", "update_time", "dict_type_id", "label", "remark", "sort", "status", "value") VALUES
(950301, NULL, NOW(), 0, NULL, NOW(), 9503, '物流',     '', 1, 1, '1'),
(950302, NULL, NOW(), 0, NULL, NOW(), 9503, '无需物流', '', 2, 1, '2'),
(950303, NULL, NOW(), 0, NULL, NOW(), 9503, '到店自提', '', 3, 1, '3'),
(950304, NULL, NOW(), 0, NULL, NOW(), 9503, '同城配送', '', 4, 1, '4')
ON CONFLICT (id) DO NOTHING;


-- ----------------------------------------------------------------------------
-- 4. mall_virtual_type —— 虚拟商品类型
--    使用：MallOrderItem.virtualType
-- ----------------------------------------------------------------------------
INSERT INTO "public"."sys_dict_type" ("id", "create_by", "create_time", "deleted", "update_by", "update_time", "name", "remark", "status", "type")
VALUES (9504, NULL, NOW(), 0, NULL, NOW(), '商城-虚拟商品类型', '', 1, 'mall_virtual_type')
ON CONFLICT (id) DO NOTHING;

INSERT INTO "public"."sys_dict_data" ("id", "create_by", "create_time", "deleted", "update_by", "update_time", "dict_type_id", "label", "remark", "sort", "status", "value") VALUES
(950401, NULL, NOW(), 0, NULL, NOW(), 9504, '卡密',     '', 1, 1, '1'),
(950402, NULL, NOW(), 0, NULL, NOW(), 9504, '充值码',   '', 2, 1, '2'),
(950403, NULL, NOW(), 0, NULL, NOW(), 9504, '兑换券',   '', 3, 1, '3'),
(950404, NULL, NOW(), 0, NULL, NOW(), 9504, '线上服务', '', 4, 1, '4'),
(950405, NULL, NOW(), 0, NULL, NOW(), 9504, '会员权益', '', 5, 1, '5')
ON CONFLICT (id) DO NOTHING;


-- ----------------------------------------------------------------------------
-- 5. mall_product_status —— 商品上架状态（1 上架 / 2 下架）
--    使用：MallProduct.status（与通用 status 语义不同，独立字典）
-- ----------------------------------------------------------------------------
INSERT INTO "public"."sys_dict_type" ("id", "create_by", "create_time", "deleted", "update_by", "update_time", "name", "remark", "status", "type")
VALUES (9505, NULL, NOW(), 0, NULL, NOW(), '商城-商品状态', '上架/下架', 1, 'mall_product_status')
ON CONFLICT (id) DO NOTHING;

INSERT INTO "public"."sys_dict_data" ("id", "create_by", "create_time", "deleted", "update_by", "update_time", "dict_type_id", "label", "remark", "sort", "status", "value") VALUES
(950501, NULL, NOW(), 0, NULL, NOW(), 9505, '上架', '', 1, 1, '1'),
(950502, NULL, NOW(), 0, NULL, NOW(), 9505, '下架', '', 2, 1, '2')
ON CONFLICT (id) DO NOTHING;


-- ----------------------------------------------------------------------------
-- 6. mall_order_status —— 订单状态
--    0 待付款 / 1 待发货 / 2 待收货 / 3 已完成 / 4 已取消 / 5 退款中 / 6 已退款
--    使用：MallOrder.status, MallOrderLog.fromStatus, MallOrderLog.toStatus
-- ----------------------------------------------------------------------------
INSERT INTO "public"."sys_dict_type" ("id", "create_by", "create_time", "deleted", "update_by", "update_time", "name", "remark", "status", "type")
VALUES (9506, NULL, NOW(), 0, NULL, NOW(), '商城-订单状态', '', 1, 'mall_order_status')
ON CONFLICT (id) DO NOTHING;

INSERT INTO "public"."sys_dict_data" ("id", "create_by", "create_time", "deleted", "update_by", "update_time", "dict_type_id", "label", "remark", "sort", "status", "value") VALUES
(950601, NULL, NOW(), 0, NULL, NOW(), 9506, '待付款', '', 1, 1, '0'),
(950602, NULL, NOW(), 0, NULL, NOW(), 9506, '待发货', '', 2, 1, '1'),
(950603, NULL, NOW(), 0, NULL, NOW(), 9506, '待收货', '', 3, 1, '2'),
(950604, NULL, NOW(), 0, NULL, NOW(), 9506, '已完成', '', 4, 1, '3'),
(950605, NULL, NOW(), 0, NULL, NOW(), 9506, '已取消', '', 5, 1, '4'),
(950606, NULL, NOW(), 0, NULL, NOW(), 9506, '退款中', '', 6, 1, '5'),
(950607, NULL, NOW(), 0, NULL, NOW(), 9506, '已退款', '', 7, 1, '6')
ON CONFLICT (id) DO NOTHING;


-- ----------------------------------------------------------------------------
-- 7. mall_order_source —— 订单来源（1 PC / 2 H5 / 3 小程序 / 4 APP / 5 后台 / 6 系统）
--    使用：MallOrder.sourceType, MallOrderLog.sourceType
-- ----------------------------------------------------------------------------
INSERT INTO "public"."sys_dict_type" ("id", "create_by", "create_time", "deleted", "update_by", "update_time", "name", "remark", "status", "type")
VALUES (9507, NULL, NOW(), 0, NULL, NOW(), '商城-订单来源', '', 1, 'mall_order_source')
ON CONFLICT (id) DO NOTHING;

INSERT INTO "public"."sys_dict_data" ("id", "create_by", "create_time", "deleted", "update_by", "update_time", "dict_type_id", "label", "remark", "sort", "status", "value") VALUES
(950701, NULL, NOW(), 0, NULL, NOW(), 9507, 'PC',     '', 1, 1, '1'),
(950702, NULL, NOW(), 0, NULL, NOW(), 9507, 'H5',     '', 2, 1, '2'),
(950703, NULL, NOW(), 0, NULL, NOW(), 9507, '小程序', '', 3, 1, '3'),
(950704, NULL, NOW(), 0, NULL, NOW(), 9507, 'APP',    '', 4, 1, '4'),
(950705, NULL, NOW(), 0, NULL, NOW(), 9507, '后台',   '', 5, 1, '5'),
(950706, NULL, NOW(), 0, NULL, NOW(), 9507, '系统',   '', 6, 1, '6')
ON CONFLICT (id) DO NOTHING;


-- ----------------------------------------------------------------------------
-- 8. mall_item_refund_status —— 订单行退款状态（0 未退款 / 1 退款中 / 2 已退款 / 3 退款失败）
--    使用：MallOrderItem.refundStatus（行级当前态指示）
-- ----------------------------------------------------------------------------
INSERT INTO "public"."sys_dict_type" ("id", "create_by", "create_time", "deleted", "update_by", "update_time", "name", "remark", "status", "type")
VALUES (9508, NULL, NOW(), 0, NULL, NOW(), '商城-订单行退款状态', '', 1, 'mall_item_refund_status')
ON CONFLICT (id) DO NOTHING;

INSERT INTO "public"."sys_dict_data" ("id", "create_by", "create_time", "deleted", "update_by", "update_time", "dict_type_id", "label", "remark", "sort", "status", "value") VALUES
(950801, NULL, NOW(), 0, NULL, NOW(), 9508, '未退款',   '', 1, 1, '0'),
(950802, NULL, NOW(), 0, NULL, NOW(), 9508, '退款中',   '', 2, 1, '1'),
(950803, NULL, NOW(), 0, NULL, NOW(), 9508, '已退款',   '', 3, 1, '2'),
(950804, NULL, NOW(), 0, NULL, NOW(), 9508, '退款失败', '', 4, 1, '3')
ON CONFLICT (id) DO NOTHING;


-- ----------------------------------------------------------------------------
-- 9. mall_refund_type —— 退款单类型（1 仅退款 / 2 退货退款 / 3 换货）
--    使用：MallOrderRefund.refundType
-- ----------------------------------------------------------------------------
INSERT INTO "public"."sys_dict_type" ("id", "create_by", "create_time", "deleted", "update_by", "update_time", "name", "remark", "status", "type")
VALUES (9509, NULL, NOW(), 0, NULL, NOW(), '商城-退款类型', '', 1, 'mall_refund_type')
ON CONFLICT (id) DO NOTHING;

INSERT INTO "public"."sys_dict_data" ("id", "create_by", "create_time", "deleted", "update_by", "update_time", "dict_type_id", "label", "remark", "sort", "status", "value") VALUES
(950901, NULL, NOW(), 0, NULL, NOW(), 9509, '仅退款',   '', 1, 1, '1'),
(950902, NULL, NOW(), 0, NULL, NOW(), 9509, '退货退款', '', 2, 1, '2'),
(950903, NULL, NOW(), 0, NULL, NOW(), 9509, '换货',     '', 3, 1, '3')
ON CONFLICT (id) DO NOTHING;


-- ----------------------------------------------------------------------------
-- 10. mall_refund_apply_status —— 退款单状态
--     1 待审核 / 2 审核通过待打款 / 3 退货中 / 4 已完成 / 5 已拒绝 / 6 已撤销
--     使用：MallOrderRefund.refundStatus
-- ----------------------------------------------------------------------------
INSERT INTO "public"."sys_dict_type" ("id", "create_by", "create_time", "deleted", "update_by", "update_time", "name", "remark", "status", "type")
VALUES (9510, NULL, NOW(), 0, NULL, NOW(), '商城-退款单状态', '', 1, 'mall_refund_apply_status')
ON CONFLICT (id) DO NOTHING;

INSERT INTO "public"."sys_dict_data" ("id", "create_by", "create_time", "deleted", "update_by", "update_time", "dict_type_id", "label", "remark", "sort", "status", "value") VALUES
(951001, NULL, NOW(), 0, NULL, NOW(), 9510, '待审核',         '', 1, 1, '1'),
(951002, NULL, NOW(), 0, NULL, NOW(), 9510, '审核通过待打款', '', 2, 1, '2'),
(951003, NULL, NOW(), 0, NULL, NOW(), 9510, '退货中',         '', 3, 1, '3'),
(951004, NULL, NOW(), 0, NULL, NOW(), 9510, '已完成',         '', 4, 1, '4'),
(951005, NULL, NOW(), 0, NULL, NOW(), 9510, '已拒绝',         '', 5, 1, '5'),
(951006, NULL, NOW(), 0, NULL, NOW(), 9510, '已撤销',         '', 6, 1, '6')
ON CONFLICT (id) DO NOTHING;


-- ----------------------------------------------------------------------------
-- 11. mall_freight_charge_type —— 运费计费方式（1 按件 / 2 按重 / 3 按体积）
--     使用：MallFreightTemplate.chargeType
-- ----------------------------------------------------------------------------
INSERT INTO "public"."sys_dict_type" ("id", "create_by", "create_time", "deleted", "update_by", "update_time", "name", "remark", "status", "type")
VALUES (9511, NULL, NOW(), 0, NULL, NOW(), '商城-运费计费方式', '', 1, 'mall_freight_charge_type')
ON CONFLICT (id) DO NOTHING;

INSERT INTO "public"."sys_dict_data" ("id", "create_by", "create_time", "deleted", "update_by", "update_time", "dict_type_id", "label", "remark", "sort", "status", "value") VALUES
(951101, NULL, NOW(), 0, NULL, NOW(), 9511, '按件',     '', 1, 1, '1'),
(951102, NULL, NOW(), 0, NULL, NOW(), 9511, '按重量',   '', 2, 1, '2'),
(951103, NULL, NOW(), 0, NULL, NOW(), 9511, '按体积',   '', 3, 1, '3')
ON CONFLICT (id) DO NOTHING;


-- ----------------------------------------------------------------------------
-- 12. mall_freight_free_type —— 包邮方式
--     0 不包邮 / 1 全国包邮 / 2 满金额包邮 / 3 满件数包邮 / 4 满重量包邮
--     使用：MallFreightTemplate.freeType
-- ----------------------------------------------------------------------------
INSERT INTO "public"."sys_dict_type" ("id", "create_by", "create_time", "deleted", "update_by", "update_time", "name", "remark", "status", "type")
VALUES (9512, NULL, NOW(), 0, NULL, NOW(), '商城-包邮方式', '', 1, 'mall_freight_free_type')
ON CONFLICT (id) DO NOTHING;

INSERT INTO "public"."sys_dict_data" ("id", "create_by", "create_time", "deleted", "update_by", "update_time", "dict_type_id", "label", "remark", "sort", "status", "value") VALUES
(951201, NULL, NOW(), 0, NULL, NOW(), 9512, '不包邮',     '', 1, 1, '0'),
(951202, NULL, NOW(), 0, NULL, NOW(), 9512, '全国包邮',   '', 2, 1, '1'),
(951203, NULL, NOW(), 0, NULL, NOW(), 9512, '满金额包邮', '', 3, 1, '2'),
(951204, NULL, NOW(), 0, NULL, NOW(), 9512, '满件数包邮', '', 4, 1, '3'),
(951205, NULL, NOW(), 0, NULL, NOW(), 9512, '满重量包邮', '', 5, 1, '4')
ON CONFLICT (id) DO NOTHING;


-- ----------------------------------------------------------------------------
-- 13. mall_log_operator_type —— 订单日志操作人类型（1 用户 / 2 商家 / 3 系统 / 4 管理员）
--     使用：MallOrderLog.operatorType
-- ----------------------------------------------------------------------------
INSERT INTO "public"."sys_dict_type" ("id", "create_by", "create_time", "deleted", "update_by", "update_time", "name", "remark", "status", "type")
VALUES (9513, NULL, NOW(), 0, NULL, NOW(), '商城-日志操作人类型', '', 1, 'mall_log_operator_type')
ON CONFLICT (id) DO NOTHING;

INSERT INTO "public"."sys_dict_data" ("id", "create_by", "create_time", "deleted", "update_by", "update_time", "dict_type_id", "label", "remark", "sort", "status", "value") VALUES
(951301, NULL, NOW(), 0, NULL, NOW(), 9513, '用户',   '', 1, 1, '1'),
(951302, NULL, NOW(), 0, NULL, NOW(), 9513, '商家',   '', 2, 1, '2'),
(951303, NULL, NOW(), 0, NULL, NOW(), 9513, '系统',   '', 3, 1, '3'),
(951304, NULL, NOW(), 0, NULL, NOW(), 9513, '管理员', '', 4, 1, '4')
ON CONFLICT (id) DO NOTHING;


-- ----------------------------------------------------------------------------
-- 14. mall_order_log_action —— 订单日志动作（字符串枚举）
--     使用：MallOrderLog.action
-- ----------------------------------------------------------------------------
INSERT INTO "public"."sys_dict_type" ("id", "create_by", "create_time", "deleted", "update_by", "update_time", "name", "remark", "status", "type")
VALUES (9514, NULL, NOW(), 0, NULL, NOW(), '商城-订单日志动作', '', 1, 'mall_order_log_action')
ON CONFLICT (id) DO NOTHING;

INSERT INTO "public"."sys_dict_data" ("id", "create_by", "create_time", "deleted", "update_by", "update_time", "dict_type_id", "label", "remark", "sort", "status", "value") VALUES
(951401, NULL, NOW(), 0, NULL, NOW(), 9514, '创建订单',     '', 1,  1, 'CREATE'),
(951402, NULL, NOW(), 0, NULL, NOW(), 9514, '支付',         '', 2,  1, 'PAY'),
(951403, NULL, NOW(), 0, NULL, NOW(), 9514, '发货',         '', 3,  1, 'SHIP'),
(951404, NULL, NOW(), 0, NULL, NOW(), 9514, '签收',         '', 4,  1, 'RECEIVE'),
(951405, NULL, NOW(), 0, NULL, NOW(), 9514, '确认完成',     '', 5,  1, 'CONFIRM'),
(951406, NULL, NOW(), 0, NULL, NOW(), 9514, '取消',         '', 6,  1, 'CANCEL'),
(951407, NULL, NOW(), 0, NULL, NOW(), 9514, '关闭',         '', 7,  1, 'CLOSE'),
(951408, NULL, NOW(), 0, NULL, NOW(), 9514, '申请退款',     '', 8,  1, 'REFUND_APPLY'),
(951409, NULL, NOW(), 0, NULL, NOW(), 9514, '退款审核通过', '', 9,  1, 'REFUND_PASS'),
(951410, NULL, NOW(), 0, NULL, NOW(), 9514, '退款被拒',     '', 10, 1, 'REFUND_REJECT'),
(951411, NULL, NOW(), 0, NULL, NOW(), 9514, '退款完成',     '', 11, 1, 'REFUND_DONE'),
(951412, NULL, NOW(), 0, NULL, NOW(), 9514, '评价',         '', 12, 1, 'COMMENT'),
(951413, NULL, NOW(), 0, NULL, NOW(), 9514, '备注',         '', 13, 1, 'REMARK'),
(951414, NULL, NOW(), 0, NULL, NOW(), 9514, '其它',         '', 14, 1, 'OTHER')
ON CONFLICT (id) DO NOTHING;
