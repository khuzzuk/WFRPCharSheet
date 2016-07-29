package pl.khuzzuk.wfrpchar.entities.items;

enum EquipmentType {
    ARMOR, WEAPON;

    public static EquipmentType forName(String name) {
        switch (name) {
            case "ARMOR":
                return ARMOR;
            case "WEAPON":
                return WEAPON;
            default:
                throw new IllegalArgumentException("Cannot parse EquipmentType with: " + name);
        }
    }
}
