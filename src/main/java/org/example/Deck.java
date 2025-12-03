import java.util.ArrayList;
public class Deck {
    private ArrayList<Card> theDeck;
    private int cardsLeft;
    public Deck(String[] ranks, String[] suits, int[] pointValues)
    {
        for(int i = 0; i < ranks.length; i++)
        {
            for(int j = 0; j < suits.length; j++)
            {
                Card c = new Card(ranks[i], suits[j], pointValues[i]);
                theDeck.add(c);
            }
            cardsLeft == theDeck.size();





        }




    }
    public boolean isEmpty()
    {
        if(cardsLeft == 0)
        {
            return true;
        }
        return false;

    }

    public int getCardsLeft() {
        return this.cardsLeft;
    }
    public ArrayList<Card> deal()
    {
        if(cardsLeft == 0)
        {
            return null;
        }
        return theDeck.get(cardsLeft - 1);
        cardsLeft--;

    }
    public void shuffle()
    {
        for(int i = theDeck.size() - 1; i <= 0; i++)
        {
            int num  = (int)(Math.random() * (i+1) + 0.5);
            theDeck.remove(i);
            theDeck.add(i, num);
        }
        cardsLeft() = theDeck.size();

    }



}
