package pl.khuzzuk.wfrpchar.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import lombok.Getter;
import pl.khuzzuk.messaging.Bus;
import pl.khuzzuk.wfrpchar.entities.Featured;
import pl.khuzzuk.wfrpchar.entities.Named;
import pl.khuzzuk.wfrpchar.gui.GuiEntityConverter;
import pl.khuzzuk.wfrpchar.repo.Criteria;
import pl.khuzzuk.wfrpchar.repo.SaveItem;

import javax.inject.Inject;
import java.net.URL;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
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
    String getAllResponse;
    String saveTopic;
    Class<?> entityType;
    @Getter
    T item;
    Runnable clearAction;
    List<GuiEntityConverter> converters = new ArrayList<>();

    @FXML
    ListView<String> items;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeValidation();
        addConverters();
    }

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
            if (item == null || !name.getText().equals(item.getName())) {
                item = supplyNewItem();
            }
            fillItemWithValues();
            bus.send(messages.getProperty("database.save"), getAllResponse,
                    SaveItem.builder().type(entityType).entity(item).build());
        }
    }

    abstract void saveAction();

    void saveItem(String line) {
        bus.send(saveTopic, line);
    }

    void saveNewItem(T item) {
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
        name.setText(item.getName());
        specialFeatures.setText(item.getName());
    }

    /**
     * It will be called from {@link EntitiesListedController}.
     */
    void addConverters() {
        addConverter(name::getText, Featured::setName);
        addConverter(specialFeatures::getText, Featured::setSpecialFeatures);
    }

    @SuppressWarnings("unchecked")
    <U> void addConverter(Supplier<U> valueFromController, BiConsumer<T, U> itemSetter) {
        converters.add(new GuiEntityConverter(this::getItem, valueFromController, itemSetter));
    }

    @SuppressWarnings("unchecked")
    <U, V> void addConverter(Supplier<U> valueFromController, BiConsumer<T, V> itemSetter, Function<U, V> mapper) {
        converters.add(new GuiEntityConverter(this::getItem, valueFromController, itemSetter, mapper));
    }

    @SuppressWarnings("unchecked")
    <U, V> void addConverter(Supplier<U> valueFromController, BiConsumer<T, V> itemSetter, Function<U, V> mapper, Predicate<T> filter) {
        converters.add(new GuiEntityConverter(this::getItem, valueFromController, itemSetter, mapper, filter));
    }

    abstract T supplyNewItem();

    void fillItemWithValues() {
        converters.stream().filter(GuiEntityConverter::canConvert).forEach(GuiEntityConverter::fill);
    }
}
