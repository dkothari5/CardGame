package org.example;

import java.awt.*;

public class Card {
    private String rank;
    private String suit;
    private int value;
    private Gameview window;
    private Image cardImage;
    public static final int CARD_WIDTH = 100;
    public static final int CARD_HEIGHT = 150;


    public Card(String rank, String suit, int value, Image cardImage)
    {
        this.rank = rank;
        this.suit = suit;
        this.value = value;
        this.cardImage = cardImage;
    }

    public String getRank() {
        return rank;
    }

    public String getSuit() {
        return suit;
    }

    public int getValue() {
        return value;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    public void setValue(int value) {
        this.value = value;
    }

    /* The toString method outputs the value of the card in a format that is easily understood
    by the user.
     */
    @Override
    public String toString() {
        return rank + " of " + suit;
    }
    public void draw(Graphics g, int x, int y, Gameview window) {
        g.drawImage(cardImage, x,y,CARD_WIDTH, CARD_HEIGHT, window);
    }
    public void drawBacks(Graphics g, Image i, int x, int y) {
        g.drawImage(i, x,y,CARD_WIDTH, CARD_HEIGHT, window);
    }
}
