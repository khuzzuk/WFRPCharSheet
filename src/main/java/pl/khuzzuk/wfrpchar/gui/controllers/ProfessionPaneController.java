package pl.khuzzuk.wfrpchar.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.entities.competency.Profession;
import pl.khuzzuk.wfrpchar.entities.competency.ProfessionClass;
import pl.khuzzuk.wfrpchar.entities.determinants.DeterminantFactory;
import pl.khuzzuk.wfrpchar.gui.ComboBoxHandler;
import pl.khuzzuk.wfrpchar.gui.EntitiesAdapter;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class ProfessionPaneController extends SkillViewController {
    @FXML
    private ComboBox<String> professionClass;
    @FXML
    private ListView<String> professionsNextView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showDeterminantCreatorMsg = messages.getProperty("determinants.creator.show.pr");
        skillChooserMsg = messages.getProperty("professions.skills.getAllTypes");
        getAction = guiPublisher::requestProfession;
        removeAction = guiPublisher::removeProfession;
        saveAction = this::saveProfession;
        guiPublisher.requestProfessions();
    }

    public void loadProfessionClasses(Collection<ProfessionClass> professionClasses) {
        ComboBoxHandler.fill(professionClasses.stream().collect(Collectors.toSet()), professionClass);
    }

    public void loadToEditor(Profession profession) {
        name.setText(profession.getName());
        specialFeatures.setText(profession.getSpecialFeatures());
        EntitiesAdapter.sendToListView(determinantsView, profession.getDeterminants());
        EntitiesAdapter.sendToListView(skillsView, profession.getSkills());
        EntitiesAdapter.sendToListView(professionsNextView, profession.getNextProfessions());
        professionClass.getSelectionModel().select(profession.getProfessionClass().getName());
    }

    @FXML
    private void showProfessionChooser() {
        guiPublisher.publish(messages.getProperty("professions.next.getAllTypes"));
    }

    public void addProfession(String profession) {
        professionsNextView.getItems().add(profession);
    }

    private void saveProfession() {
        List<String> fields = new ArrayList<>();
        fields.add(name.getText());
        fields.add(specialFeatures.getText());
        fields.add(skillsView.getItems().stream().collect(Collectors.joining("|")));
        fields.add(professionsNextView.getItems().stream().collect(Collectors.joining("|")));
        fields.add(getDeterminantsFromList());
        fields.add(ComboBoxHandler.getEmptyIfNotPresent(professionClass));
        guiPublisher.publish(fields.stream().collect(Collectors.joining(";")), messages.getProperty("professions.save"));
    }

    private String getDeterminantsFromList() {
        return determinantsView.getItems().stream()
                .map(DeterminantFactory::getProfessionDeterminant)
                .collect(Collectors.joining("|"));
    }

    @FXML
    private void removeProfession() {
        EntitiesAdapter.removeSelected(professionsNextView);
    }
}
