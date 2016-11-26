package pl.khuzzuk.wfrpchar.entities.items.usable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.collections4.collection.CompositeCollection;
import org.apache.commons.collections4.set.CompositeSet;
import pl.khuzzuk.wfrpchar.entities.Price;
import pl.khuzzuk.wfrpchar.entities.determinants.Determinant;
import pl.khuzzuk.wfrpchar.entities.determinants.DeterminantsType;
import pl.khuzzuk.wfrpchar.entities.items.BattleEquipment;
import pl.khuzzuk.wfrpchar.entities.items.Placement;
import pl.khuzzuk.wfrpchar.entities.items.ResourceType;
import pl.khuzzuk.wfrpchar.entities.items.Weapon;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@DiscriminatorValue("1")
@NoArgsConstructor
public abstract class AbstractWeapon<T extends BattleEquipment>
        extends AbstractCommodity
        implements Weapon {
    @Getter
    @Setter
    @ManyToOne
    private ResourceType primaryResource;
    @Getter
    @Setter
    @ManyToOne
    private ResourceType secondaryResource;
    @Setter
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name = "DET_REQ_MAP",
            joinColumns = {@JoinColumn(name = "EQ_ID")},
            inverseJoinColumns = {@JoinColumn(name = "DET_ID")})
    private Set<Determinant> determinants;

    public abstract T getBaseType();

    public static AbstractHandWeapon getFromPlacement(Placement placement) {
        if (placement == Placement.ONE_HAND) {
            return new OneHandedWeapon();
        }
        throw new IllegalArgumentException("Can't get hand weapon from " + placement.name());
    }

    public int getStrength() {
        return getBaseType().getStrength() *
                (primaryResource.getStrengthMod() + secondaryResource.getStrengthMod() / 11);
    }

    @Override
    public float getWeight() {
        return getBaseType().getWeight() * (primaryResource.getWeight() + secondaryResource.getWeight()/100f);
    }

    @Override
    public String getTypeName() {
        return getBaseType().getTypeName();
    }

    @Override
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
    public Set<Determinant> getBaseDeterminants() {
        return determinants;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Set<Determinant> getDeterminants() {
        return new CompositeSet<>(getBaseDeterminants(), getBaseType().getDeterminants());
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
