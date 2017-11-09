package pl.khuzzuk.wfrpchar.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.entities.determinants.DeterminantsType;
import pl.khuzzuk.wfrpchar.entities.items.Accessibility;
import pl.khuzzuk.wfrpchar.entities.items.ArmorPattern;
import pl.khuzzuk.wfrpchar.entities.items.Commodity;
import pl.khuzzuk.wfrpchar.entities.items.Placement;
import pl.khuzzuk.wfrpchar.entities.items.types.ArmorType;
import pl.khuzzuk.wfrpchar.gui.ComboBoxHandler;
import pl.khuzzuk.wfrpchar.gui.Numeric;

import java.net.URL;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Component
public class ArmorTypesPaneController extends FightingEquipmentPaneController<ArmorType> {
    @FXML
    @Numeric
    TextField armBattleMod;
    @FXML
    @Numeric
    TextField armShootingMod;
    @FXML
    @Numeric
    TextField armOpponentParryMod;
    @FXML
    @Numeric
    TextField armParryMod;
    @FXML
    private ComboBox<String> armPattern;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        entityType = ArmorType.class;
        removeEntityTopic = messages.getProperty("armorTypes.remove");
        getAllResponse = messages.getProperty("armorTypes.result");
        saveTopic = messages.getProperty("database.saveEquipment");
        clearAction = this::clear;
        ComboBoxHandler.fillWithEnums(Accessibility.SET, accessibility);
        ComboBoxHandler.fill(EnumSet.allOf(ArmorPattern.class), armPattern);
        ComboBoxHandler.fillWithEnums(EnumSet.of(Placement.CORPUS, Placement.HEAD,
                Placement.BELT, Placement.HANDS,
                Placement.LEGS, Placement.FEET),
                placementBox);
        addMappingDeterminants(Pair.of(DeterminantsType.BATTLE, armBattleMod),
                Pair.of(DeterminantsType.SHOOTING, armShootingMod),
                Pair.of(DeterminantsType.OPPONENT_PARRY, armOpponentParryMod),
                Pair.of(DeterminantsType.PARRY, armParryMod));
        initItems();
    }

    @Override
    void addConverters() {
        super.addConverters();
        addConverter(weight::getText, Commodity::setWeight, NumberUtils::toFloat);
    }

    @Override
    public void loadItem(ArmorType armor) {
        super.loadItem(armor);
        armPattern.getSelectionModel().select(armor.getPattern().getName());
    }

    @Override
    ArmorType supplyNewItem() {
        return new ArmorType();
    }

    @FXML
    @Override
    void saveAction() {
        if (name.getText().length() < 3) {
            return;
        }
        List<String> fields = new LinkedList<>();
        fields.add(ArmorPattern.forName(armPattern.getSelectionModel().getSelectedItem()).name());
        saveItem(fields.stream().collect(Collectors.joining(";")));
    }

    @Override
    @FXML
    void clear() {
        super.clear();
        armPattern.getSelectionModel().clearSelection();
    }
}
