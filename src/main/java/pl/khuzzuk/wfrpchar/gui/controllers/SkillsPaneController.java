package pl.khuzzuk.wfrpchar.gui.controllers;

import javafx.fxml.FXML;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.entities.determinants.Determinant;
import pl.khuzzuk.wfrpchar.entities.skills.Skill;
import pl.khuzzuk.wfrpchar.gui.EntitiesAdapter;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Component
public class SkillsPaneController extends AbstractWeaponController {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getAction = guiPublisher::requestSkill;
        removeAction = guiPublisher::removeSkill;
        saveAction = this::saveSkill;
        guiPublisher.requestSkills();
    }

    @FXML
    private void chooseDeterminant() {
        guiPublisher.publish(messages.getProperty("determinants.creator.show.sk"));
    }

    public void loadToEditor(Skill skill) {
        name.setText(skill.getName());
        specialFeatures.setText(skill.getSpecialFeatures());
        EntitiesAdapter.sendToListView(determinantsView, skill.getDeterminants());
    }

    private void saveSkill() {
        List<String> fields = new ArrayList<>();
        fields.add(name.getText());
        fields.add(specialFeatures.getText());
        fields.add(Determinant.determinantsToCsv(Determinant.parseFromGui(
                determinantsView.getItems().stream().collect(Collectors.toList()))));
        guiPublisher.publish(fields.stream().collect(Collectors.joining(";")),
                messages.getProperty("skills.save"));
    }
}
