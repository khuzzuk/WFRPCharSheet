package pl.khuzzuk.wfrpchar.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import pl.khuzzuk.wfrpchar.entities.Featured;
import pl.khuzzuk.wfrpchar.entities.items.Accessibility;
import pl.khuzzuk.wfrpchar.entities.items.Commodity;
import pl.khuzzuk.wfrpchar.gui.ComboBoxHandler;
import pl.khuzzuk.wfrpchar.gui.FloatNumeric;
import pl.khuzzuk.wfrpchar.gui.Numeric;

import java.net.URL;
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
        weight.clear();
        gold.clear();
        silver.clear();
        lead.clear();
        accessibility.getSelectionModel().clearSelection();
    }


    void loadToInternalEditor(Commodity commodity) {
        super.loadToInternalEditor(commodity);
        accessibility.getSelectionModel().select(commodity.getAccessibility());
        //TODO remove this if and solve it differently. usable controllers extends this class but don't need weight field.
        if (weight != null)
            weight.setText(commodity.getWeight() + "");
        gold.setText(commodity.getPrice().getGold() + "");
        silver.setText(commodity.getPrice().getSilver() + "");
        lead.setText(commodity.getPrice().getLead() + "");
        specialFeatures.setText(commodity.getSpecialFeatures());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeValidation();
        ComboBoxHandler.fillWithEnums(Accessibility.SET, accessibility);
    }

    String getPriceFromFields() {
        if (gold == null) {
            return "";
        }
        return gold.getText() + "|" + silver.getText() + "|" + lead.getText();
    }
}
