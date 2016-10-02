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
public class RangeWeaponsPaneController extends AbstractWeaponController {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        removeAction = guiPublisher::removeRangedWeapon;
        getAction = guiPublisher::requestRangedWeapon;
        guiPublisher.requestRangedWeapons();
    }

    public void loadToEditor(Gun gun) {
        loadToInternalEditor(gun);
    }

    @FXML
    private void chooseBaseType() {
        guiPublisher.publish(messages.getProperty("weapons.ranged.baseType.getAllTypes"));
    }

    @FXML
    private void chooseDeterminant() {
        guiPublisher.publish(messages.getProperty("determinants.creator.show.rw"));
    }

    @FXML
    private void saveWeapon() {
        if (name.getText().length() < 3 || baseType.length() < 3) {
            return;
        }
        List<String> fields = new ArrayList<>();
        addWeaponTypeFields(fields);
        guiPublisher.publish(fields.stream().collect(Collectors.joining(";")),
                messages.getProperty("weapons.ranged.save"));
    }
}
