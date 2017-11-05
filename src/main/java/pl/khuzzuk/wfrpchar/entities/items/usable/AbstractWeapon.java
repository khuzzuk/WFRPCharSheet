package pl.khuzzuk.wfrpchar.entities.items.usable;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.collections4.collection.CompositeCollection;
import pl.khuzzuk.wfrpchar.entities.Price;
import pl.khuzzuk.wfrpchar.entities.determinants.Determinant;
import pl.khuzzuk.wfrpchar.entities.items.BattleEquipment;
import pl.khuzzuk.wfrpchar.entities.items.Placement;
import pl.khuzzuk.wfrpchar.entities.items.ResourceType;
import pl.khuzzuk.wfrpchar.entities.items.Weapon;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public abstract class AbstractWeapon<T extends BattleEquipment>
        extends AbstractCommodity
        implements Weapon {
    @JsonIdentityReference(alwaysAsId = true)
    private ResourceType primaryResource;
    @JsonIdentityReference(alwaysAsId = true)
    private ResourceType secondaryResource;
    private Set<Determinant> determinants = new HashSet<>();
    @JsonIdentityReference(alwaysAsId = true)
    public abstract T getBaseType();

    public static AbstractHandWeapon getFromPlacement(Placement placement) {
        if (placement == Placement.ONE_HAND) {
            return new OneHandedWeapon();
        }
        throw new IllegalArgumentException("Can't get hand weapon from " + placement.name());
    }

    @JsonIgnore
    public int getStrength() {
        return getBaseType().getStrength() *
                (primaryResource.getStrengthMod() + secondaryResource.getStrengthMod() / 11);
    }

    @Override
    @JsonIgnore
    public float getWeight() {
        return getBaseType().getWeight() * (primaryResource.getWeight() + secondaryResource.getWeight()/100f);
    }

    @Override
    @JsonIgnore
    public String getTypeName() {
        return getBaseType().getTypeName();
    }

    @Override
    @JsonIgnore
    public Price getPrice() {
        return getBaseType().getPrice().multiply(
                (primaryResource.getPriceMod() + secondaryResource.getPriceMod() /100)/100)
                .add(getBasePrice());
    }

    @Override
    public void addDeterminant(Determinant determinant) {
        determinants.add(determinant);
    }

    @Override
    @JsonIgnore
    public Set<Determinant> getBaseDeterminants() {
        return determinants;
    }

    @JsonIgnore
    @SuppressWarnings("unchecked")
    @Override
    public Collection<Determinant> getAllDeterminants() {
        return new CompositeCollection<>(getBaseDeterminants(), getBaseType().getDeterminants());
    }

    void fillWeaponCsvFields(List<String> fields) {
        fields.add(getBaseType().getName());
        if (getPrimaryResource() != null) {
            fields.add(getPrimaryResource().getName());
        } else {
            fields.add("");
        }
        if (getSecondaryResource() != null) {
            fields.add(getSecondaryResource().getName());
        } else {
            fields.add("");
        }
        fields.add(Determinant.determinantsToCsv(getDeterminants()));
    }
}
