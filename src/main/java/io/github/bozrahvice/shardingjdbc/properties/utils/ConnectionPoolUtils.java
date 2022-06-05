package io.github.bozrahvice.shardingjdbc.properties.utils;



import io.github.bozrahvice.shardingjdbc.commons.druid.DruidSlf4jConfig;
import io.github.bozrahvice.shardingjdbc.commons.druid.DruidWallConfig;
import io.github.bozrahvice.shardingjdbc.properties.model.ConnectionPoolProperty;
import org.apache.commons.lang.StringUtils;

import java.util.Collection;

/**
 * @author ylpanda
 * @since 1.0.0
 */
public class ConnectionPoolUtils {

    /***
     * 若single为空，以global为准
     * @ date 2021/9/6 11:50
     * @ param global 全局连接池配置参数
     * @ param single 实际数据源配置参数
     * @ return com.hikvision.ms.spring.starter.autoconfigure.shardjdbc.property.ConnectionPoolProperty
     */
    public static ConnectionPoolProperty rebuildConnectionPool(ConnectionPoolProperty single, ConnectionPoolProperty global) {
        if (single == null) {
            return global;
        }
        Integer initialSize = null == single.getInitialSize() ? global.getInitialSize() : single.getInitialSize();
        Integer maxActive = null == single.getMaxActive() ? global.getMaxActive() : single.getMaxActive();
        Integer maxIdle = null == single.getMaxIdle() ? global.getMaxIdle() : single.getMaxIdle();
        Integer minIdle = null == single.getMinIdle() ? global.getMinIdle() : single.getMinIdle();
        Long maxWaitMillis = null == single.getMaxWaitMillis() ? global.getMaxWaitMillis() : single.getMaxWaitMillis();
        Long timeBetweenEvictionRunsMillis = null == single.getTimeBetweenEvictionRunsMillis() ? global.getTimeBetweenEvictionRunsMillis() : single.getTimeBetweenEvictionRunsMillis();
        Long timeBetweenLogStatsMillis = null == single.getTimeBetweenLogStatsMillis() ? global.getTimeBetweenLogStatsMillis() : single.getTimeBetweenLogStatsMillis();
        Integer statSqlMaxSize = null == single.getStatSqlMaxSize() ? global.getStatSqlMaxSize() : single.getStatSqlMaxSize();
        Long minEvictableIdleTimeMillis = null == single.getMaxEvictableIdleTimeMillis() ? global.getMinEvictableIdleTimeMillis() : single.getMinEvictableIdleTimeMillis();
        Long maxEvictableIdleTimeMillis = null == single.getMaxEvictableIdleTimeMillis() ? global.getMaxEvictableIdleTimeMillis() : single.getMaxEvictableIdleTimeMillis();
        Boolean testWhileIdle = null == single.getTestWhileIdle() ? global.getTestWhileIdle() : single.getTestWhileIdle();
        Boolean testOnBorrow = null == single.getTestOnBorrow() ? global.getTestOnBorrow() : single.getTestOnBorrow();
        Boolean testOnReturn = null == single.getTestOnReturn() ? global.getTestOnReturn() : single.getTestOnReturn();
        Boolean testOnCreate = null == single.getTestOnCreate() ? global.getTestOnCreate() : single.getTestOnCreate();
        String validationQuery = StringUtils.isBlank(single.getValidationQuery()) ? global.getValidationQuery() : single.getValidationQuery();
        Integer validationQueryTimeout = null == single.getValidationQueryTimeout() ? global.getValidationQueryTimeout() : single.getValidationQueryTimeout();
        Boolean useGlobalDataSourceStat = null == single.getUseGlobalDataSourceStat() ? global.getUseGlobalDataSourceStat() : single.getUseGlobalDataSourceStat();
        Boolean asyncInit = null == single.getAsyncInit() ? global.getAsyncInit() : single.getAsyncInit();
        String filters = StringUtils.isBlank(single.getFilters()) ? global.getFilters() : single.getFilters();
        Boolean clearFiltersEnable = null == single.getClearFiltersEnable() ? global.getClearFiltersEnable() : single.getClearFiltersEnable();
        Boolean resetStatEnable = null == single.getResetStatEnable() ? global.getResetStatEnable() : single.getResetStatEnable();
        Integer notFullTimeoutRetryCount = null == single.getNotFullTimeoutRetryCount() ? global.getNotFullTimeoutRetryCount() : single.getNotFullTimeoutRetryCount();
        Integer maxWaitThreadCount = null == single.getMaxWaitThreadCount() ? global.getMaxWaitThreadCount() : single.getMaxWaitThreadCount();
        Boolean failFast = null == single.getFailFast() ? global.getFailFast() : single.getFailFast();
        Long phyTimeoutMillis = null == single.getPhyTimeoutMillis() ? global.getPhyTimeoutMillis() : single.getPhyTimeoutMillis();
        Boolean keepAlive = null == single.getKeepAlive() ? global.getKeepAlive() : single.getKeepAlive();
        Boolean poolPreparedStatements = null == single.getPoolPreparedStatements() ? global.getPoolPreparedStatements() : single.getPoolPreparedStatements();
        Boolean initVariants = null == single.getInitVariants() ? global.getInitVariants() : single.getInitVariants();
        Boolean initGlobalVariants = null == single.getInitGlobalVariants() ? global.getInitGlobalVariants() : single.getInitGlobalVariants();
        Boolean useUnfairLock = null == single.getUseUnfairLock() ? global.getUseUnfairLock() : single.getUseUnfairLock();
        Boolean killWhenSocketReadTimeout = null == single.getKillWhenSocketReadTimeout() ? global.getKillWhenSocketReadTimeout() : single.getKillWhenSocketReadTimeout();
        String connectionProperties = null == single.getConnectionProperties() ? global.getConnectionProperties() : single.getConnectionProperties();
        Integer maxPoolPreparedStatementPerConnectionSize = null == single.getMaxPoolPreparedStatementPerConnectionSize() ? global.getMaxPoolPreparedStatementPerConnectionSize() : single.getMaxPoolPreparedStatementPerConnectionSize();
        Collection<?> connectionInitSqls = null == single.getConnectionInitSqls() ? global.getConnectionInitSqls() : single.getConnectionInitSqls();
        Boolean sharePreparedStatements = null == single.getSharePreparedStatements() ? global.getSharePreparedStatements() : single.getSharePreparedStatements();
        Integer connectionErrorRetryAttempts = null == single.getConnectionErrorRetryAttempts() ? global.getConnectionErrorRetryAttempts() : single.getConnectionErrorRetryAttempts();
        Boolean breakAfterAcquireFailure = null == single.getBreakAfterAcquireFailure() ? global.getBreakAfterAcquireFailure() : single.getBreakAfterAcquireFailure();
        Boolean removeAbandoned = null == single.getRemoveAbandoned() ? global.getRemoveAbandoned() : single.getRemoveAbandoned();
        Integer removeAbandonedTimeout = null == single.getRemoveAbandonedTimeout() ? global.getRemoveAbandonedTimeout() : single.getRemoveAbandonedTimeout();
        Boolean logAbandoned = null == single.getLogAbandoned() ? global.getLogAbandoned() : single.getLogAbandoned();
        Integer queryTimeout = null == single.getQueryTimeout() ? global.getQueryTimeout() : single.getQueryTimeout();
        Integer transactionQueryTimeout = null == single.getTransactionQueryTimeout() ? global.getTransactionQueryTimeout() : single.getTransactionQueryTimeout();
        Long transactionThresholdMillis = null == single.getTransactionThresholdMillis() ? global.getTransactionThresholdMillis() : single.getTransactionThresholdMillis();
        Integer maxOpenPreparedStatements = null == single.getMaxOpenPreparedStatements() ? global.getMaxOpenPreparedStatements() : single.getMaxOpenPreparedStatements();
        DruidSlf4jConfig druidSlf4j = null == single.getDruidSlf4j() ? global.getDruidSlf4j() : single.getDruidSlf4j();
        DruidWallConfig wall = null == single.getWall() ? global.getWall() : single.getWall();
        ConnectionPoolProperty connectionPoolProperty = new ConnectionPoolProperty();
        connectionPoolProperty.setInitialSize(initialSize);
        connectionPoolProperty.setMaxActive(maxActive);
        connectionPoolProperty.setMaxIdle(maxIdle);
        connectionPoolProperty.setMinIdle(minIdle);
        connectionPoolProperty.setMaxWaitMillis(maxWaitMillis);
        connectionPoolProperty.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        connectionPoolProperty.setTimeBetweenLogStatsMillis(timeBetweenLogStatsMillis);
        connectionPoolProperty.setStatSqlMaxSize(statSqlMaxSize);
        connectionPoolProperty.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        connectionPoolProperty.setMaxEvictableIdleTimeMillis(maxEvictableIdleTimeMillis);
        connectionPoolProperty.setTestWhileIdle(testWhileIdle);
        connectionPoolProperty.setTestOnBorrow(testOnBorrow);
        connectionPoolProperty.setTestOnReturn(testOnReturn);
        connectionPoolProperty.setTestOnCreate(testOnCreate);
        connectionPoolProperty.setValidationQuery(validationQuery);
        connectionPoolProperty.setValidationQueryTimeout(validationQueryTimeout);
        connectionPoolProperty.setUseGlobalDataSourceStat(useGlobalDataSourceStat);
        connectionPoolProperty.setAsyncInit(asyncInit);
        connectionPoolProperty.setFilters(filters);
        connectionPoolProperty.setClearFiltersEnable(clearFiltersEnable);
        connectionPoolProperty.setResetStatEnable(resetStatEnable);
        connectionPoolProperty.setNotFullTimeoutRetryCount(notFullTimeoutRetryCount);
        connectionPoolProperty.setMaxWaitThreadCount(maxWaitThreadCount);
        connectionPoolProperty.setFailFast(failFast);
        connectionPoolProperty.setPhyTimeoutMillis(phyTimeoutMillis);
        connectionPoolProperty.setKeepAlive(keepAlive);
        connectionPoolProperty.setPoolPreparedStatements(poolPreparedStatements);
        connectionPoolProperty.setInitVariants(initVariants);
        connectionPoolProperty.setInitGlobalVariants(initGlobalVariants);
        connectionPoolProperty.setUseUnfairLock(useUnfairLock);
        connectionPoolProperty.setKillWhenSocketReadTimeout(killWhenSocketReadTimeout);
        connectionPoolProperty.setConnectionProperties(connectionProperties);
        connectionPoolProperty.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
        connectionPoolProperty.setConnectionInitSqls(connectionInitSqls);
        connectionPoolProperty.setSharePreparedStatements(sharePreparedStatements);
        connectionPoolProperty.setConnectionErrorRetryAttempts(connectionErrorRetryAttempts);
        connectionPoolProperty.setBreakAfterAcquireFailure(breakAfterAcquireFailure);
        connectionPoolProperty.setRemoveAbandoned(removeAbandoned);
        connectionPoolProperty.setRemoveAbandonedTimeout(removeAbandonedTimeout);
        connectionPoolProperty.setLogAbandoned(logAbandoned);
        connectionPoolProperty.setQueryTimeout(queryTimeout);
        connectionPoolProperty.setTransactionQueryTimeout(transactionQueryTimeout);
        connectionPoolProperty.setTransactionThresholdMillis(transactionThresholdMillis);
        connectionPoolProperty.setMaxOpenPreparedStatements(maxOpenPreparedStatements);
        connectionPoolProperty.setDruidSlf4j(druidSlf4j);
        connectionPoolProperty.setWall(wall);
        return connectionPoolProperty;
    }

}