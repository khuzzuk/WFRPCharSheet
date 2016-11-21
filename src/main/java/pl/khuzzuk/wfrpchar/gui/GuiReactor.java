package pl.khuzzuk.wfrpchar.gui;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.entities.Race;
import pl.khuzzuk.wfrpchar.entities.competency.ProfessionClass;
import pl.khuzzuk.wfrpchar.entities.items.ResourceType;
import pl.khuzzuk.wfrpchar.gui.controllers.*;
import pl.khuzzuk.wfrpchar.messaging.Message;
import pl.khuzzuk.wfrpchar.messaging.ReactorBean;
import pl.khuzzuk.wfrpchar.messaging.subscribers.MultiContentSubscriber;
import pl.khuzzuk.wfrpchar.messaging.subscribers.MultiSubscriber;
import pl.khuzzuk.wfrpchar.messaging.subscribers.Subscribers;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Properties;

@Component
@ReactorBean
@PropertySource("classpath:messages.properties")
public class GuiReactor {
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
    @MainWindowBean
    @Subscribers
    private MultiSubscriber<Message> communicateSubscriber;
    @Inject
    @Subscribers
    @MainWindowBean
    private MultiContentSubscriber guiContentSubscriber;
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

    private void sendResourcesTypes(Collection<ResourceType> resources) {
        resourceTypesPaneController.loadAllResources(resources);
        handWeaponsPaneController.fillResourceBoxes(resources);
        rangeWeaponsPaneController.fillResourceBoxes(resources);
        ammunitionPaneController.fillResourceBoxes(resources);
        armorPaneController.fillResourceBoxes(resources);
    }

    private void sendProfessionClass(Collection<ProfessionClass> professionClasses) {
        professionClassPaneController.loadAll(professionClasses);
        professionPaneController.loadProfessionClasses(professionClasses);
        playerPaneController.loadClasses(professionClasses);
    }

    private void sendRaces(Collection<Race> races) {
        racesPaneController.loadAll(races);
        playerPaneController.loadRaces(races);
    }

