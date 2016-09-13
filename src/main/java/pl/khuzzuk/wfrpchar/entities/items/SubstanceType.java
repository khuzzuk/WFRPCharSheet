package pl.khuzzuk.wfrpchar.entities.items;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import pl.khuzzuk.wfrpchar.entities.Named;

import java.util.EnumSet;
import java.util.Set;

@AllArgsConstructor
@ToString
public enum SubstanceType implements Named<String> {
    TEXTILE("Materiał"), WOOD("Drewno"), METAL("Metal");

    private static Set<SubstanceType> SET = EnumSet.allOf(SubstanceType.class);

    @Getter
    private String name;

    public static SubstanceType forName(String name) {
        return SET.stream().filter(a -> a.getName().equals(name)).findAny().orElse(null);
    }
}
