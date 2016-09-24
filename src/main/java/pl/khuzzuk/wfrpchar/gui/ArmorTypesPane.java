package pl.khuzzuk.wfrpchar.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.TitledPane;
import javafx.stage.Window;
import pl.khuzzuk.wfrpchar.gui.controllers.ArmorTypesPaneController;

public class ArmorTypesPane extends TitledPane {
    public ArmorTypesPane(ArmorTypesPaneController armorTypesController, Window owner) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/armorTypePane.fxml"));
        loader.setControllerFactory(c -> armorTypesController);
    }
}
