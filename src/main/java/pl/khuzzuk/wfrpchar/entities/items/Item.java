package pl.khuzzuk.wfrpchar.entities.items;

import lombok.Getter;
import lombok.Setter;
import pl.khuzzuk.wfrpchar.entities.Price;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE", discriminatorType = DiscriminatorType.INTEGER)
@DiscriminatorValue(value = "0")
public class Item {
    @Id
    @GeneratedValue
    @Getter
    @Setter
    protected long id;
    @Getter
    @Setter
    protected String name;
    @Getter
    @Setter
    protected float weight;
    @Getter
    @Setter
    protected Price price;
}
