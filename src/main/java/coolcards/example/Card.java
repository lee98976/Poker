package coolcards.example;

public class Card implements Comparable<Card> {
    private String cardName;
    private String suit;
    private int value;

    public Card(String cardName, String suit, int value){
        this.cardName = cardName;
        this.suit = suit;
        this.value = value;
    }

    @Override
    public String toString(){
        return cardName + " of " + suit;
    }

    public int getValue(){
        return value;
    }

    public String getSuit(){
        return suit;
    }

    @Override
    public int compareTo(Card otherCard) {
        if (value > otherCard.getValue()) {
            return 1;
        }
        else if(value == otherCard.getValue()) {
            return 0;
        }
        else
            return -1;
    }
}
