package pl.khuzzuk.wfrpchar.gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
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
import pl.khuzzuk.wfrpchar.rules.Dices;

import javax.inject.Inject;
import javax.inject.Named;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

@Component
@PropertySource("classpath:messages.properties")
public class HandWeaponsPaneController extends ItemsListedController {
    @FXML
    private ListView<String> determinantsView;
    @FXML
    private TextArea specialFeatures;
    @FXML
    private TextField gold;
    @FXML
    private TextField silver;
    @FXML
    private TextField lead;
    @FXML
    private ComboBox<String> secondaryResource;
    @FXML
    private ComboBox<String> primaryResource;
    @FXML
    private ComboBox<String> accessibility;
    @Numeric
    @FXML
    TextField rolls;
    @FXML
    private ComboBox<String> dices;
    @FXML
    private Button chooseBaseButton;
    @Inject
    private GuiPublisher guiPublisher;
    private WhiteWeaponType baseType;
    private AbstractHandWeapon handWeapon;
    private Collection<ResourceType> resources;
    @Inject
    @Named("messages")
    private Properties messages;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeValidation();
        getAction = guiPublisher::requestHandWeapon;
        ComboBoxHandler.fill(EnumSet.allOf(Accessibility.class), accessibility);
        ComboBoxHandler.fill(EnumSet.allOf(Dices.class), dices);
        guiPublisher.requestHandWeapons();
    }

    @FXML
    private void chooseBaseType() {
        guiPublisher.publish(messages.getProperty("weapons.hand.baseType.getAllTypes"));
    }

    void setBaseType(WhiteWeaponType baseType) {
        this.baseType = baseType;
        chooseBaseButton.setText(baseType.getName());
    }

    void fillResourceBoxes(Collection<ResourceType> resourceTypes) {
        resources = resourceTypes;
        Set<ResourceType> toFill = resources.stream().collect(Collectors.toSet());
        ComboBoxHandler.fill(toFill, primaryResource);
        ComboBoxHandler.fill(toFill, secondaryResource);
    }

    @FXML
    void chooseDeterminant() {
        guiPublisher.publish(messages.getProperty("determinants.creator.show.hw"));
    }

    void addDeterminant(String determinant) {
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
                .collect(Collectors.toList()));
        handWeapon.setSpecialFeatures(specialFeatures.getText());
    }

    @FXML
    private void saveWeapon() {
        handWeapon = startWeapon();
        setHandWeapon(handWeapon);
        guiPublisher.publish(handWeapon);
    }

    void loadToEditor(AbstractHandWeapon weapon) {
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
