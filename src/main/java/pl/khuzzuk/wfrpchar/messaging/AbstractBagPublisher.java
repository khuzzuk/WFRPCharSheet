package pl.khuzzuk.wfrpchar.messaging;

public abstract class AbstractBagPublisher<T> extends AbstractPublisher<BagMessage<T>> implements BagPublisher <T> {
    @Override
    public void publish(T content) {
        BagMessage<T> message = new ContentMessage<T>();
        message.setMessage(content);
        publish(message);
    }
}
