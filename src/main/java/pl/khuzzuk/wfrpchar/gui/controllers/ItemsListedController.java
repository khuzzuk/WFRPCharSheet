package pl.khuzzuk.wfrpchar.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import pl.khuzzuk.wfrpchar.entities.Named;
import pl.khuzzuk.wfrpchar.entities.items.Accessibility;
import pl.khuzzuk.wfrpchar.entities.items.Commodity;
import pl.khuzzuk.wfrpchar.gui.ComboBoxHandler;
import pl.khuzzuk.wfrpchar.gui.FloatNumeric;
import pl.khuzzuk.wfrpchar.gui.GuiPublisher;
import pl.khuzzuk.wfrpchar.gui.Numeric;

import javax.inject.Inject;
import java.net.URL;
import java.util.Collection;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public abstract class ItemsListedController implements Controller {
    @FXML
    TextField name;
    @FXML
    ComboBox<String> accessibility;
    @FXML
    @Numeric
    TextField gold; //TODO remove iGold, goldWW etc. from Types Controllers
    @FXML
    @Numeric
    TextField silver;
    @FXML
    @Numeric
    TextField lead;
    @FXML
    @FloatNumeric
    TextField weight;
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
            saveAction.run();
        }
    }

    @FXML
    void clear() {
        name.clear();
        specialFeatures.clear();
        weight.clear();
        gold.clear();
        silver.clear();
        lead.clear();
        accessibility.getSelectionModel().clearSelection();
    }

    public void loadAll(Collection<? extends Named<String>> itemsList) {
        items.getItems().clear();
        items.getItems().addAll(itemsList.stream()
                .map(Named::getName).collect(Collectors.toList()));
    }

    void loadToInternalEditor(Commodity commodity) {
        name.setText(commodity.getName());
        accessibility.getSelectionModel().select(commodity.getAccessibility().getName());
        weight.setText(commodity.getWeight() + "");
        gold.setText(commodity.getPrice().getGold() + "");
        silver.setText(commodity.getPrice().getSilver() + "");
        lead.setText(commodity.getPrice().getLead() + "");
        specialFeatures.setText(commodity.getSpecialFeatures());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeValidation();
        ComboBoxHandler.fill(Accessibility.SET, accessibility);
    }

    String getPriceFromFields() {
        if (gold == null) {
            return "";
        }
        return gold.getText() + "|" + silver.getText() + "|" + lead.getText();
    }
}
