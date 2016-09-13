package pl.khuzzuk.wfrpchar.gui;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import pl.khuzzuk.wfrpchar.entities.Named;
import pl.khuzzuk.wfrpchar.messaging.subscribers.Reactor;

import javax.inject.Inject;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public abstract class ItemsListedController implements Controller {
    @FXML
    TextField name;
    @Inject
    GuiPublisher guiPublisher;

    Reactor saveAction;
    Consumer<String> removeAction;
    Consumer<String> getAction;

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
        }
    }

    @FXML
    private void save() {
        if (name.getText().length() >= 3) {
            saveAction.resolve();
        }
    }

    void loadAll(Collection<Named<String>> itemsList) {
        items.getItems().clear();
        items.getItems().addAll(itemsList.stream()
                .map(Named::getName).collect(Collectors.toList()));
    }
}
