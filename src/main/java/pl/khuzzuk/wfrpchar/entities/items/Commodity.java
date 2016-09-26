package pl.khuzzuk.wfrpchar.entities.items;

import pl.khuzzuk.wfrpchar.entities.Named;
import pl.khuzzuk.wfrpchar.entities.Persistable;
import pl.khuzzuk.wfrpchar.entities.Price;

public interface Commodity extends Named<String>, Persistable {
    Accessibility getAccessibility();

    Price getPrice();

    String getSpecialFeatures();
}
