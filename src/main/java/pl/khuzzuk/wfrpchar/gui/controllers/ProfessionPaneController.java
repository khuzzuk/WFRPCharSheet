package pl.khuzzuk.wfrpchar.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.entities.competency.Profession;
import pl.khuzzuk.wfrpchar.entities.competency.ProfessionClass;
import pl.khuzzuk.wfrpchar.entities.determinants.DeterminantFactory;
import pl.khuzzuk.wfrpchar.entities.items.ResourceType;
import pl.khuzzuk.wfrpchar.gui.ComboBoxHandler;
import pl.khuzzuk.wfrpchar.gui.EntitiesAdapter;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class ProfessionPaneController extends SkillViewController<Profession> {
    @FXML
    private ComboBox<String> professionClass;
    @FXML
    private ListView<String> professionsNextView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        entityType = Profession.class;
        showDeterminantCreatorMsg = messages.getProperty("determinants.creator.show.pr");
        skillChooserMsg = messages.getProperty("professions.skills.getAllTypes");
        getAllResponse = messages.getProperty("professions.result");
        removeEntityTopic = messages.getProperty("professions.remove");
        saveTopic = messages.getProperty("professions.save");
        clearAction = this::clear;
        initItems();
        bus.setReaction(messages.getProperty("professions.class.result"), this::loadProfessionClasses);
        bus.send(messages.getProperty("database.getAll"), messages.getProperty("professions.class.result"), ResourceType.class);
    }

    public void loadProfessionClasses(Collection<ProfessionClass> professionClasses) {
        ComboBoxHandler.fill(new HashSet<>(professionClasses), professionClass);
    }

    @Override
    public void loadItem(Profession profession) {
        super.loadItem(profession);
        name.setText(profession.getName());
        specialFeatures.setText(profession.getSpecialFeatures());
        EntitiesAdapter.sendToListView(determinantsView, profession.getDeterminants());
        EntitiesAdapter.sendToListView(skillsView, profession.getSkills());
        EntitiesAdapter.sendToListView(professionsNextView, profession.getNextProfessions());
        professionClass.getSelectionModel().select(profession.getProfessionClass().getName());
    }

    @Override
    Profession supplyNewItem() {
        return new Profession();
    }

    @FXML
    private void showProfessionChooser() {
        bus.send(messages.getProperty("professions.next.getAllTypes"));
    }

    public void addProfession(String profession) {
        professionsNextView.getItems().add(profession);
    }

    @Override
    void saveAction() {
        List<String> fields = new ArrayList<>();
        fields.add(name.getText());
        fields.add(specialFeatures.getText());
        fields.add(skillsView.getItems().stream().collect(Collectors.joining("|")));
        fields.add(professionsNextView.getItems().stream().collect(Collectors.joining("|")));
        fields.add(getDeterminantsFromList());
        fields.add(ComboBoxHandler.getEmptyIfNotPresent(professionClass));
        saveItem(fields.stream().collect(Collectors.joining(";")));
    }

    private String getDeterminantsFromList() {
        return determinantsView.getItems().stream()
                .map(DeterminantFactory::getProfessionDeterminant)
                .collect(Collectors.joining("|"));
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
