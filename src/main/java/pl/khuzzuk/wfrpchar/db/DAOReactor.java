package pl.khuzzuk.wfrpchar.db;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.db.annot.*;
import pl.khuzzuk.wfrpchar.entities.items.WeaponParser;
import pl.khuzzuk.wfrpchar.entities.items.WhiteWeaponType;
import pl.khuzzuk.wfrpchar.messaging.*;
import pl.khuzzuk.wfrpchar.messaging.publishers.Publishers;
import pl.khuzzuk.wfrpchar.messaging.subscribers.ContentSubscriber;
import pl.khuzzuk.wfrpchar.messaging.subscribers.Subscriber;
import pl.khuzzuk.wfrpchar.messaging.subscribers.Subscribers;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Subscribers
@ReactorBean
@Component
public class DAOReactor {
    private DAO dao;
    @Inject
    private WeaponParser weaponParser;
    @Inject
    @Publishers
    private DAOPublisher daoPublisher;
    @Inject
    @Subscribers
    @WhiteWeapons
    @DaoBean
    private Subscriber<Message> whiteWeaponsQuerySubscriber;
    @Inject
    @Named("dbResetSubscriber")
    @DaoBean
    @Subscribers
    private Subscriber<Message> dbResetSubscriber;
    @Inject
    @Subscribers
    @WhiteWeapons
    @SelectiveQuery
    private ContentSubscriber<String> whiteWeaponSelectionSubscriber;
    @Inject
    @Subscribers
    @WhiteWeapons
    @Persist
    private ContentSubscriber<String> whiteWeaponSaveSubscriber;
    @Value("${whiteWeapons.query}")
    @NotNull
    private String whiteWeaponQuery;

    private void getAllWeapons() {
        daoPublisher.publish(dao.getAllWeapons());
    }

    private void getWhiteWeaponByName(String name) {
        daoPublisher.publish(dao.getWhiteWeapon(name));
    }

    private void saveWhiteWeapon(String line) {
        WhiteWeaponType weaponType = (WhiteWeaponType) weaponParser.parseEquipment(line.split(";"));
        dao.save(weaponType);
        daoPublisher.publish(whiteWeaponQuery);
    }
    private void resetDB() {
        dao.getManager().resetDB(dao);
        daoPublisher.publish(whiteWeaponQuery);
    }

    @Inject
    public void setDao(@Manager DAO dao) {
        this.dao = dao;
    }

    @PostConstruct
    private void setReactors() {
        whiteWeaponsQuerySubscriber.setReactor(this::getAllWeapons);
        whiteWeaponSelectionSubscriber.setConsumer(this::getWhiteWeaponByName);
        whiteWeaponSaveSubscriber.setConsumer(this::saveWhiteWeapon);
        dbResetSubscriber.setReactor(this::resetDB);
    }
}
