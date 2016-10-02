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
public class ArmorPaneController extends AbstractWeaponController {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        getAction = guiPublisher::requestArmor;
        removeAction = guiPublisher::removeArmor;
        guiPublisher.requestArmor();
    }

    public void loadToEditor(Armor armor) {
        loadToInternalEditor(armor);
    }

    @FXML
    private void chooseBaseType() {
        guiPublisher.publish(messages.getProperty("armor.baseType.getAllTypes"));
    }

    @FXML
    private void chooseDeterminant() {
        guiPublisher.publish(messages.getProperty("determinants.creator.show.ar"));
    }

    @FXML
    private void saveWeapon() {
        List<String> fields = new ArrayList<>();
        addWeaponTypeFields(fields);
        guiPublisher.publish(fields.stream().collect(Collectors.joining(";")), messages.getProperty("armor.save"));
    }
}
