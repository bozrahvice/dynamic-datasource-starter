package io.github.bozrahvice.shardingjdbc.provider;


import com.alibaba.fastjson.JSONObject;
import io.github.bozrahvice.shardingjdbc.commons.ErrorCreateDataSourceException;
import io.github.bozrahvice.shardingjdbc.initdatasource.DataSourceCreator;
import io.github.bozrahvice.shardingjdbc.properties.CommonConnectionPoolProperties;
import io.github.bozrahvice.shardingjdbc.properties.model.ConnectionPoolProperty;
import io.github.bozrahvice.shardingjdbc.properties.utils.ConnectionPoolUtils;
import io.github.bozrahvice.shardingjdbc.properties.model.DataSourceProperty;
import io.github.bozrahvice.shardingjdbc.properties.ShardingJdbcDataSourceProperties;
import io.github.bozrahvice.shardingjdbc.properties.shardingsphere.masterslave.MasterSlaveRuleConfigurationProperties;
import io.github.bozrahvice.shardingjdbc.properties.shardingsphere.sharding.ShardingRuleConfigurationProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.shardingsphere.core.yaml.swapper.MasterSlaveRuleConfigurationYamlSwapper;
import org.apache.shardingsphere.core.yaml.swapper.ShardingRuleConfigurationYamlSwapper;
import org.apache.shardingsphere.shardingjdbc.api.MasterSlaveDataSourceFactory;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.apache.shardingsphere.underlying.common.config.inline.InlineExpressionParser;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author ylpanda
 * @since 1.0.0
 */
@Slf4j
public class AbstractDynamicDataSourceProvider {

    @Resource
    private DataSourceCreator dataSourceCreator;

    @Resource
    private ShardingJdbcDataSourceProperties shardingJdbcDataSourceProperties;

    @Resource
    private CommonConnectionPoolProperties commonConnectionPoolProperties;

    private final ShardingRuleConfigurationYamlSwapper shardingSwapper = new ShardingRuleConfigurationYamlSwapper();

    private final MasterSlaveRuleConfigurationYamlSwapper masterSlaveSwapper = new MasterSlaveRuleConfigurationYamlSwapper();

