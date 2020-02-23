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
@MapperScan(basePackages = "com.xjzhou.jdbc.camunda", sqlSessionFactoryRef = "camundaSqlSessionFactory")
public class CamundaDataSourceConfig {
 
    @Bean(name = "camundaDataSource")
    @ConfigurationProperties(prefix="spring.datasource.camunda")
    public DataSource camundaDataSource() {
        return DataSourceBuilder.create().build();
    }
 
    @Bean(name = "camundaTransactionManager")
    public DataSourceTransactionManager camundaTransactionManager() {
        return new DataSourceTransactionManager(camundaDataSource());
    }
 
    @Bean(name = "camundaSqlSessionFactory")
    public SqlSessionFactory camundaSqlSessionFactory(@Qualifier("camundaDataSource") DataSource camundaDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(camundaDataSource);
        return sessionFactory.getObject();
    }
    @Bean(name="camundaSqlSessionTemplate")
    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("camundaSqlSessionFactory") SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}