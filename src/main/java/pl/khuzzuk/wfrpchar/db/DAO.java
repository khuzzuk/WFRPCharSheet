package pl.khuzzuk.wfrpchar.db;

import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.db.annot.*;
import pl.khuzzuk.wfrpchar.entities.Character;
import pl.khuzzuk.wfrpchar.entities.Currency;
import pl.khuzzuk.wfrpchar.entities.Player;
import pl.khuzzuk.wfrpchar.entities.items.FightingEquipment;
import pl.khuzzuk.wfrpchar.entities.items.Item;
import pl.khuzzuk.wfrpchar.entities.items.RangedWeaponType;
import pl.khuzzuk.wfrpchar.entities.items.WhiteWeaponType;

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
    @Getter(AccessLevel.PACKAGE)
    private DAOManager manager;

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

    @NotNull
    Collection<Item> getAllItems() {
        assureSessionInit(daoItems);
        return daoItems.getAllItems();
    }

    @NotNull
    Collection<WhiteWeaponType> getAllWeapons() {
        assureSessionInit(daoWhiteWeapons);
        return daoWhiteWeapons.getAllItems();
    }

    Collection<RangedWeaponType> getAllRangedWeapons() {
        assureSessionInit(daoRangedWeapons);
        return daoRangedWeapons.getAllItems();
    }

    WhiteWeaponType getWhiteWeapon(String name) {
        assureSessionInit(daoWhiteWeapons);
        return daoWhiteWeapons.getItem(name);
    }

    RangedWeaponType getRangedWeapon(String name) {
        assureSessionInit(daoRangedWeapons);
        return daoRangedWeapons.getItem(name);
    }

    @NotNull
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

    void save(WhiteWeaponType weaponType) {
        assureSessionInit(daoWhiteWeapons);
        daoWhiteWeapons.commit(weaponType);
    }

    void save(RangedWeaponType weaponType) {
        assureSessionInit(daoRangedWeapons);
        daoRangedWeapons.commit(weaponType);
    }

    void save(FightingEquipment equipment) {
        assureSessionInit(daoFightingEquipment);
        daoFightingEquipment.commit(equipment);
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

    void removeWhiteWeaponType(String name) {
        assureSessionInit(daoWhiteWeapons);
        daoWhiteWeapons.remove(name);
    }

    void removeRangedWeaponType(String name) {
        assureSessionInit(daoRangedWeapons);
        daoRangedWeapons.remove(name);
    }
}
