package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

public class Gameview extends JFrame
{
    private Image tableImage;
    private Image cardBack;
    public static final int cardWith = 100;
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
        g.setColor(Color.white);
        g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        g.drawImage(tableImage, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT, this);
        g.drawImage(cardBack, 400 - cardWith / 2, 400 - cardHeight / 2, cardWith, cardHeight, this);

        g.setFont(new Font("Serif", Font.BOLD, 50));
        g.setColor(Color.white);
        g.drawString("Welcome to Cheat!", 250, 150);
        g.setFont(new Font("Serif", Font.BOLD, 20));
        int xInstructions = 25;
        int yInstruction = 525;
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