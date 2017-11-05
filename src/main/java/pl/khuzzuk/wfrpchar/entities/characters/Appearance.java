package pl.khuzzuk.wfrpchar.entities.characters;

import lombok.Data;
import pl.khuzzuk.wfrpchar.rules.Sex;

@Data
public class Appearance {
    private Sex sex;
    private int age;
    private int height;
    private int weight;
    private EyesColor eyesColor;
    private HairColor hairColor;
    private String description;
}
