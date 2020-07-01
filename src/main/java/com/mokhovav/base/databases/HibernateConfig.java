package com.mokhovav.base.databases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class HibernateConfig {
    @Autowired
    private Environment env;

    @Bean
    @Conditional(PostgresDBCondition.class)
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("spring.datasource.dbcp2.driver-class-name", "org.postgresql.Driver"));
        dataSource.setUrl(env.getProperty("spring.datasource.url", "jdbc:postgresql://localhost:5432/projectDB"));
        dataSource.setUsername(env.getProperty("spring.datasource.username", "user"));
        dataSource.setPassword(env.getProperty("spring.datasource.password", "user"));
        return dataSource;
    }

    @Bean
    @Conditional(PostgresDBCondition.class)
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(env.getProperty("entities.package", "com.mokhovav.base.databases.sql"));
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    @Bean
    @Conditional(PostgresDBCondition.class)
    public PlatformTransactionManager hibernateTransactionManager() {
        HibernateTransactionManager transactionManager
                = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }

    private final Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty(
                "hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto", "update"));
        hibernateProperties.setProperty(
                "hibernate.dialect", env.getProperty("spring.jooq.sql-dialect", "org.hibernate.dialect.PostgreSQLDialect"));
        return hibernateProperties;
    }
}
