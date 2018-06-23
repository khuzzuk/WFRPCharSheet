package pl.khuzzuk.wfrpchar.entities.items.usable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import pl.khuzzuk.wfrpchar.entities.Named;
import pl.khuzzuk.wfrpchar.entities.Price;
import pl.khuzzuk.wfrpchar.entities.items.Accessibility;
import pl.khuzzuk.wfrpchar.entities.items.Commodity;

import java.util.List;

@Getter
@Setter
public abstract class AbstractCommodity implements Commodity, Named<String> {
    private String name;
    private Accessibility accessibility;
    private Price basePrice; // Additional price to calculated - it usualy is like basePrice + (baseType.price * resource.priceMultiplier)
    private String specialFeatures;

    void fillCommodityFields(List<String> fields) {
        fields.add(name);
        fields.add(basePrice.toString());
        fields.add(accessibility.name());
        fields.add(specialFeatures);
    }

    @Override
    public boolean equals(Object o) {
        return namedEquals(o);
    }

    @Override
    public int hashCode() {
        return namedHashCode();
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    @JsonIgnore
    public Price getPrice() {
        return basePrice;
    }

    @Override
    @JsonIgnore
    public void setPrice(Price price) {
        basePrice = price;
    }

    @Override
    public float getWeight() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setWeight(float weight) {
        throw new UnsupportedOperationException();
    }
}
