package pl.khuzzuk.wfrpchar.entities.items;

import lombok.*;

import javax.persistence.DiscriminatorValue;

@DiscriminatorValue("2")
@NoArgsConstructor
@RequiredArgsConstructor
public abstract class FightingEquipment extends Item {
    @NonNull
    @Getter
    @Setter
    private float strength;
    @NonNull
    @Getter
    @Setter
    private EquipmentType type;

    public enum EquipmentType {
        ARMOR, WEAPON
    }
}
