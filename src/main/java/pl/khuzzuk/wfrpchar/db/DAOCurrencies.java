package pl.khuzzuk.wfrpchar.db;

import org.hibernate.Session;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.db.annot.Currencies;
import pl.khuzzuk.wfrpchar.entities.Currency;

@Component
@Currencies
public class DAOCurrencies extends DAOEntityResolver<Currency, String> implements Stateful, DAOTransactional<Currency, String> {
    @Override
    public void init(Session session) {
        elements = session.createQuery("from Currency").list();
    }
}
