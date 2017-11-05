package pl.khuzzuk.wfrpchar.entities.items.types;

import com.fasterxml.jackson.annotation.JsonIgnore;
import pl.khuzzuk.wfrpchar.entities.items.Ammo;

public class AmmunitionType extends FightingEquipment implements Ammo {
    public AmmunitionType() {
        type = EquipmentType.AMMO;
    }

    @Override
    public String toCsv() {
        return name + ";" + weight + ";" + price.toString() + ";" + accessibility.name() + ";" +
                specialFeatures + ";" + strength + ";" + type.name() + ";" + placement.name();
    }

    @Override
    @JsonIgnore
    public String getTypeName() {
        return name;
    }
}
