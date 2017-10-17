package pl.khuzzuk.wfrpchar.db;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import pl.khuzzuk.messaging.Bus;
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

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;
import java.util.Properties;

import static pl.khuzzuk.wfrpchar.db.LineParser.getFrom;

@NoArgsConstructor
@Component
public class DAOReactor {
    private DAO dao;
    @Inject
    private WeaponParser weaponParser;
    @Inject
    private DBInitializer dbInitializer;
    @Inject
    private DBDumper dbDumper;
    @Inject
    private Bus bus;
    @Inject
    @Named("messages")
    private Properties messages;


    private void getAllWhiteWeaponsTypes() {
        bus.send(messages.getProperty("whiteWeapons.result"), dao.getAllEntities(WhiteWeaponType.class));
    }

    private void saveItem(String line) {
        Item i = weaponParser.parseEquipment(line.split(";"));
        if (i instanceof WhiteWeaponType) {
            dao.saveEntity(WhiteWeaponType.class, (WhiteWeaponType) i);
            bus.send(messages.getProperty("whiteWeapons.query"));
        } else if (i instanceof RangedWeaponType) {
            dao.saveEntity(RangedWeaponType.class, (RangedWeaponType) i);
            bus.send(messages.getProperty("rangedWeapons.query"));
        } else if (i instanceof ArmorType) {
            dao.saveEntity(ArmorType.class, (ArmorType) i);
            bus.send(messages.getProperty("armorTypes.query"));
        } else if (i instanceof MiscItem) {
            dao.saveEntity(MiscItem.class, (MiscItem) i);
            bus.send(messages.getProperty("miscItemTypes.query"));
        } else if (i instanceof AmmunitionType) {
            dao.saveEntity(AmmunitionType.class, (AmmunitionType) i);
            bus.send(messages.getProperty("ammo.type.query"));
        }
    }

    private void saveResourceType(String line) {
        dao.saveEntity(ResourceType.class, ResourceType.getFromCsv(line.split(";")));
        bus.send(messages.getProperty("resource.type.query"));
    }

    private void saveHandWeapon(String line) {
        String[] fields = line.split(";");
        ParserBag<WhiteWeaponType> bag = getParserBag(WhiteWeaponType.class, fields);
        dao.saveEntity(AbstractHandWeapon.class, weaponParser.parseHandWeapon(fields, bag));
        bus.send(messages.getProperty("weapons.hand.query"));
    }

    private void saveRangedWeapon(String line) {
        String[] fields = line.split(";");
        ParserBag<RangedWeaponType> bag = getParserBag(RangedWeaponType.class, fields);
        Gun gun = weaponParser.parseGun(fields, bag);
        dao.saveEntity(Gun.class, gun);
        bus.send(messages.getProperty("weapons.ranged.query"));
    }

    private void saveArmor(String line) {
        String[] fields = line.split(";");
        Armor armor = weaponParser.parseArmor(fields, getParserBag(ArmorType.class, fields));
        dao.saveEntity(Armor.class, armor);
        bus.send(messages.getProperty("armor.query"));
    }

    private void saveAmmunition(String line) {
        String[] fields = line.split(";");
        Ammunition ammunition = weaponParser.parseAmmunition(fields,
                getParserBag(AmmunitionType.class, fields));
        dao.saveEntity(Ammunition.class, ammunition);
        bus.send(messages.getProperty("ammunition.query"));
    }

    private <T extends FightingEquipment> ParserBag<T> getParserBag(Class<T> entityType, String[] fields) {
        return new ParserBag<>(
                dao.getEntity(entityType, fields[4]),
                dao.getEntity(ResourceType.class, fields[5]),
                dao.getEntity(ResourceType.class, fields[6]));
    }

    private void saveSkill(String line) {
        dao.saveEntity(Skill.class, Skill.fromCsv(line.split(";")));
        bus.send(messages.getProperty("skills.query"));
    }

    private void saveProfessionClass(String line) {
        dao.saveEntity(ProfessionClass.class, ProfessionClass.fromCsv(line.split(";"), dao));
        bus.send(messages.getProperty("professions.class.query"));
    }

    private void saveProfession(String line) {
        dao.saveEntity(Profession.class, Profession.build(line.split(";"), dao));
        bus.send(messages.getProperty("professions.query"));
    }

    private void saveRace(String line) {
        dao.saveEntity(Race.class, Race.fromCsv(line.split(";"), dao));
        bus.send(messages.getProperty("race.query"));
    }

