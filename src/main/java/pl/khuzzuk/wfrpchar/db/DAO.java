package pl.khuzzuk.wfrpchar.db;

import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.db.annot.Manager;
import pl.khuzzuk.wfrpchar.entities.Character;
import pl.khuzzuk.wfrpchar.entities.Currency;
import pl.khuzzuk.wfrpchar.entities.*;
import pl.khuzzuk.wfrpchar.entities.characters.Player;
import pl.khuzzuk.wfrpchar.entities.competency.*;
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
    private Map<Class<? extends Persistable>, DAOTransactional<? extends Persistable, ? extends Comparable<?>>> resolvers;

    @Inject
    public DAO(@Manager DAOManager manager) {
        this.manager = manager;
    }

    @PostConstruct
    private void initResolvers() {
        resolvers = new HashMap<>();
        putResolver(MiscItem.class);
        putResolver(Item.class);
        putResolver(WhiteWeaponType.class);
        putResolver(FightingEquipment.class);
        putResolver(RangedWeaponType.class);
        putResolver(ArmorType.class);
        putResolver(AmmunitionType.class);
        putResolver(Player.class);
        putResolver(Character.class);
        putResolver(Currency.class);
        putResolver(ResourceType.class);
        putResolver(AbstractCommodity.class);
        putResolver(AbstractHandWeapon.class);
        putResolver(Gun.class);
        putResolver(Armor.class);
        putResolver(Ammunition.class);
        putResolver(Skill.class);
        putResolver(ProfessionClass.class);
        putResolver(Profession.class);
        putResolver(Race.class);
        putResolver(MagicSchool.class);
        putResolver(Spell.class);
    }

    private <T extends Persistable & Named<U>, U extends Comparable<? super U>> void putResolver(Class<T> type) {
        resolvers.put(type, new DAOEntityTypeResolver<>(type, manager));
    }

    @SuppressWarnings("unchecked")
    <T extends Persistable & Named<U>, U extends Comparable<? super U>>
    Collection<T> getAllEntities(Class<T> entityType) {
        DAOTransactional<T, U> resolver = (DAOTransactional<T, U>) resolvers.get(entityType);
        return resolver.getAllItems();
    }

    @SuppressWarnings("unchecked")
    public <T extends Persistable & Named<U>, U extends Comparable<? super U>>
    T getEntity(Class<T> entityType, U name) {
        DAOTransactional<T, U> resolver = (DAOTransactional<T, U>) resolvers.get(entityType);
        return resolver.getItem(name);
    }

    @SuppressWarnings("unchecked")
    public <T extends Persistable & Named<U>, U extends Comparable<? super U>>
    Set<T> getEntities(Class<T> type, U... names) {
        DAOTransactional<T, U> resolver = (DAOTransactional<T, U>) resolvers.get(type);
        Set<T> entities = new HashSet<>();
        for (U n : names) {
            Optional.ofNullable(getEntity(type, n)).ifPresent(entities::add);
        }
        entities.remove(null);
        return entities;
    }

    @SuppressWarnings("unchecked")
    public <T extends Persistable & Named<U>, U extends Comparable<? super U>>
    List<T> getEntitiesAsList(Class<T> type, U... names) {
        List<T> entities = new ArrayList<>();
        for (U n : names) {
            Optional.ofNullable(getEntity(type, n)).ifPresent(entities::add);
        }
        entities.remove(null);
        return entities;
    }

    @SuppressWarnings("unchecked")
    <T extends Persistable> void saveEntity(Class<T> entityType, T entity) {
        DAOTransactional<T, String> resolver = (DAOTransactional<T, String>) resolvers.get(entityType);
        if (resolver == null) {
            throw new IllegalStateException("no EntityResolver for class " + entityType);
        }
        resolver.commit(entity);
    }

    @SuppressWarnings("unchecked")
    <T extends Persistable> void removeEntity(Class<T> entityType, String name) {
        DAOTransactional<T, String> resolver = (DAOTransactional<T, String>) resolvers.get(entityType);
        resolver.remove(name);
    }
}
