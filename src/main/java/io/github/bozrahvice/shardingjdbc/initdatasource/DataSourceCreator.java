package io.github.bozrahvice.shardingjdbc.initdatasource;

import io.github.bozrahvice.shardingjdbc.commons.ErrorCreateDataSourceException;
import io.github.bozrahvice.shardingjdbc.properties.model.ConnectionPoolProperty;
import io.github.bozrahvice.shardingjdbc.properties.model.DataSourceProperty;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;

/**
 * @author ylpanda
 * @since 1.0.0
 */
@Slf4j
@Setter
public class DataSourceCreator {
    private DbcpDataSourceCreator dbcpDataSourceCreator;

    private DruidDataSourceCreator druidDataSourceCreator;

    /**
     * druid数据源
     */
    private final static String DRUID_DATASOURCE = "com.alibaba.druid.pool.DruidDataSource";
    /**
     * dbcp2数据源
     */
    private final static String DBCP2_DATASOURCE = "org.apache.commons.dbcp2.BasicDataSource";

    public DataSource createDataSource(DataSourceProperty dataSourceProperty, ConnectionPoolProperty connectionPoolProperty) {
        DataSource dataSource;
        Class<? extends DataSource> type = dataSourceProperty.getType();
        if (DRUID_DATASOURCE.equals(type.getName())) {
            dataSource = druidDataSourceCreator.createDataSource(dataSourceProperty, connectionPoolProperty);
        } else if (DBCP2_DATASOURCE.equals(type.getName())) {
            dataSource = dbcpDataSourceCreator.createDataSource(dataSourceProperty, connectionPoolProperty);
        } else {
            throw new ErrorCreateDataSourceException("dataSource 【" + type.getName() + "】 not support");
        }
        return dataSource;
    }
}