package pl.khuzzuk.wfrpchar.gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.entities.items.Accessibility;
import pl.khuzzuk.wfrpchar.entities.items.Item;
import pl.khuzzuk.wfrpchar.entities.items.Placement;
import pl.khuzzuk.wfrpchar.entities.items.WhiteWeaponType;
import pl.khuzzuk.wfrpchar.messaging.publishers.Publishers;
import pl.khuzzuk.wfrpchar.rules.Dices;

import javax.inject.Inject;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

@Component
@MainWindowBean
@PropertySource("classpath:messages.properties")
public class MainWindowController implements Controller {
    @FXML
    private TitledPane armorTypesPane;
    @FXML
    private TitledPane rangedWeaponTypePane;
    @Inject
    @FXML
    private ArmorTypesPaneController armorTypesPaneController;
    @Inject
    private RangedWeaponTypePaneController rangedWeaponTypePaneController;
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

    @FXML
    void saveWhiteWeapon() {
        loader.saveWhiteWeaponType();
    }

    @FXML
    private void removeWhiteWeaponType() {
        if (nameWW.getText().length() == 0) return;
        guiPublisher.removeWhiteWeapon(nameWW.getText());
    }


    private void fillComboBoxesWithEnums() {
        Set<Accessibility> accessibilities = EnumSet.allOf(Accessibility.class);
        ComboBoxHandler.fill(Accessibility.SET, accessibilityBoxWW);
        ComboBoxHandler.fill(Dices.SET, diceWW);
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
}
