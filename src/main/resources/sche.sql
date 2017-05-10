
CREATE DATABASE `food_blog`;

use `food_blog`;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`(
  `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '主键',
  `username` VARCHAR(255) NOT NULL UNIQUE COMMENT '用户名称',
  `password` VARCHAR(255) NOT NULL COMMENT '密码',
  `valid` INT  DEFAULT 1 COMMENT '是否有效，1代表是，0代表不是',
  `role` INT DEFAULT 1 COMMENT '用户角色，1代表普通用户',
  `photo_url` VARCHAR(255) COMMENT '头像路径',
  `gender` INT COMMENT '性别',
  `age` INT COMMENT '年龄',
  `address` VARCHAR(255) COMMENT '地址',
  `email` VARCHAR(255) COMMENT '邮箱',
  `phone` VARCHAR(255) COMMENT '联系电话',
  `register_time` TIMESTAMP DEFAULT now() COMMENT '注册时间',
  `summary` VARCHAR(255) COMMENT '个人简介，限制字数'
)ENGINE=InnoDb DEFAULT CHAR SET utf8 COMMENT '用户表';

/*CREATE TABLE `food_blog`.`user` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(255) NOT NULL COMMENT '用户名',
  `password` VARCHAR(255) NOT NULL COMMENT '密码',
  `valid` INT NULL DEFAULT 1 COMMENT '是否有效，1代表是，0代表不是',
  `role` INT NULL DEFAULT 1 COMMENT '用户角色，1代表普通用户',
  `photo_url` VARCHAR(255) NULL COMMENT '头像路径',
  `gender` INT NULL COMMENT '性别',
  `age` INT NULL COMMENT '年龄',
  `address` VARCHAR(255) NULL COMMENT '地址',
  `email` VARCHAR(255) NULL COMMENT '邮箱',
  `phone` VARCHAR(255) NULL COMMENT '电话',
  `register_time` TIMESTAMP NULL DEFAULT now() COMMENT '注册时间',
  `summary` VARCHAR(255) NULL COMMENT '个人简介',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC),
  FULLTEXT INDEX `u_index` (`username` ASC))
  COMMENT = '用户表';
*/

ALTER TABLE `food_blog`.`user`
  ADD FULLTEXT INDEX `index3` (`username` ASC)  WITH PARSER ngram;

CREATE TABLE `blog` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '发布人',
  `address` varchar(255) DEFAULT NULL COMMENT '美食地址',
  `food_name` varchar(255) NOT NULL COMMENT '美食名',
  `content` varchar(512) DEFAULT NULL COMMENT '博文内容',
  `description` varchar(512) DEFAULT NULL COMMENT '菜品描述',
  `ctime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `price` double DEFAULT NULL COMMENT '价格',
  `photo_path` varchar(512) DEFAULT NULL COMMENT '博文图片路径',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `address` (`address`),
  KEY `food_name` (`food_name`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='博客表';

ALTER TABLE `food_blog`.`blog`
  ADD FULLTEXT INDEX `index3` (`address`,`food_name`,`content`,`description` ASC)  WITH PARSER ngram;

#
# CREATE TABLE `blog`(
#   `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
#   `user_id` BIGINT NOT NULL COMMENT '发布人',
#   -- `city_id` BIGINT COMMENT '城市Id',
#   `address` VARCHAR(255) COMMENT '美食地址',
#   `food_name` VARCHAR(255) NOT NULL COMMENT '美食名',
#   `content` VARCHAR(512) COMMENT '博文内容',
#   `description` VARCHAR(512) COMMENT '菜品描述',
#   `ctime` TIMESTAMP DEFAULT now() COMMENT '创建时间',
#   `price` DOUBLE COMMENT '价格',
#   `photo_path` VARCHAR(512) COMMENT '博文图片路径',
#   KEY (`user_id`),
#   KEY (`address`),
#   KEY (`food_name`)
# )ENGINE=MyISAM DEFAULT CHAR SET utf8 COMMENT '博客表';

