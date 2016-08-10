package pl.khuzzuk.wfrpchar.messaging.subscribers;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import pl.khuzzuk.wfrpchar.messaging.BagMessage;
import pl.khuzzuk.wfrpchar.messaging.Bus;
import pl.khuzzuk.wfrpchar.messaging.BusBean;

import javax.inject.Inject;
import java.util.function.Consumer;

@ToString(exclude = {"bus", "reactor", "consumer"})
public abstract class AbstractContentSubscriber<T> implements ContentSubscriber<T> {
    @Inject
    @BusBean
    private Bus bus;
    @Setter
    @NonNull
    Consumer<T> consumer;
    @Setter
    @Getter
    @NonNull
    private String messageType;
    @Setter
    @NonNull
    private Reactor reactor;

    @Override
    public void receive(BagMessage<T> message) {
        if (reactor == null) {
            receive(message.getMessage());
        } else {
            reactor.resolve();
        }
    }

    @Override
    public void subscribe() {
        if (messageType == null) throw new IllegalStateException("No message type set for " + this);
        bus.subscribe(this, messageType);
    }

    @Override
    public void receive(T content) {
        consumer.accept(content);
    }

    @Override
    public void unSubscribe() {
        bus.unSubscribe(this, getMessageType());
    }
}
