package pl.khuzzuk.wfrpchar.entities.items.usable;

import lombok.Getter;
import pl.khuzzuk.wfrpchar.entities.items.Placement;
import pl.khuzzuk.wfrpchar.entities.items.types.TwoHandedWeaponType;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("4")
public class TwoHandedWeapon extends AbstractHandWeapon<TwoHandedWeaponType> {
    @Getter
    @ManyToOne
    private TwoHandedWeaponType baseType;

    @Override
    public void setBaseType(TwoHandedWeaponType baseType) {
        this.baseType = baseType;
    }

    @Override
    public Placement getPlacement() {
        return baseType.getPlacement();
    }
}
