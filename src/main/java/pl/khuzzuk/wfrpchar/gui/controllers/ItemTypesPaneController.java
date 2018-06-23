package pl.khuzzuk.wfrpchar.gui.controllers;

import javafx.fxml.FXML;
import org.springframework.stereotype.Component;
import pl.khuzzuk.messaging.Bus;
import pl.khuzzuk.wfrpchar.entities.items.Accessibility;
import pl.khuzzuk.wfrpchar.entities.items.types.EquipmentType;
import pl.khuzzuk.wfrpchar.entities.items.types.MiscItem;
import pl.khuzzuk.wfrpchar.gui.ComboBoxHandler;

import javax.inject.Inject;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Component
public class ItemTypesPaneController extends CommodityPaneController<MiscItem> {
    @Inject
    private Bus bus;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeValidation();
        entityType = MiscItem.class;
        ComboBoxHandler.fillWithEnums(Accessibility.SET, accessibility);
        initItems();
    }

    @Override
    MiscItem supplyNewItem() {
        return new MiscItem();
    }
}
