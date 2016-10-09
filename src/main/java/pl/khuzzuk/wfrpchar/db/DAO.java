package pl.khuzzuk.wfrpchar.db;

import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.db.annot.*;
import pl.khuzzuk.wfrpchar.entities.*;
import pl.khuzzuk.wfrpchar.entities.Character;
import pl.khuzzuk.wfrpchar.entities.competency.Profession;
import pl.khuzzuk.wfrpchar.entities.competency.ProfessionClass;
import pl.khuzzuk.wfrpchar.entities.items.ResourceType;
import pl.khuzzuk.wfrpchar.entities.items.types.*;
import pl.khuzzuk.wfrpchar.entities.items.usable.AbstractHandWeapon;
import pl.khuzzuk.wfrpchar.entities.items.usable.Ammunition;
import pl.khuzzuk.wfrpchar.entities.items.usable.Armor;
import pl.khuzzuk.wfrpchar.entities.items.usable.Gun;
import pl.khuzzuk.wfrpchar.entities.competency.Skill;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
@Manager
public class DAO {
    private DAOTransactional<Item, String> daoItems;
    private DAOTransactional<WhiteWeaponType, String> daoWhiteWeapons;
    private DAOTransactional<FightingEquipment, String> daoFightingEquipment;
    private DAOTransactional<Character, String> daoCharacters;
    private DAOTransactional<Player, String> daoPlayer;
    private DAOTransactional<Currency, String> daoCurrencies;
    private DAOTransactional<RangedWeaponType, String> daoRangedWeaponsTypes;
    private DAOTransactional<ArmorType, String> daoArmorTypes;
    @Inject
    @Types
    @Items
    private DAOTransactional<MiscItem, String> daoMiscItems;
    private DAOTransactional<ResourceType, String> daoResources;
    private DAOTransactional<AbstractHandWeapon<? extends WhiteWeaponType>, String> daoHandWeapons;
    private final DAOTransactional<Gun, String> daoRangedWeapons;
    @Getter(AccessLevel.PACKAGE)
    private DAOManager manager;
    private Map<Class<? extends Persistable>, DAOTransactional<? extends Persistable, String>> resolvers;

    @Inject
    public DAO(@Items @NotNull DAOTransactional<Item, String> daoItems,
               @WhiteWeapons @Types DAOTransactional<WhiteWeaponType, String> daoWhiteWeaponType,
               @RangedWeapons @Types DAOTransactional<RangedWeaponType, String> daoRangedWeaponsTypes,
               @Armors @Types DAOTransactional<ArmorType, String> daoArmorTypes,
               @Ammunitions @Types DAOTransactional<AmmunitionType, String> daoAmmunitionTypes,
               @FightingEquipments DAOTransactional<FightingEquipment, String> daoFightingEquipment,
               @Constants @Characters DAOTransactional<Character, String> daoCharacters,
               @Players DAOTransactional<Player, String> daoPlayer,
               @Currencies DAOTransactional<Currency, String> daoCurrencies,
               @Resources @Types DAOTransactional<ResourceType, String> daoResources,
               @WhiteWeapons DAOTransactional<AbstractHandWeapon<? extends WhiteWeaponType>, String> daoHandWeapons,
               @RangedWeapons DAOTransactional<Gun, String> daoRangedWeapons,
               @Armors DAOTransactional<Armor, String> daoArmor,
               @Ammunitions DAOTransactional<Ammunition, String> daoAmmunition,
               @Skills DAOTransactional<Skill, String> daoSkills,
               @Professions DAOTransactional<Profession, String> daoProffesion,
               @Professions @Types DAOTransactional<ProfessionClass, String> daoProfessionClass,
               @Races DAOTransactional<Race, String> daoRaces,
               @Manager DAOManager manager) {
        this.daoItems = daoItems;
        this.daoWhiteWeapons = daoWhiteWeaponType;
        this.daoArmorTypes = daoArmorTypes;
        this.daoFightingEquipment = daoFightingEquipment;
        this.daoCharacters = daoCharacters;
        this.daoPlayer = daoPlayer;
        this.daoCurrencies = daoCurrencies;
        this.daoResources = daoResources;
        this.daoHandWeapons = daoHandWeapons;
        this.daoRangedWeapons = daoRangedWeapons;
        this.daoRangedWeaponsTypes = daoRangedWeaponsTypes;
        this.manager = manager;
        resolvers = new HashMap<>();
        resolvers.put(Item.class, daoItems);
        resolvers.put(WhiteWeaponType.class, daoWhiteWeaponType);
        resolvers.put(RangedWeaponType.class, daoRangedWeaponsTypes);
        resolvers.put(ArmorType.class, daoArmorTypes);
        resolvers.put(AmmunitionType.class, daoAmmunitionTypes);
        resolvers.put(FightingEquipment.class, daoFightingEquipment);
        resolvers.put(Character.class, daoCharacters);
        resolvers.put(Player.class, daoPlayer);
        resolvers.put(Currency.class, daoCurrencies);
        resolvers.put(ResourceType.class, daoResources);
        resolvers.put(AbstractHandWeapon.class, daoHandWeapons);
        resolvers.put(Gun.class, daoRangedWeapons);
        resolvers.put(Armor.class, daoArmor);
        resolvers.put(Ammunition.class, daoAmmunition);
        resolvers.put(Skill.class, daoSkills);
        resolvers.put(ProfessionClass.class, daoProfessionClass);
        resolvers.put(Profession.class, daoProffesion);
        resolvers.put(Race.class, daoRaces);
    }

