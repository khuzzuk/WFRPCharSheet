package pl.khuzzuk.wfrpchar.db;

import javafx.scene.control.Alert;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.entities.Character;
import pl.khuzzuk.wfrpchar.entities.Currency;
import pl.khuzzuk.wfrpchar.entities.Documented;
import pl.khuzzuk.wfrpchar.entities.Race;
import pl.khuzzuk.wfrpchar.entities.characters.Player;
import pl.khuzzuk.wfrpchar.entities.competency.*;
import pl.khuzzuk.wfrpchar.entities.items.ResourceType;
import pl.khuzzuk.wfrpchar.entities.items.types.Item;
import pl.khuzzuk.wfrpchar.entities.items.usable.AbstractHandWeapon;
import pl.khuzzuk.wfrpchar.entities.items.usable.Ammunition;
import pl.khuzzuk.wfrpchar.entities.items.usable.Armor;
import pl.khuzzuk.wfrpchar.entities.items.usable.Gun;

import javax.inject.Inject;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class DBDumper {
    private DAO dao;

    @Inject
    public DBDumper(DAO dao) {
        this.dao = dao;
    }

    @SuppressWarnings("unchecked")
    void saveToFiles() {
        Collection<SaveItem> saveItems = new ArrayList<>();
        try {
            saveItems.add(new SaveItem(dao.getAllEntities(Ammunition.class),
                    new BufferedWriter(new FileWriter("ammo.csv"))));
            saveItems.add(new SaveItem(dao.getAllEntities(Armor.class),
                    new BufferedWriter(new FileWriter("armor.csv"))));
            saveItems.add(new SaveItem(dao.getAllEntities(Character.class),
                    new BufferedWriter(new FileWriter("characters.csv"))));
            saveItems.add(new SaveItem(dao.getAllEntities(Currency.class),
                    new BufferedWriter(new FileWriter("currencies.csv"))));
            saveItems.add(new SaveItem(dao.getAllEntities(AbstractHandWeapon.class),
                    new BufferedWriter(new FileWriter("handWeapons.csv"))));
            saveItems.add(new SaveItem(dao.getAllEntities(Item.class),
                    new BufferedWriter(new FileWriter("items.csv"))));
            saveItems.add(new SaveItem(dao.getAllEntities(MagicSchool.class),
                    new BufferedWriter(new FileWriter("magicSchools.csv"))));
            saveItems.add(new SaveItem(dao.getAllEntities(Player.class),
                    new BufferedWriter(new FileWriter("players.csv"))));
            saveItems.add(new SaveItem(dao.getAllEntities(ProfessionClass.class),
                    new BufferedWriter(new FileWriter("professionClass.csv"))));
            saveItems.add(new SaveItem(dao.getAllEntities(Profession.class),
                    new BufferedWriter(new FileWriter("professions.csv"))));
            saveItems.add(new SaveItem(dao.getAllEntities(Race.class),
                    new BufferedWriter(new FileWriter("races.csv"))));
            saveItems.add(new SaveItem(dao.getAllEntities(Gun.class),
                    new BufferedWriter(new FileWriter("rangedWeapons.csv"))));
            saveItems.add(new SaveItem(dao.getAllEntities(ResourceType.class),
                    new BufferedWriter(new FileWriter("resources.csv"))));
            saveItems.add(new SaveItem(dao.getAllEntities(Skill.class),
                    new BufferedWriter(new FileWriter("skills.csv"))));
            saveItems.add(new SaveItem(dao.getAllEntities(Spell.class),
                    new BufferedWriter(new FileWriter("spells.csv"))));

            saveItems.forEach(SaveItem::save);
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd");
            alert.setHeaderText("Nie udało się stworzyć pliku");
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    private static class SaveItem<T extends Documented> {
        private Collection<T> items;
        private BufferedWriter writer;

        private String getItems() {
            return items.stream().map(Documented::toCsv).filter(Objects::nonNull).collect(Collectors.joining("\n"));
        }

        private void save() {
            try {
                writer.write(getItems());
                writer.newLine();
                writer.flush();
                writer.close();
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Błąd");
                alert.setHeaderText("Nie udało się zapisać do pliku: " + writer.toString());
                alert.showAndWait();
                e.printStackTrace();
            }
        }
    }
}
