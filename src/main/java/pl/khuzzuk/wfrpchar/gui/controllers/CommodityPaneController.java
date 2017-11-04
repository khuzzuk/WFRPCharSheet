package pl.khuzzuk.wfrpchar.gui.controllers;

import pl.khuzzuk.wfrpchar.entities.Price;
import pl.khuzzuk.wfrpchar.entities.items.Commodity;

public abstract class CommodityPaneController<T extends Commodity> extends ItemsListedController<T> {
    @Override
    void fillItemWithValues(T item) {
        super.fillItemWithValues(item);
        item.setPrice(Price.parsePrice(getPriceFromFields()));
        item.setAccessibility(accessibility.getSelectionModel().getSelectedItem());
    }
}
