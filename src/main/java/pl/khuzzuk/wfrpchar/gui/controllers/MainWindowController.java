package pl.khuzzuk.wfrpchar.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TitledPane;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.gui.GuiPublisher;
import pl.khuzzuk.wfrpchar.gui.MainWindowBean;
import pl.khuzzuk.wfrpchar.messaging.publishers.Publishers;

import javax.inject.Inject;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

@Component
@MainWindowBean
@PropertySource("classpath:messages.properties")
public class MainWindowController implements Controller {
    @FXML
    private TitledPane ammunitionTypesPane;
    @FXML
    private TitledPane armorPane;
    @FXML
    private TitledPane rangedWeaponsPane;
    @FXML
    private TitledPane handWeaponsPane;
    @FXML
    private TitledPane resourceTypesPane;
    @FXML
    private TitledPane itemTypesPane;
    @FXML
    private TitledPane whiteWeaponTypePane;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeValidation();
        guiPublisher.requestWhiteWeapons();
        guiPublisher.requestRangedWeaponsTypes();
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
}
