package pl.khuzzuk.wfrpchar.db;

import org.hibernate.Session;
import pl.khuzzuk.wfrpchar.entities.Nameable;

import javax.validation.constraints.NotNull;
import java.util.List;

public class DAOEntityResolver<T extends Nameable, U> implements Stateful, DAOTransactional<T, U> {
    @NotNull
    private List<T> elements;
    @NotNull
    private String query;

    public DAOEntityResolver(String query) {
        this.query = query;
    }

    private boolean hasElement(T t) {
        return elements.stream().filter(e -> e.equals(t)).findFirst().isPresent();
    }

    @Override
    public T getItem(U criteria) {
        return elements.stream().filter(c -> c.getName().equals(criteria)).findAny().orElse(null);
    }

    @Override
    public List<T> getAllItems() {
        return elements;
    }

    @Override
    public boolean commit(T toCommit, Session session) {
        session.beginTransaction();
        boolean query = hasElement(toCommit);
        if (!query) session.save(toCommit);
        session.getTransaction().commit();
        return !query;
    }

    @Override
    public void assureInitialization(Session session) {
        if (requireInitialization()) init(session);
    }

    @Override
    public boolean requireInitialization() {
        return elements==null;
    }

    @Override
    public void init(Session session) {
        session.beginTransaction();
        elements = session.createQuery(query).list();
        session.getTransaction().commit();
    }
}
