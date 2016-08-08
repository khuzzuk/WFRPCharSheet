package pl.khuzzuk.wfrpchar.messaging.subscribers;

import pl.khuzzuk.wfrpchar.messaging.BagMessage;

import java.util.function.Consumer;

public interface ContentSubscriber<T> extends Subscriber<BagMessage<T>> {
    void receive(T content);

    void setConsumer(Consumer<T> consumer);
}
