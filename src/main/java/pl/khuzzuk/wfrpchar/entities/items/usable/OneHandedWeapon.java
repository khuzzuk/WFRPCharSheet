package pl.khuzzuk.wfrpchar.entities.items.usable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.khuzzuk.wfrpchar.entities.items.types.OneHandedWeaponType;
import pl.khuzzuk.wfrpchar.entities.items.types.WhiteWeaponType;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("3")
@NoArgsConstructor
public class OneHandedWeapon extends AbstractHandWeapon {
    @Getter
    @ManyToOne
    private OneHandedWeaponType baseType;

    @Override
    public void setBaseType(WhiteWeaponType baseType) {
        this.baseType = (OneHandedWeaponType) baseType;
    }
}
