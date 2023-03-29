--这个文件主要用来写数据库建表语句.
-- 一般建议,在建表的时候,把建表的sql保留下来,以备后续部署到其他机器的时候就方便了
-- 创建两个表
create database if not exists blog_system;
use blog_system;

-- 删除旧表,重新建表,删除旧表防止里面残留数据对后续程序有影响.
drop table if exists user;
drop table if exists blog;

--真正的创建表.
create table blog(
    blogId int primary key auto_increment,
    title varchar(128),
    content varchar(4096),
    postTime datetime,
    userId int
);

create table user(
    useId int primary key auto_increment,
    username varchar(20) unique,  -- 要求用户名不能和别人重复
    password varchar(20)
);

--插入一些数据
insert into blog values(1,"这是我的第一篇博客","从今天开始我要好好敲代码",now(),1);
insert into blog values(2,"这是我的第二篇博客","从昨天开始我要好好敲代码",now(),1);
insert into blog values(3,"这是我的第三篇博客","从前天开始我要好好敲代码",now(),1);

-- 插入一些数据
insert into user values(1,"mhy2656810734","weiaizuqiu1314");
insert into user values(2,"zhangsan","123");
 insert into user values(null,"程序猿小马","weiaizuqiu1314");