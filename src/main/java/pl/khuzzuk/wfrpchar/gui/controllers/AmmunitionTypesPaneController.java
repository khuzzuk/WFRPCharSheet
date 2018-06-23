package pl.khuzzuk.wfrpchar.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.entities.items.types.AmmunitionType;
import pl.khuzzuk.wfrpchar.gui.Numeric;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Component
public class AmmunitionTypesPaneController extends CommodityPaneController<AmmunitionType> {
    @Numeric
    @FXML
    TextField strength;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        entityType = AmmunitionType.class;
        initItems();
    }

    @Override
    public void loadItem(AmmunitionType ammunitionType) {
        super.loadItem(ammunitionType);
        strength.setText(ammunitionType.getStrength() + "");
    }

    @Override
    void addConverters() {
        super.addConverters();
        addConverter(strength::getText, AmmunitionType::setStrength, NumberUtils::toInt);
    }

    @Override
    AmmunitionType supplyNewItem() {
        return new AmmunitionType();
    }

    @Override
    void clear() {
        super.clear();
        strength.clear();
    }
}
