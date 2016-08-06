package pl.khuzzuk.wfrpchar.gui;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.db.annot.SelectiveQuery;
import pl.khuzzuk.wfrpchar.messaging.*;

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
    private BagPublisher<String> queryRequestPublisher;
    @Value("${whiteWeapons.query}")
    @NotNull
    @Setter
    private String whiteWeaponQuery;
    @Value("${database.reset}")
    @NotNull
    @Setter
    private String databaseReset;
    @Value("${whiteWeapons.query.specific}")
    @NotNull
    @Setter
    private String whiteWeaponGet;

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
        queryRequestPublisher.publish(name, whiteWeaponGet);
    }
}
