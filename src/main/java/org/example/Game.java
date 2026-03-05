// Card Game by Dylan Kothari
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

    public void playGame() {
        boolean isRank = false;
        boolean isGameOver = false;
        int rankIndex = 0;
        int declaration = 0;
        int index = 0;
        int numChallengingPlayer = 0;
        int winningPlayer = 0;
        Scanner input = new Scanner(System.in);
        // While loop that continues until the game is over (which is based on one player having an empty hand.)
        while (!isGameOver) {

            for (int j = 0; j < currentPlayers.size(); j++) {
                if (!isGameOver) {

                    /* Keeps track of whose turn it is. */
                    currentPlayerIndex = j;

                    // The player's hand is sorted by rank
                    currentPlayers.get(j).sort();
                    // Prints out hand to the user
                    System.out.println(currentPlayers.get(j).getName() + ": Here is your hand:");
                    System.out.println(currentPlayers.get(j).printHand());
                    // User is prompted to choose how many cards they wish to play
                    System.out.println(currentPlayers.get(j).getName() + ": How many " + ranks[rankIndex] + "s do you wish to play?");
                    declaration = input.nextInt();
                    while ((declaration <= 0) || (declaration > 4) || (declaration > currentPlayers.get(j).getHand().size())) {
                        System.out.println("You may only play between 1 and 4 cards per turn, and no more than the number of cards in your hand.");
                        // User is prompted to select the indices of the cards they want to play
                        System.out.println(currentPlayers.get(j).getName() + ": How many " + ranks[rankIndex] + "s do you wish to play?");
                        declaration = input.nextInt();
                    }

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

                    // If there is a challenger, then a follow-up question is asked to identify who the challenger is.
                    if (cheatQuestion == 1) {
                        System.out.println("What player number is challenging?");
                        for (int i = 0; i < currentPlayers.size(); i++) {
                            System.out.println("Player " + i + ": " + currentPlayers.get(i).getName());
                        }
                        numChallengingPlayer = input.nextInt();
                        // Reprompts the user if the index of the player challenging is the same as the one being challenged
                        while (numChallengingPlayer == j) {
                            System.out.println("A player can not challenge themselves. The challenge must be made by a different player number.");
                            System.out.println("What player number is challenging?");
                            numChallengingPlayer = input.nextInt();
                        }

                        /* The boolean variable isRank is used to track whether all the cards played on this turn
                        matched the specified rank, which determines whether the success or failure of a potential challenge.
                         */
                        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
                        // If the challenge fails, the cards will be moved from the pot to the challenger's hand.
                        if (isRank) {
                            System.out.println("The challenge failed");
                            while (pot.size() > 0) {
                                currentPlayers.get(numChallengingPlayer).addCard(pot.get(0));
                                pot.remove(0);
                            }
                        }
                        // If the challenge succeeds, the cards will be moved from the pot to the lier's hand.
                        else {
                            System.out.println("The challenge was a success");
                            while (pot.size() > 0) {
                                currentPlayers.get(j).addCard(pot.get(0));
                                pot.remove(0);
                            }
                        }
                    }
                    isRank = false;
                    // Check to see if the current player's hand is empty which means they have won
                    if (currentPlayers.get(j).getHand().isEmpty()) {
                        isGameOver = true;
                        winningPlayer = j;
                    }

                }
                // Iterates through ranks in increasing order and loops back
                rankIndex++;
                if (rankIndex == ranks.length) {
                    rankIndex = 0;
                }
                // Resets the window for the next user's turn
                gameState = NEXTTURN_STATE;
                window.repaint();

                System.out.println("Press 1 when the next player is ready for their turn.");
                int readyInput = input.nextInt();
                // Shifts to end game screen when game is over
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

    /* This is the main method which initializes the Game object and calls the playGame method
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


