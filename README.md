# QnA

## 项目说明

QnA是一个匿名的私人问答网站。QnA is an anonymous and private Q&amp;A website.

QnA使用如下技术开发：

- **前端**：Bootstrap + JQuery
- **后端**：SpringBoot + Thymeleaf +MyBatis

## 项目特点

- 利用BootStrap搭建前端页面，并对CSS样式进行了调整，利用JQuery搭建完成了用户表单验证等功能；
- 利用SpringBoot整合Thymeleaf、MyBatis完成后端开发，按照MVC模式分层开发实现了业务分离；
- 利用JavaMailSender实现了对账户和邮箱的验证，以及新消息的邮件提醒；
- 响应式页面，同时支持PC端及移动端；

## 项目树形结构

```
QnA
├─.idea
│  ├─dataSources
│  └─libraries
├─.mvn
│  └─wrapper
└─src
    ├─main
    │  ├─java
    │  │  └─top
    │  │      └─migod
    │  │          ├─config //SpringBoot的MVC设置
    │  │          ├─controller //控制器
    │  │          ├─mapper //DAO接口
    │  │          ├─pojo //Java实体
    │  │          ├─service //业务层，实际上仅QuestionMapper拥有对应的Service，其余直接使用Mapper
    │  │          └─util
    │  └─resources
    │      ├─mybatis
    │      │  └─mapper //DAO接口的MyBatis实现
    │      ├─static //静态资源
    │      │  ├─css //CSS文件
    │      │  ├─error //存放了404页面
    │      │  ├─imgs
    │      │  │  ├─profile_pictures //已无效，可删除
    │      │  │  └─site //站点图片
    │      │  └─js //JavaScript文件
    │      └─templates //Thymeleaf模板HTML文件
    └─test
        └─java
            └─top
                └─migod
```

## 控制器说明

### LoginController

控制登录相关的所有操作，对应`signin.html`视图

### SignupController

控制注册相关的所有操作，对应`signup.html`视图

### QuestionAnswerController.java

负责对`panel.html`、`question.html`视图中的提问和回答表单的处理，是QnA的核心控制器

### UserSettingController.java

控制用户资料修改相关的所有操作，对应`user_setting.html`视图，同时负责头像文件的上传

### OtherController.java

调试用控制器，可删除

## 配置说明

在配置文件中，除了必要的数据库和邮箱服务配置，请添加下面三个QnA程序必须的配置，否则程序不能正常运行。

```properties
# QnA配置
## 站点域名（带端口号） 
qna.domain=localhost:8080
## 头像文件的储存文件夹（绝对路径）
qna.avatar_folder_path=E:/QnA_avatars/
## 头像文件夹的相对路径映射
qna.avatar_folder_path.relative=/avatar/**
```

## 数据库结构

由于笔者在MyBatis的xml文件中写死了数据库名，请在创建数据库时，务必将数据库名称设置为`qna_database`，或者修改全部的`XxxMapper.xml`文件，否则程序无法正常执行。

创建完数据库后，请执行下面的MySQL语句以创建数据表。数据表详细结构可见下方代码。

```mysql
DROP TABLE IF EXISTS `answer`;
CREATE TABLE `answer` (
  `aid` int(10) NOT NULL AUTO_INCREMENT,
  `uid` int(8) DEFAULT NULL,
  `qid` int(10) NOT NULL COMMENT 'parent question',
  `content` varchar(800) NOT NULL,
  `time` datetime NOT NULL,
  PRIMARY KEY (`aid`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `question`;
CREATE TABLE `question` (
  `qid` int(10) NOT NULL AUTO_INCREMENT,
  `uid` int(8) DEFAULT NULL,
  `content` varchar(500) NOT NULL,
  `time` datetime NOT NULL,
  PRIMARY KEY (`qid`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `temp_info`;
CREATE TABLE `temp_info` (
  `check_code` varchar(8) NOT NULL COMMENT '激活码/验证码',
  `type` int(1) DEFAULT NULL COMMENT '1-用户激活, 2-Email验证',
  `uid` int(16) DEFAULT NULL,
  `username` varchar(16) DEFAULT NULL,
  `password` varchar(32) DEFAULT NULL,
  `email` varchar(64) DEFAULT NULL,
  `nickname` varchar(16) DEFAULT NULL,
  PRIMARY KEY (`check_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `uid` int(8) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(16) NOT NULL,
  `password` varchar(32) NOT NULL,
  `email` varchar(64) NOT NULL,
  `nickname` varchar(16) NOT NULL,
  `self_intro` varchar(200) DEFAULT NULL,
  `avatar_name` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

```

## 协议

本项目遵循MIT协议，请在协议许可的范围内使用。
