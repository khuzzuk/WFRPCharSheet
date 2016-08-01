package pl.khuzzuk.wfrpchar.gui;

import javafx.stage.Stage;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import javax.inject.Inject;

@Configuration
@Lazy
public class ScreensConfig {
    @Setter
    private Stage stage;
    @Inject
    private MainWindowControllerWW controller;

    @Bean
    public MainWindow mainWindowConfiguration() {
        return new MainWindow(controller, stage);
    }
}
