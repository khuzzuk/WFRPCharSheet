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

    @Override
    public String toString() {
        return "OneHandedWeaponType{" +
                "name: " + name + ", weight: " + weight + ", " + price + ", accessibility: " + accessibility +
                ", strength: " + strength + ", " + determinants + ", dices: " + rolls + dices
                + "}";
    }
}
