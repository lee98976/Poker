package coolcards.example;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;


public class PokerGame {
    //Game Variables
    int currentDealer = 0;
    int standardBet; //What you have to reach in order to currently play
    int bigBlind;
    int smallBlind;
    int bigPot;
    int smallPot;                                                                       
    int FPTNP;
    ArrayList<Integer> playersStillInIndex = new ArrayList<Integer>();
    ArrayList<Card> river = new ArrayList<Card>();
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
            RunSession();
            //When players reach zero money, kick the player out
            //When there is one player, break out and announce the winner
        }
    }

    public void UpdateScreen(){
        clearScreen();
        FancyTextWrap(playerList.get(0).toString());
        FancyTextWrap(playerList.get(1).toString());
        FancyTextWrap(playerList.get(2).toString());
        FancyTextWrap(playerList.get(3).toString());
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

    public void CheckAndCallMove(int playerIndex){
        //System.out.println("You have checked at no additional expense.");
        PayMoney(playerList.get(playerIndex), standardBet - playerList.get(playerIndex).getCurrentBet());
    }

    public void Options(int playerIndex){
        int option = scanner.nextInt();

        if (option == 1) CheckAndCallMove(playerIndex);
        else if (option == 2) RaiseMove(playerIndex);
        else if (option == 3) FoldMove(playerIndex);
    }

    public void ComputerOptions(int playerIndex) {
        Random rand = new Random();
        int option = rand.nextInt(1, 8);


        if (option < 5) CheckAndCallMove(playerIndex);
        else if (option < 7) ComputerRaiseMove(playerIndex);
        else if (option < 8) FoldMove(playerIndex);
    }

    void ComputerRaiseMove(int playerIndex) {
        Random rand = new Random();
        Player computer = playerList.get(playerIndex);
        int minimumRaise = computer.getPlayerMoney() / 100;
        int maxRaise = computer.getPlayerMoney() / 10;
        int raiseAmount = rand.nextInt(minimumRaise, maxRaise);
        standardBet += raiseAmount;    
        PayMoney(computer, raiseAmount + standardBet - computer.getCurrentBet());
        System.out.println(computer.GetName() + " has raised by " + raiseAmount + " dollars. Money: " + computer.getPlayerMoney());
        FPTNP = playerIndex;
    }

    

    public void RaiseMove(int playerIndex){
        System.out.println("How much money would you like to raise the standard bet by?");
        int raiseAmount = scanner.nextInt();
        int playerMoney = playerList.get(playerIndex).getPlayerMoney();
        if (playerMoney < raiseAmount + playerList.get(playerIndex).getCurrentBet()){
            System.out.println("You can't raise more money than you have.");
            Options(playerIndex); //Try again
        }
        else {
            standardBet += raiseAmount;
            PayMoney(playerList.get(playerIndex), raiseAmount + standardBet - playerList.get(playerIndex).getCurrentBet());
            System.out.println("You have raised by " + raiseAmount + " dollars. Money: " + playerList.get(playerIndex).getPlayerMoney());
            FPTNP = playerIndex;
        }    
    }

    public void FoldMove(int playerIndex){
        System.out.println(playerList.get(playerIndex).GetName() + " has folded!");
        //Remove the player from session
        for (int i = 0; i < playersStillInIndex.size(); i++) {
            if (playersStillInIndex.get(i) == playerIndex) {
                playersStillInIndex.remove(i);
            }
        }
    }

    public void RunTurnOnePlayer(int playerIndex){
        if (playerIndex == 0) { //Run player turn
            if (playerList.get(playerIndex).getCurrentBet() == standardBet){
                System.out.println("1: Check, 2: Raise, 3: Fold");
                Options(playerIndex);
            } 
            else { 
                System.out.println("1: Call, 2: Raise, 3: Fold");
                Options(playerIndex);
            }
        }
        else { //Run Computer Turn
            System.out.println(playerList.get(playerIndex).GetName() + "'s turn");
            ComputerOptions(playerIndex);
        }
    }

    
    public void PayMoney(Player player, int money){
        bigPot += money;
        player.setCurrentBet(money + player.getCurrentBet());
        player.setPlayerMoney(player.getPlayerMoney() - money);
        System.out.println(player.GetName() + " has bet " + money + " dollars");
    }

    private void setInitialBets(){
        //Set initial bets
        Player dealer = playerList.get(currentDealer);
        PayMoney(dealer, bigBlind);
        int temp = currentDealer + 1;
        if (temp > 3) temp = 0;
        Player nextDealer = playerList.get(temp);
        PayMoney(nextDealer, smallBlind);
    }

    public void RunSession(){
        for (int i = 0; i < playerList.size(); i++) {
            playersStillInIndex.add(i);
            playerList.get(i).setCurrentBet(0);
        }
        setInitialBets();
        System.out.println("running Cycle now");
        RunCycle();

        if (river.size() == 5) { //Showdown
            RunTurn(); //Run a final turn
            System.out.println("Showdown");
            ArrayList<Integer> deckValue = new ArrayList<Integer>();
            for (int i: playersStillInIndex){
                ArrayList<Card> combinedCards = new ArrayList<Card>();
                combinedCards.addAll(river);
                combinedCards.addAll(playerList.get(i).getHand().getCards());
                Hand combinedHand = new Hand();
                combinedHand.SetHand(combinedCards);
                combinedHand.Sort();
                combinedHand.Evaluate();
                int someValue = combinedHand.getRank() * 20 - combinedHand.getHighCardValue();
                deckValue.add(someValue);
            }
            int min = Integer.MAX_VALUE;
            int winnerIndex = 929929;
            for (int i = 0; i < deckValue.size(); i++) {
                if (deckValue.get(i) < min) {
                    winnerIndex = i;
                    min = deckValue.get(i);
                }
            }
            winnerIndex = deckValue.get(winnerIndex);
            System.out.println("Player " + winnerIndex + " has won!");
            playerList.get(winnerIndex).setPlayerMoney(playerList.get(winnerIndex).getPlayerMoney() + bigPot);
            bigPot = 0;
            smallPot = 0;
            return;
        }

        //Reset
        river.clear();
        playersStillInIndex.clear();
        bigBlind = (int) Math.ceil(1.25 * bigBlind);
        smallBlind = (int) Math.ceil(0.5 * bigBlind);
        bigPot = 0;
        smallPot = 0;

        currentDealer += 1;
        if (currentDealer > 3) currentDealer -= 4;
    }
    public void RevealCard(){
        if (river.size() == 0) {
            river.add(myDeck.DrawCard());
            river.add(myDeck.DrawCard());
            river.add(myDeck.DrawCard());
        }
        else if(river.size()==3) {
            river.add(myDeck.DrawCard());
        }
        else if (river.size()==4) {
            river.add(myDeck.DrawCard());
        }
    }

    public void RunTurn(){
        int currentPlayer = currentDealer;
        FPTNP = currentPlayer;
        while (true) {
            System.out.println(playersStillInIndex);
            if (playersStillInIndex.contains(currentPlayer)) {
                System.out.println(("Turn:" + currentPlayer));
                RunTurnOnePlayer(currentPlayer);
                UpdateScreen();
            
                currentPlayer += 1;
                if (currentPlayer > 3) currentPlayer = 0;
                if (currentPlayer == FPTNP) {
                   break;
                }
            }
            else {
                currentPlayer += 1;
            }
            
        }
        System.out.println("The turn has been completed.");
    } 

    
    public static void clearScreen() {  
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }

    public void RunCycle(){
        while (true) {
            System.out.println("running turn now");
            RunTurn();
            RevealCard();
        }
    }
}
