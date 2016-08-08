package pl.khuzzuk.wfrpchar.messaging.publishers;

import pl.khuzzuk.wfrpchar.messaging.Message;

public interface Publisher<T extends Message> {
    void publish(T message);
}
