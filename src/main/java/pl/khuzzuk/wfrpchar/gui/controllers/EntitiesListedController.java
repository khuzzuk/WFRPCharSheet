package pl.khuzzuk.wfrpchar.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import pl.khuzzuk.messaging.Bus;
import pl.khuzzuk.wfrpchar.entities.Featured;
import pl.khuzzuk.wfrpchar.entities.Named;
import pl.khuzzuk.wfrpchar.repo.Criteria;
import pl.khuzzuk.wfrpchar.repo.SaveItem;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

public abstract class EntitiesListedController<T extends Featured> implements Controller {
    @FXML
    TextField name;
    @Inject
    Bus bus;
    @FXML
    TextArea specialFeatures;
    @Inject
    @javax.inject.Named("messages")
    Properties messages;
    Runnable saveAction;
    String removeEntityTopic;
    private String getNamedEntityTopic;
    private String removeTopic;
    String getAllResponse;
    String saveTopic;
    Class<?> entityType;
    T item;
    Runnable clearAction;

    @FXML
    ListView<String> items;

    void initItems() {
        getNamedEntityTopic = entityType.getName() + ".get.named";
        getAllResponse = entityType.getName() + ".get.all";
        bus.setGuiReaction(getAllResponse, this::loadAll);
        bus.setGuiReaction(getNamedEntityTopic, this::loadItem);
        bus.send(messages.getProperty("database.getAll"), getAllResponse, entityType);
    }

    @FXML
    private void getEntity() {
        Optional.ofNullable(items.getSelectionModel().getSelectedItem())
                .ifPresent(selected -> bus.send(messages.getProperty("database.get.named"), getNamedEntityTopic,
                        Criteria.builder().type(entityType).name(selected).build()));
    }

    @FXML
    private void remove() {
        if (name.getText().length() >= 3) {
            bus.send(messages.getProperty("database.remove"), getAllResponse,
                    Criteria.builder().type(entityType).name(name.getText()).build());
            clearAction.run();
        }
    }

    @FXML
    private void save() {
        if (name.getText().length() >= 3) {
            saveAction();
        }
    }

    abstract void saveAction();

    void saveItem(String line) {
        bus.send(saveTopic, line);
    }

    void saveNewItem(T item) {
        bus.send(messages.getProperty("database.save"), getAllResponse, SaveItem.builder().type(entityType).entity(item).build());
    }

    void communicateDataChanges() {
        bus.send(messages.getProperty("database.change"));
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

    public synchronized void loadAll(Collection<? extends Named<String>> itemsList) {
        items.getItems().clear();
        items.getItems().addAll(itemsList.stream()
                .map(Named::getName).collect(Collectors.toList()));
    }

    void loadToInternalEditor(Named<String> named) {
        name.setText(named.getName());
    }

    void loadItem(T item) {
        clearAction.run();
        this.item = item;
    }

    void fillItemWithValues(T item) {
        item.setName(name.getText());
        item.setSpecialFeatures(specialFeatures.getText());
    }
}
