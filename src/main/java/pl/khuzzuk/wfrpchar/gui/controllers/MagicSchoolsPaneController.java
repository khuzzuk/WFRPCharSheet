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
        initItems();
    }

    @Override
    MagicSchool supplyNewItem() {
        return new MagicSchool();
    }
}
