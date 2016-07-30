package pl.khuzzuk.wfrpchar.db;

import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.db.annot.Characters;
import pl.khuzzuk.wfrpchar.db.annot.Constants;
import pl.khuzzuk.wfrpchar.db.annot.CommitTransaction;
import pl.khuzzuk.wfrpchar.db.annot.QueryTransaction;
import pl.khuzzuk.wfrpchar.entities.Character;

import java.util.List;

@Component
@NoArgsConstructor
@Constants
@Characters
public class DAOConstants implements Stateful, DAOTransactional<Character, String> {
    private List<Character> characters;
    @Override
    public boolean requireInitialization() {
        return characters==null;
    }

    @Override
    public void init(Session session) {
        characters = (List<Character>) session.createQuery("FROM Character").list();
    }

    @Override
    @QueryTransaction
    public List<Character> getAllItems() {
        return characters;
    }

    @Override
    @QueryTransaction
    public Character getItem(String criteria) {
        return characters.stream().filter(i -> i.getName().equals(criteria)).findAny().orElse(null);
    }

    @Override
    @CommitTransaction
    public boolean commit(Character toCommit, Session session) {
        boolean query = characters.stream()
                .filter(i -> i.equals(toCommit))
                .findAny().isPresent();
        if (!query) session.save(toCommit);
        return !query;
    }
}
