package pl.khuzzuk.wfrpchar.messaging;

import org.apache.commons.collections4.MultiValuedMap;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.concurrent.BlockingQueue;

@Component
@BusBean
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
            channel.put(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
