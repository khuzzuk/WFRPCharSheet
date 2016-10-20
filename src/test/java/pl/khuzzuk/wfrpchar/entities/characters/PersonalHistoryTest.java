package pl.khuzzuk.wfrpchar.entities.characters;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PersonalHistoryTest {
    @Test
    public void twoWaysParsing() throws Exception {
        String line = "history;birthplace;father;mother;siblings";
        PersonalHistory history = PersonalHistory.fromCsv(line.split(";"));
        Assert.assertEquals(history.toCsv(), line);
    }
}