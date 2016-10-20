package pl.khuzzuk.wfrpchar.gui.controllers;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import pl.khuzzuk.wfrpchar.entities.characters.EyesColor;
import pl.khuzzuk.wfrpchar.entities.characters.HairColor;
import pl.khuzzuk.wfrpchar.entities.determinants.DeterminantsType;
import pl.khuzzuk.wfrpchar.gui.ComboBoxHandler;
import pl.khuzzuk.wfrpchar.gui.Numeric;
import pl.khuzzuk.wfrpchar.rules.Sex;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class PlayerPaneController extends ItemsListedController {
    @FXML
    private TableView equipment;
    @FXML
    private TableView armors;
    @FXML
    private TableView rangedWeapons;
    @FXML
    private TableView weapons;
    @FXML
    @Numeric
    TextField speed;
    @FXML
    @Numeric
    TextField battle;
    @FXML
    @Numeric
    TextField shooting;
    @FXML
    @Numeric
    TextField strength;
    @FXML
    @Numeric
    TextField endurance;
    @FXML
    @Numeric
    TextField health;
    @FXML
    @Numeric
    TextField initiative;
    @FXML
    @Numeric
    TextField attacks;
    @FXML
    @Numeric
    TextField dexterity;
    @FXML
    @Numeric
    TextField leaderSkills;
    @FXML
    @Numeric
    TextField intelligence;
    @FXML
    @Numeric
    TextField control;
    @FXML
    @Numeric
    TextField will;
    @FXML
    @Numeric
    TextField charisma;
    @FXML
    private ComboBox<String> professionClass;
    @FXML
    private ComboBox<String> hair;
    @FXML
    private ComboBox<String> eyes;
    @FXML
    @Numeric
    TextField height;
    @FXML
    @Numeric
    TextField age;
    @FXML
    private ComboBox<String> character;
    @FXML
    private ComboBox<String> sex;
    @FXML
    private ComboBox<String> race;

    private Map<DeterminantsType, GuiDeterminant> guiDeterminants;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ComboBoxHandler.fill(Sex.SET, sex);
        ComboBoxHandler.fill(EyesColor.SET, eyes);
        ComboBoxHandler.fill(HairColor.SET, hair);
        getAction = guiPublisher::requestPlayer;
        removeAction = guiPublisher::removePlayer;
        guiPublisher.requestPlayers();
    }

    private void initDeterminantsMap() {
        guiDeterminants = new HashMap<>();
        guiDeterminants.put(DeterminantsType.SPEED, new GuiDeterminant(speed.textProperty()));
        guiDeterminants.put(DeterminantsType.BATTLE, new GuiDeterminant(battle.textProperty()));
        guiDeterminants.put(DeterminantsType.SHOOTING, new GuiDeterminant(shooting.textProperty()));
        guiDeterminants.put(DeterminantsType.STRENGTH, new GuiDeterminant(strength.textProperty()));
        guiDeterminants.put(DeterminantsType.DURABILITY, new GuiDeterminant(endurance.textProperty()));
        guiDeterminants.put(DeterminantsType.HEALTH, new GuiDeterminant(health.textProperty()));
        guiDeterminants.put(DeterminantsType.INITIATIVE, new GuiDeterminant(initiative.textProperty()));
        guiDeterminants.put(DeterminantsType.ATTACKS, new GuiDeterminant(attacks.textProperty()));
        guiDeterminants.put(DeterminantsType.DEXTERITY, new GuiDeterminant(dexterity.textProperty()));
        guiDeterminants.put(DeterminantsType.LEADER_SKILLS, new GuiDeterminant(leaderSkills.textProperty()));
        guiDeterminants.put(DeterminantsType.INTELLIGENCE, new GuiDeterminant(intelligence.textProperty()));
        guiDeterminants.put(DeterminantsType.CONTROL, new GuiDeterminant(control.textProperty()));
        guiDeterminants.put(DeterminantsType.WILL, new GuiDeterminant(will.textProperty()));
        guiDeterminants.put(DeterminantsType.CHARISMA, new GuiDeterminant(charisma.textProperty()));
    }

    private void savePlayer() {
        List<String> fields = new ArrayList<>(32);
        fields.add(name.getText());
        fields.add(""); //profession class
        fields.add(""); //profession
        fields.add(""); //career
        fields.add(""); //character
        fields.add(Sex.forName(sex.getSelectionModel().getSelectedItem()).name());
        fields.add(age.getText());
        fields.add(height.getText());
        fields.add(weight.getText());
        fields.add(EyesColor.forName(eyes.getSelectionModel().getSelectedItem()).name());
        fields.add(HairColor.forName(hair.getSelectionModel().getSelectedItem()).name());
        fields.add(specialFeatures.getText());
        fields.add(getDeterminantsFromFields());
        fields.add(""); //equipment
        fields.add(""); //commodities
        fields.add(""); //skills
        fields.add(getPriceFromFields());
        fields.add(""); //history
        fields.add(""); //birthplace
        fields.add(""); //father
        fields.add(""); //mother
        fields.add(""); //siblings
    }

    private String getDeterminantsFromFields() {
        List<String> determinants = new ArrayList<>(16);
        guiDeterminants.forEach((t, g) -> determinants.add(g.toString(t)));
        return determinants.stream().collect(Collectors.joining("|"));
    }

    private class GuiDeterminant {
        StringProperty baseValue;

        private GuiDeterminant(StringProperty baseValue) {
            this.baseValue = baseValue;
        }

        private String toString(DeterminantsType type) {
            return baseValue.getValue() + "," + type.name();
        }
    }
}
