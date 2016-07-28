package pl.khuzzuk.wfrpchar.db;

import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.db.annot.Constants;
import pl.khuzzuk.wfrpchar.db.annot.Manager;
import pl.khuzzuk.wfrpchar.db.annot.Transaction;
import pl.khuzzuk.wfrpchar.entities.Character;
import pl.khuzzuk.wfrpchar.entities.items.Item;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

@Component
@NoArgsConstructor
@Constants
public class DAOConstants {
    @Inject
    @Manager
    private DAOManager manager;
    private Session session;

    @PostConstruct
    private void setupSession() {
        session = manager.openNewSession();
    }

    @Transaction
    public List<Character> getCharacters() {
        return (List<Character>) session.createQuery("FROM Character").list();
    }

    @Transaction
    public List<Item> getAllItems() {
        return (List<Item>) session.createQuery("FROM Item").list();
    }
}
