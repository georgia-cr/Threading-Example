// imports JUnit 4 modules
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
// imports Java Reflection module
import java.lang.reflect.Method;
// imports data structure modules
import java.util.LinkedList;
import java.util.Queue;

@Category({UnitTests.class})
public class TestCardGame {
    // sets variable types
    private CardGame cardGame;
    private Queue<Card> cardQueue;

    @Before
    public void setUpTest(){
        // initialises objects
        cardGame = new CardGame();
        cardQueue = new LinkedList<>();
        for (int i= 0; i<30; i++){
            cardQueue.add(new Card(i));
        }
    }

    @Test
    public void testDistributeCards() throws Exception{
        // makes method accessible
        Method method = CardGame.class.getDeclaredMethod("createCardDecks", int.class);
        method.setAccessible(true);
        // invokes method createCardDecks on cardGame object with parameter 4 passed to method
        CardDeck[] result = (CardDeck[]) method.invoke(cardGame, 4);
        // asserts that 4 CardDeck objects are created
        assert(result.length == 4);
        // asserts that each object is different from the others
        assert(result[0] != result[1]);
        assert(result[1] != result[2]);
        assert(result[2] != result[3]);
        // makes method accessible to test
        Method method1 = CardGame.class.getDeclaredMethod("createPlayers", int.class, CardDeck[].class);
        method1.setAccessible(true);
        // invokes method createPlayers on cardGame object with parameters 4,
        // and result (CardDeck list) passed to method
        Player[] result1 = (Player[]) method1.invoke(cardGame, 4, result);
        // asserts that 4 Player objects are created
        assert(result1.length == 4);
        // asserts that each object is different from the others
        assert(result1[0] != result1[1]);
        assert(result1[1] != result1[2]);
        assert(result1[2] != result1[3]);
        // ensures method is accessible
        Method method2 = CardGame.class.getDeclaredMethod("distributeCards", int.class, CardDeck[].class, Player[].class, Queue.class);
        method2.setAccessible(true);
        // invokes method createCardDecks on cardGame object with parameters 4,
        // result (CardDeck list), result1 (Player list), and a queue of cards passed to method
        boolean result2 = (boolean) method2.invoke(cardGame, 4, result, result1, cardQueue);
        // asserts that result2 is true (cards are successfully distributed)
        assert(result2);
    }

    @After
    public void tearDownTest(){
        // tears down test by setting object values to null
        cardGame = null;
        cardQueue = null;
    }
}