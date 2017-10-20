package pl.khuzzuk.wfrpchar.gui;

import javafx.scene.control.Alert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.khuzzuk.messaging.Bus;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import java.util.Properties;

@Component
public class AlertReporter {
    @Autowired
    private Bus bus;
    @Autowired
    @Named("messages")
    private Properties messages;

    @PostConstruct
    private void init() {
        bus.setGuiReaction(messages.getProperty("prog.show.alert"), this::showAlert);
    }

    private void showAlert(String message) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd");
            alert.setHeaderText("Nie udało się zapisać do pliku z bazą");
            alert.showAndWait();
    }
}
