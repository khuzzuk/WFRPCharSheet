package pl.khuzzuk.wfrpchar.gui.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.apache.commons.collections4.collection.CompositeCollection;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.entities.Character;
import pl.khuzzuk.wfrpchar.entities.CsvBuilder;
import pl.khuzzuk.wfrpchar.entities.Named;
import pl.khuzzuk.wfrpchar.entities.Race;
import pl.khuzzuk.wfrpchar.entities.characters.*;
import pl.khuzzuk.wfrpchar.entities.competency.Profession;
import pl.khuzzuk.wfrpchar.entities.competency.ProfessionClass;
import pl.khuzzuk.wfrpchar.entities.competency.Spell;
import pl.khuzzuk.wfrpchar.entities.determinants.DeterminantsType;
import pl.khuzzuk.wfrpchar.entities.items.*;
import pl.khuzzuk.wfrpchar.gui.ComboBoxHandler;
import pl.khuzzuk.wfrpchar.gui.ListViewHandler;
import pl.khuzzuk.wfrpchar.gui.Numeric;
import pl.khuzzuk.wfrpchar.rules.Sex;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import static pl.khuzzuk.wfrpchar.gui.ComboBoxHandler.selectOrEmpty;
import static pl.khuzzuk.wfrpchar.gui.ListViewHandler.shouldAddToList;

@Component
public class PlayerPaneController extends ItemsListedController<Player> {
    @FXML
    private TableView<Spell> spells;
    @FXML
    private TextArea history;
    @FXML
    private TextField birthplace;
    @FXML
    private TextField father;
    @FXML
    private TextField mother;
    @FXML
    private TextField siblings;
    @FXML
    private TableView<PlayersAmmunition> ammunition;
    @FXML
    private ListView<String> skills;
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
        initializeValidation();
        ComboBoxHandler.fill(Sex.SET, sex);
        ComboBoxHandler.fill(EyesColor.SET, eyes);
        ComboBoxHandler.fill(HairColor.SET, hair);
        entityType = Player.class;
        getAllResponse = messages.getProperty("player.result");
        removeEntityTopic = messages.getProperty("player.remove");
        saveTopic = messages.getProperty("player.save");
        clearAction = this::clear;
        initItems();
        bus.setReaction(messages.getProperty("professions.class.result"), this::loadClasses);
        bus.send(messages.getProperty("database.getAll"), messages.getProperty("professions.class.result"), ResourceType.class);
        bus.setReaction(messages.getProperty("race.result"), this::loadClasses);
        bus.send(messages.getProperty("database.getAll"), messages.getProperty("professions.class.result"), Race.class);
        bus.setReaction(messages.getProperty("character.result"), this::loadCharacters);
        bus.send(messages.getProperty("database.getAll"), messages.getProperty("character.result"), Character.class);
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

        ((TableColumn<PlayersAmmunition, String>) ammunition.getColumns().get(0))
                .setCellValueFactory(param -> new SimpleStringProperty(
                        param.getValue().getName()));
        ((TableColumn<PlayersAmmunition, String>) ammunition.getColumns().get(1))
                .setCellValueFactory(param -> new SimpleStringProperty(
                        param.getValue().getStrength() + ""));
        ((TableColumn<PlayersAmmunition, String>) ammunition.getColumns().get(2))
                .setCellValueFactory(param -> new SimpleStringProperty(
                        param.getValue().getCount() + ""));

