package pl.khuzzuk.wfrpchar.db;

import org.hibernate.Session;

public interface Stateful {
    boolean requireInitialization();
    void init(Session session);
}
