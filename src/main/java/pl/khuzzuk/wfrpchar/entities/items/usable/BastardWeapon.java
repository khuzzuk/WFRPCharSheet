package pl.khuzzuk.wfrpchar.entities.items.usable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import pl.khuzzuk.wfrpchar.entities.determinants.Determinant;
import pl.khuzzuk.wfrpchar.entities.items.Bastard;
import pl.khuzzuk.wfrpchar.entities.items.Placement;
import pl.khuzzuk.wfrpchar.entities.items.types.BastardWeaponType;
import pl.khuzzuk.wfrpchar.entities.items.types.WhiteWeaponType;

import javax.persistence.*;
import java.util.Set;

@Entity
@DiscriminatorValue("5")
@Getter
@Setter
public class BastardWeapon extends AbstractHandWeapon<BastardWeaponType> implements Bastard {
    @ManyToOne
    private BastardWeaponType baseType;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "DET_REQ_BAST_MAP",
            joinColumns = {@JoinColumn(name = "EQ_ID")},
            inverseJoinColumns = {@JoinColumn(name = "DET_ID")})
    private Set<Determinant> oneHandedDeterminants;

    @Override
    @JsonIgnore
    public int getOneHandedStrength() {
        return baseType != null ? baseType.getOneHandedStrength() : 0;
    }

    @Override
    @JsonIgnore
    public Placement getPlacement() {
        return baseType.getPlacement();
    }
}
