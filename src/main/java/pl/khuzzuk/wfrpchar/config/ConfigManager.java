package pl.khuzzuk.wfrpchar.config;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import pl.khuzzuk.wfrpchar.gui.ScreensConfig;

import javax.inject.Named;

@Configuration
//@EnableAspectJAutoProxy(exposeProxy = true)
@ComponentScan({"pl.khuzzuk.wfrpchar"})
@Import(ScreensConfig.class)
public class ConfigManager {
    @Bean
    @Named("factory")
    SessionFactory sessionFactory() {
        return new org.hibernate.cfg.Configuration().configure().buildSessionFactory();
    }
}
