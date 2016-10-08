package pl.khuzzuk.wfrpchar.db;

import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.db.annot.Initializer;
import pl.khuzzuk.wfrpchar.entities.Character;
import pl.khuzzuk.wfrpchar.entities.Currency;
import pl.khuzzuk.wfrpchar.entities.Persistable;
import pl.khuzzuk.wfrpchar.entities.competency.Profession;
import pl.khuzzuk.wfrpchar.entities.competency.Skill;
import pl.khuzzuk.wfrpchar.entities.items.ParserBag;
import pl.khuzzuk.wfrpchar.entities.items.ResourceType;
import pl.khuzzuk.wfrpchar.entities.items.WeaponParser;
import pl.khuzzuk.wfrpchar.entities.items.types.*;
import pl.khuzzuk.wfrpchar.entities.items.usable.AbstractHandWeapon;
import pl.khuzzuk.wfrpchar.entities.items.usable.Ammunition;
import pl.khuzzuk.wfrpchar.entities.items.usable.Armor;
import pl.khuzzuk.wfrpchar.entities.items.usable.Gun;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Initializer
public class DBInitializer {
    private static final String DISCLAIMER = "spec:";
    private WeaponParser weaponParser;
    private DAO dao;

    @Inject
    public DBInitializer(WeaponParser weaponParser) {
        this.weaponParser = weaponParser;
    }

    void resetDatabase(DAO dao) {
        this.dao = dao;
        loadCurrencies();
        loadCharacters();
        loadItems("/items.csv");
        loadItems("/whiteWeaponTypes.csv");
        loadItems("/rangedWeaponTypes.csv");
        loadItems("/armorType.csv");
        parseFileToItems("/ammoTypes.csv")
                .forEach(a -> dao.saveEntity(AmmunitionType.class, (AmmunitionType) a));
        loadResources();
        loadHandWeapons();
        loadGuns();
        loadArmors();
        loadAmmunitions();
        loadSkills();
        loadProfessions();
    }

    private void loadCurrencies() {
        List<Currency> currencies = readResource("/currencies.csv").stream()
                .map(values -> new Currency(values[0], values[1], values[2], values[3], Float.parseFloat(values[4])))
                .collect(Collectors.toList());
        currencies.forEach(dao::save);
    }

    private void loadCharacters() {
        List<Character> characters = readResource("/characters.csv").stream()
                .map(s -> new Character(s[0])).collect(Collectors.toList());
        characters.forEach(dao::save);
    }

    private void loadResources() {
        List<ResourceType> resources = readResource("/resources.csv")
                .stream().map(ResourceType::getFromCsv).collect(Collectors.toList());
        resources.forEach(dao::save);
    }

    private void loadHandWeapons() {
        List<String[]> lines =
                readResource("/handWeapons.csv").stream()
                        .collect(Collectors.toList());
        for (String[] s : lines) {
            ParserBag<WhiteWeaponType> bag = new ParserBag<>(
                    dao.getWhiteWeapon(s[4]),
                    dao.getResourceType(s[5]),
                    dao.getResourceType(s[6]));
            dao.saveEntity(AbstractHandWeapon.class, weaponParser.parseHandWeapon(s, bag));
        }
    }

    private void loadGuns() {
        List<String[]> list = readResource("/rangedWeapons.csv")
                .stream().collect(Collectors.toList());
        for (String[] s : list) {
            ParserBag<RangedWeaponType> bag = new ParserBag<>(
                    dao.getEntity(RangedWeaponType.class, s[4]),
                    dao.getEntity(ResourceType.class, s[5]),
                    dao.getEntity(ResourceType.class, s[5]));
            dao.saveEntity(Gun.class, weaponParser.parseGun(s, bag));
        }
    }

    private void loadArmors() {
        List<String[]> list = readResource("/armor.csv")
                .stream().filter(s -> !s[0].startsWith("spec")).collect(Collectors.toList());
        for (String[] s : list) {
            ParserBag<ArmorType> bag = new ParserBag<>(
                    dao.getEntity(ArmorType.class, s[4]),
                    dao.getEntity(ResourceType.class, s[5]),
                    dao.getEntity(ResourceType.class, s[5]));
            dao.saveEntity(Armor.class, weaponParser.parseArmor(s, bag));
        }
    }

    private void loadAmmunitions() {
        List<String[]> list = readResource("/ammo.csv")
                .stream().collect(Collectors.toList());
        for (String[] s : list) {
            ParserBag<AmmunitionType> bag = new ParserBag<>(
                    dao.getEntity(AmmunitionType.class, s[4]),
                    dao.getEntity(ResourceType.class, s[5]),
                    dao.getEntity(ResourceType.class, s[5]));
            dao.saveEntity(Ammunition.class, weaponParser.parseAmmunition(s, bag));
        }
    }

    private void loadSkills() {
        readResource("/skills.csv").stream()
        .map(Skill::fromCsv).forEach(this::saveEntity);
    }

    private void loadProfessions() {
        Map<Profession, String[]> map = readResource("/professions.csv").stream()
                .collect(Collectors.toMap(l -> Profession.build(l, null, null), l -> l));
        map.keySet().forEach(p -> dao.saveEntity(Profession.class, p));
        map.forEach((p, l) -> Profession.update(p, l, dao));
        map.keySet().forEach(p -> dao.saveEntity(Profession.class, p));
    }

    private void loadItems(String path) {
        List<Item> equipments = parseFileToItems(path);
        equipments.forEach(dao::save);
    }

    private List<Item> parseFileToItems(String path) {
        return readResource(path).stream()
                    .map(weaponParser::parseEquipment).collect(Collectors.toList());
    }

    private List<String[]> readResource(String location) {
        try {
            Path resource = Paths.get(getClass().getResource(location).toURI());
            return Files.readAllLines(resource).stream().filter(this::linesFilter).map(s -> s.split(";")).collect(Collectors.toList());
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    private boolean linesFilter(String line) {
        return !line.startsWith(DISCLAIMER);
    }

    @SuppressWarnings("unchecked")
    private <T extends Persistable> void saveEntity(T entity) {
        dao.saveEntity((Class<T>) entity.getClass(), entity);
    }
}
