package pl.khuzzuk.wfrpchar.gui;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class ArmorTypesPaneController implements Controller {
    @FXML
    TextField armName;
    @FXML
    ListView armorTypesList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
