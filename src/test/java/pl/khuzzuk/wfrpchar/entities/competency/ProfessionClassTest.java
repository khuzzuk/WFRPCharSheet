package pl.khuzzuk.wfrpchar.entities.competency;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ProfessionClassTest {
    @Test(groups = "fast")
    public void twoWaysParsing() throws Exception {
        String line = "Uczony;;INTELLIGENCE;";
        ProfessionClass professionClass = ProfessionClass.fromCsv(line.split(";"));
        Assert.assertEquals(professionClass.toCsv(), line);
    }
}