package pl.khuzzuk.wfrpchar.messaging;

public interface BagPublisher<T> extends Publisher<BagMessage<T>> {
    void publish(T content, String msgType);
}
