package pl.khuzzuk.wfrpchar.db;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.db.annot.DaoBean;
import pl.khuzzuk.wfrpchar.db.annot.Manager;
import pl.khuzzuk.wfrpchar.entities.Character;
import pl.khuzzuk.wfrpchar.entities.Race;
import pl.khuzzuk.wfrpchar.entities.characters.Player;
import pl.khuzzuk.wfrpchar.entities.competency.*;
import pl.khuzzuk.wfrpchar.entities.items.ParserBag;
import pl.khuzzuk.wfrpchar.entities.items.ResourceType;
import pl.khuzzuk.wfrpchar.entities.items.WeaponParser;
import pl.khuzzuk.wfrpchar.entities.items.types.*;
import pl.khuzzuk.wfrpchar.entities.items.usable.AbstractHandWeapon;
import pl.khuzzuk.wfrpchar.entities.items.usable.Ammunition;
import pl.khuzzuk.wfrpchar.entities.items.usable.Armor;
import pl.khuzzuk.wfrpchar.entities.items.usable.Gun;
import pl.khuzzuk.wfrpchar.messaging.Message;
import pl.khuzzuk.wfrpchar.messaging.ReactorBean;
import pl.khuzzuk.wfrpchar.messaging.publishers.Publishers;
import pl.khuzzuk.wfrpchar.messaging.subscribers.MultiContentSubscriber;
import pl.khuzzuk.wfrpchar.messaging.subscribers.MultiSubscriber;
import pl.khuzzuk.wfrpchar.messaging.subscribers.Subscribers;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Properties;

import static pl.khuzzuk.wfrpchar.db.LineParser.getFrom;

@NoArgsConstructor
@Subscribers
@ReactorBean
@Component
public class DAOReactor {
    private DAO dao;
    @Inject
    private WeaponParser weaponParser;
    @Inject
    @Publishers
    private DAOPublisher daoPublisher;
    @Inject
    @Subscribers
    @DaoBean
    private MultiContentSubscriber daoContentSubscriber;
    @Inject
    @Subscribers
    @DaoBean
    private MultiSubscriber<Message> multiSubscriber;
    @Inject
    private DBInitializer dbInitializer;
    @Inject
    private DBDumper dbDumper;
    @Inject
    @Named("messages")
    private Properties messages;


    private void getAllWhiteWeaponsTypes() {
        daoPublisher.publishWhiteWeapons(dao.getAllEntities(WhiteWeaponType.class));
    }

    private void saveItem(String line) {
        Item i = weaponParser.parseEquipment(line.split(";"));
        if (i instanceof WhiteWeaponType) {
            dao.saveEntity(WhiteWeaponType.class, (WhiteWeaponType) i);
            daoPublisher.publish(messages.getProperty("whiteWeapons.query"));
        } else if (i instanceof RangedWeaponType) {
            dao.saveEntity(RangedWeaponType.class, (RangedWeaponType) i);
            daoPublisher.publish(messages.getProperty("rangedWeapons.query"));
        } else if (i instanceof ArmorType) {
            dao.saveEntity(ArmorType.class, (ArmorType) i);
            daoPublisher.publish(messages.getProperty("armorTypes.query"));
        } else if (i instanceof MiscItem) {
            dao.saveEntity(MiscItem.class, (MiscItem) i);
            daoPublisher.publish(messages.getProperty("miscItemTypes.query"));
        } else if (i instanceof AmmunitionType) {
            dao.saveEntity(AmmunitionType.class, (AmmunitionType) i);
            daoPublisher.publish(messages.getProperty("ammo.type.query"));
        }
    }

    private void saveResourceType(String line) {
        dao.saveEntity(ResourceType.class, ResourceType.getFromCsv(line.split(";")));
        daoPublisher.publish(messages.getProperty("resource.type.query"));
    }

    private void saveHandWeapon(String line) {
        String[] fields = line.split(";");
        ParserBag<WhiteWeaponType> bag = getParserBag(WhiteWeaponType.class, fields);
        dao.saveEntity(AbstractHandWeapon.class, weaponParser.parseHandWeapon(fields, bag));
        daoPublisher.publish(messages.getProperty("weapons.hand.query"));
    }

