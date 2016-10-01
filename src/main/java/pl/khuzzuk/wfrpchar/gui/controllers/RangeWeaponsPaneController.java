package pl.khuzzuk.wfrpchar.gui.controllers;

import javafx.fxml.FXML;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.entities.items.usable.Gun;
import pl.khuzzuk.wfrpchar.gui.EntitiesAdapter;

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
        name.setText(gun.getName());
        baseType = gun.getBaseType().getName();
        chooseBaseButton.setText(baseType);
        accessibility.getSelectionModel().select(gun.getAccessibility().getName());
        primaryResource.getSelectionModel().select(gun.getPrimaryResource().getName());
        secondaryResource.getSelectionModel().select(gun.getSecondaryResource().getName());
        gold.setText(gun.getBasePrice().getGold() + "");
        silver.setText(gun.getBasePrice().getSilver() + "");
        lead.setText(gun.getBasePrice().getLead() + "");
        EntitiesAdapter.sendToListView(determinantsView, gun.getDeterminants());
        specialFeatures.setText(gun.getSpecialFeatures());
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
