package pl.khuzzuk.wfrpchar.entities.items;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("9")
public class ArmorType extends FightingEquipment {

    @Getter
    @Setter
    private ArmorPattern pattern;

    public ArmorType() {
        type = EquipmentType.ARMOR;
    }

    @Override
    public String toCsv() {
        return name + ";" +
                weight + ";" +
                price.toString() + ";" +
                accessibility.name() + ";" +
                specialFeature + ";" +
                strength + ";" +
                type.name() + ";" +
                placement.name() + ";" +
                getLangToCsv() + ";" +
                determinantsToCsv(determinants) + ";" +
                pattern.name();
    }
}
