package pl.khuzzuk.wfrpchar.gui;

import javafx.stage.Stage;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import pl.khuzzuk.messaging.Bus;
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
    @Inject
    private Bus bus;

    @Bean
    @MainWindowBean
    @Inject
    public MainWindow mainWindowConfiguration(ControllersFactoryDecorator decorator) {
        return new MainWindow(decorator, stage);
    }

    @Bean
    @Scope(value = "prototype")
    public <T extends pl.khuzzuk.wfrpchar.entities.Named<String>>
    LinkedEntityChooserController<T> equipmentTypeChooserController() {
        return new LinkedEntityChooserController<>(bus);
    }

    @Bean
    @Scope(value = "prototype")
    public <T extends pl.khuzzuk.wfrpchar.entities.Named<String>>
    LinkedStringChooserController<T> linkedStringChooserController() {
        return new LinkedStringChooserController<>(bus);
    }

    private <T extends pl.khuzzuk.wfrpchar.entities.Named<String>>
    LinkedEntityChooserController<T> getController(String subscription, String publishing) {
        LinkedEntityChooserController<T> controller = equipmentTypeChooserController();
        controller.setMsgType(subscription);
        controller.setChoiceReady(publishing);
        return controller;
    }

    private <T extends pl.khuzzuk.wfrpchar.entities.Named<String>>
    LinkedStringChooserController<T> linkedStringChooserController(
            String subscription, String publishing) {
        LinkedStringChooserController<T> controller = linkedStringChooserController();
        controller.setMsgType(subscription);
        controller.setChoiceReady(publishing);
        return controller;
    }

    @Bean
    public LinkedEntityChooserStage handWeaponTypeChooserStage() {
        return new LinkedEntityChooserStage(stage, linkedStringChooserController(
                messages.getProperty("weapons.hand.baseType.allTypesList"),
                messages.getProperty("weapons.hand.baseType.choice")));
    }

    @Bean
    public LinkedEntityChooserStage rangedWeaponTypeChooser() {
        return new LinkedEntityChooserStage(stage, linkedStringChooserController(
                messages.getProperty("weapons.ranged.baseType.allTypesList"),
                messages.getProperty("weapons.ranged.baseType.choice")));
    }

    @Bean
    public LinkedEntityChooserStage armorTypeChooser() {
        return new LinkedEntityChooserStage(stage, linkedStringChooserController(
                messages.getProperty("armor.baseType.allTypesList"),
                messages.getProperty("armor.baseType.choice")));
    }

    @Bean
    public LinkedEntityChooserStage professionClassSkillsChooser() {
        return new LinkedEntityChooserStage(stage, linkedStringChooserController(
                messages.getProperty("professions.class.skills.allTypesList"),
                messages.getProperty("professions.class.skills.choice")));
    }

    @Bean
    public LinkedEntityChooserStage professionsSkillsChooser() {
        return new LinkedEntityChooserStage(stage, linkedStringChooserController(
                messages.getProperty("professions.skills.allTypesList"),
                messages.getProperty("professions.skills.choice")));
    }

    @Bean
    public LinkedEntityChooserStage professionNextChooser() {
        return new LinkedEntityChooserStage(stage, linkedStringChooserController(
                messages.getProperty("professions.next.allTypesList"),
                messages.getProperty("professions.next.choice")));
    }

    @Bean
    public LinkedEntityChooserStage raceSkillsChooser() {
        return new LinkedEntityChooserStage(stage, linkedStringChooserController(
                messages.getProperty("race.skills.allTypesList"),
                messages.getProperty("race.skills.choice")));
    }

    @Bean
    public LinkedEntityChooserStage spellSchoolChooser() {
        return new LinkedEntityChooserStage(stage, linkedStringChooserController(
                messages.getProperty("magic.spells.school.allTypesList"),
                messages.getProperty("magic.spells.school.choice")));
    }

    @Bean
    public LinkedEntityChooserStage spellIngredientsChooser() {
        return new LinkedEntityChooserStage(stage, linkedStringChooserController(
                messages.getProperty("magic.spells.ingredients.allTypesList"),
                messages.getProperty("magic.spells.ingredients.choice")));
    }

    @Bean
    public LinkedEntityChooserStage playerProfessionChooser() {
        return new LinkedEntityChooserStage(stage, linkedStringChooserController(
                messages.getProperty("player.profession.allTypesList"),
                messages.getProperty("player.profession.choice")));
    }

    @Bean
    public LinkedEntityChooserStage playerWhiteWeaponsChooser() {
        return new LinkedEntityChooserStage(stage, getController(
                messages.getProperty("player.weapons.white.allTypesList"),
                messages.getProperty("player.weapons.white.choice")));
    }

    @Bean
    public LinkedEntityChooserStage playerRangedWeaponsChooser() {
        return new LinkedEntityChooserStage(stage, getController(
                messages.getProperty("player.weapons.ranged.allTypesList"),
                messages.getProperty("player.weapons.ranged.choice")));
    }

    @Bean
    public LinkedEntityChooserStage playerArmorsChooser() {
        return new LinkedEntityChooserStage(stage, getController(
                messages.getProperty("player.armors.allTypesList"),
                messages.getProperty("player.armors.choice")));
    }

    @Bean
    public LinkedEntityChooserStage playerItemsChooser() {
        return new LinkedEntityChooserStage(stage, getController(
                messages.getProperty("player.equipment.allTypesList"),
                messages.getProperty("player.equipment.choice")));
    }

    @Bean
    public LinkedEntityChooserStage playerSkillsChooser() {
        return new LinkedEntityChooserStage(stage, linkedStringChooserController(
                messages.getProperty("player.skills.allTypesList"),
                messages.getProperty("player.skills.choice")));
    }

    @Bean
    public LinkedEntityChooserStage playerSpellsChooser() {
        return new LinkedEntityChooserStage(stage, getController(
                messages.getProperty("player.spells.allTypesList"),
                messages.getProperty("player.spells.choice")));
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
