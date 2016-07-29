package pl.khuzzuk.wfrpchar.entities.items;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import pl.khuzzuk.wfrpchar.determinants.Extension;

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
    List<Extension> oneHandedExtension;

    @Override
    public String toCsv() {
        return null;
    }
}
