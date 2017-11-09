package pl.khuzzuk.wfrpchar.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.entities.LoadingTimes;
import pl.khuzzuk.wfrpchar.entities.items.Placement;
import pl.khuzzuk.wfrpchar.entities.items.types.RangedWeaponType;
import pl.khuzzuk.wfrpchar.gui.ComboBoxHandler;
import pl.khuzzuk.wfrpchar.gui.Numeric;

import java.net.URL;
import java.util.EnumSet;
import java.util.ResourceBundle;

@Component
public class RangedWeaponTypePaneController extends FightingEquipmentPaneController<RangedWeaponType> {
    @FXML
    TextField rwTypeName;
    @FXML
    @Numeric
    TextField rwMinRange;
    @FXML
    @Numeric
    TextField rwMedRange;
    @FXML
    @Numeric
    TextField rwMaxRange;
    @FXML
    ComboBox<LoadingTimes> rwLoadTime;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        entityType = RangedWeaponType.class;
        getAllResponse = messages.getProperty("rangedWeapons.result");
        removeEntityTopic = messages.getProperty("rangedWeapons.remove");
        saveTopic = messages.getProperty("database.saveEquipment");
        clearAction = this::clear;
        ComboBoxHandler.fillWithEnums(LoadingTimes.SET, rwLoadTime);
        ComboBoxHandler.fillWithEnums(EnumSet.of(Placement.TWO_HANDS, Placement.ONE_HAND), placementBox);
        addMappingDeterminants();
        initItems();
    }

    @Override
    public void loadItem(RangedWeaponType rangedWeapon) {
        super.loadItem(rangedWeapon);
        rwTypeName.setText(rangedWeapon.getTypeName());
        rwMinRange.setText(rangedWeapon.getShortRange() + "");
        rwMedRange.setText(rangedWeapon.getEffectiveRange() + "");
        rwMaxRange.setText(rangedWeapon.getMaximumRange() + "");
        rwLoadTime.getSelectionModel().select(rangedWeapon.getReloadTime());
    }

    @Override
    void addConverters() {
        super.addConverters();
        addConverter(rwTypeName::getText, RangedWeaponType::setTypeName);
        addConverter(rwMinRange::getText, RangedWeaponType::setShortRange, NumberUtils::toInt);
        addConverter(rwMedRange::getText, RangedWeaponType::setEffectiveRange, NumberUtils::toInt);
        addConverter(rwMaxRange::getText, RangedWeaponType::setMaximumRange, NumberUtils::toInt);
        addConverter(rwLoadTime::getValue, RangedWeaponType::setReloadTime);
    }

    @Override
    RangedWeaponType supplyNewItem() {
        return new RangedWeaponType();
    }

    @FXML
    @Override
    void saveAction() {
    }

    @Override
    void clear() {
        super.clear();
        rwTypeName.clear();
        rwMinRange.clear();
        rwMedRange.clear();
        rwMaxRange.clear();
        rwLoadTime.getSelectionModel().clearSelection();
    }
}
