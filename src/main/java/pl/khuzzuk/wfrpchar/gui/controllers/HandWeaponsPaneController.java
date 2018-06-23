package pl.khuzzuk.wfrpchar.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.entities.items.BattleEquipment;
import pl.khuzzuk.wfrpchar.entities.items.types.BastardWeaponType;
import pl.khuzzuk.wfrpchar.entities.items.types.TwoHandedWeaponType;
import pl.khuzzuk.wfrpchar.entities.items.types.WhiteWeaponType;
import pl.khuzzuk.wfrpchar.entities.items.usable.AbstractHandWeapon;
import pl.khuzzuk.wfrpchar.entities.items.usable.BastardWeapon;
import pl.khuzzuk.wfrpchar.entities.items.usable.OneHandedWeapon;
import pl.khuzzuk.wfrpchar.entities.items.usable.TwoHandedWeapon;
import pl.khuzzuk.wfrpchar.gui.ComboBoxHandler;
import pl.khuzzuk.wfrpchar.gui.Numeric;
import pl.khuzzuk.wfrpchar.rules.Dices;

import java.net.URL;
import java.util.EnumSet;
import java.util.ResourceBundle;

@Component
@PropertySource("classpath:messages.properties")
public class HandWeaponsPaneController extends AbstractWeaponController<AbstractHandWeapon<? extends BattleEquipment>> {
    @Numeric
    @FXML
    TextField rolls;
    @FXML
    private ComboBox<Dices> dices;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        entityType = AbstractHandWeapon.class;
        baseEntityType = WhiteWeaponType.class;
        ComboBoxHandler.fillWithEnums(EnumSet.allOf(Dices.class), dices);
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

    @Override
    void addConverters() {
        super.addConverters();
        addConverter(dices::getValue, AbstractHandWeapon::setDices);
        addConverter(rolls::getText, AbstractHandWeapon::setRolls, NumberUtils::toInt);
    }

    @Override
    void loadItem(AbstractHandWeapon<? extends BattleEquipment> weapon) {
        super.loadItem(weapon);
        dices.getSelectionModel().select(weapon.getDices());
        rolls.setText(weapon.getRolls() + "");
    }

    @Override
    AbstractHandWeapon<? extends WhiteWeaponType> supplyNewItem() {
        if (baseType instanceof BastardWeaponType) {
            return new BastardWeapon();
        } else if (baseType instanceof TwoHandedWeaponType) {
            return new TwoHandedWeapon();
        } else return new OneHandedWeapon();
    }

    @FXML
    @Override
    void clear() {
        super.clear();
        rolls.setText("");
        dices.getSelectionModel().clearSelection();
    }
}
