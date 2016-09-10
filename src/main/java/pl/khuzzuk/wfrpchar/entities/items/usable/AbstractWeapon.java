package pl.khuzzuk.wfrpchar.entities.items.usable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.collections4.collection.CompositeCollection;
import pl.khuzzuk.wfrpchar.entities.Named;
import pl.khuzzuk.wfrpchar.entities.Price;
import pl.khuzzuk.wfrpchar.entities.determinants.Determinant;
import pl.khuzzuk.wfrpchar.entities.determinants.DeterminantsType;
import pl.khuzzuk.wfrpchar.entities.items.ResourceType;
import pl.khuzzuk.wfrpchar.entities.items.Weapon;
import pl.khuzzuk.wfrpchar.entities.items.types.WeaponType;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@DiscriminatorValue("1")
@ToString(exclude = "id")
@NoArgsConstructor
public abstract class AbstractWeapon
        extends AbstractCommodity
        implements Named<String>, Weapon {
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    @ManyToOne
    WeaponType baseType;
    @Embedded
    @Getter
    @Setter
    private ResourceType primaryResource;
    @Embedded
    @Getter
    @Setter
    private ResourceType secondaryResource;
    @Getter
    @Setter
    @OneToMany
    private List<Determinant> determinants;
    @Getter
    @Setter
    private String specialFeatures;
    public int getStrength() {
        return baseType.getStrength() *
                (primaryResource.getStrengthMod() + secondaryResource.getStrengthMod() / 11);
    }

    @Override
    public String getTypeName() {
        return baseType.getTypeName();
    }

    @Override
    public Price getPrice() {
        return baseType.getPrice().multiply(
                (primaryResource.getPriceMod() + secondaryResource.getPriceMod() /100)/100)
                .add(getBasePrice());
    }

    @Override
    public void addDeterminant(Determinant determinant) {
        determinants.add(determinant);
    }

    @Override
    public Collection<Determinant> getAllDeterminants() {
        return new CompositeCollection<>(determinants, baseType.getDeterminants());
    }

    @Override
    public Collection<Determinant> getDeterminantForType(DeterminantsType type) {
        return getAllDeterminants().stream().filter(d -> d.getLabel() == type)
                .collect(Collectors.toList());
    }
}
