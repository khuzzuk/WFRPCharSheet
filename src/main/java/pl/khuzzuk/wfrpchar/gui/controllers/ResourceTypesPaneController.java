package pl.khuzzuk.wfrpchar.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.entities.items.ResourceType;
import pl.khuzzuk.wfrpchar.entities.items.SubstanceType;
import pl.khuzzuk.wfrpchar.gui.ComboBoxHandler;
import pl.khuzzuk.wfrpchar.gui.EntitiesAdapter;
import pl.khuzzuk.wfrpchar.gui.Numeric;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class ResourceTypesPaneController extends ItemsListedController<ResourceType> implements Controller {
    @FXML
    @Numeric
    TextField resStrength;
    @FXML
    @Numeric
    TextField resPrice;
    @FXML
    private ComboBox<String> resType;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeValidation();
        entityType = ResourceType.class;
        saveTopic = messages.getProperty("resource.type.save");
        removeEntityTopic = messages.getProperty("resource.type.remove");
        getAllResponse = messages.getProperty("resource.type.result");
        clearAction = this::clear;
        ComboBoxHandler.fill(EnumSet.allOf(SubstanceType.class), resType);
        initItems();
    }

    public void loadAllResources(Collection<ResourceType> resources) {
        EntitiesAdapter.sendToListView(items, resources);
    }

    @Override
    public void loadItem(ResourceType resource) {
        super.loadItem(resource);
        name.setText(resource.getName());
        resStrength.setText(Integer.toString(resource.getStrengthMod()));
        resPrice.setText(Integer.toString(resource.getPriceMod()));
        resType.getSelectionModel().select(resource.getSubstanceType().getName());
    }

    @Override
    void saveAction() {
        List<String> fields = new LinkedList<>();
        fields.add(name.getText());
        fields.add(resStrength.getText());
        fields.add(resPrice.getText());
        fields.add(SubstanceType.forName(resType.getSelectionModel().getSelectedItem()).name());
        bus.send(messages.getProperty("resource.type.save"));
        saveItem(fields.stream().collect(Collectors.joining(";")));
    }

    @Override
    void clear() {
        super.clear();
        resStrength.clear();
        resPrice.clear();
        resType.getSelectionModel().clearSelection();
    }
}
