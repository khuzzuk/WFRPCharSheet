package pl.khuzzuk.wfrpchar.entities.competency;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;
import pl.khuzzuk.wfrpchar.entities.CsvBuilder;
import pl.khuzzuk.wfrpchar.entities.Documented;
import pl.khuzzuk.wfrpchar.entities.Featured;
import pl.khuzzuk.wfrpchar.entities.Persistable;
import pl.khuzzuk.wfrpchar.repo.TypeIdResolver;

import java.util.ArrayList;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id",
        resolver = TypeIdResolver.class,
        scope = MagicSchool.class)
@Getter
@Setter
public class MagicSchool implements Featured, Persistable, Documented {
    private long id;
    private String name;
    private String description;

    @Override
    public String toCsv() {
        return new CsvBuilder(new ArrayList<>()).add(name).add(description).build();
    }

    public static MagicSchool getFromCsv(String[] line) {
        MagicSchool school = new MagicSchool();
        school.setName(line[0]);
        if (line.length >= 2) {
            school.setDescription(line[1]);
        }
        return school;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    @JsonIgnore
    public String getSpecialFeatures() {
        return description;
    }

    @Override
    public void setSpecialFeatures(String specialFeatures) {
        description = specialFeatures;
    }
}