    @PostConstruct
    private void setConsumers() {
        guiContentSubscriber.subscribe(miscItemResult, itemTypesPaneController::loadAll);
        guiContentSubscriber.subscribe(namedMiscItemResult, itemTypesPaneController::loadMiscItemToEditor);
        guiContentSubscriber.subscribe(whiteWeaponsMsg, whiteWeaponTypePaneController::loadAll);
        guiContentSubscriber.subscribe(namedWhiteWeaponsMsg, whiteWeaponTypePaneController::loadWhiteWeaponToEditor);
        guiContentSubscriber.subscribe(rangedWeaponMsg, rangedWeaponTypePaneController::loadAll);
        guiContentSubscriber.subscribe(namedRangedWeaponMsg, rangedWeaponTypePaneController::loadRangedWeaponToEditor);
        guiContentSubscriber.subscribe(listOfArmorTypesResult, armorTypesPaneController::loadAll);
        guiContentSubscriber.subscribe(messages.getProperty("ammo.type.result"), ammunitionTypesPaneController::loadAll);
        guiContentSubscriber.subscribe(namedArmorTypeMsg, armorTypesPaneController::loadArmorTypeToEditor);
        guiContentSubscriber.subscribe(messages.getProperty("ammo.type.result.specific"), ammunitionTypesPaneController::loadToEditor);
        guiContentSubscriber.subscribe(messages.getProperty("resource.type.result"), this::sendResourcesTypes);
        guiContentSubscriber.subscribe(resourceTypeResultSpecific, resourceTypesPaneController::loadToEditor);

        //DeterminantsCreator start action
        communicateSubscriber.subscribe(messages.getProperty("determinants.creator.show.hw"), hwDeterminantCreatorController::show);
        communicateSubscriber.subscribe(messages.getProperty("determinants.creator.show.rw"), rwDeterminantCreatorController::show);
        communicateSubscriber.subscribe(messages.getProperty("determinants.creator.show.ar"), arDeterminantCreatorController::show);
        communicateSubscriber.subscribe(messages.getProperty("determinants.creator.show.am"), amDeterminantCreatorController::show);
        communicateSubscriber.subscribe(messages.getProperty("determinants.creator.show.sk"), skDeterminantCreatorController::show);
        communicateSubscriber.subscribe(messages.getProperty("determinants.creator.show.pr"), prDeterminantCreatorController::show);

        //DeterminantsCreator end actions
        guiContentSubscriber.subscribe(messages.getProperty("determinants.creator.add.hw"), handWeaponsPaneController::addDeterminant);
        guiContentSubscriber.subscribe(messages.getProperty("determinants.creator.add.rw"), rangeWeaponsPaneController::addDeterminant);
        guiContentSubscriber.subscribe(messages.getProperty("determinants.creator.add.ar"), armorPaneController::addDeterminant);
        guiContentSubscriber.subscribe(messages.getProperty("determinants.creator.add.am"), ammunitionPaneController::addDeterminant);
        guiContentSubscriber.subscribe(messages.getProperty("determinants.creator.add.sk"), skillsPaneController::addDeterminant);
        guiContentSubscriber.subscribe(messages.getProperty("determinants.creator.add.pr"), professionPaneController::addDeterminant);

        //Base type choosers
        guiContentSubscriber.subscribe(messages.getProperty("weapons.hand.baseType.choice"), handWeaponsPaneController::setBaseType);
        guiContentSubscriber.subscribe(messages.getProperty("weapons.ranged.baseType.choice"), rangeWeaponsPaneController::setBaseType);
        guiContentSubscriber.subscribe(messages.getProperty("armor.baseType.choice"), armorPaneController::setBaseType);
        guiContentSubscriber.subscribe(messages.getProperty("professions.class.skills.choice"), professionClassPaneController::addSkill);
        guiContentSubscriber.subscribe(messages.getProperty("professions.skills.choice"), professionPaneController::addSkill);
        guiContentSubscriber.subscribe(messages.getProperty("professions.next.choice"), professionPaneController::addProfession);
        guiContentSubscriber.subscribe(messages.getProperty("ammunition.baseType.choice"), ammunitionPaneController::setBaseType);
        guiContentSubscriber.subscribe(messages.getProperty("race.skills.choice"), racesPaneController::addSkill);
        guiContentSubscriber.subscribe(messages.getProperty("player.profession.choice"), playerPaneController::loadProfessionChoice);

        //regular queries
        guiContentSubscriber.subscribe(messages.getProperty("weapons.hand.result"), handWeaponsPaneController::loadAll);
        guiContentSubscriber.subscribe(messages.getProperty("weapons.hand.result.specific"), handWeaponsPaneController::loadToEditor);
        guiContentSubscriber.subscribe(messages.getProperty("weapons.ranged.result"), rangeWeaponsPaneController::loadAll);
        guiContentSubscriber.subscribe(messages.getProperty("weapons.ranged.result.specific"), rangeWeaponsPaneController::loadToEditor);
        guiContentSubscriber.subscribe(messages.getProperty("armor.result"), armorPaneController::loadAll);
        guiContentSubscriber.subscribe(messages.getProperty("armor.result.specific"), armorPaneController::loadToEditor);
        guiContentSubscriber.subscribe(messages.getProperty("ammunition.result"), ammunitionPaneController::loadAll);
        guiContentSubscriber.subscribe(messages.getProperty("ammunition.result.specific"), ammunitionPaneController::loadToEditor);
        guiContentSubscriber.subscribe(messages.getProperty("character.result"), playerPaneController::loadCharacters);
        guiContentSubscriber.subscribe(messages.getProperty("skills.result"), skillsPaneController::loadAll);
        guiContentSubscriber.subscribe(messages.getProperty("skills.result.specific"), skillsPaneController::loadToEditor);
        guiContentSubscriber.subscribe(messages.getProperty("professions.class.result"), this::sendProfessionClass);
        guiContentSubscriber.subscribe(messages.getProperty("professions.class.result.specific"), professionClassPaneController::loadToEditor);
        guiContentSubscriber.subscribe(messages.getProperty("professions.result"), professionPaneController::loadAll);
        guiContentSubscriber.subscribe(messages.getProperty("professions.result.specific"), professionPaneController::loadToEditor);
        guiContentSubscriber.subscribe(messages.getProperty("race.result"), this::sendRaces);
        guiContentSubscriber.subscribe(messages.getProperty("race.result.specific"), racesPaneController::loadToEditor);
        guiContentSubscriber.subscribe(messages.getProperty("player.result"), playerPaneController::loadAll);
        guiContentSubscriber.subscribe(messages.getProperty("player.result.specific"), playerPaneController::loadPlayer);
    }
}
