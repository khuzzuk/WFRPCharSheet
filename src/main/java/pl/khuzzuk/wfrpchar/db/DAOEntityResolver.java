package pl.khuzzuk.wfrpchar.db;

import org.hibernate.Session;
import pl.khuzzuk.wfrpchar.entities.Named;
import pl.khuzzuk.wfrpchar.entities.Persistable;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DAOEntityResolver<T extends Named<U> & Persistable, U extends Comparable<? super U>>
        implements Stateful, DAOTransactional<T, U> {
    private String query;
    private Session session;
    private Lock lock = new ReentrantLock();

    DAOEntityResolver(String query, Session session) {
        this.query = query;
        this.session = session;
    }

    @Override
    @SuppressWarnings("unchecked")
    public synchronized T getItem(U criteria) {
        if (isInvalid(criteria)) {
            return null;
        }
        session.beginTransaction();
        List<T> list = session.createQuery(query).list();
        session.getTransaction().commit();
        return list.stream().filter(n -> n.getName().equals(criteria)).findAny().orElse(null);
    }

    @Override
    @SuppressWarnings("unchecked")
    public synchronized Collection<T> getAllItems() {
        session.beginTransaction();
        List<T> items = session.createQuery(query).list();
        session.getTransaction().commit();
        return items;
    }

    @Override
    public synchronized boolean commit(T toCommit) {
        if (session == null || !session.isOpen()) {
            throw new IllegalStateException("No session started for committing " + toCommit);
        }
        session.beginTransaction();
        List<T> elements = session.createQuery(query).list();
        T other = elements.stream().filter(n -> n.getName().equals(toCommit.getName())).findAny().orElse(null);
        if (other == null) {
            session.save(toCommit);
        } else {
            toCommit.setId(other.getId());
            session.detach(other);
            session.saveOrUpdate(toCommit);
        }
        closeTransaction();
        return other == null;
    }

    @Override
    public boolean remove(U toRemove) {
        if (isInvalid(toRemove)) {
            throw new IllegalArgumentException(toRemove.toString());
        }
        lock.lock();
        if (session == null || !session.isOpen()) {
            throw new IllegalStateException("No session started for removing " + toRemove);
        }
        session.beginTransaction();
        session.remove(toRemove);
        closeTransaction();
        lock.unlock();
        return true;
    }

    public void assureInitialization(Session session) {
        lock.lock();
        if (this.session == session) {
            throw new IllegalStateException("Cannot init with same session");
        }
        if (this.session != null) {
            this.session.close();
        }
        this.session = session;
        lock.unlock();
    }

    @Override
    public boolean requireInitialization() {
        return session == null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void init(Session session) {
    }

    public synchronized void closeSession() {
        session.close();
        session = null;
    }

    private void closeTransaction() {
        session.getTransaction().commit();
    }

    private boolean isInvalid(U argument) {
        return argument == null || argument.equals("");
    }
}
