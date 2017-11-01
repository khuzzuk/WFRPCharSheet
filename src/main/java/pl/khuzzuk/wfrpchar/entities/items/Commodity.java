package pl.khuzzuk.wfrpchar.entities.items;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import pl.khuzzuk.wfrpchar.entities.Named;
import pl.khuzzuk.wfrpchar.entities.Persistable;
import pl.khuzzuk.wfrpchar.entities.Price;
import pl.khuzzuk.wfrpchar.repo.TypeIdResolver;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id",
        resolver = TypeIdResolver.class,
        scope = Commodity.class)
public interface Commodity extends Named<String>, Persistable {
    Accessibility getAccessibility();

    Price getPrice();

    String getSpecialFeatures();

    float getWeight();
}
