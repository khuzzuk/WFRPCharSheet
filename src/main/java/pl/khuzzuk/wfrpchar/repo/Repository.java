package pl.khuzzuk.wfrpchar.repo;

import lombok.Getter;
import lombok.Setter;
import pl.khuzzuk.wfrpchar.entities.Character;
import pl.khuzzuk.wfrpchar.entities.Currency;
import pl.khuzzuk.wfrpchar.entities.Race;
import pl.khuzzuk.wfrpchar.entities.characters.Player;
import pl.khuzzuk.wfrpchar.entities.competency.*;
import pl.khuzzuk.wfrpchar.entities.items.ResourceType;
import pl.khuzzuk.wfrpchar.entities.items.types.*;
import pl.khuzzuk.wfrpchar.entities.items.usable.AbstractHandWeapon;
import pl.khuzzuk.wfrpchar.entities.items.usable.Ammunition;
import pl.khuzzuk.wfrpchar.entities.items.usable.Armor;
import pl.khuzzuk.wfrpchar.entities.items.usable.Gun;

import java.util.List;

@Getter
@Setter
public class Repository {
    private Types types;
    private Commodities items;
    private List<MagicSchool> magicSchools;
    private List<Spell> spells;
    private List<Skill> skills;
    private List<ProfessionClass> professionClasses;
    private List<Profession> professions;
    private List<Character> characters;
    private List<Currency> currencies;
    private List<Race> races;
    private List<Player> players;

    @Getter
    @Setter
    public static class Types {
        private List<MiscItem> miscItems;
        private List<AmmunitionType> ammunitionTypes;
        private List<ArmorType> armorTypes;
        private List<RangedWeaponType> rangedWeaponTypes;
        private List<WhiteWeaponType> whiteWeaponTypes;
        private List<ResourceType> resourceTypes;
    }

    @Getter
    @Setter
    public static class Commodities {
        private List<Ammunition> ammunitions;
        private List<AbstractHandWeapon<? extends WhiteWeaponType>> handWeapons;
        private List<Armor> armors;
        private List<Gun> guns;
    }
}
