package coolcards.example;
import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    ArrayList<Card> cardList = new ArrayList<Card>();

    public Deck() {
        CreateSuitCards("Spades");
        CreateSuitCards("Clubs");
        CreateSuitCards("Hearts");
        CreateSuitCards("Diamonds");
    }

    private void CreateSuitCards(String suitName){
        cardList.add(new Card("A", suitName, 14));
        cardList.add(new Card("2", suitName, 2));
        cardList.add(new Card("3", suitName, 3));
        cardList.add(new Card("4", suitName, 4));
        cardList.add(new Card("5", suitName, 5));
        cardList.add(new Card("6", suitName, 6));
        cardList.add(new Card("7", suitName, 7));
        cardList.add(new Card("8", suitName, 8));
        cardList.add(new Card("9", suitName, 9));
        cardList.add(new Card("10", suitName, 10));
        cardList.add(new Card("J", suitName, 11));
        cardList.add(new Card("Q", suitName, 12));
        cardList.add(new Card("K", suitName, 13));
    }

    public void PrintCards(){
        for(int i = 0; i < cardList.size(); i++){
            System.out.println(cardList.get(i));
        }
    }

    public void Shuffle(){
        Collections.shuffle(cardList);
    }

    public Card DrawCard(){
        return cardList.remove(cardList.size() - 1);
    }

    public Hand GetNewHand() {
        Hand newHand = new Hand();
        newHand.AddCard(new Card("2", "weqe", 2));
        newHand.AddCard(new Card("K", "Spades", 13));
        newHand.AddCard(new Card("Q", "Spades", 12));
        newHand.AddCard(new Card("3", "Spades", 3));
        newHand.AddCard(new Card("A", "Spades", 14));
        newHand.AddCard(new Card("9", "qr", 9));
        newHand.AddCard(new Card("5", "fwdf", 5));
        
        newHand.Sort();
        newHand.Evaluate();
        newHand.getHandValue();
        return newHand;
    }
}
