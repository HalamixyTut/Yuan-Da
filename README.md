项目技术人员应此处描述一些项目基本信息，及注意事项：

* 1，使用的技术介绍

* 2，主要功能

* 3，配置信息

* 4，初始化数据库脚本
```
mvn process-resources -D skipLiquibaseRun=false -D db.driver=oracle.jdbc.driver.OracleDriver -D db.url=jdbc:oracle:thin:@10.10.10.135:1521:HAP -D db.user=hap_uat -D db.password=hap_uat
```
