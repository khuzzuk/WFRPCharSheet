package pl.khuzzuk.wfrpchar.gui;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;

@Component
public class ArmorTypesPaneController {
    @FXML
    TextField armName;
    @FXML
    ListView armorTypesList;
}
