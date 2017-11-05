package pl.khuzzuk.wfrpchar.entities.items.types;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import pl.khuzzuk.wfrpchar.entities.LoadingTimes;
import pl.khuzzuk.wfrpchar.entities.items.RangedWeapon;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class RangedWeaponType extends WeaponType implements RangedWeapon {
    int shortRange;
    int effectiveRange;
    int maximumRange;
    LoadingTimes reloadTime;

    public RangedWeaponType() {
        type = EquipmentType.RANGED_WEAPON;
    }
}
