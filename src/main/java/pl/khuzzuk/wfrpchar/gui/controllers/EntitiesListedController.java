package pl.khuzzuk.wfrpchar.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import pl.khuzzuk.wfrpchar.entities.Named;
import pl.khuzzuk.wfrpchar.gui.GuiPublisher;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Properties;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public abstract class EntitiesListedController implements Controller {
    @FXML
    TextField name;
    @Inject
    GuiPublisher guiPublisher;
    @FXML
    TextArea specialFeatures;
    @Inject
    @javax.inject.Named("messages")
    Properties messages;
    Runnable saveAction;
    Consumer<String> removeAction;
    Consumer<String> getAction;
    Runnable clearAction;

    @FXML
    ListView<String> items;

    @FXML
    private void getEntity() {
        String selected = items.getSelectionModel().getSelectedItem();
        if (selected != null) {
            getAction.accept(selected);
        }
    }

    @FXML
    private void remove() {
        if (name.getText().length() >= 3) {
            removeAction.accept(name.getText());
            clearAction.run();
        }
    }

    @FXML
    private void save() {
        if (name.getText().length() >= 3) {
            saveAction.run();
        }
    }

    @FXML
    void clear() {
        name.clear();
        specialFeatures.clear();
    }

    @FXML
    private void newItem() {
        clearAction.run();
    }

    public void loadAll(Collection<? extends Named<String>> itemsList) {
        items.getItems().clear();
        items.getItems().addAll(itemsList.stream()
                .map(Named::getName).collect(Collectors.toList()));
    }

    void loadToInternalEditor(Named<String> named) {
        name.setText(named.getName());
    }
}
