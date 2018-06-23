package pl.khuzzuk.wfrpchar.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import pl.khuzzuk.wfrpchar.entities.Price;
import pl.khuzzuk.wfrpchar.entities.items.BattleEquipment;
import pl.khuzzuk.wfrpchar.entities.items.usable.AbstractWeapon;

public abstract class ItemWithBaseTypeController<T extends AbstractWeapon<? extends BattleEquipment>> extends AbstractFeaturedController<T> {
    @FXML
    Label endGold;
    @FXML
    Label endSilver;
    @FXML
    Label endLead;

    @Override
    void addConverters() {
        super.addConverters();
        addConverter(() -> Price.parsePrice(getPriceFromFields()), AbstractWeapon::setBasePrice);
        addConverter(accessibility::getValue, AbstractWeapon::setAccessibility);
    }

    @Override
    void loadItem(T commodity) {
        super.loadItem(commodity);
        accessibility.getSelectionModel().select(commodity.getAccessibility());
        gold.setText(commodity.getBasePrice().getGold() + "");
        silver.setText(commodity.getBasePrice().getSilver() + "");
        lead.setText(commodity.getBasePrice().getLead() + "");
        endGold.setText(commodity.getPrice().getGold() + "");
        endSilver.setText(commodity.getPrice().getSilver() + "");
        endLead.setText(commodity.getPrice().getLead() + "");
    }
}
