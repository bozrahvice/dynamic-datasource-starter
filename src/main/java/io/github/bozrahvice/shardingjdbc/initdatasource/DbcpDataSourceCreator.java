package io.github.bozrahvice.shardingjdbc.initdatasource;

import io.github.bozrahvice.shardingjdbc.properties.model.ConnectionPoolProperty;
import io.github.bozrahvice.shardingjdbc.properties.model.DataSourceProperty;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.util.Collection;

/**
 * @author ylpanda
 * @since 1.0.0
 */
public class DbcpDataSourceCreator {

    @SuppressWarnings("unchecked")
    public DataSource createDataSource(DataSourceProperty dataSourceProperty, ConnectionPoolProperty connectionPoolProperty) {
        BasicDataSource basicDataSource = new BasicDataSource();

        basicDataSource.setUsername(dataSourceProperty.getUsername());
        basicDataSource.setPassword(dataSourceProperty.getPassword());
        basicDataSource.setUrl(dataSourceProperty.getUrl());
        basicDataSource.setDriverClassName(dataSourceProperty.getDriverClassName());
        basicDataSource.setValidationQuery(connectionPoolProperty.getValidationQuery());
        Integer maxTotal = connectionPoolProperty.getMaxActive();
        if (maxTotal != null) {
            basicDataSource.setMaxTotal(maxTotal);
        }

        Integer maxIdle = connectionPoolProperty.getMaxIdle();
        if (maxIdle != null) {
            basicDataSource.setMaxIdle(maxIdle);
        }

        Integer minIdle = connectionPoolProperty.getMinIdle();
        if (minIdle != null) {
            basicDataSource.setMinIdle(minIdle);
        }

        Integer initialSize = connectionPoolProperty.getInitialSize();
        if (initialSize != null) {
            basicDataSource.setInitialSize(initialSize);
        }

        Long maxWaitMillis = connectionPoolProperty.getMaxWaitMillis();
        if (maxWaitMillis != null) {
            basicDataSource.setMaxWaitMillis(maxWaitMillis);
        }

        Boolean poolPreparedStatements = connectionPoolProperty.getPoolPreparedStatements();
        if (poolPreparedStatements != null) {
            basicDataSource.setPoolPreparedStatements(poolPreparedStatements);
        }

        Integer maxOpenPreparedStatements = connectionPoolProperty.getMaxOpenPreparedStatements();
        if (maxOpenPreparedStatements != null) {
            basicDataSource.setMaxOpenPreparedStatements(maxOpenPreparedStatements);
        }
        Boolean testOnCreate = connectionPoolProperty.getTestOnCreate();
        if (testOnCreate != null) {
            basicDataSource.setTestOnCreate(testOnCreate);
        }

        Boolean testOnBorrow = connectionPoolProperty.getTestOnBorrow();
        if (testOnBorrow != null) {
            basicDataSource.setTestOnBorrow(testOnBorrow);
        }

        Boolean testOnReturn = connectionPoolProperty.getTestOnReturn();
        if (testOnReturn != null) {
            basicDataSource.setTestOnReturn(testOnReturn);
        }

        Long timeBetweenEvictionRunsMillis = connectionPoolProperty.getTimeBetweenEvictionRunsMillis();
        if (timeBetweenEvictionRunsMillis != null) {
            basicDataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        }

        Long minEvictableIdleTimeMillis = connectionPoolProperty.getMinEvictableIdleTimeMillis();
        if (minEvictableIdleTimeMillis != null) {
            basicDataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        }

        Boolean testWhileIdle = connectionPoolProperty.getTestWhileIdle();
        if (testWhileIdle != null) {
            basicDataSource.setTestWhileIdle(testWhileIdle);
        }
        Integer validationQueryTimeout = connectionPoolProperty.getValidationQueryTimeout();
        if (validationQueryTimeout != null) {
            basicDataSource.setValidationQueryTimeout(validationQueryTimeout);
        }
        basicDataSource.setConnectionInitSqls((Collection<String>) connectionPoolProperty.getConnectionInitSqls());
        Integer removeAbandonedTimeout = connectionPoolProperty.getRemoveAbandonedTimeout();
        if (removeAbandonedTimeout != null) {
            basicDataSource.setRemoveAbandonedTimeout(removeAbandonedTimeout);
        }
        Boolean logAbandoned = connectionPoolProperty.getLogAbandoned();
        if (removeAbandonedTimeout != null) {
            basicDataSource.setLogAbandoned(logAbandoned);
        }
        return basicDataSource;

    }
}