package pl.khuzzuk.wfrpchar.entities.competency;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;
import pl.khuzzuk.wfrpchar.entities.Documented;
import pl.khuzzuk.wfrpchar.entities.Featured;
import pl.khuzzuk.wfrpchar.entities.Persistable;
import pl.khuzzuk.wfrpchar.entities.determinants.Determinant;
import pl.khuzzuk.wfrpchar.entities.determinants.DeterminantFactory;
import pl.khuzzuk.wfrpchar.repo.TypeIdResolver;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id",
        resolver = TypeIdResolver.class,
        scope = Skill.class)
public class Skill implements Featured, Persistable, Documented {
    @Getter
    @Setter
    @Id
    @GeneratedValue
    private long id;
    @Getter
    @Setter
    @NaturalId
    private String name;
    @Getter
    @Setter
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "DET_SKILL_MAP",
            joinColumns = {@JoinColumn(name = "SKILL_ID")},
            inverseJoinColumns = {@JoinColumn(name = "DET_ID")})
    private Set<Determinant> determinants;
    @Getter
    @Setter
    private String specialFeatures;

    @Override
    public String toCsv() {
        return name + ";" + specialFeatures + ";" + Determinant.determinantsToCsv(determinants);
    }

    public static Skill fromCsv(String[] fields) {
        Skill skill = new Skill();
        skill.name = fields[0];
        if (fields.length >= 2) {
            skill.specialFeatures = fields[1];
        } else {
            skill.specialFeatures = "";
        }
        if (fields.length >= 3) {
            skill.determinants = DeterminantFactory.createDeterminants(fields[2]);
        } else {
            skill.determinants = new HashSet<>();
        }
        return skill;
    }

    public static String collectionToCsv(Collection<Skill> skills) {
        return skills == null ? "" : skills.stream().map(Skill::getName).collect(Collectors.joining("|"));
    }

    @Override
    public boolean equals(Object o) {
        return namedEquals(o);
    }

    @Override
    public int hashCode() {
        return namedHashCode();
    }

    @Override
    public String toString() {
        return name;
    }
}
