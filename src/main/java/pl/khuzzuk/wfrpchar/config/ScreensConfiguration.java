package pl.khuzzuk.wfrpchar.config;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.khuzzuk.wfrpchar.gui.MainWindowConfiguration;

@Configuration
public class ScreensConfiguration {
    @Setter
    private Stage stage;
    public void show(Parent screen) {
        stage.setScene(new Scene(screen, 1200, 860));
    }

    @Bean
    MainWindowConfiguration mainWindowConfiguration() {
        return new MainWindowConfiguration(getClass().getResource("/mainWindow.fxml"), stage);
    }
}
