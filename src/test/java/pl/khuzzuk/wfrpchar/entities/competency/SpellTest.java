package pl.khuzzuk.wfrpchar.entities.competency;

import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.khuzzuk.wfrpchar.db.DAO;
import pl.khuzzuk.wfrpchar.entities.Price;
import pl.khuzzuk.wfrpchar.entities.items.Accessibility;
import pl.khuzzuk.wfrpchar.entities.items.types.MiscItem;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class SpellTest {
    @Mock
    private DAO dao;

    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        Set<MiscItem> ingredients = new HashSet<>();
        ingredients.add(new MiscItem("Siarka", 0, new Price(0, 0, 0), Accessibility.UNCOMMON));
        when(dao.getEntities(Matchers.eq(MiscItem.class) ,any(String.class))).thenReturn(ingredients);
        MagicSchool school = new MagicSchool();
        school.setName("Elementalna");
        when(dao.getEntity(eq(MagicSchool.class), any())).thenReturn(school);
    }

    @Test
    public void twoWaysParsingTest() throws Exception {
        String line = "Kula ognia;ROUND;1;Elementalna;1;Siarka;Tworzy kulę ognia zadająco k5 obrażeń";
        Spell spell = Spell.fromCsv(line.split(";"), dao);
        assertEquals(spell.toCsv(), line);
    }
}