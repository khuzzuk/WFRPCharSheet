package pl.khuzzuk.wfrpchar.entities.items.types;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.NaturalId;
import pl.khuzzuk.wfrpchar.entities.Documented;
import pl.khuzzuk.wfrpchar.entities.Named;
import pl.khuzzuk.wfrpchar.entities.Persistable;
import pl.khuzzuk.wfrpchar.entities.Price;
import pl.khuzzuk.wfrpchar.entities.items.Accessibility;
import pl.khuzzuk.wfrpchar.entities.items.Commodity;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "class", discriminatorType = DiscriminatorType.INTEGER)
@DiscriminatorValue(value = "0")
@NoArgsConstructor
@Data
public abstract class Item implements Named<String>, Commodity, Persistable, Documented {
    @Id
    @GeneratedValue
    long id;
    @NonNull
    @NaturalId
    String name;
    @NonNull
    EquipmentType type;
    @NonNull
    float weight;
    @NonNull
    @Embedded
    Price price;
    @NonNull
    Accessibility accessibility;
    String specialFeatures;

    public abstract String toCsv();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item that = (Item) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (int) weight;
        return result;
    }

    @Override
    public String toString() {
        return name;
    }
}
