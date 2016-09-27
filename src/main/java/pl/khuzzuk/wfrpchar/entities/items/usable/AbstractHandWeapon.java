package pl.khuzzuk.wfrpchar.entities.items.usable;

import lombok.Setter;
import pl.khuzzuk.wfrpchar.entities.Named;
import pl.khuzzuk.wfrpchar.entities.Persistable;
import pl.khuzzuk.wfrpchar.entities.determinants.Determinant;
import pl.khuzzuk.wfrpchar.entities.determinants.DeterminantsType;
import pl.khuzzuk.wfrpchar.entities.items.HandWeapon;
import pl.khuzzuk.wfrpchar.entities.items.types.WhiteWeaponType;
import pl.khuzzuk.wfrpchar.rules.Dices;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@DiscriminatorValue("2")
public abstract class AbstractHandWeapon<T extends WhiteWeaponType> extends AbstractWeapon implements Named<String>, HandWeapon, Persistable {

    @Setter
    private Dices dices;

    @Setter
    private int rolls;

    public abstract WhiteWeaponType getBaseType();

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

    @Override
    public String toCsv() {
        List<String> fields = new ArrayList<>();
        fillCommodityFields(fields);
        fillHandWeaponCsvFields(fields);
        if (dices != null) {
            fields.add(dices.name());
        } else {
            fields.add("");
        }
        return fields.stream().collect(Collectors.joining(";"));
    }


    void fillHandWeaponCsvFields(List<String> fields) {
        fields.add(getBaseType().getName());
        if (getPrimaryResource() != null) {
            fields.add(getPrimaryResource().getName());
        } else {
            fields.add("");
        }
        if (getSecondaryResource() != null) {
            fields.add(getSecondaryResource().getName());
        } else {
            fields.add("");
        }
        fields.add(Determinant.determinantsToCsv(getDeterminants()));
    }

    public abstract void setBaseType(T baseType);
}
