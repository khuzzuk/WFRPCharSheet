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

    public void requestRangedWeaponsTypes() {
        publisher.publish(new CommunicateMessage().setType(messages.getProperty("rangedWeapons.query")));
    }

    public void requestArmorTypes() {
        publisher.publish(new CommunicateMessage().setType(messages.getProperty("armorTypes.query")));
    }

    public void requestHandWeapons() {
        publisher.publish(new CommunicateMessage().setType(messages.getProperty("weapons.hand.query")));
    }

    public void requestRangedWeapons() {
        publish(messages.getProperty("weapons.ranged.query"));
    }

    public void requestArmor() {
        publish(messages.getProperty("armor.query"));
    }

    public void requestAmmunition() {
        publish(messages.getProperty("ammunition.query"));
    }

    public void requestResourceTypes() {
        publisher.publish(new CommunicateMessage().setType(messages.getProperty("resource.type.query")));
    }

    public void requestCharacters() {
        publish(messages.getProperty("character.query"));
    }

    public void requestSkills() {
        publish(messages.getProperty("skills.query"));
    }

    public void requestProfessionClasses() {
        publish(messages.getProperty("professions.class.query"));
    }

    public void requestProfessions() {
        publish(messages.getProperty("professions.query"));
    }

    public void requestRaces() {
        publish(messages.getProperty("race.query"));
    }

    public void requestMagicSchools() {
        publish(messages.getProperty("magic.schools.query"));
    }

    public void requestSpells() {
        publish(messages.getProperty("magic.spells.query"));
    }

    public void requestPlayers() {
        publish(messages.getProperty("player.query"));
    }

    public void requestResetDB() {
        publisher.publish(new CommunicateMessage().setType(messages.getProperty("database.reset")));
    }

    public void requestMiscItemTypeLoad(String name) {
        textRequestPublisher.publish(name, messages.getProperty("miscItemTypes.query.specific"));
    }

    public void requestWhiteWeaponType(String name) {
        textRequestPublisher.publish(name, messages.getProperty("whiteWeapons.query.specific"));
    }

    public void requestRangedWeaponType(String name) {
        textRequestPublisher.publish(name, messages.getProperty("rangedWeapons.query.specific"));
    }

    public void requestArmorTypeLoad(String name) {
        textRequestPublisher.publish(name, messages.getProperty("armorTypes.query.specific"));
    }

    public void requestAmmunitionType(String name) {
        textRequestPublisher.publish(name, messages.getProperty("ammo.type.query.specific"));
    }

    public void requestHandWeapon(String name) {
        textRequestPublisher.publish(name, messages.getProperty("weapons.hand.query.specific"));
    }

    public void requestRangedWeapon(String name) {
        publish(name, messages.getProperty("weapons.ranged.query.specific"));
    }

    public void requestArmor(String name) {
        publish(name, messages.getProperty("armor.query.specific"));
    }

    public void requestAmmunition(String name) {
        publish(name, messages.getProperty("ammunition.query.specific"));
    }

    public void requestResourceType(String name) {
        publish(name, messages.getProperty("resource.type.query.specific"));
    }

    public void requestSkill(String name) {
        publish(name, messages.getProperty("skills.query.specific"));
    }

    public void requestProfessionClass(String name) {
        publish(name, messages.getProperty("professions.class.query.specific"));
    }

    public void requestProfession(String name) {
        publish(name, messages.getProperty("professions.query.specific"));
    }

    public void requestRace(String name) {
        publish(name, messages.getProperty("race.query.specific"));
    }

    public void requestMagicSchool(String name) {
        publish(name, messages.getProperty("magic.schools.query.specific"));
    }

    public void requestSpell(String name) {
        publish(name, messages.getProperty("magic.spells.query.specific"));
    }

    public void requestPlayer(String name) {
        publish(name, messages.getProperty("player.query.specific"));
    }

    public void removeMiscItemType(String name) {
        publish(name, messages.getProperty("miscItemTypes.remove"));
    }

    public void removeRangedTypeWeapon(String name) {
        publish(name, messages.getProperty("rangedWeapons.remove"));
    }

    public void removeWhiteWeapon(String name) {
        publish(name, messages.getProperty("whiteWeapons.remove"));
    }

    public void removeArmorType(String name) {
        publish(name, messages.getProperty("armorTypes.remove"));
    }

    public void removeAmmunitionType(String name) {
        publish(name, messages.getProperty("ammo.type.remove"));
    }

    public void removeResourceType(String name) {
        publish(name, messages.getProperty("resource.type.remove"));
    }

    public void removeHandWeapon(String name) {
        publish(name, messages.getProperty("weapons.hand.remove"));
    }

    public void removeRangedWeapon(String name) {
        publish(name, messages.getProperty("weapons.ranged.remove"));
    }

    public void removeArmor(String name) {
        publish(name, messages.getProperty("armor.remove"));
    }

    public void removeAmmunition(String name) {
        publish(name, messages.getProperty("ammunition.remove"));
    }

    public void removeSkill(String name) {
        publish(name, messages.getProperty("skills.remove"));
    }

    public void removeProfessionClass(String name) {
        publish(name, messages.getProperty("professions.class.remove"));
    }

    public void removeProfession(String name) {
        publish(name, messages.getProperty("professions.remove"));
    }

    public void removeRace(String name) {
        publish(name, messages.getProperty("race.remove"));
    }

    public void removeMagicSchool(String name) {
        publish(name, messages.getProperty("magic.schools.remove"));
    }

    public void removeSpell(String name) {
        publish(name, messages.getProperty("magic.spells.remove"));
    }

    public void removePlayer(String name) {
        publish(name, messages.getProperty("player.remove"));
    }

    public void savePlayer(String line) {
        publish(line, messages.getProperty("player.save"));
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

    public <T> void publish(T content, String msg) {
        contentPublisher.publish(content, msg);
    }

    public void publish(AbstractHandWeapon handWeapon) {
        contentPublisher.publish(handWeapon, messages.getProperty("weapons.hand.save"));
    }
}
