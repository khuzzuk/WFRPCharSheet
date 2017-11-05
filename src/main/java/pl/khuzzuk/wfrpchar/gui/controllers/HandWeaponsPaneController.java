package pl.khuzzuk.wfrpchar.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.entities.CsvBuilder;
import pl.khuzzuk.wfrpchar.entities.items.types.WhiteWeaponType;
import pl.khuzzuk.wfrpchar.entities.items.usable.AbstractHandWeapon;
import pl.khuzzuk.wfrpchar.entities.items.usable.OneHandedWeapon;
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
public class HandWeaponsPaneController extends AbstractWeaponController<AbstractHandWeapon<? extends WhiteWeaponType>> {
    @Numeric
    @FXML
    TextField rolls;
    @FXML
    private ComboBox<String> dices;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        entityType = AbstractHandWeapon.class;
        getAllResponse = messages.getProperty("weapons.hand.result");
        removeEntityTopic = messages.getProperty("weapons.hand.remove");
        saveTopic = messages.getProperty("weapons.hand.save");
        clearAction = this::clear;
        ComboBoxHandler.fill(EnumSet.allOf(Dices.class), dices);
        initItems();
    }

    @FXML
    private void chooseBaseType() {
        bus.send(messages.getProperty("weapons.hand.baseType.getAllTypes"));
    }

    @FXML
    void chooseDeterminant() {
        bus.send(messages.getProperty("determinants.creator.show.hw"));
    }

    @FXML
    @Override
    void saveAction() {
        List<String> fields = new ArrayList<>();
        addWeaponTypeFields(fields);
        saveItem(new CsvBuilder(fields)
                .add(ComboBoxHandler.getEmptyIfNotPresent(dices))
                .add(rolls.getText())
                .add("").build());
    }

    @Override
    public void loadItem(AbstractHandWeapon<? extends WhiteWeaponType> weapon) {
        super.loadItem(weapon);
        ComboBoxHandler.selectOrEmpty(dices, weapon.getDices());
        loadToInternalEditor(weapon);
    }

    @Override
    AbstractHandWeapon<? extends WhiteWeaponType> supplyNewItem() {
        //TODO change that! Use OneHandedWeapon.getFromPlacement() instaed, but placement is in base type.
        return new OneHandedWeapon();
    }

    @Override
    @FXML
    void clearEditor() {
        super.clearEditor();
        rolls.setText("");
        dices.getSelectionModel().clearSelection();
    }
}
