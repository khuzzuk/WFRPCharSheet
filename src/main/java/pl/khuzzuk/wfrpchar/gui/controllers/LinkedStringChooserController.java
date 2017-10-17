package pl.khuzzuk.wfrpchar.gui.controllers;

import javafx.fxml.FXML;
import pl.khuzzuk.messaging.Bus;
import pl.khuzzuk.wfrpchar.entities.Named;

public class LinkedStringChooserController<T extends Named<String>> extends LinkedEntityChooserController<T> {
    public LinkedStringChooserController(Bus bus) {
        super(bus);
    }

    @FXML
    private void sendSelected() {
        if (!items.getSelectionModel().isEmpty()) {
            bus.send(choiceReady, items.getSelectionModel().getSelectedItem().getName());
            parent.hide();
        }
    }
}
