package pl.khuzzuk.wfrpchar.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import pl.khuzzuk.wfrpchar.entities.items.BattleEquipment;
import pl.khuzzuk.wfrpchar.entities.items.ResourceType;
import pl.khuzzuk.wfrpchar.entities.items.usable.AbstractWeapon;
import pl.khuzzuk.wfrpchar.gui.ComboBoxHandler;
import pl.khuzzuk.wfrpchar.gui.GuiEntityConverter;
import pl.khuzzuk.wfrpchar.repo.Criteria;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public abstract class AbstractWeaponController<T extends AbstractWeapon<? extends BattleEquipment>> extends ItemWithBaseTypeController<T> {
    @FXML
    ComboBox<ResourceType> secondaryResource;
    @FXML
    ComboBox<ResourceType> primaryResource;
    @FXML
    Button chooseBaseButton;

    Collection<ResourceType> resources;
    BattleEquipment baseType;
    Class<? extends BattleEquipment> baseEntityType;
    private String setBaseTypeMessage;
    private String getBaseTypeMessage;

    @Override
    void initItems() {
        super.initItems();
        setBaseTypeMessage = entityType.getName() + "baseType.set";
        getBaseTypeMessage = baseEntityType.getName() + ".get.named";
        bus.setReaction(messages.getProperty("resource.type.result"), this::fillResourceBoxes);
        bus.<BattleEquipment>setReaction(setBaseTypeMessage, baseType -> this.baseType = baseType);
        bus.send(messages.getProperty("database.getAll"), messages.getProperty("resource.type.result"), ResourceType.class);
    }

    private synchronized void fillResourceBoxes(Collection<ResourceType> resourceTypes) {
        resources = resourceTypes;
        Set<ResourceType> toFill = new HashSet<>(resources);
        ComboBoxHandler.fillWithEnums(toFill, primaryResource);
        ComboBoxHandler.fillWithEnums(toFill, secondaryResource);
    }

    public void setBaseType(String type) {
        bus.send(getBaseTypeMessage, setBaseTypeMessage, Criteria.builder().name(type).type(baseEntityType).build());
        chooseBaseButton.setText(type);
    }

    @Override
    @SuppressWarnings("unchecked")
    void addConverters() {
        super.addConverters();
        converters.add(new GuiEntityConverter(this::getItem, () -> baseType,
                (o, o2) -> ((AbstractWeapon) o).setBaseType((BattleEquipment) o2)));
        addConverter(primaryResource::getValue, AbstractWeapon::setPrimaryResource);
        addConverter(secondaryResource::getValue, AbstractWeapon::setSecondaryResource);
    }

    @Override
    void loadItem(T weapon) {
        super.loadItem(weapon);
        baseType = weapon.getBaseType();
        chooseBaseButton.setText(baseType.getName());
        primaryResource.getSelectionModel().select(weapon.getPrimaryResource());
        secondaryResource.getSelectionModel().select(weapon.getSecondaryResource());
    }

    @Override
    void clear() {
        super.clear();
        chooseBaseButton.setText("brak");
        primaryResource.getSelectionModel().clearSelection();
        secondaryResource.getSelectionModel().clearSelection();
        baseType = null;
        determinantsView.getItems().clear();
    }
}
