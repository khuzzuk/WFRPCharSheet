package pl.khuzzuk.wfrpchar.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.entities.LoadingTimes;
import pl.khuzzuk.wfrpchar.entities.competency.MagicSchool;
import pl.khuzzuk.wfrpchar.entities.competency.Spell;
import pl.khuzzuk.wfrpchar.entities.items.types.MiscItem;
import pl.khuzzuk.wfrpchar.gui.ComboBoxHandler;
import pl.khuzzuk.wfrpchar.gui.ListViewHandler;
import pl.khuzzuk.wfrpchar.gui.Numeric;

import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;

//TODO extend something else since it has too much fields from ItemsListedController
@Component
public class SpellsPaneController extends EntitiesListedController<Spell> {
    @FXML
    private ListView<MiscItem> ingredients;
    @FXML
    @Numeric
    TextField level;
    @FXML
    private Button school;
    @FXML
    @Numeric
    TextField magicCost;
    @FXML
    private ComboBox<LoadingTimes> loadingTimes;
    private MagicSchool magicSchool;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeValidation();
        ComboBoxHandler.fillWithEnums(LoadingTimes.SET, loadingTimes);
        entityType = Spell.class;
        initItems();
    }

    public void clear() {
        loadingTimes.getSelectionModel().clearSelection();
        magicCost.clear();
        school.setText("brak");
        level.clear();
        ingredients.getItems().clear();
    }

    @Override
    public void loadItem(Spell spell) {
        super.loadItem(spell);
        loadingTimes.getSelectionModel().select(spell.getCastTime());
        magicCost.setText(spell.getMagicCost() + "");
        magicSchool = spell.getSchool();
        school.setText(spell.getSchool().getName());
        level.setText(spell.getLevel() + "");
        ingredients.getItems().addAll(spell.getIngredients());
    }

    @Override
    void addConverters() {
        super.addConverters();
        addConverter(loadingTimes::getValue, Spell::setCastTime);
        addConverter(magicCost::getText, Spell::setMagicCost, NumberUtils::toInt);
        addConverter(() -> magicSchool, Spell::setSchool);
        addConverter(level::getText, Spell::setLevel, NumberUtils::toInt);
        addConverter(ingredients::getItems, Spell::setIngredients, HashSet::new);
    }

    @Override
    Spell supplyNewItem() {
        return new Spell();
    }

    public void setSchool(String school) {
        this.school.setText(school);
    }

    public void addIngredient(MiscItem item) {
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
