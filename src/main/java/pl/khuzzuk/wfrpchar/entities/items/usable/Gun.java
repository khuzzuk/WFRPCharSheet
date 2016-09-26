package pl.khuzzuk.wfrpchar.entities.items.usable;

import lombok.Getter;
import lombok.Setter;
import pl.khuzzuk.wfrpchar.entities.LoadingTimes;
import pl.khuzzuk.wfrpchar.entities.Persistable;
import pl.khuzzuk.wfrpchar.entities.items.RangedWeapon;
import pl.khuzzuk.wfrpchar.entities.items.types.RangedWeaponType;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("6")
public class Gun extends AbstractWeapon implements RangedWeapon, Persistable {
    @Getter
    @Setter
    @ManyToOne
    private RangedWeaponType baseType;

    @Override
    public int getShortRange() {
        return baseType != null ? baseType.getShortRange() : 0;
    }

    @Override
    public int getEffectiveRange() {
        return baseType != null ? baseType.getEffectiveRange() : 0;
    }

    @Override
    public int getMaximumRange() {
        return baseType != null ? baseType.getMaximumRange() : 0;
    }

    @Override
    public LoadingTimes getReloadTime() {
        return baseType != null ? baseType.getReloadTime() : null;
    }
}