    private void saveRangedWeapon(String line) {
        String[] fields = line.split(";");
        ParserBag<RangedWeaponType> bag = getParserBag(RangedWeaponType.class, fields);
        Gun gun = weaponParser.parseGun(fields, bag);
        dao.saveEntity(Gun.class, gun);
        daoPublisher.publish(messages.getProperty("weapons.ranged.query"));
    }

    private void saveArmor(String line) {
        String[] fields = line.split(";");
        Armor armor = weaponParser.parseArmor(fields, getParserBag(ArmorType.class, fields));
        dao.saveEntity(Armor.class, armor);
        daoPublisher.publish(messages.getProperty("armor.query"));
    }

    private void saveAmmunition(String line) {
        String[] fields = line.split(";");
        Ammunition ammunition = weaponParser.parseAmmunition(fields,
                getParserBag(AmmunitionType.class, fields));
        dao.saveEntity(Ammunition.class, ammunition);
        daoPublisher.publish(messages.getProperty("ammunition.query"));
    }

    private <T extends FightingEquipment> ParserBag<T> getParserBag(Class<T> entityType, String[] fields) {
        return new ParserBag<>(
                dao.getEntity(entityType, fields[4]),
                dao.getEntity(ResourceType.class, fields[5]),
                dao.getEntity(ResourceType.class, fields[6]));
    }

    private void saveSkill(String line) {
        dao.saveEntity(Skill.class, Skill.fromCsv(line.split(";")));
        daoPublisher.publish(messages.getProperty("skills.query"));
    }

    private void saveProfessionClass(String line) {
        dao.saveEntity(ProfessionClass.class, ProfessionClass.fromCsv(line.split(";"), dao));
        daoPublisher.publish(messages.getProperty("professions.class.query"));
    }

    private void saveProfession(String line) {
        dao.saveEntity(Profession.class, Profession.build(line.split(";"), dao));
        daoPublisher.publish(messages.getProperty("professions.query"));
    }

    private void saveRace(String line) {
        dao.saveEntity(Race.class, Race.fromCsv(line.split(";"), dao));
        daoPublisher.publish(messages.getProperty("race.query"));
    }

    private void saveMagicSchool(String line) {
        dao.saveEntity(MagicSchool.class, MagicSchool.getFromCsv(line.split(";")));
        daoPublisher.publish(messages.getProperty("magic.schools.query"));
    }

    private void saveSpell(String line) {
        dao.saveEntity(Spell.class, Spell.fromCsv(line.split(";"), dao));
        daoPublisher.publish(messages.getProperty("magic.spells.query"));
    }

    private void savePlayer(String line) {
        dao.saveEntity(Player.class, Player.fromCsv(getFrom(line, 24), dao));
        daoPublisher.publish(messages.getProperty("player.query"));
    }

    private void removeMiscItem(String name) {
        dao.removeEntity(MiscItem.class, name);
        daoPublisher.publish(messages.getProperty("miscItemTypes.query"));
    }

    private void removeWhiteWeaponType(String name) {
        dao.removeEntity(WhiteWeaponType.class, name);
        daoPublisher.publish(messages.getProperty("whiteWeapons.query"));
    }

    private void removeRangedWeaponType(String name) {
        dao.removeEntity(RangedWeaponType.class, name);
        daoPublisher.publish(messages.getProperty("rangedWeapons.query"));
    }

    private void removeArmorType(String name) {
        dao.removeEntity(ArmorType.class, name);
        daoPublisher.publish(messages.getProperty("armorTypes.query"));
    }

    private void removeAmmunitionType(String name) {
        dao.removeEntity(AmmunitionType.class, name);
        daoPublisher.publish(messages.getProperty("ammo.type.query"));
    }

    private void removeHandWeapon(String name) {
        dao.removeEntity(AbstractHandWeapon.class, name);
        daoPublisher.publish(messages.getProperty("weapons.hand.query"));
    }

    private void removeRangedWeapon(String name) {
        dao.removeEntity(Gun.class, name);
        daoPublisher.publish(messages.getProperty("weapons.ranged.query"));
    }

    private void removeArmor(String name) {
        dao.removeEntity(Armor.class, name);
        daoPublisher.publish(messages.getProperty("armor.query"));
    }

    private void removeAmmunition(String name) {
        dao.removeEntity(Ammunition.class, name);
        daoPublisher.publish(messages.getProperty("ammunition.query"));
    }

    private void removeResourceType(String name) {
        dao.removeEntity(ResourceType.class, name);
        daoPublisher.publish(messages.getProperty("resource.type.query"));
    }

