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
        this.setTitle("CHEAT");
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setVisible(true);

    }
    public void paint(Graphics g)
    {
        int currentPlayer = backend.getCurrentPlayerIndex();
        // draw permanent display components
        g.setFont(new Font("Serif", Font.BOLD, 15));
        g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        g.drawImage(tableImage, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT, this);
        g.setColor(Color.black);
        g.setFont(new Font("Arial", Font.BOLD, 75));
        g.drawString("CHEAT", 380, 150);







        // draw pre-game display components

        if (backend.getGameState() == Game.PREGAME_STATE) {

            g.setFont(new Font("Serif", Font.BOLD, 50));
            g.setColor(Color.black);
            g.drawString("Welcome to Cheat!", 320, 275);

            g.setFont(new Font("Serif", Font.BOLD, 20));
            g.setColor(Color.white);
            int xInstructions = 20;
            int yInstructions = 400;
            g.drawString("Instructions: You will be given a hand of cards, and the specific rank of the card that must " +
                    "be played will start", xInstructions, yInstructions);
            g.drawString("at the lowest rank and go up in increasing order. You must declare how many cards of the specific rank " +
                    "you will ", xInstructions, yInstructions + 25);
            g.drawString("play. This goes around to all of the players. You can lie about how many you have, but if someone " +
                    "else correctly ", xInstructions, yInstructions + (25 * 2));
            g.drawString("calls that you are lying, then you have to pick up all of the cards from the central pile, which is where" +
                    " everyone", xInstructions, yInstructions + (25 * 3));
            g.drawString("has put their cards. However, if the challenging player is wrong about you lying, then they must take all of the", xInstructions, yInstructions + (25 * 4));
            g.drawString("cards in the pile. The first person to discard" + " all of their cards is the winner!", xInstructions, yInstructions + (25 * 5));
            g.setColor(Color.magenta);
            g.drawString("Please enter the players names in the console to get started...", xInstructions, yInstructions + (25 * 7));

        }

        // draw in-game display components

        if (backend.getGameState() == Game.INTURN_STATE) {

            if (!backend.getPot().isEmpty()) {
                g.drawImage(cardBack, (WINDOW_WIDTH / 2) - CARD_WIDTH / 2, 400 - cardHeight / 2, CARD_WIDTH, cardHeight, this);
            }
            g.setFont(new Font("Serif", Font.ITALIC, 40));
            g.setColor(Color.white);
            g.drawString("There are currently " + String.valueOf(backend.getPot().size()) + " cards in the pile.", 170, 280);

            //int currentPlayer = backend.getCurrentPlayerIndex();
            int cardsInHand = backend.getCurrentPlayers().get(currentPlayer).getHand().size();
            int handWidth = (cardsInHand - 1) * 25 + CARD_WIDTH;
            int startXPosition = (WINDOW_WIDTH - handWidth) / 2;
            for (int i = 0; i < cardsInHand; i++) {
                g.setFont(new Font("Serif", Font.PLAIN, 30));
                g.setColor(Color.white);
                g.drawString(backend.getCurrentPlayers().get(currentPlayer).getName() + "'s hand", startXPosition, 700);

                g.setColor(Color.MAGENTA);
                g.drawString("Please play your turn using the console window...", startXPosition, 750);
                backend.getCurrentPlayers().get(currentPlayer).getHand().get(i).draw(g, startXPosition + (i * 25), 500, this);

            }
        }

        if (backend.getGameState() == Game.POSTTURN_STATE) {
            g.setFont(new Font("Serif", Font.PLAIN, 40));
            g.drawString(backend.getCurrentPlayers().get(currentPlayer).getName() + " played " + backend.getLastTurnQuantity() + " " + backend.getLastTurnRank() + "s",100,400);
            g.drawString("Do any of the other players want to challenge?", 100, 500);
            g.setColor(Color.MAGENTA);
            g.drawString("Please initiate or decline challenge using the console window...", 100, 600);
        }

        if (backend.getGameState() == Game.POSTGAME_STATE) {
            winningScreen(g);
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
        int winningPlayer = backend.getCurrentPlayerIndex();
        g.drawString(backend.getCurrentPlayers().get(winningPlayer).getName() + " wins!", (WINDOW_HEIGHT / 2) - 100, WINDOW_HEIGHT / 2);
        //g.drawString("It's a Tie", (WINDOW_HEIGHT / 2) - 100, 400);
    }




}