package pl.khuzzuk.wfrpchar.entities.items.usable;

import lombok.Getter;
import lombok.Setter;
import pl.khuzzuk.wfrpchar.entities.items.BattleEquipment;
import pl.khuzzuk.wfrpchar.entities.items.Placement;
import pl.khuzzuk.wfrpchar.entities.items.types.AmmunitionType;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("8")
public class Ammunition extends AbstractWeapon<AmmunitionType> implements BattleEquipment {
    @Getter
    @Setter
    @ManyToOne
    private AmmunitionType baseType;

    @Override
    public String toCsv() {
        return null;
    }

    @Override
    public Placement getPlacement() {
        return baseType.getPlacement();
    }
}
