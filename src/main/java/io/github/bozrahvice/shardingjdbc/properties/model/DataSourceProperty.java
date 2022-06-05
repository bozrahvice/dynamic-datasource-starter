package io.github.bozrahvice.shardingjdbc.properties.model;

import lombok.Getter;
import lombok.Setter;

import javax.sql.DataSource;

/**
 * @author ylpanda
 * @since 1.0.0
 */
@Getter
@Setter
public class DataSourceProperty {

    /**
     * 连接池类型
     */
    private Class<? extends DataSource> type;
    /**
     * JDBC driver
     */
    private String driverClassName;
    /**
     * JDBC url 地址
     */
    private String url;
    /**
     * JDBC 用户名
     */
    private String username;
    /**
     * JDBC 密码
     */
    private String password;

    private String name;

}