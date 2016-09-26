package pl.khuzzuk.wfrpchar.entities.items.types;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import pl.khuzzuk.wfrpchar.entities.determinants.Determinant;
import pl.khuzzuk.wfrpchar.entities.determinants.DeterminantsType;
import pl.khuzzuk.wfrpchar.entities.items.HandWeapon;
import pl.khuzzuk.wfrpchar.entities.items.Placement;
import pl.khuzzuk.wfrpchar.rules.Dices;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

@DiscriminatorValue("4")
@Entity
@NoArgsConstructor
public abstract class WhiteWeaponType extends WeaponType implements HandWeapon {
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
    public int getBattleMod() {
        return Determinant.getSumForType(determinants, DeterminantsType.BATTLE);
    }

    @Override
    public int getInitiativeMod() {
        return Determinant.getSumForType(determinants, DeterminantsType.INITIATIVE);
    }

    @Override
    public int getParryMod() {
        return Determinant.getSumForType(determinants, DeterminantsType.PARRY);
    }

    @Override
    public int getOpponentParryMod() {
        return Determinant.getSumForType(determinants, DeterminantsType.OPPONENT_PARRY);
    }

    @Override
    @NotNull
    public String toCsv() {
        getLangToCsv();
        return name + ";" +
                weight + ";" +
                price.getGold() + "|" + price.getSilver() + "|" + price.getLead() + ";" +
                accessibility + ";" +
                specialFeatures + ";" +
                strength + ";" +
                type + ";" +
                placement + ";" +
                getLangToCsv() + ";" +
                Determinant.determinantsToCsv(determinants) + ";" +
                typeName + ";" +
                dices + ";" +
                rolls
                ;
    }

    public static WhiteWeaponType getFromPlacement(Placement placement) {
        switch (placement) {
            case ONE_HAND:
                return new OneHandedWeaponType();
            case TWO_HANDS:
                return new TwoHandedWeaponType();
            case BASTARD:
                return new BastardWeaponType();
        }
        throw new IllegalArgumentException("Wrong placement for a weapon: " + placement.getName());
    }
}
