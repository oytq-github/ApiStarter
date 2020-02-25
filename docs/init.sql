/*
SQLyog 
MySQL - 5.7.25 : Database - api_starter
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`api_starter` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `api_starter`;

/*Table structure for table `api_admin_resource` */

CREATE TABLE `api_admin_resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `resource_type` varchar(60) NOT NULL COMMENT '资源类型:RESOURCE_MENU,RESOURCE_BUTTON,RESOURCE_INTERFACE',
  `resource_name` varchar(60) NOT NULL COMMENT '资源名称',
  `resource_desc` varchar(255) NOT NULL COMMENT '资源描述',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父类ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

/*Data for the table `api_admin_resource` */

insert  into `api_admin_resource`(`id`,`resource_type`,`resource_name`,`resource_desc`,`parent_id`,`create_time`,`update_time`) values 
(1,'RESOURCE_MENU','SYSTEM_MANAGER','系统管理',NULL,'2019-10-31 20:26:30','2019-10-31 20:26:33'),
(2,'RESOURCE_MENU','RESOURCE_MENU','系统管理/资源管理',1,'2019-11-13 20:23:46','2019-11-13 20:23:48'),
(3,'RESOURCE_MENU','USER_MENU','系统管理/用户管理',1,'2019-11-13 20:24:45','2019-11-13 20:24:47'),
(4,'RESOURCE_MENU','ROLE_MENU','系统管理/角色管理',1,'2019-11-13 20:25:50','2019-11-13 20:25:52'),
(5,'RESOURCE_BUTTON','SYSTEM_MANAGER_BUTTON','系统管理按钮',NULL,'2019-11-15 17:45:33','2019-11-15 17:45:35'),
(6,'RESOURCE_BUTTON','RESOURCE_MENU_BUTTON','资源管理按钮',5,'2019-11-15 17:46:53','2019-11-15 17:46:54'),
(7,'RESOURCE_BUTTON','USER_MENU_BUTTON','用户管理按钮',5,'2019-11-15 17:47:40','2019-11-15 17:47:42'),
(8,'RESOURCE_BUTTON','ROLE_MENU_BUTTON','角色管理按钮',5,'2019-11-15 17:48:08','2019-11-15 17:48:10'),
(9,'RESOURCE_BUTTON','ROLE_EDIT_BUTTON','角色编辑按钮',8,'2019-11-15 17:49:59','2019-11-15 17:50:01'),
(10,'RESOURCE_BUTTON','ROLE_RESOURCE_SETTING_BUTTON','角色资源配置按钮',8,'2019-11-15 17:50:34','2019-11-15 17:50:35'),
(11,'RESOURCE_BUTTON','ROLE_DELETE_BUTTON','角色删除按钮',8,'2019-11-15 17:51:07','2019-11-15 17:51:05'),
(12,'RESOURCE_BUTTON','ROLE_ADD_BUTTON','添加角色按钮',8,'2019-11-15 17:52:21','2019-11-15 17:52:23'),
(13,'RESOURCE_BUTTON','USER_ADD_BUTTON','添加用户按钮',7,'2019-11-15 17:52:51','2019-11-15 17:52:53'),
(14,'RESOURCE_BUTTON','USER_STATUS_CHANGE_BUTTON','变更用户状态按钮',7,'2019-11-15 17:54:18','2019-11-15 17:54:19'),
(15,'RESOURCE_BUTTON','USER_EDIT_BUTTON','用户编辑按钮',7,'2019-11-15 17:54:44','2019-11-15 17:54:46'),
(16,'RESOURCE_BUTTON','USER_ROLE_SETTING_BUTTON','用户角色配置按钮',7,'2019-11-15 17:55:13','2019-11-15 17:55:14'),
(17,'RESOURCE_BUTTON','USER_DELETE_BUTTON','删除用户按钮',7,'2019-11-15 17:55:39','2019-11-15 17:55:41');

/*Table structure for table `api_admin_role` */

CREATE TABLE `api_admin_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `role_name` varchar(60) NOT NULL COMMENT '角色名称',
  `role_desc` varchar(255) NOT NULL COMMENT '角色描述',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `api_version` int(11) NOT NULL DEFAULT '0' COMMENT '数据版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

/*Data for the table `api_admin_role` */

insert  into `api_admin_role`(`id`,`role_name`,`role_desc`,`create_time`,`update_time`,`api_version`) values 
(2,'administrators','管理员','2019-11-13 21:12:13','2019-11-13 21:12:15',0),
(3,'kuaiji','会计','2019-11-14 13:26:47','2019-11-14 13:26:47',0),
(4,'shixi','实习生','2019-11-14 13:27:36','2019-11-14 13:27:36',0),
(5,'shenji','审计','2019-11-14 13:29:54','2019-11-14 13:29:54',0);

