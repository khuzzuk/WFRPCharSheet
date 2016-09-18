package pl.khuzzuk.wfrpchar.messaging.subscribers;

import javafx.application.Platform;
import pl.khuzzuk.wfrpchar.messaging.Message;

public class GuiCommunicateSubscriber extends CommunicateSubscriber {
    @Override
    public void receive(Message message) {
        Platform.runLater(() -> super.receive(message));
    }
}
