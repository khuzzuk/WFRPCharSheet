package pl.khuzzuk.wfrpchar.db;

import lombok.NoArgsConstructor;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.db.annot.Initializer;
import pl.khuzzuk.wfrpchar.db.annot.Transaction;
import pl.khuzzuk.wfrpchar.entities.Character;
import pl.khuzzuk.wfrpchar.entities.Currency;
import pl.khuzzuk.wfrpchar.entities.Price;
import pl.khuzzuk.wfrpchar.entities.items.Item;
import pl.khuzzuk.wfrpchar.entities.items.MiscItem;
import pl.khuzzuk.wfrpchar.entities.items.WeaponParser;

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
    private static final String DISCLAIMER = "spec:";
    @Inject
    WeaponParser weaponParser;
    @Transaction(close = true)
    void resetDatabase(Session session) {
        loadCurrencies(session);
        loadCharacters(session);
        loadMiscItems(session);
        loadWeaponsTypes(session);
    }

    private void loadCurrencies(Session session) {
        save(readResource("/currencies.csv").stream().map(values ->
                new Currency(values[0], values[1], values[2], values[3], Float.parseFloat(values[4])))
                .collect(Collectors.toList()), session);
    }

    private void loadCharacters(Session session) {
        save(readResource("/characters.csv")
                .stream()
                .map(s -> new Character(s[0]))
                .collect(Collectors.toList()), session);
    }

    private void loadMiscItems(Session session) {
        save(readResource("/items.csv")
                .stream()
                .filter(s -> !s[0].startsWith(DISCLAIMER))
                .map(s -> new MiscItem(s[0], Float.parseFloat(s[1]),
                        Price.parsePrice(s[2]), Item.Accessibility.forName(s[3])))
                .collect(Collectors.toList()), session);
    }

    private void loadWeaponsTypes(Session session) {
        save(readResource("/whiteWeaponTypes.csv")
                .stream()
                .filter(s -> !s[0].startsWith(DISCLAIMER))
                .map(weaponParser::parseEquipment)
                .collect(Collectors.toList()), session);
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
