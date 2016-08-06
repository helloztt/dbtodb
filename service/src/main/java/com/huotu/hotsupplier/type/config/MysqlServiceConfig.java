package com.huotu.hotsupplier.type.config;

import com.huotu.hotsupplier.type.util.Springfactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by admin on 2016/1/21.
 */
@Configuration
@ComponentScan(basePackages = "com.huotu.hotsupplier.type")
@EnableJpaRepositories(
        basePackages = "com.huotu.hotsupplier.type.repository.mysql",
        entityManagerFactoryRef = "mysqlEntityManagerFactory",
        transactionManagerRef = "mysqlTransactionManager"
)
@EnableTransactionManagement
@ImportResource({"classpath:hbm_config_test.xml"})
public class MysqlServiceConfig {
        @Bean
        public Springfactory springfactory(){
                return new Springfactory();
        }
        @Bean
        public ThreadPoolTaskScheduler threadPoolTaskScheduler(){
                ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
                threadPoolTaskScheduler.setPoolSize(40);
                //设置线程池中为守护线程
                threadPoolTaskScheduler.setDaemon(true);
                return threadPoolTaskScheduler;
        }
}
