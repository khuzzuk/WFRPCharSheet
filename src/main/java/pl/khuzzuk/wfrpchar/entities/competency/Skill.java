package pl.khuzzuk.wfrpchar.entities.competency;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;
import pl.khuzzuk.wfrpchar.entities.Documented;
import pl.khuzzuk.wfrpchar.entities.Featured;
import pl.khuzzuk.wfrpchar.entities.Persistable;
import pl.khuzzuk.wfrpchar.entities.determinants.Determinant;
import pl.khuzzuk.wfrpchar.entities.determinants.DeterminantFactory;
import pl.khuzzuk.wfrpchar.repo.TypeIdResolver;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id",
        resolver = TypeIdResolver.class,
        scope = Skill.class)
@Getter
@Setter
public class Skill implements Featured, Persistable, Documented {
    private long id;
    private String name;
    private Set<Determinant> determinants;
    private String specialFeatures;

    @Override
    public String toCsv() {
        return name + ";" + specialFeatures + ";" + Determinant.determinantsToCsv(determinants);
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
