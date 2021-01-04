// imports modules used
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Player extends Thread {
    // extends Thread so that threads can be instantiated by calling start() method through CardGame
    // initialises variable names, access modifiers, and types
    private final GameFlag gameFlag;
    private volatile boolean turnTaken;
    private int playerNum;
    // list of 4 Card objects in hand
    private Card[] cardSet;
    // list modelling times that each Card object has been in the Player objects hand
    private List<Integer> timeCard;
    // the CardDeck types
    private CardDeck discardDeck;
    private CardDeck pickUpDeck;

    public Player(int playerNum, CardDeck discardDeck, CardDeck pickUpDeck, GameFlag gameFlag) {
        // class constructor
        // initialises variables
        this.playerNum = playerNum;
        this.gameFlag = gameFlag;
        // cardSet models the player's hand
        this.cardSet = new Card[4];
        // models the decks to the left and right of the player
        this.discardDeck = discardDeck;
        this.pickUpDeck = pickUpDeck;
        timeCard = new ArrayList<>();
        this.turnTaken = false;
        // initialises list of 4 0s
        // models the length of time each card has been in a players hand
        for (int i = 0; i < 4; i++) {
            timeCard.add(0);
        }
        // creates player files for output of actions within game
        // uses try catch statement to ensure any errors are caught
    }

    private void writeToFile(String fileText) {
        // writes a given parameter to the file
        // uses a try catch clause to catch any errors
        try {
            String fileName = "player" + playerNum + "_output.txt";
            // appends to the file instead of overwriting any data
            FileWriter myWriter = new FileWriter(fileName, true);
            myWriter.write(fileText + "\n");
            myWriter.close();
        } catch (IOException e) {
            // catches if write was not possible
            System.out.println("An error occurred writing to file");
            e.printStackTrace();
        }
    }

    public void addNewCard(Card newCard) {
        // assigns a given card to a position in the player's hand
        if (cardSet[0] == null) {
            cardSet[0] = newCard;
        } else if (cardSet[1] == null) {
            cardSet[1] = newCard;
        } else if (cardSet[2] == null) {
            cardSet[2] = newCard;
        } else if (cardSet[3] == null) {
            cardSet[3] = newCard;
            // outputs the players initial hand to player file
            writeToFile("Player " + playerNum + " initial hand is : " + cardSet[0].getCardNum() + " " +
                    cardSet[1].getCardNum() + " " + cardSet[2].getCardNum() + " " + cardSet[3].getCardNum());
        } else {
            // if cardSet is not used properly, error is caught
            System.out.println("all cards already defined");
        }
    }

    private int cardToDiscard() {
        /*
        returns the Card which needs to be discarded
         */
        int longestTime = 0;
        int cardIndex = 0;
        for (int i = 0; i < 4; i++) {
            int currentCardTime = timeCard.get(i);
            /*
            if the Card is not a preferred Card number, and the time that the Card has
            been in the players hand is longer than others, will return the index
            of this card - ensures a player does not hold onto any Card not matching
            their player number indefinitely
             */
            if ((!(preferredCardInHand(cardSet[i]))) && (currentCardTime >= longestTime)) {
                // sets the longest time and the card index of the card spending the longest time
                // in the players hand
                longestTime = currentCardTime;
                cardIndex = i;
            }
        }
        // returns index of the card to discard
        return cardIndex;
    }

    private boolean preferredCardInHand(Card card) {
        // checks if a card is a preferred card (matches player number)
        return (card.getCardNum() == playerNum);
    }

    private synchronized void mainGame() {
        /*
        iterates through game actions until the player wins,
        or is informed by another thread that it has won,
        locks objects by declaring method as synchronized
         */
        // take turn is atomic as turnTaken is a volatile variable
        if (!getFlag()) {
            // if the game has not yet been won
            // access to object gameFlag is locked by thread while executing synchronised block
            synchronized (gameFlag) {
                // checks if all card numbers match
                if (cardSet[0].getCardNum() == cardSet[1].getCardNum() &&
                        cardSet[1].getCardNum() == cardSet[2].getCardNum()
                        && cardSet[2].getCardNum() == cardSet[3].getCardNum()) {
                    // outputs which player wins
                    System.out.println("Player " + playerNum + " wins");
                    // sets variables accessible by other threads to indicate which player has won
                    gameFlag.setPlayerNumWon(playerNum);
                    gameFlag.setGame();
                }
            }
        }
        if (!getFlag()) {
            // executes take turn if the game has not been won yet
            turnTaken = takeTurn();
            if (turnTaken) {
                // outputs the current hand of the player to its output file
                writeToFile("Player " + playerNum + " current hand: " + " " + cardSet[0].getCardNum() + " " + cardSet[1].getCardNum() + " "
                        + cardSet[2].getCardNum() + " " + cardSet[3].getCardNum());
                turnTaken = false;
            }
        }
        if (getFlag()) {
            // if game is won writes final player actions to file
            if (playerNum == gameFlag.getPlayerNumWon()) {
                // executes if the player has won the game
                // outputs actions to the player's output file
                writeToFile("Player " + playerNum + " wins");
                writeToFile("Player " + playerNum + " exits");
                writeToFile("Player " + playerNum + " final hand: " + " " + cardSet[0].getCardNum() + " " + cardSet[1].getCardNum() + " "
                        + cardSet[2].getCardNum() + " " + cardSet[3].getCardNum());
            } else {
                // executes if another player has won the game
                // gets the number of the player who has won
                int playerWon = gameFlag.getPlayerNumWon();
                // outputs actions to the player's output file
                writeToFile("Player " + playerWon + " has informed player " + playerNum + " that player " + playerWon + " has won");
                writeToFile("Player " + playerNum + " exits");
                writeToFile("Player " + playerNum + " hand: " + " " + cardSet[0].getCardNum() + " " + cardSet[1].getCardNum() + " "
                        + cardSet[2].getCardNum() + " " + cardSet[3].getCardNum());
            }
            // calls a write method on the deck that the player picks up from
            pickUpDeck.finalDeckAction();
        } else {
            // otherwise uses a looping structure to play
            synchronized (gameFlag) {
                // locks object gameFlag while thread is using it
                if (cardSet[0].getCardNum() == cardSet[1].getCardNum() && cardSet[1].getCardNum() == cardSet[2].getCardNum()
                        && cardSet[2].getCardNum() == cardSet[3].getCardNum()) {
                    // outputs which player wins
                    System.out.println("Player " + playerNum + " wins");
                    // sets variables accessible by other threads to indicate which player has won
                    gameFlag.setPlayerNumWon(playerNum);
                    gameFlag.setGame();
                }
            }
            // recursive loop to call itself
            mainGame();
        }
    }

    private synchronized boolean getFlag() {
        /*
        returns if the game has been won by checking if the flag through the GameFlag instance gameFlag
        has been set
         */
        boolean gameFlagTrue = false;
        // synchronizing on the object means that no two threads can access it
        // therefore meaning the object is locked to any reads or changes, and any other threads must wait
        synchronized (gameFlag) {
            if (gameFlag.getGame()) gameFlagTrue = true;
        }
        // returns true if the game has been won
        return gameFlagTrue;
    }

    private boolean takeTurn() {
        /*
        player picks up and discards to decks
         */
        // if deck is empty the thread waits, then returns calls the mainGame method
        // this allows other threads to deposit further Card types onto the deck
        if (pickUpDeck.checkIfDeckEmpty()) {
            // uses try catch clause to ensure errors are caught
            try {
                wait(100);
            } catch (InterruptedException e) {
                // if thread is interrupted calls mainGame method
                mainGame();
            }
            // calls mainGame method so that synchronised lock is removed
            // and other threads can deposit cards on the empty deck
            mainGame();
        }
        synchronized (this) {
            /*
            synchronised method ensures pick up and discard of a Card is atomic
            as the pick up and discard decks are both locked within the period
            that the actions are taken,
            in Card class cardNum is declared volatile so any read or writes
            are atomic.
             */
            // works out which Card to discard using cardToDiscard method
            int cardDiscardIndex = cardToDiscard();
            Card prevCard = cardSet[cardDiscardIndex];
            // discards a Card to the discard deck
            discardDeck.setBottomCard(cardSet[cardDiscardIndex]);
            // picks up a Card from the pick up deck
            cardSet[cardDiscardIndex] = pickUpDeck.getTopCard();
            // adds 1 to the timeCard list (simulates each card aging with each round)
            for (int i = 0; i < 4; i++) {
                timeCard.set(i, (timeCard.get(i) + 1));
            }
            // resets the round count to 0 for Card picked from pick up deck
            timeCard.set(cardDiscardIndex, 0);
            // outputs actions to file
            writeToFile("Player " + playerNum + " draws a " + cardSet[cardDiscardIndex].getCardNum() + " from deck " + pickUpDeck.getCardDeckNum());
            writeToFile("Player " + playerNum + " discards " + prevCard.getCardNum() + " to deck " + discardDeck.getCardDeckNum());
        }
        return true;
    }

    public void run() {
        // initial method called calls checkIfWon to determine if game won initially
        //checkIfWon();
        // then mainGame to begin game loop
        mainGame();
    }

}
