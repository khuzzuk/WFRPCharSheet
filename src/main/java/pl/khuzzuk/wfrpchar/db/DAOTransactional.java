package pl.khuzzuk.wfrpchar.db;

import java.util.Collection;

public interface DAOTransactional<T, U> {
    Collection<T> getAllItems();

    T getItem(U criteria);

    boolean commit(T toCommit);

    boolean remove(U toRemove);
}
