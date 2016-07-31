package pl.khuzzuk.wfrpchar.db;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.db.annot.CommitTransaction;
import pl.khuzzuk.wfrpchar.db.annot.Manager;

import javax.inject.Inject;

@Aspect
@Component
public class TransactionAspect {
    @Inject
    @Manager
    DAO dao;

    @Pointcut("execution(@pl.khuzzuk.wfrpchar.db.annot.CommitTransaction * *(..)) && args(session)")
    public void commitTransaction(Session session) {}

    @Before("commitTransaction(session)")
    public void openTransaction(Session session) {
        session.beginTransaction();
    }

    @After("commitTransaction(session) && @annotation(transaction)")
    public void closeTransaction(Session session, CommitTransaction transaction) {
        session.getTransaction().commit();
        if (transaction.close()) dao.closeSession(session);
    }

    //@Pointcut("execution(@pl.khuzzuk.wfrpchar.db.annot.QueryTransaction * *(..)) && this(stateful)")
    public void queryTransaction(Stateful stateful) {}

    //@Before("execution(@pl.khuzzuk.wfrpchar.db.annot.QueryTransaction * *(..))")
    @Before("execution(@pl.khuzzuk.wfrpchar.db.annot.QueryTransaction * *(..)) && this(stateful)")
    public void checkState(Stateful stateful) {
        if (stateful.requireInitialization()) {
            Session session = dao.getCurrentSession();
            session.beginTransaction();
            stateful.init(session);
            session.getTransaction().commit();
        }
    }
    @Before("execution(@pl.khuzzuk.wfrpchar.db.annot.QueryTransaction * pl.khuzzuk.wfrpchar.db.DAOEntityResolver.*(..))")
    public void checkAbstractState() {
        System.out.println("asd");
        /*if (entityResolver.requireInitialization()) {
            Session session = dao.getCurrentSession();
            session.beginTransaction();
            entityResolver.init(session);
            session.getTransaction().commit();
        }*/
    }
}
