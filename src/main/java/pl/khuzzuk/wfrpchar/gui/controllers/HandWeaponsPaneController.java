package pl.khuzzuk.wfrpchar.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.entities.CsvBuilder;
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

@Component
@PropertySource("classpath:messages.properties")
public class HandWeaponsPaneController extends AbstractWeaponController {
    @Numeric
    @FXML
    TextField rolls;
    @FXML
    private ComboBox<String> dices;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        getAction = guiPublisher::requestHandWeapon;
        clearAction = this::clear;
        saveAction = this::saveWeapon;
        removeAction = this::removeWeapon;
        clearAction = this::clear;
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
        List<String> fields = new ArrayList<>();
        addWeaponTypeFields(fields);
        guiPublisher.publish(new CsvBuilder(fields)
                .add(ComboBoxHandler.getEmptyIfNotPresent(dices))
                .add(rolls.getText())
                .add("").build(), messages.getProperty("weapons.hand.save"));
    }

    public void loadToEditor(AbstractHandWeapon<? extends WhiteWeaponType> weapon) {
        loadToInternalEditor(weapon);
    }

    private void removeWeapon(String name) {
        if (name.length()>=3) {
            guiPublisher.removeHandWeapon(name);
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
