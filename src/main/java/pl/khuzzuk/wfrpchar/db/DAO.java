package pl.khuzzuk.wfrpchar.db;

import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.db.annot.*;
import pl.khuzzuk.wfrpchar.entities.Character;
import pl.khuzzuk.wfrpchar.entities.Currency;
import pl.khuzzuk.wfrpchar.entities.Player;
import pl.khuzzuk.wfrpchar.entities.items.ResourceType;
import pl.khuzzuk.wfrpchar.entities.items.types.*;
import pl.khuzzuk.wfrpchar.entities.items.usable.AbstractHandWeapon;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Component
@Manager
public class DAO {
    private DAOTransactional<Item, String> daoItems;
    private DAOTransactional<WhiteWeaponType, String> daoWhiteWeapons;
    private DAOTransactional<FightingEquipment, String> daoFightingEquipment;
    private DAOTransactional<Character, String> daoCharacters;
    private DAOTransactional<Player, String> daoPlayer;
    private DAOTransactional<Currency, String> daoCurrencies;
    @Inject
    @RangedWeapons
    private DAOTransactional<RangedWeaponType, String> daoRangedWeapons;
    @Inject
    @Armor
    @Types
    private DAOTransactional<ArmorType, String> daoArmorTypes;
    @Inject
    @Types
    @Items
    private DAOTransactional<MiscItem, String> daoMiscItems;
    @Inject
    @Resources
    @Types
    private DAOTransactional<ResourceType, String> daoResources;
    private DAOTransactional<AbstractHandWeapon, String> daoHandWeapons;
    @Getter(AccessLevel.PACKAGE)
    private DAOManager manager;

    @Inject
    public DAO(@Items @NotNull DAOTransactional<Item, String> daoItems,
               @WhiteWeapons @Types DAOTransactional<WhiteWeaponType, String> daoWhiteWeaponType,
               @FightingEquipments DAOTransactional<FightingEquipment, String> daoFightingEquipment,
               @Constants @Characters DAOTransactional<Character, String> daoCharacters,
               @Players DAOTransactional<Player, String> daoPlayer,
               @Currencies DAOTransactional<Currency, String> daoCurrencies,
               @WhiteWeapons DAOTransactional<AbstractHandWeapon, String> daoHandWeapons,
               @Manager DAOManager manager) {
        this.daoItems = daoItems;
        this.daoWhiteWeapons = daoWhiteWeaponType;
        this.daoFightingEquipment = daoFightingEquipment;
        this.daoCharacters = daoCharacters;
        this.daoPlayer = daoPlayer;
        this.daoCurrencies = daoCurrencies;
        this.daoHandWeapons = daoHandWeapons;
        this.manager = manager;
    }

    Collection<Item> getAllItems() {
        assureSessionInit(daoItems);
        return daoItems.getAllItems();
    }

    Collection<MiscItem> getAllMiscItems() {
        assureSessionInit(daoMiscItems);
        return daoMiscItems.getAllItems();
    }

    Collection<WhiteWeaponType> getAllWhiteWeaponTypes() {
        assureSessionInit(daoWhiteWeapons);
        return daoWhiteWeapons.getAllItems();
    }

    Collection<RangedWeaponType> getAllRangedWeapons() {
        assureSessionInit(daoRangedWeapons);
        return daoRangedWeapons.getAllItems();
    }

    Collection<ArmorType> getAllArmorTypes() {
        assureSessionInit(daoArmorTypes);
        return daoArmorTypes.getAllItems();
    }

    Collection<ResourceType> getAllResourceTypes() {
        assureSessionInit(daoResources);
        return daoResources.getAllItems();
    }

    Collection<AbstractHandWeapon> getAllHandWeapons() {
        assureSessionInit(daoHandWeapons);
        return daoHandWeapons.getAllItems();
    }

    MiscItem getMiscItem(String name) {
        assureSessionInit(daoMiscItems);
        return daoMiscItems.getItem(name);
    }

    WhiteWeaponType getWhiteWeapon(String name) {
        assureSessionInit(daoWhiteWeapons);
        return daoWhiteWeapons.getItem(name);
    }

    RangedWeaponType getRangedWeapon(String name) {
        assureSessionInit(daoRangedWeapons);
        return daoRangedWeapons.getItem(name);
    }

    ArmorType getArmorType(String name) {
        assureSessionInit(daoArmorTypes);
        return daoArmorTypes.getItem(name);
    }

    ResourceType getResourceType(String name) {
        assureSessionInit(daoResources);
        return daoResources.getItem(name);
    }

    AbstractHandWeapon getHandWeapon(String name) {
        assureSessionInit(daoHandWeapons);
        return daoHandWeapons.getItem(name);
    }

    Collection<Character> getAllCharacters() {
        assureSessionInit(daoCharacters);
        return daoCharacters.getAllItems();
    }

    private void assureSessionInit(DAOTransactional transactional) {
        if (transactional.requireInitialization()) {
            transactional.assureInitialization(manager.openNewSession());
        }
    }

    void save(Item item) {
        assureSessionInit(daoItems);
        daoItems.commit(item);
    }

    void save(MiscItem item) {
        assureSessionInit(daoMiscItems);
        daoMiscItems.commit(item);
    }

    void save(WhiteWeaponType weaponType) {
        assureSessionInit(daoWhiteWeapons);
        daoWhiteWeapons.commit(weaponType);
    }

    void save(RangedWeaponType weaponType) {
        assureSessionInit(daoRangedWeapons);
        daoRangedWeapons.commit(weaponType);
    }

    void save(ArmorType armorType) {
        assureSessionInit(daoArmorTypes);
        daoArmorTypes.commit(armorType);
    }

    void save(FightingEquipment equipment) {
        assureSessionInit(daoFightingEquipment);
        daoFightingEquipment.commit(equipment);
    }

    void save(AbstractHandWeapon handWeapon) {
        assureSessionInit(daoHandWeapons);
        daoHandWeapons.commit(handWeapon);
    }

    void save(Character character) {
        assureSessionInit(daoCharacters);
        daoCharacters.commit(character);
    }

    void save(Player player) {
        assureSessionInit(daoPlayer);
        daoPlayer.commit(player);
    }

    void save(Currency currency) {
        assureSessionInit(daoCurrencies);
        daoCurrencies.commit(currency);
    }

    void save(ResourceType resource) {
        assureSessionInit(daoResources);
        daoResources.commit(resource);
    }

    void removeMiscItem(String name) {
        assureSessionInit(daoMiscItems);
        daoMiscItems.remove(name);
    }

    void removeWhiteWeaponType(String name) {
        assureSessionInit(daoWhiteWeapons);
        daoWhiteWeapons.remove(name);
    }

    void removeRangedWeaponType(String name) {
        assureSessionInit(daoRangedWeapons);
        daoRangedWeapons.remove(name);
    }

    void removeArmorType(String name) {
        assureSessionInit(daoArmorTypes);
        daoArmorTypes.remove(name);
    }

    void removeResourceType(String name) {
        assureSessionInit(daoResources);
        daoResources.remove(name);
    }

    void removeHandWeapon(String name) {
        assureSessionInit(daoHandWeapons);
        daoHandWeapons.remove(name);
    }
}
