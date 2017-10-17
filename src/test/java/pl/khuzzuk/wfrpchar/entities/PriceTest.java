package pl.khuzzuk.wfrpchar.entities;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class PriceTest {
    @DataProvider(name = "pricesToAdd")
    public static Object[][] pricesToAdd() {
        return new Object[][]{{
                new Price(1, 1, 1), new Price(1, 1, 1), new Price(2, 2, 2)}, {
                new Price(0, 5, 0), new Price(0, 2, 0), new Price(0, 7, 0)
        }};
    }

    @Test(dataProvider = "pricesToAdd")
    public void addPrices(Price first, Price second, Price expected) throws Exception {
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
