public class GameFlag {
    // defines access modifiers, types, and variable names
    private int playerNumWon;
    // declares as volatile so any assignments will be volatile
    protected volatile boolean gameFlag;

    public GameFlag(){
        // class constructor
        // initialises variables
        this.gameFlag = false;
        this.playerNumWon = 0;
    }

    // sets variable game as true
    public void setGame() {
        this.gameFlag = true;
    }

    // returns boolean variable game
    public boolean getGame(){
        return gameFlag;
    }

    // sets the variable playerNumWon to parameter
    public void setPlayerNumWon(int playerNumWon) {
        this.playerNumWon = playerNumWon;
    }

    // returns int variable playerNumWon
    public int getPlayerNumWon() {return this.playerNumWon;}
}
