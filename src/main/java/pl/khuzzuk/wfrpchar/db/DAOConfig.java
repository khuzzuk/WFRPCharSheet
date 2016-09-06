package pl.khuzzuk.wfrpchar.db;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import pl.khuzzuk.wfrpchar.db.annot.DaoBean;
import pl.khuzzuk.wfrpchar.db.annot.SelectiveQuery;
import pl.khuzzuk.wfrpchar.db.annot.WhiteWeapons;
import pl.khuzzuk.wfrpchar.messaging.Message;
import pl.khuzzuk.wfrpchar.messaging.publishers.*;
import pl.khuzzuk.wfrpchar.messaging.subscribers.*;

@Configuration
@PropertySource("classpath:/messages.properties")
public class DAOConfig {
    //Publishers

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
    @DaoBean
    public MultiContentSubscriber daoItemsSubscriber() {
        return new MultiBagSubscriber();
    }
}
