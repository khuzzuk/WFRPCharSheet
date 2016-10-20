package pl.khuzzuk.wfrpchar.entities.characters;

import lombok.Getter;
import lombok.Setter;
import pl.khuzzuk.wfrpchar.entities.Documented;
import pl.khuzzuk.wfrpchar.rules.Sex;

import javax.persistence.Embeddable;

@Embeddable
public class Appearance implements Documented {
    @Getter
    @Setter
    private Sex sex;
    @Getter
    @Setter
    private int age;
    @Getter
    @Setter
    private int height;
    @Getter
    @Setter
    private int weight;
    @Getter
    @Setter
    private EyesColor eyesColor;
    @Getter
    @Setter
    private HairColor hairColor;
    @Getter
    @Setter
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
