package pl.khuzzuk.wfrpchar.db;

import org.hibernate.Session;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.db.annot.*;
import pl.khuzzuk.wfrpchar.entities.Character;
import pl.khuzzuk.wfrpchar.entities.Currency;
import pl.khuzzuk.wfrpchar.entities.Player;
import pl.khuzzuk.wfrpchar.entities.items.FightingEquipment;
import pl.khuzzuk.wfrpchar.entities.items.Item;
import pl.khuzzuk.wfrpchar.entities.items.WeaponType;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.util.List;

@Component
@Manager
public class DAO {
    private DAOTransactional<Item, String> daoItems;
    private DAOTransactional<WeaponType, String> daoWeapons;
    private DAOTransactional<FightingEquipment, String> daoFightingEquipment;
    private DAOTransactional<Character, String> daoCharacters;
    private DAOTransactional<Player, String> daoPlayer;
    private DAOTransactional<Currency, String> daoCurrencies;
    private DAOManager manager;
    private Session session;

    @Inject
    public DAO(@Items @NotNull DAOTransactional<Item, String> daoItems,
               @Weapons @NotNull DAOTransactional<WeaponType, String> daoWeaponType,
               @FightingEquipments @NotNull DAOTransactional<FightingEquipment, String> daoFightingEquipment,
               @Constants @Characters @NotNull DAOTransactional<Character, String> daoCharacters,
               @Players @NotNull DAOTransactional<Player, String> daoPlayer,
               @Currencies @NotNull DAOTransactional<Currency, String> daoCurrencies,
               @Manager @NotNull DAOManager manager) {
        this.daoItems = daoItems;
        this.daoWeapons = daoWeaponType;
        this.daoFightingEquipment = daoFightingEquipment;
        this.daoCharacters = daoCharacters;
        this.daoPlayer = daoPlayer;
        this.daoCurrencies = daoCurrencies;
        this.manager = manager;
    }

    @NotNull
    public List<Item> getAllItems() {
        return daoItems.getAllItems();
    }

    @NotNull
    public List<WeaponType> getAllWeapons() {
        return daoWeapons.getAllItems();
    }

    @NotNull
    public List<Character> getAllCharacters() {
        return daoCharacters.getAllItems();
    }

    public boolean savePlayer(Player player) {
        return daoPlayer.commit(player, session);
    }

    @NotNull
    Session getCurrentSession() {
        if (session == null) session = manager.openNewSession();
        return session;
    }

    private void closeSession() {
        session.close();
        session = null;
    }

    void closeSession(Session session) {
        if (this.session == session) closeSession();
        else session.close();
    }

    @PostConstruct
    private void assureSessionInit() {
        if (session == null) {
            session = manager.openNewSession();
        }
    }

    void save(Item item) {
        assureSessionInit();
        daoItems.commit(item, session);
    }

    void save(WeaponType weaponType) {
        assureSessionInit();
        daoWeapons.commit(weaponType, session);
    }

    void save(FightingEquipment equipment) {
        assureSessionInit();
        daoFightingEquipment.commit(equipment, session);
    }

    void save(Character character) {
        assureSessionInit();
        daoCharacters.commit(character, session);
    }

    void save(Player player) {
        assureSessionInit();
        daoPlayer.commit(player, session);
    }

    void save(Currency currency) {
        assureSessionInit();
        daoCurrencies.commit(currency, session);
    }
}
