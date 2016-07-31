package pl.khuzzuk.wfrpchar.entities.items;

import lombok.*;
import pl.khuzzuk.wfrpchar.rules.Dices;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

@DiscriminatorValue("4")
@Entity
@NoArgsConstructor
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
    @NotNull
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WhiteWeaponType that = (WhiteWeaponType) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (dices != null ? dices.hashCode() : 0);
        result = 31 * result + rolls;
        return result;
    }
}
