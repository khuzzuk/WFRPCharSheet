package pl.khuzzuk.wfrpchar.gui.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.entities.Character;
import pl.khuzzuk.wfrpchar.entities.CsvBuilder;
import pl.khuzzuk.wfrpchar.entities.Race;
import pl.khuzzuk.wfrpchar.entities.characters.Appearance;
import pl.khuzzuk.wfrpchar.entities.characters.EyesColor;
import pl.khuzzuk.wfrpchar.entities.characters.HairColor;
import pl.khuzzuk.wfrpchar.entities.characters.Player;
import pl.khuzzuk.wfrpchar.entities.competency.Profession;
import pl.khuzzuk.wfrpchar.entities.competency.ProfessionClass;
import pl.khuzzuk.wfrpchar.entities.determinants.DeterminantsType;
import pl.khuzzuk.wfrpchar.entities.items.Commodity;
import pl.khuzzuk.wfrpchar.entities.items.HandWeapon;
import pl.khuzzuk.wfrpchar.entities.items.ProtectiveWearings;
import pl.khuzzuk.wfrpchar.entities.items.RangedWeapon;
import pl.khuzzuk.wfrpchar.gui.ComboBoxHandler;
import pl.khuzzuk.wfrpchar.gui.Numeric;
import pl.khuzzuk.wfrpchar.rules.Sex;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import static pl.khuzzuk.wfrpchar.gui.ComboBoxHandler.selectOrEmpty;

@Component
public class PlayerPaneController extends ItemsListedController {
    @FXML
    private ListView<String> professionsHistory;
    @FXML
    private Button profession;
    @FXML
    private Label speedAct;
    @FXML
    private Label battleAct;
    @FXML
    private Label shootingAct;
    @FXML
    private Label strengthAct;
    @FXML
    private Label enduranceAct;
    @FXML
    private Label healthAct;
    @FXML
    private Label initiativeAct;
    @FXML
    private Label attacksAct;
    @FXML
    private Label dexterityAct;
    @FXML
    private Label leaderSkillsAct;
    @FXML
    private Label intelligenceAct;
    @FXML
    private Label controlAct;
    @FXML
    private Label willAct;
    @FXML
    private Label charismaAct;
    @FXML
    private TableView<Commodity> equipment;
    @FXML
    private TableView<ProtectiveWearings> armors;
    @FXML
    private TableView<RangedWeapon> rangedWeapons;
    @FXML
    private TableView<HandWeapon> weapons;
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
        saveAction = this::savePlayer;
        guiPublisher.requestPlayers();
        guiPublisher.requestCharacters();
        initDeterminantsMap();
        initTableViews();
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

    @SuppressWarnings("unchecked")
    private void initTableViews() {
        ((TableColumn<HandWeapon, String>) weapons.getColumns().get(0))
                .setCellValueFactory(param -> new SimpleStringProperty(
                        param.getValue().getName()));
        ((TableColumn<HandWeapon, String>) weapons.getColumns().get(1))
                .setCellValueFactory(param -> new SimpleStringProperty(
                        param.getValue().getInitiativeMod() + ""));
        ((TableColumn<HandWeapon, String>) weapons.getColumns().get(2))
                .setCellValueFactory(param -> new SimpleStringProperty(
                        param.getValue().getBattleMod() + ""));
        ((TableColumn<HandWeapon, String>) weapons.getColumns().get(3))
                .setCellValueFactory(param -> new SimpleStringProperty(
                        param.getValue().getParryMod() + ""));
        ((TableColumn<HandWeapon, String>) weapons.getColumns().get(4))
                .setCellValueFactory(param -> new SimpleStringProperty(
                        param.getValue().getOpponentParryMod() + ""));
        ((TableColumn<HandWeapon, String>) weapons.getColumns().get(5))
                .setCellValueFactory(param -> new SimpleStringProperty(
                        param.getValue().getDices().getName()));

        ((TableColumn<RangedWeapon, String>) rangedWeapons.getColumns().get(0))
                .setCellValueFactory(param -> new SimpleStringProperty(
                        param.getValue().getName()));
        ((TableColumn<RangedWeapon, String>) rangedWeapons.getColumns().get(1))
                .setCellValueFactory(param -> new SimpleStringProperty(
                        param.getValue().getShortRange() + ""));
        ((TableColumn<RangedWeapon, String>) rangedWeapons.getColumns().get(2))
                .setCellValueFactory(param -> new SimpleStringProperty(
                        param.getValue().getEffectiveRange() + ""));
        ((TableColumn<RangedWeapon, String>) rangedWeapons.getColumns().get(3))
                .setCellValueFactory(param -> new SimpleStringProperty(
                        param.getValue().getMaximumRange() + ""));
        ((TableColumn<RangedWeapon, String>) rangedWeapons.getColumns().get(4))
                .setCellValueFactory(param -> new SimpleStringProperty(
                        param.getValue().getReloadTime().getName()));
        ((TableColumn<RangedWeapon, String>) rangedWeapons.getColumns().get(5))
                .setCellValueFactory(param -> new SimpleStringProperty(
                        param.getValue().getStrength() + ""));

        ((TableColumn<ProtectiveWearings, String>) armors.getColumns().get(0))
                .setCellValueFactory(param -> new SimpleStringProperty(
                        param.getValue().getName()));
        ((TableColumn<ProtectiveWearings, String>) armors.getColumns().get(1))
                .setCellValueFactory(param -> new SimpleStringProperty(
                        param.getValue().getStrength() + ""));
        ((TableColumn<ProtectiveWearings, String>) armors.getColumns().get(2))
                .setCellValueFactory(param -> new SimpleStringProperty(
                        param.getValue().getPlacement().getName()));

        ((TableColumn<Commodity, String>) equipment.getColumns().get(0))
                .setCellValueFactory(param -> new SimpleStringProperty(
                        param.getValue().getName()));
        ((TableColumn<Commodity, String>) equipment.getColumns().get(1))
                .setCellValueFactory(param -> new SimpleStringProperty(
                        param.getValue().getWeight() + ""));
    }

