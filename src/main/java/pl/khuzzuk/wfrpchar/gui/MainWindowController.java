package pl.khuzzuk.wfrpchar.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.db.DAOManager;
import pl.khuzzuk.wfrpchar.db.annot.Manager;
import pl.khuzzuk.wfrpchar.entities.Labelled;
import pl.khuzzuk.wfrpchar.entities.LangElement;
import pl.khuzzuk.wfrpchar.entities.determinants.DeterminantsType;
import pl.khuzzuk.wfrpchar.entities.items.BastardWeaponType;
import pl.khuzzuk.wfrpchar.entities.items.Item;
import pl.khuzzuk.wfrpchar.entities.items.Placement;
import pl.khuzzuk.wfrpchar.entities.items.WhiteWeaponType;
import pl.khuzzuk.wfrpchar.messaging.publishers.Publishers;
import pl.khuzzuk.wfrpchar.rules.Dices;

import javax.inject.Inject;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

@Component
@MainWindowBean
@PropertySource("classpath:messages.properties")
public class MainWindowController implements Initializable {
    @Inject
    @Manager
    private DAOManager manager;
    @Inject
    @Publishers
    private GuiPublisher guiPublisher;
    private WhiteWeaponType whiteWeaponType;

    //WHITE WEAPON MENU
    @FXML
    private ListView<String> weaponList;
    @FXML
    private TextField nameWW;
    @FXML
    private TextField typeNameWW;
    @FXML
    @FloatNumeric
    private TextField weightWW;
    @FXML
    @Numeric
    private TextField goldWW;
    @FXML
    @Numeric
    private TextField silverWW;
    @FXML
    @Numeric
    private TextField leadWW;
    @FXML
    @Numeric
    private TextField strengthBasicWW;
    @FXML
    @Numeric
    private TextField strengthBastardWW;
    @FXML
    private Label strengthBastardLabelWW;
    @FXML
    @Numeric
    private TextField battleModWW;
    @FXML
    @Numeric
    private TextField initModWW;
    @FXML
    @Numeric
    private TextField opponentParryModWW;
    @FXML
    @Numeric
    private TextField parryModWW;
    @FXML
    @Numeric
    private TextField bastBattleModWW;
    @FXML
    @Numeric
    private TextField bastInitModWW;
    @FXML
    @Numeric
    private TextField bastOpParryModWW;
    @FXML
    @Numeric
    private TextField bastParryModWW;
    @FXML
    private TextField langMascWW;
    @FXML
    private TextField langFemWW;
    @FXML
    private TextField langNeutrWW;
    @FXML
    private TextField langAblativeWW;
    @FXML
    private TextArea specialFeaturesWW;
    @FXML
    private ComboBox<String> accessibilityBoxWW;
    @FXML
    private ComboBox<String> placementBoxWW;
    @FXML
    private ComboBox<String> diceWW;
    @FXML
    private Slider rollsWW;
    @FXML
    private Button newWhiteWeaponButton;
    @FXML
    private Button saveWhiteWeaponButton;
    private Map<DeterminantsType, TextField> whiteWeaponModifiers;
    private Map<DeterminantsType, TextField> bastWhiteWeaponMods;
    private Map<LangElement, TextField> whiteWeaponLangFields;

    @Override

    public void initialize(URL location, ResourceBundle resources) {
        initializeValidation();
        initFieldsMap();
        fillComboBoxesWithEnums();
        guiPublisher.requestWhiteWeapons();
    }

    void loadWhiteWeapon(List<WhiteWeaponType> weapons) {
        List<String> weaponsNames = weapons.stream().map(WhiteWeaponType::getName).collect(Collectors.toList());
        weaponList.getItems().clear();
        weaponList.getItems().addAll(weaponsNames);
    }

