package pl.khuzzuk.wfrpchar.entities.characters;

import lombok.Getter;
import lombok.Setter;
import pl.khuzzuk.wfrpchar.db.DAO;
import pl.khuzzuk.wfrpchar.entities.Documented;
import pl.khuzzuk.wfrpchar.entities.Named;
import pl.khuzzuk.wfrpchar.entities.Price;
import pl.khuzzuk.wfrpchar.entities.determinants.Determinant;
import pl.khuzzuk.wfrpchar.entities.items.Accessibility;
import pl.khuzzuk.wfrpchar.entities.items.Ammo;
import pl.khuzzuk.wfrpchar.entities.items.Placement;
import pl.khuzzuk.wfrpchar.entities.items.usable.Ammunition;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Collection;
import java.util.Set;

@Entity
public class PlayersAmmunition implements Ammo, Documented, Named<String> {
    @Id
    @GeneratedValue
    @Getter
    @Setter
    private long id;
    @Getter
    @Setter
    @ManyToOne
    private Ammunition ammunition;
    @Getter
    @Setter
    private int count;

    @Override
    public int getStrength() {
        return ammunition.getStrength();
    }

    @Override
    public String getName() {
        return ammunition.getName();
    }

    @Override
    public Accessibility getAccessibility() {
        return ammunition.getAccessibility();
    }

    @Override
    public Price getPrice() {
        return ammunition.getPrice();
    }

    @Override
    public Set<Determinant> getDeterminants() {
        return ammunition.getDeterminants();
    }

    @Override
    public String getSpecialFeatures() {
        return ammunition.getSpecialFeatures();
    }

    @Override
    public void setDeterminants(Set<Determinant> determinants) {
        ammunition.setDeterminants(determinants);
    }

    @Override
    public float getWeight() {
        return ammunition.getWeight();
    }

    @Override
    public Placement getPlacement() {
        return ammunition.getPlacement();
    }

    @Override
    public String getTypeName() {
        return ammunition.getTypeName();
    }

    @Override
    public void addDeterminant(Determinant determinant) {
        ammunition.addDeterminant(determinant);
    }

    @Override
    public Collection<Determinant> getBaseDeterminants() {
        return ammunition.getBaseDeterminants();
    }

    @Override
    public String toCsv() {
        return getName() + ":" + getCount();
    }

    public static PlayersAmmunition fromCsv(String[] fields, DAO dao) {
        if (fields.length <= 1) {
            return null;
        }
        PlayersAmmunition ammunition = new PlayersAmmunition();
        ammunition.setAmmunition(dao.getEntity(Ammunition.class, fields[0]));
        ammunition.setCount(Integer.parseInt(fields[1]));
        return ammunition;
    }
}
