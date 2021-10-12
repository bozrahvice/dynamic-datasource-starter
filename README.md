<p align="center">
	<strong>基于springboot的快速集成多数据源的启动器</strong>
</p>

# 简介

dynamic-datasource-starter 基于springboot的快速集成多数据源的启动器。

**Jdk 1.8, SpringBoot 2.x**。

# 功能点
- 本框架主要简化了对Druid、Dbcp2、ShardingJdbc等组件的集成方式；
- 支持普通数据源之间、shardingJdbc数据源之间、以及普通数据源与shardingJdbc数据源的相互切换；
- 约定配置文件名称为 jdbcdynamic.properties

# 快速上手
- 提供dynamic-datasource-test示例项目，提供大家快速上手。
- dynamic-datasource-test示例项目地址：https://github.com/bozrahvice/dynamic-datasource-test
- 下面介绍下 dynamic-datasource-starter 的使用。

# 添加maven依赖
```maven
    <dependency>
        <groupId>io.github.bozrahvice.starter</groupId>
        <artifactId>dynamic-datasource-starter</artifactId>
        <version>1.1.0</version>
    </dependency>
```

```
SpringBootApplication中去掉DataSourceAutoConfiguration.class 和 DataSourceHealthContributorAutoConfiguration.class
```

```
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, DataSourceHealthContributorAutoConfiguration.class})
```
# jdbcdynamic.properties配置文件简单说明
``` 
#公共的连接池配置属性，
# 若配置了此项，则实际数据源可以不配置对应的连接池属性，会复用此链接池属性，
# 若实际数据源也配置了链接池则以，实际数据源下配置的连接池属性为准
jdbc.common.connectionPool.maxWait = 10000
jdbc.common.connectionPool.maxIdle = 10
jdbc.common.connectionPool.minIdle = 5
jdbc.common.connectionPool.initialSize = 5
jdbc.common.connectionPool.maxActive = 10
jdbc.common.connectionPool.validationQuery = select 1
jdbc.common.connectionPool.filters = stat,wall,slf4j
jdbc.common.connectionPool.connectionProperties = druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000
jdbc.common.connectionPool.wall.multiStatementAllow = true
jdbc.common.connectionPool.wall.noneBaseStatementAllow = true

#mybatis属性配置
mybatis.enable=true
mybatis.type-aliases-package=io.github.bozrahvice.example.shardingjdbc.sql.dto.*
mybatis.mapper-locations=classpath:mybatis/**/*.xml

#不进行分库的数据源（简单的数据源）配置示例
#可以查看 com.panda.leaf.shardingjdbc.properties.DynamicDataSourceProperties java类中的datasource
#jdbc.dynamic配置文件前缀
#datasource为需要解析的map对象名
#testshardingjdbc（可以自定义）为datasource map对象中的key，同时为@ds中的value值（即需要选择的数据源）
# url、driver-class-name、username、password等 为 为datasource map对象中的 value
#jdbc.dynamic.datasource.testshardingjdbc.url = jdbc:mysql://localhost:3306/testshardingjdbc?useUnicode=true&characterEncoding=utf8&allowMultiQueries=true&serverTimezone=Asia/Shanghai
#jdbc.dynamic.datasource.testshardingjdbc.driver-class-name = com.mysql.cj.jdbc.Driver
#jdbc.dynamic.datasource.testshardingjdbc.username = *****
#jdbc.dynamic.datasource.testshardingjdbc.password = *****
#jdbc.dynamic.datasource.testshardingjdbc.type = com.alibaba.druid.pool.DruidDataSource
#以下为单独数据源 连接池属性配置，若为配置则采用  jdbc.common.connectionPool 公共配置属性，其他数据源配置类似
#jdbc.dynamic.connectionPool.mallChannel.maxWait = 10000
#jdbc.dynamic.connectionPool.mallChannel.maxIdle = 10
#jdbc.dynamic.connectionPool.mallChannel.minIdle = 5
#jdbc.dynamic.connectionPool.mallChannel.initialSize = 5
#jdbc.dynamic.connectionPool.mallChannel.maxActive = 10
#jdbc.dynamic.connectionPool.mallChannel.validationQuery = select 1
#jdbc.dynamic.connectionPool.mallChannel.filters = stat,wall,slf4j
#jdbc.dynamic.connectionPool.mallChannel.connectionProperties = druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000
#jdbc.dynamic.connectionPool.mallChannel.wall.multiStatementAllow = true
#jdbc.dynamic.connectionPool.mallChannel.wall.noneBaseStatementAllow = true

#是否打印shardingJdbc sql日志
jdbc.shardingsphere.props.sql.show=true

#groupIds为List列表，改配置必须，作为shardingJdbc数据源的组ID,同时为@ds中value的值，以下配置数据源的同时认为以该值开头的为同一个组的数据源
jdbc.shardingsphere.groupIds=testDB,masterSlaveDB

#以下为分库数据源配置示例
#jdbc.dynamic配置文件前缀
#datasource为需要解析的map对象名
#testDB0、testDB1 为datasource map对象中的key，为groupIds中的testDB逻辑数据源下需要分库的两个实体数据源
#testDB0Slave0、testDB0Slave1为testDB0下的从库（可不配置）
#testDB1Slave0、testDB1Slave1为testDB1下的从库（可不配置）
# url、driver-class-name、username、password等 为 为datasource map对象中的 value
jdbc.shardingsphere.datasource.testDB0.url=jdbc:mysql://localhost:3306/xxx?useUnicode=true&characterEncoding=utf8&allowMultiQueries=true&serverTimezone=Asia/Shanghai
jdbc.shardingsphere.datasource.testDB0.driver-class-name=com.mysql.cj.jdbc.Driver
jdbc.shardingsphere.datasource.testDB0.username=  *****
jdbc.shardingsphere.datasource.testDB0.password=  *****
jdbc.shardingsphere.datasource.testDB0.type=com.alibaba.druid.pool.DruidDataSource

jdbc.shardingsphere.datasource.testDB1.url=jdbc:mysql://localhost:3306/xxx?useUnicode=true&characterEncoding=utf8&allowMultiQueries=true&serverTimezone=Asia/Shanghai
jdbc.shardingsphere.datasource.testDB1.driver-class-name=com.mysql.cj.jdbc.Driver
jdbc.shardingsphere.datasource.testDB1.username= *****
jdbc.shardingsphere.datasource.testDB1.password= *****
jdbc.shardingsphere.datasource.testDB1.type=com.alibaba.druid.pool.DruidDataSource


jdbc.shardingsphere.datasource.testDB0Slave0.url=jdbc:mysql://testDB0的从库数据库地址0/xxx?useUnicode=true&characterEncoding=utf8&allowMultiQueries=true&serverTimezone=Asia/Shanghai
jdbc.shardingsphere.datasource.testDB0Slave0.driver-class-name=com.mysql.cj.jdbc.Driver
jdbc.shardingsphere.datasource.testDB0Slave0.username= *****
jdbc.shardingsphere.datasource.testDB0Slave0.password= *****
jdbc.shardingsphere.datasource.testDB0Slave0.type=com.alibaba.druid.pool.DruidDataSource

jdbc.shardingsphere.datasource.testDB0Slave1.url=jdbc:mysql://testDB0的从库数据库地址1/xxx?useUnicode=true&characterEncoding=utf8&allowMultiQueries=true&serverTimezone=Asia/Shanghai
jdbc.shardingsphere.datasource.testDB0Slave1.driver-class-name=com.mysql.cj.jdbc.Driver
jdbc.shardingsphere.datasource.testDB0Slave1.username= *****
jdbc.shardingsphere.datasource.testDB0Slave1.password= *****
jdbc.shardingsphere.datasource.testDB0Slave1.type=com.alibaba.druid.pool.DruidDataSource

jdbc.shardingsphere.datasource.testDB0Slave2.url=jdbc:mysql://testDB0的从库数据库地址2/xxx?useUnicode=true&characterEncoding=utf8&allowMultiQueries=true&serverTimezone=Asia/Shanghai
jdbc.shardingsphere.datasource.testDB0Slave2.driver-class-name=com.mysql.cj.jdbc.Driver
jdbc.shardingsphere.datasource.testDB0Slave2.username= *****
jdbc.shardingsphere.datasource.testDB0Slave2.password= *****
jdbc.shardingsphere.datasource.testDB0Slave2.type=com.alibaba.druid.pool.DruidDataSource

jdbc.shardingsphere.datasource.testDB1Slave0.url=jdbc:mysql://testDB1的从库数据库地址0/xxx?useUnicode=true&characterEncoding=utf8&allowMultiQueries=true&serverTimezone=Asia/Shanghai
jdbc.shardingsphere.datasource.testDB1Slave0.driver-class-name=com.mysql.cj.jdbc.Driver
jdbc.shardingsphere.datasource.testDB1Slave0.username= *****
jdbc.shardingsphere.datasource.testDB1Slave0.password= *****
jdbc.shardingsphere.datasource.testDB1Slave0.type=com.alibaba.druid.pool.DruidDataSource

jdbc.shardingsphere.datasource.testDB1Slave1.url=jdbc:mysql://testDB1的从库数据库地址1/xxx?useUnicode=true&characterEncoding=utf8&allowMultiQueries=true&serverTimezone=Asia/Shanghai
jdbc.shardingsphere.datasource.testDB1Slave1.driver-class-name=com.mysql.cj.jdbc.Driver
jdbc.shardingsphere.datasource.testDB1Slave1.username= *****
jdbc.shardingsphere.datasource.testDB1Slave1.password= *****
jdbc.shardingsphere.datasource.testDB1Slave1.type=com.alibaba.druid.pool.DruidDataSource

jdbc.shardingsphere.datasource.testDB1Slave2.url=jdbc:mysql://testDB1的从库数据库地址2/xxx?useUnicode=true&characterEncoding=utf8&allowMultiQueries=true&serverTimezone=Asia/Shanghai
jdbc.shardingsphere.datasource.testDB1Slave2.driver-class-name=com.mysql.cj.jdbc.Driver
jdbc.shardingsphere.datasource.testDB1Slave2.username= *****
jdbc.shardingsphere.datasource.testDB1Slave2.password= *****
jdbc.shardingsphere.datasource.testDB1Slave2.type=com.alibaba.druid.pool.DruidDataSource


#实际数据源
jdbc.shardingsphere.sharding.testDB.tables.test_table.actual-data-nodes=testDB$->{0..1}.test_table$->{0..1}

#分库策略 根据id进行分库
jdbc.shardingsphere.sharding.testDB.tables.test_table.database-strategy.inline.sharding-column=id
jdbc.shardingsphere.sharding.testDB.tables.test_table.database-strategy.inline.algorithm-expression=testDB$->{io.github.bozrahvice.shardingjdbc.algorithm.PartitionByMurmurHash.calculate(2, id)}

#分片策略 根据trade分表
jdbc.shardingsphere.sharding.testDB.tables.test_table.table-strategy.inline.sharding-column=trade
jdbc.shardingsphere.sharding.testDB.tables.test_table.table-strategy.inline.algorithm-expression=test_table$->{io.github.bozrahvice.shardingjdbc.algorithm.PartitionByMurmurHash.calculate(2, trade)}

#分库分表下的读写分离配置 如果配置了读写分离那么读操作只会去slave去查询
jdbc.shardingsphere.sharding.testDB.masterSlaveRules.testDB0.masterDataSourceName=testDB0
jdbc.shardingsphere.sharding.testDB.masterSlaveRules.testDB0.slaveDataSourceNames=testDB0Slave0,testDB0Slave1,testDB0Slave2
jdbc.shardingsphere.sharding.testDB.masterSlaveRules.testDB0.name=testDB0
jdbc.shardingsphere.sharding.testDB.masterSlaveRules.testDB0.loadBalanceAlgorithmType=round_robin

jdbc.shardingsphere.sharding.testDB.masterSlaveRules.testDB1.masterDataSourceName=testDB1
jdbc.shardingsphere.sharding.testDB.masterSlaveRules.testDB1.slaveDataSourceNames=testDB1Slave0,testDB1Slave1,testDB1Slave2
jdbc.shardingsphere.sharding.testDB.masterSlaveRules.testDB1.name=testDB1
jdbc.shardingsphere.sharding.testDB.masterSlaveRules.testDB1.loadBalanceAlgorithmType=round_robin

#全局表配置 这里配置的表列表,对于发生的所有数据变更,都会不经sharding处理,而是直接发送到所有数据节点
jdbc.shardingsphere.sharding.testDB.broadcastTables=global_table

#不分库分表的主从数据库配置
jdbc.shardingsphere.datasource.masterSlaveDB0.url=jdbc:mysql://主库数据源地址/xxx?useUnicode=true&characterEncoding=utf8&allowMultiQueries=true&serverTimezone=Asia/Shanghai
jdbc.shardingsphere.datasource.masterSlaveDB0.driver-class-name=com.mysql.cj.jdbc.Driver
jdbc.shardingsphere.datasource.masterSlaveDB0.username= *****
jdbc.shardingsphere.datasource.masterSlaveDB0.password= *****
jdbc.shardingsphere.datasource.masterSlaveDB0.type=com.alibaba.druid.pool.DruidDataSource

jdbc.shardingsphere.datasource.masterSlaveDB1.url=jdbc:mysql://从库数据源地址0/xxx?useUnicode=true&characterEncoding=utf8&allowMultiQueries=true&serverTimezone=Asia/Shanghai
jdbc.shardingsphere.datasource.masterSlaveDB1.driver-class-name=com.mysql.cj.jdbc.Driver
jdbc.shardingsphere.datasource.masterSlaveDB1.username= *****
jdbc.shardingsphere.datasource.masterSlaveDB1.password= *****
jdbc.shardingsphere.datasource.masterSlaveDB1.type=com.alibaba.druid.pool.DruidDataSource

jdbc.shardingsphere.datasource.masterSlaveDB2.url=jdbc:mysql://从库数据源地址1/xxx?useUnicode=true&characterEncoding=utf8&allowMultiQueries=true&serverTimezone=Asia/Shanghai
jdbc.shardingsphere.datasource.masterSlaveDB2.driver-class-name=com.mysql.cj.jdbc.Driver
jdbc.shardingsphere.datasource.masterSlaveDB2.username= *****
jdbc.shardingsphere.datasource.masterSlaveDB2.password= *****
jdbc.shardingsphere.datasource.masterSlaveDB2.type=com.alibaba.druid.pool.DruidDataSource

#主库库数据源
jdbc.shardingsphere.masterSlaveRules.masterSlaveDB.masterDataSourceName=masterSlaveDB0
#@ds的value
jdbc.shardingsphere.masterSlaveRules.masterSlaveDB.name=masterSlaveDB
#从库可以多个逗号隔开
jdbc.shardingsphere.masterSlaveRules.masterSlaveDB.slaveDataSourceNames=masterSlaveDB1,masterSlaveDB2
#配置从库选择策略，提供轮询与随机，这里选择用轮询 random 随机  round_robin 轮询
jdbc.shardingsphere.masterSlaveRules.masterSlaveDB.loadBalanceAlgorithmType=round_robin
```
