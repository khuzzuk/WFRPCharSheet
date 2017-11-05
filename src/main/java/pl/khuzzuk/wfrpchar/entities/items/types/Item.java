package pl.khuzzuk.wfrpchar.entities.items.types;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.khuzzuk.wfrpchar.entities.Named;
import pl.khuzzuk.wfrpchar.entities.Price;
import pl.khuzzuk.wfrpchar.entities.items.Accessibility;
import pl.khuzzuk.wfrpchar.entities.items.Commodity;

@NoArgsConstructor
@Data
public abstract class Item implements Named<String>, Commodity {
    String name;
    EquipmentType type;
    float weight;
    Price price;
    Accessibility accessibility;
    String specialFeatures;

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
