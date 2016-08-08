package pl.khuzzuk.wfrpchar.messaging.publishers;

import pl.khuzzuk.wfrpchar.messaging.BagMessage;

public interface BagPublisher<T> extends Publisher<BagMessage<T>> {
    void publish(T content, String msgType);
}
