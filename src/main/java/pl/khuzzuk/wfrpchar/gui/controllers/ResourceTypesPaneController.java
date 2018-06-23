package pl.khuzzuk.wfrpchar.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.entities.items.ResourceType;
import pl.khuzzuk.wfrpchar.entities.items.SubstanceType;
import pl.khuzzuk.wfrpchar.gui.ComboBoxHandler;
import pl.khuzzuk.wfrpchar.gui.Numeric;

import java.net.URL;
import java.util.EnumSet;
import java.util.ResourceBundle;

@Component
public class ResourceTypesPaneController extends CommodityPaneController<ResourceType> implements Controller {
    @FXML
    @Numeric
    TextField resStrength;
    @FXML
    @Numeric
    TextField resPrice;
    @FXML
    private ComboBox<SubstanceType> resType;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        entityType = ResourceType.class;
        ComboBoxHandler.fillWithEnums(EnumSet.allOf(SubstanceType.class), resType);
        initItems();
    }

    @Override
    public void loadItem(ResourceType resource) {
        super.loadItem(resource);
        resStrength.setText(Integer.toString(resource.getStrengthMod()));
        resPrice.setText(Integer.toString(resource.getPriceMod()));
        resType.getSelectionModel().select(resource.getSubstanceType());
    }

    @Override
    void addConverters() {
        super.addConverters();
        addConverter(resStrength::getText, ResourceType::setStrengthMod, NumberUtils::toInt);
        addConverter(resPrice::getText, ResourceType::setPriceMod, NumberUtils::toInt);
        addConverter(resType::getValue, ResourceType::setSubstanceType);
    }

    @Override
    ResourceType supplyNewItem() {
        return new ResourceType();
    }

    @Override
    void clear() {
        super.clear();
        resStrength.clear();
        resPrice.clear();
        resType.getSelectionModel().clearSelection();
    }
}
