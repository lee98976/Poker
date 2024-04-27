package coolcards.example;

import java.util.Scanner;
import java.util.Random;

public class Player {
    Scanner scanner;
    String playerName;
    int playerMoney;
    int currentBet; // How much money you put it
    Hand playerHand;
    boolean isPlayerControlled = false;
    boolean isFolded = false;
    boolean isAllIn = false;

    public int getCurrentBet() {
        return currentBet;
    }

    public void setCurrentBet(int currentBet) {
        this.currentBet = currentBet;
    }

    public Hand getHand() {
        return playerHand;
    }

    public Player(String playerName, int playerMoney, boolean isPlayerControlled) {
        playerHand = new Hand();
        this.playerName = playerName;
        this.playerMoney = playerMoney;
        this.isPlayerControlled = isPlayerControlled;
        scanner = new Scanner(System.in);
    }

    public void ResetPlayer(){
        isFolded = false;
        isAllIn = false;
        currentBet = 0;
        playerHand = null;
    }

    public int DoTurn(int standardBet) {
        if (isFolded)
            return 0;

        if (isPlayerControlled) {
            if (currentBet >= standardBet) System.out.println("1: Check, 2: Raise, 3: Fold");
            else System.out.println("1: Call, 2: Raise, 3: Fold");
            return PickOption(standardBet);
        } else {
            System.out.println(playerName + "'s turn");
            return ComputerOptions(standardBet);
        }
    }

    public int PickOption(int standardBet) {
        int amount = 0;

        while (true) {
            int option = scanner.nextInt();
            if (option == 1) {
                //System.out.println("shoulda");
                amount = CheckAndCallMove(standardBet);
            }
            else if (option == 2) {
                System.out.println("How much would you like to raise?");
                int raiseAmount = scanner.nextInt();
                amount = RaiseMove(standardBet, raiseAmount);
            }
            else if (option == 3) {
                amount = FoldMove();
            }
            else {
                System.out.println("Invalid Input");
                continue;
            }
            break;
        }

        return amount;
    }

    public int CheckAndCallMove(int standardBet) {
        if (currentBet >= standardBet) {
            //Check
            System.out.println(playerName + " checked\n");
            return 0;
        } else {
            //Call
            return standardBet - currentBet;
        }

    }

    public int RaiseMove(int standardBet, int raiseAmount) {
        while(true){
            int actualAmount = 0;

            //Wrong inputs
            if (raiseAmount <= 0) {
                System.out.println("You have to raise more than $0");
                continue;
            }
            else if (playerMoney < raiseAmount + standardBet - currentBet) {
                System.out.println("You can't raise more money than you have.");
                continue;
            } 
            //Right inputs
            else {
                actualAmount = raiseAmount + standardBet - currentBet;
                System.out.println(playerName + " has raised by " + raiseAmount + " dollars.");
            }

            return actualAmount;        
        }
        
    }

    public int FoldMove() {
        System.out.println(playerName + " has folded!");
        isFolded = true;
        return 0;
    }

    //Computer Functions
    public int ComputerOptions(int standardBet) {
        Random rand = new Random();
        int option = rand.nextInt(1, 8);
        int amount = 0;

        if (option < 4){
            amount = CheckAndCallMove(standardBet);
            //System.out.println("check or call");
        }
        else if (option < 7) {
            int raiseAmount = rand.nextInt(playerMoney / 10, playerMoney / 5);
            amount = RaiseMove(standardBet, raiseAmount);
            //System.out.println("raise");
        }
        else if (option < 8) {
            amount = FoldMove();
            //System.out.println("fold");
        }
        return amount;
    }

    public void SetHand(Hand playerHand) {
        this.playerHand = playerHand;
    }

    public String GetName() {
        return playerName;
    }

    public int getPlayerMoney() {
        return playerMoney;
    }

    public void setPlayerMoney(int playerMoney) {
        this.playerMoney = playerMoney;
    }

    @Override
    public String toString() {
        return playerName + ", Money: " + playerMoney + ", Hand: " + playerHand.toString();
    }

    public String getPlayerName() {
        return playerName;
    }

    public Hand getPlayerHand() {
        return playerHand;
    }

    public boolean isPlayerControlled() {
        return isPlayerControlled;
    }

    public boolean isFolded() {
        return isFolded;
    }

    public void setFolded(boolean isFolded) {
        this.isFolded = isFolded;
    }
}
