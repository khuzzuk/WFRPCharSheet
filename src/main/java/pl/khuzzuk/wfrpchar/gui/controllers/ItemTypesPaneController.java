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
public class ItemTypesPaneController extends ItemsListedController<MiscItem> {
    @Inject
    private Bus bus;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeValidation();
        entityType = MiscItem.class;
        removeEntityTopic = messages.getProperty("miscItemTypes.remove");
        getAllResponse = messages.getProperty("miscItemTypes.result");
        saveTopic = messages.getProperty("database.saveEquipment");
        clearAction = this::clear;
        ComboBoxHandler.fillWithEnums(Accessibility.SET, accessibility);
        initItems();
    }

    @Override
    public void loadItem(MiscItem item) {
        super.loadItem(item);
        name.setText(item.getName());
        weight.setText(item.getWeight() + "");
        specialFeatures.setText(item.getSpecialFeatures());
        gold.setText(item.getPrice().getGold() + "");
        silver.setText(item.getPrice().getSilver() + "");
        lead.setText(item.getPrice().getLead() + "");
        accessibility.getSelectionModel().select(item.getAccessibility());
    }

    @Override
    MiscItem supplyNewItem() {
        return new MiscItem();
    }

    @FXML
    @Override
    void saveAction() {
        if (name.getText().length() < 3) {
            return;
        }
        List<String> line = new LinkedList<>();
        line.add(name.getText());
        line.add(weight.getText());
        line.add(getPriceFromFields());
        line.add(accessibility.getSelectionModel().getSelectedItem().name());
        line.add(specialFeatures.getText());
        line.add("");
        line.add(EquipmentType.MISC_ITEM.name());
        saveItem(line.stream().collect(Collectors.joining(";")));
    }
}