    private void saveMagicSchool(String line) {
        dao.saveEntity(MagicSchool.class, MagicSchool.getFromCsv(line.split(";")));
        bus.send(messages.getProperty("magic.schools.query"));
    }

    private void saveSpell(String line) {
        dao.saveEntity(Spell.class, Spell.fromCsv(line.split(";"), dao));
        bus.send(messages.getProperty("magic.spells.query"));
    }

    private void savePlayer(String line) {
        dao.saveEntity(Player.class, Player.fromCsv(getFrom(line, 24), dao));
        bus.send(messages.getProperty("player.query"));
    }

    private void removeMiscItem(String name) {
        dao.removeEntity(MiscItem.class, name);
        bus.send(messages.getProperty("miscItemTypes.query"));
    }

    private void removeWhiteWeaponType(String name) {
        dao.removeEntity(WhiteWeaponType.class, name);
        bus.send(messages.getProperty("whiteWeapons.query"));
    }

    private void removeRangedWeaponType(String name) {
        dao.removeEntity(RangedWeaponType.class, name);
        bus.send(messages.getProperty("rangedWeapons.query"));
    }

    private void removeArmorType(String name) {
        dao.removeEntity(ArmorType.class, name);
        bus.send(messages.getProperty("armorTypes.query"));
    }

    private void removeAmmunitionType(String name) {
        dao.removeEntity(AmmunitionType.class, name);
        bus.send(messages.getProperty("ammo.type.query"));
    }

    private void removeHandWeapon(String name) {
        dao.removeEntity(AbstractHandWeapon.class, name);
        bus.send(messages.getProperty("weapons.hand.query"));
    }

    private void removeRangedWeapon(String name) {
        dao.removeEntity(Gun.class, name);
        bus.send(messages.getProperty("weapons.ranged.query"));
    }

    private void removeArmor(String name) {
        dao.removeEntity(Armor.class, name);
        bus.send(messages.getProperty("armor.query"));
    }

    private void removeAmmunition(String name) {
        dao.removeEntity(Ammunition.class, name);
        bus.send(messages.getProperty("ammunition.query"));
    }

    private void removeResourceType(String name) {
        dao.removeEntity(ResourceType.class, name);
        bus.send(messages.getProperty("resource.type.query"));
    }

    private void removeSkill(String name) {
        dao.removeEntity(Skill.class, name);
        bus.send(messages.getProperty("skills.query"));
    }

    private void removeProfessionClass(String name) {
        dao.removeEntity(ProfessionClass.class, name);
        bus.send(messages.getProperty("professions.class.query"));
    }

    private void removeProfession(String name) {
        dao.removeEntity(Profession.class, name);
        bus.send(messages.getProperty("professions.query"));
    }

    private void removeRace(String name) {
        dao.removeEntity(Race.class, name);
        bus.send(messages.getProperty("race.query"));
    }

    private void removeMagicSchool(String name) {
        dao.removeEntity(MagicSchool.class, name);
        bus.send(messages.getProperty("magic.schools.query"));
    }

    private void removeSpell(String name) {
        dao.removeEntity(Spell.class, name);
        bus.send(messages.getProperty("magic.spells.query"));
    }

    private void removePlayer(String name) {
        dao.removeEntity(Player.class, name);
        bus.send(messages.getProperty("player.query"));
    }

    private void getAllMiscItemsTypes() {
        bus.send(messages.getProperty("miscItemTypes.result"), dao.getAllEntities(MiscItem.class));
    }

    private void getAllRangedWeaponTypes() {
        bus.send(messages.getProperty("rangedWeapons.result"), dao.getAllEntities(RangedWeaponType.class));
    }

    private void getAllAmmunitionTypes() {
        bus.send(messages.getProperty("ammo.type.result"), dao.getAllEntities(AmmunitionType.class));
    }

    private void getAllArmorTypes() {
        bus.send(messages.getProperty("armorTypes.result"), dao.getAllEntities(ArmorType.class));
    }

    private void getAllResourceTypes() {
        bus.send(messages.getProperty("resource.type.result"), dao.getAllEntities(ResourceType.class));
    }

    private void getAllHandWeapons() {
        bus.send(messages.getProperty("weapons.hand.result"), dao.getAllEntities(AbstractHandWeapon.class));
    }

    private void getAllRangedWeapons() {
        bus.send(messages.getProperty("weapons.ranged.result"), dao.getAllEntities(Gun.class));
    }

