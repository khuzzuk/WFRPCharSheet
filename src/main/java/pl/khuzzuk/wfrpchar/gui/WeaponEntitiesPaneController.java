package pl.khuzzuk.wfrpchar.gui;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class WeaponEntitiesPaneController extends ItemsListedController implements Controller {
    @FXML
    @Numeric
    TextField weGold;
    @FXML
    private ListView wepList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeValidation();
    }
}
