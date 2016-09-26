package pl.khuzzuk.wfrpchar.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.entities.Price;
import pl.khuzzuk.wfrpchar.entities.determinants.DeterminantFactory;
import pl.khuzzuk.wfrpchar.entities.items.Accessibility;
import pl.khuzzuk.wfrpchar.entities.items.ResourceType;
import pl.khuzzuk.wfrpchar.entities.items.types.WhiteWeaponType;
import pl.khuzzuk.wfrpchar.entities.items.usable.AbstractHandWeapon;
import pl.khuzzuk.wfrpchar.entities.items.usable.AbstractWeapon;
import pl.khuzzuk.wfrpchar.entities.items.usable.OneHandedWeapon;
import pl.khuzzuk.wfrpchar.gui.ComboBoxHandler;
import pl.khuzzuk.wfrpchar.gui.EntitiesAdapter;
import pl.khuzzuk.wfrpchar.gui.Numeric;
import pl.khuzzuk.wfrpchar.rules.Dices;

import java.net.URL;
import java.util.EnumSet;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Component
@PropertySource("classpath:messages.properties")
public class HandWeaponsPaneController extends AbstractWeaponController {
    @Numeric
    @FXML
    TextField rolls;
    @FXML
    private ComboBox<String> dices;
    private WhiteWeaponType baseType;
    private AbstractHandWeapon handWeapon;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        getAction = guiPublisher::requestHandWeapon;
        ComboBoxHandler.fill(EnumSet.allOf(Dices.class), dices);
        guiPublisher.requestHandWeapons();
    }

    @FXML
    private void chooseBaseType() {
        guiPublisher.publish(messages.getProperty("weapons.hand.baseType.getAllTypes"));
    }

    public void setBaseType(WhiteWeaponType baseType) {
        this.baseType = baseType;
        chooseBaseButton.setText(baseType.getName());
    }

    @FXML
    void chooseDeterminant() {
        guiPublisher.publish(messages.getProperty("determinants.creator.show.hw"));
    }

    public void addDeterminant(String determinant) {
        determinantsView.getItems().add(determinant);
    }

    private AbstractHandWeapon startWeapon() {
        if (baseType == null) {
            return new OneHandedWeapon();
        }
        return AbstractWeapon.getFromPlacement(baseType.getPlacement());
    }

    private void setHandWeapon(AbstractHandWeapon handWeapon) {
        handWeapon.setBaseType(baseType);
        handWeapon.setName(name.getText());
        handWeapon.setAccessibility(Accessibility.forName(accessibility.getSelectionModel().getSelectedItem()));
        handWeapon.setPrimaryResource(getResourceFromList(primaryResource));
        handWeapon.setSecondaryResource(getResourceFromList(secondaryResource));
        handWeapon.setBasePrice(Price.parsePrice(gold.getText() + "|" + silver.getText() + "|" + lead.getText()));
        handWeapon.setDeterminants(determinantsView.getItems().stream().map(DeterminantFactory::getDeterminantByName)
                .collect(Collectors.toSet()));
        handWeapon.setSpecialFeatures(specialFeatures.getText());
    }

    @FXML
    private void saveWeapon() {
        handWeapon = startWeapon();
        setHandWeapon(handWeapon);
        guiPublisher.publish(handWeapon);
    }

    public void loadToEditor(AbstractHandWeapon weapon) {
        handWeapon = weapon;
        baseType = handWeapon.getBaseType();
        chooseBaseButton.setText(baseType.getName());
        name.setText(handWeapon.getName());
        accessibility.getSelectionModel().select(handWeapon.getAccessibility().getName());
        primaryResource.getSelectionModel().select(handWeapon.getPrimaryResource().getName());
        secondaryResource.getSelectionModel().select(handWeapon.getSecondaryResource().getName());
        Price basePrice = handWeapon.getBasePrice();
        gold.setText(basePrice.getGold() + "");
        silver.setText(basePrice.getSilver() + "");
        lead.setText(basePrice.getLead() + "");
        EntitiesAdapter.sendToListView(determinantsView, handWeapon.getDeterminants());
    }

    private ResourceType getResourceFromList(ComboBox<String> view) {
        return resources.stream()
                .filter(r -> r.getName().equals(view.getSelectionModel().getSelectedItem()))
                .findAny().orElse(null);
    }

    @FXML
    private void removeWeapon() {
        if (name.getText().length()>=3) {
            guiPublisher.removeHandWeapon(name.getText());
        }
    }

    private void clearEditor() {
        handWeapon = null;
        name.setText("");
        rolls.setText("");
        dices.getSelectionModel().clearSelection();
        accessibility.getSelectionModel().clearSelection();
        primaryResource.getSelectionModel().clearSelection();
        secondaryResource.getSelectionModel().clearSelection();
        specialFeatures.setText("");
        gold.setText("");
        silver.setText("");
        lead.setText("");
        determinantsView.getItems().clear();
    }
}
