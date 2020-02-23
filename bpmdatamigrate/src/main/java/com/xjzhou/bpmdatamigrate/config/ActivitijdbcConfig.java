package com.xjzhou.bpmdatamigrate.config;
 
import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
 
import javax.sql.DataSource;
 
/**
 * @program: game_api
 * @description:
 * @author: Dading
 * @create: 2019-04-12 14:14
 * @version: 1.0
 **/
@Configuration
// 扫描 Mapper 接口并容器管理
@MapperScan(basePackages = ActivitijdbcConfig.PACKAGE, sqlSessionFactoryRef = "secondSqlSessionFactory")
public class ActivitijdbcConfig {
 
    // 精确到 cluster 目录，以便跟其他数据源隔离
    static final String PACKAGE = "com.xjzhou.bpmdatamigrate.config";
    static final String MAPPER_LOCATION = "classpath:mapper/second/*.xml";
 
    @Value("${activiti.datasource.url}")
    private String url;
 
    @Value("${activiti.datasource.username}")
    private String user;
 
    @Value("${activiti.datasource.password}")
    private String password;
 
    @Value("${activiti.datasource.driver-class-name}")
    private String driverClass;
 
    @Bean(name = "activitiDataSource")
    public DataSource activitiDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        return dataSource;
    }
 
    @Bean(name = "activitiTransactionManager")
    public DataSourceTransactionManager activitiTransactionManager() {
        return new DataSourceTransactionManager(activitiDataSource());
    }
 
    @Bean(name = "activitiSqlSessionFactory")
    public SqlSessionFactory activitiSqlSessionFactory(@Qualifier("activitiDataSource") DataSource activitiDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(activitiDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(ActivitijdbcConfig.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }
}