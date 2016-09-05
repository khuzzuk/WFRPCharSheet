package pl.khuzzuk.wfrpchar.db;

import org.hibernate.Session;
import pl.khuzzuk.wfrpchar.entities.Nameable;
import pl.khuzzuk.wfrpchar.entities.Persistable;

import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DAOEntityResolver<T extends Nameable<U> & Persistable, U extends Comparable<U>>
        implements Stateful, DAOTransactional<T, U> {
    @NotNull
    private Map<U, T> elements;
    @NotNull
    private String query;
    private Session session;
    private Lock lock = new ReentrantLock();

    DAOEntityResolver(String query, Session session) {
        this.query = query;
        this.session = session;
    }

    @Override
    public T getItem(U criteria) {
        return elements.get(criteria);
    }

    @Override
    public Collection<T> getAllItems() {
        return elements.values();
    }

    @Override
    public boolean commit(T toCommit) {
        lock.lock();
        if (session == null || !session.isOpen()) {
            throw new IllegalStateException("No session started for committing " + toCommit);
        }
        session.beginTransaction();
        T other = elements.get(toCommit.getName());
        if (other == null) {
            elements.put(toCommit.getName(), toCommit);
            session.save(toCommit);
        } else {
            toCommit.setId(other.getId());
            session.detach(other);
            elements.remove(other.getName());
            elements.put(toCommit.getName(), toCommit);
            session.saveOrUpdate(toCommit);
        }
        closeTransaction();
        lock.unlock();
        return other == null;
    }

    @Override
    public boolean remove(U toRemove) {
        lock.lock();
        if (session == null || !session.isOpen()) {
            throw new IllegalStateException("No session started for removing " + toRemove);
        }
        Optional<T> element = Optional.of(elements.get(toRemove));
        if (!element.isPresent()) {
            return false;
        }
        elements.remove(toRemove);
        session.beginTransaction();
        session.remove(element.get());
        closeTransaction();
        lock.unlock();
        return true;
    }

    @Override
    public void assureInitialization(Session session) {
        lock.lock();
        if (this.session == session) {
            throw new IllegalStateException("Cannot init with same session");
        }
        if (this.session != null) {
            this.session.close();
        }
        this.session = session;
        init(session);
        lock.unlock();
    }

    @Override
    public boolean requireInitialization() {
        return elements==null || session == null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void init(Session session) {
        session.beginTransaction();
        elements = new TreeMap<>();
        Collection<T> result = session.createQuery(query).list();
        result.forEach(e -> elements.put(e.getName(), e));
        closeTransaction();
    }

    @Override
    public void closeSession() {
        session.close();
        session = null;
    }

    private void closeTransaction() {
        session.getTransaction().commit();
    }
}
