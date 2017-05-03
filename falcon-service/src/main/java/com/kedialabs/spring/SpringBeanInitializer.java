package com.kedialabs.spring;

import java.beans.PropertyVetoException;
import java.util.Properties;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import com.kedialabs.application.config.AppConfig;
import com.kedialabs.application.config.DBConfig;
import com.mchange.v2.c3p0.ComboPooledDataSource;

import io.dropwizard.setup.Environment;

@Configuration
@ComponentScan(basePackageClasses = {})
public class SpringBeanInitializer {
    
    @Inject
    private AppConfig config;
    
    @Inject
    private Environment env;
    
    @Bean
    public DBConfig getDBConfig(){
        return config.getDbConfig();
    }
    
    @Bean
    @Inject
    public ComboPooledDataSource getComboPooledDataSource(DBConfig dbConfig) throws PropertyVetoException{
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource.setDriverClass(dbConfig.getDriverClass());
        comboPooledDataSource.setJdbcUrl(dbConfig.getUrl());
        comboPooledDataSource.setUser(dbConfig.getUser());
        comboPooledDataSource.setPassword(dbConfig.getPassword());
        comboPooledDataSource.setAcquireIncrement(dbConfig.getConnectionPoolProperties().getAcquireIncrement());
        comboPooledDataSource.setInitialPoolSize(dbConfig.getConnectionPoolProperties().getInitialPoolSize());
        comboPooledDataSource.setMinPoolSize(dbConfig.getConnectionPoolProperties().getMinPoolSize());
        comboPooledDataSource.setMaxPoolSize(dbConfig.getConnectionPoolProperties().getMaxPoolSize());
        comboPooledDataSource.setMaxIdleTime(dbConfig.getConnectionPoolProperties().getMaxIdleTime());
        comboPooledDataSource.setMaxStatements(dbConfig.getConnectionPoolProperties().getMaxStatements());
        comboPooledDataSource.setIdleConnectionTestPeriod(dbConfig.getConnectionPoolProperties().getIdleConnectionTestPeriod());
        return comboPooledDataSource;
    }
    
    @Bean
    @Inject
    public LocalContainerEntityManagerFactoryBean getLocalContainerEntityManagerFactoryBean(DBConfig dbConfig,ComboPooledDataSource dataSource) throws PropertyVetoException{
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setPersistenceXmlLocation("classpath:persistence.xml");
        entityManagerFactory.setPersistenceUnitName("falcon");
        entityManagerFactory.setDataSource(getComboPooledDataSource(dbConfig));
        Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.ejb.naming_strategy", "org.hibernate.cfg.ImprovedNamingStrategy");
        jpaProperties.put("hibernate.show_sql", Boolean.FALSE);
        jpaProperties.put("hibernate.format_sql", Boolean.FALSE);
        jpaProperties.put("hibernate.current_session_context_class", "thread");
        jpaProperties.put("hibernate.dialect", dbConfig.getHibernateProperties().getDialect());
        jpaProperties.put("hibernate.connection.release_mode", "AFTER_TRANSACTION");
        //jpaProperties.put("hibernate.hbm2ddl.auto", "update");
        entityManagerFactory.setJpaProperties(jpaProperties);
        return entityManagerFactory;
    }
    
    @Bean
    @Inject
    public JpaTransactionManager getJpaTransactionManager(EntityManagerFactory entityManagerFactory){
        return new JpaTransactionManager(entityManagerFactory);
    }
    
}
