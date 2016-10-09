package pl.khuzzuk.wfrpchar.entities.characters;

import lombok.Getter;
import pl.khuzzuk.wfrpchar.entities.Named;

import java.util.EnumSet;
import java.util.Set;

public enum EyesColor implements Named<String> {
    BLUE("Niebieskie"), LIGHT_BLUE("Jasno niebieskie"), AMBER("Bursztynowe"),
    BROWN("Brązowe"), GREY("Szare"), GREEN("Zielone"), HAZEL("Piwne"),
    RED("Czerwone"), VIOLET("Fioletowe");

    public static final Set<EyesColor> SET = EnumSet.allOf(EyesColor.class);

    @Getter
    private final String name;

    EyesColor(String name) {
        this.name = name;
    }

    public static EyesColor forName(String name) {
        return SET.stream().filter(e -> e.name.equals(name)).findAny().orElseThrow(IllegalArgumentException::new);
    }
}
