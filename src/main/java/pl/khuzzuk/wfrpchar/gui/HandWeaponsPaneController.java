package pl.khuzzuk.wfrpchar.gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.entities.determinants.Determinant;
import pl.khuzzuk.wfrpchar.entities.items.Accessibility;
import pl.khuzzuk.wfrpchar.entities.items.HandWeapon;
import pl.khuzzuk.wfrpchar.entities.items.Placement;
import pl.khuzzuk.wfrpchar.entities.items.ResourceType;
import pl.khuzzuk.wfrpchar.entities.items.types.WhiteWeaponType;
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
    private HandWeapon handWeapon;
    private Collection<ResourceType> resources;
    private Collection<HandWeapon> weapons;
    private Set<Determinant> determinants;
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
            determinants = new HashSet<>();
        }
        determinants.add(determinant);
        EntitiesAdapter.listDeterminants(determinantsView, determinants);
    }
}
