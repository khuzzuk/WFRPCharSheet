package pl.khuzzuk.wfrpchar.gui;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.db.annot.SelectiveQuery;
import pl.khuzzuk.wfrpchar.messaging.*;
import pl.khuzzuk.wfrpchar.messaging.publishers.BagPublisher;
import pl.khuzzuk.wfrpchar.messaging.publishers.Publisher;
import pl.khuzzuk.wfrpchar.messaging.publishers.Publishers;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

@Component
@Publishers
@PropertySource("classpath:messages.properties")
public class GuiPublisher {
    @Inject
    @Publishers
    @MainWindowBean
    private Publisher<Message> publisher;
    @Inject
    @Publishers
    @SelectiveQuery
    @MainWindowBean
    private BagPublisher<String> textRequestPublisher;
    @Value("${guiController.initMap}")
    @NotNull
    private String initLoader;
    @Value("${whiteWeapons.query}")
    @NotNull
    private String whiteWeaponQuery;
    @Value("${database.reset}")
    @NotNull
    private String databaseReset;
    @Value("${whiteWeapons.query.specific}")
    @NotNull
    private String whiteWeaponGet;
    @Value("${whiteWeapons.save}")
    @NotNull
    private String whiteWeaponSave;
    @Value("${whiteWeapons.remove}")
    @NotNull
    private String whiteWeaponTypeRemove;
    @Value("${rangedWeapons.query}")
    @NotNull
    private String rangeWeaponsQuery;
    @Value("${rangedWeapons.query.specific}")
    @NotNull
    private String rangedWeaponGet;
    @Value("${rangedWeapons.save}")
    @NotNull
    private String rangedWeaponSave;
    @Value("${rangedWeapons.remove}")
    @NotNull
    private String rangedWeaponRemove;
    @Value("${database.saveEquipment}")
    @NotNull
    private String dbSaveEquipment;

    void requestWhiteWeapons() {
        publisher.publish(new CommunicateMessage().setType(whiteWeaponQuery));
    }

    void requestRangedWeapons() {
        publisher.publish(new CommunicateMessage().setType(rangeWeaponsQuery));
    }

    void requestResetDB() {
        publisher.publish(new CommunicateMessage().setType(databaseReset));
    }

    void requestWhiteWeaponLoad(String name) {
        textRequestPublisher.publish(name, whiteWeaponGet);
    }
    void requestRangedWeaponLoad(String name) {
        textRequestPublisher.publish(name, rangedWeaponGet);
    }

    void saveItem(String line) {
        textRequestPublisher.publish(line, dbSaveEquipment);
    }

    void removeRangedWeapon(String name) {
        textRequestPublisher.publish(name, rangedWeaponRemove);
    }

    void removeWhiteWeapon(String name) {
        textRequestPublisher.publish(name, whiteWeaponTypeRemove);
    }

    void initLoader() {
        publisher.publish(new CommunicateMessage().setType(initLoader));
    }
}
