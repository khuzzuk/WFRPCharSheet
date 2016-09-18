package pl.khuzzuk.wfrpchar.gui;

import javafx.stage.Stage;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Properties;

@Configuration
public class ScreensConfig {
    @Setter
    private Stage stage;
    @Inject
    @MainWindowBean
    private MainWindowController controller;
    @Inject
    private ArmorTypesPaneController armorTypesController;
    @Inject
    @Named("messages")
    private Properties messages;

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

    @Bean
    @Named("hwDeterminantsCreatorController")
    public DeterminantCreatorController handWeaponDeterminantController() {
        return new DeterminantCreatorController(messages.getProperty("determinants.creator.show.hw"),
                messages.getProperty("determinants.creator.add.hw"));
    }

    @Bean
    @Named("handWeaponsDeterminantsCreatorStage")
    public DeterminantCreatorStage determinantCreatorStage() {
        return new DeterminantCreatorStage(handWeaponDeterminantController());
    }
}
