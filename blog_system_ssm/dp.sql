create database if not exists mycnblog charset utf8;
use mycnblog;
drop table if exists userinfo;
CREATE TABLE `userinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL,
  `password` varchar(70) DEFAULT NULL,
  `photo` varchar(500) DEFAULT '',
  `createtime` datetime,
  `updatetime` datetime,
  `state` int(11) DEFAULT '1',
  PRIMARY KEY (`id`)
);

drop table if exists articleinfo;
 CREATE TABLE `articleinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL,
  `content` text NOT NULL,
  `createtime` datetime,
  `updatetime` datetime,
  `uid` int(11) NOT NULL,
  `rcount` int(11) NOT NULL DEFAULT '1',
  `state` int(11) DEFAULT '1',
  PRIMARY KEY (`id`)
);



insert into userinfo values(1,"张三","5a0dd0a8b6c4447e83d80ba184a54c6a$6eb445152f559c851a9133b89ce8c93b","","2023-07-23 11:35:06","2023-07-23 11:35:06",1);
insert into userinfo values(2,"李四","5a0dd0a8b6c4447e83d80ba184a54c6a$6eb445152f559c851a9133b89ce8c93b","","2023-07-23 11:35:06","2023-07-23 11:35:06",1);


insert into articleinfo values(null,"张三的第一篇文章","这是张三写的第一篇文章","2023-07-23 11:35:06","2023-07-23 11:35:06",1,0,1);
insert into articleinfo values(null,"张三的第二篇文章","这是张三写的第二篇文章","2023-07-23 11:36:06","2023-07-23 11:36:06",1,0,1);

insert into articleinfo values(null,"李四的第一篇文章","这是李四写的第一篇文章","2023-07-23 11:35:06","2023-07-23 11:35:06",2,0,1);
insert into articleinfo values(null,"李四的第二篇文章","这是李四写的第二篇文章","2023-07-23 11:36:06","2023-07-23 11:36:06",2,0,1);

