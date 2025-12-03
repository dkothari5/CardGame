package org.example;
import java.util.ArrayList;

public class Player {

    private ArrayList<Card> hand;
    private int points;

    public Player(String name) {
        this.name = name;
        int points = 0;

    }

    public Player(String name, ArrayList<Card> hand, int points) {
        this.name = name;
        this.hand = new ArrayList<Card>();
        points = 0;

    }

    public ArrayList<Card> getHand() {
        return this.hand;
    }

    public int getPoints() {
        return this.points;
    }

    public void addPoints(int point) {
        points+= point;
    }
    public void addPoints(ArrayList<Card> h)
    {
        hand.add(h);
    }
    public String toString()
    {
        return name + " has " + points + " " + name + "'s cards: " + hand;
    }
}

