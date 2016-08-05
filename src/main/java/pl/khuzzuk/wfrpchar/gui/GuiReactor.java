package pl.khuzzuk.wfrpchar.gui;

import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.entities.items.WeaponType;
import pl.khuzzuk.wfrpchar.messaging.ContentSubscriber;
import pl.khuzzuk.wfrpchar.messaging.ReactorBean;
import pl.khuzzuk.wfrpchar.messaging.Subscribers;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

@Component
@ReactorBean
public class GuiReactor {
    @Inject
    @MainWindowBean
    private MainWindowController controller;
    @Inject
    @MainWindowBean
    @Subscribers
    private ContentSubscriber<List<WeaponType>> weaponTypeSubscriber;

    @PostConstruct
    private void setConsumers() {
        weaponTypeSubscriber.setConsumer(controller::loadWeapon);
    }
}
