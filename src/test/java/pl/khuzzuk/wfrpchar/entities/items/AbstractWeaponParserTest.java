package pl.khuzzuk.wfrpchar.entities.items;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import pl.khuzzuk.wfrpchar.entities.determinants.DeterminantFactory;
import pl.khuzzuk.wfrpchar.entities.items.types.BastardWeaponType;
import pl.khuzzuk.wfrpchar.entities.items.types.Item;
import pl.khuzzuk.wfrpchar.entities.items.types.OneHandedWeaponType;
import pl.khuzzuk.wfrpchar.entities.items.types.WhiteWeaponType;
import pl.khuzzuk.wfrpchar.entities.items.usable.AbstractHandWeapon;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class AbstractWeaponParserTest {

    private WeaponParser parser;
    @Mock
    private ResourceType resource;

    @BeforeSuite
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        parser = new WeaponParser(new DeterminantFactory());
        when(resource.getName()).thenReturn("Stal");
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
}