package pl.khuzzuk.wfrpchar.entities.characters;

import lombok.Getter;
import pl.khuzzuk.wfrpchar.entities.Named;

import java.util.EnumSet;
import java.util.Set;

public enum HairColor implements Named<String> {
    BLACK("Czarne"), BRUNETTE("Czarnobrunatne"), BROWN("Szatynowe"), RED("Rude"), DARK_BLONDE("Ciemno blond"), BLONDE("Blond"),
    GREY("Siwe"), WHITE("Białe"), BOLD("Łyse");

    public static final Set<HairColor> SET = EnumSet.allOf(HairColor.class);

    @Getter
    private final String name;

    HairColor(String name) {
        this.name = name;
    }

    public static HairColor forName(String name) {
        return SET.stream().filter(e -> e.name.equals(name)).findAny().orElseThrow(IllegalArgumentException::new);
    }
}
