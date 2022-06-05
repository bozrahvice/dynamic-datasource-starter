package io.github.bozrahvice.shardingjdbc.spring.boot.autoconfiguration;

import io.github.bozrahvice.shardingjdbc.initdatasource.DataSourceCreator;
import io.github.bozrahvice.shardingjdbc.initdatasource.DbcpDataSourceCreator;
import io.github.bozrahvice.shardingjdbc.initdatasource.DruidDataSourceCreator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ylpanda
 * @since 1.0.0
 */
@Slf4j
@Configuration
@AllArgsConstructor
public class DynamicDataSourceCreatorAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public DataSourceCreator dataSourceCreator() {
        DataSourceCreator dataSourceCreator = new DataSourceCreator();
        dataSourceCreator.setDbcpDataSourceCreator(basicDataSourceCreator());
        dataSourceCreator.setDruidDataSourceCreator(druidDataSourceCreator());
        return dataSourceCreator;
    }

    @Bean
    @ConditionalOnMissingBean
    public DbcpDataSourceCreator basicDataSourceCreator() {
        return new DbcpDataSourceCreator();
    }


    @Bean
    @ConditionalOnMissingBean
    public DruidDataSourceCreator druidDataSourceCreator() {
        return new DruidDataSourceCreator();
    }
}