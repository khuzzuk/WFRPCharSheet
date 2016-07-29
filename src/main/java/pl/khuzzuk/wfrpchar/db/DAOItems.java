package pl.khuzzuk.wfrpchar.db;

import org.hibernate.Session;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.db.annot.Items;
import pl.khuzzuk.wfrpchar.db.annot.Transaction;
import pl.khuzzuk.wfrpchar.entities.items.Item;
import pl.khuzzuk.wfrpchar.entities.items.WeaponType;

import java.util.List;

@Component
@Items
public class DAOItems {
    @Transaction
    List<Item> getAllItems(Session session) {
        return (List<Item>) session.createQuery("FROM Item").list();
    }
    @Transaction
    List<WeaponType> getAllWeapons(Session session) {
        return (List<WeaponType>) session.createQuery("" +
                "select i " +
                "from Item i " +
                "where type(i) = OneHandedWeaponType " +
                "or type(i) = TwoHandedWeaponType " +
                "or type(i) = BastardWeaponType").list();
    }
}
