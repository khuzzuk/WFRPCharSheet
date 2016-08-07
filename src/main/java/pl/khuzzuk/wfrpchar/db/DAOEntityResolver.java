package pl.khuzzuk.wfrpchar.db;

import org.hibernate.Session;
import pl.khuzzuk.wfrpchar.entities.Nameable;
import pl.khuzzuk.wfrpchar.entities.Persistable;

import javax.validation.constraints.NotNull;
import java.util.List;

public class DAOEntityResolver<T extends Nameable & Persistable, U> implements Stateful, DAOTransactional<T, U> {
    @NotNull
    private List<T> elements;
    @NotNull
    private String query;

    public DAOEntityResolver(String query) {
        this.query = query;
    }

    private T hasElement(T t) {
        return elements.stream().filter(e -> e.equals(t)).findFirst().orElse(null);
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
        T other = hasElement(toCommit);
        if (other == null) {
            session.save(toCommit);
        } else {
            toCommit.setId(other.getId());
            session.detach(other);
            elements.remove(other);
            elements.add(toCommit);
            session.saveOrUpdate(toCommit);
        }
        session.getTransaction().commit();
        return other == null;
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
