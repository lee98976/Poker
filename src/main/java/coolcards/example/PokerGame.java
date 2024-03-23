package coolcards.example;

import java.util.ArrayList;
import java.util.Scanner;

public class PokerGame {
    //Game Variables
    int currentDealer = 0;
    int standardBet; //What you have to reach in order to currently play
    int bigBlind;
    int smallBlind;
    int bigPot;
    int smallPot;
    ArrayList<Integer> playersStillInIndex = new ArrayList<Integer>();
    Deck myDeck;
    ArrayList<Player> playerList;
    Scanner scanner;

    public PokerGame() {
        playerList = new ArrayList<Player>();
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

        while (true) {
            playersStillInIndex.clear();
            for (int i = 0; i < playerList.size(); i++) {
                playersStillInIndex.add(i);
            }
            RunSession();
            //When players reach zero money, kick the player out
            //When there is one player, break out and announce the winner
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
            if (playerList.get(playerIndex).getCurrentBet() == standardBet){ //They are the dealer.
                System.out.println("1: Check, 2: Raise, 3: Fold");
                int option = scanner.nextInt();
                int raiseAmount = 0;

                if (option == 1) {
                    System.out.println("You have checked at no additional expense.");
                }
                else if (option == 2) {
                    System.out.println("How much money would you like to raise the pot by?");
                    raiseAmount = scanner.nextInt();
                    System.out.println("You have raised by " + raiseAmount + " dollars.");
                    if(playerList.get(playerIndex).getPlayerMoney() < raiseAmount + playerList.get(playerIndex).getCurrentBet()){
                        System.out.println("You can't raise more money than you have.");  
                    }
                    playerList.get(playerIndex).setPlayerMoney(playerList.get(playerIndex).getPlayerMoney()); //WORK IN PROGRESS
                }
                else if (option == 3) {
                    System.out.println(playerList.get(playerIndex).GetName() + " has folded!");
                    //Remove the player from session
                    playersStillInIndex.remove(playerIndex);
                }
            } 
            else {
                System.out.println("1: Call, 2: Raise, 3: Fold");
            }
    
        }
        else { //Run Computer Turn
            System.out.println(playerList.get(playerIndex).GetName() + "'s turn");
        }
        }
    }
   
    

    public void RunSession(){
        while (true) {
            RunCompleteTurn();
            bigBlind = (int) Math.ceil(1.25 * bigBlind);
            smallBlind = (int) Math.ceil(0.5 * bigBlind);
            bigPot = 0;
            smallPot = 0;

            currentDealer += 1;
            if (currentDealer > 3) currentDealer -= 4;
            
            //When all cards are revealed, break out and then evaluate
            //Then, give all the money to the winner
        }
    }
    public void RunCompleteTurn(){
        for (Integer i : playersStillInIndex) {
            RunTurnOnePlayer(i);
        }
        System.out.println("The turn has been completed.");
    }
}
