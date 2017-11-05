package pl.khuzzuk.wfrpchar.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.khuzzuk.wfrpchar.entities.competency.Skill;
import pl.khuzzuk.wfrpchar.entities.determinants.Determinant;
import pl.khuzzuk.wfrpchar.repo.TypeIdResolver;

import java.util.Collection;
import java.util.Set;

@ToString(exclude = "id")
@Getter
@Setter
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id",
        resolver = TypeIdResolver.class,
        scope = Race.class)
public class Race implements Named<String>, Persistable, Documented, SkillContainer, DeterminantContainer {
    private long id;
    private String name;
    private String specialFeatures;
    private Set<Determinant> determinants;
    private Set<Skill> skills;

    @Override
    public String toCsv() {
        return name + ";" + specialFeatures + ";" + Determinant.determinantsToCsv(determinants) + ";" + Skill.collectionToCsv(skills);
    }

    @Override
    @JsonIgnore
    public Collection<Determinant> getAllDeterminants() {
        return determinants;
    }
}
