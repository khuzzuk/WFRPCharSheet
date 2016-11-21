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

public class LinkedEntityChooserController extends GuiContentSubscriber<Collection<? extends Named<String>>> implements Controller {
    @Inject
    private GuiPublisher publisher;
    @FXML
    private ListView<String> items;
    @Setter
    private Stage parent;
    @Setter
    private String choiceReady;

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
            EntitiesAdapter.sendToListView(items, c);
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
