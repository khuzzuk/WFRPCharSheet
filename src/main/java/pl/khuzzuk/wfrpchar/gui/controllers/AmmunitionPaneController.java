package pl.khuzzuk.wfrpchar.gui.controllers;

import javafx.fxml.FXML;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.entities.items.usable.Ammunition;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Component
public class AmmunitionPaneController extends AbstractWeaponController {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        entityType = Ammunition.class;
        getAllResponse = messages.getProperty("ammunition.result");
        getEntityTopic = messages.getProperty("ammunition.query.specific");
        removeEntityTopic = messages.getProperty("ammunition.remove");
        saveTopic = messages.getProperty("ammunition.save");
        saveAction = this::saveAmmunition;
        clearAction = this::clear;
        initItems();
    }

    @FXML
    private void chooseBaseType() {
        bus.send(messages.getProperty("ammunition.baseType.getAllTypes"));
    }

    @FXML
    private void chooseDeterminant() {
        bus.send(messages.getProperty("determinants.creator.show.am"));
    }

    @FXML
    private void saveAmmunition() {
        List<String> fields = new ArrayList<>();
        addWeaponTypeFields(fields);
        saveItem(fields.stream().collect(Collectors.joining(";")));
    }

    public void loadToEditor(Ammunition ammunition) {
        loadToInternalEditor(ammunition);
    }
}
