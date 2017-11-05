package pl.khuzzuk.wfrpchar.entities.items.types;

import lombok.Getter;
import lombok.Setter;
import pl.khuzzuk.wfrpchar.entities.determinants.Determinant;
import pl.khuzzuk.wfrpchar.entities.items.Bastard;
import pl.khuzzuk.wfrpchar.entities.items.Placement;

import java.util.Set;

@Getter
@Setter
public class BastardWeaponType extends WhiteWeaponType implements Bastard {
    int oneHandedStrength;
    Set<Determinant> oneHandedDeterminants;

    public BastardWeaponType() {
        placement = Placement.BASTARD;
    }
}
