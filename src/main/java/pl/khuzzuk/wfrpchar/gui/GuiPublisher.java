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
    @Value("${whiteWeapons.query}")
    @NotNull
    private String whiteWeaponQuery;
    @Value("${database.reset}")
    @NotNull
    private String databaseReset;
    @Value("${miscItemTypes.query}")
    private String miscItemQuery;
    @Value("${miscItemTypes.query.specific}")
    private String miscItemGet;
    @Value("${miscItemTypes.remove}")
    private String miscItemRemove;
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
    @Value("${armorTypes.query}")
    private String armorTypesQuery;
    @Value("${armorTypes.query.specific}")
    private String armorTypesGet;
    @Value("${armorTypes.remove}")
    private String armorTypeRemove;
    @Value("${database.saveEquipment}")
    @NotNull
    private String dbSaveEquipment;

    void saveItem(String line) {
        textRequestPublisher.publish(line, dbSaveEquipment);
    }

    void requestMiscItemType() {
        publisher.publish(new CommunicateMessage().setType(miscItemQuery));
    }

    void requestWhiteWeapons() {
        publisher.publish(new CommunicateMessage().setType(whiteWeaponQuery));
    }

    void requestRangedWeapons() {
        publisher.publish(new CommunicateMessage().setType(rangeWeaponsQuery));
    }

    void requestArmorTypes() {
        publisher.publish(new CommunicateMessage().setType(armorTypesQuery));
    }

    void requestResetDB() {
        publisher.publish(new CommunicateMessage().setType(databaseReset));
    }

    void requestMiscItemTypeLoad(String name) {
        textRequestPublisher.publish(name, miscItemGet);
    }

    void requestWhiteWeaponLoad(String name) {
        textRequestPublisher.publish(name, whiteWeaponGet);
    }

    void requestRangedWeaponLoad(String name) {
        textRequestPublisher.publish(name, rangedWeaponGet);
    }

    void requestArmorTypeLoad(String name) {
        textRequestPublisher.publish(name, armorTypesGet);
    }

    void removeMiscItemType(String name) {
        textRequestPublisher.publish(name, miscItemRemove);
    }

    void removeRangedWeapon(String name) {
        textRequestPublisher.publish(name, rangedWeaponRemove);
    }

    void removeWhiteWeapon(String name) {
        textRequestPublisher.publish(name, whiteWeaponTypeRemove);
    }

    void removeArmorType(String name) {
        textRequestPublisher.publish(name, armorTypeRemove);
    }
}
