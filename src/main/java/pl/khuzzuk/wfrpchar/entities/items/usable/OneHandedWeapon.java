package pl.khuzzuk.wfrpchar.entities.items.usable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.khuzzuk.wfrpchar.entities.items.Placement;
import pl.khuzzuk.wfrpchar.entities.items.types.OneHandedWeaponType;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("3")
@NoArgsConstructor
public class OneHandedWeapon extends AbstractHandWeapon<OneHandedWeaponType> {
    @Getter
    @ManyToOne
    private OneHandedWeaponType baseType;

    @Override
    public void setBaseType(OneHandedWeaponType baseType) {
        this.baseType = baseType;
    }

    @Override
    public Placement getPlacement() {
        return baseType.getPlacement();
    }
}
