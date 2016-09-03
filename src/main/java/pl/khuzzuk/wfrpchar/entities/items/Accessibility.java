package pl.khuzzuk.wfrpchar.entities.items;

import lombok.Getter;
import pl.khuzzuk.wfrpchar.entities.Nameable;

public enum Accessibility implements Nameable<String> {
    COMMON("Powszechny"), UNCOMMON("Trudno dostępny"), SCARCE("Niespotykany"), RARE("Rzadki"), EXCEPTIONAL("Niedostępny");

    @Getter
    private String name;

    Accessibility(String name) {
        this.name = name;
    }

    public static Accessibility forName(String name) {
        switch (name) {
            case "Powszechny":
                return COMMON;
            case "Trudno dostępny":
                return UNCOMMON;
            case "Niespotykany":
                return SCARCE;
            case "Rzadki":
                return RARE;
            case "Niedostępny":
                return EXCEPTIONAL;
            default:
                throw new IllegalArgumentException("cannot resolve Accessibility level enum with " + name);
        }
    }
}
