package pl.khuzzuk.wfrpchar.entities.items.types;

import lombok.*;
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
public abstract class Item implements Named<String>, Commodity, Persistable {
    @Id
    @GeneratedValue
    @Getter
    @Setter
    long id;
    @Getter
    @Setter
    @NonNull
    String name;
    @NonNull
    @Getter
    @Setter
    EquipmentType type;
    @Getter
    @Setter
    @NonNull
    float weight;
    @Getter
    @Setter
    @NonNull
    @Embedded
    Price price;
    @Getter
    @Setter
    @NonNull
    Accessibility accessibility;
    @Getter
    @Setter
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
