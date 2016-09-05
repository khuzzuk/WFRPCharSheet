package pl.khuzzuk.wfrpchar.gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.entities.LoadingTimes;
import pl.khuzzuk.wfrpchar.entities.items.*;
import pl.khuzzuk.wfrpchar.messaging.publishers.Publishers;
import pl.khuzzuk.wfrpchar.rules.Dices;

import javax.inject.Inject;
import java.net.URL;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Component
@MainWindowBean
@PropertySource("classpath:messages.properties")
public class MainWindowController implements Controller {
    public TitledPane armorTypesPane;
    @Inject
    @Getter(AccessLevel.PACKAGE)
    @FXML
    private ArmorTypesPaneController armorTypesPaneController;
    @Inject
    @Publishers
    private GuiPublisher guiPublisher;
    @Inject
    @MainWindowBean
    private ItemsLoaderToGui loader;
    //WHITE WEAPON MENU
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

    //RANGED WEAPONS
    @FXML
    ListView<String> rangedWeaponList;
    @FXML
    TextField rwName;
    @FXML
    TextField rwTypeName;
    @FXML
    @Numeric
    TextField rwGold;
    @FXML
    @Numeric
    TextField rwSilver;
    @FXML
    @Numeric
    TextField rwLead;
    @FXML
    ComboBox<String> rwAccessibility;
    @FXML
    TextArea rwSpecialFeatures;
    @FXML
    @FloatNumeric
    TextField rwWeight;
    @FXML
    @Numeric
    TextField rwStrength;
    @FXML
    @Numeric
    TextField rwMinRange;
    @FXML
    @Numeric
    TextField rwMedRange;
    @FXML
    @Numeric
    TextField rwMaxRange;
    @FXML
    ComboBox<String> rwLoadTime;
    @FXML
    TextField rwLangMasc;
    @FXML
    TextField rwLangFem;
    @FXML
    TextField rwLangNeutr;
    @FXML
    TextField rwLangAblative;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeValidation();
        fillComboBoxesWithEnums();
        guiPublisher.initLoader();
        guiPublisher.requestWhiteWeapons();
        guiPublisher.requestRangedWeapons();
    }

    void loadWhiteWeapon(Collection<WhiteWeaponType> weapons) {
        weaponList.getItems().clear();
        weaponList.getItems()
                .addAll(weapons.stream().map(Item::getName).collect(Collectors.toList()));
    }

    void loadRangedWeapon(Collection<RangedWeaponType> weapons) {
        rangedWeaponList.getItems().clear();
        rangedWeaponList.getItems()
                .addAll(weapons.stream().map(Item::getName).collect(Collectors.toList()));
    }

    @FXML
    void saveWhiteWeapon() {
        loader.saveWhiteWeaponType();
    }

    @FXML
    private void saveRangedWeapon() {
        loader.saveRangedWeaponType();
    }

    @FXML
    private void removeWhiteWeaponType() {
        if (nameWW.getText().length() == 0) return;
        guiPublisher.removeWhiteWeapon(nameWW.getText());
    }

    @FXML
    private void removeRangedWeapon() {
        if (rwName.getText().length() == 0) return;
        guiPublisher.removeRangedWeapon(rwName.getText());
    }

    private void fillComboBoxesWithEnums() {
        Set<Accessibility> accessibilities = EnumSet.allOf(Accessibility.class);
        ComboBoxHandler.fill(accessibilities, accessibilityBoxWW);
        ComboBoxHandler.fill(accessibilities, rwAccessibility);
        ComboBoxHandler.fill(EnumSet.allOf(Dices.class), diceWW);
        ComboBoxHandler.fill(EnumSet.allOf(LoadingTimes.class), rwLoadTime);
        ComboBoxHandler.fill((Set) EnumSet.of(Placement.ONE_HAND, Placement.TWO_HANDS, Placement.BASTARD),
                placementBoxWW);
    }

    @FXML
    private void onResetAction() {
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
    private void selectWhiteWeapon() {
        String selected = weaponList.getSelectionModel().getSelectedItem();
        if (selected != null) guiPublisher.requestWhiteWeaponLoad(selected);
    }

    @FXML
    private void selectRangedWeapon() {
        EntitiesAdapter.sendQuery(rangedWeaponList, guiPublisher::requestRangedWeaponLoad);
    }

    private static class EntitiesAdapter {
        private static void sendQuery(ListView<String> list, Consumer<String> action) {
            String selected = list.getSelectionModel().getSelectedItem();
            if (selected != null) {
                action.accept(selected);
            }
        }
    }
}
