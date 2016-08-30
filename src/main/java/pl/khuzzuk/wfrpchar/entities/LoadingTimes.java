package pl.khuzzuk.wfrpchar.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.EnumSet;

public enum LoadingTimes implements Nameable {
    WITH_SHOOTING("do końca rundy"),
    ROUND("runda"),
    TURN("tura");

    @Getter
    @Setter
    private final String name;

    LoadingTimes(String name) {
        this.name = name;
    }

    public static LoadingTimes forName(String name) {
        return EnumSet.allOf(LoadingTimes.class)
                .stream().filter(l -> l.getName().equals(name)).findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
