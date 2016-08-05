package pl.khuzzuk.wfrpchar.messaging;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.HashSetValuedHashMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import pl.khuzzuk.wfrpchar.db.annot.SelectiveQuery;
import pl.khuzzuk.wfrpchar.entities.items.WeaponType;
import pl.khuzzuk.wfrpchar.gui.MainWindowBean;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@PropertySource("classpath:messages.properties")
public class MessageBusConfig {
    @Bean
    @Channel
    BlockingQueue<Message> channel() {
        return new ArrayBlockingQueue<>(1024);
    }

    @Bean
    @Subscribers
    MultiValuedMap<String, Subscriber<? extends Message>> subscribers() {
        return new HashSetValuedHashMap<>();
    }

    @Bean
    @Named("pool")
    ExecutorService pool() {
        return Executors.newFixedThreadPool(3);
    }

    @Bean
    @Publishers
    @MainWindowBean
    public Publisher<Message> mainWindowPublisher() {
        return new CommunicatePublisher();
    }

    @Bean
    @Publishers
    @SelectiveQuery
    @MainWindowBean
    public BagPublisher<String> queryRequestsPublisher() {
        return new ContentPublisher<>();
    }

    @Bean
    @Subscribers
    @MainWindowBean
    @Inject
    public ContentSubscriber<List<WeaponType>> weaponTypeSubscriber(
            @Value("${whiteWeapons.result}") String resultsMessageType) {
        ContentSubscriber<List<WeaponType>> subscriber = new BagSubscriber<>();
        subscriber.setMessageType(resultsMessageType);
        return subscriber;
    }
}
