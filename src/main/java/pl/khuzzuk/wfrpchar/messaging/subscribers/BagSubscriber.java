package pl.khuzzuk.wfrpchar.messaging.subscribers;

import javax.annotation.PostConstruct;

public class BagSubscriber<T> extends AbstractContentSubscriber<T> {
    public BagSubscriber(String msgType) {
        setMessageType(msgType);
    }

    @Override
    @PostConstruct
    public void subscribe() {
        super.subscribe();
    }
}
