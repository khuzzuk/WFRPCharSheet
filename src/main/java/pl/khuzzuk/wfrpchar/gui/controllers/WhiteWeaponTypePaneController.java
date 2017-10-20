package pl.khuzzuk.wfrpchar.gui.controllers;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.entities.LangElement;
import pl.khuzzuk.wfrpchar.entities.determinants.DeterminantsType;
import pl.khuzzuk.wfrpchar.entities.items.Accessibility;
import pl.khuzzuk.wfrpchar.entities.items.Placement;
import pl.khuzzuk.wfrpchar.entities.items.types.BastardWeaponType;
import pl.khuzzuk.wfrpchar.entities.items.types.WhiteWeaponType;
import pl.khuzzuk.wfrpchar.gui.ComboBoxHandler;
import pl.khuzzuk.wfrpchar.gui.MappingUtil;
import pl.khuzzuk.wfrpchar.gui.Numeric;
import pl.khuzzuk.wfrpchar.rules.Dices;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class WhiteWeaponTypePaneController extends ItemsListedController {
    private Map<DeterminantsType, TextField> whiteWeaponModifiers;
    private Map<DeterminantsType, TextField> bastWhiteWeaponMods;
    private Map<LangElement, TextField> whiteWeaponLangFields;

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
    TextField langMascWW;
    @FXML
    TextField langFemWW;
    @FXML
    TextField langNeutrWW;
    @FXML
    TextField langAblativeWW;
    @FXML
    ComboBox<String> placementBoxWW;
    @FXML
    ComboBox<String> diceWW;
    @FXML
    Slider rollsWW;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeValidation();
        entityType = WhiteWeaponType.class;
        getAllResponse = messages.getProperty("whiteWeapons.result");
        getEntityTopic = messages.getProperty("whiteWeapons.query.specific");
        removeEntityTopic = messages.getProperty("whiteWeapons.remove");
        saveTopic = messages.getProperty("database.saveEquipment");
        saveAction = this::saveWhiteWeaponType;
        clearAction = this::clear;
        initFieldsMap();
        fillComboBoxesWithEnums();
        setBastardComponentsStatus(null, null, null);
        placementBoxWW.getSelectionModel().selectedItemProperty().addListener(this::setBastardComponentsStatus);
        initItems();
    }

    private void setBastardComponentsStatus(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        boolean isVisible = newValue != null && newValue.equals("Półtoraręczny");
        bastWhiteWeaponMods.values().forEach(t -> t.setVisible(isVisible));
        strengthBastardLabelWW.setVisible(isVisible);
        strengthBastardWW.setVisible(isVisible);
    }

    private void fillComboBoxesWithEnums() {
        ComboBoxHandler.fill(Accessibility.SET, accessibility);
        ComboBoxHandler.fill(Dices.SET, diceWW);
        ComboBoxHandler.fill(EnumSet.of(Placement.ONE_HAND, Placement.TWO_HANDS, Placement.BASTARD),
                placementBoxWW);
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
        whiteWeaponLangFields = new HashMap<>();
        whiteWeaponLangFields.put(LangElement.ADJECTIVE_MASC_SING, langMascWW);
        whiteWeaponLangFields.put(LangElement.ADJECTIVE_FEM_SING, langFemWW);
        whiteWeaponLangFields.put(LangElement.ADJECTIVE_NEUTR_SING, langNeutrWW);
        whiteWeaponLangFields.put(LangElement.ABLATIVE, langAblativeWW);
    }

    public void loadWhiteWeaponToEditor(WhiteWeaponType weaponType) {
        clear();
        name.setText(weaponType.getName());
        typeNameWW.setText(weaponType.getTypeName());
        accessibility.getSelectionModel().select(weaponType.getAccessibility().getName());
        placementBoxWW.getSelectionModel().select(weaponType.getPlacement().getName());
        diceWW.getSelectionModel().select(weaponType.getDices().name());
        rollsWW.adjustValue(weaponType.getRolls());
        weight.setText("" + weaponType.getWeight());
        gold.setText("" + weaponType.getPrice().getGold());
        silver.setText("" + weaponType.getPrice().getSilver());
        lead.setText("" + weaponType.getPrice().getLead());
        strengthBasicWW.setText("" + weaponType.getStrength());
        weaponType.getDeterminants().forEach(d -> MappingUtil.mapDeterminant(d, whiteWeaponModifiers));
        weaponType.getNames().forEach((lang, val) -> whiteWeaponLangFields.get(lang).setText(val));
        specialFeatures.setText(weaponType.getSpecialFeatures());
        if (weaponType instanceof BastardWeaponType) {
            BastardWeaponType bastard = (BastardWeaponType) weaponType;
            strengthBastardWW.setText("" + bastard.getOneHandedStrength());
            bastard.getOneHandedDeterminants().forEach(d -> MappingUtil.mapDeterminant(d, bastWhiteWeaponMods));
        }
    }

    @FXML
    private void saveWhiteWeaponType() {
        List<String> fields = new LinkedList<>();
        if (name.getText().length() == 0) return;
        fields.add(name.getText());
        fields.add(weight.getText());
        fields.add(getPriceFromFields());
        fields.add(Accessibility.forName(accessibility.getSelectionModel().getSelectedItem()).name());
        fields.add(specialFeatures.getText());
        fields.add(strengthBasicWW.getText());
        fields.add("WEAPON");
        fields.add(Placement.forName(placementBoxWW.getSelectionModel().getSelectedItem()).name());
        fields.add(langMascWW.getText() + "|" + langFemWW.getText() + "|" +
                langNeutrWW.getText() + "|" + langAblativeWW.getText());
        String line = EnumSet.allOf(DeterminantsType.class).stream()
                .filter(d -> whiteWeaponModifiers.get(d) != null && whiteWeaponModifiers.get(d).getText().length() > 0)
                .map(d -> "" + whiteWeaponModifiers.get(d).getText() + "," + d.name()).collect(Collectors.joining("|"));
        fields.add(line);
        fields.add(typeNameWW.getText());
        fields.add(diceWW.getSelectionModel().getSelectedItem());
        fields.add(Integer.toString((int) rollsWW.getValue()));
        if (placementBoxWW.getSelectionModel().getSelectedIndex() == 2) {
            fields.add(strengthBastardWW.getText());
            line = EnumSet.allOf(DeterminantsType.class).stream()
                    .filter(d -> bastWhiteWeaponMods.get(d) != null && bastWhiteWeaponMods.get(d).getText().length() > 0)
                    .map(d -> "" + bastWhiteWeaponMods.get(d).getText() + "," + d.name())
                    .collect(Collectors.joining("|"));
            fields.add(line);
        }
        saveItem(fields.stream().collect(Collectors.joining(";")));
    }

    @Override
    void clear() {
        super.clear();
        strengthBasicWW.clear();
        placementBoxWW.getSelectionModel().clearSelection();
        whiteWeaponModifiers.values().forEach(TextField::clear);
        langMascWW.clear();
        langFemWW.clear();
        langNeutrWW.clear();
        langAblativeWW.clear();
        typeNameWW.clear();
        diceWW.getSelectionModel().clearSelection();
        rollsWW.adjustValue(0);
        bastWhiteWeaponMods.values().forEach(TextField::clear);
    }
}
