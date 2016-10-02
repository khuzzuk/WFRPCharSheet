package pl.khuzzuk.wfrpchar.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.entities.items.types.AmmunitionType;
import pl.khuzzuk.wfrpchar.gui.Numeric;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class AmmunitionTypesPaneController extends ItemsListedController {
    @Numeric
    @FXML
    TextField strength;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        guiPublisher.publish(messages.getProperty("ammo.type.query"));
    }

    public void loadToEditor(AmmunitionType ammunitionType) {
        loadToInternalEditor(ammunitionType);
        strength.setText(ammunitionType.getStrength() + "");
    }
}
