package pl.khuzzuk.wfrpchar.entities.competency;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;
import pl.khuzzuk.wfrpchar.entities.DeterminantContainer;
import pl.khuzzuk.wfrpchar.entities.Featured;
import pl.khuzzuk.wfrpchar.entities.determinants.Determinant;
import pl.khuzzuk.wfrpchar.repo.TypeIdResolver;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.IntSequenceGenerator.class,
        property = "id",
        resolver = TypeIdResolver.class,
        scope = Skill.class)
@Getter
@Setter
public class Skill implements Featured, DeterminantContainer {
    private String name;
    private Set<Determinant> determinants;
    private String specialFeatures;

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

    @Override
    @JsonIgnore
    public Collection<Determinant> getAllDeterminants() {
        return determinants;
    }
}
