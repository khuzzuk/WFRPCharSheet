package pl.khuzzuk.wfrpchar.gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.db.DAOManager;
import pl.khuzzuk.wfrpchar.db.annot.Manager;

import javax.inject.Inject;
import java.util.Optional;

@Component
@NoArgsConstructor
public class MainWindowController {
    @Inject
    @Manager
    private DAOManager manager;

    //GENERAL MENU
    @FXML
    private MenuItem menuItemClose;
    @FXML
    private MenuItem menuItemReset;
    @FXML
    private ListView<String> weaponList;

    //WHITE WEAPON MENU
    @FXML
    private TextField nameWW;
    @FXML
    private TextField typeNameWW;
    @FXML
    private TextField weightWW;
    @FXML
    private TextField goldWW;
    @FXML
    private TextField silverWW;
    @FXML
    private TextField leadWW;
    @FXML
    private TextField strengthBasicWW;
    @FXML
    private TextField strengthBastardWW;
    @FXML
    private Label strengthBastardLabelWW;
    @FXML
    private TextField battleModWW;
    @FXML
    private TextField initModWW;
    @FXML
    private TextField opponentParryModWW;
    @FXML
    private TextField parryModWW;
    @FXML
    private TextField bastBattleModWW;
    @FXML
    private TextField bastInitModWW;
    @FXML
    private TextField bastOpParryModWW;
    @FXML
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

    public void onResetAction() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Resetowanie wszystkich wpisów w bazie");
        alert.setHeaderText("Ta akcja spowoduje usunięcie wszystkich dotychczasowych wpisów.");
        alert.setContentText("Kontynuować?");
        Optional<ButtonType> chosenButton = alert.showAndWait();
        if (chosenButton.orElse(null) == ButtonType.OK) {
            manager.resetDB();
        }
    }

    public void loadWeapon() {

    }
}
