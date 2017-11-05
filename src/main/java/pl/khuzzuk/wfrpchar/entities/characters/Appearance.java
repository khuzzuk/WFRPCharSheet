package pl.khuzzuk.wfrpchar.entities.characters;

import lombok.Data;
import pl.khuzzuk.wfrpchar.entities.Documented;
import pl.khuzzuk.wfrpchar.rules.Sex;

@Data
public class Appearance implements Documented {
    private Sex sex;
    private int age;
    private int height;
    private int weight;
    private EyesColor eyesColor;
    private HairColor hairColor;
    private String description;

    @Override
    public String toCsv() {
        return sex + ";" + age + ";" + height + ";" + weight + ";" +
                eyesColor + ";" + hairColor + ";" + description;
    }
}
