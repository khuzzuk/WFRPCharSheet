package pl.khuzzuk.wfrpchar.db;

import org.hibernate.Session;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.db.annot.CommitTransaction;
import pl.khuzzuk.wfrpchar.db.annot.FightingEquipments;
import pl.khuzzuk.wfrpchar.db.annot.QueryTransaction;
import pl.khuzzuk.wfrpchar.entities.items.FightingEquipment;

import java.util.List;
import java.util.Optional;

@Component
@FightingEquipments
public class DAOFightingEquipment implements Stateful, DAOTransactional<FightingEquipment, String> {
    private List<FightingEquipment> equipments;
    @Override
    @QueryTransaction
    public List<FightingEquipment> getAllItems() {
        return equipments;
    }

    @Override
    @QueryTransaction
    public FightingEquipment getItem(String criteria) {
        return equipments.stream().filter(e -> e.getName().equals(criteria)).findFirst().orElseGet(null);
    }

    @Override
    @CommitTransaction
    public boolean commit(FightingEquipment toCommit, Session session) {
        Optional<FightingEquipment> query = equipments.stream()
                .filter(i -> i.equals(toCommit))
                .findAny();
        if (!query.isPresent()) {
            session.save(toCommit);
        }
        return !query.isPresent();
    }

    @Override
    public boolean requireInitialization() {
        return equipments==null;
    }

    @Override
    public void init(Session session) {
        equipments = session.createQuery("from Item i " +
                "where type(i) = OneHandedWeaponType " +
                "or type(i) = TwoHandedWeaponType " +
                "or type(i) = BastardWeaponType ").list();
    }
}
