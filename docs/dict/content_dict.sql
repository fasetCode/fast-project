INSERT INTO "public"."sys_dict_type" ("id", "create_by", "create_time", "deleted", "update_by", "update_time", "name", "remark", "status", "type")
VALUES (9600, NULL, NOW(), 0, NULL, NOW(), '内容-类型', 'ContentInfo.type', 1, 'content_type')
ON CONFLICT (id) DO NOTHING;

INSERT INTO "public"."sys_dict_data" ("id", "create_by", "create_time", "deleted", "update_by", "update_time", "dict_type_id", "label", "remark", "sort", "status", "value") VALUES
(960001, NULL, NOW(), 0, NULL, NOW(), 9600, '文章',   '', 1, 1, '1'),
(960002, NULL, NOW(), 0, NULL, NOW(), 9600, '帖子',   '', 2, 1, '2'),
(960003, NULL, NOW(), 0, NULL, NOW(), 9600, '知识库', '', 3, 1, '3')
ON CONFLICT (id) DO NOTHING;


INSERT INTO "public"."sys_dict_type" ("id", "create_by", "create_time", "deleted", "update_by", "update_time", "name", "remark", "status", "type")
VALUES (9601, NULL, NOW(), 0, NULL, NOW(), '内容-发布状态', 'ContentInfo.publishStatus', 1, 'content_publish_status')
ON CONFLICT (id) DO NOTHING;

INSERT INTO "public"."sys_dict_data" ("id", "create_by", "create_time", "deleted", "update_by", "update_time", "dict_type_id", "label", "remark", "sort", "status", "value") VALUES
(960101, NULL, NOW(), 0, NULL, NOW(), 9601, '草稿',   '', 1, 1, '0'),
(960102, NULL, NOW(), 0, NULL, NOW(), 9601, '已发布', '', 2, 1, '1'),
(960103, NULL, NOW(), 0, NULL, NOW(), 9601, '已下线', '', 3, 1, '2')
ON CONFLICT (id) DO NOTHING;


INSERT INTO "public"."sys_dict_type" ("id", "create_by", "create_time", "deleted", "update_by", "update_time", "name", "remark", "status", "type")
VALUES (9602, NULL, NOW(), 0, NULL, NOW(), '内容-审核状态', 'ContentInfo.auditStatus / ContentAuditLog.auditStatus', 1, 'content_audit_status')
ON CONFLICT (id) DO NOTHING;

INSERT INTO "public"."sys_dict_data" ("id", "create_by", "create_time", "deleted", "update_by", "update_time", "dict_type_id", "label", "remark", "sort", "status", "value") VALUES
(960201, NULL, NOW(), 0, NULL, NOW(), 9602, '未提交', '', 1, 1, '0'),
(960202, NULL, NOW(), 0, NULL, NOW(), 9602, '待审核', '', 2, 1, '1'),
(960203, NULL, NOW(), 0, NULL, NOW(), 9602, '通过',   '', 3, 1, '2'),
(960204, NULL, NOW(), 0, NULL, NOW(), 9602, '驳回',   '', 4, 1, '3')
ON CONFLICT (id) DO NOTHING;


INSERT INTO "public"."sys_dict_type" ("id", "create_by", "create_time", "deleted", "update_by", "update_time", "name", "remark", "status", "type")
VALUES (9603, NULL, NOW(), 0, NULL, NOW(), '内容-评论状态', 'ContentComment.status', 1, 'content_comment_status')
ON CONFLICT (id) DO NOTHING;

INSERT INTO "public"."sys_dict_data" ("id", "create_by", "create_time", "deleted", "update_by", "update_time", "dict_type_id", "label", "remark", "sort", "status", "value") VALUES
(960301, NULL, NOW(), 0, NULL, NOW(), 9603, '正常',   '', 1, 1, '1'),
(960302, NULL, NOW(), 0, NULL, NOW(), 9603, '待审核', '', 2, 1, '2'),
(960303, NULL, NOW(), 0, NULL, NOW(), 9603, '屏蔽',   '', 3, 1, '3')
ON CONFLICT (id) DO NOTHING;


INSERT INTO "public"."sys_dict_type" ("id", "create_by", "create_time", "deleted", "update_by", "update_time", "name", "remark", "status", "type")
VALUES (9604, NULL, NOW(), 0, NULL, NOW(), '内容-标签展示类型', 'ContentTag.displayType', 1, 'content_tag_display_type')
ON CONFLICT (id) DO NOTHING;

