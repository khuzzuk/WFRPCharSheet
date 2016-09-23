package pl.khuzzuk.wfrpchar.entities.items;

import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.entities.LoadingTimes;
import pl.khuzzuk.wfrpchar.entities.determinants.DeterminantFactory;
import pl.khuzzuk.wfrpchar.entities.LangElement;
import pl.khuzzuk.wfrpchar.entities.Price;
import pl.khuzzuk.wfrpchar.entities.items.types.*;
import pl.khuzzuk.wfrpchar.rules.Dices;

import javax.inject.Inject;

@Component
public class WeaponParser {
    private DeterminantFactory determinantFactory;

    @Inject
    public WeaponParser(DeterminantFactory determinantFactory) {
        this.determinantFactory = determinantFactory;
    }

    public Item parseEquipment(String[] columns) {
        EquipmentType type = EquipmentType.valueOf(columns[6]);
        if (type == EquipmentType.WEAPON) {
            return parseWeapon(columns);
        } else if (type == EquipmentType.RANGED_WEAPON) {
            return parseRangedWeapon(columns);
        } else if (type == EquipmentType.ARMOR) {
            return parseArmorType(columns);
        } else {
            return parseMiscItem(columns);
        }
    }

    private MiscItem parseMiscItem(String[] columns) {
        MiscItem item = new MiscItem();
        fillItemFields(columns, item);
        return item;
    }

    private ArmorType parseArmorType(String[] columns) {
        ArmorType armor = new ArmorType();
        fillItemFields(columns, armor);
        fillFightingEquipmentFields(columns, armor);
        armor.setPattern(ArmorPattern.valueOf(columns[10]));
        return armor;
    }

    private RangedWeaponType parseRangedWeapon(String[] columns) {
        RangedWeaponType rangedWeapon = new RangedWeaponType();
        fillItemFields(columns, rangedWeapon);
        fillFightingEquipmentFields(columns, rangedWeapon);
        fillWeaponFields(columns, rangedWeapon);
        rangedWeapon.setShortRange(Integer.parseInt(columns[11]));
        rangedWeapon.setEffectiveRange(Integer.parseInt(columns[12]));
        rangedWeapon.setMaximumRange(Integer.parseInt(columns[13]));
        rangedWeapon.setReloadTime(LoadingTimes.valueOf(columns[14]));
        return rangedWeapon;
    }

    private WeaponType parseWeapon(String[] columns) {
        WhiteWeaponType weapon = WhiteWeaponType.getFromPlacement(Placement.valueOf(columns[7]));
        if (weapon instanceof BastardWeaponType) {
            addBastardFields((BastardWeaponType) weapon, columns);
        }
        fillItemFields(columns, weapon);
        fillWeaponFields(columns, weapon);
        fillFightingEquipmentFields(columns, weapon);
        weapon.setDices(Dices.valueOf(columns[11]));
        weapon.setRolls(Integer.parseInt(columns[12]));
        return weapon;
    }

    private void addBastardFields(BastardWeaponType weaponType, String[] columns) {
        weaponType.setOneHandedStrength(Integer.parseInt(columns[13]));
        weaponType.setOneHandedDeterminants(determinantFactory.createDeterminants(columns[14]));
    }

    private void fillItemFields(String[] columns, Item item) {
        item.setName(columns[0]);
        item.setWeight(Float.parseFloat(columns[1]));
        item.setPrice(Price.parsePrice(columns[2]));
        item.setAccessibility(Accessibility.valueOf(columns[3]));
        item.setSpecialFeature(columns[4]);
    }

    private void fillFightingEquipmentFields(String[] columns, FightingEquipment equipment) {
        equipment.setStrength(Integer.parseInt(columns[5]));
        equipment.setPlacement(Placement.valueOf(columns[7]));
        equipment.setNames(LangElement.parseLang(columns[8]));
        equipment.setDeterminants(determinantFactory.createDeterminants(columns[9]));
    }

    private void fillWeaponFields(String[] columns, WeaponType weaponType) {
        weaponType.setTypeName(columns[10]);
    }
}
