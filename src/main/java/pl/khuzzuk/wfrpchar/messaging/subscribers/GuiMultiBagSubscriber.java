package pl.khuzzuk.wfrpchar.messaging.subscribers;

import javafx.application.Platform;
import pl.khuzzuk.wfrpchar.messaging.BagMessage;

public class GuiMultiBagSubscriber extends AbstractMultiContentSubscriber {
    @Override
    public void receive(BagMessage message) {
        Platform.runLater(() -> super.receive(message));
    }

    @Override
    public void receive(Object content) {
        Platform.runLater(() -> super.receive(content));
    }
}
