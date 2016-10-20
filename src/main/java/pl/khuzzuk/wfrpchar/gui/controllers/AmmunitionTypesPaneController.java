package pl.khuzzuk.wfrpchar.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.entities.items.Accessibility;
import pl.khuzzuk.wfrpchar.entities.items.types.AmmunitionType;
import pl.khuzzuk.wfrpchar.gui.Numeric;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Component
public class AmmunitionTypesPaneController extends ItemsListedController {
    @Numeric
    @FXML
    TextField strength;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        guiPublisher.publish(messages.getProperty("ammo.type.query"));
        getAction = guiPublisher::requestAmmunitionType;
        removeAction = guiPublisher::removeAmmunitionType;
        saveAction = this::save;
    }

    public void loadToEditor(AmmunitionType ammunitionType) {
        loadToInternalEditor(ammunitionType);
        strength.setText(ammunitionType.getStrength() + "");
    }

    private void save() {
        List<String> fields = new ArrayList<>();
        fields.add(name.getText());
        fields.add(weight.getText());
        fields.add(getPriceFromFields());
        fields.add(Accessibility.forName(accessibility.getSelectionModel().getSelectedItem()).name());
        fields.add(specialFeatures.getText());
        fields.add(strength.getText());
        fields.add("AMMO");
        fields.add("AMMO");
        guiPublisher.publish(fields.stream().collect(Collectors.joining(";")),
                messages.getProperty("database.saveEquipment"));
    }
}
