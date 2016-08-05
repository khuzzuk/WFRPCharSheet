package pl.khuzzuk.wfrpchar.messaging;

import javax.annotation.PostConstruct;

public class BagSubscriber<T> extends AbstractContentSubscriber<T> {
    @Override
    @PostConstruct
    public void subscribe() {
        super.subscribe();
    }
}
