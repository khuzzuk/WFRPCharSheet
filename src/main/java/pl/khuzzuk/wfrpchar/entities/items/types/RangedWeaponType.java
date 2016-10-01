package pl.khuzzuk.wfrpchar.entities.items.types;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import pl.khuzzuk.wfrpchar.entities.LoadingTimes;
import pl.khuzzuk.wfrpchar.entities.Persistable;
import pl.khuzzuk.wfrpchar.entities.determinants.Determinant;
import pl.khuzzuk.wfrpchar.entities.items.RangedWeapon;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("8")
public class RangedWeaponType extends WeaponType implements RangedWeapon, Persistable {
    @Getter
    @Setter
    @NotNull
    int shortRange;
    @Getter
    @Setter
    @NotNull
    int effectiveRange;
    @Getter
    @Setter
    @NotNull
    int maximumRange;
    @NotNull
    @Getter
    @Setter
    LoadingTimes reloadTime;

    public RangedWeaponType() {
        type = EquipmentType.RANGED_WEAPON;
    }

    @Override
    public String toCsv() {
        List<String> elements = new LinkedList<>();
        elements.add(name);
        elements.add("" + weight);
        elements.add(price.toString());
        elements.add(accessibility.name());
        elements.add(specialFeatures);
        elements.add("" + strength);
        elements.add(type.name());
        elements.add(placement.name());
        elements.add(getLangToCsv());
        elements.add(Determinant.determinantsToCsv(determinants));
        elements.add(typeName);
        elements.add("" + shortRange);
        elements.add("" + effectiveRange);
        elements.add("" + maximumRange);
        elements.add(reloadTime.name());
        return elements.stream().collect(Collectors.joining(";"));
    }
}
