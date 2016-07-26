package pl.khuzzuk.wfrpchar.config;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.inject.Named;

@Configuration
@EnableTransactionManagement
@ComponentScan({"pl.khuzzuk.wfrpchar.db"})
public class DAOConfig {
        @Bean
        @Named("factory")
        SessionFactory sessionFactory() {
            return new org.hibernate.cfg.Configuration().configure().buildSessionFactory();
        }
}
