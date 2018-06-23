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
        initItems();
    }

    @FXML
    private void chooseDeterminant() {
        bus.send(messages.getProperty("determinants.creator.show.sk"));
    }

    @Override
    Skill supplyNewItem() {
        return new Skill();
    }
}
