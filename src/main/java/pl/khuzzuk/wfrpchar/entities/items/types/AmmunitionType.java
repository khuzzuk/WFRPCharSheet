package pl.khuzzuk.wfrpchar.entities.items.types;

import pl.khuzzuk.wfrpchar.entities.items.Ammo;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("10")
public class AmmunitionType extends FightingEquipment implements Ammo {
    public AmmunitionType() {
        type = EquipmentType.AMMO;
    }

    @Override
    public String toCsv() {
        return name + ";" + weight + ";" + price.toString() + ";" + accessibility.name() + ";" +
                specialFeatures + ";" + strength + ";" + type.name();
    }

    @Override
    public String getTypeName() {
        return name;
    }
}
