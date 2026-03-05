package org.example;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    private Deck cardDeck;
    private ArrayList<Card> pot;
    private ArrayList<Player> currentPlayers;
    private String[] ranks = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
    private Gameview window;
    private int currentPlayerIndex;
    private int lastTurnQuantity;
    private String lastTurnRank;
    //private Boolean
    private int gameState;
    public static final int PREGAME_STATE = 0;
    public static final int INTURN_STATE = 1;
    public static final int POSTTURN_STATE = 2;
    public static final int NEXTTURN_STATE = 3;
    public static final int POSTGAME_STATE = 4;




    /* The Game class constructor initializes several instance variables. To initialize the arrayList of players,
    it asks the users to enter in their names in the terminal. After the players are created, the full deck of cards
    is distributed into across the hands of all the users.
     */
    public Game() {
        this.window = new Gameview(this);
        pot = new ArrayList<>();
        String[] suits = {"Spades", "Hearts", "Diamonds", "Clubs"};
        int[] values = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
        cardDeck = new Deck(ranks, suits, values);
        cardDeck.shuffle();
        int playerIndex = 0;
        gameState = PREGAME_STATE;
        printInstructions();
        currentPlayers = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        System.out.println("What is the first player's name (minimum of 2 players is required): ");
        String name = scanner.nextLine();
        while (name.length() != 0) {
            Player d = new Player(name);
            currentPlayers.add(d);
            System.out.println("What is the next player's name (please enter an empty line if all players have already been entered):");
            name = scanner.nextLine();
        }

        /* This code block is used to distribute the deck of cards to all the players. One card at a time
        is removed from the deck (using the deal method) and is added to a user's hand. This process is repeated until
        the deck is empty.
         */
        while (!cardDeck.isEmpty())
        {
            currentPlayers.get(playerIndex).addCard(cardDeck.deal());
            playerIndex = (playerIndex + 1) % currentPlayers.size();
        }
        gameState = INTURN_STATE;
        window.repaint();

    }
    public ArrayList<Player> getCurrentPlayers()
    {
        return currentPlayers;
    }


    /* The printInstructions method simply outputs a text description of the game. */

    public void printInstructions() {
        System.out.println("\nYou will be given a hand of cards, and the specific rank of the card that must be played will ");
        System.out.println("start at the lowest rank and go up in increasing order. You must declare how many cards of the specific rank you will");
        System.out.println("be playing. This goes around to all of the players. You can lie about how many you have, but if");
        System.out.println("someone else correctly calls that you are lying, then you have to pick up all of the cards from the");
        System.out.println("central pile, which is where everyone has put their cards. However, if the challenging player is wrong about you lying,");
        System.out.println("then they must pick up all of the cards in the pile. The first person to discard all of their cards");
        System.out.println("is the winner!\n");
    }


    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    /* The playGame method controls the overall game-play for "Cheat". The high-level control mechanism is a while
        loop that continues until the game is over (which is based on one player having an empty hand.) Each player takes turns
        playing cards for the specified rank. A player plays cards by first declaring how many cards they wish to play on the turn,
        and then specifying which cards they want to play via their index values. The assumption is that only the current player
        should be viewing the terminal during their turn. After a player's turn, any other player can challenge (call "cheat").
        If the challenge is successful, then the lying player takes all the cards from the central pot into their hand. And if the
        challenge is not successful because the player was telling the truth, then the challenger receives all the cards from the
        central pot. The game play continues by iterating through the card ranks, and with each player taking turns in sequence, until
        one player has emptied their hand and becomes the winner.
         */
    public void playGame() {
        boolean isRank = false;
        boolean isGameOver = false;
        int rankIndex = 0;
        int declaration = 0;
        int index = 0;
        int numChallengingPlayer = 0;
        int winningPlayer = 0;
        Scanner input = new Scanner(System.in);

        //gameState = 0;
        //printInstructions();
        /*window.repaint();
        int tempval = input.nextInt();
        gameState = 1;
        window.repaint();*/

        while (!isGameOver) {

            for (int j = 0; j < currentPlayers.size(); j++) {
                if (!isGameOver) {

                    /* Display of the pot was added for testing purposes. */
                    //System.out.println("Here is what is in the pot:");
                    //System.out.println(pot);
                    currentPlayerIndex = j;

                    // the hand needs to be sorted

                    currentPlayers.get(j).sort();
                    System.out.println(currentPlayers.get(j).getName() + ": Here is your hand:");
                    System.out.println(currentPlayers.get(j).printHand());
                    // add code here to display on the screen the user's hand

                    System.out.println(currentPlayers.get(j).getName() + ": How many " + ranks[rankIndex] + "s do you wish to play?");
                    declaration = input.nextInt();
                    while ((declaration <= 0) || (declaration > 4) || (declaration > currentPlayers.get(j).getHand().size())) {
                        System.out.println("You may only play between 1 and 4 cards per turn, and no more than the number of cards in your hand.");
                        System.out.println(currentPlayers.get(j).getName() + ": How many " + ranks[rankIndex] + "s do you wish to play?");
                        declaration = input.nextInt();
                    }

                    /* The user's hand is printed out on the screen and they are prompted to select the index of the card
                    they wish to play.
                     */
                    for (int x = 1; x <= declaration; x++) {
                        System.out.println(currentPlayers.get(j).getName() + ": Here is your hand:");
                        System.out.println(currentPlayers.get(j).printHand());
                        System.out.println("For card " + x + ", please enter the index of the card you want to play?");
                        index = input.nextInt();
                        pot.add(currentPlayers.get(j).getHand().get(index));
                        if (currentPlayers.get(j).getHand().get(index).getRank().equals(ranks[rankIndex])) {
                            isRank = true;
                        }
                        currentPlayers.get(j).removeCard(index);
                    }

                    gameState = POSTTURN_STATE;
                    lastTurnQuantity = declaration;
                    lastTurnRank = ranks[rankIndex];
                    window.repaint();
                    System.out.println(currentPlayers.get(j).getName() + " played " + declaration + " " + ranks[rankIndex] + "s");
                    System.out.println("Does anyone think that they are lying and want to call cheat? Answer 1 if yes or answer 2 if no");
                    int cheatQuestion = input.nextInt();

                    /* To streamline the game, we do not go around and ask each player if they want to challenge.
                    Instead all players are asked at once, and if there is a challenger, then a follow-up question
                    is asked to identify who the challenger is. This is important so we can correctly move the cards
                    from the pot to the correct challenging player if the challenge fails.
                     */

                    if (cheatQuestion == 1) {
                        System.out.println("What player number is challenging?");
                        for (int i = 0; i < currentPlayers.size(); i++) {
                            System.out.println("Player " + i + ": " + currentPlayers.get(i).getName());
                        }
                        numChallengingPlayer = input.nextInt();

                        while (numChallengingPlayer == j) {
                            System.out.println("A player can not challenge themselves. The challenge must be made by a different player number.");
                            System.out.println("What player number is challenging?");
                            numChallengingPlayer = input.nextInt();
                        }

                        /* The boolean variable isRank is used to track whether all the cards played on this turn
                        matched the specified rank. Then that variable can be used to control the successful vs failed
                        challenge flows.
                         */
                        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
                        if (isRank) {
                            System.out.println("The challenge failed");
                            while (pot.size() > 0) {
                                currentPlayers.get(numChallengingPlayer).addCard(pot.get(0));
                                pot.remove(0);
                            }
                        } else {
                            System.out.println("The challenge was a success");
                            while (pot.size() > 0) {
                                currentPlayers.get(j).addCard(pot.get(0));
                                pot.remove(0);
                            }
                        }
                    }
                    isRank = false;
                    // check to see if the current player has won
                    if (currentPlayers.get(j).getHand().isEmpty()) {
                        isGameOver = true;
                        winningPlayer = j;
                    }

                }
                rankIndex++;
                if (rankIndex == ranks.length) {
                    rankIndex = 0;
                }
                gameState = NEXTTURN_STATE;
                window.repaint();

                System.out.println("Press 1 when the next player is ready for their turn.");
                int readyInput = input.nextInt();

                if (isGameOver) gameState = POSTGAME_STATE;
                else gameState = INTURN_STATE;
                window.repaint();
            }
        }
        System.out.println("Congratulations " + currentPlayers.get(winningPlayer).getName() + " for winning this game!");




    }


    public ArrayList<Card> getPot() {
        return pot;
    }

    public int getGameState() {
        return gameState;
    }

    /* This is the main method which initializes the Game object and calls the playGame method which controls
       the overall gameplay for "Cheat".
     */
    public static void main(String[] args)
    {
        Game c = new Game();
        c.playGame();
    }

    public int getLastTurnQuantity() {
        return lastTurnQuantity;
    }

    public String getLastTurnRank() {
        return lastTurnRank;
    }
}


