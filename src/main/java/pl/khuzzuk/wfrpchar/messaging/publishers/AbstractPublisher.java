package pl.khuzzuk.wfrpchar.messaging.publishers;

import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.messaging.Bus;
import pl.khuzzuk.wfrpchar.messaging.BusBean;
import pl.khuzzuk.wfrpchar.messaging.Message;

import javax.inject.Inject;

@Component
public abstract class AbstractPublisher implements Publisher<Message> {
    @Inject
    @BusBean
    private Bus bus;

    @Override
    public void publish(Message message) {
        bus.publish(message);
    }
}
