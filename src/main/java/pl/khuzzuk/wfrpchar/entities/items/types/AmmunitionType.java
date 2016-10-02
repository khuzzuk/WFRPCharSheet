package pl.khuzzuk.wfrpchar.entities.items.types;

import lombok.Getter;
import lombok.Setter;
import pl.khuzzuk.wfrpchar.entities.items.Ammo;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("10")
public class AmmunitionType extends Item implements Ammo {
    @Getter
    @Setter
    private int strength;

    public AmmunitionType() {
        type = EquipmentType.AMMO;
    }

    @Override
    public String toCsv() {
        return name + ";" + weight + ";" + price.toString() + ";" + accessibility.name() + ";" +
                specialFeatures + ";" + strength + ";" + type.name();
    }
}
