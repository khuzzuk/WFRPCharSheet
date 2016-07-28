package pl.khuzzuk.wfrpchar.entities.items;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("4")
public abstract class WeaponType extends FightingEquipment {
    @NonNull
    @Getter
    @Setter
    String typeName;
}
