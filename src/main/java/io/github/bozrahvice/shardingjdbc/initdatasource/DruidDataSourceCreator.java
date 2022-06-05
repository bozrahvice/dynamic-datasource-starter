package io.github.bozrahvice.shardingjdbc.initdatasource;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import io.github.bozrahvice.shardingjdbc.commons.druid.DruidSlf4jConfig;
import io.github.bozrahvice.shardingjdbc.commons.druid.DruidWallConfigUtil;
import io.github.bozrahvice.shardingjdbc.properties.model.ConnectionPoolProperty;
import io.github.bozrahvice.shardingjdbc.properties.model.DataSourceProperty;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author ylpanda
 * @since 1.0.0
 */
public class DruidDataSourceCreator {
    private static final String STAT = "stat";
    private static final String WALL = "wall";

    private static final String SLF4J = "slf4j";

    /**
     * 创建 Druid dataSource资源
     */
    public DataSource createDataSource(DataSourceProperty dataSourceProperty, ConnectionPoolProperty connectionPoolProperty) {

        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setName(dataSourceProperty.getName());
        dataSource.setUsername(dataSourceProperty.getUsername());
        dataSource.setPassword(dataSourceProperty.getPassword());
        dataSource.setUrl(dataSourceProperty.getUrl());
        dataSource.setDriverClassName(dataSourceProperty.getDriverClassName());

        Long maxWaitMillis = connectionPoolProperty.getMaxWaitMillis();
        if (maxWaitMillis != null) {
            dataSource.setMaxWait(maxWaitMillis);
        }
        Integer validationQueryTimeout = connectionPoolProperty.getValidationQueryTimeout();
        if (validationQueryTimeout != null && !validationQueryTimeout.equals(-1)) {
            dataSource.setValidationQueryTimeout(validationQueryTimeout);
        }
        Boolean sharePreparedStatements = connectionPoolProperty.getSharePreparedStatements();
        if (sharePreparedStatements != null && sharePreparedStatements.equals(true)) {
            dataSource.setSharePreparedStatements(true);
        }

        Integer connectionErrorRetryAttempts = connectionPoolProperty.getConnectionErrorRetryAttempts();
        if (connectionErrorRetryAttempts != null && !connectionErrorRetryAttempts.equals(1)) {
            dataSource.setConnectionErrorRetryAttempts(connectionErrorRetryAttempts);
        }
        Boolean breakAfterAcquireFailure = connectionPoolProperty.getBreakAfterAcquireFailure();
        if (breakAfterAcquireFailure != null && breakAfterAcquireFailure.equals(true)) {
            dataSource.setBreakAfterAcquireFailure(true);
        }
        Boolean abandoned = connectionPoolProperty.getRemoveAbandoned();
        if (abandoned != null) {
            dataSource.setRemoveAbandoned(abandoned);
        }
        Boolean logAbandoned = connectionPoolProperty.getLogAbandoned();
        if (logAbandoned != null) {
            dataSource.setLogAbandoned(logAbandoned);
        }
        Integer queryTimeOut = connectionPoolProperty.getQueryTimeout();
        if (queryTimeOut != null) {
            dataSource.setQueryTimeout(queryTimeOut);
        }
        Integer transactionQueryTimeout = connectionPoolProperty.getTransactionQueryTimeout();
        if (transactionQueryTimeout != null) {
            dataSource.setTransactionQueryTimeout(transactionQueryTimeout);
        }
        Boolean asyncInit = connectionPoolProperty.getAsyncInit();
        if (asyncInit != null) {
            dataSource.setAsyncInit(asyncInit);
        }
        Boolean killWhenSocketReadTimeout = connectionPoolProperty.getKillWhenSocketReadTimeout();
        if (killWhenSocketReadTimeout != null) {
            dataSource.setKillWhenSocketReadTimeout(killWhenSocketReadTimeout);
        }
        Boolean useGlobalDataSourceStat = connectionPoolProperty.getUseGlobalDataSourceStat();
        if (useGlobalDataSourceStat != null) {
            dataSource.setUseGlobalDataSourceStat(useGlobalDataSourceStat);
        }
        Boolean keepAlive = connectionPoolProperty.getKeepAlive();
        if (keepAlive != null) {
            dataSource.setKeepAlive(keepAlive);
        }
        Boolean resetStatEnable = connectionPoolProperty.getResetStatEnable();
        if (resetStatEnable != null) {
            dataSource.setResetStatEnable(resetStatEnable);
        }
        Boolean poolPreparedStatements = connectionPoolProperty.getPoolPreparedStatements();
        if (poolPreparedStatements != null) {
            dataSource.setPoolPreparedStatements(poolPreparedStatements);
        }
        Integer maxActive = connectionPoolProperty.getMaxActive();
        if (maxActive != null) {
            dataSource.setMaxActive(maxActive);
        }
        Long timeBetweenLogStatsMillis = connectionPoolProperty.getTimeBetweenLogStatsMillis();
        if (timeBetweenLogStatsMillis != null) {
            dataSource.setTimeBetweenLogStatsMillis(timeBetweenLogStatsMillis);
        }
        Boolean useUnfairLock = connectionPoolProperty.getUseUnfairLock();
        if (useUnfairLock != null) {
            dataSource.setUseUnfairLock(useUnfairLock);
        }
        Long transactionThresholdMillis = connectionPoolProperty.getTransactionThresholdMillis();
        if (transactionThresholdMillis != null) {
            dataSource.setTransactionThresholdMillis(transactionThresholdMillis);
        }
        Integer maxPoolPreparedStatementPerConnectionSize = connectionPoolProperty.getMaxPoolPreparedStatementPerConnectionSize();
        if (maxPoolPreparedStatementPerConnectionSize != null) {
            dataSource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
        }
        Long timeBetweenConnectErrorMillis = connectionPoolProperty.getTimeBetweenEvictionRunsMillis();
        if (timeBetweenConnectErrorMillis != null) {
            dataSource.setTimeBetweenConnectErrorMillis(timeBetweenConnectErrorMillis);
        }
        Integer maxOpenPreparedStatements = connectionPoolProperty.getMaxOpenPreparedStatements();
        if (maxOpenPreparedStatements != null) {
            dataSource.setMaxOpenPreparedStatements(maxOpenPreparedStatements);
        }
        Integer removeAbandonedTimeout = connectionPoolProperty.getRemoveAbandonedTimeout();
        if (removeAbandonedTimeout != null) {
            dataSource.setRemoveAbandonedTimeout(removeAbandonedTimeout);
        }

        Boolean removeAbandoned = connectionPoolProperty.getRemoveAbandoned();
        if (removeAbandoned != null) {
            dataSource.setRemoveAbandoned(removeAbandoned);
        }
        Long minEvictableIdleTimeMillis = connectionPoolProperty.getMinEvictableIdleTimeMillis();
        if (minEvictableIdleTimeMillis != null) {
            dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        }

        Long maxEvictableIdleTimeMillis = connectionPoolProperty.getMaxEvictableIdleTimeMillis();
        if (maxEvictableIdleTimeMillis != null) {
            dataSource.setMaxEvictableIdleTimeMillis(maxEvictableIdleTimeMillis);
        }

        Long phyTimeoutMillis = connectionPoolProperty.getPhyTimeoutMillis();
        if (phyTimeoutMillis != null) {
            dataSource.setPhyTimeoutMillis(phyTimeoutMillis);
        }

        Long timeBetweenEvictionRunsMillis = connectionPoolProperty.getTimeBetweenEvictionRunsMillis();
        if (timeBetweenEvictionRunsMillis != null) {
            dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        }

        Integer maxWaitThreadCount = connectionPoolProperty.getMaxWaitThreadCount();
        if (maxWaitThreadCount != null) {
            dataSource.setMaxWaitThreadCount(maxWaitThreadCount);
        }

        String validationQuery = connectionPoolProperty.getValidationQuery();
        dataSource.setValidationQuery(validationQuery);

        Boolean testOnReturn = connectionPoolProperty.getTestOnReturn();
        if (testOnReturn != null && testOnReturn.equals(true)) {
            dataSource.setTestOnReturn(true);
        }

        Boolean testOnBorrow = connectionPoolProperty.getTestOnBorrow();
        if (testOnBorrow != null) {
            dataSource.setTestOnBorrow(testOnBorrow);
        }


        Boolean testWhileIdle = connectionPoolProperty.getTestWhileIdle();
        if (testWhileIdle != null) {
            dataSource.setTestWhileIdle(testWhileIdle);
        }

        Boolean initVariants = connectionPoolProperty.getInitVariants();
        if (initVariants != null) {
            dataSource.setInitVariants(initVariants);
        }

        Boolean initGlobalVariants = connectionPoolProperty.getInitGlobalVariants();
        if (initGlobalVariants != null) {
            dataSource.setInitGlobalVariants(initGlobalVariants);
        }

        Integer queryTimeout = connectionPoolProperty.getQueryTimeout();
        if (queryTimeout != null) {
            dataSource.setQueryTimeout(queryTimeout);
        }

        Integer notFullTimeoutRetryCount = connectionPoolProperty.getNotFullTimeoutRetryCount();
        if (notFullTimeoutRetryCount != null) {
            dataSource.setNotFullTimeoutRetryCount(notFullTimeoutRetryCount);
        }

        Integer minIdle = connectionPoolProperty.getMinIdle();
        if (minIdle != null) {
            dataSource.setMinIdle(minIdle);
        }

        Integer initialSize = connectionPoolProperty.getInitialSize();
        if (initialSize != null) {
            dataSource.setInitialSize(initialSize);
        }


        Boolean clearFiltersEnable = connectionPoolProperty.getClearFiltersEnable();
        if (clearFiltersEnable != null) {
            dataSource.setClearFiltersEnable(clearFiltersEnable);
        }

        Boolean failFast = connectionPoolProperty.getFailFast();
        if (failFast != null) {
            dataSource.setFailFast(failFast);
        }
        dataSource.setConnectionInitSqls(connectionPoolProperty.getConnectionInitSqls());

        String filters = connectionPoolProperty.getFilters();
        String connectionPropertiesStr = connectionPoolProperty.getConnectionProperties();
        dataSource.setConnectionProperties(connectionPropertiesStr);
        Properties connectionProperties = buildConnectionProperties(connectionPropertiesStr);
        List<Filter> proxyFilters = new ArrayList<>();
        if (!StringUtils.isEmpty(filters) && filters.contains(STAT)) {
            StatFilter statFilter = new StatFilter();
            statFilter.configFromProperties(connectionProperties);
            proxyFilters.add(statFilter);
        }
        if (!StringUtils.isEmpty(filters) && filters.contains(WALL)) {
            WallConfig wallConfig = DruidWallConfigUtil.toWallConfig(connectionPoolProperty.getWall());
            WallFilter wallFilter = new WallFilter();
            wallFilter.setConfig(wallConfig);
            proxyFilters.add(wallFilter);
        }
        if (!StringUtils.isEmpty(filters) && filters.contains(SLF4J)) {
            Slf4jLogFilter slf4jLogFilter = new Slf4jLogFilter();
            DruidSlf4jConfig slf4jConfig = new DruidSlf4jConfig();
            slf4jLogFilter.setStatementLogEnabled(slf4jConfig.getEnable());
            slf4jLogFilter.setStatementExecutableSqlLogEnable(slf4jConfig.getStatementExecutableSqlLogEnable());
            proxyFilters.add(slf4jLogFilter);
        }
        dataSource.setProxyFilters(proxyFilters);
        return dataSource;
    }


    private Properties buildConnectionProperties(String connectionProperties) {
        Properties properties = new Properties();
        if (connectionProperties == null || connectionProperties.trim().length() == 0) {
            return properties;
        }
        String[] entries = connectionProperties.split(";");
        for (String entry : entries) {
            if (entry.length() > 0) {
                int index = entry.indexOf('=');
                if (index > 0) {
                    String name = entry.substring(0, index);
                    String value = entry.substring(index + 1);
                    properties.setProperty(name, value);
                } else {
                    properties.setProperty(entry, "");
                }
            }
        }
        return properties;
    }
}