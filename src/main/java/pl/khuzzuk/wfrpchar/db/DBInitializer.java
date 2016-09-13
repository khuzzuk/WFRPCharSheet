package pl.khuzzuk.wfrpchar.db;

import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.db.annot.Initializer;
import pl.khuzzuk.wfrpchar.entities.Character;
import pl.khuzzuk.wfrpchar.entities.Currency;
import pl.khuzzuk.wfrpchar.entities.items.ResourceType;
import pl.khuzzuk.wfrpchar.entities.items.types.Item;
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
        loadItems(dao, "/items.csv");
        loadItems(dao, "/whiteWeaponTypes.csv");
        loadItems(dao, "/rangedWeaponTypes.csv");
        loadItems(dao, "/armorType.csv");
        loadResources(dao);
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

    private void loadResources(DAO dao) {
        List<ResourceType> resources = readResource("/resources.csv")
                .stream().filter(s -> !s[0].startsWith("spec")).map(ResourceType::getFromCsv)
                .collect(Collectors.toList());
        resources.forEach(dao::save);
    }

    private void loadItems(DAO dao, String path) {
        List<Item> equipments = readResource(path)
                .stream().filter(s -> !s[0].startsWith(DISCLAIMER))
                .map(weaponParser::parseEquipment).collect(Collectors.toList());
        equipments.forEach(dao::save);
    }

    private List<String[]> readResource(String location) {
        try {
            Path resource = Paths.get(getClass().getResource(location).toURI());
            return Files.readAllLines(resource).stream().map(s -> s.split(";")).collect(Collectors.toList());
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
