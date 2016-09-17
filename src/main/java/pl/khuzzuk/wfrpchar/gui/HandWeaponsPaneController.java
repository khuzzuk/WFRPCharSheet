package pl.khuzzuk.wfrpchar.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.entities.items.types.WhiteWeaponType;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

@Component
@PropertySource("classpath:messages.properties")
public class HandWeaponsPaneController extends ItemsListedController {
    @FXML
    private Button chooseBaseButton;
    @Inject
    private GuiPublisher guiPublisher;
    private WhiteWeaponType baseType;
    @Value("${weapons.hand.baseType.getAllTypes}")
    private String startBaseTypeChoice;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeValidation();
    }

    @FXML
    private void chooseBaseType() {
        guiPublisher.publish(startBaseTypeChoice);
    }
}
