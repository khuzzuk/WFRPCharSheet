package pl.khuzzuk.wfrpchar.determinants;

import lombok.Getter;

import static pl.khuzzuk.wfrpchar.determinants.DeterminantsType.DetObjectType.ABSOLUTE;
import static pl.khuzzuk.wfrpchar.determinants.DeterminantsType.DetObjectType.PERCENTAGE;

enum DeterminantsType {
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

    DeterminantsType(String name, DetObjectType objectType) {
        this.name = name;
        this.objectType = objectType;
    }

    enum DetObjectType {
        PERCENTAGE, ABSOLUTE
    }
}
