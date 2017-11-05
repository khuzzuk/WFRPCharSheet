package pl.khuzzuk.wfrpchar.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.entities.CsvBuilder;
import pl.khuzzuk.wfrpchar.entities.LangElement;
import pl.khuzzuk.wfrpchar.entities.LoadingTimes;
import pl.khuzzuk.wfrpchar.entities.items.types.RangedWeaponType;
import pl.khuzzuk.wfrpchar.gui.ComboBoxHandler;
import pl.khuzzuk.wfrpchar.gui.Numeric;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

@Component
public class RangedWeaponTypePaneController extends ItemsListedController<RangedWeaponType> {
    @FXML
    TextField rwTypeName;
    @FXML
    @Numeric
    TextField rwStrength;
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
    ComboBox<String> rwLoadTime;
    @FXML
    TextField rwLangMasc;
    @FXML
    TextField rwLangFem;
    @FXML
    TextField rwLangNeutr;
    @FXML
    TextField rwLangAblative;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        entityType = RangedWeaponType.class;
        getAllResponse = messages.getProperty("rangedWeapons.result");
        removeEntityTopic = messages.getProperty("rangedWeapons.remove");
        saveTopic = messages.getProperty("database.saveEquipment");
        clearAction = this::clear;
        ComboBoxHandler.fill(LoadingTimes.SET, rwLoadTime);
        initItems();
    }

    @Override
    public void loadItem(RangedWeaponType rangedWeapon) {
        super.loadItem(rangedWeapon);
        name.setText(rangedWeapon.getName());
        rwTypeName.setText(rangedWeapon.getTypeName());
        weight.setText("" + rangedWeapon.getWeight());
        gold.setText(rangedWeapon.getPrice().getGold() + "");
        silver.setText(rangedWeapon.getPrice().getSilver() + "");
        lead.setText(rangedWeapon.getPrice().getLead() + "");
        accessibility.getSelectionModel().select(rangedWeapon.getAccessibility());
        specialFeatures.setText(rangedWeapon.getSpecialFeatures());
        rwStrength.setText(rangedWeapon.getStrength() + "");
        rwMinRange.setText(rangedWeapon.getShortRange() + "");
        rwMedRange.setText(rangedWeapon.getEffectiveRange() + "");
        rwMaxRange.setText(rangedWeapon.getMaximumRange() + "");
        rwLoadTime.getSelectionModel().select(rangedWeapon.getReloadTime().getName());
        rwLangMasc.setText(rangedWeapon.getNames().get(LangElement.ADJECTIVE_MASC_SING));
        rwLangFem.setText(rangedWeapon.getNames().get(LangElement.ADJECTIVE_FEM_SING));
        rwLangNeutr.setText(rangedWeapon.getNames().get(LangElement.ADJECTIVE_NEUTR_SING));
        rwLangAblative.setText(rangedWeapon.getNames().get(LangElement.ABLATIVE));
    }

    @Override
    RangedWeaponType supplyNewItem() {
        return new RangedWeaponType();
    }

    @FXML
    @Override
    void saveAction() {
        if (name.getText().length() < 3) return;
        saveItem(new CsvBuilder(new ArrayList<>())
                .add(name.getText())
                .add(weight.getText())
                .add(getPriceFromFields())
                .add(accessibility.getSelectionModel().getSelectedItem().name())
                .add(specialFeatures.getText())
                .add(rwStrength.getText())
                .add("RANGED_WEAPON")
                .add("TWO_HANDS")
                .add(Optional.ofNullable(rwLangMasc.getText()).orElse("") + "|" +
                Optional.ofNullable(rwLangFem.getText()).orElse("") + "|" +
                Optional.ofNullable(rwLangNeutr.getText()).orElse("") + "|" +
                Optional.ofNullable(rwLangAblative.getText()).orElse(""))
                .add("") //determinants
                .add(rwTypeName.getText())
                .add(rwMinRange.getText())
                .add(rwMedRange.getText())
                .add(rwMaxRange.getText())
                .add(LoadingTimes.forName(rwLoadTime.getSelectionModel().getSelectedItem()).name())
                .build());
    }

    @Override
    void clear() {
        super.clear();
        rwStrength.clear();
        rwLangMasc.clear();
        rwLangFem.clear();
        rwLangNeutr.clear();
        rwLangAblative.clear();
        rwTypeName.clear();
        rwMinRange.clear();
        rwMedRange.clear();
        rwMaxRange.clear();
        rwLoadTime.getSelectionModel().clearSelection();
    }
}
