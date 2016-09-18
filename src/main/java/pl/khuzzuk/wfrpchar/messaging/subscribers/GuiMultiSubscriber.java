package pl.khuzzuk.wfrpchar.messaging.subscribers;

import javafx.application.Platform;
import pl.khuzzuk.wfrpchar.messaging.Message;

public class GuiMultiSubscriber extends AbstractMultiSubscriber {
    @Override
    public void receive(Message message) {
        Platform.runLater(() -> super.receive(message));
    }
}
