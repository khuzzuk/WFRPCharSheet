package pl.khuzzuk.wfrpchar.entities.items.usable;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;
import pl.khuzzuk.wfrpchar.entities.Documented;
import pl.khuzzuk.wfrpchar.entities.Named;
import pl.khuzzuk.wfrpchar.entities.Price;
import pl.khuzzuk.wfrpchar.entities.items.Accessibility;
import pl.khuzzuk.wfrpchar.entities.items.Commodity;

import javax.persistence.*;
import java.util.List;

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "class", discriminatorType = DiscriminatorType.INTEGER)
@Entity
@DiscriminatorValue("0")
@Table(name = "items_entities")
@Getter
@Setter
@JsonIdentityInfo(property = "id", generator = ObjectIdGenerators.PropertyGenerator.class)
public abstract class AbstractCommodity implements Commodity, Named<String>, Documented {
    @Id
    @GeneratedValue
    private long id;
    @NaturalId
    private String name;
    private Accessibility accessibility;
    private Price basePrice;
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
}
