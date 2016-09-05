package pl.khuzzuk.wfrpchar.db;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import pl.khuzzuk.wfrpchar.db.annot.DaoBean;
import pl.khuzzuk.wfrpchar.db.annot.SelectiveQuery;
import pl.khuzzuk.wfrpchar.db.annot.WhiteWeapons;
import pl.khuzzuk.wfrpchar.entities.items.WhiteWeaponType;
import pl.khuzzuk.wfrpchar.messaging.Message;
import pl.khuzzuk.wfrpchar.messaging.publishers.*;
import pl.khuzzuk.wfrpchar.messaging.subscribers.*;

import java.util.Collection;

@Configuration
@PropertySource("classpath:/messages.properties")
public class DAOConfig {
    //Publishers
    @Bean
    @WhiteWeapons
    @Publishers
    @DaoBean
    public ContentPublisher<Collection<WhiteWeaponType>> weaponsTypesPublisher() {
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

    @Bean
    @Publishers
    public MultiContentPublisher entitiesPublisher() {
        return new MultiBagPublisher();
    }

    //Subscribers
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
    @DaoBean
    public MultiContentSubscriber daoItemsSubscriber() {
        return new MultiBagSubscriber();
    }
}
