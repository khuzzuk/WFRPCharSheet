package pl.khuzzuk.wfrpchar.entities.characters;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;
import pl.khuzzuk.wfrpchar.entities.Character;
import pl.khuzzuk.wfrpchar.entities.*;
import pl.khuzzuk.wfrpchar.entities.competency.Profession;
import pl.khuzzuk.wfrpchar.entities.competency.ProfessionClass;
import pl.khuzzuk.wfrpchar.entities.competency.Skill;
import pl.khuzzuk.wfrpchar.entities.competency.Spell;
import pl.khuzzuk.wfrpchar.entities.determinants.Determinant;
import pl.khuzzuk.wfrpchar.entities.determinants.DeterminantFactory;
import pl.khuzzuk.wfrpchar.entities.items.HandWeapon;
import pl.khuzzuk.wfrpchar.entities.items.ProtectiveWearings;
import pl.khuzzuk.wfrpchar.entities.items.RangedWeapon;
import pl.khuzzuk.wfrpchar.entities.items.types.Item;
import pl.khuzzuk.wfrpchar.entities.items.usable.AbstractCommodity;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor
@ToString(exclude = "id")
@Getter
@Setter
public class Player implements Featured, Persistable, Documented {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NaturalId
    private String name;
    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    private Race race;
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIdentityReference(alwaysAsId = true)
    private ProfessionClass professionClass;
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIdentityReference(alwaysAsId = true)
    private Profession currentProfession;
    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIdentityReference(alwaysAsId = true)
    private Set<Profession> career;
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIdentityReference(alwaysAsId = true)
    private Character character;
    private Appearance appearance;
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Determinant> determinants;
    @ManyToMany
    @JsonIdentityReference(alwaysAsId = true)
    private List<Item> equipment;
    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIdentityReference(alwaysAsId = true)
    private List<AbstractCommodity> commodities;
    @ManyToMany
    @JsonIdentityReference(alwaysAsId = true)
    private Set<PlayersAmmunition> ammunition;
    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIdentityReference(alwaysAsId = true)
    private Set<Skill> skills;
    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIdentityReference(alwaysAsId = true)
    private Set<Spell> spells;
    private Price money;
    private PersonalHistory personalHistory;

    public static Player fromCsv(String[] fields) {
        if (fields.length < 23) {
            throw new IllegalArgumentException("player lines have not enough fields, required 23, had " + fields.length);
        }
        Player player = new Player();
        player.setName(fields[0]);
        player.setAppearance(Appearance.getFromCsv(Arrays.copyOfRange(fields, 6, 13)));
        player.setDeterminants(DeterminantFactory.createDeterminants(fields[13]));
        player.setMoney(Price.parsePrice(fields[18]));
        player.setPersonalHistory(PersonalHistory.fromCsv(Arrays.copyOfRange(fields, 20, 25)));
        return player;
    }

    private static Set<PlayersAmmunition> convertAmmunition(String field[], String separator) {
        return new HashSet<>();
    }

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
