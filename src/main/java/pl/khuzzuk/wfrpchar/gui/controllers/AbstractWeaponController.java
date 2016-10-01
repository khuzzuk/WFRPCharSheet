package pl.khuzzuk.wfrpchar.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import pl.khuzzuk.wfrpchar.entities.determinants.Determinant;
import pl.khuzzuk.wfrpchar.entities.items.Accessibility;
import pl.khuzzuk.wfrpchar.entities.items.ResourceType;
import pl.khuzzuk.wfrpchar.gui.ComboBoxHandler;
import pl.khuzzuk.wfrpchar.gui.GuiPublisher;
import pl.khuzzuk.wfrpchar.gui.Numeric;

import javax.inject.Inject;
import javax.inject.Named;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public abstract class AbstractWeaponController extends ItemsListedController {
    @FXML
    ComboBox<String> secondaryResource;
    @FXML
    ComboBox<String> primaryResource;
    @FXML
    @Numeric
    TextField gold;
    @FXML
    @Numeric
    TextField silver;
    @FXML
    @Numeric
    TextField lead;
    @FXML
    ListView<String> determinantsView;
    @FXML
    TextArea specialFeatures;
    @FXML
    Button chooseBaseButton;

    Collection<ResourceType> resources;
    String baseType;
    @Inject
    @Named("messages")
    Properties messages;
    @Inject
    GuiPublisher guiPublisher;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
    }

    public void fillResourceBoxes(Collection<ResourceType> resourceTypes) {
        resources = resourceTypes;
        Set<ResourceType> toFill = resources.stream().collect(Collectors.toSet());
        ComboBoxHandler.fill(toFill, primaryResource);
        ComboBoxHandler.fill(toFill, secondaryResource);
    }

    public void addDeterminant(String determinant) {
        determinantsView.getItems().add(determinant);
    }

    public void setBaseType(String type) {
        baseType = type;
        chooseBaseButton.setText(type);
    }

    void addWeaponTypeFields(List<String> fields) {
        fields.add(name.getText());
        fields.add(gold.getText() + "|" + silver.getText() + "|" + lead.getText());
        fields.add(Accessibility.forName(accessibility.getSelectionModel().getSelectedItem()).name());
        fields.add(specialFeatures.getText());
        fields.add(baseType);
        fields.add(primaryResource.getSelectionModel().getSelectedItem());
        fields.add(secondaryResource.getSelectionModel().getSelectedItem());
        fields.add(Determinant.determinantsToCsv(Determinant.parseFromGui(
                determinantsView.getItems().stream().collect(Collectors.toList()))));
    }
}
