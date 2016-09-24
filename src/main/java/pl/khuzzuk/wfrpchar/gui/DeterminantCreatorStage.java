package pl.khuzzuk.wfrpchar.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.extern.log4j.Log4j2;
import pl.khuzzuk.wfrpchar.gui.controllers.DeterminantCreatorController;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Log4j2
public class DeterminantCreatorStage extends Stage {
    private DeterminantCreatorController controller;

    public DeterminantCreatorStage(DeterminantCreatorController creatorController) {
        super(StageStyle.DECORATED);
        controller = creatorController;
    }

    @PostConstruct
    private void init() {
        controller.setParent(this);
        initModality(Modality.WINDOW_MODAL);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/determinantCreator.fxml"));
        loader.setControllerFactory(c -> controller);
        try {
            setScene(new Scene(loader.load()));
        } catch (IOException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }
}
