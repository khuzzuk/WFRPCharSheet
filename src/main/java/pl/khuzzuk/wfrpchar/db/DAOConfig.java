package pl.khuzzuk.wfrpchar.db;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import pl.khuzzuk.wfrpchar.db.annot.DaoBean;
import pl.khuzzuk.wfrpchar.db.annot.Persist;
import pl.khuzzuk.wfrpchar.db.annot.SelectiveQuery;
import pl.khuzzuk.wfrpchar.db.annot.WhiteWeapons;
import pl.khuzzuk.wfrpchar.entities.items.WhiteWeaponType;
import pl.khuzzuk.wfrpchar.messaging.*;
import pl.khuzzuk.wfrpchar.messaging.publishers.*;
import pl.khuzzuk.wfrpchar.messaging.subscribers.*;

import java.util.List;

@Configuration
@PropertySource("classpath:/messages.properties")
public class DAOConfig {
    //Publishers
    @Bean
    @WhiteWeapons
    @Publishers
    @DaoBean
    public ContentPublisher<List<WhiteWeaponType>> weaponsTypesPublisher() {
        return new ContentPublisher<>();
    }

    @Bean
    @DaoBean
    @Publishers
    @WhiteWeapons
    @SelectiveQuery
    public BagPublisher<WhiteWeaponType> whiteQWeaponResultPublisher() {
        return new ContentPublisher<>();
    }

    @Bean
    @DaoBean
    @Publishers
    public Publisher<Message> communicatePublisher() {
        return new CommunicatePublisher();
    }

    //Subscribers

    @Bean(name = "dbResetSubscriber")
    @DaoBean
    @Subscribers
    public Subscriber<Message> dbResetSubscriber(
            @Value("${database.reset}") String dbReset) {
        Subscriber<Message> subscriber = new CommunicateSubscriber();
        subscriber.setMessageType(dbReset);
        return subscriber;
    }

    @Bean
    @Subscribers
    @WhiteWeapons
    @DaoBean
    public Subscriber<Message> whiteWeaponsQuerySubscriber(Environment environment) {
        Subscriber<Message> subscriber = new CommunicateSubscriber();
        subscriber.setMessageType(environment.getProperty("whiteWeapons.query"));
        return subscriber;
    }

    @Bean
    @Subscribers
    @DaoBean
    public MultiSubscriber<Message> simpleSubscriber() {
        return new MultiCommunicateSubscriber();
    }

    @Bean
    @Subscribers
    @WhiteWeapons
    @SelectiveQuery
    public ContentSubscriber<String> whiteWeaponNameSubscriber(
            @Value("${whiteWeapons.query.specific}") String msgType) {
        return new BagSubscriber<>(msgType);
    }

    @Bean
    @Subscribers
    @WhiteWeapons
    @Persist
    public ContentSubscriber<String> whiteWeaponSaveSubscriber(
            @Value("${whiteWeapons.save}") String msgType) {
        return new BagSubscriber<>(msgType);
    }
}
