package pl.khuzzuk.wfrpchar.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import pl.khuzzuk.wfrpchar.gui.EntitiesAdapter;
import pl.khuzzuk.wfrpchar.gui.GuiPublisher;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public abstract class AbstractFeaturedController extends ItemsListedController {
    @FXML
    ListView<String> determinantsView;
    @Inject
    GuiPublisher guiPublisher;
    String showDeterminantCreatorMsg;

    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
    }

    public void addDeterminant(String determinant) {
        determinantsView.getItems().add(determinant);
    }

    @FXML
    private void chooseDeterminant() {
        guiPublisher.publish(showDeterminantCreatorMsg);
    }

    @FXML
    private void removeDeterminant() {
        EntitiesAdapter.removeSelected(determinantsView);
    }

    @FXML
    void clearEditor() {
        name.setText("");
        accessibility.getSelectionModel().clearSelection();
        specialFeatures.setText("");
        gold.setText("");
        silver.setText("");
        lead.setText("");
        determinantsView.getItems().clear();
    }
}
