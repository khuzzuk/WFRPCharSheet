package pl.khuzzuk.wfrpchar.entities;

import lombok.Getter;
import org.apache.commons.collections4.SetUtils;

import java.util.EnumSet;
import java.util.Set;

public enum LoadingTimes implements Named<String> {
    WITH_SHOOTING("do końca rundy"),
    ROUND("runda"),
    TURN("tura");

    @Getter
    private final String name;
    public static final Set<LoadingTimes> SET = SetUtils.unmodifiableSet(EnumSet.allOf(LoadingTimes.class));

    LoadingTimes(String name) {
        this.name = name;
    }

    public static LoadingTimes forName(String name) {
        return EnumSet.allOf(LoadingTimes.class)
                .stream().filter(l -> l.getName().equals(name)).findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
