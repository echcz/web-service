CREATE TABLE `action_log` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
    `tenant_name` varchar(50) NOT NULL DEFAULT '' COMMENT '租户名',
    `actor_name` varchar(50) NOT NULL DEFAULT '' COMMENT '操作者名',
    `actor_roles` varchar(255) NOT NULL DEFAULT '' COMMENT '操作者拥有的角色，用逗号分隔',
    `group` varchar(50) NOT NULL DEFAULT '' COMMENT '操作组',
    `name` varchar(100) NOT NULL DEFAULT '' COMMENT '操作名',
    `type` varchar(50) NOT NULL DEFAULT 'COMMAND' COMMENT '操作类型',
    `content` text COMMENT '操作内容',
    `action_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
    PRIMARY KEY (`id`),
    KEY `idx_tenant_action_time` (`tenant_name`, `action_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='操作日志';

CREATE TABLE `document` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
    `tenant_name` varchar(50) NOT NULL DEFAULT '' COMMENT '租户名',
    `name` varchar(100) NOT NULL COMMENT '文档名称',
    `file_path` varchar(255) NOT NULL DEFAULT '' COMMENT '文件地址',
    `username` varchar(50) NOT NULL DEFAULT '' COMMENT '用户名',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_name` (`tenant_name`, `name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文档';