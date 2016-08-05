package pl.khuzzuk.wfrpchar.db;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import pl.khuzzuk.wfrpchar.db.annot.Weapons;
import pl.khuzzuk.wfrpchar.entities.items.WeaponType;
import pl.khuzzuk.wfrpchar.messaging.*;

import java.util.List;

@Configuration
@PropertySource("classpath:/messages.properties")
public class DAOConfig {
    public static final String WEAPONS_QUERRY = "wq";
    public static final String WEAPONS_RESULT = "wr";

    //Publishers
    @Bean
    @Weapons
    @Publishers
    public ContentPublisher<List<WeaponType>> weaponsTypesPublisher() {
        return new ContentPublisher<>();
    }


    //Subscribers
    @Bean
    @Subscribers
    @Weapons
    public Subscriber<Message> weaponsQuerySubscriber(Environment environment) {
        Subscriber<Message> subscriber = new CommunicateSubscriber();
        subscriber.setMessageType(environment.getProperty("weapons.query"));
        return subscriber;
    }
}
