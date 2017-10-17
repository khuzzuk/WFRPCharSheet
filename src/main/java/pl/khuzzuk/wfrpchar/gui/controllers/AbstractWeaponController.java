package pl.khuzzuk.wfrpchar.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import pl.khuzzuk.wfrpchar.entities.determinants.Determinant;
import pl.khuzzuk.wfrpchar.entities.items.Accessibility;
import pl.khuzzuk.wfrpchar.entities.items.ResourceType;
import pl.khuzzuk.wfrpchar.entities.items.types.FightingEquipment;
import pl.khuzzuk.wfrpchar.entities.items.usable.AbstractWeapon;
import pl.khuzzuk.wfrpchar.gui.ComboBoxHandler;
import pl.khuzzuk.wfrpchar.gui.EntitiesAdapter;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractWeaponController extends AbstractFeaturedController {
    @FXML
    ComboBox<String> secondaryResource;
    @FXML
    ComboBox<String> primaryResource;
    @FXML
    Button chooseBaseButton;

    Collection<ResourceType> resources;
    String baseType;

    @Override
    void initItems() {
        super.initItems();
        bus.setReaction(messages.getProperty("resource.type.result"), this::fillResourceBoxes);
        bus.send(messages.getProperty("database.getAll"), messages.getProperty("resource.type.result"), ResourceType.class);
    }

    public void fillResourceBoxes(Collection<ResourceType> resourceTypes) {
        resources = resourceTypes;
        Set<ResourceType> toFill = new HashSet<>(resources);
        ComboBoxHandler.fill(toFill, primaryResource);
        ComboBoxHandler.fill(toFill, secondaryResource);
    }

    public void setBaseType(String type) {
        baseType = type;
        chooseBaseButton.setText(type);
    }

    void addWeaponTypeFields(List<String> fields) {
        fields.add(name.getText());
        fields.add(getPriceFromFields());
        fields.add(Accessibility.forName(accessibility.getSelectionModel().getSelectedItem()).name());
        fields.add(specialFeatures.getText());
        fields.add(baseType);
        fields.add(primaryResource.getSelectionModel().getSelectedItem());
        fields.add(secondaryResource.getSelectionModel().getSelectedItem());
        fields.add(Determinant.determinantsToCsv(Determinant.parseFromGui(
                determinantsView.getItems().stream().collect(Collectors.toList()))));
    }

    void loadToInternalEditor(AbstractWeapon<? extends FightingEquipment> weapon) {
        super.loadToInternalEditor(weapon);
        baseType = weapon.getBaseType().getName();
        chooseBaseButton.setText(baseType);
        primaryResource.getSelectionModel().select(weapon.getPrimaryResource().getName());
        secondaryResource.getSelectionModel().select(weapon.getSecondaryResource().getName());
        EntitiesAdapter.sendToListView(determinantsView, weapon.getDeterminants());
    }

    @FXML
    void clearEditor() {
        super.clearEditor();
        primaryResource.getSelectionModel().clearSelection();
        secondaryResource.getSelectionModel().clearSelection();
    }

    @Override
    void clear() {
        super.clear();
        chooseBaseButton.setText("brak");
        primaryResource.getSelectionModel().clearSelection();
        secondaryResource.getSelectionModel().clearSelection();
        baseType = "";
        determinantsView.getItems().clear();
    }
}
