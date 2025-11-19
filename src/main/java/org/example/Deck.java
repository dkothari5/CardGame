public class Deck {
    private ArrayList<Integer> cards;
    private int cardsLeft;
    public Deck(int[] ranks, String[] suits, double[] pointValues)
    {
        this.cards = new ArrayList<>();




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
        return cardsLeft;
    }
    public

}
