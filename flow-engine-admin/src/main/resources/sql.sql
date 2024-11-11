-- 流程引擎表
DROP TABLE IF EXISTS `flow_engine`;
CREATE TABLE `flow_engine` (
                               `id` bigint NOT NULL AUTO_INCREMENT,
                               `name` varchar(64) NOT NULL COMMENT '引擎名称，唯一',
                               `description` varchar(256) NOT NULL DEFAULT '' COMMENT '描述',
                               `content` longtext COMMENT '引擎配置内容，必须是xml形式',
                               `status` varchar(32) NOT NULL COMMENT '状态',
                               `create_time` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                               PRIMARY KEY (`id`),
                               UNIQUE KEY `uk_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='引擎配置表';


INSERT INTO `flow_engine`.`flow_engine` (`name`, `description`, `content`, `status`, `create_time`, `update_time`) VALUES ('测试1', '测试', '测试', 'up', '2024-11-08 16:57:32', '2024-11-08 16:57:32');
INSERT INTO `flow_engine`.`flow_engine` (`name`, `description`, `content`, `status`, `create_time`, `update_time`) VALUES ('测试2', '测试', '测试', 'up', '2024-11-08 16:57:32', '2024-11-08 16:57:32');
INSERT INTO `flow_engine`.`flow_engine` (`name`, `description`, `content`, `status`, `create_time`, `update_time`) VALUES ('测试3', '测试', '测试', 'up', '2024-11-08 16:57:32', '2024-11-08 16:57:32');
INSERT INTO `flow_engine`.`flow_engine` (`name`, `description`, `content`, `status`, `create_time`, `update_time`) VALUES ('测试4', '测试', '测试', 'up', '2024-11-08 16:57:32', '2024-11-08 16:57:32');
INSERT INTO `flow_engine`.`flow_engine` (`name`, `description`, `content`, `status`, `create_time`, `update_time`) VALUES ('测试5', '测试', '测试', 'up', '2024-11-08 16:57:32', '2024-11-08 16:57:32');
INSERT INTO `flow_engine`.`flow_engine` (`name`, `description`, `content`, `status`, `create_time`, `update_time`) VALUES ('测试6', '测试', '测试', 'up', '2024-11-08 16:57:32', '2024-11-08 16:57:32');
INSERT INTO `flow_engine`.`flow_engine` (`name`, `description`, `content`, `status`, `create_time`, `update_time`) VALUES ('测试7', '测试', '测试', 'up', '2024-11-08 16:57:32', '2024-11-08 16:57:32');
INSERT INTO `flow_engine`.`flow_engine` (`name`, `description`, `content`, `status`, `create_time`, `update_time`) VALUES ('测试8', '测试', '测试', 'up', '2024-11-08 16:57:32', '2024-11-08 16:57:32');
INSERT INTO `flow_engine`.`flow_engine` (`name`, `description`, `content`, `status`, `create_time`, `update_time`) VALUES ('测试9', '测试', '测试', 'up', '2024-11-08 16:57:32', '2024-11-08 16:57:32');
INSERT INTO `flow_engine`.`flow_engine` (`name`, `description`, `content`, `status`, `create_time`, `update_time`) VALUES ('测试10', '测试', '测试', 'up', '2024-11-08 16:57:32', '2024-11-08 16:57:32');
INSERT INTO `flow_engine`.`flow_engine` (`name`, `description`, `content`, `status`, `create_time`, `update_time`) VALUES ('测试11', '测试', '测试', 'up', '2024-11-08 16:57:32', '2024-11-08 16:57:32');
INSERT INTO `flow_engine`.`flow_engine` (`name`, `description`, `content`, `status`, `create_time`, `update_time`) VALUES ('测试14', '测试', '测试', 'up', '2024-11-08 16:57:32', '2024-11-08 16:57:32');
INSERT INTO `flow_engine`.`flow_engine` (`name`, `description`, `content`, `status`, `create_time`, `update_time`) VALUES ('测试12', '测试', '测试', 'up', '2024-11-08 16:57:32', '2024-11-08 16:57:32');
INSERT INTO `flow_engine`.`flow_engine` (`name`, `description`, `content`, `status`, `create_time`, `update_time`) VALUES ('测试13', '测试', '测试', 'up', '2024-11-08 16:57:32', '2024-11-08 16:57:32');
INSERT INTO `flow_engine`.`flow_engine` (`name`, `description`, `content`, `status`, `create_time`, `update_time`) VALUES ('测试15', '测试', '测试', 'up', '2024-11-08 16:57:32', '2024-11-08 16:57:32');
