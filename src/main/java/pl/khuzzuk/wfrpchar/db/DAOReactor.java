package pl.khuzzuk.wfrpchar.db;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.db.annot.DaoBean;
import pl.khuzzuk.wfrpchar.db.annot.Manager;
import pl.khuzzuk.wfrpchar.db.annot.SelectiveQuery;
import pl.khuzzuk.wfrpchar.db.annot.WhiteWeapons;
import pl.khuzzuk.wfrpchar.messaging.*;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

@NoArgsConstructor
@Subscribers
@ReactorBean
@Component
public class DAOReactor {
    private DAO dao;
    @Inject
    @Publishers
    private DAOPublisher daoPublisher;
    @Inject
    @Subscribers
    @WhiteWeapons
    @DaoBean
    private Subscriber<Message> whiteWeaponsQuerySubscriber;
    @Inject
    @Subscribers
    @WhiteWeapons
    @SelectiveQuery
    private ContentSubscriber<String> whiteWeaponSelectionSubscriber;

    private void getAllWeapons() {
        daoPublisher.publish(dao.getAllWeapons());
    }

    void getWhiteWeaponByName(String name) {
        daoPublisher.publish(dao.getWhiteWeapon(name));
    }

    @Inject
    public void setDao(@Manager DAO dao) {
        this.dao = dao;
    }

    @PostConstruct
    private void setReactors() {
        whiteWeaponsQuerySubscriber.setReactor(this::getAllWeapons);
        whiteWeaponSelectionSubscriber.setConsumer(this::getWhiteWeaponByName);
    }
}
