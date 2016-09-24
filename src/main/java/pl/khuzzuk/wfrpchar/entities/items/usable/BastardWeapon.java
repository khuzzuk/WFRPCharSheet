package pl.khuzzuk.wfrpchar.entities.items.usable;

import lombok.Getter;
import lombok.Setter;
import pl.khuzzuk.wfrpchar.entities.determinants.Determinant;
import pl.khuzzuk.wfrpchar.entities.items.Bastard;
import pl.khuzzuk.wfrpchar.entities.items.types.BastardWeaponType;
import pl.khuzzuk.wfrpchar.entities.items.types.WhiteWeaponType;

import javax.persistence.*;
import java.util.Set;

@Entity
@DiscriminatorValue("5")
public class BastardWeapon extends AbstractHandWeapon implements Bastard {
    @Getter
    @ManyToOne
    private BastardWeaponType baseType;

    @Getter
    @Setter
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "DET_REQ_BAST_MAP",
            joinColumns = {@JoinColumn(name = "EQ_ID")},
            inverseJoinColumns = {@JoinColumn(name = "DET_ID")})
    private Set<Determinant> oneHandedDeterminants;

    @Override
    public void setBaseType(WhiteWeaponType baseType) {
        this.baseType = (BastardWeaponType) baseType;
    }

    @Override
    public int getOneHandedStrength() {
        return baseType != null ? baseType.getOneHandedStrength() : 0;
    }
}
