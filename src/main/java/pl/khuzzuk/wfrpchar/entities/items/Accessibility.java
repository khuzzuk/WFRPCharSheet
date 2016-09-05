package pl.khuzzuk.wfrpchar.entities.items;

import lombok.Getter;
import org.apache.commons.collections4.SetUtils;
import pl.khuzzuk.wfrpchar.entities.Nameable;

import java.util.EnumSet;
import java.util.Set;

public enum Accessibility implements Nameable<String> {
    COMMON("Powszechny"), UNCOMMON("Trudno dostępny"), SCARCE("Niespotykany"), RARE("Rzadki"), EXCEPTIONAL("Niedostępny");

    public static Set<Accessibility> SET = SetUtils.unmodifiableSet(EnumSet.allOf(Accessibility.class));
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
