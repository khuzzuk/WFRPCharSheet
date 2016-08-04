package pl.khuzzuk.wfrpchar.db;

import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;
import pl.khuzzuk.wfrpchar.db.annot.Manager;
import pl.khuzzuk.wfrpchar.messaging.Publishers;
import pl.khuzzuk.wfrpchar.messaging.Subscribers;

import javax.inject.Inject;

@Configuration
@NoArgsConstructor
@Subscribers
@Manager
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
