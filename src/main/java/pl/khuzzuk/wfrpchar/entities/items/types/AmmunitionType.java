package pl.khuzzuk.wfrpchar.entities.items.types;

import com.fasterxml.jackson.annotation.JsonIgnore;
import pl.khuzzuk.wfrpchar.entities.items.Ammo;

public class AmmunitionType extends FightingEquipment implements Ammo {
    public AmmunitionType() {
        type = EquipmentType.AMMO;
    }

    @Override
    @JsonIgnore
    public String getTypeName() {
        return name;
    }
}
