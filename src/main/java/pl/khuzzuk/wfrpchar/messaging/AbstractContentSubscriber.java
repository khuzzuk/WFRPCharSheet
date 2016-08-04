package pl.khuzzuk.wfrpchar.messaging;

import lombok.NonNull;
import lombok.Setter;

import javax.inject.Inject;
import java.util.function.Consumer;

public abstract class AbstractContentSubscriber<T> implements ContentSubscriber<T> {
    @Inject
    @BusBean
    private Bus bus;
    @Setter
    @NonNull
    private Consumer<T> consumer;
    @Setter
    @NonNull
    private String messageType;
    @Setter
    @NonNull
    private Reactor reactor;

    @Override
    public void receive(BagMessage<T> message) {
        reactor.resolve();
    }

    @Override
    public void subscribe() {
        bus.subscribe(this, messageType);
    }

    @Override
    public void receive(T content) {
        consumer.accept(content);
    }
}
