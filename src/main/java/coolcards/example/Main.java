package coolcards.example;

import java.lang.Thread;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        // System.out.println("Select Your Game: ");

        //
        // Player player1 = new Player("Me", 1000);
        // Player player2 = new Player("Bob", 1000);

        // myDeck.Shuffle();
        // myDeck.PrintCards();

        // Card drawnCard = myDeck.DrawCard();
        // System.out.println("\n" + drawnCard);

        PokerGame game = new PokerGame();
        System.out.println("Let's play some Poker!\n");
        Thread.sleep(1000);
        game.Setup();
        game.StartGame();
        // game.PrintAllPlayerStatus();
    }
}