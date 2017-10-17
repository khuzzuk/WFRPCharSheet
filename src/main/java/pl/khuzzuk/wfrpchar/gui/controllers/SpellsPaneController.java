package pl.khuzzuk.wfrpchar.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.entities.CsvBuilder;
import pl.khuzzuk.wfrpchar.entities.LoadingTimes;
import pl.khuzzuk.wfrpchar.entities.Named;
import pl.khuzzuk.wfrpchar.entities.competency.Spell;
import pl.khuzzuk.wfrpchar.gui.ComboBoxHandler;
import pl.khuzzuk.wfrpchar.gui.ListViewHandler;
import pl.khuzzuk.wfrpchar.gui.Numeric;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

//TODO extend something else since it has too much fields from ItemsListedController
@Component
public class SpellsPaneController extends EntitiesListedController {
    @FXML
    private ListView<String> ingredients;
    @FXML
    @Numeric
    TextField level;
    @FXML
    private Button school;
    @FXML
    @Numeric
    TextField magicCost;
    @FXML
    private ComboBox<String> loadingTimes;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeValidation();
        ComboBoxHandler.fill(LoadingTimes.SET, loadingTimes);
        entityType = Spell.class;
        getAllResponse = messages.getProperty("magic.spells.result");
        getEntityTopic = messages.getProperty("magic.spells.query.specific");
        removeEntityTopic = messages.getProperty("magic.spells.remove");
        saveTopic = messages.getProperty("magic.spells.save");
        saveAction = this::saveSpell;
        clearAction = this::clear;
        initItems();
    }

    public void clear() {
        name.clear();
        loadingTimes.getSelectionModel().clearSelection();
        magicCost.clear();
        school.setText("brak");
        level.clear();
        ingredients.getItems().clear();
        specialFeatures.clear();
    }

    public void loadSpell(Spell spell) {
        clear();
        name.setText(spell.getName());
        ComboBoxHandler.selectOrEmpty(loadingTimes, spell.getCastTime());
        magicCost.setText(spell.getMagicCost() + "");
        school.setText(spell.getSchool().getName());
        level.setText(spell.getLevel() + "");
        ingredients.getItems().addAll(spell.getIngredients().stream().map(Named::getName)
                .collect(Collectors.toList()));
        specialFeatures.setText(spell.getDescription());
    }

    private void saveSpell() {
        saveItem(new CsvBuilder(new ArrayList<>())
                        .add(name.getText())
                        .add(LoadingTimes.forName(Optional.ofNullable(
                                loadingTimes.getSelectionModel().getSelectedItem()).orElse("")).name())
                        .add(magicCost.getText())
                        .add(school.getText())
                        .add(level.getText())
                        .add(ListViewHandler.getFromList(ingredients))
                        .add(specialFeatures.getText()).build());
    }

    public void setSchool(String school) {
        this.school.setText(school);
    }

    public void addIngredient(String item) {
        if (ListViewHandler.shouldAddToList(item, ingredients)) {
            ingredients.getItems().add(item);
        }
    }

    @FXML
    private void chooseSchool() {
        bus.send(messages.getProperty("magic.spells.school.getAllTypes"));
    }

    @FXML
    private void chooseIngredient() {
        bus.send(messages.getProperty("magic.spells.ingredients.getAllTypes"));
    }
}
