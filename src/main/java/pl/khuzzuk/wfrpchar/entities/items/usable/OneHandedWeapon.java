package pl.khuzzuk.wfrpchar.entities.items.usable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.khuzzuk.wfrpchar.entities.determinants.Determinant;
import pl.khuzzuk.wfrpchar.entities.determinants.DeterminantsType;
import pl.khuzzuk.wfrpchar.entities.items.HandWeapon;
import pl.khuzzuk.wfrpchar.entities.items.types.WhiteWeaponType;
import pl.khuzzuk.wfrpchar.rules.Dices;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("2")
@NoArgsConstructor
public class OneHandedWeapon extends AbstractWeapon implements HandWeapon {
    @Getter
    @Setter
    @ManyToOne
    WhiteWeaponType baseType;

    @Override
    public Dices getDices() {
        return baseType.getDices();
    }

    @Override
    public int getRolls() {
        return baseType.getRolls();
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
