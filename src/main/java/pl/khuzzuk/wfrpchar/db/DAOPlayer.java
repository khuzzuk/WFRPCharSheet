package pl.khuzzuk.wfrpchar.db;

import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.entities.Player;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

@Component
@Named("daoPlayer")
public class DAOPlayer {
    @Inject
    private DAOManager daoManager;
    @Inject
    private EntityManager entityManager;

    private void commitPlayer(final Player player) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(player);
        transaction.commit();
    }

    public void commit(Player player) {
        commitPlayer(player);
    }
}
