package pl.khuzzuk.wfrpchar.entities.items.usable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import pl.khuzzuk.wfrpchar.entities.determinants.Determinant;
import pl.khuzzuk.wfrpchar.entities.items.Bastard;
import pl.khuzzuk.wfrpchar.entities.items.Placement;
import pl.khuzzuk.wfrpchar.entities.items.types.BastardWeaponType;

import java.util.Set;

@Getter
@Setter
public class BastardWeapon extends AbstractHandWeapon<BastardWeaponType> implements Bastard {
    private BastardWeaponType baseType;
    private Set<Determinant> oneHandedDeterminants;

    @Override
    @JsonIgnore
    public int getOneHandedStrength() {
        return baseType != null ? baseType.getOneHandedStrength() : 0;
    }

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
