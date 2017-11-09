package pl.khuzzuk.wfrpchar.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.tuple.Pair;
import pl.khuzzuk.wfrpchar.entities.determinants.DeterminantsType;
import pl.khuzzuk.wfrpchar.entities.items.Placement;
import pl.khuzzuk.wfrpchar.entities.items.types.FightingEquipment;
import pl.khuzzuk.wfrpchar.gui.MappingUtil;
import pl.khuzzuk.wfrpchar.gui.Numeric;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public abstract class FightingEquipmentPaneController<T extends FightingEquipment> extends ItemTypePaneController<T> {
    @FXML
    ComboBox<Placement> placementBox;
    @FXML
    @Numeric
    TextField strength;
    Map<DeterminantsType, TextField> determinants;

    void addMappingDeterminants(Pair... determinantFields) {
        determinants = new HashMap<>();
        Arrays.stream(determinantFields).forEach(mapping -> determinants.put((DeterminantsType) mapping.getKey(), (TextField) mapping.getValue()));
    }

    @Override
    void addConverters() {
        super.addConverters();
        addConverter(strength::getText, FightingEquipment::setStrength, NumberUtils::toInt);
        addConverter(() -> getModifiers(determinants), FightingEquipment::setDeterminants);
        addConverter(placementBox::getValue, FightingEquipment::setPlacement);
    }

    @Override
    void loadItem(T item) {
        super.loadItem(item);
        strength.setText("" + item.getStrength());
        placementBox.getSelectionModel().select(item.getPlacement());
        item.getDeterminants().forEach(a -> MappingUtil.mapDeterminant(a, determinants));
    }

    @Override
    void clear() {
        super.clear();
        placementBox.getSelectionModel().clearSelection();
        strength.clear();
        determinants.values().forEach(TextField::clear);
    }
}
