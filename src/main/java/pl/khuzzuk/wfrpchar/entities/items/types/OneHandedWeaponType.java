package pl.khuzzuk.wfrpchar.entities.items.types;

import pl.khuzzuk.wfrpchar.entities.items.Placement;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("5")
public class OneHandedWeaponType extends WhiteWeaponType {
    public OneHandedWeaponType() {
        this.placement = Placement.ONE_HAND;
    }
}
