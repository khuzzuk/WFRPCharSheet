package pl.khuzzuk.wfrpchar.db;

import org.hibernate.Session;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.db.annot.*;
import pl.khuzzuk.wfrpchar.entities.Character;
import pl.khuzzuk.wfrpchar.entities.Player;
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
    private DAOTransactional<Item, String> daoItems;
    @Inject
    @Weapons
    private DAOTransactional<WeaponType, String> daoWeapons;
    @Inject
    @Constants
    @Characters
    private DAOTransactional<Character, String> daoCharacters;
    @Inject
    @Players
    private DAOTransactional<Player, String> daoPlayer;
    @Inject
    @Manager
    private DAOManager manager;
    private Session session;

    @PostConstruct
    private void setupSession() {
        session = manager.openNewSession();
    }

    public List<Item> getAllItems() {
        return daoItems.getAllItems();
    }

    public List<WeaponType> getAllWeapons() {
        return daoWeapons.getAllItems();
    }

    public List<Character> getAllCharacters() {
        return daoCharacters.getAllItems();
    }

    public boolean savePlayer(Player player) {
        return daoPlayer.commit(player, session);
    }

    Session getCurrentSession() {
        if (session==null) session = manager.openNewSession();
        return session;
    }

    void closeSession() {
        session.close();
        session = null;
    }

    void closeSession(Session session) {
        if (this.session == session) closeSession();
        else session.close();
    }
}
