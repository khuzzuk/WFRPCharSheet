package pl.khuzzuk.wfrpchar.gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.entities.LangElement;
import pl.khuzzuk.wfrpchar.entities.determinants.DeterminantsType;
import pl.khuzzuk.wfrpchar.entities.items.*;
import pl.khuzzuk.wfrpchar.messaging.publishers.Publishers;
import pl.khuzzuk.wfrpchar.rules.Dices;

import javax.inject.Inject;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class WhiteWeaponTypePaneController implements Controller {
    @Inject
    @Publishers
    private GuiPublisher guiPublisher;
    private Map<DeterminantsType, TextField> whiteWeaponModifiers;
    private Map<DeterminantsType, TextField> bastWhiteWeaponMods;
    private Map<LangElement, TextField> whiteWeaponLangFields;

    @FXML
    ListView<String> weaponList;
    @FXML
    TextField nameWW;
    @FXML
    TextField typeNameWW;
    @FXML
    @FloatNumeric
    TextField weightWW;
    @FXML
    @Numeric
    TextField goldWW;
    @FXML
    @Numeric
    TextField silverWW;
    @FXML
    @Numeric
    TextField leadWW;
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
    TextArea specialFeaturesWW;
    @FXML
    ComboBox<String> accessibilityBoxWW;
    @FXML
    ComboBox<String> placementBoxWW;
    @FXML
    ComboBox<String> diceWW;
    @FXML
    Slider rollsWW;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeValidation();
        fillComboBoxesWithEnums();
    }

    private void fillComboBoxesWithEnums() {
        ComboBoxHandler.fill(Accessibility.SET, accessibilityBoxWW);
        ComboBoxHandler.fill(Dices.SET, diceWW);
        ComboBoxHandler.fill((Set) EnumSet.of(Placement.ONE_HAND, Placement.TWO_HANDS, Placement.BASTARD),
                placementBoxWW);
    }

    void initFieldsMap() {
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

    void loadWhiteWeaponToEditor(WhiteWeaponType weaponType) {
        nameWW.setText(weaponType.getName());
        typeNameWW.setText(weaponType.getTypeName());
        accessibilityBoxWW.getSelectionModel().select(weaponType.getAccessibility().getName());
        placementBoxWW.getSelectionModel().select(weaponType.getPlacement().getName());
        diceWW.getSelectionModel().select(weaponType.getDices().name());
        rollsWW.adjustValue(weaponType.getRolls());
        weightWW.setText("" + weaponType.getWeight());
        goldWW.setText("" + weaponType.getPrice().getGold());
        silverWW.setText("" + weaponType.getPrice().getSilver());
        leadWW.setText("" + weaponType.getPrice().getLead());
        strengthBasicWW.setText("" + weaponType.getStrength());
        weaponType.getDeterminants().forEach(d -> mapTypeToField(whiteWeaponModifiers, d));
        weaponType.getNames().forEach((lang, val) -> whiteWeaponLangFields.get(lang).setText(val));
        specialFeaturesWW.setText(weaponType.getSpecialFeature());
        if (weaponType instanceof BastardWeaponType) {
            BastardWeaponType bastard = (BastardWeaponType) weaponType;
            strengthBastardWW.setText("" + bastard.getOneHandedStrength());
            bastard.getOneHandedDeterminants().forEach(d -> mapTypeToField(bastWhiteWeaponMods, d));
        }
    }

    void saveWhiteWeaponType() {
        List<String> fields = new LinkedList<>();
        if (nameWW.getText().length() == 0) return;
        fields.add(nameWW.getText());
        fields.add(weightWW.getText());
        fields.add(goldWW.getText() + "|" +
                silverWW.getText() + "|" +
                leadWW.getText());
        fields.add(Accessibility.forName(accessibilityBoxWW.getSelectionModel().getSelectedItem()).name());
        fields.add(specialFeaturesWW.getText());
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
        guiPublisher.saveItem(fields.stream().collect(Collectors.joining(";")));
    }

    void loadWhiteWeapon(Collection<WhiteWeaponType> weapons) {
        weaponList.getItems().clear();
        weaponList.getItems()
                .addAll(weapons.stream().map(Item::getName).collect(Collectors.toList()));
    }

    @FXML
    private void removeWhiteWeaponType() {
        if (nameWW.getText().length() == 0) return;
        guiPublisher.removeWhiteWeapon(nameWW.getText());
    }

    @FXML
    private void selectWhiteWeapon() {
        String selected = weaponList.getSelectionModel().getSelectedItem();
        if (selected != null) guiPublisher.requestWhiteWeaponLoad(selected);
    }
}
