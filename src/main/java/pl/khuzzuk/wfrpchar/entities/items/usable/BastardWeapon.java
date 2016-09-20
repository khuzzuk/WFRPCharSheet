package pl.khuzzuk.wfrpchar.entities.items.usable;

import lombok.Getter;
import pl.khuzzuk.wfrpchar.entities.items.types.BastardWeaponType;
import pl.khuzzuk.wfrpchar.entities.items.types.WhiteWeaponType;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("5")
public class BastardWeapon extends AbstractHandWeapon {
    @Getter
    @ManyToOne
    private BastardWeaponType baseType;

    @Override
    public void setBaseType(WhiteWeaponType baseType) {
        this.baseType = (BastardWeaponType) baseType;
    }
}
