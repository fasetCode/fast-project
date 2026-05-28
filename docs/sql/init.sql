/*
 Navicat Premium Dump SQL

 Source Server         : localhost_5432
 Source Server Type    : PostgreSQL
 Source Server Version : 170005 (170005)
 Source Host           : localhost:5432
 Source Catalog        : fast-project
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 170005 (170005)
 File Encoding         : 65001

 Date: 28/05/2026 08:05:11
*/


-- ----------------------------
-- Table structure for content_attachment
-- ----------------------------
DROP TABLE IF EXISTS "public"."content_attachment";
CREATE TABLE "public"."content_attachment" (
  "id" int8 NOT NULL,
  "create_by" int8,
  "create_time" timestamp(6),
  "deleted" int4,
  "update_by" int8,
  "update_time" timestamp(6),
  "content_id" int8,
  "file_id" int8,
  "mime_type" varchar(100) COLLATE "pg_catalog"."default",
  "name" varchar(200) COLLATE "pg_catalog"."default",
  "size" int8,
  "sort" int4,
  "url" varchar(500) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of content_attachment
-- ----------------------------

-- ----------------------------
-- Table structure for content_audit_log
-- ----------------------------
DROP TABLE IF EXISTS "public"."content_audit_log";
CREATE TABLE "public"."content_audit_log" (
  "id" int8 NOT NULL,
  "create_by" int8,
  "create_time" timestamp(6),
  "deleted" int4,
  "update_by" int8,
  "update_time" timestamp(6),
  "audit_by" int8,
  "audit_status" int4,
  "audit_time" timestamp(6),
  "content_id" int8,
  "reason" text COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of content_audit_log
-- ----------------------------

-- ----------------------------
-- Table structure for content_body
-- ----------------------------
DROP TABLE IF EXISTS "public"."content_body";
CREATE TABLE "public"."content_body" (
  "id" int8 NOT NULL,
  "create_by" int8,
  "create_time" timestamp(6),
  "deleted" int4,
  "update_by" int8,
  "update_time" timestamp(6),
  "content" text COLLATE "pg_catalog"."default",
  "content_html" text COLLATE "pg_catalog"."default",
  "content_id" int8,
  "format" varchar(50) COLLATE "pg_catalog"."default",
  "reading_time" int4,
  "word_count" int4
)
;

-- ----------------------------
-- Records of content_body
-- ----------------------------
INSERT INTO "public"."content_body" VALUES (335207226434260992, 1, '2026-05-28 06:07:10.631126', 0, 1, '2026-05-28 06:45:36.124903', 'a bsdfsdf c daaaa efff f aaaaaaa', '<table style="min-width: 75px;"><colgroup><col style="min-width: 25px;"><col style="min-width: 25px;"><col style="min-width: 25px;"></colgroup><tbody><tr><th colspan="1" rowspan="1"><p>a</p></th><th colspan="1" rowspan="1"><p>bsdfsdf</p></th><th colspan="1" rowspan="1"><p>c</p></th></tr><tr><td colspan="1" rowspan="1"><p>daaaa</p></td><td colspan="1" rowspan="1"><p>efff</p></td><td colspan="1" rowspan="1"><p>f</p></td></tr></tbody></table><p></p><p></p><p><a target="_blank" rel="noopener noreferrer" href="sdafsd">aaaaaaa</a></p><img data-img-id="327640694992801792" width="100%">', 335196873533034496, 'html', 1, 32);

-- ----------------------------
-- Table structure for content_category
-- ----------------------------
DROP TABLE IF EXISTS "public"."content_category";
CREATE TABLE "public"."content_category" (
  "id" int8 NOT NULL,
  "create_by" int8,
  "create_time" timestamp(6),
  "deleted" int4,
  "update_by" int8,
  "update_time" timestamp(6),
  "name" varchar(100) COLLATE "pg_catalog"."default",
  "parent_id" int8,
  "remark" varchar(500) COLLATE "pg_catalog"."default",
  "sort" int4,
  "status" int4
)
;

-- ----------------------------
-- Records of content_category
-- ----------------------------
INSERT INTO "public"."content_category" VALUES (334860869794140160, 1, '2026-05-27 07:10:52.773385', 0, 1, '2026-05-27 07:24:19.402346', '休息吧', 334860969362722816, '', 0, 1);
INSERT INTO "public"."content_category" VALUES (334860969362722816, 1, '2026-05-27 07:11:16.512998', 0, 1, '2026-05-27 07:26:08.455588', '休息', NULL, '', 0, 1);

-- ----------------------------
-- Table structure for content_category_rel
-- ----------------------------
DROP TABLE IF EXISTS "public"."content_category_rel";
CREATE TABLE "public"."content_category_rel" (
  "id" int8 NOT NULL,
  "category_id" int8,
  "content_id" int8
)
;

-- ----------------------------
-- Records of content_category_rel
-- ----------------------------
INSERT INTO "public"."content_category_rel" VALUES (335228213750009856, 334860869794140160, 335196873533034496);

-- ----------------------------
-- Table structure for content_comment
-- ----------------------------
DROP TABLE IF EXISTS "public"."content_comment";
CREATE TABLE "public"."content_comment" (
  "id" int8 NOT NULL,
  "create_by" int8,
  "create_time" timestamp(6),
  "deleted" int4,
  "update_by" int8,
  "update_time" timestamp(6),
  "avatar" varchar(500) COLLATE "pg_catalog"."default",
  "content" text COLLATE "pg_catalog"."default",
  "content_id" int8,
  "ip" varchar(50) COLLATE "pg_catalog"."default",
  "like_count" int8,
  "nickname" varchar(100) COLLATE "pg_catalog"."default",
  "parent_id" int8,
  "reply_to_comment_id" int8,
  "reply_to_user_id" int8,
  "root_id" int8,
  "status" int4,
  "user_agent" varchar(500) COLLATE "pg_catalog"."default",
  "user_id" int8
)
;

-- ----------------------------
-- Records of content_comment
-- ----------------------------

-- ----------------------------
-- Table structure for content_comment_like
-- ----------------------------
DROP TABLE IF EXISTS "public"."content_comment_like";
CREATE TABLE "public"."content_comment_like" (
  "id" int8 NOT NULL,
  "create_by" int8,
  "create_time" timestamp(6),
  "deleted" int4,
  "update_by" int8,
  "update_time" timestamp(6),
  "action" int4,
  "comment_id" int8,
  "content_id" int8,
  "user_id" int8
)
;

-- ----------------------------
-- Records of content_comment_like
-- ----------------------------

-- ----------------------------
-- Table structure for content_favorite
-- ----------------------------
DROP TABLE IF EXISTS "public"."content_favorite";
CREATE TABLE "public"."content_favorite" (
  "id" int8 NOT NULL,
  "create_by" int8,
  "create_time" timestamp(6),
  "deleted" int4,
  "update_by" int8,
  "update_time" timestamp(6),
  "content_id" int8,
  "user_id" int8
)
;

-- ----------------------------
-- Records of content_favorite
-- ----------------------------

-- ----------------------------
-- Table structure for content_info
-- ----------------------------
DROP TABLE IF EXISTS "public"."content_info";
CREATE TABLE "public"."content_info" (
  "id" int8 NOT NULL,
  "create_by" int8,
  "create_time" timestamp(6),
  "deleted" int4,
  "update_by" int8,
  "update_time" timestamp(6),
  "allow_comment" bool,
  "audit_by" int8,
  "audit_status" int4,
  "audit_time" timestamp(6),
  "author_id" int8,
  "author_name" varchar(100) COLLATE "pg_catalog"."default",
  "comment_count" int8,
  "cover" varchar(500) COLLATE "pg_catalog"."default",
  "favorite_count" int8,
  "last_comment_time" timestamp(6),
  "like_count" int8,
  "publish_status" int4,
  "publish_time" timestamp(6),
  "recommend_flag" bool,
  "source" varchar(100) COLLATE "pg_catalog"."default",
  "source_url" varchar(500) COLLATE "pg_catalog"."default",
  "status" int4,
  "summary" varchar(500) COLLATE "pg_catalog"."default",
  "title" varchar(200) COLLATE "pg_catalog"."default",
  "top_flag" bool,
  "view_count" int8
)
;

-- ----------------------------
-- Records of content_info
-- ----------------------------
INSERT INTO "public"."content_info" VALUES (335196873533034496, 1, '2026-05-28 05:26:02.30752', 0, 1, '2026-05-28 07:09:50.384462', 't', NULL, 2, NULL, 311663478626717696, '管理员1', NULL, '327635300161556480', NULL, NULL, NULL, 1, NULL, 't', '', '', 1, 'aaaaaaaaaaaafff', '支付1', 't', NULL);

-- ----------------------------
-- Table structure for content_like
-- ----------------------------
DROP TABLE IF EXISTS "public"."content_like";
CREATE TABLE "public"."content_like" (
  "id" int8 NOT NULL,
  "create_by" int8,
  "create_time" timestamp(6),
  "deleted" int4,
  "update_by" int8,
  "update_time" timestamp(6),
  "content_id" int8,
  "user_id" int8
)
;

-- ----------------------------
-- Records of content_like
-- ----------------------------

-- ----------------------------
-- Table structure for content_report
-- ----------------------------
DROP TABLE IF EXISTS "public"."content_report";
CREATE TABLE "public"."content_report" (
  "id" int8 NOT NULL,
  "create_by" int8,
  "create_time" timestamp(6),
  "deleted" int4,
  "update_by" int8,
  "update_time" timestamp(6),
  "handle_by" int8,
  "handle_time" timestamp(6),
  "ip" varchar(50) COLLATE "pg_catalog"."default",
  "reason" text COLLATE "pg_catalog"."default",
  "reason_type" int4,
  "report_by" int8,
  "status" int4,
  "target_id" int8,
  "target_type" int4,
  "user_agent" varchar(500) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of content_report
-- ----------------------------

-- ----------------------------
-- Table structure for content_revision
-- ----------------------------
DROP TABLE IF EXISTS "public"."content_revision";
CREATE TABLE "public"."content_revision" (
  "id" int8 NOT NULL,
  "create_by" int8,
  "create_time" timestamp(6),
  "deleted" int4,
  "update_by" int8,
  "update_time" timestamp(6),
  "content" text COLLATE "pg_catalog"."default",
  "content_html" text COLLATE "pg_catalog"."default",
  "content_id" int8,
  "editor_id" int8,
  "format" varchar(50) COLLATE "pg_catalog"."default",
  "reason" text COLLATE "pg_catalog"."default",
  "version" int4,
  "word_count" int4
)
;

-- ----------------------------
-- Records of content_revision
-- ----------------------------
INSERT INTO "public"."content_revision" VALUES (335196873675640832, 1, '2026-05-28 05:26:02.341985', 1, 1, '2026-05-28 05:26:02.341985', 'aaaaaaaaaaaaaa', '<p>aaaaaaaaaaaaaa</p>', 335196873533034500, NULL, 'html', NULL, 1, 14);
INSERT INTO "public"."content_revision" VALUES (335196945494708224, 1, '2026-05-28 05:26:19.464499', 1, 1, '2026-05-28 05:26:19.464499', 'asdfsadfsdafasd', '<p>asdfsadfsdafasd</p>', 335196873533034500, NULL, 'html', NULL, 1, 15);
INSERT INTO "public"."content_revision" VALUES (335197131503702016, 1, '2026-05-28 05:27:03.812628', 1, 1, '2026-05-28 05:27:03.812628', 'sadfsdafsadfsdafsad', '<p>sadfsdafsadfsdafsad</p>', 335196873533034500, NULL, 'html', NULL, 1, 19);
INSERT INTO "public"."content_revision" VALUES (335208358917312512, 1, '2026-05-28 06:11:40.636455', 0, 1, '2026-05-28 06:11:40.636455', '', '<table style="min-width: 75px;"><colgroup><col style="min-width: 25px;"><col style="min-width: 25px;"><col style="min-width: 25px;"></colgroup><tbody><tr><th colspan="1" rowspan="1"><p></p></th><th colspan="1" rowspan="1"><p></p></th><th colspan="1" rowspan="1"><p></p></th></tr><tr><td colspan="1" rowspan="1"><p></p></td><td colspan="1" rowspan="1"><p></p></td><td colspan="1" rowspan="1"><p></p></td></tr></tbody></table><img data-img-id="329749056903581696">', 335196873533034496, NULL, 'html', NULL, 1, 0);
INSERT INTO "public"."content_revision" VALUES (335209530138955776, 1, '2026-05-28 06:16:19.877283', 0, 1, '2026-05-28 06:16:19.877283', 'a b c d e f', '<table style="min-width: 75px;"><colgroup><col style="min-width: 25px;"><col style="min-width: 25px;"><col style="min-width: 25px;"></colgroup><tbody><tr><th colspan="1" rowspan="1"><p>a</p></th><th colspan="1" rowspan="1"><p>b</p></th><th colspan="1" rowspan="1"><p>c</p></th></tr><tr><td colspan="1" rowspan="1"><p>d</p></td><td colspan="1" rowspan="1"><p>e</p></td><td colspan="1" rowspan="1"><p>f</p></td></tr></tbody></table><img data-img-id="329749056903581696">', 335196873533034496, NULL, 'html', NULL, 2, 11);
INSERT INTO "public"."content_revision" VALUES (335215676358791168, 1, '2026-05-28 06:40:45.250775', 0, 1, '2026-05-28 06:40:45.250775', 'a bsdfsdf c daaaa efff f aaaaaaa', '<table style="min-width: 75px;"><colgroup><col style="min-width: 25px;"><col style="min-width: 25px;"><col style="min-width: 25px;"></colgroup><tbody><tr><th colspan="1" rowspan="1"><p>a</p></th><th colspan="1" rowspan="1"><p>bsdfsdf</p></th><th colspan="1" rowspan="1"><p>c</p></th></tr><tr><td colspan="1" rowspan="1"><p>daaaa</p></td><td colspan="1" rowspan="1"><p>efff</p></td><td colspan="1" rowspan="1"><p>f</p></td></tr></tbody></table><p></p><p></p><p><a target="_blank" rel="noopener noreferrer" href="sdafsd">aaaaaaa</a></p>', 335196873533034496, NULL, 'html', NULL, 3, 32);
INSERT INTO "public"."content_revision" VALUES (335216896355995648, 1, '2026-05-28 06:45:36.120558', 0, 1, '2026-05-28 06:45:36.120558', 'a bsdfsdf c daaaa efff f aaaaaaa', '<table style="min-width: 75px;"><colgroup><col style="min-width: 25px;"><col style="min-width: 25px;"><col style="min-width: 25px;"></colgroup><tbody><tr><th colspan="1" rowspan="1"><p>a</p></th><th colspan="1" rowspan="1"><p>bsdfsdf</p></th><th colspan="1" rowspan="1"><p>c</p></th></tr><tr><td colspan="1" rowspan="1"><p>daaaa</p></td><td colspan="1" rowspan="1"><p>efff</p></td><td colspan="1" rowspan="1"><p>f</p></td></tr></tbody></table><p></p><p></p><p><a target="_blank" rel="noopener noreferrer" href="sdafsd">aaaaaaa</a></p><img data-img-id="327640694992801792" width="100%">', 335196873533034496, NULL, 'html', NULL, 4, 32);

-- ----------------------------
-- Table structure for content_tag
-- ----------------------------
DROP TABLE IF EXISTS "public"."content_tag";
CREATE TABLE "public"."content_tag" (
  "id" int8 NOT NULL,
  "create_by" int8,
  "create_time" timestamp(6),
  "deleted" int4,
  "update_by" int8,
  "update_time" timestamp(6),
  "color" varchar(50) COLLATE "pg_catalog"."default",
  "display_type" int4,
  "icon" varchar(500) COLLATE "pg_catalog"."default",
  "image" varchar(500) COLLATE "pg_catalog"."default",
  "name" varchar(100) COLLATE "pg_catalog"."default",
  "status" int4
)
;

-- ----------------------------
-- Records of content_tag
-- ----------------------------
INSERT INTO "public"."content_tag" VALUES (334872083605622784, 1, '2026-05-27 07:55:26.354087', 0, 1, '2026-05-27 07:55:45.484851', '#1f7cff', 3, '327635300161556480', '331170521175691264', '总经理', 1);
INSERT INTO "public"."content_tag" VALUES (334870140703346688, 1, '2026-05-27 07:47:43.130149', 0, 1, '2026-05-27 07:55:50.291612', '#d6009d', 3, '327640694992801792', '327635300161556480', '总店', 1);

-- ----------------------------
-- Table structure for content_tag_rel
-- ----------------------------
DROP TABLE IF EXISTS "public"."content_tag_rel";
CREATE TABLE "public"."content_tag_rel" (
  "id" int8 NOT NULL,
  "content_id" int8,
  "tag_id" int8
)
;

-- ----------------------------
-- Records of content_tag_rel
-- ----------------------------
INSERT INTO "public"."content_tag_rel" VALUES (335228213766787072, 335196873533034496, 334872083605622800);

-- ----------------------------
-- Table structure for content_view_log
-- ----------------------------
DROP TABLE IF EXISTS "public"."content_view_log";
CREATE TABLE "public"."content_view_log" (
  "id" int8 NOT NULL,
  "create_by" int8,
  "create_time" timestamp(6),
  "deleted" int4,
  "update_by" int8,
  "update_time" timestamp(6),
  "content_id" int8,
  "ip" varchar(50) COLLATE "pg_catalog"."default",
  "referer" varchar(500) COLLATE "pg_catalog"."default",
  "user_agent" varchar(500) COLLATE "pg_catalog"."default",
  "user_id" int8
)
;

-- ----------------------------
-- Records of content_view_log
-- ----------------------------

-- ----------------------------
-- Table structure for file_config
-- ----------------------------
DROP TABLE IF EXISTS "public"."file_config";
CREATE TABLE "public"."file_config" (
  "id" int8 NOT NULL,
  "create_by" int8,
  "create_time" timestamp(6),
  "deleted" int4,
  "update_by" int8,
  "update_time" timestamp(6),
  "access_domain" varchar(255) COLLATE "pg_catalog"."default",
  "description" varchar(255) COLLATE "pg_catalog"."default",
  "remote_token" varchar(255) COLLATE "pg_catalog"."default",
  "remote_url" varchar(255) COLLATE "pg_catalog"."default",
  "status" int4,
  "storage_path" varchar(255) COLLATE "pg_catalog"."default",
  "type" varchar(255) COLLATE "pg_catalog"."default",
  "weight" int4,
  "config" text COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of file_config
-- ----------------------------
INSERT INTO "public"."file_config" VALUES (314530719714119680, NULL, '2026-04-01 04:46:07.278288', 0, 1, '2026-05-07 08:38:11.384481', 'http://127.0.0.1/file', '', '', '', 1, 'E:\work\images\file\nginx-1.28.3\html\file\', 'localFileStorageStrategy', NULL, NULL);
INSERT INTO "public"."file_config" VALUES (324422057674805248, 1, '2026-04-28 11:50:46.020616', 0, 1, '2026-05-22 06:28:26.136513', '', '', '', '', 2, '/test', 'aliyunFileStorageStrategy', NULL, '{
  "accessKeyId":"xx",
  "accessKeySecret":"xx",
  "region":"cn-xx",
  "bucket":"xx",
  "endpoint":"https://oss-cn-beijing.aliyuncs.com",
  "privateBucket":true,
  "urlExpireSeconds":600
}');

-- ----------------------------
-- Table structure for file_domain
-- ----------------------------
DROP TABLE IF EXISTS "public"."file_domain";
CREATE TABLE "public"."file_domain" (
  "id" int8 NOT NULL,
  "create_by" int8,
  "create_time" timestamp(6),
  "deleted" int4,
  "update_by" int8,
  "update_time" timestamp(6),
  "config_id" int8,
  "domain" varchar(255) COLLATE "pg_catalog"."default",
  "status" int4
)
;

-- ----------------------------
-- Records of file_domain
-- ----------------------------
INSERT INTO "public"."file_domain" VALUES (314602975387586560, NULL, '2026-04-01 09:33:14.373862', 0, NULL, '2026-04-01 09:34:49.000047', 314530719714119680, 'http://127.0.0.1/file', 1);
INSERT INTO "public"."file_domain" VALUES (314603023403978752, NULL, '2026-04-01 09:33:25.821856', 1, NULL, '2026-04-01 09:34:42.658917', 314530719714119680, 'http://127.0.0.1:123/file', 1);

-- ----------------------------
-- Table structure for file_info
-- ----------------------------
DROP TABLE IF EXISTS "public"."file_info";
CREATE TABLE "public"."file_info" (
  "id" int8 NOT NULL,
  "create_by" int8,
  "create_time" timestamp(6),
  "deleted" int4,
  "update_by" int8,
  "update_time" timestamp(6),
  "access_path" varchar(2000) COLLATE "pg_catalog"."default",
  "config_id" int8,
  "file_md5" varchar(255) COLLATE "pg_catalog"."default",
  "file_name" varchar(255) COLLATE "pg_catalog"."default",
  "file_path" varchar(2000) COLLATE "pg_catalog"."default",
  "file_size" int8,
  "file_storage" varchar(200) COLLATE "pg_catalog"."default",
  "file_type" varchar(255) COLLATE "pg_catalog"."default",
  "file_type_id" int8,
  "status" int4
)
;

-- ----------------------------
-- Records of file_info
-- ----------------------------
INSERT INTO "public"."file_info" VALUES (324782893933137920, 1, '2026-04-29 11:44:36.08821', 1, 1, '2026-04-29 11:44:36.08821', 'https://zwytestimage.oss-cn-beijing.aliyuncs.com/2026/04/29/20dbcde8a7eb7af1b9d0641c18f5216f.png?x-oss-expires=599&x-oss-signature-version=OSS4-HMAC-SHA256&x-oss-credential=LTAI5tAMwGrAy4Fs4jL6Tx7q%2F20260429%2Fcn-beijing%2Foss%2Faliyun_v4_request&x-oss-date=20260429T034436Z&x-oss-signature=5b0b79ced501749893975e1b68b3b1cda2b6cef3add649e6f1b7e81d070112f5', 324422057674805248, '20dbcde8a7eb7af1b9d0641c18f5216f', '壮族.png', '/2026/04/29/20dbcde8a7eb7af1b9d0641c18f5216f.png', 16116, 'aliyunFileStorageStrategy', 'png', 314563051296788480, 1);
INSERT INTO "public"."file_info" VALUES (325388697107304448, 1, '2026-05-01 03:51:50.820372', 1, 1, '2026-05-01 03:51:50.820372', 'https://zwytestimage.oss-cn-beijing.aliyuncs.com/2026/05/01/5e4b4d4b81537cc98e748d7c51c28b55.png?x-oss-expires=600&x-oss-signature-version=OSS4-HMAC-SHA256&x-oss-credential=LTAI5tAMwGrAy4Fs4jL6Tx7q%2F20260430%2Fcn-beijing%2Foss%2Faliyun_v4_request&x-oss-date=20260430T195150Z&x-oss-signature=007aef41cb3bea384f6f8313b345fc993e22cfd993081090a903d7b6b2356232', 324422057674805248, '5e4b4d4b81537cc98e748d7c51c28b55', 'image.png', '/2026/05/01/5e4b4d4b81537cc98e748d7c51c28b55.png', 761120, 'aliyunFileStorageStrategy', 'png', 314563051296788480, 1);
INSERT INTO "public"."file_info" VALUES (325380924059553792, 1, '2026-05-01 03:20:57.581181', 1, 1, '2026-05-01 03:20:57.581181', 'https://zwytestimage.oss-cn-beijing.aliyuncs.com/2026/05/01/cd1c55b1f6477c3bbfe9be1d3839e505.jpg?x-oss-expires=600&x-oss-signature-version=OSS4-HMAC-SHA256&x-oss-credential=LTAI5tAMwGrAy4Fs4jL6Tx7q%2F20260430%2Fcn-beijing%2Foss%2Faliyun_v4_request&x-oss-date=20260430T192057Z&x-oss-signature=1af4c5b347eca526db421d20d94a018ef2666e8bc8ebb0e97495abc03ec5a124', 324422057674805248, 'cd1c55b1f6477c3bbfe9be1d3839e505', 'CQ9.jpg', '/2026/05/01/cd1c55b1f6477c3bbfe9be1d3839e505.jpg', 271293, 'aliyunFileStorageStrategy', 'jpg', 314562980400467968, 1);
INSERT INTO "public"."file_info" VALUES (325380864647237632, 1, '2026-05-01 03:20:43.416827', 1, 1, '2026-05-01 03:20:43.416827', 'https://zwytestimage.oss-cn-beijing.aliyuncs.com/2026/05/01/d77199754151a33a0d4f334c2521a337.jpg?x-oss-expires=599&x-oss-signature-version=OSS4-HMAC-SHA256&x-oss-credential=LTAI5tAMwGrAy4Fs4jL6Tx7q%2F20260430%2Fcn-beijing%2Foss%2Faliyun_v4_request&x-oss-date=20260430T192043Z&x-oss-signature=19d6f8d7a88241c47d88fdb3ec58708e8231486dfb72e7d7ee7a6fda986d62f5', 324422057674805248, 'd77199754151a33a0d4f334c2521a337', '热门.jpg', '/2026/05/01/d77199754151a33a0d4f334c2521a337.jpg', 1157270, 'aliyunFileStorageStrategy', 'jpg', 314562980400467968, 1);
INSERT INTO "public"."file_info" VALUES (325387737484103680, 1, '2026-05-01 03:48:02.028048', 1, 1, '2026-05-01 03:48:02.028048', 'https://zwytestimage.oss-cn-beijing.aliyuncs.com/2026/05/01/223a167901e1351b2f08988d1ce9be21.png?x-oss-expires=600&x-oss-signature-version=OSS4-HMAC-SHA256&x-oss-credential=LTAI5tAMwGrAy4Fs4jL6Tx7q%2F20260430%2Fcn-beijing%2Foss%2Faliyun_v4_request&x-oss-date=20260430T194802Z&x-oss-signature=0421acbefe78d2e7a974ada960c76eb86ee2850eaaddb6316943f313bb774e50', 324422057674805248, '223a167901e1351b2f08988d1ce9be21', '1.PNG', '/2026/05/01/223a167901e1351b2f08988d1ce9be21.png', 97163, 'aliyunFileStorageStrategy', 'png', 314563051296788480, 1);
INSERT INTO "public"."file_info" VALUES (325390908830912512, 1, '2026-05-01 04:00:38.136309', 1, 1, '2026-05-01 04:00:38.136309', 'https://zwytestimage.oss-cn-beijing.aliyuncs.com/2026/05/01/97baefc373d1bbe661802ed3b013e0a5.png?x-oss-expires=600&x-oss-signature-version=OSS4-HMAC-SHA256&x-oss-credential=LTAI5tAMwGrAy4Fs4jL6Tx7q%2F20260430%2Fcn-beijing%2Foss%2Faliyun_v4_request&x-oss-date=20260430T200038Z&x-oss-signature=d523b9a3df5ae25b80b06ea4d4627e58c1d51a3e580c512545c41353b49f4e1e', 324422057674805248, '97baefc373d1bbe661802ed3b013e0a5', 'image_2026-02-14_00-13-23.png', '/2026/05/01/97baefc373d1bbe661802ed3b013e0a5.png', 54793, 'aliyunFileStorageStrategy', 'png', 314563051296788480, 1);
INSERT INTO "public"."file_info" VALUES (325391124225200128, 1, '2026-05-01 04:01:29.490471', 1, 1, '2026-05-01 04:01:29.490471', 'https://zwytestimage.oss-cn-beijing.aliyuncs.com/2026/05/01/bafcb99e53c50acae3550f3a4659497f.png?x-oss-expires=600&x-oss-signature-version=OSS4-HMAC-SHA256&x-oss-credential=LTAI5tAMwGrAy4Fs4jL6Tx7q%2F20260430%2Fcn-beijing%2Foss%2Faliyun_v4_request&x-oss-date=20260430T200129Z&x-oss-signature=4678dba1d9045283dd7922a5284170c656d223f5ece5a3e6ac4d05fe0b35d72f', 324422057674805248, 'bafcb99e53c50acae3550f3a4659497f', '8888.png', '/2026/05/01/bafcb99e53c50acae3550f3a4659497f.png', 3756, 'aliyunFileStorageStrategy', 'png', 314563051296788480, 1);
INSERT INTO "public"."file_info" VALUES (327635300161556480, 1, '2026-05-07 08:39:02.72898', 0, 1, '2026-05-07 08:39:02.72898', 'http://127.0.0.1/file/2026/05/07/9e6a165c1ef2e30fa809bb7525420059.png', 314530719714119680, '9e6a165c1ef2e30fa809bb7525420059', '土家族.png', '/2026/05/07/9e6a165c1ef2e30fa809bb7525420059.png', 14448, 'localFileStorageStrategy', 'png', 314563051296788480, 1);
INSERT INTO "public"."file_info" VALUES (327640694992801792, 1, '2026-05-07 09:00:28.956423', 0, 1, '2026-05-07 09:00:28.956423', 'http://127.0.0.1/file/2026/05/07/9ed4b3e47c6ffb240c7a5a54b9da4b0e.png', 314530719714119680, '9ed4b3e47c6ffb240c7a5a54b9da4b0e', '奶牛猫.png', '/2026/05/07/9ed4b3e47c6ffb240c7a5a54b9da4b0e.png', 10305, 'localFileStorageStrategy', 'png', 314563051296788480, 1);
INSERT INTO "public"."file_info" VALUES (329749056903581696, 1, '2026-05-13 04:38:21.607553', 0, 1, '2026-05-13 04:38:21.607553', 'http://127.0.0.1/file/2026/05/13/beab13a3ddd6e0fd1a2787f32c338a53.png', 314530719714119680, 'beab13a3ddd6e0fd1a2787f32c338a53', 'logo-32.png', '/2026/05/13/beab13a3ddd6e0fd1a2787f32c338a53.png', 7301, 'localFileStorageStrategy', 'png', 314563051296788480, 1);
INSERT INTO "public"."file_info" VALUES (331170521175691264, 1, '2026-05-17 02:46:45.099466', 0, 1, '2026-05-17 02:46:45.099466', 'http://127.0.0.1/file/2026/05/17/20dbcde8a7eb7af1b9d0641c18f5216f.png', 314530719714119680, '20dbcde8a7eb7af1b9d0641c18f5216f', '壮族.png', '/2026/05/17/20dbcde8a7eb7af1b9d0641c18f5216f.png', 16116, 'localFileStorageStrategy', 'png', 314563051296788480, 1);

-- ----------------------------
-- Table structure for file_type
-- ----------------------------
DROP TABLE IF EXISTS "public"."file_type";
CREATE TABLE "public"."file_type" (
  "id" int8 NOT NULL,
  "create_by" int8,
  "create_time" timestamp(6),
  "deleted" int4,
  "update_by" int8,
  "update_time" timestamp(6),
  "file_space" float8,
  "name" varchar(255) COLLATE "pg_catalog"."default",
  "platform_ratio" float8
)
;

-- ----------------------------
-- Records of file_type
-- ----------------------------
INSERT INTO "public"."file_type" VALUES (314562980400467968, NULL, '2026-04-01 06:54:18.82504', 0, NULL, '2026-04-01 06:54:18.82504', 0, 'JPG', 0);
INSERT INTO "public"."file_type" VALUES (314563051296788480, NULL, '2026-04-01 06:54:35.728733', 0, NULL, '2026-04-01 06:54:35.728733', 0, 'PNG', 0);
INSERT INTO "public"."file_type" VALUES (314569137689071616, NULL, '2026-04-01 07:18:46.837828', 0, NULL, '2026-04-01 07:18:46.837828', 0, 'EXE', 0);

-- ----------------------------
-- Table structure for mall_cart
-- ----------------------------
DROP TABLE IF EXISTS "public"."mall_cart";
CREATE TABLE "public"."mall_cart" (
  "id" int8 NOT NULL,
  "create_by" int8,
  "create_time" timestamp(6),
  "deleted" int4,
  "update_by" int8,
  "update_time" timestamp(6),
  "price" numeric(12,2),
  "product_id" int8,
  "product_snapshot" text COLLATE "pg_catalog"."default",
  "quantity" int4,
  "selected" int4,
  "shop_id" int8,
  "sku_id" int8,
  "sku_snapshot" text COLLATE "pg_catalog"."default",
  "user_id" int8
)
;

-- ----------------------------
-- Records of mall_cart
-- ----------------------------

-- ----------------------------
-- Table structure for mall_freight_template
-- ----------------------------
DROP TABLE IF EXISTS "public"."mall_freight_template";
CREATE TABLE "public"."mall_freight_template" (
  "id" int8 NOT NULL,
  "create_by" int8,
  "create_time" timestamp(6),
  "deleted" int4,
  "update_by" int8,
  "update_time" timestamp(6),
  "charge_type" int4,
  "default_additional_fee" numeric(12,2),
  "default_additional_unit" numeric(10,3),
  "default_first_fee" numeric(12,2),
  "default_first_unit" numeric(10,3),
  "exclude_regions" text COLLATE "pg_catalog"."default",
  "free_amount" numeric(12,2),
  "free_quantity" int4,
  "free_region_rules" text COLLATE "pg_catalog"."default",
  "free_type" int4,
  "free_weight" numeric(10,3),
  "is_default" int4,
  "name" varchar(200) COLLATE "pg_catalog"."default",
  "region_rules" text COLLATE "pg_catalog"."default",
  "remark" varchar(500) COLLATE "pg_catalog"."default",
  "shop_id" int8,
  "sort" int4,
  "status" int4
)
;

-- ----------------------------
-- Records of mall_freight_template
-- ----------------------------

-- ----------------------------
-- Table structure for mall_order
-- ----------------------------
DROP TABLE IF EXISTS "public"."mall_order";
CREATE TABLE "public"."mall_order" (
  "id" int8 NOT NULL,
  "create_by" int8,
  "create_time" timestamp(6),
  "deleted" int4,
  "update_by" int8,
  "update_time" timestamp(6),
  "close_time" timestamp(6),
  "delivery_time" timestamp(6),
  "discount_amount" numeric(12,2),
  "express_company" varchar(255) COLLATE "pg_catalog"."default",
  "express_no" varchar(255) COLLATE "pg_catalog"."default",
  "freight_amount" numeric(12,2),
  "item_kind_count" int4,
  "items_snapshot" text COLLATE "pg_catalog"."default",
  "order_no" varchar(64) COLLATE "pg_catalog"."default",
  "pay_amount" numeric(12,2),
  "pay_time" timestamp(6),
  "pay_trade_no" varchar(128) COLLATE "pg_catalog"."default",
  "pay_type" int4,
  "receive_time" timestamp(6),
  "receiver_address" varchar(500) COLLATE "pg_catalog"."default",
  "receiver_city" varchar(255) COLLATE "pg_catalog"."default",
  "receiver_district" varchar(255) COLLATE "pg_catalog"."default",
  "receiver_name" varchar(255) COLLATE "pg_catalog"."default",
  "receiver_phone" varchar(255) COLLATE "pg_catalog"."default",
  "receiver_postal_code" varchar(255) COLLATE "pg_catalog"."default",
  "receiver_province" varchar(255) COLLATE "pg_catalog"."default",
  "shop_id" int8,
  "shop_remark" varchar(500) COLLATE "pg_catalog"."default",
  "source_type" int4,
  "status" int4,
  "total_amount" numeric(12,2),
  "total_quantity" int4,
  "user_id" int8,
  "user_remark" varchar(500) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of mall_order
-- ----------------------------

-- ----------------------------
-- Table structure for mall_order_item
-- ----------------------------
DROP TABLE IF EXISTS "public"."mall_order_item";
CREATE TABLE "public"."mall_order_item" (
  "id" int8 NOT NULL,
  "create_by" int8,
  "create_time" timestamp(6),
  "deleted" int4,
  "update_by" int8,
  "update_time" timestamp(6),
  "brand_id" int8,
  "category_id" int8,
  "commented" int4,
  "delivery_type" int4,
  "discount_amount" numeric(12,2),
  "extra" text COLLATE "pg_catalog"."default",
  "gift_points_amount" int4,
  "order_id" int8,
  "order_no" varchar(64) COLLATE "pg_catalog"."default",
  "original_price" numeric(12,2),
  "pay_type" int4,
  "points_amount" int4,
  "points_price" int4,
  "price" numeric(12,2),
  "product_id" int8,
  "product_snapshot" text COLLATE "pg_catalog"."default",
  "quantity" int4,
  "refund_amount" numeric(12,2),
  "refund_quantity" int4,
  "refund_status" int4,
  "remark" varchar(500) COLLATE "pg_catalog"."default",
  "shop_id" int8,
  "sku_id" int8,
  "sku_snapshot" text COLLATE "pg_catalog"."default",
  "total_amount" numeric(12,2),
  "user_id" int8,
  "virtual_type" int4
)
;

-- ----------------------------
-- Records of mall_order_item
-- ----------------------------

-- ----------------------------
-- Table structure for mall_order_log
-- ----------------------------
DROP TABLE IF EXISTS "public"."mall_order_log";
CREATE TABLE "public"."mall_order_log" (
  "id" int8 NOT NULL,
  "create_by" int8,
  "create_time" timestamp(6),
  "deleted" int4,
  "update_by" int8,
  "update_time" timestamp(6),
  "action" varchar(32) COLLATE "pg_catalog"."default",
  "content" varchar(1000) COLLATE "pg_catalog"."default",
  "extra" text COLLATE "pg_catalog"."default",
  "from_status" int4,
  "ip" varchar(64) COLLATE "pg_catalog"."default",
  "operator_id" int8,
  "operator_name" varchar(100) COLLATE "pg_catalog"."default",
  "operator_type" int4,
  "order_id" int8,
  "order_no" varchar(64) COLLATE "pg_catalog"."default",
  "source_type" int4,
  "to_status" int4
)
;

-- ----------------------------
-- Records of mall_order_log
-- ----------------------------

-- ----------------------------
-- Table structure for mall_order_refund
-- ----------------------------
DROP TABLE IF EXISTS "public"."mall_order_refund";
CREATE TABLE "public"."mall_order_refund" (
  "id" int8 NOT NULL,
  "create_by" int8,
  "create_time" timestamp(6),
  "deleted" int4,
  "update_by" int8,
  "update_time" timestamp(6),
  "cancel_time" timestamp(6),
  "description" varchar(1000) COLLATE "pg_catalog"."default",
  "evidence" text COLLATE "pg_catalog"."default",
  "extra" text COLLATE "pg_catalog"."default",
  "finish_time" timestamp(6),
  "order_id" int8,
  "order_item_id" int8,
  "order_no" varchar(64) COLLATE "pg_catalog"."default",
  "pay_refund_trade_no" varchar(128) COLLATE "pg_catalog"."default",
  "reason" varchar(200) COLLATE "pg_catalog"."default",
  "refund_amount" numeric(12,2),
  "refund_no" varchar(64) COLLATE "pg_catalog"."default",
  "refund_points" int4,
  "refund_quantity" int4,
  "refund_status" int4,
  "refund_type" int4,
  "return_express_company" varchar(255) COLLATE "pg_catalog"."default",
  "return_express_no" varchar(255) COLLATE "pg_catalog"."default",
  "shop_id" int8,
  "shop_reject_reason" varchar(500) COLLATE "pg_catalog"."default",
  "shop_reply_text" varchar(500) COLLATE "pg_catalog"."default",
  "shop_reply_time" timestamp(6),
  "user_id" int8
)
;

-- ----------------------------
-- Records of mall_order_refund
-- ----------------------------

-- ----------------------------
-- Table structure for mall_pickup_shop
-- ----------------------------
DROP TABLE IF EXISTS "public"."mall_pickup_shop";
CREATE TABLE "public"."mall_pickup_shop" (
  "id" int8 NOT NULL,
  "create_by" int8,
  "create_time" timestamp(6),
  "deleted" int4,
  "update_by" int8,
  "update_time" timestamp(6),
  "city" varchar(255) COLLATE "pg_catalog"."default",
  "city_code" varchar(255) COLLATE "pg_catalog"."default",
  "code" varchar(64) COLLATE "pg_catalog"."default",
  "contact_name" varchar(255) COLLATE "pg_catalog"."default",
  "contact_phone" varchar(255) COLLATE "pg_catalog"."default",
  "detail_address" varchar(500) COLLATE "pg_catalog"."default",
  "district" varchar(255) COLLATE "pg_catalog"."default",
  "district_code" varchar(255) COLLATE "pg_catalog"."default",
  "latitude" numeric(10,6),
  "longitude" numeric(10,6),
  "name" varchar(200) COLLATE "pg_catalog"."default",
  "open_time" varchar(100) COLLATE "pg_catalog"."default",
  "pickup_notice" text COLLATE "pg_catalog"."default",
  "postal_code" varchar(255) COLLATE "pg_catalog"."default",
  "province" varchar(255) COLLATE "pg_catalog"."default",
  "province_code" varchar(255) COLLATE "pg_catalog"."default",
  "remark" varchar(500) COLLATE "pg_catalog"."default",
  "shop_id" int8,
  "sort" int4,
  "status" int4
)
;

-- ----------------------------
-- Records of mall_pickup_shop
-- ----------------------------

-- ----------------------------
-- Table structure for mall_product
-- ----------------------------
DROP TABLE IF EXISTS "public"."mall_product";
CREATE TABLE "public"."mall_product" (
  "id" int8 NOT NULL,
  "create_by" int8,
  "create_time" timestamp(6),
  "deleted" int4,
  "update_by" int8,
  "update_time" timestamp(6),
  "album_images" varchar(2000) COLLATE "pg_catalog"."default",
  "brand_id" int8,
  "category_id" int8,
  "cost_price" numeric(12,2),
  "detail" oid,
  "is_hot" int4,
  "is_new" int4,
  "is_recommend" int4,
  "keywords" varchar(500) COLLATE "pg_catalog"."default",
  "main_image" varchar(500) COLLATE "pg_catalog"."default",
  "name" varchar(200) COLLATE "pg_catalog"."default",
  "original_price" numeric(12,2),
  "price" numeric(12,2),
  "product_sn" varchar(255) COLLATE "pg_catalog"."default",
  "sales" int4,
  "shop_id" int8,
  "sort" int4,
  "status" int4,
  "stock" int4,
  "sub_title" varchar(500) COLLATE "pg_catalog"."default",
  "unit" varchar(255) COLLATE "pg_catalog"."default",
  "weight" numeric(10,3)
)
;

-- ----------------------------
-- Records of mall_product
-- ----------------------------
INSERT INTO "public"."mall_product" VALUES (332344134121689088, 1, '2026-05-20 08:30:16.230235', 0, 1, '2026-05-20 08:40:42.796763', '331170521175691264', 332000565657735168, 331983987067195392, 0.00, 409653, 0, 0, 0, '', '327640694992801792', '商品名称', 0.00, 0.00, 'admin', 0, 331970909915189248, 0, 1, 0, '副标题', '', 0.000);

-- ----------------------------
-- Table structure for mall_product_brand
-- ----------------------------
DROP TABLE IF EXISTS "public"."mall_product_brand";
CREATE TABLE "public"."mall_product_brand" (
  "id" int8 NOT NULL,
  "create_by" int8,
  "create_time" timestamp(6),
  "deleted" int4,
  "update_by" int8,
  "update_time" timestamp(6),
  "code" varchar(255) COLLATE "pg_catalog"."default",
  "description" varchar(1000) COLLATE "pg_catalog"."default",
  "first_letter" varchar(2) COLLATE "pg_catalog"."default",
  "logo" varchar(500) COLLATE "pg_catalog"."default",
  "name" varchar(255) COLLATE "pg_catalog"."default",
  "show_in_home" int4,
  "sort" int4,
  "status" int4
)
;

-- ----------------------------
-- Records of mall_product_brand
-- ----------------------------
INSERT INTO "public"."mall_product_brand" VALUES (332000565657735168, 1, '2026-05-19 09:45:03.125606', 0, 1, '2026-05-19 09:45:03.125606', 'wwd', 'saa', '', '331170521175691264', 'aab', 1, 0, 1);

-- ----------------------------
-- Table structure for mall_product_category
-- ----------------------------
DROP TABLE IF EXISTS "public"."mall_product_category";
CREATE TABLE "public"."mall_product_category" (
  "id" int8 NOT NULL,
  "create_by" int8,
  "create_time" timestamp(6),
  "deleted" int4,
  "update_by" int8,
  "update_time" timestamp(6),
  "description" varchar(500) COLLATE "pg_catalog"."default",
  "icon" varchar(500) COLLATE "pg_catalog"."default",
  "image" varchar(500) COLLATE "pg_catalog"."default",
  "level" int4,
  "name" varchar(255) COLLATE "pg_catalog"."default",
  "parent_id" int8,
  "show_in_home" int4,
  "sort" int4,
  "status" int4
)
;

-- ----------------------------
-- Records of mall_product_category
-- ----------------------------
INSERT INTO "public"."mall_product_category" VALUES (331983987067195392, 1, '2026-05-19 08:39:10.481695', 0, 1, '2026-05-19 08:39:10.481695', '', '327640694992801792', '331170521175691264', 1, '衣服', 0, 0, 0, 1);
INSERT INTO "public"."mall_product_category" VALUES (331984072509362176, 1, '2026-05-19 08:39:30.852473', 0, 1, '2026-05-19 09:40:32.463964', '', '', '', 2, 'aa', 331983987067195392, 0, 0, 1);

-- ----------------------------
-- Table structure for mall_product_sku
-- ----------------------------
DROP TABLE IF EXISTS "public"."mall_product_sku";
CREATE TABLE "public"."mall_product_sku" (
  "id" int8 NOT NULL,
  "create_by" int8,
  "create_time" timestamp(6),
  "deleted" int4,
  "update_by" int8,
  "update_time" timestamp(6),
  "barcode" varchar(64) COLLATE "pg_catalog"."default",
  "cost_price" numeric(12,2),
  "extra" text COLLATE "pg_catalog"."default",
  "gift_points" int4,
  "image" varchar(500) COLLATE "pg_catalog"."default",
  "lock_stock" int4,
  "original_price" numeric(12,2),
  "pay_type" int4,
  "points_price" int4,
  "price" numeric(12,2),
  "product_id" int8,
  "sales" int4,
  "shop_id" int8,
  "sku_sn" varchar(64) COLLATE "pg_catalog"."default",
  "sort" int4,
  "spec_text" varchar(500) COLLATE "pg_catalog"."default",
  "specs" text COLLATE "pg_catalog"."default",
  "status" int4,
  "stock" int4,
  "volume" numeric(10,3),
  "weight" numeric(10,3)
)
;

-- ----------------------------
-- Records of mall_product_sku
-- ----------------------------
INSERT INTO "public"."mall_product_sku" VALUES (332344134205575168, 1, '2026-05-20 08:30:16.250572', 0, 1, '2026-05-20 08:40:42.810324', '条形码', 0.00, '', 0, '327635300161556480', 0, 0.00, 1, 0, 0.00, 332344134121689088, 0, 331970909915189248, 'SKU编号', 0, '规格描述', '', 1, 0, 0.000, 0.000);

-- ----------------------------
-- Table structure for mall_shop
-- ----------------------------
DROP TABLE IF EXISTS "public"."mall_shop";
CREATE TABLE "public"."mall_shop" (
  "id" int8 NOT NULL,
  "create_by" int8,
  "create_time" timestamp(6),
  "deleted" int4,
  "update_by" int8,
  "update_time" timestamp(6),
  "address" varchar(500) COLLATE "pg_catalog"."default",
  "banner" varchar(500) COLLATE "pg_catalog"."default",
  "code" varchar(255) COLLATE "pg_catalog"."default",
  "contact_email" varchar(255) COLLATE "pg_catalog"."default",
  "contact_name" varchar(255) COLLATE "pg_catalog"."default",
  "contact_phone" varchar(255) COLLATE "pg_catalog"."default",
  "description" varchar(1000) COLLATE "pg_catalog"."default",
  "logo" varchar(500) COLLATE "pg_catalog"."default",
  "name" varchar(255) COLLATE "pg_catalog"."default",
  "owner_id" int8,
  "remark" varchar(500) COLLATE "pg_catalog"."default",
  "sort" int4,
  "status" int4
)
;

-- ----------------------------
-- Records of mall_shop
-- ----------------------------
INSERT INTO "public"."mall_shop" VALUES (331970909915189248, 1, '2026-05-19 07:47:12.645158', 0, 1, '2026-05-19 07:52:13.646094', '', 'aaabb', 'count', '', 'admin', '13712341234', '<img data-img-id="327640694992801792" width="136px"><p>aaffff</p>', '329749056903581696', '总店', 311663478626717696, '', 0, 1);
INSERT INTO "public"."mall_shop" VALUES (332312019170627584, 1, '2026-05-20 06:22:39.429039', 0, 1, '2026-05-20 06:22:39.429039', '', '', 'aafff', '', '', '', '', '', '总店2', NULL, '', 0, 1);

-- ----------------------------
-- Table structure for mall_user_address
-- ----------------------------
DROP TABLE IF EXISTS "public"."mall_user_address";
CREATE TABLE "public"."mall_user_address" (
  "id" int8 NOT NULL,
  "create_by" int8,
  "create_time" timestamp(6),
  "deleted" int4,
  "update_by" int8,
  "update_time" timestamp(6),
  "city" varchar(255) COLLATE "pg_catalog"."default",
  "city_code" varchar(255) COLLATE "pg_catalog"."default",
  "detail_address" varchar(500) COLLATE "pg_catalog"."default",
  "district" varchar(255) COLLATE "pg_catalog"."default",
  "district_code" varchar(255) COLLATE "pg_catalog"."default",
  "is_default" int4,
  "latitude" varchar(255) COLLATE "pg_catalog"."default",
  "longitude" varchar(255) COLLATE "pg_catalog"."default",
  "postal_code" varchar(255) COLLATE "pg_catalog"."default",
  "province" varchar(255) COLLATE "pg_catalog"."default",
  "province_code" varchar(255) COLLATE "pg_catalog"."default",
  "receiver_name" varchar(255) COLLATE "pg_catalog"."default",
  "receiver_phone" varchar(255) COLLATE "pg_catalog"."default",
  "tag" varchar(255) COLLATE "pg_catalog"."default",
  "user_id" int8
)
;

-- ----------------------------
-- Records of mall_user_address
-- ----------------------------

-- ----------------------------
-- Table structure for message_config
-- ----------------------------
DROP TABLE IF EXISTS "public"."message_config";
CREATE TABLE "public"."message_config" (
  "id" int8 NOT NULL,
  "create_by" int8,
  "create_time" timestamp(6),
  "deleted" int4,
  "update_by" int8,
  "update_time" timestamp(6),
  "config" varchar(255) COLLATE "pg_catalog"."default",
  "description" varchar(255) COLLATE "pg_catalog"."default",
  "status" int4,
  "title" varchar(255) COLLATE "pg_catalog"."default",
  "type" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of message_config
-- ----------------------------
INSERT INTO "public"."message_config" VALUES (321770014233464832, 1, '2026-04-21 04:12:29.591948', 0, 1, '2026-04-24 04:01:29.993428', '{
    "host": "smtp.qq.com",
    "port": 465,
    "username": "123456",
    "password": "123456",
    "protocol": "smtps",
    "defaultEncoding": "UTF-8",
    "auth": true,
    "starttlsEnable": true,
    "from": "123456"
}', 'host：邮件服务器地址 port：邮件服务器端口 username：邮箱账号 password：邮箱授权码或密码 protocol：发送协议 defaultEncoding：默认编码 auth：是否需要身份验证 starttlsEnable：是否启用 STARTTLS 安全连接，默认为 true from：发件人邮箱地址', 1, '邮箱配置', 'messageSendMailService');

-- ----------------------------
-- Table structure for message_record
-- ----------------------------
DROP TABLE IF EXISTS "public"."message_record";
CREATE TABLE "public"."message_record" (
  "id" int8 NOT NULL,
  "create_by" int8,
  "create_time" timestamp(6),
  "deleted" int4,
  "update_by" int8,
  "update_time" timestamp(6),
  "config_id" int8,
  "content" varchar(255) COLLATE "pg_catalog"."default",
  "message_type" varchar(255) COLLATE "pg_catalog"."default",
  "operator_id" int8,
  "receiver" varchar(255) COLLATE "pg_catalog"."default",
  "status" varchar(255) COLLATE "pg_catalog"."default",
  "title" varchar(255) COLLATE "pg_catalog"."default",
  "user_type" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of message_record
-- ----------------------------
INSERT INTO "public"."message_record" VALUES (322529532735262720, 1, '2026-04-23 06:30:32.913889', 0, 1, '2026-04-23 06:30:32.913889', 321770014233464832, 'aaa', 'code', 1, '123456@qq.com', 'SENT', 'aa', NULL);
INSERT INTO "public"."message_record" VALUES (322536424672989184, 1, '2026-04-23 06:57:56.079707', 0, 1, '2026-04-23 06:57:56.079707', 321770014233464832, '【注册】您的注册验证码是：验证码，有效期10分钟，请勿泄露给他人。', 'CODE', 1, 'asdfsdfsad', 'SENT', '注册验证码', NULL);
INSERT INTO "public"."message_record" VALUES (322536623084539904, 1, '2026-04-23 06:58:43.384085', 0, 1, '2026-04-23 06:58:43.384085', 321770014233464832, '【注册】注册 您的注册验证码是：验证码，有效期10分钟，请勿泄露给他人。', 'CODE', 1, 'asdfsdfsad', 'SENT', '注册验证码', NULL);
INSERT INTO "public"."message_record" VALUES (322536841905573888, 1, '2026-04-23 06:59:35.555962', 0, 1, '2026-04-23 06:59:35.555962', 321770014233464832, '【注册】注册 您的注册验证码是：验证码，有效期10分钟，请勿泄露给他人。', 'CODE', 1, 'asdfsdfsad', 'SENT', '注册验证码', NULL);
INSERT INTO "public"."message_record" VALUES (322537329229172736, 1, '2026-04-23 07:01:31.742397', 0, 1, '2026-04-23 07:01:31.742397', 321770014233464832, '【注册】注册 您的注册验证码是：验证码，有效期10分钟，请勿泄露给他人。', 'CODE', 1, 'asdfsdfsad', 'SENT', '注册验证码', NULL);
INSERT INTO "public"."message_record" VALUES (322561700681355264, 1, '2026-04-23 08:38:22.349373', 0, 1, '2026-04-23 08:38:22.349373', 321770014233464832, '【注册】注册 您的注册验证码是：12341，有效期10分钟，请勿泄露给他人。', 'CODE', 1, '3350542608@qq.com', 'SENT', '注册验证码', NULL);
INSERT INTO "public"."message_record" VALUES (322563647803101184, 1, '2026-04-23 08:46:06.579342', 0, 1, '2026-04-23 08:46:06.579342', 321770014233464832, '【注册】注册 您的注册验证码是：123码，有效期10分钟，请勿泄露给他人。', 'CODE', 1, '3350542608@qq.com', 'FAILED', '注册验证码', NULL);
INSERT INTO "public"."message_record" VALUES (322564782668517376, 1, '2026-04-23 08:50:37.152324', 0, 1, '2026-04-23 08:50:37.152324', 321770014233464832, '【注册】注册 您的注册验证码是：123码，有效期10分钟，请勿泄露给他人。', 'CODE', 1, '3350542608@qq.com', 'SENT', '注册验证码', NULL);
INSERT INTO "public"."message_record" VALUES (322799708563181568, 1, '2026-04-24 00:24:07.850627', 0, 1, '2026-04-24 00:24:07.850627', 321770014233464832, '【注册】注册 您的注册验证码是：验证码，有效期10分钟，请勿泄露给他人。', 'CODE', 1, '3350542608@qq.com', 'SENT', '注册验证码', NULL);
INSERT INTO "public"."message_record" VALUES (322800162911162368, 1, '2026-04-24 00:25:56.175525', 0, 1, '2026-04-24 00:25:56.175525', 321770014233464832, '【注册】注册 您的注册验证码是：验证码，有效期10分钟，请勿泄露给他人。', 'CODE', 1, '3350542608@qq.com', 'FAILED', '注册验证码', NULL);
INSERT INTO "public"."message_record" VALUES (322800364380360704, 1, '2026-04-24 00:26:44.209849', 0, 1, '2026-04-24 00:26:44.209849', 321770014233464832, '【注册】注册 您的注册验证码是：验证码，有效期10分钟，请勿泄露给他人。', 'CODE', 1, '3350542608@qq.com', 'SENT', '注册验证码', NULL);

-- ----------------------------
-- Table structure for message_template
-- ----------------------------
DROP TABLE IF EXISTS "public"."message_template";
CREATE TABLE "public"."message_template" (
  "id" int8 NOT NULL,
  "create_by" int8,
  "create_time" timestamp(6),
  "deleted" int4,
  "update_by" int8,
  "update_time" timestamp(6),
  "code" varchar(255) COLLATE "pg_catalog"."default",
  "content" varchar(255) COLLATE "pg_catalog"."default",
  "description" varchar(255) COLLATE "pg_catalog"."default",
  "status" int4,
  "title" varchar(255) COLLATE "pg_catalog"."default",
  "type_id" int8,
  "config_id" int8
)
;

-- ----------------------------
-- Records of message_template
-- ----------------------------
INSERT INTO "public"."message_template" VALUES (321780181989724160, 1, '2026-04-21 04:52:53.773547', 0, 1, '2026-04-23 06:58:39.007694', 'bb', '【#{appName}】#{appName} 您的注册验证码是：#{code}，有效期#{expireTime}分钟，请勿泄露给他人。', '{
    "appName":"注册",
    "code":"验证码",
     "expireTime":"10"
}', 1, '注册验证码', 321774750718365696, 321770014233464832);

-- ----------------------------
-- Table structure for message_type
-- ----------------------------
DROP TABLE IF EXISTS "public"."message_type";
CREATE TABLE "public"."message_type" (
  "id" int8 NOT NULL,
  "create_by" int8,
  "create_time" timestamp(6),
  "deleted" int4,
  "update_by" int8,
  "update_time" timestamp(6),
  "code" varchar(255) COLLATE "pg_catalog"."default",
  "description" varchar(255) COLLATE "pg_catalog"."default",
  "status" int4,
  "title" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of message_type
-- ----------------------------
INSERT INTO "public"."message_type" VALUES (321774750718365696, 1, '2026-04-21 04:31:18.857641', 0, 1, '2026-04-23 06:31:49.618061', 'CODE', '用于注册，登录，修改密码登 发送验证码', 1, '验证码');

-- ----------------------------
-- Table structure for message_verification_code
-- ----------------------------
DROP TABLE IF EXISTS "public"."message_verification_code";
CREATE TABLE "public"."message_verification_code" (
  "id" int8 NOT NULL,
  "create_by" int8,
  "create_time" timestamp(6),
  "deleted" int4,
  "update_by" int8,
  "update_time" timestamp(6),
  "business_data" varchar(255) COLLATE "pg_catalog"."default",
  "code" varchar(255) COLLATE "pg_catalog"."default",
  "config_id" int8,
  "expire_time" int8,
  "status" int4,
  "target" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of message_verification_code
-- ----------------------------
INSERT INTO "public"."message_verification_code" VALUES (322536623101317120, 1, '2026-04-23 06:58:43.388285', 0, 1, '2026-04-23 06:59:13.621146', NULL, '验证码', 321770014233464832, 1776899323388, 2, 'asdfsdfsad');
INSERT INTO "public"."message_verification_code" VALUES (322536841918156800, 1, '2026-04-23 06:59:35.558603', 0, 1, '2026-04-23 06:59:42.603067', NULL, '验证码', 321770014233464832, 1776899375558, 2, 'asdfsdfsad');
INSERT INTO "public"."message_verification_code" VALUES (322536424744292352, 1, '2026-04-23 06:57:56.096404', 0, 1, '2026-04-23 07:01:27.337716', NULL, '验证码', 321770014233464832, 1776899276096, 2, 'asdfsdfsad');
INSERT INTO "public"."message_verification_code" VALUES (322537329245949952, 1, '2026-04-23 07:01:31.746066', 0, 1, '2026-04-23 07:47:26.091506', NULL, '验证码', 321770014233464832, 1776899491746, 3, 'asdfsdfsad');
INSERT INTO "public"."message_verification_code" VALUES (322561700702326784, 1, '2026-04-23 08:38:22.354248', 0, 1, '2026-04-23 08:38:22.354248', NULL, '12341', 321770014233464832, 1776905302353, 1, '3350542608@qq.com');
INSERT INTO "public"."message_verification_code" VALUES (322563647933124608, 1, '2026-04-23 08:46:06.610174', 0, 1, '2026-04-23 08:46:06.610174', NULL, '123码', 321770014233464832, 1776905766609, 1, '3350542608@qq.com');
INSERT INTO "public"."message_verification_code" VALUES (322564782735626240, 1, '2026-04-23 08:50:37.168705', 0, 1, '2026-04-23 08:50:37.168705', NULL, '123码', 321770014233464832, 1776906037168, 1, '3350542608@qq.com');
INSERT INTO "public"."message_verification_code" VALUES (322799708592541696, 1, '2026-04-24 00:24:07.857646', 0, 1, '2026-04-24 00:24:07.857646', NULL, '验证码', 321770014233464832, 1776962047856, 1, '3350542608@qq.com');
INSERT INTO "public"."message_verification_code" VALUES (322800162927939584, 1, '2026-04-24 00:25:56.179764', 0, 1, '2026-04-24 00:25:56.179764', NULL, '验证码', 321770014233464832, 1776962156179, 1, '3350542608@qq.com');
INSERT INTO "public"."message_verification_code" VALUES (322800364397137920, 1, '2026-04-24 00:26:44.21373', 0, 1, '2026-04-24 00:26:44.21373', NULL, '验证码', 321770014233464832, 1776962204213, 1, '3350542608@qq.com');

-- ----------------------------
-- Table structure for page_application
-- ----------------------------
DROP TABLE IF EXISTS "public"."page_application";
CREATE TABLE "public"."page_application" (
  "id" int8 NOT NULL,
  "create_by" int8,
  "create_time" timestamp(6),
  "deleted" int4,
  "update_by" int8,
  "update_time" timestamp(6),
  "code" varchar(255) COLLATE "pg_catalog"."default",
  "icon" varchar(255) COLLATE "pg_catalog"."default",
  "status" int4,
  "title" varchar(255) COLLATE "pg_catalog"."default",
  "type_id" int8
)
;

-- ----------------------------
-- Records of page_application
-- ----------------------------
INSERT INTO "public"."page_application" VALUES (327622567294799872, 1, '2026-05-07 07:48:26.976275', 0, 1, '2026-05-10 05:49:42.387685', 'web', '327640694992801792', 1, 'APPUL', 327582763836379136);

-- ----------------------------
-- Table structure for page_component
-- ----------------------------
DROP TABLE IF EXISTS "public"."page_component";
CREATE TABLE "public"."page_component" (
  "id" int8 NOT NULL,
  "create_by" int8,
  "create_time" timestamp(6),
  "deleted" int4,
  "update_by" int8,
  "update_time" timestamp(6),
  "code" varchar(255) COLLATE "pg_catalog"."default",
  "icon" varchar(255) COLLATE "pg_catalog"."default",
  "sort" int4,
  "status" int4,
  "title" varchar(255) COLLATE "pg_catalog"."default",
  "type_id" int8
)
;

-- ----------------------------
-- Records of page_component
-- ----------------------------
INSERT INTO "public"."page_component" VALUES (327584417465896960, 1, '2026-05-07 05:16:51.348243', 0, 1, '2026-05-07 11:24:27.643475', 'test1', '327640694992801792', 0, 1, '测试1', 327582763836379136);
INSERT INTO "public"."page_component" VALUES (329848579109818368, 1, '2026-05-13 11:13:49.550816', 0, 1, '2026-05-13 11:14:02.952102', 'swipe', '327635300161556480', 0, 1, '轮播图', 329848261059940352);
INSERT INTO "public"."page_component" VALUES (331170376434454528, 1, '2026-05-17 02:46:10.590213', 0, 1, '2026-05-17 02:46:10.590213', 'carousel', '', 0, 1, '轮番图', 327582763836379136);
INSERT INTO "public"."page_component" VALUES (332700800243601408, 1, '2026-05-21 08:07:32.060162', 0, 1, '2026-05-21 08:54:20.937272', 'mall_top_information_bar', '', 0, 1, '商城顶部信息栏', 327582763836379136);

-- ----------------------------
-- Table structure for page_config
-- ----------------------------
DROP TABLE IF EXISTS "public"."page_config";
CREATE TABLE "public"."page_config" (
  "id" int8 NOT NULL,
  "create_by" int8,
  "create_time" timestamp(6),
  "deleted" int4,
  "update_by" int8,
  "update_time" timestamp(6),
  "config" text COLLATE "pg_catalog"."default",
  "path_url" varchar(255) COLLATE "pg_catalog"."default",
  "status" int4,
  "version" varchar(255) COLLATE "pg_catalog"."default",
  "application_id" int8,
  "title" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of page_config
-- ----------------------------
INSERT INTO "public"."page_config" VALUES (327982819794096128, 1, '2026-05-08 07:39:57.865142', 0, 1, '2026-05-09 09:46:23.926164', '{"version":"1","nodes":[{"id":"1778291094192-1a5oa","type":"lc-grid","label":"栅格布局","props":{"columns":2,"gap":12,"padding":0,"bgColor":"transparent"},"children":[]}]}', '/home', 1, '1.0.0', 327622567294799872, '首页');
INSERT INTO "public"."page_config" VALUES (328249358409666560, 1, '2026-05-09 01:19:05.623344', 0, 1, '2026-05-21 11:42:25.53428', '{"version":"1","nodes":[{"id":"1779325454332-9z9tm","type":"lc-div","label":"自定义 Div","props":{"display":"block","flexDirection":"row","flexWrap":false,"alignItems":"stretch","justifyContent":"flex-start","gap":0,"padding":0,"minHeight":0,"bgColor":"","borderRadius":0,"sticky":"top"},"children":[{"id":"1779325224108-r9qpk","type":"mall_top_information_bar","label":"商城顶部信息栏","props":{"navItems":[]},"children":[]}],"css":{"width":"100%"}},{"id":"1778957327518-f5jb0","type":"lc-flex","label":"弹性容器","props":{"direction":"column","gap":12,"wrap":true,"align":"center","padding":0,"bgColor":"transparent"},"children":[{"id":"1778957181604-35y9v","type":"carousel","label":"轮番图","props":{"slides":[{"src":"327640694992801792","alt":"","link":""},{"src":"331170521175691264","alt":"","link":""}],"height":300,"arrows":false,"autoplaySpeed":2000},"css":{"width":"800px","border-radius":"10px","overflow":"hidden","border":"1px solid #52c41a"}}]},{"id":"1779333146430-hi172","type":"carousel","label":"轮番图","props":{}},{"id":"1779333150300-92rky","type":"carousel","label":"轮番图","props":{}},{"id":"1779333152200-r4gib","type":"carousel","label":"轮番图","props":{}},{"id":"1779333156241-ayl34","type":"carousel","label":"轮番图","props":{}},{"id":"1779333140922-pmhxk","type":"carousel","label":"轮番图","props":{}}]}', '/home', 1, '1.0.1', 327622567294799872, '首页');

-- ----------------------------
-- Table structure for page_type
-- ----------------------------
DROP TABLE IF EXISTS "public"."page_type";
CREATE TABLE "public"."page_type" (
  "id" int8 NOT NULL,
  "create_by" int8,
  "create_time" timestamp(6),
  "deleted" int4,
  "update_by" int8,
  "update_time" timestamp(6),
  "code" varchar(255) COLLATE "pg_catalog"."default",
  "status" int4,
  "title" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of page_type
-- ----------------------------
INSERT INTO "public"."page_type" VALUES (327582763836379136, 1, '2026-05-07 05:10:17.092164', 0, 1, '2026-05-08 08:19:25.069349', 'ant_design_vue', 1, 'Ant Design Vue');
INSERT INTO "public"."page_type" VALUES (329848261059940352, 1, '2026-05-13 11:12:33.72131', 0, 1, '2026-05-13 11:12:33.72131', 'vant2', 1, 'Vant2');

-- ----------------------------
-- Table structure for page_web_config
-- ----------------------------
DROP TABLE IF EXISTS "public"."page_web_config";
CREATE TABLE "public"."page_web_config" (
  "id" int8 NOT NULL,
  "create_by" int8,
  "create_time" timestamp(6),
  "deleted" int4,
  "update_by" int8,
  "update_time" timestamp(6),
  "config" text COLLATE "pg_catalog"."default",
  "path_url" varchar(255) COLLATE "pg_catalog"."default",
  "application_id" int8
)
;

-- ----------------------------
-- Records of page_web_config
-- ----------------------------
INSERT INTO "public"."page_web_config" VALUES (328723901276360704, 1, '2026-05-10 08:44:45.459373', 1, 1, '2026-05-10 09:24:14.316109', '{"version":"1","nodes":[{"id":"1778291705457-4qy1b","type":"lc-grid","label":"栅格布局","props":{"columns":2,"gap":4,"padding":0,"bgColor":"transparent"},"children":[{"id":"1778295148914-0kh3u","type":"lc-button","label":"按钮","props":{"label":"按钮","btnType":"primary","size":"middle","block":true}},{"id":"1778295156187-aw58v","type":"lc-button","label":"按钮","props":{"label":"按钮","btnType":"primary","size":"middle","block":true}},{"id":"1778295151717-f6l7y","type":"lc-button","label":"按钮","props":{"label":"按钮","btnType":"primary","size":"middle","block":true}},{"id":"1778295159967-3e2wi","type":"lc-button","label":"按钮","props":{"label":"按钮","btnType":"primary","size":"middle","block":true}}]},{"id":"1778291707737-7sbmk","type":"lc-flex","label":"弹性容器","props":{"direction":"row","gap":12,"wrap":true,"align":"flex-start","padding":0,"bgColor":"transparent"},"children":[]},{"id":"1778291710409-vxz6a","type":"lc-card","label":"卡片","props":{"title":"卡片标题","bordered":true,"shadow":false,"padding":2,"bgColor":"#ffffff","borderRadius":8},"children":[]},{"id":"1778291712318-x3cld","type":"lc-divider","label":"分割线","props":{"text":"","color":"#e8e8e8","margin":16}},{"id":"1778291748151-yh0wz","type":"lc-image","label":"图片","props":{"src":"","alt":"","width":100,"height":200,"objectFit":"cover","borderRadius":0}},{"id":"1778291714597-dus5s","type":"lc-spacer","label":"间距","props":{"height":24}},{"id":"1778291724268-qbldf","type":"lc-text","label":"文本","props":{"content":"文本内容","fontSize":14,"color":"#333333","fontWeight":"normal","align":"left","lineHeight":1.6}},{"id":"1778295688584-kg5ho","type":"test1","label":"测试1","props":{}},{"id":"1778291722368-snlyw","type":"lc-heading","label":"标题","props":{"content":"标题文字","level":"h2","color":"#1a1a1a","align":"left"}},{"id":"1778291726171-a1q6s","type":"lc-button","label":"按钮","props":{"label":"按钮","btnType":"primary","size":"middle","block":true}},{"id":"1778291728858-0ht5c","type":"lc-tag","label":"标签","props":{"content":"标签","color":"#1677ff","bgColor":"#e6f4ff","borderColor":"#91caff"}},{"id":"1778291742910-a56b2","type":"lc-link","label":"链接","props":{"text":"链接文字","href":"#","color":"#1677ff","underline":true}}]}', '/home', 327622567294799872);
INSERT INTO "public"."page_web_config" VALUES (328734629685235712, 1, '2026-05-10 09:27:23.311612', 0, 1, '2026-05-21 11:42:25.53428', '{"version":"1","nodes":[{"id":"1779325454332-9z9tm","type":"lc-div","label":"自定义 Div","props":{"display":"block","flexDirection":"row","flexWrap":false,"alignItems":"stretch","justifyContent":"flex-start","gap":0,"padding":0,"minHeight":0,"bgColor":"","borderRadius":0,"sticky":"top"},"children":[{"id":"1779325224108-r9qpk","type":"mall_top_information_bar","label":"商城顶部信息栏","props":{"navItems":[]},"children":[]}],"css":{"width":"100%"}},{"id":"1778957327518-f5jb0","type":"lc-flex","label":"弹性容器","props":{"direction":"column","gap":12,"wrap":true,"align":"center","padding":0,"bgColor":"transparent"},"children":[{"id":"1778957181604-35y9v","type":"carousel","label":"轮番图","props":{"slides":[{"src":"327640694992801792","alt":"","link":""},{"src":"331170521175691264","alt":"","link":""}],"height":300,"arrows":false,"autoplaySpeed":2000},"css":{"width":"800px","border-radius":"10px","overflow":"hidden","border":"1px solid #52c41a"}}]},{"id":"1779333146430-hi172","type":"carousel","label":"轮番图","props":{}},{"id":"1779333150300-92rky","type":"carousel","label":"轮番图","props":{}},{"id":"1779333152200-r4gib","type":"carousel","label":"轮番图","props":{}},{"id":"1779333156241-ayl34","type":"carousel","label":"轮番图","props":{}},{"id":"1779333140922-pmhxk","type":"carousel","label":"轮番图","props":{}}]}', '/home', 327622567294799872);

-- ----------------------------
-- Table structure for rate_limit_api_config
-- ----------------------------
DROP TABLE IF EXISTS "public"."rate_limit_api_config";
CREATE TABLE "public"."rate_limit_api_config" (
  "id" int8 NOT NULL,
  "create_by" int8,
  "create_time" timestamp(6),
  "deleted" int4,
  "update_by" int8,
  "update_time" timestamp(6),
  "api_path" varchar(256) COLLATE "pg_catalog"."default" NOT NULL,
  "enabled" bool NOT NULL,
  "http_method" varchar(16) COLLATE "pg_catalog"."default",
  "limit_dimension" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "max_requests" int8 NOT NULL,
  "time_window" int4 NOT NULL,
  "app_code" varchar(32) COLLATE "pg_catalog"."default" NOT NULL
)
;

-- ----------------------------
-- Records of rate_limit_api_config
-- ----------------------------
INSERT INTO "public"."rate_limit_api_config" VALUES (320726117516447744, 1, '2026-04-18 07:04:25.219617', 0, 1, '2026-04-18 07:04:25.219617', '/auth/captcha', 't', 'GET', 'IP', 2, 5, 'admin');

-- ----------------------------
-- Table structure for rate_limit_global_config
-- ----------------------------
DROP TABLE IF EXISTS "public"."rate_limit_global_config";
CREATE TABLE "public"."rate_limit_global_config" (
  "id" int8 NOT NULL,
  "create_by" int8,
  "create_time" timestamp(6),
  "deleted" int4,
  "update_by" int8,
  "update_time" timestamp(6),
  "burst_capacity" int8,
  "enabled" bool NOT NULL,
  "max_requests" int8 NOT NULL,
  "time_window" int4 NOT NULL,
  "app_code" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of rate_limit_global_config
-- ----------------------------
INSERT INTO "public"."rate_limit_global_config" VALUES (319010515709988864, 1, '2026-04-13 13:27:13.874515', 0, 1, '2026-04-16 06:05:17.836022', 2000, 't', 1000, 1, 'admin');

-- ----------------------------
-- Table structure for rate_limit_ip_black_white
-- ----------------------------
DROP TABLE IF EXISTS "public"."rate_limit_ip_black_white";
CREATE TABLE "public"."rate_limit_ip_black_white" (
  "id" int8 NOT NULL,
  "create_by" int8,
  "create_time" timestamp(6),
  "deleted" int4,
  "update_by" int8,
  "update_time" timestamp(6),
  "app_code" varchar(255) COLLATE "pg_catalog"."default",
  "enabled" bool,
  "ip_address" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "limit_msg" varchar(255) COLLATE "pg_catalog"."default",
  "list_type" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "remark" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of rate_limit_ip_black_white
-- ----------------------------

-- ----------------------------
-- Table structure for rate_limit_ip_config
-- ----------------------------
DROP TABLE IF EXISTS "public"."rate_limit_ip_config";
CREATE TABLE "public"."rate_limit_ip_config" (
  "id" int8 NOT NULL,
  "create_by" int8,
  "create_time" timestamp(6),
  "deleted" int4,
  "update_by" int8,
  "update_time" timestamp(6),
  "app_code" varchar(255) COLLATE "pg_catalog"."default",
  "burst_capacity" int8,
  "enabled" bool,
  "ip_address" varchar(255) COLLATE "pg_catalog"."default",
  "ip_type" varchar(255) COLLATE "pg_catalog"."default",
  "max_requests" int8,
  "time_window" int4
)
;

-- ----------------------------
-- Records of rate_limit_ip_config
-- ----------------------------
INSERT INTO "public"."rate_limit_ip_config" VALUES (320022298037981184, 1, '2026-04-16 08:27:41.579446', 0, 1, '2026-04-16 09:47:56.453363', 'admin', 100, 't', '', 'ALL', 60, 1);

-- ----------------------------
-- Table structure for rate_limit_record
-- ----------------------------
DROP TABLE IF EXISTS "public"."rate_limit_record";
CREATE TABLE "public"."rate_limit_record" (
  "id" int8 NOT NULL,
  "create_by" int8,
  "create_time" timestamp(6),
  "deleted" int4,
  "update_by" int8,
  "update_time" timestamp(6),
  "app_code" varchar(255) COLLATE "pg_catalog"."default",
  "headers" text COLLATE "pg_catalog"."default",
  "ip" varchar(255) COLLATE "pg_catalog"."default",
  "limit_key" varchar(255) COLLATE "pg_catalog"."default",
  "limit_reason" varchar(500) COLLATE "pg_catalog"."default",
  "limit_type" varchar(255) COLLATE "pg_catalog"."default",
  "method" varchar(255) COLLATE "pg_catalog"."default",
  "query_params" text COLLATE "pg_catalog"."default",
  "target_value" varchar(255) COLLATE "pg_catalog"."default",
  "url" varchar(1000) COLLATE "pg_catalog"."default",
  "user_id" int8
)
;

-- ----------------------------
-- Records of rate_limit_record
-- ----------------------------

-- ----------------------------
-- Table structure for rate_limit_user_black_white
-- ----------------------------
DROP TABLE IF EXISTS "public"."rate_limit_user_black_white";
CREATE TABLE "public"."rate_limit_user_black_white" (
  "id" int8 NOT NULL,
  "create_by" int8,
  "create_time" timestamp(6),
  "deleted" int4,
  "update_by" int8,
  "update_time" timestamp(6),
  "app_code" varchar(255) COLLATE "pg_catalog"."default",
  "enabled" bool,
  "limit_msg" varchar(255) COLLATE "pg_catalog"."default",
  "list_type" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "remark" varchar(255) COLLATE "pg_catalog"."default",
  "user_id" int8 NOT NULL
)
;

-- ----------------------------
-- Records of rate_limit_user_black_white
-- ----------------------------

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_config";
CREATE TABLE "public"."sys_config" (
  "id" int8 NOT NULL,
  "create_by" int8,
  "create_time" timestamp(6),
  "deleted" int4,
  "update_by" int8,
  "update_time" timestamp(6),
  "config_key" varchar(255) COLLATE "pg_catalog"."default",
  "config_value" varchar(255) COLLATE "pg_catalog"."default",
  "name" varchar(255) COLLATE "pg_catalog"."default",
  "remark" varchar(255) COLLATE "pg_catalog"."default",
  "status" int4,
  "type" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of sys_config
-- ----------------------------

-- ----------------------------
-- Table structure for sys_department
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_department";
CREATE TABLE "public"."sys_department" (
  "id" int8 NOT NULL,
  "create_by" int8,
  "create_time" timestamp(6),
  "deleted" int4,
  "update_by" int8,
  "update_time" timestamp(6),
  "email" varchar(255) COLLATE "pg_catalog"."default",
  "leader" varchar(255) COLLATE "pg_catalog"."default",
  "name" varchar(255) COLLATE "pg_catalog"."default",
  "parent_id" int8,
  "phone" varchar(255) COLLATE "pg_catalog"."default",
  "sort" int4,
  "status" int4,
  "tenant_id" int8
)
;

-- ----------------------------
-- Records of sys_department
-- ----------------------------
INSERT INTO "public"."sys_department" VALUES (313513559294349312, NULL, '2026-03-29 09:24:17.336056', 0, NULL, '2026-03-29 09:27:59.273437', '', '', '研发部', 313512651718266880, '', 0, 1, NULL);
INSERT INTO "public"."sys_department" VALUES (313514376491569152, NULL, '2026-03-29 09:27:32.171482', 0, NULL, '2026-03-29 09:28:03.362267', '', '', '财务部', 313512651718266880, '', 0, 1, NULL);
INSERT INTO "public"."sys_department" VALUES (313514475816882176, NULL, '2026-03-29 09:27:55.852622', 0, NULL, '2026-03-29 09:30:07.161255', '', '', '销售部', 313512651718266880, '', 0, 1, NULL);
INSERT INTO "public"."sys_department" VALUES (313512651718266880, NULL, '2026-03-29 09:20:40.953158', 0, NULL, '2026-04-04 13:32:17.178934', '', '张三', '总部', NULL, '123456', 0, 1, NULL);

-- ----------------------------
-- Table structure for sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_dict_data";
CREATE TABLE "public"."sys_dict_data" (
  "id" int8 NOT NULL,
  "create_by" int8,
  "create_time" timestamp(6),
  "deleted" int4,
  "update_by" int8,
  "update_time" timestamp(6),
  "dict_type_id" int8,
  "label" varchar(255) COLLATE "pg_catalog"."default",
  "remark" varchar(255) COLLATE "pg_catalog"."default",
  "sort" int4,
  "status" int4,
  "value" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of sys_dict_data
-- ----------------------------
INSERT INTO "public"."sys_dict_data" VALUES (313546551102410752, NULL, '2026-03-29 11:35:23.196376', 0, NULL, '2026-03-30 07:45:16.057349', 313546470873763840, '正常', '', 1, 1, '1');
INSERT INTO "public"."sys_dict_data" VALUES (313853518752649216, NULL, '2026-03-30 07:55:09.987914', 0, NULL, '2026-03-30 08:12:10.31675', 313546623328325632, '未知', '', 2, 1, '3');
INSERT INTO "public"."sys_dict_data" VALUES (313546786356727808, NULL, '2026-03-29 11:36:19.285496', 0, NULL, '2026-03-30 08:13:53.412716', 313546470873763840, '冻结', '', 2, 1, '2');
INSERT INTO "public"."sys_dict_data" VALUES (313853293044568064, NULL, '2026-03-30 07:54:16.174813', 0, NULL, '2026-03-30 13:03:55.046773', 313546623328325632, '男', '', 1, 1, '1');
INSERT INTO "public"."sys_dict_data" VALUES (313853457268346880, NULL, '2026-03-30 07:54:55.328321', 0, NULL, '2026-04-04 13:32:13.869712', 313546623328325632, '女', '', 0, 1, '2');
INSERT INTO "public"."sys_dict_data" VALUES (321770418778279936, 1, '2026-04-21 04:14:06.042521', 0, 1, '2026-04-21 04:14:14.076', 321770235235536896, '短信', '', 1, 1, 'message_www');
INSERT INTO "public"."sys_dict_data" VALUES (321770341162684416, 1, '2026-04-21 04:13:47.53757', 0, 1, '2026-04-23 06:29:54.778638', 321770235235536896, '邮箱1', '', 0, 1, 'messageSendMailService');
INSERT INTO "public"."sys_dict_data" VALUES (322907070850535424, 1, '2026-04-24 07:30:45.014465', 0, 1, '2026-04-24 07:30:47.865999', 322906994128326656, '已发送', '', 0, 1, 'SENT');
INSERT INTO "public"."sys_dict_data" VALUES (322907139456765952, 1, '2026-04-24 07:31:01.371282', 0, 1, '2026-04-24 07:31:01.371282', 322906994128326656, '发送失败', '', 0, 1, 'FAILED');
INSERT INTO "public"."sys_dict_data" VALUES (322907559591809024, 1, '2026-04-24 07:32:41.539961', 0, 1, '2026-04-24 07:32:41.539961', 322907458316144640, '通知', '', 0, 1, 'NOTICE');
INSERT INTO "public"."sys_dict_data" VALUES (322907510367457280, 1, '2026-04-24 07:32:29.803461', 0, 1, '2026-04-24 07:32:44.053309', 322907458316144640, '验证码', '', 0, 1, 'CODE');
INSERT INTO "public"."sys_dict_data" VALUES (324369044696338432, 1, '2026-04-28 08:20:06.741625', 0, 1, '2026-04-28 08:20:41.586058', 324368936110002176, '本地', '', 0, 1, 'localFileStorageStrategy');
INSERT INTO "public"."sys_dict_data" VALUES (324369112266575872, 1, '2026-04-28 08:20:22.851859', 0, 1, '2026-04-28 08:55:30.403913', 324368936110002176, '阿里云', '', 2, 1, 'aliyunFileStorageStrategy');
INSERT INTO "public"."sys_dict_data" VALUES (950001, NULL, '2026-05-18 20:52:49.819702', 0, NULL, '2026-05-18 20:52:49.819702', 9500, '否', '', 1, 1, '0');
INSERT INTO "public"."sys_dict_data" VALUES (950002, NULL, '2026-05-18 20:52:49.819702', 0, NULL, '2026-05-18 20:52:49.819702', 9500, '是', '', 2, 1, '1');
INSERT INTO "public"."sys_dict_data" VALUES (950101, NULL, '2026-05-18 20:52:49.822172', 0, NULL, '2026-05-18 20:52:49.822172', 9501, '现金', '', 1, 1, '1');
INSERT INTO "public"."sys_dict_data" VALUES (950102, NULL, '2026-05-18 20:52:49.822172', 0, NULL, '2026-05-18 20:52:49.822172', 9501, '积分', '', 2, 1, '2');
INSERT INTO "public"."sys_dict_data" VALUES (950103, NULL, '2026-05-18 20:52:49.822172', 0, NULL, '2026-05-18 20:52:49.822172', 9501, '现金+积分', '', 3, 1, '3');
INSERT INTO "public"."sys_dict_data" VALUES (950201, NULL, '2026-05-18 20:52:49.824353', 0, NULL, '2026-05-18 20:52:49.824353', 9502, '微信', '', 1, 1, '1');
INSERT INTO "public"."sys_dict_data" VALUES (950202, NULL, '2026-05-18 20:52:49.824353', 0, NULL, '2026-05-18 20:52:49.824353', 9502, '支付宝', '', 2, 1, '2');
INSERT INTO "public"."sys_dict_data" VALUES (950203, NULL, '2026-05-18 20:52:49.824353', 0, NULL, '2026-05-18 20:52:49.824353', 9502, '余额', '', 3, 1, '3');
INSERT INTO "public"."sys_dict_data" VALUES (950204, NULL, '2026-05-18 20:52:49.824353', 0, NULL, '2026-05-18 20:52:49.824353', 9502, '其它', '', 4, 1, '4');
INSERT INTO "public"."sys_dict_data" VALUES (950301, NULL, '2026-05-18 20:52:49.826223', 0, NULL, '2026-05-18 20:52:49.826223', 9503, '物流', '', 1, 1, '1');
INSERT INTO "public"."sys_dict_data" VALUES (950302, NULL, '2026-05-18 20:52:49.826223', 0, NULL, '2026-05-18 20:52:49.826223', 9503, '无需物流', '', 2, 1, '2');
INSERT INTO "public"."sys_dict_data" VALUES (950303, NULL, '2026-05-18 20:52:49.826223', 0, NULL, '2026-05-18 20:52:49.826223', 9503, '到店自提', '', 3, 1, '3');
INSERT INTO "public"."sys_dict_data" VALUES (950304, NULL, '2026-05-18 20:52:49.826223', 0, NULL, '2026-05-18 20:52:49.826223', 9503, '同城配送', '', 4, 1, '4');
INSERT INTO "public"."sys_dict_data" VALUES (950401, NULL, '2026-05-18 20:52:49.828408', 0, NULL, '2026-05-18 20:52:49.828408', 9504, '卡密', '', 1, 1, '1');
INSERT INTO "public"."sys_dict_data" VALUES (950402, NULL, '2026-05-18 20:52:49.828408', 0, NULL, '2026-05-18 20:52:49.828408', 9504, '充值码', '', 2, 1, '2');
INSERT INTO "public"."sys_dict_data" VALUES (950403, NULL, '2026-05-18 20:52:49.828408', 0, NULL, '2026-05-18 20:52:49.828408', 9504, '兑换券', '', 3, 1, '3');
INSERT INTO "public"."sys_dict_data" VALUES (950404, NULL, '2026-05-18 20:52:49.828408', 0, NULL, '2026-05-18 20:52:49.828408', 9504, '线上服务', '', 4, 1, '4');
INSERT INTO "public"."sys_dict_data" VALUES (950405, NULL, '2026-05-18 20:52:49.828408', 0, NULL, '2026-05-18 20:52:49.828408', 9504, '会员权益', '', 5, 1, '5');
INSERT INTO "public"."sys_dict_data" VALUES (950501, NULL, '2026-05-18 20:52:49.830232', 0, NULL, '2026-05-18 20:52:49.830232', 9505, '上架', '', 1, 1, '1');
INSERT INTO "public"."sys_dict_data" VALUES (950502, NULL, '2026-05-18 20:52:49.830232', 0, NULL, '2026-05-18 20:52:49.830232', 9505, '下架', '', 2, 1, '2');
INSERT INTO "public"."sys_dict_data" VALUES (950601, NULL, '2026-05-18 20:52:49.833688', 0, NULL, '2026-05-18 20:52:49.833688', 9506, '待付款', '', 1, 1, '0');
INSERT INTO "public"."sys_dict_data" VALUES (950602, NULL, '2026-05-18 20:52:49.833688', 0, NULL, '2026-05-18 20:52:49.833688', 9506, '待发货', '', 2, 1, '1');
INSERT INTO "public"."sys_dict_data" VALUES (950603, NULL, '2026-05-18 20:52:49.833688', 0, NULL, '2026-05-18 20:52:49.833688', 9506, '待收货', '', 3, 1, '2');
INSERT INTO "public"."sys_dict_data" VALUES (950604, NULL, '2026-05-18 20:52:49.833688', 0, NULL, '2026-05-18 20:52:49.833688', 9506, '已完成', '', 4, 1, '3');
INSERT INTO "public"."sys_dict_data" VALUES (950605, NULL, '2026-05-18 20:52:49.833688', 0, NULL, '2026-05-18 20:52:49.833688', 9506, '已取消', '', 5, 1, '4');
INSERT INTO "public"."sys_dict_data" VALUES (950606, NULL, '2026-05-18 20:52:49.833688', 0, NULL, '2026-05-18 20:52:49.833688', 9506, '退款中', '', 6, 1, '5');
INSERT INTO "public"."sys_dict_data" VALUES (950607, NULL, '2026-05-18 20:52:49.833688', 0, NULL, '2026-05-18 20:52:49.833688', 9506, '已退款', '', 7, 1, '6');
INSERT INTO "public"."sys_dict_data" VALUES (950701, NULL, '2026-05-18 20:52:49.835596', 0, NULL, '2026-05-18 20:52:49.835596', 9507, 'PC', '', 1, 1, '1');
INSERT INTO "public"."sys_dict_data" VALUES (950702, NULL, '2026-05-18 20:52:49.835596', 0, NULL, '2026-05-18 20:52:49.835596', 9507, 'H5', '', 2, 1, '2');
INSERT INTO "public"."sys_dict_data" VALUES (950703, NULL, '2026-05-18 20:52:49.835596', 0, NULL, '2026-05-18 20:52:49.835596', 9507, '小程序', '', 3, 1, '3');
INSERT INTO "public"."sys_dict_data" VALUES (950704, NULL, '2026-05-18 20:52:49.835596', 0, NULL, '2026-05-18 20:52:49.835596', 9507, 'APP', '', 4, 1, '4');
INSERT INTO "public"."sys_dict_data" VALUES (950705, NULL, '2026-05-18 20:52:49.835596', 0, NULL, '2026-05-18 20:52:49.835596', 9507, '后台', '', 5, 1, '5');
INSERT INTO "public"."sys_dict_data" VALUES (950706, NULL, '2026-05-18 20:52:49.835596', 0, NULL, '2026-05-18 20:52:49.835596', 9507, '系统', '', 6, 1, '6');
INSERT INTO "public"."sys_dict_data" VALUES (950801, NULL, '2026-05-18 20:52:49.837663', 0, NULL, '2026-05-18 20:52:49.837663', 9508, '未退款', '', 1, 1, '0');
INSERT INTO "public"."sys_dict_data" VALUES (950802, NULL, '2026-05-18 20:52:49.837663', 0, NULL, '2026-05-18 20:52:49.837663', 9508, '退款中', '', 2, 1, '1');
INSERT INTO "public"."sys_dict_data" VALUES (950803, NULL, '2026-05-18 20:52:49.837663', 0, NULL, '2026-05-18 20:52:49.837663', 9508, '已退款', '', 3, 1, '2');
INSERT INTO "public"."sys_dict_data" VALUES (950804, NULL, '2026-05-18 20:52:49.837663', 0, NULL, '2026-05-18 20:52:49.837663', 9508, '退款失败', '', 4, 1, '3');
INSERT INTO "public"."sys_dict_data" VALUES (950901, NULL, '2026-05-18 20:52:49.839795', 0, NULL, '2026-05-18 20:52:49.839795', 9509, '仅退款', '', 1, 1, '1');
INSERT INTO "public"."sys_dict_data" VALUES (950902, NULL, '2026-05-18 20:52:49.839795', 0, NULL, '2026-05-18 20:52:49.839795', 9509, '退货退款', '', 2, 1, '2');
INSERT INTO "public"."sys_dict_data" VALUES (950903, NULL, '2026-05-18 20:52:49.839795', 0, NULL, '2026-05-18 20:52:49.839795', 9509, '换货', '', 3, 1, '3');
INSERT INTO "public"."sys_dict_data" VALUES (951001, NULL, '2026-05-18 20:52:49.850908', 0, NULL, '2026-05-18 20:52:49.850908', 9510, '待审核', '', 1, 1, '1');
INSERT INTO "public"."sys_dict_data" VALUES (951002, NULL, '2026-05-18 20:52:49.850908', 0, NULL, '2026-05-18 20:52:49.850908', 9510, '审核通过待打款', '', 2, 1, '2');
INSERT INTO "public"."sys_dict_data" VALUES (951003, NULL, '2026-05-18 20:52:49.850908', 0, NULL, '2026-05-18 20:52:49.850908', 9510, '退货中', '', 3, 1, '3');
INSERT INTO "public"."sys_dict_data" VALUES (951004, NULL, '2026-05-18 20:52:49.850908', 0, NULL, '2026-05-18 20:52:49.850908', 9510, '已完成', '', 4, 1, '4');
INSERT INTO "public"."sys_dict_data" VALUES (951005, NULL, '2026-05-18 20:52:49.850908', 0, NULL, '2026-05-18 20:52:49.850908', 9510, '已拒绝', '', 5, 1, '5');
INSERT INTO "public"."sys_dict_data" VALUES (951006, NULL, '2026-05-18 20:52:49.850908', 0, NULL, '2026-05-18 20:52:49.850908', 9510, '已撤销', '', 6, 1, '6');
INSERT INTO "public"."sys_dict_data" VALUES (951101, NULL, '2026-05-18 20:52:49.863121', 0, NULL, '2026-05-18 20:52:49.863121', 9511, '按件', '', 1, 1, '1');
INSERT INTO "public"."sys_dict_data" VALUES (951102, NULL, '2026-05-18 20:52:49.863121', 0, NULL, '2026-05-18 20:52:49.863121', 9511, '按重量', '', 2, 1, '2');
INSERT INTO "public"."sys_dict_data" VALUES (951103, NULL, '2026-05-18 20:52:49.863121', 0, NULL, '2026-05-18 20:52:49.863121', 9511, '按体积', '', 3, 1, '3');
INSERT INTO "public"."sys_dict_data" VALUES (951201, NULL, '2026-05-18 20:52:49.864852', 0, NULL, '2026-05-18 20:52:49.864852', 9512, '不包邮', '', 1, 1, '0');
INSERT INTO "public"."sys_dict_data" VALUES (951202, NULL, '2026-05-18 20:52:49.864852', 0, NULL, '2026-05-18 20:52:49.864852', 9512, '全国包邮', '', 2, 1, '1');
INSERT INTO "public"."sys_dict_data" VALUES (951203, NULL, '2026-05-18 20:52:49.864852', 0, NULL, '2026-05-18 20:52:49.864852', 9512, '满金额包邮', '', 3, 1, '2');
INSERT INTO "public"."sys_dict_data" VALUES (951204, NULL, '2026-05-18 20:52:49.864852', 0, NULL, '2026-05-18 20:52:49.864852', 9512, '满件数包邮', '', 4, 1, '3');
INSERT INTO "public"."sys_dict_data" VALUES (951205, NULL, '2026-05-18 20:52:49.864852', 0, NULL, '2026-05-18 20:52:49.864852', 9512, '满重量包邮', '', 5, 1, '4');
INSERT INTO "public"."sys_dict_data" VALUES (951301, NULL, '2026-05-18 20:52:49.866602', 0, NULL, '2026-05-18 20:52:49.866602', 9513, '用户', '', 1, 1, '1');
INSERT INTO "public"."sys_dict_data" VALUES (951302, NULL, '2026-05-18 20:52:49.866602', 0, NULL, '2026-05-18 20:52:49.866602', 9513, '商家', '', 2, 1, '2');
INSERT INTO "public"."sys_dict_data" VALUES (951303, NULL, '2026-05-18 20:52:49.866602', 0, NULL, '2026-05-18 20:52:49.866602', 9513, '系统', '', 3, 1, '3');
INSERT INTO "public"."sys_dict_data" VALUES (951304, NULL, '2026-05-18 20:52:49.866602', 0, NULL, '2026-05-18 20:52:49.866602', 9513, '管理员', '', 4, 1, '4');
INSERT INTO "public"."sys_dict_data" VALUES (951401, NULL, '2026-05-18 20:52:49.86882', 0, NULL, '2026-05-18 20:52:49.86882', 9514, '创建订单', '', 1, 1, 'CREATE');
INSERT INTO "public"."sys_dict_data" VALUES (951402, NULL, '2026-05-18 20:52:49.86882', 0, NULL, '2026-05-18 20:52:49.86882', 9514, '支付', '', 2, 1, 'PAY');
INSERT INTO "public"."sys_dict_data" VALUES (951403, NULL, '2026-05-18 20:52:49.86882', 0, NULL, '2026-05-18 20:52:49.86882', 9514, '发货', '', 3, 1, 'SHIP');
INSERT INTO "public"."sys_dict_data" VALUES (951404, NULL, '2026-05-18 20:52:49.86882', 0, NULL, '2026-05-18 20:52:49.86882', 9514, '签收', '', 4, 1, 'RECEIVE');
INSERT INTO "public"."sys_dict_data" VALUES (951405, NULL, '2026-05-18 20:52:49.86882', 0, NULL, '2026-05-18 20:52:49.86882', 9514, '确认完成', '', 5, 1, 'CONFIRM');
INSERT INTO "public"."sys_dict_data" VALUES (951406, NULL, '2026-05-18 20:52:49.86882', 0, NULL, '2026-05-18 20:52:49.86882', 9514, '取消', '', 6, 1, 'CANCEL');
INSERT INTO "public"."sys_dict_data" VALUES (951407, NULL, '2026-05-18 20:52:49.86882', 0, NULL, '2026-05-18 20:52:49.86882', 9514, '关闭', '', 7, 1, 'CLOSE');
INSERT INTO "public"."sys_dict_data" VALUES (951408, NULL, '2026-05-18 20:52:49.86882', 0, NULL, '2026-05-18 20:52:49.86882', 9514, '申请退款', '', 8, 1, 'REFUND_APPLY');
INSERT INTO "public"."sys_dict_data" VALUES (951409, NULL, '2026-05-18 20:52:49.86882', 0, NULL, '2026-05-18 20:52:49.86882', 9514, '退款审核通过', '', 9, 1, 'REFUND_PASS');
INSERT INTO "public"."sys_dict_data" VALUES (951410, NULL, '2026-05-18 20:52:49.86882', 0, NULL, '2026-05-18 20:52:49.86882', 9514, '退款被拒', '', 10, 1, 'REFUND_REJECT');
INSERT INTO "public"."sys_dict_data" VALUES (951411, NULL, '2026-05-18 20:52:49.86882', 0, NULL, '2026-05-18 20:52:49.86882', 9514, '退款完成', '', 11, 1, 'REFUND_DONE');
INSERT INTO "public"."sys_dict_data" VALUES (951412, NULL, '2026-05-18 20:52:49.86882', 0, NULL, '2026-05-18 20:52:49.86882', 9514, '评价', '', 12, 1, 'COMMENT');
INSERT INTO "public"."sys_dict_data" VALUES (951413, NULL, '2026-05-18 20:52:49.86882', 0, NULL, '2026-05-18 20:52:49.86882', 9514, '备注', '', 13, 1, 'REMARK');
INSERT INTO "public"."sys_dict_data" VALUES (951414, NULL, '2026-05-18 20:52:49.86882', 0, NULL, '2026-05-18 20:52:49.86882', 9514, '其它', '', 14, 1, 'OTHER');
INSERT INTO "public"."sys_dict_data" VALUES (960001, NULL, '2026-05-26 20:32:34.608106', 0, NULL, '2026-05-26 20:32:34.608106', 9600, '文章', '', 1, 1, '1');
INSERT INTO "public"."sys_dict_data" VALUES (960002, NULL, '2026-05-26 20:32:34.608106', 0, NULL, '2026-05-26 20:32:34.608106', 9600, '帖子', '', 2, 1, '2');
INSERT INTO "public"."sys_dict_data" VALUES (960003, NULL, '2026-05-26 20:32:34.608106', 0, NULL, '2026-05-26 20:32:34.608106', 9600, '知识库', '', 3, 1, '3');
INSERT INTO "public"."sys_dict_data" VALUES (960101, NULL, '2026-05-26 20:32:34.612003', 0, NULL, '2026-05-26 20:32:34.612003', 9601, '草稿', '', 1, 1, '0');
INSERT INTO "public"."sys_dict_data" VALUES (960102, NULL, '2026-05-26 20:32:34.612003', 0, NULL, '2026-05-26 20:32:34.612003', 9601, '已发布', '', 2, 1, '1');
INSERT INTO "public"."sys_dict_data" VALUES (960103, NULL, '2026-05-26 20:32:34.612003', 0, NULL, '2026-05-26 20:32:34.612003', 9601, '已下线', '', 3, 1, '2');
INSERT INTO "public"."sys_dict_data" VALUES (960201, NULL, '2026-05-26 20:32:34.613915', 0, NULL, '2026-05-26 20:32:34.613915', 9602, '未提交', '', 1, 1, '0');
INSERT INTO "public"."sys_dict_data" VALUES (960202, NULL, '2026-05-26 20:32:34.613915', 0, NULL, '2026-05-26 20:32:34.613915', 9602, '待审核', '', 2, 1, '1');
INSERT INTO "public"."sys_dict_data" VALUES (960203, NULL, '2026-05-26 20:32:34.613915', 0, NULL, '2026-05-26 20:32:34.613915', 9602, '通过', '', 3, 1, '2');
INSERT INTO "public"."sys_dict_data" VALUES (960204, NULL, '2026-05-26 20:32:34.613915', 0, NULL, '2026-05-26 20:32:34.613915', 9602, '驳回', '', 4, 1, '3');
INSERT INTO "public"."sys_dict_data" VALUES (960301, NULL, '2026-05-26 20:32:34.618667', 0, NULL, '2026-05-26 20:32:34.618667', 9603, '正常', '', 1, 1, '1');
INSERT INTO "public"."sys_dict_data" VALUES (960302, NULL, '2026-05-26 20:32:34.618667', 0, NULL, '2026-05-26 20:32:34.618667', 9603, '待审核', '', 2, 1, '2');
INSERT INTO "public"."sys_dict_data" VALUES (960303, NULL, '2026-05-26 20:32:34.618667', 0, NULL, '2026-05-26 20:32:34.618667', 9603, '屏蔽', '', 3, 1, '3');
INSERT INTO "public"."sys_dict_data" VALUES (960401, NULL, '2026-05-26 20:32:34.621138', 0, NULL, '2026-05-26 20:32:34.621138', 9604, '文字', '', 1, 1, '1');
INSERT INTO "public"."sys_dict_data" VALUES (960402, NULL, '2026-05-26 20:32:34.621138', 0, NULL, '2026-05-26 20:32:34.621138', 9604, '图标+文字', '', 2, 1, '2');
INSERT INTO "public"."sys_dict_data" VALUES (960403, NULL, '2026-05-26 20:32:34.621138', 0, NULL, '2026-05-26 20:32:34.621138', 9604, '图片', '', 3, 1, '3');
INSERT INTO "public"."sys_dict_data" VALUES (960501, NULL, '2026-05-26 20:32:34.62343', 0, NULL, '2026-05-26 20:32:34.62343', 9605, '点赞', '', 1, 1, '1');
INSERT INTO "public"."sys_dict_data" VALUES (960502, NULL, '2026-05-26 20:32:34.62343', 0, NULL, '2026-05-26 20:32:34.62343', 9605, '点踩', '', 2, 1, '2');
INSERT INTO "public"."sys_dict_data" VALUES (960601, NULL, '2026-05-26 20:32:34.625742', 0, NULL, '2026-05-26 20:32:34.625742', 9606, '内容', '', 1, 1, '1');
INSERT INTO "public"."sys_dict_data" VALUES (960602, NULL, '2026-05-26 20:32:34.625742', 0, NULL, '2026-05-26 20:32:34.625742', 9606, '评论', '', 2, 1, '2');
INSERT INTO "public"."sys_dict_data" VALUES (960701, NULL, '2026-05-26 20:32:34.628188', 0, NULL, '2026-05-26 20:32:34.628188', 9607, '垃圾广告', '', 1, 1, '1');
INSERT INTO "public"."sys_dict_data" VALUES (960702, NULL, '2026-05-26 20:32:34.628188', 0, NULL, '2026-05-26 20:32:34.628188', 9607, '辱骂攻击', '', 2, 1, '2');
INSERT INTO "public"."sys_dict_data" VALUES (960703, NULL, '2026-05-26 20:32:34.628188', 0, NULL, '2026-05-26 20:32:34.628188', 9607, '涉黄低俗', '', 3, 1, '3');
INSERT INTO "public"."sys_dict_data" VALUES (960704, NULL, '2026-05-26 20:32:34.628188', 0, NULL, '2026-05-26 20:32:34.628188', 9607, '涉政敏感', '', 4, 1, '4');
INSERT INTO "public"."sys_dict_data" VALUES (960705, NULL, '2026-05-26 20:32:34.628188', 0, NULL, '2026-05-26 20:32:34.628188', 9607, '其它', '', 5, 1, '99');
INSERT INTO "public"."sys_dict_data" VALUES (960801, NULL, '2026-05-26 20:32:34.630426', 0, NULL, '2026-05-26 20:32:34.630426', 9608, '待处理', '', 1, 1, '1');
INSERT INTO "public"."sys_dict_data" VALUES (960802, NULL, '2026-05-26 20:32:34.630426', 0, NULL, '2026-05-26 20:32:34.630426', 9608, '已处理', '', 2, 1, '2');
INSERT INTO "public"."sys_dict_data" VALUES (960803, NULL, '2026-05-26 20:32:34.630426', 0, NULL, '2026-05-26 20:32:34.630426', 9608, '已驳回', '', 3, 1, '3');

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_dict_type";
CREATE TABLE "public"."sys_dict_type" (
  "id" int8 NOT NULL,
  "create_by" int8,
  "create_time" timestamp(6),
  "deleted" int4,
  "update_by" int8,
  "update_time" timestamp(6),
  "name" varchar(255) COLLATE "pg_catalog"."default",
  "remark" varchar(255) COLLATE "pg_catalog"."default",
  "status" int4,
  "type" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
INSERT INTO "public"."sys_dict_type" VALUES (313546470873763840, NULL, '2026-03-29 11:35:04.068933', 0, NULL, '2026-03-29 11:35:04.068933', '数据状态', '', 1, 'status');
INSERT INTO "public"."sys_dict_type" VALUES (313546623328325632, NULL, '2026-03-29 11:35:40.416541', 0, NULL, '2026-03-29 11:35:52.577524', '性别', '', 1, 'sex');
INSERT INTO "public"."sys_dict_type" VALUES (321770235235536896, 1, '2026-04-21 04:13:22.282312', 0, 1, '2026-04-21 04:13:22.282312', '消息配置类型', '', 1, 'message_config_type');
INSERT INTO "public"."sys_dict_type" VALUES (322906994128326656, 1, '2026-04-24 07:30:26.722846', 0, 1, '2026-04-24 07:30:26.722846', '消息发送状态', '', 1, 'message_record_status');
INSERT INTO "public"."sys_dict_type" VALUES (322907458316144640, 1, '2026-04-24 07:32:17.393548', 0, 1, '2026-04-24 07:32:17.393548', '消息发送类型', '', 1, 'message_record_type');
INSERT INTO "public"."sys_dict_type" VALUES (324368936110002176, 1, '2026-04-28 08:19:40.852406', 0, 1, '2026-04-28 08:19:40.852406', '文件配置类型', '', 1, 'file_config_type');
INSERT INTO "public"."sys_dict_type" VALUES (9500, NULL, '2026-05-18 20:52:49.810841', 0, NULL, '2026-05-18 20:52:49.810841', '通用-是否', '通用是/否字典', 1, 'yes_no');
INSERT INTO "public"."sys_dict_type" VALUES (9501, NULL, '2026-05-18 20:52:49.821255', 0, NULL, '2026-05-18 20:52:49.821255', '商城-支付方式', '现金/积分/混合', 1, 'mall_pay_type');
INSERT INTO "public"."sys_dict_type" VALUES (9502, NULL, '2026-05-18 20:52:49.82355', 0, NULL, '2026-05-18 20:52:49.82355', '商城-支付渠道', '微信/支付宝/余额/其它', 1, 'mall_pay_channel');
INSERT INTO "public"."sys_dict_type" VALUES (9503, NULL, '2026-05-18 20:52:49.82524', 0, NULL, '2026-05-18 20:52:49.82524', '商城-配送方式', '物流/无需物流/自提/同城', 1, 'mall_delivery_type');
INSERT INTO "public"."sys_dict_type" VALUES (9504, NULL, '2026-05-18 20:52:49.827418', 0, NULL, '2026-05-18 20:52:49.827418', '商城-虚拟商品类型', '', 1, 'mall_virtual_type');
INSERT INTO "public"."sys_dict_type" VALUES (9505, NULL, '2026-05-18 20:52:49.829312', 0, NULL, '2026-05-18 20:52:49.829312', '商城-商品状态', '上架/下架', 1, 'mall_product_status');
INSERT INTO "public"."sys_dict_type" VALUES (9506, NULL, '2026-05-18 20:52:49.832249', 0, NULL, '2026-05-18 20:52:49.832249', '商城-订单状态', '', 1, 'mall_order_status');
INSERT INTO "public"."sys_dict_type" VALUES (9507, NULL, '2026-05-18 20:52:49.834806', 0, NULL, '2026-05-18 20:52:49.834806', '商城-订单来源', '', 1, 'mall_order_source');
INSERT INTO "public"."sys_dict_type" VALUES (9508, NULL, '2026-05-18 20:52:49.836525', 0, NULL, '2026-05-18 20:52:49.836525', '商城-订单行退款状态', '', 1, 'mall_item_refund_status');
INSERT INTO "public"."sys_dict_type" VALUES (9509, NULL, '2026-05-18 20:52:49.838719', 0, NULL, '2026-05-18 20:52:49.838719', '商城-退款类型', '', 1, 'mall_refund_type');
INSERT INTO "public"."sys_dict_type" VALUES (9510, NULL, '2026-05-18 20:52:49.840872', 0, NULL, '2026-05-18 20:52:49.840872', '商城-退款单状态', '', 1, 'mall_refund_apply_status');
INSERT INTO "public"."sys_dict_type" VALUES (9511, NULL, '2026-05-18 20:52:49.861493', 0, NULL, '2026-05-18 20:52:49.861493', '商城-运费计费方式', '', 1, 'mall_freight_charge_type');
INSERT INTO "public"."sys_dict_type" VALUES (9512, NULL, '2026-05-18 20:52:49.864042', 0, NULL, '2026-05-18 20:52:49.864042', '商城-包邮方式', '', 1, 'mall_freight_free_type');
INSERT INTO "public"."sys_dict_type" VALUES (9513, NULL, '2026-05-18 20:52:49.865748', 0, NULL, '2026-05-18 20:52:49.865748', '商城-日志操作人类型', '', 1, 'mall_log_operator_type');
INSERT INTO "public"."sys_dict_type" VALUES (9514, NULL, '2026-05-18 20:52:49.86771', 0, NULL, '2026-05-18 20:52:49.86771', '商城-订单日志动作', '', 1, 'mall_order_log_action');
INSERT INTO "public"."sys_dict_type" VALUES (9600, NULL, '2026-05-26 20:32:34.601462', 0, NULL, '2026-05-26 20:32:34.601462', '内容-类型', 'ContentInfo.type', 1, 'content_type');
INSERT INTO "public"."sys_dict_type" VALUES (9601, NULL, '2026-05-26 20:32:34.611018', 0, NULL, '2026-05-26 20:32:34.611018', '内容-发布状态', 'ContentInfo.publishStatus', 1, 'content_publish_status');
INSERT INTO "public"."sys_dict_type" VALUES (9602, NULL, '2026-05-26 20:32:34.613007', 0, NULL, '2026-05-26 20:32:34.613007', '内容-审核状态', 'ContentInfo.auditStatus / ContentAuditLog.auditStatus', 1, 'content_audit_status');
INSERT INTO "public"."sys_dict_type" VALUES (9603, NULL, '2026-05-26 20:32:34.617114', 0, NULL, '2026-05-26 20:32:34.617114', '内容-评论状态', 'ContentComment.status', 1, 'content_comment_status');
INSERT INTO "public"."sys_dict_type" VALUES (9604, NULL, '2026-05-26 20:32:34.6199', 0, NULL, '2026-05-26 20:32:34.6199', '内容-标签展示类型', 'ContentTag.displayType', 1, 'content_tag_display_type');
INSERT INTO "public"."sys_dict_type" VALUES (9605, NULL, '2026-05-26 20:32:34.622502', 0, NULL, '2026-05-26 20:32:34.622502', '内容-评论点赞操作', 'ContentCommentLike.action', 1, 'content_comment_like_action');
INSERT INTO "public"."sys_dict_type" VALUES (9606, NULL, '2026-05-26 20:32:34.624798', 0, NULL, '2026-05-26 20:32:34.624798', '内容-举报目标类型', 'ContentReport.targetType', 1, 'content_report_target_type');
INSERT INTO "public"."sys_dict_type" VALUES (9607, NULL, '2026-05-26 20:32:34.626575', 0, NULL, '2026-05-26 20:32:34.626575', '内容-举报原因类型', 'ContentReport.reasonType', 1, 'content_report_reason_type');
INSERT INTO "public"."sys_dict_type" VALUES (9608, NULL, '2026-05-26 20:32:34.629408', 0, NULL, '2026-05-26 20:32:34.629408', '内容-举报处理状态', 'ContentReport.status', 1, 'content_report_status');

-- ----------------------------
-- Table structure for sys_idempotent_duplicate_log
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_idempotent_duplicate_log";
CREATE TABLE "public"."sys_idempotent_duplicate_log" (
  "id" int8 NOT NULL,
  "create_by" int8,
  "create_time" timestamp(6),
  "deleted" int4,
  "update_by" int8,
  "update_time" timestamp(6),
  "duplicate_count" int4,
  "first_request_time" timestamp(6),
  "ip_address" varchar(255) COLLATE "pg_catalog"."default",
  "last_duplicate_time" timestamp(6),
  "message" varchar(255) COLLATE "pg_catalog"."default",
  "prefix" varchar(255) COLLATE "pg_catalog"."default",
  "request_id" varchar(255) COLLATE "pg_catalog"."default",
  "request_method" varchar(255) COLLATE "pg_catalog"."default",
  "request_params" text COLLATE "pg_catalog"."default",
  "request_url" varchar(255) COLLATE "pg_catalog"."default",
  "title" varchar(255) COLLATE "pg_catalog"."default",
  "user_agent" varchar(255) COLLATE "pg_catalog"."default",
  "user_id" int8,
  "username" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of sys_idempotent_duplicate_log
-- ----------------------------
INSERT INTO "public"."sys_idempotent_duplicate_log" VALUES (317003170234634240, 1, '2026-04-08 00:30:45.418954', 0, 1, '2026-04-08 00:30:45.418954', 1, '2026-04-08 00:30:45.416947', '0:0:0:0:0:0:0:1', '2026-04-08 00:30:45.416947', '请求正在处理中，请勿重复提交', 'update:sys:user:', '12345', 'PUT', 'SysUserUpdate(id=1, username=admin, nickname=管理员, email=2@qq.com, phone=18711321234, gender=1, status=1, departmentId=313513559294349312, postId=313512533233373184, roleIds=[311703722914877440], avatar=http://127.0.0.1/file/2026/04/04/beab13a3ddd6e0fd1a2787f32c338a53.png)', '/sys/users', '后台用户修改', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/146.0.0.0 Safari/537.36', 1, NULL);

-- ----------------------------
-- Table structure for sys_operation_log
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_operation_log";
CREATE TABLE "public"."sys_operation_log" (
  "id" int8 NOT NULL,
  "create_by" int8,
  "create_time" timestamp(6),
  "deleted" int4,
  "update_by" int8,
  "update_time" timestamp(6),
  "action" varchar(255) COLLATE "pg_catalog"."default",
  "class_name" varchar(255) COLLATE "pg_catalog"."default",
  "description" text COLLATE "pg_catalog"."default",
  "error_msg" text COLLATE "pg_catalog"."default",
  "http_method" varchar(255) COLLATE "pg_catalog"."default",
  "ip" varchar(255) COLLATE "pg_catalog"."default",
  "method_name" varchar(255) COLLATE "pg_catalog"."default",
  "request_params" text COLLATE "pg_catalog"."default",
  "response_data" text COLLATE "pg_catalog"."default",
  "success" bool,
  "time_cost" int8,
  "type" varchar(255) COLLATE "pg_catalog"."default",
  "url" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of sys_operation_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_permissions
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_permissions";
CREATE TABLE "public"."sys_permissions" (
  "id" int8 NOT NULL,
  "create_by" int8,
  "create_time" timestamp(6),
  "deleted" int4,
  "update_by" int8,
  "update_time" timestamp(6),
  "application_code" varchar(255) COLLATE "pg_catalog"."default",
  "application_id" int8,
  "code" varchar(255) COLLATE "pg_catalog"."default",
  "component" varchar(255) COLLATE "pg_catalog"."default",
  "component_name" varchar(255) COLLATE "pg_catalog"."default",
  "icon" varchar(255) COLLATE "pg_catalog"."default",
  "parent_id" int8,
  "sort" int4,
  "status" int4,
  "title" varchar(255) COLLATE "pg_catalog"."default",
  "type" int4,
  "url" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of sys_permissions
-- ----------------------------
INSERT INTO "public"."sys_permissions" VALUES (312425344407834624, NULL, '2026-03-26 09:20:06.68943', 0, NULL, '2026-03-26 09:21:00.579677', '', NULL, 'admin:system:permissions:add', '', '', '', 311760876300537856, 0, 1, '添加', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (312425344483332096, NULL, '2026-03-26 09:20:06.707809', 0, NULL, '2026-03-26 09:21:03.807688', '', NULL, 'admin:system:permissions:delete', '', '', '', 311760876300537856, 0, 1, '删除', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (312425344453971968, NULL, '2026-03-26 09:20:06.700433', 0, NULL, '2026-03-26 09:21:13.360743', '', NULL, 'admin:system:permissions:update', '', '', '', 311760876300537856, 0, 1, '修改', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (312424744588808192, NULL, '2026-03-26 09:17:43.681182', 0, NULL, '2026-03-26 09:21:17.226802', '', NULL, 'admin:system:role:update', '', '', '', 311762528248139776, 0, 1, '修改', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (312424744626556928, NULL, '2026-03-26 09:17:43.691127', 0, NULL, '2026-03-26 09:21:22.577432', '', NULL, 'admin:system:role:delete', '', '', '', 311762528248139776, 0, 1, '删除', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (312424744555253760, NULL, '2026-03-26 09:17:43.673872', 0, NULL, '2026-03-26 09:21:31.897463', '', NULL, 'admin:system:role:add', '', '', '', 311762528248139776, 0, 1, '添加', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (312424193062998016, NULL, '2026-03-26 09:15:32.187394', 0, NULL, '2026-03-26 09:21:39.386872', '', NULL, 'admin:system:user:delete', '', '', '', 311762357514801152, 0, 1, '删除', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (312424193046220800, NULL, '2026-03-26 09:15:32.183071', 0, NULL, '2026-03-26 09:21:43.986619', '', NULL, 'admin:system:user:update', '', '', '', 311762357514801152, 0, 1, '修改', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (312424192979111936, NULL, '2026-03-26 09:15:32.16765', 0, NULL, '2026-03-26 09:21:52.330212', '', NULL, 'admin:system:user:add', '', '', '', 311762357514801152, 0, 1, '添加', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (311760876300537856, NULL, '2026-03-24 13:19:45.147798', 0, NULL, '2026-03-29 09:12:08.565358', '', NULL, 'admin:system:permissions:page', 'views/system/syspermissions/index', '', 'MenuOutlined', 311757014093139968, 1000, 1, '菜单管理', 1, '/system/syspermissions');
INSERT INTO "public"."sys_permissions" VALUES (313546319996260352, NULL, '2026-03-29 11:34:28.096738', 0, NULL, '2026-03-30 07:51:27.2148', '', NULL, 'admin:system:dict:type:page', 'views/system/sysdict/index', '', 'TableOutlined', 311757014093139968, 5, 1, '系统字典', 1, '/system/sysdict');
INSERT INTO "public"."sys_permissions" VALUES (313511610557796352, NULL, '2026-03-29 09:16:32.721868', 0, NULL, '2026-03-29 09:16:32.721868', '', NULL, 'admin:system:post:add', '', '', '', 313511173993664512, 0, 0, '添加', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (313511610620710912, NULL, '2026-03-29 09:16:32.736748', 0, NULL, '2026-03-29 09:16:32.736748', '', NULL, 'admin:system:post:update', '', '', '', 313511173993664512, 0, 0, '修改', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (313511610658459648, NULL, '2026-03-29 09:16:32.74561', 0, NULL, '2026-03-29 09:16:32.74561', '', NULL, 'admin:system:post:delete', '', '', '', 313511173993664512, 0, 0, '删除', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (313512229410574336, NULL, '2026-03-29 09:19:00.267106', 0, NULL, '2026-03-29 09:19:00.267106', '', NULL, 'admin:system:department:add', '', '', '', 313512003115290624, 0, 0, '添加', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (313512229460905984, NULL, '2026-03-29 09:19:00.279405', 0, NULL, '2026-03-29 09:19:00.279405', '', NULL, 'admin:system:department:update', '', '', '', 313512003115290624, 0, 0, '修改', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (313512229490266112, NULL, '2026-03-29 09:19:00.286571', 0, NULL, '2026-03-29 09:19:00.286571', '', NULL, 'admin:system:department:delete', '', '', '', 313512003115290624, 0, 0, '删除', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (314513638113284096, NULL, '2026-04-01 03:38:14.707616', 0, NULL, '2026-04-01 03:38:14.707616', '', NULL, '', '', '', 'FolderOpenOutlined', NULL, 0, 1, '文件管理', 0, '');
INSERT INTO "public"."sys_permissions" VALUES (313511173993664512, NULL, '2026-03-29 09:14:48.636976', 0, NULL, '2026-03-29 11:32:46.37862', '', NULL, 'admin:system:post:page', 'views/system/syspost/index', '', 'ScheduleOutlined', 311757014093139968, 3, 1, '岗位管理', 1, '/system/syspost');
INSERT INTO "public"."sys_permissions" VALUES (313512003115290624, NULL, '2026-03-29 09:18:06.314872', 0, NULL, '2026-03-29 11:32:51.761965', '', NULL, 'admin:system:department:page', 'views/system/sysdepartment/index', '', 'ClusterOutlined', 311757014093139968, 4, 1, '部门管理', 1, '/system/sysdepartment');
INSERT INTO "public"."sys_permissions" VALUES (314537408047747072, NULL, '2026-04-01 05:12:41.901201', 0, NULL, '2026-04-01 05:12:41.901201', '', NULL, 'admin:file:upload:chunk', '', '', '', 314536770404487168, 0, 0, '大文件', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (313549285599875072, NULL, '2026-03-29 11:46:15.151682', 0, NULL, '2026-03-29 11:46:15.151682', '', NULL, 'admin:system:config:page', 'views/system/sysconfig/index', '', 'ContainerOutlined', 311757014093139968, 5, 1, '系统配置', 1, '/system/sysconfig');
INSERT INTO "public"."sys_permissions" VALUES (313549686361427968, NULL, '2026-03-29 11:47:50.700002', 0, NULL, '2026-03-29 11:47:50.700002', '', NULL, 'admin:system:config:add', '', '', '', 313549285599875072, 0, 0, '添加', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (313549686420148224, NULL, '2026-03-29 11:47:50.714944', 0, NULL, '2026-03-29 11:47:50.714944', '', NULL, 'admin:system:config:update', '', '', '', 313549285599875072, 0, 0, '修改', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (313549686445314048, NULL, '2026-03-29 11:47:50.720589', 0, NULL, '2026-03-29 11:47:50.720589', '', NULL, 'admin:system:config:delete', '', '', '', 313549285599875072, 0, 0, '删除', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (311757014093139968, NULL, '2026-03-24 13:04:24.325961', 0, NULL, '2026-03-25 06:10:41.654908', '', NULL, '', '', '', 'SettingOutlined', NULL, 0, 1, '系统管理', 0, '');
INSERT INTO "public"."sys_permissions" VALUES (311762357514801152, NULL, '2026-03-24 13:25:38.296772', 0, NULL, '2026-03-26 09:13:43.276678', '', NULL, 'admin:system:user:page', 'views/system/sysuser/index', '', 'UserOutlined', 311757014093139968, 1, 1, '用户管理', 1, '/system/sysuser');
INSERT INTO "public"."sys_permissions" VALUES (311762528248139776, NULL, '2026-03-24 13:26:19.002246', 0, NULL, '2026-03-26 09:13:52.575951', '', NULL, 'admin:system:role:page', 'views/system/sysrole/index', '', 'UsergroupAddOutlined', 311757014093139968, 2, 1, '角色管理', 1, '/system/sysrole');
INSERT INTO "public"."sys_permissions" VALUES (313550330203869184, NULL, '2026-03-29 11:50:24.204617', 0, NULL, '2026-03-29 11:50:24.204617', '', NULL, 'admin:system:dict:type:add', '', '', '', 313546319996260352, 0, 0, '字典类型添加', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (313550330241617920, NULL, '2026-03-29 11:50:24.213574', 0, NULL, '2026-03-29 11:50:24.213574', '', NULL, 'admin:system:dict:type:update', '', '', '', 313546319996260352, 0, 0, '字典类型修改', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (313550330266783744, NULL, '2026-03-29 11:50:24.219964', 0, NULL, '2026-03-29 11:50:24.219964', '', NULL, 'admin:system:dict:type:delete', '', '', '', 313546319996260352, 0, 0, '字典类型删除', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (313550330304532480, NULL, '2026-03-29 11:50:24.228329', 0, NULL, '2026-03-29 11:50:24.228329', '', NULL, 'admin:system:dict:data:add', '', '', '', 313546319996260352, 0, 0, '字典数据添加', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (313550330333892608, NULL, '2026-03-29 11:50:24.235252', 0, NULL, '2026-03-29 11:50:24.235252', '', NULL, 'admin:system:dict:data:update', '', '', '', 313546319996260352, 0, 0, '字典数据修改', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (313550330359058432, NULL, '2026-03-29 11:50:24.241765', 0, NULL, '2026-03-29 11:50:24.241765', '', NULL, 'admin:system:dict:data:delete', '', '', '', 313546319996260352, 0, 0, '字典数据删除', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (313550330384224256, NULL, '2026-03-29 11:50:24.247363', 0, NULL, '2026-03-29 11:50:24.247363', '', NULL, 'admin:system:dict:data:page', '', '', '', 313546319996260352, 0, 0, '字典数据', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (314514382686130176, NULL, '2026-04-01 03:41:12.227622', 0, NULL, '2026-04-01 03:41:21.618836', '', NULL, 'admin:file:config:page', 'views/file/fileconfig/index', '', '', 314513638113284096, 0, 1, '文件配置', 1, '/file/fileconfig');
INSERT INTO "public"."sys_permissions" VALUES (314529426132045824, NULL, '2026-04-01 04:40:58.864516', 0, NULL, '2026-04-01 04:40:58.864516', '', NULL, 'admin:file:config:add', '', '', '', 314514382686130176, 0, 0, '添加', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (314529426207543296, NULL, '2026-04-01 04:40:58.882829', 0, NULL, '2026-04-01 04:40:58.882829', '', NULL, 'admin:file:config:update', '', '', '', 314514382686130176, 0, 0, '修改', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (314529426228514816, NULL, '2026-04-01 04:40:58.887138', 0, NULL, '2026-04-01 04:40:58.887138', '', NULL, 'admin:file:config:delete', '', '', '', 314514382686130176, 0, 0, '删除', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (314529906530848768, NULL, '2026-04-01 04:42:53.400687', 0, NULL, '2026-04-01 04:43:06.332966', '', NULL, 'admin:file:info:page', 'views/file/fileinfo/index', 'FileFileinfo', 'FileOutlined', 314513638113284096, 2, 1, '文件信息', 1, '/file/fileinfo');
INSERT INTO "public"."sys_permissions" VALUES (314530113607831552, NULL, '2026-04-01 04:43:42.771097', 0, NULL, '2026-04-01 04:43:42.771097', '', NULL, 'admin:file:info:add', '', '', '', 314529906530848768, 0, 0, '添加', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (314530113637191680, NULL, '2026-04-01 04:43:42.778721', 0, NULL, '2026-04-01 04:43:42.778721', '', NULL, 'admin:file:info:update', '', '', '', 314529906530848768, 0, 0, '修改', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (314530113658163200, NULL, '2026-04-01 04:43:42.783359', 0, NULL, '2026-04-01 04:43:42.783359', '', NULL, 'admin:file:info:delete', '', '', '', 314529906530848768, 0, 0, '删除', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (314536770404487168, NULL, '2026-04-01 05:10:09.875553', 0, NULL, '2026-04-01 05:12:26.425303', '', NULL, 'admin:file:upload:simple', 'views/file/fileupload/index', 'FileFileupload', '', 314513638113284096, 3, 1, '文件上传', 1, '/file/fileupload');
INSERT INTO "public"."sys_permissions" VALUES (314602586227478528, NULL, '2026-04-01 09:31:41.590497', 0, NULL, '2026-04-01 09:31:41.590497', '', NULL, 'admin:file:domain:add', '', '', '', 314602359294660608, 0, 0, '添加', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (314602586261032960, NULL, '2026-04-01 09:31:41.598461', 0, NULL, '2026-04-01 09:31:41.598461', '', NULL, 'admin:file:domain:update', '', '', '', 314602359294660608, 0, 0, '修改', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (314602586298781696, NULL, '2026-04-01 09:31:41.607137', 0, NULL, '2026-04-01 09:31:41.607137', '', NULL, 'admin:file:domain:delete', '', '', '', 314602359294660608, 0, 0, '删除', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (314602359294660608, NULL, '2026-04-01 09:30:47.485717', 0, NULL, '2026-04-01 09:32:19.319009', '', NULL, 'admin:file:domain:page', 'views/file/filedomain/index', 'FileFiledomain', '', 314513638113284096, 4, 1, '文件域名', 1, '/file/filedomain');
INSERT INTO "public"."sys_permissions" VALUES (316477984586469376, 1, '2026-04-06 13:43:51.402794', 0, 1, '2026-04-06 13:43:51.402794', '', NULL, 'admin:logs:operation:add', '', '', '', 316477772799283200, 0, 0, '添加', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (316477984615829504, 1, '2026-04-06 13:43:51.409429', 0, 1, '2026-04-06 13:43:51.409429', '', NULL, 'admin:logs:operation:update', '', '', '', 316477772799283200, 0, 0, '修改', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (316477984636801024, 1, '2026-04-06 13:43:51.414852', 0, 1, '2026-04-06 13:43:51.414852', '', NULL, 'admin:logs:operation:delete', '', '', '', 316477772799283200, 0, 0, '删除', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (321043647103635456, 1, '2026-04-19 04:06:10.172192', 0, 1, '2026-04-19 04:06:21.210196', '', NULL, 'admin:system:tenant:page', 'views/system/systenant/index', 'SystemSystenant', 'HddOutlined', 311757014093139968, 7, 1, '租户管理', 1, '/system/systenant');
INSERT INTO "public"."sys_permissions" VALUES (321043873273090048, 1, '2026-04-19 04:07:04.095705', 0, 1, '2026-04-19 04:07:04.095705', '', NULL, 'admin:system:tenant:add', '', '', '', 321043647103635456, 0, 0, '添加', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (316654743831318528, 1, '2026-04-07 01:26:14.090772', 0, 1, '2026-04-07 01:26:14.090772', '', NULL, 'admin:monitor:online:kickout', '', '', '', 316654270747381760, 0, 0, '踢出', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (316654743902621696, 1, '2026-04-07 01:26:14.107334', 0, 1, '2026-04-07 01:26:14.107334', '', NULL, 'admin:monitor:online:user', '', '', '', 316654270747381760, 0, 0, '在线统计', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (316654270747381760, 1, '2026-04-07 01:24:21.299472', 0, 1, '2026-04-07 01:28:27.26733', '', NULL, 'admin:monitor:online:user', 'views/monitor/onlineuser/index', 'MonitorOnlineuser', '', 316477320535871488, 1, 1, '在线监控', 1, '/monitor/onlineuser');
INSERT INTO "public"."sys_permissions" VALUES (316775517045002240, 1, '2026-04-07 09:26:08.668474', 0, 1, '2026-04-07 09:26:21.177149', '', NULL, 'monitor', 'views/monitor/duplicate-log/index', 'MonitorDuplicateLog', 'AlignCenterOutlined', 316477320535871488, 2, 1, '幕等日志', 1, '/monitor/duplicate-log');
INSERT INTO "public"."sys_permissions" VALUES (318994124692918272, 1, '2026-04-13 12:22:05.951754', 0, 1, '2026-04-13 12:22:05.951754', '', NULL, 'admin:ratelimit:global-config:page', 'views/ratelimit/global-config/index', 'RatelimitGlobalConfig', '', 318993678729351168, 0, 1, '全局限流配置', 1, '/ratelimit/global-config');
INSERT INTO "public"."sys_permissions" VALUES (318994357510344704, 1, '2026-04-13 12:23:01.459724', 0, 1, '2026-04-13 12:23:01.459724', '', NULL, 'admin:ratelimit:global-config:add', '', '', '', 318994124692918272, 0, 0, '添加', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (318994357548093440, 1, '2026-04-13 12:23:01.468757', 0, 1, '2026-04-13 12:23:01.468757', '', NULL, 'admin:ratelimit:global-config:update', '', '', '', 318994124692918272, 0, 0, '修改', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (318994357602619392, 1, '2026-04-13 12:23:01.481326', 0, 1, '2026-04-13 12:23:01.481326', '', NULL, 'admin:ratelimit:global-config:delete', '', '', '', 318994124692918272, 0, 0, '删除', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (318995237680844800, 1, '2026-04-13 12:26:31.308252', 0, 1, '2026-04-13 12:26:37.563248', '', NULL, 'admin:ratelimit:ip-config:page', 'views/ratelimit/ip-config/index', 'RatelimitIpConfig', '', 318993678729351168, 2, 1, 'IP限流配置', 1, '/ratelimit/ip-config');
INSERT INTO "public"."sys_permissions" VALUES (318995415292841984, 1, '2026-04-13 12:27:13.654005', 0, 1, '2026-04-13 12:27:13.654005', '', NULL, 'admin:ratelimit:ip-config:add', '', '', '', 318995237680844800, 0, 0, '添加', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (318995415326396416, 1, '2026-04-13 12:27:13.662788', 0, 1, '2026-04-13 12:27:13.662788', '', NULL, 'admin:ratelimit:ip-config:update', '', '', '', 318995237680844800, 0, 0, '修改', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (318995415359950848, 1, '2026-04-13 12:27:13.670229', 0, 1, '2026-04-13 12:27:13.670229', '', NULL, 'admin:ratelimit:ip-config:delete', '', '', '', 318995237680844800, 0, 0, '删除', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (318995950456672256, 1, '2026-04-13 12:29:21.247932', 0, 1, '2026-04-13 12:29:21.247932', '', NULL, 'admin:ratelimit:api-config:add', '', '', '', 318995774476259328, 0, 0, '添加', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (318995950507003904, 1, '2026-04-13 12:29:21.259888', 0, 1, '2026-04-13 12:29:21.259888', '', NULL, 'admin:ratelimit:api-config:update', '', '', '', 318995774476259328, 0, 0, '修改', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (318995950548946944, 1, '2026-04-13 12:29:21.269573', 0, 1, '2026-04-13 12:29:21.269573', '', NULL, 'admin:ratelimit:api-config:delete', '', '', '', 318995774476259328, 0, 0, '删除', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (318996311225536512, 1, '2026-04-13 12:30:47.261043', 0, 1, '2026-04-13 12:30:47.261043', '', NULL, 'admin:ratelimit:record:add', '', '', '', 318996165058236416, 0, 0, '添加', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (318996311263285248, 1, '2026-04-13 12:30:47.270147', 0, 1, '2026-04-13 12:30:47.270147', '', NULL, 'admin:ratelimit:record:update', '', '', '', 318996165058236416, 0, 0, '修改', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (318996311305228288, 1, '2026-04-13 12:30:47.280978', 0, 1, '2026-04-13 12:30:47.280978', '', NULL, 'admin:ratelimit:record:delete', '', '', '', 318996165058236416, 0, 0, '删除', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (318994978028261376, 1, '2026-04-13 12:25:29.40278', 1, 1, '2026-04-13 12:25:29.40278', '', NULL, 'admin:ratelimit:config:update', '', '', '', 318994824483180544, 0, 0, '修改', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (318994977986318336, 1, '2026-04-13 12:25:29.392722', 1, 1, '2026-04-13 12:25:29.392722', '', NULL, 'admin:ratelimit:config:add', '', '', '', 318994824483180544, 0, 0, '添加', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (318994978057621504, 1, '2026-04-13 12:25:29.409924', 1, 1, '2026-04-13 12:25:29.409924', '', NULL, 'admin:ratelimit:config:delete', '', '', '', 318994824483180544, 0, 0, '删除', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (318994824483180544, 1, '2026-04-13 12:24:52.794896', 1, 1, '2026-04-13 12:24:52.794896', '', NULL, 'admin:ratelimit:config:page', 'views/ratelimit/config/index', 'RatelimitConfig', '', 318993678729351168, 1, 1, '配置', 1, '/ratelimit/config');
INSERT INTO "public"."sys_permissions" VALUES (320780969307148288, 1, '2026-04-18 10:42:22.905111', 0, 1, '2026-04-18 10:42:22.905111', '', NULL, 'monitor:slow-query:delete', '', '', '', 320780885941161984, 0, 0, '删除', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (320780885941161984, 1, '2026-04-18 10:42:03.029374', 0, 1, '2026-04-18 10:42:34.381694', '', NULL, 'monitor:slow-query:list', 'views/monitor/slow-query/index', 'MonitorSlowQuery', 'ConsoleSqlOutlined', 316477320535871488, 4, 1, '慢sql日志', 1, '/monitor/slow-query');
INSERT INTO "public"."sys_permissions" VALUES (320987596123672576, 1, '2026-04-19 00:23:26.577904', 0, 1, '2026-04-19 00:23:26.577904', '', NULL, 'monitor:slow-query:update', '', '', '', 320780885941161984, 0, 0, '修改', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (321043874619461632, 1, '2026-04-19 04:07:04.416031', 0, 1, '2026-04-19 04:07:04.416031', '', NULL, 'admin:system:tenant:update', '', '', '', 321043647103635456, 0, 0, '修改', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (321043875705786368, 1, '2026-04-19 04:07:04.675861', 0, 1, '2026-04-19 04:07:04.675861', '', NULL, 'admin:system:tenant:delete', '', '', '', 321043647103635456, 0, 0, '删除', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (321768608076271616, 1, '2026-04-21 04:06:54.337268', 0, 1, '2026-04-21 04:07:06.591496', '', NULL, '', '', '', 'MessageOutlined', NULL, 5, 1, '消息管理', 0, '');
INSERT INTO "public"."sys_permissions" VALUES (321768925010464768, 1, '2026-04-21 04:08:09.900419', 0, 1, '2026-04-21 04:08:09.900419', '', NULL, 'admin:message:config:page', 'views/message/config/index', 'MessageConfig', 'ApiOutlined', 321768608076271616, 0, 1, '消息配置', 1, '/message/config');
INSERT INTO "public"."sys_permissions" VALUES (321769057961512960, 1, '2026-04-21 04:08:41.598704', 0, 1, '2026-04-21 04:08:41.598704', '', NULL, 'admin:message:config:add', '', '', '', 321768925010464768, 0, 0, '添加', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (321769059295301632, 1, '2026-04-21 04:08:41.916134', 0, 1, '2026-04-21 04:08:41.916134', '', NULL, 'admin:message:config:update', '', '', '', 321768925010464768, 0, 0, '修改', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (321769060406792192, 1, '2026-04-21 04:08:42.181146', 0, 1, '2026-04-21 04:08:42.181146', '', NULL, 'admin:message:config:delete', '', '', '', 321768925010464768, 0, 0, '删除', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (321774421545193472, 1, '2026-04-21 04:30:00.376681', 0, 1, '2026-04-21 04:30:00.376681', '', NULL, 'admin:message:type:page', 'views/message/type/index', 'MessageType', 'BlockOutlined', 321768608076271616, 1, 1, '消息类型', 1, '/message/type');
INSERT INTO "public"."sys_permissions" VALUES (321774596267315200, 1, '2026-04-21 04:30:42.033333', 0, 1, '2026-04-21 04:30:42.033333', '', NULL, 'admin:message:type:add', '', '', '', 321774421545193472, 0, 0, '添加', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (321774597626269696, 1, '2026-04-21 04:30:42.35776', 0, 1, '2026-04-21 04:30:42.35776', '', NULL, 'admin:message:type:update', '', '', '', 321774421545193472, 0, 0, '修改', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (321774598733565952, 1, '2026-04-21 04:30:42.62149', 0, 1, '2026-04-21 04:30:42.62149', '', NULL, 'admin:message:type:delete', '', '', '', 321774421545193472, 0, 0, '删除', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (321776690806263808, 1, '2026-04-21 04:39:01.410976', 0, 1, '2026-04-21 04:39:01.410976', '', NULL, 'admin:message:template:page', 'views/message/template/index', 'MessageTemplate', 'AppstoreOutlined', 321768608076271616, 3, 1, '消息模版', 1, '/message/template');
INSERT INTO "public"."sys_permissions" VALUES (321776882582425600, 1, '2026-04-21 04:39:47.133213', 0, 1, '2026-04-21 04:39:47.133213', '', NULL, 'admin:message:template:add', '', '', '', 321776690806263808, 0, 0, '添加', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (321776883953963008, 1, '2026-04-21 04:39:47.460747', 0, 1, '2026-04-21 04:39:47.460747', '', NULL, 'admin:message:template:update', '', '', '', 321776690806263808, 0, 0, '修改', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (321776884998344704, 1, '2026-04-21 04:39:47.709807', 0, 1, '2026-04-21 04:39:47.709807', '', NULL, 'admin:message:template:delete', '', '', '', 321776690806263808, 0, 0, '删除', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (318995774476259328, 1, '2026-04-13 12:28:39.290437', 0, 1, '2026-04-22 04:16:09.107424', '', NULL, 'admin:ratelimit:api-config:page', 'views/ratelimit/api-config/index', 'RatelimitApiConfig', '', 318993678729351168, 3, 1, 'API限流配置', 1, '/ratelimit/api-config');
INSERT INTO "public"."sys_permissions" VALUES (318996165058236416, 1, '2026-04-13 12:30:12.412186', 0, 1, '2026-04-22 04:16:15.847466', '', NULL, 'admin:ratelimit:record:page', 'views/ratelimit/record/index', 'RatelimitRecord', '', 318993678729351168, 4, 1, '限流记录', 1, '/ratelimit/record');
INSERT INTO "public"."sys_permissions" VALUES (318993678729351168, 1, '2026-04-13 12:20:19.625333', 0, 1, '2026-05-01 03:37:10.901718', '', NULL, '', '', '', 'ColumnWidthOutlined', NULL, 4, 1, '限流管理', 0, '');
INSERT INTO "public"."sys_permissions" VALUES (321835165842280448, 1, '2026-04-21 08:31:22.945429', 0, 1, '2026-04-21 08:31:33.301165', '', NULL, 'admin:message:verificationCode:page', 'views/message/verificationcode/index', 'MessageVerificationcode', 'CalculatorOutlined', 321768608076271616, 4, 1, '验证码', 1, '/message/verificationcode');
INSERT INTO "public"."sys_permissions" VALUES (321835324428914688, 1, '2026-04-21 08:32:00.755212', 0, 1, '2026-04-21 08:32:00.755212', '', NULL, 'admin:message:verificationCode:delete', '', 'MessageVerificationcode', '', 321835165842280448, 0, 1, '删除', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (321837457962307584, 1, '2026-04-21 08:40:29.42903', 0, 1, '2026-04-21 08:40:40.952103', '', NULL, 'admin:message:record:page', 'views/message/record/index', 'MessageRecord', 'BarsOutlined', 321768608076271616, 5, 1, '消息记录', 1, '/message/record');
INSERT INTO "public"."sys_permissions" VALUES (321837761428590592, 1, '2026-04-21 08:41:41.781528', 0, 1, '2026-04-21 08:41:41.781528', '', NULL, 'admin:message:record:delete', '', '', '', 321837457962307584, 0, 0, '删除', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (322133236576817152, 1, '2026-04-22 04:15:48.546735', 0, 1, '2026-04-22 04:15:48.546735', '', NULL, 'admin:ratelimit:ip-bw-config:page', 'views/ratelimit/ip-bw-config/index', 'RatelimitIpBwConfig', 'WifiOutlined', 318993678729351168, 5, 1, 'IP和名单', 1, '/ratelimit/ip-bw-config');
INSERT INTO "public"."sys_permissions" VALUES (322133527577628672, 1, '2026-04-22 04:16:57.92623', 0, 1, '2026-04-22 04:16:57.92623', '', NULL, 'admin:ratelimit:ip-bw-config:add', '', '', '', 322133236576817152, 0, 0, '添加', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (322133528571678720, 1, '2026-04-22 04:16:58.163692', 0, 1, '2026-04-22 04:16:58.163692', '', NULL, 'admin:ratelimit:ip-bw-config:update', '', '', '', 322133236576817152, 0, 0, '修改', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (322133528890445824, 1, '2026-04-22 04:16:58.239792', 0, 1, '2026-04-22 04:16:58.239792', '', NULL, 'admin:ratelimit:ip-bw-config:delete', '', '', '', 322133236576817152, 0, 0, '删除', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (316477320535871488, 1, '2026-04-06 13:41:13.080337', 0, 1, '2026-05-01 03:36:55.512202', '', NULL, '', '', '', 'VideoCameraOutlined', NULL, 3, 1, '监控管理', 0, '');
INSERT INTO "public"."sys_permissions" VALUES (322134032211120128, 1, '2026-04-22 04:18:58.240327', 0, 1, '2026-04-22 04:18:58.240327', '', NULL, 'admin:ratelimit:user-bw-config:add', '', '', '', 322133770276835328, 0, 0, '添加', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (322134033570074624, 1, '2026-04-22 04:18:58.564405', 0, 1, '2026-04-22 04:18:58.564405', '', NULL, 'admin:ratelimit:user-bw-config:update', '', '', '', 322133770276835328, 0, 0, '修改', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (322134034677370880, 1, '2026-04-22 04:18:58.828091', 0, 1, '2026-04-22 04:18:58.828091', '', NULL, 'admin:ratelimit:user-bw-config:delete', '', '', '', 322133770276835328, 0, 0, '删除', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (322133770276835328, 1, '2026-04-22 04:17:55.790315', 0, 1, '2026-04-22 04:19:41.914441', '', NULL, 'admin:ratelimit:user-bw-config:page', 'views/ratelimit/user-bw-config/index', 'RatelimitUserBwConfig', 'UserOutlined', 318993678729351168, 6, 1, '用户黑白名单', 1, '/ratelimit/user-bw-config');
INSERT INTO "public"."sys_permissions" VALUES (322527163695894528, 1, '2026-04-23 06:21:08.09097', 0, 1, '2026-04-23 06:21:08.09097', '', NULL, 'admin:message:test:send', 'views/message/test/index', 'MessageTest', 'BugOutlined', 321768608076271616, 6, 1, '消息测试', 1, '/message/test');
INSERT INTO "public"."sys_permissions" VALUES (322527291479560192, 1, '2026-04-23 06:21:38.556302', 0, 1, '2026-04-23 06:21:38.556302', '', NULL, 'admin:message:test:verify', '', '', '', 322527163695894528, 0, 0, '验证测试验证码', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (323170839119925248, 1, '2026-04-25 00:58:52.27052', 0, 1, '2026-04-25 00:58:52.27052', '', NULL, 'usergrowth:userlevelaccount:query', 'views/userGrowth/userlevelaccount/index', 'UsergrowthUserlevelaccount', 'UserOutlined', 322952165469589504, 0, 1, '成长账户', 1, '/userGrowth/userlevelaccount');
INSERT INTO "public"."sys_permissions" VALUES (323171045924278272, 1, '2026-04-25 00:59:41.576723', 0, 1, '2026-04-25 00:59:41.576723', '', NULL, 'usergrowth:userlevelaccount:add', '', '', '', 323170839119925248, 0, 0, '添加', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (323171046972854272, 1, '2026-04-25 00:59:41.82771', 0, 1, '2026-04-25 00:59:41.82771', '', NULL, 'usergrowth:userlevelaccount:update', '', '', '', 323170839119925248, 0, 0, '修改', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (323171047304204288, 1, '2026-04-25 00:59:41.905532', 0, 1, '2026-04-25 00:59:41.905532', '', NULL, 'usergrowth:userlevelaccount:delete', '', '', '', 323170839119925248, 0, 0, '删除', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (323171843106279424, 1, '2026-04-25 01:02:51.639809', 0, 1, '2026-04-25 01:02:51.639809', '', NULL, 'usergrowth:userlevelconfig:query', 'views/userGrowth/userlevelconfig/index', 'UsergrowthUserlevelconfig', 'AuditOutlined', 322952165469589504, 2, 1, '等级配置', 1, '/userGrowth/userlevelconfig');
INSERT INTO "public"."sys_permissions" VALUES (323172120530128896, 1, '2026-04-25 01:03:57.782963', 0, 1, '2026-04-25 01:03:57.782963', '', NULL, 'usergrowth:userlevelconfig:add', '', '', '', 323171843106279424, 0, 0, '添加', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (323172121557733376, 1, '2026-04-25 01:03:58.027233', 0, 1, '2026-04-25 01:03:58.027233', '', NULL, 'usergrowth:userlevelconfig:update', '', '', '', 323171843106279424, 0, 0, '修改', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (323172121876500480, 1, '2026-04-25 01:03:58.103921', 0, 1, '2026-04-25 01:03:58.103921', '', NULL, 'usergrowth:userlevelconfig:delete', '', '', '', 323171843106279424, 0, 0, '删除', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (323172541172682752, 1, '2026-04-25 01:05:38.071297', 0, 1, '2026-04-25 01:05:38.071297', '', NULL, 'usergrowth:userintegralrecord:query', 'views/userGrowth/userlevelrecord/index', 'UsergrowthUserlevelrecord', 'BarsOutlined', 322952165469589504, 3, 1, '成长值记录', 1, '/userGrowth/userlevelrecord');
INSERT INTO "public"."sys_permissions" VALUES (323172690603151360, 1, '2026-04-25 01:06:13.698878', 0, 1, '2026-04-25 01:06:13.698878', '', NULL, 'usergrowth:userintegralrecord:add', '', '', '', 323172541172682752, 0, 0, '添加', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (323172691987271680, 1, '2026-04-25 01:06:14.028394', 0, 1, '2026-04-25 01:06:14.028394', '', NULL, 'usergrowth:userintegralrecord:update', '', '', '', 323172541172682752, 0, 0, '修改', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (323172693027459072, 1, '2026-04-25 01:06:14.276912', 0, 1, '2026-04-25 01:06:14.276912', '', NULL, 'usergrowth:userintegralrecord:delete', '', '', '', 323172541172682752, 0, 0, '删除', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (323173706522300416, 1, '2026-04-25 01:10:15.912209', 0, 1, '2026-04-25 01:10:24.652681', '', NULL, 'usergrowth:userintegralaccount:query', 'views/userGrowth/userintegralaccount/index', 'UsergrowthUserintegralaccount', 'UserOutlined', 322952165469589504, 4, 1, '积分账户', 1, '/userGrowth/userintegralaccount');
INSERT INTO "public"."sys_permissions" VALUES (323173867587768320, 1, '2026-04-25 01:10:54.313653', 0, 1, '2026-04-25 01:10:54.313653', '', NULL, 'usergrowth:userintegralaccount:add', '', '', '', 323173706522300416, 0, 0, '添加', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (323173868942528512, 1, '2026-04-25 01:10:54.636404', 0, 1, '2026-04-25 01:10:54.636404', '', NULL, 'usergrowth:userintegralaccount:update', '', '', '', 323173706522300416, 0, 0, '修改', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (323173869974327296, 1, '2026-04-25 01:10:54.882942', 0, 1, '2026-04-25 01:10:54.882942', '', NULL, 'usergrowth:userintegralaccount:delete', '', '', '', 323173706522300416, 0, 0, '删除', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (323174207154425856, 1, '2026-04-25 01:12:15.272984', 0, 1, '2026-04-25 01:12:15.272984', '', NULL, 'usergrowth:userintegralrecord:query', 'views/userGrowth/userintegralrecord/index', 'UsergrowthUserintegralrecord', 'BarsOutlined', 322952165469589504, 5, 1, '积分流水', 1, '/userGrowth/userintegralrecord');
INSERT INTO "public"."sys_permissions" VALUES (323174354022174720, 1, '2026-04-25 01:12:50.288345', 0, 1, '2026-04-25 01:12:50.288345', '', NULL, 'usergrowth:userintegralrecord:add', '', '', '', 323174207154425856, 0, 0, '添加', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (323174355083333632, 1, '2026-04-25 01:12:50.541064', 0, 1, '2026-04-25 01:12:50.541064', '', NULL, 'usergrowth:userintegralrecord:update', '', '', '', 323174207154425856, 0, 0, '修改', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (323174355334991872, 1, '2026-04-25 01:12:50.601603', 0, 1, '2026-04-25 01:12:50.601603', '', NULL, 'usergrowth:userintegralrecord:delete', '', '', '', 323174207154425856, 0, 0, '删除', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (322952165469589504, 1, '2026-04-24 10:29:56.409463', 0, 1, '2026-05-01 03:37:17.693254', '', NULL, '', '', '', 'ReadOutlined', NULL, 6, 1, '权益管理', 0, '');
INSERT INTO "public"."sys_permissions" VALUES (316477772799283200, 1, '2026-04-06 13:43:00.90881', 0, 1, '2026-05-01 03:45:23.538202', '', NULL, 'admin:logs:operation:page', 'views/monitor/operationlog/index', 'MonitorOperationlog', 'ContainerOutlined', 316477320535871488, 0, 1, '日志管理', 1, '/monitor/operationlog');
INSERT INTO "public"."sys_permissions" VALUES (327575307823484928, 1, '2026-05-07 04:40:39.440508', 0, 1, '2026-05-07 04:40:39.440508', '', NULL, '', '', '', 'WindowsFilled', NULL, 0, 1, '页面配置', 0, '');
INSERT INTO "public"."sys_permissions" VALUES (327575872301305856, 1, '2026-05-07 04:42:54.022061', 0, 1, '2026-05-07 04:42:54.022061', '', NULL, 'admin:page:type:page', 'views/page/pagetype/index', 'PagePagetype', '', 327575307823484928, 0, 1, '页面类型', 1, '/page/pagetype');
INSERT INTO "public"."sys_permissions" VALUES (327576056225730560, 1, '2026-05-07 04:43:37.873263', 0, 1, '2026-05-07 04:43:37.873263', '', NULL, 'admin:page:type:add', '', '', '', 327575872301305856, 0, 0, '添加', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (327576056263479296, 1, '2026-05-07 04:43:37.88213', 0, 1, '2026-05-07 04:43:37.88213', '', NULL, 'admin:page:type:update', '', '', '', 327575872301305856, 0, 0, '修改', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (327576056309616640, 1, '2026-05-07 04:43:37.893646', 0, 1, '2026-05-07 04:43:37.893646', '', NULL, 'admin:page:type:delete', '', '', '', 327575872301305856, 0, 0, '删除', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (320000000000000131, NULL, '2026-05-19 10:00:00', 0, NULL, '2026-05-19 10:00:00', '', NULL, 'admin:mall:orderRefund:add', '', '', '', 320000000000000011, 0, 1, '添加', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (320000000000000132, NULL, '2026-05-19 10:00:00', 0, NULL, '2026-05-19 10:00:00', '', NULL, 'admin:mall:orderRefund:update', '', '', '', 320000000000000011, 0, 1, '修改', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (327583550016720896, 1, '2026-05-07 05:13:24.532513', 0, 1, '2026-05-07 05:13:31.679477', '', NULL, 'admin:page:component:page', 'views/page/pagecomponent/index', 'PagePagecomponent', '', 327575307823484928, 1, 1, '组件', 1, '/page/pagecomponent');
INSERT INTO "public"."sys_permissions" VALUES (327583747291615232, 1, '2026-05-07 05:14:11.566396', 0, 1, '2026-05-07 05:14:11.566396', '', NULL, 'admin:page:component:add', '', '', '', 327583550016720896, 0, 0, '添加', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (327583747333558272, 1, '2026-05-07 05:14:11.576236', 0, 1, '2026-05-07 05:14:11.576236', '', NULL, 'admin:page:component:update', '', '', '', 327583550016720896, 0, 0, '修改', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (327583747367112704, 1, '2026-05-07 05:14:11.584105', 0, 1, '2026-05-07 05:14:11.584105', '', NULL, 'admin:page:component:delete', '', '', '', 327583550016720896, 0, 0, '删除', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (327612559035338752, 1, '2026-05-07 07:08:40.82101', 0, 1, '2026-05-07 07:08:40.82101', '', NULL, 'admin:page:application:add', '', '', '', 327612413174222848, 0, 0, '添加', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (327612559068893184, 1, '2026-05-07 07:08:40.829342', 0, 1, '2026-05-07 07:08:40.829342', '', NULL, 'admin:page:application:update', '', '', '', 327612413174222848, 0, 0, '修改', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (327612559094059008, 1, '2026-05-07 07:08:40.835523', 0, 1, '2026-05-07 07:08:40.835523', '', NULL, 'admin:page:application:delete', '', '', '', 327612413174222848, 0, 0, '删除', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (327668371699142656, 1, '2026-05-07 10:50:27.597201', 0, 1, '2026-05-07 10:50:27.597201', '', NULL, 'admin:page:application:config', '', '', '', 327612413174222848, 0, 1, '页面配置', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (327612413174222848, 1, '2026-05-07 07:08:06.045721', 0, 1, '2026-05-07 10:54:54.463246', '', NULL, 'admin:page:application:page', 'views/page/pageapplication/index', 'PagePageapplication', '', 327575307823484928, 3, 1, '应用', 1, '/page/pageapplication');
INSERT INTO "public"."sys_permissions" VALUES (320000000000000000, NULL, '2026-05-19 10:00:00', 0, NULL, '2026-05-19 10:00:00', '', NULL, '', '', '', 'ShopOutlined', NULL, 100, 1, '商城管理', 0, '');
INSERT INTO "public"."sys_permissions" VALUES (320000000000000001, NULL, '2026-05-19 10:00:00', 0, NULL, '2026-05-19 10:00:00', '', NULL, 'admin:mall:brand:page', 'views/mall/mallproductbrand/index', '', 'TagsOutlined', 320000000000000000, 100, 1, '品牌管理', 1, '/mall/mallproductbrand');
INSERT INTO "public"."sys_permissions" VALUES (320000000000000101, NULL, '2026-05-19 10:00:00', 0, NULL, '2026-05-19 10:00:00', '', NULL, 'admin:mall:brand:add', '', '', '', 320000000000000001, 0, 1, '添加', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (320000000000000102, NULL, '2026-05-19 10:00:00', 0, NULL, '2026-05-19 10:00:00', '', NULL, 'admin:mall:brand:update', '', '', '', 320000000000000001, 0, 1, '修改', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (320000000000000103, NULL, '2026-05-19 10:00:00', 0, NULL, '2026-05-19 10:00:00', '', NULL, 'admin:mall:brand:delete', '', '', '', 320000000000000001, 0, 1, '删除', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (320000000000000002, NULL, '2026-05-19 10:00:00', 0, NULL, '2026-05-19 10:00:00', '', NULL, 'admin:mall:category:page', 'views/mall/mallproductcategory/index', '', 'AppstoreOutlined', 320000000000000000, 200, 1, '分类管理', 1, '/mall/mallproductcategory');
INSERT INTO "public"."sys_permissions" VALUES (320000000000000104, NULL, '2026-05-19 10:00:00', 0, NULL, '2026-05-19 10:00:00', '', NULL, 'admin:mall:category:add', '', '', '', 320000000000000002, 0, 1, '添加', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (320000000000000105, NULL, '2026-05-19 10:00:00', 0, NULL, '2026-05-19 10:00:00', '', NULL, 'admin:mall:category:update', '', '', '', 320000000000000002, 0, 1, '修改', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (320000000000000106, NULL, '2026-05-19 10:00:00', 0, NULL, '2026-05-19 10:00:00', '', NULL, 'admin:mall:category:delete', '', '', '', 320000000000000002, 0, 1, '删除', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (320000000000000003, NULL, '2026-05-19 10:00:00', 0, NULL, '2026-05-19 10:00:00', '', NULL, 'admin:mall:shop:page', 'views/mall/mallshop/index', '', 'ShopOutlined', 320000000000000000, 300, 1, '店铺管理', 1, '/mall/mallshop');
INSERT INTO "public"."sys_permissions" VALUES (320000000000000107, NULL, '2026-05-19 10:00:00', 0, NULL, '2026-05-19 10:00:00', '', NULL, 'admin:mall:shop:add', '', '', '', 320000000000000003, 0, 1, '添加', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (320000000000000108, NULL, '2026-05-19 10:00:00', 0, NULL, '2026-05-19 10:00:00', '', NULL, 'admin:mall:shop:update', '', '', '', 320000000000000003, 0, 1, '修改', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (320000000000000109, NULL, '2026-05-19 10:00:00', 0, NULL, '2026-05-19 10:00:00', '', NULL, 'admin:mall:shop:delete', '', '', '', 320000000000000003, 0, 1, '删除', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (320000000000000004, NULL, '2026-05-19 10:00:00', 0, NULL, '2026-05-19 10:00:00', '', NULL, 'admin:mall:product:page', 'views/mall/mallproduct/index', '', 'GiftOutlined', 320000000000000000, 400, 1, '商品管理', 1, '/mall/mallproduct');
INSERT INTO "public"."sys_permissions" VALUES (320000000000000110, NULL, '2026-05-19 10:00:00', 0, NULL, '2026-05-19 10:00:00', '', NULL, 'admin:mall:product:add', '', '', '', 320000000000000004, 0, 1, '添加', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (320000000000000111, NULL, '2026-05-19 10:00:00', 0, NULL, '2026-05-19 10:00:00', '', NULL, 'admin:mall:product:update', '', '', '', 320000000000000004, 0, 1, '修改', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (320000000000000112, NULL, '2026-05-19 10:00:00', 0, NULL, '2026-05-19 10:00:00', '', NULL, 'admin:mall:product:delete', '', '', '', 320000000000000004, 0, 1, '删除', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (320000000000000005, NULL, '2026-05-19 10:00:00', 0, NULL, '2026-05-19 10:00:00', '', NULL, 'admin:mall:sku:page', 'views/mall/mallproductsku/index', '', 'BarcodeOutlined', 320000000000000000, 500, 1, 'SKU管理', 1, '/mall/mallproductsku');
INSERT INTO "public"."sys_permissions" VALUES (320000000000000113, NULL, '2026-05-19 10:00:00', 0, NULL, '2026-05-19 10:00:00', '', NULL, 'admin:mall:sku:add', '', '', '', 320000000000000005, 0, 1, '添加', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (320000000000000114, NULL, '2026-05-19 10:00:00', 0, NULL, '2026-05-19 10:00:00', '', NULL, 'admin:mall:sku:update', '', '', '', 320000000000000005, 0, 1, '修改', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (320000000000000115, NULL, '2026-05-19 10:00:00', 0, NULL, '2026-05-19 10:00:00', '', NULL, 'admin:mall:sku:delete', '', '', '', 320000000000000005, 0, 1, '删除', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (320000000000000006, NULL, '2026-05-19 10:00:00', 0, NULL, '2026-05-19 10:00:00', '', NULL, 'admin:mall:cart:page', 'views/mall/mallcart/index', '', 'ShoppingCartOutlined', 320000000000000000, 600, 1, '购物车', 1, '/mall/mallcart');
INSERT INTO "public"."sys_permissions" VALUES (320000000000000116, NULL, '2026-05-19 10:00:00', 0, NULL, '2026-05-19 10:00:00', '', NULL, 'admin:mall:cart:add', '', '', '', 320000000000000006, 0, 1, '添加', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (320000000000000117, NULL, '2026-05-19 10:00:00', 0, NULL, '2026-05-19 10:00:00', '', NULL, 'admin:mall:cart:update', '', '', '', 320000000000000006, 0, 1, '修改', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (320000000000000118, NULL, '2026-05-19 10:00:00', 0, NULL, '2026-05-19 10:00:00', '', NULL, 'admin:mall:cart:delete', '', '', '', 320000000000000006, 0, 1, '删除', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (320000000000000007, NULL, '2026-05-19 10:00:00', 0, NULL, '2026-05-19 10:00:00', '', NULL, 'admin:mall:freightTemplate:page', 'views/mall/mallfreighttemplate/index', '', 'CarOutlined', 320000000000000000, 700, 1, '运费模板', 1, '/mall/mallfreighttemplate');
INSERT INTO "public"."sys_permissions" VALUES (320000000000000119, NULL, '2026-05-19 10:00:00', 0, NULL, '2026-05-19 10:00:00', '', NULL, 'admin:mall:freightTemplate:add', '', '', '', 320000000000000007, 0, 1, '添加', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (320000000000000120, NULL, '2026-05-19 10:00:00', 0, NULL, '2026-05-19 10:00:00', '', NULL, 'admin:mall:freightTemplate:update', '', '', '', 320000000000000007, 0, 1, '修改', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (320000000000000121, NULL, '2026-05-19 10:00:00', 0, NULL, '2026-05-19 10:00:00', '', NULL, 'admin:mall:freightTemplate:delete', '', '', '', 320000000000000007, 0, 1, '删除', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (320000000000000008, NULL, '2026-05-19 10:00:00', 0, NULL, '2026-05-19 10:00:00', '', NULL, 'admin:mall:order:page', 'views/mall/mallorder/index', '', 'FileDoneOutlined', 320000000000000000, 800, 1, '订单管理', 1, '/mall/mallorder');
INSERT INTO "public"."sys_permissions" VALUES (320000000000000122, NULL, '2026-05-19 10:00:00', 0, NULL, '2026-05-19 10:00:00', '', NULL, 'admin:mall:order:add', '', '', '', 320000000000000008, 0, 1, '添加', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (320000000000000123, NULL, '2026-05-19 10:00:00', 0, NULL, '2026-05-19 10:00:00', '', NULL, 'admin:mall:order:update', '', '', '', 320000000000000008, 0, 1, '修改', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (320000000000000124, NULL, '2026-05-19 10:00:00', 0, NULL, '2026-05-19 10:00:00', '', NULL, 'admin:mall:order:delete', '', '', '', 320000000000000008, 0, 1, '删除', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (320000000000000009, NULL, '2026-05-19 10:00:00', 0, NULL, '2026-05-19 10:00:00', '', NULL, 'admin:mall:orderItem:page', 'views/mall/mallorderitem/index', '', 'UnorderedListOutlined', 320000000000000000, 900, 1, '订单商品', 1, '/mall/mallorderitem');
INSERT INTO "public"."sys_permissions" VALUES (320000000000000125, NULL, '2026-05-19 10:00:00', 0, NULL, '2026-05-19 10:00:00', '', NULL, 'admin:mall:orderItem:add', '', '', '', 320000000000000009, 0, 1, '添加', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (320000000000000126, NULL, '2026-05-19 10:00:00', 0, NULL, '2026-05-19 10:00:00', '', NULL, 'admin:mall:orderItem:update', '', '', '', 320000000000000009, 0, 1, '修改', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (320000000000000127, NULL, '2026-05-19 10:00:00', 0, NULL, '2026-05-19 10:00:00', '', NULL, 'admin:mall:orderItem:delete', '', '', '', 320000000000000009, 0, 1, '删除', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (320000000000000010, NULL, '2026-05-19 10:00:00', 0, NULL, '2026-05-19 10:00:00', '', NULL, 'admin:mall:orderLog:page', 'views/mall/mallorderlog/index', '', 'FileTextOutlined', 320000000000000000, 1000, 1, '订单日志', 1, '/mall/mallorderlog');
INSERT INTO "public"."sys_permissions" VALUES (320000000000000128, NULL, '2026-05-19 10:00:00', 0, NULL, '2026-05-19 10:00:00', '', NULL, 'admin:mall:orderLog:add', '', '', '', 320000000000000010, 0, 1, '添加', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (320000000000000129, NULL, '2026-05-19 10:00:00', 0, NULL, '2026-05-19 10:00:00', '', NULL, 'admin:mall:orderLog:update', '', '', '', 320000000000000010, 0, 1, '修改', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (320000000000000130, NULL, '2026-05-19 10:00:00', 0, NULL, '2026-05-19 10:00:00', '', NULL, 'admin:mall:orderLog:delete', '', '', '', 320000000000000010, 0, 1, '删除', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (320000000000000011, NULL, '2026-05-19 10:00:00', 0, NULL, '2026-05-19 10:00:00', '', NULL, 'admin:mall:orderRefund:page', 'views/mall/mallorderrefund/index', '', 'RollbackOutlined', 320000000000000000, 1100, 1, '售后退款', 1, '/mall/mallorderrefund');
INSERT INTO "public"."sys_permissions" VALUES (320000000000000133, NULL, '2026-05-19 10:00:00', 0, NULL, '2026-05-19 10:00:00', '', NULL, 'admin:mall:orderRefund:delete', '', '', '', 320000000000000011, 0, 1, '删除', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (320000000000000012, NULL, '2026-05-19 10:00:00', 0, NULL, '2026-05-19 10:00:00', '', NULL, 'admin:mall:pickupShop:page', 'views/mall/mallpickupshop/index', '', 'EnvironmentOutlined', 320000000000000000, 1200, 1, '自提门店', 1, '/mall/mallpickupshop');
INSERT INTO "public"."sys_permissions" VALUES (320000000000000134, NULL, '2026-05-19 10:00:00', 0, NULL, '2026-05-19 10:00:00', '', NULL, 'admin:mall:pickupShop:add', '', '', '', 320000000000000012, 0, 1, '添加', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (320000000000000135, NULL, '2026-05-19 10:00:00', 0, NULL, '2026-05-19 10:00:00', '', NULL, 'admin:mall:pickupShop:update', '', '', '', 320000000000000012, 0, 1, '修改', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (320000000000000136, NULL, '2026-05-19 10:00:00', 0, NULL, '2026-05-19 10:00:00', '', NULL, 'admin:mall:pickupShop:delete', '', '', '', 320000000000000012, 0, 1, '删除', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (320000000000000013, NULL, '2026-05-19 10:00:00', 0, NULL, '2026-05-19 10:00:00', '', NULL, 'admin:mall:userAddress:page', 'views/mall/malluseraddress/index', '', 'HomeOutlined', 320000000000000000, 1300, 1, '用户地址', 1, '/mall/malluseraddress');
INSERT INTO "public"."sys_permissions" VALUES (320000000000000137, NULL, '2026-05-19 10:00:00', 0, NULL, '2026-05-19 10:00:00', '', NULL, 'admin:mall:userAddress:add', '', '', '', 320000000000000013, 0, 1, '添加', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (320000000000000138, NULL, '2026-05-19 10:00:00', 0, NULL, '2026-05-19 10:00:00', '', NULL, 'admin:mall:userAddress:update', '', '', '', 320000000000000013, 0, 1, '修改', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (320000000000000139, NULL, '2026-05-19 10:00:00', 0, NULL, '2026-05-19 10:00:00', '', NULL, 'admin:mall:userAddress:delete', '', '', '', 320000000000000013, 0, 1, '删除', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (399000000000000000, NULL, '2026-05-26 21:40:44.348062', 0, NULL, '2026-05-26 21:40:44.348062', '', NULL, '', '', '', 'ReadOutlined', NULL, 2000, 1, '内容管理', 0, '');
INSERT INTO "public"."sys_permissions" VALUES (399000000000000001, NULL, '2026-05-26 21:40:44.354222', 0, NULL, '2026-05-26 21:40:44.354222', '', NULL, 'admin:content:info:page', 'views/content/info/index', '', 'FileTextOutlined', 399000000000000000, 1000, 1, '内容', 1, '/content/info');
INSERT INTO "public"."sys_permissions" VALUES (399000000000000002, NULL, '2026-05-26 21:40:44.355694', 0, NULL, '2026-05-26 21:40:44.355694', '', NULL, 'admin:content:info:add', '', '', '', 399000000000000001, 0, 1, '添加', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (399000000000000003, NULL, '2026-05-26 21:40:44.356714', 0, NULL, '2026-05-26 21:40:44.356714', '', NULL, 'admin:content:info:delete', '', '', '', 399000000000000001, 0, 1, '删除', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (399000000000000004, NULL, '2026-05-26 21:40:44.357614', 0, NULL, '2026-05-26 21:40:44.357614', '', NULL, 'admin:content:info:update', '', '', '', 399000000000000001, 0, 1, '修改', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (399000000000000021, NULL, '2026-05-26 21:40:44.363586', 0, NULL, '2026-05-26 21:40:44.363586', '', NULL, 'admin:content:comment:page', 'views/content/comment/index', '', 'CommentOutlined', 399000000000000000, 1020, 1, '评论', 1, '/content/comment');
INSERT INTO "public"."sys_permissions" VALUES (399000000000000022, NULL, '2026-05-26 21:40:44.364566', 0, NULL, '2026-05-26 21:40:44.364566', '', NULL, 'admin:content:comment:add', '', '', '', 399000000000000021, 0, 1, '添加', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (399000000000000023, NULL, '2026-05-26 21:40:44.36544', 0, NULL, '2026-05-26 21:40:44.36544', '', NULL, 'admin:content:comment:delete', '', '', '', 399000000000000021, 0, 1, '删除', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (399000000000000024, NULL, '2026-05-26 21:40:44.367131', 0, NULL, '2026-05-26 21:40:44.367131', '', NULL, 'admin:content:comment:update', '', '', '', 399000000000000021, 0, 1, '修改', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (399000000000000031, NULL, '2026-05-26 21:40:44.368566', 0, NULL, '2026-05-26 21:40:44.368566', '', NULL, 'admin:content:category:page', 'views/content/category/index', '', 'PartitionOutlined', 399000000000000000, 1030, 1, '分类', 1, '/content/category');
INSERT INTO "public"."sys_permissions" VALUES (399000000000000032, NULL, '2026-05-26 21:40:44.370222', 0, NULL, '2026-05-26 21:40:44.370222', '', NULL, 'admin:content:category:add', '', '', '', 399000000000000031, 0, 1, '添加', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (399000000000000033, NULL, '2026-05-26 21:40:44.371506', 0, NULL, '2026-05-26 21:40:44.371506', '', NULL, 'admin:content:category:delete', '', '', '', 399000000000000031, 0, 1, '删除', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (399000000000000034, NULL, '2026-05-26 21:40:44.372713', 0, NULL, '2026-05-26 21:40:44.372713', '', NULL, 'admin:content:category:update', '', '', '', 399000000000000031, 0, 1, '修改', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (399000000000000051, NULL, '2026-05-26 21:40:44.377812', 0, NULL, '2026-05-26 21:40:44.377812', '', NULL, 'admin:content:tag:page', 'views/content/tag/index', '', 'TagOutlined', 399000000000000000, 1050, 1, '标签', 1, '/content/tag');
INSERT INTO "public"."sys_permissions" VALUES (399000000000000052, NULL, '2026-05-26 21:40:44.379049', 0, NULL, '2026-05-26 21:40:44.379049', '', NULL, 'admin:content:tag:add', '', '', '', 399000000000000051, 0, 1, '添加', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (399000000000000053, NULL, '2026-05-26 21:40:44.380044', 0, NULL, '2026-05-26 21:40:44.380044', '', NULL, 'admin:content:tag:delete', '', '', '', 399000000000000051, 0, 1, '删除', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (399000000000000054, NULL, '2026-05-26 21:40:44.381433', 0, NULL, '2026-05-26 21:40:44.381433', '', NULL, 'admin:content:tag:update', '', '', '', 399000000000000051, 0, 1, '修改', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (399000000000000071, NULL, '2026-05-26 21:40:44.387229', 0, NULL, '2026-05-26 21:40:44.387229', '', NULL, 'admin:content:attachment:page', 'views/content/attachment/index', '', 'PaperClipOutlined', 399000000000000000, 1070, 1, '附件', 1, '/content/attachment');
INSERT INTO "public"."sys_permissions" VALUES (399000000000000072, NULL, '2026-05-26 21:40:44.388257', 0, NULL, '2026-05-26 21:40:44.388257', '', NULL, 'admin:content:attachment:add', '', '', '', 399000000000000071, 0, 1, '添加', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (399000000000000073, NULL, '2026-05-26 21:40:44.389161', 0, NULL, '2026-05-26 21:40:44.389161', '', NULL, 'admin:content:attachment:delete', '', '', '', 399000000000000071, 0, 1, '删除', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (399000000000000074, NULL, '2026-05-26 21:40:44.390455', 0, NULL, '2026-05-26 21:40:44.390455', '', NULL, 'admin:content:attachment:update', '', '', '', 399000000000000071, 0, 1, '修改', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (399000000000000081, NULL, '2026-05-26 21:40:44.391746', 0, NULL, '2026-05-26 21:40:44.391746', '', NULL, 'admin:content:like:page', 'views/content/like/index', '', 'LikeOutlined', 399000000000000000, 1080, 1, '点赞记录', 1, '/content/like');
INSERT INTO "public"."sys_permissions" VALUES (399000000000000082, NULL, '2026-05-26 21:40:44.393036', 0, NULL, '2026-05-26 21:40:44.393036', '', NULL, 'admin:content:like:add', '', '', '', 399000000000000081, 0, 1, '添加', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (399000000000000083, NULL, '2026-05-26 21:40:44.394194', 0, NULL, '2026-05-26 21:40:44.394194', '', NULL, 'admin:content:like:delete', '', '', '', 399000000000000081, 0, 1, '删除', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (399000000000000084, NULL, '2026-05-26 21:40:44.395517', 0, NULL, '2026-05-26 21:40:44.395517', '', NULL, 'admin:content:like:update', '', '', '', 399000000000000081, 0, 1, '修改', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (399000000000000091, NULL, '2026-05-26 21:40:44.396544', 0, NULL, '2026-05-26 21:40:44.396544', '', NULL, 'admin:content:favorite:page', 'views/content/favorite/index', '', 'StarOutlined', 399000000000000000, 1090, 1, '收藏记录', 1, '/content/favorite');
INSERT INTO "public"."sys_permissions" VALUES (399000000000000092, NULL, '2026-05-26 21:40:44.399592', 0, NULL, '2026-05-26 21:40:44.399592', '', NULL, 'admin:content:favorite:add', '', '', '', 399000000000000091, 0, 1, '添加', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (399000000000000093, NULL, '2026-05-26 21:40:44.401045', 0, NULL, '2026-05-26 21:40:44.401045', '', NULL, 'admin:content:favorite:delete', '', '', '', 399000000000000091, 0, 1, '删除', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (399000000000000094, NULL, '2026-05-26 21:40:44.402713', 0, NULL, '2026-05-26 21:40:44.402713', '', NULL, 'admin:content:favorite:update', '', '', '', 399000000000000091, 0, 1, '修改', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (399000000000000101, NULL, '2026-05-26 21:40:44.403969', 0, NULL, '2026-05-26 21:40:44.403969', '', NULL, 'admin:content:viewLog:page', 'views/content/viewLog/index', '', 'EyeOutlined', 399000000000000000, 1100, 1, '浏览记录', 1, '/content/viewLog');
INSERT INTO "public"."sys_permissions" VALUES (399000000000000102, NULL, '2026-05-26 21:40:44.405258', 0, NULL, '2026-05-26 21:40:44.405258', '', NULL, 'admin:content:viewLog:add', '', '', '', 399000000000000101, 0, 1, '添加', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (399000000000000103, NULL, '2026-05-26 21:40:44.406087', 0, NULL, '2026-05-26 21:40:44.406087', '', NULL, 'admin:content:viewLog:delete', '', '', '', 399000000000000101, 0, 1, '删除', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (399000000000000104, NULL, '2026-05-26 21:40:44.406931', 0, NULL, '2026-05-26 21:40:44.406931', '', NULL, 'admin:content:viewLog:update', '', '', '', 399000000000000101, 0, 1, '修改', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (399000000000000111, NULL, '2026-05-26 21:40:44.407973', 0, NULL, '2026-05-26 21:40:44.407973', '', NULL, 'admin:content:auditLog:page', 'views/content/auditLog/index', '', 'SafetyOutlined', 399000000000000000, 1110, 1, '审核记录', 1, '/content/auditLog');
INSERT INTO "public"."sys_permissions" VALUES (399000000000000112, NULL, '2026-05-26 21:40:44.409421', 0, NULL, '2026-05-26 21:40:44.409421', '', NULL, 'admin:content:auditLog:add', '', '', '', 399000000000000111, 0, 1, '添加', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (399000000000000113, NULL, '2026-05-26 21:40:44.410222', 0, NULL, '2026-05-26 21:40:44.410222', '', NULL, 'admin:content:auditLog:delete', '', '', '', 399000000000000111, 0, 1, '删除', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (399000000000000114, NULL, '2026-05-26 21:40:44.411134', 0, NULL, '2026-05-26 21:40:44.411134', '', NULL, 'admin:content:auditLog:update', '', '', '', 399000000000000111, 0, 1, '修改', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (399000000000000121, NULL, '2026-05-26 21:40:44.411965', 0, NULL, '2026-05-26 21:40:44.411965', '', NULL, 'admin:content:revision:page', 'views/content/revision/index', '', 'HistoryOutlined', 399000000000000000, 1120, 1, '修订记录', 1, '/content/revision');
INSERT INTO "public"."sys_permissions" VALUES (399000000000000122, NULL, '2026-05-26 21:40:44.413036', 0, NULL, '2026-05-26 21:40:44.413036', '', NULL, 'admin:content:revision:add', '', '', '', 399000000000000121, 0, 1, '添加', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (399000000000000123, NULL, '2026-05-26 21:40:44.414356', 0, NULL, '2026-05-26 21:40:44.414356', '', NULL, 'admin:content:revision:delete', '', '', '', 399000000000000121, 0, 1, '删除', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (399000000000000124, NULL, '2026-05-26 21:40:44.415562', 0, NULL, '2026-05-26 21:40:44.415562', '', NULL, 'admin:content:revision:update', '', '', '', 399000000000000121, 0, 1, '修改', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (399000000000000131, NULL, '2026-05-26 21:40:44.416377', 0, NULL, '2026-05-26 21:40:44.416377', '', NULL, 'admin:content:commentLike:page', 'views/content/commentLike/index', '', 'LikeOutlined', 399000000000000000, 1130, 1, '评论点赞', 1, '/content/commentLike');
INSERT INTO "public"."sys_permissions" VALUES (399000000000000132, NULL, '2026-05-26 21:40:44.417185', 0, NULL, '2026-05-26 21:40:44.417185', '', NULL, 'admin:content:commentLike:add', '', '', '', 399000000000000131, 0, 1, '添加', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (399000000000000133, NULL, '2026-05-26 21:40:44.418013', 0, NULL, '2026-05-26 21:40:44.418013', '', NULL, 'admin:content:commentLike:delete', '', '', '', 399000000000000131, 0, 1, '删除', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (399000000000000134, NULL, '2026-05-26 21:40:44.419213', 0, NULL, '2026-05-26 21:40:44.419213', '', NULL, 'admin:content:commentLike:update', '', '', '', 399000000000000131, 0, 1, '修改', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (399000000000000141, NULL, '2026-05-26 21:40:44.420113', 0, NULL, '2026-05-26 21:40:44.420113', '', NULL, 'admin:content:report:page', 'views/content/report/index', '', 'WarningOutlined', 399000000000000000, 1140, 1, '举报', 1, '/content/report');
INSERT INTO "public"."sys_permissions" VALUES (399000000000000142, NULL, '2026-05-26 21:40:44.420919', 0, NULL, '2026-05-26 21:40:44.420919', '', NULL, 'admin:content:report:add', '', '', '', 399000000000000141, 0, 1, '添加', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (399000000000000143, NULL, '2026-05-26 21:40:44.421966', 0, NULL, '2026-05-26 21:40:44.421966', '', NULL, 'admin:content:report:delete', '', '', '', 399000000000000141, 0, 1, '删除', 2, '');
INSERT INTO "public"."sys_permissions" VALUES (399000000000000144, NULL, '2026-05-26 21:40:44.422813', 0, NULL, '2026-05-26 21:40:44.422813', '', NULL, 'admin:content:report:update', '', '', '', 399000000000000141, 0, 1, '修改', 2, '');

-- ----------------------------
-- Table structure for sys_post
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_post";
CREATE TABLE "public"."sys_post" (
  "id" int8 NOT NULL,
  "create_by" int8,
  "create_time" timestamp(6),
  "deleted" int4,
  "update_by" int8,
  "update_time" timestamp(6),
  "code" varchar(255) COLLATE "pg_catalog"."default",
  "name" varchar(255) COLLATE "pg_catalog"."default",
  "remark" varchar(255) COLLATE "pg_catalog"."default",
  "sort" int4,
  "status" int4,
  "tenant_id" int8
)
;

-- ----------------------------
-- Records of sys_post
-- ----------------------------
INSERT INTO "public"."sys_post" VALUES (313512533233373184, NULL, '2026-03-29 09:20:12.704896', 0, NULL, '2026-03-30 08:14:22.520846', 'admin', '总经理', '', 0, 1, NULL);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_role";
CREATE TABLE "public"."sys_role" (
  "id" int8 NOT NULL,
  "create_by" int8,
  "create_time" timestamp(6),
  "deleted" int4,
  "update_by" int8,
  "update_time" timestamp(6),
  "application_code" varchar(255) COLLATE "pg_catalog"."default",
  "application_id" int8,
  "code" varchar(255) COLLATE "pg_catalog"."default",
  "status" int4,
  "title" varchar(255) COLLATE "pg_catalog"."default",
  "tenant_id" int8
)
;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO "public"."sys_role" VALUES (311703722914877440, NULL, '2026-03-24 09:32:38.718829', 0, NULL, '2026-03-30 07:10:49.446441', '', NULL, 'admin', 1, '超级管理员', NULL);
INSERT INTO "public"."sys_role" VALUES (312029954386825216, NULL, '2026-03-25 07:08:58.362991', 0, NULL, '2026-03-30 08:13:38.075893', '', NULL, 'admin2', 1, '普通管理员', NULL);

-- ----------------------------
-- Table structure for sys_role_permissions
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_role_permissions";
CREATE TABLE "public"."sys_role_permissions" (
  "role_id" int8 NOT NULL,
  "permission_id" int8 NOT NULL
)
;

-- ----------------------------
-- Records of sys_role_permissions
-- ----------------------------
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 311762357514801152);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 314602359294660608);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 313512229460905984);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 323173868942528512);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 314530113607831552);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 323172121876500480);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 322527163695894528);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 318995237680844800);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 322134033570074624);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 322134034677370880);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 313550330359058432);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 321043873273090048);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 316775517045002240);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 323174207154425856);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 313511610620710912);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 321837761428590592);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 313512229410574336);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 318993678729351168);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 313550330203869184);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 318995415359950848);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 321774421545193472);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 323173706522300416);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 323174355334991872);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 321043647103635456);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 312424744626556928);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 314529426132045824);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 313546319996260352);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 322527291479560192);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 316654743902621696);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 312424193062998016);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 314602586227478528);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 318995950548946944);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 322134032211120128);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 323172693027459072);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 313511173993664512);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 318995950456672256);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 323174354022174720);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 321768608076271616);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 318995950507003904);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 318996311225536512);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 314514382686130176);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 323172541172682752);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 316654743831318528);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 316477984586469376);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 321776884998344704);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 321835165842280448);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 323171047304204288);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 318994124692918272);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 316477320535871488);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 318996311305228288);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 312424744555253760);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 314530113658163200);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 322133528571678720);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 312424192979111936);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 316477984615829504);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 321835324428914688);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 322133527577628672);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 311757014093139968);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 313550330266783744);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 321769059295301632);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 318995415326396416);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 321774598733565952);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 321043875705786368);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 322952165469589504);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 323172691987271680);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 316654270747381760);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 318995415292841984);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 313512229490266112);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 313512003115290624);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 318995774476259328);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 318994357510344704);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 318996311263285248);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 313549686420148224);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 318996165058236416);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 311760876300537856);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 313550330241617920);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 313511610658459648);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 321837457962307584);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 320987596123672576);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 323174355083333632);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 312424744588808192);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 321043874619461632);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 320780969307148288);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 321776883953963008);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 323171045924278272);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 323172121557733376);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 312425344407834624);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 321776882582425600);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 323173869974327296);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 314602586261032960);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 312424193046220800);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 322133236576817152);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 314513638113284096);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 321774597626269696);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 322133770276835328);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 314529906530848768);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 312425344483332096);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 323171843106279424);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 313549285599875072);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 321769060406792192);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 312425344453971968);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 313512003115290624);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 318994357510344704);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 318996311263285248);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 313549686420148224);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 311762357514801152);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 311760876300537856);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 313550330241617920);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 313511610658459648);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 314602359294660608);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 320987596123672576);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 312424744588808192);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 313512229460905984);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 321043874619461632);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 320780969307148288);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 314530113607831552);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 312425344407834624);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 318995237680844800);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 313550330359058432);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 321043873273090048);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 316775517045002240);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 318995774476259328);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 314602586261032960);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 312424193046220800);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 313511610620710912);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 314513638113284096);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 313512229410574336);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 318993678729351168);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 314529906530848768);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 312425344483332096);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 313550330203869184);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 318995415359950848);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 313549285599875072);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 321769060406792192);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 312425344453971968);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 314537408047747072);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 321043647103635456);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 318994357548093440);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 312424744626556928);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 314529426132045824);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 316477984636801024);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 318996165058236416);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 313546319996260352);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 313550330333892608);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 316654743902621696);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 312424193062998016);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 314536770404487168);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 311762528248139776);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 314602586227478528);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 318995950548946944);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 313511173993664512);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 321769057961512960);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 318995950456672256);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 313550330384224256);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 313549686361427968);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 313549686445314048);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 321768608076271616);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 318995950507003904);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 318996311225536512);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 314514382686130176);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 316654743831318528);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 320780885941161984);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 316477984586469376);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 318994357602619392);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 314529426207543296);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 313511610557796352);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 318994124692918272);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 316477320535871488);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 318996311305228288);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 312424744555253760);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 314530113658163200);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 313550330304532480);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 316477772799283200);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 312424192979111936);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 314529426228514816);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 316477984615829504);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 311757014093139968);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 321768925010464768);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 313550330266783744);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 321769059295301632);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 318995415326396416);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 321043875705786368);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 314530113637191680);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 314602586298781696);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 316654270747381760);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 318995415292841984);
INSERT INTO "public"."sys_role_permissions" VALUES (312029954386825216, 313512229490266112);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 314537408047747072);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 318994357548093440);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 316477984636801024);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 323172120530128896);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 321774596267315200);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 321776690806263808);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 313550330333892608);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 323170839119925248);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 314536770404487168);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 311762528248139776);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 323171046972854272);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 321769057961512960);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 322133528890445824);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 313550330384224256);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 313549686361427968);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 313549686445314048);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 320780885941161984);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 318994357602619392);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 314529426207543296);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 313511610557796352);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 313550330304532480);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 316477772799283200);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 314529426228514816);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 323172690603151360);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 321768925010464768);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 323173867587768320);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 314530113637191680);
INSERT INTO "public"."sys_role_permissions" VALUES (311703722914877440, 314602586298781696);

