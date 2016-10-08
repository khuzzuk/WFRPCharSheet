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
    public LinkedEntityChooserController equipmentTypeChooserController() {
        return new LinkedEntityChooserController();
    }

    private LinkedEntityChooserController getController(String subscripsion, String publishing) {
        LinkedEntityChooserController controller = equipmentTypeChooserController();
        controller.setMessageType(subscripsion);
        controller.setChoiceReady(publishing);
        controller.subscribe();
        return controller;
    }

    @Bean
    public LinkedEntityChooserStage handWeaponTypeChooserStage() {
        return new LinkedEntityChooserStage(stage,getController(
                messages.getProperty("weapons.hand.baseType.allTypesList"),
                messages.getProperty("weapons.hand.baseType.choice")));
    }

    @Bean
    public LinkedEntityChooserStage rangedWeaponTypeChooser() {
        LinkedEntityChooserController controller = equipmentTypeChooserController();
        controller.setMessageType(messages.getProperty("weapons.ranged.baseType.allTypesList"));
        controller.setChoiceReady(messages.getProperty("weapons.ranged.baseType.choice"));
        controller.subscribe();
        return new LinkedEntityChooserStage(stage, controller);
    }

    @Bean
    public LinkedEntityChooserStage armorTypeChooser() {
        LinkedEntityChooserController controller = equipmentTypeChooserController();
        controller.setMessageType(messages.getProperty("armor.baseType.allTypesList"));
        controller.setChoiceReady(messages.getProperty("armor.baseType.choice"));
        controller.subscribe();
        return new LinkedEntityChooserStage(stage, controller);
    }

    @Bean
    public LinkedEntityChooserStage professionsSkillsChooser() {
        return new LinkedEntityChooserStage(stage, getController(
                messages.getProperty("professions.skills.allTypesList"),
                messages.getProperty("professions.skills.choice")));
    }

    @Bean
    public LinkedEntityChooserStage professionNextChooser() {
        return new LinkedEntityChooserStage(stage, getController(
                messages.getProperty("professions.next.allTypesList"),
                messages.getProperty("professions.next.choice")));
    }

    @Bean
    @Named("hwDeterminantsCreatorController")
    public DeterminantCreatorController handWeaponDeterminantController() {
        DeterminantCreatorController controller = new DeterminantCreatorController(messages.getProperty("determinants.creator.show.hw"),
                messages.getProperty("determinants.creator.add.hw"));
        DeterminantCreatorStage stage = determinantStage();
        stage.setController(controller);
        stage.init();
        return controller;
    }

    @Bean
    @Named("rwDeterminantsCreatorController")
    public DeterminantCreatorController rangedWeaponDeterminantController() {
        DeterminantCreatorController controller = new DeterminantCreatorController(messages.getProperty("determinants.creator.show.rw"),
                messages.getProperty("determinants.creator.add.rw"));
        DeterminantCreatorStage stage = determinantStage();
        stage.setController(controller);
        stage.init();
        return controller;
    }

    @Bean
    @Named("arDeterminantsCreatorController")
    public DeterminantCreatorController armorDeterminantController() {
        DeterminantCreatorController controller = new DeterminantCreatorController(messages.getProperty("determinants.creator.show.ar"),
                messages.getProperty("determinants.creator.add.ar"));
        DeterminantCreatorStage stage = determinantStage();
        stage.setController(controller);
        stage.init();
        return controller;
    }

    @Bean
    @Named("amDeterminantsCreatorController")
    public DeterminantCreatorController ammunitionDeterminantController() {
        DeterminantCreatorController controller = new DeterminantCreatorController(messages.getProperty("determinants.creator.show.am"),
                messages.getProperty("determinants.creator.add.am"));
        DeterminantCreatorStage stage = determinantStage();
        stage.setController(controller);
        stage.init();
        return controller;
    }

    @Bean
    @Named("skDeterminantsCreatorController")
    public DeterminantCreatorController skillDeterminantController() {
        DeterminantCreatorController controller = new DeterminantCreatorController(messages.getProperty("determinants.creator.show.sk"),
                messages.getProperty("determinants.creator.add.sk"));
        DeterminantCreatorStage stage = determinantStage();
        stage.setController(controller);
        stage.init();
        return controller;
    }

    @Bean
    @Named("prDeterminantsCreatorController")
    public DeterminantCreatorController professionDeterminantController() {
        DeterminantCreatorController controller = new DeterminantCreatorController(messages.getProperty("determinants.creator.show.pr"),
                messages.getProperty("determinants.creator.add.pr"));
        DeterminantCreatorStage stage = determinantStage();
        stage.setController(controller);
        stage.init();
        return controller;
    }

    @Bean
    @Scope(value = "prototype")
    public DeterminantCreatorStage determinantStage() {
        return new DeterminantCreatorStage();
    }

    @Named("handWeaponsDeterminantsCreatorStage")
    public DeterminantCreatorStage determinantCreatorStage() {
        return new DeterminantCreatorStage(handWeaponDeterminantController());
    }

    @Named("rangedWeaponsDeterminantsCreatorStage")
    public DeterminantCreatorStage rangedWeaponDeterminantCreatorStage() {
        return new DeterminantCreatorStage(rangedWeaponDeterminantController());
    }

    @Named("armorDeterminantsCreatorStage")
    public DeterminantCreatorStage armorDeterminantCreatorStage() {
        return new DeterminantCreatorStage(armorDeterminantController());
    }

    @Named("ammunitionDeterminantsCreatorStage")
    public DeterminantCreatorStage ammunitionDeterminantCreatorStage() {
        return new DeterminantCreatorStage(ammunitionDeterminantController());
    }
}
