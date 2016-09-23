package pl.khuzzuk.wfrpchar.gui;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.khuzzuk.wfrpchar.entities.determinants.Determinant;
import pl.khuzzuk.wfrpchar.entities.determinants.DeterminantsType;
import pl.khuzzuk.wfrpchar.entities.determinants.MiscExtension;

import javax.inject.Inject;
import java.net.URL;
import java.util.EnumSet;
import java.util.ResourceBundle;

@NoArgsConstructor
public class DeterminantCreatorController implements Controller {
    @FXML
    private TextField description;
    @FXML
    @Numeric
    TextField strength;
    @FXML
    private ComboBox<String> type;
    @Setter(AccessLevel.PACKAGE)
    private Stage parent;
    private String showMsg;
    private String sendMsg;
    @Inject
    private GuiPublisher publisher;

    public DeterminantCreatorController(String showMsg, String sendMsg) {
        this.showMsg = showMsg;
        this.sendMsg = sendMsg;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeValidation();
        ComboBoxHandler.fill(EnumSet.allOf(DeterminantsType.class), type);
    }

    @FXML
    void send() {
        String chosenType = type.getSelectionModel().getSelectedItem();
        if (chosenType != null) {
            String determinant = chosenType + ": " + strength.getText() + ": " + description.getText();
            publisher.publish(determinant, sendMsg);
        }
        parent.hide();
    }

    void show() {
        clear();
        parent.show();
    }

    @FXML
    void hide() {
        parent.hide();
    }

    private void clear() {
        description.setText("");
        strength.setText("");
        type.getSelectionModel().clearSelection();
    }
}
