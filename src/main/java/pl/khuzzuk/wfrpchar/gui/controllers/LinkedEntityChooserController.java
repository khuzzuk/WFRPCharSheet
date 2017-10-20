package pl.khuzzuk.wfrpchar.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import lombok.Setter;
import pl.khuzzuk.messaging.Bus;
import pl.khuzzuk.wfrpchar.entities.Named;
import pl.khuzzuk.wfrpchar.gui.EntitiesAdapter;

import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

public class LinkedEntityChooserController<T extends Named<String>> implements Controller {
    Bus bus;
    @FXML
    ListView<T> items;
    @Setter
    Stage parent;
    @Setter
    String choiceReady;
    @Setter
    private String msgType;

    public LinkedEntityChooserController(Bus bus) {
        this.bus = bus;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeValidation();
        bus.<Collection<T>>setGuiReaction(msgType, c -> {
            EntitiesAdapter.sendToListViewUnchanged(items, c);
            parent.show();
        });
    }

    @FXML
    private void sendSelected() {
        if (!items.getSelectionModel().isEmpty()) {
            bus.send(choiceReady, items.getSelectionModel().getSelectedItem());
            parent.hide();
        }
    }

    @FXML
    private void cancel() {
        parent.hide();
    }
}
