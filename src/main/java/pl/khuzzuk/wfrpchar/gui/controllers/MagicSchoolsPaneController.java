package pl.khuzzuk.wfrpchar.gui.controllers;

import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.entities.CsvBuilder;
import pl.khuzzuk.wfrpchar.entities.competency.MagicSchool;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

//TODO extend something else since it has too much fields from ItemsListedController
@Component
public class MagicSchoolsPaneController extends EntitiesListedController {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeValidation();
        getAction = guiPublisher::requestMagicSchool;
        removeAction = guiPublisher::removeMagicSchool;
        saveAction = this::saveSchool;
        clearAction = super::clear;
        guiPublisher.requestMagicSchools();
    }

    private void saveSchool() {
        guiPublisher.publish(new CsvBuilder(new ArrayList<>())
                .add(name.getText())
                .add(specialFeatures.getText())
                .build(), messages.getProperty("magic.schools.save"));
    }

    public void loadToEditor(MagicSchool school) {
        name.setText(school.getName());
        specialFeatures.setText(school.getDescription());
    }
}
