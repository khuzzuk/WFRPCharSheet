package pl.khuzzuk.wfrpchar.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.entities.items.ResourceType;
import pl.khuzzuk.wfrpchar.entities.items.SubstanceType;
import pl.khuzzuk.wfrpchar.gui.*;
import pl.khuzzuk.wfrpchar.messaging.publishers.Publishers;

import javax.inject.Inject;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class ResourceTypesPaneController extends ItemsListedController implements Controller {
    @FXML
    @Numeric
    TextField resStrength;
    @FXML
    @Numeric
    TextField resPrice;
    @FXML
    private ComboBox<String> resType;

    @Inject
    @Publishers
    private GuiPublisher guiPublisher;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeValidation();
        saveAction = () -> {
            List<String> fields = new LinkedList<>();
            fields.add(name.getText());
            fields.add(resStrength.getText());
            fields.add(resPrice.getText());
            fields.add(SubstanceType.forName(resType.getSelectionModel().getSelectedItem()).name());
            guiPublisher.saveResourceType(fields.stream().collect(Collectors.joining(";")));
        };
        removeAction = guiPublisher::removeResourceType;
        getAction = guiPublisher::requestResourceType;
        ComboBoxHandler.fill(EnumSet.allOf(SubstanceType.class), resType);
        guiPublisher.requestResourceTypes();
    }

    public void loadAllResources(Collection<ResourceType> resources) {
        EntitiesAdapter.sendToListView(items, resources);
    }

    public void loadToEditor(ResourceType resource) {
        name.setText(resource.getName());
        resStrength.setText(Integer.toString(resource.getStrengthMod()));
        resPrice.setText(Integer.toString(resource.getPriceMod()));
        resType.getSelectionModel().select(resource.getSubstanceType().getName());
    }

}
