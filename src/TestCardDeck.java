// imports JUnit 4 modules
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import static org.junit.Assert.*;

@Category({UnitTests.class})
public class TestCardDeck {
    // sets variable types
    private CardDeck cardDeck;
    private File file;
    private boolean found;

    @Before
    public void setUpTest(){
        // sets up test by instantiating a new CardDeck object
        cardDeck = new CardDeck(1);
    }

    @Test
    public void testCheckIfDeckEmpty(){
        // asserts deck is empty if no cards added
        assertTrue(cardDeck.checkIfDeckEmpty());
    }

    @Test(expected = NoSuchElementException.class)
    public void testGetCardException(){
        // asserts that the object throws an exception if there is no card
        // never called in the game as the deck is always checked before a card is picked up
        cardDeck.getTopCard();
    }

    @Test
    public void testGetCardNormal(){
        // asserts that if there is one card in deck, the same card is picked up
        cardDeck.setBottomCard(new Card(5));
        assertEquals(5, cardDeck.getTopCard().getCardNum());
    }

    @Test
    public void testGetCardDeckNum(){
        // asserts that the card deck number is correct
        assertEquals(1, cardDeck.getCardDeckNum());
    }

    @Test
    public void testFinalDeckAction(){
        // asserts that the CardDeck object correctly outputs at the end of the game
        cardDeck.finalDeckAction();
        file = new File("deck1_output.txt");
        // asserts that the file exists and is readable
        assertTrue(file.exists());
        assertTrue(file.canRead());
        try {
            Scanner scanner = new Scanner(file);
            found = false;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if(line.contains("Deck 1 contents: ")) {
                    found = true;
                    break;
                }
            }
        } catch(FileNotFoundException e) {
            // test fails if the file is not found
            fail();
        }
    }

    @After
    public void tearDownTest(){
        // tears down test by setting objects to null
        cardDeck = null;
        file = null;
        found = false;
    }

}
