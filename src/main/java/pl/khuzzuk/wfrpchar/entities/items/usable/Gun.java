package pl.khuzzuk.wfrpchar.entities.items.usable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import pl.khuzzuk.wfrpchar.entities.LoadingTimes;
import pl.khuzzuk.wfrpchar.entities.Persistable;
import pl.khuzzuk.wfrpchar.entities.items.Placement;
import pl.khuzzuk.wfrpchar.entities.items.RangedWeapon;
import pl.khuzzuk.wfrpchar.entities.items.types.RangedWeaponType;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@DiscriminatorValue("6")
@Getter
@Setter
public class Gun extends AbstractWeapon<RangedWeaponType> implements RangedWeapon, Persistable {
    @ManyToOne
    private RangedWeaponType baseType;

    @Override
    @JsonIgnore
    public int getShortRange() {
        return baseType != null ? baseType.getShortRange() : 0;
    }

    @Override
    @JsonIgnore
    public int getEffectiveRange() {
        return baseType != null ? baseType.getEffectiveRange() : 0;
    }

    @Override
    @JsonIgnore
    public int getMaximumRange() {
        return baseType != null ? baseType.getMaximumRange() : 0;
    }

    @Override
    @JsonIgnore
    public LoadingTimes getReloadTime() {
        return baseType != null ? baseType.getReloadTime() : null;
    }

    @Override
    public String toCsv() {
        List<String> fields = new ArrayList<>();
        fillCommodityFields(fields);
        fillWeaponCsvFields(fields);
        return fields.stream().collect(Collectors.joining(";"));
    }

    @Override
    @JsonIgnore
    public Placement getPlacement() {
        return baseType.getPlacement();
    }
}
