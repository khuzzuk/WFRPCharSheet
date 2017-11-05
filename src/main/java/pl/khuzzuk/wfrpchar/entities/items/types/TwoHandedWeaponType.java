package pl.khuzzuk.wfrpchar.entities.items.types;

import pl.khuzzuk.wfrpchar.entities.items.Placement;

public class TwoHandedWeaponType extends WhiteWeaponType {
    public TwoHandedWeaponType() {
        this.placement = Placement.TWO_HANDS;
    }

    @Override
    public String toCsv() {
        return null;
    }
}
