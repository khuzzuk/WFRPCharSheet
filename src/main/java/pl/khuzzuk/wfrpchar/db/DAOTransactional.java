package pl.khuzzuk.wfrpchar.db;

import org.hibernate.Session;

import java.util.List;

public interface DAOTransactional<T, U> {
    List<T> getAllItems();

    T getItem(U criteria);

    boolean commit(T toCommit);

    boolean requireInitialization();

    void assureInitialization(Session session);

    void closeSession();
}
