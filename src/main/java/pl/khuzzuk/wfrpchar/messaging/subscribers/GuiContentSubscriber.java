package pl.khuzzuk.wfrpchar.messaging.subscribers;

import javafx.application.Platform;

import javax.annotation.PostConstruct;

public class GuiContentSubscriber<T> extends AbstractContentSubscriber<T> {
    public GuiContentSubscriber(String msgType) {
        this.setMessageType(msgType);
    }

    @Override
    @PostConstruct
    public void subscribe() {
        super.subscribe();
    }
    @Override
    public void receive(T content) {
        Platform.runLater(() -> consumer.accept(content));
    }
}
