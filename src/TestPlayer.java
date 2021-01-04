// imports JUnit 4 modules
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import static org.junit.Assert.*;
// imports data structure modules
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.PrintStream;
// imports Java Reflection module
import java.lang.reflect.Method;

@Category({UnitTests.class})
public class TestPlayer {
    // sets up variables
    private Player testPlayer;
    private Player testPlayer1;
    private Player testPlayer2;
    private GameFlag gameFlag;
    private CardDeck cardDeck1;
    private CardDeck cardDeck2;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUpTest(){
        // sets up test by creating new instances of GameFlag, Card, CardDeck, and Player objects
        gameFlag = new GameFlag();
        cardDeck1 = new CardDeck(1);
        cardDeck2 = new CardDeck(2);
        // passes the player number, and CardDeck objects to the Player object constructor
        testPlayer = new Player(1,cardDeck1,cardDeck2,gameFlag);
        testPlayer1 = new Player(2, cardDeck1, cardDeck2, gameFlag);
        testPlayer2 = new Player(3, cardDeck1, cardDeck2, gameFlag);
        // sets new print stream content
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void testPreferredCardInHand() throws Exception {
        // throws exception if there is no such method
        // makes method visible using Java Reflection for testing
        Method method = Player.class.getDeclaredMethod("preferredCardInHand", Card.class);
        method.setAccessible(true);
        boolean returnValueCorrect = (boolean) method.invoke(testPlayer, new Card(1));
        // asserts that the player has a preferred card in hand
        assertTrue(returnValueCorrect);
        boolean returnValueWrong = (boolean) method.invoke(testPlayer, new Card(2));
        // asserts the player does not have a preferred card in hand
        assertFalse(returnValueWrong);
    }

    @Test
    public void testGetFlag() throws Exception {
        // throws exception if there is no such method
        // makes method visible using Java Reflection for testing
        Method method = Player.class.getDeclaredMethod("getFlag");
        method.setAccessible(true);
        boolean returnValue = (boolean) method.invoke(testPlayer);
        // asserts that the flag is false
        assertFalse(returnValue);
        gameFlag.setGame();
        // asserts the flag is true after changing it to true
        returnValue = (boolean) method.invoke(testPlayer);
        assertTrue(returnValue);
    }

    @Test
    public void testCardToDiscard() throws Exception {
        // simulates there is one non-preferred card in the hand
        testPlayer.addNewCard(new Card(1));
        testPlayer.addNewCard(new Card(2));
        testPlayer.addNewCard(new Card(1));
        testPlayer.addNewCard(new Card(1));
        // makes method visible for testing
        Method method = Player.class.getDeclaredMethod("cardToDiscard");
        method.setAccessible(true);
        Integer returnValue1 = (Integer) method.invoke(testPlayer);
        // checks that the index of the card to discard is the second card (non-preferred card)
        assert(returnValue1 == 1);

        // simulates that all the cards are the same
        testPlayer1.addNewCard(new Card(1));
        testPlayer1.addNewCard(new Card(1));
        testPlayer1.addNewCard(new Card(1));
        testPlayer1.addNewCard(new Card(1));
        // invokes the method
        Integer returnValue2 = (Integer) method.invoke(testPlayer1);
        // asserts that the fourth card should be returned (index of three)
        assert(returnValue2 == 3);
    }

    @Test
    public void testAddNewCard(){
        // adds 4 cards
        testPlayer2.addNewCard(new Card(1));
        testPlayer2.addNewCard(new Card(1));
        testPlayer2.addNewCard(new Card(1));
        testPlayer2.addNewCard(new Card(1));
        //asserts that "all cards already defined" is output when a fifth card is attempted
        // to be added when the player's hand is full
        testPlayer2.addNewCard(new Card(1));
        assertEquals("all cards already defined", outContent.toString().trim());
    }

    @Test
    public void testWriteToFile() throws Exception {
        // ensures method is accessible
        Method method = Player.class.getDeclaredMethod("writeToFile", String.class);
        method.setAccessible(true);
        // envokes method writeToFile, passing 'test text' as a parameter
        method.invoke(testPlayer, "test text");
        boolean found = false;
        File file = new File("player1_output.txt");
        // asserts that the file exists and is readable
        assertTrue(file.exists());
        assertTrue(file.canRead());
        // detects presence of 'test text' within the file,
        // to check whether the text has been added to file
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            // iterates through file checking for text
            String line = scanner.nextLine();
            if(line.contains("test text")) {
                found = true;
                break;
            }
        }
        if (!found){
            // fails if the text has not been found
            fail();
        }
    }

    @Test
    public void testTakeTurn() throws Exception{
        // sets up decks and the player's hand
        cardDeck1.setBottomCard(new Card(1));
        cardDeck2.setBottomCard(new Card(1));
        testPlayer.addNewCard(new Card(1));
        testPlayer.addNewCard(new Card(2));
        testPlayer.addNewCard(new Card(3));
        testPlayer.addNewCard(new Card(4));
        // ensures method is accessible
        Method method = Player.class.getDeclaredMethod("takeTurn");
        method.setAccessible(true);
        boolean result = (boolean) method.invoke(testPlayer);
        // asserts that the player could take their turn
        assert(result);
    }

    @Test
    public void testMainGameWonTrue() throws Exception{
        // sets that the game has been won
        gameFlag.setGame();
        // adds cards to player's hand
        testPlayer.addNewCard(new Card(1));
        testPlayer.addNewCard(new Card(2));
        testPlayer.addNewCard(new Card(3));
        testPlayer.addNewCard(new Card(4));
        // ensures method is accessible
        Method method = Player.class.getDeclaredMethod("mainGame");
        method.setAccessible(true);
        method.invoke(testPlayer);
        File file = new File("player1_output.txt");
        // asserts that the file exists and is readable
        assertTrue(file.exists());
        assertTrue(file.canRead());
        try {
            Scanner scanner = new Scanner(file);
            boolean found = false;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if(line.contains("Player 1 wins")) {
                    found = true;
                    break;
                }
            }
            if (!found){
                // fails if the file is not found
                fail();
            }
        } catch(FileNotFoundException e) {
            fail();
        }
    }

    @After
    public void tearDownTest(){
        // tears down test by setting all objects to null
        gameFlag = null;
        testPlayer = null;
        testPlayer1 = null;
        testPlayer2 = null;
        cardDeck1 = null;
        cardDeck2 = null;
        System.setOut(originalOut);
    }
}