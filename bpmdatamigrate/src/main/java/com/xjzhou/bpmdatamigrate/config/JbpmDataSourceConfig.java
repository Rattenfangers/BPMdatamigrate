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
import org.springframework.context.annotation.Primary;
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
@MapperScan(basePackages = "com.xjzhou.jdbc.jbpm" ,sqlSessionFactoryRef = "jbpmSqlSessionFactory")
public class JbpmDataSourceConfig {
 
    @Bean(name = "jbpmDataSource")
    @ConfigurationProperties(prefix="spring.datasource.jbpm")
    @Primary
    public DataSource jbpmDataSource() {
        return DataSourceBuilder.create().build();
    }
 
    @Bean(name = "jbpmTransactionManager")
    @Primary
    public DataSourceTransactionManager jbpmTransactionManager(@Qualifier("jbpmDataSource") DataSource jbpmDataSource) {
        return new DataSourceTransactionManager(jbpmDataSource);
    }
 
    @Bean(name = "jbpmSqlSessionFactory")
    @Primary
    public SqlSessionFactory jbpmSqlSessionFactory(@Qualifier("jbpmDataSource") DataSource jbpmDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(jbpmDataSource);
        return sessionFactory.getObject();
    }
    @Bean(name="jbpmSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("jbpmSqlSessionFactory") SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }
 
}