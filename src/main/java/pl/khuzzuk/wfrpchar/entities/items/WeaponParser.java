package pl.khuzzuk.wfrpchar.entities.items;

import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.entities.LangElement;
import pl.khuzzuk.wfrpchar.entities.LoadingTimes;
import pl.khuzzuk.wfrpchar.entities.Price;
import pl.khuzzuk.wfrpchar.entities.determinants.DeterminantFactory;
import pl.khuzzuk.wfrpchar.entities.items.types.*;
import pl.khuzzuk.wfrpchar.entities.items.usable.*;
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
        } else if (type == EquipmentType.MISC_ITEM) {
            return parseMiscItem(columns);
        } else {
            return parseAmmunitionType(columns);
        }
    }

    private AmmunitionType parseAmmunitionType(String[] columns) {
        AmmunitionType type = new AmmunitionType();
        fillItemFields(columns, type);
        type.setStrength(Integer.parseInt(columns[5]));
        return type;
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
        item.setSpecialFeatures(columns[4]);
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

    @SuppressWarnings("unchecked")
    public <T extends WhiteWeaponType> AbstractHandWeapon<T> parseHandWeapon(String[] fields, ParserBag<T> bag) {
        AbstractHandWeapon<T> weapon = AbstractWeapon.getFromPlacement(bag.getBaseType().getPlacement());
        weapon.setBaseType(bag.getBaseType());
        fillCommodityItem(fields, weapon);
        fillBattleEquipment(fields, weapon, bag.getPrimaryResource(), bag.getSecondaryResource());
        fillHandWeapon(fields, weapon);
        if (weapon instanceof Bastard) {
            fillBastard(fields, (BastardWeapon) weapon);
        }
        return weapon;
    }

    public Gun parseGun(String[] fields, ParserBag<RangedWeaponType> bag) {
        Gun gun = new Gun();
        gun.setBaseType(bag.getBaseType());
        fillCommodityItem(fields, gun);
        fillBattleEquipment(fields, gun, bag.getPrimaryResource(), bag.getSecondaryResource());
        return gun;
    }

    public Armor parseArmor(String[] fields, ParserBag<ArmorType> bag) {
        Armor armor = new Armor();
        armor.setBaseType(bag.getBaseType());
        fillCommodityItem(fields, armor);
        fillBattleEquipment(fields, armor, bag.getPrimaryResource(), bag.getSecondaryResource());
        return armor;
    }

    private void fillCommodityItem(String[] fields, AbstractCommodity commodity) {
        commodity.setName(fields[0]);
        commodity.setBasePrice(Price.parsePrice(fields[1]));
        commodity.setAccessibility(Accessibility.valueOf(fields[2]));
        commodity.setSpecialFeatures(fields[3]);
    }

    private void fillBattleEquipment(String[] line, AbstractWeapon weapon, ResourceType primaryResource, ResourceType secondaryResource) {
        weapon.setPrimaryResource(primaryResource);
        weapon.setSecondaryResource(secondaryResource);
        weapon.setDeterminants(determinantFactory.createDeterminants(line[7]));
    }

    private void fillHandWeapon(String[] fields, AbstractHandWeapon weapon) {
        if (fields.length >= 9 && fields[8].length() > 0) {
            weapon.setDices(Dices.valueOf(fields[8]));
        }
        if (fields.length >= 10 && fields[9].length() > 0) {
            weapon.setRolls(Integer.parseInt(fields[9]));
        }
    }

    private void fillBastard(String[] fields, BastardWeapon weapon) {
        weapon.setOneHandedDeterminants(determinantFactory.createDeterminants(fields[10]));
    }
}
