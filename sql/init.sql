
CREATE DATABASE `wed-kanban` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin;

USE `wed-kanban`;

/*Table structure for table `auth` */

DROP TABLE IF EXISTS `auth`;

CREATE TABLE `auth` (
  `user` varchar(20) CHARACTER SET utf8 NOT NULL COMMENT '域账号',
  `sid` bigint(20) NOT NULL COMMENT '空间id',
  `privilege` varchar(1) CHARACTER SET cp1250 DEFAULT NULL COMMENT '权限：s为对空间有编辑权限，对空间内卡片有读写权限，m，对空间有查看权限，对卡片有读写权限，r为只读权限',
  PRIMARY KEY (`user`,`sid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `authority_content` */

DROP TABLE IF EXISTS `authority_content`;

CREATE TABLE `authority_content` (
  `user` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '用户ID',
  `content` varchar(4096) COLLATE utf8_bin DEFAULT NULL COMMENT '用户权限JSON串'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `blacklist` */

DROP TABLE IF EXISTS `blacklist`;

CREATE TABLE `blacklist` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `customer_id` varchar(18) NOT NULL COMMENT '用户id',
  `start_date` datetime NOT NULL COMMENT '生效开始时间',
  `duration` int(11) DEFAULT NULL COMMENT '持续时间',
  `reason` varchar(31) NOT NULL COMMENT '列入黑名单原因',
  `plate_id` varchar(31) DEFAULT NULL COMMENT '被禁板块',
  `operate_type` tinyint(4) DEFAULT NULL COMMENT '被禁操作',
  `created` varchar(20) DEFAULT NULL,
  `created_date` datetime NOT NULL,
  `created_ip` varchar(64) DEFAULT NULL,
  `modified` varchar(20) DEFAULT NULL,
  `modified_date` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `modified_ip` varchar(64) DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `blacklist_customer_id` (`customer_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='黑名单';

/*Table structure for table `card_attr_instance` */

DROP TABLE IF EXISTS `card_attr_instance`;

CREATE TABLE `card_attr_instance` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `card_id` bigint(20) DEFAULT NULL COMMENT '所属卡片ID',
  `space_id` bigint(20) DEFAULT NULL COMMENT '所属空间',
  `template_id` bigint(20) DEFAULT NULL COMMENT '模板ID',
  `card_type` tinyint(4) DEFAULT NULL COMMENT '卡片类型',
  `attr_id` bigint(20) DEFAULT NULL COMMENT '所属自定义属性',
  `attr_value` text DEFAULT NULL COMMENT '自定义属性值',
  `creator` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier` varchar(32) DEFAULT NULL COMMENT '修改者',
  `modify_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  `parent_id` bigint(20) DEFAULT NULL,
  `status` tinyint(4) NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`),
  UNIQUE KEY `card_id` (`card_id`,`attr_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='卡片属性值';


/*Table structure for table `card_instance` */

DROP TABLE IF EXISTS `card_instance`;

CREATE TABLE `card_instance` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `space_id` bigint(20) DEFAULT NULL COMMENT '所属空间',
  `card_type` tinyint(4) DEFAULT NULL COMMENT '卡片类型',
  `card_title` varchar(128) DEFAULT NULL COMMENT '卡片标题',
  `template_id` bigint(20) DEFAULT NULL COMMENT '模板ID',
  `card_desc` varchar(1024) DEFAULT NULL COMMENT '卡片描述',
  `sprint` int(11) NOT NULL DEFAULT 0 COMMENT '迭代',
  `life_status` varchar(32) DEFAULT NULL COMMENT '生命周期状态',
  `end_life` varchar(32) DEFAULT NULL COMMENT '生命周期结束状态定义',
  `attr_values` varchar(1024) DEFAULT NULL COMMENT '自定义属性值（保留）',
  `creator` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier` varchar(32) DEFAULT NULL COMMENT '修改者',
  `modify_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  `status` tinyint(4) NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`),
  KEY `sprint` (`space_id`,`sprint`) USING BTREE,
  KEY `space_id_2` (`space_id`,`template_id`,`sprint`) USING BTREE,
  KEY `space_id` (`space_id`,`template_id`,`sprint`,`card_type`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='卡片实例';


/*Table structure for table `card_template` */

DROP TABLE IF EXISTS `card_template`;

CREATE TABLE `card_template` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `card_type` tinyint(4) DEFAULT NULL COMMENT '卡片类型',
  `space_id` bigint(20) DEFAULT NULL COMMENT '所属空间',
  `parent_template` bigint(20) DEFAULT NULL COMMENT '父模板',
  `template_flag` tinyint(4) DEFAULT NULL COMMENT '软删除（保留）',
  `creator` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier` varchar(32) DEFAULT NULL COMMENT '修改者',
  `modify_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  `status` tinyint(4) NOT NULL DEFAULT 1,
  `name` varchar(255) NOT NULL COMMENT '模版名',
  PRIMARY KEY (`id`),
  UNIQUE KEY `space_type_uk` (`space_id`,`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='卡片模板';


/*Table structure for table `card_template_attr` */

DROP TABLE IF EXISTS `card_template_attr`;

CREATE TABLE `card_template_attr` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `template_id` bigint(20) DEFAULT NULL COMMENT '所属模板ID',
  `attr_label` varchar(128) DEFAULT NULL COMMENT '属性label',
  `input_type` varchar(32) DEFAULT NULL COMMENT '空间类型',
  `input_option` varchar(1024) DEFAULT NULL COMMENT '空间选项',
  `default_value` varchar(256) DEFAULT NULL COMMENT '默认值',
  `creator` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier` varchar(32) DEFAULT NULL COMMENT '修改者',
  `modify_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  `status` tinyint(4) NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`),
  UNIQUE KEY `for_index` (`template_id`,`attr_label`) USING BTREE,
  KEY `template_id` (`template_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='卡片属性模板';


/*Table structure for table `comment` */

DROP TABLE IF EXISTS `comment`;

CREATE TABLE `comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '评论id',
  `customer_id` varchar(18) NOT NULL COMMENT '用户id',
  `nick_name` varchar(31) NOT NULL COMMENT '用户昵称',
  `plate_id` varchar(31) NOT NULL DEFAULT '' COMMENT '板块id',
  `plate_name` varchar(255) NOT NULL COMMENT '板块名称',
  `original_content` varchar(1023) DEFAULT NULL COMMENT '原始评论内容',
  `disposed_content` varchar(1023) DEFAULT NULL COMMENT '处理后评论内容',
  `reply_count` int(11) DEFAULT 0 COMMENT '回复次数',
  `praise_count` int(11) DEFAULT 0 COMMENT '点赞次数',
  `last_reply_date` datetime DEFAULT NULL COMMENT '最后回复时间',
  `top_bottom` tinyint(4) DEFAULT NULL COMMENT '特殊位置',
  `sort_value` smallint(6) DEFAULT NULL COMMENT '特殊位置排序权值',
  `created` varchar(20) DEFAULT NULL,
  `created_date` datetime NOT NULL,
  `created_ip` varchar(64) DEFAULT NULL,
  `modified` varchar(20) DEFAULT NULL,
  `modified_date` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `modified_ip` varchar(64) DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `comment_created_date` (`created_date`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='评论表';

/*Table structure for table `comment1` */

DROP TABLE IF EXISTS `comment1`;

CREATE TABLE `comment1` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '评论id',
  `customer_id` varchar(18) NOT NULL COMMENT '用户id',
  `nick_name` varchar(31) NOT NULL,
  `plate_id` varchar(31) NOT NULL DEFAULT '' COMMENT '板块id',
  `original_content` varchar(1023) DEFAULT NULL COMMENT '原始评论内容',
  `disposed_content` varchar(1023) DEFAULT NULL COMMENT '处理后评论内容',
  `reply_count` int(11) DEFAULT 0 COMMENT '回复次数',
  `praise_count` int(11) DEFAULT 0 COMMENT '点赞次数',
  `last_reply_date` datetime NOT NULL COMMENT '最后回复时间',
  `top_bottom` tinyint(4) DEFAULT NULL COMMENT '特殊位置',
  `sort_value` smallint(6) DEFAULT NULL COMMENT '特殊位置排序权值',
  `created` varchar(20) DEFAULT NULL,
  `created_date` datetime NOT NULL,
  `created_ip` varchar(64) DEFAULT NULL,
  `modified` varchar(20) DEFAULT NULL,
  `modified_date` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `modified_ip` varchar(64) DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `comment_created_date` (`created_date`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='评论表';

/*Table structure for table `comment2` */

DROP TABLE IF EXISTS `comment2`;

CREATE TABLE `comment2` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '评论id',
  `customer_id` varchar(18) NOT NULL COMMENT '用户id',
  `nick_name` varchar(31) NOT NULL COMMENT '用户昵称',
  `plate_id` varchar(31) NOT NULL DEFAULT '' COMMENT '板块id',
  `original_content` varchar(1023) DEFAULT NULL COMMENT '原始评论内容',
  `disposed_content` varchar(1023) DEFAULT NULL COMMENT '处理后评论内容',
  `reply_count` int(11) DEFAULT 0 COMMENT '回复次数',
  `praise_count` int(11) DEFAULT 0 COMMENT '点赞次数',
  `last_reply_date` datetime NOT NULL COMMENT '最后回复时间',
  `top_bottom` tinyint(4) DEFAULT NULL COMMENT '特殊位置',
  `sort_value` smallint(6) DEFAULT NULL COMMENT '特殊位置排序权值',
  `created` varchar(20) DEFAULT NULL,
  `created_date` datetime NOT NULL,
  `created_ip` varchar(64) DEFAULT NULL,
  `modified` varchar(20) DEFAULT NULL,
  `modified_date` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `modified_ip` varchar(64) DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `comment_created_date` (`created_date`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='评论表';

/*Table structure for table `comment_article` */

DROP TABLE IF EXISTS `comment_article`;

CREATE TABLE `comment_article` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '文章id',
  `customer_id` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者id',
  `biz_sys` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '业务系统代码',
  `biz_type` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '业务类型',
  `biz_id` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '业务标识',
  `type` char(2) COLLATE utf8_bin DEFAULT NULL COMMENT '文章类型（虚拟文章、真实文章)',
  `title` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '文章标题',
  `summary` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '文章摘要',
  `original_content` text COLLATE utf8_bin DEFAULT NULL COMMENT '原始评论内容',
  `disposed_content` text COLLATE utf8_bin DEFAULT NULL COMMENT '处理后评论内容',
  `repost_article_id` bigint(20) DEFAULT NULL COMMENT '转发原文章ID',
  `repost_article_title` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '转发原文章主题',
  `reply_no` int(11) DEFAULT 0 COMMENT '评论次数',
  `praise_no` int(11) DEFAULT 0 COMMENT '点赞次数',
  `is_recommend` tinyint(3) DEFAULT 0 COMMENT '是否推荐文章 0-不推荐 1-推荐',
  `status` tinyint(3) DEFAULT 0 COMMENT '0-初始 1-审核通过 2--审核不通过',
  `created_date` datetime NOT NULL DEFAULT current_timestamp() COMMENT '创建时间',
  `modified_date` datetime NOT NULL DEFAULT current_timestamp() COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_article_bizsys_biztype_bizid` (`biz_sys`,`biz_type`,`biz_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='文章表';

/*Table structure for table `comment_blacklist` */

DROP TABLE IF EXISTS `comment_blacklist`;

CREATE TABLE `comment_blacklist` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `customer_id` varchar(18) COLLATE utf8_bin NOT NULL COMMENT '用户id',
  `reason` varchar(31) COLLATE utf8_bin NOT NULL COMMENT '列入黑名单原因',
  `created` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '添加人',
  `modified` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `status` tinyint(3) DEFAULT 1 COMMENT '0-无效 1-有效',
  `created_date` datetime NOT NULL DEFAULT current_timestamp() COMMENT '创建时间',
  `modified_date` datetime NOT NULL DEFAULT current_timestamp() COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `customer_id` (`customer_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='黑名单';

/*Table structure for table `comment_config` */

DROP TABLE IF EXISTS `comment_config`;

CREATE TABLE `comment_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `biz_type` varchar(31) COLLATE utf8_bin NOT NULL COMMENT '业务类型',
  `config_key` varchar(31) COLLATE utf8_bin NOT NULL COMMENT '配置key',
  `config_value` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '配置value',
  `config_desc` varchar(31) COLLATE utf8_bin NOT NULL COMMENT '配置描述',
  `created_date` datetime NOT NULL DEFAULT current_timestamp() COMMENT '创建时间',
  `modified_date` datetime NOT NULL DEFAULT current_timestamp() COMMENT '修改时间',
  `status` tinyint(3) DEFAULT 1 COMMENT '0-无效 1-有效',
  PRIMARY KEY (`id`),
  UNIQUE KEY `type_key` (`biz_type`,`config_key`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='配置表';

/*Table structure for table `comment_count` */

DROP TABLE IF EXISTS `comment_count`;

CREATE TABLE `comment_count` (
  `plate_id` varchar(31) NOT NULL COMMENT '板块id',
  `comment_count` int(11) DEFAULT 0 COMMENT '评论次数',
  `created_date` datetime NOT NULL,
  `modified_date` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`plate_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='板块评论计数表';

/*Table structure for table `comment_picture` */

DROP TABLE IF EXISTS `comment_picture`;

CREATE TABLE `comment_picture` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `article_id` bigint(20) NOT NULL COMMENT '文章ID',
  `customer_id` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '用户id',
  `image_url` varchar(256) COLLATE utf8_bin NOT NULL COMMENT '图片url',
  `image_size` varchar(30) COLLATE utf8_bin NOT NULL COMMENT '图片大小',
  `status` tinyint(3) DEFAULT 1 COMMENT '0-初始 1-审核通过 2--审核不通过',
  `created_date` datetime NOT NULL DEFAULT current_timestamp() COMMENT '创建时间',
  `modified_date` datetime NOT NULL DEFAULT current_timestamp() COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `reply_articleid_status` (`article_id`,`status`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='评论图片表';

/*Table structure for table `comment_praise` */

DROP TABLE IF EXISTS `comment_praise`;

CREATE TABLE `comment_praise` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `article_id` bigint(20) DEFAULT 0 COMMENT '文章id',
  `reply_id` bigint(20) DEFAULT 0 COMMENT '评论id',
  `customer_id` varchar(18) COLLATE utf8_bin NOT NULL COMMENT '用户id',
  `status` tinyint(3) DEFAULT 1 COMMENT '是否有效，1有效，0无效',
  `created_date` datetime NOT NULL DEFAULT current_timestamp() COMMENT '创建时间',
  `modified_date` datetime NOT NULL DEFAULT current_timestamp() COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='点赞表';

/*Table structure for table `comment_reply` */

DROP TABLE IF EXISTS `comment_reply`;

CREATE TABLE `comment_reply` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `article_id` bigint(20) DEFAULT 0 COMMENT '文章ID',
  `customer_id` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '用户id',
  `to_customer_id` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '被评论用户id',
  `original_content` text COLLATE utf8_bin DEFAULT NULL COMMENT '原始评论内容',
  `disposed_content` text COLLATE utf8_bin DEFAULT NULL COMMENT '处理后评论内容',
  `parent_id` bigint(20) DEFAULT 0 COMMENT '评论ID',
  `root_reply_id` bigint(20) DEFAULT 0 COMMENT '一级评论ID',
  `praise_no` int(11) DEFAULT 0 COMMENT '点赞次数',
  `status` tinyint(3) DEFAULT 0 COMMENT '0-初始 1-审核通过 2--审核不通过',
  `created_date` datetime NOT NULL DEFAULT current_timestamp() COMMENT '创建时间',
  `modified_date` datetime NOT NULL DEFAULT current_timestamp() COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `reply_articleid_status` (`article_id`,`status`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='评论表';

/*Table structure for table `comment_report` */

DROP TABLE IF EXISTS `comment_report`;

CREATE TABLE `comment_report` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `source_id` varchar(18) COLLATE utf8_bin NOT NULL COMMENT '发起举报用户id',
  `target_id` varchar(18) COLLATE utf8_bin NOT NULL COMMENT '被举报用户id',
  `article_id` varchar(31) COLLATE utf8_bin NOT NULL COMMENT '文章id',
  `reply_id` bigint(20) DEFAULT NULL COMMENT '回复id',
  `status` tinyint(3) DEFAULT 0 COMMENT '0未处理，1不做处理，2删除，3删除并且禁言',
  `created_date` datetime NOT NULL DEFAULT current_timestamp() COMMENT '创建时间',
  `modified_date` datetime NOT NULL DEFAULT current_timestamp() COMMENT '修改时间',
  `created` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '添加人',
  `modified` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`),
  KEY `IDX_report_reply_id` (`reply_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='举报表';

/*Table structure for table `comment_sensitiveword` */

DROP TABLE IF EXISTS `comment_sensitiveword`;

CREATE TABLE `comment_sensitiveword` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `comment` varchar(31) COLLATE utf8_bin NOT NULL COMMENT '敏感词内容',
  `level` tinyint(4) DEFAULT 0 COMMENT '敏感等级',
  `created_date` datetime NOT NULL DEFAULT current_timestamp() COMMENT '创建时间',
  `modified_date` datetime NOT NULL DEFAULT current_timestamp() COMMENT '修改时间',
  `status` tinyint(3) DEFAULT 1 COMMENT '0-无效 1-有效',
  PRIMARY KEY (`id`),
  UNIQUE KEY `comment` (`comment`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='敏感词';

/*Table structure for table `comment_topic` */

DROP TABLE IF EXISTS `comment_topic`;

CREATE TABLE `comment_topic` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '标题',
  `summary` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '摘要',
  `biz_sys` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '业务系统代码',
  `biz_type` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '业务类型',
  `biz_id` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '业务标识',
  `type` tinyint(3) DEFAULT 0 COMMENT '主题类型0--虚拟主题、1--真实主题',
  `article_num` bigint(20) DEFAULT 0 COMMENT '主键',
  `status` tinyint(3) DEFAULT 0 COMMENT '主题状态 0-初始 1-审核通过 2-审核不通过',
  `created_date` datetime NOT NULL DEFAULT current_timestamp() COMMENT '创建时间',
  `modified_date` datetime NOT NULL DEFAULT current_timestamp() COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_topic_bizsys_biztype_bizid` (`biz_sys`,`biz_type`,`biz_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='主题表';

/*Table structure for table `comment_topic_article` */

DROP TABLE IF EXISTS `comment_topic_article`;

CREATE TABLE `comment_topic_article` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '关系ID',
  `topic_id` bigint(20) NOT NULL COMMENT '主题ID',
  `article_id` bigint(20) NOT NULL COMMENT '文章ID',
  `status` tinyint(3) DEFAULT 1 COMMENT '0-无效 1-有效',
  `created_date` datetime NOT NULL DEFAULT current_timestamp() COMMENT '创建时间',
  `modified_date` datetime NOT NULL DEFAULT current_timestamp() COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='主题文章关系表';

/*Table structure for table `config` */

DROP TABLE IF EXISTS `config`;

CREATE TABLE `config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `config_key` varchar(31) NOT NULL COMMENT '配置key',
  `config_value` varchar(255) NOT NULL COMMENT '配置value',
  `config_desc` varchar(31) NOT NULL COMMENT '配置描述',
  `config_type` varchar(15) NOT NULL COMMENT '配置分类',
  `created_date` datetime NOT NULL,
  `modified_date` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`),
  UNIQUE KEY `config_key` (`config_key`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='配置表';

/*Table structure for table `op_log` */

DROP TABLE IF EXISTS `op_log`;

CREATE TABLE `op_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `op_type` varchar(32) DEFAULT NULL COMMENT '操作类型',
  `op_object` bigint(20) DEFAULT NULL COMMENT '操作对象（空间ID，卡片ID）',
  `operator` varchar(32) DEFAULT NULL COMMENT '操作者',
  `init_status` varchar(32) DEFAULT NULL COMMENT '初始状态',
  `change_status` varchar(32) DEFAULT NULL COMMENT '操作后状态',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  `creator` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier` varchar(32) DEFAULT NULL COMMENT '修改者',
  `modify_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='操作日志';

/*Table structure for table `plate` */

DROP TABLE IF EXISTS `plate`;

CREATE TABLE `plate` (
  `id` varchar(31) NOT NULL COMMENT '业务id，同时作为板块id',
  `name` varchar(255) NOT NULL COMMENT '板块名称',
  `visitable` tinyint(4) DEFAULT 1 COMMENT '是否可访问',
  `submitable` tinyint(4) DEFAULT 1 COMMENT '是否可提交',
  `operate_option` tinyint(4) DEFAULT NULL COMMENT '操作选项',
  `sort_value` smallint(6) DEFAULT NULL COMMENT '排序选项',
  `created` varchar(20) DEFAULT NULL,
  `created_date` datetime NOT NULL,
  `created_ip` varchar(64) DEFAULT NULL,
  `modified` varchar(20) DEFAULT NULL,
  `modified_date` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `modified_ip` varchar(64) DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='板块表';

/*Table structure for table `praise` */

DROP TABLE IF EXISTS `praise`;

CREATE TABLE `praise` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `comment_id` bigint(20) DEFAULT NULL COMMENT '评论id',
  `customer_id` varchar(18) NOT NULL COMMENT '用户id',
  `status` tinyint(4) DEFAULT 1 COMMENT '是否有效，1有效，0无效',
  `created_date` datetime NOT NULL,
  `modified_date` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`),
  UNIQUE KEY `praise_count_index` (`comment_id`,`customer_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='点赞表';

/*Table structure for table `praise_count` */

DROP TABLE IF EXISTS `praise_count`;

CREATE TABLE `praise_count` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `comment_id` bigint(20) DEFAULT NULL COMMENT '评论id',
  `customer_id` varchar(18) NOT NULL COMMENT '用户id',
  `status` tinyint(4) DEFAULT 1 COMMENT '是否有效，1有效，0无效',
  `created_date` datetime NOT NULL,
  `modified_date` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`),
  UNIQUE KEY `praise_count_index` (`comment_id`,`customer_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='点赞表';

/*Table structure for table `reply` */

DROP TABLE IF EXISTS `reply`;

CREATE TABLE `reply` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `customer_id` varchar(18) NOT NULL COMMENT '用户id',
  `plate_id` varchar(31) NOT NULL COMMENT '板块id',
  `plate_name` varchar(255) NOT NULL COMMENT '板块名称',
  `nick_name` varchar(31) NOT NULL COMMENT '用户昵称',
  `comment_id` bigint(20) NOT NULL COMMENT '被回复评论id',
  `replay_id` bigint(20) DEFAULT NULL COMMENT '被回复回复id',
  `to_customer_id` varchar(18) NOT NULL COMMENT '被回复用户id',
  `to_user_nick_name` varchar(31) NOT NULL COMMENT '被回复用户昵称',
  `original_content` varchar(1023) NOT NULL COMMENT '回复原始内容',
  `disposed_content` varchar(1023) NOT NULL COMMENT '回复处理后内容',
  `created` varchar(20) DEFAULT NULL,
  `created_date` datetime NOT NULL,
  `created_ip` varchar(64) DEFAULT NULL,
  `modified` varchar(20) DEFAULT NULL,
  `modified_date` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `modified_ip` varchar(64) DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `reply_comment_id` (`comment_id`) USING BTREE,
  KEY `reply_created_date` (`created_date`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='回复表';

/*Table structure for table `report` */

DROP TABLE IF EXISTS `report`;

CREATE TABLE `report` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `source_id` varchar(18) NOT NULL COMMENT '发起举报用户id',
  `target_id` varchar(18) NOT NULL COMMENT '被举报用户id',
  `plate_id` varchar(31) NOT NULL COMMENT '板块id',
  `comment_id` bigint(20) DEFAULT NULL COMMENT '评论id',
  `reply_id` bigint(20) DEFAULT NULL COMMENT '回复id',
  `content` varchar(1023) NOT NULL COMMENT '被举报内容',
  `content_created_date` datetime NOT NULL COMMENT '被举报内容的创建时间',
  `status` tinyint(4) DEFAULT 0 COMMENT '0未处理，1不做处理，2删除，3删除并且禁言',
  `created_date` datetime NOT NULL,
  `modified_date` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `created` varchar(20) DEFAULT NULL,
  `modified` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_report_comment_id` (`comment_id`) USING BTREE,
  KEY `IDX_report_reply_id` (`reply_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='举报表';

/*Table structure for table `sensitive_word` */

DROP TABLE IF EXISTS `sensitive_word`;

CREATE TABLE `sensitive_word` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `comment` varchar(31) NOT NULL COMMENT '敏感词内容',
  `level` tinyint(4) DEFAULT 0 COMMENT '敏感等级',
  `type` tinyint(4) DEFAULT NULL COMMENT '所属类型',
  `created_date` datetime NOT NULL,
  `modified_date` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='敏感词';

/*Table structure for table `space` */

DROP TABLE IF EXISTS `space`;

CREATE TABLE `space` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `space_name` varchar(64) DEFAULT NULL COMMENT '空间名称',
  `space_pin` varchar(64) NOT NULL COMMENT '唯一标识——探讨是否需要（唯一约束）',
  `space_desc` varchar(128) DEFAULT NULL COMMENT '空间描述',
  `encrypt` tinyint(4) DEFAULT NULL COMMENT '是否加密（保留）',
  `production` varchar(64) DEFAULT NULL COMMENT '产品线',
  `organization` varchar(64) DEFAULT NULL COMMENT '机构',
  `life_def` varchar(64) DEFAULT NULL COMMENT '生命周期定义（保留）',
  `parent_space` bigint(20) DEFAULT NULL COMMENT '父空间',
  `root_space` bigint(20) DEFAULT NULL COMMENT '顶层空间',
  `creator` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier` varchar(32) DEFAULT NULL COMMENT '修改者',
  `modify_time` timestamp NOT NULL DEFAULT current_timestamp() COMMENT '最后修改时间',
  `status` tinyint(4) NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNIQUE_pin` (`space_pin`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='空间';

/*Data for the table `space` */

insert  into `space`(`id`,`space_name`,`space_pin`,`space_desc`,`encrypt`,`production`,`organization`,`life_def`,`parent_space`,`root_space`,`creator`,`create_time`,`modifier`,`modify_time`,`status`) values (-1,'root','root','root',NULL,NULL,NULL,NULL,NULL,NULL,'SYSTEM','1970-12-31 23:59:59','SYSTEM','1970-12-31 23:59:59',0);

/*Table structure for table `sprint` */

DROP TABLE IF EXISTS `sprint`;

CREATE TABLE `sprint` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `space_id` bigint(20) DEFAULT NULL COMMENT '空间ID',
  `sprint` int(11) DEFAULT NULL COMMENT 'SPRINT',
  `active` int(11) DEFAULT NULL COMMENT '活动SPRINT',
  KEY `id` (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

/*Table structure for table `t_user` */

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '记录自增',
  `jdpin` varchar(20) NOT NULL COMMENT 'jdpin',
  `name` varchar(20) DEFAULT NULL COMMENT '姓名',
  `telephone` varchar(15) DEFAULT NULL COMMENT '电话号码',
  `type` int(2) NOT NULL DEFAULT 1 COMMENT '用户类型 1：运营  2：KOL',
  `status` int(1) NOT NULL DEFAULT 1 COMMENT '用户状态 1：正常 0：注销  2：锁定',
  `created` varchar(20) NOT NULL COMMENT '创建人',
  `created_date` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '创建时间',
  `modified` varchar(20) DEFAULT NULL COMMENT '修改人',
  `modified_date` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `jdpin` (`jdpin`) USING BTREE,
  KEY `type` (`type`) USING BTREE,
  KEY `status` (`status`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `template_attr_for_index` */

DROP TABLE IF EXISTS `template_attr_for_index`;

CREATE TABLE `template_attr_for_index` (
  `template_id` bigint(20) NOT NULL,
  `attr_label` varchar(255) NOT NULL,
  `attr_id` bigint(20) DEFAULT NULL,
  `index` bigint(20) NOT NULL DEFAULT 0,
  `show` tinyint(4) NOT NULL DEFAULT 1,
  `status` tinyint(4) NOT NULL DEFAULT 1,
  PRIMARY KEY (`template_id`,`attr_label`),
  UNIQUE KEY `template_id` (`template_id`,`attr_label`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `template_attr_sort` */

DROP TABLE IF EXISTS `template_attr_sort`;

CREATE TABLE `template_attr_sort` (
  `template_id` int(11) NOT NULL,
  `template_attr_sort` varchar(255) COLLATE utf8_bin NOT NULL,
  `status` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '1',
  PRIMARY KEY (`template_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `test` */

DROP TABLE IF EXISTS `test`;

CREATE TABLE `test` (
  `aa` bigint(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (`aa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` varchar(32) NOT NULL COMMENT '用户ID',
  `user_name` varchar(128) NOT NULL COMMENT '用户名称',
  `user_pwd` varchar(32) NOT NULL COMMENT '用户密码',
  `user_type` tinyint(1) NOT NULL DEFAULT 1 COMMENT '用户类型 0:管理员 1:普通用户',
  `creator` varchar(32) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modifier` varchar(32) DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '用户状态 0:无效 1：有效',
  `permissions` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `USER_ID_IDX` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `user` */

insert  into `user`(`id`,`user_id`,`user_name`,`user_pwd`,`user_type`,`creator`,`create_time`,`modifier`,`modify_time`,`status`,`permissions`) values (1,'admin','admin','21232F297A57A5A743894A0E4A801FC3',0,'SYSTEM','1970-12-31 23:59:59','SYSTEM','1970-12-31 23:59:59',1,NULL);