    private void getAllArmors() {
        bus.send(messages.getProperty("armor.result"), dao.getAllEntities(Armor.class));
    }

    private void getAllAmmunitions() {
        bus.send(messages.getProperty("ammunition.result"), dao.getAllEntities(Ammunition.class));
    }

    private void getAllCharacters() {
        bus.send(messages.getProperty("character.result"), dao.getAllEntities(Character.class));
    }

    private void getAllSkills() {
        bus.send(messages.getProperty("skills.result"), dao.getAllEntities(Skill.class));
    }

    private void getAllProfessionClasses() {
        bus.send(messages.getProperty("professions.class.result"), dao.getAllEntities(ProfessionClass.class));
    }

    private void getAllProfessions() {
        bus.send(messages.getProperty("professions.result"), dao.getAllEntities(Profession.class));
    }

    private void getAllRaces() {
        bus.send(messages.getProperty("race.result"), dao.getAllEntities(Race.class));
    }

    private void getAllMagicSchools() {
        bus.send(messages.getProperty("magic.schools.result"), dao.getAllEntities(MagicSchool.class));
    }

    private void getAllSpells() {
        bus.send(messages.getProperty("magic.spells.result"), dao.getAllEntities(Spell.class));
    }

    private void getAllPlayers() {
        bus.send(messages.getProperty("player.result"), dao.getAllEntities(Player.class));
    }

    private void getAllWWBaseType() {
        bus.send(messages.getProperty("weapons.hand.baseType.allTypesList"), dao.getAllEntities(WhiteWeaponType.class));
    }

    private void getAllRangedBaseTypes() {
        bus.send(messages.getProperty("weapons.ranged.baseType.allTypesList"), dao.getAllEntities(RangedWeaponType.class));
    }

    private void getAllArmorBaseTypes() {
        bus.send(messages.getProperty("armor.baseType.allTypesList"), dao.getAllEntities(ArmorType.class));
    }

    private void getWhiteWeaponByName(String name) {
        bus.send(messages.getProperty("whiteWeapons.result.specific"), dao.getEntity(WhiteWeaponType.class, name));
    }

    private void getRangedWeaponTypeByName(String name) {
        bus.send(messages.getProperty("rangedWeapons.result.specific"), dao.getEntity(RangedWeaponType.class, name));
    }

    private void getMiscItemTypeByName(String name) {
        bus.send(messages.getProperty("miscItemTypes.result.specific"), dao.getEntity(MiscItem.class, name));
    }

    private void getArmorTypeByName(String name) {
        bus.send(messages.getProperty("armorTypes.result.specific"), dao.getEntity(ArmorType.class, name));
    }

    private void getAmmunitionTypeByName(String name) {
        bus.send(messages.getProperty("ammo.type.result.specific"), dao.getEntity(AmmunitionType.class, name));
    }

    private void getResourceType(String name) {
        bus.send(messages.getProperty("resource.type.result.specific"), dao.getEntity(ResourceType.class, name));
    }

    private void getHandWeapon(String name) {
        bus.send(messages.getProperty("weapons.hand.result.specific"), dao.getEntity(AbstractHandWeapon.class, name));
    }

    private void getRangedWeapon(String name) {
        bus.send(messages.getProperty("weapons.ranged.result.specific"), dao.getEntity(Gun.class, name));
    }

    private void getArmor(String name) {
        bus.send(messages.getProperty("armor.result.specific"), dao.getEntity(Armor.class, name));
    }

    private void getAmmunition(String name) {
        bus.send(messages.getProperty("ammunition.result.specific"), dao.getEntity(Ammunition.class, name));
    }

    private void getSkill(String name) {
        bus.send(messages.getProperty("skills.result.specific"), dao.getEntity(Skill.class, name));
    }

    private void getProfessionClass(String name) {
        bus.send(messages.getProperty("professions.class.result.specific"), dao.getEntity(ProfessionClass.class, name));
    }

    private void getProfession(String name) {
        bus.send(messages.getProperty("professions.result.specific"), dao.getEntity(Profession.class, name));
    }

    private void getRace(String name) {
        bus.send(messages.getProperty("race.result.specific"), dao.getEntity(Race.class, name));
    }

    private void getMagicSchool(String name) {
        bus.send(messages.getProperty("magic.schools.result.specific"), dao.getEntity(MagicSchool.class, name));
    }

