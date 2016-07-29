package pl.khuzzuk.wfrpchar.determinants;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class DeterminantTest {
    @Test
    public void toCSVWithExtension() throws Exception {
        Extension extension = new ProfessionExtension(10, 1);
        List<Extension> extensions = new ArrayList<>();
        extensions.add(extension);
        Determinant determinant = new PercentageDeterminant();
        determinant.setBaseValue(10);
        determinant.setType(DeterminantsType.BATTLE);
        determinant.setExtensions(extensions);
        assertEquals(determinant.toCSV(), "10,BATTLE,10-1-true");
    }

    @Test(groups = "fast")
    public void toCSVWithMultipleExtensions() throws Exception {
        List<Extension> extensions = new ArrayList<>();
        extensions.add(new MiscExtension(10, "descr"));
        extensions.add(new ProfessionExtension(10, 1));
        Determinant determinant = new PercentageDeterminant();
        determinant.setBaseValue(10);
        determinant.setType(DeterminantsType.SHOOTING);
        determinant.setExtensions(extensions);
        assertEquals(determinant.toCSV(), "10,SHOOTING,10-descr-false:10-1-true");
    }

    @Test(groups = "fast")
    public void toCsvWithoutExtensions() throws Exception {
        Determinant determinant = new PercentageDeterminant();
        determinant.setType(DeterminantsType.PARRY);
        determinant.setBaseValue(10);
        assertEquals(determinant.toCSV(), "10,PARRY");
    }
}