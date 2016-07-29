package pl.khuzzuk.wfrpchar.entities.items;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.determinants.DeterminantFactory;
import pl.khuzzuk.wfrpchar.entities.LangElement;
import pl.khuzzuk.wfrpchar.entities.Price;
import pl.khuzzuk.wfrpchar.rules.Dices;

import javax.inject.Inject;
import java.util.Arrays;

@Component
@NoArgsConstructor
public class WeaponParser {
    @Inject
    private DeterminantFactory determinantFactory;
    public FightingEquipment parseEquipment(String[] columns) {
        EquipmentType type = EquipmentType.valueOf(columns[6]);
        if (type == EquipmentType.WEAPON) {
            return parseWeapon(columns);
        } else {
            return parseArmor(columns);
        }
    }

    private WeaponType parseWeapon(String[] columns) {
        Placement placement = Placement.valueOf(columns[7]);
        if (placement == Placement.ONE_HAND) {
            return parseOneHandedWeaponType(columns);
        }
        throw new IllegalArgumentException(Arrays.toString(columns));
    }

    private OneHandedWeaponType parseOneHandedWeaponType(String[] columns) {
        OneHandedWeaponType weaponType = new OneHandedWeaponType();
        weaponType.name = columns[0];
        weaponType.weight = Float.parseFloat(columns[1]);
        weaponType.price = Price.parsePrice(columns[2]);
        weaponType.accessibility = Item.Accessibility.valueOf(columns[3]);
        weaponType.specialFeature = columns[4];
        weaponType.strength = Integer.parseInt(columns[5]);
        weaponType.names = LangElement.parseLang(columns[8]);
        weaponType.determinants = determinantFactory.createDeterminants(columns[9]);
        weaponType.typeName = columns[10];
        weaponType.dices = Dices.valueOf(columns[11]);
        weaponType.rolls = Integer.parseInt(columns[12]);
        return weaponType;
    }

    private FightingEquipment parseArmor(String[] columns) {
        return null;
    }
}
