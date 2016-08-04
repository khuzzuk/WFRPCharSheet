package pl.khuzzuk.wfrpchar.gui;

import javafx.stage.Stage;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import pl.khuzzuk.wfrpchar.db.DAOConfig;
import pl.khuzzuk.wfrpchar.entities.items.WeaponType;
import pl.khuzzuk.wfrpchar.messaging.*;

import javax.inject.Inject;
import java.util.List;

@Configuration
@Lazy
public class ScreensConfig {
    @Setter
    private Stage stage;
    @Inject
    @MainWindowBean
    private MainWindowController controller;

    @Bean
    @MainWindowBean
    public MainWindow mainWindowConfiguration() {
        return new MainWindow(controller, stage);
    }

    @Bean
    @Publishers
    @MainWindowBean
    public Publisher<Message> mainWindowPublisher() {
        return new CommunicatePublisher();
    }

    @Bean
    @Subscribers
    @MainWindowBean
    public ContentSubscriber<List<WeaponType>> weaponTypeSubscriber() {
        ContentSubscriber<List<WeaponType>> subscriber = new BagSubscriber<>();
        subscriber.setMessageType(DAOConfig.WEAPONS_RESULT);
        subscriber.setConsumer(controller::loadWeapon);
        return subscriber;
    }
}
