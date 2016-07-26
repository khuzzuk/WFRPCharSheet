package pl.khuzzuk.wfrpchar.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Configuration
@EnableTransactionManagement
@ComponentScan({"pl.khuzzuk.wfrpchar.db"})
public class DAOConfig {
    /*
        @Bean
        SessionFactory sessionFactory() {
            return new org.hibernate.cfg.Configuration().configure().buildSessionFactory();
        }
    */
    @Bean
    EntityManager entityManager() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("wfrpChar");
        return Persistence.createEntityManagerFactory("wfrpChar").createEntityManager();
    }
}
