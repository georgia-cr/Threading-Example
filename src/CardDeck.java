// imports methods used in this class
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class CardDeck {
    /*
    CardDeck class that simulates the deck of cards between players,
    includes methods which alter the state of the queue of Card types
     */

    // initialises variable names, class modifiers, and types
    private int cardDeckNum;
    // a queue of type Card
    private Queue<Card> cardsInDeck;

    public CardDeck(int cardDeckNum){
        // class constructor
        // assigns cardDeckNum an actual value
        this.cardDeckNum = cardDeckNum;
        // creates a new LinkedList and assigns to variable cardsInDeck
        this.cardsInDeck = new LinkedList<>();
    }

    /*
    the below methods have the 'synchronized' declaration in method declaration
    so that each can only be accessed by one thread at a time as the thread
    locks the instance of the CardDeck so that any changes cannot be overwritten
    or changes while the Player thread is using it
    */

    public synchronized boolean checkIfDeckEmpty(){
        // returns true if the deck (queue of type Card) is empty, false otherwise
        return (cardsInDeck.isEmpty());
    }

    public synchronized Card getTopCard(){
        // returns the top card and removes it from the pack (start of the queue)
        return cardsInDeck.remove();
    }

    public synchronized void setBottomCard(Card bottomCard){
        // adds a card to the bottom of the pack (end of the queue)
        cardsInDeck.add(bottomCard);
    }

    public synchronized int getCardDeckNum(){
        // returns the card deck number
        return cardDeckNum;
    }

    public synchronized void finalDeckAction(){
        /*
        creates a text output file with information about the final state of the deck
         */
        // assigns a file name to variable fileName
        String fileName = "deck"+cardDeckNum+"_output.txt";
        /*
        a try catch clause attempts to create a file using fileName as a file name,
        and creates a FileWriter instance so that each element of the queue (each Card in the deck)
        can be appended and written to the file, then FileWriter is closed
         */
        try{
            File deckFile = new File(fileName);
            if(deckFile.createNewFile()){}
            FileWriter myWriter = new FileWriter(fileName, true);
            myWriter.write("Deck "+cardDeckNum+" contents: ");
            for (Card item:cardsInDeck){
                String deckCard = (item.getCardNum()) + " ";
                myWriter.write(deckCard);
            }
            myWriter.close();
        } catch (IOException e){
            // catches an IOException
            System.out.println("An error occurred creating and adding to deck file");
        }
    }
}
