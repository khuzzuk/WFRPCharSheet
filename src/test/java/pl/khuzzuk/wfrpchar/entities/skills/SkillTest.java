package pl.khuzzuk.wfrpchar.entities.skills;

import org.testng.Assert;
import org.testng.annotations.Test;
import pl.khuzzuk.wfrpchar.entities.competency.Skill;

public class SkillTest {
    @Test
    public void twoWaysParse() throws Exception {
        String line = "Akrobatyka;Pozwala na specjalne akcje związane ze zręcznością;10,DEXTERITY";
        Skill skill = Skill.fromCsv(line.split(";"));
        Assert.assertEquals(skill.toCsv(), line);
        line = "Aktorstwo;;";
        skill = Skill.fromCsv(line.split(";"));
        Assert.assertEquals(skill.toCsv(), line);
    }
}