    private void removeSkill(String name) {
        dao.removeEntity(Skill.class, name);
        daoPublisher.publish(messages.getProperty("skills.query"));
    }

    private void removeProfessionClass(String name) {
        dao.removeEntity(ProfessionClass.class, name);
        daoPublisher.publish(messages.getProperty("professions.class.query"));
    }

    private void removeProfession(String name) {
        dao.removeEntity(Profession.class, name);
        daoPublisher.publish(messages.getProperty("professions.query"));
    }

    private void removeRace(String name) {
        dao.removeEntity(Race.class, name);
        daoPublisher.publish(messages.getProperty("race.query"));
    }

    private void removeMagicSchool(String name) {
        dao.removeEntity(MagicSchool.class, name);
        daoPublisher.publish(messages.getProperty("magic.schools.query"));
    }

    private void removeSpell(String name) {
        dao.removeEntity(Spell.class, name);
        daoPublisher.publish(messages.getProperty("magic.spells.query"));
    }

    private void removePlayer(String name) {
        dao.removeEntity(Player.class, name);
        daoPublisher.publish(messages.getProperty("player.query"));
    }

    private void getAllMiscItemsTypes() {
        daoPublisher.publishMiscItems(dao.getAllEntities(MiscItem.class));
    }

    private void getAllRangedWeaponTypes() {
        daoPublisher.publishRangedWeaponTypes(dao.getAllEntities(RangedWeaponType.class), messages.getProperty("rangedWeapons.result"));
    }

    private void getRangedWeaponTypeByName(String name) {
        daoPublisher.publish(dao.getEntity(RangedWeaponType.class, name), messages.getProperty("rangedWeapons.result.specific"));
    }

    private void getAllArmorTypes() {
        daoPublisher.publishArmorTypes(dao.getAllEntities(ArmorType.class));
    }

    private void getAllAmmunitionTypes() {
        daoPublisher.publish(dao.getAllEntities(AmmunitionType.class), messages.getProperty("ammo.type.result"));
    }

    private void getAllResourceTypes() {
        daoPublisher.publishResourceTypes(dao.getAllEntities(ResourceType.class));
    }

    private <T extends WhiteWeaponType> void getAllHandWeapons() {
        daoPublisher.publishHandWeapons(dao.getAllEntities(AbstractHandWeapon.class));
    }

    private void getAllRangedWeapons() {
        daoPublisher.publishRangedWeapons(dao.getAllEntities(Gun.class));
    }

    private void getAllArmors() {
        daoPublisher.publish(dao.getAllEntities(Armor.class), messages.getProperty("armor.result"));
    }

    private void getAllAmmunitions() {
        daoPublisher.publish(dao.getAllEntities(Ammunition.class), messages.getProperty("ammunition.result"));
    }

    private void getAllCharacters() {
        daoPublisher.publish(dao.getAllEntities(Character.class), messages.getProperty("character.result"));
    }

    private void getAllSkills() {
        daoPublisher.publish(dao.getAllEntities(Skill.class), messages.getProperty("skills.result"));
    }

    private void getAllProfessionClasses() {
        daoPublisher.publish(dao.getAllEntities(ProfessionClass.class), messages.getProperty("professions.class.result"));
    }

    private void getAllProfessions() {
        daoPublisher.publish(dao.getAllEntities(Profession.class), messages.getProperty("professions.result"));
    }

    private void getAllRaces() {
        daoPublisher.publish(dao.getAllEntities(Race.class), messages.getProperty("race.result"));
    }

    private void getAllMagicSchools() {
        daoPublisher.publish(dao.getAllEntities(MagicSchool.class), messages.getProperty("magic.schools.result"));
    }

    private void getAllSpells() {
        daoPublisher.publish(dao.getAllEntities(Spell.class), messages.getProperty("magic.spells.result"));
    }

    private void getAllPlayers() {
        daoPublisher.publish(dao.getAllEntities(Player.class), messages.getProperty("player.result"));
    }

    private void getAllWWBaseType() {
        daoPublisher.publishWhiteWeaponsBaseTypes(dao.getAllEntities(WhiteWeaponType.class));
    }

    private void getAllRangedBaseTypes() {
        daoPublisher.publishRangedWeaponTypes(dao.getAllEntities(RangedWeaponType.class),
                messages.getProperty("weapons.ranged.baseType.allTypesList"));
    }

