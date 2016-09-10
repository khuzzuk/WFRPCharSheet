package pl.khuzzuk.wfrpchar.entities.items.usable;

import lombok.Getter;
import lombok.Setter;
import pl.khuzzuk.wfrpchar.entities.Price;
import pl.khuzzuk.wfrpchar.entities.items.Accessibility;
import pl.khuzzuk.wfrpchar.entities.items.Commodity;

import javax.persistence.*;

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "class", discriminatorType = DiscriminatorType.INTEGER)
@Entity
@DiscriminatorValue("0")
public abstract class AbstractCommodity implements Commodity {
    @Id
    @Getter
    @Setter
    private long id;
    @Getter
    @Setter
    private Accessibility accessibility;
    @Getter
    @Setter
    private Price basePrice;
}
