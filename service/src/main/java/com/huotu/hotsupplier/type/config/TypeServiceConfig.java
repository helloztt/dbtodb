package com.huotu.hotsupplier.type.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

/**
 * Created by admin on 2016/1/21.
 */
@Configuration
@ComponentScan(basePackages = "com.huotu.hotsupplier.type.service")
@EnableJpaRepositories(
        basePackages = "com.huotu.hotsupplier.type.repository",
        entityManagerFactoryRef = "sqlEntityManagerFactory",
        transactionManagerRef = "sqlTransactionManager"
)
@EnableTransactionManagement
@ImportResource({"classpath:hbm_config_test.xml"})
public class TypeServiceConfig {
}