/*Table structure for table `api_admin_role_resource` */

CREATE TABLE `api_admin_role_resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `resource_id` bigint(20) NOT NULL COMMENT '资源ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=192 DEFAULT CHARSET=utf8;

/*Data for the table `api_admin_role_resource` */

insert  into `api_admin_role_resource`(`id`,`role_id`,`resource_id`,`create_time`,`update_time`) values 
(176,2,1,'2019-11-16 14:52:20','2019-11-16 14:52:20'),
(177,2,2,'2019-11-16 14:52:20','2019-11-16 14:52:20'),
(178,2,3,'2019-11-16 14:52:20','2019-11-16 14:52:20'),
(179,2,4,'2019-11-16 14:52:20','2019-11-16 14:52:20'),
(180,2,5,'2019-11-16 14:52:20','2019-11-16 14:52:20'),
(181,2,6,'2019-11-16 14:52:20','2019-11-16 14:52:20'),
(182,2,7,'2019-11-16 14:52:20','2019-11-16 14:52:20'),
(183,2,13,'2019-11-16 14:52:20','2019-11-16 14:52:20'),
(184,2,14,'2019-11-16 14:52:20','2019-11-16 14:52:20'),
(185,2,15,'2019-11-16 14:52:20','2019-11-16 14:52:20'),
(186,2,16,'2019-11-16 14:52:20','2019-11-16 14:52:20'),
(187,2,17,'2019-11-16 14:52:20','2019-11-16 14:52:20'),
(188,2,9,'2019-11-16 14:52:21','2019-11-16 14:52:21'),
(189,2,10,'2019-11-16 14:52:21','2019-11-16 14:52:21'),
(190,2,11,'2019-11-16 14:52:21','2019-11-16 14:52:21'),
(191,2,12,'2019-11-16 14:52:21','2019-11-16 14:52:21');

/*Table structure for table `api_admin_user` */

CREATE TABLE `api_admin_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(60) NOT NULL COMMENT '登录账号',
  `password` varchar(60) NOT NULL COMMENT '登录密码',
  `user_salt` varchar(60) NOT NULL COMMENT '密码盐',
  `real_name` varchar(60) NOT NULL COMMENT '真实姓名',
  `nick_name` varchar(60) NOT NULL COMMENT '用户昵称',
  `user_phone` varchar(60) NOT NULL COMMENT '用户手机号',
  `avatar_url` varchar(255) DEFAULT NULL COMMENT '头像地址',
  `user_email` varchar(60) NOT NULL COMMENT '用户邮箱',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `user_status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '数据状态: -1停用 1正常',
  `user_version` int(11) NOT NULL DEFAULT '0' COMMENT '数据版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

/*Data for the table `api_admin_user` */

insert  into `api_admin_user`(`id`,`username`,`password`,`user_salt`,`real_name`,`nick_name`,`user_phone`,`avatar_url`,`user_email`,`create_time`,`update_time`,`user_status`,`user_version`) values 
(2,'123123','77d3b7ed9db7d236b9eac8262d27f6a5','123','123123','123123','123123','123123','132123','2019-11-11 19:01:47','2019-11-11 19:01:49',1,0),
(4,'213','2131','23','123','123','123','13','123','2019-11-11 19:20:22','2019-11-11 19:20:24',1,0),
(5,'123','123','132','123','123','32','123','123','2019-11-11 19:20:33','2019-11-11 19:20:37',1,0),
(6,'123','123','123','13','23','123','123','123','2019-11-11 19:20:48','2019-11-11 19:20:51',1,0),
(7,'123','123','123','123','321','321','321','31','2019-11-11 19:21:01','2019-11-11 19:21:03',1,0),
(10,'1','c4ca4238a0b923820dcc509a6f75849b','zE58','1','1','1',NULL,'1','2019-11-13 20:15:42','2019-11-13 20:15:42',1,0),
(11,'test','098f6bcd4621d373cade4e832627b4f6','lOAu','test','test','test',NULL,'test','2019-11-15 22:27:33','2019-11-15 22:27:33',1,0);

/*Table structure for table `api_admin_user_role` */

CREATE TABLE `api_admin_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

/*Data for the table `api_admin_user_role` */

insert  into `api_admin_user_role`(`id`,`user_id`,`role_id`,`create_time`,`update_time`) values 
(18,2,2,'2019-11-16 13:32:57','2019-11-16 13:32:57'),
(19,2,3,'2019-11-16 13:32:57','2019-11-16 13:32:57'),
(20,2,4,'2019-11-16 13:32:57','2019-11-16 13:32:57'),
(21,2,5,'2019-11-16 13:32:57','2019-11-16 13:32:57');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
