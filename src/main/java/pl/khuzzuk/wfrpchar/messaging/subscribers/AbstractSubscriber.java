package pl.khuzzuk.wfrpchar.messaging.subscribers;

import lombok.*;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.messaging.Bus;
import pl.khuzzuk.wfrpchar.messaging.BusBean;
import pl.khuzzuk.wfrpchar.messaging.Message;

import javax.inject.Inject;

@Component
@ToString(exclude = {"bus", "reactor"})
public abstract class AbstractSubscriber implements Subscriber<Message> {
    @Inject
    @BusBean
    @Getter(AccessLevel.PACKAGE)
    @Setter(AccessLevel.PACKAGE)
    private Bus bus;
    @Setter
    @Getter
    @NonNull
    private String messageType;
    @Setter
    @Getter(AccessLevel.PACKAGE)
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

    @Override
    public void unSubscribe() {
        bus.unSubscribe(this, messageType);
    }
}