    protected Map<String, DataSource> createDataSourceMap() {
        Map<String, DataSource> dataSourceMap = new HashMap<>();
        List<String> dataSourceNames = shardingJdbcDataSourceProperties.getDataSourceNames();
        if (dataSourceNames.isEmpty()) {
            log.warn("dataSource names is empty,can not load dataSource");
            return dataSourceMap;
        }
        Map<String, DataSourceProperty> shardingDatasourceMap = shardingJdbcDataSourceProperties.getDatasource();
        Map<String, ConnectionPoolProperty> shardingConnectionPoolMap = shardingJdbcDataSourceProperties.getConnectionPool();
        Map<String, ShardingRuleConfigurationProperties> shardingMap = shardingJdbcDataSourceProperties.getSharding();
        Map<String, MasterSlaveRuleConfigurationProperties> masterSlaveRulesMap = shardingJdbcDataSourceProperties.getMasterSlaveRules();
        Properties props = shardingJdbcDataSourceProperties.getProps();


        if (!shardingDatasourceMap.isEmpty()) {
            log.info("dataSource properties is not empty,start load dataSource");
            dataSourceNames.forEach(dataSourceName -> shardingDatasourceMap.forEach((key, dataSourceProperty) -> {
                if (StringUtils.equals(key, dataSourceName)) {
                    log.info("dataSource Key:[{}] equals dataSourceName:[{}], is a simple dataSource,start loading simple datasource", key, dataSourceName);
                    ConnectionPoolProperty connectionPoolProperty = ConnectionPoolUtils.rebuildConnectionPool(shardingConnectionPoolMap.get(dataSourceName), commonConnectionPoolProperties.getConnectionPool());
                    dataSourceProperty.setName(dataSourceName);
                    dataSourceMap.put(dataSourceName, dataSourceCreator.createDataSource(dataSourceProperty, connectionPoolProperty));
                }
            }));
            if (!shardingMap.isEmpty()) {
                Map<String, List<String>> dataSourceRelationMap = new ConcurrentHashMap<>();
                shardingMap.forEach((realDataSourceName, shardingRuleConfigurationProperties) -> {
                    List<String> masterSlaveDataNodes = new ArrayList<>();
                    shardingRuleConfigurationProperties.getMasterSlaveRules().forEach((masterSlaveRealDataSourceName, yamlMasterSlaveRuleConfiguration) -> {
                        masterSlaveDataNodes.add(yamlMasterSlaveRuleConfiguration.getMasterDataSourceName());
                        masterSlaveDataNodes.addAll(yamlMasterSlaveRuleConfiguration.getSlaveDataSourceNames());
                    });
                    shardingRuleConfigurationProperties.getTables().forEach((tables, yamlTableRuleConfiguration) -> {
                        List<String> dataNodes = new InlineExpressionParser(yamlTableRuleConfiguration.getActualDataNodes()).splitAndEvaluate();
                        dataNodes = dataNodes.stream().map(s -> s.contains(".") ? s.substring(0, s.indexOf(".")) : s).distinct().collect(Collectors.toList());
                        masterSlaveDataNodes.addAll(dataNodes);
                    });
                    List<String> finalDataNodes = masterSlaveDataNodes.stream().distinct().collect(Collectors.toList());
                    dataSourceRelationMap.put(realDataSourceName, finalDataNodes);
                });
                log.info("sharding logic dataSource and reality dataSource relationship is：【{}】",  JSONObject.toJSON(dataSourceRelationMap));
                dataSourceRelationMap.forEach((realDataSourceName, logicDataSourceList) -> {
                    if (dataSourceNames.contains(realDataSourceName)) {
                        Map<String, DataSource> logicDataSourceMap = new HashMap<>();
                        for (String logicDataSourceName : logicDataSourceList) {
                            DataSourceProperty dataSourceProperty = shardingDatasourceMap.get(logicDataSourceName);
                            dataSourceProperty.setName(logicDataSourceName);
                            ConnectionPoolProperty connectionPoolProperty = ConnectionPoolUtils.rebuildConnectionPool(shardingConnectionPoolMap.get(logicDataSourceName), commonConnectionPoolProperties.getConnectionPool());
                            logicDataSourceMap.put(logicDataSourceName, dataSourceCreator.createDataSource(dataSourceProperty, connectionPoolProperty));
                        }
                        ShardingRuleConfigurationProperties shardingRuleConfigurationProperties = shardingMap.get(realDataSourceName);
                        DataSource dataSource;
                        try {
                            dataSource = ShardingDataSourceFactory.createDataSource(logicDataSourceMap, shardingSwapper.swap(shardingRuleConfigurationProperties), props);
                        } catch (Exception e) {
                            throw new ErrorCreateDataSourceException("sharding dataSource create error,", e);
                        }
                        dataSourceMap.put(realDataSourceName, dataSource);
                    }
                });
            }
            if (!masterSlaveRulesMap.isEmpty()) {
                Map<String, List<String>> dataSourceRelationMap = new ConcurrentHashMap<>();
                masterSlaveRulesMap.forEach((realDataSourceName, masterSlaveRuleConfigurationProperties) -> {
                    List<String> dataNodes = new ArrayList<>();
                    dataNodes.add(masterSlaveRuleConfigurationProperties.getMasterDataSourceName());
                    dataNodes.addAll(masterSlaveRuleConfigurationProperties.getSlaveDataSourceNames());
                    dataNodes = dataNodes.stream().distinct().collect(Collectors.toList());
                    dataSourceRelationMap.put(realDataSourceName, dataNodes);
                });
                log.info("masterSlave logic dataSource and reality dataSource relationship is：【{}】",  JSONObject.toJSON(dataSourceRelationMap));
                dataSourceRelationMap.forEach((realDataSourceName, logicDataSourceList) -> {
                    if (dataSourceNames.contains(realDataSourceName)) {
                        Map<String, DataSource> masterSlaveLogicDataSourceMap = new HashMap<>();
                        for (String logicDataSourceName : logicDataSourceList) {
                            DataSourceProperty dataSourceProperty = shardingDatasourceMap.get(logicDataSourceName);
                            dataSourceProperty.setName(logicDataSourceName);
                            ConnectionPoolProperty connectionPoolProperty = ConnectionPoolUtils.rebuildConnectionPool(shardingConnectionPoolMap.get(logicDataSourceName), commonConnectionPoolProperties.getConnectionPool());
                            masterSlaveLogicDataSourceMap.put(logicDataSourceName, dataSourceCreator.createDataSource(dataSourceProperty, connectionPoolProperty));
                        }
                        MasterSlaveRuleConfigurationProperties masterSlaveRuleConfigurationProperties = masterSlaveRulesMap.get(realDataSourceName);
                        DataSource dataSource;
                        try {
                            dataSource = MasterSlaveDataSourceFactory.createDataSource(masterSlaveLogicDataSourceMap, masterSlaveSwapper.swap(masterSlaveRuleConfigurationProperties), props);
                        } catch (Exception e) {
                            throw new ErrorCreateDataSourceException("sharding dataSource create error,", e);
                        }
                        dataSourceMap.put(realDataSourceName, dataSource);
                    }
                });
            }
        } else {
            log.warn("dataSource properties is not empty,can not load dataSource");
        }
        return dataSourceMap;
    }

}