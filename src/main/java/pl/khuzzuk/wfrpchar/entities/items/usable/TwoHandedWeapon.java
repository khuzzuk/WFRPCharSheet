package pl.khuzzuk.wfrpchar.entities.items.usable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import pl.khuzzuk.wfrpchar.entities.items.Placement;
import pl.khuzzuk.wfrpchar.entities.items.types.TwoHandedWeaponType;

@Getter
@Setter
public class TwoHandedWeapon extends AbstractHandWeapon<TwoHandedWeaponType> {
    private TwoHandedWeaponType baseType;

    @Override
    @JsonIgnore
    public Placement getPlacement() {
        return baseType.getPlacement();
    }

    @Override
    public void setPlacement(Placement placement) {
        throw new UnsupportedOperationException();
    }
}
