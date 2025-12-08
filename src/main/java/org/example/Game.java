package org.example;

import java.util.ArrayList;

public class Game {
    private Deck cardDeck;
    public Game()
    {
        String[] ranks = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        String[] suits = {"Hearts", "Clubs", "Spades", "Diamonds"};
        int [] values = {1,2,3,4,5,6,7,8,9,10,11,12,13};
        cardDeck = new Deck(ranks, suits, values);
        ArrayList<Player> currentPlayers;
        currentPlayers = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        System.out.println("What is your name: ");
        String name = scanner.nextLine();
        while(name != null)
        {
            Player d = new Player(name);
            currentPlayers.add(d);
            System.out.println("What is your name: ");
            name = scanner.nextLine();
        }
        for(int i = 0; i < theDeck.size(); i++)
        {
            int playerIndex = 0;
            int randomCard = (int)(Math.random() * theDeck.size());
            currentPlayers.get(playerIndex).getHand().add(cardDeck.get(randomCard));
            cardDeck.remove(randomCard);
            playerIndex = (playerIndex + 1) % currentPlayers.size();
        }

    }
    public void printInstructions()
    {
        System.out.print("You will be given a hand of cards, and the specific rank of the card that must be played will " +
                "start at 1 and go in increasing order. You must declare how many of the specific rank, cards you will " +
                "be playing." + "This goes around to all of the players." + "You can lie about how many you have, but if" +
                " someone else " + "predicts that you are lying, then you have to" + "pick up all of the cards from the " +
                "pile, which is where everyone has put their cards. However, if that person" + "is wrong about you lying," +
                " then they must pick up all of the cards in the pile. The first person to discard" + "all of their cards" +
                " is the winner!");

    }
    public void playGame()
    {

        for(int i = 0; i < ranks.length; i++)
        {
            Scanner input = new Scanner(System.in);
            String declaration = scanner.nextLine();
            if()
        }

    }
}


