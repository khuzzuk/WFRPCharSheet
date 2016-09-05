package pl.khuzzuk.wfrpchar.db;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.db.annot.*;
import pl.khuzzuk.wfrpchar.entities.items.*;
import pl.khuzzuk.wfrpchar.messaging.Message;
import pl.khuzzuk.wfrpchar.messaging.ReactorBean;
import pl.khuzzuk.wfrpchar.messaging.publishers.Publishers;
import pl.khuzzuk.wfrpchar.messaging.subscribers.*;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Subscribers
@ReactorBean
@Component
public class DAOReactor {
    private DAO dao;
    @Inject
    private WeaponParser weaponParser;
    @Inject
    @Publishers
    private DAOPublisher daoPublisher;
    @Inject
    @Subscribers
    @WhiteWeapons
    @SelectiveQuery
    private ContentSubscriber<String> whiteWeaponSelectionSubscriber;
    @Inject
    @Subscribers
    @DaoBean
    private MultiContentSubscriber daoContentSubscriber;
    @Inject
    @Subscribers
    @DaoBean
    private MultiSubscriber<Message> multiSubscriber;
    @Value("${whiteWeapons.query}")
    @NotNull
    private String whiteWeaponQuery;
    @Value("${whiteWeapons.remove}")
    @NotNull
    private String whiteWeaponRemove;
    @Value("${rangedWeapons.query}")
    private String rangedWeaponsQuery;
    @Value("${rangedWeapons.query.specific}")
    private String rangeWeaponNamedQuery;
    @Value("${rangedWeapons.remove}")
    private String rangedWeaponRemove;
    @Value("${armorTypes.query}")
    private String armorTypesQuery;
    @Value("${armorTypes.query.specific}")
    private String armorTypeNamedQuery;
    @Value("${armorTypes.remove}")
    private String armorTypeRemove;
    @Value("${database.saveEquipment}")
    private String dbSaveEquipment;
    @Value("${database.reset}")
    private String resetDbMessage;

    private void getAllWeapons() {
        daoPublisher.publishWhiteWeapons(dao.getAllWeapons());
    }

    private void getWhiteWeaponByName(String name) {
        daoPublisher.publish(dao.getWhiteWeapon(name));
    }

    private void saveItem(String line) {
        Item i = weaponParser.parseEquipment(line.split(";"));
        if (i instanceof WhiteWeaponType) {
            dao.save((WhiteWeaponType) i);
            daoPublisher.publish(whiteWeaponQuery);
        } else if (i instanceof RangedWeaponType) {
            dao.save((RangedWeaponType) i);
            daoPublisher.publish(rangedWeaponsQuery);
        } else if (i instanceof ArmorType) {
            dao.save((ArmorType) i);
            daoPublisher.publish(armorTypesQuery);
        }
    }

    private void removeWhiteWeaponType(String name) {
        dao.removeWhiteWeaponType(name);
        daoPublisher.publish(whiteWeaponQuery);
    }

    private void removeRangedWeaponType(String name) {
        dao.removeRangedWeaponType(name);
        daoPublisher.publish(rangedWeaponsQuery);
    }

    private void removeArmorType(String name) {
        dao.removeArmorType(name);
        daoPublisher.publish(armorTypesQuery);
    }

    private void getAllRangedWeaponTypes() {
        daoPublisher.publishRangedWeapons(dao.getAllRangedWeapons());
    }

    private void getRangedWeaponTypeByName(String name) {
        daoPublisher.publish(dao.getRangedWeapon(name));
    }

    private void getAllArmorTypes() {
        daoPublisher.publishArmorTypes(dao.getAllArmorTypes());
    }

    private void getArmorTypeByName(String name) {
        daoPublisher.publish(dao.getArmorType(name));
    }

    private void resetDB() {
        dao.getManager().resetDB(dao);
        daoPublisher.publish(whiteWeaponQuery);
    }

    @Inject
    public void setDao(@Manager DAO dao) {
        this.dao = dao;
    }

    @PostConstruct
    private void setReactors() {
        multiSubscriber.subscribe(whiteWeaponQuery, this::getAllWeapons);
        multiSubscriber.subscribe(resetDbMessage, this::resetDB);
        multiSubscriber.subscribe(rangedWeaponsQuery, this::getAllRangedWeaponTypes);
        multiSubscriber.subscribe(armorTypesQuery, this::getAllArmorTypes);
        whiteWeaponSelectionSubscriber.setConsumer(this::getWhiteWeaponByName);
        daoContentSubscriber.subscribe(dbSaveEquipment, this::saveItem);
        daoContentSubscriber.subscribe(whiteWeaponRemove, this::removeWhiteWeaponType);
        daoContentSubscriber.subscribe(rangeWeaponNamedQuery, this::getRangedWeaponTypeByName);
        daoContentSubscriber.subscribe(rangedWeaponRemove, this::removeRangedWeaponType);
        daoContentSubscriber.subscribe(armorTypeNamedQuery, this::getArmorTypeByName);
        daoContentSubscriber.subscribe(armorTypeRemove, this::removeArmorType);
    }
}
