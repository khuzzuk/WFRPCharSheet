package pl.khuzzuk.wfrpchar.gui.controllers;

import javafx.fxml.FXML;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.entities.items.types.ArmorType;
import pl.khuzzuk.wfrpchar.entities.items.usable.Armor;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class ArmorPaneController extends AbstractWeaponController<Armor> {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        entityType = Armor.class;
        baseEntityType = ArmorType.class;
        initItems();
    }

    @Override
    Armor supplyNewItem() {
        return new Armor();
    }

    @FXML
    private void chooseBaseType() {
        bus.send(messages.getProperty("armor.baseType.getAllTypes"));
    }

    @FXML
    private void chooseDeterminant() {
        bus.send(messages.getProperty("determinants.creator.show.ar"));
    }
}
