package pl.khuzzuk.wfrpchar.entities;

import org.testng.Assert;
import org.testng.annotations.Test;

public class RaceTest {
    @Test(groups = "fast")
    public void twoWaysParsing() throws Exception {
        String line = "Człowiek;;2,SPEED|20,BATTLE|20,SHOOTING|1,STRENGTH|1,DURABILITY|4,HEALTH|30,INITIATIVE|1,ATTACKS|30,DEXTERITY|30,LEADER_SKILLS|30,INTELLIGENCE|20,CONTROL|30,WILL|20,CHARISMA;";
        Race race = Race.fromCsv(line.split(";"));
        Assert.assertEquals(race.getDeterminants().size(), 14);
    }
}