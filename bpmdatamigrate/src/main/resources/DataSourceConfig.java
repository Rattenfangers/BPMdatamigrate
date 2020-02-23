package src.main.resources;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfig {
    
    @Bean(name = "jbpmDataSource")
    @Qualifier("jbpmDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.jbpm") // application.properteis中对应属性的前缀
    public DataSource dataSourceJbpm() {
        return DataSourceBuilder.create().build();
    }
    
    @Bean(name = "activitiDataSource")
    @Qualifier("activitiDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.activiti") // application.properteis中对应属性的前缀
    public DataSource dataSourceActiviti() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "camundaDataSource")
    @Qualifier("camundaDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.camunda") // application.properteis中对应属性的前缀
    public DataSource dataSourceCamunda() {
        return DataSourceBuilder.create().build();
    }
}