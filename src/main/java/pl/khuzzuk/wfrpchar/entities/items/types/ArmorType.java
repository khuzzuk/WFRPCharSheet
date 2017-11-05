package pl.khuzzuk.wfrpchar.entities.items.types;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import pl.khuzzuk.wfrpchar.entities.determinants.Determinant;
import pl.khuzzuk.wfrpchar.entities.items.ArmorPattern;
import pl.khuzzuk.wfrpchar.entities.items.BattleEquipment;

public class ArmorType extends FightingEquipment implements BattleEquipment {

    @Getter
    @Setter
    private ArmorPattern pattern;

    public ArmorType() {
        type = EquipmentType.ARMOR;
    }

    @Override
    @JsonIgnore
    public String getTypeName() {
        //TODO add typeName to armorTypes
        return name;
    }
}
