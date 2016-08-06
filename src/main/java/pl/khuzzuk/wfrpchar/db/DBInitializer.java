package pl.khuzzuk.wfrpchar.db;

import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.db.annot.Initializer;
import pl.khuzzuk.wfrpchar.entities.Character;
import pl.khuzzuk.wfrpchar.entities.Currency;
import pl.khuzzuk.wfrpchar.entities.Price;
import pl.khuzzuk.wfrpchar.entities.items.FightingEquipment;
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
@Initializer
public class DBInitializer {
    private static final String DISCLAIMER = "spec:";
    private WeaponParser weaponParser;

    @Inject
    public DBInitializer(WeaponParser weaponParser) {
        this.weaponParser = weaponParser;
    }

    void resetDatabase(DAO dao) {
        loadCurrencies(dao);
        loadCharacters(dao);
        loadMiscItems(dao);
        loadWeaponsTypes(dao);
    }

    private void loadCurrencies(DAO dao) {
        List<Currency> currencies = readResource("/currencies.csv").stream()
                .map(values -> new Currency(values[0], values[1], values[2], values[3], Float.parseFloat(values[4])))
                .collect(Collectors.toList());
        currencies.forEach(dao::save);
    }

    private void loadCharacters(DAO dao) {
        List<Character> characters = readResource("/characters.csv")
                .stream()
                .map(s -> new Character(s[0]))
                .collect(Collectors.toList());
        characters.forEach(dao::save);
    }

    private void loadMiscItems(DAO dao) {
        List<Item> miscItems = readResource("/items.csv")
                .stream()
                .filter(s -> !s[0].startsWith(DISCLAIMER))
                .map(s -> new MiscItem(s[0], Float.parseFloat(s[1]),
                        Price.parsePrice(s[2]), Item.Accessibility.valueOf(s[3])))
                .collect(Collectors.toList());
        miscItems.forEach(dao::save);
    }

    private void loadWeaponsTypes(DAO dao) {
        List<FightingEquipment> weapons = readResource("/whiteWeaponTypes.csv")
                .stream()
                .filter(s -> !s[0].startsWith(DISCLAIMER))
                .map(weaponParser::parseEquipment)
                .collect(Collectors.toList());
        weapons.forEach(dao::save);
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
}
