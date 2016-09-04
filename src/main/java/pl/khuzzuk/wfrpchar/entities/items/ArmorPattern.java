package pl.khuzzuk.wfrpchar.entities.items;

import lombok.Getter;
import pl.khuzzuk.wfrpchar.entities.LangElement;
import pl.khuzzuk.wfrpchar.entities.Nameable;

import java.util.Map;

public enum ArmorPattern implements Nameable<String> {
    TEXTILE("Wyszywany", 10, 10, 15, "wyszywany|wyszywaną|wyszywane|wyszywanym"),
    LEATHER("Skórzany", 50, 50, 50, "skórzany|skórzana|skórzane|skórzanym"),
    MAIL("kolczóga", 100, 100, 200, "kolczy|kolcza|kolcze|kolczym"),
    LAMELLAR("Lamelkowy", 125, 125, 150, "lamelkowy|lamelkowa|lamelkowe|lamelkowym"),
    SPLINT("Brygantyna", 130, 150, 145, "paskowy|paskowa|paskowe|brygantyną"),
    SCALE("Łuskowy", 120, 120, 130, "łuskowy|łuskowa|łuskowe|łuskowym"),
    PLATE("Płytowy", 200, 200, 300, "płytowy|płytowa|płytowe|płytowym");

    @Getter
    private String name;
    public final int strengthMod;
    public final int weightMod;
    public final int priceMod;
    public final Map<LangElement, String> langElements;

    ArmorPattern(String name, int strengthMod, int weightMod, int priceMod, String langElements) {
        this.name = name;
        this.strengthMod = strengthMod;
        this.weightMod = weightMod;
        this.priceMod = priceMod;
        this.langElements = LangElement.parseLang(langElements);
    }
}
