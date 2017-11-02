package pl.khuzzuk.wfrpchar.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;
import pl.khuzzuk.wfrpchar.db.DAO;
import pl.khuzzuk.wfrpchar.entities.competency.Skill;
import pl.khuzzuk.wfrpchar.entities.determinants.Determinant;
import pl.khuzzuk.wfrpchar.entities.determinants.DeterminantFactory;
import pl.khuzzuk.wfrpchar.repo.TypeIdResolver;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@ToString(exclude = "id")
@Getter
@Setter
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id",
        resolver = TypeIdResolver.class,
        scope = Race.class)
public class Race implements Named<String>, Persistable, Documented, SkillContainer, DeterminantContainer {
    @Id
    @GeneratedValue
    private long id;
    @NaturalId
    private String name;
    private String specialFeatures;
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Determinant> determinants;
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

    @Override
    @JsonIgnore
    public Collection<Determinant> getAllDeterminants() {
        return determinants;
    }
}