    public void loadPlayer(Player player) {
        name.setText(player.getName());
        selectOrEmpty(race, player.getRace());
        selectOrEmpty(character, player.getCharacter());
        selectOrEmpty(professionClass, player.getProfessionClass());
        profession.setText("brak");
        Optional.ofNullable(player.getCurrentProfession()).ifPresent(p ->
                profession.setText(p.getName()));
        professionsHistory.getItems().clear();
        Optional.ofNullable(player.getCareer()).ifPresent(c ->
                professionsHistory.getItems().addAll(c.stream().map(Profession::getName)
                        .collect(Collectors.toList())));
        Appearance appearance = player.getAppearance();
        if (appearance != null) {
            selectOrEmpty(sex, appearance.getSex());
            age.setText("" + appearance.getAge());
            height.setText("" + appearance.getHeight());
            weight.setText("" + appearance.getWeight());
            selectOrEmpty(eyes, appearance.getEyesColor());
            selectOrEmpty(hair, appearance.getHairColor());
            specialFeatures.setText(appearance.getDescription());
        }
        player.getDeterminants().forEach(d ->
                guiDeterminants.get(d.getType()).setCurrentValue(d.getBaseValue() + ""));
    }

    private void savePlayer() {
        CsvBuilder builder = new CsvBuilder(new ArrayList<>());
        builder.add(name.getText())
                .add(ComboBoxHandler.getEmptyIfNotPresent(race))
                .add(ComboBoxHandler.getEmptyIfNotPresent(professionClass))
                .add(profession.getText().equals("brak") ? "" : profession.getText())
                .add(getItemsToCsv(professionsHistory))
                .add(ComboBoxHandler.getEmptyIfNotPresent(character))
                .add(Sex.forName(sex.getSelectionModel().getSelectedItem()).name())
                .add(age.getText())
                .add(height.getText())
                .add(weight.getText())
                .add(EyesColor.forName(eyes.getSelectionModel().getSelectedItem()).name())
                .add(HairColor.forName(hair.getSelectionModel().getSelectedItem()).name())
                .add(specialFeatures.getText())
                .add(getDeterminantsFromFields())
                .add("") //equipment
                .add("") //commodities
                .add("") //skills
                .add(getPriceFromFields())
                .add("") //history
                .add("") //birthplace
                .add("") //father
                .add("") //mother
                .add("none"); //siblings
        guiPublisher.savePlayer(builder.build());
    }

    public void loadCharacters(Collection<Character> characters) {
        ComboBoxHandler.fill(characters, character);
    }

    public void loadRaces(Collection<Race> races) {
        ComboBoxHandler.fill(races, race);
    }

    public void loadClasses(Collection<ProfessionClass> classes) {
        ComboBoxHandler.fill(classes, professionClass);
    }

    public void loadProfessionChoice(String name) {
        if (shouldAddToList(name, professionsHistory)) {
            professionsHistory.getItems().add(name);
        }
        profession.setText(name);
    }

    public void loadWhiteWeaponChoice(HandWeapon handWeapon) {
        weapons.getItems().add(handWeapon);
    }

    public void loadRangedWeaponChoice(RangedWeapon weapon) {
        rangedWeapons.getItems().add(weapon);
    }

    public void loadArmorsChoice(ProtectiveWearings armor) {
        armors.getItems().add(armor);
    }

    public void loadEquipment(Commodity commodity) {
        equipment.getItems().add(commodity);
    }

    private boolean shouldAddToList(String name, ListView<String> listView) {
        return name != null && !name.equals("brak") &&
                !listView.getItems().contains(name);
    }

    @FXML
    private void chooseProfession() {
        guiPublisher.publish(messages.getProperty("player.profession.getAllTypes"));
    }

    @FXML
    private void chooseHandWeapon() {
        guiPublisher.publish(messages.getProperty("player.weapons.white.getAllTypes"));
    }

    @FXML
    private void chooseRangedWeapon() {
        guiPublisher.publish(messages.getProperty("player.weapons.ranged.getAllTypes"));
    }

    @FXML
    private void chooseArmor() {
        guiPublisher.publish(messages.getProperty("player.armors.getAllTypes"));
    }

    @FXML
    private void chooseEquipment() {
        guiPublisher.publish(messages.getProperty("player.equipment.getAllTypes"));
    }

    private String getDeterminantsFromFields() {
        List<String> determinants = new ArrayList<>(16);
        guiDeterminants.forEach((t, g) -> determinants.add(g.toString(t)));
        return determinants.stream().collect(Collectors.joining("|"));
    }

    private String getItemsToCsv(ListView<String> listView) {
        return listView.getItems().stream().collect(Collectors.joining("|"));
    }

    private class GuiDeterminant {
        StringProperty baseValue;

        private GuiDeterminant(StringProperty baseValue) {
            this.baseValue = baseValue;
        }

        private void setCurrentValue(String value) {
            baseValue.setValue(value);
        }

        private String toString(DeterminantsType type) {
            return baseValue.getValue() + "," + type.name();
        }
    }
}