    private void getAllArmorBaseTypes() {
        daoPublisher.publish(dao.getAllEntities(ArmorType.class),
                messages.getProperty("armor.baseType.allTypesList"));
    }

    private void getWhiteWeaponByName(String name) {
        daoPublisher.publish(dao.getEntity(WhiteWeaponType.class, name), messages.getProperty("whiteWeapons.result.specific"));
    }

    private void getMiscItemTypeByName(String name) {
        daoPublisher.publish(dao.getEntity(MiscItem.class, name));
    }

    private void getArmorTypeByName(String name) {
        daoPublisher.publish(dao.getEntity(ArmorType.class, name));
    }

    private void getAmmunitionTypeByName(String name) {
        daoPublisher.publish(dao.getEntity(AmmunitionType.class, name), messages.getProperty("ammo.type.result.specific"));
    }

    private void getResourceType(String name) {
        daoPublisher.publish(dao.getEntity(ResourceType.class, name));
    }

    private void getHandWeapon(String name) {
        daoPublisher.publish(dao.getEntity(AbstractHandWeapon.class, name));
    }

    private void getRangedWeapon(String name) {
        daoPublisher.publish(dao.getEntity(Gun.class, name));
    }

    private void getArmor(String name) {
        daoPublisher.publish(dao.getEntity(Armor.class, name), messages.getProperty("armor.result.specific"));
    }

    private void getAmmunition(String name) {
        daoPublisher.publish(dao.getEntity(Ammunition.class, name), messages.getProperty("ammunition.result.specific"));
    }

    private void getSkill(String name) {
        daoPublisher.publish(dao.getEntity(Skill.class, name), messages.getProperty("skills.result.specific"));
    }

    private void getProfessionClass(String name) {
        daoPublisher.publish(dao.getEntity(ProfessionClass.class, name),
                messages.getProperty("professions.class.result.specific"));
    }

    private void getProfession(String name) {
        daoPublisher.publish(dao.getEntity(Profession.class, name), messages.getProperty("professions.result.specific"));
    }

    private void getRace(String name) {
        daoPublisher.publish(dao.getEntity(Race.class, name), messages.getProperty("race.result.specific"));
    }

    private void getMagicSchool(String name) {
        daoPublisher.publish(dao.getEntity(MagicSchool.class, name), messages.getProperty("magic.schools.result.specific"));
    }

    private void getSpell(String name) {
        daoPublisher.publish(dao.getEntity(Spell.class, name), messages.getProperty("magic.spells.result.specific"));
    }

    private void getPlayer(String name) {
        daoPublisher.publish(dao.getEntity(Player.class, name), messages.getProperty("player.result.specific"));
    }

    private void getWWBaseTypeByName(String name) {
        daoPublisher.publish(dao.getEntity(WhiteWeaponType.class, name), messages.getProperty("weapons.hand.baseType.choice"));
    }

    private void getRWBaseTypeByName(String name) {
        daoPublisher.publish(dao.getEntity(RangedWeaponType.class, name), messages.getProperty("weapons.ranged.baseType.choice"));
    }

    private void getARBaseTypeByName(String name) {
        daoPublisher.publish(dao.getEntity(RangedWeaponType.class, name), messages.getProperty("armor.baseType.choice"));
    }

    private void getAMBaseTypeByName(String name) {
        daoPublisher.publish(dao.getEntity(AmmunitionType.class, name), messages.getProperty("ammunition.baseType.choice"));
    }

    private void getSkillsToChoose() {
        daoPublisher.publish(dao.getAllEntities(Skill.class), messages.getProperty("professions.skills.allTypesList"));
    }

    private void getSkillsToProfessionClassChooser() {
        daoPublisher.publish(dao.getAllEntities(Skill.class), messages.getProperty("professions.class.skills.allTypesList"));
    }

    private void getSkillsForRaceChooser() {
        daoPublisher.publish(dao.getAllEntities(Skill.class), messages.getProperty("race.skills.allTypesList"));
    }

    private void getProfessionsToNextChoose() {
        daoPublisher.publish(dao.getAllEntities(Profession.class), messages.getProperty("professions.next.allTypesList"));
    }

    private void getSchoolForSpell() {
        daoPublisher.publish(dao.getAllEntities(MagicSchool.class), messages.getProperty("magic.spells.school.allTypesList"));
    }

