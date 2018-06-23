package pl.khuzzuk.wfrpchar.entities.competency;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.khuzzuk.wfrpchar.entities.DeterminantContainer;
import pl.khuzzuk.wfrpchar.entities.SkillContainer;
import pl.khuzzuk.wfrpchar.entities.determinants.Determinant;
import pl.khuzzuk.wfrpchar.entities.determinants.DeterminantsType;
import pl.khuzzuk.wfrpchar.repo.TypeIdResolver;

import java.util.Collection;
import java.util.Set;

@ToString(of = "name")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.IntSequenceGenerator.class,
        property = "id",
        resolver = TypeIdResolver.class,
        scope = ProfessionClass.class)
@Getter
@Setter
public class ProfessionClass implements SkillContainer, DeterminantContainer {
    private String name;
    private String specialFeatures;
    private DeterminantsType primaryDeterminant;
    @JsonIdentityReference(alwaysAsId = true)
    private Set<Skill> skills;

    @Override
    @JsonIgnore
    public Collection<Determinant> getAllDeterminants() {
        throw new UnsupportedOperationException();
    }

    @Override
    @JsonIgnore
    public Collection<Determinant> getDeterminants() {
        throw new UnsupportedOperationException();
    }

    @Override
    @JsonIgnore
    public void setDeterminants(Set<Determinant> determinants) {
        throw new UnsupportedOperationException();
    }
}
