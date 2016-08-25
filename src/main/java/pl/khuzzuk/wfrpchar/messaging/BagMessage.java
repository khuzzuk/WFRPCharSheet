package pl.khuzzuk.wfrpchar.messaging;

public interface BagMessage<T> extends Message {
    BagMessage<T> setMessage(T content);
    T getMessage();
}
