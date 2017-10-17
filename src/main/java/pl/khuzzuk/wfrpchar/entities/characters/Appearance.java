package pl.khuzzuk.wfrpchar.entities.characters;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import pl.khuzzuk.wfrpchar.entities.Documented;
import pl.khuzzuk.wfrpchar.rules.Sex;

import javax.persistence.Embeddable;

@Embeddable
@Data
public class Appearance implements Documented {
    private Sex sex;
    private int age;
    private int height;
    private int weight;
    private EyesColor eyesColor;
    private HairColor hairColor;
    private String description;

    public static Appearance getFromCsv(String[] fields) {
        Appearance appearance = new Appearance();
        appearance.setSex(Sex.valueOf(fields[0]));
        appearance.setAge(Integer.parseInt(fields[1]));
        appearance.setHeight(Integer.parseInt(fields[2]));
        appearance.setWeight(Integer.parseInt(fields[3]));
        appearance.setEyesColor(EyesColor.valueOf(fields[4]));
        appearance.setHairColor(HairColor.valueOf(fields[5]));
        appearance.setDescription(fields[6]);
        return appearance;
    }

    @Override
    public String toCsv() {
        return sex + ";" + age + ";" + height + ";" + weight + ";" +
                eyesColor + ";" + hairColor + ";" + description;
    }
}
