package pl.khuzzuk.wfrpchar.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.gui.controllers.EquipmentTypeChooserController;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Log
public class EquipmentTypeChooserStage extends Stage {
    @Autowired
    private ApplicationContext context;
    private final EquipmentTypeChooserController controller;
    EquipmentTypeChooserStage(Window parent, EquipmentTypeChooserController controller) {
        super(StageStyle.DECORATED);
        this.controller = controller;
        controller.setParent(this);
        initOwner(parent);
        initModality(Modality.WINDOW_MODAL);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/handWeaponTypeChooser.fxml"));
        loader.setControllerFactory(c -> controller);
        try {
            Parent root = loader.load();
            setScene(new Scene(root));
        } catch (IOException e) {
            log.severe(e.getMessage());
            throw new RuntimeException(e);
        }
    }
    @PostConstruct
    private void autowire() {
        context.getAutowireCapableBeanFactory()
                .autowireBeanProperties(controller, AutowireCapableBeanFactory.AUTOWIRE_NO, false);
    }
}
