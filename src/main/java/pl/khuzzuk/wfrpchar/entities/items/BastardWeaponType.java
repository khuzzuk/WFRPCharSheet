package pl.khuzzuk.wfrpchar.entities.items;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import pl.khuzzuk.wfrpchar.determinants.Determinant;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@DiscriminatorValue("7")
public class BastardWeaponType extends WhiteWeaponType {
    @NonNull
    @Getter
    @Setter
    int oneHandedStrength;
    @NonNull
    @Getter
    @Setter
    @OneToMany
    List<Determinant> oneHandedDeterminants;

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
