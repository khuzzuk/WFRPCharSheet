package pl.khuzzuk.wfrpchar.repo;

import lombok.Data;
import pl.khuzzuk.wfrpchar.entities.Character;
import pl.khuzzuk.wfrpchar.entities.Currency;
import pl.khuzzuk.wfrpchar.entities.Race;
import pl.khuzzuk.wfrpchar.entities.characters.Player;
import pl.khuzzuk.wfrpchar.entities.competency.*;
import pl.khuzzuk.wfrpchar.entities.items.Commodity;
import pl.khuzzuk.wfrpchar.entities.items.types.Item;

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
}
