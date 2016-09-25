package pl.khuzzuk.wfrpchar.db;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.db.annot.DaoBean;
import pl.khuzzuk.wfrpchar.db.annot.Manager;
import pl.khuzzuk.wfrpchar.entities.items.*;
import pl.khuzzuk.wfrpchar.entities.items.types.*;
import pl.khuzzuk.wfrpchar.entities.items.usable.AbstractHandWeapon;
import pl.khuzzuk.wfrpchar.messaging.Message;
import pl.khuzzuk.wfrpchar.messaging.ReactorBean;
import pl.khuzzuk.wfrpchar.messaging.publishers.Publishers;
import pl.khuzzuk.wfrpchar.messaging.subscribers.MultiContentSubscriber;
import pl.khuzzuk.wfrpchar.messaging.subscribers.MultiSubscriber;
import pl.khuzzuk.wfrpchar.messaging.subscribers.Subscribers;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import java.util.Properties;

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
    @DaoBean
    private MultiContentSubscriber daoContentSubscriber;
    @Inject
    @Subscribers
    @DaoBean
    private MultiSubscriber<Message> multiSubscriber;
    @Inject
    @Named("messages")
    private Properties messages;
    @Value("${miscItemTypes.query}")
    private String miscItemTypeQuery;
    @Value("${miscItemTypes.query.specific}")
    private String miscItemTypeNamedQuery;
    @Value("${miscItemTypes.remove}")
    private String miscItemTypeRemove;
    @Value("${whiteWeapons.query}")
    @NotNull
    private String whiteWeaponQuery;
    @Value("${whiteWeapons.query.specific}")
    private String whiteWeaponNamedQuery;
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
    @Value("${resource.type.query}")
    private String resourceTypeQuery;
    @Value("${resource.type.query.specific}")
    private String resourceTypeQuerySpecific;
    @Value("${resource.type.result}")
    private String resourceTypeResult;
    @Value("${resource.type.result.specific}")
    private String resourceTypeResultSpecific;
    @Value("${resource.type.save}")
    private String resourceTypeSave;
    @Value("${resource.type.remove}")
    private String resourceTypeRemove;
    @Value("${database.saveEquipment}")
    private String dbSaveEquipment;
    @Value("${database.reset}")
    private String resetDbMessage;

    private void getAllWhiteWeaponsTypes() {
        daoPublisher.publishWhiteWeapons(dao.getAllWhiteWeaponTypes());
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
        } else if (i instanceof MiscItem) {
            dao.save((MiscItem) i);
            daoPublisher.publish(miscItemTypeQuery);
        }
    }

    private void saveResourceType(String line) {
        dao.save(ResourceType.getFromCsv(line.split(";")));
        daoPublisher.publish(resourceTypeQuery);
    }

    private void saveHandWeapon(AbstractHandWeapon weapon) {
        dao.save(weapon);
        daoPublisher.publish(messages.getProperty("weapons.hand.query"));
    }

    private void removeMiscItem(String name) {
        dao.removeMiscItem(name);
        daoPublisher.publish(miscItemTypeQuery);
    }

    private void removeWhiteWeaponType(String name) {
        dao.removeWhiteWeaponType(name);
        daoPublisher.publish(whiteWeaponQuery);
    }

    private void removeRangedWeaponType(String name) {
        dao.removeRangedWeaponType(name);
        daoPublisher.publish(rangedWeaponsQuery);
    }

    private void removeHandWeapon(String name) {
        dao.removeHandWeapon(name);
        daoPublisher.publish(messages.getProperty("weapons.hand.query"));
    }

    private void removeArmorType(String name) {
        dao.removeArmorType(name);
        daoPublisher.publish(armorTypesQuery);
    }

    private void removeResourceType(String name) {
        dao.removeResourceType(name);
        daoPublisher.publish(resourceTypeQuery);
    }

    private void getAllMiscItemsTypes() {
        daoPublisher.publishMiscItems(dao.getAllMiscItems());
    }

    private void getAllRangedWeaponTypes() {
        daoPublisher.publishRangedWeaponTypes(dao.getAllRangedWeapons());
    }

    private void getRangedWeaponTypeByName(String name) {
        daoPublisher.publish(dao.getRangedWeapon(name));
    }

    private void getAllArmorTypes() {
        daoPublisher.publishArmorTypes(dao.getAllArmorTypes());
    }

    private void getAllResourceTypes() {
        daoPublisher.publishResourceTypes(dao.getAllResourceTypes());
    }

    private void getAllHandWeapons() {
        daoPublisher.publishHandWeapons(dao.getAllHandWeapons());
    }

    private void getAllRangedWeapons() {
        daoPublisher.publish
    }

    private void getAllWWBaseType() {
        daoPublisher.publishWhiteWeaponsBaseTypes(dao.getAllWhiteWeaponTypes());
    }

    private void getWhiteWeaponByName(String name) {
        daoPublisher.publish(dao.getWhiteWeapon(name), messages.getProperty("whiteWeapons.result.specific"));
    }

    private void getMiscItemTypeByName(String name) {
        daoPublisher.publish(dao.getMiscItem(name));
    }

    private void getArmorTypeByName(String name) {
        daoPublisher.publish(dao.getArmorType(name));
    }

    private void getResourceType(String name) {
        daoPublisher.publish(dao.getResourceType(name));
    }

    private void getHandWeapon(String name) {
        daoPublisher.publish(dao.getHandWeapon(name));
    }

    private void getWWBaseTypeByName(String name) {
        daoPublisher.publish(dao.getWhiteWeapon(name), messages.getProperty("weapons.hand.baseType.choice"));
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
        multiSubscriber.subscribe(miscItemTypeQuery, this::getAllMiscItemsTypes);
        multiSubscriber.subscribe(whiteWeaponQuery, this::getAllWhiteWeaponsTypes);
        multiSubscriber.subscribe(resetDbMessage, this::resetDB);
        multiSubscriber.subscribe(rangedWeaponsQuery, this::getAllRangedWeaponTypes);
        multiSubscriber.subscribe(armorTypesQuery, this::getAllArmorTypes);
        multiSubscriber.subscribe(resourceTypeQuery, this::getAllResourceTypes);
        multiSubscriber.subscribe(messages.getProperty("weapons.hand.baseType.getAllTypes"), this::getAllWWBaseType);
        multiSubscriber.subscribe(messages.getProperty("weapons.hand.query"), this::getAllHandWeapons);
        daoContentSubscriber.subscribe(dbSaveEquipment, this::saveItem);
        daoContentSubscriber.subscribe(miscItemTypeNamedQuery, this::getMiscItemTypeByName);
        daoContentSubscriber.subscribe(miscItemTypeRemove, this::removeMiscItem);
        daoContentSubscriber.subscribe(whiteWeaponNamedQuery, this::getWhiteWeaponByName);
        daoContentSubscriber.subscribe(whiteWeaponRemove, this::removeWhiteWeaponType);
        daoContentSubscriber.subscribe(rangeWeaponNamedQuery, this::getRangedWeaponTypeByName);
        daoContentSubscriber.subscribe(rangedWeaponRemove, this::removeRangedWeaponType);
        daoContentSubscriber.subscribe(armorTypeNamedQuery, this::getArmorTypeByName);
        daoContentSubscriber.subscribe(armorTypeRemove, this::removeArmorType);
        daoContentSubscriber.subscribe(resourceTypeQuerySpecific, this::getResourceType);
        daoContentSubscriber.subscribe(resourceTypeRemove, this::removeResourceType);
        daoContentSubscriber.subscribe(resourceTypeSave, this::saveResourceType);
        daoContentSubscriber.subscribe(messages.getProperty("weapons.hand.baseType.selected"), this::getWWBaseTypeByName);
        daoContentSubscriber.subscribe(messages.getProperty("weapons.hand.query.specific"), this::getHandWeapon);
        daoContentSubscriber.subscribe(messages.getProperty("weapons.hand.save"), this::saveHandWeapon);
        daoContentSubscriber.subscribe(messages.getProperty("weapons.hand.remove"), this::removeHandWeapon);
    }
}
