package pl.khuzzuk.wfrpchar.entities.characters;

import lombok.Getter;
import lombok.Setter;
import pl.khuzzuk.wfrpchar.rules.Sex;

import javax.persistence.Embeddable;

@Embeddable
public class Appearance {
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
}
