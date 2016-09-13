package pl.khuzzuk.wfrpchar.db;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.db.annot.DaoBean;
import pl.khuzzuk.wfrpchar.entities.items.ResourceType;
import pl.khuzzuk.wfrpchar.entities.items.types.ArmorType;
import pl.khuzzuk.wfrpchar.entities.items.types.MiscItem;
import pl.khuzzuk.wfrpchar.entities.items.types.RangedWeaponType;
import pl.khuzzuk.wfrpchar.entities.items.types.WhiteWeaponType;
import pl.khuzzuk.wfrpchar.messaging.CommunicateMessage;
import pl.khuzzuk.wfrpchar.messaging.Message;
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
    @DaoBean
    @Publishers
    private Publisher<Message> communicatePublisher;
    @Inject
    @Publishers
    private MultiContentPublisher entitiesPublisher;
    @Value("${miscItemTypes.result}")
    private String mistItemTypeResult;
    @Value("${miscItemTypes.result.specific}")
    private String mistItemTypeNamedResult;
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
    @Value("${armorTypes.result}")
    private String armorTypesResult;
    @Value("${armorTypes.result.specific}")
    private String armorTypesNamedResult;
    @Value("${resource.type.result}")
    private String resourceTypeResult;
    @Value("${resource.type.result.specific}")
    private String resourceTypeResultSpecific;

    void publishMiscItems(Collection<MiscItem> results) {
        entitiesPublisher.publish(results, mistItemTypeResult);
    }

    void publishWhiteWeapons(Collection<WhiteWeaponType> results) {
        entitiesPublisher.publish(results, weaponResultMsgType);
    }

    void publishRangedWeapons(Collection<RangedWeaponType> results) {
        entitiesPublisher.publish(results, rangedWeaponsResult);
    }

    void publishArmorTypes(Collection<ArmorType> allArmorTypes) {
        entitiesPublisher.publish(allArmorTypes, armorTypesResult);
    }

    void publishResourceTypes(Collection<ResourceType> resourceTypes) {
        entitiesPublisher.publish(resourceTypes, resourceTypeResult);
    }

    void publish(MiscItem item) {
        entitiesPublisher.publish(item, mistItemTypeNamedResult);
    }

    void publish(WhiteWeaponType result) {
        entitiesPublisher.publish(result, whiteWeaponNamedResultMsgType);
    }

    void publish(RangedWeaponType weapon) {
        entitiesPublisher.publish(weapon, rangeWeaponNamedResult);
    }

    void publish(ArmorType armorType) {
        entitiesPublisher.publish(armorType, armorTypesNamedResult);
    }

    void publish(ResourceType resourceType) {
        entitiesPublisher.publish(resourceType, resourceTypeResultSpecific);
    }

    void publish(String communicate) {
        Message message = new CommunicateMessage();
        message.setType(communicate);
        communicatePublisher.publish(new CommunicateMessage().setType(communicate));
    }
}
