package pl.khuzzuk.wfrpchar.entities.items.types;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import pl.khuzzuk.wfrpchar.entities.items.Weapon;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("3")
@EqualsAndHashCode(callSuper = true)
public abstract class WeaponType extends FightingEquipment implements Weapon {
    @NonNull
    @Getter
    @Setter
    String typeName;

    public WeaponType() {
        type = EquipmentType.WEAPON;
    }
}
