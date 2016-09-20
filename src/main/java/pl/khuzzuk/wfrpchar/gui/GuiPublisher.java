package pl.khuzzuk.wfrpchar.gui;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.db.annot.SelectiveQuery;
import pl.khuzzuk.wfrpchar.entities.determinants.Determinant;
import pl.khuzzuk.wfrpchar.entities.items.usable.AbstractHandWeapon;
import pl.khuzzuk.wfrpchar.messaging.CommunicateMessage;
import pl.khuzzuk.wfrpchar.messaging.Message;
import pl.khuzzuk.wfrpchar.messaging.publishers.BagPublisher;
import pl.khuzzuk.wfrpchar.messaging.publishers.MultiContentPublisher;
import pl.khuzzuk.wfrpchar.messaging.publishers.Publisher;
import pl.khuzzuk.wfrpchar.messaging.publishers.Publishers;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Properties;

@Component
@Publishers
@PropertySource("classpath:messages.properties")
public class GuiPublisher {
    @Inject
    @Publishers
    @MainWindowBean
    private Publisher<Message> publisher;
    @Inject
    @Publishers
    @SelectiveQuery
    @MainWindowBean
    private BagPublisher<String> textRequestPublisher;
    @Inject
    @Publishers
    @MainWindowBean
    private MultiContentPublisher contentPublisher;
    @Inject
    @Named("messages")
    private Properties messages;

    void saveItem(String line) {
        textRequestPublisher.publish(line, messages.getProperty("database.saveEquipment"));
    }

    void saveResourceType(String line) {
        textRequestPublisher.publish(line, messages.getProperty("resource.type.save"));
    }

    void requestMiscItemType() {
        publisher.publish(new CommunicateMessage().setType(messages.getProperty("miscItemTypes.query")));
    }

    void requestWhiteWeapons() {
        publisher.publish(new CommunicateMessage().setType(messages.getProperty("whiteWeapons.query")));
    }

    void requestRangedWeapons() {
        publisher.publish(new CommunicateMessage().setType(messages.getProperty("rangedWeapons.query")));
    }

    void requestArmorTypes() {
        publisher.publish(new CommunicateMessage().setType(messages.getProperty("armorTypes.query")));
    }

    void requestHandWeapons() {
        publisher.publish(new CommunicateMessage().setType(messages.getProperty("weapons.hand.query")));
    }

    void requestResourceTypes() {
        publisher.publish(new CommunicateMessage().setType(messages.getProperty("resource.type.query")));
    }

    void requestResetDB() {
        publisher.publish(new CommunicateMessage().setType(messages.getProperty("database.reset")));
    }

    void requestMiscItemTypeLoad(String name) {
        textRequestPublisher.publish(name, messages.getProperty("miscItemTypes.query.specific"));
    }

    void requestWhiteWeaponLoad(String name) {
        textRequestPublisher.publish(name, messages.getProperty("whiteWeapons.query.specific"));
    }

    void requestRangedWeaponLoad(String name) {
        textRequestPublisher.publish(name, messages.getProperty("rangedWeapons.query.specific"));
    }

    void requestArmorTypeLoad(String name) {
        textRequestPublisher.publish(name, messages.getProperty("armorTypes.query.specific"));
    }

    void requestResourceType(String name) {
        textRequestPublisher.publish(name, messages.getProperty("resource.type.query.specific"));
    }

    void removeMiscItemType(String name) {
        textRequestPublisher.publish(name, messages.getProperty("miscItemTypes.remove"));
    }

    void removeRangedWeapon(String name) {
        textRequestPublisher.publish(name, messages.getProperty("rangedWeapons.remove"));
    }

    void removeWhiteWeapon(String name) {
        textRequestPublisher.publish(name, messages.getProperty("whiteWeapons.remove"));
    }

    void removeArmorType(String name) {
        textRequestPublisher.publish(name, messages.getProperty("armorTypes.remove"));
    }

    void removeResourceType(String name) {
        textRequestPublisher.publish(name, messages.getProperty("resource.type.remove"));
    }

    void publish(String text) {
        publisher.publish(new CommunicateMessage().setType(text));
    }

    void requestWWBaseType(String name) {
        textRequestPublisher.publish(name, messages.getProperty("weapons.hand.baseType.selected"));
    }

    public void publish(Determinant determinant, String msg) {
        contentPublisher.publish(determinant, msg);
    }

    void publish(AbstractHandWeapon handWeapon) {
        contentPublisher.publish(handWeapon, messages.getProperty("weapons.hand.save"));
    }
}
