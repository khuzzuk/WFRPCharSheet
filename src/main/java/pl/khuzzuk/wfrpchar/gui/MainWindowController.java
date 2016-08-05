package pl.khuzzuk.wfrpchar.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import lombok.Setter;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.db.*;
import pl.khuzzuk.wfrpchar.db.annot.Manager;
import pl.khuzzuk.wfrpchar.entities.items.WeaponType;
import pl.khuzzuk.wfrpchar.messaging.*;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Component
@MainWindowBean
public class MainWindowController implements Initializable {
    @Inject
    @Manager
    private DAOManager manager;
    @Inject
    @Publishers
    @MainWindowBean
    private Publisher<Message> publisher;
    @Inject
    @MainWindowBean
    @Subscribers
    private ContentSubscriber<List<WeaponType>> weaponTypeSubscriber;
    @Setter
    private MainWindow mainWindow;

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

    @PostConstruct
    private void setReactors() {
        weaponTypeSubscriber.setConsumer(this::loadWeapon);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeValidation();
        Message message = new CommunicateMessage();
        message.setType(DAOConfig.WEAPONS_QUERRY);
        publisher.publish(message);
    }

    public void loadWeapon(List<WeaponType> weapons) {
        List<String> weaponsNames = weapons.stream().map(WeaponType::getName).collect(Collectors.toList());
        weaponList.getItems().clear();
        weaponList.getItems().addAll(weaponsNames);
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
    public void onResetAction() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Resetowanie wszystkich wpisów w bazie");
        alert.setHeaderText("Ta akcja spowoduje usunięcie wszystkich dotychczasowych wpisów.");
        alert.setContentText("Kontynuować?");
        Optional<ButtonType> chosenButton = alert.showAndWait();
        /*if (chosenButton.orElse(null) == ButtonType.OK) {
            manager.resetDB(dao);
        }*/
    }
}
