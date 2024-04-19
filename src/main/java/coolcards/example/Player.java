package coolcards.example;

import java.util.Scanner;

public class Player {
    String playerName;
    int playerMoney;
    int currentBet; // How much money you put it
    Hand playerHand;
    boolean isPlayerControlled = false;

    boolean isFolded = false;

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
    }

    public void DoTurn(int standardBet) {
        if (isFolded)
            return;

        if (isPlayerControlled) {
            if (currentBet >= standardBet) {
                System.out.println("1: Check, 2: Raise, 3: Fold");
                PickOption(standardBet);

            } else {
                System.out.println("1: Call, 2: Raise, 3: Fold");
            }
        } else {
            System.out.println(playerName + "'s turn");
        }
    }

    public void PickOption(int standardBet) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            int option = scanner.nextInt();
            if (option == 1)
                CheckAndCallMove(standardBet);
            else if (option == 2)
                RaiseMove(standardBet);
            else if (option == 3)
                FoldMove();
            else {
                System.out.println("Invalid Input");
                continue;
            }
            break;
        }
        scanner.close();

    }

    public void CheckAndCallMove(int standardBet) {
        if (currentBet >= standardBet) {
            System.out.println(playerName + " checked\n");
        } else {
            // System.out.println("You have checked at no additional expense.");
            PayMoney(playerList.get(playerIndex), standardBet - playerList.get(playerIndex).getCurrentBet());
        }

    }

    public void RaiseMove(int playerIndex) {
        System.out.println("How much money would you like to raise the standard bet by?");
        int raiseAmount = scanner.nextInt();
        int playerMoney = playerList.get(playerIndex).getPlayerMoney();
        if (playerMoney < raiseAmount + playerList.get(playerIndex).getCurrentBet()) {
            System.out.println("You can't raise more money than you have.");
            Options(playerIndex); // Try again
        } else {
            standardBet += raiseAmount;
            PayMoney(playerList.get(playerIndex),
                    raiseAmount + standardBet - playerList.get(playerIndex).getCurrentBet());
            System.out.println("You have raised by " + raiseAmount + " dollars. Money: "
                    + playerList.get(playerIndex).getPlayerMoney());
            FPTNP = playerIndex;
        }
    }

    public void FoldMove() {
        System.out.println(playerList.get(playerIndex).GetName() + " has folded!");
        // Remove the player from session
        /*
         * for (int i = 0; i < playersStillInIndex.size(); i++) {
         * if (playersStillInIndex.get(i) == playerIndex) {
         * playersStillInIndex.remove(i);
         * }
         * }
         */
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
