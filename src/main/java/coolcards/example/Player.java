package coolcards.example;

public class Player {
    String playerName;
    int playerMoney;
    int currentBet; //How much money you put it
    public int getCurrentBet() {
        return currentBet;
    }

    public void setCurrentBet(int currentBet) {
        this.currentBet = currentBet;
    }

    Hand playerHand;

    public Player(String playerName, int playerMoney) {
        playerHand = new Hand();
        this.playerName = playerName;
        this.playerMoney = playerMoney;
    }

    public void SetHand(Hand playerHand){
        this.playerHand = playerHand;
    }

    @Override
    public String toString() {
        return playerName + ", Money: " + playerMoney +", Hand: " + playerHand.toString();
    }
}
