package pl.khuzzuk.wfrpchar.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import pl.khuzzuk.wfrpchar.entities.Featured;
import pl.khuzzuk.wfrpchar.gui.EntitiesAdapter;

import java.net.URL;
import java.util.ResourceBundle;

public abstract class AbstractFeaturedController<T extends Featured> extends ItemsListedController<T> {
    @FXML
    ListView<String> determinantsView;
    String showDeterminantCreatorMsg;

    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
    }

    public void addDeterminant(String determinant) {
        determinantsView.getItems().add(determinant);
    }

    @FXML
    private void chooseDeterminant() {
        bus.send(showDeterminantCreatorMsg);
    }

    @FXML
    private void removeDeterminant() {
        EntitiesAdapter.removeSelected(determinantsView);
    }

    @FXML
    void clearEditor() {
        super.clear();
        determinantsView.getItems().clear();
    }
}
