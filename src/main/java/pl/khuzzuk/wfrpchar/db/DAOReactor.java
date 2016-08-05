package pl.khuzzuk.wfrpchar.db;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.db.annot.Manager;
import pl.khuzzuk.wfrpchar.messaging.Publishers;
import pl.khuzzuk.wfrpchar.messaging.Subscribers;

import javax.inject.Inject;

@NoArgsConstructor
@Subscribers
@Manager
@Component
public class DAOReactor {
    @Inject
    @Manager
    private DAO dao;
    @Inject
    @Publishers
    private DAOPublisher daoPublisher;

    void getAllWeapons() {
        daoPublisher.publish(dao.getAllWeapons());
    }
}
