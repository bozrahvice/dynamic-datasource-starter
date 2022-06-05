package io.github.bozrahvice.shardingjdbc.spring.boot.autoconfiguration;

import io.github.bozrahvice.shardingjdbc.commons.DbHealthIndicator;
import io.github.bozrahvice.shardingjdbc.commons.HealthCheckAdapter;
import org.springframework.boot.actuate.autoconfigure.health.ConditionalOnEnabledHealthIndicator;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @ author ylpanda
 * @ since 1.0.0
 */
@Configuration
public class DynamicDataSourceHealthCheckAutoConfiguration {

    @Bean
    public HealthCheckAdapter healthCheckAdapter() {
        return new HealthCheckAdapter();
    }

    @ConditionalOnClass(AbstractHealthIndicator.class)
    @ConditionalOnEnabledHealthIndicator("dynamicDS")
    public class HealthIndicatorConfiguration{

        @Bean("dynamicDataSourceHealthCheck")
        public DbHealthIndicator healthIndicator(DataSource dataSource, HealthCheckAdapter healthCheckAdapter) {
            return new DbHealthIndicator(dataSource, "SELECT 1", healthCheckAdapter);
        }
    }




}