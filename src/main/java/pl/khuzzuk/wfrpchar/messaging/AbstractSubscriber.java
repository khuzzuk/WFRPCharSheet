package pl.khuzzuk.wfrpchar.messaging;

import lombok.NonNull;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public abstract class AbstractSubscriber implements Subscriber<Message> {
    @Inject
    @BusBean
    private Bus bus;
    @Setter
    @NonNull
    private String messageType;
    @Setter
    @NonNull
    private Reactor reactor;

    @Override
    public void subscribe() {
        if (messageType == null) throw new IllegalStateException("No message type set for " + this);
        bus.subscribe(this, messageType);
    }

    @Override
    public void receive(Message message) {
        reactor.resolve();
    }
}
