package pl.khuzzuk.wfrpchar.messaging;

public interface Subscriber<T extends Message> {
    void receive(T message);
    void subscribe();
    void setMessageType(String messageType);
    void setReactor(Reactor reactor);
}
