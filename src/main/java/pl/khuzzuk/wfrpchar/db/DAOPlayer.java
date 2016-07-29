package pl.khuzzuk.wfrpchar.db;

import org.hibernate.Session;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.db.annot.Transaction;
import pl.khuzzuk.wfrpchar.entities.Player;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Component
@Named("daoPlayer")
public class DAOPlayer {
    @Inject
    private DAOManager daoManager;
    private Session session;

    @PostConstruct
    private void initSession() {
        session = daoManager.openNewSession();
    }

    @Transaction
    public void commit(Player player) {
        session.save(player);
    }

    public List read(String name) {
        session.beginTransaction();
        List result = session.createQuery("from Player").list();
        session.getTransaction().commit();
        return result;
    }
}
