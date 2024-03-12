package coolcards.example;
import java.util.ArrayList;
import java.util.Collections;

public class Hand {
    ArrayList<Card> cards;
    ArrayList<Card> bestHand;
    int rank;
    int highCardValue;

    public Hand(){
        cards = new ArrayList<Card>();
    }

    public void AddCard(Card card){
        cards.add(card);
    }

    public void Sort(){
        Collections.sort(cards);
    }

    // public void TrueEvalute(){

    // }

    public void Evaluate(){
        bestHand = new ArrayList<Card>();

        //Straight Flush and Royal Flush: WORKING
        CheckForStraightFlush();
        
        if(!bestHand.isEmpty()){
            if(highCardValue != 14) rank = 2;
            else rank = 1;
            return;
            
        }
        bestHand.clear();
        highCardValue = 0;
        //FourKind: WORKING
        nKindCheck(4);

        if(!bestHand.isEmpty()){

            return;
        }

        bestHand.clear();
        highCardValue = 0;
        //FullHouse: WORKING
        FullHouse();
        if(!bestHand.isEmpty()){
            rank = 4;
            return;
        }
        

        bestHand.clear();
        highCardValue = 0;
        //Flush: WORKING
        if(cards.size() >= 5) CheckForFlush(4);
        if(cards.size() >= 6) CheckForFlush(5);
        if(cards.size() == 7) CheckForFlush(6);

        if(!bestHand.isEmpty()){
            rank = 5;
            Collections.sort(bestHand);
            highCardValue = bestHand.get(4).getValue();
            return;
        }

        bestHand.clear();
        highCardValue = 0;

        //Straight
        CheckForStraight();


        if(!bestHand.isEmpty()){
            rank = 6;
            return;
        }
        
        bestHand.clear();
        highCardValue = 0;
        //ThreeKind: WORKING
        nKindCheck(3);

        if(!bestHand.isEmpty()){
            rank = 7;
            return;
        }

        bestHand.clear();
        highCardValue = 0;
        //TwoPair
        TwoPair();

        if(!bestHand.isEmpty()){
            rank = 8;
            Collections.sort(bestHand);
            highCardValue = bestHand.get(4).getValue();
            return;
        }

        bestHand.clear();
        highCardValue = 0;
        //Pair
        nKindCheck(2);
        if(!bestHand.isEmpty()){
            rank = 9;
            return;
        }

        bestHand.clear();
        highCardValue = 0;
        //HighCard
        
        bestHand = new ArrayList<Card>(cards.subList(cards.size()-5, cards.size()));
        if(!bestHand.isEmpty()){
            rank = 10;
            highCardValue = bestHand.get(4).getValue();
            return;
        }
    
    }

    

    public void getHandValue(){
        for (int i = 0; i < bestHand.size(); i++){
            System.out.println(bestHand.get(i));
        }
        switch(rank){
            case 1: System.out.println("royalFlush"); break; 
            case 2: System.out.println("straightFlush"); break;
            case 3: System.out.println("4Kind"); break;
            case 4: System.out.println("Full House"); break;
            case 5: System.out.println("flush"); break;
            case 6: System.out.println("straight"); break;
            case 7: System.out.println("3Kind"); break;
            case 8: System.out.println("2Pair"); break;
            case 9: System.out.println("Pair"); break;
            case 10: System.out.println("HighCard"); break;
            default: System.out.println("unknown type 404"); break;
        }
        System.out.println("highCard: " + highCardValue);
    }

    public void nKindCheck(int n){
        for (int i = cards.size() - 1; i > -1; i--){
            int checkCard = cards.get(i).getValue();
            int occurences = 0;
            ArrayList<Card> duplicateCards = new ArrayList<Card>();
            for (int j = cards.size() - 1; j > -1; j--){
                
                if (cards.get(j).getValue() == checkCard) {
                    occurences += 1;
                    duplicateCards.add(cards.get(j));
                    highCardValue = checkCard;
                }
            }  
            if (occurences == n){
                bestHand = duplicateCards;
                // Fills the list up with extra
                for(int k = 0; k < cards.size(); k++){
                    if(cards.get(k).getValue() != checkCard){
                        bestHand.add(cards.get(k));
                    }
                    if(bestHand.size() == 5) break;
                }
                return;
            }
        }
    }

    public void FullHouse(){
        ArrayList<Card> FullHouseHand = new ArrayList<Card>();
        for (int i = cards.size() - 1; i > -1; i--){
            int checkCard = cards.get(i).getValue();
            int occurences = 0;
            FullHouseHand.clear();
            for (int j = cards.size() - 1; j > -1; j--){
                
                if (cards.get(j).getValue() == checkCard) {
                    occurences += 1;
                    FullHouseHand.add(cards.get(j));
                    highCardValue = checkCard;
                }
            }  
            if (occurences == 3){
                break;
            }
        }
        if (FullHouseHand.size() != 3) {
            return;
        }
        
        for (int i = cards.size() - 1; i > -1; i--){
            int checkCard = cards.get(i).getValue();
            int occurences = 0;
            ArrayList<Card> part2 = new ArrayList<Card>();
            for (int j = cards.size() - 1; j > -1; j--){
                if (cards.get(j).getValue() == checkCard && checkCard != highCardValue) {
                    occurences += 1;
                    part2.add(cards.get(j));
                }
            }  
            if (occurences == 2){
                bestHand.addAll(part2);
                bestHand.addAll(FullHouseHand);
                return;
            }
        }
    }

