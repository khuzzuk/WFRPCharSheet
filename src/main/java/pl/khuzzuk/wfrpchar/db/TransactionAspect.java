package pl.khuzzuk.wfrpchar.db;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.db.annot.Transaction;

@Aspect
@Component
public class TransactionAspect {
    @Before("execution(@pl.khuzzuk.wfrpchar.db.annot.Transaction * *(..)) && args(session)")
    public void openTransaction(Session session) {
        session.beginTransaction();
    }

    @After("execution(@pl.khuzzuk.wfrpchar.db.annot.Transaction * *(..)) && args(session) && @annotation(transaction)")
    public void closeTransaction(Session session, Transaction transaction) {
        session.getTransaction().commit();
        if (transaction.close()) session.close();
    }
}
