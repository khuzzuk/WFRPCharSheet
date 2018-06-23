package pl.khuzzuk.wfrpchar.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import pl.khuzzuk.wfrpchar.entities.Featured;
import pl.khuzzuk.wfrpchar.entities.items.Accessibility;
import pl.khuzzuk.wfrpchar.gui.ComboBoxHandler;
import pl.khuzzuk.wfrpchar.gui.FloatNumeric;
import pl.khuzzuk.wfrpchar.gui.Numeric;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public abstract class ItemsListedController<T extends Featured> extends EntitiesListedController<T> {
    @FXML
    ComboBox<Accessibility> accessibility;
    @FXML
    @Numeric
    TextField gold;
    @FXML
    @Numeric
    TextField silver;
    @FXML
    @Numeric
    TextField lead;
    @FXML
    @FloatNumeric
    TextField weight;

    @FXML
    void clear() {
        super.clear();
        Optional.ofNullable(weight).ifPresent(TextInputControl::clear);
        Optional.ofNullable(gold).ifPresent(TextInputControl::clear);
        Optional.ofNullable(silver).ifPresent(TextInputControl::clear);
        Optional.ofNullable(lead).ifPresent(TextInputControl::clear);
        Optional.ofNullable(accessibility).map(ComboBox::getSelectionModel).ifPresent(SelectionModel::clearSelection);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        ComboBoxHandler.fillWithEnums(Accessibility.SET, accessibility);
    }

    String getPriceFromFields() {
        if (gold == null) {
            return "";
        }
        return gold.getText() + "|" + silver.getText() + "|" + lead.getText();
    }
}
