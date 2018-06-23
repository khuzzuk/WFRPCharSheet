package pl.khuzzuk.wfrpchar.gui.controllers;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.entities.determinants.DeterminantsType;
import pl.khuzzuk.wfrpchar.entities.items.Placement;
import pl.khuzzuk.wfrpchar.entities.items.types.BastardWeaponType;
import pl.khuzzuk.wfrpchar.entities.items.types.WhiteWeaponType;
import pl.khuzzuk.wfrpchar.gui.ComboBoxHandler;
import pl.khuzzuk.wfrpchar.gui.MappingUtil;
import pl.khuzzuk.wfrpchar.gui.Numeric;
import pl.khuzzuk.wfrpchar.rules.Dices;

import java.net.URL;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Predicate;

@Component
public class WhiteWeaponTypePaneController extends FightingEquipmentPaneController<WhiteWeaponType> {
    private Map<DeterminantsType, TextField> bastWhiteWeaponMods;

    @FXML
    TextField typeNameWW;
    @FXML
    @Numeric
    TextField strengthBastardWW;
    @FXML
    Label strengthBastardLabelWW;
    @FXML
    @Numeric
    TextField battleModWW;
    @FXML
    @Numeric
    TextField initModWW;
    @FXML
    @Numeric
    TextField opponentParryModWW;
    @FXML
    @Numeric
    TextField parryModWW;
    @FXML
    @Numeric
    TextField bastBattleModWW;
    @FXML
    @Numeric
    TextField bastInitModWW;
    @FXML
    @Numeric
    TextField bastOpParryModWW;
    @FXML
    @Numeric
    TextField bastParryModWW;
    @FXML
    ComboBox<Dices> diceWW;
    @FXML
    Slider rollsWW;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        entityType = WhiteWeaponType.class;
        initFieldsMap();
        fillComboBoxesWithEnums();
        setBastardComponentsStatus(null, null, null);
        placementBox.getSelectionModel().selectedItemProperty().addListener(this::setBastardComponentsStatus);
        addMappingDeterminants(Pair.of(DeterminantsType.BATTLE, battleModWW),
                Pair.of(DeterminantsType.INITIATIVE, initModWW),
                Pair.of(DeterminantsType.PARRY, parryModWW),
                Pair.of(DeterminantsType.OPPONENT_PARRY, opponentParryModWW));
        initItems();
    }

    void addConverters() {
        super.addConverters();
        addConverter(typeNameWW::getText, WhiteWeaponType::setTypeName);
        addConverter(diceWW::getValue, WhiteWeaponType::setDices);
        addConverter(rollsWW::getValue, WhiteWeaponType::setRolls, Double::intValue);

        Predicate<WhiteWeaponType> bastardTest = weapon -> weapon instanceof BastardWeaponType;
        addConverter(strengthBastardWW::getText,
                (weapon, num) -> ((BastardWeaponType) weapon).setOneHandedStrength(num),
                NumberUtils::toInt, bastardTest);
        addConverter(() -> getModifiers(bastWhiteWeaponMods),
                (weapon, determinants) -> ((BastardWeaponType) weapon).setOneHandedDeterminants(determinants),
                determinants -> determinants, bastardTest);

    }

    private void setBastardComponentsStatus(ObservableValue<? extends Placement> observable, Placement oldValue, Placement newValue) {
        boolean isVisible = newValue != null && newValue == Placement.BASTARD;
        bastWhiteWeaponMods.values().forEach(t -> t.setVisible(isVisible));
        strengthBastardLabelWW.setVisible(isVisible);
        strengthBastardWW.setVisible(isVisible);
    }

    private void fillComboBoxesWithEnums() {
        ComboBoxHandler.fillWithEnums(Dices.SET, diceWW);
        ComboBoxHandler.fillWithEnums(EnumSet.of(Placement.ONE_HAND, Placement.TWO_HANDS, Placement.BASTARD),
                placementBox);
    }

    private void initFieldsMap() {
        bastWhiteWeaponMods = new HashMap<>();
        bastWhiteWeaponMods.put(DeterminantsType.BATTLE, bastBattleModWW);
        bastWhiteWeaponMods.put(DeterminantsType.INITIATIVE, bastInitModWW);
        bastWhiteWeaponMods.put(DeterminantsType.PARRY, bastParryModWW);
        bastWhiteWeaponMods.put(DeterminantsType.OPPONENT_PARRY, bastOpParryModWW);
    }

    @Override
    public void loadItem(WhiteWeaponType weaponType) {
        super.loadItem(weaponType);
        typeNameWW.setText(weaponType.getTypeName());
        diceWW.getSelectionModel().select(weaponType.getDices());
        rollsWW.adjustValue(weaponType.getRolls());
        if (weaponType instanceof BastardWeaponType) {
            BastardWeaponType bastard = (BastardWeaponType) weaponType;
            strengthBastardWW.setText("" + bastard.getOneHandedStrength());
            bastard.getOneHandedDeterminants().forEach(d -> MappingUtil.mapDeterminant(d, bastWhiteWeaponMods));
        }
    }

    @Override
    WhiteWeaponType supplyNewItem() {
        if (placementBox.getValue() == null) {
            placementBox.getSelectionModel().select(Placement.ONE_HAND);
        }
        return WhiteWeaponType.getFromPlacement(placementBox.getValue());
    }

    @Override
    void clear() {
        super.clear();
        typeNameWW.clear();
        diceWW.getSelectionModel().clearSelection();
        rollsWW.adjustValue(0);
        bastWhiteWeaponMods.values().forEach(TextField::clear);
    }
}
