public class Card {
    /*
    Card class acts as a card, and holds a card number
     */
    // definition of class modifiers and type
    // declaring as volatile ensures that the assignment or change of cardNum is atomic
    private volatile int cardNum;

    public Card(int cardNum){
        // the constructor initialises the cardNum value
        this.cardNum = cardNum;
    }

    public int getCardNum(){
        // returns the cardNum value
        return cardNum;
    }
}
