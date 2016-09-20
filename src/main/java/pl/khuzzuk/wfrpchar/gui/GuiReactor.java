package pl.khuzzuk.wfrpchar.gui;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.entities.items.ResourceType;
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
    private ResourceTypesPaneController resourceTypesPaneController;
    @Inject
    private HandWeaponsPaneController handWeaponsPaneController;
    @Inject
    @Named("hwDeterminantsCreatorController")
    private DeterminantCreatorController hwDeterminantCreatorController;
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
        guiContentSubscriber.subscribe(namedArmorTypeMsg, armorTypesPaneController::loadArmorTypeToEditor);
        guiContentSubscriber.subscribe(messages.getProperty("resource.type.result"), this::sendResourcesTypes);
        guiContentSubscriber.subscribe(resourceTypeResultSpecific, resourceTypesPaneController::loadToEditor);
        guiContentSubscriber.subscribe(messages.getProperty("weapons.hand.baseType.choice"), handWeaponsPaneController::setBaseType);
        guiContentSubscriber.subscribe(messages.getProperty("determinants.creator.add.hw"), handWeaponsPaneController::addDeterminant);
        guiContentSubscriber.subscribe(messages.getProperty("weapons.hand.result"), handWeaponsPaneController::loadAll);
        guiContentSubscriber.subscribe(messages.getProperty("weapons.hand.result.specific"), handWeaponsPaneController::loadToEditor);
        communicateSubscriber.subscribe(messages.getProperty("determinants.creator.show.hw"), hwDeterminantCreatorController::show);
    }
}
