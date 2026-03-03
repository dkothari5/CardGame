package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import static org.example.Card.CARD_HEIGHT;
import static org.example.Card.CARD_WIDTH;

public class Gameview extends JFrame
{
    private Image tableImage;
    private Image cardBack;
    public static final int CARD_WIDTH = 100;
    public static final int cardHeight = 150;
    private ArrayList<Image> cardImages;
    private final int WINDOW_WIDTH = 1000;
    private final int WINDOW_HEIGHT = 800;
    private final int TITLE_BAR_HEIGHT = 23;
    private Game backend;
    public Gameview(Game backend)
    {
        this.backend = backend;
        cardBack = new ImageIcon("Resources/Cards/back.png").getImage();
        tableImage = new ImageIcon("Resources/cardtable.jpg").getImage();


        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Cheat");
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setVisible(true);

    }
    public void paint(Graphics g)
    {
        g.setFont(new Font("Serif", Font.BOLD, 15));
        g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        g.drawImage(tableImage, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT, this);
        g.drawImage(cardBack, 420 - CARD_WIDTH / 2, 400 - cardHeight / 2, CARD_WIDTH, cardHeight, this);

        g.setFont(new Font("Serif", Font.BOLD, 50));
        g.setColor(Color.black);
        g.drawString("Welcome to Cheat!", 250, 150);
        g.setFont(new Font("Serif", Font.BOLD, 20));
        g.setColor(Color.red);
        int xInstructions = 0;
        int yInstructions = 570;
        g.drawString("Instructions: You will be given a hand of cards, and the specific rank of the card that must " +
                "be played will start at",xInstructions, yInstructions);
        g.drawString("the lowest rank and go up in increasing order. You must declare how many cards of the specific rank " +
                "you will play.", xInstructions, yInstructions + 25);
        g.drawString("This goes around to all of the players. You can lie about how many you have, but if someone " +
                "else correctly calls that",xInstructions, yInstructions + (25*2));
        g.drawString("you are lying, then you have to pick up all of the cards from the central pile, which is where" +
                " everyone has put their",xInstructions, yInstructions + (25*3));
        g.drawString("cards. However, if the challenging player is wrong about you lying, then they must take all of the" +
                " cards in the pile. ", xInstructions, yInstructions + (25 * 4));
        g.drawString("The first person to discard" + " all of their cards is the winner!", xInstructions, yInstructions + (25*5));
        g.setColor(Color.black);
        g.setFont(new Font("Arial", Font.BOLD, 75));
        g.drawString("Cheat", 350, 100);

        int currentPlayer = backend.getCurrentPlayerIndex();
        for(int i = 0; i < backend.getCurrentPlayers().get(currentPlayer).getHand().size(); i++)
        {
            g.drawString(backend.getCurrentPlayers().get(currentPlayer).getName() + "'s Hand", 340, 750);
            backend.getCurrentPlayers().get(currentPlayer).getHand().get(i).draw(g,100 + (i*25), 500, this);

        }



        //if (backend.getTheDeck().getCardsLeft() != 0) {
            //g.drawImage(cardBack, 400 - cardWith / 2, 400 - cardHeight / 2, cardWith, cardHeight, this);
        //}

    }
    public ArrayList<Image> getImages()
    {
        return cardImages;
    }
    public void winningScreen(Graphics g) {
        g.setColor(new Color(173,216,230));
        g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        g.setColor(Color.black);
        // Draws who the winner was or if it was a tie

            // g.drawString(g.get(winningPlayer).getName() + " wins!", (WINDOW_HEIGHT / 2) - 100, WINDOW_HEIGHT / 2);
            g.drawString("It's a Tie", (WINDOW_HEIGHT / 2) - 100, 400);
    }




}