    private void getIngredientsForSpell() {
        daoPublisher.publish(dao.getAllEntities(MiscItem.class), messages.getProperty("magic.spells.ingredients.allTypesList"));
    }

    private void getProfessionsForPlayerChoice() {
        daoPublisher.publish(dao.getAllEntities(Profession.class), messages.getProperty("player.profession.allTypesList"));
    }

    private void getHandWeaponsForPlayerChoice() {
        daoPublisher.publish(dao.getAllEntities(AbstractHandWeapon.class),
                messages.getProperty("player.weapons.white.allTypesList"));
    }

    private void getRangedWeaponsForPlayerChoice() {
        daoPublisher.publish(dao.getAllEntities(Gun.class),
                messages.getProperty("player.weapons.ranged.allTypesList"));
    }

    private void getArmorsForPlayerChoice() {
        daoPublisher.publish(dao.getAllEntities(Armor.class),
                messages.getProperty("player.armors.allTypesList"));
    }

    private void getItemsForPlayerChoice() {
        daoPublisher.publish(dao.getAllEntities(MiscItem.class),
                messages.getProperty("player.equipment.allTypesList"));
    }

    private void getSkillsForPlayerChoice() {
        daoPublisher.publish(dao.getAllEntities(Skill.class),
                messages.getProperty("player.skills.allTypesList"));
    }

    private void getSpellsForPlayerChoice() {
        daoPublisher.publish(dao.getAllEntities(Spell.class),
                messages.getProperty("player.spells.allTypesList"));
    }

    private void resetDB() {
        dao.getManager().resetDB(dao);
        daoPublisher.publish(messages.getProperty("whiteWeapons.query"));
        daoPublisher.publish(messages.getProperty("rangedWeapons.query"));
        daoPublisher.publish(messages.getProperty("armorTypes.query"));
        daoPublisher.publish(messages.getProperty("ammo.type.query"));
        daoPublisher.publish(messages.getProperty("miscItemTypes.query"));
        daoPublisher.publish(messages.getProperty("weapons.hand.query"));
        daoPublisher.publish(messages.getProperty("weapons.ranged.query"));
        daoPublisher.publish(messages.getProperty("armor.query"));
        daoPublisher.publish(messages.getProperty("ammunition.query"));
        daoPublisher.publish(messages.getProperty("skills.query"));
        daoPublisher.publish(messages.getProperty("professions.class.query"));
        daoPublisher.publish(messages.getProperty("professions.query"));
        daoPublisher.publish(messages.getProperty("race.query"));
        daoPublisher.publish(messages.getProperty("magic.schools.query"));
        daoPublisher.publish(messages.getProperty("magic.spells.query"));
    }

    @Inject
    public void setDao(@Manager DAO dao) {
        this.dao = dao;
    }

