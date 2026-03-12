package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.awt.event.MouseEvent;

import static org.example.Card.CARD_HEIGHT;
import static org.example.Card.CARD_WIDTH;

public class Gameview extends JFrame implements MouseListener {
    private Image tableImage;
    private Image cardBack;
    public static final int CARD_WIDTH = 100;
    public static final int cardHeight = 150;
    private int currentPlayer;
    private ArrayList<Image> cardImages;
    private final int WINDOW_WIDTH = 1000;
    private final int WINDOW_HEIGHT = 800;
    private final int TITLE_BAR_HEIGHT = 23;
    private final int HEADING_SIZE = 75;
    private final int NORMAL_TEXT_SIZE = 40;
    private final int LINEBREAK_VERTICAL_SHIFT = 25;
    private final int HORIZONTAL_SHIFT_BETWEEN_CARDS = 25;
    private Game backend;

    // Chase's instance variables
    public static final int BUTTON_START_X = 200;
    public static final int BUTTON_START_Y = 650;
    public static final int BUTTON_WIDTH = 200;
    public static final int BUTTON_HEIGHT  = 100;

    public Gameview(Game backend) {
        this.backend = backend;
        currentPlayer = backend.getCurrentPlayerIndex();
        // Saves card and table image files into Image objects
        cardBack = new ImageIcon("Resources/Cards/back.png").getImage();
        tableImage = new ImageIcon("Resources/cardtable.jpg").getImage();

        // Sets up the window
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("CHEAT");
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setVisible(true);

        // Chase: adds mouse listener
        this.addMouseListener(this);
    }

    public void paint(Graphics g) {
        // Draws permanent display components
        g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        g.drawImage(tableImage, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT, this);
        g.setFont(new Font("Arial", Font.BOLD, HEADING_SIZE));
        g.drawString("CHEAT", 380, 150);

        if (backend.getGameState() == Game.PREGAME_STATE) {
            preGameDisplay(g);
        }

        else if (backend.getGameState() == Game.INTURN_STATE) {
            inTurnDisplay(g);
        }

        else if (backend.getGameState() == Game.POSTTURN_STATE) {
            postTurnDisplay(g);
        }

        else if (backend.getGameState() == Game.NEXTTURN_STATE) {
            nextTurnDisplay(g);
        }

        else if (backend.getGameState() == Game.POSTGAME_STATE) {
            postGameDisplay(g);
        }



    }

    public ArrayList<Image> getImages() {
        return cardImages;
    }

    // Draws pre-game display components including instructions
    public void preGameDisplay(Graphics g) {
        g.setFont(new Font("Serif", Font.BOLD, HEADING_SIZE - 25));
        g.setColor(Color.black);
        g.drawString("Welcome to Cheat!", 320, 275);
        g.setFont(new Font("Serif", Font.BOLD, NORMAL_TEXT_SIZE / 2));
        g.setColor(Color.white);
        int xInstructions = 20;
        int yInstructions = 400;
        g.drawString("Instructions: You will be given a hand of cards, and the specific rank of the card that must " +
                "be played will start", xInstructions, yInstructions);
        g.drawString("at the lowest rank and go up in increasing order. You must declare how many cards of the specific rank " +
                "you will ", xInstructions, yInstructions + LINEBREAK_VERTICAL_SHIFT);
        g.drawString("play. This goes around to all of the players. You can lie about how many you have, but if someone " +
                "else correctly ", xInstructions, yInstructions + (LINEBREAK_VERTICAL_SHIFT * 2));
        g.drawString("calls that you are lying, then you have to pick up all of the cards from the central pile, which is where" +
                " everyone", xInstructions, yInstructions + (LINEBREAK_VERTICAL_SHIFT * 3));
        g.drawString("has put their cards. However, if the challenging player is wrong about you lying, then they must take all of the", xInstructions, yInstructions + (LINEBREAK_VERTICAL_SHIFT * 4));
        g.drawString("cards in the pile. The first person to discard" + " all of their cards is the winner!", xInstructions, yInstructions + (LINEBREAK_VERTICAL_SHIFT * 5));
        g.setColor(Color.magenta);
        g.drawString("Please enter the players names in the console to get started...", xInstructions, yInstructions + (LINEBREAK_VERTICAL_SHIFT * 7));
    }

