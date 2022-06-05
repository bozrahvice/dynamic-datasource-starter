package io.github.bozrahvice.shardingjdbc.properties.model;



import io.github.bozrahvice.shardingjdbc.commons.druid.DruidSlf4jConfig;
import io.github.bozrahvice.shardingjdbc.commons.druid.DruidWallConfig;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

/**
 * @author ylpanda
 * @since 1.0.0
 */
@Getter
@Setter
public class ConnectionPoolProperty {

    private Integer initialSize;
    /**
     * druid 对应 maxActive 最大活跃数
     * dbcp2 对应 maxTotal 最大活跃数
     */
    private Integer maxActive;
    /**
     * druid maxIdle is deprecated
     * dbcp2 最大idle数
     */
    private Integer maxIdle;

    /**
     * druid minIdle 最小idle数
     * dbcp2 minIdle 最小idle数
     */
    private Integer minIdle;
    /**
     * 最长等待时间(毫秒)
     */
    private Long maxWaitMillis;
    private Long timeBetweenEvictionRunsMillis;
    private Long timeBetweenLogStatsMillis;
    private Integer statSqlMaxSize;
    private Long minEvictableIdleTimeMillis;
    private Long maxEvictableIdleTimeMillis;
    private Boolean testWhileIdle;
    private Boolean testOnBorrow;
    private Boolean testOnReturn;

    /**
     * druid 无此参数
     * dbcp2
     */
    private Boolean testOnCreate;
    private String validationQuery;
    private Integer validationQueryTimeout;
    private Boolean useGlobalDataSourceStat;
    private Boolean asyncInit;
    private String filters;
    private Boolean clearFiltersEnable;
    private Boolean resetStatEnable;
    private Integer notFullTimeoutRetryCount;
    private Integer maxWaitThreadCount;
    private Boolean failFast;
    private Long phyTimeoutMillis;
    private Boolean keepAlive;
    private Boolean poolPreparedStatements;
    private Boolean initVariants;
    private Boolean initGlobalVariants;
    private Boolean useUnfairLock;
    private Boolean killWhenSocketReadTimeout;
    private String connectionProperties;
    private Integer maxPoolPreparedStatementPerConnectionSize;
    private Collection<?> connectionInitSqls;
    private Boolean sharePreparedStatements;
    private Integer connectionErrorRetryAttempts;
    private Boolean breakAfterAcquireFailure;
    private Boolean removeAbandoned;
    private Integer removeAbandonedTimeout;
    private Boolean logAbandoned;
    private Integer queryTimeout;
    private Integer transactionQueryTimeout;
    private Long    transactionThresholdMillis;

    private Integer maxOpenPreparedStatements;

    private DruidSlf4jConfig druidSlf4j;

    private DruidWallConfig wall;
}