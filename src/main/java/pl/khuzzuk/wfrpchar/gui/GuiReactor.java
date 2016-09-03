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
    @Value("${guiController.initMap}")
    @NotNull
    private String initLoader;
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

    @PostConstruct
    private void setConsumers() {
        guiContentSubscriber.subscribe(whiteWeaponsMsg, controller::loadWhiteWeapon);
        guiContentSubscriber.subscribe(namedWhiteWeaponsMsg, loader::loadWhiteWeaponToEditor);
        guiContentSubscriber.subscribe(rangedWeaponMsg, controller::loadRangedWeapon);
        guiContentSubscriber.subscribe(namedRangedWeaponMsg, loader::loadRangedWeaponToEditor);
        communicateSubscriber.subscribe(initLoader, loader::initFieldsMap);
    }
}
