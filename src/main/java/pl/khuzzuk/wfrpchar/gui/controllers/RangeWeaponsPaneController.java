package pl.khuzzuk.wfrpchar.gui.controllers;

import javafx.fxml.FXML;
import org.springframework.stereotype.Component;
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
        getAllResponse = messages.getProperty("weapons.ranged.result");
        removeEntityTopic = messages.getProperty("weapons.ranged.remove");
        saveTopic = messages.getProperty("weapons.ranged.save");
        saveAction = this::saveWeapon;
        clearAction = this::clear;
        initItems();
    }

    @Override
    public void loadItem(Gun gun) {
        loadToInternalEditor(gun);
    }

    @FXML
    private void chooseBaseType() {
        bus.send(messages.getProperty("weapons.ranged.baseType.getAllTypes"));
    }

    @FXML
    private void chooseDeterminant() {
        bus.send(messages.getProperty("determinants.creator.show.rw"));
    }

    @FXML
    private void saveWeapon() {
        if (name.getText().length() < 3 || baseType.length() < 3) {
            return;
        }
        List<String> fields = new ArrayList<>();
        addWeaponTypeFields(fields);
        saveItem(fields.stream().collect(Collectors.joining(";")));
    }
}
