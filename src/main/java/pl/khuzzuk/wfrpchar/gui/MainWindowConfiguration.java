package pl.khuzzuk.wfrpchar.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URL;

public class MainWindowConfiguration extends Stage {
    @Autowired
    private ApplicationContext context;

    protected final Object controller;

    public MainWindowConfiguration(URL fxml, Window parent) {
        super(StageStyle.DECORATED);
        initOwner(parent);
        initModality(Modality.WINDOW_MODAL);
        FXMLLoader loader = new FXMLLoader(fxml);
        try {
            setScene(new Scene(loader.load()));
            controller = loader.getController();
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