    private void getSpell(String name) {
        bus.send(messages.getProperty("magic.spells.result.specific"), dao.getEntity(Spell.class, name));
    }

    private void getPlayer(String name) {
        bus.send(messages.getProperty("player.result.specific"), dao.getEntity(Player.class, name));
    }

    private void getWWBaseTypeByName(String name) {
        bus.send(messages.getProperty("weapons.hand.baseType.choice"), dao.getEntity(WhiteWeaponType.class, name));
    }

    private void getRWBaseTypeByName(String name) {
        bus.send(messages.getProperty("weapons.ranged.baseType.choice"), dao.getEntity(RangedWeaponType.class, name));
    }

    private void getARBaseTypeByName(String name) {
        bus.send(messages.getProperty("armor.baseType.choice"), dao.getEntity(RangedWeaponType.class, name));
    }

    private void getAMBaseTypeByName(String name) {
        bus.send(messages.getProperty("ammunition.baseType.choice"), dao.getEntity(AmmunitionType.class, name));
    }

    private void getSkillsToChoose() {
        bus.send(messages.getProperty("professions.skills.allTypesList"), dao.getAllEntities(Skill.class));
    }

    private void getSkillsToProfessionClassChooser() {
        bus.send(messages.getProperty("professions.class.skills.allTypesList"), dao.getAllEntities(Skill.class));
    }

    private void getSkillsForRaceChooser() {
        bus.send(messages.getProperty("race.skills.allTypesList"), dao.getAllEntities(Skill.class));
    }

    private void getProfessionsToNextChoose() {
        bus.send(messages.getProperty("professions.next.allTypesList"), dao.getAllEntities(Profession.class));
    }

    private void getSchoolForSpell() {
        bus.send(messages.getProperty("magic.spells.school.allTypesList"), dao.getAllEntities(MagicSchool.class));
    }

    private void getIngredientsForSpell() {
        bus.send(messages.getProperty("magic.spells.ingredients.allTypesList"), dao.getAllEntities(MiscItem.class));
    }

    private void getProfessionsForPlayerChoice() {
        bus.send(messages.getProperty("player.profession.allTypesList"), dao.getAllEntities(Profession.class));
    }

    private void getHandWeaponsForPlayerChoice() {
        bus.send(messages.getProperty("player.weapons.white.allTypesList"), dao.getAllEntities(AbstractHandWeapon.class));
    }

    private void getRangedWeaponsForPlayerChoice() {
        bus.send(messages.getProperty("player.weapons.ranged.allTypesList"), dao.getAllEntities(Gun.class));
    }

    private void getArmorsForPlayerChoice() {
        bus.send(messages.getProperty("player.armors.allTypesList"), dao.getAllEntities(Armor.class));
    }

    private void getItemsForPlayerChoice() {
        bus.send(messages.getProperty("player.equipment.allTypesList"), dao.getAllEntities(MiscItem.class));
    }

    private void getSkillsForPlayerChoice() {
        bus.send(messages.getProperty("player.skills.allTypesList"), dao.getAllEntities(Skill.class));
    }

    private void getSpellsForPlayerChoice() {
        bus.send(messages.getProperty("player.spells.allTypesList"), dao.getAllEntities(Spell.class));
    }

    private void resetDB() {
        dao.getManager().resetDB(dao);
        bus.send(messages.getProperty("whiteWeapons.query"));
        bus.send(messages.getProperty("rangedWeapons.query"));
        bus.send(messages.getProperty("armorTypes.query"));
        bus.send(messages.getProperty("ammo.type.query"));
        bus.send(messages.getProperty("miscItemTypes.query"));
        bus.send(messages.getProperty("weapons.hand.query"));
        bus.send(messages.getProperty("weapons.ranged.query"));
        bus.send(messages.getProperty("armor.query"));
        bus.send(messages.getProperty("ammunition.query"));
        bus.send(messages.getProperty("skills.query"));
        bus.send(messages.getProperty("professions.class.query"));
        bus.send(messages.getProperty("professions.query"));
        bus.send(messages.getProperty("race.query"));
        bus.send(messages.getProperty("magic.schools.query"));
        bus.send(messages.getProperty("magic.spells.query"));
    }

    @Inject
    public void setDao(@Manager DAO dao) {
        this.dao = dao;
    }