    public void TwoPair(){
        ArrayList<Card> firstPair = new ArrayList<Card>();
        Card randomCard = new Card(null, null, 0);
        int highCardValue1 = 0;

        for (int i = cards.size() - 1; i > -1; i--){
            int checkCard = cards.get(i).getValue();
            int occurences = 0;
            for (int j = cards.size() - 1; j > -1; j--){
                
                if (cards.get(j).getValue() == checkCard) {
                    occurences += 1;
                    firstPair.add(cards.get(j));
                    highCardValue = checkCard;
                }
            }  
            if (occurences == 2){
                break;
            }
        }
        
        for (int i = cards.size() - 1; i > -1; i--){
            int checkCard = cards.get(i).getValue();
            int occurences = 0;
            ArrayList<Card> secondPair = new ArrayList<Card>();
            for (int j = cards.size() - 1; j > -1; j--){
                
                if (cards.get(j).getValue() == checkCard && checkCard != highCardValue) {
                    occurences += 1;
                    secondPair.add(cards.get(j));
                    highCardValue1 = checkCard;
                }
                else if (cards.get(j).getValue() != highCardValue) {
                    randomCard = cards.get(j);
                }
            }  
            if (occurences == 2){
                bestHand.addAll(secondPair);
                bestHand.addAll(firstPair);
                bestHand.add(randomCard);
                highCardValue = Math.max(highCardValue, highCardValue1);
                return;
            }
        }
    }

    void CheckForStraightFlush(){
        ArrayList<Card> straightFlush = new ArrayList<Card>();
        for(int i=0; i< cards.size() -1; i++){
            ArrayList<Card> temp = new ArrayList<Card>();
            temp.add(cards.get(i));
            int currentValue = cards.get(i).getValue();
            String currentSuit = cards.get(i).getSuit();
            for(int j=i+1; j< cards.size(); j++){
                int nextValue= cards.get(j).getValue();

                if(currentValue == nextValue-1 
                    && currentSuit.equals(cards.get(j).getSuit())){
                    temp.add(cards.get(j));
                    currentValue++;
                }
                else if(currentValue ==5 && nextValue ==14
                    && currentSuit.equals(cards.get(j).getSuit())){
                    temp.add(0,cards.get(j));
                }
                if(temp.size() ==5) break;
            }
            if(temp.size() ==5){
                if(straightFlush.isEmpty()) straightFlush = temp;
                if(straightFlush.get(4).getValue()<temp.get(4).getValue()){
                    straightFlush = temp;
                }
            }
        }
        if(straightFlush.size() ==5) {
            bestHand = straightFlush;
            highCardValue = straightFlush.get(4).getValue();
        }
    }


    void CheckForFlush(int i){
        ArrayList<Card> flush = new ArrayList<Card>();
        String suitName = cards.get(i).getSuit();
        int occurences = 0;
        

        for (int j = cards.size() - 1; j > -1; j--)
        {
            if (suitName.equals(cards.get(j).getSuit()) && occurences < 5){
                flush.add(cards.get(j));
                occurences++;
            }
        }

        if (occurences == 5) bestHand = flush;
        else
            return;
    }

    void CheckForStraight(){
        ArrayList<Card> straight = new ArrayList<Card>();
        for(int i=0; i< cards.size() -1; i++){
            ArrayList<Card> temp = new ArrayList<Card>();
            temp.add(cards.get(i));
            int currentValue = cards.get(i).getValue();
            for(int j=i+1; j< cards.size(); j++){
                int nextValue= cards.get(j).getValue();

                if(currentValue == nextValue-1){
                    temp.add(cards.get(j));
                    currentValue++;
                }
                else if(currentValue ==5 && nextValue ==14){
                    temp.add(0,cards.get(j));
                }
                if(temp.size() ==5) break;
            }
            if(temp.size() ==5){
                if(straight.isEmpty()) straight = temp;
                if(straight.get(4).getValue()<temp.get(4).getValue()){
                    straight = temp;
                }
            }
        }
        if(straight.size() ==5) {
            bestHand = straight;
            highCardValue = straight.get(4).getValue();
        }
    }



    @Override
    public String toString(){
        String contents = "";
        for (int i = 0; i < cards.size(); i++){
            contents += cards.get(i);
            if(i != cards.size() - 1)
                contents += ", ";
        }

        return contents;
    }
}
