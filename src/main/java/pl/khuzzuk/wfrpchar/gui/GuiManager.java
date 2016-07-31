package pl.khuzzuk.wfrpchar.gui;

import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import pl.khuzzuk.wfrpchar.config.ScreensConfiguration;

public class GuiManager extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        ApplicationContext context = new AnnotationConfigApplicationContext("pl.khuzzuk.wfrpchar.config");
        ScreensConfiguration screens = context.getBean(ScreensConfiguration.class);
        screens.setStage(primaryStage);
        //Parent root = null;
        //root = FXMLLoader.load(getClass().getResource("/mainWindow.fxml"));
        //primaryStage.setScene(new Scene(root));
        //primaryStage.show();
    }
}
