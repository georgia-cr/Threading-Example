import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class UserInput {
    /*
    gets input from the user and ensures that this is correct
     */

    // initialises types, and access modifiers for variables
    private int finalNumPlayers;
    private Queue<Card> finalCardQueue;

    public void init(){
        // gets number of players
        int numPlayers = inputPlayers();
        // if the method inputPlayers returns less that 1
        // iterates until a suitable value is found
        while (numPlayers < 1){
            numPlayers = inputPlayers();
        }
        // reads input file to a queue of Card objects
        Queue<Card> cardQueue = readFileToQueue(numPlayers, new LinkedList<>());
        while (cardQueue == null){
            // if the method readFileToQueue returns null
            // iterates until a suitable value is found
            cardQueue = readFileToQueue(numPlayers, new LinkedList<>());
        }
        this.finalNumPlayers = numPlayers;
        this.finalCardQueue = cardQueue;
    }

    public int getNumPlayers(){
        // returns the correct number of players
        return this.finalNumPlayers;
    }

    public Queue<Card> getCardQueue(){
        // returns the correct card queue
        return this.finalCardQueue;
    }

    private int inputPlayers(){
        /*
        asks the user to input the number of players,
        if the input is not suitable, asks the user again
         */
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the number of players:");
        int numPlayers = -1;
        try {
            // ensures each line is an integer
            numPlayers = scanner.nextInt();
            // ensures that there is at least one player
            if (numPlayers <= 1) {
                numPlayers = -1;
            }
        } catch (InputMismatchException e){
            // catches any non-integer input
        }
        return numPlayers;
    }

    private String inputFileLocation(){
        /*
        asks the user to enter the location of the pack and returns this
         */
        Scanner scanner = new Scanner(System.in);
        String fileLocation;
        System.out.println("Please enter the location of the pack to load:");
        // try catch clause catches if the input is not text
        fileLocation = scanner.nextLine();
        return fileLocation;
    }

    private Queue<Card> readFileToQueue(int numPlayers, Queue<Card> cardQueue){
        /*
        reads the file from the file location given by the method inputFileLocation
         */
        int counter = 0;
        // clears the queue
        cardQueue.clear();
        try{
            // gets user input of the file location
            Scanner scanner = new Scanner(new File(inputFileLocation()));
            while (scanner.hasNext()){
                // ensures each item is an integer
                int cardNum = Integer.parseInt(scanner.next());
                // ensures no non-negative integers are in the pack
                if (cardNum<0){
                    System.out.println("Each number needs to be non-negative, pack is not valid, please enter a valid path ");
                    // returns counter as -1 if an integer is negative
                    counter = -1;
                    break;
                }
                cardQueue.add(new Card(cardNum));
                // counts the number of lines in the file
                counter++;
            }
            scanner.close();
            // catches any exceptional input
        } catch (FileNotFoundException | NumberFormatException e){
            System.out.println("Exception thrown, pack is not valid, please enter a valid path ");
            counter = -1;
        }
        // ensures that the pack has 8n rows
        if (!(counter == (8*numPlayers))){
            System.out.println("Wrong number of rows, pack is not valid, please enter a valid path ");
            counter = -1;
        }
        if (counter == -1 || counter == 0){
            cardQueue = null;
        }
        // returns the queue of cards if the pack file is valid, otherwise returns null
        return cardQueue;
    }

}
