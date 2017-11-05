package pl.khuzzuk.wfrpchar.gui.controllers;

import javafx.fxml.FXML;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.entities.competency.Skill;
import pl.khuzzuk.wfrpchar.entities.determinants.Determinant;
import pl.khuzzuk.wfrpchar.gui.EntitiesAdapter;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Component
public class SkillsPaneController extends AbstractFeaturedController<Skill> {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        entityType = Skill.class;
        getAllResponse = messages.getProperty("skills.result");
        removeEntityTopic = messages.getProperty("skills.remove");
        saveTopic = messages.getProperty("skills.save");
        clearAction = this::clear;
        initItems();
    }

    @FXML
    private void chooseDeterminant() {
        bus.send(messages.getProperty("determinants.creator.show.sk"));
    }

    @Override
    public void loadItem(Skill skill) {
        super.loadItem(skill);
        name.setText(skill.getName());
        specialFeatures.setText(skill.getSpecialFeatures());
        EntitiesAdapter.sendToListView(determinantsView, skill.getDeterminants());
    }

    @Override
    Skill supplyNewItem() {
        return new Skill();
    }

    @Override
    void saveAction() {
        List<String> fields = new ArrayList<>();
        fields.add(name.getText());
        fields.add(specialFeatures.getText());
        fields.add(Determinant.determinantsToCsv(Determinant.parseFromGui(
                new ArrayList<>(determinantsView.getItems()))));
        saveItem(fields.stream().collect(Collectors.joining(";")));
    }
}
