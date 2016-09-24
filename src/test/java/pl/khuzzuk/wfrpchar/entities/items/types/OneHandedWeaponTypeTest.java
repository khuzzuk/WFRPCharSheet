package pl.khuzzuk.wfrpchar.entities.items.types;

import org.testng.annotations.Test;
import pl.khuzzuk.wfrpchar.entities.determinants.*;

import java.util.*;

import static org.testng.Assert.assertEquals;

public class OneHandedWeaponTypeTest {
    @Test
    public void checkBattleMod() throws Exception {
        OneHandedWeaponType weapon = new OneHandedWeaponType();
        Set<Determinant> determinants = new HashSet<>();

        determinants.add(getBattleFirstDeterminant());
        determinants.add(getBattleSecondDeterminant());
        weapon.determinants = determinants;
        assertEquals(weapon.getBattleMod(), 30);
    }

    private Determinant getBattleFirstDeterminant() {
        Determinant determinant = new PercentageDeterminant();
        determinant.setType(DeterminantsType.BATTLE);
        determinant.setBaseValue(10);
        return determinant;
    }
    private Determinant getBattleSecondDeterminant() {
        Determinant determinant = new PercentageDeterminant();
        determinant.setType(DeterminantsType.BATTLE);
        determinant.setBaseValue(10);
        Extension extension = new MiscExtension();
        extension.setModifier(10);
        List<Extension> extensions = new LinkedList<>();
        extensions.add(extension);
        determinant.setExtensions(extensions);
        return determinant;
    }
}
