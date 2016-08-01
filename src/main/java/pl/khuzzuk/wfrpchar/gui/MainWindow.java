package pl.khuzzuk.wfrpchar.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import pl.khuzzuk.wfrpchar.db.DAO;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URL;

public class MainWindow extends Stage {
    @Autowired
    private ApplicationContext context;

    protected final MainWindowControllerWW controller;

    public MainWindow(URL fxml, Window parent) {
        super(StageStyle.DECORATED);
        initOwner(parent);
        initModality(Modality.WINDOW_MODAL);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mainWindow.fxml"));
        try {
            Parent root = loader.load();
            setScene(new Scene(root));
            controller = loader.getController();
            controller.setMainWindow(this);
            show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PostConstruct
    private void autowire() {
        context.getAutowireCapableBeanFactory()
                .autowireBeanProperties(controller, AutowireCapableBeanFactory.AUTOWIRE_NO, false);
        controller.setDao(context.getBean(DAO.class));
    }
}
