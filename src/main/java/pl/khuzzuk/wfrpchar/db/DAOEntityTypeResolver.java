package pl.khuzzuk.wfrpchar.db;

import org.hibernate.Session;
import pl.khuzzuk.wfrpchar.entities.Named;
import pl.khuzzuk.wfrpchar.entities.Persistable;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DAOEntityTypeResolver<T extends Named<U> & Persistable, U extends Comparable<? super U>>
        implements DAOTransactional<T, U> {
    private Class<T> query;
    private DAOManager manager;
    private Lock lock = new ReentrantLock();

    DAOEntityTypeResolver(Class<T> query, DAOManager manager) {
        this.query = query;
        this.manager = manager;
    }

    @Override
    @SuppressWarnings("unchecked")
    public synchronized T getItem(U criteria) {
        if (isInvalid(criteria)) {
            return null;
        }
        Session session = manager.openNewSession();
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = builder.createQuery(query);
        Root<T> from = criteriaQuery.from(query);
        criteriaQuery.select(from).where(builder.equal(from.get("name"), criteria));
        session.beginTransaction();
        T t = session.createQuery(criteriaQuery).getResultList().stream().findAny().orElse(null);
        session.getTransaction().commit();
        return t;
    }

    @Override
    public synchronized Collection<T> getAllItems() {
        Session session = manager.openNewSession();
        CriteriaQuery<T> criteria = manager.getCriteriaBuilder().createQuery(query);
        criteria.from(query);
        session.beginTransaction();
        List<T> items = session.createQuery(criteria).list();
        session.getTransaction().commit();
        return items;
    }

    @Override
    public synchronized boolean commit(T toCommit) {
        Session session = manager.openNewSession();
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(this.query);
        Root<T> from = criteria.from(this.query);
        criteria.select(from).where(builder.equal(from.get("name"), toCommit.getName()));
        session.beginTransaction();
        List<T> elements = session.createQuery(criteria).getResultList();
        T other = elements.stream().findAny().orElse(null);
        if (other == null) {
            session.save(toCommit);
        } else {
            toCommit.setId(other.getId());
            session.detach(other);
            session.saveOrUpdate(toCommit);
        }
        closeTransaction(session);
        return other == null;
    }

    @Override
    public synchronized boolean remove(U toRemove) {
        if (isInvalid(toRemove)) {
            throw new IllegalArgumentException(toRemove.toString());
        }
        Session session = manager.openNewSession();
        session.beginTransaction();
        session.remove(toRemove);
        closeTransaction(session);
        return true;
    }

    private void closeTransaction(Session session) {
        session.getTransaction().commit();
        session.close();
    }

    private boolean isInvalid(U argument) {
        return argument == null || argument.equals("");
    }
}
