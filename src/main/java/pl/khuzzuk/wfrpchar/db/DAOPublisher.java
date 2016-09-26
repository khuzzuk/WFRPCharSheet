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
import pl.khuzzuk.wfrpchar.entities.items.usable.AbstractHandWeapon;
import pl.khuzzuk.wfrpchar.entities.items.usable.Gun;
import pl.khuzzuk.wfrpchar.messaging.CommunicateMessage;
import pl.khuzzuk.wfrpchar.messaging.Message;
import pl.khuzzuk.wfrpchar.messaging.publishers.MultiContentPublisher;
import pl.khuzzuk.wfrpchar.messaging.publishers.Publisher;
import pl.khuzzuk.wfrpchar.messaging.publishers.Publishers;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Properties;

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
    @Inject
    @Named("messages")
    private Properties messages;
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

    void publishRangedWeaponTypes(Collection<RangedWeaponType> results, String message) {
        entitiesPublisher.publish(results, message);
    }

    void publishArmorTypes(Collection<ArmorType> allArmorTypes) {
        entitiesPublisher.publish(allArmorTypes, armorTypesResult);
    }

    void publishResourceTypes(Collection<ResourceType> resourceTypes) {
        entitiesPublisher.publish(resourceTypes, resourceTypeResult);
    }

    void publishWhiteWeaponsBaseTypes(Collection<WhiteWeaponType> results) {
        entitiesPublisher.publish(results, messages.getProperty("weapons.hand.baseType.allTypesList"));
    }

    void publishHandWeapons(Collection<AbstractHandWeapon<? extends WhiteWeaponType>> allHandWeapons) {
        entitiesPublisher.publish(allHandWeapons, messages.getProperty("weapons.hand.result"));
    }

    void publishRangedWeapons(Collection<Gun> allRangedWeapon) {
        entitiesPublisher.publish(allRangedWeapon, messages.getProperty("weapons.hand.result"));
    }

    void publish(MiscItem item) {
        entitiesPublisher.publish(item, mistItemTypeNamedResult);
    }

    public void publish(WhiteWeaponType whiteWeapon, String message) {
        entitiesPublisher.publish(whiteWeapon, message);
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

    void publish(AbstractHandWeapon handWeapon) {
        entitiesPublisher.publish(handWeapon, messages.getProperty("weapons.hand.result.specific"));
    }

    void publish(Gun gun) {
        entitiesPublisher.publish(gun, messages.getProperty("weapons.ranged.result.specific"));
    }

    void publish(String communicate) {
        Message message = new CommunicateMessage();
        message.setType(communicate);
        communicatePublisher.publish(new CommunicateMessage().setType(communicate));
    }
}
