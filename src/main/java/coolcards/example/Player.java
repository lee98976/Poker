package coolcards.example;

public class Player {
    String playerName;
    int playerMoney;
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
