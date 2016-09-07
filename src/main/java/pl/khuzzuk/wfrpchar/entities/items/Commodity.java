package pl.khuzzuk.wfrpchar.entities.items;

import pl.khuzzuk.wfrpchar.entities.Price;

public interface Commodity {
    Accessibility getAccessibility();

    Price getPrice();
}
