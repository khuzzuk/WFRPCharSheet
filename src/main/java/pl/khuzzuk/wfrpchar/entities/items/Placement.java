package pl.khuzzuk.wfrpchar.entities.items;

import lombok.Getter;
import pl.khuzzuk.wfrpchar.entities.Named;

import java.util.EnumSet;

public enum Placement implements Named<String> {
    ONE_HAND("Jedna ręka"), TWO_HANDS("Dwie ręce"), BASTARD("Półtoraręczny"),
    HEAD("Głowa"), CORPUS("Tors"), HANDS("Ręce"), LEGS("Nogi"), BELT("Pas"), FEET("Stopy");

    @Getter
    private String name;

    Placement(String name) {
        this.name = name;
    }

    public static Placement forName(String name) {
        return EnumSet.allOf(Placement.class)
                .stream().filter(p -> name.equals(p.name)).findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
