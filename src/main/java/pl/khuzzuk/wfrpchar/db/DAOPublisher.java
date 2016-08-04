package pl.khuzzuk.wfrpchar.db;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.db.annot.Weapons;
import pl.khuzzuk.wfrpchar.entities.items.WeaponType;
import pl.khuzzuk.wfrpchar.messaging.ContentPublisher;
import pl.khuzzuk.wfrpchar.messaging.Publishers;

import javax.inject.Inject;
import java.util.List;

@NoArgsConstructor
@Component
@Publishers
public class DAOPublisher {
    @Inject
    @Weapons
    @Publishers
    private ContentPublisher<List<WeaponType>> weaponsPublisher;

    void publish(List<WeaponType> results) {
        weaponsPublisher.publish(results);
    }
}
