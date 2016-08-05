package pl.khuzzuk.wfrpchar.db;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.db.annot.Manager;
import pl.khuzzuk.wfrpchar.db.annot.Weapons;
import pl.khuzzuk.wfrpchar.messaging.Message;
import pl.khuzzuk.wfrpchar.messaging.Publishers;
import pl.khuzzuk.wfrpchar.messaging.Subscriber;
import pl.khuzzuk.wfrpchar.messaging.Subscribers;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

@NoArgsConstructor
@Subscribers
@Manager
@Component
public class DAOReactor {
    private DAO dao;
    @Inject
    @Publishers
    private DAOPublisher daoPublisher;
    @Inject
    @Subscribers
    @Weapons
    private Subscriber<Message> weaponsQuerySubscriber;

    void getAllWeapons() {
        daoPublisher.publish(dao.getAllWeapons());
    }
    @Inject
    public void setDao(@Manager DAO dao) {
        this.dao = dao;
    }

    @PostConstruct
    private void setReactors() {
        weaponsQuerySubscriber.setReactor(this::getAllWeapons);
    }
}
