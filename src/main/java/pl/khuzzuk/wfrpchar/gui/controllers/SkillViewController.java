package pl.khuzzuk.wfrpchar.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import pl.khuzzuk.wfrpchar.entities.DeterminantContainer;
import pl.khuzzuk.wfrpchar.entities.SkillContainer;
import pl.khuzzuk.wfrpchar.entities.competency.Skill;
import pl.khuzzuk.wfrpchar.gui.EntitiesAdapter;

import java.util.HashSet;

public abstract class SkillViewController<T extends SkillContainer & DeterminantContainer> extends AbstractFeaturedController<T> {
    @FXML
    ListView<Skill> skillsView;
    String skillChooserMsg;

    @FXML
    private void removeSkill() {
        EntitiesAdapter.removeSelected(skillsView);
    }

    public void addSkill(Skill skill) {
        skillsView.getItems().add(skill);
    }

    @FXML
    private void showSkillChooser() {
        bus.send(messages.getProperty(skillChooserMsg));
    }

    @Override
    void loadItem(T item) {
        super.loadItem(item);
        skillsView.getItems().addAll(item.getSkills());
    }

    @Override
    void addConverters() {
        super.addConverters();
        addConverter(skillsView::getItems, SkillContainer::setSkills, HashSet::new);
    }

    void clear() {
        super.clear();
        skillsView.getItems().clear();
    }
}
