// imports JUnit 4 modules
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
// imports data structures modules
import java.io.ByteArrayInputStream;
import java.util.LinkedList;
import java.util.Queue;
// imports Java Reflection module
import java.lang.reflect.Method;

@Category(UnitTests.class)
public class TestUserInput {

    private UserInput userInput;

    @Before
    public void setUpTest(){
        // initialises objects
        userInput = new UserInput();
    }

    @Test
    public void testInputPlayers() throws Exception{
        // ensures method is accessible
        Method method = UserInput.class.getDeclaredMethod("inputPlayers");
        method.setAccessible(true);
        // sets input stream to '4'
        ByteArrayInputStream in = new ByteArrayInputStream("4".getBytes());
        System.setIn(in);
        // invokes the method inputPlayers on object userInput
        int result = (int) method.invoke(userInput);
        // asserts that the value returned is 4
        assert(result == 4);
    }

    @Test
    public void testInputPlayersException() throws Exception{
        // ensures method is accessible
        Method method = UserInput.class.getDeclaredMethod("inputPlayers");
        method.setAccessible(true);
        // sets byte stream input to 'f'
        ByteArrayInputStream in = new ByteArrayInputStream("f".getBytes());
        System.setIn(in);
        // asserts that method returns -1 by invoking inputPlayers on userInput object
        int result = (int) method.invoke(userInput);
        assert(result == -1);
    }

    @Test
    public void testInputPlayersZero() throws Exception{
        // ensures method is accessible
        Method method = UserInput.class.getDeclaredMethod("inputPlayers");
        method.setAccessible(true);
        // sets byte stream input to '0'
        ByteArrayInputStream in = new ByteArrayInputStream("0".getBytes());
        System.setIn(in);
        // asserts that method returns -1 by invoking inputPlayers on userInput object
        int result1 = (int) method.invoke(userInput);
        assert(result1 == -1);
    }

    @Test
    public void testInputFileLocation() throws Exception{
        // ensures method is accessible
        Method method = UserInput.class.getDeclaredMethod("inputFileLocation");
        method.setAccessible(true);
        // sets byte stream input to 'cards.txt'
        ByteArrayInputStream in = new ByteArrayInputStream("cards.txt".getBytes());
        System.setIn(in);
        // asserts that the user input is 'cards.txt' by invoking inputFileLocation on userInput object
        String result = (String) method.invoke(userInput);
        assert(result.equals("cards.txt"));
    }

    @Test
    public void testReadFileToQueue() throws Exception{
        // ensures method is accessible
        Method method = UserInput.class.getDeclaredMethod("readFileToQueue", int.class, Queue.class);
        method.setAccessible(true);
        // sets input byte stream as a file name
        ByteArrayInputStream in = new ByteArrayInputStream("mockCardsNegativeInt.txt".getBytes());
        System.setIn(in);
        // asserts that the result is null from invoking method readFileToQueue on userInput object
        Queue<Card> result = (Queue<Card>) method.invoke(userInput, 4, new LinkedList<>());
        assert(result == null);
        // sets input byte stream as a file name
        ByteArrayInputStream in1 = new ByteArrayInputStream("mockCardsNonInt.txt".getBytes());
        System.setIn(in1);
        // asserts that the result is null from invoking method readFileToQueue on userInput object
        Queue<Card> result1 = (Queue<Card>) method.invoke(userInput, 4, new LinkedList<>());
        assert(result1 == null);
        // sets input byte stream as a file name
        ByteArrayInputStream in2 = new ByteArrayInputStream("mockCardsLowRows.txt".getBytes());
        System.setIn(in2);
        // asserts that the result is null from invoking method readFileToQueue on userInput object
        Queue<Card> result2 = (Queue<Card>) method.invoke(userInput, 4, new LinkedList<>());
        assert(result2 == null);
    }

    @After
    public void tearDownTest(){
        // tears down test by setting object to null
        userInput = null;
    }
}
