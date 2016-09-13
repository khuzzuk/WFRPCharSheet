package pl.khuzzuk.wfrpchar.gui;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.entities.items.Accessibility;
import pl.khuzzuk.wfrpchar.entities.items.types.EquipmentType;
import pl.khuzzuk.wfrpchar.entities.items.types.MiscItem;
import pl.khuzzuk.wfrpchar.messaging.publishers.Publishers;

import javax.inject.Inject;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class ItemTypesPaneController implements Controller {
    @Inject
    @Publishers
    private GuiPublisher publisher;
    @FXML
    private ComboBox<String> iAccessibility;
    @FXML
    private TextArea iSpecialFeatures;
    @FXML
    private TextField iGold;
    @FXML
    private TextField iSilver;
    @FXML
    private TextField iLead;
    @FXML
    private TextField iWeight;
    @FXML
    private TextField iName;
    @FXML
    private ListView<String> itemTypesList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeValidation();
        ComboBoxHandler.fill(EnumSet.allOf(Accessibility.class), iAccessibility);
        publisher.requestMiscItemType();
    }

    void loadMiscItemTypes(Collection<MiscItem> miscItems) {
        EntitiesAdapter.sendToListView(itemTypesList, miscItems);
    }

    void loadMiscItemToEditor(MiscItem item) {
        iName.setText(item.getName());
        iWeight.setText(item.getWeight() + "");
        iSpecialFeatures.setText(item.getSpecialFeature());
        iGold.setText(item.getPrice().getGold() + "");
        iSilver.setText(item.getPrice().getSilver() + "");
        iLead.setText(item.getPrice().getLead() + "");
        iAccessibility.getSelectionModel().select(item.getAccessibility().getName());
    }

    @FXML
    private void getItemType() {
        String name = itemTypesList.getSelectionModel().getSelectedItem();
        if (name != null && name.length() > 2) {
            publisher.requestMiscItemTypeLoad(name);
        }
    }

    @FXML
    private void saveItem() {
        if (iName.getText().length() < 3) {
            return;
        }
        List<String> line = new LinkedList<>();
        line.add(iName.getText());
        line.add(iWeight.getText());
        line.add(iGold.getText() + "|" + iSilver.getText() + "|" + iLead.getText());
        line.add(Accessibility.forName(iAccessibility.getSelectionModel().getSelectedItem()).name());
        line.add(iSpecialFeatures.getText());
        line.add("");
        line.add(EquipmentType.MISC_ITEM.name());
        publisher.saveItem(line.stream().collect(Collectors.joining(";")));
    }

    @FXML
    private void removeItem() {
        String name = iName.getText();
        if (name.length() > 2) {
            publisher.removeMiscItemType(name);
        }
    }
}
