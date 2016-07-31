package pl.khuzzuk.wfrpchar.db;

import lombok.Getter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.db.annot.*;
import pl.khuzzuk.wfrpchar.entities.Character;
import pl.khuzzuk.wfrpchar.entities.Player;
import pl.khuzzuk.wfrpchar.entities.items.FightingEquipment;
import pl.khuzzuk.wfrpchar.entities.items.Item;

import javax.inject.Inject;

@Component
@Manager
public class DAOManager {
    private SessionFactory factory;
    @Initializer
    private DBInitializer initializer;
    @Getter
    private Session session;

    @Inject
    public DAOManager(SessionFactory factory, @Initializer DBInitializer initializer) {
        this.factory = factory;
    }

    public Session openNewSession() {
        session = factory.openSession();
        return session;
    }

    public void resetDB(DAO dao) {
        initializer.resetDatabase(dao);
    }

    @Bean
    @Items
    public DAOEntityResolver<Item, String> daoItems() {
        return new DAOEntityResolver<>("from Item");
    }
    @Bean
    @Weapons
    public DAOEntityResolver<Item, String> daoWeaponType() {
        return new DAOEntityResolver<>("from Item i " +
                "where type(i) = OneHandedWeaponType " +
                "or type(i) = TwoHandedWeaponType " +
                "or type(i) = BastardWeaponType");
    }
    @Bean
    @FightingEquipments
    public DAOEntityResolver<FightingEquipment, String> daoFightingEquipment() {
        return new DAOEntityResolver<>("from Item i " +
                "where type(i) = OneHandedWeaponType " +
                "or type(i) = TwoHandedWeaponType " +
                "or type(i) = BastardWeaponType");
    }
    @Bean
    @Players
    public DAOEntityResolver<Player, String> daoPlayer() {
        return new DAOEntityResolver<>("from Player");
    }
    @Bean
    @Currencies
    public DAOEntityResolver<Player, String> daoCurrencies() {
        return new DAOEntityResolver<>("from Currency");
    }

    @Bean
    @Constants
    @Characters
    public DAOEntityResolver<Character, String> daoCharacters() {
        return new DAOEntityResolver<>("FROM Character");
    }
}
