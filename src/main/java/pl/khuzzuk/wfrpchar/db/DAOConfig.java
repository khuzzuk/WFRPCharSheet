package pl.khuzzuk.wfrpchar.db;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import pl.khuzzuk.wfrpchar.db.annot.Manager;
import pl.khuzzuk.wfrpchar.db.annot.Weapons;
import pl.khuzzuk.wfrpchar.entities.items.WeaponType;
import pl.khuzzuk.wfrpchar.messaging.*;

import javax.inject.Inject;
import java.util.List;

@Configuration
@Lazy
public class DAOConfig {
    public static final String WEAPONS_QUERRY = "weaponsQuerry";
    public static final String WEAPONS_RESULT = "weaponsQuerry";
    @Inject
    @Subscribers
    @Manager
    private DAOReactor daoReactor;

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
    public Subscriber<Message> weaponsQuerySubscriber() {
        Subscriber<Message> subscriber = new CommunicateSubscriber();
        subscriber.setMessageType(WEAPONS_QUERRY);
        subscriber.setReactor(daoReactor::getAllWeapons);
        return subscriber;
    }
}
