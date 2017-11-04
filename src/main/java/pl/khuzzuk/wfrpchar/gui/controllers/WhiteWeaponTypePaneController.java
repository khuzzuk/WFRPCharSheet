package pl.khuzzuk.wfrpchar.gui.controllers;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.entities.determinants.Determinant;
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
import java.util.stream.Collectors;

@Component
public class WhiteWeaponTypePaneController extends ItemTypePaneController<WhiteWeaponType> {
    private Map<DeterminantsType, TextField> whiteWeaponModifiers;
    private Map<DeterminantsType, TextField> bastWhiteWeaponMods;

    @FXML
    TextField typeNameWW;
    @FXML
    @Numeric
    TextField strengthBasicWW;

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
        getAllResponse = messages.getProperty("whiteWeapons.result");
        removeEntityTopic = messages.getProperty("whiteWeapons.remove");
        clearAction = this::clear;
        initFieldsMap();
        fillComboBoxesWithEnums();
        setBastardComponentsStatus(null, null, null);
        placementBox.getSelectionModel().selectedItemProperty().addListener(this::setBastardComponentsStatus);
        initItems();
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
        whiteWeaponModifiers = new HashMap<>();
        whiteWeaponModifiers.put(DeterminantsType.BATTLE, battleModWW);
        whiteWeaponModifiers.put(DeterminantsType.INITIATIVE, initModWW);
        whiteWeaponModifiers.put(DeterminantsType.PARRY, parryModWW);
        whiteWeaponModifiers.put(DeterminantsType.OPPONENT_PARRY, opponentParryModWW);
        bastWhiteWeaponMods = new HashMap<>();
        bastWhiteWeaponMods.put(DeterminantsType.BATTLE, bastBattleModWW);
        bastWhiteWeaponMods.put(DeterminantsType.INITIATIVE, bastInitModWW);
        bastWhiteWeaponMods.put(DeterminantsType.PARRY, bastParryModWW);
        bastWhiteWeaponMods.put(DeterminantsType.OPPONENT_PARRY, bastOpParryModWW);
    }

    @Override
    public void loadItem(WhiteWeaponType weaponType) {
        super.loadItem(weaponType);
        name.setText(weaponType.getName());
        typeNameWW.setText(weaponType.getTypeName());
        accessibility.getSelectionModel().select(weaponType.getAccessibility());
        placementBox.getSelectionModel().select(weaponType.getPlacement());
        diceWW.getSelectionModel().select(weaponType.getDices());
        rollsWW.adjustValue(weaponType.getRolls());
        weight.setText("" + weaponType.getWeight());
        gold.setText("" + weaponType.getPrice().getGold());
        silver.setText("" + weaponType.getPrice().getSilver());
        lead.setText("" + weaponType.getPrice().getLead());
        strengthBasicWW.setText("" + weaponType.getStrength());
        weaponType.getDeterminants().forEach(d -> MappingUtil.mapDeterminant(d, whiteWeaponModifiers));
        weaponType.getNames().forEach((lang, val) -> langFields.get(lang).setText(val));
        specialFeatures.setText(weaponType.getSpecialFeatures());
        if (weaponType instanceof BastardWeaponType) {
            BastardWeaponType bastard = (BastardWeaponType) weaponType;
            strengthBastardWW.setText("" + bastard.getOneHandedStrength());
            bastard.getOneHandedDeterminants().forEach(d -> MappingUtil.mapDeterminant(d, bastWhiteWeaponMods));
        }
    }

    @FXML
    @Override
    void saveAction() {
        if (name.getText().length() == 0 || placementBox.getSelectionModel().isEmpty()) return;
        if (item.getName().equals(name.getText())) {
            fillItemWithValues(item);
            communicateDataChanges();
        } else {
            WhiteWeaponType weaponType = WhiteWeaponType.getFromPlacement(placementBox.getValue());
            fillItemWithValues(weaponType);
            saveNewItem(weaponType);
        }
    }

    @Override
    void fillItemWithValues(WhiteWeaponType item) {
        super.fillItemWithValues(item);
        item.setWeight(Float.parseFloat(weight.getText()));
        item.setStrength(Integer.parseInt(strengthBasicWW.getText()));
        item.setDeterminants(whiteWeaponModifiers.entrySet().stream()
                .filter(entry -> StringUtils.isNoneBlank(entry.getValue().getText()))
                .map(entry -> {
                    Determinant determinant = entry.getKey().getDeterminant();
                    determinant.setBaseValue(Integer.parseInt(entry.getValue().getText()));
                    return determinant;
                }).collect(Collectors.toSet()));
        item.setTypeName(typeNameWW.getText());
        item.setDices(diceWW.getValue());
        item.setRolls((int) rollsWW.getValue());

        if (item instanceof BastardWeaponType) {
            BastardWeaponType bastardWeaponType = (BastardWeaponType) item;
            if (NumberUtils.isParsable(strengthBasicWW.getText())) {
                bastardWeaponType.setOneHandedStrength(Integer.parseInt(strengthBasicWW.getText()));
            }
            bastardWeaponType.setOneHandedDeterminants(bastWhiteWeaponMods.entrySet().stream()
            .filter(entry -> NumberUtils.isParsable(entry.getValue().getText()))
            .map(entry -> {
                Determinant determinant = entry.getKey().getDeterminant();
                determinant.setBaseValue(Integer.parseInt(entry.getValue().getText()));
                return determinant;
            }).collect(Collectors.toSet()));
        }
    }

    @Override
    void clear() {
        super.clear();
        strengthBasicWW.clear();
        whiteWeaponModifiers.values().forEach(TextField::clear);
        typeNameWW.clear();
        diceWW.getSelectionModel().clearSelection();
        rollsWW.adjustValue(0);
        bastWhiteWeaponMods.values().forEach(TextField::clear);
    }
}
