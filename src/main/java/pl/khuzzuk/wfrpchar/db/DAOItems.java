package pl.khuzzuk.wfrpchar.db;

import org.hibernate.Session;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.db.annot.CommitTransaction;
import pl.khuzzuk.wfrpchar.db.annot.Items;
import pl.khuzzuk.wfrpchar.db.annot.QueryTransaction;
import pl.khuzzuk.wfrpchar.entities.items.Item;
import pl.khuzzuk.wfrpchar.entities.items.WeaponType;
import pl.khuzzuk.wfrpchar.entities.items.WhiteWeaponType;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Items
public class DAOItems implements Stateful, DAOTransactional<Item, String> {
    private List<Item> items;

    @Override
    @QueryTransaction
    public Item getItem(String criteria) {
        return items.stream().filter(i -> i.getName().equals(criteria)).findAny().orElse(null);
    }

    @Override
    @CommitTransaction
    public boolean commit(Item toCommit, Session session) {
        boolean query = items.stream()
                .filter(i -> i.equals(toCommit))
                .findAny().isPresent();
        if (!query) session.save(toCommit);
        return !query;
    }

    @QueryTransaction
    @Override
    public List<Item> getAllItems() {
        return items;
    }
    @QueryTransaction
    List<WeaponType> getAllWeapons() {
        return items.stream()
                .filter(i -> i instanceof WeaponType)
                .map(i -> (WeaponType) i)
                .collect(Collectors.toList());
    }
    @QueryTransaction
    List<WhiteWeaponType> getAllWhiteWeapons() {
        return items.stream()
                .filter(i -> i instanceof WhiteWeaponType)
                .map(i -> (WhiteWeaponType) i)
                .collect(Collectors.toList());
    }

    @Override
    public boolean requireInitialization() {
        return items==null;
    }

    @Override
    public void init(Session session) {
        items = session.createQuery("FROM Item").list();
    }
}
