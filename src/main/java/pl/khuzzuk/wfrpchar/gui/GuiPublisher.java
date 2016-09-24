package pl.khuzzuk.wfrpchar.gui;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.db.annot.SelectiveQuery;
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

    public void saveItem(String line) {
        textRequestPublisher.publish(line, messages.getProperty("database.saveEquipment"));
    }

    public void saveResourceType(String line) {
        textRequestPublisher.publish(line, messages.getProperty("resource.type.save"));
    }

    public void requestMiscItemType() {
        publisher.publish(new CommunicateMessage().setType(messages.getProperty("miscItemTypes.query")));
    }

    public void requestWhiteWeapons() {
        publisher.publish(new CommunicateMessage().setType(messages.getProperty("whiteWeapons.query")));
    }

    public void requestRangedWeapons() {
        publisher.publish(new CommunicateMessage().setType(messages.getProperty("rangedWeapons.query")));
    }

    public void requestArmorTypes() {
        publisher.publish(new CommunicateMessage().setType(messages.getProperty("armorTypes.query")));
    }

    public void requestHandWeapons() {
        publisher.publish(new CommunicateMessage().setType(messages.getProperty("weapons.hand.query")));
    }

    public void requestResourceTypes() {
        publisher.publish(new CommunicateMessage().setType(messages.getProperty("resource.type.query")));
    }

    public void requestResetDB() {
        publisher.publish(new CommunicateMessage().setType(messages.getProperty("database.reset")));
    }

    public void requestMiscItemTypeLoad(String name) {
        textRequestPublisher.publish(name, messages.getProperty("miscItemTypes.query.specific"));
    }

    public void requestWhiteWeaponLoad(String name) {
        textRequestPublisher.publish(name, messages.getProperty("whiteWeapons.query.specific"));
    }

    public void requestRangedWeaponLoad(String name) {
        textRequestPublisher.publish(name, messages.getProperty("rangedWeapons.query.specific"));
    }

    public void requestArmorTypeLoad(String name) {
        textRequestPublisher.publish(name, messages.getProperty("armorTypes.query.specific"));
    }

    public void requestHandWeapon(String name) {
        textRequestPublisher.publish(name, messages.getProperty("weapons.hand.query.specific"));
    }

    public void requestResourceType(String name) {
        textRequestPublisher.publish(name, messages.getProperty("resource.type.query.specific"));
    }

    public void removeMiscItemType(String name) {
        textRequestPublisher.publish(name, messages.getProperty("miscItemTypes.remove"));
    }

    public void removeRangedWeapon(String name) {
        textRequestPublisher.publish(name, messages.getProperty("rangedWeapons.remove"));
    }

    public void removeWhiteWeapon(String name) {
        textRequestPublisher.publish(name, messages.getProperty("whiteWeapons.remove"));
    }

    public void removeArmorType(String name) {
        textRequestPublisher.publish(name, messages.getProperty("armorTypes.remove"));
    }

    public void removeResourceType(String name) {
        textRequestPublisher.publish(name, messages.getProperty("resource.type.remove"));
    }

    public void removeHandWeapon(String name) {
        contentPublisher.publish(name, messages.getProperty("weapons.hand.remove"));
    }

    public void publish(String text) {
        publisher.publish(new CommunicateMessage().setType(text));
    }

    public void requestWWBaseType(String name) {
        textRequestPublisher.publish(name, messages.getProperty("weapons.hand.baseType.selected"));
    }

    public void publish(String textContent, String msg) {
        textRequestPublisher.publish(textContent, msg);
    }

    public void publish(AbstractHandWeapon handWeapon) {
        contentPublisher.publish(handWeapon, messages.getProperty("weapons.hand.save"));
    }
}
