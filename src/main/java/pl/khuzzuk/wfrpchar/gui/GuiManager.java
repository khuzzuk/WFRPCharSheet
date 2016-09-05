package pl.khuzzuk.wfrpchar.gui;

import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class GuiManager extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        ApplicationContext context = new AnnotationConfigApplicationContext("pl.khuzzuk.wfrpchar.config");
        ScreensConfig screens = context.getBean(ScreensConfig.class);
        screens.setStage(primaryStage);
        ControllersFactoryDecorator decorator = context.getBean(ControllersFactoryDecorator.class);
        screens.mainWindowConfiguration(decorator).show();
    }
}
