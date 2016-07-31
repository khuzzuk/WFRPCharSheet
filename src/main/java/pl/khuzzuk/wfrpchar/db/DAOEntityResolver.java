package pl.khuzzuk.wfrpchar.db;

import org.hibernate.Session;
import pl.khuzzuk.wfrpchar.db.annot.CommitTransaction;
import pl.khuzzuk.wfrpchar.db.annot.QueryTransaction;
import pl.khuzzuk.wfrpchar.entities.Nameable;

import javax.validation.constraints.NotNull;
import java.util.List;

public abstract class DAOEntityResolver<T extends Nameable, U> implements Stateful, DAOTransactional<T, U> {
    @NotNull
    List<T> elements;

    boolean hasElement(T t) {
        return elements.stream().filter(e -> e.equals(t)).findFirst().isPresent();
    }

    @Override
    @QueryTransaction
    public T getItem(U criteria) {
        return elements.stream().filter(c -> c.getName().equals(criteria)).findAny().orElse(null);
    }

    @Override
    @QueryTransaction
    public List<T> getAllItems() {
        return elements;
    }

    @Override
    @CommitTransaction
    public boolean commit(T toCommit, Session session) {
        boolean query = hasElement(toCommit);
        if (!query) session.save(toCommit);
        return !query;
    }

    @Override
    public boolean requireInitialization() {
        return elements==null;
    }
}
