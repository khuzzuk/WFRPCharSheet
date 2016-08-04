package pl.khuzzuk.wfrpchar.messaging;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public abstract class AbstractPublisher implements Publisher<Message> {
    @Inject
    @BusBean
    private Bus bus;

    @Override
    public void publish(Message message) {
        bus.publish(message);
    }
}
