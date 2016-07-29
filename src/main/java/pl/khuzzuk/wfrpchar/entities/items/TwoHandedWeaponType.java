package pl.khuzzuk.wfrpchar.entities.items;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("6")
public class TwoHandedWeaponType extends WhiteWeaponType {
    public TwoHandedWeaponType() {
        this.placement = Placement.TWO_HANDS;
    }

    @Override
    public String toCsv() {
        return null;
    }
}
