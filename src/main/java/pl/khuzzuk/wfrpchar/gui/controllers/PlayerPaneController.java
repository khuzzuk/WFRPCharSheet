package pl.khuzzuk.wfrpchar.gui.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.entities.Character;
import pl.khuzzuk.wfrpchar.entities.Named;
import pl.khuzzuk.wfrpchar.entities.Price;
import pl.khuzzuk.wfrpchar.entities.Race;
import pl.khuzzuk.wfrpchar.entities.characters.*;
import pl.khuzzuk.wfrpchar.entities.competency.Profession;
import pl.khuzzuk.wfrpchar.entities.competency.ProfessionClass;
import pl.khuzzuk.wfrpchar.entities.competency.Skill;
import pl.khuzzuk.wfrpchar.entities.competency.Spell;
import pl.khuzzuk.wfrpchar.entities.determinants.Determinant;
import pl.khuzzuk.wfrpchar.entities.determinants.DeterminantsType;
import pl.khuzzuk.wfrpchar.entities.items.*;
import pl.khuzzuk.wfrpchar.entities.items.types.Item;
import pl.khuzzuk.wfrpchar.entities.items.usable.AbstractCommodity;
import pl.khuzzuk.wfrpchar.gui.ComboBoxHandler;
import pl.khuzzuk.wfrpchar.gui.Numeric;
import pl.khuzzuk.wfrpchar.rules.Sex;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

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
    private ListView<Skill> skills;
    @FXML
    private ListView<Profession> professionsHistory;
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
    private ComboBox<ProfessionClass> professionClass;
    @FXML
    private ComboBox<HairColor> hair;
    @FXML
    private ComboBox<EyesColor> eyes;
    @FXML
    @Numeric
    TextField height;
    @FXML
    @Numeric
    TextField age;
    @FXML
    private ComboBox<Character> character;
    @FXML
    private ComboBox<Sex> sex;
    @FXML
    private ComboBox<Race> race;

    private Map<DeterminantsType, GuiDeterminant> guiDeterminants;
    private Profession playersProfession;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeValidation();
        ComboBoxHandler.fillWithEnums(Sex.SET, sex);
        ComboBoxHandler.fillWithEnums(EyesColor.SET, eyes);
        ComboBoxHandler.fillWithEnums(HairColor.SET, hair);
        entityType = Player.class;
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
        super.loadItem(player);
        race.getSelectionModel().select(player.getRace());
        character.getSelectionModel().select(player.getCharacter());
        professionClass.getSelectionModel().select(player.getProfessionClass());
        Optional.ofNullable(player.getCurrentProfession()).ifPresent(p -> {
            playersProfession = p;
            profession.setText(p.getName());
        });

        Optional.ofNullable(player.getCareer()).ifPresent(c -> professionsHistory.getItems().addAll(c));
        Appearance appearance = player.getAppearance();
        if (appearance != null) {
            sex.getSelectionModel().select(appearance.getSex());
            eyes.getSelectionModel().select(appearance.getEyesColor());
            hair.getSelectionModel().select(appearance.getHairColor());
            age.setText("" + appearance.getAge());
            height.setText("" + appearance.getHeight());
            weight.setText("" + appearance.getWeight());
        }
        player.getDeterminants().forEach(d ->
                guiDeterminants.get(d.getType()).setCurrentValue(d.getBaseValue() + ""));
        weapons.getItems().addAll(player.getHandWeapons());
        ammunition.getItems().addAll(player.getAmmunition());
        rangedWeapons.getItems().addAll(player.getRangedWeapons());
        armors.getItems().addAll(player.getArmors());
        skills.getItems().addAll(player.getSkills());
        spells.getItems().addAll(player.getSpells());
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
        return new Player(new Appearance(), new PersonalHistory());
    }

    @FXML
    void clear() {
        age.clear();
        height.clear();
        weight.clear();
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
    void addConverters() {
        super.addConverters();
        addConverter(race::getValue, Player::setRace);
        addConverter(professionClass::getValue, Player::setProfessionClass);
        addConverter(() -> playersProfession, Player::setCurrentProfession);
        addConverter(professionsHistory::getItems, Player::setCareer, HashSet::new);
        addConverter(character::getValue, Player::setCharacter);
        addConverter(sex::getValue, (p, value) -> p.getAppearance().setSex(value));
        addConverter(age::getText, (p, value) -> p.getAppearance().setAge(value), NumberUtils::toInt);
        addConverter(height::getText, (p, value) -> p.getAppearance().setHeight(value), NumberUtils::toInt);
        addConverter(weight::getText, (p, value) -> p.getAppearance().setWeight(value), NumberUtils::toInt);
        addConverter(eyes::getValue, (p, value) -> p.getAppearance().setEyesColor(value));
        addConverter(hair::getValue, (p, value) -> p.getAppearance().setHairColor(value));
        addConverter(this::getDeterminantsFromFields, Player::setDeterminants);
        addConverter(equipment::getItems, Player::setEquipment,
                values -> values.stream().map(commodity -> (Item) commodity).collect(Collectors.toList()));
        addConverter(this::getFightingEquipment, Player::setCommodities);
        addConverter(skills::getItems, Player::setSkills, HashSet::new);
        addConverter(() -> Price.parsePrice(getPriceFromFields()), Player::setMoney);
        addConverter(spells::getItems, Player::setSpells, HashSet::new);
        addConverter(history::getText, (p, value) -> p.getPersonalHistory().setHistory(value));
        addConverter(birthplace::getText, (p, value) -> p.getPersonalHistory().setBirthplace(value));
        addConverter(father::getText, (p, value) -> p.getPersonalHistory().setFather(value));
        addConverter(mother::getText, (p, value) -> p.getPersonalHistory().setMother(value));
        addConverter(siblings::getText, (p, value) -> p.getPersonalHistory().setSiblings(value));
    }

    private List<AbstractCommodity> getFightingEquipment() {
        List<AbstractCommodity> commodities = new ArrayList<>();
        weapons.getItems().stream().map(item -> (AbstractCommodity) item).forEach(commodities::add);
        rangedWeapons.getItems().stream().map(item -> (AbstractCommodity) item).forEach(commodities::add);
        armors.getItems().stream().map(item -> (AbstractCommodity) item).forEach(commodities::add);
        return commodities;
    }

    public void loadCharacters(Collection<Character> characters) {
        ComboBoxHandler.fillWithEnums(characters, character);
    }

    public void loadRaces(Collection<Race> races) {
        ComboBoxHandler.fillWithEnums(races, race);
    }

    public void loadClasses(Collection<ProfessionClass> classes) {
        ComboBoxHandler.fillWithEnums(classes, professionClass);
    }

    public void loadProfessionChoice(Profession professionToLoad) {
        if (shouldAddToList(professionToLoad, professionsHistory)) {
            professionsHistory.getItems().add(professionToLoad);
        }
        profession.setText(professionToLoad.getName());
        playersProfession = professionToLoad;
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

    public void loadSkill(Skill skill) {
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

    private Set<Determinant> getDeterminantsFromFields() {
        Set<Determinant> determinants = new HashSet<>(16);
        guiDeterminants.forEach((t, g) -> determinants.add(g.build(t)));
        return determinants;
    }

    private class GuiDeterminant {
        StringProperty baseValue;

        private GuiDeterminant(StringProperty baseValue) {
            this.baseValue = baseValue;
        }

        private void setCurrentValue(String value) {
            baseValue.setValue(value);
        }

        private Determinant build(DeterminantsType type) {
            Determinant determinant = type.getDeterminant();
            determinant.setBaseValue(NumberUtils.toInt(baseValue.get()));
            return determinant;
        }
    }
}