        ((TableColumn<Spell, String>) spells.getColumns().get(0))
                .setCellValueFactory(param -> new SimpleStringProperty(
                        param.getValue().getName()));
        ((TableColumn<Spell, String>) spells.getColumns().get(1))
                .setCellValueFactory(param -> new SimpleStringProperty(
                        param.getValue().getCastTime().getName()));
        ((TableColumn<Spell, String>) spells.getColumns().get(2))
                .setCellValueFactory(param -> new SimpleStringProperty(
                        param.getValue().getMagicCost() + ""));
        ((TableColumn<Spell, String>) spells.getColumns().get(3))
                .setCellValueFactory(param -> new SimpleStringProperty(
                        param.getValue().getSchool().getName()));
        ((TableColumn<Spell, String>) spells.getColumns().get(4))
                .setCellValueFactory(param -> new SimpleStringProperty(
                        param.getValue().getLevel() + ""));
        ((TableColumn<Spell, String>) spells.getColumns().get(5))
                .setCellValueFactory(param -> new SimpleStringProperty(
                        param.getValue().getIngredients().stream()
                                .map(Named::getName).collect(Collectors.joining(", "))));
        ((TableColumn<Spell, String>) spells.getColumns().get(6))
                .setCellValueFactory(param -> new SimpleStringProperty(
                        param.getValue().getDescription()));
    }

    @Override
    public void loadItem(Player player) {
        clear();
        super.loadItem(player);
        name.setText(player.getName());
        selectOrEmpty(race, player.getRace());
        selectOrEmpty(character, player.getCharacter());
        selectOrEmpty(professionClass, player.getProfessionClass());
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
        weapons.getItems().addAll(player.getHandWeapons());
        ammunition.getItems().addAll(player.getAmmunition());
        rangedWeapons.getItems().addAll(player.getRangedWeapons());
        armors.getItems().addAll(player.getArmors());
        skills.getItems().addAll(player.getSkills().stream().map(Named::getName).collect(Collectors.toList()));
        spells.getItems().addAll(player.getSpells());
        gold.setText(player.getMoney().getGold() + "");
        silver.setText(player.getMoney().getSilver() + "");
        lead.setText(player.getMoney().getLead() + "");
        PersonalHistory playerHistory = player.getPersonalHistory();
        if (playerHistory != null) {
            history.setText(playerHistory.getHistory());
            birthplace.setText(playerHistory.getBirthplace());
            father.setText(playerHistory.getFather());
            mother.setText(playerHistory.getMother());
            siblings.setText(playerHistory.getSiblings());
        }
    }

    @Override
    Player supplyNewItem() {
        return new Player();
    }

    @FXML
    void clear() {
        name.clear();
        age.clear();
        height.clear();
        weight.clear();
        specialFeatures.clear();
        gold.clear();
        silver.clear();
        lead.clear();
        history.clear();
        birthplace.clear();
        father.clear();
        mother.clear();
        siblings.clear();
        race.getSelectionModel().clearSelection();
        character.getSelectionModel().clearSelection();
        professionClass.getSelectionModel().clearSelection();
        sex.getSelectionModel().clearSelection();
        eyes.getSelectionModel().clearSelection();
        hair.getSelectionModel().clearSelection();
        profession.setText("brak");
        professionsHistory.getItems().clear();
        equipment.getItems().clear();
        weapons.getItems().clear();
        rangedWeapons.getItems().clear();
        ammunition.getItems().clear();
        armors.getItems().clear();
        skills.getItems().clear();
        spells.getItems().clear();
        guiDeterminants.forEach((type, guiDeterminant) -> guiDeterminant.setCurrentValue(0 + ""));
    }

    @Override
    void saveAction() {
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
                .add(equipment.getItems().stream().map(Named::getName)
                        .collect(Collectors.joining("|")))
                .add(getFightingEquipment().stream().map(Named::getName)
                        .collect(Collectors.joining("|")))
                .add(ListViewHandler.getFromList(skills))
                .add(getPriceFromFields())
                .add(spells.getItems().stream().map(Named::getName).collect(Collectors.joining("|")))
                .add(history.getText())
                .add(birthplace.getText())
                .add(father.getText())
                .add(mother.getText())
                .add(siblings.getText());
        saveItem(builder.build());
    }

    @SuppressWarnings("unchecked")
    private Collection<Named<String>> getFightingEquipment() {
        return new CompositeCollection(
                weapons.getItems(), rangedWeapons.getItems(), armors.getItems());
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

    public void loadSkill(String skill) {
        if (shouldAddToList(skill, skills)) {
            skills.getItems().add(skill);
        }
    }

    public void loadSpell(Spell spell) {
        if (shouldAddToList(spell, spells)) {
            spells.getItems().add(spell);
        }
    }

    @FXML
    private void chooseProfession() {
        bus.send(messages.getProperty("player.profession.getAllTypes"));
    }

    @FXML
    private void chooseHandWeapon() {
        bus.send(messages.getProperty("player.weapons.white.getAllTypes"));
    }

    @FXML
    private void chooseRangedWeapon() {
        bus.send(messages.getProperty("player.weapons.ranged.getAllTypes"));
    }

    @FXML
    private void chooseArmor() {
        bus.send(messages.getProperty("player.armors.getAllTypes"));
    }

    @FXML
    private void chooseEquipment() {
        bus.send(messages.getProperty("player.equipment.getAllTypes"));
    }

    @FXML
    private void chooseSkill() {
        bus.send(messages.getProperty("player.skills.getAllTypes"));
    }

    @FXML
    private void chooseSpell() {
        bus.send(messages.getProperty("player.spells.getAllTypes"));
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
