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
    private Session session;

    public DAOEntityResolver(String query, Session session) {
        this.query = query;
        this.session = session;
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
    public boolean commit(T toCommit) {
        if (session == null || !session.isOpen()) {
            throw new IllegalStateException("No session started for committing " + toCommit);
        }
        session.beginTransaction();
        T other = hasElement(toCommit);
        if (other == null) {
            elements.add(toCommit);
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
        if (this.session != null) {
            this.session.close();
        }
        this.session = session;
        init(session);
    }

    @Override
    public boolean requireInitialization() {
        return elements==null || session == null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void init(Session session) {
        session.beginTransaction();
        elements = session.createQuery(query).list();
        session.getTransaction().commit();
    }

    @Override
    public void closeSession() {
        session.close();
        session = null;
    }
}
