package pl.khuzzuk.wfrpchar.entities.items;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import pl.khuzzuk.wfrpchar.determinants.Determinant;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@DiscriminatorValue("7")
@EqualsAndHashCode(callSuper = true)
public class BastardWeaponType extends WhiteWeaponType {
    @NonNull
    @Getter
    @Setter
    int oneHandedStrength;
    @NonNull
    @Getter
    @Setter
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "DET_EQ_BAST_MAP",
            joinColumns = {@JoinColumn(name = "EQ_ID")},
            inverseJoinColumns = {@JoinColumn(name = "DET_ID")})
    Set<Determinant> oneHandedDeterminants;

    public BastardWeaponType() {
        placement = Placement.BASTARD;
    }

    @Override
    public String toCsv() {
        StringBuilder builder = new StringBuilder(super.toCsv());
        builder.append(";").append(oneHandedStrength);
        builder.append(";").append(determinantsToCsv(oneHandedDeterminants));
        return builder.toString();
    }
}
