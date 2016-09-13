package pl.khuzzuk.wfrpchar.gui;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.entities.items.ResourceType;
import pl.khuzzuk.wfrpchar.entities.items.SubstanceType;
import pl.khuzzuk.wfrpchar.messaging.publishers.Publishers;

import javax.inject.Inject;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class ResourceTypesPaneController implements Controller {
    @FXML
    @Numeric
    TextField resStrength;
    @FXML
    @Numeric
    TextField resPrice;
    @FXML
    private ComboBox<String> resType;
    @FXML
    private TextField resName;
    @FXML
    private ListView<String> resList;

    @Inject
    @Publishers
    private GuiPublisher guiPublisher;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeValidation();
        ComboBoxHandler.fill(EnumSet.allOf(SubstanceType.class), resType);
        guiPublisher.requestResourceTypes();
    }

    void loadAllResources(Collection<ResourceType> resources) {
        EntitiesAdapter.sendToListView(resList, resources);
    }

    @FXML
    private void selectResource() {
        EntitiesAdapter.sendQuery(resList, guiPublisher::requestResourceType);
    }

    void loadToEditor(ResourceType resource) {
        resName.setText(resource.getName());
        resStrength.setText(Integer.toString(resource.getStrengthMod()));
        resPrice.setText(Integer.toString(resource.getPriceMod()));
        resType.getSelectionModel().select(resource.getSubstanceType().getName());
    }

    @FXML
    private void save() {
        if (resName.getText().length() < 3) {
            return;
        }
        List<String> fields = new LinkedList<>();
        fields.add(resName.getText());
        fields.add(resStrength.getText());
        fields.add(resPrice.getText());
        fields.add(SubstanceType.forName(resType.getSelectionModel().getSelectedItem()).name());
        guiPublisher.saveResourceType(fields.stream().collect(Collectors.joining(";")));
    }

    @FXML
    private void remove() {
        if (resName.getText().length() >= 3) {
            guiPublisher.removeResourceType(resName.getText());
        }
    }
}
