package com.xjzhou.bpmdatamigrate.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
@MapperScan(basePackages = "com.xjzhou.jdbc.activiti", sqlSessionFactoryRef = "activitiSqlSessionFactory")
public class ActivitiDataSourceConfig {
 
    @Bean(name = "activitiDataSource")
    @ConfigurationProperties(prefix="spring.datasource.activiti")
    public DataSource activitiDataSource() {
        return DataSourceBuilder.create().build();
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
        return sessionFactory.getObject();
    }
    @Bean(name="activitiSqlSessionTemplate")
    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("activitiSqlSessionFactory") SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}