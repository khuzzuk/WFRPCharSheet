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
        guiPublisher.requestAmmunition();
        getAction = guiPublisher::requestAmmunition;
        removeAction = guiPublisher::removeAmmunition;
        saveAction = this::saveAmmunition;
        clearAction = this::clear;
    }

    @FXML
    private void chooseBaseType() {
        guiPublisher.publish("ammunition.baseType.getAllTypes");
    }

    @FXML
    private void chooseDeterminant() {
        guiPublisher.publish(messages.getProperty("determinants.creator.show.am"));
    }

    @FXML
    private void saveAmmunition() {
        List<String> fields = new ArrayList<>();
        addWeaponTypeFields(fields);
        guiPublisher.publish(fields.stream().collect(Collectors.joining(";")),
                messages.getProperty("ammunition.save"));
    }

    public void loadToEditor(Ammunition ammunition) {
        loadToInternalEditor(ammunition);
    }
}
