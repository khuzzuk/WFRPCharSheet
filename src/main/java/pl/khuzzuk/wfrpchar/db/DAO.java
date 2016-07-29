package pl.khuzzuk.wfrpchar.db;

import org.hibernate.Session;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.db.annot.Constants;
import pl.khuzzuk.wfrpchar.db.annot.Items;
import pl.khuzzuk.wfrpchar.db.annot.Manager;
import pl.khuzzuk.wfrpchar.entities.Character;
import pl.khuzzuk.wfrpchar.entities.items.Item;
import pl.khuzzuk.wfrpchar.entities.items.WeaponType;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

@Component
@Manager
public class DAO {
    @Inject
    @Items
    private DAOItems items;
    @Inject
    @Constants
    private DAOConstants daoConstants;
    @Inject
    @Manager
    private DAOManager manager;
    private Session session;

    @PostConstruct
    private void setupSession() {
        session = manager.openNewSession();
    }

    public List<Item> getAllItems() {
        return items.getAllItems(session);
    }

    public List<WeaponType> getAllWeapons() {
        return items.getAllWeapons(session);
    }

    public List<Character> getAllCharacters() {
        return daoConstants.getCharacters(session);
    }
}
