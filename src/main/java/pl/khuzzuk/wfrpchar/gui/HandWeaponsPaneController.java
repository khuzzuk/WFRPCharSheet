package pl.khuzzuk.wfrpchar.gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.entities.Price;
import pl.khuzzuk.wfrpchar.entities.determinants.Determinant;
import pl.khuzzuk.wfrpchar.entities.items.Accessibility;
import pl.khuzzuk.wfrpchar.entities.items.HandWeapon;
import pl.khuzzuk.wfrpchar.entities.items.Placement;
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
    private ListView<Determinant> determinantsView;
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
    private ComboBox<String> placement;
    @FXML
    private Button chooseBaseButton;
    @Inject
    private GuiPublisher guiPublisher;
    private WhiteWeaponType baseType;
    private AbstractHandWeapon handWeapon;
    private Collection<ResourceType> resources;
    private Collection<HandWeapon> weapons;
    private List<Determinant> determinants;
    @Inject
    @Named("messages")
    private Properties messages;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeValidation();
        ComboBoxHandler.fill(EnumSet.of(Placement.ONE_HAND, Placement.TWO_HANDS, Placement.BASTARD),
                placement);
        ComboBoxHandler.fill(EnumSet.allOf(Accessibility.class), accessibility);
        ComboBoxHandler.fill(EnumSet.allOf(Dices.class), dices);
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

    void addDeterminant(Determinant determinant) {
        if (determinants == null) {
            determinants = new ArrayList<>();
        }
        determinants.add(determinant);
        EntitiesAdapter.listDeterminants(determinantsView, determinants);
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
        handWeapon.setDeterminants(determinants);
    }

    private void save() {
        if (handWeapon == null) {
            handWeapon = startWeapon();
        }
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
        determinants = handWeapon.getDeterminants();
        EntitiesAdapter.listDeterminants(determinantsView, determinants);
    }

    private ResourceType getResourceFromList(ComboBox<String> view) {
        return resources.stream()
                .filter(r -> r.getName().equals(view.getSelectionModel().getSelectedItem()))
                .findAny().orElse(null);
    }
}
