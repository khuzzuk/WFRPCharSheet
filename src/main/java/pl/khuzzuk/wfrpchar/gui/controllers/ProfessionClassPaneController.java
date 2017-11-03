package pl.khuzzuk.wfrpchar.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.entities.competency.ProfessionClass;
import pl.khuzzuk.wfrpchar.entities.determinants.DeterminantsType;
import pl.khuzzuk.wfrpchar.gui.ComboBoxHandler;
import pl.khuzzuk.wfrpchar.gui.EntitiesAdapter;

import java.net.URL;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Component
public class ProfessionClassPaneController extends SkillViewController<ProfessionClass> {
    @FXML
    private ComboBox<String> baseDeterminantType;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ComboBoxHandler.fill(EnumSet.allOf(DeterminantsType.class), baseDeterminantType);
        entityType = ProfessionClass.class;
        skillChooserMsg = messages.getProperty("professions.class.skills.getAllTypes");
        getAllResponse = messages.getProperty("professions.class.result");
        removeEntityTopic = messages.getProperty("professions.class.remove");
        saveTopic = messages.getProperty("professions.class.save");
        saveAction = this::saveClass;
        clearAction = this::clear;
        initItems();
    }

    private void saveClass() {
        List<String> fields = new ArrayList<>();
        fields.add(name.getText());
        fields.add(specialFeatures.getText());
        if (baseDeterminantType.getSelectionModel().getSelectedIndex() > -1) {
            fields.add(DeterminantsType.forName(baseDeterminantType.getSelectionModel().getSelectedItem()).name());
        }
        fields.add(skillsView.getItems().stream().collect(Collectors.joining("|")));
        saveItem(fields.stream().collect(Collectors.joining(";")));
    }

    @Override
    public void loadItem(ProfessionClass professionClass) {
        name.setText(professionClass.getName());
        specialFeatures.setText(professionClass.getSpecialFeatures());
        baseDeterminantType.getSelectionModel().select(professionClass.getPrimaryDeterminant().getName());
        if (professionClass.getSkills() != null) {
            EntitiesAdapter.sendToListView(skillsView, professionClass.getSkills());
        }
    }

    void clear() {
        super.clear();
        baseDeterminantType.getSelectionModel().clearSelection();
    }
}
