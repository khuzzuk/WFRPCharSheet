package pl.khuzzuk.wfrpchar.messaging.subscribers;

import pl.khuzzuk.wfrpchar.messaging.Message;

public interface Subscriber<T extends Message> {
    void receive(T message);
    void subscribe();
    void setMessageType(String messageType);
    String getMessageType();
    void setReactor(Reactor reactor);
    void unSubscribe();
}
