package pl.khuzzuk.wfrpchar.messaging;

public interface BagMessage<T> extends Message {
    void setMessage(T content);
    T getMessage();
}
