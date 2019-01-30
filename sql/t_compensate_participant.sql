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

-- 导出  表 adolph.t_compensate_participant 结构
CREATE TABLE IF NOT EXISTS `t_compensate_participant` (
  `id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '主键',
  `txId` bigint(20) unsigned NOT NULL COMMENT '事务ID',
  `compensateUri` varchar(100) NOT NULL COMMENT '补偿URL',
  `status` tinyint(4) NOT NULL COMMENT '0 初始 1 不用补偿 2 补偿成功',
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `txId` (`txId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='补偿事务参与者';

-- 数据导出被取消选择。
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
