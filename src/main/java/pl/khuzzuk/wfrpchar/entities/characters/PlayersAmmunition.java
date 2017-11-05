package pl.khuzzuk.wfrpchar.entities.characters;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import pl.khuzzuk.wfrpchar.entities.Documented;
import pl.khuzzuk.wfrpchar.entities.Named;
import pl.khuzzuk.wfrpchar.entities.Price;
import pl.khuzzuk.wfrpchar.entities.determinants.Determinant;
import pl.khuzzuk.wfrpchar.entities.items.Accessibility;
import pl.khuzzuk.wfrpchar.entities.items.Ammo;
import pl.khuzzuk.wfrpchar.entities.items.Placement;
import pl.khuzzuk.wfrpchar.entities.items.usable.Ammunition;

import java.util.Collection;
import java.util.Set;

@Getter
@Setter
public class PlayersAmmunition implements Ammo, Documented, Named<String> {
    private long id;
    @JsonIdentityReference(alwaysAsId = true)
    private Ammunition ammunition;
    private int count;

    @Override
    @JsonIgnore
    public int getStrength() {
        return ammunition.getStrength();
    }

    @Override
    @JsonIgnore
    public String getName() {
        return ammunition.getName();
    }

    @Override
    @JsonIgnore
    public Accessibility getAccessibility() {
        return ammunition.getAccessibility();
    }

    @Override
    @JsonIgnore
    public Price getPrice() {
        return ammunition.getPrice();
    }

    @Override
    @JsonIgnore
    public Collection<Determinant> getAllDeterminants() {
        return ammunition.getAllDeterminants();
    }

    @Override
    @JsonIgnore
    public Set<Determinant> getDeterminants() {
        return ammunition.getDeterminants();
    }

    @Override
    @JsonIgnore
    public String getSpecialFeatures() {
        return ammunition.getSpecialFeatures();
    }

    @Override
    @JsonIgnore
    public void setDeterminants(Set<Determinant> determinants) {
        ammunition.setDeterminants(determinants);
    }

    @Override
    @JsonIgnore
    public float getWeight() {
        return ammunition.getWeight();
    }

    @Override
    @JsonIgnore
    public Placement getPlacement() {
        return ammunition.getPlacement();
    }

    @Override
    public void setPlacement(Placement placement) {
        throw new UnsupportedOperationException();
    }

    @Override
    @JsonIgnore
    public String getTypeName() {
        return ammunition.getTypeName();
    }

    @Override
    public void addDeterminant(Determinant determinant) {
        ammunition.addDeterminant(determinant);
    }

    @Override
    @JsonIgnore
    public Collection<Determinant> getBaseDeterminants() {
        return ammunition.getBaseDeterminants();
    }

    @Override
    public String toCsv() {
        return getName() + ":" + getCount();
    }

    @Override
    public void setAccessibility(Accessibility accessibility) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setPrice(Price price) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setSpecialFeatures(String specialFeatures) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setName(String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setWeight(float weight) {
        throw new UnsupportedOperationException();
    }
}
