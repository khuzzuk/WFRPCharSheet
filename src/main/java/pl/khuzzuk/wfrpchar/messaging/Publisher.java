package pl.khuzzuk.wfrpchar.messaging;

public interface Publisher<T extends Message> {
    void publish(T message);
}
