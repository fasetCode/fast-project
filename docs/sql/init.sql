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

 Date: 22/05/2026 06:30:01
*/


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
INSERT INTO "public"."rate_limit_record" VALUES (321048502081818624, NULL, '2026-04-19 04:25:27.689046', 0, NULL, '2026-04-19 04:25:27.689046', 'admin', '{"sec-fetch-mode":"cors","referer":"http://localhost:5173/","sec-fetch-site":"same-origin","accept-language":"zh-CN,zh;q=0.9","cookie":"Webstorm-be57267c=3eadc49d-14bd-4aa2-afec-2d25156fcfc6; Idea-8296fa34=a860d207-ed9b-4911-b7bf-2ba129f8f40c; Idea-8296fa36=4c326892-ce98-4765-969c-3d9d07072086; Webstorm-be57267d=02b3c472-4cad-4ee7-9bb2-3b64df4a104d; i18n_redirected=zh; Hm_lvt_5819d05c0869771ff6e6a81cdec5b2e8=1770431378","accept":"application/json, text/plain, */*","sec-ch-ua":"\"Google Chrome\";v=\"147\", \"Not.A/Brand\";v=\"8\", \"Chromium\";v=\"147\"","sec-ch-ua-mobile":"?0","sec-ch-ua-platform":"\"Windows\"","host":"localhost:1230","connection":"close","accept-encoding":"gzip, deflate, br, zstd","user-agent":"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/147.0.0.0 Safari/537.36","sec-fetch-dest":"empty"}', '0:0:0:0:0:0:0:1', 'api_limit:GET:/auth/captcha', 'ExAPI (配置: 2/5s, 维度: IP)', 'API', 'GET', NULL, '0:0:0:0:0:0:0:1', '/auth/captcha', NULL);
INSERT INTO "public"."rate_limit_record" VALUES (322905910731214848, NULL, '2026-04-24 07:26:08.420297', 0, NULL, '2026-04-24 07:26:08.420297', 'admin', '{"sec-fetch-mode":"cors","referer":"http://localhost:1234/","sec-fetch-site":"same-origin","accept-language":"zh-CN,zh;q=0.9","cookie":"Webstorm-be57267c=3eadc49d-14bd-4aa2-afec-2d25156fcfc6; Idea-8296fa34=a860d207-ed9b-4911-b7bf-2ba129f8f40c; Idea-8296fa36=4c326892-ce98-4765-969c-3d9d07072086; Webstorm-be57267d=02b3c472-4cad-4ee7-9bb2-3b64df4a104d; i18n_redirected=zh; Hm_lvt_5819d05c0869771ff6e6a81cdec5b2e8=1770431378","accept":"application/json, text/plain, */*","sec-ch-ua":"\"Google Chrome\";v=\"147\", \"Not.A/Brand\";v=\"8\", \"Chromium\";v=\"147\"","sec-ch-ua-mobile":"?0","sec-ch-ua-platform":"\"Windows\"","host":"localhost:1230","connection":"close","accept-encoding":"gzip, deflate, br, zstd","user-agent":"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/147.0.0.0 Safari/537.36","sec-fetch-dest":"empty"}', '0:0:0:0:0:0:0:1', 'api_limit:GET:/auth/captcha', 'ExAPI (配置: 2/5s, 维度: IP)', 'API', 'GET', NULL, '0:0:0:0:0:0:0:1', '/auth/captcha', NULL);
INSERT INTO "public"."rate_limit_record" VALUES (325376008867942400, NULL, '2026-05-01 03:01:25.708491', 0, NULL, '2026-05-01 03:01:25.708491', 'admin', '{"Origin":"http://192.168.1.198:1234","Accept":"application/json, text/plain, */*","Connection":"keep-alive","User-Agent":"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/147.0.0.0 Safari/537.36","Referer":"http://192.168.1.198:1234/","Host":"192.168.1.198:1230","Accept-Encoding":"gzip, deflate","Accept-Language":"zh-CN,zh;q=0.9"}', '192.168.1.200', 'api_limit:GET:/auth/captcha', 'ExAPI (配置: 2/5s, 维度: IP)', 'API', 'GET', NULL, '192.168.1.200', '/auth/captcha', NULL);
INSERT INTO "public"."rate_limit_record" VALUES (325376237319098368, NULL, '2026-05-01 03:02:20.175344', 0, NULL, '2026-05-01 03:02:20.175344', 'admin', '{"Origin":"http://192.168.1.198:1234","Accept":"application/json, text/plain, */*","Connection":"keep-alive","User-Agent":"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/147.0.0.0 Safari/537.36","Referer":"http://192.168.1.198:1234/","Host":"192.168.1.198:1230","Accept-Encoding":"gzip, deflate","Accept-Language":"zh-CN,zh;q=0.9"}', '192.168.1.198', 'api_limit:GET:/auth/captcha', 'ExAPI (配置: 2/5s, 维度: IP)', 'API', 'GET', NULL, '192.168.1.198', '/auth/captcha', NULL);
INSERT INTO "public"."rate_limit_record" VALUES (325376319976247296, NULL, '2026-05-01 03:02:39.882884', 0, NULL, '2026-05-01 03:02:39.882884', 'admin', '{"Origin":"http://192.168.1.198:1234","Accept":"application/json, text/plain, */*","Connection":"keep-alive","User-Agent":"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/147.0.0.0 Safari/537.36","Referer":"http://192.168.1.198:1234/","Host":"192.168.1.198:1230","Accept-Encoding":"gzip, deflate","Accept-Language":"zh-CN,zh;q=0.9"}', '192.168.1.200', 'api_limit:GET:/auth/captcha', 'ExAPI (配置: 2/5s, 维度: IP)', 'API', 'GET', NULL, '192.168.1.200', '/auth/captcha', NULL);
INSERT INTO "public"."rate_limit_record" VALUES (325376326825545728, NULL, '2026-05-01 03:02:41.51508', 0, NULL, '2026-05-01 03:02:41.51508', 'admin', '{"Origin":"http://192.168.1.198:1234","Accept":"application/json, text/plain, */*","Connection":"keep-alive","User-Agent":"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/147.0.0.0 Safari/537.36","Referer":"http://192.168.1.198:1234/","Host":"192.168.1.198:1230","Accept-Encoding":"gzip, deflate","Accept-Language":"zh-CN,zh;q=0.9"}', '192.168.1.200', 'api_limit:GET:/auth/captcha', 'ExAPI (配置: 2/5s, 维度: IP)', 'API', 'GET', NULL, '192.168.1.200', '/auth/captcha', NULL);
INSERT INTO "public"."rate_limit_record" VALUES (325376330235514880, NULL, '2026-05-01 03:02:42.328453', 0, NULL, '2026-05-01 03:02:42.328453', 'admin', '{"Origin":"http://192.168.1.198:1234","Accept":"application/json, text/plain, */*","Connection":"keep-alive","User-Agent":"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/147.0.0.0 Safari/537.36","Referer":"http://192.168.1.198:1234/","Host":"192.168.1.198:1230","Accept-Encoding":"gzip, deflate","Accept-Language":"zh-CN,zh;q=0.9"}', '192.168.1.200', 'api_limit:GET:/auth/captcha', 'ExAPI (配置: 2/5s, 维度: IP)', 'API', 'GET', NULL, '192.168.1.200', '/auth/captcha', NULL);
INSERT INTO "public"."rate_limit_record" VALUES (325376654153224192, NULL, '2026-05-01 03:03:59.556852', 0, NULL, '2026-05-01 03:03:59.556852', 'admin', '{"Origin":"http://192.168.1.198:1234","Accept":"application/json, text/plain, */*","Connection":"keep-alive","User-Agent":"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/147.0.0.0 Safari/537.36","Referer":"http://192.168.1.198:1234/","Host":"192.168.1.198:1230","Accept-Encoding":"gzip, deflate","Accept-Language":"zh-CN,zh;q=0.9"}', '192.168.1.198', 'api_limit:GET:/auth/captcha', 'ExAPI (配置: 2/5s, 维度: IP)', 'API', 'GET', NULL, '192.168.1.198', '/auth/captcha', NULL);
INSERT INTO "public"."rate_limit_record" VALUES (325376697115480064, NULL, '2026-05-01 03:04:09.799113', 0, NULL, '2026-05-01 03:04:09.799113', 'admin', '{"Origin":"http://192.168.1.198:1234","Accept":"application/json, text/plain, */*","Connection":"keep-alive","User-Agent":"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/147.0.0.0 Safari/537.36","Referer":"http://192.168.1.198:1234/","Host":"192.168.1.198:1230","Accept-Encoding":"gzip, deflate","Accept-Language":"zh-CN,zh;q=0.9"}', '192.168.1.198', 'api_limit:GET:/auth/captcha', 'ExAPI (配置: 2/5s, 维度: IP)', 'API', 'GET', NULL, '192.168.1.198', '/auth/captcha', NULL);
INSERT INTO "public"."rate_limit_record" VALUES (325376721018818560, NULL, '2026-05-01 03:04:15.498388', 0, NULL, '2026-05-01 03:04:15.498388', 'admin', '{"Origin":"http://192.168.1.198:1234","Accept":"application/json, text/plain, */*","Connection":"keep-alive","User-Agent":"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/147.0.0.0 Safari/537.36","Referer":"http://192.168.1.198:1234/","Host":"192.168.1.198:1230","Accept-Encoding":"gzip, deflate","Accept-Language":"zh-CN,zh;q=0.9"}', '192.168.1.200', 'api_limit:GET:/auth/captcha', 'ExAPI (配置: 2/5s, 维度: IP)', 'API', 'GET', NULL, '192.168.1.200', '/auth/captcha', NULL);
INSERT INTO "public"."rate_limit_record" VALUES (325377696265801728, NULL, '2026-05-01 03:08:08.015374', 0, NULL, '2026-05-01 03:08:08.015374', 'admin', '{"Origin":"http://192.168.1.198:1234","Accept":"application/json, text/plain, */*","Connection":"keep-alive","User-Agent":"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/147.0.0.0 Safari/537.36","Referer":"http://192.168.1.198:1234/","Host":"192.168.1.198:1230","Accept-Encoding":"gzip, deflate","Accept-Language":"zh-CN,zh;q=0.9"}', '192.168.1.200', 'api_limit:GET:/auth/captcha', 'ExAPI (配置: 2/5s, 维度: IP)', 'API', 'GET', NULL, '192.168.1.200', '/auth/captcha', NULL);
INSERT INTO "public"."rate_limit_record" VALUES (325377704830570496, NULL, '2026-05-01 03:08:10.057517', 0, NULL, '2026-05-01 03:08:10.057517', 'admin', '{"Origin":"http://192.168.1.198:1234","Accept":"application/json, text/plain, */*","Connection":"keep-alive","User-Agent":"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/147.0.0.0 Safari/537.36","Referer":"http://192.168.1.198:1234/","Host":"192.168.1.198:1230","Accept-Encoding":"gzip, deflate","Accept-Language":"zh-CN,zh;q=0.9"}', '192.168.1.200', 'api_limit:GET:/auth/captcha', 'ExAPI (配置: 2/5s, 维度: IP)', 'API', 'GET', NULL, '192.168.1.200', '/auth/captcha', NULL);
INSERT INTO "public"."rate_limit_record" VALUES (325377707447816192, NULL, '2026-05-01 03:08:10.681618', 0, NULL, '2026-05-01 03:08:10.681618', 'admin', '{"Origin":"http://192.168.1.198:1234","Accept":"application/json, text/plain, */*","Connection":"keep-alive","User-Agent":"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/147.0.0.0 Safari/537.36","Referer":"http://192.168.1.198:1234/","Host":"192.168.1.198:1230","Accept-Encoding":"gzip, deflate","Accept-Language":"zh-CN,zh;q=0.9"}', '192.168.1.200', 'api_limit:GET:/auth/captcha', 'ExAPI (配置: 2/5s, 维度: IP)', 'API', 'GET', NULL, '192.168.1.200', '/auth/captcha', NULL);
INSERT INTO "public"."rate_limit_record" VALUES (325377716566233088, NULL, '2026-05-01 03:08:12.855847', 0, NULL, '2026-05-01 03:08:12.855847', 'admin', '{"Origin":"http://192.168.1.198:1234","Accept":"application/json, text/plain, */*","Connection":"keep-alive","User-Agent":"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/147.0.0.0 Safari/537.36","Referer":"http://192.168.1.198:1234/","Host":"192.168.1.198:1230","Accept-Encoding":"gzip, deflate","Accept-Language":"zh-CN,zh;q=0.9"}', '192.168.1.200', 'api_limit:GET:/auth/captcha', 'ExAPI (配置: 2/5s, 维度: IP)', 'API', 'GET', NULL, '192.168.1.200', '/auth/captcha', NULL);
INSERT INTO "public"."rate_limit_record" VALUES (325377718592081920, NULL, '2026-05-01 03:08:13.338142', 0, NULL, '2026-05-01 03:08:13.338142', 'admin', '{"Origin":"http://192.168.1.198:1234","Accept":"application/json, text/plain, */*","Connection":"keep-alive","User-Agent":"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/147.0.0.0 Safari/537.36","Referer":"http://192.168.1.198:1234/","Host":"192.168.1.198:1230","Accept-Encoding":"gzip, deflate","Accept-Language":"zh-CN,zh;q=0.9"}', '192.168.1.200', 'api_limit:GET:/auth/captcha', 'ExAPI (配置: 2/5s, 维度: IP)', 'API', 'GET', NULL, '192.168.1.200', '/auth/captcha', NULL);

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
INSERT INTO "public"."sys_operation_log" VALUES (317002036128387072, 1, '2026-04-08 00:26:15.026165', 0, NULL, '2026-04-08 00:26:15.026165', 'UPDATE', 'com.fastproject.module.system.controller.SysUsersController', '后台用户修改', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=SysUserUpdate(id=311663478626717696, username=admin1, nickname=管理员1, email=, phone=, gender=1, status=1, departmentId=null, postId=null, roleIds=[], avatar=null)', NULL, 't', 26, 'BUSINESS', '/sys/users');
INSERT INTO "public"."sys_operation_log" VALUES (317003151968440320, 1, '2026-04-08 00:30:41.063483', 0, NULL, '2026-04-08 00:30:41.063483', 'UPDATE', 'com.fastproject.module.system.controller.SysUsersController', '后台用户修改', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=SysUserUpdate(id=1, username=admin, nickname=管理员, email=2@qq.com, phone=18711321234, gender=1, status=1, departmentId=313513559294349312, postId=313512533233373184, roleIds=[311703722914877440], avatar=http://127.0.0.1/file/2026/04/04/beab13a3ddd6e0fd1a2787f32c338a53.png)', NULL, 't', 36, 'BUSINESS', '/sys/users');
INSERT INTO "public"."sys_operation_log" VALUES (317003608266772480, 1, '2026-04-08 00:32:29.85398', 0, NULL, '2026-04-08 00:32:29.85398', 'UPDATE', 'com.fastproject.module.system.controller.SysRoleController', '修改角色', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=SysRoleUpdate(id=311703722914877440, title=超级管理员, code=admin, status=1, applicationId=null, applicationCode=, permissionIds=[313512003115290624, 313549686420148224, 311762357514801152, 311760876300537856, 313550330241617920, 313511610658459648, 314602359294660608, 312424744588808192, 313512229460905984, 314530113607831552, 312425344407834624, 313550330359058432, 314602586261032960, 312424193046220800, 313511610620710912, 314513638113284096, 313512229410574336, 314529906530848768, 312425344483332... [截断]', NULL, 't', 59, 'BUSINESS', '/sys/role');
INSERT INTO "public"."sys_operation_log" VALUES (317479311449919488, NULL, '2026-04-09 08:02:46.330303', 0, NULL, '2026-04-09 08:02:46.330817', 'LOGIN', 'com.fastproject.module.system.controller.AuthController', '用户登录', NULL, 'POST', '0:0:0:0:0:0:0:1', 'login', 'arg0=LoginDto(username=admin, password=123456, captchaKey=2c1fa263b46d45169ff4d6668c906f3d, captcha=R5AC)', NULL, 't', 109, 'LOGIN', '/auth/login');
INSERT INTO "public"."sys_operation_log" VALUES (317480420511977472, 1, '2026-04-09 08:07:10.751703', 0, NULL, '2026-04-09 08:07:10.751703', 'UPDATE', 'com.fastproject.module.system.controller.SysUsersController', '后台用户修改', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=SysUserUpdate(id=1, username=admin, nickname=管理员, email=2@qq.com, phone=18711321234, gender=1, status=1, departmentId=313513559294349312, postId=313512533233373184, roleIds=[311703722914877440], avatar=http://127.0.0.1/file/2026/04/04/beab13a3ddd6e0fd1a2787f32c338a53.png)', NULL, 't', 17, 'BUSINESS', '/sys/users');
INSERT INTO "public"."sys_operation_log" VALUES (317480431148732416, 1, '2026-04-09 08:07:13.287538', 0, NULL, '2026-04-09 08:07:13.287538', 'UPDATE', 'com.fastproject.module.system.controller.SysUsersController', '后台用户修改', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=SysUserUpdate(id=1, username=admin, nickname=管理员, email=2@qq.com, phone=18711321234, gender=1, status=1, departmentId=313513559294349312, postId=313512533233373184, roleIds=[311703722914877440], avatar=http://127.0.0.1/file/2026/04/04/beab13a3ddd6e0fd1a2787f32c338a53.png)', NULL, 't', 17, 'BUSINESS', '/sys/users');
INSERT INTO "public"."sys_operation_log" VALUES (317541534360801280, NULL, '2026-04-09 12:10:01.428345', 0, NULL, '2026-04-09 12:10:01.428345', 'LOGIN', 'com.fastproject.module.system.controller.AuthController', '用户登录', NULL, 'POST', '0:0:0:0:0:0:0:1', 'login', 'arg0=LoginDto(username=admin, password=123456, captchaKey=7f26f1e3702a4b78b9559f396d8b3921, captcha=xzue)', NULL, 't', 64, 'LOGIN', '/auth/login');
INSERT INTO "public"."sys_operation_log" VALUES (318140483568275456, NULL, '2026-04-11 03:50:02.047561', 0, NULL, '2026-04-11 03:50:02.047561', 'LOGIN', 'com.fastproject.module.system.controller.AuthController', '用户登录', NULL, 'POST', '0:0:0:0:0:0:0:1', 'login', 'arg0=LoginDto(username=admin, password=123456, captchaKey=fd66b953c58b4b7681cc2cf31f79af22, captcha=pfy6)', NULL, 't', 193, 'LOGIN', '/auth/login');
INSERT INTO "public"."sys_operation_log" VALUES (318980745861402624, NULL, '2026-04-13 11:28:56.189845', 0, NULL, '2026-04-13 11:28:56.189845', 'LOGIN', 'com.fastproject.module.system.controller.AuthController', '用户登录', NULL, 'POST', '0:0:0:0:0:0:0:1', 'login', 'arg0=LoginDto(username=admin, password=123456, captchaKey=75025d62fe214094a949c03244a8d953, captcha=ryds)', NULL, 't', 165, 'LOGIN', '/auth/login');
INSERT INTO "public"."sys_operation_log" VALUES (318993678750322688, 1, '2026-04-13 12:20:19.630231', 0, NULL, '2026-04-13 12:20:19.630231', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysPermissionsCreate(title=限流, code=, url=, type=0, component=, icon=ColumnWidthOutlined, status=1, parentId=null, componentName=, sort=0, applicationId=null, applicationCode=)', NULL, 't', 7, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (318993739760668672, 1, '2026-04-13 12:20:34.176928', 0, NULL, '2026-04-13 12:20:34.176928', 'UPDATE', 'com.fastproject.module.system.controller.SysPermissionsController', '修改权限', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=SysPermissionsUpdate(id=318993678729351168, title=限流, code=, url=, type=0, component=, icon=ColumnWidthOutlined, status=1, parentId=null, componentName=, sort=4, applicationId=null, applicationCode=)', NULL, 't', 15, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (318994124705501184, 1, '2026-04-13 12:22:05.954546', 0, NULL, '2026-04-13 12:22:05.954546', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysPermissionsCreate(title=全局限流配置, code=admin:ratelimit:global-config:page, url=/ratelimit/global-config, type=1, component=views/ratelimit/global-config/index, icon=, status=1, parentId=318993678729351168, componentName=RatelimitGlobalConfig, sort=0, applicationId=null, applicationCode=)', NULL, 't', 4, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (318994357527121920, 1, '2026-04-13 12:23:01.463031', 0, NULL, '2026-04-13 12:23:01.463031', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysPermissionsCreate(title=添加, code=admin:ratelimit:global-config:add, url=, type=2, component=, icon=, status=0, parentId=318994124692918272, componentName=, sort=0, applicationId=null, applicationCode=)', NULL, 't', 4, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (318994357560676352, 1, '2026-04-13 12:23:01.471419', 0, NULL, '2026-04-13 12:23:01.471419', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysPermissionsCreate(title=修改, code=admin:ratelimit:global-config:update, url=, type=2, component=, icon=, status=0, parentId=318994124692918272, componentName=, sort=0, applicationId=null, applicationCode=)', NULL, 't', 2, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (318994357611008000, 1, '2026-04-13 12:23:01.483952', 0, NULL, '2026-04-13 12:23:01.483952', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysPermissionsCreate(title=删除, code=admin:ratelimit:global-config:delete, url=, type=2, component=, icon=, status=0, parentId=318994124692918272, componentName=, sort=0, applicationId=null, applicationCode=)', NULL, 't', 3, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (318994824499957760, 1, '2026-04-13 12:24:52.798489', 0, NULL, '2026-04-13 12:24:52.798489', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysPermissionsCreate(title=配置, code=admin:ratelimit:config:page, url=/ratelimit/config, type=1, component=views/ratelimit/config/index, icon=, status=1, parentId=318993678729351168, componentName=RatelimitConfig, sort=1, applicationId=null, applicationCode=)', NULL, 't', 4, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (318994978003095552, 1, '2026-04-13 12:25:29.396878', 0, NULL, '2026-04-13 12:25:29.396878', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysPermissionsCreate(title=添加, code=admin:ratelimit:config:add, url=, type=2, component=, icon=, status=0, parentId=318994824483180544, componentName=, sort=0, applicationId=null, applicationCode=)', NULL, 't', 4, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (318994978040844288, 1, '2026-04-13 12:25:29.405308', 0, NULL, '2026-04-13 12:25:29.405308', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysPermissionsCreate(title=修改, code=admin:ratelimit:config:update, url=, type=2, component=, icon=, status=0, parentId=318994824483180544, componentName=, sort=0, applicationId=null, applicationCode=)', NULL, 't', 2, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (318994978070204416, 1, '2026-04-13 12:25:29.41264', 0, NULL, '2026-04-13 12:25:29.41264', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysPermissionsCreate(title=删除, code=admin:ratelimit:config:delete, url=, type=2, component=, icon=, status=0, parentId=318994824483180544, componentName=, sort=0, applicationId=null, applicationCode=)', NULL, 't', 3, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (318995237693427712, 1, '2026-04-13 12:26:31.311373', 0, NULL, '2026-04-13 12:26:31.311373', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysPermissionsCreate(title=IP限流配置, code=admin:ratelimit:ip-config:page, url=/ratelimit/ip-config, type=1, component=views/ratelimit/ip-config/index, icon=, status=1, parentId=318993678729351168, componentName=RatelimitIpConfig, sort=0, applicationId=null, applicationCode=)', NULL, 't', 3, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (318995263928799232, 1, '2026-04-13 12:26:37.566951', 0, NULL, '2026-04-13 12:26:37.566951', 'UPDATE', 'com.fastproject.module.system.controller.SysPermissionsController', '修改权限', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=SysPermissionsUpdate(id=318995237680844800, title=IP限流配置, code=admin:ratelimit:ip-config:page, url=/ratelimit/ip-config, type=1, component=views/ratelimit/ip-config/index, icon=, status=1, parentId=318993678729351168, componentName=RatelimitIpConfig, sort=2, applicationId=null, applicationCode=)', NULL, 't', 7, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (318995415305424896, 1, '2026-04-13 12:27:13.657297', 0, NULL, '2026-04-13 12:27:13.657297', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysPermissionsCreate(title=添加, code=admin:ratelimit:ip-config:add, url=, type=2, component=, icon=, status=0, parentId=318995237680844800, componentName=, sort=0, applicationId=null, applicationCode=)', NULL, 't', 4, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (318995415334785024, 1, '2026-04-13 12:27:13.66486', 0, NULL, '2026-04-13 12:27:13.66486', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysPermissionsCreate(title=修改, code=admin:ratelimit:ip-config:update, url=, type=2, component=, icon=, status=0, parentId=318995237680844800, componentName=, sort=0, applicationId=null, applicationCode=)', NULL, 't', 2, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (318995415368339456, 1, '2026-04-13 12:27:13.67255', 0, NULL, '2026-04-13 12:27:13.67255', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysPermissionsCreate(title=删除, code=admin:ratelimit:ip-config:delete, url=, type=2, component=, icon=, status=0, parentId=318995237680844800, componentName=, sort=0, applicationId=null, applicationCode=)', NULL, 't', 2, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (318995774488842240, 1, '2026-04-13 12:28:39.293141', 0, NULL, '2026-04-13 12:28:39.293141', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysPermissionsCreate(title=API限流配置, code=admin:ratelimit:api-config:page, url=/ratelimit/api-config, type=1, component=views/ratelimit/api-config/index, icon=, status=1, parentId=318993678729351168, componentName=RatelimitApiConfig, sort=4, applicationId=null, applicationCode=)', NULL, 't', 5, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (318995950473449472, 1, '2026-04-13 12:29:21.251687', 0, NULL, '2026-04-13 12:29:21.251687', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysPermissionsCreate(title=添加, code=admin:ratelimit:api-config:add, url=, type=2, component=, icon=, status=0, parentId=318995774476259328, componentName=, sort=0, applicationId=null, applicationCode=)', NULL, 't', 5, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (318995950519586816, 1, '2026-04-13 12:29:21.262424', 0, NULL, '2026-04-13 12:29:21.262424', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysPermissionsCreate(title=修改, code=admin:ratelimit:api-config:update, url=, type=2, component=, icon=, status=0, parentId=318995774476259328, componentName=, sort=0, applicationId=null, applicationCode=)', NULL, 't', 2, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (318995950557335552, 1, '2026-04-13 12:29:21.271653', 0, NULL, '2026-04-13 12:29:21.271653', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysPermissionsCreate(title=删除, code=admin:ratelimit:api-config:delete, url=, type=2, component=, icon=, status=0, parentId=318995774476259328, componentName=, sort=0, applicationId=null, applicationCode=)', NULL, 't', 2, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (318996165066625024, 1, '2026-04-13 12:30:12.414873', 0, NULL, '2026-04-13 12:30:12.414873', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysPermissionsCreate(title=限流记录, code=admin:ratelimit:record:page, url=/ratelimit/record, type=1, component=views/ratelimit/record/index, icon=, status=1, parentId=318993678729351168, componentName=RatelimitRecord, sort=5, applicationId=null, applicationCode=)', NULL, 't', 4, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (318996311238119424, 1, '2026-04-13 12:30:47.264059', 0, NULL, '2026-04-13 12:30:47.264059', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysPermissionsCreate(title=添加, code=admin:ratelimit:record:add, url=, type=2, component=, icon=, status=0, parentId=318996165058236416, componentName=, sort=0, applicationId=null, applicationCode=)', NULL, 't', 5, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (318996311267479552, 1, '2026-04-13 12:30:47.271654', 0, NULL, '2026-04-13 12:30:47.271654', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysPermissionsCreate(title=修改, code=admin:ratelimit:record:update, url=, type=2, component=, icon=, status=0, parentId=318996165058236416, componentName=, sort=0, applicationId=null, applicationCode=)', NULL, 't', 2, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (318996311317811200, 1, '2026-04-13 12:30:47.28351', 0, NULL, '2026-04-13 12:30:47.28351', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysPermissionsCreate(title=删除, code=admin:ratelimit:record:delete, url=, type=2, component=, icon=, status=0, parentId=318996165058236416, componentName=, sort=0, applicationId=null, applicationCode=)', NULL, 't', 2, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (318996388925018112, 1, '2026-04-13 12:31:05.786693', 0, NULL, '2026-04-13 12:31:05.786693', 'UPDATE', 'com.fastproject.module.system.controller.SysRoleController', '修改角色', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=SysRoleUpdate(id=311703722914877440, title=超级管理员, code=admin, status=1, applicationId=null, applicationCode=, permissionIds=[313512003115290624, 313549686420148224, 311762357514801152, 311760876300537856, 313550330241617920, 313511610658459648, 314602359294660608, 312424744588808192, 313512229460905984, 314530113607831552, 312425344407834624, 313550330359058432, 316775517045002240, 314602586261032960, 312424193046220800, 313511610620710912, 314513638113284096, 313512229410574336, 314529906530848... [截断]', NULL, 't', 85, 'BUSINESS', '/sys/role');
INSERT INTO "public"."sys_operation_log" VALUES (319010515735154688, 1, '2026-04-13 13:27:13.880873', 0, NULL, '2026-04-13 13:27:13.880873', 'CREATE', 'com.fastproject.module.ratelimit.controller.GlobalRateLimitConfigController', '添加全局限流配置', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=GlobalRateLimitConfigCreate(maxRequests=1000, timeWindow=1, burstCapacity=1500, enabled=true)', NULL, 't', 26, 'BUSINESS', '/ratelimit/global-config');
INSERT INTO "public"."sys_operation_log" VALUES (319010562921074688, 1, '2026-04-13 13:27:25.130136', 0, NULL, '2026-04-13 13:27:25.130136', 'UPDATE', 'com.fastproject.module.ratelimit.controller.GlobalRateLimitConfigController', '修改全局限流配置', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=GlobalRateLimitConfigUpdate(id=319010515709988864, maxRequests=1000, timeWindow=1, burstCapacity=1500, enabled=false)', NULL, 't', 8, 'BUSINESS', '/ratelimit/global-config');
INSERT INTO "public"."sys_operation_log" VALUES (319010566003888128, 1, '2026-04-13 13:27:25.865721', 0, NULL, '2026-04-13 13:27:25.865721', 'UPDATE', 'com.fastproject.module.ratelimit.controller.GlobalRateLimitConfigController', '修改全局限流配置', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=GlobalRateLimitConfigUpdate(id=319010515709988864, maxRequests=1000, timeWindow=1, burstCapacity=1500, enabled=true)', NULL, 't', 7, 'BUSINESS', '/ratelimit/global-config');
INSERT INTO "public"."sys_operation_log" VALUES (319182046956228608, NULL, '2026-04-14 00:48:50.110628', 0, NULL, '2026-04-14 00:48:50.110628', 'LOGIN', 'com.fastproject.module.system.controller.AuthController', '用户登录', NULL, 'POST', '0:0:0:0:0:0:0:1', 'login', 'arg0=LoginDto(username=admin, password=123456, captchaKey=a939538f71d744f19ab39e8d6e714866, captcha=uujz)', NULL, 't', 69, 'LOGIN', '/auth/login');
INSERT INTO "public"."sys_operation_log" VALUES (319311534511755264, NULL, '2026-04-14 09:23:22.349976', 0, NULL, '2026-04-14 09:23:22.349976', 'LOGIN', 'com.fastproject.module.system.controller.AuthController', '用户登录', NULL, 'POST', '0:0:0:0:0:0:0:1', 'login', 'arg0=LoginDto(username=admin, password=123456, captchaKey=2ed3f2e2a8b643c8be79f2818d813dd6, captcha=dunj)', NULL, 't', 164, 'LOGIN', '/auth/login');
INSERT INTO "public"."sys_operation_log" VALUES (319646935701131264, NULL, '2026-04-15 07:36:08.224995', 0, NULL, '2026-04-15 07:36:08.224995', 'LOGIN', 'com.fastproject.module.system.controller.AuthController', '用户登录', NULL, 'POST', '0:0:0:0:0:0:0:1', 'login', 'arg0=LoginDto(username=admin, password=123456, captchaKey=56943ea0b9114af2bd5c516c6beb9347, captcha=pwew)', NULL, 't', 165, 'LOGIN', '/auth/login');
INSERT INTO "public"."sys_operation_log" VALUES (319661585503227904, 1, '2026-04-15 08:34:21.009211', 0, NULL, '2026-04-15 08:34:21.009211', 'LOGOUT', 'com.fastproject.module.system.controller.AuthController', '用户退出', NULL, 'POST', '0:0:0:0:0:0:0:1', 'logout', '{}', NULL, 't', 5, 'LOGIN', '/auth/logout');
INSERT INTO "public"."sys_operation_log" VALUES (319661726314401792, NULL, '2026-04-15 08:34:54.581871', 0, NULL, '2026-04-15 08:34:54.581871', 'LOGIN', 'com.fastproject.module.system.controller.AuthController', '用户登录', NULL, 'POST', '0:0:0:0:0:0:0:1', 'login', 'arg0=LoginDto(username=admin, password=123456, captchaKey=9511c21a5ef74a40867434d45225761a, captcha=TEY2)', NULL, 't', 101, 'LOGIN', '/auth/login');
INSERT INTO "public"."sys_operation_log" VALUES (319662048004935680, NULL, '2026-04-15 08:36:11.27899', 0, NULL, '2026-04-15 08:36:11.27899', 'LOGIN', 'com.fastproject.module.system.controller.AuthController', '用户登录', NULL, 'POST', '0:0:0:0:0:0:0:1', 'login', 'arg0=LoginDto(username=admin, password=123456, captchaKey=43a93fa74755434e909d6c78fe219de1, captcha=sutp)', NULL, 't', 98, 'LOGIN', '/auth/login');
INSERT INTO "public"."sys_operation_log" VALUES (319666942623485952, 1, '2026-04-15 08:55:38.246', 0, NULL, '2026-04-15 08:55:38.246', 'LOGOUT', 'com.fastproject.module.system.controller.AuthController', '用户退出', NULL, 'POST', '0:0:0:0:0:0:0:1', 'logout', '{}', NULL, 't', 7, 'LOGIN', '/auth/logout');
INSERT INTO "public"."sys_operation_log" VALUES (319667203672772608, NULL, '2026-04-15 08:56:40.485402', 0, NULL, '2026-04-15 08:56:40.485402', 'LOGIN', 'com.fastproject.module.system.controller.AuthController', '用户登录', NULL, 'POST', '0:0:0:0:0:0:0:1', 'login', 'arg0=LoginDto(username=admin, password=123456, captchaKey=0502e83de52e46599802554abb0440aa, captcha=Pqm4)', NULL, 't', 95, 'LOGIN', '/auth/login');
INSERT INTO "public"."sys_operation_log" VALUES (319673003380183040, 1, '2026-04-15 09:19:43.243928', 0, NULL, '2026-04-15 09:19:43.243928', 'UPDATE', 'com.fastproject.module.ratelimit.controller.GlobalRateLimitConfigController', '修改全局限流配置', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=GlobalRateLimitConfigUpdate(id=319010515709988864, maxRequests=1200, timeWindow=1, burstCapacity=1500, enabled=true)', NULL, 't', 30, 'BUSINESS', '/ratelimit/global-config');
INSERT INTO "public"."sys_operation_log" VALUES (319673973606584320, 1, '2026-04-15 09:23:34.563523', 0, NULL, '2026-04-15 09:23:34.563523', 'UPDATE', 'com.fastproject.module.ratelimit.controller.GlobalRateLimitConfigController', '修改全局限流配置', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=GlobalRateLimitConfigUpdate(id=319010515709988864, maxRequests=1000, timeWindow=1, burstCapacity=1500, enabled=true)', NULL, 't', 8, 'BUSINESS', '/ratelimit/global-config');
INSERT INTO "public"."sys_operation_log" VALUES (319674206788915200, 1, '2026-04-15 09:24:30.158402', 0, NULL, '2026-04-15 09:24:30.158402', 'UPDATE', 'com.fastproject.module.ratelimit.controller.GlobalRateLimitConfigController', '修改全局限流配置', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=GlobalRateLimitConfigUpdate(id=319010515709988864, maxRequests=1200, timeWindow=1, burstCapacity=1500, enabled=true)', NULL, 't', 5, 'BUSINESS', '/ratelimit/global-config');
INSERT INTO "public"."sys_operation_log" VALUES (319676994071695360, 1, '2026-04-15 09:35:34.698827', 0, NULL, '2026-04-15 09:35:34.698827', 'UPDATE', 'com.fastproject.module.ratelimit.controller.GlobalRateLimitConfigController', '修改全局限流配置', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=GlobalRateLimitConfigUpdate(id=319010515709988864, maxRequests=1200, timeWindow=1, burstCapacity=2000, enabled=true)', NULL, 't', 7, 'BUSINESS', '/ratelimit/global-config');
INSERT INTO "public"."sys_operation_log" VALUES (319677218819280896, 1, '2026-04-15 09:36:28.282753', 0, NULL, '2026-04-15 09:36:28.282753', 'UPDATE', 'com.fastproject.module.ratelimit.controller.GlobalRateLimitConfigController', '修改全局限流配置', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=GlobalRateLimitConfigUpdate(id=319010515709988864, maxRequests=1000, timeWindow=1, burstCapacity=2000, enabled=true)', NULL, 't', 8, 'BUSINESS', '/ratelimit/global-config');
INSERT INTO "public"."sys_operation_log" VALUES (319936131665891328, NULL, '2026-04-16 02:45:17.915388', 0, NULL, '2026-04-16 02:45:17.915388', 'LOGIN', 'com.fastproject.module.system.controller.AuthController', '用户登录', NULL, 'POST', '0:0:0:0:0:0:0:1', 'login', 'arg0=LoginDto(username=admin, password=123456, captchaKey=0a28eb6712ee44548264a1cada6ce9a9, captcha=3ftv)', NULL, 't', 115, 'LOGIN', '/auth/login');
INSERT INTO "public"."sys_operation_log" VALUES (319952489149501440, NULL, '2026-04-16 03:50:17.843176', 0, NULL, '2026-04-16 03:50:17.843176', 'LOGIN', 'com.fastproject.module.system.controller.AuthController', '用户登录', NULL, 'POST', '0:0:0:0:0:0:0:1', 'login', 'arg0=LoginDto(username=admin, password=123456, captchaKey=79a889c8cb214a8a8c8a4e5e34b0cc44, captcha=Ecas)', NULL, 't', 57, 'LOGIN', '/auth/login');
INSERT INTO "public"."sys_operation_log" VALUES (319985518299451392, NULL, '2026-04-16 06:01:32.606476', 0, NULL, '2026-04-16 06:01:32.606476', 'LOGIN', 'com.fastproject.module.system.controller.AuthController', '用户登录', NULL, 'POST', '0:0:0:0:0:0:0:1', 'login', 'arg0=LoginDto(username=admin, password=123456, captchaKey=b49f4989b45e4179b212ec46331bb935, captcha=txyp)', NULL, 't', 100, 'LOGIN', '/auth/login');
INSERT INTO "public"."sys_operation_log" VALUES (319986463058038784, 1, '2026-04-16 06:05:17.854122', 0, NULL, '2026-04-16 06:05:17.854122', 'UPDATE', 'com.fastproject.module.ratelimit.controller.GlobalRateLimitConfigController', '修改全局限流配置', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=GlobalRateLimitConfigUpdate(id=319010515709988864, appCode=admin, maxRequests=1000, timeWindow=1, burstCapacity=2000, enabled=true)', NULL, 't', 26, 'BUSINESS', '/ratelimit/global-config');
INSERT INTO "public"."sys_operation_log" VALUES (319987359527604224, 1, '2026-04-16 06:08:51.589004', 0, NULL, '2026-04-16 06:08:51.589004', 'UPDATE', 'com.fastproject.module.ratelimit.controller.GlobalRateLimitConfigController', '修改全局限流配置', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=GlobalRateLimitConfigUpdate(id=319010515709988864, appCode=admin, maxRequests=1000, timeWindow=1, burstCapacity=2000, enabled=true)', NULL, 't', 5, 'BUSINESS', '/ratelimit/global-config');
INSERT INTO "public"."sys_operation_log" VALUES (320022298117672960, 1, '2026-04-16 08:27:41.598921', 0, NULL, '2026-04-16 08:27:41.598921', 'CREATE', 'com.fastproject.module.ratelimit.controller.IpRateLimitConfigController', '添加IP限流配置', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=IpRateLimitConfigCreate(appCode=admin, ipAddress=, ipType=SINGLE, maxRequests=60, timeWindow=1, burstCapacity=100, limitType=LIMIT, enabled=true, expireTime=null)', NULL, 't', 25, 'BUSINESS', '/ratelimit/ip-config');
INSERT INTO "public"."sys_operation_log" VALUES (320024507983204352, 1, '2026-04-16 08:36:28.47149', 0, NULL, '2026-04-16 08:36:28.47149', 'UPDATE', 'com.fastproject.module.ratelimit.controller.IpRateLimitConfigController', '修改IP限流配置', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=IpRateLimitConfigUpdate(id=320022298037981184, appCode=admin, ipAddress=, ipType=ALL, maxRequests=60, timeWindow=1, burstCapacity=100, limitType=LIMIT, enabled=true)', NULL, 't', 28, 'BUSINESS', '/ratelimit/ip-config');
INSERT INTO "public"."sys_operation_log" VALUES (320035428046606336, 1, '2026-04-16 09:19:52.017585', 0, NULL, '2026-04-16 09:19:52.017585', 'UPDATE', 'com.fastproject.module.ratelimit.controller.IpRateLimitConfigController', '修改IP限流配置', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=IpRateLimitConfigUpdate(id=320022298037981184, appCode=admin, ipAddress=, ipType=ALL, maxRequests=60, timeWindow=1, burstCapacity=100, enabled=true)', NULL, 't', 11, 'BUSINESS', '/ratelimit/ip-config');
INSERT INTO "public"."sys_operation_log" VALUES (320039246431916032, 1, '2026-04-16 09:35:02.39172', 0, NULL, '2026-04-16 09:35:02.39172', 'UPDATE', 'com.fastproject.module.ratelimit.controller.IpRateLimitConfigController', '修改IP限流配置', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=IpRateLimitConfigUpdate(id=320022298037981184, appCode=admin, ipAddress=, ipType=ALL, maxRequests=1, timeWindow=1, burstCapacity=0, enabled=true)', NULL, 't', 34, 'BUSINESS', '/ratelimit/ip-config');
INSERT INTO "public"."sys_operation_log" VALUES (320040176871149568, 1, '2026-04-16 09:38:44.225885', 0, NULL, '2026-04-16 09:38:44.225885', 'UPDATE', 'com.fastproject.module.ratelimit.controller.IpRateLimitConfigController', '修改IP限流配置', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=IpRateLimitConfigUpdate(id=320022298037981184, appCode=admin, ipAddress=, ipType=ALL, maxRequests=5, timeWindow=1, burstCapacity=0, enabled=true)', NULL, 't', 27, 'BUSINESS', '/ratelimit/ip-config');
INSERT INTO "public"."sys_operation_log" VALUES (320040406685454336, 1, '2026-04-16 09:39:39.017974', 0, NULL, '2026-04-16 09:39:39.017974', 'UPDATE', 'com.fastproject.module.ratelimit.controller.IpRateLimitConfigController', '修改IP限流配置', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=IpRateLimitConfigUpdate(id=320022298037981184, appCode=admin, ipAddress=, ipType=ALL, maxRequests=5, timeWindow=1, burstCapacity=10, enabled=true)', NULL, 't', 26, 'BUSINESS', '/ratelimit/ip-config');
INSERT INTO "public"."sys_operation_log" VALUES (320041951778967552, 1, '2026-04-16 09:45:47.396808', 0, NULL, '2026-04-16 09:45:47.396808', 'UPDATE', 'com.fastproject.module.ratelimit.controller.IpRateLimitConfigController', '修改IP限流配置', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=IpRateLimitConfigUpdate(id=320022298037981184, appCode=admin, ipAddress=, ipType=ALL, maxRequests=2, timeWindow=1, burstCapacity=3, enabled=true)', NULL, 't', 30, 'BUSINESS', '/ratelimit/ip-config');
INSERT INTO "public"."sys_operation_log" VALUES (320042493100036096, 1, '2026-04-16 09:47:56.457886', 0, NULL, '2026-04-16 09:47:56.457886', 'UPDATE', 'com.fastproject.module.ratelimit.controller.IpRateLimitConfigController', '修改IP限流配置', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=IpRateLimitConfigUpdate(id=320022298037981184, appCode=admin, ipAddress=, ipType=ALL, maxRequests=60, timeWindow=1, burstCapacity=100, enabled=true)', NULL, 't', 8, 'BUSINESS', '/ratelimit/ip-config');
INSERT INTO "public"."sys_operation_log" VALUES (320722734780911616, NULL, '2026-04-18 06:50:58.7128', 0, NULL, '2026-04-18 06:50:58.7128', 'LOGIN', 'com.fastproject.module.system.controller.AuthController', '用户登录', NULL, 'POST', '0:0:0:0:0:0:0:1', 'login', 'arg0=LoginDto(username=admin, password=123456, captchaKey=9a6c9fa218e948639f3e6017743a30bf, captcha=wnq6)', NULL, 't', 91, 'LOGIN', '/auth/login');
INSERT INTO "public"."sys_operation_log" VALUES (320723729111977984, 1, '2026-04-18 06:54:55.779571', 0, NULL, '2026-04-18 06:54:55.779571', 'DELETE', 'com.fastproject.module.system.controller.SysPermissionsController', '删除权限', NULL, 'DELETE', '0:0:0:0:0:0:0:1', 'delete', 'arg0=318994978028261376', NULL, 't', 36, 'BUSINESS', '/sys/permissions/318994978028261376');
INSERT INTO "public"."sys_operation_log" VALUES (320723739140558848, 1, '2026-04-18 06:54:58.170428', 0, NULL, '2026-04-18 06:54:58.170428', 'DELETE', 'com.fastproject.module.system.controller.SysPermissionsController', '删除权限', NULL, 'DELETE', '0:0:0:0:0:0:0:1', 'delete', 'arg0=318994977986318336', NULL, 't', 6, 'BUSINESS', '/sys/permissions/318994977986318336');
INSERT INTO "public"."sys_operation_log" VALUES (320723750217715712, 1, '2026-04-18 06:55:00.81141', 0, NULL, '2026-04-18 06:55:00.81141', 'DELETE', 'com.fastproject.module.system.controller.SysPermissionsController', '删除权限', NULL, 'DELETE', '0:0:0:0:0:0:0:1', 'delete', 'arg0=318994978057621504', NULL, 't', 5, 'BUSINESS', '/sys/permissions/318994978057621504');
INSERT INTO "public"."sys_operation_log" VALUES (320723760959328256, 1, '2026-04-18 06:55:03.372575', 0, NULL, '2026-04-18 06:55:03.372575', 'DELETE', 'com.fastproject.module.system.controller.SysPermissionsController', '删除权限', NULL, 'DELETE', '0:0:0:0:0:0:0:1', 'delete', 'arg0=318994824483180544', NULL, 't', 5, 'BUSINESS', '/sys/permissions/318994824483180544');
INSERT INTO "public"."sys_operation_log" VALUES (320726117591945216, 1, '2026-04-18 07:04:25.237527', 0, NULL, '2026-04-18 07:04:25.237527', 'CREATE', 'com.fastproject.module.ratelimit.controller.ApiRateLimitConfigController', '添加API限流配置', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=ApiRateLimitConfigCreate(appCode=admin, apiPath=/auth/captcha, httpMethod=GET, maxRequests=2, timeWindow=5, limitDimension=IP, enabled=true)', NULL, 't', 25, 'BUSINESS', '/ratelimit/api-config');
INSERT INTO "public"."sys_operation_log" VALUES (320728185618698240, NULL, '2026-04-18 07:12:38.293967', 0, NULL, '2026-04-18 07:12:38.293967', 'LOGIN', 'com.fastproject.module.system.controller.AuthController', '用户登录', NULL, 'POST', '0:0:0:0:0:0:0:1', 'login', 'arg0=LoginDto(username=admin, password=123456, captchaKey=a5d5ad56f0b64db2b1e4b1dbb4975c36, captcha=GZFK)', NULL, 't', 68, 'LOGIN', '/auth/login');
INSERT INTO "public"."sys_operation_log" VALUES (320772480157487104, NULL, '2026-04-18 10:08:38.934195', 0, NULL, '2026-04-18 10:08:38.934195', 'LOGIN', 'com.fastproject.module.system.controller.AuthController', '用户登录', NULL, 'POST', '0:0:0:0:0:0:0:1', 'login', 'arg0=LoginDto(username=admin, password=123456, captchaKey=d54db7974a8c470aa36907a54e735678, captcha=GDSP)', NULL, 't', 59, 'LOGIN', '/auth/login');
INSERT INTO "public"."sys_operation_log" VALUES (320780886020853760, 1, '2026-04-18 10:42:03.048295', 0, NULL, '2026-04-18 10:42:03.048295', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysPermissionsCreate(title=慢sql日志, code=monitor:slow-query:list, url=/monitor/slow-query, type=1, component=views/monitor/slow-query/index, icon=ConsoleSqlOutlined, status=1, parentId=316477320535871488, componentName=MonitorSlowQuery, sort=0, applicationId=null, applicationCode=)', NULL, 't', 22, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (320780969323925504, 1, '2026-04-18 10:42:22.909231', 0, NULL, '2026-04-18 10:42:22.909231', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysPermissionsCreate(title=删除, code=monitor:slow-query:delete, url=, type=2, component=, icon=, status=0, parentId=320780885941161984, componentName=, sort=0, applicationId=null, applicationCode=)', NULL, 't', 4, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (320781017470341120, 1, '2026-04-18 10:42:34.388665', 0, NULL, '2026-04-18 10:42:34.388665', 'UPDATE', 'com.fastproject.module.system.controller.SysPermissionsController', '修改权限', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=SysPermissionsUpdate(id=320780885941161984, title=慢sql日志, code=monitor:slow-query:list, url=/monitor/slow-query, type=1, component=views/monitor/slow-query/index, icon=ConsoleSqlOutlined, status=1, parentId=316477320535871488, componentName=MonitorSlowQuery, sort=4, applicationId=null, applicationCode=)', NULL, 't', 12, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (320781102677626880, 1, '2026-04-18 10:42:54.703528', 0, NULL, '2026-04-18 10:42:54.703528', 'UPDATE', 'com.fastproject.module.system.controller.SysRoleController', '修改角色', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=SysRoleUpdate(id=311703722914877440, title=超级管理员, code=admin, status=1, applicationId=null, applicationCode=, permissionIds=[313512003115290624, 318994357510344704, 318996311263285248, 311762357514801152, 313549686420148224, 311760876300537856, 313550330241617920, 313511610658459648, 314602359294660608, 312424744588808192, 313512229460905984, 314530113607831552, 312425344407834624, 318995237680844800, 313550330359058432, 316775517045002240, 318995774476259328, 314602586261032960, 312424193046220... [截断]', NULL, 't', 84, 'BUSINESS', '/sys/role');
INSERT INTO "public"."sys_operation_log" VALUES (320781141269417984, 1, '2026-04-18 10:43:03.904291', 0, NULL, '2026-04-18 10:43:03.904291', 'UPDATE', 'com.fastproject.module.system.controller.SysRoleController', '修改角色', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=SysRoleUpdate(id=311703722914877440, title=超级管理员, code=admin, status=1, applicationId=null, applicationCode=, permissionIds=[313512003115290624, 318994357510344704, 318996311263285248, 311762357514801152, 313549686420148224, 311760876300537856, 313550330241617920, 313511610658459648, 314602359294660608, 312424744588808192, 313512229460905984, 314530113607831552, 312425344407834624, 318995237680844800, 313550330359058432, 316775517045002240, 318995774476259328, 314602586261032960, 312424193046220... [截断]', NULL, 't', 62, 'BUSINESS', '/sys/role');
INSERT INTO "public"."sys_operation_log" VALUES (320826694292344832, NULL, '2026-04-18 13:44:04.591395', 0, NULL, '2026-04-18 13:44:04.591395', 'LOGIN', 'com.fastproject.module.system.controller.AuthController', '用户登录', NULL, 'POST', '0:0:0:0:0:0:0:1', 'login', 'arg0=LoginDto(username=admin, password=123456, captchaKey=4c0d1e64eafe46d481dfb651614cefc0, captcha=EY4h)', NULL, 't', 89, 'LOGIN', '/auth/login');
INSERT INTO "public"."sys_operation_log" VALUES (320827672425009152, 1, '2026-04-18 13:47:57.796976', 0, NULL, '2026-04-18 13:47:57.796976', 'OTHER', 'com.fastproject.module.system.controller.SlowQueryLogController', '删除慢查询日志', NULL, 'DELETE', '0:0:0:0:0:0:0:1', 'delete', 'arg0=[320826872533487616]', NULL, 't', 19, 'BUSINESS', '/api/system/slow-query');
INSERT INTO "public"."sys_operation_log" VALUES (320827681648283648, 1, '2026-04-18 13:47:59.995029', 0, NULL, '2026-04-18 13:47:59.995029', 'OTHER', 'com.fastproject.module.system.controller.SlowQueryLogController', '删除慢查询日志', NULL, 'DELETE', '0:0:0:0:0:0:0:1', 'delete', 'arg0=[320801431957409792]', NULL, 't', 5, 'BUSINESS', '/api/system/slow-query');
INSERT INTO "public"."sys_operation_log" VALUES (320827689441300480, 1, '2026-04-18 13:48:01.853302', 0, NULL, '2026-04-18 13:48:01.853302', 'OTHER', 'com.fastproject.module.system.controller.SlowQueryLogController', '删除慢查询日志', NULL, 'DELETE', '0:0:0:0:0:0:0:1', 'delete', 'arg0=[320801414689460224]', NULL, 't', 7, 'BUSINESS', '/api/system/slow-query');
INSERT INTO "public"."sys_operation_log" VALUES (320827698840735744, 1, '2026-04-18 13:48:04.094103', 0, NULL, '2026-04-18 13:48:04.094103', 'OTHER', 'com.fastproject.module.system.controller.SlowQueryLogController', '删除慢查询日志', NULL, 'DELETE', '0:0:0:0:0:0:0:1', 'delete', 'arg0=[320796271453540352]', NULL, 't', 6, 'BUSINESS', '/api/system/slow-query');
INSERT INTO "public"."sys_operation_log" VALUES (320972376378576896, NULL, '2026-04-18 23:22:57.907485', 0, NULL, '2026-04-18 23:22:57.907485', 'LOGIN', 'com.fastproject.module.system.controller.AuthController', '用户登录', NULL, 'POST', '0:0:0:0:0:0:0:1', 'login', 'arg0=LoginDto(username=admin, password=123456, captchaKey=da9885f5fbda44dfa8b953f79ab73fa6, captcha=h59e)', NULL, 't', 89, 'LOGIN', '/auth/login');
INSERT INTO "public"."sys_operation_log" VALUES (320987596207558656, 1, '2026-04-19 00:23:26.59848', 0, NULL, '2026-04-19 00:23:26.59848', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysPermissionsCreate(title=修改, code=monitor:slow-query:update, url=, type=2, component=, icon=, status=0, parentId=320780885941161984, componentName=, sort=0, applicationId=null, applicationCode=)', NULL, 't', 23, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (320987672283844608, 1, '2026-04-19 00:23:44.73539', 0, NULL, '2026-04-19 00:23:44.73539', 'UPDATE', 'com.fastproject.module.system.controller.SysRoleController', '修改角色', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=SysRoleUpdate(id=311703722914877440, title=超级管理员, code=admin, status=1, applicationId=null, applicationCode=, permissionIds=[313512003115290624, 318994357510344704, 318996311263285248, 311762357514801152, 313549686420148224, 311760876300537856, 313550330241617920, 313511610658459648, 314602359294660608, 312424744588808192, 313512229460905984, 314530113607831552, 312425344407834624, 318995237680844800, 313550330359058432, 318995774476259328, 314602586261032960, 312424193046220800, 313511610620710... [截断]', NULL, 't', 93, 'BUSINESS', '/sys/role');
INSERT INTO "public"."sys_operation_log" VALUES (320987722451914752, 1, '2026-04-19 00:23:56.696775', 0, NULL, '2026-04-19 00:23:56.696775', 'OTHER', 'com.fastproject.module.system.controller.SlowQueryLogController', '修改慢查询日志', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=SlowQueryLogVo(id=1, sqlContent=null, sqlMd5=null, executionTime=null, severity=null, module=null, triggerCount=null, status=不需要处理, remark=啊啊啊, createTime=null)', NULL, 't', 11, 'BUSINESS', '/api/system/slow-query');
INSERT INTO "public"."sys_operation_log" VALUES (321024434276274176, NULL, '2026-04-19 02:49:49.477054', 0, NULL, '2026-04-19 02:49:49.477054', 'LOGIN', 'com.fastproject.module.system.controller.AuthController', '用户登录', NULL, 'POST', '0:0:0:0:0:0:0:1', 'login', 'arg0=LoginDto(username=admin, password=123456, captchaKey=6cf31352a4ff420cb7e8efdce810604d, captcha=jkaz)', NULL, 't', 71, 'LOGIN', '/auth/login');
INSERT INTO "public"."sys_operation_log" VALUES (321043647174938624, 1, '2026-04-19 04:06:10.189566', 0, NULL, '2026-04-19 04:06:10.189566', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysPermissionsCreate(title=租户管理, code=admin:system:tenant:page, url=/system/systenant, type=1, component=views/system/systenant/index, icon=HddOutlined, status=1, parentId=311757014093139968, componentName=SystemSystenant, sort=0, applicationId=null, applicationCode=)', NULL, 't', 21, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (321043693429723136, 1, '2026-04-19 04:06:21.217638', 0, NULL, '2026-04-19 04:06:21.217638', 'UPDATE', 'com.fastproject.module.system.controller.SysPermissionsController', '修改权限', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=SysPermissionsUpdate(id=321043647103635456, title=租户管理, code=admin:system:tenant:page, url=/system/systenant, type=1, component=views/system/systenant/index, icon=HddOutlined, status=1, parentId=311757014093139968, componentName=SystemSystenant, sort=7, applicationId=null, applicationCode=)', NULL, 't', 11, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (321043873285672960, 1, '2026-04-19 04:07:04.098356', 0, NULL, '2026-04-19 04:07:04.098356', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysPermissionsCreate(title=添加, code=admin:system:tenant:add, url=, type=2, component=, icon=, status=0, parentId=321043647103635456, componentName=, sort=0, applicationId=null, applicationCode=)', NULL, 't', 3, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (321043874627850240, 1, '2026-04-19 04:07:04.418744', 0, NULL, '2026-04-19 04:07:04.418744', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysPermissionsCreate(title=修改, code=admin:system:tenant:update, url=, type=2, component=, icon=, status=0, parentId=321043647103635456, componentName=, sort=0, applicationId=null, applicationCode=)', NULL, 't', 3, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (321043875722563584, 1, '2026-04-19 04:07:04.679637', 0, NULL, '2026-04-19 04:07:04.679637', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysPermissionsCreate(title=删除, code=admin:system:tenant:delete, url=, type=2, component=, icon=, status=0, parentId=321043647103635456, componentName=, sort=0, applicationId=null, applicationCode=)', NULL, 't', 4, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (321044019775934464, 1, '2026-04-19 04:07:39.024369', 0, NULL, '2026-04-19 04:07:39.024369', 'UPDATE', 'com.fastproject.module.system.controller.SysRoleController', '修改角色', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=SysRoleUpdate(id=311703722914877440, title=超级管理员, code=admin, status=1, applicationId=null, applicationCode=, permissionIds=[318994357510344704, 318996311263285248, 314602359294660608, 320987596123672576, 320780969307148288, 314530113607831552, 318995237680844800, 316775517045002240, 318995774476259328, 314602586261032960, 314513638113284096, 318993678729351168, 314529906530848768, 318995415359950848, 314537408047747072, 318994357548093440, 314529426132045824, 316477984636801024, 318996165058236... [截断]', NULL, 't', 94, 'BUSINESS', '/sys/role');
INSERT INTO "public"."sys_operation_log" VALUES (321044251922272256, 1, '2026-04-19 04:08:34.372516', 0, NULL, '2026-04-19 04:08:34.372516', 'CREATE', 'com.fastproject.module.system.controller.SysTenantController', '添加租户', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysTenantCreate(name=总经理, contactName=张三, contactPhone=, status=0, domain=http://localhost:5173, expireTime=null, accountCount=100, remark=)', NULL, 't', 10, 'BUSINESS', '/sys/tenant');
INSERT INTO "public"."sys_operation_log" VALUES (321046767628062720, NULL, '2026-04-19 04:18:34.163492', 0, NULL, '2026-04-19 04:18:34.163492', 'LOGIN', 'com.fastproject.module.system.controller.AuthController', '用户登录', NULL, 'POST', '0:0:0:0:0:0:0:1', 'login', 'arg0=LoginDto(username=admin, password=123456, captchaKey=18a04d5324fd47d992ceda7ae8a8a25f, captcha=Q3N4)', NULL, 't', 61, 'LOGIN', '/auth/login');
INSERT INTO "public"."sys_operation_log" VALUES (321046944304730112, NULL, '2026-04-19 04:19:16.286418', 0, NULL, '2026-04-19 04:19:16.286418', 'LOGIN', 'com.fastproject.module.system.controller.AuthController', '用户登录', NULL, 'POST', '0:0:0:0:0:0:0:1', 'login', 'arg0=LoginDto(username=admin, password=123456, captchaKey=cc5f82c01e5242bd879721876b65632a, captcha=EM5Y)', NULL, 't', 88, 'LOGIN', '/auth/login');
INSERT INTO "public"."sys_operation_log" VALUES (321048549175463936, NULL, '2026-04-19 04:25:38.917163', 0, NULL, '2026-04-19 04:25:38.917163', 'LOGIN', 'com.fastproject.module.system.controller.AuthController', '用户登录', NULL, 'POST', '0:0:0:0:0:0:0:1', 'login', 'arg0=LoginDto(username=admin, password=123456, captchaKey=fd77f0fc4d4545ecad2e5ffb49f1f4d5, captcha=Ywum)', NULL, 't', 89, 'LOGIN', '/auth/login');
INSERT INTO "public"."sys_operation_log" VALUES (321049097081589760, 1, '2026-04-19 04:27:49.548976', 0, NULL, '2026-04-19 04:27:49.548976', 'UPDATE', 'com.fastproject.module.system.controller.SysTenantController', '修改租户', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=SysTenantUpdate(id=321044251905495040, name=总经理, contactName=张三, contactPhone=, status=0, domain=http://localhost:5173, expireTime=2034-04-07 04:27:46, accountCount=100, remark=)', NULL, 't', 37, 'BUSINESS', '/sys/tenant');
INSERT INTO "public"."sys_operation_log" VALUES (321049915511934976, 1, '2026-04-19 04:31:04.677991', 0, NULL, '2026-04-19 04:31:04.677991', 'UPDATE', 'com.fastproject.module.system.controller.SysTenantController', '修改租户', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=SysTenantUpdate(id=321044251905495040, name=总经理, contactName=张三, contactPhone=, status=0, domain=http://localhost:5173, expireTime=2034-04-07 04:27:46, accountCount=100, remark=)', NULL, 't', 9, 'BUSINESS', '/sys/tenant');
INSERT INTO "public"."sys_operation_log" VALUES (321050713268555776, NULL, '2026-04-19 04:34:14.877292', 0, NULL, '2026-04-19 04:34:14.877292', 'LOGIN', 'com.fastproject.module.system.controller.AuthController', '用户登录', NULL, 'POST', '0:0:0:0:0:0:0:1', 'login', 'arg0=LoginDto(username=admin, password=123456, captchaKey=f75e9bb3a1ce42128729134d3be99164, captcha=PPBE)', NULL, 't', 51, 'LOGIN', '/auth/login');
INSERT INTO "public"."sys_operation_log" VALUES (321051052126375936, NULL, '2026-04-19 04:35:35.66757', 0, NULL, '2026-04-19 04:35:35.66757', 'LOGIN', 'com.fastproject.module.system.controller.AuthController', '用户登录', NULL, 'POST', '0:0:0:0:0:0:0:1', 'login', 'arg0=LoginDto(username=admin, password=123456, captchaKey=dcc83013310f4ef9949f5290fec0274e, captcha=7pq9)', NULL, 't', 46, 'LOGIN', '/auth/login');
INSERT INTO "public"."sys_operation_log" VALUES (321051447271755776, NULL, '2026-04-19 04:37:09.877855', 0, NULL, '2026-04-19 04:37:09.877855', 'LOGIN', 'com.fastproject.module.system.controller.AuthController', '用户登录', NULL, 'POST', '0:0:0:0:0:0:0:1', 'login', 'arg0=LoginDto(username=admin, password=123456, captchaKey=7173af836e0e4920a96bc8b59d3d1b9a, captcha=9jt5)', NULL, 't', 90, 'LOGIN', '/auth/login');
INSERT INTO "public"."sys_operation_log" VALUES (321140680418267136, NULL, '2026-04-19 10:31:44.717051', 0, NULL, '2026-04-19 10:31:44.717051', 'LOGIN', 'com.fastproject.module.system.controller.AuthController', '用户登录', NULL, 'POST', '0:0:0:0:0:0:0:1', 'login', 'arg0=LoginDto(username=admin, password=123456, captchaKey=c258b188454d430f8592ad35eb80780c, captcha=E2h9)', NULL, 't', 94, 'LOGIN', '/auth/login');
INSERT INTO "public"."sys_operation_log" VALUES (321150066599530496, NULL, '2026-04-19 11:09:02.557209', 0, NULL, '2026-04-19 11:09:02.557209', 'LOGIN', 'com.fastproject.module.system.controller.AuthController', '用户登录', NULL, 'POST', '0:0:0:0:0:0:0:1', 'login', 'arg0=LoginDto(username=admin, password=123456, captchaKey=5b8a1066257e4b45b6518c5820ebb062, captcha=H5sz)', NULL, 't', 96, 'LOGIN', '/auth/login');
INSERT INTO "public"."sys_operation_log" VALUES (321151497402454016, 1, '2026-04-19 11:14:43.687311', 0, NULL, '2026-04-19 11:14:43.687311', 'UPDATE', 'com.fastproject.module.system.controller.SysTenantController', '修改租户', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=SysTenantUpdate(id=321044251905495040, name=总经理, contactName=张三, contactPhone=, adminId=311655641745854464, status=0, domain=http://localhost:5173, expireTime=2034-04-07 04:27:46, accountCount=100, remark=)', NULL, 't', 20, 'BUSINESS', '/sys/tenant');
INSERT INTO "public"."sys_operation_log" VALUES (321213645210128384, NULL, '2026-04-19 15:21:40.879837', 0, NULL, '2026-04-19 15:21:40.879837', 'LOGIN', 'com.fastproject.module.system.controller.AuthController', '用户登录', NULL, 'POST', '0:0:0:0:0:0:0:1', 'login', 'arg0=LoginDto(username=admin, password=123456, captchaKey=6e4492f519304a4eb30cecf73cb52858, captcha=v7ka)', NULL, 't', 62, 'LOGIN', '/auth/login');
INSERT INTO "public"."sys_operation_log" VALUES (321216076358750208, 1, '2026-04-19 15:31:20.510575', 0, NULL, '2026-04-19 15:31:20.510575', 'UPDATE', 'com.fastproject.module.system.controller.SysUsersController', '后台用户修改密码', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'updatePassword', 'arg0=SysUserPasswordUpdate(id=311655641745854464, newPassword=123456)', NULL, 't', 75, 'BUSINESS', '/sys/users/password');
INSERT INTO "public"."sys_operation_log" VALUES (321216127722196992, NULL, '2026-04-19 15:31:32.756619', 0, NULL, '2026-04-19 15:31:32.756619', 'LOGIN', 'com.fastproject.module.system.controller.AuthController', '用户登录', NULL, 'POST', '0:0:0:0:0:0:0:1', 'login', 'arg0=LoginDto(username=admin4, password=123456, captchaKey=65924244555843ec875d2ea7235bdd93, captcha=je84)', NULL, 't', 62, 'LOGIN', '/auth/login');
INSERT INTO "public"."sys_operation_log" VALUES (321216211377590272, 1, '2026-04-19 15:31:52.701383', 0, NULL, '2026-04-19 15:31:52.701383', 'UPDATE', 'com.fastproject.module.system.controller.SysUsersController', '后台用户修改', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=SysUserUpdate(id=311655641745854464, username=admin4, nickname=管理员4, email=null, phone=null, gender=2, status=1, departmentId=313513559294349312, postId=313512533233373184, roleIds=[312029954386825216], avatar=null)', NULL, 't', 35, 'BUSINESS', '/sys/users');
INSERT INTO "public"."sys_operation_log" VALUES (321216399538262016, 1, '2026-04-19 15:32:37.562266', 0, NULL, '2026-04-19 15:32:37.562266', 'UPDATE', 'com.fastproject.module.system.controller.SysRoleController', '修改角色', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=SysRoleUpdate(id=312029954386825216, title=普通管理员, code=admin2, status=1, applicationId=null, applicationCode=, permissionIds=[311757014093139968, 311762357514801152, 312424193062998016, 312424193046220800, 312424192979111936, 311762528248139776, 312424744588808192, 312424744626556928, 312424744555253760, 313511173993664512, 313511610557796352, 313511610620710912, 313511610658459648, 313512003115290624, 313512229410574336, 313512229460905984, 313512229490266112, 313549285599875072, 31354968636142... [截断]', NULL, 't', 43, 'BUSINESS', '/sys/role');
INSERT INTO "public"."sys_operation_log" VALUES (321217258842099712, 311655641745854464, '2026-04-19 15:36:02.43606', 0, NULL, '2026-04-19 15:36:02.43606', 'LOGOUT', 'com.fastproject.module.system.controller.AuthController', '用户退出', NULL, 'POST', '0:0:0:0:0:0:0:1', 'logout', '{}', NULL, 't', 4, 'LOGIN', '/auth/logout');
INSERT INTO "public"."sys_operation_log" VALUES (321217290739781632, NULL, '2026-04-19 15:36:10.04115', 0, NULL, '2026-04-19 15:36:10.04115', 'LOGIN', 'com.fastproject.module.system.controller.AuthController', '用户登录', NULL, 'POST', '0:0:0:0:0:0:0:1', 'login', 'arg0=LoginDto(username=admin4, password=123456, captchaKey=cccb2d0ae572453abb47c30807af0662, captcha=Kmmk)', NULL, 't', 60, 'LOGIN', '/auth/login');
INSERT INTO "public"."sys_operation_log" VALUES (321217452610555904, 1, '2026-04-19 15:36:48.634828', 0, NULL, '2026-04-19 15:36:48.634828', 'UPDATE', 'com.fastproject.module.system.controller.SysRoleController', '修改角色', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=SysRoleUpdate(id=312029954386825216, title=普通管理员, code=admin2, status=1, applicationId=null, applicationCode=, permissionIds=[313512003115290624, 313550330333892608, 311762357514801152, 312424193062998016, 313549686420148224, 311760876300537856, 313550330241617920, 313511610658459648, 311762528248139776, 312424744588808192, 313511173993664512, 313512229460905984, 321043874619461632, 313550330384224256, 313549686361427968, 313549686445314048, 312425344407834624, 313550330359058432, 32104387327309... [截断]', NULL, 't', 115, 'BUSINESS', '/sys/role');
INSERT INTO "public"."sys_operation_log" VALUES (321217503214833664, NULL, '2026-04-19 15:37:00.699187', 0, NULL, '2026-04-19 15:37:00.699187', 'LOGIN', 'com.fastproject.module.system.controller.AuthController', '用户登录', NULL, 'POST', '0:0:0:0:0:0:0:1', 'login', 'arg0=LoginDto(username=admin4, password=123456, captchaKey=198f3257f4f241c887829788c0afcd72, captcha=Tysn)', NULL, 't', 70, 'LOGIN', '/auth/login');
INSERT INTO "public"."sys_operation_log" VALUES (321218223179698176, 1, '2026-04-19 15:39:52.352457', 0, NULL, '2026-04-19 15:39:52.352457', 'CREATE', 'com.fastproject.module.system.controller.SysTenantController', '添加租户', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysTenantCreate(name=总经理啊, contactName=张三, contactPhone=, adminId=311655641745854464, status=0, domain=http://127.0.0.1:5173, expireTime=2027-04-24 15:39:02, accountCount=100, remark=)', NULL, 't', 10, 'BUSINESS', '/sys/tenant');
INSERT INTO "public"."sys_operation_log" VALUES (321218311293636608, 311655641745854464, '2026-04-19 15:40:13.360835', 0, NULL, '2026-04-19 15:40:13.360835', 'LOGOUT', 'com.fastproject.module.system.controller.AuthController', '用户退出', NULL, 'POST', '0:0:0:0:0:0:0:1', 'logout', '{}', NULL, 't', 4, 'LOGIN', '/auth/logout');
INSERT INTO "public"."sys_operation_log" VALUES (321218445477810176, NULL, '2026-04-19 15:40:45.352893', 0, NULL, '2026-04-19 15:40:45.352893', 'LOGIN', 'com.fastproject.module.system.controller.AuthController', '用户登录', NULL, 'POST', '0:0:0:0:0:0:0:1', 'login', 'arg0=LoginDto(username=admin4, password=123456, captchaKey=bd3f0b810e8d4943b7f075b5592dccdc, captcha=YMM9)', NULL, 't', 82, 'LOGIN', '/auth/login');
INSERT INTO "public"."sys_operation_log" VALUES (321218697983299584, 1, '2026-04-19 15:41:45.55464', 0, NULL, '2026-04-19 15:41:45.55464', 'UPDATE', 'com.fastproject.module.system.controller.SysTenantController', '修改租户', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=SysTenantUpdate(id=321218223158726656, name=总经理啊, contactName=张三, contactPhone=, adminId=311655641745854464, status=0, domain=http://127.0.0.1:5173, expireTime=2027-04-24 15:39:02, accountCount=100, remark=)', NULL, 't', 11, 'BUSINESS', '/sys/tenant');
INSERT INTO "public"."sys_operation_log" VALUES (321218856133726208, 311655641745854464, '2026-04-19 15:42:23.260518', 0, NULL, '2026-04-19 15:42:23.260518', 'LOGOUT', 'com.fastproject.module.system.controller.AuthController', '用户退出', NULL, 'POST', '0:0:0:0:0:0:0:1', 'logout', '{}', NULL, 't', 4, 'LOGIN', '/auth/logout');
INSERT INTO "public"."sys_operation_log" VALUES (321218901226688512, NULL, '2026-04-19 15:42:34.011768', 0, NULL, '2026-04-19 15:42:34.011768', 'LOGIN', 'com.fastproject.module.system.controller.AuthController', '用户登录', NULL, 'POST', '0:0:0:0:0:0:0:1', 'login', 'arg0=LoginDto(username=admin4, password=123456, captchaKey=bc0326a6f21546dd9f74d26fd19b2b68, captcha=Hz8q)', NULL, 't', 56, 'LOGIN', '/auth/login');
INSERT INTO "public"."sys_operation_log" VALUES (321314689151799296, NULL, '2026-04-19 22:03:11.632306', 0, NULL, '2026-04-19 22:03:11.632306', 'LOGIN', 'com.fastproject.module.system.controller.AuthController', '用户登录', NULL, 'POST', '0:0:0:0:0:0:0:1', 'login', 'arg0=LoginDto(username=admin, password=123456, captchaKey=304172043aef4cb5a1661b327892bb70, captcha=R6us)', NULL, 't', 62, 'LOGIN', '/auth/login');
INSERT INTO "public"."sys_operation_log" VALUES (321768425129119744, NULL, '2026-04-21 04:06:10.719928', 0, NULL, '2026-04-21 04:06:10.719928', 'LOGIN', 'com.fastproject.module.system.controller.AuthController', '用户登录', NULL, 'POST', '0:0:0:0:0:0:0:1', 'login', 'arg0=LoginDto(username=admin, password=123456, captchaKey=c0de437915004a43a42868ee058af074, captcha=5ejr)', NULL, 't', 85, 'LOGIN', '/auth/login');
INSERT INTO "public"."sys_operation_log" VALUES (321768608093048832, 1, '2026-04-21 04:06:54.341514', 0, NULL, '2026-04-21 04:06:54.341514', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysPermissionsCreate(title=消息管理, code=, url=, type=0, component=, icon=MessageOutlined, status=1, parentId=null, componentName=, sort=0, applicationId=null, applicationCode=)', NULL, 't', 7, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (321768659511021568, 1, '2026-04-21 04:07:06.600152', 0, NULL, '2026-04-21 04:07:06.600152', 'UPDATE', 'com.fastproject.module.system.controller.SysPermissionsController', '修改权限', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=SysPermissionsUpdate(id=321768608076271616, title=消息管理, code=, url=, type=0, component=, icon=MessageOutlined, status=1, parentId=null, componentName=, sort=5, applicationId=null, applicationCode=)', NULL, 't', 13, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (321768925027241984, 1, '2026-04-21 04:08:09.904516', 0, NULL, '2026-04-21 04:08:09.904516', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysPermissionsCreate(title=消息配置, code=admin:message:config:page, url=/message/config, type=1, component=views/message/config/index, icon=ApiOutlined, status=1, parentId=321768608076271616, componentName=MessageConfig, sort=0, applicationId=null, applicationCode=)', NULL, 't', 4, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (321769057978290176, 1, '2026-04-21 04:08:41.602408', 0, NULL, '2026-04-21 04:08:41.602408', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysPermissionsCreate(title=添加, code=admin:message:config:add, url=, type=2, component=, icon=, status=0, parentId=321768925010464768, componentName=, sort=0, applicationId=null, applicationCode=)', NULL, 't', 3, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (321769059307884544, 1, '2026-04-21 04:08:41.91947', 0, NULL, '2026-04-21 04:08:41.91947', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysPermissionsCreate(title=修改, code=admin:message:config:update, url=, type=2, component=, icon=, status=0, parentId=321768925010464768, componentName=, sort=0, applicationId=null, applicationCode=)', NULL, 't', 3, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (321769060419375104, 1, '2026-04-21 04:08:42.184962', 0, NULL, '2026-04-21 04:08:42.184962', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysPermissionsCreate(title=删除, code=admin:message:config:delete, url=, type=2, component=, icon=, status=0, parentId=321768925010464768, componentName=, sort=0, applicationId=null, applicationCode=)', NULL, 't', 3, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (321769117629681664, 1, '2026-04-21 04:08:55.824606', 0, NULL, '2026-04-21 04:08:55.824606', 'UPDATE', 'com.fastproject.module.system.controller.SysRoleController', '修改角色', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=SysRoleUpdate(id=312029954386825216, title=普通管理员, code=admin2, status=1, applicationId=null, applicationCode=, permissionIds=[313512003115290624, 318994357510344704, 318996311263285248, 311762357514801152, 313549686420148224, 311760876300537856, 313550330241617920, 313511610658459648, 314602359294660608, 320987596123672576, 312424744588808192, 313512229460905984, 321043874619461632, 320780969307148288, 314530113607831552, 312425344407834624, 318995237680844800, 313550330359058432, 31677551704500... [截断]', NULL, 't', 90, 'BUSINESS', '/sys/role');
INSERT INTO "public"."sys_operation_log" VALUES (321769311167451136, 1, '2026-04-21 04:09:41.96749', 0, NULL, '2026-04-21 04:09:41.96749', 'LOGOUT', 'com.fastproject.module.system.controller.AuthController', '用户退出', NULL, 'POST', '0:0:0:0:0:0:0:1', 'logout', '{}', NULL, 't', 4, 'LOGIN', '/auth/logout');
INSERT INTO "public"."sys_operation_log" VALUES (321769349100736512, NULL, '2026-04-21 04:09:51.011723', 0, NULL, '2026-04-21 04:09:51.011723', 'LOGIN', 'com.fastproject.module.system.controller.AuthController', '用户登录', NULL, 'POST', '0:0:0:0:0:0:0:1', 'login', 'arg0=LoginDto(username=admin, password=123456, captchaKey=43e092769e9449f4a46fa781c0392292, captcha=edbt)', NULL, 't', 61, 'LOGIN', '/auth/login');
INSERT INTO "public"."sys_operation_log" VALUES (321769826068598784, 1, '2026-04-21 04:11:44.729093', 0, NULL, '2026-04-21 04:11:44.729093', 'UPDATE', 'com.fastproject.module.system.controller.SysRoleController', '修改角色', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=SysRoleUpdate(id=311703722914877440, title=超级管理员, code=admin, status=1, applicationId=null, applicationCode=, permissionIds=[313512003115290624, 318994357510344704, 318996311263285248, 311762357514801152, 313549686420148224, 311760876300537856, 313550330241617920, 313511610658459648, 314602359294660608, 320987596123672576, 312424744588808192, 313512229460905984, 321043874619461632, 320780969307148288, 314530113607831552, 312425344407834624, 318995237680844800, 313550330359058432, 316775517045002... [截断]', NULL, 't', 68, 'BUSINESS', '/sys/role');
INSERT INTO "public"."sys_operation_log" VALUES (321770014258630656, 1, '2026-04-21 04:12:29.597655', 0, NULL, '2026-04-21 04:12:29.597655', 'CREATE', 'com.fastproject.module.message.controller.MessageConfigController', '添加消息配置', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=MessageConfigCreate(title=邮箱配置, type=email, config={

}, description=aaa, status=1)', NULL, 't', 12, 'BUSINESS', '/message/config');
INSERT INTO "public"."sys_operation_log" VALUES (321770235248119808, 1, '2026-04-21 04:13:22.285968', 0, NULL, '2026-04-21 04:13:22.285968', 'CREATE', 'com.fastproject.module.system.controller.SysDictTypeController', '添加字典类型', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysDictTypeCreate(name=消息配置类型, type=message_config_type, status=1, remark=)', NULL, 't', 6, 'BUSINESS', '/sys/dict/type');
INSERT INTO "public"."sys_operation_log" VALUES (321770341183655936, 1, '2026-04-21 04:13:47.542667', 0, NULL, '2026-04-21 04:13:47.542667', 'CREATE', 'com.fastproject.module.system.controller.SysDictDataController', '添加字典数据', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysDictDataCreate(sort=0, label=邮箱1, value=mssage_aa, dictTypeId=321770235235536896, status=1, remark=)', NULL, 't', 10, 'BUSINESS', '/sys/dict/data');
INSERT INTO "public"."sys_operation_log" VALUES (321770418795057152, 1, '2026-04-21 04:14:06.046105', 0, NULL, '2026-04-21 04:14:06.046105', 'CREATE', 'com.fastproject.module.system.controller.SysDictDataController', '添加字典数据', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysDictDataCreate(sort=0, label=短信, value=message_www, dictTypeId=321770235235536896, status=1, remark=)', NULL, 't', 5, 'BUSINESS', '/sys/dict/data');
INSERT INTO "public"."sys_operation_log" VALUES (321770452483706880, 1, '2026-04-21 04:14:14.078893', 0, NULL, '2026-04-21 04:14:14.078893', 'UPDATE', 'com.fastproject.module.system.controller.SysDictDataController', '修改字典数据', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=SysDictDataUpdate(id=321770418778279936, sort=1, label=短信, value=message_www, dictTypeId=321770235235536896, status=1, remark=)', NULL, 't', 8, 'BUSINESS', '/sys/dict/data');
INSERT INTO "public"."sys_operation_log" VALUES (321770875676397568, 1, '2026-04-21 04:15:54.975836', 0, NULL, '2026-04-21 04:15:54.975836', 'UPDATE', 'com.fastproject.module.message.controller.MessageConfigController', '修改消息配置', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=MessageConfigUpdate(id=321770014233464832, title=邮箱配置, type=mssage_aa, config={

}, description=aaa, status=1)', NULL, 't', 8, 'BUSINESS', '/message/config');
INSERT INTO "public"."sys_operation_log" VALUES (321774421620690944, 1, '2026-04-21 04:30:00.394514', 0, NULL, '2026-04-21 04:30:00.394514', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysPermissionsCreate(title=消息类型, code=admin:message:type:page, url=/message/type, type=1, component=views/message/type/index, icon=BlockOutlined, status=1, parentId=321768608076271616, componentName=MessageType, sort=1, applicationId=null, applicationCode=)', NULL, 't', 22, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (321774596279898112, 1, '2026-04-21 04:30:42.036879', 0, NULL, '2026-04-21 04:30:42.036879', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysPermissionsCreate(title=添加, code=admin:message:type:add, url=, type=2, component=, icon=, status=0, parentId=321774421545193472, componentName=, sort=0, applicationId=null, applicationCode=)', NULL, 't', 4, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (321774597647241216, 1, '2026-04-21 04:30:42.362309', 0, NULL, '2026-04-21 04:30:42.362309', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysPermissionsCreate(title=修改, code=admin:message:type:update, url=, type=2, component=, icon=, status=0, parentId=321774421545193472, componentName=, sort=0, applicationId=null, applicationCode=)', NULL, 't', 5, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (321774598750343168, 1, '2026-04-21 04:30:42.625374', 0, NULL, '2026-04-21 04:30:42.625374', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysPermissionsCreate(title=删除, code=admin:message:type:delete, url=, type=2, component=, icon=, status=0, parentId=321774421545193472, componentName=, sort=0, applicationId=null, applicationCode=)', NULL, 't', 4, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (321774645391003648, 1, '2026-04-21 04:30:53.745036', 0, NULL, '2026-04-21 04:30:53.745036', 'UPDATE', 'com.fastproject.module.system.controller.SysRoleController', '修改角色', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=SysRoleUpdate(id=311703722914877440, title=超级管理员, code=admin, status=1, applicationId=null, applicationCode=, permissionIds=[313512003115290624, 318994357510344704, 318996311263285248, 311762357514801152, 313549686420148224, 311760876300537856, 313550330241617920, 313511610658459648, 314602359294660608, 320987596123672576, 312424744588808192, 313512229460905984, 321043874619461632, 320780969307148288, 314530113607831552, 312425344407834624, 318995237680844800, 313550330359058432, 316775517045002... [截断]', NULL, 't', 91, 'BUSINESS', '/sys/role');
INSERT INTO "public"."sys_operation_log" VALUES (321774750739337216, 1, '2026-04-21 04:31:18.862712', 0, NULL, '2026-04-21 04:31:18.862712', 'CREATE', 'com.fastproject.module.message.controller.MessageTypeController', '添加消息类型', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=MessageTypeCreate(title=验证码, description=, status=1, code=code)', NULL, 't', 7, 'BUSINESS', '/message/type');
INSERT INTO "public"."sys_operation_log" VALUES (321774883518418944, 1, '2026-04-21 04:31:50.519706', 0, NULL, '2026-04-21 04:31:50.519706', 'UPDATE', 'com.fastproject.module.message.controller.MessageTypeController', '修改消息类型', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=MessageTypeUpdate(id=321774750718365696, title=验证码, description=用于注册，登录，修改密码登 发送验证码, status=1, code=code)', NULL, 't', 11, 'BUSINESS', '/message/type');
INSERT INTO "public"."sys_operation_log" VALUES (321776690818846720, 1, '2026-04-21 04:39:01.413676', 0, NULL, '2026-04-21 04:39:01.413676', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysPermissionsCreate(title=消息模版, code=admin:message:template:page, url=/message/template, type=1, component=views/message/template/index, icon=AppstoreOutlined, status=1, parentId=321768608076271616, componentName=MessageTemplate, sort=3, applicationId=null, applicationCode=)', NULL, 't', 3, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (321776882599202816, 1, '2026-04-21 04:39:47.137416', 0, NULL, '2026-04-21 04:39:47.137416', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysPermissionsCreate(title=添加, code=admin:message:template:add, url=, type=2, component=, icon=, status=0, parentId=321776690806263808, componentName=, sort=0, applicationId=null, applicationCode=)', NULL, 't', 4, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (321776883970740224, 1, '2026-04-21 04:39:47.464682', 0, NULL, '2026-04-21 04:39:47.464682', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysPermissionsCreate(title=修改, code=admin:message:template:update, url=, type=2, component=, icon=, status=0, parentId=321776690806263808, componentName=, sort=0, applicationId=null, applicationCode=)', NULL, 't', 4, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (321776885015121920, 1, '2026-04-21 04:39:47.713451', 0, NULL, '2026-04-21 04:39:47.713451', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysPermissionsCreate(title=删除, code=admin:message:template:delete, url=, type=2, component=, icon=, status=0, parentId=321776690806263808, componentName=, sort=0, applicationId=null, applicationCode=)', NULL, 't', 5, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (321776930628177920, 1, '2026-04-21 04:39:58.588863', 0, NULL, '2026-04-21 04:39:58.588863', 'UPDATE', 'com.fastproject.module.system.controller.SysRoleController', '修改角色', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=SysRoleUpdate(id=311703722914877440, title=超级管理员, code=admin, status=1, applicationId=null, applicationCode=, permissionIds=[313512003115290624, 318994357510344704, 318996311263285248, 311762357514801152, 313549686420148224, 311760876300537856, 313550330241617920, 313511610658459648, 314602359294660608, 320987596123672576, 312424744588808192, 313512229460905984, 321043874619461632, 320780969307148288, 314530113607831552, 312425344407834624, 318995237680844800, 313550330359058432, 316775517045002... [截断]', NULL, 't', 72, 'BUSINESS', '/sys/role');
INSERT INTO "public"."sys_operation_log" VALUES (321777035934568448, NULL, '2026-04-21 04:40:23.695828', 0, NULL, '2026-04-21 04:40:23.696834', 'LOGIN', 'com.fastproject.module.system.controller.AuthController', '用户登录', NULL, 'POST', '0:0:0:0:0:0:0:1', 'login', 'arg0=LoginDto(username=admin, password=123456, captchaKey=d84847effe384e0da230343b8ec22d44, captcha=HNSW)', NULL, 't', 97, 'LOGIN', '/auth/login');
INSERT INTO "public"."sys_operation_log" VALUES (321780182077804544, 1, '2026-04-21 04:52:53.794197', 0, NULL, '2026-04-21 04:52:53.794197', 'CREATE', 'com.fastproject.module.message.controller.MessageTemplateController', '添加消息模版', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=MessageTemplateCreate(code=bb, title=aa, config=, description=, status=0, content=, typeId=321774750718365696)', NULL, 't', 31, 'BUSINESS', '/message/template');
INSERT INTO "public"."sys_operation_log" VALUES (321780194392281088, 1, '2026-04-21 04:52:56.730047', 0, NULL, '2026-04-21 04:52:56.730047', 'UPDATE', 'com.fastproject.module.message.controller.MessageTemplateController', '修改消息模版', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=MessageTemplateUpdate(id=321780181989724160, code=bb, title=aa, config=, description=, status=1, content=, typeId=321774750718365696)', NULL, 't', 15, 'BUSINESS', '/message/template');
INSERT INTO "public"."sys_operation_log" VALUES (321780205419106304, 1, '2026-04-21 04:52:59.359402', 0, NULL, '2026-04-21 04:52:59.359402', 'UPDATE', 'com.fastproject.module.message.controller.MessageTemplateController', '修改消息模版', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=MessageTemplateUpdate(id=321780181989724160, code=bb, title=aa, config=, description=, status=2, content=, typeId=321774750718365696)', NULL, 't', 10, 'BUSINESS', '/message/template');
INSERT INTO "public"."sys_operation_log" VALUES (321780223404281856, 1, '2026-04-21 04:53:03.647989', 0, NULL, '2026-04-21 04:53:03.647989', 'UPDATE', 'com.fastproject.module.message.controller.MessageTemplateController', '修改消息模版', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=MessageTemplateUpdate(id=321780181989724160, code=bb, title=aa, config=, description=, status=1, content=, typeId=321774750718365696)', NULL, 't', 10, 'BUSINESS', '/message/template');
INSERT INTO "public"."sys_operation_log" VALUES (321780237249679360, 1, '2026-04-21 04:53:06.948053', 0, NULL, '2026-04-21 04:53:06.948053', 'UPDATE', 'com.fastproject.module.message.controller.MessageTemplateController', '修改消息模版', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=MessageTemplateUpdate(id=321780181989724160, code=bb, title=aa, config=, description=, status=2, content=, typeId=321774750718365696)', NULL, 't', 8, 'BUSINESS', '/message/template');
INSERT INTO "public"."sys_operation_log" VALUES (321780241305571328, 1, '2026-04-21 04:53:07.915728', 0, NULL, '2026-04-21 04:53:07.915728', 'UPDATE', 'com.fastproject.module.message.controller.MessageTemplateController', '修改消息模版', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=MessageTemplateUpdate(id=321780181989724160, code=bb, title=aa, config=, description=, status=1, content=, typeId=321774750718365696)', NULL, 't', 7, 'BUSINESS', '/message/template');
INSERT INTO "public"."sys_operation_log" VALUES (321780339242569728, 1, '2026-04-21 04:53:31.265935', 0, NULL, '2026-04-21 04:53:31.265935', 'UPDATE', 'com.fastproject.module.message.controller.MessageTemplateController', '修改消息模版', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=MessageTemplateUpdate(id=321780181989724160, code=bb, title=aa, config=, description=, status=1, content=, typeId=321774750718365696)', NULL, 't', 3, 'BUSINESS', '/message/template');
INSERT INTO "public"."sys_operation_log" VALUES (321782391364521984, 1, '2026-04-21 05:01:40.529481', 0, NULL, '2026-04-21 05:01:40.529481', 'UPDATE', 'com.fastproject.module.message.controller.MessageTemplateController', '修改消息模版', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=MessageTemplateUpdate(id=321780181989724160, code=bb, title=aa, configId=321770014233464832, description=, status=1, content=, typeId=321774750718365696)', NULL, 't', 31, 'BUSINESS', '/message/template');
INSERT INTO "public"."sys_operation_log" VALUES (321829152212783104, NULL, '2026-04-21 08:07:29.184542', 0, NULL, '2026-04-21 08:07:29.184542', 'LOGIN', 'com.fastproject.module.system.controller.AuthController', '用户登录', NULL, 'POST', '0:0:0:0:0:0:0:1', 'login', 'arg0=LoginDto(username=admin, password=123456, captchaKey=2415b650cee1442eaabbff5f46660548, captcha=wrbn)', NULL, 't', 130, 'LOGIN', '/auth/login');
INSERT INTO "public"."sys_operation_log" VALUES (321829203676893184, 1, '2026-04-21 08:07:41.454854', 0, NULL, '2026-04-21 08:07:41.454854', 'UPDATE', 'com.fastproject.module.message.controller.MessageTemplateController', '修改消息模版', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=MessageTemplateUpdate(id=321780181989724160, code=bb, title=aa, configId=321770014233464832, description=ffff, status=1, content=aaa, typeId=321774750718365696)', NULL, 't', 7, 'BUSINESS', '/message/template');
INSERT INTO "public"."sys_operation_log" VALUES (321835165867446272, 1, '2026-04-21 08:31:22.951339', 0, NULL, '2026-04-21 08:31:22.951339', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysPermissionsCreate(title=验证码, code=admin:message:verificationCode:page, url=/message/verificationcode, type=1, component=views/message/verificationcode/index, icon=CalculatorOutlined, status=1, parentId=321768608076271616, componentName=MessageVerificationcode, sort=0, applicationId=null, applicationCode=)', NULL, 't', 8, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (321835209299464192, 1, '2026-04-21 08:31:33.30677', 0, NULL, '2026-04-21 08:31:33.30677', 'UPDATE', 'com.fastproject.module.system.controller.SysPermissionsController', '修改权限', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=SysPermissionsUpdate(id=321835165842280448, title=验证码, code=admin:message:verificationCode:page, url=/message/verificationcode, type=1, component=views/message/verificationcode/index, icon=CalculatorOutlined, status=1, parentId=321768608076271616, componentName=MessageVerificationcode, sort=4, applicationId=null, applicationCode=)', NULL, 't', 8, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (321835324445691904, 1, '2026-04-21 08:32:00.759793', 0, NULL, '2026-04-21 08:32:00.759793', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysPermissionsCreate(title=删除, code=admin:message:verificationCode:delete, url=, type=2, component=, icon=, status=1, parentId=321835165842280448, componentName=MessageVerificationcode, sort=0, applicationId=null, applicationCode=)', NULL, 't', 4, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (321835385263099904, 1, '2026-04-21 08:32:15.259932', 0, NULL, '2026-04-21 08:32:15.259932', 'UPDATE', 'com.fastproject.module.system.controller.SysRoleController', '修改角色', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=SysRoleUpdate(id=311703722914877440, title=超级管理员, code=admin, status=1, applicationId=null, applicationCode=, permissionIds=[313512003115290624, 318994357510344704, 318996311263285248, 311762357514801152, 313549686420148224, 311760876300537856, 313550330241617920, 313511610658459648, 314602359294660608, 320987596123672576, 312424744588808192, 313512229460905984, 321043874619461632, 320780969307148288, 314530113607831552, 321776883953963008, 312425344407834624, 318995237680844800, 321776882582425... [截断]', NULL, 't', 107, 'BUSINESS', '/sys/role');
INSERT INTO "public"."sys_operation_log" VALUES (321835495833341952, NULL, '2026-04-21 08:32:41.621519', 0, NULL, '2026-04-21 08:32:41.621519', 'LOGIN', 'com.fastproject.module.system.controller.AuthController', '用户登录', NULL, 'POST', '0:0:0:0:0:0:0:1', 'login', 'arg0=LoginDto(username=admin, password=123456, captchaKey=1b6923d56336464393e3a4762a61494c, captcha=QPEA)', NULL, 't', 104, 'LOGIN', '/auth/login');
INSERT INTO "public"."sys_operation_log" VALUES (321837457979084800, 1, '2026-04-21 08:40:29.433029', 0, NULL, '2026-04-21 08:40:29.433029', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysPermissionsCreate(title=消息记录, code=admin:message:record:page, url=/message/record, type=1, component=views/message/record/index, icon=BarsOutlined, status=1, parentId=321768608076271616, componentName=MessageRecord, sort=0, applicationId=null, applicationCode=)', NULL, 't', 7, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (321837506335215616, 1, '2026-04-21 08:40:40.962541', 0, NULL, '2026-04-21 08:40:40.962541', 'UPDATE', 'com.fastproject.module.system.controller.SysPermissionsController', '修改权限', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=SysPermissionsUpdate(id=321837457962307584, title=消息记录, code=admin:message:record:page, url=/message/record, type=1, component=views/message/record/index, icon=BarsOutlined, status=1, parentId=321768608076271616, componentName=MessageRecord, sort=5, applicationId=null, applicationCode=)', NULL, 't', 15, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (321837761445367808, 1, '2026-04-21 08:41:41.785678', 0, NULL, '2026-04-21 08:41:41.785678', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysPermissionsCreate(title=删除, code=admin:message:record:delete, url=, type=2, component=, icon=, status=0, parentId=321837457962307584, componentName=, sort=0, applicationId=null, applicationCode=)', NULL, 't', 4, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (321837815782576128, 1, '2026-04-21 08:41:54.740589', 0, NULL, '2026-04-21 08:41:54.740589', 'UPDATE', 'com.fastproject.module.system.controller.SysRoleController', '修改角色', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=SysRoleUpdate(id=311703722914877440, title=超级管理员, code=admin, status=1, applicationId=null, applicationCode=, permissionIds=[311762357514801152, 314602359294660608, 313512229460905984, 314530113607831552, 318995237680844800, 313550330359058432, 316775517045002240, 321043873273090048, 313511610620710912, 313512229410574336, 318993678729351168, 313550330203869184, 318995415359950848, 321774421545193472, 321043647103635456, 312424744626556928, 314529426132045824, 318996165058236416, 313546319996260... [截断]', NULL, 't', 107, 'BUSINESS', '/sys/role');
INSERT INTO "public"."sys_operation_log" VALUES (321837900826284032, NULL, '2026-04-21 08:42:15.016968', 0, NULL, '2026-04-21 08:42:15.016968', 'LOGIN', 'com.fastproject.module.system.controller.AuthController', '用户登录', NULL, 'POST', '0:0:0:0:0:0:0:1', 'login', 'arg0=LoginDto(username=admin, password=123456, captchaKey=4c27d8777c7d4137aa8ec103e2faad0d, captcha=Ghh4)', NULL, 't', 107, 'LOGIN', '/auth/login');
INSERT INTO "public"."sys_operation_log" VALUES (322121049502257152, NULL, '2026-04-22 03:27:22.921387', 0, NULL, '2026-04-22 03:27:22.921387', 'LOGIN', 'com.fastproject.module.system.controller.AuthController', '用户登录', NULL, 'POST', '0:0:0:0:0:0:0:1', 'login', 'arg0=LoginDto(username=admin, password=123456, captchaKey=7ca6a3b7c2f74bf4b6a152777d4eff8e, captcha=vjvy)', NULL, 't', 72, 'LOGIN', '/auth/login');
INSERT INTO "public"."sys_operation_log" VALUES (322125954891780096, 1, '2026-04-22 03:46:52.457197', 0, NULL, '2026-04-22 03:46:52.457197', 'UPDATE', 'com.fastproject.module.message.controller.MessageTypeController', '修改消息类型', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=MessageTypeUpdate(id=321774750718365696, title=验证码, description=用于注册，登录，修改密码登 发送验证码, status=2, code=code)', NULL, 't', 27, 'BUSINESS', '/message/type');
INSERT INTO "public"."sys_operation_log" VALUES (322125958817648640, 1, '2026-04-22 03:46:53.393574', 0, NULL, '2026-04-22 03:46:53.393574', 'UPDATE', 'com.fastproject.module.message.controller.MessageTypeController', '修改消息类型', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=MessageTypeUpdate(id=321774750718365696, title=验证码, description=用于注册，登录，修改密码登 发送验证码, status=1, code=code)', NULL, 't', 7, 'BUSINESS', '/message/type');
INSERT INTO "public"."sys_operation_log" VALUES (322133236669091840, 1, '2026-04-22 04:15:48.5685', 0, NULL, '2026-04-22 04:15:48.5685', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysPermissionsCreate(title=IP和名单, code=admin:ratelimit:ip-bw-config:page, url=/ratelimit/ip-bw-config, type=1, component=views/ratelimit/ip-bw-config/index, icon=WifiOutlined, status=1, parentId=318993678729351168, componentName=RatelimitIpBwConfig, sort=5, applicationId=null, applicationCode=)', NULL, 't', 25, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (322133322853650432, 1, '2026-04-22 04:16:09.116162', 0, NULL, '2026-04-22 04:16:09.116162', 'UPDATE', 'com.fastproject.module.system.controller.SysPermissionsController', '修改权限', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=SysPermissionsUpdate(id=318995774476259328, title=API限流配置, code=admin:ratelimit:api-config:page, url=/ratelimit/api-config, type=1, component=views/ratelimit/api-config/index, icon=, status=1, parentId=318993678729351168, componentName=RatelimitApiConfig, sort=3, applicationId=null, applicationCode=)', NULL, 't', 13, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (322133351102287872, 1, '2026-04-22 04:16:15.851216', 0, NULL, '2026-04-22 04:16:15.851216', 'UPDATE', 'com.fastproject.module.system.controller.SysPermissionsController', '修改权限', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=SysPermissionsUpdate(id=318996165058236416, title=限流记录, code=admin:ratelimit:record:page, url=/ratelimit/record, type=1, component=views/ratelimit/record/index, icon=, status=1, parentId=318993678729351168, componentName=RatelimitRecord, sort=4, applicationId=null, applicationCode=)', NULL, 't', 7, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (322133527590211584, 1, '2026-04-22 04:16:57.929428', 0, NULL, '2026-04-22 04:16:57.929428', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysPermissionsCreate(title=添加, code=admin:ratelimit:ip-bw-config:add, url=, type=2, component=, icon=, status=0, parentId=322133236576817152, componentName=, sort=0, applicationId=null, applicationCode=)', NULL, 't', 4, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (322133528588455936, 1, '2026-04-22 04:16:58.167249', 0, NULL, '2026-04-22 04:16:58.167249', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysPermissionsCreate(title=修改, code=admin:ratelimit:ip-bw-config:update, url=, type=2, component=, icon=, status=0, parentId=322133236576817152, componentName=, sort=0, applicationId=null, applicationCode=)', NULL, 't', 4, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (322133528903028736, 1, '2026-04-22 04:16:58.242837', 0, NULL, '2026-04-22 04:16:58.242837', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysPermissionsCreate(title=删除, code=admin:ratelimit:ip-bw-config:delete, url=, type=2, component=, icon=, status=0, parentId=322133236576817152, componentName=, sort=0, applicationId=null, applicationCode=)', NULL, 't', 3, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (322133770289418240, 1, '2026-04-22 04:17:55.793608', 0, NULL, '2026-04-22 04:17:55.793608', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysPermissionsCreate(title=用户黑白名单, code=admin:ratelimit:user-bw-config:page, url=/ratelimit/user-bw-config, type=1, component=views/ratelimit/user-bw-config/index, icon=UserOutlined, status=0, parentId=318993678729351168, componentName=RatelimitUserBwConfig, sort=0, applicationId=null, applicationCode=)', NULL, 't', 4, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (322133812219875328, 1, '2026-04-22 04:18:05.79081', 0, NULL, '2026-04-22 04:18:05.79081', 'UPDATE', 'com.fastproject.module.system.controller.SysPermissionsController', '修改权限', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=SysPermissionsUpdate(id=322133770276835328, title=用户黑白名单, code=admin:ratelimit:user-bw-config:page, url=/ratelimit/user-bw-config, type=1, component=views/ratelimit/user-bw-config/index, icon=UserOutlined, status=0, parentId=318993678729351168, componentName=RatelimitUserBwConfig, sort=6, applicationId=null, applicationCode=)', NULL, 't', 5, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (322134032223703040, 1, '2026-04-22 04:18:58.24371', 0, NULL, '2026-04-22 04:18:58.24371', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysPermissionsCreate(title=添加, code=admin:ratelimit:user-bw-config:add, url=, type=2, component=, icon=, status=0, parentId=322133770276835328, componentName=, sort=0, applicationId=null, applicationCode=)', NULL, 't', 4, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (322134033582657536, 1, '2026-04-22 04:18:58.567637', 0, NULL, '2026-04-22 04:18:58.567637', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysPermissionsCreate(title=修改, code=admin:ratelimit:user-bw-config:update, url=, type=2, component=, icon=, status=0, parentId=322133770276835328, componentName=, sort=0, applicationId=null, applicationCode=)', NULL, 't', 4, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (322134034689953792, 1, '2026-04-22 04:18:58.831517', 0, NULL, '2026-04-22 04:18:58.831517', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysPermissionsCreate(title=删除, code=admin:ratelimit:user-bw-config:delete, url=, type=2, component=, icon=, status=0, parentId=322133770276835328, componentName=, sort=0, applicationId=null, applicationCode=)', NULL, 't', 3, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (322134128541700096, 1, '2026-04-22 04:19:21.207334', 0, NULL, '2026-04-22 04:19:21.207334', 'UPDATE', 'com.fastproject.module.system.controller.SysRoleController', '修改角色', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=SysRoleUpdate(id=311703722914877440, title=超级管理员, code=admin, status=1, applicationId=null, applicationCode=, permissionIds=[311762357514801152, 314602359294660608, 313512229460905984, 314530113607831552, 318995237680844800, 313550330359058432, 316775517045002240, 321043873273090048, 313511610620710912, 321837761428590592, 313512229410574336, 318993678729351168, 313550330203869184, 318995415359950848, 321774421545193472, 321043647103635456, 312424744626556928, 314529426132045824, 313546319996260... [截断]', NULL, 't', 117, 'BUSINESS', '/sys/role');
INSERT INTO "public"."sys_operation_log" VALUES (322134215405735936, 1, '2026-04-22 04:19:41.917046', 0, NULL, '2026-04-22 04:19:41.917046', 'UPDATE', 'com.fastproject.module.system.controller.SysPermissionsController', '修改权限', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=SysPermissionsUpdate(id=322133770276835328, title=用户黑白名单, code=admin:ratelimit:user-bw-config:page, url=/ratelimit/user-bw-config, type=1, component=views/ratelimit/user-bw-config/index, icon=UserOutlined, status=1, parentId=318993678729351168, componentName=RatelimitUserBwConfig, sort=6, applicationId=null, applicationCode=)', NULL, 't', 5, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (322234545237790720, NULL, '2026-04-22 10:58:22.41347', 0, NULL, '2026-04-22 10:58:22.41347', 'LOGIN', 'com.fastproject.module.system.controller.AuthController', '用户登录', NULL, 'POST', '0:0:0:0:0:0:0:1', 'login', 'arg0=LoginDto(username=admin, password=123456, captchaKey=52ed20d91a1741df9442b9be61a619cf, captcha=t8gg)', NULL, 't', 86, 'LOGIN', '/auth/login');
INSERT INTO "public"."sys_operation_log" VALUES (322288489821507584, NULL, '2026-04-22 14:32:43.804791', 0, NULL, '2026-04-22 14:32:43.804791', 'LOGIN', 'com.fastproject.module.system.controller.AuthController', '用户登录', NULL, 'POST', '0:0:0:0:0:0:0:1', 'login', 'arg0=LoginDto(username=admin, password=123456, captchaKey=ffaaf99852e6461ebe06bd59311527c7, captcha=SHRZ)', NULL, 't', 57, 'LOGIN', '/auth/login');
INSERT INTO "public"."sys_operation_log" VALUES (322478238679044096, NULL, '2026-04-23 03:06:43.457994', 0, NULL, '2026-04-23 03:06:43.457994', 'LOGIN', 'com.fastproject.module.system.controller.AuthController', '用户登录', NULL, 'POST', '0:0:0:0:0:0:0:1', 'login', 'arg0=LoginDto(username=admin, password=123456, captchaKey=ab0c1abf0c46406694e7c7c9e500985f, captcha=MDBV)', NULL, 't', 101, 'LOGIN', '/auth/login');
INSERT INTO "public"."sys_operation_log" VALUES (322510499206008832, NULL, '2026-04-23 05:14:54.96679', 0, NULL, '2026-04-23 05:14:54.96679', 'LOGIN', 'com.fastproject.module.system.controller.AuthController', '用户登录', NULL, 'POST', '0:0:0:0:0:0:0:1', 'login', 'arg0=LoginDto(username=admin, password=123456, captchaKey=4e4dbb7e61cf4fb9b6a4f06e793c8033, captcha=H2as)', NULL, 't', 99, 'LOGIN', '/auth/login');
INSERT INTO "public"."sys_operation_log" VALUES (322527163775586304, 1, '2026-04-23 06:21:08.1098', 0, NULL, '2026-04-23 06:21:08.1098', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysPermissionsCreate(title=消息测试, code=admin:message:test:send, url=/message/test, type=1, component=views/message/test/index, icon=BugOutlined, status=1, parentId=321768608076271616, componentName=MessageTest, sort=6, applicationId=null, applicationCode=)', NULL, 't', 22, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (322527291492143104, 1, '2026-04-23 06:21:38.55981', 0, NULL, '2026-04-23 06:21:38.55981', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysPermissionsCreate(title=验证测试验证码, code=admin:message:test:verify, url=, type=2, component=, icon=, status=0, parentId=322527163695894528, componentName=, sort=0, applicationId=null, applicationCode=)', NULL, 't', 3, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (322527344000634880, 1, '2026-04-23 06:21:51.07828', 0, NULL, '2026-04-23 06:21:51.07828', 'UPDATE', 'com.fastproject.module.system.controller.SysRoleController', '修改角色', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=SysRoleUpdate(id=311703722914877440, title=超级管理员, code=admin, status=1, applicationId=null, applicationCode=, permissionIds=[311762357514801152, 314602359294660608, 313512229460905984, 314530113607831552, 318995237680844800, 322134033570074624, 322134034677370880, 313550330359058432, 316775517045002240, 321043873273090048, 313511610620710912, 321837761428590592, 313512229410574336, 318993678729351168, 313550330203869184, 318995415359950848, 321774421545193472, 321043647103635456, 312424744626556... [截断]', NULL, 't', 116, 'BUSINESS', '/sys/role');
INSERT INTO "public"."sys_operation_log" VALUES (322527535701299200, 1, '2026-04-23 06:22:36.783104', 0, NULL, '2026-04-23 06:22:36.783104', 'UPDATE', 'com.fastproject.module.message.controller.MessageTemplateController', '修改消息模版', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=MessageTemplateUpdate(id=321780181989724160, code=bb, title=aa, configId=321770014233464832, description=ffff, status=1, content=aaa, typeId=321774750718365696)', NULL, 't', 7, 'BUSINESS', '/message/template');
INSERT INTO "public"."sys_operation_log" VALUES (322528997026172928, NULL, '2026-04-23 06:28:25.190958', 0, NULL, '2026-04-23 06:28:25.190958', 'LOGIN', 'com.fastproject.module.system.controller.AuthController', '用户登录', NULL, 'POST', '0:0:0:0:0:0:0:1', 'login', 'arg0=LoginDto(username=admin, password=123456, captchaKey=aa4b2c240d9548d1ab460d1b9f8c781b, captcha=9jq5)', NULL, 't', 77, 'LOGIN', '/auth/login');
INSERT INTO "public"."sys_operation_log" VALUES (322529372823228416, 1, '2026-04-23 06:29:54.787764', 0, NULL, '2026-04-23 06:29:54.787764', 'UPDATE', 'com.fastproject.module.system.controller.SysDictDataController', '修改字典数据', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=SysDictDataUpdate(id=321770341162684416, sort=0, label=邮箱1, value=messageSendMailService, dictTypeId=321770235235536896, status=1, remark=)', NULL, 't', 20, 'BUSINESS', '/sys/dict/data');
INSERT INTO "public"."sys_operation_log" VALUES (322529443992178688, 1, '2026-04-23 06:30:11.755405', 0, NULL, '2026-04-23 06:30:11.755405', 'UPDATE', 'com.fastproject.module.message.controller.MessageConfigController', '修改消息配置', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=MessageConfigUpdate(id=321770014233464832, title=邮箱配置, type=messageSendMailService, config={

}, description=aaa, status=1)', NULL, 't', 9, 'BUSINESS', '/message/config');
INSERT INTO "public"."sys_operation_log" VALUES (322529532756234240, 1, '2026-04-23 06:30:32.918745', 0, NULL, '2026-04-23 06:30:32.918745', 'OTHER', 'com.fastproject.module.message.controller.MessageTestController', '发送测试消息', NULL, 'POST', '0:0:0:0:0:0:0:1', 'send', 'arg0=MessageTestSend(templateCode=bb, receiver=123456@qq.com, params={})', NULL, 't', 11, 'BUSINESS', '/message/test/send');
INSERT INTO "public"."sys_operation_log" VALUES (322529854471933952, 1, '2026-04-23 06:31:49.621111', 0, NULL, '2026-04-23 06:31:49.621111', 'UPDATE', 'com.fastproject.module.message.controller.MessageTypeController', '修改消息类型', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=MessageTypeUpdate(id=321774750718365696, title=验证码, description=用于注册，登录，修改密码登 发送验证码, status=1, code=CODE)', NULL, 't', 7, 'BUSINESS', '/message/type');
INSERT INTO "public"."sys_operation_log" VALUES (322530695127896064, 1, '2026-04-23 06:35:10.049913', 0, NULL, '2026-04-23 06:35:10.049913', 'UPDATE', 'com.fastproject.module.message.controller.MessageTemplateController', '修改消息模版', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=MessageTemplateUpdate(id=321780181989724160, code=bb, title=注册验证码, configId=321770014233464832, description={
    "appName":"注册",
    "code":"验证码",
     "expireTime":"10"
}, status=1, content=【#{appName}】您的注册验证码是：#{code}，有效期#{expireTime}分钟，请勿泄露给他人。, typeId=321774750718365696)', NULL, 't', 7, 'BUSINESS', '/message/template');
INSERT INTO "public"."sys_operation_log" VALUES (322534559545692160, NULL, '2026-04-23 06:50:31.398186', 0, NULL, '2026-04-23 06:50:31.398186', 'LOGIN', 'com.fastproject.module.system.controller.AuthController', '用户登录', NULL, 'POST', '0:0:0:0:0:0:0:1', 'login', 'arg0=LoginDto(username=admin, password=123456, captchaKey=a858a25481db40ab9c388135ce609eb2, captcha=ERWR)', NULL, 't', 90, 'LOGIN', '/auth/login');
INSERT INTO "public"."sys_operation_log" VALUES (322535390441508864, 1, '2026-04-23 06:53:49.499687', 0, NULL, '2026-04-23 06:53:49.499687', 'OTHER', 'com.fastproject.module.message.controller.MessageTestController', '发送测试消息', 'java.util.regex.PatternSyntaxException: Illegal repetition near index 2
#{appName}
  ^
	at java.base/java.util.regex.Pattern.error(Pattern.java:2205)
	at java.base/java.util.regex.Pattern.closure(Pattern.java:3488)
	at java.base/java.util.regex.Pattern.sequence(Pattern.java:2391)
	at java.base/java.util.regex.Pattern.expr(Pattern.java:2246)
	at java.base/java.util.regex.Pattern.compile(Pattern.java:1946)
	at java.base/java.util.regex.Pattern.<init>(Pattern.java:1577)
	at java.base/java.util.regex.Pattern.compile(Pattern.java:1102)
	at java.base/java.lang.String.replaceAll(String.java:3134)
	at com.fastproject.message.send.MessageSendUtils.renderTemplate(MessageSendUtils.java:79)
	at com.fastproject.message.send.MessageSendUtils.sendMessage(MessageSendUtils.java:46)
	at com.fastproject.module.message.controller.MessageTestController.send(MessageTestController.java:34)
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:104)
	at java.base/java.lang.reflect.Method.invoke(Method.java:565)
	at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:359)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.invokeJoinpoint(ReflectiveMethodInvocation.java:190)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:158)
	at org.springframework.aop.aspectj.AspectJAfterThrowingAdvice.invoke(AspectJAfterThrowingAdvice.java:63)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:168)
	at org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint.proceed(MethodInvocationProceedingJoinPoint.java:82)
	at com.fastproject.logs.aspect.LogAspect.around(LogAspect.java:93)
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:104)
	at java.base/java.lang.reflect.Method.invoke(Method.java:565)
	at org.springframework.a... [截断]', 'POST', '0:0:0:0:0:0:0:1', 'send', 'arg0=MessageTestSend(templateCode=bb, receiver=asdfsdfsad, params={appName=注册, code=验证码, expireTime=10})', NULL, 'f', 5, 'BUSINESS', '/message/test/send');
INSERT INTO "public"."sys_operation_log" VALUES (322535764384681984, 1, '2026-04-23 06:55:18.654433', 0, NULL, '2026-04-23 06:55:18.654433', 'OTHER', 'com.fastproject.module.message.controller.MessageTestController', '发送测试消息', 'java.util.regex.PatternSyntaxException: Illegal repetition near index 2
#{appName}
  ^
	at java.base/java.util.regex.Pattern.error(Pattern.java:2205)
	at java.base/java.util.regex.Pattern.closure(Pattern.java:3488)
	at java.base/java.util.regex.Pattern.sequence(Pattern.java:2391)
	at java.base/java.util.regex.Pattern.expr(Pattern.java:2246)
	at java.base/java.util.regex.Pattern.compile(Pattern.java:1946)
	at java.base/java.util.regex.Pattern.<init>(Pattern.java:1577)
	at java.base/java.util.regex.Pattern.compile(Pattern.java:1102)
	at java.base/java.lang.String.replaceAll(String.java:3134)
	at com.fastproject.message.send.MessageSendUtils.renderTemplate(MessageSendUtils.java:79)
	at com.fastproject.message.send.MessageSendUtils.sendMessage(MessageSendUtils.java:46)
	at com.fastproject.module.message.controller.MessageTestController.send(MessageTestController.java:34)
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:104)
	at java.base/java.lang.reflect.Method.invoke(Method.java:565)
	at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:359)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.invokeJoinpoint(ReflectiveMethodInvocation.java:190)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:158)
	at org.springframework.aop.aspectj.AspectJAfterThrowingAdvice.invoke(AspectJAfterThrowingAdvice.java:63)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:168)
	at org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint.proceed(MethodInvocationProceedingJoinPoint.java:82)
	at com.fastproject.logs.aspect.LogAspect.around(LogAspect.java:93)
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:104)
	at java.base/java.lang.reflect.Method.invoke(Method.java:565)
	at org.springframework.a... [截断]', 'POST', '0:0:0:0:0:0:0:1', 'send', 'arg0=MessageTestSend(templateCode=bb, receiver=asdfsdfsad, params={appName=注册, code=验证码, expireTime=10})', NULL, 'f', 15, 'BUSINESS', '/message/test/send');
INSERT INTO "public"."sys_operation_log" VALUES (322536054810873856, 1, '2026-04-23 06:56:27.897752', 0, NULL, '2026-04-23 06:56:27.897752', 'OTHER', 'com.fastproject.module.message.controller.MessageTestController', '发送测试消息', 'java.util.regex.PatternSyntaxException: Illegal repetition near index 2
#{appName}
  ^
	at java.base/java.util.regex.Pattern.error(Pattern.java:2205)
	at java.base/java.util.regex.Pattern.closure(Pattern.java:3488)
	at java.base/java.util.regex.Pattern.sequence(Pattern.java:2391)
	at java.base/java.util.regex.Pattern.expr(Pattern.java:2246)
	at java.base/java.util.regex.Pattern.compile(Pattern.java:1946)
	at java.base/java.util.regex.Pattern.<init>(Pattern.java:1577)
	at java.base/java.util.regex.Pattern.compile(Pattern.java:1102)
	at java.base/java.lang.String.replaceAll(String.java:3134)
	at com.fastproject.message.send.MessageSendUtils.renderTemplate(MessageSendUtils.java:81)
	at com.fastproject.message.send.MessageSendUtils.sendMessage(MessageSendUtils.java:47)
	at com.fastproject.module.message.controller.MessageTestController.send(MessageTestController.java:34)
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:104)
	at java.base/java.lang.reflect.Method.invoke(Method.java:565)
	at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:359)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.invokeJoinpoint(ReflectiveMethodInvocation.java:190)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:158)
	at org.springframework.aop.aspectj.AspectJAfterThrowingAdvice.invoke(AspectJAfterThrowingAdvice.java:63)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:168)
	at org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint.proceed(MethodInvocationProceedingJoinPoint.java:82)
	at com.fastproject.logs.aspect.LogAspect.around(LogAspect.java:93)
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:104)
	at java.base/java.lang.reflect.Method.invoke(Method.java:565)
	at org.springframework.a... [截断]', 'POST', '0:0:0:0:0:0:0:1', 'send', 'arg0=MessageTestSend(templateCode=bb, receiver=asdfsdfsad, params={appName=注册, code=验证码, expireTime=10})', NULL, 'f', 28, 'BUSINESS', '/message/test/send');
INSERT INTO "public"."sys_operation_log" VALUES (322536424765263872, 1, '2026-04-23 06:57:56.101486', 0, NULL, '2026-04-23 06:57:56.101486', 'OTHER', 'com.fastproject.module.message.controller.MessageTestController', '发送测试消息', NULL, 'POST', '0:0:0:0:0:0:0:1', 'send', 'arg0=MessageTestSend(templateCode=bb, receiver=asdfsdfsad, params={appName=注册, code=验证码, expireTime=10})', NULL, 't', 51, 'BUSINESS', '/message/test/send');
INSERT INTO "public"."sys_operation_log" VALUES (322536604763820032, 1, '2026-04-23 06:58:39.016844', 0, NULL, '2026-04-23 06:58:39.016844', 'UPDATE', 'com.fastproject.module.message.controller.MessageTemplateController', '修改消息模版', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=MessageTemplateUpdate(id=321780181989724160, code=bb, title=注册验证码, configId=321770014233464832, description={
    "appName":"注册",
    "code":"验证码",
     "expireTime":"10"
}, status=1, content=【#{appName}】#{appName} 您的注册验证码是：#{code}，有效期#{expireTime}分钟，请勿泄露给他人。, typeId=321774750718365696)', NULL, 't', 18, 'BUSINESS', '/message/template');
INSERT INTO "public"."sys_operation_log" VALUES (322536623113900032, 1, '2026-04-23 06:58:43.391297', 0, NULL, '2026-04-23 06:58:43.391297', 'OTHER', 'com.fastproject.module.message.controller.MessageTestController', '发送测试消息', NULL, 'POST', '0:0:0:0:0:0:0:1', 'send', 'arg0=MessageTestSend(templateCode=bb, receiver=asdfsdfsad, params={appName=注册, code=验证码, expireTime=10})', NULL, 't', 15, 'BUSINESS', '/message/test/send');
INSERT INTO "public"."sys_operation_log" VALUES (322536749924487168, 1, '2026-04-23 06:59:13.625198', 0, NULL, '2026-04-23 06:59:13.625198', 'OTHER', 'com.fastproject.module.message.controller.MessageTestController', '验证测试验证码', NULL, 'POST', '0:0:0:0:0:0:0:1', 'verify', 'arg0=MessageTestVerify(target=asdfsdfsad, code=验证码)', NULL, 't', 10, 'BUSINESS', '/message/test/verify');
INSERT INTO "public"."sys_operation_log" VALUES (322536841930739712, 1, '2026-04-23 06:59:35.561158', 0, NULL, '2026-04-23 06:59:35.561158', 'OTHER', 'com.fastproject.module.message.controller.MessageTestController', '发送测试消息', NULL, 'POST', '0:0:0:0:0:0:0:1', 'send', 'arg0=MessageTestSend(templateCode=bb, receiver=asdfsdfsad, params={appName=注册, code=验证码, expireTime=10})', NULL, 't', 11, 'BUSINESS', '/message/test/send');
INSERT INTO "public"."sys_operation_log" VALUES (322536871479611392, 1, '2026-04-23 06:59:42.606073', 0, NULL, '2026-04-23 06:59:42.606073', 'OTHER', 'com.fastproject.module.message.controller.MessageTestController', '验证测试验证码', NULL, 'POST', '0:0:0:0:0:0:0:1', 'verify', 'arg0=MessageTestVerify(target=asdfsdfsad, code=验证码)', NULL, 't', 4, 'BUSINESS', '/message/test/verify');
INSERT INTO "public"."sys_operation_log" VALUES (322537310770040832, 1, '2026-04-23 07:01:27.341413', 0, NULL, '2026-04-23 07:01:27.341413', 'OTHER', 'com.fastproject.module.message.controller.MessageTestController', '验证测试验证码', NULL, 'POST', '0:0:0:0:0:0:0:1', 'verify', 'arg0=MessageTestVerify(target=asdfsdfsad, code=验证码)', NULL, 't', 5, 'BUSINESS', '/message/test/verify');
INSERT INTO "public"."sys_operation_log" VALUES (322537329258532864, 1, '2026-04-23 07:01:31.749549', 0, NULL, '2026-04-23 07:01:31.749549', 'OTHER', 'com.fastproject.module.message.controller.MessageTestController', '发送测试消息', NULL, 'POST', '0:0:0:0:0:0:0:1', 'send', 'arg0=MessageTestSend(templateCode=bb, receiver=asdfsdfsad, params={appName=注册, code=验证码, expireTime=10})', NULL, 't', 15, 'BUSINESS', '/message/test/send');
INSERT INTO "public"."sys_operation_log" VALUES (322548881818783744, 1, '2026-04-23 07:47:26.094863', 0, NULL, '2026-04-23 07:47:26.094863', 'OTHER', 'com.fastproject.module.message.controller.MessageTestController', '验证测试验证码', NULL, 'POST', '0:0:0:0:0:0:0:1', 'verify', 'arg0=MessageTestVerify(target=asdfsdfsad, code=验证码)', NULL, 't', 5, 'BUSINESS', '/message/test/verify');
INSERT INTO "public"."sys_operation_log" VALUES (322556783770079232, 1, '2026-04-23 08:18:50.066972', 0, NULL, '2026-04-23 08:18:50.066972', 'UPDATE', 'com.fastproject.module.message.controller.MessageConfigController', '修改消息配置', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=MessageConfigUpdate(id=321770014233464832, title=邮箱配置, type=messageSendMailService, config={
    "host": "smtp.qq.com",
    "port": 465,
    "username": "example@qq.com",
    "password": "your_auth_code_here",
    "protocol": "smtps",
    "defaultEncoding": "UTF-8",
    "auth": true,
    "starttlsEnable": true,
    "from": "example@qq.com"
}, description=aaa, status=1)', NULL, 't', 31, 'BUSINESS', '/message/config');
INSERT INTO "public"."sys_operation_log" VALUES (322561475468201984, 1, '2026-04-23 08:37:28.654925', 0, NULL, '2026-04-23 08:37:28.654925', 'UPDATE', 'com.fastproject.module.message.controller.MessageConfigController', '修改消息配置', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=MessageConfigUpdate(id=321770014233464832, title=邮箱配置, type=messageSendMailService, config={
    "host": "smtp.qq.com",
    "port": 465,
    "username": "2681024378@qq.com",
    "password": "djdjbvhdolwbeaii",
    "protocol": "smtps",
    "defaultEncoding": "UTF-8",
    "auth": true,
    "starttlsEnable": true,
    "from": "2681024378@qq.com"
}, description=aaa, status=1)', NULL, 't', 35, 'BUSINESS', '/message/config');
INSERT INTO "public"."sys_operation_log" VALUES (322561700714909696, 1, '2026-04-23 08:38:22.357919', 0, NULL, '2026-04-23 08:38:22.357919', 'OTHER', 'com.fastproject.module.message.controller.MessageTestController', '发送测试消息', NULL, 'POST', '0:0:0:0:0:0:0:1', 'send', 'arg0=MessageTestSend(templateCode=bb, receiver=3350542608@qq.com, params={appName=注册, code=12341, expireTime=10})', NULL, 't', 4052, 'BUSINESS', '/message/test/send');
INSERT INTO "public"."sys_operation_log" VALUES (322562597150920704, 1, '2026-04-23 08:41:56.084281', 0, NULL, '2026-04-23 08:41:56.084281', 'UPDATE', 'com.fastproject.module.message.controller.MessageConfigController', '修改消息配置', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=MessageConfigUpdate(id=321770014233464832, title=邮箱配置, type=messageSendMailService, config={
    "host": "smtp.qq.com",
    "port": 465,
    "username": "2681024378@qq.com",
    "password": "djdjbvhdolwbeaii",
    "protocol": "smtps",
    "defaultEncoding": "UTF-8",
    "auth": true,
    "starttlsEnable": true,
    "from": "2681024378@qq.com"
}, description=host：邮件服务器地址 port：邮件服务器端口 username：邮箱账号 password：邮箱授权码或密码 protocol：发送协议 defaultEncoding：默认编码 auth：是否需要身份验证 starttlsEnable：是否启用 STARTTLS 安全连接... [截断]', NULL, 't', 5, 'BUSINESS', '/message/config');
INSERT INTO "public"."sys_operation_log" VALUES (322907458328727552, 1, '2026-04-24 07:32:17.396142', 0, NULL, '2026-04-24 07:32:17.396142', 'CREATE', 'com.fastproject.module.system.controller.SysDictTypeController', '添加字典类型', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysDictTypeCreate(name=消息发送类型, type=message_record_type, status=1, remark=)', NULL, 't', 5, 'BUSINESS', '/sys/dict/type');
INSERT INTO "public"."sys_operation_log" VALUES (322563647954096128, 1, '2026-04-23 08:46:06.615904', 0, NULL, '2026-04-23 08:46:06.615904', 'OTHER', 'com.fastproject.module.message.controller.MessageTestController', '发送测试消息', NULL, 'POST', '0:0:0:0:0:0:0:1', 'send', 'arg0=MessageTestSend(templateCode=bb, receiver=3350542608@qq.com, params={appName=注册, code=123码, expireTime=10})', NULL, 't', 115, 'BUSINESS', '/message/test/send');
INSERT INTO "public"."sys_operation_log" VALUES (322564782777569280, 1, '2026-04-23 08:50:37.178073', 0, NULL, '2026-04-23 08:50:37.178073', 'OTHER', 'com.fastproject.module.message.controller.MessageTestController', '发送测试消息', NULL, 'POST', '0:0:0:0:0:0:0:1', 'send', 'arg0=MessageTestSend(templateCode=bb, receiver=3350542608@qq.com, params={appName=注册, code=123码, expireTime=10})', NULL, 't', 4285, 'BUSINESS', '/message/test/send');
INSERT INTO "public"."sys_operation_log" VALUES (322565375512416256, 1, '2026-04-23 08:52:58.497806', 0, NULL, '2026-04-23 08:52:58.498319', 'UPDATE', 'com.fastproject.module.message.controller.MessageConfigController', '修改消息配置', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=MessageConfigUpdate(id=321770014233464832, title=邮箱配置, type=messageSendMailService, config={
    "host": "smtp.qq.com",
    "port": 465,
    "username": "12345@qq.com",
    "password": "12345",
    "protocol": "smtps",
    "defaultEncoding": "UTF-8",
    "auth": true,
    "starttlsEnable": true,
    "from": "12345@qq.com"
}, description=host：邮件服务器地址 port：邮件服务器端口 username：邮箱账号 password：邮箱授权码或密码 protocol：发送协议 defaultEncoding：默认编码 auth：是否需要身份验证 starttlsEnable：是否启用 STARTTLS 安全连接，默认为 true from：发件人邮箱地... [截断]', NULL, 't', 30, 'BUSINESS', '/message/config');
INSERT INTO "public"."sys_operation_log" VALUES (322607149505187840, NULL, '2026-04-23 11:38:58.193961', 0, NULL, '2026-04-23 11:38:58.193961', 'LOGIN', 'com.fastproject.module.system.controller.AuthController', '用户登录', 'com.fastproject.exception.BusinessException: 验证码错误
	at com.fastproject.module.system.service.impl.AuthServiceImpl.validateCaptcha(AuthServiceImpl.java:77)
	at com.fastproject.module.system.service.impl.AuthServiceImpl.login(AuthServiceImpl.java:39)
	at com.fastproject.module.system.controller.AuthController.login(AuthController.java:48)
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:104)
	at java.base/java.lang.reflect.Method.invoke(Method.java:565)
	at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:359)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.invokeJoinpoint(ReflectiveMethodInvocation.java:190)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:158)
	at org.springframework.aop.aspectj.AspectJAfterThrowingAdvice.invoke(AspectJAfterThrowingAdvice.java:63)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:168)
	at org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint.proceed(MethodInvocationProceedingJoinPoint.java:82)
	at com.fastproject.logs.aspect.LogAspect.around(LogAspect.java:93)
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:104)
	at java.base/java.lang.reflect.Method.invoke(Method.java:565)
	at org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethodWithGivenArgs(AbstractAspectJAdvice.java:648)
	at org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethod(AbstractAspectJAdvice.java:630)
	at org.springframework.aop.aspectj.AspectJAroundAdvice.invoke(AspectJAroundAdvice.java:70)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:168)
	at org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint.proceed(MethodInvocationProceedingJoinPoint.java:82)
	at com.fastp... [截断]', 'POST', '0:0:0:0:0:0:0:1', 'login', 'arg0=LoginDto(username=admin, password=123456, captchaKey=1893737a6007469c9e8a4b7346ae706b, captcha=s3qh)', NULL, 'f', 4, 'LOGIN', '/auth/login');
INSERT INTO "public"."sys_operation_log" VALUES (322607169021284352, NULL, '2026-04-23 11:39:02.846417', 0, NULL, '2026-04-23 11:39:02.846417', 'LOGIN', 'com.fastproject.module.system.controller.AuthController', '用户登录', 'com.fastproject.exception.BusinessException: 验证码错误
	at com.fastproject.module.system.service.impl.AuthServiceImpl.validateCaptcha(AuthServiceImpl.java:77)
	at com.fastproject.module.system.service.impl.AuthServiceImpl.login(AuthServiceImpl.java:39)
	at com.fastproject.module.system.controller.AuthController.login(AuthController.java:48)
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:104)
	at java.base/java.lang.reflect.Method.invoke(Method.java:565)
	at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:359)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.invokeJoinpoint(ReflectiveMethodInvocation.java:190)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:158)
	at org.springframework.aop.aspectj.AspectJAfterThrowingAdvice.invoke(AspectJAfterThrowingAdvice.java:63)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:168)
	at org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint.proceed(MethodInvocationProceedingJoinPoint.java:82)
	at com.fastproject.logs.aspect.LogAspect.around(LogAspect.java:93)
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:104)
	at java.base/java.lang.reflect.Method.invoke(Method.java:565)
	at org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethodWithGivenArgs(AbstractAspectJAdvice.java:648)
	at org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethod(AbstractAspectJAdvice.java:630)
	at org.springframework.aop.aspectj.AspectJAroundAdvice.invoke(AspectJAroundAdvice.java:70)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:168)
	at org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint.proceed(MethodInvocationProceedingJoinPoint.java:82)
	at com.fastp... [截断]', 'POST', '0:0:0:0:0:0:0:1', 'login', 'arg0=LoginDto(username=admin, password=123456, captchaKey=d06ae4a946de4a458fbdbf9d91aaed6b, captcha=UHTA)', NULL, 'f', 4, 'LOGIN', '/auth/login');
INSERT INTO "public"."sys_operation_log" VALUES (322626987040575488, NULL, '2026-04-23 12:57:47.830769', 0, NULL, '2026-04-23 12:57:47.830769', 'LOGIN', 'com.fastproject.module.system.controller.AuthController', '用户登录', 'com.fastproject.exception.BusinessException: 验证码已过期，请重新获取
	at com.fastproject.module.system.service.impl.AuthServiceImpl.validateCaptcha(AuthServiceImpl.java:70)
	at com.fastproject.module.system.service.impl.AuthServiceImpl.login(AuthServiceImpl.java:39)
	at com.fastproject.module.system.controller.AuthController.login(AuthController.java:48)
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:104)
	at java.base/java.lang.reflect.Method.invoke(Method.java:565)
	at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:359)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.invokeJoinpoint(ReflectiveMethodInvocation.java:190)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:158)
	at org.springframework.aop.aspectj.AspectJAfterThrowingAdvice.invoke(AspectJAfterThrowingAdvice.java:63)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:168)
	at org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint.proceed(MethodInvocationProceedingJoinPoint.java:82)
	at com.fastproject.logs.aspect.LogAspect.around(LogAspect.java:93)
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:104)
	at java.base/java.lang.reflect.Method.invoke(Method.java:565)
	at org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethodWithGivenArgs(AbstractAspectJAdvice.java:648)
	at org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethod(AbstractAspectJAdvice.java:630)
	at org.springframework.aop.aspectj.AspectJAroundAdvice.invoke(AspectJAroundAdvice.java:70)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:168)
	at org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint.proceed(MethodInvocationProceedingJoinPoint.java:82)
	at co... [截断]', 'POST', '0:0:0:0:0:0:0:1', 'login', 'arg0=LoginDto(username=admin, password=123456, captchaKey=86fbb9afe0904f7fbb0857fd155cf6cc, captcha=gk9b)', NULL, 'f', 2, 'LOGIN', '/auth/login');
INSERT INTO "public"."sys_operation_log" VALUES (322627013422747648, NULL, '2026-04-23 12:57:54.120714', 0, NULL, '2026-04-23 12:57:54.120714', 'LOGIN', 'com.fastproject.module.system.controller.AuthController', '用户登录', NULL, 'POST', '0:0:0:0:0:0:0:1', 'login', 'arg0=LoginDto(username=admin, password=123456, captchaKey=db38ad34feba4b90a6a3d28771ef8ba7, captcha=ay6d)', NULL, 't', 100, 'LOGIN', '/auth/login');
INSERT INTO "public"."sys_operation_log" VALUES (322627117546344448, 1, '2026-04-23 12:58:18.945361', 0, NULL, '2026-04-23 12:58:18.945361', 'LOGOUT', 'com.fastproject.module.system.controller.AuthController', '用户退出', NULL, 'POST', '0:0:0:0:0:0:0:1', 'logout', '{}', NULL, 't', 5, 'LOGIN', '/auth/logout');
INSERT INTO "public"."sys_operation_log" VALUES (322799199571808256, NULL, '2026-04-24 00:22:06.49765', 0, NULL, '2026-04-24 00:22:06.49765', 'LOGIN', 'com.fastproject.module.system.controller.AuthController', '用户登录', NULL, 'POST', '0:0:0:0:0:0:0:1', 'login', 'arg0=LoginDto(username=admin, password=123456, captchaKey=9dcead21cc6749f3b7f6ae327a133eec, captcha=4uzg)', NULL, 't', 65, 'LOGIN', '/auth/login');
INSERT INTO "public"."sys_operation_log" VALUES (322799493923868672, 1, '2026-04-24 00:23:16.676135', 0, NULL, '2026-04-24 00:23:16.676135', 'UPDATE', 'com.fastproject.module.message.controller.MessageConfigController', '修改消息配置', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=MessageConfigUpdate(id=321770014233464832, title=邮箱配置, type=messageSendMailService, config={
    "host": "smtp.share-email.com",
    "port": 465,
    "username": "jnd28@5270.ag",
    "password": "okok..999",
    "protocol": "smtps",
    "defaultEncoding": "UTF-8",
    "auth": true,
    "starttlsEnable": true,
    "from": "jnd28@5270.ag"
}, description=host：邮件服务器地址 port：邮件服务器端口 username：邮箱账号 password：邮箱授权码或密码 protocol：发送协议 defaultEncoding：默认编码 auth：是否需要身份验证 starttlsEnable：是否启用 STARTTLS 安全连接，默认为 t... [截断]', NULL, 't', 8, 'BUSINESS', '/message/config');
INSERT INTO "public"."sys_operation_log" VALUES (322799708609318912, 1, '2026-04-24 00:24:07.861555', 0, NULL, '2026-04-24 00:24:07.861555', 'OTHER', 'com.fastproject.module.message.controller.MessageTestController', '发送测试消息', NULL, 'POST', '0:0:0:0:0:0:0:1', 'send', 'arg0=MessageTestSend(templateCode=bb, receiver=3350542608@qq.com, params={appName=注册, code=验证码, expireTime=10})', NULL, 't', 4556, 'BUSINESS', '/message/test/send');
INSERT INTO "public"."sys_operation_log" VALUES (322800135203590144, 1, '2026-04-24 00:25:49.569037', 0, NULL, '2026-04-24 00:25:49.569037', 'UPDATE', 'com.fastproject.module.message.controller.MessageConfigController', '修改消息配置', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=MessageConfigUpdate(id=321770014233464832, title=邮箱配置, type=messageSendMailService, config={
    "host": "smtp.share-email.com",
    "port": 465,
    "username": "pc28@5270.ag",
    "password": "okok..999",
    "protocol": "smtps",
    "defaultEncoding": "UTF-8",
    "auth": true,
    "starttlsEnable": true,
    "from": "jnd28@5270.ag"
}, description=host：邮件服务器地址 port：邮件服务器端口 username：邮箱账号 password：邮箱授权码或密码 protocol：发送协议 defaultEncoding：默认编码 auth：是否需要身份验证 starttlsEnable：是否启用 STARTTLS 安全连接，默认为 tr... [截断]', NULL, 't', 7, 'BUSINESS', '/message/config');
INSERT INTO "public"."sys_operation_log" VALUES (322800162940522496, 1, '2026-04-24 00:25:56.18295', 0, NULL, '2026-04-24 00:25:56.18295', 'OTHER', 'com.fastproject.module.message.controller.MessageTestController', '发送测试消息', NULL, 'POST', '0:0:0:0:0:0:0:1', 'send', 'arg0=MessageTestSend(templateCode=bb, receiver=3350542608@qq.com, params={appName=注册, code=验证码, expireTime=10})', NULL, 't', 3574, 'BUSINESS', '/message/test/send');
INSERT INTO "public"."sys_operation_log" VALUES (322907139469348864, 1, '2026-04-24 07:31:01.37499', 0, NULL, '2026-04-24 07:31:01.37499', 'CREATE', 'com.fastproject.module.system.controller.SysDictDataController', '添加字典数据', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysDictDataCreate(sort=0, label=发送失败, value=FAILED, dictTypeId=322906994128326656, status=1, remark=)', NULL, 't', 4, 'BUSINESS', '/sys/dict/data');
INSERT INTO "public"."sys_operation_log" VALUES (322800336165277696, 1, '2026-04-24 00:26:37.482849', 0, NULL, '2026-04-24 00:26:37.482849', 'UPDATE', 'com.fastproject.module.message.controller.MessageConfigController', '修改消息配置', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=MessageConfigUpdate(id=321770014233464832, title=邮箱配置, type=messageSendMailService, config={
    "host": "smtp.share-email.com",
    "port": 465,
    "username": "jnd28@5270.ag",
    "password": "okok..999",
    "protocol": "smtps",
    "defaultEncoding": "UTF-8",
    "auth": true,
    "starttlsEnable": true,
    "from": "jnd28@5270.ag"
}, description=host：邮件服务器地址 port：邮件服务器端口 username：邮箱账号 password：邮箱授权码或密码 protocol：发送协议 defaultEncoding：默认编码 auth：是否需要身份验证 starttlsEnable：是否启用 STARTTLS 安全连接，默认为 t... [截断]', NULL, 't', 6, 'BUSINESS', '/message/config');
INSERT INTO "public"."sys_operation_log" VALUES (322800364409720832, 1, '2026-04-24 00:26:44.216352', 0, NULL, '2026-04-24 00:26:44.216352', 'OTHER', 'com.fastproject.module.message.controller.MessageTestController', '发送测试消息', NULL, 'POST', '0:0:0:0:0:0:0:1', 'send', 'arg0=MessageTestSend(templateCode=bb, receiver=3350542608@qq.com, params={appName=注册, code=验证码, expireTime=10})', NULL, 't', 3605, 'BUSINESS', '/message/test/send');
INSERT INTO "public"."sys_operation_log" VALUES (322854216999768064, NULL, '2026-04-24 04:00:43.674168', 0, NULL, '2026-04-24 04:00:43.674168', 'LOGIN', 'com.fastproject.module.system.controller.AuthController', '用户登录', 'com.fastproject.exception.BusinessException: 验证码错误
	at com.fastproject.module.system.service.impl.AuthServiceImpl.validateCaptcha(AuthServiceImpl.java:77)
	at com.fastproject.module.system.service.impl.AuthServiceImpl.login(AuthServiceImpl.java:39)
	at com.fastproject.module.system.controller.AuthController.login(AuthController.java:48)
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:104)
	at java.base/java.lang.reflect.Method.invoke(Method.java:565)
	at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:359)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.invokeJoinpoint(ReflectiveMethodInvocation.java:190)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:158)
	at org.springframework.aop.aspectj.AspectJAfterThrowingAdvice.invoke(AspectJAfterThrowingAdvice.java:63)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:168)
	at org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint.proceed(MethodInvocationProceedingJoinPoint.java:82)
	at com.fastproject.logs.aspect.LogAspect.around(LogAspect.java:93)
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:104)
	at java.base/java.lang.reflect.Method.invoke(Method.java:565)
	at org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethodWithGivenArgs(AbstractAspectJAdvice.java:648)
	at org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethod(AbstractAspectJAdvice.java:630)
	at org.springframework.aop.aspectj.AspectJAroundAdvice.invoke(AspectJAroundAdvice.java:70)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:168)
	at org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint.proceed(MethodInvocationProceedingJoinPoint.java:82)
	at com.fastp... [截断]', 'POST', '0:0:0:0:0:0:0:1', 'login', 'arg0=LoginDto(username=admin, password=123456, captchaKey=a32583080860482aaf57aaf14ec56724, captcha=peih)', NULL, 'f', 5, 'LOGIN', '/auth/login');
INSERT INTO "public"."sys_operation_log" VALUES (322854232451584000, NULL, '2026-04-24 04:00:47.358748', 0, NULL, '2026-04-24 04:00:47.358748', 'LOGIN', 'com.fastproject.module.system.controller.AuthController', '用户登录', 'com.fastproject.exception.BusinessException: 验证码错误
	at com.fastproject.module.system.service.impl.AuthServiceImpl.validateCaptcha(AuthServiceImpl.java:77)
	at com.fastproject.module.system.service.impl.AuthServiceImpl.login(AuthServiceImpl.java:39)
	at com.fastproject.module.system.controller.AuthController.login(AuthController.java:48)
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:104)
	at java.base/java.lang.reflect.Method.invoke(Method.java:565)
	at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:359)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.invokeJoinpoint(ReflectiveMethodInvocation.java:190)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:158)
	at org.springframework.aop.aspectj.AspectJAfterThrowingAdvice.invoke(AspectJAfterThrowingAdvice.java:63)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:168)
	at org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint.proceed(MethodInvocationProceedingJoinPoint.java:82)
	at com.fastproject.logs.aspect.LogAspect.around(LogAspect.java:93)
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:104)
	at java.base/java.lang.reflect.Method.invoke(Method.java:565)
	at org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethodWithGivenArgs(AbstractAspectJAdvice.java:648)
	at org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethod(AbstractAspectJAdvice.java:630)
	at org.springframework.aop.aspectj.AspectJAroundAdvice.invoke(AspectJAroundAdvice.java:70)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:168)
	at org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint.proceed(MethodInvocationProceedingJoinPoint.java:82)
	at com.fastp... [截断]', 'POST', '0:0:0:0:0:0:0:1', 'login', 'arg0=LoginDto(username=admin, password=123456, captchaKey=8f93eb588b874d059d08103d07e3f2c6, captcha=gjpg)', NULL, 'f', 3, 'LOGIN', '/auth/login');
INSERT INTO "public"."sys_operation_log" VALUES (322854250751332352, NULL, '2026-04-24 04:00:51.721544', 0, NULL, '2026-04-24 04:00:51.721544', 'LOGIN', 'com.fastproject.module.system.controller.AuthController', '用户登录', NULL, 'POST', '0:0:0:0:0:0:0:1', 'login', 'arg0=LoginDto(username=admin, password=123456, captchaKey=20a101f245ff4f068eade152a05e4e97, captcha=xgbv)', NULL, 't', 70, 'LOGIN', '/auth/login');
INSERT INTO "public"."sys_operation_log" VALUES (322854411292512256, 1, '2026-04-24 04:01:29.997561', 0, NULL, '2026-04-24 04:01:29.997561', 'UPDATE', 'com.fastproject.module.message.controller.MessageConfigController', '修改消息配置', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=MessageConfigUpdate(id=321770014233464832, title=邮箱配置, type=messageSendMailService, config={
    "host": "smtp.qq.com",
    "port": 465,
    "username": "123456",
    "password": "123456",
    "protocol": "smtps",
    "defaultEncoding": "UTF-8",
    "auth": true,
    "starttlsEnable": true,
    "from": "123456"
}, description=host：邮件服务器地址 port：邮件服务器端口 username：邮箱账号 password：邮箱授权码或密码 protocol：发送协议 defaultEncoding：默认编码 auth：是否需要身份验证 starttlsEnable：是否启用 STARTTLS 安全连接，默认为 true from：发件人邮箱地址, status=1... [截断]', NULL, 't', 7, 'BUSINESS', '/message/config');
INSERT INTO "public"."sys_operation_log" VALUES (322905960374996992, NULL, '2026-04-24 07:26:20.256075', 0, NULL, '2026-04-24 07:26:20.256075', 'LOGIN', 'com.fastproject.module.system.controller.AuthController', '用户登录', 'com.fastproject.exception.BusinessException: 验证码错误
	at com.fastproject.module.system.service.impl.AuthServiceImpl.validateCaptcha(AuthServiceImpl.java:77)
	at com.fastproject.module.system.service.impl.AuthServiceImpl.login(AuthServiceImpl.java:39)
	at com.fastproject.module.system.controller.AuthController.login(AuthController.java:48)
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:104)
	at java.base/java.lang.reflect.Method.invoke(Method.java:565)
	at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:359)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.invokeJoinpoint(ReflectiveMethodInvocation.java:190)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:158)
	at org.springframework.aop.aspectj.AspectJAfterThrowingAdvice.invoke(AspectJAfterThrowingAdvice.java:63)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:168)
	at org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint.proceed(MethodInvocationProceedingJoinPoint.java:82)
	at com.fastproject.logs.aspect.LogAspect.around(LogAspect.java:93)
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:104)
	at java.base/java.lang.reflect.Method.invoke(Method.java:565)
	at org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethodWithGivenArgs(AbstractAspectJAdvice.java:648)
	at org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethod(AbstractAspectJAdvice.java:630)
	at org.springframework.aop.aspectj.AspectJAroundAdvice.invoke(AspectJAroundAdvice.java:70)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:168)
	at org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint.proceed(MethodInvocationProceedingJoinPoint.java:82)
	at com.fastp... [截断]', 'POST', '0:0:0:0:0:0:0:1', 'login', 'arg0=LoginDto(username=admin, password=123456, captchaKey=b9f1e4225ad544048ac31847e73f1ae9, captcha=1UYA)', NULL, 'f', 4, 'LOGIN', '/auth/login');
INSERT INTO "public"."sys_operation_log" VALUES (322905981665284096, NULL, '2026-04-24 07:26:25.332436', 0, NULL, '2026-04-24 07:26:25.332436', 'LOGIN', 'com.fastproject.module.system.controller.AuthController', '用户登录', 'com.fastproject.exception.BusinessException: 验证码错误
	at com.fastproject.module.system.service.impl.AuthServiceImpl.validateCaptcha(AuthServiceImpl.java:77)
	at com.fastproject.module.system.service.impl.AuthServiceImpl.login(AuthServiceImpl.java:39)
	at com.fastproject.module.system.controller.AuthController.login(AuthController.java:48)
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:104)
	at java.base/java.lang.reflect.Method.invoke(Method.java:565)
	at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:359)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.invokeJoinpoint(ReflectiveMethodInvocation.java:190)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:158)
	at org.springframework.aop.aspectj.AspectJAfterThrowingAdvice.invoke(AspectJAfterThrowingAdvice.java:63)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:168)
	at org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint.proceed(MethodInvocationProceedingJoinPoint.java:82)
	at com.fastproject.logs.aspect.LogAspect.around(LogAspect.java:93)
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:104)
	at java.base/java.lang.reflect.Method.invoke(Method.java:565)
	at org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethodWithGivenArgs(AbstractAspectJAdvice.java:648)
	at org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethod(AbstractAspectJAdvice.java:630)
	at org.springframework.aop.aspectj.AspectJAroundAdvice.invoke(AspectJAroundAdvice.java:70)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:168)
	at org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint.proceed(MethodInvocationProceedingJoinPoint.java:82)
	at com.fastp... [截断]', 'POST', '0:0:0:0:0:0:0:1', 'login', 'arg0=LoginDto(username=admin, password=123456, captchaKey=deccdf4ca13f4548acb2ea684332324c, captcha=madj)', NULL, 'f', 3, 'LOGIN', '/auth/login');
INSERT INTO "public"."sys_operation_log" VALUES (322905996563451904, NULL, '2026-04-24 07:26:28.88429', 0, NULL, '2026-04-24 07:26:28.88429', 'LOGIN', 'com.fastproject.module.system.controller.AuthController', '用户登录', NULL, 'POST', '0:0:0:0:0:0:0:1', 'login', 'arg0=LoginDto(username=admin, password=123456, captchaKey=75b03f961442429eb7db599734af9ca1, captcha=mypk)', NULL, 't', 102, 'LOGIN', '/auth/login');
INSERT INTO "public"."sys_operation_log" VALUES (322906994145103872, 1, '2026-04-24 07:30:26.726542', 0, NULL, '2026-04-24 07:30:26.726542', 'CREATE', 'com.fastproject.module.system.controller.SysDictTypeController', '添加字典类型', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysDictTypeCreate(name=消息发送状态, type=message_record_status, status=1, remark=)', NULL, 't', 9, 'BUSINESS', '/sys/dict/type');
INSERT INTO "public"."sys_operation_log" VALUES (322907070863118336, 1, '2026-04-24 07:30:45.017043', 0, NULL, '2026-04-24 07:30:45.017043', 'CREATE', 'com.fastproject.module.system.controller.SysDictDataController', '添加字典数据', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysDictDataCreate(sort=0, label=已发送, value=SENT, dictTypeId=322906994128326656, status=0, remark=)', NULL, 't', 7, 'BUSINESS', '/sys/dict/data');
INSERT INTO "public"."sys_operation_log" VALUES (322907082846244864, 1, '2026-04-24 07:30:47.874401', 0, NULL, '2026-04-24 07:30:47.874401', 'UPDATE', 'com.fastproject.module.system.controller.SysDictDataController', '修改字典数据', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=SysDictDataUpdate(id=322907070850535424, sort=0, label=已发送, value=SENT, dictTypeId=322906994128326656, status=1, remark=)', NULL, 't', 15, 'BUSINESS', '/sys/dict/data');
INSERT INTO "public"."sys_operation_log" VALUES (322907098725879808, 1, '2026-04-24 07:30:51.660775', 0, NULL, '2026-04-24 07:30:51.660775', 'UPDATE', 'com.fastproject.module.system.controller.SysDictDataController', '修改字典数据', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=SysDictDataUpdate(id=322907070850535424, sort=0, label=已发送, value=SENT, dictTypeId=322906994128326656, status=1, remark=)', NULL, 't', 4, 'BUSINESS', '/sys/dict/data');
INSERT INTO "public"."sys_operation_log" VALUES (325376416306827264, 1, '2026-05-01 03:03:02.849251', 0, NULL, '2026-05-01 03:03:02.849251', 'LOGOUT', 'com.fastproject.module.system.controller.AuthController', '用户退出', NULL, 'POST', '192.168.1.198', 'logout', '{}', NULL, 't', 5, 'LOGIN', '/auth/logout');
INSERT INTO "public"."sys_operation_log" VALUES (322907510380040192, 1, '2026-04-24 07:32:29.806503', 0, NULL, '2026-04-24 07:32:29.806503', 'CREATE', 'com.fastproject.module.system.controller.SysDictDataController', '添加字典数据', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysDictDataCreate(sort=0, label=验证码, value=CODE, dictTypeId=322907458316144640, status=0, remark=)', NULL, 't', 5, 'BUSINESS', '/sys/dict/data');
INSERT INTO "public"."sys_operation_log" VALUES (322907559608586240, 1, '2026-04-24 07:32:41.543283', 0, NULL, '2026-04-24 07:32:41.543283', 'CREATE', 'com.fastproject.module.system.controller.SysDictDataController', '添加字典数据', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysDictDataCreate(sort=0, label=通知, value=NOTICE, dictTypeId=322907458316144640, status=1, remark=)', NULL, 't', 4, 'BUSINESS', '/sys/dict/data');
INSERT INTO "public"."sys_operation_log" VALUES (322907570144677888, 1, '2026-04-24 07:32:44.055881', 0, NULL, '2026-04-24 07:32:44.055881', 'UPDATE', 'com.fastproject.module.system.controller.SysDictDataController', '修改字典数据', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=SysDictDataUpdate(id=322907510367457280, sort=0, label=验证码, value=CODE, dictTypeId=322907458316144640, status=1, remark=)', NULL, 't', 6, 'BUSINESS', '/sys/dict/data');
INSERT INTO "public"."sys_operation_log" VALUES (322952165507338240, 1, '2026-04-24 10:29:56.418978', 0, NULL, '2026-04-24 10:29:56.418978', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysPermissionsCreate(title=权益, code=, url=, type=0, component=, icon=ReadOutlined, status=1, parentId=null, componentName=, sort=6, applicationId=null, applicationCode=)', NULL, 't', 11, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (323170839199617024, 1, '2026-04-25 00:58:52.289218', 0, NULL, '2026-04-25 00:58:52.289218', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysPermissionsCreate(title=成长账户, code=usergrowth:userlevelaccount:query, url=/userGrowth/userlevelaccount, type=1, component=views/userGrowth/userlevelaccount/index, icon=UserOutlined, status=1, parentId=322952165469589504, componentName=UsergrowthUserlevelaccount, sort=0, applicationId=null, applicationCode=)', NULL, 't', 23, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (323171045936861184, 1, '2026-04-25 00:59:41.579836', 0, NULL, '2026-04-25 00:59:41.579836', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysPermissionsCreate(title=添加, code=usergrowth:userlevelaccount:add, url=, type=2, component=, icon=, status=0, parentId=323170839119925248, componentName=, sort=0, applicationId=null, applicationCode=)', NULL, 't', 5, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (323171046989631488, 1, '2026-04-25 00:59:41.830918', 0, NULL, '2026-04-25 00:59:41.830918', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysPermissionsCreate(title=修改, code=usergrowth:userlevelaccount:update, url=, type=2, component=, icon=, status=0, parentId=323170839119925248, componentName=, sort=0, applicationId=null, applicationCode=)', NULL, 't', 4, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (323171047320981504, 1, '2026-04-25 00:59:41.909293', 0, NULL, '2026-04-25 00:59:41.909293', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysPermissionsCreate(title=删除, code=usergrowth:userlevelaccount:delete, url=, type=2, component=, icon=, status=0, parentId=323170839119925248, componentName=, sort=0, applicationId=null, applicationCode=)', NULL, 't', 3, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (323171843123056640, 1, '2026-04-25 01:02:51.643493', 0, NULL, '2026-04-25 01:02:51.643493', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysPermissionsCreate(title=等级配置, code=usergrowth:userlevelconfig:query, url=/userGrowth/userlevelconfig, type=1, component=views/userGrowth/userlevelconfig/index, icon=AuditOutlined, status=1, parentId=322952165469589504, componentName=UsergrowthUserlevelconfig, sort=2, applicationId=null, applicationCode=)', NULL, 't', 4, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (323172120546906112, 1, '2026-04-25 01:03:57.78633', 0, NULL, '2026-04-25 01:03:57.78633', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysPermissionsCreate(title=添加, code=usergrowth:userlevelconfig:add, url=, type=2, component=, icon=, status=0, parentId=323171843106279424, componentName=, sort=0, applicationId=null, applicationCode=)', NULL, 't', 4, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (323172121574510592, 1, '2026-04-25 01:03:58.031907', 0, NULL, '2026-04-25 01:03:58.031907', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysPermissionsCreate(title=修改, code=usergrowth:userlevelconfig:update, url=, type=2, component=, icon=, status=0, parentId=323171843106279424, componentName=, sort=0, applicationId=null, applicationCode=)', NULL, 't', 5, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (323172121893277696, 1, '2026-04-25 01:03:58.107616', 0, NULL, '2026-04-25 01:03:58.107616', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysPermissionsCreate(title=删除, code=usergrowth:userlevelconfig:delete, url=, type=2, component=, icon=, status=0, parentId=323171843106279424, componentName=, sort=0, applicationId=null, applicationCode=)', NULL, 't', 4, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (323172541189459968, 1, '2026-04-25 01:05:38.075527', 0, NULL, '2026-04-25 01:05:38.075527', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysPermissionsCreate(title=成长值记录, code=usergrowth:userintegralrecord:query, url=/userGrowth/userlevelrecord, type=1, component=views/userGrowth/userlevelrecord/index, icon=BarsOutlined, status=1, parentId=322952165469589504, componentName=UsergrowthUserlevelrecord, sort=3, applicationId=null, applicationCode=)', NULL, 't', 4, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (323172690615734272, 1, '2026-04-25 01:06:13.701664', 0, NULL, '2026-04-25 01:06:13.701664', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysPermissionsCreate(title=添加, code=usergrowth:userintegralrecord:add, url=, type=2, component=, icon=, status=0, parentId=323172541172682752, componentName=, sort=0, applicationId=null, applicationCode=)', NULL, 't', 3, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (323172691999854592, 1, '2026-04-25 01:06:14.031208', 0, NULL, '2026-04-25 01:06:14.031208', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysPermissionsCreate(title=修改, code=usergrowth:userintegralrecord:update, url=, type=2, component=, icon=, status=0, parentId=323172541172682752, componentName=, sort=0, applicationId=null, applicationCode=)', NULL, 't', 4, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (323172693044236288, 1, '2026-04-25 01:06:14.280097', 0, NULL, '2026-04-25 01:06:14.280097', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysPermissionsCreate(title=删除, code=usergrowth:userintegralrecord:delete, url=, type=2, component=, icon=, status=0, parentId=323172541172682752, componentName=, sort=0, applicationId=null, applicationCode=)', NULL, 't', 4, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (323173706534883328, 1, '2026-04-25 01:10:15.915282', 0, NULL, '2026-04-25 01:10:15.915282', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysPermissionsCreate(title=积分账户, code=usergrowth:userintegralaccount:query, url=/userGrowth/userintegralaccount, type=1, component=views/userGrowth/userintegralaccount/index, icon=UserOutlined, status=1, parentId=322952165469589504, componentName=UsergrowthUserintegralaccount, sort=0, applicationId=null, applicationCode=)', NULL, 't', 4, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (324377952001331200, 1, '2026-04-28 08:55:30.408718', 0, NULL, '2026-04-28 08:55:30.408718', 'UPDATE', 'com.fastproject.module.system.controller.SysDictDataController', '修改字典数据', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=SysDictDataUpdate(id=324369112266575872, sort=2, label=阿里云, value=aliyunFileStorageStrategy, dictTypeId=324368936110002176, status=1, remark=)', NULL, 't', 8, 'BUSINESS', '/sys/dict/data');
INSERT INTO "public"."sys_operation_log" VALUES (323173743222460416, 1, '2026-04-25 01:10:24.662413', 0, NULL, '2026-04-25 01:10:24.662413', 'UPDATE', 'com.fastproject.module.system.controller.SysPermissionsController', '修改权限', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=SysPermissionsUpdate(id=323173706522300416, title=积分账户, code=usergrowth:userintegralaccount:query, url=/userGrowth/userintegralaccount, type=1, component=views/userGrowth/userintegralaccount/index, icon=UserOutlined, status=1, parentId=322952165469589504, componentName=UsergrowthUserintegralaccount, sort=4, applicationId=null, applicationCode=)', NULL, 't', 13, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (323173867600351232, 1, '2026-04-25 01:10:54.316601', 0, NULL, '2026-04-25 01:10:54.316601', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysPermissionsCreate(title=添加, code=usergrowth:userintegralaccount:add, url=, type=2, component=, icon=, status=0, parentId=323173706522300416, componentName=, sort=0, applicationId=null, applicationCode=)', NULL, 't', 3, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (323173868955111424, 1, '2026-04-25 01:10:54.639506', 0, NULL, '2026-04-25 01:10:54.639506', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysPermissionsCreate(title=修改, code=usergrowth:userintegralaccount:update, url=, type=2, component=, icon=, status=0, parentId=323173706522300416, componentName=, sort=0, applicationId=null, applicationCode=)', NULL, 't', 4, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (323173869991104512, 1, '2026-04-25 01:10:54.886019', 0, NULL, '2026-04-25 01:10:54.886019', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysPermissionsCreate(title=删除, code=usergrowth:userintegralaccount:delete, url=, type=2, component=, icon=, status=0, parentId=323173706522300416, componentName=, sort=0, applicationId=null, applicationCode=)', NULL, 't', 4, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (323174207167008768, 1, '2026-04-25 01:12:15.275561', 0, NULL, '2026-04-25 01:12:15.275561', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysPermissionsCreate(title=积分流水, code=usergrowth:userintegralrecord:query, url=/userGrowth/userintegralrecord, type=1, component=views/userGrowth/userintegralrecord/index, icon=BarsOutlined, status=1, parentId=322952165469589504, componentName=UsergrowthUserintegralrecord, sort=5, applicationId=null, applicationCode=)', NULL, 't', 3, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (323174354030563328, 1, '2026-04-25 01:12:50.290639', 0, NULL, '2026-04-25 01:12:50.290639', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysPermissionsCreate(title=添加, code=usergrowth:userintegralrecord:add, url=, type=2, component=, icon=, status=0, parentId=323174207154425856, componentName=, sort=0, applicationId=null, applicationCode=)', NULL, 't', 3, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (323174355095916544, 1, '2026-04-25 01:12:50.544669', 0, NULL, '2026-04-25 01:12:50.544669', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysPermissionsCreate(title=修改, code=usergrowth:userintegralrecord:update, url=, type=2, component=, icon=, status=0, parentId=323174207154425856, componentName=, sort=0, applicationId=null, applicationCode=)', NULL, 't', 4, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (323174355347574784, 1, '2026-04-25 01:12:50.604293', 0, NULL, '2026-04-25 01:12:50.604293', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysPermissionsCreate(title=删除, code=usergrowth:userintegralrecord:delete, url=, type=2, component=, icon=, status=0, parentId=323174207154425856, componentName=, sort=0, applicationId=null, applicationCode=)', NULL, 't', 3, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (323174411173761024, 1, '2026-04-25 01:13:03.914511', 0, NULL, '2026-04-25 01:13:03.914511', 'UPDATE', 'com.fastproject.module.system.controller.SysRoleController', '修改角色', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=SysRoleUpdate(id=311703722914877440, title=超级管理员, code=admin, status=1, applicationId=null, applicationCode=, permissionIds=[311762357514801152, 314602359294660608, 313512229460905984, 314530113607831552, 322527163695894528, 318995237680844800, 322134033570074624, 322134034677370880, 313550330359058432, 321043873273090048, 316775517045002240, 313511610620710912, 321837761428590592, 313512229410574336, 318993678729351168, 313550330203869184, 318995415359950848, 321774421545193472, 321043647103635... [截断]', NULL, 't', 128, 'BUSINESS', '/sys/role');
INSERT INTO "public"."sys_operation_log" VALUES (323250439892111360, 1, '2026-04-25 06:15:10.573895', 0, NULL, '2026-04-25 06:15:10.573895', 'UPDATE', 'com.fastproject.module.system.controller.ProfileController', '更新个人信息', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'updateProfile', 'arg0=UserProfileUpdate(nickname=管理员, phone=18711321234, email=2@qq.com, gender=1, remark=aaa, avatar=http://127.0.0.1/file/2026/04/04/beab13a3ddd6e0fd1a2787f32c338a53.png)', NULL, 't', 11, 'BUSINESS', '/sys/profile');
INSERT INTO "public"."sys_operation_log" VALUES (324277077190774784, NULL, '2026-04-28 02:14:39.979229', 0, NULL, '2026-04-28 02:14:39.979229', 'LOGIN', 'com.fastproject.module.system.controller.AuthController', '用户登录', 'com.fastproject.exception.BusinessException: 验证码错误
	at com.fastproject.module.system.service.impl.AuthServiceImpl.validateCaptcha(AuthServiceImpl.java:77)
	at com.fastproject.module.system.service.impl.AuthServiceImpl.login(AuthServiceImpl.java:39)
	at com.fastproject.module.system.controller.AuthController.login(AuthController.java:48)
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:104)
	at java.base/java.lang.reflect.Method.invoke(Method.java:565)
	at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:359)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.invokeJoinpoint(ReflectiveMethodInvocation.java:190)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:158)
	at org.springframework.aop.aspectj.AspectJAfterThrowingAdvice.invoke(AspectJAfterThrowingAdvice.java:63)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:168)
	at org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint.proceed(MethodInvocationProceedingJoinPoint.java:82)
	at com.fastproject.logs.aspect.LogAspect.around(LogAspect.java:93)
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:104)
	at java.base/java.lang.reflect.Method.invoke(Method.java:565)
	at org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethodWithGivenArgs(AbstractAspectJAdvice.java:648)
	at org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethod(AbstractAspectJAdvice.java:630)
	at org.springframework.aop.aspectj.AspectJAroundAdvice.invoke(AspectJAroundAdvice.java:70)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:168)
	at org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint.proceed(MethodInvocationProceedingJoinPoint.java:82)
	at com.fastp... [截断]', 'POST', '0:0:0:0:0:0:0:1', 'login', 'arg0=LoginDto(username=admin, password=123456, captchaKey=e91e8b2964ee4142b8129696d7af9f65, captcha=1hjke)', NULL, 'f', 4, 'LOGIN', '/auth/login');
INSERT INTO "public"."sys_operation_log" VALUES (324277102406930432, NULL, '2026-04-28 02:14:45.991401', 0, NULL, '2026-04-28 02:14:45.991401', 'LOGIN', 'com.fastproject.module.system.controller.AuthController', '用户登录', NULL, 'POST', '0:0:0:0:0:0:0:1', 'login', 'arg0=LoginDto(username=admin, password=123456, captchaKey=6e848bb7de59410796590aa8d1d0243a, captcha=5tzp)', NULL, 't', 99, 'LOGIN', '/auth/login');
INSERT INTO "public"."sys_operation_log" VALUES (324366185124728832, 1, '2026-04-28 08:08:44.966097', 0, NULL, '2026-04-28 08:08:44.966097', 'UPDATE', 'com.fastproject.module.system.controller.SysUsersController', '后台用户修改', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=SysUserUpdate(id=311655641745854464, username=admin4, nickname=管理员4, email=null, phone=null, gender=2, status=2, departmentId=null, postId=null, roleIds=null, avatar=null)', NULL, 't', 52, 'BUSINESS', '/sys/users');
INSERT INTO "public"."sys_operation_log" VALUES (324368936130973696, 1, '2026-04-28 08:19:40.857777', 0, NULL, '2026-04-28 08:19:40.857777', 'CREATE', 'com.fastproject.module.system.controller.SysDictTypeController', '添加字典类型', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysDictTypeCreate(name=文件配置类型, type=file_config_type, status=1, remark=)', NULL, 't', 11, 'BUSINESS', '/sys/dict/type');
INSERT INTO "public"."sys_operation_log" VALUES (324369044717309952, 1, '2026-04-28 08:20:06.746819', 0, NULL, '2026-04-28 08:20:06.746819', 'CREATE', 'com.fastproject.module.system.controller.SysDictDataController', '添加字典数据', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysDictDataCreate(sort=0, label=本地, value=localFileStorageStrategy, dictTypeId=324368936110002176, status=0, remark=)', NULL, 't', 9, 'BUSINESS', '/sys/dict/data');
INSERT INTO "public"."sys_operation_log" VALUES (324369112279158784, 1, '2026-04-28 08:20:22.854969', 0, NULL, '2026-04-28 08:20:22.854969', 'CREATE', 'com.fastproject.module.system.controller.SysDictDataController', '添加字典数据', NULL, 'POST', '0:0:0:0:0:0:0:1', 'add', 'arg0=SysDictDataCreate(sort=0, label=阿里云, value=aliyunFileStorageStrategy, dictTypeId=324368936110002176, status=1, remark=)', NULL, 't', 4, 'BUSINESS', '/sys/dict/data');
INSERT INTO "public"."sys_operation_log" VALUES (324369190859444224, 1, '2026-04-28 08:20:41.589255', 0, NULL, '2026-04-28 08:20:41.589255', 'UPDATE', 'com.fastproject.module.system.controller.SysDictDataController', '修改字典数据', NULL, 'PUT', '0:0:0:0:0:0:0:1', 'update', 'arg0=SysDictDataUpdate(id=324369044696338432, sort=0, label=本地, value=localFileStorageStrategy, dictTypeId=324368936110002176, status=1, remark=)', NULL, 't', 8, 'BUSINESS', '/sys/dict/data');
INSERT INTO "public"."sys_operation_log" VALUES (325376427832774656, NULL, '2026-05-01 03:03:05.597489', 0, NULL, '2026-05-01 03:03:05.597489', 'LOGIN', 'com.fastproject.module.system.controller.AuthController', '用户登录', 'com.fastproject.exception.BusinessException: 验证码错误
	at com.fastproject.module.system.service.impl.AuthServiceImpl.validateCaptcha(AuthServiceImpl.java:77)
	at com.fastproject.module.system.service.impl.AuthServiceImpl.login(AuthServiceImpl.java:39)
	at com.fastproject.module.system.controller.AuthController.login(AuthController.java:48)
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:104)
	at java.base/java.lang.reflect.Method.invoke(Method.java:565)
	at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:359)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.invokeJoinpoint(ReflectiveMethodInvocation.java:190)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:158)
	at org.springframework.aop.aspectj.AspectJAfterThrowingAdvice.invoke(AspectJAfterThrowingAdvice.java:63)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:168)
	at org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint.proceed(MethodInvocationProceedingJoinPoint.java:82)
	at com.fastproject.logs.aspect.LogAspect.around(LogAspect.java:93)
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:104)
	at java.base/java.lang.reflect.Method.invoke(Method.java:565)
	at org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethodWithGivenArgs(AbstractAspectJAdvice.java:648)
	at org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethod(AbstractAspectJAdvice.java:630)
	at org.springframework.aop.aspectj.AspectJAroundAdvice.invoke(AspectJAroundAdvice.java:70)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:168)
	at org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint.proceed(MethodInvocationProceedingJoinPoint.java:82)
	at com.fastp... [截断]', 'POST', '192.168.1.198', 'login', 'arg0=LoginDto(username=admin, password=123456R, captchaKey=5bfc442bb60d41e99e05abc998863e3a, captcha=sss)', NULL, 'f', 4, 'LOGIN', '/auth/login');
INSERT INTO "public"."sys_operation_log" VALUES (325377270279704576, NULL, '2026-05-01 03:06:26.452377', 0, NULL, '2026-05-01 03:06:26.452377', 'LOGIN', 'com.fastproject.module.system.controller.AuthController', '用户登录', 'com.fastproject.exception.BusinessException: 用户名或密码错误
	at com.fastproject.module.system.service.impl.AuthServiceImpl.login(AuthServiceImpl.java:51)
	at com.fastproject.module.system.controller.AuthController.login(AuthController.java:48)
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:104)
	at java.base/java.lang.reflect.Method.invoke(Method.java:565)
	at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:359)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.invokeJoinpoint(ReflectiveMethodInvocation.java:190)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:158)
	at org.springframework.aop.aspectj.AspectJAfterThrowingAdvice.invoke(AspectJAfterThrowingAdvice.java:63)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:168)
	at org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint.proceed(MethodInvocationProceedingJoinPoint.java:82)
	at com.fastproject.logs.aspect.LogAspect.around(LogAspect.java:93)
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:104)
	at java.base/java.lang.reflect.Method.invoke(Method.java:565)
	at org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethodWithGivenArgs(AbstractAspectJAdvice.java:648)
	at org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethod(AbstractAspectJAdvice.java:630)
	at org.springframework.aop.aspectj.AspectJAroundAdvice.invoke(AspectJAroundAdvice.java:70)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:168)
	at org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint.proceed(MethodInvocationProceedingJoinPoint.java:82)
	at com.fastproject.idempotent.aspect.IdempotentAspect.around(IdempotentAspect.java:104)
	at java.base/jdk.interna... [截断]', 'POST', '192.168.1.198', 'login', 'arg0=LoginDto(username=admin, password=123456R, captchaKey=75a3d66dd7094c5fa4ed13960d8828be, captcha=A4Hp)', NULL, 'f', 62, 'LOGIN', '/auth/login');
INSERT INTO "public"."sys_operation_log" VALUES (325377322427486208, NULL, '2026-05-01 03:06:38.885272', 0, NULL, '2026-05-01 03:06:38.885272', 'LOGIN', 'com.fastproject.module.system.controller.AuthController', '用户登录', NULL, 'POST', '192.168.1.198', 'login', 'arg0=LoginDto(username=admin, password=123456, captchaKey=c9b6c665413e44cf82e0143440cb8bf4, captcha=pjnu)', NULL, 't', 56, 'LOGIN', '/auth/login');
INSERT INTO "public"."sys_operation_log" VALUES (325377692985856000, NULL, '2026-05-01 03:08:07.233947', 0, NULL, '2026-05-01 03:08:07.233947', 'LOGIN', 'com.fastproject.module.system.controller.AuthController', '用户登录', NULL, 'POST', '192.168.1.198', 'login', 'arg0=LoginDto(username=admin, password=123456, captchaKey=c774353b2f594dceb3089efd1d656afe, captcha=kg58)', NULL, 't', 54, 'LOGIN', '/auth/login');
INSERT INTO "public"."sys_operation_log" VALUES (325377742650609664, NULL, '2026-05-01 03:08:19.074034', 0, NULL, '2026-05-01 03:08:19.074034', 'LOGIN', 'com.fastproject.module.system.controller.AuthController', '用户登录', NULL, 'POST', '192.168.1.200', 'login', 'arg0=LoginDto(username=admin, password=123456, captchaKey=9d6955888c63403e82bedba530da8c94, captcha=myxu)', NULL, 't', 49, 'LOGIN', '/auth/login');
INSERT INTO "public"."sys_operation_log" VALUES (325384175215579136, NULL, '2026-05-01 03:33:52.717695', 0, NULL, '2026-05-01 03:33:52.717695', 'LOGIN', 'com.fastproject.module.system.controller.AuthController', '用户登录', NULL, 'POST', '192.168.1.197', 'login', 'arg0=LoginDto(username=admin, password=123456, captchaKey=ec79840ad52c420a9a6ec7709a2fe08c, captcha=hkr2)', NULL, 't', 54, 'LOGIN', '/auth/login');
INSERT INTO "public"."sys_operation_log" VALUES (325384941955321856, 1, '2026-05-01 03:36:55.522193', 0, NULL, '2026-05-01 03:36:55.522193', 'UPDATE', 'com.fastproject.module.system.controller.SysPermissionsController', '修改权限', NULL, 'PUT', '192.168.1.197', 'update', 'arg0=SysPermissionsUpdate(id=316477320535871488, title=监控管理, code=, url=, type=0, component=, icon=VideoCameraOutlined, status=1, parentId=null, componentName=, sort=3, applicationId=null, applicationCode=)', NULL, 't', 14, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (325385006472105984, 1, '2026-05-01 03:37:10.904417', 0, NULL, '2026-05-01 03:37:10.904417', 'UPDATE', 'com.fastproject.module.system.controller.SysPermissionsController', '修改权限', NULL, 'PUT', '192.168.1.197', 'update', 'arg0=SysPermissionsUpdate(id=318993678729351168, title=限流管理, code=, url=, type=0, component=, icon=ColumnWidthOutlined, status=1, parentId=null, componentName=, sort=4, applicationId=null, applicationCode=)', NULL, 't', 4, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (325385034959818752, 1, '2026-05-01 03:37:17.696937', 0, NULL, '2026-05-01 03:37:17.696937', 'UPDATE', 'com.fastproject.module.system.controller.SysPermissionsController', '修改权限', NULL, 'PUT', '192.168.1.197', 'update', 'arg0=SysPermissionsUpdate(id=322952165469589504, title=权益管理, code=, url=, type=0, component=, icon=ReadOutlined, status=1, parentId=null, componentName=, sort=6, applicationId=null, applicationCode=)', NULL, 't', 4, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (325387072737251328, 1, '2026-05-01 03:45:23.540789', 0, NULL, '2026-05-01 03:45:23.540789', 'UPDATE', 'com.fastproject.module.system.controller.SysPermissionsController', '修改权限', NULL, 'PUT', '192.168.1.197', 'update', 'arg0=SysPermissionsUpdate(id=316477772799283200, title=日志管理, code=admin:logs:operation:page, url=/monitor/operationlog, type=1, component=views/monitor/operationlog/index, icon=ContainerOutlined, status=1, parentId=316477320535871488, componentName=MonitorOperationlog, sort=0, applicationId=null, applicationCode=)', NULL, 't', 4, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (325390914468057088, 1, '2026-05-01 04:00:39.48019', 0, NULL, '2026-05-01 04:00:39.48019', 'UPDATE', 'com.fastproject.module.system.controller.ProfileController', '更新个人信息', NULL, 'PUT', '192.168.1.197', 'updateProfile', 'arg0=UserProfileUpdate(nickname=管理员, phone=18711321234, email=2@qq.com, gender=1, remark=aaa, avatar=325390908830912512)', NULL, 't', 16, 'BUSINESS', '/sys/profile');
INSERT INTO "public"."sys_operation_log" VALUES (325391126611759104, 1, '2026-05-01 04:01:30.059413', 0, NULL, '2026-05-01 04:01:30.059413', 'UPDATE', 'com.fastproject.module.system.controller.ProfileController', '更新个人信息', NULL, 'PUT', '192.168.1.197', 'updateProfile', 'arg0=UserProfileUpdate(nickname=管理员, phone=18711321234, email=2@qq.com, gender=1, remark=aaa, avatar=325391124225200128)', NULL, 't', 6, 'BUSINESS', '/sys/profile');
INSERT INTO "public"."sys_operation_log" VALUES (327518787513487360, NULL, '2026-05-07 00:56:03.948374', 0, NULL, '2026-05-07 00:56:03.948374', 'LOGIN', 'com.fastproject.module.system.controller.AuthController', '用户登录', NULL, 'POST', '127.0.0.1', 'login', 'arg0=LoginDto(username=admin, password=123456, captchaKey=23452bc4599e4755b7430b2a570e95c0, captcha=jg9b)', NULL, 't', 97, 'LOGIN', '/auth/login');
INSERT INTO "public"."sys_operation_log" VALUES (327575307911565312, 1, '2026-05-07 04:40:39.461063', 0, NULL, '2026-05-07 04:40:39.461063', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '127.0.0.1', 'add', 'arg0=SysPermissionsCreate(title=页面配置, code=, url=, type=0, component=, icon=WindowsFilled, status=1, parentId=null, componentName=, sort=0, applicationId=null, applicationCode=)', NULL, 't', 25, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (327575872334860288, 1, '2026-05-07 04:42:54.03036', 0, NULL, '2026-05-07 04:42:54.03036', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '127.0.0.1', 'add', 'arg0=SysPermissionsCreate(title=页面类型, code=admin:page:type:page, url=/page/pagetype, type=1, component=views/page/pagetype/index, icon=, status=1, parentId=327575307823484928, componentName=PagePagetype, sort=0, applicationId=null, applicationCode=)', NULL, 't', 8, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (327576056234119168, 1, '2026-05-07 04:43:37.875882', 0, NULL, '2026-05-07 04:43:37.875882', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '127.0.0.1', 'add', 'arg0=SysPermissionsCreate(title=添加, code=admin:page:type:add, url=, type=2, component=, icon=, status=0, parentId=327575872301305856, componentName=, sort=0, applicationId=null, applicationCode=)', NULL, 't', 3, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (327576056276062208, 1, '2026-05-07 04:43:37.885799', 0, NULL, '2026-05-07 04:43:37.885799', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '127.0.0.1', 'add', 'arg0=SysPermissionsCreate(title=修改, code=admin:page:type:update, url=, type=2, component=, icon=, status=0, parentId=327575872301305856, componentName=, sort=0, applicationId=null, applicationCode=)', NULL, 't', 3, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (327576056326393856, 1, '2026-05-07 04:43:37.897027', 0, NULL, '2026-05-07 04:43:37.897027', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '127.0.0.1', 'add', 'arg0=SysPermissionsCreate(title=删除, code=admin:page:type:delete, url=, type=2, component=, icon=, status=0, parentId=327575872301305856, componentName=, sort=0, applicationId=null, applicationCode=)', NULL, 't', 4, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (327576120369221632, 1, '2026-05-07 04:43:53.166214', 0, NULL, '2026-05-07 04:43:53.166214', 'LOGOUT', 'com.fastproject.module.system.controller.AuthController', '用户退出', NULL, 'POST', '127.0.0.1', 'logout', '{}', NULL, 't', 4, 'LOGIN', '/auth/logout');
INSERT INTO "public"."sys_operation_log" VALUES (327576142187991040, NULL, '2026-05-07 04:43:58.368884', 0, NULL, '2026-05-07 04:43:58.368884', 'LOGIN', 'com.fastproject.module.system.controller.AuthController', '用户登录', 'org.hibernate.LazyInitializationException: Cannot lazily initialize collection of role ''com.fastproject.system.domain.SysUsers.roles'' with key ''1'' (no session)
	at org.hibernate.collection.spi.AbstractPersistentCollection.throwLazyInitializationException(AbstractPersistentCollection.java:649)
	at org.hibernate.collection.spi.AbstractPersistentCollection.withTemporarySessionIfNeeded(AbstractPersistentCollection.java:234)
	at org.hibernate.collection.spi.AbstractPersistentCollection.readSize(AbstractPersistentCollection.java:161)
	at org.hibernate.collection.spi.PersistentSet.size(PersistentSet.java:146)
	at com.fastproject.system.mapper.SysUsersMapperImpl.sysRoleSetToSysRoleVoSet(SysUsersMapperImpl.java:150)
	at com.fastproject.system.mapper.SysUsersMapperImpl.toLoginUser(SysUsersMapperImpl.java:127)
	at com.fastproject.system.service.impl.SysUsersServiceImpl.getLoginByUsername(SysUsersServiceImpl.java:251)
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:104)
	at java.base/java.lang.reflect.Method.invoke(Method.java:565)
	at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:359)
	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:715)
	at com.fastproject.system.service.impl.SysUsersServiceImpl$$SpringCGLIB$$0.getLoginByUsername(<generated>)
	at com.fastproject.module.system.service.impl.AuthServiceImpl.login(AuthServiceImpl.java:45)
	at com.fastproject.module.system.controller.AuthController.login(AuthController.java:48)
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:104)
	at java.base/java.lang.reflect.Method.invoke(Method.java:565)
	at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:359)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.invokeJoinpoint(ReflectiveMethodInvocation.java:190)
	at org.spr... [截断]', 'POST', '127.0.0.1', 'login', 'arg0=LoginDto(username=admin, password=123456, captchaKey=544236286dda416097702350d24bc61e, captcha=guhx)', NULL, 'f', 7, 'LOGIN', '/auth/login');
INSERT INTO "public"."sys_operation_log" VALUES (327576174022758400, NULL, '2026-05-07 04:44:05.958959', 0, NULL, '2026-05-07 04:44:05.958959', 'LOGIN', 'com.fastproject.module.system.controller.AuthController', '用户登录', 'org.hibernate.LazyInitializationException: Cannot lazily initialize collection of role ''com.fastproject.system.domain.SysUsers.roles'' with key ''1'' (no session)
	at org.hibernate.collection.spi.AbstractPersistentCollection.throwLazyInitializationException(AbstractPersistentCollection.java:649)
	at org.hibernate.collection.spi.AbstractPersistentCollection.withTemporarySessionIfNeeded(AbstractPersistentCollection.java:234)
	at org.hibernate.collection.spi.AbstractPersistentCollection.readSize(AbstractPersistentCollection.java:161)
	at org.hibernate.collection.spi.PersistentSet.size(PersistentSet.java:146)
	at com.fastproject.system.mapper.SysUsersMapperImpl.sysRoleSetToSysRoleVoSet(SysUsersMapperImpl.java:150)
	at com.fastproject.system.mapper.SysUsersMapperImpl.toLoginUser(SysUsersMapperImpl.java:127)
	at com.fastproject.system.service.impl.SysUsersServiceImpl.getLoginByUsername(SysUsersServiceImpl.java:251)
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:104)
	at java.base/java.lang.reflect.Method.invoke(Method.java:565)
	at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:359)
	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:715)
	at com.fastproject.system.service.impl.SysUsersServiceImpl$$SpringCGLIB$$0.getLoginByUsername(<generated>)
	at com.fastproject.module.system.service.impl.AuthServiceImpl.login(AuthServiceImpl.java:45)
	at com.fastproject.module.system.controller.AuthController.login(AuthController.java:48)
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:104)
	at java.base/java.lang.reflect.Method.invoke(Method.java:565)
	at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:359)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.invokeJoinpoint(ReflectiveMethodInvocation.java:190)
	at org.spr... [截断]', 'POST', '127.0.0.1', 'login', 'arg0=LoginDto(username=admin, password=123456, captchaKey=da2df162a5d2413db704c14839f5a36e, captcha=a33v)', NULL, 'f', 5, 'LOGIN', '/auth/login');
INSERT INTO "public"."sys_operation_log" VALUES (327576574222274560, NULL, '2026-05-07 04:45:41.373344', 0, NULL, '2026-05-07 04:45:41.373344', 'LOGIN', 'com.fastproject.module.system.controller.AuthController', '用户登录', 'com.fastproject.exception.BusinessException: 验证码错误
	at com.fastproject.module.system.service.impl.AuthServiceImpl.validateCaptcha(AuthServiceImpl.java:95)
	at com.fastproject.module.system.service.impl.AuthServiceImpl.login(AuthServiceImpl.java:43)
	at com.fastproject.module.system.controller.AuthController.login(AuthController.java:48)
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:104)
	at java.base/java.lang.reflect.Method.invoke(Method.java:565)
	at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:359)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.invokeJoinpoint(ReflectiveMethodInvocation.java:190)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:158)
	at org.springframework.aop.aspectj.AspectJAfterThrowingAdvice.invoke(AspectJAfterThrowingAdvice.java:63)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:168)
	at org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint.proceed(MethodInvocationProceedingJoinPoint.java:82)
	at com.fastproject.logs.aspect.LogAspect.around(LogAspect.java:93)
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:104)
	at java.base/java.lang.reflect.Method.invoke(Method.java:565)
	at org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethodWithGivenArgs(AbstractAspectJAdvice.java:648)
	at org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethod(AbstractAspectJAdvice.java:630)
	at org.springframework.aop.aspectj.AspectJAroundAdvice.invoke(AspectJAroundAdvice.java:70)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:168)
	at org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint.proceed(MethodInvocationProceedingJoinPoint.java:82)
	at com.fastp... [截断]', 'POST', '127.0.0.1', 'login', 'arg0=LoginDto(username=admin, password=123456, captchaKey=425ecea6d3c041baa2daf0e813fb8112, captcha=xuey)', NULL, 'f', 4, 'LOGIN', '/auth/login');
INSERT INTO "public"."sys_operation_log" VALUES (327576598305968128, NULL, '2026-05-07 04:45:47.115163', 0, NULL, '2026-05-07 04:45:47.115163', 'LOGIN', 'com.fastproject.module.system.controller.AuthController', '用户登录', 'org.hibernate.LazyInitializationException: Cannot lazily initialize collection of role ''com.fastproject.system.domain.SysUsers.roles'' with key ''1'' (no session)
	at org.hibernate.collection.spi.AbstractPersistentCollection.throwLazyInitializationException(AbstractPersistentCollection.java:649)
	at org.hibernate.collection.spi.AbstractPersistentCollection.withTemporarySessionIfNeeded(AbstractPersistentCollection.java:234)
	at org.hibernate.collection.spi.AbstractPersistentCollection.readSize(AbstractPersistentCollection.java:161)
	at org.hibernate.collection.spi.PersistentSet.size(PersistentSet.java:146)
	at com.fastproject.system.mapper.SysUsersMapperImpl.sysRoleSetToSysRoleVoSet(SysUsersMapperImpl.java:150)
	at com.fastproject.system.mapper.SysUsersMapperImpl.toLoginUser(SysUsersMapperImpl.java:127)
	at com.fastproject.system.service.impl.SysUsersServiceImpl.getLoginByUsername(SysUsersServiceImpl.java:251)
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:104)
	at java.base/java.lang.reflect.Method.invoke(Method.java:565)
	at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:359)
	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:715)
	at com.fastproject.system.service.impl.SysUsersServiceImpl$$SpringCGLIB$$0.getLoginByUsername(<generated>)
	at com.fastproject.module.system.service.impl.AuthServiceImpl.login(AuthServiceImpl.java:45)
	at com.fastproject.module.system.controller.AuthController.login(AuthController.java:48)
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:104)
	at java.base/java.lang.reflect.Method.invoke(Method.java:565)
	at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:359)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.invokeJoinpoint(ReflectiveMethodInvocation.java:190)
	at org.spr... [截断]', 'POST', '127.0.0.1', 'login', 'arg0=LoginDto(username=admin, password=123456, captchaKey=6cdd735cf5294812980291f618335e87, captcha=kdws)', NULL, 'f', 16, 'LOGIN', '/auth/login');
INSERT INTO "public"."sys_operation_log" VALUES (327576962983923712, NULL, '2026-05-07 04:47:14.061221', 0, NULL, '2026-05-07 04:47:14.061221', 'LOGIN', 'com.fastproject.module.system.controller.AuthController', '用户登录', 'com.fastproject.exception.BusinessException: 验证码错误
	at com.fastproject.module.system.service.impl.AuthServiceImpl.validateCaptcha(AuthServiceImpl.java:95)
	at com.fastproject.module.system.service.impl.AuthServiceImpl.login(AuthServiceImpl.java:43)
	at com.fastproject.module.system.controller.AuthController.login(AuthController.java:48)
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:104)
	at java.base/java.lang.reflect.Method.invoke(Method.java:565)
	at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:359)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.invokeJoinpoint(ReflectiveMethodInvocation.java:190)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:158)
	at org.springframework.aop.aspectj.AspectJAfterThrowingAdvice.invoke(AspectJAfterThrowingAdvice.java:63)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:168)
	at org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint.proceed(MethodInvocationProceedingJoinPoint.java:82)
	at com.fastproject.logs.aspect.LogAspect.around(LogAspect.java:93)
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:104)
	at java.base/java.lang.reflect.Method.invoke(Method.java:565)
	at org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethodWithGivenArgs(AbstractAspectJAdvice.java:648)
	at org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethod(AbstractAspectJAdvice.java:630)
	at org.springframework.aop.aspectj.AspectJAroundAdvice.invoke(AspectJAroundAdvice.java:70)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:168)
	at org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint.proceed(MethodInvocationProceedingJoinPoint.java:82)
	at com.fastp... [截断]', 'POST', '127.0.0.1', 'login', 'arg0=LoginDto(username=admin, password=123456, captchaKey=7c9b29cc14ec498bb5b5b83a1de39532, captcha=xpzg)', NULL, 'f', 5, 'LOGIN', '/auth/login');
INSERT INTO "public"."sys_operation_log" VALUES (327576982336442368, NULL, '2026-05-07 04:47:18.675661', 0, NULL, '2026-05-07 04:47:18.675661', 'LOGIN', 'com.fastproject.module.system.controller.AuthController', '用户登录', 'org.hibernate.LazyInitializationException: Cannot lazily initialize collection of role ''com.fastproject.system.domain.SysUsers.roles'' with key ''1'' (no session)
	at org.hibernate.collection.spi.AbstractPersistentCollection.throwLazyInitializationException(AbstractPersistentCollection.java:649)
	at org.hibernate.collection.spi.AbstractPersistentCollection.withTemporarySessionIfNeeded(AbstractPersistentCollection.java:234)
	at org.hibernate.collection.spi.AbstractPersistentCollection.readSize(AbstractPersistentCollection.java:161)
	at org.hibernate.collection.spi.PersistentSet.size(PersistentSet.java:146)
	at com.fastproject.system.mapper.SysUsersMapperImpl.sysRoleSetToSysRoleVoSet(SysUsersMapperImpl.java:151)
	at com.fastproject.system.mapper.SysUsersMapperImpl.toLoginUser(SysUsersMapperImpl.java:127)
	at com.fastproject.system.service.impl.SysUsersServiceImpl.getLoginByUsername(SysUsersServiceImpl.java:252)
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:104)
	at java.base/java.lang.reflect.Method.invoke(Method.java:565)
	at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:359)
	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:715)
	at com.fastproject.system.service.impl.SysUsersServiceImpl$$SpringCGLIB$$0.getLoginByUsername(<generated>)
	at com.fastproject.module.system.service.impl.AuthServiceImpl.login(AuthServiceImpl.java:45)
	at com.fastproject.module.system.controller.AuthController.login(AuthController.java:48)
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:104)
	at java.base/java.lang.reflect.Method.invoke(Method.java:565)
	at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:359)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.invokeJoinpoint(ReflectiveMethodInvocation.java:190)
	at org.spr... [截断]', 'POST', '127.0.0.1', 'login', 'arg0=LoginDto(username=admin, password=123456, captchaKey=13e8ab21a1f34a27ba7d98864d3c40e3, captcha=rwds)', NULL, 'f', 18, 'LOGIN', '/auth/login');
INSERT INTO "public"."sys_operation_log" VALUES (327577295869054976, NULL, '2026-05-07 04:48:33.427068', 0, NULL, '2026-05-07 04:48:33.427068', 'LOGIN', 'com.fastproject.module.system.controller.AuthController', '用户登录', 'com.fastproject.exception.BusinessException: 验证码错误
	at com.fastproject.module.system.service.impl.AuthServiceImpl.validateCaptcha(AuthServiceImpl.java:95)
	at com.fastproject.module.system.service.impl.AuthServiceImpl.login(AuthServiceImpl.java:43)
	at com.fastproject.module.system.controller.AuthController.login(AuthController.java:48)
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:104)
	at java.base/java.lang.reflect.Method.invoke(Method.java:565)
	at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:359)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.invokeJoinpoint(ReflectiveMethodInvocation.java:190)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:158)
	at org.springframework.aop.aspectj.AspectJAfterThrowingAdvice.invoke(AspectJAfterThrowingAdvice.java:63)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:168)
	at org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint.proceed(MethodInvocationProceedingJoinPoint.java:82)
	at com.fastproject.logs.aspect.LogAspect.around(LogAspect.java:93)
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:104)
	at java.base/java.lang.reflect.Method.invoke(Method.java:565)
	at org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethodWithGivenArgs(AbstractAspectJAdvice.java:648)
	at org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethod(AbstractAspectJAdvice.java:630)
	at org.springframework.aop.aspectj.AspectJAroundAdvice.invoke(AspectJAroundAdvice.java:70)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:168)
	at org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint.proceed(MethodInvocationProceedingJoinPoint.java:82)
	at com.fastp... [截断]', 'POST', '127.0.0.1', 'login', 'arg0=LoginDto(username=admin, password=123456, captchaKey=6401a08d62184983aee63f94c0e282ac, captcha=b3ou)', NULL, 'f', 5, 'LOGIN', '/auth/login');
INSERT INTO "public"."sys_operation_log" VALUES (327577314311409664, NULL, '2026-05-07 04:48:37.824701', 0, NULL, '2026-05-07 04:48:37.824701', 'LOGIN', 'com.fastproject.module.system.controller.AuthController', '用户登录', 'org.hibernate.LazyInitializationException: Cannot lazily initialize collection of role ''com.fastproject.system.domain.SysUsers.roles'' with key ''1'' (no session)
	at org.hibernate.collection.spi.AbstractPersistentCollection.throwLazyInitializationException(AbstractPersistentCollection.java:649)
	at org.hibernate.collection.spi.AbstractPersistentCollection.withTemporarySessionIfNeeded(AbstractPersistentCollection.java:234)
	at org.hibernate.collection.spi.AbstractPersistentCollection.readSize(AbstractPersistentCollection.java:161)
	at org.hibernate.collection.spi.PersistentSet.size(PersistentSet.java:146)
	at com.fastproject.system.mapper.SysUsersMapperImpl.sysRoleSetToSysRoleVoSet(SysUsersMapperImpl.java:151)
	at com.fastproject.system.mapper.SysUsersMapperImpl.toLoginUser(SysUsersMapperImpl.java:127)
	at com.fastproject.system.service.impl.SysUsersServiceImpl.getLoginByUsername(SysUsersServiceImpl.java:252)
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:104)
	at java.base/java.lang.reflect.Method.invoke(Method.java:565)
	at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:359)
	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:715)
	at com.fastproject.system.service.impl.SysUsersServiceImpl$$SpringCGLIB$$0.getLoginByUsername(<generated>)
	at com.fastproject.module.system.service.impl.AuthServiceImpl.login(AuthServiceImpl.java:45)
	at com.fastproject.module.system.controller.AuthController.login(AuthController.java:48)
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:104)
	at java.base/java.lang.reflect.Method.invoke(Method.java:565)
	at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:359)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.invokeJoinpoint(ReflectiveMethodInvocation.java:190)
	at org.spr... [截断]', 'POST', '127.0.0.1', 'login', 'arg0=LoginDto(username=admin, password=123456, captchaKey=b97978df282246acb30b6b78638e6b5f, captcha=dejd)', NULL, 'f', 15, 'LOGIN', '/auth/login');
INSERT INTO "public"."sys_operation_log" VALUES (327577876037767168, NULL, '2026-05-07 04:50:51.750451', 0, NULL, '2026-05-07 04:50:51.750451', 'LOGIN', 'com.fastproject.module.system.controller.AuthController', '用户登录', 'com.fastproject.exception.BusinessException: 验证码错误
	at com.fastproject.module.system.service.impl.AuthServiceImpl.validateCaptcha(AuthServiceImpl.java:95)
	at com.fastproject.module.system.service.impl.AuthServiceImpl.login(AuthServiceImpl.java:43)
	at com.fastproject.module.system.controller.AuthController.login(AuthController.java:48)
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:104)
	at java.base/java.lang.reflect.Method.invoke(Method.java:565)
	at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:359)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.invokeJoinpoint(ReflectiveMethodInvocation.java:190)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:158)
	at org.springframework.aop.aspectj.AspectJAfterThrowingAdvice.invoke(AspectJAfterThrowingAdvice.java:63)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:168)
	at org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint.proceed(MethodInvocationProceedingJoinPoint.java:82)
	at com.fastproject.logs.aspect.LogAspect.around(LogAspect.java:93)
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:104)
	at java.base/java.lang.reflect.Method.invoke(Method.java:565)
	at org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethodWithGivenArgs(AbstractAspectJAdvice.java:648)
	at org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethod(AbstractAspectJAdvice.java:630)
	at org.springframework.aop.aspectj.AspectJAroundAdvice.invoke(AspectJAroundAdvice.java:70)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:168)
	at org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint.proceed(MethodInvocationProceedingJoinPoint.java:82)
	at com.fastp... [截断]', 'POST', '127.0.0.1', 'login', 'arg0=LoginDto(username=admin, password=123456, captchaKey=d6164dca1770444b83db299ad459c8f2, captcha=nsve)', NULL, 'f', 4, 'LOGIN', '/auth/login');
INSERT INTO "public"."sys_operation_log" VALUES (327577894610145280, NULL, '2026-05-07 04:50:56.178281', 0, NULL, '2026-05-07 04:50:56.178281', 'LOGIN', 'com.fastproject.module.system.controller.AuthController', '用户登录', NULL, 'POST', '127.0.0.1', 'login', 'arg0=LoginDto(username=admin, password=123456, captchaKey=6e38cdc43e3141c1955a2230a99b9794, captcha=ufhs)', NULL, 't', 123, 'LOGIN', '/auth/login');
INSERT INTO "public"."sys_operation_log" VALUES (327580869353148416, 1, '2026-05-07 05:02:45.412887', 0, NULL, '2026-05-07 05:02:45.412887', 'LOGOUT', 'com.fastproject.module.system.controller.AuthController', '用户退出', NULL, 'POST', '127.0.0.1', 'logout', '{}', NULL, 't', 4, 'LOGIN', '/auth/logout');
INSERT INTO "public"."sys_operation_log" VALUES (327580893873049600, NULL, '2026-05-07 05:02:51.258705', 0, NULL, '2026-05-07 05:02:51.258705', 'LOGIN', 'com.fastproject.module.system.controller.AuthController', '用户登录', NULL, 'POST', '127.0.0.1', 'login', 'arg0=LoginDto(username=admin, password=123456, captchaKey=1fc0d16b09194bbba914f12b9167736b, captcha=ny3k)', NULL, 't', 79, 'LOGIN', '/auth/login');
INSERT INTO "public"."sys_operation_log" VALUES (327582763937042432, 1, '2026-05-07 05:10:17.116065', 0, NULL, '2026-05-07 05:10:17.116065', 'CREATE', 'com.fastproject.module.page.controller.PageTypeController', '添加页面类型', NULL, 'POST', '127.0.0.1', 'add', 'arg0=PageTypeCreate(title=UI, code=ui, status=1)', NULL, 't', 35, 'BUSINESS', '/page/type');
INSERT INTO "public"."sys_operation_log" VALUES (327582794454798336, 1, '2026-05-07 05:10:24.392828', 0, NULL, '2026-05-07 05:10:24.392828', 'UPDATE', 'com.fastproject.module.page.controller.PageTypeController', '修改页面类型', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=PageTypeUpdate(id=327582763836379136, title=UI, code=ui, status=1)', NULL, 't', 8, 'BUSINESS', '/page/type');
INSERT INTO "public"."sys_operation_log" VALUES (327583550079635456, 1, '2026-05-07 05:13:24.547972', 0, NULL, '2026-05-07 05:13:24.547972', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '127.0.0.1', 'add', 'arg0=SysPermissionsCreate(title=组件, code=admin:page:component:page, url=/page/pagecomponent, type=1, component=views/page/pagecomponent/index, icon=, status=1, parentId=327575307823484928, componentName=PagePagecomponent, sort=0, applicationId=null, applicationCode=)', NULL, 't', 17, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (327583580035354624, 1, '2026-05-07 05:13:31.689857', 0, NULL, '2026-05-07 05:13:31.689857', 'UPDATE', 'com.fastproject.module.system.controller.SysPermissionsController', '修改权限', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=SysPermissionsUpdate(id=327583550016720896, title=组件, code=admin:page:component:page, url=/page/pagecomponent, type=1, component=views/page/pagecomponent/index, icon=, status=1, parentId=327575307823484928, componentName=PagePagecomponent, sort=1, applicationId=null, applicationCode=)', NULL, 't', 13, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (327583747312586752, 1, '2026-05-07 05:14:11.571064', 0, NULL, '2026-05-07 05:14:11.571064', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '127.0.0.1', 'add', 'arg0=SysPermissionsCreate(title=添加, code=admin:page:component:add, url=, type=2, component=, icon=, status=0, parentId=327583550016720896, componentName=, sort=0, applicationId=null, applicationCode=)', NULL, 't', 5, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (327583747341946880, 1, '2026-05-07 05:14:11.578798', 0, NULL, '2026-05-07 05:14:11.578798', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '127.0.0.1', 'add', 'arg0=SysPermissionsCreate(title=修改, code=admin:page:component:update, url=, type=2, component=, icon=, status=0, parentId=327583550016720896, componentName=, sort=0, applicationId=null, applicationCode=)', NULL, 't', 2, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (327583747379695616, 1, '2026-05-07 05:14:11.587228', 0, NULL, '2026-05-07 05:14:11.587228', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '127.0.0.1', 'add', 'arg0=SysPermissionsCreate(title=删除, code=admin:page:component:delete, url=, type=2, component=, icon=, status=0, parentId=327583550016720896, componentName=, sort=0, applicationId=null, applicationCode=)', NULL, 't', 4, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (327584306845323264, 1, '2026-05-07 05:16:24.9746', 0, NULL, '2026-05-07 05:16:24.9746', 'UPDATE', 'com.fastproject.module.page.controller.PageTypeController', '修改页面类型', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=PageTypeUpdate(id=327582763836379136, title=自定义UI, code=ui, status=1)', NULL, 't', 28, 'BUSINESS', '/page/type');
INSERT INTO "public"."sys_operation_log" VALUES (327584417491062784, 1, '2026-05-07 05:16:51.354017', 0, NULL, '2026-05-07 05:16:51.354017', 'CREATE', 'com.fastproject.module.page.controller.PageComponentController', '添加页面组件', NULL, 'POST', '127.0.0.1', 'add', 'arg0=PageComponentCreate(title=测试1, icon=, typeId=null, code=test1, status=1, sort=0)', NULL, 't', 8, 'BUSINESS', '/page/component');
INSERT INTO "public"."sys_operation_log" VALUES (327584517378412544, 1, '2026-05-07 05:17:15.169871', 0, NULL, '2026-05-07 05:17:15.169871', 'UPDATE', 'com.fastproject.module.page.controller.PageComponentController', '修改页面组件', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=PageComponentUpdate(id=327584417465896960, title=测试1, icon=, typeId=null, code=test1, status=1, sort=0)', NULL, 't', 5, 'BUSINESS', '/page/component');
INSERT INTO "public"."sys_operation_log" VALUES (327584813580161024, 1, '2026-05-07 05:18:25.789083', 0, NULL, '2026-05-07 05:18:25.789083', 'UPDATE', 'com.fastproject.module.page.controller.PageComponentController', '修改页面组件', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=PageComponentUpdate(id=327584417465896960, title=测试1, icon=, typeId=null, code=test1, status=1, sort=0)', NULL, 't', 3, 'BUSINESS', '/page/component');
INSERT INTO "public"."sys_operation_log" VALUES (327593930931703808, 1, '2026-05-07 05:54:39.535673', 0, NULL, '2026-05-07 05:54:39.535673', 'UPDATE', 'com.fastproject.module.page.controller.PageComponentController', '修改页面组件', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=PageComponentUpdate(id=327584417465896960, title=测试1, icon=, typeId=327582763836379136, code=test1, status=1, sort=0)', NULL, 't', 13, 'BUSINESS', '/page/component');
INSERT INTO "public"."sys_operation_log" VALUES (327594680051175424, 1, '2026-05-07 05:57:38.139344', 0, NULL, '2026-05-07 05:57:38.139344', 'UPDATE', 'com.fastproject.module.page.controller.PageComponentController', '修改页面组件', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=PageComponentUpdate(id=327584417465896960, title=测试1, icon=, typeId=327582763836379136, code=test1, status=1, sort=0)', NULL, 't', 18, 'BUSINESS', '/page/component');
INSERT INTO "public"."sys_operation_log" VALUES (327594720958222336, 1, '2026-05-07 05:57:47.892473', 0, NULL, '2026-05-07 05:57:47.892473', 'UPDATE', 'com.fastproject.module.page.controller.PageComponentController', '修改页面组件', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=PageComponentUpdate(id=327584417465896960, title=测试1, icon=, typeId=327582763836379136, code=test1, status=1, sort=0)', NULL, 't', 6, 'BUSINESS', '/page/component');
INSERT INTO "public"."sys_operation_log" VALUES (327612413232943104, 1, '2026-05-07 07:08:06.059613', 0, NULL, '2026-05-07 07:08:06.059613', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '127.0.0.1', 'add', 'arg0=SysPermissionsCreate(title=页面配置, code=admin:page:application:page, url=/page/pageapplication, type=1, component=views/page/pageapplication/index, icon=, status=1, parentId=327575307823484928, componentName=PagePageapplication, sort=3, applicationId=null, applicationCode=)', NULL, 't', 15, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (327612559047921664, 1, '2026-05-07 07:08:40.824165', 0, NULL, '2026-05-07 07:08:40.824165', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '127.0.0.1', 'add', 'arg0=SysPermissionsCreate(title=添加, code=admin:page:application:add, url=, type=2, component=, icon=, status=0, parentId=327612413174222848, componentName=, sort=0, applicationId=null, applicationCode=)', NULL, 't', 4, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (327612559077281792, 1, '2026-05-07 07:08:40.8319', 0, NULL, '2026-05-07 07:08:40.8319', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '127.0.0.1', 'add', 'arg0=SysPermissionsCreate(title=修改, code=admin:page:application:update, url=, type=2, component=, icon=, status=0, parentId=327612413174222848, componentName=, sort=0, applicationId=null, applicationCode=)', NULL, 't', 2, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (327612559102447616, 1, '2026-05-07 07:08:40.837675', 0, NULL, '2026-05-07 07:08:40.837675', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '127.0.0.1', 'add', 'arg0=SysPermissionsCreate(title=删除, code=admin:page:application:delete, url=, type=2, component=, icon=, status=0, parentId=327612413174222848, componentName=, sort=0, applicationId=null, applicationCode=)', NULL, 't', 2, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (327622567336742912, 1, '2026-05-07 07:48:26.986637', 0, NULL, '2026-05-07 07:48:26.986637', 'CREATE', 'com.fastproject.module.page.controller.PageApplicationController', '添加页面应用', NULL, 'POST', '127.0.0.1', 'add', 'arg0=PageApplicationCreate(title=APPUL, code=app_web, status=0, icon=, typeId=327582763836379136)', NULL, 't', 15, 'BUSINESS', '/page/application');
INSERT INTO "public"."sys_operation_log" VALUES (327622916789374976, 1, '2026-05-07 07:49:50.303027', 0, NULL, '2026-05-07 07:49:50.303027', 'UPDATE', 'com.fastproject.module.page.controller.PageApplicationController', '修改页面应用', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=PageApplicationUpdate(id=327622567294799872, title=APPUL, code=app_web, status=1, icon=, typeId=327582763836379136)', NULL, 't', 47, 'BUSINESS', '/page/application');
INSERT INTO "public"."sys_operation_log" VALUES (327635319274999808, 1, '2026-05-07 08:39:07.285211', 0, NULL, '2026-05-07 08:39:07.285211', 'UPDATE', 'com.fastproject.module.page.controller.PageApplicationController', '修改页面应用', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=PageApplicationUpdate(id=327622567294799872, title=APPUL, code=app_web, status=1, icon=http://127.0.0.1/file/2026/05/07/9e6a165c1ef2e30fa809bb7525420059.png, typeId=327582763836379136)', NULL, 't', 11, 'BUSINESS', '/page/application');
INSERT INTO "public"."sys_operation_log" VALUES (327635534786727936, 1, '2026-05-07 08:39:58.667841', 0, NULL, '2026-05-07 08:39:58.667841', 'UPDATE', 'com.fastproject.module.page.controller.PageApplicationController', '修改页面应用', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=PageApplicationUpdate(id=327622567294799872, title=APPUL, code=app_web, status=1, icon=327635300161556480, typeId=327582763836379136)', NULL, 't', 11, 'BUSINESS', '/page/application');
INSERT INTO "public"."sys_operation_log" VALUES (327637926844436480, 1, '2026-05-07 08:49:28.97871', 0, NULL, '2026-05-07 08:49:28.97871', 'UPDATE', 'com.fastproject.module.page.controller.PageApplicationController', '修改页面应用', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=PageApplicationUpdate(id=327622567294799872, title=APPUL, code=app_web, status=1, icon=http://127.0.0.1/file/2026/05/07/9e6a165c1ef2e30fa809bb7525420059.png, typeId=327582763836379136)', NULL, 't', 41, 'BUSINESS', '/page/application');
INSERT INTO "public"."sys_operation_log" VALUES (327638514109911040, 1, '2026-05-07 08:51:48.993978', 0, NULL, '2026-05-07 08:51:48.993978', 'UPDATE', 'com.fastproject.module.page.controller.PageApplicationController', '修改页面应用', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=PageApplicationUpdate(id=327622567294799872, title=APPUL, code=app_web, status=1, icon=327635300161556480, typeId=327582763836379136)', NULL, 't', 30, 'BUSINESS', '/page/application');
INSERT INTO "public"."sys_operation_log" VALUES (327639968786812928, 1, '2026-05-07 08:57:35.815703', 0, NULL, '2026-05-07 08:57:35.815703', 'UPDATE', 'com.fastproject.module.page.controller.PageApplicationController', '修改页面应用', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=PageApplicationUpdate(id=327622567294799872, title=APPUL, code=app_web, status=1, icon=327635300161556480, typeId=327582763836379136)', NULL, 't', 4, 'BUSINESS', '/page/application');
INSERT INTO "public"."sys_operation_log" VALUES (327640609064095744, 1, '2026-05-07 09:00:08.469229', 0, NULL, '2026-05-07 09:00:08.469229', 'UPDATE', 'com.fastproject.module.page.controller.PageApplicationController', '修改页面应用', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=PageApplicationUpdate(id=327622567294799872, title=APPUL, code=app_web, status=1, icon=327635300161556480, typeId=327582763836379136)', NULL, 't', 3, 'BUSINESS', '/page/application');
INSERT INTO "public"."sys_operation_log" VALUES (327640621844140032, 1, '2026-05-07 09:00:11.516428', 0, NULL, '2026-05-07 09:00:11.516428', 'UPDATE', 'com.fastproject.module.page.controller.PageApplicationController', '修改页面应用', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=PageApplicationUpdate(id=327622567294799872, title=APPUL, code=app_web, status=1, icon=327635300161556480, typeId=327582763836379136)', NULL, 't', 6, 'BUSINESS', '/page/application');
INSERT INTO "public"."sys_operation_log" VALUES (327640699463929856, 1, '2026-05-07 09:00:30.022807', 0, NULL, '2026-05-07 09:00:30.022807', 'UPDATE', 'com.fastproject.module.page.controller.PageApplicationController', '修改页面应用', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=PageApplicationUpdate(id=327622567294799872, title=APPUL, code=app_web, status=1, icon=327640694992801792, typeId=327582763836379136)', NULL, 't', 7, 'BUSINESS', '/page/application');
INSERT INTO "public"."sys_operation_log" VALUES (327641615734804480, 1, '2026-05-07 09:04:08.478334', 0, NULL, '2026-05-07 09:04:08.478334', 'UPDATE', 'com.fastproject.module.page.controller.PageApplicationController', '修改页面应用', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=PageApplicationUpdate(id=327622567294799872, title=APPUL, code=app_web, status=1, icon=327640694992801792, typeId=327582763836379136)', NULL, 't', 5, 'BUSINESS', '/page/application');
INSERT INTO "public"."sys_operation_log" VALUES (327641877719420928, 1, '2026-05-07 09:05:10.940021', 0, NULL, '2026-05-07 09:05:10.940021', 'UPDATE', 'com.fastproject.module.page.controller.PageApplicationController', '修改页面应用', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=PageApplicationUpdate(id=327622567294799872, title=APPUL, code=app_web, status=1, icon=327640694992801792, typeId=327582763836379136)', NULL, 't', 5, 'BUSINESS', '/page/application');
INSERT INTO "public"."sys_operation_log" VALUES (327666211926183936, 1, '2026-05-07 10:41:52.6678', 0, NULL, '2026-05-07 10:41:52.6678', 'UPDATE', 'com.fastproject.module.page.controller.PageApplicationController', '修改页面应用', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=PageApplicationUpdate(id=327622567294799872, title=APPUL, code=app_web, status=2, icon=327640694992801792, typeId=327582763836379136)', NULL, 't', 13, 'BUSINESS', '/page/application');
INSERT INTO "public"."sys_operation_log" VALUES (327666214862196736, 1, '2026-05-07 10:41:53.367523', 0, NULL, '2026-05-07 10:41:53.367523', 'UPDATE', 'com.fastproject.module.page.controller.PageApplicationController', '修改页面应用', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=PageApplicationUpdate(id=327622567294799872, title=APPUL, code=app_web, status=1, icon=327640694992801792, typeId=327582763836379136)', NULL, 't', 7, 'BUSINESS', '/page/application');
INSERT INTO "public"."sys_operation_log" VALUES (327668371711725568, 1, '2026-05-07 10:50:27.600516', 0, NULL, '2026-05-07 10:50:27.600516', 'CREATE', 'com.fastproject.module.system.controller.SysPermissionsController', '添加权限', NULL, 'POST', '127.0.0.1', 'add', 'arg0=SysPermissionsCreate(title=页面配置, code=admin:page:application:config, url=, type=2, component=, icon=, status=1, parentId=327612413174222848, componentName=, sort=0, applicationId=null, applicationCode=)', NULL, 't', 6, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (327669491045634048, 1, '2026-05-07 10:54:54.470668', 0, NULL, '2026-05-07 10:54:54.470668', 'UPDATE', 'com.fastproject.module.system.controller.SysPermissionsController', '修改权限', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=SysPermissionsUpdate(id=327612413174222848, title=应用, code=admin:page:application:page, url=/page/pageapplication, type=1, component=views/page/pageapplication/index, icon=, status=1, parentId=327575307823484928, componentName=PagePageapplication, sort=3, applicationId=null, applicationCode=)', NULL, 't', 10, 'BUSINESS', '/sys/permissions');
INSERT INTO "public"."sys_operation_log" VALUES (327676928301600768, 1, '2026-05-07 11:24:27.650655', 0, NULL, '2026-05-07 11:24:27.650655', 'UPDATE', 'com.fastproject.module.page.controller.PageComponentController', '修改页面组件', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=PageComponentUpdate(id=327584417465896960, title=测试1, icon=327640694992801792, typeId=327582763836379136, code=test1, status=1, sort=0)', NULL, 't', 13, 'BUSINESS', '/page/component');
INSERT INTO "public"."sys_operation_log" VALUES (327982097253928960, 1, '2026-05-08 07:37:05.598853', 0, NULL, '2026-05-08 07:37:05.598853', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '新增页面', 'com.fastproject.exception.BusinessException: 应用不存在
	at com.fastproject.page.service.impl.PageConfigServiceImpl.lambda$save$0(PageConfigServiceImpl.java:43)
	at java.base/java.util.Optional.orElseThrow(Optional.java:403)
	at com.fastproject.page.service.impl.PageConfigServiceImpl.save(PageConfigServiceImpl.java:43)
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:104)
	at java.base/java.lang.reflect.Method.invoke(Method.java:565)
	at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:359)
	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:715)
	at com.fastproject.page.service.impl.PageConfigServiceImpl$$SpringCGLIB$$0.save(<generated>)
	at com.fastproject.module.page.controller.PageConfigController.save(PageConfigController.java:55)
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:104)
	at java.base/java.lang.reflect.Method.invoke(Method.java:565)
	at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:359)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.invokeJoinpoint(ReflectiveMethodInvocation.java:190)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:158)
	at org.springframework.aop.aspectj.AspectJAfterThrowingAdvice.invoke(AspectJAfterThrowingAdvice.java:63)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:168)
	at org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint.proceed(MethodInvocationProceedingJoinPoint.java:82)
	at com.fastproject.logs.aspect.LogAspect.around(LogAspect.java:93)
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:104)
	at java.base/java.lang.reflect.Method.invoke(Method.java:565)
	at org.springframework.... [截断]', 'POST', '127.0.0.1', 'save', 'arg0=PageConfigCreate(pathUrl=/home, config=null, status=1, version=1.0.0, applicationId=327622567294799900)', NULL, 'f', 14, 'BUSINESS', '/page/config');
INSERT INTO "public"."sys_operation_log" VALUES (327982129923362816, 1, '2026-05-08 07:37:13.38758', 0, NULL, '2026-05-08 07:37:13.38758', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '新增页面', 'com.fastproject.exception.BusinessException: 应用不存在
	at com.fastproject.page.service.impl.PageConfigServiceImpl.lambda$save$0(PageConfigServiceImpl.java:43)
	at java.base/java.util.Optional.orElseThrow(Optional.java:403)
	at com.fastproject.page.service.impl.PageConfigServiceImpl.save(PageConfigServiceImpl.java:43)
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:104)
	at java.base/java.lang.reflect.Method.invoke(Method.java:565)
	at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:359)
	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:715)
	at com.fastproject.page.service.impl.PageConfigServiceImpl$$SpringCGLIB$$0.save(<generated>)
	at com.fastproject.module.page.controller.PageConfigController.save(PageConfigController.java:55)
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:104)
	at java.base/java.lang.reflect.Method.invoke(Method.java:565)
	at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:359)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.invokeJoinpoint(ReflectiveMethodInvocation.java:190)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:158)
	at org.springframework.aop.aspectj.AspectJAfterThrowingAdvice.invoke(AspectJAfterThrowingAdvice.java:63)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:168)
	at org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint.proceed(MethodInvocationProceedingJoinPoint.java:82)
	at com.fastproject.logs.aspect.LogAspect.around(LogAspect.java:93)
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:104)
	at java.base/java.lang.reflect.Method.invoke(Method.java:565)
	at org.springframework.... [截断]', 'POST', '127.0.0.1', 'save', 'arg0=PageConfigCreate(pathUrl=aaa, config=null, status=1, version=1.0.0, applicationId=327622567294799900)', NULL, 'f', 4, 'BUSINESS', '/page/config');
INSERT INTO "public"."sys_operation_log" VALUES (327982230565687296, 1, '2026-05-08 07:37:37.382507', 0, NULL, '2026-05-08 07:37:37.382507', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '新增页面', 'com.fastproject.exception.BusinessException: 应用不存在
	at com.fastproject.page.service.impl.PageConfigServiceImpl.lambda$save$0(PageConfigServiceImpl.java:43)
	at java.base/java.util.Optional.orElseThrow(Optional.java:403)
	at com.fastproject.page.service.impl.PageConfigServiceImpl.save(PageConfigServiceImpl.java:43)
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:104)
	at java.base/java.lang.reflect.Method.invoke(Method.java:565)
	at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:359)
	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:715)
	at com.fastproject.page.service.impl.PageConfigServiceImpl$$SpringCGLIB$$0.save(<generated>)
	at com.fastproject.module.page.controller.PageConfigController.save(PageConfigController.java:55)
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:104)
	at java.base/java.lang.reflect.Method.invoke(Method.java:565)
	at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:359)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.invokeJoinpoint(ReflectiveMethodInvocation.java:190)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:158)
	at org.springframework.aop.aspectj.AspectJAfterThrowingAdvice.invoke(AspectJAfterThrowingAdvice.java:63)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:168)
	at org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint.proceed(MethodInvocationProceedingJoinPoint.java:82)
	at com.fastproject.logs.aspect.LogAspect.around(LogAspect.java:93)
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:104)
	at java.base/java.lang.reflect.Method.invoke(Method.java:565)
	at org.springframework.... [截断]', 'POST', '127.0.0.1', 'save', 'arg0=PageConfigCreate(pathUrl=aaa, config=null, status=1, version=1.0.0, applicationId=327622567294799900)', NULL, 'f', 3, 'BUSINESS', '/page/config');
INSERT INTO "public"."sys_operation_log" VALUES (327982819844427776, 1, '2026-05-08 07:39:57.877044', 0, NULL, '2026-05-08 07:39:57.877044', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '新增页面', NULL, 'POST', '127.0.0.1', 'save', 'arg0=PageConfigCreate(pathUrl=/home, config=null, status=1, version=1.0.0, applicationId=327622567294799872)', NULL, 't', 13, 'BUSINESS', '/page/config');
INSERT INTO "public"."sys_operation_log" VALUES (327992446732603392, 1, '2026-05-08 08:18:13.106138', 0, NULL, '2026-05-08 08:18:13.106138', 'UPDATE', 'com.fastproject.module.page.controller.PageConfigController', '修改页面', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=PageConfigUpdate(id=327982819794096128, title=首页, pathUrl=/home, config=null, status=1, version=1.0.0, applicationId=null)', NULL, 't', 32, 'BUSINESS', '/page/config');
INSERT INTO "public"."sys_operation_log" VALUES (327992653142691840, 1, '2026-05-08 08:19:02.318989', 0, NULL, '2026-05-08 08:19:02.318989', 'UPDATE', 'com.fastproject.module.page.controller.PageTypeController', '修改页面类型', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=PageTypeUpdate(id=327582763836379136, title=自定义UI, code=ant_design_vue, status=1)', NULL, 't', 11, 'BUSINESS', '/page/type');
INSERT INTO "public"."sys_operation_log" VALUES (327992748584079360, 1, '2026-05-08 08:19:25.073247', 0, NULL, '2026-05-08 08:19:25.073247', 'UPDATE', 'com.fastproject.module.page.controller.PageTypeController', '修改页面类型', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=PageTypeUpdate(id=327582763836379136, title=Ant Design Vue, code=ant_design_vue, status=1)', NULL, 't', 6, 'BUSINESS', '/page/type');
INSERT INTO "public"."sys_operation_log" VALUES (328249358485164032, 1, '2026-05-09 01:19:05.641565', 0, NULL, '2026-05-09 01:19:05.641565', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '新增页面', NULL, 'POST', '127.0.0.1', 'save', 'arg0=PageConfigCreate(title=我的, pathUrl=/user, config=null, status=1, version=1.0.0, applicationId=327622567294799872)', NULL, 't', 23, 'BUSINESS', '/page/config');
INSERT INTO "public"."sys_operation_log" VALUES (328376691338645504, 1, '2026-05-09 09:45:04.159358', 0, NULL, '2026-05-09 09:45:04.159358', 'UPDATE', 'com.fastproject.module.page.controller.PageConfigController', '修改页面', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=PageConfigUpdate(id=327982819794096128, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778291094192-1a5oa","type":"lc-grid","label":"栅格布局","props":{"columns":2,"gap":12,"padding":0,"bgColor":"transparent"},"children":[]},{"id":"1778291098820-cggjg","type":"lc-image","label":"图片","props":{"src":"","alt":"","width":100,"height":200,"objectFit":"cover","borderRadius":0}}]}, status=1, version=1.0.0, applicationId=null)', NULL, 't', 30, 'BUSINESS', '/page/config');
INSERT INTO "public"."sys_operation_log" VALUES (328377025922469888, 1, '2026-05-09 09:46:23.930302', 0, NULL, '2026-05-09 09:46:23.930302', 'UPDATE', 'com.fastproject.module.page.controller.PageConfigController', '修改页面', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=PageConfigUpdate(id=327982819794096128, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778291094192-1a5oa","type":"lc-grid","label":"栅格布局","props":{"columns":2,"gap":12,"padding":0,"bgColor":"transparent"},"children":[]}]}, status=1, version=1.0.0, applicationId=null)', NULL, 't', 6, 'BUSINESS', '/page/config');
INSERT INTO "public"."sys_operation_log" VALUES (328377082226806784, 1, '2026-05-09 09:46:37.354217', 0, NULL, '2026-05-09 09:46:37.354217', 'UPDATE', 'com.fastproject.module.page.controller.PageConfigController', '修改页面', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=PageConfigUpdate(id=328249358409666560, title=我的, pathUrl=/user, config={"version":"1","nodes":[{"id":"1778291190974-bylwb","type":"lc-card","label":"卡片","props":{"title":"卡片标题","bordered":true,"shadow":false,"padding":16,"bgColor":"#ffffff","borderRadius":8},"children":[]}]}, status=1, version=1.0.0, applicationId=null)', NULL, 't', 6, 'BUSINESS', '/page/config');
INSERT INTO "public"."sys_operation_log" VALUES (328378347577020416, 1, '2026-05-09 09:51:39.037624', 0, NULL, '2026-05-09 09:51:39.037624', 'UPDATE', 'com.fastproject.module.page.controller.PageConfigController', '修改页面', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=PageConfigUpdate(id=328249358409666560, title=我的, pathUrl=/user, config={"version":"1","nodes":[{"id":"1778291227894-tyujn","type":"lc-flex","label":"弹性容器","props":{"direction":"row","gap":12,"wrap":true,"align":"flex-start","padding":0,"bgColor":"transparent"},"children":[]},{"id":"1778291231126-mwkrd","type":"lc-image","label":"图片","props":{"src":"","alt":"","width":100,"height":200,"objectFit":"cover","borderRadius":0}},{"id":"1778291236944-srm5s","type":"lc-button","label":"按钮","props":{"lab... [截断]', NULL, 't', 5, 'BUSINESS', '/page/config');
INSERT INTO "public"."sys_operation_log" VALUES (328378596026617856, 1, '2026-05-09 09:52:38.272592', 0, NULL, '2026-05-09 09:52:38.272592', 'UPDATE', 'com.fastproject.module.page.controller.PageConfigController', '修改页面', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=PageConfigUpdate(id=328249358409666560, title=我的, pathUrl=/user, config={"version":"1","nodes":[{"id":"1778291227894-tyujn","type":"lc-flex","label":"弹性容器","props":{"direction":"row","gap":12,"wrap":true,"align":"flex-start","padding":0,"bgColor":"transparent"},"children":[]},{"id":"1778291236944-srm5s","type":"lc-button","label":"按钮","props":{"label":"按钮","btnType":"primary","size":"middle","block":false}}]}, status=1, version=1.0.0, applicationId=null)', NULL, 't', 5, 'BUSINESS', '/page/config');
INSERT INTO "public"."sys_operation_log" VALUES (328379403677601792, 1, '2026-05-09 09:55:50.831951', 0, NULL, '2026-05-09 09:55:50.831951', 'UPDATE', 'com.fastproject.module.page.controller.PageConfigController', '修改页面', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=PageConfigUpdate(id=328249358409666560, title=我的, pathUrl=/user, config={"version":"1","nodes":[{"id":"1778291705457-4qy1b","type":"lc-grid","label":"栅格布局","props":{"columns":2,"gap":12,"padding":0,"bgColor":"transparent"},"children":[]},{"id":"1778291707737-7sbmk","type":"lc-flex","label":"弹性容器","props":{"direction":"row","gap":12,"wrap":true,"align":"flex-start","padding":0,"bgColor":"transparent"},"children":[]},{"id":"1778291710409-vxz6a","type":"lc-card","label":"卡片","props":{"title":"卡片标题"... [截断]', NULL, 't', 4, 'BUSINESS', '/page/config');
INSERT INTO "public"."sys_operation_log" VALUES (328379553145819136, 1, '2026-05-09 09:56:26.467413', 0, NULL, '2026-05-09 09:56:26.467413', 'UPDATE', 'com.fastproject.module.page.controller.PageConfigController', '修改页面', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=PageConfigUpdate(id=328249358409666560, title=我的, pathUrl=/user, config={"version":"1","nodes":[{"id":"1778291705457-4qy1b","type":"lc-grid","label":"栅格布局","props":{"columns":2,"gap":12,"padding":0,"bgColor":"transparent"},"children":[]},{"id":"1778291707737-7sbmk","type":"lc-flex","label":"弹性容器","props":{"direction":"row","gap":12,"wrap":true,"align":"flex-start","padding":0,"bgColor":"transparent"},"children":[]},{"id":"1778291710409-vxz6a","type":"lc-card","label":"卡片","props":{"title":"卡片标题"... [截断]', NULL, 't', 5, 'BUSINESS', '/page/config');
INSERT INTO "public"."sys_operation_log" VALUES (328393836600627200, 1, '2026-05-09 10:53:11.908093', 0, NULL, '2026-05-09 10:53:11.908093', 'UPDATE', 'com.fastproject.module.page.controller.PageConfigController', '修改页面', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=PageConfigUpdate(id=328249358409666560, title=我的, pathUrl=/user, config={"version":"1","nodes":[{"id":"1778291705457-4qy1b","type":"lc-grid","label":"栅格布局","props":{"columns":2,"gap":12,"padding":0,"bgColor":"transparent"},"children":[{"id":"1778295148914-0kh3u","type":"lc-button","label":"按钮","props":{"label":"按钮","btnType":"primary","size":"middle","block":true}},{"id":"1778295156187-aw58v","type":"lc-button","label":"按钮","props":{"label":"按钮","btnType":"primary","size":"middle","block":true}}... [截断]', NULL, 't', 8, 'BUSINESS', '/page/config');
INSERT INTO "public"."sys_operation_log" VALUES (328394170827935744, 1, '2026-05-09 10:54:31.594912', 0, NULL, '2026-05-09 10:54:31.594912', 'UPDATE', 'com.fastproject.module.page.controller.PageConfigController', '修改页面', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=PageConfigUpdate(id=328249358409666560, title=我的, pathUrl=/user, config={"version":"1","nodes":[{"id":"1778291705457-4qy1b","type":"lc-grid","label":"栅格布局","props":{"columns":2,"gap":4,"padding":0,"bgColor":"transparent"},"children":[{"id":"1778295148914-0kh3u","type":"lc-button","label":"按钮","props":{"label":"按钮","btnType":"primary","size":"middle","block":true}},{"id":"1778295156187-aw58v","type":"lc-button","label":"按钮","props":{"label":"按钮","btnType":"primary","size":"middle","block":true}},... [截断]', NULL, 't', 5, 'BUSINESS', '/page/config');
INSERT INTO "public"."sys_operation_log" VALUES (328395928069345280, 1, '2026-05-09 11:01:30.553265', 0, NULL, '2026-05-09 11:01:30.553265', 'UPDATE', 'com.fastproject.module.page.controller.PageConfigController', '修改页面', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=PageConfigUpdate(id=328249358409666560, title=我的, pathUrl=/user, config={"version":"1","nodes":[{"id":"1778291705457-4qy1b","type":"lc-grid","label":"栅格布局","props":{"columns":2,"gap":4,"padding":0,"bgColor":"transparent"},"children":[{"id":"1778295148914-0kh3u","type":"lc-button","label":"按钮","props":{"label":"按钮","btnType":"primary","size":"middle","block":true}},{"id":"1778295156187-aw58v","type":"lc-button","label":"按钮","props":{"label":"按钮","btnType":"primary","size":"middle","block":true}},... [截断]', NULL, 't', 6, 'BUSINESS', '/page/config');
INSERT INTO "public"."sys_operation_log" VALUES (328679848396591104, 1, '2026-05-10 05:49:42.434276', 0, NULL, '2026-05-10 05:49:42.434276', 'UPDATE', 'com.fastproject.module.page.controller.PageApplicationController', '修改页面应用', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=PageApplicationUpdate(id=327622567294799872, title=APPUL, code=web, status=1, icon=327640694992801792, typeId=327582763836379136)', NULL, 't', 73, 'BUSINESS', '/page/application');
INSERT INTO "public"."sys_operation_log" VALUES (328679942793596928, 1, '2026-05-10 05:50:04.94076', 0, NULL, '2026-05-10 05:50:04.94076', 'UPDATE', 'com.fastproject.module.page.controller.PageConfigController', '修改页面', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=PageConfigUpdate(id=328249358409666560, title=我的, pathUrl=/home, config=null, status=1, version=1.0.0, applicationId=null)', NULL, 't', 10, 'BUSINESS', '/page/config');
INSERT INTO "public"."sys_operation_log" VALUES (328679985952985088, 1, '2026-05-10 05:50:15.230367', 0, NULL, '2026-05-10 05:50:15.230367', 'UPDATE', 'com.fastproject.module.page.controller.PageConfigController', '修改页面', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config=null, status=1, version=1.0.0, applicationId=null)', NULL, 't', 6, 'BUSINESS', '/page/config');
INSERT INTO "public"."sys_operation_log" VALUES (328680013798969344, 1, '2026-05-10 05:50:21.869374', 0, NULL, '2026-05-10 05:50:21.869374', 'UPDATE', 'com.fastproject.module.page.controller.PageConfigController', '修改页面', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config=null, status=1, version=1.0.1, applicationId=null)', NULL, 't', 6, 'BUSINESS', '/page/config');
INSERT INTO "public"."sys_operation_log" VALUES (328680080819752960, 1, '2026-05-10 05:50:37.848725', 0, NULL, '2026-05-10 05:50:37.848725', 'UPDATE', 'com.fastproject.module.page.controller.PageConfigController', '修改页面', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778291705457-4qy1b","type":"lc-grid","label":"栅格布局","props":{"columns":2,"gap":4,"padding":0,"bgColor":"transparent"},"children":[{"id":"1778295148914-0kh3u","type":"lc-button","label":"按钮","props":{"label":"按钮","btnType":"primary","size":"middle","block":true}},{"id":"1778295156187-aw58v","type":"lc-button","label":"按钮","props":{"label":"按钮","btnType":"primary","size":"middle","block":true}},... [截断]', NULL, 't', 3, 'BUSINESS', '/page/config');
INSERT INTO "public"."sys_operation_log" VALUES (328682423057518592, 1, '2026-05-10 05:59:56.281855', 0, NULL, '2026-05-10 05:59:56.281855', 'UPDATE', 'com.fastproject.module.page.controller.PageConfigController', '修改页面', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778291705457-4qy1b","type":"lc-grid","label":"栅格布局","props":{"columns":2,"gap":4,"padding":0,"bgColor":"transparent"},"children":[{"id":"1778295148914-0kh3u","type":"lc-button","label":"按钮","props":{"label":"按钮","btnType":"primary","size":"middle","block":true}},{"id":"1778295156187-aw58v","type":"lc-button","label":"按钮","props":{"label":"按钮","btnType":"primary","size":"middle","block":true}},... [截断]', NULL, 't', 3, 'BUSINESS', '/page/config');
INSERT INTO "public"."sys_operation_log" VALUES (328682520868687872, 1, '2026-05-10 06:00:19.601051', 0, NULL, '2026-05-10 06:00:19.601051', 'UPDATE', 'com.fastproject.module.page.controller.PageConfigController', '修改页面', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778291705457-4qy1b","type":"lc-grid","label":"栅格布局","props":{"columns":2,"gap":4,"padding":0,"bgColor":"transparent"},"children":[{"id":"1778295148914-0kh3u","type":"lc-button","label":"按钮","props":{"label":"按钮","btnType":"primary","size":"middle","block":true}},{"id":"1778295156187-aw58v","type":"lc-button","label":"按钮","props":{"label":"按钮","btnType":"primary","size":"middle","block":true}},... [截断]', NULL, 't', 6, 'BUSINESS', '/page/config');
INSERT INTO "public"."sys_operation_log" VALUES (329752141520572416, 1, '2026-05-13 04:50:37.037857', 0, NULL, '2026-05-13 04:50:37.037857', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778608801139-a0z2n","type":"lc-image","label":"图片","props":{"src":"329749056903581696","alt":"啊啊啊","width":100,"height":300,"objectFit":"contain","borderRadius":0}}]}, status=1, version=1.0.1, applicationId=null)', NULL, 't', 6, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (328721431284289536, 1, '2026-05-10 08:34:56.567309', 0, NULL, '2026-05-10 08:34:56.567309', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', 'org.springframework.dao.InvalidDataAccessApiUsageException: The given id must not be null
	at org.springframework.orm.jpa.EntityManagerFactoryUtils.convertJpaAccessExceptionIfPossible(EntityManagerFactoryUtils.java:464)
	at org.springframework.orm.jpa.hibernate.HibernateExceptionTranslator.translateExceptionIfPossible(HibernateExceptionTranslator.java:112)
	at org.springframework.orm.jpa.vendor.HibernateJpaDialect.translateExceptionIfPossible(HibernateJpaDialect.java:223)
	at org.springframework.orm.jpa.AbstractEntityManagerFactoryBean.translateExceptionIfPossible(AbstractEntityManagerFactoryBean.java:576)
	at org.springframework.dao.support.ChainedPersistenceExceptionTranslator.translateExceptionIfPossible(ChainedPersistenceExceptionTranslator.java:61)
	at org.springframework.dao.support.DataAccessUtils.translateIfNecessary(DataAccessUtils.java:346)
	at org.springframework.dao.support.PersistenceExceptionTranslationInterceptor.invoke(PersistenceExceptionTranslationInterceptor.java:157)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:179)
	at org.springframework.data.jpa.repository.support.CrudMethodMetadataPostProcessor$CrudMethodMetadataPopulatingMethodInterceptor.invoke(CrudMethodMetadataPostProcessor.java:166)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:179)
	at org.springframework.aop.framework.JdkDynamicAopProxy.invoke(JdkDynamicAopProxy.java:222)
	at jdk.proxy2/jdk.proxy2.$Proxy172.findById(Unknown Source)
	at com.fastproject.page.service.impl.PageConfigServiceImpl.publish(PageConfigServiceImpl.java:187)
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:104)
	at java.base/java.lang.reflect.Method.invoke(Method.java:565)
	at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:359)
	at org.springframework.aop.framework.ReflectiveMethodInvocation... [截断]', 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778291705457-4qy1b","type":"lc-grid","label":"栅格布局","props":{"columns":2,"gap":4,"padding":0,"bgColor":"transparent"},"children":[{"id":"1778295148914-0kh3u","type":"lc-button","label":"按钮","props":{"label":"按钮","btnType":"primary","size":"middle","block":true}},{"id":"1778295156187-aw58v","type":"lc-button","label":"按钮","props":{"label":"按钮","btnType":"primary","size":"middle","block":true}},... [截断]', NULL, 'f', 18, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (328721471830626304, 1, '2026-05-10 08:35:06.234407', 0, NULL, '2026-05-10 08:35:06.234407', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', 'org.springframework.dao.InvalidDataAccessApiUsageException: The given id must not be null
	at org.springframework.orm.jpa.EntityManagerFactoryUtils.convertJpaAccessExceptionIfPossible(EntityManagerFactoryUtils.java:464)
	at org.springframework.orm.jpa.hibernate.HibernateExceptionTranslator.translateExceptionIfPossible(HibernateExceptionTranslator.java:112)
	at org.springframework.orm.jpa.vendor.HibernateJpaDialect.translateExceptionIfPossible(HibernateJpaDialect.java:223)
	at org.springframework.orm.jpa.AbstractEntityManagerFactoryBean.translateExceptionIfPossible(AbstractEntityManagerFactoryBean.java:576)
	at org.springframework.dao.support.ChainedPersistenceExceptionTranslator.translateExceptionIfPossible(ChainedPersistenceExceptionTranslator.java:61)
	at org.springframework.dao.support.DataAccessUtils.translateIfNecessary(DataAccessUtils.java:346)
	at org.springframework.dao.support.PersistenceExceptionTranslationInterceptor.invoke(PersistenceExceptionTranslationInterceptor.java:157)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:179)
	at org.springframework.data.jpa.repository.support.CrudMethodMetadataPostProcessor$CrudMethodMetadataPopulatingMethodInterceptor.invoke(CrudMethodMetadataPostProcessor.java:166)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:179)
	at org.springframework.aop.framework.JdkDynamicAopProxy.invoke(JdkDynamicAopProxy.java:222)
	at jdk.proxy2/jdk.proxy2.$Proxy172.findById(Unknown Source)
	at com.fastproject.page.service.impl.PageConfigServiceImpl.publish(PageConfigServiceImpl.java:187)
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:104)
	at java.base/java.lang.reflect.Method.invoke(Method.java:565)
	at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:359)
	at org.springframework.aop.framework.ReflectiveMethodInvocation... [截断]', 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778291705457-4qy1b","type":"lc-grid","label":"栅格布局","props":{"columns":2,"gap":4,"padding":0,"bgColor":"transparent"},"children":[{"id":"1778295148914-0kh3u","type":"lc-button","label":"按钮","props":{"label":"按钮","btnType":"primary","size":"middle","block":true}},{"id":"1778295156187-aw58v","type":"lc-button","label":"按钮","props":{"label":"按钮","btnType":"primary","size":"middle","block":true}},... [截断]', NULL, 'f', 4, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (328722025017380864, 1, '2026-05-10 08:37:18.124446', 0, NULL, '2026-05-10 08:37:18.124446', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', 'org.springframework.dao.InvalidDataAccessApiUsageException: The given id must not be null
	at org.springframework.orm.jpa.EntityManagerFactoryUtils.convertJpaAccessExceptionIfPossible(EntityManagerFactoryUtils.java:464)
	at org.springframework.orm.jpa.hibernate.HibernateExceptionTranslator.translateExceptionIfPossible(HibernateExceptionTranslator.java:112)
	at org.springframework.orm.jpa.vendor.HibernateJpaDialect.translateExceptionIfPossible(HibernateJpaDialect.java:223)
	at org.springframework.orm.jpa.AbstractEntityManagerFactoryBean.translateExceptionIfPossible(AbstractEntityManagerFactoryBean.java:576)
	at org.springframework.dao.support.ChainedPersistenceExceptionTranslator.translateExceptionIfPossible(ChainedPersistenceExceptionTranslator.java:61)
	at org.springframework.dao.support.DataAccessUtils.translateIfNecessary(DataAccessUtils.java:346)
	at org.springframework.dao.support.PersistenceExceptionTranslationInterceptor.invoke(PersistenceExceptionTranslationInterceptor.java:157)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:179)
	at org.springframework.data.jpa.repository.support.CrudMethodMetadataPostProcessor$CrudMethodMetadataPopulatingMethodInterceptor.invoke(CrudMethodMetadataPostProcessor.java:166)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:179)
	at org.springframework.aop.framework.JdkDynamicAopProxy.invoke(JdkDynamicAopProxy.java:222)
	at jdk.proxy2/jdk.proxy2.$Proxy172.findById(Unknown Source)
	at com.fastproject.page.service.impl.PageConfigServiceImpl.publish(PageConfigServiceImpl.java:188)
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:104)
	at java.base/java.lang.reflect.Method.invoke(Method.java:565)
	at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:359)
	at org.springframework.aop.framework.ReflectiveMethodInvocation... [截断]', 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778291705457-4qy1b","type":"lc-grid","label":"栅格布局","props":{"columns":2,"gap":4,"padding":0,"bgColor":"transparent"},"children":[{"id":"1778295148914-0kh3u","type":"lc-button","label":"按钮","props":{"label":"按钮","btnType":"primary","size":"middle","block":true}},{"id":"1778295156187-aw58v","type":"lc-button","label":"按钮","props":{"label":"按钮","btnType":"primary","size":"middle","block":true}},... [截断]', NULL, 'f', 23, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (328723028617859072, 1, '2026-05-10 08:41:17.401877', 0, NULL, '2026-05-10 08:41:17.401877', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', 'org.springframework.dao.InvalidDataAccessApiUsageException: The given id must not be null
	at org.springframework.orm.jpa.EntityManagerFactoryUtils.convertJpaAccessExceptionIfPossible(EntityManagerFactoryUtils.java:464)
	at org.springframework.orm.jpa.hibernate.HibernateExceptionTranslator.translateExceptionIfPossible(HibernateExceptionTranslator.java:112)
	at org.springframework.orm.jpa.vendor.HibernateJpaDialect.translateExceptionIfPossible(HibernateJpaDialect.java:223)
	at org.springframework.orm.jpa.AbstractEntityManagerFactoryBean.translateExceptionIfPossible(AbstractEntityManagerFactoryBean.java:576)
	at org.springframework.dao.support.ChainedPersistenceExceptionTranslator.translateExceptionIfPossible(ChainedPersistenceExceptionTranslator.java:61)
	at org.springframework.dao.support.DataAccessUtils.translateIfNecessary(DataAccessUtils.java:346)
	at org.springframework.dao.support.PersistenceExceptionTranslationInterceptor.invoke(PersistenceExceptionTranslationInterceptor.java:157)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:179)
	at org.springframework.data.jpa.repository.support.CrudMethodMetadataPostProcessor$CrudMethodMetadataPopulatingMethodInterceptor.invoke(CrudMethodMetadataPostProcessor.java:166)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:179)
	at org.springframework.aop.framework.JdkDynamicAopProxy.invoke(JdkDynamicAopProxy.java:222)
	at jdk.proxy2/jdk.proxy2.$Proxy172.findById(Unknown Source)
	at com.fastproject.page.service.impl.PageConfigServiceImpl.publish(PageConfigServiceImpl.java:188)
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:104)
	at java.base/java.lang.reflect.Method.invoke(Method.java:565)
	at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:359)
	at org.springframework.aop.framework.ReflectiveMethodInvocation... [截断]', 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778291705457-4qy1b","type":"lc-grid","label":"栅格布局","props":{"columns":2,"gap":4,"padding":0,"bgColor":"transparent"},"children":[{"id":"1778295148914-0kh3u","type":"lc-button","label":"按钮","props":{"label":"按钮","btnType":"primary","size":"middle","block":true}},{"id":"1778295156187-aw58v","type":"lc-button","label":"按钮","props":{"label":"按钮","btnType":"primary","size":"middle","block":true}},... [截断]', NULL, 'f', 4, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (328723083986866176, 1, '2026-05-10 08:41:30.602799', 0, NULL, '2026-05-10 08:41:30.602799', 'UPDATE', 'com.fastproject.module.page.controller.PageConfigController', '修改页面', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778291705457-4qy1b","type":"lc-grid","label":"栅格布局","props":{"columns":2,"gap":4,"padding":0,"bgColor":"transparent"},"children":[{"id":"1778295148914-0kh3u","type":"lc-button","label":"按钮","props":{"label":"按钮","btnType":"primary","size":"middle","block":true}},{"id":"1778295156187-aw58v","type":"lc-button","label":"按钮","props":{"label":"按钮","btnType":"primary","size":"middle","block":true}},... [截断]', NULL, 't', 2, 'BUSINESS', '/page/config');
INSERT INTO "public"."sys_operation_log" VALUES (328723901339275264, 1, '2026-05-10 08:44:45.474774', 0, NULL, '2026-05-10 08:44:45.474774', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=327982819794096128, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778291094192-1a5oa","type":"lc-grid","label":"栅格布局","props":{"columns":2,"gap":12,"padding":0,"bgColor":"transparent"},"children":[]}]}, status=1, version=1.0.0, applicationId=null)', NULL, 't', 36, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (331989908191318016, 1, '2026-05-19 09:02:42.187443', 0, NULL, '2026-05-19 09:02:42.187443', 'LOGOUT', 'com.fastproject.module.system.controller.AuthController', '用户退出', NULL, 'POST', '127.0.0.1', 'logout', '{}', NULL, 't', 6, 'LOGIN', '/auth/logout');
INSERT INTO "public"."sys_operation_log" VALUES (328725296935211008, 1, '2026-05-10 08:50:18.210984', 0, NULL, '2026-05-10 08:50:18.210984', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778291705457-4qy1b","type":"lc-grid","label":"栅格布局","props":{"columns":2,"gap":4,"padding":0,"bgColor":"transparent"},"children":[{"id":"1778295148914-0kh3u","type":"lc-button","label":"按钮","props":{"label":"按钮","btnType":"primary","size":"middle","block":true}},{"id":"1778295156187-aw58v","type":"lc-button","label":"按钮","props":{"label":"按钮","btnType":"primary","size":"middle","block":true}},... [截断]', NULL, 't', 30, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (328733798969774080, 1, '2026-05-10 09:24:05.253623', 0, NULL, '2026-05-10 09:24:05.254133', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=327982819794096128, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778291094192-1a5oa","type":"lc-grid","label":"栅格布局","props":{"columns":2,"gap":12,"padding":0,"bgColor":"transparent"},"children":[]}]}, status=1, version=1.0.0, applicationId=null)', NULL, 't', 34, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (328733836995334144, 1, '2026-05-10 09:24:14.319725', 0, NULL, '2026-05-10 09:24:14.319725', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778291705457-4qy1b","type":"lc-grid","label":"栅格布局","props":{"columns":2,"gap":4,"padding":0,"bgColor":"transparent"},"children":[{"id":"1778295148914-0kh3u","type":"lc-button","label":"按钮","props":{"label":"按钮","btnType":"primary","size":"middle","block":true}},{"id":"1778295156187-aw58v","type":"lc-button","label":"按钮","props":{"label":"按钮","btnType":"primary","size":"middle","block":true}},... [截断]', NULL, 't', 7, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (328734594297892864, 1, '2026-05-10 09:27:14.874033', 0, NULL, '2026-05-10 09:27:14.874033', 'DELETE', 'com.fastproject.module.page.controller.PageWebConfigController', '删除页面Web配置', NULL, 'DELETE', '127.0.0.1', 'delete', 'arg0=328723901276360704', NULL, 't', 12, 'BUSINESS', '/page/web/config/328723901276360704');
INSERT INTO "public"."sys_operation_log" VALUES (328734629697818624, 1, '2026-05-10 09:27:23.314245', 0, NULL, '2026-05-10 09:27:23.314245', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778291705457-4qy1b","type":"lc-grid","label":"栅格布局","props":{"columns":2,"gap":4,"padding":0,"bgColor":"transparent"},"children":[{"id":"1778295148914-0kh3u","type":"lc-button","label":"按钮","props":{"label":"按钮","btnType":"primary","size":"middle","block":true}},{"id":"1778295156187-aw58v","type":"lc-button","label":"按钮","props":{"label":"按钮","btnType":"primary","size":"middle","block":true}},... [截断]', NULL, 't', 6, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (329100010329346048, NULL, '2026-05-11 09:39:16.845812', 0, NULL, '2026-05-11 09:39:16.845812', 'LOGIN', 'com.fastproject.module.system.controller.AuthController', '用户登录', NULL, 'POST', '127.0.0.1', 'login', 'arg0=LoginDto(username=admin, password=123456, captchaKey=9b15e42536ce42228ebdf6aa02d0bd09, captcha=UCKV)', NULL, 't', 120, 'LOGIN', '/auth/login');
INSERT INTO "public"."sys_operation_log" VALUES (329376435749916672, 1, '2026-05-12 03:57:41.801983', 0, NULL, '2026-05-12 03:57:41.801983', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778291705457-4qy1b","type":"lc-grid","label":"栅格布局","props":{"columns":2,"gap":4,"padding":0,"bgColor":"transparent"},"children":[{"id":"1778295148914-0kh3u","type":"lc-button","label":"按钮","props":{"label":"按钮","btnType":"primary","size":"middle","block":true}},{"id":"1778295156187-aw58v","type":"lc-button","label":"按钮","props":{"label":"按钮","btnType":"primary","size":"middle","block":true}},... [截断]', NULL, 't', 30, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (329376510840541184, 1, '2026-05-12 03:57:59.704322', 0, NULL, '2026-05-12 03:57:59.704322', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778291705457-4qy1b","type":"lc-grid","label":"栅格布局","props":{"columns":2,"gap":4,"padding":0,"bgColor":"transparent"},"children":[{"id":"1778295148914-0kh3u","type":"lc-button","label":"按钮","props":{"label":"按钮","btnType":"primary","size":"middle","block":true}},{"id":"1778295156187-aw58v","type":"lc-button","label":"按钮","props":{"label":"按钮","btnType":"default","size":"large","block":true}},{... [截断]', NULL, 't', 6, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (329376555535044608, 1, '2026-05-12 03:58:10.360347', 0, NULL, '2026-05-12 03:58:10.360347', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778291705457-4qy1b","type":"lc-grid","label":"栅格布局","props":{"columns":2,"gap":4,"padding":0,"bgColor":"transparent"},"children":[{"id":"1778295148914-0kh3u","type":"lc-button","label":"按钮","props":{"label":"按钮","btnType":"primary","size":"middle","block":true}},{"id":"1778295156187-aw58v","type":"lc-button","label":"按钮","props":{"label":"按钮","btnType":"default","size":"small","block":true}},{... [截断]', NULL, 't', 7, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (329377581201756160, 1, '2026-05-12 04:02:14.898526', 0, NULL, '2026-05-12 04:02:14.898526', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778291705457-4qy1b","type":"lc-grid","label":"栅格布局","props":{"columns":2,"gap":4,"padding":0,"bgColor":"transparent"},"children":[{"id":"1778295148914-0kh3u","type":"lc-button","label":"按钮","props":{"label":"按钮","btnType":"primary","size":"small","block":true}},{"id":"1778295156187-aw58v","type":"lc-button","label":"按钮","props":{"label":"按钮","btnType":"default","size":"small","block":true}},{"... [截断]', NULL, 't', 7, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (329379000944300032, 1, '2026-05-12 04:07:53.391077', 0, NULL, '2026-05-12 04:07:53.391077', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778291705457-4qy1b","type":"lc-grid","label":"栅格布局","props":{"columns":2,"gap":4,"padding":0,"bgColor":"transparent"},"children":[{"id":"1778295148914-0kh3u","type":"lc-button","label":"按钮","props":{"label":"按钮","btnType":"primary","size":"small","block":true}},{"id":"1778295156187-aw58v","type":"lc-button","label":"按钮","props":{"label":"按钮","btnType":"default","size":"large","block":true}},{"... [截断]', NULL, 't', 7, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (329379943135973376, 1, '2026-05-12 04:11:38.027504', 0, NULL, '2026-05-12 04:11:38.027504', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778291705457-4qy1b","type":"lc-grid","label":"栅格布局","props":{"columns":2,"gap":4,"padding":0,"bgColor":"transparent"},"children":[{"id":"1778295148914-0kh3u","type":"lc-button","label":"按钮","props":{"label":"按钮","btnType":"primary","size":"small","block":true}},{"id":"1778295156187-aw58v","type":"lc-button","label":"按钮","props":{"label":"按钮","btnType":"default","size":"small","block":true}},{"... [截断]', NULL, 't', 8, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (329381062679597056, 1, '2026-05-12 04:16:04.947595', 0, NULL, '2026-05-12 04:16:04.947595', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778291705457-4qy1b","type":"lc-grid","label":"栅格布局","props":{"columns":2,"gap":0,"padding":0,"bgColor":"transparent"},"children":[{"id":"1778295148914-0kh3u","type":"lc-button","label":"按钮","props":{"label":"按钮","btnType":"primary","size":"small","block":true}},{"id":"1778295156187-aw58v","type":"lc-button","label":"按钮","props":{"label":"按钮","btnType":"default","size":"small","block":true}},{"... [截断]', NULL, 't', 8, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (329382518333771776, 1, '2026-05-12 04:21:52.002296', 0, NULL, '2026-05-12 04:21:52.002296', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778291705457-4qy1b","type":"lc-grid","label":"栅格布局","props":{"columns":2,"gap":0,"padding":0,"bgColor":"transparent"},"children":[{"id":"1778295148914-0kh3u","type":"lc-button","label":"按钮","props":{"label":"按钮","btnType":"primary","size":"small","block":true}},{"id":"1778295156187-aw58v","type":"lc-button","label":"按钮","props":{"label":"按钮","btnType":"default","size":"small","block":true}},{"... [截断]', NULL, 't', 382, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (329383441432973312, 1, '2026-05-12 04:25:32.086706', 0, NULL, '2026-05-12 04:25:32.086706', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778291705457-4qy1b","type":"lc-grid","label":"栅格布局","props":{"columns":2,"gap":5,"padding":0,"bgColor":"transparent"},"children":[{"id":"1778295148914-0kh3u","type":"lc-button","label":"按钮","props":{"label":"按钮","btnType":"primary","size":"small","block":true}},{"id":"1778295156187-aw58v","type":"lc-button","label":"按钮","props":{"label":"按钮","btnType":"default","size":"small","block":true}},{"... [截断]', NULL, 't', 10, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (329399341523537920, 1, '2026-05-12 05:28:42.963617', 0, NULL, '2026-05-12 05:28:42.963617', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778291705457-4qy1b","type":"lc-grid","label":"栅格布局","props":{"columns":2,"gap":5,"padding":0,"bgColor":"transparent"},"children":[{"id":"1778295148914-0kh3u","type":"lc-button","label":"按钮","props":{"label":"按钮","btnType":"default","size":"small","block":true}},{"id":"1778295156187-aw58v","type":"lc-button","label":"按钮","props":{"label":"按钮","btnType":"default","size":"small","block":true}},{"... [截断]', NULL, 't', 32, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (329399378706042880, 1, '2026-05-12 05:28:51.82845', 0, NULL, '2026-05-12 05:28:51.82845', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778291705457-4qy1b","type":"lc-grid","label":"栅格布局","props":{"columns":2,"gap":5,"padding":0,"bgColor":"transparent"},"children":[{"id":"1778295148914-0kh3u","type":"lc-button","label":"按钮","props":{"label":"按钮","btnType":"default","size":"small","block":true}},{"id":"1778295156187-aw58v","type":"lc-button","label":"按钮","props":{"label":"按钮","btnType":"default","size":"small","block":true}},{"... [截断]', NULL, 't', 9, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (329399511313158144, 1, '2026-05-12 05:29:23.44408', 0, NULL, '2026-05-12 05:29:23.44408', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778291705457-4qy1b","type":"lc-grid","label":"栅格布局","props":{"columns":2,"gap":5,"padding":0,"bgColor":"transparent"},"children":[{"id":"1778295148914-0kh3u","type":"lc-button","label":"按钮","props":{"label":"按钮","btnType":"default","size":"small","block":true}},{"id":"1778295156187-aw58v","type":"lc-button","label":"按钮","props":{"label":"按钮","btnType":"default","size":"small","block":true}},{"... [截断]', NULL, 't', 7, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (329399546725666816, 1, '2026-05-12 05:29:31.887722', 0, NULL, '2026-05-12 05:29:31.887722', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778291705457-4qy1b","type":"lc-grid","label":"栅格布局","props":{"columns":2,"gap":5,"padding":0,"bgColor":"transparent"},"children":[{"id":"1778295148914-0kh3u","type":"lc-button","label":"按钮","props":{"label":"按钮","btnType":"default","size":"small","block":true}},{"id":"1778295156187-aw58v","type":"lc-button","label":"按钮","props":{"label":"按钮","btnType":"default","size":"small","block":true}},{"... [截断]', NULL, 't', 11, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (329399583094476800, 1, '2026-05-12 05:29:40.55868', 0, NULL, '2026-05-12 05:29:40.55868', 'UPDATE', 'com.fastproject.module.page.controller.PageConfigController', '修改页面', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778291705457-4qy1b","type":"lc-grid","label":"栅格布局","props":{"columns":2,"gap":5,"padding":0,"bgColor":"transparent"},"children":[{"id":"1778295148914-0kh3u","type":"lc-button","label":"按钮","props":{"label":"按钮","btnType":"default","size":"small","block":true}},{"id":"1778295156187-aw58v","type":"lc-button","label":"按钮","props":{"label":"按钮","btnType":"default","size":"small","block":true}},{"... [截断]', NULL, 't', 6, 'BUSINESS', '/page/config');
INSERT INTO "public"."sys_operation_log" VALUES (329409662355116032, 1, '2026-05-12 06:09:43.641877', 0, NULL, '2026-05-12 06:09:43.641877', 'UPDATE', 'com.fastproject.module.page.controller.PageConfigController', '修改页面', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778291705457-4qy1b","type":"lc-grid","label":"栅格布局","props":{"columns":2,"gap":5,"padding":0,"bgColor":"transparent"},"children":[{"id":"1778295148914-0kh3u","type":"lc-button","label":"按钮","props":{"label":"按钮","btnType":"default","size":"small","block":true}},{"id":"1778295156187-aw58v","type":"lc-button","label":"按钮","props":{"label":"按钮","btnType":"default","size":"small","block":true}},{"... [截断]', NULL, 't', 2, 'BUSINESS', '/page/config');
INSERT INTO "public"."sys_operation_log" VALUES (329410019135197184, 1, '2026-05-12 06:11:08.704519', 0, NULL, '2026-05-12 06:11:08.704519', 'UPDATE', 'com.fastproject.module.page.controller.PageConfigController', '修改页面', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778291705457-4qy1b","type":"lc-grid","label":"栅格布局","props":{"columns":2,"gap":5,"padding":0,"bgColor":"transparent"},"children":[{"id":"1778295148914-0kh3u","type":"lc-button","label":"按钮","props":{"label":"按钮","btnType":"default","size":"small","block":true}},{"id":"1778295156187-aw58v","type":"lc-button","label":"按钮","props":{"label":"按钮","btnType":"default","size":"small","block":true}},{"... [截断]', NULL, 't', 4, 'BUSINESS', '/page/config');
INSERT INTO "public"."sys_operation_log" VALUES (331989942173569024, NULL, '2026-05-19 09:02:50.28967', 0, NULL, '2026-05-19 09:02:50.28967', 'LOGIN', 'com.fastproject.module.system.controller.AuthController', '用户登录', NULL, 'POST', '127.0.0.1', 'login', 'arg0=LoginDto(username=admin, password=123456, captchaKey=f2fcea1561ba4b868a1e64e6f64c68a0, captcha=98bx)', NULL, 't', 97, 'LOGIN', '/auth/login');
INSERT INTO "public"."sys_operation_log" VALUES (329410068988694528, 1, '2026-05-12 06:11:20.590281', 0, NULL, '2026-05-12 06:11:20.590281', 'UPDATE', 'com.fastproject.module.page.controller.PageConfigController', '修改页面', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778291705457-4qy1b","type":"lc-grid","label":"栅格布局","props":{"columns":2,"gap":5,"padding":0,"bgColor":"transparent"},"children":[{"id":"1778295148914-0kh3u","type":"lc-button","label":"按钮","props":{"label":"按钮","btnType":"default","size":"small","block":true}},{"id":"1778295156187-aw58v","type":"lc-button","label":"按钮","props":{"label":"按钮","btnType":"default","size":"small","block":true}},{"... [截断]', NULL, 't', 5, 'BUSINESS', '/page/config');
INSERT INTO "public"."sys_operation_log" VALUES (329410770356015104, 1, '2026-05-12 06:14:07.809704', 0, NULL, '2026-05-12 06:14:07.809704', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778291705457-4qy1b","type":"lc-grid","label":"栅格布局","props":{"columns":2,"gap":5,"padding":0,"bgColor":"transparent"},"children":[{"id":"1778295148914-0kh3u","type":"lc-button","label":"按钮","props":{"label":"按钮","btnType":"default","size":"small","block":true}},{"id":"1778295156187-aw58v","type":"lc-button","label":"按钮","props":{"label":"按钮","btnType":"default","size":"small","block":true}},{"... [截断]', NULL, 't', 5, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (329412484727443456, 1, '2026-05-12 06:20:56.547599', 0, NULL, '2026-05-12 06:20:56.547599', 'UPDATE', 'com.fastproject.module.page.controller.PageConfigController', '修改页面', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[]}, status=1, version=1.0.1, applicationId=null)', NULL, 't', 5, 'BUSINESS', '/page/config');
INSERT INTO "public"."sys_operation_log" VALUES (329412763514441728, 1, '2026-05-12 06:22:03.015636', 0, NULL, '2026-05-12 06:22:03.015636', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778538061531-f28jw","type":"lc-flex","label":"弹性容器","props":{"direction":"column","gap":12,"wrap":true,"align":"flex-start","padding":0,"bgColor":"transparent"},"children":[{"id":"1778538076119-sim7c","type":"lc-grid","label":"栅格布局","props":{"columns":2,"gap":12,"padding":0,"bgColor":"transparent"},"children":[{"id":"1778538118512-4787e","type":"lc-heading","label":"标题","props":{"content":"标题文... [截断]', NULL, 't', 7, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (329412818682122240, 1, '2026-05-12 06:22:16.168386', 0, NULL, '2026-05-12 06:22:16.168386', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778538061531-f28jw","type":"lc-flex","label":"弹性容器","props":{"direction":"column","gap":12,"wrap":true,"align":"flex-start","padding":0,"bgColor":"transparent"},"children":[{"id":"1778538134621-hac3l","type":"lc-button","label":"按钮","props":{"label":"按钮","btnType":"primary","size":"middle","block":false}}]}]}, status=1, version=1.0.1, applicationId=null)', NULL, 't', 5, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (329418458884345856, 1, '2026-05-12 06:44:40.897615', 0, NULL, '2026-05-12 06:44:40.897615', 'UPDATE', 'com.fastproject.module.page.controller.PageConfigController', '修改页面', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778539260789-kt6a3","type":"lc-div","label":"自定义 Div","props":{"display":"flex","flexDirection":"column","flexWrap":false,"alignItems":"center","justifyContent":"flex-start","gap":0,"padding":0,"minHeight":60,"bgColor":"transparent","borderRadius":0},"children":[{"id":"1778539337520-b5y34","type":"lc-div","label":"自定义 Div","props":{"display":"block","flexDirection":"row","flexWrap":false,"alig... [截断]', NULL, 't', 5, 'BUSINESS', '/page/config');
INSERT INTO "public"."sys_operation_log" VALUES (329418466044022784, 1, '2026-05-12 06:44:42.604321', 0, NULL, '2026-05-12 06:44:42.604321', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778539260789-kt6a3","type":"lc-div","label":"自定义 Div","props":{"display":"flex","flexDirection":"column","flexWrap":false,"alignItems":"center","justifyContent":"flex-start","gap":0,"padding":0,"minHeight":60,"bgColor":"transparent","borderRadius":0},"children":[{"id":"1778539337520-b5y34","type":"lc-div","label":"自定义 Div","props":{"display":"block","flexDirection":"row","flexWrap":false,"alig... [截断]', NULL, 't', 6, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (329419366607228928, 1, '2026-05-12 06:48:17.3151', 0, NULL, '2026-05-12 06:48:17.3151', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778539260789-kt6a3","type":"lc-div","label":"自定义 Div","props":{"display":"flex","flexDirection":"column","flexWrap":false,"alignItems":"center","justifyContent":"flex-start","gap":0,"padding":0,"minHeight":60,"bgColor":"transparent","borderRadius":0},"children":[{"id":"1778539337520-b5y34","type":"lc-div","label":"自定义 Div","props":{"display":"block","flexDirection":"row","flexWrap":false,"alig... [截断]', NULL, 't', 6, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (329419879432196096, 1, '2026-05-12 06:50:19.582365', 0, NULL, '2026-05-12 06:50:19.582365', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778539260789-kt6a3","type":"lc-div","label":"自定义 Div","props":{"display":"flex","flexDirection":"column","flexWrap":false,"alignItems":"center","justifyContent":"flex-start","gap":0,"padding":0,"minHeight":60,"bgColor":"transparent","borderRadius":0},"children":[{"id":"1778539337520-b5y34","type":"lc-div","label":"自定义 Div","props":{"display":"block","flexDirection":"row","flexWrap":false,"alig... [截断]', NULL, 't', 6, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (329419976261898240, 1, '2026-05-12 06:50:42.668739', 0, NULL, '2026-05-12 06:50:42.668739', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778539260789-kt6a3","type":"lc-div","label":"自定义 Div","props":{"display":"flex","flexDirection":"column","flexWrap":false,"alignItems":"center","justifyContent":"flex-start","gap":0,"padding":0,"minHeight":60,"bgColor":"transparent","borderRadius":0},"children":[{"id":"1778539337520-b5y34","type":"lc-div","label":"自定义 Div","props":{"display":"block","flexDirection":"row","flexWrap":false,"alig... [截断]', NULL, 't', 6, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (329420260035923968, 1, '2026-05-12 06:51:50.325205', 0, NULL, '2026-05-12 06:51:50.325205', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778539260789-kt6a3","type":"lc-div","label":"自定义 Div","props":{"display":"flex","flexDirection":"column","flexWrap":false,"alignItems":"center","justifyContent":"flex-start","gap":0,"padding":0,"minHeight":60,"bgColor":"transparent","borderRadius":0},"children":[{"id":"1778539337520-b5y34","type":"lc-div","label":"自定义 Div","props":{"display":"block","flexDirection":"row","flexWrap":false,"alig... [截断]', NULL, 't', 6, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (329420385508528128, 1, '2026-05-12 06:52:20.240704', 0, NULL, '2026-05-12 06:52:20.240704', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778539260789-kt6a3","type":"lc-div","label":"自定义 Div","props":{"display":"flex","flexDirection":"column","flexWrap":false,"alignItems":"center","justifyContent":"flex-start","gap":0,"padding":0,"minHeight":60,"bgColor":"transparent","borderRadius":0},"children":[{"id":"1778539337520-b5y34","type":"lc-div","label":"自定义 Div","props":{"display":"block","flexDirection":"row","flexWrap":false,"alig... [截断]', NULL, 't', 6, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (329420586398912512, 1, '2026-05-12 06:53:08.136873', 0, NULL, '2026-05-12 06:53:08.136873', 'UPDATE', 'com.fastproject.module.page.controller.PageConfigController', '修改页面', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778539260789-kt6a3","type":"lc-div","label":"自定义 Div","props":{"display":"flex","flexDirection":"column","flexWrap":false,"alignItems":"center","justifyContent":"flex-start","gap":0,"padding":0,"minHeight":60,"bgColor":"transparent","borderRadius":0},"children":[{"id":"1778539337520-b5y34","type":"lc-div","label":"自定义 Div","props":{"display":"block","flexDirection":"row","flexWrap":false,"alig... [截断]', NULL, 't', 5, 'BUSINESS', '/page/config');
INSERT INTO "public"."sys_operation_log" VALUES (329420591251722240, 1, '2026-05-12 06:53:09.293689', 0, NULL, '2026-05-12 06:53:09.293689', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778539260789-kt6a3","type":"lc-div","label":"自定义 Div","props":{"display":"flex","flexDirection":"column","flexWrap":false,"alignItems":"center","justifyContent":"flex-start","gap":0,"padding":0,"minHeight":60,"bgColor":"transparent","borderRadius":0},"children":[{"id":"1778539337520-b5y34","type":"lc-div","label":"自定义 Div","props":{"display":"block","flexDirection":"row","flexWrap":false,"alig... [截断]', NULL, 't', 4, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (329420714815918080, 1, '2026-05-12 06:53:38.753283', 0, NULL, '2026-05-12 06:53:38.753283', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778539260789-kt6a3","type":"lc-div","label":"自定义 Div","props":{"display":"flex","flexDirection":"column","flexWrap":false,"alignItems":"center","justifyContent":"flex-start","gap":0,"padding":0,"minHeight":60,"bgColor":"#e8e8e8","borderRadius":0},"children":[{"id":"1778539337520-b5y34","type":"lc-div","label":"自定义 Div","props":{"display":"block","flexDirection":"row","flexWrap":false,"alignIte... [截断]', NULL, 't', 6, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (329420806620844032, 1, '2026-05-12 06:54:00.641002', 0, NULL, '2026-05-12 06:54:00.641002', 'UPDATE', 'com.fastproject.module.page.controller.PageConfigController', '修改页面', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778539260789-kt6a3","type":"lc-div","label":"自定义 Div","props":{"display":"flex","flexDirection":"column","flexWrap":false,"alignItems":"center","justifyContent":"flex-start","gap":0,"padding":0,"minHeight":60,"bgColor":"#e8e8e8","borderRadius":0},"children":[{"id":"1778539337520-b5y34","type":"lc-div","label":"自定义 Div","props":{"display":"block","flexDirection":"row","flexWrap":false,"alignIte... [截断]', NULL, 't', 4, 'BUSINESS', '/page/config');
INSERT INTO "public"."sys_operation_log" VALUES (329421049672372224, 1, '2026-05-12 06:54:58.589113', 0, NULL, '2026-05-12 06:54:58.589113', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778539260789-kt6a3","type":"lc-div","label":"自定义 Div","props":{"display":"flex","flexDirection":"column","flexWrap":false,"alignItems":"center","justifyContent":"flex-start","gap":0,"padding":0,"minHeight":60,"bgColor":"#e8e8e8","borderRadius":0},"children":[{"id":"1778539337520-b5y34","type":"lc-div","label":"自定义 Div","props":{"display":"block","flexDirection":"row","flexWrap":false,"alignIte... [截断]', NULL, 't', 6, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (329421186402488320, 1, '2026-05-12 06:55:31.18808', 0, NULL, '2026-05-12 06:55:31.18808', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778539260789-kt6a3","type":"lc-div","label":"自定义 Div","props":{"display":"flex","flexDirection":"column","flexWrap":false,"alignItems":"center","justifyContent":"flex-start","gap":0,"padding":0,"minHeight":60,"bgColor":"#e8e8e8","borderRadius":0},"children":[{"id":"1778539337520-b5y34","type":"lc-div","label":"自定义 Div","props":{"display":"block","flexDirection":"row","flexWrap":false,"alignIte... [截断]', NULL, 't', 6, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (329421328601976832, 1, '2026-05-12 06:56:05.091527', 0, NULL, '2026-05-12 06:56:05.091527', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778539260789-kt6a3","type":"lc-div","label":"自定义 Div","props":{"display":"flex","flexDirection":"column","flexWrap":false,"alignItems":"center","justifyContent":"flex-start","gap":0,"padding":0,"minHeight":60,"bgColor":"#e8e8e8","borderRadius":0},"children":[{"id":"1778539337520-b5y34","type":"lc-div","label":"自定义 Div","props":{"display":"block","flexDirection":"row","flexWrap":false,"alignIte... [截断]', NULL, 't', 6, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (329421500639744000, 1, '2026-05-12 06:56:46.108743', 0, NULL, '2026-05-12 06:56:46.108743', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778539260789-kt6a3","type":"lc-div","label":"自定义 Div","props":{"display":"flex","flexDirection":"column","flexWrap":false,"alignItems":"center","justifyContent":"flex-start","gap":0,"padding":0,"minHeight":60,"bgColor":"#e8e8e8","borderRadius":0},"children":[{"id":"1778539337520-b5y34","type":"lc-div","label":"自定义 Div","props":{"display":"block","flexDirection":"row","flexWrap":false,"alignIte... [截断]', NULL, 't', 5, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (329464989700198400, 1, '2026-05-12 09:49:34.708654', 0, NULL, '2026-05-12 09:49:34.708654', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778539260789-kt6a3","type":"lc-div","label":"自定义 Div","props":{"display":"flex","flexDirection":"column","flexWrap":false,"alignItems":"center","justifyContent":"flex-start","gap":0,"padding":0,"minHeight":60,"bgColor":"#e8e8e8","borderRadius":0},"children":[{"id":"1778539337520-b5y34","type":"lc-div","label":"自定义 Div","props":{"display":"block","flexDirection":"row","flexWrap":false,"alignIte... [截断]', NULL, 't', 6, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (331990028307795968, 1, '2026-05-19 09:03:10.825', 0, NULL, '2026-05-19 09:03:10.825', 'UPDATE', 'com.fastproject.module.mall.controller.MallProductCategoryController', '修改分类', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=MallProductCategoryUpdate(id=331984072509362176, name=aa, parentId=null, level=1, icon=, image=, sort=0, showInHome=0, status=1, description=)', NULL, 't', 4, 'BUSINESS', '/mall/category');
INSERT INTO "public"."sys_operation_log" VALUES (329465388825972736, 1, '2026-05-12 09:51:09.867258', 0, NULL, '2026-05-12 09:51:09.867258', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778539260789-kt6a3","type":"lc-div","label":"自定义 Div","props":{"display":"flex","flexDirection":"column","flexWrap":false,"alignItems":"center","justifyContent":"flex-start","gap":0,"padding":0,"minHeight":60,"bgColor":"#e8e8e8","borderRadius":0},"children":[{"id":"1778539337520-b5y34","type":"lc-div","label":"自定义 Div","props":{"display":"block","flexDirection":"row","flexWrap":false,"alignIte... [截断]', NULL, 't', 8, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (329465662462365696, 1, '2026-05-12 09:52:15.107327', 0, NULL, '2026-05-12 09:52:15.107327', 'UPDATE', 'com.fastproject.module.page.controller.PageConfigController', '修改页面', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778539260789-kt6a3","type":"lc-div","label":"自定义 Div","props":{"display":"flex","flexDirection":"column","flexWrap":false,"alignItems":"center","justifyContent":"flex-start","gap":0,"padding":0,"minHeight":60,"bgColor":"#e8e8e8","borderRadius":0},"children":[{"id":"1778539337520-b5y34","type":"lc-div","label":"自定义 Div","props":{"display":"block","flexDirection":"row","flexWrap":false,"alignIte... [截断]', NULL, 't', 6, 'BUSINESS', '/page/config');
INSERT INTO "public"."sys_operation_log" VALUES (329465668422471680, 1, '2026-05-12 09:52:16.528462', 0, NULL, '2026-05-12 09:52:16.528462', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778539260789-kt6a3","type":"lc-div","label":"自定义 Div","props":{"display":"flex","flexDirection":"column","flexWrap":false,"alignItems":"center","justifyContent":"flex-start","gap":0,"padding":0,"minHeight":60,"bgColor":"#e8e8e8","borderRadius":0},"children":[{"id":"1778539337520-b5y34","type":"lc-div","label":"自定义 Div","props":{"display":"block","flexDirection":"row","flexWrap":false,"alignIte... [截断]', NULL, 't', 8, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (329465823758520320, 1, '2026-05-12 09:52:53.563677', 0, NULL, '2026-05-12 09:52:53.563677', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778539260789-kt6a3","type":"lc-div","label":"自定义 Div","props":{"display":"flex","flexDirection":"column","flexWrap":false,"alignItems":"center","justifyContent":"flex-start","gap":0,"padding":0,"minHeight":60,"bgColor":"#e8e8e8","borderRadius":0},"children":[{"id":"1778539337520-b5y34","type":"lc-div","label":"自定义 Div","props":{"display":"block","flexDirection":"row","flexWrap":false,"alignIte... [截断]', NULL, 't', 6, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (329465871112212480, 1, '2026-05-12 09:53:04.853115', 0, NULL, '2026-05-12 09:53:04.853115', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778539260789-kt6a3","type":"lc-div","label":"自定义 Div","props":{"display":"flex","flexDirection":"column","flexWrap":false,"alignItems":"center","justifyContent":"flex-start","gap":0,"padding":0,"minHeight":60,"bgColor":"#e8e8e8","borderRadius":0},"children":[{"id":"1778539337520-b5y34","type":"lc-div","label":"自定义 Div","props":{"display":"block","flexDirection":"row","flexWrap":false,"alignIte... [截断]', NULL, 't', 8, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (329466033498886144, 1, '2026-05-12 09:53:43.569094', 0, NULL, '2026-05-12 09:53:43.569094', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778539260789-kt6a3","type":"lc-div","label":"自定义 Div","props":{"display":"flex","flexDirection":"column","flexWrap":false,"alignItems":"center","justifyContent":"flex-start","gap":0,"padding":0,"minHeight":60,"bgColor":"#e8e8e8","borderRadius":0},"children":[{"id":"1778539337520-b5y34","type":"lc-div","label":"自定义 Div","props":{"display":"block","flexDirection":"row","flexWrap":false,"alignIte... [截断]', NULL, 't', 7, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (329466247395807232, 1, '2026-05-12 09:54:34.566589', 0, NULL, '2026-05-12 09:54:34.566589', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[]}, status=1, version=1.0.1, applicationId=null)', NULL, 't', 7, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (329466517144080384, 1, '2026-05-12 09:55:38.879234', 0, NULL, '2026-05-12 09:55:38.879234', 'UPDATE', 'com.fastproject.module.page.controller.PageConfigController', '修改页面', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778550881868-ge9f2","type":"lc-holy-grail","label":"圣杯布局","props":{"leftWidth":180,"rightWidth":180,"headerHeight":64,"footerHeight":56,"mainMinH":200,"gap":0,"bgColor":"transparent"},"children":[{"id":"1778550881868-k6mov","type":"lc-div","label":"头部","props":{"display":"flex","flexDirection":"column","gap":0,"padding":8,"minHeight":60,"bgColor":"#fafafa","borderRadius":0,"flexWrap":false,"al... [截断]', NULL, 't', 4, 'BUSINESS', '/page/config');
INSERT INTO "public"."sys_operation_log" VALUES (329466564292251648, 1, '2026-05-12 09:55:50.120546', 0, NULL, '2026-05-12 09:55:50.120546', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778550881868-ge9f2","type":"lc-holy-grail","label":"圣杯布局","props":{"leftWidth":180,"rightWidth":180,"headerHeight":64,"footerHeight":56,"mainMinH":200,"gap":0,"bgColor":"transparent"},"children":[{"id":"1778550881868-k6mov","type":"lc-div","label":"头部","props":{"display":"flex","flexDirection":"column","gap":0,"padding":8,"minHeight":60,"bgColor":"#fafafa","borderRadius":0,"flexWrap":false,"al... [截断]', NULL, 't', 9, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (329466657842008064, 1, '2026-05-12 09:56:12.424835', 0, NULL, '2026-05-12 09:56:12.424835', 'UPDATE', 'com.fastproject.module.page.controller.PageConfigController', '修改页面', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778550881868-ge9f2","type":"lc-holy-grail","label":"圣杯布局","props":{"leftWidth":180,"rightWidth":180,"headerHeight":64,"footerHeight":56,"mainMinH":200,"gap":0,"bgColor":"transparent"},"children":[{"id":"1778550881868-k6mov","type":"lc-div","label":"头部","props":{"display":"flex","flexDirection":"column","gap":0,"padding":8,"minHeight":60,"bgColor":"#fafafa","borderRadius":0,"flexWrap":false,"al... [截断]', NULL, 't', 4, 'BUSINESS', '/page/config');
INSERT INTO "public"."sys_operation_log" VALUES (329466668814307328, 1, '2026-05-12 09:56:15.040282', 0, NULL, '2026-05-12 09:56:15.040282', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778550881868-ge9f2","type":"lc-holy-grail","label":"圣杯布局","props":{"leftWidth":180,"rightWidth":180,"headerHeight":64,"footerHeight":56,"mainMinH":200,"gap":0,"bgColor":"transparent"},"children":[{"id":"1778550881868-k6mov","type":"lc-div","label":"头部","props":{"display":"flex","flexDirection":"column","gap":0,"padding":8,"minHeight":60,"bgColor":"#fafafa","borderRadius":0,"flexWrap":false,"al... [截断]', NULL, 't', 4, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (329466979595456512, 1, '2026-05-12 09:57:29.136406', 0, NULL, '2026-05-12 09:57:29.136406', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778550881868-ge9f2","type":"lc-holy-grail","label":"圣杯布局","props":{"leftWidth":180,"rightWidth":180,"headerHeight":64,"footerHeight":56,"mainMinH":200,"gap":0,"bgColor":"transparent"},"children":[{"id":"1778550881868-k6mov","type":"lc-div","label":"头部","props":{"display":"flex","flexDirection":"column","gap":0,"padding":8,"minHeight":60,"bgColor":"#fafafa","borderRadius":0,"flexWrap":false,"al... [截断]', NULL, 't', 7, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (329467157459111936, 1, '2026-05-12 09:58:11.542666', 0, NULL, '2026-05-12 09:58:11.542666', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778550881868-ge9f2","type":"lc-holy-grail","label":"圣杯布局","props":{"leftWidth":180,"rightWidth":180,"headerHeight":64,"footerHeight":56,"mainMinH":200,"gap":0,"bgColor":"#e8e8e8"},"children":[{"id":"1778550881868-k6mov","type":"lc-div","label":"头部","props":{"display":"flex","flexDirection":"column","gap":0,"padding":8,"minHeight":60,"bgColor":"#fafafa","borderRadius":0,"flexWrap":false,"alignI... [截断]', NULL, 't', 6, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (329467504353218560, 1, '2026-05-12 09:59:34.248369', 0, NULL, '2026-05-12 09:59:34.248369', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778550881868-ge9f2","type":"lc-holy-grail","label":"圣杯布局","props":{"leftWidth":180,"rightWidth":180,"headerHeight":64,"footerHeight":56,"mainMinH":200,"gap":0,"bgColor":"#e8e8e8"},"children":[{"id":"1778550881868-k6mov","type":"lc-div","label":"头部","props":{"display":"flex","flexDirection":"column","gap":0,"padding":8,"minHeight":60,"bgColor":"#fafafa","borderRadius":0,"flexWrap":false,"alignI... [截断]', NULL, 't', 6, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (329467671244574720, 1, '2026-05-12 10:00:14.038046', 0, NULL, '2026-05-12 10:00:14.038046', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778550881868-ge9f2","type":"lc-holy-grail","label":"圣杯布局","props":{"leftWidth":180,"rightWidth":180,"headerHeight":64,"footerHeight":56,"mainMinH":200,"gap":0,"bgColor":"#e8e8e8"},"children":[{"id":"1778550881868-k6mov","type":"lc-div","label":"头部","props":{"display":"flex","flexDirection":"column","gap":0,"padding":8,"minHeight":60,"bgColor":"#fafafa","borderRadius":0,"flexWrap":false,"alignI... [截断]', NULL, 't', 7, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (329467783010193408, 1, '2026-05-12 10:00:40.685748', 0, NULL, '2026-05-12 10:00:40.685748', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778550881868-ge9f2","type":"lc-holy-grail","label":"圣杯布局","props":{"leftWidth":180,"rightWidth":180,"headerHeight":64,"footerHeight":56,"mainMinH":200,"gap":0,"bgColor":"#e8e8e8"},"children":[{"id":"1778550881868-k6mov","type":"lc-div","label":"头部","props":{"display":"flex","flexDirection":"column","gap":0,"padding":8,"minHeight":60,"bgColor":"#fafafa","borderRadius":0,"flexWrap":false,"alignI... [截断]', NULL, 't', 5, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (329467863373058048, 1, '2026-05-12 10:00:59.845659', 0, NULL, '2026-05-12 10:00:59.845659', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778550881868-ge9f2","type":"lc-holy-grail","label":"圣杯布局","props":{"leftWidth":180,"rightWidth":180,"headerHeight":64,"footerHeight":56,"mainMinH":200,"gap":0,"bgColor":"#e8e8e8"},"children":[{"id":"1778550881868-k6mov","type":"lc-div","label":"头部","props":{"display":"flex","flexDirection":"column","gap":0,"padding":8,"minHeight":60,"bgColor":"#fafafa","borderRadius":0,"flexWrap":false,"alignI... [截断]', NULL, 't', 6, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (329468537276076032, 1, '2026-05-12 10:03:40.516773', 0, NULL, '2026-05-12 10:03:40.516773', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778550881868-ge9f2","type":"lc-holy-grail","label":"圣杯布局","props":{"leftWidth":180,"rightWidth":180,"headerHeight":64,"footerHeight":56,"mainMinH":200,"gap":0,"bgColor":"#e8e8e8"},"children":[{"id":"1778550881868-k6mov","type":"lc-div","label":"头部","props":{"display":"flex","flexDirection":"column","gap":0,"padding":8,"minHeight":60,"bgColor":"#fafafa","borderRadius":0,"flexWrap":false,"alignI... [截断]', NULL, 't', 6, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (329468639583539200, 1, '2026-05-12 10:04:04.90856', 0, NULL, '2026-05-12 10:04:04.90856', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778550881868-ge9f2","type":"lc-holy-grail","label":"圣杯布局","props":{"leftWidth":180,"rightWidth":180,"headerHeight":64,"footerHeight":56,"mainMinH":200,"gap":0,"bgColor":"#e8e8e8"},"children":[{"id":"1778550881868-k6mov","type":"lc-div","label":"头部","props":{"display":"flex","flexDirection":"column","gap":0,"padding":8,"minHeight":60,"bgColor":"#fafafa","borderRadius":0,"flexWrap":false,"alignI... [截断]', NULL, 't', 6, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (329468705778044928, 1, '2026-05-12 10:04:20.690628', 0, NULL, '2026-05-12 10:04:20.690628', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778550881868-ge9f2","type":"lc-holy-grail","label":"圣杯布局","props":{"leftWidth":180,"rightWidth":180,"headerHeight":64,"footerHeight":56,"mainMinH":200,"gap":0,"bgColor":"#e8e8e8"},"children":[{"id":"1778550881868-k6mov","type":"lc-div","label":"头部","props":{"display":"flex","flexDirection":"column","gap":0,"padding":8,"minHeight":60,"bgColor":"#fafafa","borderRadius":0,"flexWrap":false,"alignI... [截断]', NULL, 't', 7, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (329468950800896000, 1, '2026-05-12 10:05:19.108638', 0, NULL, '2026-05-12 10:05:19.108638', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778550881868-ge9f2","type":"lc-holy-grail","label":"圣杯布局","props":{"leftWidth":180,"rightWidth":180,"headerHeight":64,"footerHeight":200,"mainMinH":200,"gap":0,"bgColor":"#e8e8e8"},"children":[{"id":"1778550881868-k6mov","type":"lc-div","label":"头部","props":{"display":"flex","flexDirection":"column","gap":0,"padding":8,"minHeight":60,"bgColor":"#fafafa","borderRadius":0,"flexWrap":false,"align... [截断]', NULL, 't', 7, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (331997927335661568, 1, '2026-05-19 09:34:34.100255', 0, NULL, '2026-05-19 09:34:34.100255', 'UPDATE', 'com.fastproject.module.mall.controller.MallProductCategoryController', '修改分类', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=MallProductCategoryUpdate(id=331984072509362176, name=aa, parentId=0, level=1, icon=, image=, sort=0, showInHome=0, status=1, description=)', NULL, 't', 10, 'BUSINESS', '/mall/category');
INSERT INTO "public"."sys_operation_log" VALUES (329469049878745088, 1, '2026-05-12 10:05:42.730478', 0, NULL, '2026-05-12 10:05:42.730478', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778550881868-ge9f2","type":"lc-holy-grail","label":"圣杯布局","props":{"leftWidth":180,"rightWidth":180,"headerHeight":64,"footerHeight":20,"mainMinH":200,"gap":0,"bgColor":"#e8e8e8"},"children":[{"id":"1778550881868-k6mov","type":"lc-div","label":"头部","props":{"display":"flex","flexDirection":"column","gap":0,"padding":8,"minHeight":60,"bgColor":"#fafafa","borderRadius":0,"flexWrap":false,"alignI... [截断]', NULL, 't', 11, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (329469328724463616, 1, '2026-05-12 10:06:49.212592', 0, NULL, '2026-05-12 10:06:49.212592', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778550881868-ge9f2","type":"lc-holy-grail","label":"圣杯布局","props":{"leftWidth":180,"rightWidth":180,"headerHeight":64,"footerHeight":20,"mainMinH":200,"gap":0,"bgColor":"#e8e8e8"},"children":[{"id":"1778550881868-k6mov","type":"lc-div","label":"头部","props":{"display":"flex","flexDirection":"column","gap":0,"padding":8,"minHeight":60,"bgColor":"#fafafa","borderRadius":0,"flexWrap":false,"alignI... [截断]', NULL, 't', 8, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (329469673739522048, 1, '2026-05-12 10:08:11.470394', 0, NULL, '2026-05-12 10:08:11.470394', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778551685269-cp3to","type":"lc-div","label":"自定义 Div","props":{"display":"block","flexDirection":"row","flexWrap":false,"alignItems":"stretch","justifyContent":"flex-start","gap":0,"padding":0,"minHeight":60,"bgColor":"transparent","borderRadius":0},"children":[{"id":"1778551688839-tumei","type":"lc-divider","label":"分割线","props":{"text":"","color":"#e8e8e8","margin":16}}]}]}, status=1, versio... [截断]', NULL, 't', 6, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (329469740538007552, 1, '2026-05-12 10:08:27.396873', 0, NULL, '2026-05-12 10:08:27.396873', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[]}, status=1, version=1.0.1, applicationId=null)', NULL, 't', 5, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (329469786927009792, 1, '2026-05-12 10:08:38.45682', 0, NULL, '2026-05-12 10:08:38.45682', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778551717291-n6wfy","type":"lc-div","label":"自定义 Div","props":{"display":"block","flexDirection":"row","flexWrap":false,"alignItems":"stretch","justifyContent":"flex-start","gap":0,"padding":0,"minHeight":60,"bgColor":"transparent","borderRadius":0},"children":[]}]}, status=1, version=1.0.1, applicationId=null)', NULL, 't', 5, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (329469887602888704, 1, '2026-05-12 10:09:02.459768', 0, NULL, '2026-05-12 10:09:02.459768', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778551717291-n6wfy","type":"lc-div","label":"自定义 Div","props":{"display":"block","flexDirection":"row","flexWrap":false,"alignItems":"stretch","justifyContent":"flex-start","gap":0,"padding":0,"minHeight":60,"bgColor":"transparent","borderRadius":0},"children":[{"id":"1778551740341-h67ej","type":"lc-text","label":"文本","props":{"content":"文本内容","fontSize":14,"color":"#333333","fontWeight":"norm... [截断]', NULL, 't', 5, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (329472747396796416, 1, '2026-05-12 10:20:24.287289', 0, NULL, '2026-05-12 10:20:24.287289', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778551717291-n6wfy","type":"lc-div","label":"自定义 Div","props":{"display":"block","flexDirection":"row","flexWrap":false,"alignItems":"stretch","justifyContent":"flex-start","gap":0,"padding":0,"minHeight":60,"bgColor":"transparent","borderRadius":0},"children":[{"id":"1778551740341-h67ej","type":"lc-text","label":"文本","props":{"content":"文本内容","fontSize":14,"color":"#333333","fontWeight":"norm... [截断]', NULL, 't', 7, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (329474889901805568, 1, '2026-05-12 10:28:55.100708', 0, NULL, '2026-05-12 10:28:55.100708', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778551717291-n6wfy","type":"lc-div","label":"自定义 Div","props":{"display":"block","flexDirection":"row","flexWrap":false,"alignItems":"stretch","justifyContent":"flex-start","gap":0,"padding":0,"minHeight":60,"bgColor":"transparent","borderRadius":0},"children":[]},{"id":"1778552375488-7z5al","type":"lc-button","label":"按钮","props":{"label":"按钮","btnType":"primary","size":"middle","block":false... [截断]', NULL, 't', 7, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (329474916044902400, 1, '2026-05-12 10:29:01.333651', 0, NULL, '2026-05-12 10:29:01.333651', 'UPDATE', 'com.fastproject.module.page.controller.PageConfigController', '修改页面', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778552375488-7z5al","type":"lc-button","label":"按钮","props":{"label":"按钮","btnType":"primary","size":"middle","block":false}},{"id":"1778552389208-6rsdd","type":"lc-heading","label":"标题","props":{"content":"q","level":"h2","color":"#1a1a1a","align":"left"}},{"id":"1778552380587-ky10g","type":"lc-button","label":"按钮","props":{"label":"按钮","btnType":"primary","size":"middle","block":false}},{"id... [截断]', NULL, 't', 5, 'BUSINESS', '/page/config');
INSERT INTO "public"."sys_operation_log" VALUES (329474973494284288, 1, '2026-05-12 10:29:15.030398', 0, NULL, '2026-05-12 10:29:15.030398', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778552375488-7z5al","type":"lc-button","label":"按钮","props":{"label":"按钮","btnType":"primary","size":"middle","block":false}},{"id":"1778552389208-6rsdd","type":"lc-heading","label":"标题","props":{"content":"q","level":"h2","color":"#1a1a1a","align":"left"}},{"id":"1778552380587-ky10g","type":"lc-button","label":"按钮","props":{"label":"按钮","btnType":"primary","size":"middle","block":false}},{"id... [截断]', NULL, 't', 6, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (329475097406607360, 1, '2026-05-12 10:29:44.573138', 0, NULL, '2026-05-12 10:29:44.573138', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778552375488-7z5al","type":"lc-button","label":"按钮","props":{"label":"按钮","btnType":"primary","size":"middle","block":false}},{"id":"1778552389208-6rsdd","type":"lc-heading","label":"标题","props":{"content":"q","level":"h2","color":"#1a1a1a","align":"left"}},{"id":"1778552380587-ky10g","type":"lc-button","label":"按钮","props":{"label":"按钮","btnType":"primary","size":"middle","block":false}},{"id... [截断]', NULL, 't', 5, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (329475172736307200, 1, '2026-05-12 10:30:02.533271', 0, NULL, '2026-05-12 10:30:02.533271', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778552974993-te5jx","type":"lc-holy-grail","label":"圣杯布局","props":{"leftWidth":180,"rightWidth":180,"headerHeight":64,"footerHeight":56,"mainMinH":200,"gap":0,"bgColor":"transparent"},"children":[{"id":"1778552974993-qw5wc","type":"lc-div","label":"头部","props":{"display":"flex","flexDirection":"column","gap":0,"padding":8,"minHeight":60,"bgColor":"#fafafa","borderRadius":0,"flexWrap":false,"al... [截断]', NULL, 't', 7, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (329475268941058048, 1, '2026-05-12 10:30:25.470815', 0, NULL, '2026-05-12 10:30:25.470815', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778552375488-7z5al","type":"lc-button","label":"按钮","props":{"label":"按钮","btnType":"primary","size":"middle","block":false}},{"id":"1778552974993-te5jx","type":"lc-holy-grail","label":"圣杯布局","props":{"leftWidth":180,"rightWidth":180,"headerHeight":64,"footerHeight":56,"mainMinH":200,"gap":0,"bgColor":"transparent"},"children":[{"id":"1778552974993-qw5wc","type":"lc-div","label":"头部","props":{... [截断]', NULL, 't', 5, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (329475343851327488, 1, '2026-05-12 10:30:43.330897', 0, NULL, '2026-05-12 10:30:43.330897', 'UPDATE', 'com.fastproject.module.page.controller.PageConfigController', '修改页面', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778552375488-7z5al","type":"lc-button","label":"按钮","props":{"label":"按钮","btnType":"primary","size":"large","block":false}},{"id":"1778552974993-te5jx","type":"lc-holy-grail","label":"圣杯布局","props":{"leftWidth":180,"rightWidth":180,"headerHeight":64,"footerHeight":56,"mainMinH":200,"gap":0,"bgColor":"transparent"},"children":[{"id":"1778552974993-qw5wc","type":"lc-div","label":"头部","props":{"... [截断]', NULL, 't', 4, 'BUSINESS', '/page/config');
INSERT INTO "public"."sys_operation_log" VALUES (329475346342744064, 1, '2026-05-12 10:30:43.924994', 0, NULL, '2026-05-12 10:30:43.924994', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778552375488-7z5al","type":"lc-button","label":"按钮","props":{"label":"按钮","btnType":"primary","size":"large","block":false}},{"id":"1778552974993-te5jx","type":"lc-holy-grail","label":"圣杯布局","props":{"leftWidth":180,"rightWidth":180,"headerHeight":64,"footerHeight":56,"mainMinH":200,"gap":0,"bgColor":"transparent"},"children":[{"id":"1778552974993-qw5wc","type":"lc-div","label":"头部","props":{"... [截断]', NULL, 't', 4, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (329709181663842304, 1, '2026-05-13 01:59:54.60952', 0, NULL, '2026-05-13 01:59:54.60952', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[]}, status=1, version=1.0.1, applicationId=null)', NULL, 't', 28, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (329749142849064960, 1, '2026-05-13 04:38:42.098002', 0, NULL, '2026-05-13 04:38:42.098002', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778608801139-a0z2n","type":"lc-image","label":"图片","props":{"src":"329749056903581696","alt":"","width":100,"height":200,"objectFit":"cover","borderRadius":0}}]}, status=1, version=1.0.1, applicationId=null)', NULL, 't', 10, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (329749515353591808, 1, '2026-05-13 04:40:10.910763', 0, NULL, '2026-05-13 04:40:10.910763', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778608801139-a0z2n","type":"lc-image","label":"图片","props":{"src":"329749056903581696","alt":"","width":null,"height":0,"objectFit":"cover","borderRadius":0}}]}, status=1, version=1.0.1, applicationId=null)', NULL, 't', 7, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (329751589118152704, 1, '2026-05-13 04:48:25.334078', 0, NULL, '2026-05-13 04:48:25.334078', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778608801139-a0z2n","type":"lc-image","label":"图片","props":{"src":"329749056903581696","alt":"","width":100,"height":0,"objectFit":"cover","borderRadius":0}}]}, status=1, version=1.0.1, applicationId=null)', NULL, 't', 8, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (329751665089581056, 1, '2026-05-13 04:48:43.447812', 0, NULL, '2026-05-13 04:48:43.447812', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778608801139-a0z2n","type":"lc-image","label":"图片","props":{"src":"329749056903581696","alt":"","width":50,"height":0,"objectFit":"cover","borderRadius":0}}]}, status=1, version=1.0.1, applicationId=null)', NULL, 't', 6, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (329751717354803200, 1, '2026-05-13 04:48:55.908101', 0, NULL, '2026-05-13 04:48:55.908101', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778608801139-a0z2n","type":"lc-image","label":"图片","props":{"src":"329749056903581696","alt":"","width":10,"height":0,"objectFit":"cover","borderRadius":0}}]}, status=1, version=1.0.1, applicationId=null)', NULL, 't', 8, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (329751930056347648, 1, '2026-05-13 04:49:46.620888', 0, NULL, '2026-05-13 04:49:46.620888', 'UPDATE', 'com.fastproject.module.page.controller.PageConfigController', '修改页面', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778608801139-a0z2n","type":"lc-image","label":"图片","props":{"src":"329749056903581696","alt":"","width":100,"height":300,"objectFit":"cover","borderRadius":0}}]}, status=1, version=1.0.1, applicationId=null)', NULL, 't', 112, 'BUSINESS', '/page/config');
INSERT INTO "public"."sys_operation_log" VALUES (329751935206952960, 1, '2026-05-13 04:49:47.848199', 0, NULL, '2026-05-13 04:49:47.848199', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778608801139-a0z2n","type":"lc-image","label":"图片","props":{"src":"329749056903581696","alt":"","width":100,"height":300,"objectFit":"cover","borderRadius":0}}]}, status=1, version=1.0.1, applicationId=null)', NULL, 't', 6, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (329752057483497472, 1, '2026-05-13 04:50:17.001373', 0, NULL, '2026-05-13 04:50:17.001373', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778608801139-a0z2n","type":"lc-image","label":"图片","props":{"src":"329749056903581696","alt":"啊啊啊","width":100,"height":300,"objectFit":"cover","borderRadius":0}}]}, status=1, version=1.0.1, applicationId=null)', NULL, 't', 6, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (329769788941406208, 1, '2026-05-13 06:00:44.510843', 0, NULL, '2026-05-13 06:00:44.510843', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778608801139-a0z2n","type":"lc-image","label":"图片","props":{"src":"329749056903581696","alt":"啊啊啊","width":100,"height":300,"objectFit":"contain","borderRadius":0}},{"id":"1778623216504-ycxgu","type":"lc-richtext","label":"富文本","props":{"content":"<p>在此编辑富文本内容</p><img data-img-id=\"329749056903581696\">","minHeight":120}}]}, status=1, version=1.0.1, applicationId=null)', NULL, 't', 7, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (329769967706836992, 1, '2026-05-13 06:01:27.131079', 0, NULL, '2026-05-13 06:01:27.131079', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778608801139-a0z2n","type":"lc-image","label":"图片","props":{"src":"329749056903581696","alt":"啊啊啊","width":100,"height":300,"objectFit":"contain","borderRadius":0}},{"id":"1778623216504-ycxgu","type":"lc-richtext","label":"富文本","props":{"content":"<p>在此编辑富文本内容</p><img data-img-id=\"329749056903581696\"><table style=\"min-width: 75px;\"><colgroup><col style=\"min-width: 25px;\"><col style=\"min... [截断]', NULL, 't', 7, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (329835035840614400, 1, '2026-05-13 10:20:00.58325', 0, NULL, '2026-05-13 10:20:00.58325', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778608801139-a0z2n","type":"lc-image","label":"图片","props":{"src":"329749056903581696","alt":"啊啊啊","width":100,"height":300,"objectFit":"contain","borderRadius":0}},{"id":"1778623216504-ycxgu","type":"lc-richtext","label":"富文本","props":{"content":"<p>在此编辑富文本内容</p><img data-img-id=\"329749056903581696\"><table style=\"min-width: 75px;\"><colgroup><col style=\"min-width: 25px;\"><col style=\"min... [截断]', NULL, 't', 3, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (329835298903166976, 1, '2026-05-13 10:21:03.302554', 0, NULL, '2026-05-13 10:21:03.302554', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778608801139-a0z2n","type":"lc-image","label":"图片","props":{"src":"329749056903581696","alt":"啊啊啊","width":100,"height":300,"objectFit":"contain","borderRadius":0}},{"id":"1778623216504-ycxgu","type":"lc-richtext","label":"富文本","props":{"content":"<p style=\"text-align: center;\">在此编辑富文本内容</p><img data-img-id=\"329749056903581696\" data-align=\"center\"><table style=\"min-width: 75px;\"><colgr... [截断]', NULL, 't', 7, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (329847555624144896, 1, '2026-05-13 11:09:45.532579', 0, NULL, '2026-05-13 11:09:45.533094', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778608801139-a0z2n","type":"lc-image","label":"图片","props":{"src":"327635300161556480","alt":"啊啊啊","width":100,"height":300,"objectFit":"contain","borderRadius":0}},{"id":"1778623216504-ycxgu","type":"lc-richtext","label":"富文本","props":{"content":"<p style=\"text-align: center;\">在此编辑富文本内容</p><img data-img-id=\"329749056903581696\" data-align=\"center\"><table style=\"min-width: 75px;\"><colgr... [截断]', NULL, 't', 35, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (329848261072523264, 1, '2026-05-13 11:12:33.724919', 0, NULL, '2026-05-13 11:12:33.724919', 'CREATE', 'com.fastproject.module.page.controller.PageTypeController', '添加页面类型', NULL, 'POST', '127.0.0.1', 'add', 'arg0=PageTypeCreate(title=Vant2, code=vant2, status=1)', NULL, 't', 8, 'BUSINESS', '/page/type');
INSERT INTO "public"."sys_operation_log" VALUES (329848579143372800, 1, '2026-05-13 11:13:49.558128', 0, NULL, '2026-05-13 11:13:49.558128', 'CREATE', 'com.fastproject.module.page.controller.PageComponentController', '添加页面组件', NULL, 'POST', '127.0.0.1', 'add', 'arg0=PageComponentCreate(title=轮播图, icon=327640694992801792, typeId=329848261059940352, code=swipe, status=1, sort=0)', NULL, 't', 13, 'BUSINESS', '/page/component');
INSERT INTO "public"."sys_operation_log" VALUES (329848635338657792, 1, '2026-05-13 11:14:02.956234', 0, NULL, '2026-05-13 11:14:02.956234', 'UPDATE', 'com.fastproject.module.page.controller.PageComponentController', '修改页面组件', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=PageComponentUpdate(id=329848579109818368, title=轮播图, icon=327635300161556480, typeId=329848261059940352, code=swipe, status=1, sort=0)', NULL, 't', 11, 'BUSINESS', '/page/component');
INSERT INTO "public"."sys_operation_log" VALUES (330050945901793280, 1, '2026-05-14 00:37:57.553224', 0, NULL, '2026-05-14 00:37:57.553224', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778608801139-a0z2n","type":"lc-image","label":"图片","props":{"src":"327635300161556480","alt":"啊啊啊","width":100,"height":300,"objectFit":"contain","borderRadius":0}},{"id":"1778623216504-ycxgu","type":"lc-richtext","label":"富文本","props":{"content":"<p style=\"text-align: center;\">在此编辑富文本内容</p><img data-img-id=\"329749056903581696\" data-align=\"center\"><table style=\"min-width: 75px;\"><colgr... [截断]', NULL, 't', 6, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (330051718152851456, 1, '2026-05-14 00:41:01.672993', 0, NULL, '2026-05-14 00:41:01.672993', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778608801139-a0z2n","type":"lc-image","label":"图片","props":{"src":"327635300161556480","alt":"啊啊啊","width":100,"height":300,"objectFit":"contain","borderRadius":0}},{"id":"1778623216504-ycxgu","type":"lc-richtext","label":"富文本","props":{"content":"<p style=\"text-align: center;\">在此编辑富文本内容</p><img data-img-id=\"329749056903581696\" data-align=\"center\"><table style=\"min-width: 75px;\"><colgr... [截断]', NULL, 't', 16, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (330051908108685312, 1, '2026-05-14 00:41:46.961672', 0, NULL, '2026-05-14 00:41:46.961672', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778608801139-a0z2n","type":"lc-image","label":"图片","props":{"src":"327635300161556480","alt":"啊啊啊","width":100,"height":300,"objectFit":"contain","borderRadius":0}},{"id":"1778623216504-ycxgu","type":"lc-richtext","label":"富文本","props":{"content":"<p style=\"text-align: center;\">在此编辑富文本内容</p><img data-img-id=\"329749056903581696\" data-align=\"center\"><table style=\"min-width: 75px;\"><colgr... [截断]', NULL, 't', 5, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (331170376648364032, 1, '2026-05-17 02:46:10.641373', 0, NULL, '2026-05-17 02:46:10.641373', 'CREATE', 'com.fastproject.module.page.controller.PageComponentController', '添加页面组件', NULL, 'POST', '127.0.0.1', 'add', 'arg0=PageComponentCreate(title=轮番图, icon=, typeId=327582763836379136, code=carousel, status=1, sort=0)', NULL, 't', 67, 'BUSINESS', '/page/component');
INSERT INTO "public"."sys_operation_log" VALUES (331170583104589824, 1, '2026-05-17 02:46:59.864319', 0, NULL, '2026-05-17 02:46:59.864319', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778957181604-35y9v","type":"carousel","label":"轮番图","props":{"slides":[{"src":"327640694992801792","alt":"","link":""},{"src":"331170521175691264","alt":"","link":""}],"height":300}}]}, status=1, version=1.0.1, applicationId=null)', NULL, 't', 21, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (331170798754729984, 1, '2026-05-17 02:47:51.279194', 0, NULL, '2026-05-17 02:47:51.279194', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778957181604-35y9v","type":"carousel","label":"轮番图","props":{"slides":[{"src":"327640694992801792","alt":"","link":""},{"src":"331170521175691264","alt":"","link":""}],"height":300},"css":{"width":"800px"}}]}, status=1, version=1.0.1, applicationId=null)', NULL, 't', 9, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (331170918472749056, 1, '2026-05-17 02:48:19.822939', 0, NULL, '2026-05-17 02:48:19.822939', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778957181604-35y9v","type":"carousel","label":"轮番图","props":{"slides":[{"src":"327640694992801792","alt":"","link":""},{"src":"331170521175691264","alt":"","link":""}],"height":300,"arrows":true},"css":{"width":"800px"}}]}, status=1, version=1.0.1, applicationId=null)', NULL, 't', 8, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (331170985250263040, 1, '2026-05-17 02:48:35.743601', 0, NULL, '2026-05-17 02:48:35.743601', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778957181604-35y9v","type":"carousel","label":"轮番图","props":{"slides":[{"src":"327640694992801792","alt":"","link":""},{"src":"331170521175691264","alt":"","link":""}],"height":300,"arrows":false},"css":{"width":"800px"}}]}, status=1, version=1.0.1, applicationId=null)', NULL, 't', 8, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (331171262334373888, 1, '2026-05-17 02:49:41.806328', 0, NULL, '2026-05-17 02:49:41.806328', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778957327518-f5jb0","type":"lc-flex","label":"弹性容器","props":{"direction":"column","gap":12,"wrap":true,"align":"center","padding":0,"bgColor":"transparent"},"children":[{"id":"1778957181604-35y9v","type":"carousel","label":"轮番图","props":{"slides":[{"src":"327640694992801792","alt":"","link":""},{"src":"331170521175691264","alt":"","link":""}],"height":300,"arrows":false},"css":{"width":"800px"... [截断]', NULL, 't', 7, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (331172249459625984, 1, '2026-05-17 02:53:37.154849', 0, NULL, '2026-05-17 02:53:37.154849', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778957327518-f5jb0","type":"lc-flex","label":"弹性容器","props":{"direction":"column","gap":12,"wrap":true,"align":"center","padding":0,"bgColor":"transparent"},"children":[{"id":"1778957181604-35y9v","type":"carousel","label":"轮番图","props":{"slides":[{"src":"327640694992801792","alt":"","link":""},{"src":"331170521175691264","alt":"","link":""}],"height":300,"arrows":false},"css":{"width":"800px"... [截断]', NULL, 't', 7, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (331172543107043328, 1, '2026-05-17 02:54:47.165889', 0, NULL, '2026-05-17 02:54:47.165889', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778957327518-f5jb0","type":"lc-flex","label":"弹性容器","props":{"direction":"column","gap":12,"wrap":true,"align":"center","padding":0,"bgColor":"transparent"},"children":[{"id":"1778957181604-35y9v","type":"carousel","label":"轮番图","props":{"slides":[{"src":"327640694992801792","alt":"","link":""},{"src":"331170521175691264","alt":"","link":""}],"height":300,"arrows":false,"autoplaySpeed":2000},"... [截断]', NULL, 't', 7, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (331970910061989888, 1, '2026-05-19 07:47:12.680481', 0, NULL, '2026-05-19 07:47:12.680481', 'CREATE', 'com.fastproject.module.mall.controller.MallShopController', '添加店铺', NULL, 'POST', '127.0.0.1', 'add', 'arg0=MallShopCreate(name=总店, code=count, logo=329749056903581696, banner=, description=, ownerId=311663478626717696, contactName=, contactPhone=, contactEmail=, address=, status=1, sort=0, remark=)', NULL, 't', 46, 'BUSINESS', '/mall/shop');
INSERT INTO "public"."sys_operation_log" VALUES (331971459826192384, 1, '2026-05-19 07:49:23.754171', 0, NULL, '2026-05-19 07:49:23.754171', 'UPDATE', 'com.fastproject.module.mall.controller.MallShopController', '修改店铺', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=MallShopUpdate(id=331970909915189248, name=总店, code=count, logo=329749056903581696, banner=, description=, ownerId=311663478626717696, contactName=admin, contactPhone=13712341234, contactEmail=, address=, status=1, sort=0, remark=)', NULL, 't', 18, 'BUSINESS', '/mall/shop');
INSERT INTO "public"."sys_operation_log" VALUES (331971886349160448, 1, '2026-05-19 07:51:05.445317', 0, NULL, '2026-05-19 07:51:05.445317', 'UPDATE', 'com.fastproject.module.mall.controller.MallShopController', '修改店铺', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=MallShopUpdate(id=331970909915189248, name=总店, code=count, logo=329749056903581696, banner=, description=<img data-img-id="327640694992801792" width="136px"><p>aaffff</p>, ownerId=311663478626717696, contactName=admin, contactPhone=13712341234, contactEmail=, address=, status=1, sort=0, remark=)', NULL, 't', 6, 'BUSINESS', '/mall/shop');
INSERT INTO "public"."sys_operation_log" VALUES (331972152012181504, 1, '2026-05-19 07:52:08.784357', 0, NULL, '2026-05-19 07:52:08.784357', 'UPDATE', 'com.fastproject.module.mall.controller.MallShopController', '修改店铺', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=MallShopUpdate(id=331970909915189248, name=总店, code=count, logo=329749056903581696, banner=aaa, description=<img data-img-id="327640694992801792" width="136px"><p>aaffff</p>, ownerId=311663478626717696, contactName=admin, contactPhone=13712341234, contactEmail=, address=, status=1, sort=0, remark=)', NULL, 't', 6, 'BUSINESS', '/mall/shop');
INSERT INTO "public"."sys_operation_log" VALUES (331972172413276160, 1, '2026-05-19 07:52:13.648762', 0, NULL, '2026-05-19 07:52:13.648762', 'UPDATE', 'com.fastproject.module.mall.controller.MallShopController', '修改店铺', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=MallShopUpdate(id=331970909915189248, name=总店, code=count, logo=329749056903581696, banner=aaabb, description=<img data-img-id="327640694992801792" width="136px"><p>aaffff</p>, ownerId=311663478626717696, contactName=admin, contactPhone=13712341234, contactEmail=, address=, status=1, sort=0, remark=)', NULL, 't', 7, 'BUSINESS', '/mall/shop');
INSERT INTO "public"."sys_operation_log" VALUES (331983987096555520, 1, '2026-05-19 08:39:10.488841', 0, NULL, '2026-05-19 08:39:10.488841', 'CREATE', 'com.fastproject.module.mall.controller.MallProductCategoryController', '添加分类', NULL, 'POST', '127.0.0.1', 'add', 'arg0=MallProductCategoryCreate(name=衣服, parentId=0, level=1, icon=327640694992801792, image=331170521175691264, sort=0, showInHome=0, status=1, description=)', NULL, 't', 8, 'BUSINESS', '/mall/category');
INSERT INTO "public"."sys_operation_log" VALUES (331984072534528000, 1, '2026-05-19 08:39:30.858722', 0, NULL, '2026-05-19 08:39:30.858722', 'CREATE', 'com.fastproject.module.mall.controller.MallProductCategoryController', '添加分类', NULL, 'POST', '127.0.0.1', 'add', 'arg0=MallProductCategoryCreate(name=aa, parentId=null, level=1, icon=, image=, sort=0, showInHome=0, status=1, description=)', NULL, 't', 6, 'BUSINESS', '/mall/category');
INSERT INTO "public"."sys_operation_log" VALUES (331984149005078528, 1, '2026-05-19 08:39:49.090074', 0, NULL, '2026-05-19 08:39:49.090074', 'UPDATE', 'com.fastproject.module.mall.controller.MallProductCategoryController', '修改分类', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=MallProductCategoryUpdate(id=331984072509362176, name=aa, parentId=null, level=1, icon=, image=, sort=0, showInHome=0, status=1, description=)', NULL, 't', 5, 'BUSINESS', '/mall/category');
INSERT INTO "public"."sys_operation_log" VALUES (331985506999078912, 1, '2026-05-19 08:45:12.861343', 0, NULL, '2026-05-19 08:45:12.861343', 'UPDATE', 'com.fastproject.module.mall.controller.MallProductCategoryController', '修改分类', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=MallProductCategoryUpdate(id=331984072509362176, name=aa, parentId=null, level=1, icon=, image=, sort=0, showInHome=0, status=1, description=)', NULL, 't', 26, 'BUSINESS', '/mall/category');
INSERT INTO "public"."sys_operation_log" VALUES (331998066439753728, 1, '2026-05-19 09:35:07.265888', 0, NULL, '2026-05-19 09:35:07.265888', 'UPDATE', 'com.fastproject.module.mall.controller.MallProductCategoryController', '修改分类', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=MallProductCategoryUpdate(id=331983987067195392, name=衣服, parentId=0, level=1, icon=327640694992801792, image=331170521175691264, sort=0, showInHome=0, status=1, description=)', NULL, 't', 3, 'BUSINESS', '/mall/category');
INSERT INTO "public"."sys_operation_log" VALUES (331998865744072704, 1, '2026-05-19 09:38:17.834124', 0, NULL, '2026-05-19 09:38:17.834124', 'UPDATE', 'com.fastproject.module.mall.controller.MallProductCategoryController', '修改分类', 'com.fastproject.exception.BusinessException: 父级分类不存在
	at com.fastproject.mall.service.impl.MallProductCategoryServiceImpl.lambda$applyParent$0(MallProductCategoryServiceImpl.java:93)
	at java.base/java.util.Optional.orElseThrow(Optional.java:403)
	at com.fastproject.mall.service.impl.MallProductCategoryServiceImpl.applyParent(MallProductCategoryServiceImpl.java:93)
	at com.fastproject.mall.service.impl.MallProductCategoryServiceImpl.update(MallProductCategoryServiceImpl.java:79)
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:104)
	at java.base/java.lang.reflect.Method.invoke(Method.java:565)
	at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:359)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.invokeJoinpoint(ReflectiveMethodInvocation.java:190)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:158)
	at org.springframework.transaction.interceptor.TransactionInterceptor$1.proceedWithInvocation(TransactionInterceptor.java:133)
	at org.springframework.transaction.interceptor.TransactionAspectSupport.invokeWithinTransaction(TransactionAspectSupport.java:371)
	at org.springframework.transaction.interceptor.TransactionInterceptor.invoke(TransactionInterceptor.java:130)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:179)
	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:719)
	at com.fastproject.mall.service.impl.MallProductCategoryServiceImpl$$SpringCGLIB$$0.update(<generated>)
	at com.fastproject.module.mall.controller.MallProductCategoryController.update(MallProductCategoryController.java:43)
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:104)
	at java.base/java.lang.reflect.Method.invoke(Method.java:565)
	at org.springframe... [截断]', 'PUT', '127.0.0.1', 'update', 'arg0=MallProductCategoryUpdate(id=331984072509362176, name=aa, parentId=331983987067195400, level=1, icon=, image=, sort=0, showInHome=0, status=1, description=)', NULL, 'f', 8, 'BUSINESS', '/mall/category');
INSERT INTO "public"."sys_operation_log" VALUES (331998896203108352, 1, '2026-05-19 09:38:25.096422', 0, NULL, '2026-05-19 09:38:25.096422', 'UPDATE', 'com.fastproject.module.mall.controller.MallProductCategoryController', '修改分类', 'com.fastproject.exception.BusinessException: 父级分类不存在
	at com.fastproject.mall.service.impl.MallProductCategoryServiceImpl.lambda$applyParent$0(MallProductCategoryServiceImpl.java:93)
	at java.base/java.util.Optional.orElseThrow(Optional.java:403)
	at com.fastproject.mall.service.impl.MallProductCategoryServiceImpl.applyParent(MallProductCategoryServiceImpl.java:93)
	at com.fastproject.mall.service.impl.MallProductCategoryServiceImpl.update(MallProductCategoryServiceImpl.java:79)
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:104)
	at java.base/java.lang.reflect.Method.invoke(Method.java:565)
	at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:359)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.invokeJoinpoint(ReflectiveMethodInvocation.java:190)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:158)
	at org.springframework.transaction.interceptor.TransactionInterceptor$1.proceedWithInvocation(TransactionInterceptor.java:133)
	at org.springframework.transaction.interceptor.TransactionAspectSupport.invokeWithinTransaction(TransactionAspectSupport.java:371)
	at org.springframework.transaction.interceptor.TransactionInterceptor.invoke(TransactionInterceptor.java:130)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:179)
	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:719)
	at com.fastproject.mall.service.impl.MallProductCategoryServiceImpl$$SpringCGLIB$$0.update(<generated>)
	at com.fastproject.module.mall.controller.MallProductCategoryController.update(MallProductCategoryController.java:43)
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:104)
	at java.base/java.lang.reflect.Method.invoke(Method.java:565)
	at org.springframe... [截断]', 'PUT', '127.0.0.1', 'update', 'arg0=MallProductCategoryUpdate(id=331984072509362176, name=aa, parentId=331983987067195400, level=1, icon=, image=, sort=0, showInHome=0, status=1, description=)', NULL, 'f', 6, 'BUSINESS', '/mall/category');
INSERT INTO "public"."sys_operation_log" VALUES (331999430460968960, 1, '2026-05-19 09:40:32.473428', 0, NULL, '2026-05-19 09:40:32.473428', 'UPDATE', 'com.fastproject.module.mall.controller.MallProductCategoryController', '修改分类', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=MallProductCategoryUpdate(id=331984072509362176, name=aa, parentId=331983987067195392, level=1, icon=, image=, sort=0, showInHome=0, status=1, description=)', NULL, 't', 13, 'BUSINESS', '/mall/category');
INSERT INTO "public"."sys_operation_log" VALUES (331999488048762880, 1, '2026-05-19 09:40:46.203104', 0, NULL, '2026-05-19 09:40:46.203104', 'DELETE', 'com.fastproject.module.mall.controller.MallProductCategoryController', '批量删除分类', NULL, 'DELETE', '127.0.0.1', 'batchDelete', 'arg0=[331983987067195392, 331984072509362176]', NULL, 't', 10, 'BUSINESS', '/mall/category/batch');
INSERT INTO "public"."sys_operation_log" VALUES (332000565687095296, 1, '2026-05-19 09:45:03.132884', 0, NULL, '2026-05-19 09:45:03.132884', 'CREATE', 'com.fastproject.module.mall.controller.MallProductBrandController', '添加品牌', NULL, 'POST', '127.0.0.1', 'add', 'arg0=MallProductBrandCreate(name=aab, code=wwd, logo=331170521175691264, firstLetter=, description=saa, sort=0, showInHome=1, status=1)', NULL, 't', 15, 'BUSINESS', '/mall/brand');
INSERT INTO "public"."sys_operation_log" VALUES (332294395300810752, 1, '2026-05-20 05:12:37.571964', 0, NULL, '2026-05-20 05:12:37.571964', 'CREATE', 'com.fastproject.module.mall.controller.MallProductController', '添加商品', NULL, 'POST', '127.0.0.1', 'add', 'arg0=MallProductCreate(name=啊啊, subTitle=ss, shopId=0, categoryId=0, brandId=0, productSn=, mainImage=327635300161556480, albumImages=327640694992801792,329749056903581696, detail=, price=0, originalPrice=0, costPrice=0, stock=0, sales=0, unit=, weight=0, status=1, isNew=0, isHot=0, isRecommend=0, sort=0, keywords=)', NULL, 't', 32, 'BUSINESS', '/mall/product');
INSERT INTO "public"."sys_operation_log" VALUES (332307034097717248, 1, '2026-05-20 06:02:50.895242', 0, NULL, '2026-05-20 06:02:50.895242', 'UPDATE', 'com.fastproject.module.mall.controller.MallProductController', '修改商品', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=MallProductUpdate(id=332294395187564544, name=啊啊, subTitle=ss, shopId=0, categoryId=0, brandId=0, productSn=, mainImage=327635300161556480, albumImages=327640694992801792,329749056903581696, detail=<table style="min-width: 75px;"><colgroup><col style="min-width: 25px;"><col style="min-width: 25px;"><col style="min-width: 25px;"></colgroup><tbody><tr><th colspan="1" rowspan="1"><p>a</p></th><th colspan="1" rowspan="1"><p>b</p></th><th colspan="1" rowspan="1"><p>c</p></th></tr><tr><td colspan="1" ... [截断]', NULL, 't', 21, 'BUSINESS', '/mall/product');
INSERT INTO "public"."sys_operation_log" VALUES (332309505427771392, 1, '2026-05-20 06:12:40.10675', 0, NULL, '2026-05-20 06:12:40.10675', 'UPDATE', 'com.fastproject.module.mall.controller.MallProductController', '修改商品', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=MallProductUpdate(id=332294395187564544, name=啊啊, subTitle=ss, shopId=331970909915189250, categoryId=0, brandId=0, productSn=, mainImage=327635300161556480, albumImages=327640694992801792,329749056903581696, detail=<table style="min-width: 75px;"><colgroup><col style="min-width: 25px;"><col style="min-width: 25px;"><col style="min-width: 25px;"></colgroup><tbody><tr><th colspan="1" rowspan="1"><p>a</p></th><th colspan="1" rowspan="1"><p>b</p></th><th colspan="1" rowspan="1"><p>c</p></th></tr><tr... [截断]', NULL, 't', 12, 'BUSINESS', '/mall/product');
INSERT INTO "public"."sys_operation_log" VALUES (332310116353314816, 1, '2026-05-20 06:15:05.762567', 0, NULL, '2026-05-20 06:15:05.762567', 'UPDATE', 'com.fastproject.module.mall.controller.MallProductController', '修改商品', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=MallProductUpdate(id=332294395187564544, name=啊啊, subTitle=ss, shopId=null, categoryId=0, brandId=0, productSn=, mainImage=327635300161556480, albumImages=327640694992801792,329749056903581696, detail=<table style="min-width: 75px;"><colgroup><col style="min-width: 25px;"><col style="min-width: 25px;"><col style="min-width: 25px;"></colgroup><tbody><tr><th colspan="1" rowspan="1"><p>a</p></th><th colspan="1" rowspan="1"><p>b</p></th><th colspan="1" rowspan="1"><p>c</p></th></tr><tr><td colspan="... [截断]', NULL, 't', 12, 'BUSINESS', '/mall/product');
INSERT INTO "public"."sys_operation_log" VALUES (332310141401698304, 1, '2026-05-20 06:15:11.734235', 0, NULL, '2026-05-20 06:15:11.734235', 'UPDATE', 'com.fastproject.module.mall.controller.MallProductController', '修改商品', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=MallProductUpdate(id=332294395187564544, name=啊啊, subTitle=ss, shopId=null, categoryId=0, brandId=0, productSn=, mainImage=327635300161556480, albumImages=327640694992801792,329749056903581696, detail=<table style="min-width: 75px;"><colgroup><col style="min-width: 25px;"><col style="min-width: 25px;"><col style="min-width: 25px;"></colgroup><tbody><tr><th colspan="1" rowspan="1"><p>a</p></th><th colspan="1" rowspan="1"><p>b</p></th><th colspan="1" rowspan="1"><p>c</p></th></tr><tr><td colspan="... [截断]', NULL, 't', 12, 'BUSINESS', '/mall/product');
INSERT INTO "public"."sys_operation_log" VALUES (332312019183210496, 1, '2026-05-20 06:22:39.432159', 0, NULL, '2026-05-20 06:22:39.432159', 'CREATE', 'com.fastproject.module.mall.controller.MallShopController', '添加店铺', NULL, 'POST', '127.0.0.1', 'add', 'arg0=MallShopCreate(name=总店2, code=aafff, logo=, banner=, description=, ownerId=null, contactName=, contactPhone=, contactEmail=, address=, status=1, sort=0, remark=)', NULL, 't', 12, 'BUSINESS', '/mall/shop');
INSERT INTO "public"."sys_operation_log" VALUES (332346762155134976, 1, '2026-05-20 08:40:42.802528', 0, NULL, '2026-05-20 08:40:42.802528', 'UPDATE', 'com.fastproject.module.mall.controller.MallProductController', '修改商品', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=MallProductUpdate(id=332344134121689088, name=商品名称, subTitle=副标题, shopId=331970909915189248, categoryId=331983987067195392, brandId=332000565657735168, productSn=admin, mainImage=327640694992801792, albumImages=331170521175691264, detail=<p>aaaaa</p><img data-img-id="327635300161556480" width="100%">, price=0, originalPrice=0, costPrice=0, stock=0, sales=0, unit=, weight=0, status=1, isNew=0, isHot=0, isRecommend=0, sort=0, keywords=)', NULL, 't', 13, 'BUSINESS', '/mall/product');
INSERT INTO "public"."sys_operation_log" VALUES (332313297204416512, 1, '2026-05-20 06:27:44.136421', 0, NULL, '2026-05-20 06:27:44.136421', 'UPDATE', 'com.fastproject.module.mall.controller.MallProductController', '修改商品', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=MallProductUpdate(id=332294395187564544, name=啊啊, subTitle=ss, shopId=332312019170627600, categoryId=0, brandId=0, productSn=, mainImage=327635300161556480, albumImages=327640694992801792,329749056903581696, detail=<table style="min-width: 75px;"><colgroup><col style="min-width: 25px;"><col style="min-width: 25px;"><col style="min-width: 25px;"></colgroup><tbody><tr><th colspan="1" rowspan="1"><p>a</p></th><th colspan="1" rowspan="1"><p>b</p></th><th colspan="1" rowspan="1"><p>c</p></th></tr><tr... [截断]', NULL, 't', 14, 'BUSINESS', '/mall/product');
INSERT INTO "public"."sys_operation_log" VALUES (332313334730854400, 1, '2026-05-20 06:27:53.083457', 0, NULL, '2026-05-20 06:27:53.083457', 'UPDATE', 'com.fastproject.module.mall.controller.MallProductController', '修改商品', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=MallProductUpdate(id=332294395187564544, name=啊啊, subTitle=ss, shopId=331970909915189250, categoryId=0, brandId=0, productSn=, mainImage=327635300161556480, albumImages=327640694992801792,329749056903581696, detail=<table style="min-width: 75px;"><colgroup><col style="min-width: 25px;"><col style="min-width: 25px;"><col style="min-width: 25px;"></colgroup><tbody><tr><th colspan="1" rowspan="1"><p>a</p></th><th colspan="1" rowspan="1"><p>b</p></th><th colspan="1" rowspan="1"><p>c</p></th></tr><tr... [截断]', NULL, 't', 11, 'BUSINESS', '/mall/product');
INSERT INTO "public"."sys_operation_log" VALUES (332313468176830464, 1, '2026-05-20 06:28:24.899077', 0, NULL, '2026-05-20 06:28:24.899077', 'UPDATE', 'com.fastproject.module.mall.controller.MallProductController', '修改商品', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=MallProductUpdate(id=332294395187564544, name=啊啊, subTitle=ss, shopId=332312019170627600, categoryId=0, brandId=0, productSn=, mainImage=327635300161556480, albumImages=327640694992801792,329749056903581696, detail=<table style="min-width: 75px;"><colgroup><col style="min-width: 25px;"><col style="min-width: 25px;"><col style="min-width: 25px;"></colgroup><tbody><tr><th colspan="1" rowspan="1"><p>a</p></th><th colspan="1" rowspan="1"><p>b</p></th><th colspan="1" rowspan="1"><p>c</p></th></tr><tr... [截断]', NULL, 't', 12, 'BUSINESS', '/mall/product');
INSERT INTO "public"."sys_operation_log" VALUES (332315408667381760, 1, '2026-05-20 06:36:07.548683', 0, NULL, '2026-05-20 06:36:07.548683', 'UPDATE', 'com.fastproject.module.mall.controller.MallProductController', '修改商品', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=MallProductUpdate(id=332294395187564544, name=啊啊, subTitle=ss, shopId=332312019170627600, categoryId=331983987067195392, brandId=0, productSn=, mainImage=327635300161556480, albumImages=327640694992801792,329749056903581696, detail=<table style="min-width: 75px;"><colgroup><col style="min-width: 25px;"><col style="min-width: 25px;"><col style="min-width: 25px;"></colgroup><tbody><tr><th colspan="1" rowspan="1"><p>a</p></th><th colspan="1" rowspan="1"><p>b</p></th><th colspan="1" rowspan="1"><p>c... [截断]', NULL, 't', 12, 'BUSINESS', '/mall/product');
INSERT INTO "public"."sys_operation_log" VALUES (332315433002733568, 1, '2026-05-20 06:36:13.35043', 0, NULL, '2026-05-20 06:36:13.35043', 'UPDATE', 'com.fastproject.module.mall.controller.MallProductController', '修改商品', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=MallProductUpdate(id=332294395187564544, name=啊啊, subTitle=ss, shopId=332312019170627600, categoryId=331984072509362176, brandId=0, productSn=, mainImage=327635300161556480, albumImages=327640694992801792,329749056903581696, detail=<table style="min-width: 75px;"><colgroup><col style="min-width: 25px;"><col style="min-width: 25px;"><col style="min-width: 25px;"></colgroup><tbody><tr><th colspan="1" rowspan="1"><p>a</p></th><th colspan="1" rowspan="1"><p>b</p></th><th colspan="1" rowspan="1"><p>c... [截断]', NULL, 't', 12, 'BUSINESS', '/mall/product');
INSERT INTO "public"."sys_operation_log" VALUES (332316424154517504, 1, '2026-05-20 06:40:09.659324', 0, NULL, '2026-05-20 06:40:09.659324', 'UPDATE', 'com.fastproject.module.mall.controller.MallProductController', '修改商品', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=MallProductUpdate(id=332294395187564544, name=啊啊, subTitle=ss, shopId=332312019170627600, categoryId=331984072509362176, brandId=332000565657735168, productSn=, mainImage=327635300161556480, albumImages=327640694992801792,329749056903581696, detail=<table style="min-width: 75px;"><colgroup><col style="min-width: 25px;"><col style="min-width: 25px;"><col style="min-width: 25px;"></colgroup><tbody><tr><th colspan="1" rowspan="1"><p>a</p></th><th colspan="1" rowspan="1"><p>b</p></th><th colspan="1"... [截断]', NULL, 't', 12, 'BUSINESS', '/mall/product');
INSERT INTO "public"."sys_operation_log" VALUES (332316525614731264, 1, '2026-05-20 06:40:33.849767', 0, NULL, '2026-05-20 06:40:33.849767', 'UPDATE', 'com.fastproject.module.mall.controller.MallProductController', '修改商品', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=MallProductUpdate(id=332294395187564544, name=啊啊, subTitle=ss, shopId=332312019170627600, categoryId=331984072509362176, brandId=332000565657735168, productSn=admin, mainImage=327635300161556480, albumImages=327640694992801792,329749056903581696, detail=<table style="min-width: 75px;"><colgroup><col style="min-width: 25px;"><col style="min-width: 25px;"><col style="min-width: 25px;"></colgroup><tbody><tr><th colspan="1" rowspan="1"><p>a</p></th><th colspan="1" rowspan="1"><p>b</p></th><th colspa... [截断]', NULL, 't', 14, 'BUSINESS', '/mall/product');
INSERT INTO "public"."sys_operation_log" VALUES (332316579196964864, 1, '2026-05-20 06:40:46.624611', 0, NULL, '2026-05-20 06:40:46.624611', 'UPDATE', 'com.fastproject.module.mall.controller.MallProductController', '修改商品', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=MallProductUpdate(id=332294395187564544, name=啊啊, subTitle=ss, shopId=332312019170627600, categoryId=331984072509362176, brandId=332000565657735168, productSn=admin, mainImage=327635300161556480, albumImages=327640694992801792,329749056903581696, detail=<table style="min-width: 75px;"><colgroup><col style="min-width: 25px;"><col style="min-width: 25px;"><col style="min-width: 25px;"></colgroup><tbody><tr><th colspan="1" rowspan="1"><p>a</p></th><th colspan="1" rowspan="1"><p>b</p></th><th colspa... [截断]', NULL, 't', 12, 'BUSINESS', '/mall/product');
INSERT INTO "public"."sys_operation_log" VALUES (332323548750286848, 1, '2026-05-20 07:08:28.295889', 0, NULL, '2026-05-20 07:08:28.295889', 'UPDATE', 'com.fastproject.module.mall.controller.MallProductController', '修改商品', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=MallProductUpdate(id=332294395187564544, name=啊啊, subTitle=ss, shopId=null, categoryId=331984072509362176, brandId=332000565657735168, productSn=admin, mainImage=327635300161556480, albumImages=327640694992801792,329749056903581696, detail=<table style="min-width: 75px;"><colgroup><col style="min-width: 25px;"><col style="min-width: 25px;"><col style="min-width: 25px;"></colgroup><tbody><tr><th colspan="1" rowspan="1"><p>a</p></th><th colspan="1" rowspan="1"><p>b</p></th><th colspan="1" rowspan=... [截断]', NULL, 't', 43, 'BUSINESS', '/mall/product');
INSERT INTO "public"."sys_operation_log" VALUES (332323589661528064, 1, '2026-05-20 07:08:38.049589', 0, NULL, '2026-05-20 07:08:38.049589', 'UPDATE', 'com.fastproject.module.mall.controller.MallProductController', '修改商品', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=MallProductUpdate(id=332294395187564544, name=啊啊, subTitle=ss, shopId=331970909915189250, categoryId=331984072509362176, brandId=332000565657735168, productSn=admin, mainImage=327635300161556480, albumImages=327640694992801792,329749056903581696, detail=<table style="min-width: 75px;"><colgroup><col style="min-width: 25px;"><col style="min-width: 25px;"><col style="min-width: 25px;"></colgroup><tbody><tr><th colspan="1" rowspan="1"><p>a</p></th><th colspan="1" rowspan="1"><p>b</p></th><th colspa... [截断]', NULL, 't', 17, 'BUSINESS', '/mall/product');
INSERT INTO "public"."sys_operation_log" VALUES (332328029999206400, 1, '2026-05-20 07:26:16.708005', 0, NULL, '2026-05-20 07:26:16.708005', 'UPDATE', 'com.fastproject.module.mall.controller.MallProductController', '修改商品', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=MallProductUpdate(id=332294395187564544, name=啊啊, subTitle=ss, shopId=331970909915189248, categoryId=331984072509362176, brandId=332000565657735168, productSn=admin, mainImage=327635300161556480, albumImages=327640694992801792,329749056903581696, detail=<table style="min-width: 75px;"><colgroup><col style="min-width: 25px;"><col style="min-width: 25px;"><col style="min-width: 25px;"></colgroup><tbody><tr><th colspan="1" rowspan="1"><p>a</p></th><th colspan="1" rowspan="1"><p>b</p></th><th colspa... [截断]', NULL, 't', 45, 'BUSINESS', '/mall/product');
INSERT INTO "public"."sys_operation_log" VALUES (332328239357890560, 1, '2026-05-20 07:27:06.623015', 0, NULL, '2026-05-20 07:27:06.623015', 'UPDATE', 'com.fastproject.module.mall.controller.MallProductController', '修改商品', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=MallProductUpdate(id=332294395187564544, name=啊啊, subTitle=ss, shopId=331970909915189248, categoryId=331984072509362176, brandId=332000565657735168, productSn=admin, mainImage=327635300161556480, albumImages=327640694992801792,329749056903581696, detail=<table style="min-width: 75px;"><colgroup><col style="min-width: 25px;"><col style="min-width: 25px;"><col style="min-width: 25px;"></colgroup><tbody><tr><th colspan="1" rowspan="1"><p>a</p></th><th colspan="1" rowspan="1"><p>b</p></th><th colspa... [截断]', NULL, 't', 58, 'BUSINESS', '/mall/product');
INSERT INTO "public"."sys_operation_log" VALUES (332341018345213952, 1, '2026-05-20 08:17:53.371806', 0, NULL, '2026-05-20 08:17:53.371806', 'UPDATE', 'com.fastproject.module.mall.controller.MallProductController', '修改商品', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=MallProductUpdate(id=332294395187564544, name=啊啊, subTitle=ss, shopId=331970909915189248, categoryId=331984072509362176, brandId=332000565657735168, productSn=admin, mainImage=327635300161556480, albumImages=327640694992801792,329749056903581696, detail=<table style="min-width: 75px;"><colgroup><col style="min-width: 25px;"><col style="min-width: 25px;"><col style="min-width: 25px;"></colgroup><tbody><tr><th colspan="1" rowspan="1"><p>a</p></th><th colspan="1" rowspan="1"><p>b</p></th><th colspa... [截断]', NULL, 't', 42, 'BUSINESS', '/mall/product');
INSERT INTO "public"."sys_operation_log" VALUES (332341018412322816, 1, '2026-05-20 08:17:53.387885', 0, NULL, '2026-05-20 08:17:53.387885', 'CREATE', 'com.fastproject.module.mall.controller.MallProductSkuController', '添加商品SKU', NULL, 'POST', '127.0.0.1', 'add', 'arg0=MallProductSkuCreate(productId=332294395187564544, shopId=331970909915189250, skuSn=SKU编号, barcode=条形码, specText=规格描述, specs=, image=327640694992801792, payType=1, price=0, originalPrice=0, costPrice=0, pointsPrice=0, giftPoints=0, stock=0, lockStock=0, sales=0, weight=0, volume=0, sort=0, status=1, extra=)', NULL, 't', 7, 'BUSINESS', '/mall/sku');
INSERT INTO "public"."sys_operation_log" VALUES (332341329751314432, 1, '2026-05-20 08:19:07.616167', 0, NULL, '2026-05-20 08:19:07.616167', 'CREATE', 'com.fastproject.module.mall.controller.MallProductController', '添加商品', NULL, 'POST', '127.0.0.1', 'add', 'arg0=MallProductCreate(name=商品名称, subTitle=副标题, shopId=332312019170627584, categoryId=331984072509362176, brandId=332000565657735168, productSn=, mainImage=327640694992801792, albumImages=331170521175691264, detail=<p>bbb</p>, price=0, originalPrice=0, costPrice=0, stock=0, sales=0, unit=, weight=0, status=1, isNew=0, isHot=0, isRecommend=0, sort=0, keywords=aa)', NULL, 't', 9, 'BUSINESS', '/mall/product');
INSERT INTO "public"."sys_operation_log" VALUES (332341329801646080, 1, '2026-05-20 08:19:07.628623', 0, NULL, '2026-05-20 08:19:07.628623', 'CREATE', 'com.fastproject.module.mall.controller.MallProductSkuController', '添加商品SKU', 'com.fastproject.exception.BusinessException: SKU编码已存在
	at com.fastproject.mall.service.impl.MallProductSkuServiceImpl.save(MallProductSkuServiceImpl.java:45)
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:104)
	at java.base/java.lang.reflect.Method.invoke(Method.java:565)
	at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:359)
	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:715)
	at com.fastproject.mall.service.impl.MallProductSkuServiceImpl$$SpringCGLIB$$0.save(<generated>)
	at com.fastproject.module.mall.controller.MallProductSkuController.add(MallProductSkuController.java:35)
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:104)
	at java.base/java.lang.reflect.Method.invoke(Method.java:565)
	at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:359)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.invokeJoinpoint(ReflectiveMethodInvocation.java:190)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:158)
	at org.springframework.aop.aspectj.AspectJAfterThrowingAdvice.invoke(AspectJAfterThrowingAdvice.java:63)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:168)
	at org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint.proceed(MethodInvocationProceedingJoinPoint.java:82)
	at com.fastproject.logs.aspect.LogAspect.around(LogAspect.java:93)
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:104)
	at java.base/java.lang.reflect.Method.invoke(Method.java:565)
	at org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethodWithGivenArgs(AbstractAspectJAdvice.java:648)
	at org.springframework.aop.aspectj.AbstractAspect... [截断]', 'POST', '127.0.0.1', 'add', 'arg0=MallProductSkuCreate(productId=332341329717760000, shopId=332312019170627600, skuSn=SKU编号, barcode=条形码, specText=规格描述, specs=, image=327635300161556480, payType=1, price=0, originalPrice=0, costPrice=0, pointsPrice=0, giftPoints=0, stock=0, lockStock=0, sales=0, weight=0, volume=0, sort=0, status=1, extra=)', NULL, 'f', 4, 'BUSINESS', '/mall/sku');
INSERT INTO "public"."sys_operation_log" VALUES (332341888025759744, 1, '2026-05-20 08:21:20.719107', 0, NULL, '2026-05-20 08:21:20.719107', 'CREATE', 'com.fastproject.module.mall.controller.MallProductController', '添加商品', NULL, 'POST', '127.0.0.1', 'add', 'arg0=MallProductCreate(name=商品名称, subTitle=副标题, shopId=331970909915189248, categoryId=331984072509362176, brandId=332000565657735168, productSn=, mainImage=327640694992801792, albumImages=327635300161556480, detail=<p>aaaa</p>, price=0, originalPrice=0, costPrice=0, stock=0, sales=0, unit=, weight=0, status=1, isNew=0, isHot=0, isRecommend=0, sort=0, keywords=)', NULL, 't', 9, 'BUSINESS', '/mall/product');
INSERT INTO "public"."sys_operation_log" VALUES (332341888076091392, 1, '2026-05-20 08:21:20.731084', 0, NULL, '2026-05-20 08:21:20.731084', 'CREATE', 'com.fastproject.module.mall.controller.MallProductSkuController', '添加商品SKU', NULL, 'POST', '127.0.0.1', 'add', 'arg0=MallProductSkuCreate(productId=332341887988011000, shopId=331970909915189250, skuSn=SKU编号, barcode=条形码, specText=规格描述, specs=, image=331170521175691264, payType=1, price=0, originalPrice=0, costPrice=0, pointsPrice=0, giftPoints=0, stock=0, lockStock=0, sales=0, weight=0, volume=0, sort=0, status=1, extra=)', NULL, 't', 5, 'BUSINESS', '/mall/sku');
INSERT INTO "public"."sys_operation_log" VALUES (332342603087482880, 1, '2026-05-20 08:24:11.203683', 0, NULL, '2026-05-20 08:24:11.203683', 'UPDATE', 'com.fastproject.module.mall.controller.MallProductSkuController', '修改商品SKU', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=MallProductSkuUpdate(id=332341888055119872, productId=332341887988011000, shopId=331970909915189250, skuSn=SKU编号, barcode=条形码, specText=规格描述, specs=, image=331170521175691264, payType=1, price=0, originalPrice=0, costPrice=0, pointsPrice=0, giftPoints=0, stock=0, lockStock=0, sales=0, weight=0, volume=0, sort=0, status=1, extra=)', NULL, 't', 8, 'BUSINESS', '/mall/sku');
INSERT INTO "public"."sys_operation_log" VALUES (332343222271610880, 1, '2026-05-20 08:26:38.828315', 0, NULL, '2026-05-20 08:26:38.828315', 'UPDATE', 'com.fastproject.module.mall.controller.MallProductController', '修改商品', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=MallProductUpdate(id=332341887988011008, name=商品名称, subTitle=副标题, shopId=331970909915189248, categoryId=331984072509362176, brandId=332000565657735168, productSn=, mainImage=327640694992801792, albumImages=327635300161556480, detail=<p>aaaa</p>, price=0, originalPrice=0, costPrice=0, stock=0, sales=0, unit=, weight=0, status=1, isNew=0, isHot=0, isRecommend=0, sort=0, keywords=)', NULL, 't', 12, 'BUSINESS', '/mall/product');
INSERT INTO "public"."sys_operation_log" VALUES (332343222326136832, 1, '2026-05-20 08:26:38.84121', 0, NULL, '2026-05-20 08:26:38.84121', 'UPDATE', 'com.fastproject.module.mall.controller.MallProductSkuController', '修改商品SKU', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=MallProductSkuUpdate(id=332341888055119872, productId=332341887988011008, shopId=331970909915189250, skuSn=SKU编号, barcode=条形码, specText=规格描述, specs=, image=331170521175691264, payType=1, price=0, originalPrice=0, costPrice=0, pointsPrice=0, giftPoints=0, stock=0, lockStock=0, sales=0, weight=0, volume=0, sort=0, status=1, extra=)', NULL, 't', 4, 'BUSINESS', '/mall/sku');
INSERT INTO "public"."sys_operation_log" VALUES (332344134167826432, 1, '2026-05-20 08:30:16.241205', 0, NULL, '2026-05-20 08:30:16.241205', 'CREATE', 'com.fastproject.module.mall.controller.MallProductController', '添加商品', NULL, 'POST', '127.0.0.1', 'add', 'arg0=MallProductCreate(name=商品名称, subTitle=副标题, shopId=331970909915189248, categoryId=331983987067195392, brandId=332000565657735168, productSn=admin, mainImage=327640694992801792, albumImages=331170521175691264, detail=<p>aaaaa</p><img data-img-id="327635300161556480" width="100%">, price=0, originalPrice=0, costPrice=0, stock=0, sales=0, unit=, weight=0, status=1, isNew=0, isHot=0, isRecommend=0, sort=0, keywords=)', NULL, 't', 13, 'BUSINESS', '/mall/product');
INSERT INTO "public"."sys_operation_log" VALUES (332344134218158080, 1, '2026-05-20 08:30:16.253198', 0, NULL, '2026-05-20 08:30:16.253198', 'CREATE', 'com.fastproject.module.mall.controller.MallProductSkuController', '添加商品SKU', NULL, 'POST', '127.0.0.1', 'add', 'arg0=MallProductSkuCreate(productId=332344134121689088, shopId=331970909915189248, skuSn=SKU编号, barcode=条形码, specText=规格描述, specs=, image=327635300161556480, payType=null, price=0, originalPrice=0, costPrice=0, pointsPrice=0, giftPoints=0, stock=0, lockStock=0, sales=0, weight=0, volume=0, sort=0, status=1, extra=)', NULL, 't', 3, 'BUSINESS', '/mall/sku');
INSERT INTO "public"."sys_operation_log" VALUES (332346762201272320, 1, '2026-05-20 08:40:42.813348', 0, NULL, '2026-05-20 08:40:42.813348', 'UPDATE', 'com.fastproject.module.mall.controller.MallProductSkuController', '修改商品SKU', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=MallProductSkuUpdate(id=332344134205575168, productId=332344134121689088, shopId=331970909915189248, skuSn=SKU编号, barcode=条形码, specText=规格描述, specs=, image=327635300161556480, payType=1, price=0, originalPrice=0, costPrice=0, pointsPrice=0, giftPoints=0, stock=0, lockStock=0, sales=0, weight=0, volume=0, sort=0, status=1, extra=)', NULL, 't', 5, 'BUSINESS', '/mall/sku');
INSERT INTO "public"."sys_operation_log" VALUES (332700800415567872, 1, '2026-05-21 08:07:32.101456', 0, NULL, '2026-05-21 08:07:32.101456', 'CREATE', 'com.fastproject.module.page.controller.PageComponentController', '添加页面组件', NULL, 'POST', '127.0.0.1', 'add', 'arg0=PageComponentCreate(title=顶部信息栏, icon=, typeId=327582763836379136, code=top_information_bar, status=1, sort=0)', NULL, 't', 50, 'BUSINESS', '/page/component');
INSERT INTO "public"."sys_operation_log" VALUES (332701087406624768, 1, '2026-05-21 08:08:40.525139', 0, NULL, '2026-05-21 08:08:40.525139', 'UPDATE', 'com.fastproject.module.page.controller.PageConfigController', '修改页面', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778957327518-f5jb0","type":"lc-flex","label":"弹性容器","props":{"direction":"column","gap":12,"wrap":true,"align":"center","padding":0,"bgColor":"transparent"},"children":[{"id":"1779322069293-whq1k","type":"top_information_bar","label":"顶部信息栏","props":{},"css":{"width":"100%"}},{"id":"1778957181604-35y9v","type":"carousel","label":"轮番图","props":{"slides":[{"src":"327640694992801792","alt":"","li... [截断]', NULL, 't', 13, 'BUSINESS', '/page/config');
INSERT INTO "public"."sys_operation_log" VALUES (332712581561192448, 1, '2026-05-21 08:54:20.945625', 0, NULL, '2026-05-21 08:54:20.945625', 'UPDATE', 'com.fastproject.module.page.controller.PageComponentController', '修改页面组件', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=PageComponentUpdate(id=332700800243601408, title=商城顶部信息栏, icon=, typeId=327582763836379136, code=mall_top_information_bar, status=1, sort=0)', NULL, 't', 17, 'BUSINESS', '/page/component');
INSERT INTO "public"."sys_operation_log" VALUES (332714057960394752, 1, '2026-05-21 09:00:12.946793', 0, NULL, '2026-05-21 09:00:12.946793', 'UPDATE', 'com.fastproject.module.page.controller.PageConfigController', '修改页面', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778957327518-f5jb0","type":"lc-flex","label":"弹性容器","props":{"direction":"column","gap":12,"wrap":true,"align":"center","padding":0,"bgColor":"transparent"},"children":[{"id":"1778957181604-35y9v","type":"carousel","label":"轮番图","props":{"slides":[{"src":"327640694992801792","alt":"","link":""},{"src":"331170521175691264","alt":"","link":""}],"height":300,"arrows":false,"autoplaySpeed":2000},"... [截断]', NULL, 't', 6, 'BUSINESS', '/page/config');
INSERT INTO "public"."sys_operation_log" VALUES (332714172716552192, 1, '2026-05-21 09:00:40.306289', 0, NULL, '2026-05-21 09:00:40.306289', 'UPDATE', 'com.fastproject.module.page.controller.PageConfigController', '修改页面', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778957327518-f5jb0","type":"lc-flex","label":"弹性容器","props":{"direction":"column","gap":12,"wrap":true,"align":"center","padding":0,"bgColor":"transparent"},"children":[{"id":"1779325224108-r9qpk","type":"mall_top_information_bar","label":"商城顶部信息栏","props":{}},{"id":"1778957181604-35y9v","type":"carousel","label":"轮番图","props":{"slides":[{"src":"327640694992801792","alt":"","link":""},{"src":"... [截断]', NULL, 't', 4, 'BUSINESS', '/page/config');
INSERT INTO "public"."sys_operation_log" VALUES (332714251611410432, 1, '2026-05-21 09:00:59.116982', 0, NULL, '2026-05-21 09:00:59.116982', 'UPDATE', 'com.fastproject.module.page.controller.PageConfigController', '修改页面', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778957327518-f5jb0","type":"lc-flex","label":"弹性容器","props":{"direction":"column","gap":12,"wrap":true,"align":"center","padding":0,"bgColor":"transparent"},"children":[{"id":"1779325224108-r9qpk","type":"mall_top_information_bar","label":"商城顶部信息栏","props":{},"css":{"width":"100%"}},{"id":"1778957181604-35y9v","type":"carousel","label":"轮番图","props":{"slides":[{"src":"327640694992801792","alt"... [截断]', NULL, 't', 7, 'BUSINESS', '/page/config');
INSERT INTO "public"."sys_operation_log" VALUES (332719874386300928, 1, '2026-05-21 09:23:19.690261', 0, NULL, '2026-05-21 09:23:19.690261', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778957327518-f5jb0","type":"lc-flex","label":"弹性容器","props":{"direction":"column","gap":12,"wrap":true,"align":"center","padding":0,"bgColor":"transparent"},"children":[{"id":"1779325224108-r9qpk","type":"mall_top_information_bar","label":"商城顶部信息栏","props":{},"css":{"width":"100%"},"children":[{"id":"1779325454332-9z9tm","type":"lc-div","label":"自定义 Div","props":{"display":"block","flexDirecti... [截断]', NULL, 't', 17, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (332722659391574016, 1, '2026-05-21 09:34:23.687328', 0, NULL, '2026-05-21 09:34:23.687328', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778957327518-f5jb0","type":"lc-flex","label":"弹性容器","props":{"direction":"column","gap":12,"wrap":true,"align":"center","padding":0,"bgColor":"transparent"},"children":[{"id":"1779325224108-r9qpk","type":"mall_top_information_bar","label":"商城顶部信息栏","props":{},"css":{"width":"100%","max-width":"1600px"},"children":[{"id":"1779325454332-9z9tm","type":"lc-div","label":"自定义 Div","props":{"display"... [截断]', NULL, 't', 9, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (332722970592153600, 1, '2026-05-21 09:35:37.8835', 0, NULL, '2026-05-21 09:35:37.8835', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778957327518-f5jb0","type":"lc-flex","label":"弹性容器","props":{"direction":"column","gap":12,"wrap":true,"align":"center","padding":0,"bgColor":"transparent"},"children":[{"id":"1779325224108-r9qpk","type":"mall_top_information_bar","label":"商城顶部信息栏","props":{},"css":{"width":"100%","max-width":"1600px"},"children":[{"id":"1779325454332-9z9tm","type":"lc-div","label":"自定义 Div","props":{"display"... [截断]', NULL, 't', 7, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (332742123487105024, 1, '2026-05-21 10:51:44.28944', 0, NULL, '2026-05-21 10:51:44.28944', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778957327518-f5jb0","type":"lc-flex","label":"弹性容器","props":{"direction":"column","gap":12,"wrap":true,"align":"center","padding":0,"bgColor":"transparent"},"children":[{"id":"1779325454332-9z9tm","type":"lc-div","label":"自定义 Div","props":{"display":"block","flexDirection":"row","flexWrap":false,"alignItems":"stretch","justifyContent":"flex-start","gap":0,"padding":0,"minHeight":0,"bgColor":""... [截断]', NULL, 't', 7, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (332742267649527808, 1, '2026-05-21 10:52:18.660704', 0, NULL, '2026-05-21 10:52:18.660704', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778957327518-f5jb0","type":"lc-flex","label":"弹性容器","props":{"direction":"column","gap":12,"wrap":true,"align":"center","padding":0,"bgColor":"transparent"},"children":[{"id":"1779325454332-9z9tm","type":"lc-div","label":"自定义 Div","props":{"display":"block","flexDirection":"row","flexWrap":false,"alignItems":"stretch","justifyContent":"flex-start","gap":0,"padding":0,"minHeight":0,"bgColor":""... [截断]', NULL, 't', 5, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (332747109143023616, 1, '2026-05-21 11:11:32.96212', 0, NULL, '2026-05-21 11:11:32.96212', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778957327518-f5jb0","type":"lc-flex","label":"弹性容器","props":{"direction":"column","gap":12,"wrap":true,"align":"center","padding":0,"bgColor":"transparent"},"children":[{"id":"1779325454332-9z9tm","type":"lc-div","label":"自定义 Div","props":{"display":"block","flexDirection":"row","flexWrap":false,"alignItems":"stretch","justifyContent":"flex-start","gap":0,"padding":0,"minHeight":0,"bgColor":""... [截断]', NULL, 't', 8, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (332747384150953984, 1, '2026-05-21 11:12:38.529405', 0, NULL, '2026-05-21 11:12:38.529405', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778957327518-f5jb0","type":"lc-flex","label":"弹性容器","props":{"direction":"column","gap":12,"wrap":true,"align":"center","padding":0,"bgColor":"transparent"},"children":[{"id":"1779325454332-9z9tm","type":"lc-div","label":"自定义 Div","props":{"display":"block","flexDirection":"row","flexWrap":false,"alignItems":"stretch","justifyContent":"flex-start","gap":0,"padding":0,"minHeight":0,"bgColor":""... [截断]', NULL, 't', 15, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (332748401299034112, 1, '2026-05-21 11:16:41.036171', 0, NULL, '2026-05-21 11:16:41.036171', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778957327518-f5jb0","type":"lc-flex","label":"弹性容器","props":{"direction":"column","gap":12,"wrap":true,"align":"center","padding":0,"bgColor":"transparent"},"children":[{"id":"1779325454332-9z9tm","type":"lc-div","label":"自定义 Div","props":{"display":"block","flexDirection":"row","flexWrap":false,"alignItems":"stretch","justifyContent":"flex-start","gap":0,"padding":0,"minHeight":0,"bgColor":""... [截断]', NULL, 't', 10, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (332748490734178304, 1, '2026-05-21 11:17:02.359193', 0, NULL, '2026-05-21 11:17:02.359193', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1778957327518-f5jb0","type":"lc-flex","label":"弹性容器","props":{"direction":"column","gap":12,"wrap":true,"align":"center","padding":0,"bgColor":"transparent"},"children":[{"id":"1779325454332-9z9tm","type":"lc-div","label":"自定义 Div","props":{"display":"block","flexDirection":"row","flexWrap":false,"alignItems":"stretch","justifyContent":"flex-start","gap":0,"padding":0,"minHeight":0,"bgColor":""... [截断]', NULL, 't', 2, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (332754768390918144, 1, '2026-05-21 11:41:59.06909', 0, NULL, '2026-05-21 11:41:59.06909', 'UPDATE', 'com.fastproject.module.page.controller.PageConfigController', '修改页面', NULL, 'PUT', '127.0.0.1', 'update', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1779325224108-r9qpk","type":"mall_top_information_bar","label":"商城顶部信息栏","props":{"navItems":[]},"children":[]},{"id":"1778957327518-f5jb0","type":"lc-flex","label":"弹性容器","props":{"direction":"column","gap":12,"wrap":true,"align":"center","padding":0,"bgColor":"transparent"},"children":[{"id":"1779325454332-9z9tm","type":"lc-div","label":"自定义 Div","props":{"display":"block","flexDirection":"ro... [截断]', NULL, 't', 5, 'BUSINESS', '/page/config');
INSERT INTO "public"."sys_operation_log" VALUES (332754770664230912, 1, '2026-05-21 11:41:59.611138', 0, NULL, '2026-05-21 11:41:59.611138', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1779325224108-r9qpk","type":"mall_top_information_bar","label":"商城顶部信息栏","props":{"navItems":[]},"children":[]},{"id":"1778957327518-f5jb0","type":"lc-flex","label":"弹性容器","props":{"direction":"column","gap":12,"wrap":true,"align":"center","padding":0,"bgColor":"transparent"},"children":[{"id":"1779325454332-9z9tm","type":"lc-div","label":"自定义 Div","props":{"display":"block","flexDirection":"ro... [截断]', NULL, 't', 12, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (332754879405756416, 1, '2026-05-21 11:42:25.537907', 0, NULL, '2026-05-21 11:42:25.537907', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1779325454332-9z9tm","type":"lc-div","label":"自定义 Div","props":{"display":"block","flexDirection":"row","flexWrap":false,"alignItems":"stretch","justifyContent":"flex-start","gap":0,"padding":0,"minHeight":0,"bgColor":"","borderRadius":0,"sticky":"top"},"children":[{"id":"1779325224108-r9qpk","type":"mall_top_information_bar","label":"商城顶部信息栏","props":{"navItems":[]},"children":[]}],"css":{"wid... [截断]', NULL, 't', 6, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (332762430855843840, 1, '2026-05-21 12:12:25.943266', 0, NULL, '2026-05-21 12:12:25.943266', 'CREATE', 'com.fastproject.module.page.controller.PageConfigController', '发布页面', NULL, 'POST', '127.0.0.1', 'publish', 'arg0=PageConfigUpdate(id=328249358409666560, title=首页, pathUrl=/home, config={"version":"1","nodes":[{"id":"1779325454332-9z9tm","type":"lc-div","label":"自定义 Div","props":{"display":"block","flexDirection":"row","flexWrap":false,"alignItems":"stretch","justifyContent":"flex-start","gap":0,"padding":0,"minHeight":0,"bgColor":"","borderRadius":0,"sticky":"top"},"children":[{"id":"1779325224108-r9qpk","type":"mall_top_information_bar","label":"商城顶部信息栏","props":{"navItems":[]},"children":[]}],"css":{"wid... [截断]', NULL, 't', 4, 'BUSINESS', '/page/config/publish');
INSERT INTO "public"."sys_operation_log" VALUES (333034754976911360, 1, '2026-05-22 06:14:33.073741', 0, NULL, '2026-05-22 06:14:33.073741', 'LOGOUT', 'com.fastproject.module.system.controller.AuthController', '用户退出', NULL, 'POST', '127.0.0.1', 'logout', '{}', NULL, 't', 13, 'LOGIN', '/auth/logout');
INSERT INTO "public"."sys_operation_log" VALUES (333034878994092032, NULL, '2026-05-22 06:15:02.641889', 0, NULL, '2026-05-22 06:15:02.641889', 'LOGIN', 'com.fastproject.module.system.controller.AuthController', '用户登录', NULL, 'POST', '127.0.0.1', 'login', 'arg0=LoginDto(username=admin, password=123456, captchaKey=93e90b73fabf4c0fab868db015e8a0bd, captcha=meym)', NULL, 't', 109, 'LOGIN', '/auth/login');

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