    @SuppressWarnings("unchecked")
    <T extends Persistable> Collection<T> getAllEntities(Class<T> entityType) {
        DAOTransactional<T, String> resolver = (DAOTransactional<T, String>) resolvers.get(entityType);
        assureSessionInit(resolver);
        return resolver.getAllItems();
    }

    @SuppressWarnings("unchecked")
    public <T extends Persistable> T getEntity(Class<T> entityType, String name) {
        DAOTransactional<T, String> resolver = (DAOTransactional<T, String>) resolvers.get(entityType);
        assureSessionInit(resolver);
        return resolver.getItem(name);
    }

    @SuppressWarnings("unchecked")
    <T extends Persistable> void saveEntity(Class<T> entityType, T entity) {
        DAOTransactional<T, String> resolver = (DAOTransactional<T, String>) resolvers.get(entityType);
        if (resolver == null) {
            throw new IllegalStateException("no EntityResolver for class " + entityType);
        }
        assureSessionInit(resolver);
        resolver.commit(entity);
    }

    @SuppressWarnings("unchecked")
    <T extends Persistable> void removeEntity(Class<T> entityType, String name) {
        DAOTransactional<T, String> resolver = (DAOTransactional<T, String>) resolvers.get(entityType);
        assureSessionInit(resolver);
        resolver.remove(name);
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
        assureSessionInit(daoRangedWeaponsTypes);
        return daoRangedWeaponsTypes.getAllItems();
    }

    Collection<ArmorType> getAllArmorTypes() {
        assureSessionInit(daoArmorTypes);
        return daoArmorTypes.getAllItems();
    }

    Collection<ResourceType> getAllResourceTypes() {
        assureSessionInit(daoResources);
        return daoResources.getAllItems();
    }

    Collection<AbstractHandWeapon<? extends WhiteWeaponType>> getAllHandWeapons() {
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
        assureSessionInit(daoRangedWeaponsTypes);
        return daoRangedWeaponsTypes.getItem(name);
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
        assureSessionInit(daoRangedWeaponsTypes);
        daoRangedWeaponsTypes.commit(weaponType);
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
        assureSessionInit(daoRangedWeaponsTypes);
        daoRangedWeaponsTypes.remove(name);
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
