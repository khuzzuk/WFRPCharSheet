package pl.khuzzuk.wfrpchar.db;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import pl.khuzzuk.wfrpchar.db.annot.DaoBean;
import pl.khuzzuk.wfrpchar.db.annot.SelectiveQuery;
import pl.khuzzuk.wfrpchar.db.annot.WhiteWeapons;
import pl.khuzzuk.wfrpchar.entities.items.WhiteWeaponType;
import pl.khuzzuk.wfrpchar.messaging.*;

import java.util.List;

@Configuration
@PropertySource("classpath:/messages.properties")
public class DAOConfig {
    //Publishers
    @Bean
    @WhiteWeapons
    @Publishers
    public ContentPublisher<List<WhiteWeaponType>> weaponsTypesPublisher() {
        return new ContentPublisher<>();
    }

    @Bean
    @DaoBean
    @Publishers
    @WhiteWeapons
    public BagPublisher<WhiteWeaponType> whiteQWeaponResultPublisher() {
        return new ContentPublisher<>();
    }


    //Subscribers
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
    @WhiteWeapons
    @SelectiveQuery
    public ContentSubscriber<String> whiteWeaponNameSubscriber(
            @Value("${whiteWeapons.query.specific}") String msgType) {
        ContentSubscriber<String> subscriber = new BagSubscriber<>();
        subscriber.setMessageType(msgType);
        return subscriber;
    }
}