-- ----------------------------
-- Table structure for sys_slow_query_log
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_slow_query_log";
CREATE TABLE "public"."sys_slow_query_log" (
  "id" int8 NOT NULL,
  "create_by" int8,
  "create_time" timestamp(6),
  "deleted" int4,
  "update_by" int8,
  "update_time" timestamp(6),
  "execution_time" int8,
  "module" varchar(255) COLLATE "pg_catalog"."default",
  "remark" varchar(255) COLLATE "pg_catalog"."default",
  "severity" varchar(255) COLLATE "pg_catalog"."default",
  "sql_content" text COLLATE "pg_catalog"."default",
  "sql_md5" varchar(255) COLLATE "pg_catalog"."default",
  "status" varchar(255) COLLATE "pg_catalog"."default",
  "trigger_count" int4
)
;

-- ----------------------------
-- Records of sys_slow_query_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_tenant
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_tenant";
CREATE TABLE "public"."sys_tenant" (
  "id" int8 NOT NULL,
  "create_by" int8,
  "create_time" timestamp(6),
  "deleted" int4,
  "update_by" int8,
  "update_time" timestamp(6),
  "account_count" int4,
  "contact_name" varchar(255) COLLATE "pg_catalog"."default",
  "contact_phone" varchar(255) COLLATE "pg_catalog"."default",
  "domain" varchar(255) COLLATE "pg_catalog"."default",
  "expire_time" timestamp(6),
  "name" varchar(255) COLLATE "pg_catalog"."default",
  "remark" varchar(255) COLLATE "pg_catalog"."default",
  "status" int4,
  "admin_id" int8
)
;

