package pl.khuzzuk.wfrpchar.db;

import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.db.annot.Constants;
import pl.khuzzuk.wfrpchar.entities.Character;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

@Component
@NoArgsConstructor
@Constants
public class DAOConstants {
    @Inject
    private DAOManager daoManager;
    private Session session;
    @PostConstruct
    private void setupSession(){
        session = daoManager.openNewSession();
    }


    public List<Character> getCharacters() {
        session.beginTransaction();
        List<Character> results = (List<Character>) session.createQuery("FROM Character").list();
        session.getTransaction().commit();
        return results;
    }
}
