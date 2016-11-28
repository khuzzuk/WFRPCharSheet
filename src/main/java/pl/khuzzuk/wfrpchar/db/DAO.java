package pl.khuzzuk.wfrpchar.db;

import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.db.annot.Manager;
import pl.khuzzuk.wfrpchar.entities.Character;
import pl.khuzzuk.wfrpchar.entities.Currency;
import pl.khuzzuk.wfrpchar.entities.*;
import pl.khuzzuk.wfrpchar.entities.characters.Player;
import pl.khuzzuk.wfrpchar.entities.competency.Profession;
import pl.khuzzuk.wfrpchar.entities.competency.ProfessionClass;
import pl.khuzzuk.wfrpchar.entities.competency.Skill;
import pl.khuzzuk.wfrpchar.entities.items.ResourceType;
import pl.khuzzuk.wfrpchar.entities.items.types.*;
import pl.khuzzuk.wfrpchar.entities.items.usable.*;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.*;

@Component
@Manager
public class DAO {
    @Getter(AccessLevel.PACKAGE)
    private DAOManager manager;
    private Map<Class<? extends Persistable>, DAOTransactional<? extends Persistable, String>> resolvers;

    @Inject
    public DAO(@Manager DAOManager manager) {
        this.manager = manager;
    }

    //TODO put resolvers here
    @PostConstruct
    private void initResolvers() {
        resolvers = new HashMap<>();
        putResolver("from Item i where type(i) = MiscItem", MiscItem.class);
        putResolver("from Item", Item.class);
        putResolver("from Item i " +
                "where type(i) = OneHandedWeaponType " +
                "or type(i) = TwoHandedWeaponType " +
                "or type(i) = BastardWeaponType", WhiteWeaponType.class);
        putResolver("from Item i " +
                "where type(i) = OneHandedWeaponType " +
                "or type(i) = TwoHandedWeaponType " +
                "or type(i) = BastardWeaponType" +
                "or type(i) = RangedWeaponType" +
                "or type(i) = ArmorType", FightingEquipment.class);
        putResolver("FROM Item i where type(i) = RangedWeaponType", RangedWeaponType.class);
        putResolver("FROM Item i where type(i) = ArmorType", ArmorType.class);
        putResolver("FROM Item i where type(i) = AmmunitionType", AmmunitionType.class);
        putResolver("FROM Player", Player.class);
        putResolver("FROM Character", Character.class);
        putResolver("FROM Currency", Currency.class);
        putResolver("FROM ResourceType", ResourceType.class);
        putResolver("FORM AbstractCommodity", AbstractCommodity.class);
        putResolver("FROM AbstractCommodity i where type(i) = OneHandedWeapon " +
                "or type(i) = TwoHandedWeapon " +
                "or type(i) = BastardWeapon", AbstractHandWeapon.class);
        putResolver("FROM AbstractCommodity i where type(i) = Gun", Gun.class);
        putResolver("FROM AbstractCommodity i where type(i) = Armor", Armor.class);
        putResolver("FROM AbstractCommodity i where type(i) = Ammunition", Ammunition.class);
        putResolver("FROM Skill", Skill.class);
        putResolver("FROM ProfessionClass", ProfessionClass.class);
        putResolver("FROM Profession", Profession.class);
        putResolver("FROM Race", Race.class);
    }

    private <T extends Persistable & Named<String>> void putResolver(String query, Class<T> type) {
        resolvers.put(type, new DAOEntityResolver<T, String>(query, manager.openNewSession()));
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
    public <T extends Persistable> Set<T> getEntities(Class<T> type, String... names) {
        DAOTransactional<T, String> resolver = (DAOTransactional<T, String>) resolvers.get(type);
        assureSessionInit(resolver);
        Set<T> entities = new HashSet<>();
        for (String n : names) {
            entities.add(getEntity(type, n));
        }
        if (!entities.isEmpty() && entities.iterator().next() == null) {
            return Collections.emptySet();
        }
        return entities;
    }

    @SuppressWarnings("unchecked")
    public <T extends Persistable> List<T> getEntitiesAsList(Class<T> type, String... names) {
        DAOTransactional<T, String> resolver = (DAOTransactional<T, String>) resolvers.get(type);
        assureSessionInit(resolver);
        List<T> entities = new ArrayList<>();
        for (String n : names) {
            entities.add(getEntity(type, n));
        }
        return entities;
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

    private void assureSessionInit(DAOTransactional transactional) {
        if (transactional.requireInitialization()) {
            transactional.assureInitialization(manager.openNewSession());
        }
    }
}
