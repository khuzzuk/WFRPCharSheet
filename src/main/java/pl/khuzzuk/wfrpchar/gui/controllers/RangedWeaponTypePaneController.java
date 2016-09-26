package pl.khuzzuk.wfrpchar.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.entities.LangElement;
import pl.khuzzuk.wfrpchar.entities.LoadingTimes;
import pl.khuzzuk.wfrpchar.entities.items.Accessibility;
import pl.khuzzuk.wfrpchar.entities.items.types.RangedWeaponType;
import pl.khuzzuk.wfrpchar.gui.ComboBoxHandler;
import pl.khuzzuk.wfrpchar.gui.FloatNumeric;
import pl.khuzzuk.wfrpchar.gui.Numeric;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Component
public class RangedWeaponTypePaneController extends ItemsListedController {
    @FXML
    TextField rwTypeName;
    @FXML
    @Numeric
    TextField rwGold;
    @FXML
    @Numeric
    TextField rwSilver;
    @FXML
    @Numeric
    TextField rwLead;
    @FXML
    TextArea rwSpecialFeatures;
    @FXML
    @FloatNumeric
    TextField rwWeight;
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
        removeAction = guiPublisher::removeRangedTypeWeapon;
        getAction = guiPublisher::requestRangedWeaponType;
        saveAction = this::saveRangedWeaponType;
        ComboBoxHandler.fill(LoadingTimes.SET, rwLoadTime);
    }

    public void loadRangedWeaponToEditor(RangedWeaponType rangedWeapon) {
        name.setText(rangedWeapon.getName());
        rwTypeName.setText(rangedWeapon.getTypeName());
        rwWeight.setText("" + rangedWeapon.getWeight());
        rwGold.setText(rangedWeapon.getPrice().getGold() + "");
        rwSilver.setText(rangedWeapon.getPrice().getSilver() + "");
        rwLead.setText(rangedWeapon.getPrice().getLead() + "");
        accessibility.getSelectionModel().select(rangedWeapon.getAccessibility().getName());
        rwSpecialFeatures.setText(rangedWeapon.getSpecialFeatures());
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

    @FXML
    private void saveRangedWeaponType() {
        if (name.getText().length() == 0) return;
        List<String> fields = new LinkedList<>();
        fields.add(name.getText());
        fields.add(rwWeight.getText());
        fields.add(rwGold.getText() + "|" +
                rwSilver.getText() + "|" +
                rwLead.getText());
        fields.add(Accessibility.forName(
                accessibility.getSelectionModel().getSelectedItem()).name());
        fields.add(rwSpecialFeatures.getText());
        fields.add(rwStrength.getText());
        fields.add("RANGED_WEAPON");
        fields.add("TWO_HANDS");
        fields.add(Optional.ofNullable(rwLangMasc.getText()).orElse("") + "|" +
                Optional.ofNullable(rwLangFem.getText()).orElse("") + "|" +
                Optional.ofNullable(rwLangNeutr.getText()).orElse("") + "|" +
                Optional.ofNullable(rwLangAblative.getText()).orElse(""));
        fields.add(""); //determinants
        fields.add(rwTypeName.getText());
        fields.add(rwMinRange.getText());
        fields.add(rwMedRange.getText());
        fields.add(rwMaxRange.getText());
        fields.add(LoadingTimes.forName(rwLoadTime.getSelectionModel().getSelectedItem()).name());
        guiPublisher.saveItem(fields.stream().collect(Collectors.joining(";")));
    }
}
