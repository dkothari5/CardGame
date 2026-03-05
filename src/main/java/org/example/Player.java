package org.example;
import java.util.ArrayList;

public class Player {

    private ArrayList<Card> hand;
    private int points;
    private String name;

    /* This constructor method takes the player's name and is the variant used in my game. */
    public Player(String name) {
        this.name = name;
        this.points = 0;
        this.hand = new ArrayList<Card>();
    }

    public Player(String name, ArrayList<Card> hand, int points) {
        this.name = name;
        this.hand = hand;
        this.points = points;
    }

    public ArrayList<Card> getHand() {
        return this.hand;
    }

    public int getPoints() {
        return this.points;
    }

    public String getName() {
        return this.name;
    }

    public void addPoints(int point) {
        points+= point;
    }

    public void addCard(Card newCard) {
        hand.add(newCard);
    }

    public void removeCard(int index) {
        hand.remove(index);
    }

    /* This printHand method was created to display the user's hand in the terminal in a way that each
    card is shown by its index value.
     */
    public String printHand()
    {
        String printedHand = "";
        for (int i =0; i<hand.size(); i++)
        {
            printedHand += i + ":" + hand.get(i) + "; ";
        }
        return printedHand;
    }
    // Bubble sort method to sort a hand by rank
    public void sort()
    {
        int n = hand.size();
        boolean swapped;
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if(hand.get(j).getValue() > hand.get(j+1).getValue())
                {
                    sortSwapper(j);
                    swapped = true;
                }
            }
            if(!swapped)
            {
                break;
            }
        }

    }
    // Helper method for sort to swap values between objects
    private void sortSwapper(int index) {

        Card temp = null;
        temp = hand.get(index);
        hand.set(index, hand.get(index + 1));
        hand.set(index + 1, temp);
    }

    public String toString()
    {
        return name + " has " + points + " " + name + "'s cards: " + hand;
    }
}

