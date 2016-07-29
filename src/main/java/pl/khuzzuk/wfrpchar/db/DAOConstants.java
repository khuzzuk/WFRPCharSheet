package pl.khuzzuk.wfrpchar.db;

import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.db.annot.Constants;
import pl.khuzzuk.wfrpchar.db.annot.Transaction;
import pl.khuzzuk.wfrpchar.entities.Character;
import pl.khuzzuk.wfrpchar.entities.items.Item;

import java.util.List;

@Component
@NoArgsConstructor
@Constants
public class DAOConstants {
    @Transaction
    List<Character> getCharacters(Session session) {
        return (List<Character>) session.createQuery("FROM Character").list();
    }
}
