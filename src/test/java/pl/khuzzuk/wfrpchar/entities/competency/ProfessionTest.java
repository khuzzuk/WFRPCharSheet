package pl.khuzzuk.wfrpchar.entities.competency;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Set;

public class ProfessionTest {
    @Test
    public void twoWaysParsing() throws Exception {
        String line = "Aptekarz;;Warzenie trucizn;;1,HEALTH|10,DEXTERITY|10,INTELLIGENCE";
        Skill skill = new Skill();
        skill.setName("Warzenie trucizn");
        Set<Skill> skills = new HashSet<>();
        skills.add(skill);
        Profession profession = Profession.build(line.split(";"), skills, null);
        Assert.assertTrue(profession.toCsv().startsWith(line.substring(0, 28)));
        Assert.assertNotNull(profession.getDeterminants());
        Assert.assertEquals(profession.getDeterminants().size(), 3);
    }
}