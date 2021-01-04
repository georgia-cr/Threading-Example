// imports JUnit 4 modules
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@Category({UnitTests.class})
public class TestCard {
    /*
    each Card is checked by CardGame when read from the text file,
    so no need to check if accepts negative or non-numerical values
     */
    // sets variable types
    private int i1;
    private int i2;
    private Card card1;
    private Card card2;

    @Before
    public void setUpTest(){
        // initialises variables
        i1 = 0;
        i2 = 999999;
        // creates new Card objects using the numbers i1, i2
        card1 = new Card(i1);
        card2 = new Card(i2);
    }

    @Test
    public void testLowerBound(){
        // asserts that the expected card number will be 0
        assertThat("card no does not match input", card1.getCardNum(), is(0));
    }

    @Test
    public void testHighBound(){
        // asserts that the expected card number will be 999999
        assertThat("card no does not match input", card2.getCardNum(), is(999999));
    }

    @After
    public void tearDownTest(){
        // tears down test by setting integers to 0, and objects to null
        i1 = 0;
        i2 = 0;
        card1 = null;
        card2 = null;
    }
}
