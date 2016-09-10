package pl.khuzzuk.wfrpchar.entities.items;

import org.testng.annotations.Test;
import pl.khuzzuk.wfrpchar.entities.determinants.DeterminantFactory;
import pl.khuzzuk.wfrpchar.entities.items.types.BastardWeaponType;
import pl.khuzzuk.wfrpchar.entities.items.types.Item;

import static org.assertj.core.api.Assertions.assertThat;

public class AbstractWeaponParserTest {
    @Test(groups = "fast")
    public void parseBastardWeapon() throws Exception {
        WeaponParser parser = new WeaponParser(new DeterminantFactory());
        String data = "Półtorak;1.6;0|8;COMMON;Może beć używano jako broń jednoręczna;2;WEAPON;BASTARD;|||Półtorakiem;10,PARRY;Półtorak;K8;1;1;10,PARRY";
        Item equipment = parser.parseEquipment(data.split(";"));
        assertThat(equipment).isExactlyInstanceOf(BastardWeaponType.class);
        assertThat(equipment).hasFieldOrPropertyWithValue("oneHandedStrength", 1);
    }

    @Test(groups = "fast")
    public void twoWaysParsingAxe() throws Exception {
        String line = "Topór;0.85;0|7|0;COMMON;Tarcza -1 PP;0;WEAPON;ONE_HAND;|||Toporem;-10,OPPONENT_PARRY|-10,PARRY;Topór;K6;1";
        WeaponParser weaponParser = new WeaponParser(new DeterminantFactory());
        Item equipment = weaponParser.parseEquipment(line.split(";"));
        assertThat(equipment.toCsv()).isEqualTo(line);
        line = "Półtorak;1.6;0|8|0;COMMON;Może beć używano jako broń jednoręczna;2;WEAPON;BASTARD;|||Półtorakiem;10,PARRY;Półtorak;K8;1;1;10,PARRY";
        equipment = weaponParser.parseEquipment(line.split(";"));
        assertThat(equipment.toCsv()).isEqualTo(line);
    }

    @Test
    public void twoWaysParsingBow() throws Exception {
        String line = "Krótki łuk;0.75;7|0|0;COMMON;;0;RANGED_WEAPON;TWO_HANDS;|||Krótkim łukiem;;łuk;14;32;150;WITH_SHOOTING";
        WeaponParser parser = new WeaponParser(new DeterminantFactory());
        Item equipment = parser.parseEquipment(line.split(";"));
        assertThat(equipment.toCsv()).isEqualTo(line);
    }

    @Test
    public void twoWaysParsingHelm() throws Exception {
        String line = "Skórzany kaptur;0.1;0|2|5;COMMON;;1;ARMOR;HEAD;|||Skórzanym kapturem;;LEATHER";
        WeaponParser parser = new WeaponParser(new DeterminantFactory());
        Item equipment = parser.parseEquipment(line.split(";"));
        assertThat(equipment.toCsv()).isEqualTo(line);
    }
}