package pl.khuzzuk.wfrpchar.gui;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.entities.LangElement;
import pl.khuzzuk.wfrpchar.entities.LoadingTimes;
import pl.khuzzuk.wfrpchar.entities.items.Accessibility;
import pl.khuzzuk.wfrpchar.entities.items.Item;
import pl.khuzzuk.wfrpchar.entities.items.RangedWeaponType;
import pl.khuzzuk.wfrpchar.messaging.publishers.Publishers;

import javax.inject.Inject;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class RangedWeaponTypePaneController implements Controller {
    @Inject
    @Publishers
    private GuiPublisher guiPublisher;

    @FXML
    ListView<String> rangedWeaponList;
    @FXML
    TextField rwName;
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
    ComboBox<String> rwAccessibility;
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
        initializeValidation();
        ComboBoxHandler.fill(Accessibility.SET, rwAccessibility);
        ComboBoxHandler.fill(LoadingTimes.SET, rwLoadTime);
    }

    void loadRangedWeapon(Collection<RangedWeaponType> weapons) {
        rangedWeaponList.getItems().clear();
        rangedWeaponList.getItems()
                .addAll(weapons.stream().map(Item::getName).collect(Collectors.toList()));
    }

    @FXML
    private void removeRangedWeapon() {
        if (rwName.getText().length() == 0) return;
        guiPublisher.removeRangedWeapon(rwName.getText());
    }

    @FXML
    private void selectRangedWeapon() {
        EntitiesAdapter.sendQuery(rangedWeaponList, guiPublisher::requestRangedWeaponLoad);
    }

    void loadRangedWeaponToEditor(RangedWeaponType rangedWeapon) {
        rwName.setText(rangedWeapon.getName());
        rwTypeName.setText(rangedWeapon.getTypeName());
        rwWeight.setText("" + rangedWeapon.getWeight());
        rwGold.setText(rangedWeapon.getPrice().getGold() + "");
        rwSilver.setText(rangedWeapon.getPrice().getSilver() + "");
        rwLead.setText(rangedWeapon.getPrice().getLead() + "");
        rwAccessibility.getSelectionModel().select(rangedWeapon.getAccessibility().getName());
        rwSpecialFeatures.setText(rangedWeapon.getSpecialFeature());
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
    void saveRangedWeaponType() {
        if (rwName.getText().length() == 0) return;
        List<String> fields = new LinkedList<>();
        fields.add(rwName.getText());
        fields.add(rwWeight.getText());
        fields.add(rwGold.getText() + "|" +
                rwSilver.getText() + "|" +
                rwLead.getText());
        fields.add(Accessibility.forName(
                rwAccessibility.getSelectionModel().getSelectedItem()).name());
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
