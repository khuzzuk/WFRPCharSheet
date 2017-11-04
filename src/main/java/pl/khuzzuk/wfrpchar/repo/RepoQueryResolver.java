package pl.khuzzuk.wfrpchar.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.entities.Character;
import pl.khuzzuk.wfrpchar.entities.Currency;
import pl.khuzzuk.wfrpchar.entities.Named;
import pl.khuzzuk.wfrpchar.entities.Race;
import pl.khuzzuk.wfrpchar.entities.characters.Player;
import pl.khuzzuk.wfrpchar.entities.competency.*;
import pl.khuzzuk.wfrpchar.entities.items.ResourceType;
import pl.khuzzuk.wfrpchar.entities.items.types.*;
import pl.khuzzuk.wfrpchar.entities.items.usable.AbstractHandWeapon;
import pl.khuzzuk.wfrpchar.entities.items.usable.Ammunition;
import pl.khuzzuk.wfrpchar.entities.items.usable.Armor;
import pl.khuzzuk.wfrpchar.entities.items.usable.Gun;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Component
public class RepoQueryResolver {
    @Autowired
    private Repository repository;
    private Map<Class<?>, List<? extends Named<String>>> groups;

    @PostConstruct
    private void init() {
        groups = new HashMap<>();
        groups.put(MiscItem.class, repository.getTypes().getMiscItems());
        groups.put(AmmunitionType.class, repository.getTypes().getAmmunitionTypes());
        groups.put(ArmorType.class, repository.getTypes().getArmorTypes());
        groups.put(RangedWeaponType.class, repository.getTypes().getRangedWeaponTypes());
        groups.put(ResourceType.class, repository.getTypes().getResourceTypes());
        groups.put(WhiteWeaponType.class, repository.getTypes().getWhiteWeaponTypes());
        groups.put(Ammunition.class, repository.getItems().getAmmunitions());
        groups.put(Armor.class, repository.getItems().getArmors());
        groups.put(Gun.class, repository.getItems().getGuns());
        groups.put(AbstractHandWeapon.class, repository.getItems().getHandWeapons());
        groups.put(MagicSchool.class, repository.getMagicSchools());
        groups.put(Spell.class, repository.getSpells());
        groups.put(Skill.class, repository.getSkills());
        groups.put(ProfessionClass.class, repository.getProfessionClasses());
        groups.put(Profession.class, repository.getProfessions());
        groups.put(Character.class, repository.getCharacters());
        groups.put(Currency.class, repository.getCurrencies());
        groups.put(Race.class, repository.getRaces());
        groups.put(Player.class, repository.getPlayers());
    }

    <T> List<T> get(Class<T> by) {
        return (List<T>) groups.get(by);
    }

    Named<String> get(Criteria criteria) {
        return groups.get(criteria.getType()).stream().filter(named -> named.getName().equals(criteria.getName())).findAny().orElseThrow(NoSuchElementException::new);
    }

    void remove(Criteria criteria) {
        groups.get(criteria.getType()).removeIf(named -> named.getName().equals(criteria.getName()));
    }

    public void add(SaveItem<?> saveItem) {
        addToList(groups.get(saveItem.getType()), saveItem.getEntity());
    }

    @SuppressWarnings("unchecked")
    private <T> void addToList(List list, T element) {
        list.add(element);
    }
}