-- ----------------------------
-- Records of sys_tenant
-- ----------------------------
INSERT INTO "public"."sys_tenant" VALUES (321044251905495040, 1, '2026-04-19 04:08:34.368805', 0, 1, '2026-04-19 11:14:43.678705', 100, '张三', '', 'http://localhost:5173', '2034-04-07 04:27:46', '总经理', '', 0, 311655641745854464);
INSERT INTO "public"."sys_tenant" VALUES (321218223158726656, 1, '2026-04-19 15:39:52.347958', 0, 1, '2026-04-19 15:39:52.347958', 100, '张三', '', 'http://127.0.0.1:5173', '2027-04-24 15:39:02', '总经理啊', '', 0, 311655641745854464);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_user_role";
CREATE TABLE "public"."sys_user_role" (
  "user_id" int8 NOT NULL,
  "role_id" int8 NOT NULL
)
;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO "public"."sys_user_role" VALUES (1, 311703722914877440);

-- ----------------------------
-- Table structure for sys_users
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_users";
CREATE TABLE "public"."sys_users" (
  "id" int8 NOT NULL,
  "create_by" int8,
  "create_time" timestamp(6),
  "deleted" int4,
  "update_by" int8,
  "update_time" timestamp(6),
  "email" varchar(255) COLLATE "pg_catalog"."default",
  "gender" int4,
  "nickname" varchar(255) COLLATE "pg_catalog"."default",
  "password" varchar(255) COLLATE "pg_catalog"."default",
  "phone" varchar(255) COLLATE "pg_catalog"."default",
  "status" int4,
  "username" varchar(255) COLLATE "pg_catalog"."default",
  "department_id" int8,
  "post_id" int8,
  "avatar" varchar(255) COLLATE "pg_catalog"."default",
  "remark" varchar(255) COLLATE "pg_catalog"."default",
  "tenant_id" int8
)
;

