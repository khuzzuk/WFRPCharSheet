package pl.khuzzuk.wfrpchar.entities.items;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import pl.khuzzuk.wfrpchar.rules.Dices;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@DiscriminatorValue("4")
@Entity
public abstract class WhiteWeaponType extends WeaponType {
    @Enumerated(value = EnumType.STRING)
    @NonNull
    @Getter
    @Setter
    Dices dices;
    @NonNull
    @Getter
    @Setter
    int rolls;
    @Override
    public String toCsv() {
        getLangToCsv();
        return name + ";" +
                weight + ";" +
                price.getGold() + "|" + price.getSilver() + "|" + price.getLead() + ";" +
                accessibility + ";" +
                specialFeature + ";" +
                strength + ";" +
                type + ";" +
                placement + ";" +
                getLangToCsv() + ";" +
                determinantsToCsv(determinants) + ";" +
                typeName + ";" +
                dices + ";" +
                rolls
                ;
    }
}
