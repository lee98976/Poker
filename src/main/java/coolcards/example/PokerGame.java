package coolcards.example;

import java.util.ArrayList;
import java.util.Scanner;

public class PokerGame {
    int currentDealer = 0;
    int bigBlind;
    int smallBlind;
    int standardBet; //What you have to reach in order to currently play
    
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

        String playerName = "Joe";
        int opponentCount = 3;
        int startingMoney = 10000;
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

    public void PrintScreen(){
        FancyTextWrap(playerList.get(0).toString());
        FancyTextWrap(playerList.get(1).toString());
        FancyTextWrap(playerList.get(2).toString());
        System.out.println("----------------------------------------------------------------------");
    }

    public void FancyTextWrap(String text){
        System.out.println("----------------------------------------------------------------------"); // 70 chars
        if (text.length() < 70){
            int amountOnSide;
            amountOnSide = (int) (70-text.length())/2;
            System.out.print(new String(new char[amountOnSide]).replace("\0", "-"));
            System.out.print(text);
            System.out.println(new String(new char[amountOnSide]).replace("\0", "-"));
        }
        else {
            System.out.println(text);
        }
    }

    public void PrintAllPlayerStatus(){
        System.out.println(playerList.get(0));
        for(int i = 0; i < playerList.size(); i++){
        //System.out.println(playerList.get(i));
        }
    }

    public void RunTurnOnePlayer(int playerIndex){
        if (playerIndex == currentDealer) { //Dealer
            playerList.get(playerIndex).setCurrentBet(bigBlind);
        }
        else if (playerIndex == currentDealer + 1 || playerIndex == currentDealer - 3) { //Next for dealer
            playerList.get(playerIndex).setCurrentBet(smallBlind);
        }

        if (playerIndex == 0) { //Run player turn
            if (playerList.get(playerIndex).getCurrentBet() == standardBet){
                System.out.println("1: Check, 2: Raise, 3: Fold");
            }
            else {
                System.out.println();
            }
    
        }
        else { //Run Computer Turn
            //
        }
    }

    public void RunCompleteTurn(){
        RunTurnOnePlayer(0);
        RunTurnOnePlayer(1);
        RunTurnOnePlayer(2);
        RunTurnOnePlayer(3);
        currentDealer += 1;
        if (currentDealer > 3) currentDealer -= 4;
    }

}
