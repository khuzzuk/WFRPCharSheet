package pl.khuzzuk.wfrpchar.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.entities.items.types.WhiteWeaponType;
import pl.khuzzuk.wfrpchar.entities.items.usable.AbstractHandWeapon;
import pl.khuzzuk.wfrpchar.gui.ComboBoxHandler;
import pl.khuzzuk.wfrpchar.gui.Numeric;
import pl.khuzzuk.wfrpchar.rules.Dices;

import java.net.URL;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Component
@PropertySource("classpath:messages.properties")
public class HandWeaponsPaneController extends AbstractWeaponController {
    @Numeric
    @FXML
    TextField rolls;
    @FXML
    private ComboBox<String> dices;
    private AbstractHandWeapon handWeapon;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        getAction = guiPublisher::requestHandWeapon;
        ComboBoxHandler.fill(EnumSet.allOf(Dices.class), dices);
        guiPublisher.requestHandWeapons();
    }

    @FXML
    private void chooseBaseType() {
        guiPublisher.publish(messages.getProperty("weapons.hand.baseType.getAllTypes"));
    }

    @FXML
    void chooseDeterminant() {
        guiPublisher.publish(messages.getProperty("determinants.creator.show.hw"));
    }

    @FXML
    private void saveWeapon() {
        guiPublisher.publish(weaponToLine(), messages.getProperty("weapons.hand.save"));
    }

    private String weaponToLine() {
        if (name.getText().length() >= 3 && baseType.length() >= 3) {
            List<String> fields = new ArrayList<>();
            addWeaponTypeFields(fields);
            fields.add(dices.getSelectionModel().getSelectedItem());
            fields.add(rolls.getText());
            fields.add("");
            return fields.stream().collect(Collectors.joining(";"));
        }
        throw new IllegalStateException("Weapon not started Properly");
    }

    public void loadToEditor(AbstractHandWeapon<? extends WhiteWeaponType> weapon) {
        handWeapon = weapon;
        loadToInternalEditor(weapon);
    }

    @FXML
    private void removeWeapon() {
        if (name.getText().length()>=3) {
            guiPublisher.removeHandWeapon(name.getText());
        }
    }

    @Override
    @FXML
    void clearEditor() {
        super.clearEditor();
        rolls.setText("");
        dices.getSelectionModel().clearSelection();
    }
}
