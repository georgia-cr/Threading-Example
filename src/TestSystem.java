// imports JUnit 4 modules used
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
// imports data structures used
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
// imports Java Reflection module
import java.lang.reflect.Method;

@Category({SystemTests.class})
public class TestSystem {
    // assigns types to variables
    private CardGame cardGame;
    private Queue<Card> cardQueue;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUpTest(){
        // initialises variables
        cardGame = new CardGame();
        cardQueue = new LinkedList<>();
        for (int i= 0; i<32; i++){
            // adds Card objects to queue
            cardQueue.add(new Card(4));
        }
        // sets the out content
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void testSystem() throws Exception{
        // ensures method is visible
        Method method = CardGame.class.getDeclaredMethod("startGame", int.class, Queue.class);
        method.setAccessible(true);
        // starts the game with 4 players and the defined cardQueue
        method.invoke(cardGame, 4, cardQueue);
        // pauses for 5 seconds so that the threads have time to execute
        TimeUnit.SECONDS.sleep(5);
        // asserts that a player has won the game
        String outContentString = outContent.toString().trim();
        // if player 1, 2, 3, or 4 has not won, the test fails
        if (!(outContentString.equals("Player 1 wins") || outContentString.equals("Player 2 wins")
                || outContentString.equals("Player 3 wins") || outContentString.equals("Player 4 wins"))){
            fail();
        }
    }

    @After
    public void tearDownTest(){
        // tears down test by setting objects to null
        // and assigning original output
        cardGame = null;
        cardQueue = null;
        System.setOut(originalOut);
    }
}
