package coolcards.example;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;
import java.lang.Thread;

public class PokerGame {
    // Game Variables
    int currentDealer = 0;
    int standardBet; // What you have to reach in order to currently play
    int bigBlind;
    int smallBlind;
    int bigPot;
    int smallPot;
    int FPTNP;

    int sessionNumber;
    ArrayList<Card> river = new ArrayList<Card>();
    Deck myDeck;
    ArrayList<Player> playerList;
    Scanner scanner;

    public PokerGame() {
        playerList = new ArrayList<Player>();
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

        for (int i = 0; i < opponentCount + 1; i++) {
            if (i == 0) {
                playerList.add(new Player(playerName, startingMoney, true));
            } else {
                playerList.add(new Player("Computer" + i, startingMoney, false));
            }

            // System.out.println(playerList.get(i));
        }
    }

    public void StartGame() throws InterruptedException {
        while (true) {
            ResetSessionData();
            RunSession();
            // When players reach zero money, kick the player out
            // When there is one player, break out and announce the winner
        }
    }

    /*
     * All data is reset to begin a new session
     */
    public void ResetSessionData() {
        sessionNumber += 1;
        System.out.println("\n...Beginning session: " + sessionNumber);
        myDeck = new Deck();
        myDeck.Shuffle();

        for (int i = 0; i < playerList.size(); i++) {
            Hand newHand = myDeck.GetNewHand();
            playerList.get(i).SetHand(newHand);
        }
    }

    private void SetBlinds() {
        // Set initial bets
        System.out.println("Setting Blind Payments");
        Player dealer = playerList.get(currentDealer);
        PayMoney(dealer, bigBlind);
        int temp = currentDealer + 1;
        if (temp > 3)
            temp = 0;
        Player nextDealer = playerList.get(temp);
        PayMoney(nextDealer, smallBlind);
    }

    public void RunSession() throws InterruptedException {
        SetBlinds();

        while (true) {
            RunCycle();
            RevealCard();
        }

        if (river.size() == 5) { // Showdown
            RunTurn(); // Run a final turn
            System.out.println("Showdown");
            ArrayList<Integer> deckValue = new ArrayList<Integer>();
            for (int i : playersStillInIndex) {
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

        // Reset
        river.clear();
        // playersStillInIndex.clear();
        bigBlind = (int) Math.ceil(1.25 * bigBlind);
        smallBlind = (int) Math.ceil(0.5 * bigBlind);
        bigPot = 0;
        smallPot = 0;

        currentDealer += 1;
        if (currentDealer > 3)
            currentDealer -= 4;
    }

    public void RunCycle() throws InterruptedException {
        int currentPlayer = currentDealer;

        while (true) {
            playerList.get(currentPlayer).DoTurn(standardBet);
            UpdateScreen();

            // Pass the turn
            currentPlayer += 1;
            if (currentPlayer > 3)
                currentPlayer = 0;

        }

    }

    public void UpdateScreen() {
        clearScreen();
        FancyTextWrap(playerList.get(0).toString());
        FancyTextWrap(playerList.get(1).toString());
        FancyTextWrap(playerList.get(2).toString());
        FancyTextWrap(playerList.get(3).toString());
        System.out.println("----------------------------------------------------------------------");
    }

    public void FancyTextWrap(String text) {
        System.out.println("----------------------------------------------------------------------"); // 70 chars
        if (text.length() < 70) {
            int amountOnSide;
            amountOnSide = (int) (70 - text.length()) / 2;
            System.out.print(new String(new char[amountOnSide]).replace("\0", "-"));
            System.out.print(text);
            System.out.println(new String(new char[amountOnSide]).replace("\0", "-"));
        } else {
            System.out.println(text);
        }
    }

    public void ComputerOptions(int playerIndex) {
        Random rand = new Random();
        int option = rand.nextInt(1, 8);

        if (option < 5)
            CheckAndCallMove(playerIndex);
        else if (option < 7)
            ComputerRaiseMove(playerIndex);
        else if (option < 8)
            FoldMove(playerIndex);
    }

    void ComputerRaiseMove(int playerIndex) {
        Random rand = new Random();
        Player computer = playerList.get(playerIndex);
        int minimumRaise = computer.getPlayerMoney() / 100;
        int maxRaise = computer.getPlayerMoney() / 10;
        int raiseAmount = rand.nextInt(minimumRaise, maxRaise);
        standardBet += raiseAmount;
        PayMoney(computer, raiseAmount + standardBet - computer.getCurrentBet());
        System.out.println(
                computer.GetName() + " has raised by " + raiseAmount + " dollars. Money: " + computer.getPlayerMoney());
        FPTNP = playerIndex;
    }

    public void PayMoney(Player player, int money) {
        bigPot += money;
        player.setCurrentBet(money + player.getCurrentBet());
        player.setPlayerMoney(player.getPlayerMoney() - money);
        System.out.println(player.GetName() + " has bet " + money + " dollars");
    }

    public void RevealCard() {
        if (river.size() == 0) {
            river.add(myDeck.DrawCard());
            river.add(myDeck.DrawCard());
            river.add(myDeck.DrawCard());
        } else if (river.size() == 3) {
            river.add(myDeck.DrawCard());
        } else if (river.size() == 4) {
            river.add(myDeck.DrawCard());
        }
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
