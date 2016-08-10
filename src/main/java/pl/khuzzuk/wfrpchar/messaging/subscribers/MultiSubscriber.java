package pl.khuzzuk.wfrpchar.messaging.subscribers;

import pl.khuzzuk.wfrpchar.messaging.Message;

public interface MultiSubscriber<T extends Message> extends Subscriber<T> {
    void subscribe(String msgType, Reactor reactor);

    void unSubscribe(String msgType);
}
