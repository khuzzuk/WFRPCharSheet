package pl.khuzzuk.wfrpchar.db;

import lombok.NoArgsConstructor;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.db.annot.Initializer;
import pl.khuzzuk.wfrpchar.entities.Character;
import pl.khuzzuk.wfrpchar.entities.Currency;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@NoArgsConstructor
@Initializer
public class DBInitializer {
    @Inject
    private DAOManager daoManager;
    public void resetDatabase(){
        Session session = daoManager.openNewSession();
        session.beginTransaction();
        loadCurrencies(session);
        loadCharacters(session);
        session.getTransaction().commit();
        session.close();
    }

    private void loadCurrencies(Session session) {
            List<Currency> currencyList = readResource("/currencies.csv").stream().map(values ->
                    new Currency(values[0], values[1], values[2], values[3], Float.parseFloat(values[4])))
                    .collect(Collectors.toList());
            save(currencyList, session);
    }

    private void loadCharacters(Session session) {
        List<String[]> lines = readResource("/characters.csv");
        save(readResource("/characters.csv").stream().map(s -> new Character(s[0])).collect(Collectors.toList()), session);
    }

    private List<String[]> readResource(String location) {
        try {
            Path resource = Paths.get(getClass().getResource(location).toURI());
            return Files.readAllLines(resource).stream().map(s -> s.split(";")).collect(Collectors.toList());
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return Collections.EMPTY_LIST;
    }

    private <T> void save(List<T> list, Session session) {
        try {
            for (T t : list) {
                session.save(t);
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }
}
