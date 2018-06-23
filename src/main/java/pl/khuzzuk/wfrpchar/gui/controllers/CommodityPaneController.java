package pl.khuzzuk.wfrpchar.gui.controllers;

import org.apache.commons.lang3.math.NumberUtils;
import pl.khuzzuk.wfrpchar.entities.Price;
import pl.khuzzuk.wfrpchar.entities.items.Commodity;

import java.util.Optional;

public abstract class CommodityPaneController<T extends Commodity> extends ItemsListedController<T> {
    @Override
    void addConverters() {
        super.addConverters();
        addConverter(() -> Price.parsePrice(getPriceFromFields()), Commodity::setPrice);
        Optional.ofNullable(weight).ifPresent(field ->
                addConverter(field::getText, Commodity::setWeight, NumberUtils::toFloat));
        addConverter(accessibility::getValue, Commodity::setAccessibility);
    }

    @Override
    void loadItem(T item) {
        super.loadItem(item);
        accessibility.getSelectionModel().select(item.getAccessibility());
        gold.setText(item.getPrice().getGold() + "");
        silver.setText(item.getPrice().getSilver() + "");
        lead.setText(item.getPrice().getLead() + "");
        Optional.ofNullable(weight).ifPresent(textField -> textField.setText(item.getWeight() + ""));
    }
}
