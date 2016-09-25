package pl.khuzzuk.wfrpchar.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.entities.items.types.RangedWeaponType;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class RangeWeaponsPaneController extends AbstractWeaponController {
    @FXML
    private Button type;

    RangedWeaponType baseType;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        removeAction = guiPublisher::removeRangedWeapon;
        getAction = guiPublisher::requestRangedWeapon;
        guiPublisher.requestRangedWeapons();
    }

    @FXML
    private void chooseDeterminant() {
        guiPublisher.publish(messages.getProperty("determinants.creator.show.rw"));
    }

    @FXML
    private void saveWeapon() {

    }
}
