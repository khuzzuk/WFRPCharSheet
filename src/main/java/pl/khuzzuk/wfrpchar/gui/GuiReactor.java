package pl.khuzzuk.wfrpchar.gui;

import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.db.annot.SelectiveQuery;
import pl.khuzzuk.wfrpchar.db.annot.WhiteWeapons;
import pl.khuzzuk.wfrpchar.entities.items.WhiteWeaponType;
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
    @WhiteWeapons
    private ContentSubscriber<List<WhiteWeaponType>> whiteWeaponTypeSubscriber;
    @Inject
    @MainWindowBean
    @Subscribers
    @WhiteWeapons
    @SelectiveQuery
    private ContentSubscriber<WhiteWeaponType> whiteWeaponNamedSubscriber;

    @PostConstruct
    private void setConsumers() {
        whiteWeaponTypeSubscriber.setConsumer(controller::loadWhiteWeapon);
        whiteWeaponNamedSubscriber.setConsumer(controller::loadWhiteWeaponToEditor);
    }
}
