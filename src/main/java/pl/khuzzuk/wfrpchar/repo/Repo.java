package pl.khuzzuk.wfrpchar.repo;

import lombok.Data;
import pl.khuzzuk.wfrpchar.db.DAO;
import pl.khuzzuk.wfrpchar.entities.Character;
import pl.khuzzuk.wfrpchar.entities.Currency;
import pl.khuzzuk.wfrpchar.entities.Race;
import pl.khuzzuk.wfrpchar.entities.characters.Player;
import pl.khuzzuk.wfrpchar.entities.competency.*;
import pl.khuzzuk.wfrpchar.entities.items.Commodity;
import pl.khuzzuk.wfrpchar.entities.items.ResourceType;
import pl.khuzzuk.wfrpchar.entities.items.types.*;
import pl.khuzzuk.wfrpchar.entities.items.usable.AbstractHandWeapon;
import pl.khuzzuk.wfrpchar.entities.items.usable.Ammunition;
import pl.khuzzuk.wfrpchar.entities.items.usable.Armor;
import pl.khuzzuk.wfrpchar.entities.items.usable.Gun;

import java.util.ArrayList;
import java.util.List;

@Data
public class Repo {
    private List<Item> types;
    private List<Commodity> items;
    private List<MagicSchool> magicSchools;
    private List<Spell> spells;
    private List<Skill> skills;
    private List<ProfessionClass> professionClasses;
    private List<Profession> professions;
    private List<Character> characters;
    private List<Currency> currencies;
    private List<Race> races;
    private List<Player> players;

    public Repository convert(DAO dao) {
        Repository repository = new Repository();
        Repository.Types types = new Repository.Types();
        types.setAmmunitionTypes(getList(AmmunitionType.class, this.types));
        types.setArmorTypes(getList(ArmorType.class, this.types));
        types.setMiscItems(getList(MiscItem.class, this.types));
        types.setRangedWeaponTypes(getList(RangedWeaponType.class, this.types));
        types.setWhiteWeaponTypes(getList());
        types.setResourceTypes(new ArrayList<>(dao.getAllEntities(ResourceType.class)));
        repository.setTypes(types);

        Repository.Commodities commodities = new Repository.Commodities();
        commodities.setAmmunitions(getList(Ammunition.class, items));
        commodities.setArmors(getList(Armor.class, items));
        commodities.setGuns(getList(Gun.class, items));
        commodities.setHandWeapons(getListHandWeapon());
        repository.setItems(commodities);

        repository.setCharacters(characters);
        repository.setCurrencies(currencies);
        repository.setMagicSchools(magicSchools);
        repository.setProfessionClasses(professionClasses);
        repository.setProfessions(professions);
        repository.setRaces(races);
        repository.setSkills(skills);
        repository.setSpells(spells);
        repository.setPlayers(players);
        return repository;
    }

    private static <T> List<T> getList(Class<T> classForList, List<?> elements) {
        List list = new ArrayList();
        for (Object t : elements) {
            if (t.getClass().equals(classForList)) {
                list.add(t);
            }
        }
        return list;
    }

    private List<WhiteWeaponType> getList() {
        List<WhiteWeaponType> list = new ArrayList();
        for (Item t : types) {
            if (t instanceof WhiteWeaponType) {
                list.add((WhiteWeaponType) t);
            }
        }
        return list;
    }

    public List<AbstractHandWeapon<? extends WhiteWeaponType>> getListHandWeapon() {
        ArrayList<AbstractHandWeapon<? extends WhiteWeaponType>> weapons = new ArrayList<>();
        for (Commodity commodity : items) {
            if (commodity instanceof AbstractHandWeapon) {
                weapons.add((AbstractHandWeapon<? extends WhiteWeaponType>) commodity);
            }
        }
        return weapons;
    }
}