    void loadWhiteWeaponToEditor(WhiteWeaponType weaponType) {
        this.whiteWeaponType = weaponType;
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
        weaponType.getDeterminants().stream().forEach(d -> mapTypeToField(whiteWeaponModifiers, d));
        weaponType.getNames().forEach((lang, val) -> whiteWeaponLangFields.get(lang).setText(val));
        specialFeaturesWW.setText(weaponType.getSpecialFeature());
        if (weaponType instanceof BastardWeaponType) {
            BastardWeaponType bastard = (BastardWeaponType) weaponType;
            strengthBastardWW.setText("" + bastard.getOneHandedStrength());
            bastard.getOneHandedDeterminants().stream().forEach(d -> mapTypeToField(bastWhiteWeaponMods, d));
        }
    }

    @FXML
    void saveWhiteWeapon() {
        List<String> fields = new LinkedList<>();
        fields.add(nameWW.getText());
        fields.add(weightWW.getText());
        fields.add(goldWW.getText() + "|" + silverWW.getText() + "|" + leadWW.getText());
        fields.add(Item.Accessibility.forName(accessibilityBoxWW.getSelectionModel().getSelectedItem()).name());
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
        if (placementBoxWW.getSelectionModel().getSelectedIndex()==2) {
            fields.add(strengthBastardWW.getText());
            line = EnumSet.allOf(DeterminantsType.class).stream()
                    .filter(d -> bastWhiteWeaponMods.get(d) != null && bastWhiteWeaponMods.get(d).getText().length() > 0)
                    .map(d -> "" + bastWhiteWeaponMods.get(d).getText() + "," + d.name())
                    .collect(Collectors.joining("|"));
            fields.add(line);
        }
        guiPublisher.saveToDB(fields.stream().collect(Collectors.joining(";")));
    }

    private <T> void mapTypeToField(Map<T, TextField> fields, Labelled content) {
        TextField field = fields.get(content.getLabel());
        if (field != null) field.setText(content.getRepresentation());
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

    private void fillComboBoxesWithEnums() {
        accessibilityBoxWW.getItems().clear();
        accessibilityBoxWW.getItems().addAll(EnumSet.allOf(Item.Accessibility.class)
                .stream().map(Item.Accessibility::getName).collect(Collectors.toList()));
        placementBoxWW.getItems().clear();
        placementBoxWW.getItems().addAll(EnumSet.of(Placement.ONE_HAND, Placement.TWO_HANDS, Placement.BASTARD)
                .stream().map(Placement::getName).collect(Collectors.toList()));
        diceWW.getItems().clear();
        diceWW.getItems().addAll(EnumSet.allOf(Dices.class).stream().map(Dices::name).collect(Collectors.toList()));
    }

    private void initializeValidation() {
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field f : fields) {
            determineNumericAction(f);
        }
    }

    private void determineNumericAction(Field f) {
        try {
            if ((f.get(this) instanceof TextField)) {
                if (f.isAnnotationPresent(Numeric.class)) {
                    setIntegerListenerToTextField((TextField) f.get(this));
                } else if (f.isAnnotationPresent(FloatNumeric.class)) {
                    setFloatListenerToTextField((TextField) f.get(this));
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void setIntegerListenerToTextField(TextField field) {
        field.textProperty().addListener((obs, oldV, newV) -> field.setText(newV.replaceAll("[^\\d\\-]", "")));
    }

    private void setFloatListenerToTextField(TextField field) {
        field.textProperty().addListener((obs, oldV, newV) -> field.setText(newV.replaceAll("[^\\d\\-.,]", "")));
    }

    @FXML
    public void onResetAction() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Resetowanie wszystkich wpisów w bazie");
        alert.setHeaderText("Ta akcja spowoduje usunięcie wszystkich dotychczasowych wpisów.");
        alert.setContentText("Kontynuować?");
        Optional<ButtonType> chosenButton = alert.showAndWait();
        if (chosenButton.orElse(null) == ButtonType.OK) {
            guiPublisher.requestResetDB();
        }
    }

    @FXML
    public void selectWhiteWeapon() {
        String selected = weaponList.getSelectionModel().getSelectedItem();
        if (!selected.equals("")) guiPublisher.requestWhiteWeaponLoad(selected);
    }
}
