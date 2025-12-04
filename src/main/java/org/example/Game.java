package org.example;

public class Game {
    private Arraylist<Card> set;
    public Game()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What is your name: ");
        String name = scanner.nextLine();


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


