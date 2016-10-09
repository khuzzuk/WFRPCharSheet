package pl.khuzzuk.wfrpchar.entities.characters;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
public class PersonalHistory {
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
}
