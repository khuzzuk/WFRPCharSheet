package pl.khuzzuk.wfrpchar.gui.controllers;

import javafx.fxml.FXML;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.entities.items.usable.Armor;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Component
public class ArmorPaneController extends AbstractWeaponController<Armor> {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        entityType = Armor.class;
        getAllResponse = messages.getProperty("armor.result");
        getEntityTopic = messages.getProperty("armor.query.specific");
        removeEntityTopic = messages.getProperty("armor.remove");
        saveTopic = messages.getProperty("armor.save");
        clearAction = this::clear;
        saveAction = this::saveWeapon;
        initItems();
    }

    @Override
    public void loadItem(Armor armor) {
        loadToInternalEditor(armor);
    }

    @FXML
    private void chooseBaseType() {
        bus.send(messages.getProperty("armor.baseType.getAllTypes"));
    }

    @FXML
    private void chooseDeterminant() {
        bus.send(messages.getProperty("determinants.creator.show.ar"));
    }

    @FXML
    private void saveWeapon() {
        List<String> fields = new ArrayList<>();
        addWeaponTypeFields(fields);
        saveItem(fields.stream().collect(Collectors.joining(";")));
    }
}
