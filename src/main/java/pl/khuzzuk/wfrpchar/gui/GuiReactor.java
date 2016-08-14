package pl.khuzzuk.wfrpchar.gui;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.messaging.ReactorBean;
import pl.khuzzuk.wfrpchar.messaging.subscribers.MultiContentSubscriber;
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
    @Subscribers
    @MainWindowBean
    private MultiContentSubscriber guiContentSubscriber;
    @Value("${whiteWeapons.result}")
    @NotNull
    private String whiteWeaponsMsg;
    @Value("${whiteWeapons.result.specific}")
    @NotNull
    private String namedWhiteWeaponsMsg;

    @PostConstruct
    private void setConsumers() {
        guiContentSubscriber.subscribe(whiteWeaponsMsg, controller::loadWhiteWeapon);
        guiContentSubscriber.subscribe(namedWhiteWeaponsMsg, controller::loadWhiteWeaponToEditor);
    }
}
