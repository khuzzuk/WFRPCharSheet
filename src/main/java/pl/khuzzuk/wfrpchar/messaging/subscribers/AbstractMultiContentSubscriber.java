package pl.khuzzuk.wfrpchar.messaging.subscribers;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.HashSetValuedHashMap;
import pl.khuzzuk.wfrpchar.messaging.BagMessage;
import pl.khuzzuk.wfrpchar.messaging.Bus;
import pl.khuzzuk.wfrpchar.messaging.BusBean;

import javax.inject.Inject;
import java.util.function.Consumer;

@Log4j2(topic = "MessageLogger")
public abstract class AbstractMultiContentSubscriber implements MultiContentSubscriber {
    @Inject
    @BusBean
    private Bus bus;
    @Getter
    @Setter
    private String messageType;
    private MultiValuedMap<String, Consumer> consumers;
    private MultiValuedMap<String, Reactor> reactors;
    @Override
    public void receive(BagMessage message) {
        try {
            consumers.get(message.getType()).stream().forEach(c -> c.accept(message.getMessage()));
        } catch (ClassCastException e) {
            log.error("Wrong type of Message for Consumer: " + consumers.get(message.getType()) +
                    " and message type: " + message.getType());
        }
        reactors.get(message.getType()).stream().forEach(Reactor::resolve);
    }

    @Override
    public <T> void subscribe(String msgType, Consumer<T> consumer) {
        assureInit();
        consumers.put(msgType, consumer);
        bus.subscribe(this, msgType);
    }

    @Override
    public void subscribe(String msgType, Reactor reactor) {
        assureInit();
        reactors.put(msgType, reactor);
        bus.subscribe(this, msgType);
    }

    @Override
    public void unSubscribe(String msgType) {
        assureInit();
        bus.unSubscribe(this, messageType);
        consumers.remove(msgType);
        reactors.remove(msgType);
    }

    @Override
    public void subscribe() {
        bus.subscribe(this, messageType);
    }

    @Override
    public void unSubscribe() {
        bus.unSubscribe(this, messageType);
    }

    private void assureProperState() {
        if (messageType == null) {
            throw new IllegalStateException("No message type set for this subscriber");
        }
    }

    private void assureInit() {
        if (consumers == null) {
            consumers = new HashSetValuedHashMap<>();
        }
        if (reactors == null) {
            reactors = new HashSetValuedHashMap<>();
        }
    }

    @Override
    public void setConsumer(Consumer consumer) {
        assureProperState();
        assureInit();
        consumers.put(messageType, consumer);
    }

    @Override
    public void setReactor(Reactor reactor) {
        assureProperState();
        assureInit();
        reactors.put(messageType, reactor);
    }

    @Override
    public void receive(Object content) {
        consumers.get(messageType).stream().forEach(c -> c.accept(content));
        reactors.get(messageType).stream().forEach(Reactor::resolve);
    }
}
