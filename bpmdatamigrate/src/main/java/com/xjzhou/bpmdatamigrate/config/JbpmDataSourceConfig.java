package com.xjzhou.bpmdatamigrate.config;
 
import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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
@MapperScan(basePackages = JbpmDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "jbpmSqlSessionFactory")
public class JbpmDataSourceConfig {
 
 
    // 精确到 master 目录，以便跟其他数据源隔离
    static final String PACKAGE = "com.xjzhou.bpmdatamigrate.config";
    static final String MAPPER_LOCATION = "classpath:mapper/master/*.xml";
 
    @Value("${jbpm.datasource.url}")
    private String url;
 
    @Value("${jbpm.datasource.username}")
    private String user;
 
    @Value("${jbpm.datasource.password}")
    private String password;
 
    @Value("${jbpm.datasource.driver-class-name}")
    private String driverClass;
 
    @Bean(name = "jbpmDataSource")
    @Primary
    public DataSource jbpmDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        return dataSource;
    }
 
    @Bean(name = "jbpmTransactionManager")
    @Primary
    public DataSourceTransactionManager jbpmTransactionManager() {
        return new DataSourceTransactionManager(jbpmDataSource());
    }
 
    @Bean(name = "jbpmSqlSessionFactory")
    @Primary
    public SqlSessionFactory masterSqlSessionFactory(@Qualifier("jbpmDataSource") DataSource jbpmDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(jbpmDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(JbpmDataSourceConfig.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }
 
}