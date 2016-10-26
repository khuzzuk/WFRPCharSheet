package pl.khuzzuk.wfrpchar.entities.characters;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.collections4.MultiValuedMap;
import pl.khuzzuk.wfrpchar.db.DAO;
import pl.khuzzuk.wfrpchar.entities.Character;
import pl.khuzzuk.wfrpchar.entities.*;
import pl.khuzzuk.wfrpchar.entities.competency.Profession;
import pl.khuzzuk.wfrpchar.entities.competency.ProfessionClass;
import pl.khuzzuk.wfrpchar.entities.competency.Skill;
import pl.khuzzuk.wfrpchar.entities.determinants.Determinant;
import pl.khuzzuk.wfrpchar.entities.determinants.DeterminantFactory;
import pl.khuzzuk.wfrpchar.entities.items.Commodity;
import pl.khuzzuk.wfrpchar.entities.items.types.Item;
import pl.khuzzuk.wfrpchar.entities.items.usable.AbstractCommodity;

import javax.persistence.*;
import java.util.*;

@Entity
@NoArgsConstructor
@ToString(exclude = "id")
public class Player implements Named<String>, Persistable, Documented {
    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    private ProfessionClass professionClass;
    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    private Profession currentProfession;
    @Getter
    @Setter
    @OneToMany(fetch = FetchType.EAGER)
    private Set<Profession> career;
    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    private Character character;
    @Getter
    @Setter
    private Appearance appearance;
    @Getter
    @Setter
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Determinant> determinants;
    @Getter
    @Setter
    @ManyToMany
    private List<Item> equipment;
    @Getter
    @Setter
    @ManyToMany(fetch = FetchType.EAGER)
    private List<AbstractCommodity> commodities;
    @Transient
    private MultiValuedMap<Class<?>, Commodity> items;
    @Getter
    @Setter
    @OneToMany(fetch = FetchType.EAGER)
    private Set<Skill> skills;
    @Getter
    @Setter
    private Price money;
    @Getter
    @Setter
    private PersonalHistory personalHistory;

    public static Player fromCsv(String[] fields, DAO dao) {
        if (fields.length < 22) {
            throw new IllegalArgumentException("player lines have not enough fields, required 23, had " + fields.length);
        }
        Player player = new Player();
        player.setName(fields[0]);
        player.setProfessionClass(dao.getEntity(ProfessionClass.class, fields[1]));
        player.setCurrentProfession(dao.getEntity(Profession.class, fields[2]));
        player.setCareer(dao.getEntities(Profession.class, fields[3].split("\\|")));
        player.setCharacter(dao.getEntity(Character.class, fields[4]));
        player.setAppearance(Appearance.getFromCsv(Arrays.copyOfRange(fields, 5, 12)));
        player.setDeterminants(DeterminantFactory.createDeterminants(fields[12]));
        player.setEquipment(dao.getEntitiesAsList(Item.class, fields[13].split("\\|")));
        player.setCommodities(dao.getEntitiesAsList(AbstractCommodity.class, fields[14].split("\\|")));
        player.setSkills(dao.getEntities(Skill.class, fields[15].split("\\|")));
        player.setMoney(Price.parsePrice(fields[16]));
        player.setPersonalHistory(PersonalHistory.fromCsv(Arrays.copyOfRange(fields, 17, 22)));
        return player;
    }

    @Override
    public String toCsv() {
        return new CsvBuilder(new ArrayList<>())
                .add(name)
                .add(professionClass)
                .add(currentProfession)
                .add(Named.toCsv(career, "|"))
                .add(character)
                .add(appearance.toCsv())
                .add(Determinant.determinantsToCsv(determinants))
                .add(Named.toCsv(equipment, "|"))
                .add(Named.toCsv(commodities, "|"))
                .add(Named.toCsv(skills, "|"))
                .add(money.toString())
                .add(personalHistory.toCsv())
                .build();
    }
}
