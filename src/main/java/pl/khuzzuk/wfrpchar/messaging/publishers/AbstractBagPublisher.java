package pl.khuzzuk.wfrpchar.messaging.publishers;

import pl.khuzzuk.wfrpchar.messaging.BagMessage;
import pl.khuzzuk.wfrpchar.messaging.Bus;
import pl.khuzzuk.wfrpchar.messaging.BusBean;
import pl.khuzzuk.wfrpchar.messaging.ContentMessage;

import javax.inject.Inject;

public abstract class AbstractBagPublisher<T> implements BagPublisher<T> {
    @Inject
    @BusBean
    private Bus bus;
    @Override
    public void publish(T content, String msgType) {
        BagMessage<T> message = new ContentMessage<>();
        message.setMessage(content);
        message.setType(msgType);
        publish(message);
    }

    @Override
    public void publish(BagMessage<T> message) {
        bus.publish(message);
    }
}
