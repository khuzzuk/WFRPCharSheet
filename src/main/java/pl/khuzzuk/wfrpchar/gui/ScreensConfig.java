package pl.khuzzuk.wfrpchar.gui;

import javafx.stage.Stage;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;

@Configuration
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
    @Inject
    public MainWindow mainWindowConfiguration(ControllersFactoryDecorator decorator) {
        return new MainWindow(decorator, stage);
    }

    @Bean
    @Inject
    public HandWeaponTypeChooserStage handWeaponTypeChooserStage(HandWeaponTypeChooserController controller) {
        return new HandWeaponTypeChooserStage(stage, controller);
    }
}