    @PostConstruct
    private void setReactors() {
        multiSubscriber.subscribe(messages.getProperty("database.reset"), this::resetDB);
        multiSubscriber.subscribe(messages.getProperty("database.save"), dbDumper::saveToFiles);

        //get all queries
        multiSubscriber.subscribe(messages.getProperty("miscItemTypes.query"), this::getAllMiscItemsTypes);
        multiSubscriber.subscribe(messages.getProperty("whiteWeapons.query"), this::getAllWhiteWeaponsTypes);
        multiSubscriber.subscribe(messages.getProperty("rangedWeapons.query"), this::getAllRangedWeaponTypes);
        multiSubscriber.subscribe(messages.getProperty("armorTypes.query"), this::getAllArmorTypes);
        multiSubscriber.subscribe(messages.getProperty("ammo.type.query"), this::getAllAmmunitionTypes);
        multiSubscriber.subscribe(messages.getProperty("resource.type.query"), this::getAllResourceTypes);
        multiSubscriber.subscribe(messages.getProperty("weapons.hand.query"), this::getAllHandWeapons);
        multiSubscriber.subscribe(messages.getProperty("weapons.ranged.query"), this::getAllRangedWeapons);
        multiSubscriber.subscribe(messages.getProperty("armor.query"), this::getAllArmors);
        multiSubscriber.subscribe(messages.getProperty("ammunition.query"), this::getAllAmmunitions);
        multiSubscriber.subscribe(messages.getProperty("character.query"), this::getAllCharacters);
        multiSubscriber.subscribe(messages.getProperty("skills.query"), this::getAllSkills);
        multiSubscriber.subscribe(messages.getProperty("professions.class.query"), this::getAllProfessionClasses);
        multiSubscriber.subscribe(messages.getProperty("professions.query"), this::getAllProfessions);
        multiSubscriber.subscribe(messages.getProperty("race.query"), this::getAllRaces);
        multiSubscriber.subscribe(messages.getProperty("magic.schools.query"), this::getAllMagicSchools);
        multiSubscriber.subscribe(messages.getProperty("magic.spells.query"), this::getAllSpells);
        multiSubscriber.subscribe(messages.getProperty("player.query"), this::getAllPlayers);

        //get all linked entities queries
        multiSubscriber.subscribe(messages.getProperty("weapons.hand.baseType.getAllTypes"), this::getAllWWBaseType);
        multiSubscriber.subscribe(messages.getProperty("weapons.ranged.baseType.getAllTypes"), this::getAllRangedBaseTypes);
        multiSubscriber.subscribe(messages.getProperty("armor.baseType.getAllTypes"), this::getAllArmorBaseTypes);
        multiSubscriber.subscribe(messages.getProperty("professions.class.skills.getAllTypes"), this::getSkillsToProfessionClassChooser);
        multiSubscriber.subscribe(messages.getProperty("professions.skills.getAllTypes"), this::getSkillsToChoose);
        multiSubscriber.subscribe(messages.getProperty("professions.next.getAllTypes"), this::getProfessionsToNextChoose);
        multiSubscriber.subscribe(messages.getProperty("race.skills.getAllTypes"), this::getSkillsForRaceChooser);
        multiSubscriber.subscribe(messages.getProperty("magic.spells.school.getAllTypes"), this::getSchoolForSpell);
        multiSubscriber.subscribe(messages.getProperty("magic.spells.ingredients.getAllTypes"), this::getIngredientsForSpell);
        multiSubscriber.subscribe(messages.getProperty("player.profession.getAllTypes"), this::getProfessionsForPlayerChoice);
        multiSubscriber.subscribe(messages.getProperty("player.weapons.white.getAllTypes"), this::getHandWeaponsForPlayerChoice);
        multiSubscriber.subscribe(messages.getProperty("player.weapons.ranged.getAllTypes"), this::getRangedWeaponsForPlayerChoice);
        multiSubscriber.subscribe(messages.getProperty("player.armors.getAllTypes"), this::getArmorsForPlayerChoice);
        multiSubscriber.subscribe(messages.getProperty("player.equipment.getAllTypes"), this::getItemsForPlayerChoice);
        multiSubscriber.subscribe(messages.getProperty("player.skills.getAllTypes"), this::getSkillsForPlayerChoice);
        multiSubscriber.subscribe(messages.getProperty("player.spells.getAllTypes"), this::getSpellsForPlayerChoice);

        //content queries
        daoContentSubscriber.subscribe(messages.getProperty("database.saveEquipment"), this::saveItem);
        daoContentSubscriber.subscribe(messages.getProperty("miscItemTypes.query.specific"), this::getMiscItemTypeByName);
        daoContentSubscriber.subscribe(messages.getProperty("miscItemTypes.remove"), this::removeMiscItem);
        daoContentSubscriber.subscribe(messages.getProperty("whiteWeapons.query.specific"), this::getWhiteWeaponByName);
        daoContentSubscriber.subscribe(messages.getProperty("whiteWeapons.remove"), this::removeWhiteWeaponType);
        daoContentSubscriber.subscribe(messages.getProperty("rangedWeapons.query.specific"), this::getRangedWeaponTypeByName);
        daoContentSubscriber.subscribe(messages.getProperty("rangedWeapons.remove"), this::removeRangedWeaponType);
        daoContentSubscriber.subscribe(messages.getProperty("armorTypes.query.specific"), this::getArmorTypeByName);
        daoContentSubscriber.subscribe(messages.getProperty("armorTypes.remove"), this::removeArmorType);
        daoContentSubscriber.subscribe(messages.getProperty("ammo.type.query.specific"), this::getAmmunitionTypeByName);
        daoContentSubscriber.subscribe(messages.getProperty("ammo.type.remove"), this::removeAmmunitionType);
        daoContentSubscriber.subscribe(messages.getProperty("resource.type.query.specific"), this::getResourceType);
        daoContentSubscriber.subscribe(messages.getProperty("resource.type.remove"), this::removeResourceType);
        daoContentSubscriber.subscribe(messages.getProperty("resource.type.save"), this::saveResourceType);
        daoContentSubscriber.subscribe(messages.getProperty("weapons.hand.baseType.selected"), this::getWWBaseTypeByName);
        daoContentSubscriber.subscribe(messages.getProperty("weapons.hand.query.specific"), this::getHandWeapon);
        daoContentSubscriber.subscribe(messages.getProperty("weapons.hand.save"), this::saveHandWeapon);
        daoContentSubscriber.subscribe(messages.getProperty("weapons.hand.remove"), this::removeHandWeapon);
        daoContentSubscriber.subscribe(messages.getProperty("weapons.ranged.baseType.selected"), this::getRWBaseTypeByName);
        daoContentSubscriber.subscribe(messages.getProperty("weapons.ranged.query.specific"), this::getRangedWeapon);
        daoContentSubscriber.subscribe(messages.getProperty("weapons.ranged.save"), this::saveRangedWeapon);
        daoContentSubscriber.subscribe(messages.getProperty("weapons.ranged.remove"), this::removeRangedWeapon);
        daoContentSubscriber.subscribe(messages.getProperty("armor.baseType.selected"), this::getARBaseTypeByName);
        daoContentSubscriber.subscribe(messages.getProperty("armor.query.specific"), this::getArmor);
        daoContentSubscriber.subscribe(messages.getProperty("armor.save"), this::saveArmor);
        daoContentSubscriber.subscribe(messages.getProperty("armor.remove"), this::removeArmor);
        daoContentSubscriber.subscribe(messages.getProperty("ammunition.baseType.selected"), this::getAMBaseTypeByName);
        daoContentSubscriber.subscribe(messages.getProperty("ammunition.query.specific"), this::getAmmunition);
        daoContentSubscriber.subscribe(messages.getProperty("ammunition.save"), this::saveAmmunition);
        daoContentSubscriber.subscribe(messages.getProperty("ammunition.remove"), this::removeAmmunition);
        daoContentSubscriber.subscribe(messages.getProperty("skills.query.specific"), this::getSkill);
        daoContentSubscriber.subscribe(messages.getProperty("skills.save"), this::saveSkill);
        daoContentSubscriber.subscribe(messages.getProperty("skills.remove"), this::removeSkill);
        daoContentSubscriber.subscribe(messages.getProperty("professions.class.query.specific"), this::getProfessionClass);
        daoContentSubscriber.subscribe(messages.getProperty("professions.class.save"), this::saveProfessionClass);
        daoContentSubscriber.subscribe(messages.getProperty("professions.class.remove"), this::removeProfessionClass);
        daoContentSubscriber.subscribe(messages.getProperty("professions.query.specific"), this::getProfession);
        daoContentSubscriber.subscribe(messages.getProperty("professions.save"), this::saveProfession);
        daoContentSubscriber.subscribe(messages.getProperty("professions.remove"), this::removeProfession);
        daoContentSubscriber.subscribe(messages.getProperty("race.query.specific"), this::getRace);
        daoContentSubscriber.subscribe(messages.getProperty("race.save"), this::saveRace);
        daoContentSubscriber.subscribe(messages.getProperty("race.remove"), this::removeRace);
        daoContentSubscriber.subscribe(messages.getProperty("magic.schools.query.specific"), this::getMagicSchool);
        daoContentSubscriber.subscribe(messages.getProperty("magic.schools.save"), this::saveMagicSchool);
        daoContentSubscriber.subscribe(messages.getProperty("magic.schools.remove"), this::removeMagicSchool);
        daoContentSubscriber.subscribe(messages.getProperty("magic.spells.query.specific"), this::getSpell);
        daoContentSubscriber.subscribe(messages.getProperty("magic.spells.save"), this::saveSpell);
        daoContentSubscriber.subscribe(messages.getProperty("magic.spells.remove"), this::removeSpell);
        daoContentSubscriber.subscribe(messages.getProperty("player.query.specific"), this::getPlayer);
        daoContentSubscriber.subscribe(messages.getProperty("player.save"), this::savePlayer);
        daoContentSubscriber.subscribe(messages.getProperty("player.remove"), this::removePlayer);
    }
}
