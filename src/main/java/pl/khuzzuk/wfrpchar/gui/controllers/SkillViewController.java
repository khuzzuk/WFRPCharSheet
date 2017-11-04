package pl.khuzzuk.wfrpchar.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import pl.khuzzuk.wfrpchar.entities.SkillContainer;
import pl.khuzzuk.wfrpchar.gui.EntitiesAdapter;

public abstract class SkillViewController<T extends SkillContainer> extends AbstractFeaturedController<T> {
    @FXML
    ListView<String> skillsView;
    String skillChooserMsg;

    @FXML
    private void removeSkill() {
        EntitiesAdapter.removeSelected(skillsView);
    }

    public void addSkill(String skill) {
        skillsView.getItems().add(skill);
    }

    @FXML
    private void showSkillChooser() {
        bus.send(messages.getProperty(skillChooserMsg));
    }

    void clear() {
        super.clearEditor();
        skillsView.getItems().clear();
    }
}
