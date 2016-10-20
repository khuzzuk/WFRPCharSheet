package pl.khuzzuk.wfrpchar.entities.characters;

import org.testng.Assert;
import org.testng.annotations.Test;

public class AppearanceTest {
    @Test
    public void twoWaysParsing() throws Exception {
        String line = "MALE;25;185;85;BLUE;BLONDE;desc";
        Appearance appearance = Appearance.getFromCsv(line.split(";"));
        Assert.assertEquals(appearance.toCsv(), line);
    }
}