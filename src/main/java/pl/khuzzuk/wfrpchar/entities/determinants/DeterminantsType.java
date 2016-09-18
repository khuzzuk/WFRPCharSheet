package pl.khuzzuk.wfrpchar.entities.determinants;

import lombok.Getter;
import org.apache.commons.collections4.SetUtils;
import pl.khuzzuk.wfrpchar.entities.Named;

import java.util.EnumSet;
import java.util.Set;
import java.util.function.Supplier;

import static pl.khuzzuk.wfrpchar.entities.determinants.DeterminantsType.DetObjectType.ABSOLUTE;
import static pl.khuzzuk.wfrpchar.entities.determinants.DeterminantsType.DetObjectType.PERCENTAGE;

public enum DeterminantsType implements Named<String> {
    SPEED("Sz", ABSOLUTE, AbsoluteDeterminant::new),
    BATTLE("WW", PERCENTAGE, PercentageDeterminant::new),
    SHOOTING("US", PERCENTAGE, PercentageDeterminant::new),
    STRENGTH("S", ABSOLUTE, AbsoluteDeterminant::new),
    DURABILITY("Wt", ABSOLUTE, AbsoluteDeterminant::new),
    HEALTH("Wt", ABSOLUTE, AbsoluteDeterminant::new),
    INITIATIVE("I", PERCENTAGE, PercentageDeterminant::new),
    ATTACKS("A", ABSOLUTE, AbsoluteDeterminant::new),
    DEXTERITY("Zr", PERCENTAGE, PercentageDeterminant::new),
    LEADER_SKILLS("CP", PERCENTAGE, PercentageDeterminant::new),
    INTELLIGENCE("Int", PERCENTAGE, PercentageDeterminant::new),
    CONTROL("Op", PERCENTAGE, PercentageDeterminant::new),
    WILL("SW", PERCENTAGE, PercentageDeterminant::new),
    CHARISMA("Ogd", PERCENTAGE, PercentageDeterminant::new),
    PARRY("P", PERCENTAGE, PercentageDeterminant::new),
    OPPONENT_PARRY("PP", PERCENTAGE, PercentageDeterminant::new);
    @Getter
    private final String name;
    @Getter
    private final DetObjectType objectType;
    private final Supplier<Determinant> supplier;
    public static final Set<DeterminantsType> SET = SetUtils.unmodifiableSet(EnumSet.allOf(DeterminantsType.class));

    DeterminantsType(String name, DetObjectType objectType, Supplier<Determinant> supplier) {
        this.name = name;
        this.objectType = objectType;
        this.supplier = supplier;
    }

    public static DeterminantsType forName(String name) {
        return EnumSet.allOf(DeterminantsType.class)
                .stream().filter(d -> d.getName().equals(name))
                .findFirst().orElseThrow(IllegalArgumentException::new);
    }

    enum DetObjectType {
        PERCENTAGE, ABSOLUTE
    }

    public Determinant getDeterminant() {
        return supplier.get();
    }
}
