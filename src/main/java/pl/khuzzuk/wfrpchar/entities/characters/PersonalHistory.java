package pl.khuzzuk.wfrpchar.entities.characters;

import lombok.Getter;
import lombok.Setter;
import pl.khuzzuk.wfrpchar.entities.Documented;

@Getter
@Setter
public class PersonalHistory implements Documented {
    private String history;
    private String birthplace;
    private String father;
    private String mother;
    private String siblings;

    @Override
    public String toCsv() {
        return history + ";" + birthplace + ";" + father + ";" + mother + ";" + siblings;
    }
}
