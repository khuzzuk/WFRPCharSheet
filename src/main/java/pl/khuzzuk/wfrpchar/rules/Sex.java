package pl.khuzzuk.wfrpchar.rules;

import lombok.Getter;
import pl.khuzzuk.wfrpchar.entities.Named;

public enum Sex implements Named<String> {
    MALE("Mężczyzna"), FEMALE("Kobieta");
    @Getter
    private final String name;

    Sex(String name) {
        this.name = name;
    }

    public static Sex forName(String name) {
        switch (name) {
            case "Mężczyzna":
                return MALE;
            case "Kobieta":
                return MALE;
            default:
                throw new IllegalArgumentException("cannot find Sex for " + name);
        }
    }
}
