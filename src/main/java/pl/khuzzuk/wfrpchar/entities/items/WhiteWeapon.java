package pl.khuzzuk.wfrpchar.entities.items;

import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("3")
@NoArgsConstructor
public class WhiteWeapon extends FightingEquipment {
}