-- ----------------------------
-- Records of sys_users
-- ----------------------------
INSERT INTO "public"."sys_users" VALUES (311663478626717696, NULL, '2026-03-24 06:52:43.73251', 0, 1, '2026-04-07 11:41:51.766182', '', 1, '管理员1', '$2a$10$V03s5iG.3qCYwWFfYFm54utK8I9Vt9Al0V0bd4OrqZYXWfjm1csha', '', 1, 'admin1', NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."sys_users" VALUES (311655641745854464, NULL, '2026-03-24 06:21:35.274228', 0, 1, '2026-04-28 08:08:44.945802', NULL, 2, '管理员4', '$2a$10$V3KDLtaz83ng5hQCM/vEkeCA1uUCKKDtijead72bD85mAXkJZuDOS', NULL, 2, 'admin4', NULL, NULL, NULL, NULL, 321218223158726656);
INSERT INTO "public"."sys_users" VALUES (1, NULL, '2026-03-24 06:52:57.417753', 0, 1, '2026-05-01 04:01:30.056328', '2@qq.com', 1, '管理员', '$2a$10$n1LECWdmWKfj1TmAwOZ8g.kJsO.Yy0.KPSCHBhQ9KctXEOLfK3LC.', '18711321234', 1, 'admin', 313513559294349312, 313512533233373184, '325391124225200128', 'aaa', NULL);

-- ----------------------------
-- Table structure for user_integral_account
-- ----------------------------
DROP TABLE IF EXISTS "public"."user_integral_account";
CREATE TABLE "public"."user_integral_account" (
  "id" int8 NOT NULL,
  "create_by" int8,
  "create_time" timestamp(6),
  "deleted" int4,
  "update_by" int8,
  "update_time" timestamp(6),
  "growth_value" int8,
  "status" int4,
  "user_id" int8,
  "integral" int8
)
;

-- ----------------------------
-- Records of user_integral_account
-- ----------------------------
INSERT INTO "public"."user_integral_account" VALUES (323294542000427008, 1, '2026-04-25 09:10:25.335392', 0, 1, '2026-04-25 09:10:25.335392', NULL, 1, 1, 0);

-- ----------------------------
-- Table structure for user_integral_record
-- ----------------------------
DROP TABLE IF EXISTS "public"."user_integral_record";
CREATE TABLE "public"."user_integral_record" (
  "id" int8 NOT NULL,
  "create_by" int8,
  "create_time" timestamp(6),
  "deleted" int4,
  "update_by" int8,
  "update_time" timestamp(6),
  "after_growth_value" int4,
  "before_growth_value" int4,
  "biz_type" varchar(255) COLLATE "pg_catalog"."default",
  "business_id" int8,
  "business_name" varchar(255) COLLATE "pg_catalog"."default",
  "change_value" int8,
  "description" text COLLATE "pg_catalog"."default",
  "status" int4,
  "type" int4,
  "user_id" int8,
  "after_integral" int4,
  "before_integral" int4
)
;

-- ----------------------------
-- Records of user_integral_record
-- ----------------------------

-- ----------------------------
-- Table structure for user_level_account
-- ----------------------------
DROP TABLE IF EXISTS "public"."user_level_account";
CREATE TABLE "public"."user_level_account" (
  "id" int8 NOT NULL,
  "create_by" int8,
  "create_time" timestamp(6),
  "deleted" int4,
  "update_by" int8,
  "update_time" timestamp(6),
  "avatar" varchar(255) COLLATE "pg_catalog"."default",
  "avatar_frame" varchar(255) COLLATE "pg_catalog"."default",
  "growth_value" int8,
  "status" int4,
  "user_id" int8
)
;

-- ----------------------------
-- Records of user_level_account
-- ----------------------------
INSERT INTO "public"."user_level_account" VALUES (323294541887180800, 1, '2026-04-25 09:10:25.308599', 0, 1, '2026-05-19 02:27:17.993796', '315745504677990400', '314636544130551808', 6000, 1, 1);

-- ----------------------------
-- Table structure for user_level_config
-- ----------------------------
DROP TABLE IF EXISTS "public"."user_level_config";
CREATE TABLE "public"."user_level_config" (
  "id" int8 NOT NULL,
  "create_by" int8,
  "create_time" timestamp(6),
  "deleted" int4,
  "update_by" int8,
  "update_time" timestamp(6),
  "background" varchar(255) COLLATE "pg_catalog"."default",
  "color" varchar(255) COLLATE "pg_catalog"."default",
  "description" text COLLATE "pg_catalog"."default",
  "growth_value" int8,
  "icon" varchar(255) COLLATE "pg_catalog"."default",
  "status" int4,
  "title" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of user_level_config
-- ----------------------------
INSERT INTO "public"."user_level_config" VALUES (323234056072466432, 1, '2026-04-25 05:10:04.366147', 0, 1, '2026-04-25 05:10:04.366147', '', '', '', 300, '', 1, '二级');
INSERT INTO "public"."user_level_config" VALUES (323234111139483648, 1, '2026-04-25 05:10:17.495328', 0, 1, '2026-04-25 05:10:17.495328', '', '', '', 6000, '', 1, '三级');
INSERT INTO "public"."user_level_config" VALUES (323234199165341696, 1, '2026-04-25 05:10:38.482043', 0, 1, '2026-05-07 08:54:10.33041', '', '', '<img data-img-id="327635300161556480" />', 10000, '', 1, '四级');
INSERT INTO "public"."user_level_config" VALUES (323234007137521664, 1, '2026-04-25 05:09:52.699853', 0, 1, '2026-04-28 08:58:54.081653', 'fff', '', '<table style="min-width:125px"><colgroup><col style="min-width:25px" /><col style="min-width:25px" /><col style="min-width:25px" /><col style="min-width:25px" /><col style="min-width:25px" /></colgroup><tbody><tr><th colspan="1" rowspan="1"><p>啊啊</p></th><th colspan="1" rowspan="1"><p>bb</p></th><th colspan="1" rowspan="1"><p>cc</p></th><th colspan="1" rowspan="1"><p>dd</p></th><th colspan="1" rowspan="1"><p>ee</p></th></tr><tr><td colspan="1" rowspan="1"><p></p></td><td colspan="1" rowspan="1"><p></p></td><td colspan="1" rowspan="1"><p></p></td><td colspan="1" rowspan="1"><p></p></td><td colspan="1" rowspan="1"><p></p></td></tr></tbody></table>', 0, '', 1, '一级');

-- ----------------------------
-- Table structure for user_level_record
-- ----------------------------
DROP TABLE IF EXISTS "public"."user_level_record";
CREATE TABLE "public"."user_level_record" (
  "id" int8 NOT NULL,
  "create_by" int8,
  "create_time" timestamp(6),
  "deleted" int4,
  "update_by" int8,
  "update_time" timestamp(6),
  "after_growth_value" int4,
  "before_growth_value" int4,
  "biz_type" varchar(255) COLLATE "pg_catalog"."default",
  "business_id" int8,
  "business_name" varchar(255) COLLATE "pg_catalog"."default",
  "change_value" int8,
  "description" text COLLATE "pg_catalog"."default",
  "status" int4,
  "type" int4,
  "user_id" int8
)
;

-- ----------------------------
-- Records of user_level_record
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table content_attachment
-- ----------------------------
ALTER TABLE "public"."content_attachment" ADD CONSTRAINT "content_attachment_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table content_audit_log
-- ----------------------------
ALTER TABLE "public"."content_audit_log" ADD CONSTRAINT "content_audit_log_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table content_body
-- ----------------------------
ALTER TABLE "public"."content_body" ADD CONSTRAINT "content_body_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table content_category
-- ----------------------------
ALTER TABLE "public"."content_category" ADD CONSTRAINT "content_category_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table content_category_rel
-- ----------------------------
ALTER TABLE "public"."content_category_rel" ADD CONSTRAINT "content_category_rel_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table content_comment
-- ----------------------------
ALTER TABLE "public"."content_comment" ADD CONSTRAINT "content_comment_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table content_comment_like
-- ----------------------------
ALTER TABLE "public"."content_comment_like" ADD CONSTRAINT "content_comment_like_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table content_favorite
-- ----------------------------
ALTER TABLE "public"."content_favorite" ADD CONSTRAINT "content_favorite_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table content_info
-- ----------------------------
ALTER TABLE "public"."content_info" ADD CONSTRAINT "content_info_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table content_like
-- ----------------------------
ALTER TABLE "public"."content_like" ADD CONSTRAINT "content_like_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table content_report
-- ----------------------------
ALTER TABLE "public"."content_report" ADD CONSTRAINT "content_report_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table content_revision
-- ----------------------------
ALTER TABLE "public"."content_revision" ADD CONSTRAINT "content_revision_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table content_tag
-- ----------------------------
ALTER TABLE "public"."content_tag" ADD CONSTRAINT "content_tag_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table content_tag_rel
-- ----------------------------
ALTER TABLE "public"."content_tag_rel" ADD CONSTRAINT "content_tag_rel_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table content_view_log
-- ----------------------------
ALTER TABLE "public"."content_view_log" ADD CONSTRAINT "content_view_log_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table file_config
-- ----------------------------
ALTER TABLE "public"."file_config" ADD CONSTRAINT "file_config_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table file_domain
-- ----------------------------
ALTER TABLE "public"."file_domain" ADD CONSTRAINT "file_domain_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table file_info
-- ----------------------------
ALTER TABLE "public"."file_info" ADD CONSTRAINT "file_info_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table file_type
-- ----------------------------
ALTER TABLE "public"."file_type" ADD CONSTRAINT "file_type_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table mall_cart
-- ----------------------------
ALTER TABLE "public"."mall_cart" ADD CONSTRAINT "mall_cart_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table mall_freight_template
-- ----------------------------
ALTER TABLE "public"."mall_freight_template" ADD CONSTRAINT "mall_freight_template_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table mall_order
-- ----------------------------
ALTER TABLE "public"."mall_order" ADD CONSTRAINT "mall_order_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table mall_order_item
-- ----------------------------
ALTER TABLE "public"."mall_order_item" ADD CONSTRAINT "mall_order_item_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table mall_order_log
-- ----------------------------
ALTER TABLE "public"."mall_order_log" ADD CONSTRAINT "mall_order_log_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table mall_order_refund
-- ----------------------------
ALTER TABLE "public"."mall_order_refund" ADD CONSTRAINT "mall_order_refund_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table mall_pickup_shop
-- ----------------------------
ALTER TABLE "public"."mall_pickup_shop" ADD CONSTRAINT "mall_pickup_shop_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table mall_product
-- ----------------------------
ALTER TABLE "public"."mall_product" ADD CONSTRAINT "mall_product_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table mall_product_brand
-- ----------------------------
ALTER TABLE "public"."mall_product_brand" ADD CONSTRAINT "mall_product_brand_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table mall_product_category
-- ----------------------------
ALTER TABLE "public"."mall_product_category" ADD CONSTRAINT "mall_product_category_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table mall_product_sku
-- ----------------------------
ALTER TABLE "public"."mall_product_sku" ADD CONSTRAINT "mall_product_sku_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table mall_shop
-- ----------------------------
ALTER TABLE "public"."mall_shop" ADD CONSTRAINT "mall_shop_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table mall_user_address
-- ----------------------------
ALTER TABLE "public"."mall_user_address" ADD CONSTRAINT "mall_user_address_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table message_config
-- ----------------------------
ALTER TABLE "public"."message_config" ADD CONSTRAINT "message_config_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table message_record
-- ----------------------------
ALTER TABLE "public"."message_record" ADD CONSTRAINT "message_record_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table message_template
-- ----------------------------
ALTER TABLE "public"."message_template" ADD CONSTRAINT "message_template_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table message_type
-- ----------------------------
ALTER TABLE "public"."message_type" ADD CONSTRAINT "message_type_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table message_verification_code
-- ----------------------------
ALTER TABLE "public"."message_verification_code" ADD CONSTRAINT "message_verification_code_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table page_application
-- ----------------------------
ALTER TABLE "public"."page_application" ADD CONSTRAINT "page_application_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table page_component
-- ----------------------------
ALTER TABLE "public"."page_component" ADD CONSTRAINT "page_component_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table page_config
-- ----------------------------
ALTER TABLE "public"."page_config" ADD CONSTRAINT "page_config_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table page_type
-- ----------------------------
ALTER TABLE "public"."page_type" ADD CONSTRAINT "page_type_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table page_web_config
-- ----------------------------
ALTER TABLE "public"."page_web_config" ADD CONSTRAINT "page_web_config_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Checks structure for table rate_limit_api_config
-- ----------------------------
ALTER TABLE "public"."rate_limit_api_config" ADD CONSTRAINT "rate_limit_api_config_limit_dimension_check" CHECK (limit_dimension::text = ANY (ARRAY['GLOBAL'::character varying, 'IP'::character varying, 'USER'::character varying]::text[]));

-- ----------------------------
-- Primary Key structure for table rate_limit_api_config
-- ----------------------------
ALTER TABLE "public"."rate_limit_api_config" ADD CONSTRAINT "rate_limit_api_config_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table rate_limit_global_config
-- ----------------------------
ALTER TABLE "public"."rate_limit_global_config" ADD CONSTRAINT "rate_limit_global_config_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Checks structure for table rate_limit_ip_black_white
-- ----------------------------
ALTER TABLE "public"."rate_limit_ip_black_white" ADD CONSTRAINT "rate_limit_ip_black_white_list_type_check" CHECK (list_type::text = ANY (ARRAY['BLACK'::character varying, 'WHITE'::character varying]::text[]));

-- ----------------------------
-- Primary Key structure for table rate_limit_ip_black_white
-- ----------------------------
ALTER TABLE "public"."rate_limit_ip_black_white" ADD CONSTRAINT "rate_limit_ip_black_white_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table rate_limit_ip_config
-- ----------------------------
ALTER TABLE "public"."rate_limit_ip_config" ADD CONSTRAINT "rate_limit_ip_config_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Checks structure for table rate_limit_record
-- ----------------------------
ALTER TABLE "public"."rate_limit_record" ADD CONSTRAINT "rate_limit_record_limit_type_check" CHECK (limit_type::text = ANY (ARRAY['GLOBAL'::character varying, 'IP'::character varying, 'USER'::character varying, 'API'::character varying]::text[]));

-- ----------------------------
-- Primary Key structure for table rate_limit_record
-- ----------------------------
ALTER TABLE "public"."rate_limit_record" ADD CONSTRAINT "rate_limit_record_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Checks structure for table rate_limit_user_black_white
-- ----------------------------
ALTER TABLE "public"."rate_limit_user_black_white" ADD CONSTRAINT "rate_limit_user_black_white_list_type_check" CHECK (list_type::text = ANY (ARRAY['BLACK'::character varying, 'WHITE'::character varying]::text[]));

-- ----------------------------
-- Primary Key structure for table rate_limit_user_black_white
-- ----------------------------
ALTER TABLE "public"."rate_limit_user_black_white" ADD CONSTRAINT "rate_limit_user_black_white_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_config
-- ----------------------------
ALTER TABLE "public"."sys_config" ADD CONSTRAINT "sys_config_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_department
-- ----------------------------
ALTER TABLE "public"."sys_department" ADD CONSTRAINT "sys_department_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_dict_data
-- ----------------------------
ALTER TABLE "public"."sys_dict_data" ADD CONSTRAINT "sys_dict_data_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_dict_type
-- ----------------------------
ALTER TABLE "public"."sys_dict_type" ADD CONSTRAINT "sys_dict_type_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_idempotent_duplicate_log
-- ----------------------------
ALTER TABLE "public"."sys_idempotent_duplicate_log" ADD CONSTRAINT "sys_idempotent_duplicate_log_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Checks structure for table sys_operation_log
-- ----------------------------
ALTER TABLE "public"."sys_operation_log" ADD CONSTRAINT "sys_operation_log_action_check" CHECK (action::text = ANY (ARRAY['QUERY'::character varying, 'CREATE'::character varying, 'UPDATE'::character varying, 'DELETE'::character varying, 'IMPORT'::character varying, 'EXPORT'::character varying, 'LOGIN'::character varying, 'LOGOUT'::character varying, 'OTHER'::character varying]::text[]));
ALTER TABLE "public"."sys_operation_log" ADD CONSTRAINT "sys_operation_log_type_check" CHECK (type::text = ANY (ARRAY['SYSTEM'::character varying, 'BUSINESS'::character varying, 'LOGIN'::character varying, 'EXCEPTION'::character varying, 'OPERATION'::character varying]::text[]));

-- ----------------------------
-- Primary Key structure for table sys_operation_log
-- ----------------------------
ALTER TABLE "public"."sys_operation_log" ADD CONSTRAINT "sys_operation_log_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_permissions
-- ----------------------------
ALTER TABLE "public"."sys_permissions" ADD CONSTRAINT "sys_permissions_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_post
-- ----------------------------
ALTER TABLE "public"."sys_post" ADD CONSTRAINT "sys_post_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_role
-- ----------------------------
ALTER TABLE "public"."sys_role" ADD CONSTRAINT "sys_role_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_role_permissions
-- ----------------------------
ALTER TABLE "public"."sys_role_permissions" ADD CONSTRAINT "sys_role_permissions_pkey" PRIMARY KEY ("role_id", "permission_id");

-- ----------------------------
-- Checks structure for table sys_slow_query_log
-- ----------------------------
ALTER TABLE "public"."sys_slow_query_log" ADD CONSTRAINT "sys_slow_query_log_severity_check" CHECK (severity::text = ANY (ARRAY['WARNING'::character varying, 'CRITICAL'::character varying, 'FATAL'::character varying]::text[]));
ALTER TABLE "public"."sys_slow_query_log" ADD CONSTRAINT "sys_slow_query_log_status_check" CHECK (status::text = ANY (ARRAY['PENDING'::character varying, 'IGNORED'::character varying]::text[]));

-- ----------------------------
-- Primary Key structure for table sys_slow_query_log
-- ----------------------------
ALTER TABLE "public"."sys_slow_query_log" ADD CONSTRAINT "sys_slow_query_log_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_tenant
-- ----------------------------
ALTER TABLE "public"."sys_tenant" ADD CONSTRAINT "sys_tenant_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_user_role
-- ----------------------------
ALTER TABLE "public"."sys_user_role" ADD CONSTRAINT "sys_user_role_pkey" PRIMARY KEY ("user_id", "role_id");

-- ----------------------------
-- Primary Key structure for table sys_users
-- ----------------------------
ALTER TABLE "public"."sys_users" ADD CONSTRAINT "sys_users_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table user_integral_account
-- ----------------------------
ALTER TABLE "public"."user_integral_account" ADD CONSTRAINT "user_integral_account_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table user_integral_record
-- ----------------------------
ALTER TABLE "public"."user_integral_record" ADD CONSTRAINT "user_integral_record_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table user_level_account
-- ----------------------------
ALTER TABLE "public"."user_level_account" ADD CONSTRAINT "user_level_account_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table user_level_config
-- ----------------------------
ALTER TABLE "public"."user_level_config" ADD CONSTRAINT "user_level_config_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table user_level_record
-- ----------------------------
ALTER TABLE "public"."user_level_record" ADD CONSTRAINT "user_level_record_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Foreign Keys structure for table mall_product
-- ----------------------------
ALTER TABLE "public"."mall_product" ADD CONSTRAINT "fkb3w6bfjl3mu5vin5wqo86qbba" FOREIGN KEY ("shop_id") REFERENCES "public"."mall_shop" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."mall_product" ADD CONSTRAINT "fkj8x1pcxqab5ue3apy6cxowc70" FOREIGN KEY ("category_id") REFERENCES "public"."mall_product_category" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."mall_product" ADD CONSTRAINT "fkmsnu189dexvfh3ujyluasocje" FOREIGN KEY ("brand_id") REFERENCES "public"."mall_product_brand" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table page_application
-- ----------------------------
ALTER TABLE "public"."page_application" ADD CONSTRAINT "fk9iejlqt1x19i4kvmd23c9cplp" FOREIGN KEY ("type_id") REFERENCES "public"."page_type" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table page_component
-- ----------------------------
ALTER TABLE "public"."page_component" ADD CONSTRAINT "fkphtkoapwtla5bmu7vygjdg7tt" FOREIGN KEY ("type_id") REFERENCES "public"."page_type" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table page_config
-- ----------------------------
ALTER TABLE "public"."page_config" ADD CONSTRAINT "fktbno3y1olo0gb8sveq63lhf67" FOREIGN KEY ("application_id") REFERENCES "public"."page_application" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table page_web_config
-- ----------------------------
ALTER TABLE "public"."page_web_config" ADD CONSTRAINT "fk6r2smbxf2gpjptpd0p9hod3i3" FOREIGN KEY ("application_id") REFERENCES "public"."page_application" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table sys_role_permissions
-- ----------------------------
ALTER TABLE "public"."sys_role_permissions" ADD CONSTRAINT "fka6lycvisbrxexjyanm5grngc0" FOREIGN KEY ("permission_id") REFERENCES "public"."sys_permissions" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."sys_role_permissions" ADD CONSTRAINT "fkaa4k4qhr2qdj6br8p5nrb2lhb" FOREIGN KEY ("role_id") REFERENCES "public"."sys_role" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table sys_user_role
-- ----------------------------
ALTER TABLE "public"."sys_user_role" ADD CONSTRAINT "fke2itwtwrl60nromvh4kqr5y97" FOREIGN KEY ("user_id") REFERENCES "public"."sys_users" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."sys_user_role" ADD CONSTRAINT "fkhh52n8vd4ny9ff4x9fb8v65qx" FOREIGN KEY ("role_id") REFERENCES "public"."sys_role" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table sys_users
-- ----------------------------
ALTER TABLE "public"."sys_users" ADD CONSTRAINT "fkipc5ydv0cuwme5ojlwaek71ep" FOREIGN KEY ("department_id") REFERENCES "public"."sys_department" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."sys_users" ADD CONSTRAINT "fksqsj6umfhb35lsc8dhdrvx46n" FOREIGN KEY ("post_id") REFERENCES "public"."sys_post" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
