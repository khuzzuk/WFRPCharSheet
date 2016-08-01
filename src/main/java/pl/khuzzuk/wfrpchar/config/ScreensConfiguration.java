package pl.khuzzuk.wfrpchar.config;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import pl.khuzzuk.wfrpchar.gui.MainWindow;

@Configuration
@Lazy
public class ScreensConfiguration {
    @Setter
    private Stage stage;
    public void show(Parent screen) {
        stage.setScene(new Scene(screen, 1200, 860));
        stage.show();
    }

    @Bean
    public MainWindow mainWindowConfiguration() {
        return new MainWindow(getClass().getResource("/mainWindow.fxml"), stage);
    }
}
