package pl.khuzzuk.wfrpchar.entities.characters;

import lombok.Getter;
import lombok.Setter;
import pl.khuzzuk.wfrpchar.entities.Documented;

import javax.persistence.Embeddable;

@Embeddable
public class PersonalHistory implements Documented {
    @Getter
    @Setter
    private String history;
    @Getter
    @Setter
    private String birthplace;
    @Getter
    @Setter
    private String father;
    @Getter
    @Setter
    private String mother;
    @Getter
    @Setter
    private String siblings;

    @Override
    public String toCsv() {
        return history + ";" + birthplace + ";" + father + ";" + mother + ";" + siblings;
    }

    public static PersonalHistory fromCsv(String[] fields) {
        PersonalHistory history = new PersonalHistory();
        history.setHistory(fields[0]);
        history.setBirthplace(fields[1]);
        history.setFather(fields[2]);
        history.setMother(fields[3]);
        history.setSiblings(fields[4]);
        return history;
    }
}
