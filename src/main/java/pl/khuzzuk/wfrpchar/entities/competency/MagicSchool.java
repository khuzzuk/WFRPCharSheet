package pl.khuzzuk.wfrpchar.entities.competency;

import lombok.Getter;
import lombok.Setter;
import pl.khuzzuk.wfrpchar.entities.CsvBuilder;
import pl.khuzzuk.wfrpchar.entities.Documented;
import pl.khuzzuk.wfrpchar.entities.Named;
import pl.khuzzuk.wfrpchar.entities.Persistable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;

@Entity
public class MagicSchool implements Named<String>, Persistable, Documented {
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
}
