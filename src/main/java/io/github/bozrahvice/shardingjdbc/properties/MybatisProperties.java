package io.github.bozrahvice.shardingjdbc.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

import static io.github.bozrahvice.shardingjdbc.support.Constants.JDBC_DYNAMIC_PROPERTIES_CLASSPATH_FULL_FILE_NAME;
import static io.github.bozrahvice.shardingjdbc.support.Constants.MYBATIS_PROPERTIES_PREFIX_NAME;

/**
 * @author ylpanda
 * @since 1.0.0
 */
@Component
@ConfigurationProperties(prefix = MYBATIS_PROPERTIES_PREFIX_NAME)
@PropertySource({JDBC_DYNAMIC_PROPERTIES_CLASSPATH_FULL_FILE_NAME})
@Getter
@Setter
public class MybatisProperties {

    private static final ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();

    private String typeAliasesPackage;

    private String[] mapperLocations = new String[]{"classpath*:/mapper/**/*.xml"};


    public Resource[] resolveMapperLocations() {
        return Stream.of(Optional.ofNullable(this.mapperLocations).orElse(new String[0]))
                .flatMap(location -> Stream.of(getResources(location))).toArray(Resource[]::new);
    }

    private Resource[] getResources(String location) {
        try {
            return resourceResolver.getResources(location);
        } catch (IOException e) {
            return new Resource[0];
        }
    }

}