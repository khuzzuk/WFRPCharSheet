package pl.khuzzuk.wfrpchar.db;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.db.annot.DaoBean;
import pl.khuzzuk.wfrpchar.db.annot.SelectiveQuery;
import pl.khuzzuk.wfrpchar.db.annot.WhiteWeapons;
import pl.khuzzuk.wfrpchar.entities.items.RangedWeaponType;
import pl.khuzzuk.wfrpchar.entities.items.WhiteWeaponType;
import pl.khuzzuk.wfrpchar.messaging.CommunicateMessage;
import pl.khuzzuk.wfrpchar.messaging.Message;
import pl.khuzzuk.wfrpchar.messaging.publishers.BagPublisher;
import pl.khuzzuk.wfrpchar.messaging.publishers.MultiContentPublisher;
import pl.khuzzuk.wfrpchar.messaging.publishers.Publisher;
import pl.khuzzuk.wfrpchar.messaging.publishers.Publishers;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Component
@Publishers
@PropertySource("classpath:messages.properties")
public class DAOPublisher {
    @Inject
    @WhiteWeapons
    @Publishers
    @DaoBean
    private BagPublisher<Collection<WhiteWeaponType>> weaponsPublisher;
    @Inject
    @WhiteWeapons
    @Publishers
    @SelectiveQuery
    @DaoBean
    private BagPublisher<WhiteWeaponType> whiteQWeaponResultPublisher;
    @Inject
    @DaoBean
    @Publishers
    private Publisher<Message> communicatePublisher;
    @Inject
    @Publishers
    private MultiContentPublisher entitiesPublisher;
    @Value("${whiteWeapons.result}")
    @NotNull
    private String weaponResultMsgType;
    @Value("${whiteWeapons.result.specific}")
    @NotNull
    private String whiteWeaponNamedResultMsgType;
    @Value("${rangedWeapons.result}")
    @NotNull
    private String rangedWeaponsResult;
    @Value("${rangedWeapons.result.specific}")
    @NotNull
    private String rangeWeaponNamedResult;

    void publishWhiteWeapons(Collection<WhiteWeaponType> results) {
        weaponsPublisher.publish(results, weaponResultMsgType);
    }

    void publish(WhiteWeaponType result) {
        whiteQWeaponResultPublisher.publish(result, whiteWeaponNamedResultMsgType);
    }

    void publishRangedWeapons(Collection<RangedWeaponType> results) {
        entitiesPublisher.publish(results, rangedWeaponsResult);
    }

    void publish(RangedWeaponType weapon) {
        entitiesPublisher.publish(weapon, rangeWeaponNamedResult);
    }

    void publish(String communicate) {
        Message message = new CommunicateMessage();
        message.setType(communicate);
        communicatePublisher.publish(new CommunicateMessage().setType(communicate));
    }
}
