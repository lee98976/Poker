package coolcards.example;

public class Main {
    public static void main(String[] args) {
        // System.out.println("Select Your Game: ");
        
        // 
        // Player player1 = new Player("Me", 1000);
        // Player player2 = new Player("Bob", 1000);

        // myDeck.Shuffle();
        // myDeck.PrintCards();

        // Card drawnCard = myDeck.DrawCard();
        // System.out.println("\n" + drawnCard);

        PokerGame game = new PokerGame();
        game.Setup();
        game.StartGame();
        //game.PrintAllPlayerStatus();
    }
}