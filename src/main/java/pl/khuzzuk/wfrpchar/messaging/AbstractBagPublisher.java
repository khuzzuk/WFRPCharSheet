package pl.khuzzuk.wfrpchar.messaging;

import javax.inject.Inject;

public abstract class AbstractBagPublisher<T> implements BagPublisher<T> {
    @Inject
    @BusBean
    private Bus bus;
    @Override
    public void publish(T content) {
        BagMessage<T> message = new ContentMessage<T>();
        message.setMessage(content);
        publish(message);
    }

    @Override
    public void publish(BagMessage<T> message) {
        bus.publish(message);
    }
}
