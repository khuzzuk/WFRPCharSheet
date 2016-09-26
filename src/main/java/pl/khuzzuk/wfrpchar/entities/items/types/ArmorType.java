package pl.khuzzuk.wfrpchar.entities.items.types;

import lombok.Getter;
import lombok.Setter;
import pl.khuzzuk.wfrpchar.entities.determinants.Determinant;
import pl.khuzzuk.wfrpchar.entities.items.ArmorPattern;

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
                specialFeatures + ";" +
                strength + ";" +
                type.name() + ";" +
                placement.name() + ";" +
                getLangToCsv() + ";" +
                Determinant.determinantsToCsv(determinants) + ";" +
                pattern.name();
    }
}
