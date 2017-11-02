package pl.khuzzuk.wfrpchar.gui.controllers;

import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.entities.CsvBuilder;
import pl.khuzzuk.wfrpchar.entities.competency.MagicSchool;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

//TODO extend something else since it has too much fields from ItemsListedController
@Component
public class MagicSchoolsPaneController extends EntitiesListedController<MagicSchool> {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeValidation();
        entityType = MagicSchool.class;
        getAllResponse = messages.getProperty("magic.schools.result");
        getEntityTopic = messages.getProperty("magic.schools.query.specific");
        removeEntityTopic = messages.getProperty("magic.schools.remove");
        saveTopic = messages.getProperty("magic.schools.save");
        saveAction = this::saveSchool;
        clearAction = super::clear;
        initItems();
    }

    private void saveSchool() {
        saveItem(new CsvBuilder(new ArrayList<>())
                .add(name.getText())
                .add(specialFeatures.getText())
                .build());
    }

    @Override
    public void loadItem(MagicSchool school) {
        name.setText(school.getName());
        specialFeatures.setText(school.getDescription());
    }
}
