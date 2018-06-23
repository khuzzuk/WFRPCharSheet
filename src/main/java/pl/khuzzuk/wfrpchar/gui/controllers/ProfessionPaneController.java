package pl.khuzzuk.wfrpchar.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.entities.competency.Profession;
import pl.khuzzuk.wfrpchar.entities.competency.ProfessionClass;
import pl.khuzzuk.wfrpchar.gui.ComboBoxHandler;
import pl.khuzzuk.wfrpchar.gui.EntitiesAdapter;

import java.net.URL;
import java.util.Collection;
import java.util.HashSet;
import java.util.ResourceBundle;

@Component
public class ProfessionPaneController extends SkillViewController<Profession> {
    @FXML
    private ComboBox<ProfessionClass> professionClass;
    @FXML
    private ListView<Profession> professionsNextView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        entityType = Profession.class;
        showDeterminantCreatorMsg = messages.getProperty("determinants.creator.show.pr");
        skillChooserMsg = messages.getProperty("professions.skills.getAllTypes");
        initItems();
        bus.<Collection<ProfessionClass>>setReaction(messages.getProperty("professions.class.result"),
                professionClasses -> ComboBoxHandler.fillWithEnums(professionClasses, professionClass));
        bus.send(messages.getProperty("database.getAll"), messages.getProperty("professions.class.result"), ProfessionClass.class);
    }

    @Override
    public void loadItem(Profession profession) {
        super.loadItem(profession);
        professionsNextView.getItems().addAll(profession.getNextProfessions());
        professionClass.getSelectionModel().select(profession.getProfessionClass());
    }

    @Override
    Profession supplyNewItem() {
        return new Profession();
    }

    @Override
    void addConverters() {
        super.addConverters();
        addConverter(professionClass::getValue, Profession::setProfessionClass);
        addConverter(professionsNextView::getItems, Profession::setNextProfessions, HashSet::new);
    }

    @FXML
    private void showProfessionChooser() {
        bus.send(messages.getProperty("professions.next.getAllTypes"));
    }

    public void addProfession(Profession profession) {
        professionsNextView.getItems().add(profession);
    }

    @FXML
    private void removeProfession() {
        EntitiesAdapter.removeSelected(professionsNextView);
    }

    @Override
    void clear() {
        super.clear();
        professionClass.getSelectionModel().clearSelection();
        professionsNextView.getItems().clear();
    }
}
