package pl.khuzzuk.wfrpchar.messaging.subscribers;

import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
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
