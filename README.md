## 个人社区

##资料 
[Spring 文档](https://spring.io/guides)
[Spring Web文档](https://spring.io/guides/gs/serving-web-content/)
[es社区](https://elasticsearch.cn/explore)
[Github deploy key](https://developer.github.com/v3/guides/managing-deploy-keys/#deploy-keys)
[Bootstrap 文档](https://v3.bootcss.com/getting-started/#download)
[Github OAuth](https://developer.github.com/apps/building-oauth-apps/creating-an-oauth-app/)
[Markdown 插件](http://editor.md.ipandao.com/)
##工具
[Git](https://www.git-scm.com/download/)
[Visual Paradigm](https://www.visual-paradigm.com/)

##脚本
```sql
    CREATE TABLE USER(
    id INT AUTO_INCREMENT PRIMARY KEY,
    account_id VARCHAR(100),
    NAME VARCHAR(50),
    token CHAR(36),
    gmt_create BIGINT,
    gmt_modified BIGINT,
    bio VARCHAR(256)
    );
    
    CREATE TABLE `community`.`question`(  
      `id` INT,
      `title` VARCHAR(50),
      `description` TEXT,
      `gmt_create` BIGINT,
      `gmt_modified` BIGINT,
      `creator` INT,
      `comment_count` INT DEFAULT 0,
      `view_count` INT DEFAULT 0,
      `like_count` INT DEFAULT 0,
      `tag` VARCHAR(256)
    );

```
##命令
mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate