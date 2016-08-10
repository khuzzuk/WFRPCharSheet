package pl.khuzzuk.wfrpchar.messaging.subscribers;

import pl.khuzzuk.wfrpchar.messaging.BagMessage;

import java.util.function.Consumer;

public interface MultiContentSubscriber extends MultiSubscriber<BagMessage>, ContentSubscriber {
    <T> void subscribe(String msgType, Consumer<T> consumer);
}
