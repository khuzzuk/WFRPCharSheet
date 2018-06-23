package pl.khuzzuk.wfrpchar.gui.controllers;

import javafx.fxml.FXML;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.entities.items.types.RangedWeaponType;
import pl.khuzzuk.wfrpchar.entities.items.usable.Gun;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Component
public class RangeWeaponsPaneController extends AbstractWeaponController<Gun> {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        entityType = Gun.class;
        baseEntityType = RangedWeaponType.class;
        initItems();
    }

    @Override
    Gun supplyNewItem() {
        return new Gun();
    }

    @FXML
    private void chooseBaseType() {
        bus.send(messages.getProperty("weapons.ranged.baseType.getAllTypes"));
    }

    @FXML
    private void chooseDeterminant() {
        bus.send(messages.getProperty("determinants.creator.show.rw"));
    }
}
