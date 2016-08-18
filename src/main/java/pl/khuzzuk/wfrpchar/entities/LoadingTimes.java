package pl.khuzzuk.wfrpchar.entities;

import lombok.Getter;
import lombok.Setter;

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
}
