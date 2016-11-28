package pl.khuzzuk.wfrpchar.gui;

import javafx.application.Application;
import javafx.stage.Stage;
import lombok.extern.java.Log;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import pl.khuzzuk.wfrpchar.gui.controllers.ControllersFactoryDecorator;

@Log
public class GuiManager extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        ApplicationContext context = new AnnotationConfigApplicationContext("pl.khuzzuk.wfrpchar.config");
        log.info("context ready");
        ScreensConfig screens = context.getBean(ScreensConfig.class);
        primaryStage.setOnCloseRequest(e -> System.exit(0));
        screens.setStage(primaryStage);
        ControllersFactoryDecorator decorator = context.getBean(ControllersFactoryDecorator.class);
        screens.mainWindowConfiguration(decorator).show();
    }
}
