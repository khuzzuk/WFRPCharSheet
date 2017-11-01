package pl.khuzzuk.wfrpchar.repo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.khuzzuk.messaging.Bus;
import pl.khuzzuk.wfrpchar.db.DAO;
import pl.khuzzuk.wfrpchar.entities.Character;
import pl.khuzzuk.wfrpchar.entities.Currency;
import pl.khuzzuk.wfrpchar.entities.Race;
import pl.khuzzuk.wfrpchar.entities.characters.Player;
import pl.khuzzuk.wfrpchar.entities.competency.*;
import pl.khuzzuk.wfrpchar.entities.items.Commodity;
import pl.khuzzuk.wfrpchar.entities.items.types.Item;
import pl.khuzzuk.wfrpchar.entities.items.usable.Ammunition;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Component
public class RepoDumper {
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private Bus bus;
    @Autowired
    @Named("messages")
    private Properties messages;
    @Autowired
    private DAO dao;

    @PostConstruct
    private void init() {
        bus.setReaction(messages.getProperty("database.save"), this::save);
    }

    private void save() {
        Repo repo = new Repo();

        repo.setTypes(new ArrayList<>(dao.getAllEntities(Item.class)));

        List<Commodity> items = new ArrayList<>();
        items.addAll(dao.getAllEntities(Ammunition.class));
        //items.addAll(dao.getAllEntities(Armor.class));
        //items.addAll(dao.getAllEntities(AbstractHandWeapon.class));
        //items.addAll(dao.getAllEntities(Gun.class));
        repo.setItems(items);

        repo.setMagicSchools(new ArrayList<>(dao.getAllEntities(MagicSchool.class)));
        repo.setSpells(new ArrayList<>(dao.getAllEntities(Spell.class)));
        repo.setSkills(new ArrayList<>(dao.getAllEntities(Skill.class)));
        repo.setProfessionClasses(new ArrayList<>(dao.getAllEntities(ProfessionClass.class)));
        repo.setProfessions(new ArrayList<>(dao.getAllEntities(Profession.class)));
        repo.setCharacters(new ArrayList<>(dao.getAllEntities(Character.class)));
        repo.setCurrencies(new ArrayList<>(dao.getAllEntities(Currency.class)));
        repo.setRaces(new ArrayList<>(dao.getAllEntities(Race.class)));
        repo.setPlayers(new ArrayList<>(dao.getAllEntities(Player.class)));

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("repo.json"))) {
            String content = mapper.writeValueAsString(repo);
            content = content.replaceAll("org.hibernate.collection.internal.PersistentSet", "java.util.HashSet");
            content = content.replaceAll("org.hibernate.collection.internal.PersistentMap", "java.util.HashMap");
            content = content.replaceAll("org.hibernate.collection.internal.PersistentBag", "java.util.ArrayList");
            Repo repo2 = mapper.readValue(content, Repo.class);
            writer.write(content);
        } catch (IOException e) {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Błąd");
//            alert.setHeaderText("Nie udało się zapisać do pliku z bazą");
//            alert.showAndWait();
            e.printStackTrace();
        }
    }
}
