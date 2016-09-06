package pl.khuzzuk.wfrpchar.gui;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.entities.items.Accessibility;
import pl.khuzzuk.wfrpchar.entities.items.MiscItem;
import pl.khuzzuk.wfrpchar.messaging.publishers.Publishers;

import javax.inject.Inject;
import java.net.URL;
import java.util.Collection;
import java.util.EnumSet;
import java.util.ResourceBundle;
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
        itemTypesList.getItems().clear();
        itemTypesList.getItems().addAll(miscItems.stream().map(MiscItem::getName).collect(Collectors.toList()));
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
        }
    }
}
