package pl.khuzzuk.wfrpchar.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GuiManager extends Application {
    public static void showWindow(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = null;
        root = FXMLLoader.load(getClass().getResource("/mainWindow.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
