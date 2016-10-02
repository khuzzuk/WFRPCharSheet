package pl.khuzzuk.wfrpchar.entities.items.usable;

import lombok.Getter;
import lombok.Setter;
import pl.khuzzuk.wfrpchar.entities.items.Placement;
import pl.khuzzuk.wfrpchar.entities.items.types.ArmorType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Armor extends AbstractWeapon<ArmorType> {
    @Getter
    @Setter
    private ArmorType baseType;

    @Override
    public String toCsv() {
        List<String> fields = new ArrayList<>();
        fillCommodityFields(fields);
        fillWeaponCsvFields(fields);
        return fields.stream().collect(Collectors.joining(";"));
    }

    @Override
    public Placement getPlacement() {
        return baseType.getPlacement();
    }
}
