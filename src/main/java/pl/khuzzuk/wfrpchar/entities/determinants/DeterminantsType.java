package pl.khuzzuk.wfrpchar.entities.determinants;

import lombok.Getter;
import org.apache.commons.collections4.SetUtils;

import java.util.EnumSet;
import java.util.Set;

import static pl.khuzzuk.wfrpchar.entities.determinants.DeterminantsType.DetObjectType.ABSOLUTE;
import static pl.khuzzuk.wfrpchar.entities.determinants.DeterminantsType.DetObjectType.PERCENTAGE;

public enum DeterminantsType {
    SPEED("Sz", ABSOLUTE),
    BATTLE("WW", PERCENTAGE),
    SHOOTING("US", PERCENTAGE),
    STRENGTH("S", ABSOLUTE),
    DURABILITY("Wt", ABSOLUTE),
    HEALTH("Wt", ABSOLUTE),
    INITIATIVE("I", PERCENTAGE),
    ATTACKS("A", ABSOLUTE),
    DEXTERITY("Zr", PERCENTAGE),
    LEADER_SKILLS("CP", PERCENTAGE),
    INTELLIGENCE("Int", PERCENTAGE),
    CONTROL("Op", PERCENTAGE),
    WILL("SW", PERCENTAGE),
    CHARISMA("Ogd", PERCENTAGE),
    PARRY("P", PERCENTAGE),
    OPPONENT_PARRY("PP", PERCENTAGE);
    @Getter
    private final String name;
    @Getter
    private final DetObjectType objectType;
    public static final Set<DeterminantsType> SET = SetUtils.unmodifiableSet(EnumSet.allOf(DeterminantsType.class));

    DeterminantsType(String name, DetObjectType objectType) {
        this.name = name;
        this.objectType = objectType;
    }

    public DeterminantsType forName(String name) {
        return EnumSet.allOf(DeterminantsType.class)
                .stream().filter(d -> d.getName().equals(name))
                .findFirst().orElseThrow(IllegalArgumentException::new);
    }

    enum DetObjectType {
        PERCENTAGE, ABSOLUTE
    }
}
