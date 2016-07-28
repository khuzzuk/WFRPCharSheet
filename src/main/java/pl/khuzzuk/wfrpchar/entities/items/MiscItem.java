package pl.khuzzuk.wfrpchar.entities.items;

import lombok.NoArgsConstructor;
import pl.khuzzuk.wfrpchar.entities.Price;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("1")
@NoArgsConstructor
public class MiscItem extends Item {
    public MiscItem(String name, float weight, Price price, Accessibility accessibility) {
        this.name = name;
        this.weight = weight;
        this.price = price;
        this.accessibility = accessibility;
    }
}
