package io.github.bozrahvice.shardingjdbc.properties;

import io.github.bozrahvice.shardingjdbc.properties.model.ConnectionPoolProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import static io.github.bozrahvice.shardingjdbc.support.Constants.COMMON_CONNECTION_POOL_PROPERTIES_PREFIX_NAME;
import static io.github.bozrahvice.shardingjdbc.support.Constants.JDBC_DYNAMIC_PROPERTIES_CLASSPATH_FULL_FILE_NAME;

/**
 * @author ylpanda
 * @since 1.0.0
 */
@Component
@ConfigurationProperties(prefix = COMMON_CONNECTION_POOL_PROPERTIES_PREFIX_NAME)
@PropertySource({JDBC_DYNAMIC_PROPERTIES_CLASSPATH_FULL_FILE_NAME})
@Getter
@Setter
public class CommonConnectionPoolProperties {

    /**
     * 公共的链接池属性，若实际数据源未配置则以这个为准，配置了则以实际数据源下配置的为准
     */
    private ConnectionPoolProperty connectionPool = new ConnectionPoolProperty();
}