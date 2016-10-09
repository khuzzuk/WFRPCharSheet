package pl.khuzzuk.wfrpchar.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
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
public class ProfessionClassPaneController extends SkillViewController {
    @FXML
    private ComboBox<String> baseDeterminantType;
    @FXML
    private ListView<String> skillsView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ComboBoxHandler.fill(EnumSet.allOf(DeterminantsType.class), baseDeterminantType);
        skillChooserMsg = messages.getProperty("professions.class.skills.getAllTypes");
        guiPublisher.requestProfessionClasses();
        getAction = guiPublisher::requestProfessionClass;
        removeAction = guiPublisher::removeProfessionClass;
        saveAction = this::saveClass;
    }

    private void saveClass() {
        List<String> fields = new ArrayList<>();
        fields.add(name.getText());
        fields.add(specialFeatures.getText());
        if (baseDeterminantType.getSelectionModel().getSelectedIndex() > -1) {
            fields.add(DeterminantsType.forName(baseDeterminantType.getSelectionModel().getSelectedItem()).name());
        }
        fields.add(skillsView.getItems().stream().collect(Collectors.joining("|")));
        guiPublisher.publish(fields.stream().collect(Collectors.joining(";")), messages.getProperty("professions.class.save"));
    }

    public void loadToEditor(ProfessionClass professionClass) {
        name.setText(professionClass.getName());
        specialFeatures.setText(professionClass.getSpecialFeatures());
        baseDeterminantType.getSelectionModel().select(professionClass.getPrimaryDeterminant().getName());
        if (professionClass.getSkills() != null) {
            EntitiesAdapter.sendToListView(skillsView, professionClass.getSkills());
        }
    }
}
