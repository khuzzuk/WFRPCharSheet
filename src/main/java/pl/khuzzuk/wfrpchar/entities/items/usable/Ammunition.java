package pl.khuzzuk.wfrpchar.entities.items.usable;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;
import pl.khuzzuk.wfrpchar.entities.items.BattleEquipment;
import pl.khuzzuk.wfrpchar.entities.items.Placement;
import pl.khuzzuk.wfrpchar.entities.items.types.AmmunitionType;
import pl.khuzzuk.wfrpchar.entities.items.types.Item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@DiscriminatorValue("8")
public class Ammunition extends AbstractWeapon<AmmunitionType> implements BattleEquipment {
    @Getter
    @Setter
    @ManyToOne
    private AmmunitionType baseType;

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
