// imports JUnit 4 modules
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@Category({UnitTests.class})
public class TestGameFlag {
    // sets variable type
    private GameFlag gameFlag;

    @Before
    public void setUpTest(){
        // sets up test by creating new instance of GameFlag
        gameFlag = new GameFlag();
    }

    @Test
    public void testGame(){
        // asserts that default value is false
        assertFalse(gameFlag.getGame());
        // changes value to true
        gameFlag.setGame();
        // asserts value is true
        assert(gameFlag.getGame());
    }

    @Test
    public void testPlayerNumWon(){
        // asserts that default value is 0
        assertEquals(0, gameFlag.getPlayerNumWon());
        // sets value to 1
        gameFlag.setPlayerNumWon(1);
        // asserts value is changed to 1
        assertEquals(1, gameFlag.getPlayerNumWon());
    }

    @After
    public void tearDownTest(){
        // tears down test by setting GameFlag object to null
        gameFlag = null;
    }
}