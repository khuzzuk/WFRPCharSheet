package pl.khuzzuk.wfrpchar.db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.db.annot.*;
import pl.khuzzuk.wfrpchar.entities.Character;
import pl.khuzzuk.wfrpchar.entities.Currency;
import pl.khuzzuk.wfrpchar.entities.Player;
import pl.khuzzuk.wfrpchar.entities.items.ResourceType;
import pl.khuzzuk.wfrpchar.entities.items.types.*;
import pl.khuzzuk.wfrpchar.entities.items.usable.AbstractHandWeapon;

import javax.inject.Inject;

@Component
@Manager
public class DAOManager {
    private SessionFactory factory;
    private DBInitializer initializer;

    @Inject
    public DAOManager(SessionFactory factory, @Initializer DBInitializer initializer) {
        this.factory = factory;
        this.initializer = initializer;
    }

    public synchronized Session openNewSession() {
        return factory.openSession();
    }

    public void resetDB(DAO dao) {
        initializer.resetDatabase(dao);
    }

    @Bean
    @Items
    public DAOEntityResolver<Item, String> daoItems() {
        return new DAOEntityResolver<>("from Item", openNewSession());
    }

    @Bean
    @Items
    @Types
    public DAOEntityResolver<MiscItem, String> daoMiscItems() {
        return new DAOEntityResolver<>("from Item i where type(i) = MiscItem", openNewSession());
    }

    @Bean
    @WhiteWeapons
    @Types
    public DAOEntityResolver<WhiteWeaponType, String> daoWeaponType() {
        return new DAOEntityResolver<>("from Item i " +
                "where type(i) = OneHandedWeaponType " +
                "or type(i) = TwoHandedWeaponType " +
                "or type(i) = BastardWeaponType", openNewSession());
    }
    @Bean
    @FightingEquipments
    public DAOEntityResolver<FightingEquipment, String> daoFightingEquipment() {
        return new DAOEntityResolver<>("from Item i " +
                "where type(i) = OneHandedWeaponType " +
                "or type(i) = TwoHandedWeaponType " +
                "or type(i) = BastardWeaponType" +
                "or type(i) = RangedWeaponType" +
                "or type(i) = ArmorType", openNewSession());
    }
    @Bean
    @Players
    public DAOEntityResolver<Player, String> daoPlayer() {
        return new DAOEntityResolver<>("from Player", openNewSession());
    }
    @Bean
    @Currencies
    public DAOEntityResolver<Currency, String> daoCurrencies() {
        return new DAOEntityResolver<>("from Currency", openNewSession());
    }

    @Bean
    @Constants
    @Characters
    public DAOEntityResolver<Character, String> daoCharacters() {
        return new DAOEntityResolver<>("FROM Character", openNewSession());
    }

    @Bean
    @RangedWeapons
    public DAOEntityResolver<RangedWeaponType, String> daoRangedWeapons() {
        return new DAOEntityResolver<>("FROM Item i where type(i) = RangedWeaponType", openNewSession());
    }

    @Bean
    @Armor
    @Types
    public DAOEntityResolver<ArmorType, String> daoArmorTypes() {
        return new DAOEntityResolver<>("FROM Item i where type(i) = ArmorType", openNewSession());
    }

    @Bean
    @Resources
    @Types
    public DAOEntityResolver<ResourceType, String> daoResourcesTypes() {
        return new DAOEntityResolver<>("FROM ResourceType", openNewSession());
    }

    @Bean
    @WhiteWeapons
    public DAOEntityResolver<AbstractHandWeapon, String> daoHandWeapons() {
        return new DAOEntityResolver<>("FROM items_entities i where type(i) = OneHandedWeapon " +
                "or type(i) = TwoHandedWeapon " +
                "or type(i) = BastardWeapon ",
                openNewSession());
    }
}
