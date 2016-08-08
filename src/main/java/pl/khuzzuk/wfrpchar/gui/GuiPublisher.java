package pl.khuzzuk.wfrpchar.gui;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.db.annot.SelectiveQuery;
import pl.khuzzuk.wfrpchar.messaging.*;
import pl.khuzzuk.wfrpchar.messaging.publishers.BagPublisher;
import pl.khuzzuk.wfrpchar.messaging.publishers.Publisher;
import pl.khuzzuk.wfrpchar.messaging.publishers.Publishers;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

@Component
@Publishers
@PropertySource("classpath:messages.properties")
public class GuiPublisher {
    @Inject
    @Publishers
    @MainWindowBean
    private Publisher<Message> publisher;
    @Inject
    @Publishers
    @SelectiveQuery
    @MainWindowBean
    private BagPublisher<String> textRequestPublisher;
    @Value("${whiteWeapons.query}")
    @NotNull
    private String whiteWeaponQuery;
    @Value("${database.reset}")
    @NotNull
    private String databaseReset;
    @Value("${whiteWeapons.query.specific}")
    @NotNull
    private String whiteWeaponGet;
    @Value("${whiteWeapons.save}")
    @NotNull
    private String whiteWeaponSave;

    void requestWhiteWeapons() {
        Message message = new CommunicateMessage();
        message.setType(whiteWeaponQuery);
        publisher.publish(message);
    }

    void requestResetDB() {
        Message message = new CommunicateMessage();
        message.setType(databaseReset);
        publisher.publish(message);
    }

    void requestWhiteWeaponLoad(String name) {
        textRequestPublisher.publish(name, whiteWeaponGet);
    }

    void saveToDB(String line) {
        textRequestPublisher.publish(line, whiteWeaponSave);
    }
}
