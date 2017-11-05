package pl.khuzzuk.wfrpchar.entities.items.usable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Setter;
import pl.khuzzuk.wfrpchar.entities.Named;
import pl.khuzzuk.wfrpchar.entities.Persistable;
import pl.khuzzuk.wfrpchar.entities.determinants.Determinant;
import pl.khuzzuk.wfrpchar.entities.determinants.DeterminantsType;
import pl.khuzzuk.wfrpchar.entities.items.HandWeapon;
import pl.khuzzuk.wfrpchar.entities.items.types.WhiteWeaponType;
import pl.khuzzuk.wfrpchar.rules.Dices;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractHandWeapon<T extends WhiteWeaponType> extends AbstractWeapon<T> implements Named<String>, HandWeapon {

    @Setter
    private Dices dices;

    @Setter
    private int rolls;


    @Override
    public Dices getDices() {
        return dices == null ? getBaseType().getDices() : dices;
    }

    @Override
    public int getRolls() {
        return rolls < 1 ? getBaseType().getRolls() : rolls;
    }

    @Override
    @JsonIgnore
    public int getBattleMod() {
        return Determinant.getSumForType(getBaseDeterminants(), DeterminantsType.BATTLE);
    }

    @Override
    @JsonIgnore
    public int getInitiativeMod() {
        return Determinant.getSumForType(getBaseDeterminants(), DeterminantsType.INITIATIVE);
    }

    @Override
    @JsonIgnore
    public int getParryMod() {
        return Determinant.getSumForType(getBaseDeterminants(), DeterminantsType.PARRY);
    }

    @Override
    @JsonIgnore
    public int getOpponentParryMod() {
        return Determinant.getSumForType(getBaseDeterminants(), DeterminantsType.OPPONENT_PARRY);
    }

    public abstract void setBaseType(T baseType);
}
