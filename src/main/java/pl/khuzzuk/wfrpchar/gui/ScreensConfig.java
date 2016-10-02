package pl.khuzzuk.wfrpchar.gui;

import javafx.stage.Stage;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import pl.khuzzuk.wfrpchar.gui.controllers.*;

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
    @Scope(value = "prototype")
    public EquipmentTypeChooserController equipmentTypeChooserController() {
        return new EquipmentTypeChooserController();
    }

    @Bean
    public EquipmentTypeChooserStage handWeaponTypeChooserStage() {
        EquipmentTypeChooserController controller = equipmentTypeChooserController();
        controller.setMessageType(messages.getProperty("weapons.hand.baseType.allTypesList"));
        controller.setChoiceReady(messages.getProperty("weapons.hand.baseType.choice"));
        controller.subscribe();
        return new EquipmentTypeChooserStage(stage, controller);
    }

    @Bean
    public EquipmentTypeChooserStage rangedWeaponTypeChooser() {
        EquipmentTypeChooserController controller = equipmentTypeChooserController();
        controller.setMessageType(messages.getProperty("weapons.ranged.baseType.allTypesList"));
        controller.setChoiceReady(messages.getProperty("weapons.ranged.baseType.choice"));
        controller.subscribe();
        return new EquipmentTypeChooserStage(stage, controller);
    }

    @Bean
    public EquipmentTypeChooserStage armorTypeChooser() {
        EquipmentTypeChooserController controller = equipmentTypeChooserController();
        controller.setMessageType(messages.getProperty("armor.baseType.allTypesList"));
        controller.setChoiceReady(messages.getProperty("armor.baseType.choice"));
        controller.subscribe();
        return new EquipmentTypeChooserStage(stage, controller);
    }

    @Bean
    @Named("hwDeterminantsCreatorController")
    public DeterminantCreatorController handWeaponDeterminantController() {
        return new DeterminantCreatorController(messages.getProperty("determinants.creator.show.hw"),
                messages.getProperty("determinants.creator.add.hw"));
    }

    @Bean
    @Named("rwDeterminantsCreatorController")
    public DeterminantCreatorController rangedWeaponDeterminantController() {
        return new DeterminantCreatorController(messages.getProperty("determinants.creator.show.rw"),
                messages.getProperty("determinants.creator.add.rw"));
    }

    @Bean
    @Named("arDeterminantsCreatorController")
    public DeterminantCreatorController armorDeterminantController() {
        return new DeterminantCreatorController(messages.getProperty("determinants.creator.show.ar"),
                messages.getProperty("determinants.creator.add.ar"));
    }

    @Bean
    @Named("handWeaponsDeterminantsCreatorStage")
    public DeterminantCreatorStage determinantCreatorStage() {
        return new DeterminantCreatorStage(handWeaponDeterminantController());
    }

    @Bean
    @Named("rangedWeaponsDeterminantsCreatorStage")
    public DeterminantCreatorStage rangedWeaponDeterminantCreatorStage() {
        return new DeterminantCreatorStage(rangedWeaponDeterminantController());
    }

    @Bean
    @Named("armorDeterminantsCreatorStage")
    public DeterminantCreatorStage armorDeterminantCreatorStage() {
        return new DeterminantCreatorStage(armorDeterminantController());
    }
}
