-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        5.7.21 - MySQL Community Server (GPL)
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  9.5.0.5196
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- 导出 adolph 的数据库结构
CREATE DATABASE IF NOT EXISTS `adolph` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `adolph`;

-- 导出  表 adolph.t_consistency_participant 结构
CREATE TABLE IF NOT EXISTS `t_consistency_participant` (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `txId` bigint(20) NOT NULL COMMENT '事务ID',
  `exchangeName` varchar(50) NOT NULL COMMENT 'exchange名称',
  `routingKey` varchar(50) NOT NULL COMMENT 'routingKey名称',
  `message` varchar(1000) NOT NULL COMMENT '具体的消息内容，包含txId',
  `status` tinyint(4) NOT NULL COMMENT '0 新注册的事务 1 已经添加了事务参与者 2 消息待发送 3 已经发送过，但是不保证发送成功 4 发送成功 ',
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='最终一致性事务参与者表';

-- 数据导出被取消选择。
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
