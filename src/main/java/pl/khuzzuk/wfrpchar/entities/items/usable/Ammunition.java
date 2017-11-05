package pl.khuzzuk.wfrpchar.entities.items.usable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import pl.khuzzuk.wfrpchar.entities.items.BattleEquipment;
import pl.khuzzuk.wfrpchar.entities.items.Placement;
import pl.khuzzuk.wfrpchar.entities.items.types.AmmunitionType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Ammunition extends AbstractWeapon<AmmunitionType> implements BattleEquipment {
    @Getter
    @Setter
    private AmmunitionType baseType;

    @Override
    public String toCsv() {
        List<String> fields = new ArrayList<>();
        fillCommodityFields(fields);
        fillWeaponCsvFields(fields);
        return fields.stream().collect(Collectors.joining(";"));
    }

    @Override
    @JsonIgnore
    public Placement getPlacement() {
        return baseType.getPlacement();
    }

    @Override
    public void setPlacement(Placement placement) {
        throw new UnsupportedOperationException();
    }
}
