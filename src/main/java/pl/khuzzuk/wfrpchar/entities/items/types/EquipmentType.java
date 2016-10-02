package pl.khuzzuk.wfrpchar.entities.items.types;

import java.util.function.Supplier;

public enum EquipmentType {
    ARMOR(ArmorType::new), WEAPON(null), RANGED_WEAPON(RangedWeaponType::new), AMMO(AmmunitionType::new), MISC_ITEM(MiscItem::new);
    private Supplier<? extends Item> equipmentSupplier;

    EquipmentType(Supplier<? extends Item> equipmentSupplier) {
        this.equipmentSupplier = equipmentSupplier;
    }

    public Item getEquipment() {
        if (equipmentSupplier == null) {
            throw new IllegalArgumentException("Cannot obtain equipment for abstract WhiteWeaponType");
        }
        return equipmentSupplier.get();
    }
}
