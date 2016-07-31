package pl.khuzzuk.wfrpchar.config;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.*;

import javax.inject.Named;

@Configuration
//@EnableAspectJAutoProxy(exposeProxy = true)
@ComponentScan({"pl.khuzzuk.wfrpchar"})
public class ConfigManager {
    @Bean
    @Named("factory")
    SessionFactory sessionFactory() {
        return new org.hibernate.cfg.Configuration().configure().buildSessionFactory();
    }
}
