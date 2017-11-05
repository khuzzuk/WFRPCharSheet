package pl.khuzzuk.wfrpchar.gui.controllers;

import pl.khuzzuk.wfrpchar.entities.Price;
import pl.khuzzuk.wfrpchar.entities.items.Commodity;

public abstract class CommodityPaneController<T extends Commodity> extends ItemsListedController<T> {
    @Override
    void addConverters() {
        super.addConverters();
        addConverter(() -> Price.parsePrice(getPriceFromFields()), Commodity::setPrice);
        addConverter(accessibility::getValue, Commodity::setAccessibility);
    }
}
