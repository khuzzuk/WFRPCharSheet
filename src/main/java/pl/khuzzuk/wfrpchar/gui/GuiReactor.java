package pl.khuzzuk.wfrpchar.gui;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import pl.khuzzuk.messaging.Bus;
import pl.khuzzuk.wfrpchar.gui.controllers.*;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import java.util.Properties;

@Component
@PropertySource("classpath:messages.properties")
public class GuiReactor {
    @Inject
    private Bus bus;
    //TODO reduce this injects
    @Inject
    @MainWindowBean
    private MainWindowController controller;
    @Inject
    private ItemTypesPaneController itemTypesPaneController;
    @Inject
    private ArmorTypesPaneController armorTypesPaneController;
    @Inject
    private RangedWeaponTypePaneController rangedWeaponTypePaneController;
    @Inject
    private WhiteWeaponTypePaneController whiteWeaponTypePaneController;
    @Inject
    private AmmunitionTypesPaneController ammunitionTypesPaneController;
    @Inject
    private ResourceTypesPaneController resourceTypesPaneController;
    @Inject
    private HandWeaponsPaneController handWeaponsPaneController;
    @Inject
    private RangeWeaponsPaneController rangeWeaponsPaneController;
    @Inject
    private ArmorPaneController armorPaneController;
    @Inject
    private AmmunitionPaneController ammunitionPaneController;
    @Inject
    private SkillsPaneController skillsPaneController;
    @Inject
    private ProfessionClassPaneController professionClassPaneController;
    @Inject
    private ProfessionPaneController professionPaneController;
    @Inject
    private RacesPaneController racesPaneController;
    @Inject
    private MagicSchoolsPaneController magicSchoolsPaneController;
    @Inject
    private SpellsPaneController spellsPaneController;
    @Inject
    private PlayerPaneController playerPaneController;
    @Inject
    @Named("hwDeterminantsCreatorController")
    private DeterminantCreatorController hwDeterminantCreatorController;
    @Inject
    @Named("rwDeterminantsCreatorController")
    private DeterminantCreatorController rwDeterminantCreatorController;
    @Inject
    @Named("arDeterminantsCreatorController")
    private DeterminantCreatorController arDeterminantCreatorController;
    @Inject
    @Named("amDeterminantsCreatorController")
    private DeterminantCreatorController amDeterminantCreatorController;
    @Inject
    @Named("skDeterminantsCreatorController")
    private DeterminantCreatorController skDeterminantCreatorController;
    @Inject
    @Named("prDeterminantsCreatorController")
    private DeterminantCreatorController prDeterminantCreatorController;
    @Inject
    @Named("messages")
    private Properties messages;
    @Value("${miscItemTypes.result}")
    @NotNull
    private String miscItemResult;
    @Value("${miscItemTypes.result.specific}")
    @NotNull
    private String namedMiscItemResult;
    @Value("${whiteWeapons.result}")
    @NotNull
    private String whiteWeaponsMsg;
    @Value("${whiteWeapons.result.specific}")
    @NotNull
    private String namedWhiteWeaponsMsg;
    @Value("${rangedWeapons.result}")
    @NotNull
    private String rangedWeaponMsg;
    @Value("${rangedWeapons.result.specific}")
    @NotNull
    private String namedRangedWeaponMsg;
    @Value("${armorTypes.result}")
    @NotNull
    private String listOfArmorTypesResult;
    @Value("${armorTypes.result.specific}")
    private String namedArmorTypeMsg;
    @Value("${resource.type.result}")
    private String resourceTypeResult;
    @Value("${resource.type.result.specific}")
    private String resourceTypeResultSpecific;

    @PostConstruct
    private void setConsumers() {
        //DeterminantsCreator start action
        bus.setReaction(messages.getProperty("determinants.creator.show.hw"), hwDeterminantCreatorController::show);
        bus.setReaction(messages.getProperty("determinants.creator.show.rw"), rwDeterminantCreatorController::show);
        bus.setReaction(messages.getProperty("determinants.creator.show.ar"), arDeterminantCreatorController::show);
        bus.setReaction(messages.getProperty("determinants.creator.show.am"), amDeterminantCreatorController::show);
        bus.setReaction(messages.getProperty("determinants.creator.show.sk"), skDeterminantCreatorController::show);
        bus.setReaction(messages.getProperty("determinants.creator.show.pr"), prDeterminantCreatorController::show);

        //DeterminantsCreator end actions
        bus.setReaction(messages.getProperty("determinants.creator.add.hw"), handWeaponsPaneController::addDeterminant);
        bus.setReaction(messages.getProperty("determinants.creator.add.rw"), rangeWeaponsPaneController::addDeterminant);
        bus.setReaction(messages.getProperty("determinants.creator.add.ar"), armorPaneController::addDeterminant);
        bus.setReaction(messages.getProperty("determinants.creator.add.am"), ammunitionPaneController::addDeterminant);
        bus.setReaction(messages.getProperty("determinants.creator.add.sk"), skillsPaneController::addDeterminant);
        bus.setReaction(messages.getProperty("determinants.creator.add.pr"), professionPaneController::addDeterminant);

        //Base type choosers
        bus.setGuiReaction(messages.getProperty("weapons.hand.baseType.choice"), handWeaponsPaneController::setBaseType);
        bus.setGuiReaction(messages.getProperty("weapons.ranged.baseType.choice"), rangeWeaponsPaneController::setBaseType);
        bus.setGuiReaction(messages.getProperty("armor.baseType.choice"), armorPaneController::setBaseType);
        bus.setGuiReaction(messages.getProperty("professions.class.skills.choice"), professionClassPaneController::addSkill);
        bus.setGuiReaction(messages.getProperty("professions.skills.choice"), professionPaneController::addSkill);
        bus.setGuiReaction(messages.getProperty("professions.next.choice"), professionPaneController::addProfession);
        bus.setGuiReaction(messages.getProperty("ammunition.baseType.choice"), ammunitionPaneController::setBaseType);
        bus.setGuiReaction(messages.getProperty("race.skills.choice"), racesPaneController::addSkill);
        bus.setGuiReaction(messages.getProperty("magic.spells.school.choice"), spellsPaneController::setSchool);
        bus.setGuiReaction(messages.getProperty("magic.spells.ingredients.choice"), spellsPaneController::addIngredient);
        bus.setGuiReaction(messages.getProperty("player.profession.choice"), playerPaneController::loadProfessionChoice);
        bus.setGuiReaction(messages.getProperty("player.weapons.white.choice"), playerPaneController::loadWhiteWeaponChoice);
        bus.setGuiReaction(messages.getProperty("player.weapons.ranged.choice"), playerPaneController::loadRangedWeaponChoice);
        bus.setGuiReaction(messages.getProperty("player.armors.choice"), playerPaneController::loadArmorsChoice);
        bus.setGuiReaction(messages.getProperty("player.equipment.choice"), playerPaneController::loadEquipment);
        bus.setGuiReaction(messages.getProperty("player.skills.choice"), playerPaneController::loadSkill);
        bus.setGuiReaction(messages.getProperty("player.spells.choice"), playerPaneController::loadSpell);
    }
}
