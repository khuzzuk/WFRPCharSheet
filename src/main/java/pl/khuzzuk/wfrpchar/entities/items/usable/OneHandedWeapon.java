package pl.khuzzuk.wfrpchar.entities.items.usable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.khuzzuk.wfrpchar.entities.items.Placement;
import pl.khuzzuk.wfrpchar.entities.items.types.OneHandedWeaponType;

@NoArgsConstructor
@Getter
@Setter
public class OneHandedWeapon extends AbstractHandWeapon<OneHandedWeaponType> {
    private OneHandedWeaponType baseType;

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