    @PostConstruct
    private void setReactors() {
        bus.setReaction(messages.getProperty("database.reset"), this::resetDB);
        bus.setReaction(messages.getProperty("database.save"), dbDumper::saveToFiles);

        //get all queries
        bus.<Class, Collection>setResponse(messages.getProperty("database.getAll"), dao::getAllEntities);
        bus.setReaction(messages.getProperty("miscItemTypes.query"), this::getAllMiscItemsTypes);
        bus.setReaction(messages.getProperty("whiteWeapons.query"), this::getAllWhiteWeaponsTypes);
        bus.setReaction(messages.getProperty("rangedWeapons.query"), this::getAllRangedWeaponTypes);
        bus.setReaction(messages.getProperty("armorTypes.query"), this::getAllArmorTypes);
        bus.setReaction(messages.getProperty("ammo.type.query"), this::getAllAmmunitionTypes);
        bus.setReaction(messages.getProperty("resource.type.query"), this::getAllResourceTypes);
        bus.setReaction(messages.getProperty("weapons.hand.query"), this::getAllHandWeapons);
        bus.setReaction(messages.getProperty("weapons.ranged.query"), this::getAllRangedWeapons);
        bus.setReaction(messages.getProperty("armor.query"), this::getAllArmors);
        bus.setReaction(messages.getProperty("ammunition.query"), this::getAllAmmunitions);
        bus.setReaction(messages.getProperty("character.query"), this::getAllCharacters);
        bus.setReaction(messages.getProperty("skills.query"), this::getAllSkills);
        bus.setReaction(messages.getProperty("professions.class.query"), this::getAllProfessionClasses);
        bus.setReaction(messages.getProperty("professions.query"), this::getAllProfessions);
        bus.setReaction(messages.getProperty("race.query"), this::getAllRaces);
        bus.setReaction(messages.getProperty("magic.schools.query"), this::getAllMagicSchools);
        bus.setReaction(messages.getProperty("magic.spells.query"), this::getAllSpells);
        bus.setReaction(messages.getProperty("player.query"), this::getAllPlayers);

        //get all linked entities queries
        bus.setReaction(messages.getProperty("weapons.hand.baseType.getAllTypes"), this::getAllWWBaseType);
        bus.setReaction(messages.getProperty("weapons.ranged.baseType.getAllTypes"), this::getAllRangedBaseTypes);
        bus.setReaction(messages.getProperty("armor.baseType.getAllTypes"), this::getAllArmorBaseTypes);
        bus.setReaction(messages.getProperty("professions.class.skills.getAllTypes"), this::getSkillsToProfessionClassChooser);
        bus.setReaction(messages.getProperty("professions.skills.getAllTypes"), this::getSkillsToChoose);
        bus.setReaction(messages.getProperty("professions.next.getAllTypes"), this::getProfessionsToNextChoose);
        bus.setReaction(messages.getProperty("race.skills.getAllTypes"), this::getSkillsForRaceChooser);
        bus.setReaction(messages.getProperty("magic.spells.school.getAllTypes"), this::getSchoolForSpell);
        bus.setReaction(messages.getProperty("magic.spells.ingredients.getAllTypes"), this::getIngredientsForSpell);
        bus.setReaction(messages.getProperty("player.profession.getAllTypes"), this::getProfessionsForPlayerChoice);
        bus.setReaction(messages.getProperty("player.weapons.white.getAllTypes"), this::getHandWeaponsForPlayerChoice);
        bus.setReaction(messages.getProperty("player.weapons.ranged.getAllTypes"), this::getRangedWeaponsForPlayerChoice);
        bus.setReaction(messages.getProperty("player.armors.getAllTypes"), this::getArmorsForPlayerChoice);
        bus.setReaction(messages.getProperty("player.equipment.getAllTypes"), this::getItemsForPlayerChoice);
        bus.setReaction(messages.getProperty("player.skills.getAllTypes"), this::getSkillsForPlayerChoice);
        bus.setReaction(messages.getProperty("player.spells.getAllTypes"), this::getSpellsForPlayerChoice);

        //content queries
        bus.setReaction(messages.getProperty("database.saveEquipment"), this::saveItem);
        bus.setReaction(messages.getProperty("miscItemTypes.query.specific"), this::getMiscItemTypeByName);
        bus.setReaction(messages.getProperty("miscItemTypes.remove"), this::removeMiscItem);
        bus.setReaction(messages.getProperty("whiteWeapons.query.specific"), this::getWhiteWeaponByName);
        bus.setReaction(messages.getProperty("whiteWeapons.remove"), this::removeWhiteWeaponType);
        bus.setReaction(messages.getProperty("rangedWeapons.query.specific"), this::getRangedWeaponTypeByName);
        bus.setReaction(messages.getProperty("rangedWeapons.remove"), this::removeRangedWeaponType);
        bus.setReaction(messages.getProperty("armorTypes.query.specific"), this::getArmorTypeByName);
        bus.setReaction(messages.getProperty("armorTypes.remove"), this::removeArmorType);
        bus.setReaction(messages.getProperty("ammo.type.query.specific"), this::getAmmunitionTypeByName);
        bus.setReaction(messages.getProperty("ammo.type.remove"), this::removeAmmunitionType);
        bus.setReaction(messages.getProperty("resource.type.query.specific"), this::getResourceType);
        bus.setReaction(messages.getProperty("resource.type.remove"), this::removeResourceType);
        bus.setReaction(messages.getProperty("resource.type.save"), this::saveResourceType);
        bus.setReaction(messages.getProperty("weapons.hand.baseType.selected"), this::getWWBaseTypeByName);
        bus.setReaction(messages.getProperty("weapons.hand.query.specific"), this::getHandWeapon);
        bus.setReaction(messages.getProperty("weapons.hand.save"), this::saveHandWeapon);
        bus.setReaction(messages.getProperty("weapons.hand.remove"), this::removeHandWeapon);
        bus.setReaction(messages.getProperty("weapons.ranged.baseType.selected"), this::getRWBaseTypeByName);
        bus.setReaction(messages.getProperty("weapons.ranged.query.specific"), this::getRangedWeapon);
        bus.setReaction(messages.getProperty("weapons.ranged.save"), this::saveRangedWeapon);
        bus.setReaction(messages.getProperty("weapons.ranged.remove"), this::removeRangedWeapon);
        bus.setReaction(messages.getProperty("armor.baseType.selected"), this::getARBaseTypeByName);
        bus.setReaction(messages.getProperty("armor.query.specific"), this::getArmor);
        bus.setReaction(messages.getProperty("armor.save"), this::saveArmor);
        bus.setReaction(messages.getProperty("armor.remove"), this::removeArmor);
        bus.setReaction(messages.getProperty("ammunition.baseType.selected"), this::getAMBaseTypeByName);
        bus.setReaction(messages.getProperty("ammunition.query.specific"), this::getAmmunition);
        bus.setReaction(messages.getProperty("ammunition.save"), this::saveAmmunition);
        bus.setReaction(messages.getProperty("ammunition.remove"), this::removeAmmunition);
        bus.setReaction(messages.getProperty("skills.query.specific"), this::getSkill);
        bus.setReaction(messages.getProperty("skills.save"), this::saveSkill);
        bus.setReaction(messages.getProperty("skills.remove"), this::removeSkill);
        bus.setReaction(messages.getProperty("professions.class.query.specific"), this::getProfessionClass);
        bus.setReaction(messages.getProperty("professions.class.save"), this::saveProfessionClass);
        bus.setReaction(messages.getProperty("professions.class.remove"), this::removeProfessionClass);
        bus.setReaction(messages.getProperty("professions.query.specific"), this::getProfession);
        bus.setReaction(messages.getProperty("professions.save"), this::saveProfession);
        bus.setReaction(messages.getProperty("professions.remove"), this::removeProfession);
        bus.setReaction(messages.getProperty("race.query.specific"), this::getRace);
        bus.setReaction(messages.getProperty("race.save"), this::saveRace);
        bus.setReaction(messages.getProperty("race.remove"), this::removeRace);
        bus.setReaction(messages.getProperty("magic.schools.query.specific"), this::getMagicSchool);
        bus.setReaction(messages.getProperty("magic.schools.save"), this::saveMagicSchool);
        bus.setReaction(messages.getProperty("magic.schools.remove"), this::removeMagicSchool);
        bus.setReaction(messages.getProperty("magic.spells.query.specific"), this::getSpell);
        bus.setReaction(messages.getProperty("magic.spells.save"), this::saveSpell);
        bus.setReaction(messages.getProperty("magic.spells.remove"), this::removeSpell);
        bus.setReaction(messages.getProperty("player.query.specific"), this::getPlayer);
        bus.setReaction(messages.getProperty("player.save"), this::savePlayer);
        bus.setReaction(messages.getProperty("player.remove"), this::removePlayer);
    }
}
