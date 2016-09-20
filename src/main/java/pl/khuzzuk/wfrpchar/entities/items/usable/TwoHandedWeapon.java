package pl.khuzzuk.wfrpchar.entities.items.usable;

import lombok.Getter;
import pl.khuzzuk.wfrpchar.entities.items.types.TwoHandedWeaponType;
import pl.khuzzuk.wfrpchar.entities.items.types.WhiteWeaponType;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("4")
public class TwoHandedWeapon extends AbstractHandWeapon {
    @Getter
    @ManyToOne
    private TwoHandedWeaponType baseType;

    @Override
    public void setBaseType(WhiteWeaponType baseType) {
        this.baseType = (TwoHandedWeaponType) baseType;
    }
}
