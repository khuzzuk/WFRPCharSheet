package pl.khuzzuk.wfrpchar.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.entities.Race;
import pl.khuzzuk.wfrpchar.entities.determinants.AbsoluteDeterminant;
import pl.khuzzuk.wfrpchar.entities.determinants.Determinant;
import pl.khuzzuk.wfrpchar.entities.determinants.DeterminantsType;
import pl.khuzzuk.wfrpchar.entities.determinants.PercentageDeterminant;
import pl.khuzzuk.wfrpchar.gui.EntitiesAdapter;
import pl.khuzzuk.wfrpchar.gui.Numeric;

import java.net.URL;
import java.util.*;
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
        initItems();
    }

    @Override
    public void loadItem(Race race) {
        super.loadItem(race);
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

    @Override
    void addConverters() {
        super.addConverters();
        addConverter(this::getDeterminants, Race::setDeterminants);
    }

    private Set<Determinant> getDeterminants() {
        Set<Determinant> determinants = new HashSet<>();
        determinants.add(getAbsoluteDeterminant(DeterminantsType.SPEED, NumberUtils.toInt(speed.getText())));
        determinants.add(getAbsoluteDeterminant(DeterminantsType.ATTACKS, NumberUtils.toInt(attacks.getText())));
        determinants.add(getAbsoluteDeterminant(DeterminantsType.HEALTH, NumberUtils.toInt(health.getText())));
        determinants.add(getAbsoluteDeterminant(DeterminantsType.DURABILITY, NumberUtils.toInt(endurance.getText())));
        determinants.add(getAbsoluteDeterminant(DeterminantsType.STRENGTH, NumberUtils.toInt(strength.getText())));

        determinants.add(getPercentageDeterminant(DeterminantsType.BATTLE, NumberUtils.toInt(battle.getText())));
        determinants.add(getPercentageDeterminant(DeterminantsType.CHARISMA, NumberUtils.toInt(charisma.getText())));
        determinants.add(getPercentageDeterminant(DeterminantsType.WILL, NumberUtils.toInt(will.getText())));
        determinants.add(getPercentageDeterminant(DeterminantsType.CONTROL, NumberUtils.toInt(control.getText())));
        determinants.add(getPercentageDeterminant(DeterminantsType.INTELLIGENCE, NumberUtils.toInt(intelligence.getText())));
        determinants.add(getPercentageDeterminant(DeterminantsType.LEADER_SKILLS, NumberUtils.toInt(leaderSkills.getText())));
        determinants.add(getPercentageDeterminant(DeterminantsType.DEXTERITY, NumberUtils.toInt(dexterity.getText())));
        determinants.add(getPercentageDeterminant(DeterminantsType.INITIATIVE, NumberUtils.toInt(initiative.getText())));
        determinants.add(getPercentageDeterminant(DeterminantsType.SHOOTING, NumberUtils.toInt(shooting.getText())));
        return determinants;
    }

    private Determinant getAbsoluteDeterminant(DeterminantsType type, int value) {
        AbsoluteDeterminant instance = new AbsoluteDeterminant();
        instance.setType(type);
        instance.setBaseValue(value);
        instance.setExtensions(new ArrayList<>());
        return instance;
    }

    private Determinant getPercentageDeterminant(DeterminantsType type, int value) {
        PercentageDeterminant instance = new PercentageDeterminant();
        instance.setType(type);
        instance.setBaseValue(value);
        return instance;
    }

    @Override
    Race supplyNewItem() {
        return new Race();
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
