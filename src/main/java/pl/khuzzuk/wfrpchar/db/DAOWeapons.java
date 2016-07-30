package pl.khuzzuk.wfrpchar.db;

import org.hibernate.Session;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.db.annot.CommitTransaction;
import pl.khuzzuk.wfrpchar.db.annot.QueryTransaction;
import pl.khuzzuk.wfrpchar.db.annot.Weapons;
import pl.khuzzuk.wfrpchar.entities.items.WeaponType;

import java.util.List;

@Component
@Weapons
public class DAOWeapons implements Stateful, DAOTransactional<WeaponType, String> {
    private List<WeaponType> weapons;
    @Override
    @QueryTransaction
    public List<WeaponType> getAllItems() {
        return weapons;
    }

    @Override
    @QueryTransaction
    public WeaponType getItem(String criteria) {
        return weapons.stream().filter(w -> w.getName().equals(criteria)).findAny().orElse(null);
    }

    @Override
    @CommitTransaction
    public boolean commit(WeaponType toCommit, Session session) {
        boolean query = weapons.stream()
                .filter(i -> i.equals(toCommit))
                .findAny().isPresent();
        if (!query) session.save(toCommit);
        return !query;
    }

    @Override
    public boolean requireInitialization() {
        return weapons==null;
    }

    @Override
    public void init(Session session) {
        weapons = session.createQuery("from Item i " +
                "where type(i) = OneHandedWeaponType " +
                "or type(i) = TwoHandedWeaponType " +
                "or type(i) = BastardWeaponType").list();
    }
}
