package pl.khuzzuk.wfrpchar.db;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.db.annot.*;
import pl.khuzzuk.wfrpchar.entities.items.WeaponParser;
import pl.khuzzuk.wfrpchar.entities.items.WhiteWeaponType;
import pl.khuzzuk.wfrpchar.messaging.Message;
import pl.khuzzuk.wfrpchar.messaging.ReactorBean;
import pl.khuzzuk.wfrpchar.messaging.publishers.Publishers;
import pl.khuzzuk.wfrpchar.messaging.subscribers.*;

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
    @Inject
    @Subscribers
    @DaoBean
    private MultiContentSubscriber daoContentSubscriber;
    @Inject
    @Subscribers
    @DaoBean
    private MultiSubscriber<Message> multiSubscriber;
    @Value("${whiteWeapons.query}")
    @NotNull
    private String whiteWeaponQuery;
    @Value("${rangedWeapons.query}")
    private String rangedWeaponsQuery;
    @Value("${rangedWeapons.query.specific}")
    private String rangeWeaponNamedQuery;
    @Value("${database.reset}")
    private String resetDbMessage;

    private void getAllWeapons() {
        daoPublisher.publishWhiteWeapons(dao.getAllWeapons());
    }

    private void getWhiteWeaponByName(String name) {
        daoPublisher.publish(dao.getWhiteWeapon(name));
    }

    private void saveWhiteWeapon(String line) {
        WhiteWeaponType weaponType = (WhiteWeaponType) weaponParser.parseEquipment(line.split(";"));
        dao.save(weaponType);
        daoPublisher.publish(whiteWeaponQuery);
    }

    private void getAllRangedWeapons() {
        daoPublisher.publishRangedWeapons(dao.getAllRangedWeapons());
    }

    private void getRangedWeaponByName(String name) {
        daoPublisher.publish(dao.getRangedWeapon(name));
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
        multiSubscriber.subscribe(whiteWeaponQuery, this::getAllWeapons);
        multiSubscriber.subscribe(resetDbMessage, this::resetDB);
        multiSubscriber.subscribe(rangedWeaponsQuery, this::getAllRangedWeapons);
        whiteWeaponSelectionSubscriber.setConsumer(this::getWhiteWeaponByName);
        whiteWeaponSaveSubscriber.setConsumer(this::saveWhiteWeapon);
        daoContentSubscriber.subscribe(rangeWeaponNamedQuery, this::getRangedWeaponByName);
    }
}
