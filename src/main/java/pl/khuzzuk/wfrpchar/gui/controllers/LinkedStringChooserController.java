package pl.khuzzuk.wfrpchar.gui.controllers;

import javafx.fxml.FXML;
import pl.khuzzuk.wfrpchar.entities.Named;

public class LinkedStringChooserController<T extends Named<String>> extends LinkedEntityChooserController<T> {
    @FXML
    private void sendSelected() {
        if (!items.getSelectionModel().isEmpty()) {
            publisher.publish(items.getSelectionModel().getSelectedItem().getName(), choiceReady);
            parent.hide();
        }
    }
}