INSERT INTO "public"."sys_dict_data" ("id", "create_by", "create_time", "deleted", "update_by", "update_time", "dict_type_id", "label", "remark", "sort", "status", "value") VALUES
(960401, NULL, NOW(), 0, NULL, NOW(), 9604, '文字',     '', 1, 1, '1'),
(960402, NULL, NOW(), 0, NULL, NOW(), 9604, '图标+文字', '', 2, 1, '2'),
(960403, NULL, NOW(), 0, NULL, NOW(), 9604, '图片',     '', 3, 1, '3')
ON CONFLICT (id) DO NOTHING;


INSERT INTO "public"."sys_dict_type" ("id", "create_by", "create_time", "deleted", "update_by", "update_time", "name", "remark", "status", "type")
VALUES (9605, NULL, NOW(), 0, NULL, NOW(), '内容-评论点赞操作', 'ContentCommentLike.action', 1, 'content_comment_like_action')
ON CONFLICT (id) DO NOTHING;

INSERT INTO "public"."sys_dict_data" ("id", "create_by", "create_time", "deleted", "update_by", "update_time", "dict_type_id", "label", "remark", "sort", "status", "value") VALUES
(960501, NULL, NOW(), 0, NULL, NOW(), 9605, '点赞', '', 1, 1, '1'),
(960502, NULL, NOW(), 0, NULL, NOW(), 9605, '点踩', '', 2, 1, '2')
ON CONFLICT (id) DO NOTHING;


INSERT INTO "public"."sys_dict_type" ("id", "create_by", "create_time", "deleted", "update_by", "update_time", "name", "remark", "status", "type")
VALUES (9606, NULL, NOW(), 0, NULL, NOW(), '内容-举报目标类型', 'ContentReport.targetType', 1, 'content_report_target_type')
ON CONFLICT (id) DO NOTHING;

INSERT INTO "public"."sys_dict_data" ("id", "create_by", "create_time", "deleted", "update_by", "update_time", "dict_type_id", "label", "remark", "sort", "status", "value") VALUES
(960601, NULL, NOW(), 0, NULL, NOW(), 9606, '内容', '', 1, 1, '1'),
(960602, NULL, NOW(), 0, NULL, NOW(), 9606, '评论', '', 2, 1, '2')
ON CONFLICT (id) DO NOTHING;


INSERT INTO "public"."sys_dict_type" ("id", "create_by", "create_time", "deleted", "update_by", "update_time", "name", "remark", "status", "type")
VALUES (9607, NULL, NOW(), 0, NULL, NOW(), '内容-举报原因类型', 'ContentReport.reasonType', 1, 'content_report_reason_type')
ON CONFLICT (id) DO NOTHING;

INSERT INTO "public"."sys_dict_data" ("id", "create_by", "create_time", "deleted", "update_by", "update_time", "dict_type_id", "label", "remark", "sort", "status", "value") VALUES
(960701, NULL, NOW(), 0, NULL, NOW(), 9607, '垃圾广告', '', 1, 1, '1'),
(960702, NULL, NOW(), 0, NULL, NOW(), 9607, '辱骂攻击', '', 2, 1, '2'),
(960703, NULL, NOW(), 0, NULL, NOW(), 9607, '涉黄低俗', '', 3, 1, '3'),
(960704, NULL, NOW(), 0, NULL, NOW(), 9607, '涉政敏感', '', 4, 1, '4'),
(960705, NULL, NOW(), 0, NULL, NOW(), 9607, '其它',     '', 5, 1, '99')
ON CONFLICT (id) DO NOTHING;


INSERT INTO "public"."sys_dict_type" ("id", "create_by", "create_time", "deleted", "update_by", "update_time", "name", "remark", "status", "type")
VALUES (9608, NULL, NOW(), 0, NULL, NOW(), '内容-举报处理状态', 'ContentReport.status', 1, 'content_report_status')
ON CONFLICT (id) DO NOTHING;

INSERT INTO "public"."sys_dict_data" ("id", "create_by", "create_time", "deleted", "update_by", "update_time", "dict_type_id", "label", "remark", "sort", "status", "value") VALUES
(960801, NULL, NOW(), 0, NULL, NOW(), 9608, '待处理', '', 1, 1, '1'),
(960802, NULL, NOW(), 0, NULL, NOW(), 9608, '已处理', '', 2, 1, '2'),
(960803, NULL, NOW(), 0, NULL, NOW(), 9608, '已驳回', '', 3, 1, '3')
ON CONFLICT (id) DO NOTHING;

