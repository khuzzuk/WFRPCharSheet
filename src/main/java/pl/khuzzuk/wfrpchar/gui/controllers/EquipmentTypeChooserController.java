package pl.khuzzuk.wfrpchar.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import lombok.Setter;
import pl.khuzzuk.wfrpchar.entities.items.types.WhiteWeaponType;
import pl.khuzzuk.wfrpchar.gui.EntitiesAdapter;
import pl.khuzzuk.wfrpchar.gui.GuiPublisher;
import pl.khuzzuk.wfrpchar.messaging.subscribers.GuiContentSubscriber;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

public class EquipmentTypeChooserController extends GuiContentSubscriber<Collection<WhiteWeaponType>> implements Controller {
    @Inject
    private GuiPublisher publisher;
    @FXML
    private ListView<String> items;
    @Setter
    private Stage parent;
    @Setter
    private String choiceReady;

    public EquipmentTypeChooserController() {
        super("");
    }

    public EquipmentTypeChooserController(String msgType, String choiceMsg) {
        super(msgType);
        choiceReady = choiceMsg;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeValidation();
    }

    public void subscribe() {
        setConsumer(c -> {
            EntitiesAdapter.sendToListView(items, c);
            parent.show();
        });
        super.subscribe();
    }

    @FXML
    private void sendSelected() {
        String selected = items.getSelectionModel().getSelectedItem();
        if (selected != null) {
            publisher.publish(selected, choiceReady);
            parent.hide();
        }
    }

    @FXML
    private void cancel() {
        parent.hide();
    }
}
