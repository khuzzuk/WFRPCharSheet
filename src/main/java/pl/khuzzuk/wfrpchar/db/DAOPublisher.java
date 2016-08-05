package pl.khuzzuk.wfrpchar.db;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.db.annot.Weapons;
import pl.khuzzuk.wfrpchar.entities.items.WeaponType;
import pl.khuzzuk.wfrpchar.messaging.ContentPublisher;
import pl.khuzzuk.wfrpchar.messaging.Publishers;

import javax.inject.Inject;
import java.util.List;

@Component
@Publishers
@PropertySource("classpath:messages.properties")
public class DAOPublisher {
    @Inject
    @Weapons
    @Publishers
    private ContentPublisher<List<WeaponType>> weaponsPublisher;
    private String weaponResultMsgType;

    @Inject
    public DAOPublisher(@Value("${weapons.result}") String weaponResultMsgType) {
        this.weaponResultMsgType = weaponResultMsgType;
    }

    void publish(List<WeaponType> results) {
        weaponsPublisher.publish(results, weaponResultMsgType);
    }
}
