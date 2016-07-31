package pl.khuzzuk.wfrpchar.db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.db.annot.Initializer;
import pl.khuzzuk.wfrpchar.db.annot.Manager;

import javax.inject.Inject;

@Component
@Manager
public class DAOManager {
    @Inject
    private SessionFactory factory;
    @Inject
    @Initializer
    private DBInitializer initializer;

    public Session openNewSession() {
        return factory.openSession();
    }

    public void resetDB() {
        initializer.resetDatabase();
    }
}
