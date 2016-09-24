package pl.khuzzuk.wfrpchar.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import pl.khuzzuk.wfrpchar.gui.controllers.MainWindowController;

import javax.annotation.PostConstruct;
import java.io.IOException;

public class MainWindow extends Stage {
    @Autowired
    private ApplicationContext context;

    private final MainWindowController controller;

    public MainWindow(Callback<Class<?>, Object> factoryDecorator, Window parent) {
        super(StageStyle.DECORATED);
        initOwner(parent);
        initModality(Modality.WINDOW_MODAL);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mainWindow.fxml"));
        loader.setControllerFactory(factoryDecorator);
        try {
            Parent root = loader.load();
            setScene(new Scene(root));
            this.controller = loader.getController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PostConstruct
    private void autowire() {
        context.getAutowireCapableBeanFactory()
                .autowireBeanProperties(controller, AutowireCapableBeanFactory.AUTOWIRE_NO, false);
    }
}
