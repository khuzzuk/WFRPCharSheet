package pl.khuzzuk.wfrpchar.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.entities.Race;
import pl.khuzzuk.wfrpchar.entities.determinants.DeterminantsType;
import pl.khuzzuk.wfrpchar.gui.EntitiesAdapter;
import pl.khuzzuk.wfrpchar.gui.Numeric;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Component
public class RacesPaneController extends SkillViewController<Race> {
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
    TextField battle;
    @FXML
    TextField speed;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        entityType = Race.class;
        skillChooserMsg = messages.getProperty("race.skills.getAllTypes");
        getAllResponse = messages.getProperty("race.result");
        getEntityTopic = messages.getProperty("race.query.specific");
        removeEntityTopic = messages.getProperty("race.remove");
        saveTopic = messages.getProperty("race.save");
        clearAction = this::clear;
        initItems();
    }

    @Override
    public void loadItem(Race race) {
        clear();
        name.setText(race.getName());
        specialFeatures.setText(race.getSpecialFeatures());
        EntitiesAdapter.sendToListView(skillsView, race.getSkills());
        speed.setText("" + race.getDeterminantValueForType(DeterminantsType.SPEED));
        battle.setText("" + race.getDeterminantValueForType(DeterminantsType.BATTLE));
        charisma.setText("" + race.getDeterminantValueForType(DeterminantsType.CHARISMA));
        will.setText("" + race.getDeterminantValueForType(DeterminantsType.WILL));
        control.setText("" + race.getDeterminantValueForType(DeterminantsType.CONTROL));
        intelligence.setText("" + race.getDeterminantValueForType(DeterminantsType.INTELLIGENCE));
        leaderSkills.setText("" + race.getDeterminantValueForType(DeterminantsType.LEADER_SKILLS));
        dexterity.setText("" + race.getDeterminantValueForType(DeterminantsType.DEXTERITY));
        attacks.setText("" + race.getDeterminantValueForType(DeterminantsType.ATTACKS));
        initiative.setText("" + race.getDeterminantValueForType(DeterminantsType.INITIATIVE));
        health.setText("" + race.getDeterminantValueForType(DeterminantsType.HEALTH));
        endurance.setText("" + race.getDeterminantValueForType(DeterminantsType.DURABILITY));
        strength.setText("" + race.getDeterminantValueForType(DeterminantsType.STRENGTH));
        shooting.setText("" + race.getDeterminantValueForType(DeterminantsType.SHOOTING));
    }

    private void saveRace() {
        List<String> fields = new ArrayList<>();
        fields.add(name.getText());
        fields.add(specialFeatures.getText());
        String determinants = speed.getText() + "," + DeterminantsType.SPEED + "|" +
                battle.getText() + "," + DeterminantsType.BATTLE + "|" +
                charisma.getText() + "," + DeterminantsType.CHARISMA + "|" +
                will.getText() + "," + DeterminantsType.WILL + "|" +
                control.getText() + "," + DeterminantsType.CONTROL + "|" +
                intelligence.getText() + "," + DeterminantsType.INTELLIGENCE + "|" +
                leaderSkills.getText() + "," + DeterminantsType.LEADER_SKILLS + "|" +
                dexterity.getText() + "," + DeterminantsType.DEXTERITY + "|" +
                attacks.getText() + "," + DeterminantsType.ATTACKS + "|" +
                initiative.getText() + "," + DeterminantsType.INITIATIVE + "|" +
                health.getText() + "," + DeterminantsType.HEALTH + "|" +
                endurance.getText() + "," + DeterminantsType.DURABILITY + "|" +
                strength.getText() + "," + DeterminantsType.STRENGTH + "|" +
                shooting.getText() + "," + DeterminantsType.SHOOTING;
        fields.add(determinants);
        fields.add(skillsView.getItems().stream().collect(Collectors.joining("|")));
        saveItem(fields.stream().collect(Collectors.joining(";")));
    }

    @Override
    void clear() {
        super.clear();
        speed.clear();
        battle.clear();
        charisma.clear();
        will.clear();
        control.clear();
        intelligence.clear();
        leaderSkills.clear();
        dexterity.clear();
        attacks.clear();
        initiative.clear();
        health.clear();
        endurance.clear();
        strength.clear();
        shooting.clear();
    }
}
