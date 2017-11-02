package pl.khuzzuk.wfrpchar.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import pl.khuzzuk.wfrpchar.entities.items.usable.AbstractCommodity;

public abstract class ItemWithBaseTypeController<T> extends AbstractFeaturedController<T> {
    @FXML
    Label endGold;
    @FXML
    Label endSilver;
    @FXML
    Label endLead;

    void loadToInternalEditor(AbstractCommodity commodity) {
        super.loadToInternalEditor(commodity);
        gold.setText(commodity.getBasePrice().getGold() + "");
        silver.setText(commodity.getBasePrice().getSilver() + "");
        lead.setText(commodity.getBasePrice().getLead() + "");
        endGold.setText(commodity.getPrice().getGold() + "");
        endSilver.setText(commodity.getPrice().getSilver() + "");
        endLead.setText(commodity.getPrice().getLead() + "");
    }
}
