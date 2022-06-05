package io.github.bozrahvice.shardingjdbc.spring.boot.autoconfiguration;

import com.alibaba.druid.support.http.StatViewServlet;
import io.github.bozrahvice.shardingjdbc.annotation.DS;
import io.github.bozrahvice.shardingjdbc.aop.DynamicDataSourceAnnotationAdvisor;
import io.github.bozrahvice.shardingjdbc.aop.DynamicDataSourceAnnotationInterceptor;
import io.github.bozrahvice.shardingjdbc.commons.DbHealthIndicator;
import io.github.bozrahvice.shardingjdbc.commons.HealthCheckAdapter;
import io.github.bozrahvice.shardingjdbc.dynamicroute.DynamicRoutingDataSource;
import io.github.bozrahvice.shardingjdbc.properties.CommonConnectionPoolProperties;
import io.github.bozrahvice.shardingjdbc.properties.MybatisProperties;
import io.github.bozrahvice.shardingjdbc.properties.ShardingJdbcDataSourceProperties;
import io.github.bozrahvice.shardingjdbc.provider.DynamicDataSourceProvider;
import io.github.bozrahvice.shardingjdbc.provider.DynamicDataSourceServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.aop.Advisor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Role;
import org.springframework.core.Ordered;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.util.HashMap;

/**
 * @author ylpanda
 * @since 1.0.0
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(value = {ShardingJdbcDataSourceProperties.class, MybatisProperties.class, CommonConnectionPoolProperties.class})
@Import(value = {DynamicDataSourceCreatorAutoConfiguration.class,DynamicDataSourceHealthCheckAutoConfiguration.class})
public class DynamicDataSourceAutoConfiguration {


    @Resource
    private MybatisProperties mybatisProperties;

    @Bean
    @ConditionalOnMissingBean
    public DynamicDataSourceProvider dynamicDataSourceProvider() {
        return new DynamicDataSourceServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public DataSource dataSource(DynamicDataSourceProvider dynamicDataSourceProvider) {
        DynamicRoutingDataSource dataSource = new DynamicRoutingDataSource();
        dataSource.setProvider(dynamicDataSourceProvider);
        return dataSource;
    }

    @Role(value = BeanDefinition.ROLE_INFRASTRUCTURE)
    @Bean
    public Advisor dynamicDatasourceAnnotationAdvisor() {
        DynamicDataSourceAnnotationInterceptor interceptor = new DynamicDataSourceAnnotationInterceptor(true);
        DynamicDataSourceAnnotationAdvisor advisor = new DynamicDataSourceAnnotationAdvisor(interceptor, DS.class);
        advisor.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return advisor;
    }

    @Bean
    @ConditionalOnMissingBean
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        factory.setTypeAliasesPackage(mybatisProperties.getTypeAliasesPackage());
        org.springframework.core.io.Resource[] mapperLocations = mybatisProperties.resolveMapperLocations();
        if (!ObjectUtils.isEmpty(mapperLocations)) {
            factory.setMapperLocations(mapperLocations);
        }
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setLazyLoadingEnabled(true);
        configuration.setAggressiveLazyLoading(false);
        configuration.setCacheEnabled(true);
        factory.setConfiguration(configuration);
        return factory.getObject();
    }

    @Bean
    @ConditionalOnMissingBean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) throws FileNotFoundException {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean
    public PlatformTransactionManager txManager(@Qualifier("dataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public ServletRegistrationBean statViewServlet() {
        ServletRegistrationBean<StatViewServlet> bean = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");
        HashMap<String, String> initParameters = new HashMap<>();
        initParameters.put("loginUsername", "admin");
        initParameters.put("loginPassword", "admin");
        initParameters.put("allow", "");
        bean.setInitParameters(initParameters);
        return bean;
    }
}