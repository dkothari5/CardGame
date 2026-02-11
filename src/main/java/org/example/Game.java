package org.example;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    private Gameview window;
    private Deck cardDeck;
    private ArrayList<Card> pot;
    private ArrayList<Player> currentPlayers;
    private String[] ranks = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
    private String[] suits = {"Spades", "Hearts", "Diamonds", "Clubs"};
    private int[] values = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};

    /* variables used to create smaller deck sizes for testing purposes */
    //private String[] ranks = {"A", "2", "3"};
    //private int[] values = {1, 2, 3};


    /* The Game class constructor initializes several instance variables. To initialize the arrayList of players,
    it asks the users to enter in their names in the terminal. After the players are created, the full deck of cards
    is distributed into across the hands of all the users.
     */
    public Game() {
        this.window = new Gameview(this);
        pot = new ArrayList<>();
        cardDeck = new Deck(ranks, suits, values);
        cardDeck.shuffle();
        int playerIndex = 0;
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

    }

    /* The printInstructions method simply outputs a text description of the game. */

    public void printInstructions() {
        System.out.println("You will be given a hand of cards, and the specific rank of the card that must be played will " +
                "start at the lowest rank and go up in increasing order. You must declare how many cards of the specific rank  you will " +
                "be playing." + " This goes around to all of the players." + " You can lie about how many you have, but if" +
                " someone else " + "correctly calls that you are lying, then you have to" + " pick up all of the cards from the " +
                "central pile, which is where everyone has put their cards. However, if the challenging player" + "is wrong about you lying," +
                " then they must pick up all of the cards in the pile. The first person to discard" + " all of their cards" +
                " is the winner!");

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

        printInstructions();

        while (!isGameOver) {
            for (int j = 0; j < currentPlayers.size(); j++) {
                if (!isGameOver) {

                    /* Display of the pot was added for testing purposes. */
                    //System.out.println("Here is what is in the pot:");
                    //System.out.println(pot);

                    System.out.println(currentPlayers.get(j).getName() + ": Here is your hand:");
                    System.out.println(currentPlayers.get(j).printHand());
                    System.out.println(currentPlayers.get(j).getName() + ": How many " + ranks[rankIndex] + "s do you wish to play?");
                    declaration = input.nextInt();
                    while ((declaration <= 0) || (declaration > 4) || (declaration > currentPlayers.get(j).getHand().size())) {
                        System.out.println("You may only play between 1 and 4 cards per turn, and no more than the number of cards in your hand.");
                        System.out.println(currentPlayers.get(j).getName() + ": How many " + ranks[rankIndex] + "s do you wish to play?");
                        declaration = input.nextInt();
                    }

                    /* After the current player declares how many cards they wish to play on this turn, they are
                    then asked to specify which cards. Their hand is printed out to the screen to make it easier to
                    identify which card(s) to play.
                     */
                    for (int x = 1; x <= declaration; x++) {
                        System.out.println(currentPlayers.get(j).getName() + ": Here is your hand:");
                        System.out.println(currentPlayers.get(j).printHand());
                        System.out.println("For card " + x + ", please enter the index of the card you want to play?");
                        index = input.nextInt();
                        pot.add(currentPlayers.get(j).getHand().get(index));
                        if (currentPlayers.get(j).getHand().get(index).getRank() == ranks[rankIndex]) {
                            isRank = true;
                        }
                        currentPlayers.get(j).removeCard(index);
                    }
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
            }
        }
        System.out.println("Congratulations " + currentPlayers.get(winningPlayer).getName() + " for winning this game!");




    }
}


