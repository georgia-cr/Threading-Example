// imports modules used
import java.util.Queue;

public class CardGame {

    private void startGame(int numPlayers, Queue<Card> cardQueue){
        /*
        creates objects by calling methods, then starts the Player object threads
         */
        // creates a list of size n of Deck objects
        CardDeck[] deckList = createCardDecks(numPlayers);
        // creates a list of size n of Player objects
        Player[] playerList = createPlayers(numPlayers, deckList);
        // returns true if the Card objects were correctly distributed
        boolean distributed = distributeCards(numPlayers, deckList, playerList, cardQueue);
        // starts threads for player objects
        if (distributed){
            for (int i=0; i<numPlayers; i++){
                playerList[i].start();
            }
        } else {
            // otherwise starts again
            System.out.println("Unable to distribute cards");
            startGame(numPlayers, cardQueue);
        }
    }

    private CardDeck[] createCardDecks(int numPlayers){
        /*
        creates a list of CardDeck objects
         */
        CardDeck[] deckList = new CardDeck[numPlayers];
        for (int i = 0; i<numPlayers; i++){
            // creates a new object CardDeck, and assigns a deck number
            CardDeck newDeck = new CardDeck((i+1));
            // adds the CardDeck object to the list
            deckList[i] = newDeck;
        }
        return deckList;
    }

    private Player[] createPlayers(int numPlayers, CardDeck[] deckList){
        /*
        creates a list of Player objects
         */
        Player[] playerList = new Player[numPlayers];
        // creates a new instance of the GameFlag object
        GameFlag gameFlag = new GameFlag();
        // creates and adds new Player objects to a list using a for loop
        for (int i=0; i<numPlayers; i++){
            // if the last Player object has not been created
            if (i != (numPlayers-1)){
                // passes the player number, pick up deck, discard deck, and GameFlag object as parameters of creation
                Player sPlayer = new Player((i+1), deckList[i], deckList[i+1], gameFlag);
                playerList[i] = sPlayer;
            } else if (i == (numPlayers-1)){
                // if the last Player object has been created, assigns the first and last deck to the Player
                // e.g. deck 1 and deck 8 if there are 8 Player objects
                Player sPlayer = new Player((i+1), deckList[i], deckList[0], gameFlag);
                playerList[i] = sPlayer;
            } else {
                // else uses a looping structure to initialise player list
                System.out.println("error creating player list");
                createPlayers(numPlayers, deckList);
            }
        }
        // returns playerList if creation is successful
        return playerList;
    }

    private boolean distributeCards(int numPlayers, CardDeck[] deckList, Player[] playerList, Queue<Card> cardQueue){
        // distributes cards to players
        for (int i=0; i<4; i++) {
            for (Player item : playerList) {
                // removes a Card from the start of the queue
                // and distributes to each Player object in a round robin fashion
                item.addNewCard(cardQueue.remove());
            }
        }
        // distributes cards into decks
        for (int i=0; i<=numPlayers; i++){
            // if cardQueue is empty, breaks from the for loop
            if (cardQueue.isEmpty()){ break; }
            // otherwise removes a Card from cardQueue and distributes to each deck in a round robin fashion
            if (i==numPlayers){ i = 0; }
            deckList[i].setBottomCard(cardQueue.remove());
        }
        // returns true if cards are distributed
        return true;
    }

    public static void main(String[] args){
        // creates new instance of UserInput class
        UserInput userInput = new UserInput();
        userInput.init();
        // creates a new instance of CardGame
        CardGame cardGame = new CardGame();
        // calls the startGame method on object cardGame
        // using the getNumPlayers and getCardQueue methods on the userInput object
        cardGame.startGame(userInput.getNumPlayers(), userInput.getCardQueue());
    }
}