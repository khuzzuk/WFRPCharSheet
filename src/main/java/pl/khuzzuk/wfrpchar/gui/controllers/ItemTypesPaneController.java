package pl.khuzzuk.wfrpchar.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.entities.items.Accessibility;
import pl.khuzzuk.wfrpchar.entities.items.types.EquipmentType;
import pl.khuzzuk.wfrpchar.entities.items.types.MiscItem;
import pl.khuzzuk.wfrpchar.gui.ComboBoxHandler;
import pl.khuzzuk.wfrpchar.gui.GuiPublisher;
import pl.khuzzuk.wfrpchar.messaging.publishers.Publishers;

import javax.inject.Inject;
import java.net.URL;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Component
public class ItemTypesPaneController extends ItemsListedController {
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeValidation();
        saveAction = this::saveItem;
        getAction = guiPublisher::requestMiscItemTypeLoad;
        removeAction = guiPublisher::removeMiscItemType;
        ComboBoxHandler.fill(EnumSet.allOf(Accessibility.class), iAccessibility);
        publisher.requestMiscItemType();
    }

    public void loadMiscItemToEditor(MiscItem item) {
        name.setText(item.getName());
        iWeight.setText(item.getWeight() + "");
        iSpecialFeatures.setText(item.getSpecialFeature());
        iGold.setText(item.getPrice().getGold() + "");
        iSilver.setText(item.getPrice().getSilver() + "");
        iLead.setText(item.getPrice().getLead() + "");
        iAccessibility.getSelectionModel().select(item.getAccessibility().getName());
    }

    @FXML
    private void saveItem() {
        if (name.getText().length() < 3) {
            return;
        }
        List<String> line = new LinkedList<>();
        line.add(name.getText());
        line.add(iWeight.getText());
        line.add(iGold.getText() + "|" + iSilver.getText() + "|" + iLead.getText());
        line.add(Accessibility.forName(iAccessibility.getSelectionModel().getSelectedItem()).name());
        line.add(iSpecialFeatures.getText());
        line.add("");
        line.add(EquipmentType.MISC_ITEM.name());
        publisher.saveItem(line.stream().collect(Collectors.joining(";")));
    }
}
