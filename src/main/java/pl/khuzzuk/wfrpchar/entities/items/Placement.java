package pl.khuzzuk.wfrpchar.entities.items;

import lombok.Getter;

public enum Placement {
    ONE_HAND("Jedna ręka"), TWO_HANDS("Dwie ręce"), BASTARD("Półtoraręczny"),
    HEAD("Głowa"), CORPUS("Tors"), HANDS("Ręce"), LEGS("Nogi"), BELT("Pas"), FEET("Stopy");

    @Getter
    private String name;

    Placement(String name) {
        this.name = name;
    }
}
