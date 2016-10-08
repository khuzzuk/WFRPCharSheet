package pl.khuzzuk.wfrpchar.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.entities.competency.Profession;
import pl.khuzzuk.wfrpchar.entities.determinants.DeterminantFactory;
import pl.khuzzuk.wfrpchar.gui.EntitiesAdapter;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Component
public class ProfessionPaneController extends AbstractFeaturedController {
    @FXML
    private ListView<String> professionsNextView;
    @FXML
    private ListView<String> skillsView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showDeterminantCreatorMsg = messages.getProperty("determinants.creator.show.pr");
        getAction = guiPublisher::requestProfession;
        removeAction = guiPublisher::removeProfession;
        saveAction = this::saveProfession;
        guiPublisher.requestProfessions();
    }

    public void loadToEditor(Profession profession) {
        name.setText(profession.getName());
        specialFeatures.setText(profession.getSpecialFeatures());
        EntitiesAdapter.sendToListView(determinantsView, profession.getDeterminants());
        EntitiesAdapter.sendToListView(skillsView, profession.getSkills());
        EntitiesAdapter.sendToListView(professionsNextView, profession.getNextProfessions());
    }

    @FXML
    private void showSkillChooser() {
        guiPublisher.publish(messages.getProperty("professions.skills.getAllTypes"));
    }

    @FXML
    private void showProfessionChooser() {
        guiPublisher.publish(messages.getProperty("professions.next.getAllTypes"));
    }

    public void addSkill(String skill) {
        skillsView.getItems().add(skill);
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
        guiPublisher.publish(fields.stream().collect(Collectors.joining(";")), messages.getProperty("professions.save"));
    }

    private String getDeterminantsFromList() {
        return determinantsView.getItems().stream()
                .map(DeterminantFactory::getProfessionDeterminant)
                .collect(Collectors.joining("|"));
    }

    @FXML
    private void removeSkill() {
        EntitiesAdapter.removeSelected(skillsView);
    }

    @FXML
    private void removeProfession() {
        EntitiesAdapter.removeSelected(professionsNextView);
    }
}
