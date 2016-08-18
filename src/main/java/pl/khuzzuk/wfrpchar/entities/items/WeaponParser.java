package pl.khuzzuk.wfrpchar.entities.items;

import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.entities.LoadingTimes;
import pl.khuzzuk.wfrpchar.entities.determinants.DeterminantFactory;
import pl.khuzzuk.wfrpchar.entities.LangElement;
import pl.khuzzuk.wfrpchar.entities.Price;
import pl.khuzzuk.wfrpchar.rules.Dices;

import javax.inject.Inject;
import java.util.Arrays;

@Component
public class WeaponParser {
    private DeterminantFactory determinantFactory;

    @Inject
    public WeaponParser(DeterminantFactory determinantFactory) {
        this.determinantFactory = determinantFactory;
    }

    public FightingEquipment parseEquipment(String[] columns) {
        EquipmentType type = EquipmentType.valueOf(columns[6]);
        if (type == EquipmentType.WEAPON) {
            return parseWeapon(columns);
        } else if (type == EquipmentType.RANGED_WEAPON) {
            return parseRangedWeapon(columns);
        } else {
            return parseArmor(columns);
        }
    }

    private RangedWeaponType parseRangedWeapon(String[] columns) {
        RangedWeaponType rangedWeapon = new RangedWeaponType();
        fillItemFields(columns, rangedWeapon);
        fillWeaponFields(columns, rangedWeapon);
        rangedWeapon.shortRange = Integer.parseInt(columns[11]);
        rangedWeapon.effectiveRange = Integer.parseInt(columns[12]);
        rangedWeapon.maximumRange = Integer.parseInt(columns[13]);
        rangedWeapon.reloadTime = LoadingTimes.valueOf(columns[14]);
        return rangedWeapon;
    }

    private WeaponType parseWeapon(String[] columns) {
        Placement placement = Placement.valueOf(columns[7]);
        if (placement == Placement.ONE_HAND) {
            return fillWhiteWeaponsVariables(new OneHandedWeaponType(), columns);
        } else if (placement == Placement.TWO_HANDS) {
            return fillWhiteWeaponsVariables(new TwoHandedWeaponType(), columns);
        } else if (placement == Placement.BASTARD){
            BastardWeaponType bastardWeaponType =
                    (BastardWeaponType) fillWhiteWeaponsVariables(new BastardWeaponType(), columns);
            return addBastardFields(bastardWeaponType, columns);
        }
        throw new IllegalArgumentException(Arrays.toString(columns));
    }

    private WhiteWeaponType fillWhiteWeaponsVariables(WhiteWeaponType weaponType, String[] columns) {
        fillItemFields(columns, weaponType);
        fillWeaponFields(columns, weaponType);
        weaponType.dices = Dices.valueOf(columns[11]);
        weaponType.rolls = Integer.parseInt(columns[12]);
        return weaponType;
    }

    private BastardWeaponType addBastardFields(BastardWeaponType weaponType, String[] columns) {
        weaponType.oneHandedStrength = Integer.parseInt(columns[13]);
        weaponType.oneHandedDeterminants = determinantFactory.createDeterminants(columns[14]);
        return weaponType;
    }

    private void fillItemFields(String[] columns, Item item) {
        item.name = columns[0];
        item.weight = Float.parseFloat(columns[1]);
        item.price = Price.parsePrice(columns[2]);
        item.accessibility = Item.Accessibility.valueOf(columns[3]);
        item.specialFeature = columns[4];
    }

    private void fillWeaponFields(String[] columns, WeaponType weaponType) {
        weaponType.strength = Integer.parseInt(columns[5]);
        weaponType.placement = Placement.valueOf(columns[7]);
        weaponType.names = LangElement.parseLang(columns[8]);
        weaponType.determinants = determinantFactory.createDeterminants(columns[9]);
        weaponType.typeName = columns[10];
    }

    private FightingEquipment parseArmor(String[] columns) {
        return null;
    }
}
