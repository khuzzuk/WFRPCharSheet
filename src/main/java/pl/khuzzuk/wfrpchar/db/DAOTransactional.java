package pl.khuzzuk.wfrpchar.db;

import org.hibernate.Session;

import java.util.List;

public interface DAOTransactional<T, U> {
    List<T> getAllItems();

    T getItem(U criteria);

    boolean commit(T toCommit, Session session);

    void assureInitialization(Session session);
}
