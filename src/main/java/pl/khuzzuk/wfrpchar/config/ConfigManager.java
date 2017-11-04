package pl.khuzzuk.wfrpchar.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import pl.khuzzuk.messaging.Bus;
import pl.khuzzuk.wfrpchar.gui.ScreensConfig;
import pl.khuzzuk.wfrpchar.repo.Repository;

import javax.inject.Named;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

@Configuration
@ComponentScan({"pl.khuzzuk.wfrpchar"})
@Import({ScreensConfig.class})
public class ConfigManager {
    @Bean
    @Named("factory")
    SessionFactory sessionFactory() {
        return new org.hibernate.cfg.Configuration().configure().buildSessionFactory();
    }

    @Bean
    @Named("messages")
    Properties properties() {
        Properties properties = new Properties();
        try {
            properties.load(new BufferedReader(new InputStreamReader(
                    ConfigManager.class.getResourceAsStream("/messages.properties"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    @Bean
    Bus bus() {
        return Bus.initializeBus(true);
    }

    @Bean
    ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        objectMapper.enableDefaultTyping();
        return objectMapper;
    }

    @Bean
    @Autowired
    Repository getRepo(ObjectMapper objectMapper) throws IOException {
        return objectMapper.readValue(new File("repository.json"), Repository.class);
    }
}
