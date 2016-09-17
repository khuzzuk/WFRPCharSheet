package pl.khuzzuk.wfrpchar.gui;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.entities.items.types.WhiteWeaponType;
import pl.khuzzuk.wfrpchar.messaging.subscribers.GuiContentSubscriber;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

@Component
@PropertySource("classpath:messages.properties")
public class HandWeaponTypeChooserController extends GuiContentSubscriber<Collection<WhiteWeaponType>> implements Controller {
    @Inject
    private GuiPublisher publisher;
    @FXML
    private ListView<String> items;
    @Setter
    private Stage parent;
    @Value("${weapons.hand.baseType.choice}")
    private String choiceReady;

    public HandWeaponTypeChooserController(@Value("${weapons.hand.baseType.allTypesList}") String msgType) {
        super(msgType);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeValidation();
    }

    @PostConstruct
    @Override
    public void subscribe() {
        setConsumer(c -> {
            EntitiesAdapter.sendToListView(items, c);
            parent.show();
        });
        super.subscribe();
    }
}
