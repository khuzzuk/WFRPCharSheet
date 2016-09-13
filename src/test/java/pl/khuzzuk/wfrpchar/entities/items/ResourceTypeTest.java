package pl.khuzzuk.wfrpchar.entities.items;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class ResourceTypeTest {
    @Test
    public void twoWaysParsing() throws Exception {
        String entry = "Stal;2;125;METAL";
        ResourceType resource = ResourceType.getFromCsv(entry.split(";"));
        assertEquals(resource.toCsv(), entry);
    }
}