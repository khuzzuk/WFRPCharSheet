package pl.khuzzuk.wfrpchar.entities.items;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import pl.khuzzuk.wfrpchar.entities.determinants.DeterminantFactory;
import pl.khuzzuk.wfrpchar.entities.items.types.*;
import pl.khuzzuk.wfrpchar.entities.items.usable.AbstractHandWeapon;
import pl.khuzzuk.wfrpchar.entities.items.usable.Armor;
import pl.khuzzuk.wfrpchar.entities.items.usable.Gun;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class AbstractWeaponParserTest {

    private WeaponParser parser;
    @Mock
    private ResourceType resource;
    @Mock
    private ResourceType wood;

    @BeforeSuite
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        parser = new WeaponParser(new DeterminantFactory());
        when(resource.getName()).thenReturn("Stal");
        when(wood.getName()).thenReturn("Drewno");
    }

    @Test(groups = "fast")
    public void parseBastardWeapon() throws Exception {
        String data = "Półtorak;1.6;0|8;COMMON;Może beć używano jako broń jednoręczna;2;WEAPON;BASTARD;|||Półtorakiem;10,PARRY;Półtorak;K8;1;1;10,PARRY";
        Item equipment = parser.parseEquipment(data.split(";"));
        assertThat(equipment).isExactlyInstanceOf(BastardWeaponType.class);
        assertThat(equipment).hasFieldOrPropertyWithValue("oneHandedStrength", 1);
    }

    @Test(groups = "fast")
    public void twoWaysParsingAxe() throws Exception {
        String line = "Topór;0.85;0|7|0;COMMON;Tarcza -1 PP;0;WEAPON;ONE_HAND;|||Toporem;-10,OPPONENT_PARRY|-10,PARRY;Topór;K6;1";
        OneHandedWeaponType equipment = (OneHandedWeaponType) parser.parseEquipment(line.split(";"));
        assertThat(equipment.getDeterminants().size()).isEqualTo(2);
        line = "Półtorak;1.6;0|8|0;COMMON;Może beć używano jako broń jednoręczna;2;WEAPON;BASTARD;|||Półtorakiem;10,PARRY;Półtorak;K8;1;1;10,PARRY";
        Item bastard = parser.parseEquipment(line.split(";"));
        assertThat(bastard.toCsv()).isEqualTo(line);
    }

    @Test
    public void twoWaysParsingBow() throws Exception {
        String line = "Krótki łuk;0.75;7|0|0;COMMON;;0;RANGED_WEAPON;TWO_HANDS;|||Krótkim łukiem;;łuk;14;32;150;WITH_SHOOTING";
        Item equipment = parser.parseEquipment(line.split(";"));
        assertThat(equipment.toCsv()).isEqualTo(line);
    }

    @Test
    public void twoWaysParsingHelm() throws Exception {
        String line = "Skórzany kaptur;0.1;0|2|5;COMMON;;1;ARMOR;HEAD;|||Skórzanym kapturem;;LEATHER";
        Item equipment = parser.parseEquipment(line.split(";"));
        assertThat(equipment.toCsv()).isEqualTo(line);
    }

    @Test(groups = "fast")
    public void twoWaysResourceParse() throws Exception {
        String line = "Drewno;33;25;WOOD;COMMON;";
        ResourceType resourceType = ResourceType.getFromCsv(line.split(";"));
        assertThat(resourceType.toCsv()).isEqualTo(line);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void twoWaysParsingSteelSword() throws Exception {
        String line = "Stalowy mistrzowski miecz;0|0|0;COMMON;;Miecz;Stal;Stal;10,BATTLE;;;";
        String baseTypeLine = "Miecz;0.75;0|5;COMMON;;0;WEAPON;ONE_HAND;|||Mieczem;;Miecz;K6;1";
        WhiteWeaponType base = (WhiteWeaponType) parser.parseEquipment(baseTypeLine.split(";"));
        ParserBag bag = new ParserBag(base, resource, resource);
        AbstractHandWeapon<? extends WhiteWeaponType> result = parser.parseHandWeapon(line.split(";"), bag);
        assertThat(result.toCsv()).isEqualTo(line);
    }

    @Test(groups = "fast")
    public void twoWaysGunParse() throws Exception {
        String line = "Mistrzowski krótki łuk;2|0|0;UNCOMMON;;Krótki łuk;Drewno;Drewno;10,SHOOTING";
        String baseTypeLine = "Krótki łuk;0.75;7|0|0;COMMON;;0;RANGED_WEAPON;TWO_HANDS;|||Krótkim łukiem;;łuk;14;32;150;WITH_SHOOTING";
        String resourceLine = "Drewno;33;25;WOOD;COMMON;";
        RangedWeaponType type = (RangedWeaponType) parser.parseEquipment(baseTypeLine.split(";"));
        ResourceType resource = ResourceType.getFromCsv(resourceLine.split(";"));
        ParserBag<RangedWeaponType> bag = new ParserBag<>(type, resource, resource);
        Gun gun = parser.parseGun(line.split(";"), bag);
        assertThat(gun.toCsv()).isEqualTo(line);
    }

    @Test(groups = "fast")
    public void twoWaysArmorParse() throws Exception {
        String line = "Mistrzowski skórzany kaptur;1|0|0;COMMON;;Skórzany kaptur;Skóra;Skóra;1,DURABILITY";
        String baseTypeLine = "Skórzany kaptur;0.1;0|2|5;COMMON;;1;ARMOR;HEAD;|||Skórzanym kapturem;;LEATHER";
        String resourceLine = "Skóra;25;20;TEXTILE;COMMON;";
        ArmorType base = (ArmorType) parser.parseEquipment(baseTypeLine.split(";"));
        ResourceType resourceType = ResourceType.getFromCsv(resourceLine.split(";"));
        ParserBag<ArmorType> bag = new ParserBag<>(base, resourceType, resourceType);
        Armor armor = parser.parseArmor(line.split(";"), bag);
        assertThat(armor.toCsv()).isEqualTo(line);
    }
}