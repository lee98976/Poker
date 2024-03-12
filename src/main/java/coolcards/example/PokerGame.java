package coolcards.example;

import java.util.ArrayList;
import java.util.Scanner;

public class PokerGame {
    int bigBlind;
    int smallBlind;
    
    Deck myDeck;
    ArrayList<Player> playerList;
    Scanner scanner;

    public PokerGame() {
        playerList = new ArrayList<>();
        myDeck = new Deck();
        myDeck.Shuffle();
        scanner = new Scanner(System.in);
    }

    public void Setup() {
        // System.out.println("What is your name?");
        // String playerName = scanner.nextLine();

        // System.out.println("How many opponents do you want?");
        // int opponentCount = scanner.nextInt();
        // scanner.nextLine();

        // System.out.println("What is the starting money for players.");
        // int startingMoney = scanner.nextInt();
        // scanner.nextLine();

        // System.out.println("What is the starting blind.");
        // bigBlind = scanner.nextInt();
        // smallBlind = bigBlind / 2;
        // scanner.nextLine();

        String playerName = "Jeff";
        int opponentCount = 1;
        int startingMoney = 1000;
        bigBlind = 100;
        smallBlind = 50;

        for(int i = 0; i < opponentCount + 1; i++){
            if (i == 0) {
                playerList.add(new Player(playerName, startingMoney));
            }
            else {
                playerList.add(new Player("Computer" + i, startingMoney));
            }

            //System.out.println(playerList.get(i));
        }
    }

    public void StartGame(){
        for(int i = 0; i < playerList.size(); i++){
            Hand newHand = myDeck.GetNewHand();
            playerList.get(i).SetHand(newHand);
        }
    }

    public void PrintAllPlayerStatus(){
        System.out.println(playerList.get(0));
        for(int i = 0; i < playerList.size(); i++){
        //System.out.println(playerList.get(i));
        }
    }
}
