package pl.khuzzuk.wfrpchar.entities.characters;

import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;
import pl.khuzzuk.wfrpchar.db.DAO;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayerTest {
    @Test
    public void twoWaysParsing() throws Exception {
        DAO dao = mock(DAO.class);
        when(dao.getEntities(Mockito.any())).thenReturn(null);
        when(dao.getEntitiesAsList(Mockito.any())).thenReturn(null);
        when(dao.getEntity(Mockito.any(), Mockito.any(String.class))).thenReturn(null);
        String line = "name;;;;;;MALE;25;185;85;BLUE;BLONDE;desc;10,BATTLE;;;;;1|0|0;;hist;birth;father;mother;siblings";
        Player player = Player.fromCsv(line.split(";"), dao);
        Assert.assertEquals(player.toCsv(), line);
    }
}