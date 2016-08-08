package pl.khuzzuk.wfrpchar.messaging;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.MultiValuedMap;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.messaging.publishers.Publisher;
import pl.khuzzuk.wfrpchar.messaging.subscribers.Subscriber;
import pl.khuzzuk.wfrpchar.messaging.subscribers.Subscribers;

import javax.inject.Inject;
import java.util.concurrent.BlockingQueue;

@Component
@BusBean
@Log4j2(topic = "MessageLogger")
public class Bus implements Publisher {
    private BlockingQueue<Message> channel;
    private MultiValuedMap<String, Subscriber<? extends Message>> subscribers;

    @Inject
    public Bus(@Channel BlockingQueue<Message> channel,
               @Subscribers MultiValuedMap<String, Subscriber<? extends Message>> subscribers) {
        this.channel = channel;
        this.subscribers = subscribers;
    }

    public void subscribe(Subscriber<? extends Message> subscriber, String messageType) {
        subscribers.put(messageType, subscriber);
    }

    @Override
    public void publish(Message message) {
        try {
            log.info("accepted message: " + message);
            channel.put(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