INSERT INTO `blog`(`user_id`,`address`,`food_name`,`content`,`description`,`price`)
VALUES
  (1,'香港尖沙咀','香港鱼蛋','贼好吃贼爽口','鱼蛋','20'),
  (2,'广州天河城博多一幸社','豚骨拉面','不用说了，广州最好吃的拉面','豚骨汤 + 手工拉面','40'),
  (3,'广州大学城南亭红墙','黑椒牛扒','环境好，东西也炒鸡好吃','牛扒','40'),
  (4,'广州太古汇翡翠上海餐厅','炒拉面','虽然是上海菜可是觉得炒拉面比小笼包好吃不要太多','拉面+神奇的酱','38'),
  (5,'广州天环广场鼎泰丰','鸡汤','很甜的鸡汤还有鸡肉很嫩','鸡肉+鲜汤','40'),
  (2,'广州北京路路口的一间韩国餐厅','炸鸡','炸鸡+啤酒，一个夏天就这么过去了~','炸鸡','30'),
  (3,'佛山祖庙Salala泰国餐厅','咖喱大虾','重口味爱好者的天堂','鲜虾','37'),
  (3,'汕尾','小米','肉肉的很好次！','猪肉+水晶皮','37'),
  (3,'广州太古汇翡翠','炒拉面','咸咸甜甜有胃口！','拉面+很好吃的酱','37')
  ;

CREATE TABLE `friendship`(
  `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
  `fans` BIGINT NOT NULL COMMENT '被关注者',
  `follower` BIGINT NOT NULL COMMENT '关注者',
  `friend_time` TIMESTAMP DEFAULT now() COMMENT '关注时间',
  KEY (`follower`),
  KEY (`fans`),
  UNIQUE (`follower`,`fans`)
)ENGINE=MyISAM DEFAULT CHAR SET utf8 COMMENT '关注关系表';

CREATE TABLE `likeit`(
  `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
  `user_id` BIGINT COMMENT '点赞用户',
  `blog_id` BIGINT COMMENT '博文id',
  `ctime` TIMESTAMP DEFAULT now() COMMENT '点赞时间',
  KEY (`user_id`),
  UNIQUE (`user_id`,`blog_id`)
)ENGINE=MyISAM DEFAULT CHAR SET utf8 COMMENT '点赞关系表';

CREATE TABLE `collect`(
  `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
  `user_id` BIGINT COMMENT '收藏用户',
  `blog_id` BIGINT COMMENT '博文id',
  `ctime` TIMESTAMP DEFAULT now() COMMENT '收藏时间',
  KEY (`user_id`),
  UNIQUE (`user_id`,`blog_id`)
)ENGINE=MyISAM DEFAULT CHAR SET utf8 COMMENT '收藏关系表';

CREATE TABLE `comment`(
  `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
  `user_id` BIGINT COMMENT '评论用户',
  `blog_id` BIGINT COMMENT '博文id',
  `content` VARCHAR(512) COMMENT '评论内容',
  `ctime` TIMESTAMP DEFAULT now() COMMENT '评论时间',
  `replay_id` BIGINT  COMMENT '二次评论',
  KEY (`blog_id`)
)ENGINE=MyISAM DEFAULT CHAR SET utf8 COMMENT '评论表';


CREATE TABLE `role`(
  `id` INTEGER NOT NULL  AUTO_INCREMENT PRIMARY KEY ,
  `name` VARCHAR(255) NOT NULL COMMENT '角色名'
)ENGINE=InnoDB DEFAULT CHAR SET utf8 COMMENT '角色表';

INSERT INTO `role`VALUES (1,'普通用户');

CREATE TABLE `permission`(
  `id` INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY ,
  `name` VARCHAR(255) NOT NULL COMMENT '权限名'
)ENGINE=InnoDB DEFAULT CHAR SET utf8 COMMENT '权限表';
INSERT INTO `permission` VALUES (1,'blog:publish');

CREATE TABLE `role_permission`(
  `id` INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY ,
  `role_id` INTEGER NOT NULL ,
  `permission__id` INTEGER NOT NULL
)ENGINE=InnoDB DEFAULT CHAR SET utf8 COMMENT '角色权限表';
INSERT INTO `role_permission`VALUES (1,1,1);

CREATE TABLE `music`(
  `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
  `owner_id` BIGINT COMMENT '拥有者id，0表示默认',
  `music_name` VARCHAR(255) COMMENT '音乐名',
  `file_path` VARCHAR(255) COMMENT '音乐文件位置',
  `description` VARCHAR(255) COMMENT '备注'
)ENGINE=InnoDB DEFAULT CHAR SET utf8 COMMENT '音乐表';

INSERT INTO `music`VALUES (1,0,"bgm1","/music/bgm1.mp3","背景音乐1");
INSERT INTO `music`VALUES (1,0,"bgm2","/music/bgm2.mp3","背景音乐2");



