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
    @MainWindowBean
    private MainWindowController controller;
    @Inject
    private ArmorTypesPaneController armorTypesController;

    @Bean
    @MainWindowBean
    public MainWindow mainWindowConfiguration() {
        return new MainWindow(controller, stage);
    }

    @Bean
    @Inject
    public ArmorTypesPane armorTypesPane(MainWindow window) {
        return new ArmorTypesPane(armorTypesController, window);
    }
}
