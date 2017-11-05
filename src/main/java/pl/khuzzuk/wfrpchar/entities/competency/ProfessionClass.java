package pl.khuzzuk.wfrpchar.entities.competency;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.khuzzuk.wfrpchar.entities.Documented;
import pl.khuzzuk.wfrpchar.entities.Persistable;
import pl.khuzzuk.wfrpchar.entities.SkillContainer;
import pl.khuzzuk.wfrpchar.entities.determinants.DeterminantsType;
import pl.khuzzuk.wfrpchar.repo.TypeIdResolver;

import java.util.Set;

@ToString(of = "name")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id",
        resolver = TypeIdResolver.class,
        scope = ProfessionClass.class)
@Getter
@Setter
public class ProfessionClass implements Persistable, Documented, SkillContainer {
    private long id;
    private String name;
    private String specialFeatures;
    private DeterminantsType primaryDeterminant;
    @JsonIdentityReference(alwaysAsId = true)
    private Set<Skill> skills;

    @Override
    public String toCsv() {
        return name + ";" + specialFeatures + ";" + primaryDeterminant.name() + ";" + Skill.collectionToCsv(skills);
    }

    public static ProfessionClass fromCsv(String[] fields) {
        ProfessionClass professionClass = new ProfessionClass();
        professionClass.setName(fields[0]);
        professionClass.setSpecialFeatures(fields[1]);
        professionClass.setPrimaryDeterminant(DeterminantsType.valueOf(fields[2]));
        return professionClass;
    }
}
