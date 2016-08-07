package pl.khuzzuk.wfrpchar.db;

import lombok.AccessLevel;
import lombok.Getter;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.db.annot.*;
import pl.khuzzuk.wfrpchar.entities.Character;
import pl.khuzzuk.wfrpchar.entities.Currency;
import pl.khuzzuk.wfrpchar.entities.Player;
import pl.khuzzuk.wfrpchar.entities.items.FightingEquipment;
import pl.khuzzuk.wfrpchar.entities.items.Item;
import pl.khuzzuk.wfrpchar.entities.items.WhiteWeaponType;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.util.List;

@Component
@Manager
public class DAO {
    private DAOTransactional<Item, String> daoItems;
    private DAOTransactional<WhiteWeaponType, String> daoWhiteWeapons;
    private DAOTransactional<FightingEquipment, String> daoFightingEquipment;
    private DAOTransactional<Character, String> daoCharacters;
    private DAOTransactional<Player, String> daoPlayer;
    private DAOTransactional<Currency, String> daoCurrencies;
    @Getter(AccessLevel.PACKAGE)
    private DAOManager manager;
    private Session session;

    @Inject
    public DAO(@Items @NotNull DAOTransactional<Item, String> daoItems,
               @WhiteWeapons @NotNull DAOTransactional<WhiteWeaponType, String> daoWhiteWeaponType,
               @FightingEquipments @NotNull DAOTransactional<FightingEquipment, String> daoFightingEquipment,
               @Constants @Characters @NotNull DAOTransactional<Character, String> daoCharacters,
               @Players @NotNull DAOTransactional<Player, String> daoPlayer,
               @Currencies @NotNull DAOTransactional<Currency, String> daoCurrencies,
               @Manager @NotNull DAOManager manager) {
        this.daoItems = daoItems;
        this.daoWhiteWeapons = daoWhiteWeaponType;
        this.daoFightingEquipment = daoFightingEquipment;
        this.daoCharacters = daoCharacters;
        this.daoPlayer = daoPlayer;
        this.daoCurrencies = daoCurrencies;
        this.manager = manager;
    }

    private void closeSession() {
        session.close();
        session = null;
    }

    @NotNull
    public List<Item> getAllItems() {
        assureSessionInit(daoItems);
        return daoItems.getAllItems();
    }

    @NotNull
    public List<WhiteWeaponType> getAllWeapons() {
        assureSessionInit(daoWhiteWeapons);
        return daoWhiteWeapons.getAllItems();
    }

    public WhiteWeaponType getWhiteWeapon(String name) {
        assureSessionInit(daoWhiteWeapons);
        return daoWhiteWeapons.getItem(name);
    }

    @NotNull
    public List<Character> getAllCharacters() {
        assureSessionInit(daoCharacters);
        return daoCharacters.getAllItems();
    }

    @NotNull
    Session getCurrentSession() {
        if (session == null) session = manager.openNewSession();
        return session;
    }

    void closeSession(Session session) {
        if (this.session == session) closeSession();
        else session.close();
    }

    private void assureSessionInit(DAOTransactional transactional) {
        if (session == null) {
            session = manager.openNewSession();
        }
        transactional.assureInitialization(session);
    }

    void save(Item item) {
        assureSessionInit(daoItems);
        daoItems.commit(item, session);
    }

    void save(WhiteWeaponType weaponType) {
        assureSessionInit(daoWhiteWeapons);
        daoWhiteWeapons.commit(weaponType, session);
    }

    void save(FightingEquipment equipment) {
        assureSessionInit(daoFightingEquipment);
        daoFightingEquipment.commit(equipment, session);
    }

    void save(Character character) {
        assureSessionInit(daoCharacters);
        daoCharacters.commit(character, session);
    }

    void save(Player player) {
        assureSessionInit(daoPlayer);
        daoPlayer.commit(player, session);
    }

    void save(Currency currency) {
        assureSessionInit(daoCurrencies);
        daoCurrencies.commit(currency, session);
    }
}
