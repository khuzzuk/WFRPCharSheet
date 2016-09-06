package pl.khuzzuk.wfrpchar.gui;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.messaging.Message;
import pl.khuzzuk.wfrpchar.messaging.ReactorBean;
import pl.khuzzuk.wfrpchar.messaging.subscribers.MultiContentSubscriber;
import pl.khuzzuk.wfrpchar.messaging.subscribers.MultiSubscriber;
import pl.khuzzuk.wfrpchar.messaging.subscribers.Subscribers;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;

@Component
@ReactorBean
@PropertySource("classpath:messages.properties")
public class GuiReactor {
    @Inject
    @MainWindowBean
    private MainWindowController controller;
    @Inject
    private ArmorTypesPaneController armorTypesPaneController;
    @Inject
    private RangedWeaponTypePaneController rangedWeaponTypePaneController;
    @Inject
    private WhiteWeaponTypePaneController whiteWeaponTypePaneController;
    @Inject
    @MainWindowBean
    private ItemsLoaderToGui loader;
    @Inject
    @MainWindowBean
    @Subscribers
    private MultiSubscriber<Message> communicateSubscriber;
    @Inject
    @Subscribers
    @MainWindowBean
    private MultiContentSubscriber guiContentSubscriber;
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

    @PostConstruct
    private void setConsumers() {
        guiContentSubscriber.subscribe(whiteWeaponsMsg, whiteWeaponTypePaneController::loadWhiteWeapon);
        guiContentSubscriber.subscribe(namedWhiteWeaponsMsg, whiteWeaponTypePaneController::loadWhiteWeaponToEditor);
        guiContentSubscriber.subscribe(rangedWeaponMsg, rangedWeaponTypePaneController::loadRangedWeapon);
        guiContentSubscriber.subscribe(namedRangedWeaponMsg, rangedWeaponTypePaneController::loadRangedWeaponToEditor);
        guiContentSubscriber.subscribe(listOfArmorTypesResult, armorTypesPaneController::loadArmorTypes);
        guiContentSubscriber.subscribe(namedArmorTypeMsg, armorTypesPaneController::loadArmorTypeToEditor);
    }
}
