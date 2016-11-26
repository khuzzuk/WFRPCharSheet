package pl.khuzzuk.wfrpchar.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import lombok.Setter;
import pl.khuzzuk.wfrpchar.entities.Named;
import pl.khuzzuk.wfrpchar.gui.EntitiesAdapter;
import pl.khuzzuk.wfrpchar.gui.GuiPublisher;
import pl.khuzzuk.wfrpchar.messaging.subscribers.GuiContentSubscriber;

import javax.inject.Inject;
import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

public class LinkedEntityChooserController<T extends Named<String>> extends GuiContentSubscriber<Collection<T>> implements Controller {
    @Inject
    GuiPublisher publisher;
    @FXML
    ListView<T> items;
    @Setter
    Stage parent;
    @Setter
    String choiceReady;

    public LinkedEntityChooserController() {
        super("");
    }

    public LinkedEntityChooserController(String msgType, String choiceMsg) {
        super(msgType);
        choiceReady = choiceMsg;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeValidation();
    }

    public void subscribe() {
        setConsumer(c -> {
            EntitiesAdapter.sendToListViewUnchanged(items, c);
            parent.show();
        });
        super.subscribe();
    }

    @FXML
    private void sendSelected() {
        if (!items.getSelectionModel().isEmpty()) {
            publisher.publish(items.getSelectionModel().getSelectedItem(), choiceReady);
            parent.hide();
        }
    }

    @FXML
    private void cancel() {
        parent.hide();
    }
}
