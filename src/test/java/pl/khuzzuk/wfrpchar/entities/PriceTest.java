package pl.khuzzuk.wfrpchar.entities;

import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class PriceTest {
    @Test
    public void addPrices() throws Exception {
        Price first = new Price(1, 1, 1);
        Price second = new Price(1, 1, 1);
        Price expected = new Price(2, 2, 2);
        Price actualPrice = first.add(second);
        assertTrue(actualPrice.equals(expected));
    }
    @Test
    public void addPricesOverlaps() throws Exception {
        Price first = new Price(9, 9, 9);
        Price second = new Price(9, 9, 9);
        Price expected = new Price(19, 9, 8);
        Price actualPrice = first.add(second);
        assertTrue(actualPrice.equals(expected));
    }

    @Test
    public void multiplyPrice() throws Exception {
        Price price = new Price(1, 1, 1);
        Price expectedPrice = new Price(2, 2, 2);
        Price actual = price.multiply(2);
        assertTrue(actual.equals(expectedPrice));
    }
    @Test
    public void multiplyPriceOverlap() throws Exception {
        Price price = new Price(8, 8, 8);
        Price expectedPrice = new Price(17, 7, 6);
        Price actual = price.multiply(2);
        Assert.assertEquals(actual, expectedPrice);
    }
}
