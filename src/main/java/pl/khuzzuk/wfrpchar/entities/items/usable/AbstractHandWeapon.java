package pl.khuzzuk.wfrpchar.entities.items.usable;

import lombok.Setter;
import pl.khuzzuk.wfrpchar.entities.Persistable;
import pl.khuzzuk.wfrpchar.entities.determinants.Determinant;
import pl.khuzzuk.wfrpchar.entities.determinants.DeterminantsType;
import pl.khuzzuk.wfrpchar.entities.items.HandWeapon;
import pl.khuzzuk.wfrpchar.entities.items.types.WhiteWeaponType;
import pl.khuzzuk.wfrpchar.rules.Dices;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("2")
public abstract class AbstractHandWeapon extends AbstractWeapon implements HandWeapon, Persistable {

    @Setter
    private Dices dices;

    @Setter
    private int rolls;

    public abstract WhiteWeaponType getBaseType();

    public abstract void setBaseType(WhiteWeaponType baseType);

    @Override
    public Dices getDices() {
        return dices == null ? getBaseType().getDices() : dices;
    }

    @Override
    public int getRolls() {
        return rolls < 1 ? getBaseType().getRolls() : rolls;
    }

    @Override
    public int getBattleMod() {
        return Determinant.getSumForType(getAllDeterminants(), DeterminantsType.BATTLE);
    }

    @Override
    public int getInitiativeMod() {
        return Determinant.getSumForType(getAllDeterminants(), DeterminantsType.INITIATIVE);
    }

    @Override
    public int getParryMod() {
        return Determinant.getSumForType(getAllDeterminants(), DeterminantsType.PARRY);
    }

    @Override
    public int getOpponentParryMod() {
        return Determinant.getSumForType(getAllDeterminants(), DeterminantsType.OPPONENT_PARRY);
    }
}
