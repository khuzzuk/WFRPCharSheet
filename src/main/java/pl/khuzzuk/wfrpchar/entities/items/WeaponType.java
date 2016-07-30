package pl.khuzzuk.wfrpchar.entities.items;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("3")
@EqualsAndHashCode(callSuper = true)
public abstract class WeaponType extends FightingEquipment {
    @NonNull
    @Getter
    @Setter
    String typeName;

    public WeaponType() {
        type = EquipmentType.WEAPON;
    }
}
