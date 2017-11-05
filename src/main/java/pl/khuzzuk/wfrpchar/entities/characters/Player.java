package pl.khuzzuk.wfrpchar.entities.characters;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.khuzzuk.wfrpchar.entities.Character;
import pl.khuzzuk.wfrpchar.entities.*;
import pl.khuzzuk.wfrpchar.entities.competency.Profession;
import pl.khuzzuk.wfrpchar.entities.competency.ProfessionClass;
import pl.khuzzuk.wfrpchar.entities.competency.Skill;
import pl.khuzzuk.wfrpchar.entities.competency.Spell;
import pl.khuzzuk.wfrpchar.entities.determinants.Determinant;
import pl.khuzzuk.wfrpchar.entities.items.HandWeapon;
import pl.khuzzuk.wfrpchar.entities.items.ProtectiveWearings;
import pl.khuzzuk.wfrpchar.entities.items.RangedWeapon;
import pl.khuzzuk.wfrpchar.entities.items.types.Item;
import pl.khuzzuk.wfrpchar.entities.items.usable.AbstractCommodity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ToString(exclude = "id")
@Getter
@Setter
public class Player implements Featured, Persistable, Documented {
    private long id;
    private String name;
    @JsonIdentityReference(alwaysAsId = true)
    private Race race;
    @JsonIdentityReference(alwaysAsId = true)
    private ProfessionClass professionClass;
    @JsonIdentityReference(alwaysAsId = true)
    private Profession currentProfession;
    @JsonIdentityReference(alwaysAsId = true)
    private Set<Profession> career;
    @JsonIdentityReference(alwaysAsId = true)
    private Character character;
    private Appearance appearance;
    private Set<Determinant> determinants;
    @JsonIdentityReference(alwaysAsId = true)
    private List<Item> equipment;
    @JsonIdentityReference(alwaysAsId = true)
    private List<AbstractCommodity> commodities;
    @JsonIdentityReference(alwaysAsId = true)
    private Set<PlayersAmmunition> ammunition;
    @JsonIdentityReference(alwaysAsId = true)
    private Set<Skill> skills;
    @JsonIdentityReference(alwaysAsId = true)
    private Set<Spell> spells;
    private Price money;
    private PersonalHistory personalHistory;

    @Override
    public String toCsv() {
        return new CsvBuilder(new ArrayList<>())
                .add(name)
                .add(getRace())
                .add(professionClass)
                .add(currentProfession)
                .add(Named.toCsv(career, "|"))
                .add(character)
                .add(appearance.toCsv())
                .add(Determinant.determinantsToCsv(determinants))
                .add(Named.toCsv(equipment, "|"))
                .add(Named.toCsv(commodities, "|"))
                .add(Documented.toCsv(ammunition, "|"))
                .add(Named.toCsv(skills, "|"))
                .add(money.toString())
                .add(Named.toCsv(spells, "|"))
                .add(personalHistory.toCsv())
                .build();
    }

    @JsonIgnore
    public Collection<HandWeapon> getHandWeapons() {
        return commodities.stream()
                .filter(e -> e instanceof HandWeapon)
                .map(e -> (HandWeapon) e)
                .collect(Collectors.toList());
    }

    @JsonIgnore
    public Collection<RangedWeapon> getRangedWeapons() {
        return commodities.stream()
                .filter(e -> e instanceof RangedWeapon)
                .map(e -> (RangedWeapon) e)
                .collect(Collectors.toList());
    }

    @JsonIgnore
    public Collection<ProtectiveWearings> getArmors() {
        return commodities.stream()
                .filter(e -> e instanceof ProtectiveWearings)
                .map(e -> (ProtectiveWearings) e)
                .collect(Collectors.toList());
    }

    @Override
    @JsonIgnore
    public String getSpecialFeatures() {
        return appearance.getDescription();
    }

    @Override
    public void setSpecialFeatures(String specialFeatures) {
        appearance.setDescription(specialFeatures);
    }
}
