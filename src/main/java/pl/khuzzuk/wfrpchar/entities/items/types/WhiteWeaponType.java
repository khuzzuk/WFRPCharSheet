package pl.khuzzuk.wfrpchar.entities.items.types;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import pl.khuzzuk.wfrpchar.entities.determinants.Determinant;
import pl.khuzzuk.wfrpchar.entities.determinants.DeterminantsType;
import pl.khuzzuk.wfrpchar.entities.items.HandWeapon;
import pl.khuzzuk.wfrpchar.entities.items.Placement;
import pl.khuzzuk.wfrpchar.rules.Dices;

@Getter
@Setter
public abstract class WhiteWeaponType extends WeaponType implements HandWeapon {
    Dices dices;
    int rolls;

    @Override
    @JsonIgnore
    public int getBattleMod() {
        return Determinant.getSumForType(determinants, DeterminantsType.BATTLE);
    }

    @Override
    @JsonIgnore
    public int getInitiativeMod() {
        return Determinant.getSumForType(determinants, DeterminantsType.INITIATIVE);
    }

    @Override
    @JsonIgnore
    public int getParryMod() {
        return Determinant.getSumForType(determinants, DeterminantsType.PARRY);
    }

    @Override
    @JsonIgnore
    public int getOpponentParryMod() {
        return Determinant.getSumForType(determinants, DeterminantsType.OPPONENT_PARRY);
    }

    @Override
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
