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
import java.util.ResourceBundle;

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
    private ComboBox<ArmorPattern> armPattern;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        entityType = ArmorType.class;
        ComboBoxHandler.fillWithEnums(Accessibility.SET, accessibility);
        ComboBoxHandler.fillWithEnums(EnumSet.allOf(ArmorPattern.class), armPattern);
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
        addConverter(armPattern::getValue, ArmorType::setPattern);
    }

    @Override
    public void loadItem(ArmorType armor) {
        super.loadItem(armor);
        armPattern.getSelectionModel().select(armor.getPattern());
    }

    @Override
    ArmorType supplyNewItem() {
        return new ArmorType();
    }

    @Override
    @FXML
    void clear() {
        super.clear();
        armPattern.getSelectionModel().clearSelection();
    }
}
