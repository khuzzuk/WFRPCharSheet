package pl.khuzzuk.wfrpchar.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.khuzzuk.wfrpchar.db.DAO;
import pl.khuzzuk.wfrpchar.entities.competency.Skill;
import pl.khuzzuk.wfrpchar.entities.determinants.Determinant;
import pl.khuzzuk.wfrpchar.entities.determinants.DeterminantFactory;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@ToString(exclude = "id")
public class Race implements Named<String>, Persistable, Documented, SkillContainer, DeterminantContainer {
    @Getter
    @Setter
    @Id
    @GeneratedValue
    private long id;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String specialFeatures;
    @Getter
    @Setter
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Determinant> determinants;
    @Getter
    @Setter
    @OneToMany(fetch = FetchType.EAGER)
    private Set<Skill> skills;

    @Override
    public String toCsv() {
        return name + ";" + specialFeatures + ";" + Determinant.determinantsToCsv(determinants) + ";" + Skill.collectionToCsv(skills);
    }

    public static Race fromCsv(String[] fields) {
        Race race = new Race();
        race.setName(fields[0]);
        race.setSpecialFeatures(fields[1]);
        race.setDeterminants(DeterminantFactory.createDeterminants(fields[2]));
        race.setSkills(new HashSet<>());
        return race;
    }

    public static Race fromCsv(String[] fields, DAO dao) {
        return SkillContainer.updateSkills(fromCsv(fields), fields, dao);
    }
}
