package pl.khuzzuk.wfrpchar.determinants;

import lombok.Getter;
import lombok.Setter;

enum DeterminantsType {
    SPEED("Sz"), BATTLE("WW"), SHOOTING("US"), STRENGTH("S"), DURABILITY("Wt"), HEALTH("Wt"),
    INITIATIVE("I"), ATTACKS("A"), DEXTERITY("Zr"), LEADER_SKILLS("CP"), INTELLIGENCE("Int"),
    CONTROL("Op"), WILL("SW"), CHARISMA("Ogd");
    @Getter
    @Setter
    private String name;

    DeterminantsType(String name) {
        this.name = name;
    }
}
