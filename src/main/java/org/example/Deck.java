package org.example;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
public class Deck {
    private ArrayList<Card> theDeck;
    private int cardsLeft;


    /* This constructor initializes the theDeck instance variable by adding a new Card for
    each unique combination of rank and suit.
     */
    public Deck(String[] ranks, String[] suits, int[] pointValues) {
        int counter = 1;
        theDeck = new ArrayList<>();
        for(int i = 0; i < ranks.length; i++) {
            for(int j = 0; j < suits.length; j++) {
                Card c = new Card(ranks[i], suits[j], pointValues[i], new ImageIcon("Resources/Cards/"+counter+".png").getImage());
                counter++;
                theDeck.add(c);
            }
        }
        cardsLeft = theDeck.size();
        shuffle();
    }

    public ArrayList<Card> getTheDeck() {
        return theDeck;
    }

    public boolean isEmpty() {
        return cardsLeft == 0;

    }

    public int getCardsLeft() {
        return this.cardsLeft;
    }

    /* The deal method returns a Card using the cardsLeft instance variable as an index. The method
    decrements the cardsLeft variable when dealing out one card.
     */
    public Card deal() {
        if(cardsLeft == 0)
        {
            return null;
        }
        return theDeck.get(cardsLeft-- - 1);
    }


    /* The shuffle method simulates "shuffling the deck" by moving Cards within theDeck instance
    variable. Cards are moved to random positions using a randon number generator.
     */
    public void shuffle() {
        Card temp  = null;
        int num = 0;
        for(int i = theDeck.size() - 1; i >= 0; i--) {
            num  = (int)(Math.random() * (i+1));
            temp = theDeck.get(num);
            theDeck.set(num, theDeck.get(i));
            theDeck.set(i,temp);
        }
        cardsLeft = theDeck.size();
    }



}