    // Draws display components for a player's turn, including the player's hand
    public void inTurnDisplay(Graphics g) {

        if (!backend.getPot().isEmpty()) {
            g.drawImage(cardBack, (WINDOW_WIDTH / 2) - CARD_WIDTH / 2, 400 - cardHeight / 2, CARD_WIDTH, cardHeight, this);
        }
        g.setFont(new Font("Serif", Font.ITALIC, NORMAL_TEXT_SIZE));
        g.setColor(Color.white);
        g.drawString("There are currently " + String.valueOf(backend.getPot().size()) + " cards in the pile.", 170, 280);
        drawHand(g);

    }
    // Displays the user's hand in the display
    public void drawHand(Graphics g) {
        currentPlayer = backend.getCurrentPlayerIndex();

        // determine the left position of the hand so that the hand is displayed horizontally centered, regardless of number of cards in the hand
        int cardsInHand = backend.getCurrentPlayers().get(currentPlayer).getHand().size();
        int handWidth = (cardsInHand - 1) * HORIZONTAL_SHIFT_BETWEEN_CARDS + CARD_WIDTH;
        int startXPosition = (WINDOW_WIDTH - handWidth) / 2;
        for (int i = 0; i < cardsInHand; i++) {
            backend.getCurrentPlayers().get(currentPlayer).getHand().get(i).draw(g, startXPosition + (i * HORIZONTAL_SHIFT_BETWEEN_CARDS), 500, this);
        }
        g.setFont(new Font("Serif", Font.PLAIN, NORMAL_TEXT_SIZE - 10));
        g.setColor(Color.white);
        g.drawString(backend.getCurrentPlayers().get(currentPlayer).getName() + "'s hand", startXPosition, 700);
        g.setColor(Color.MAGENTA);
        g.drawString("Please play your turn using the console window...", startXPosition, 750);

    }
        // Draws display components for post player turn (to manage the challenge process)
        public void postTurnDisplay (Graphics g) {
            g.setFont(new Font("Serif", Font.PLAIN, NORMAL_TEXT_SIZE));
            g.drawString(backend.getCurrentPlayers().get(currentPlayer).getName() + " played " + backend.getLastTurnQuantity() + " " + backend.getLastTurnRank() + "s", 100, 400);
            g.drawString("Do any of the other players want to challenge?", 100, 500);

            // Call Cheats that Chase Implemented
            callCheatDisplay(g, BUTTON_START_X, BUTTON_START_Y);

            g.setColor(Color.MAGENTA);
            g.setFont(new Font("Serif", Font.PLAIN, NORMAL_TEXT_SIZE - 10));
            g.drawString("Please initiate or decline by selecting an option below...", 100, 600);
        }

        // Function for the creating buttons
        public void callCheatDisplay(Graphics g, int x, int y) {
            g.setColor(new Color(103, 199, 52));
            g.fillRect(x, y, BUTTON_WIDTH, BUTTON_HEIGHT);
            g.setColor(Color.black);
            g.setFont(new Font("Arial", Font.BOLD, 28));
            g.drawString("Truth", x+ 65, y + 60);
            g.setColor(new Color(199, 52, 52));
            g.fillRect(x + 400, y, BUTTON_WIDTH, BUTTON_HEIGHT);
            g.setColor(Color.black);
            g.drawString("Lie", x + 480, y +60);
        }

        // Functions to change the variable of the cheat button and set off the code in the frontend once
        // buttons are clicked.
        public void truthClicked() {
            backend.setCheatQuestion(1);
        }

        public void falseClicked() {
            backend.setCheatQuestion(2);
        }

    // Sets up display to prompt the user to transition to the next player's turn so that a player doesn't see the prior player's hand
    public void nextTurnDisplay (Graphics g) {
        g.setFont(new Font("Serif", Font.PLAIN, NORMAL_TEXT_SIZE - 10));
        if (backend.getChallengeStatus() == Game.CHALLENGE_SUCCESS) {
            g.drawString("The challenge was successful!!!", 100, 300);
        }
        else if (backend.getChallengeStatus() == Game.CHALLENGE_FAILED) {
            g.drawString("The challenge failed!!!", 100, 300);
        }
        g.setColor(Color.MAGENTA);
        g.setFont(new Font("Serif", Font.PLAIN, NORMAL_TEXT_SIZE - 10));
        g.drawString("Press 1 when the next player is ready for their turn...", 100, 600);
    }
    // Draws display components for post game state, indicating which player won
    public void postGameDisplay (Graphics g){
            g.setColor(new Color(173, 216, 230));
            g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
            g.setColor(Color.black);
            int winningPlayer = backend.getCurrentPlayerIndex();
            g.drawString(backend.getCurrentPlayers().get(winningPlayer).getName() + " wins!", (WINDOW_HEIGHT / 2) - 70, WINDOW_HEIGHT / 2);
        }

    // Chase's code for the mouse events
    @Override
    public void mousePressed(MouseEvent e) {
        // Returns if not in the correct state for the rectangles
        if (backend.getGameState() != Game.POSTTURN_STATE) {
            return;
        }
        // Creates new rectangle for the buttons
        Rectangle truth = new Rectangle(BUTTON_START_X, BUTTON_START_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
        Rectangle lie = new Rectangle(BUTTON_START_X + 400, BUTTON_START_Y, BUTTON_WIDTH, BUTTON_HEIGHT);

        // Checks if the x and y of the mouse click is in the rectangles, and if so, set off the function.
        if (truth.contains(e.getX(), e.getY())) {
            truthClicked();
            this.repaint();
        }
        if (lie.contains(e.getX(), e.getY())) {
            falseClicked();
            this.repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }


    }




